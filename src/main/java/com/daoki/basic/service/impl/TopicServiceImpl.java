package com.daoki.basic.service.impl;

import com.alibaba.fastjson.JSON;
import com.daoki.basic.VO.request.*;
import com.daoki.basic.VO.response.*;
import com.daoki.basic.VO.response.user.UserVO;
import com.daoki.basic.entity.Content;
import com.daoki.basic.entity.OperateHistory;
import com.daoki.basic.entity.Topic;
import com.daoki.basic.entity.User;
import com.daoki.basic.enums.ContentStatusEnum;
import com.daoki.basic.enums.ErrorEnum;
import com.daoki.basic.enums.TopicOperateEnum;
import com.daoki.basic.enums.TopicStatusEnum;
import com.daoki.basic.exception.CustomException;
import com.daoki.basic.mapper.TopicConvert;
import com.daoki.basic.mapper.UserConvert;
import com.daoki.basic.repository.ContentRepository;
import com.daoki.basic.repository.OperateHistoryRepository;
import com.daoki.basic.repository.TopicRepository;
import com.daoki.basic.repository.UserRepository;
import com.daoki.basic.service.IContentService;
import com.daoki.basic.service.ITopicService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Alan
 * 2022-03-06
 * Description: the implement of the topic related service layer interface
 */
@Service
@Slf4j
public class TopicServiceImpl implements ITopicService {

    @Autowired
    private IContentService contentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private OperateHistoryRepository operateHistoryRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${daoki.web.hotTopicUrlPre:}${server.servlet.context-path:}")
    private String linkPre;

    /**
     * create topic
     * @param createTopicVO a topic when creating a new topic
     */
    @Override
    public String createTopic(CreateTopicVO createTopicVO) {

        log.info("<operator {} is creating a new topic>", getOperator());

        isQuotedRight(createTopicVO.getQuoteTopics());

        Topic topic = TopicConvert.INSTANCE.createVo2Do(createTopicVO);
        topic.setGmtCreate(new Date());
        topic.setViewCount(0);
        topic.setStatus(TopicStatusEnum.STATUS_TOPIC_RELEASED.getCode());
        topic.setContributor(getOperator());
        Topic topicSave = topicRepository.save(topic);

        // save the content in the database one by one
        if(CollectionUtils.isNotEmpty(createTopicVO.getContent())){
            for (CreateContentVO createContentVO : createTopicVO.getContent()){
                Content content = contentService.createContent(createContentVO);
                content.setTopicId(topicSave.getId());
                contentRepository.save(content);
                log.info("successfully create a new content with id {}", content.getId());
            }
        }

        log.info("<successfully create a new topic with id {}>", topicSave.getId());
        createOperationHistory(TopicOperateEnum.OPERATE_CREATE, topicSave.getId());
        return topicSave.getId();
    }

    /**
     * update topic
     * @param updateTopicVO a topic when updating a topic
     */
    @Override
    public void updateTopic(UpdateTopicVO updateTopicVO) {

        log.info("<operator {} is updating a new topic with id {}>", getOperator(), updateTopicVO.getTopicId());

        if (!isPermission(updateTopicVO.getTopicId())){
            log.error("<Fail to update the topic with id {}: the user {} doesn't have permission>",
                    updateTopicVO.getTopicId(), getOperator());
            throw new CustomException(ErrorEnum.UPDATE_TOPIC_ERROR_NOPERMISSION,"updateTopic");
        }

        Topic topic = topicRepository.findTopicById(updateTopicVO.getTopicId());

        // if the topic with this topic id dont exist, an error will be thrown
        if (Objects.isNull(topic)){
            log.error("<Fail to update the topic with id {}: the topic with id {} doesn't exist>",
                    updateTopicVO.getTopicId(), updateTopicVO.getTopicId());
            throw new CustomException(ErrorEnum.UPDATE_TOPIC_ERROR_NONEXIST, "updateTopic");
        }

        if (topic.getStatus().equals(TopicStatusEnum.STATUS_TOPIC_DELETED.getCode())){
            log.error("<Fail to update the topic with id {}: the topic with id {} have been deleted>",
                    updateTopicVO.getTopicId(), updateTopicVO.getTopicId());
            throw new CustomException(ErrorEnum.UPDATE_TOPIC_ERROR_DELETED,"updateTopic");
        }

        if (CollectionUtils.isNotEmpty(updateTopicVO.getContent())){
            for (UpdateContentVO updateContentVO : updateTopicVO.getContent()){
                // if topic id of any updated content isn't equal to this topic id, an error will be thrown
                if (!updateContentVO.getTopicId().equals(updateTopicVO.getTopicId())){
                    log.error("<Fail to update the topic with id {}: " +
                                    "the updated content's topic id {} isn't equal to updated topic's topic id {}>",
                            updateContentVO.getTopicId(), updateTopicVO.getTopicId(), updateTopicVO.getTopicId());
                    throw new CustomException(ErrorEnum.UPDATE_TOPIC_ERROR_TOPICID_MISMATCH, "updateTopic");
                }

                // if the updated content contains the content id, the content already exist
                if (Objects.nonNull(updateContentVO.getContentId())){
                    // in this case, if the content with this content id and topic id cannot be found, an error will be thrown
                    if (Objects.isNull(
                            contentExist(updateContentVO.getContentId(), updateContentVO.getTopicId())
                    )){
                        log.error("<Fail to update the topic with id {}: the updated content with id {} doesn't exist>",
                                updateContentVO.getTopicId(), updateTopicVO.getTopicId());
                        throw new CustomException(ErrorEnum.UPDATE_TOPIC_ERROR_CONTENT_NONEXIST, "updateTopic");
                    }
                }
            }
        }


        isQuotedRight(updateTopicVO.getQuoteTopics());

        // get ids of all contents contained in topic with this topic id in database
        Set<String> existedContentId =
                contentRepository.findContentsByTopicIdAndStatus(
                        updateTopicVO.getTopicId(),ContentStatusEnum.STATUS_CONTENT_RELEASED.getCode()
                        ).stream().map(Content::getId).collect(Collectors.toSet());

        // if the updated content dont contain the content id, the content need be created
        // or the original content will be replaced with the updated content
        if (CollectionUtils.isNotEmpty(updateTopicVO.getContent())){
            for (UpdateContentVO updateContentVO : updateTopicVO.getContent()){
                if (Objects.nonNull(updateContentVO.getContentId())){
                    // get the content ids exist in database but dont exist in updated topic
                    contentService.updateContent(updateContentVO);
                    log.info("successfully update a content with id {}", updateContentVO.getContentId());
                    existedContentId.remove(updateContentVO.getContentId());
                }else {
                    log.debug("one updated content in the updated topic without content id");
                    log.info("creating a new content");
                    Content content = contentService.createContent(updateContentVO);
                    content.setTopicId(updateTopicVO.getTopicId());
                    contentRepository.save(content);
                    log.info("successfully create a new content with id {}", content.getId());
                }
            }
        }

        // delete the content ids dont exist in updated topic
        log.info("the contents in database with ids {} doesn't contained in updated topic, " +
                "and these contents will be deleted",
                JSON.toJSONString(existedContentId));
        if (CollectionUtils.isNotEmpty(existedContentId)){
            for (String deleteContentId : existedContentId){
                contentService.deleteContent(deleteContentId);
                log.info("successfully delete the content with content id {}", deleteContentId);
            }
        }

        // replace the original topic with the updated topic
        Topic topicSave = TopicConvert.INSTANCE.updateVo2Do(updateTopicVO);
        topicSave.setGmtCreate(topic.getGmtCreate());
        topicSave.setViewCount(topic.getViewCount());
        topicSave.setStatus(topic.getStatus());
        topicSave.setContributor(getOperator());
        topicRepository.save(topicSave);

        log.info("<successfully update the topic with id {}>", updateTopicVO.getTopicId());

        // save the operation history recording in the database
        createOperationHistory(TopicOperateEnum.OPERATE_UPDATE,updateTopicVO.getTopicId());
    }

    /**
     * delete topic according to topic id
     * @param topicId topic id
     */
    @Override
    public void deleteTopic(String topicId) {

        log.info("<operator {} is deleting the topic with id {}>", getOperator(), topicId);

        if (!isPermission(topicId)){
            log.error("<Fail to delete the topic with id {}: the user {} doesn't have permission>",
                    topicId, getOperator());
            throw new CustomException(ErrorEnum.DELETE_TOPIC_ERROR_NOPERMISSION,"deleteTopic");
        }

        Topic topic = topicRepository.findTopicById(topicId);

        // if the topic with this topic id cannot be found, an error will be thrown
        if (Objects.isNull(topic)){
            log.error("<Fail to delete the topic with id {}: the topic doesn't exist>", topicId);
            throw new CustomException(ErrorEnum.DELETE_TOPIC_ERROR_NONEXIST, "deleteTopic");
        }

        if (topic.getStatus().equals(TopicStatusEnum.STATUS_TOPIC_DELETED.getCode())){
            return;
        }

        List<Content> contentList = contentRepository.findContentsByTopicIdAndStatus(
                topicId, TopicStatusEnum.STATUS_TOPIC_RELEASED.getCode());
        if (CollectionUtils.isNotEmpty(contentList)){
            for (Content content : contentList){
                contentService.deleteContent(content.getId());
                log.info("successfully delete the content with id {}", content.getId());
            }
        }

        topic.setStatus(TopicStatusEnum.STATUS_TOPIC_DELETED.getCode());
        topicRepository.save(topic);

        log.info("<successfully delete the topic with id {}>", topicId);

        createOperationHistory(TopicOperateEnum.OPERATE_DELETE, topicId);
    }

    /**
     * search topics fuzzily according keyword
     * @param fuzzySearchTopicVO key word used for searching fuzzily
     * @return page of fuzzy searching topic vo
     */
    @Override
    public PageVO<FuzzySearchTopicFormVO> getTopicByName(FuzzySearchTopicVO fuzzySearchTopicVO) {

        log.info("<searching fuzzily the topic by keyword {}>", fuzzySearchTopicVO.getKeyword());

        Pageable pageable = PageRequest.of(fuzzySearchTopicVO.getPage(),fuzzySearchTopicVO.getSize());
        Page<Topic> topicPage = topicRepository.findTopicsByStatusAndNameLike(
                pageable, TopicStatusEnum.STATUS_TOPIC_RELEASED.getCode(), fuzzySearchTopicVO.getKeyword());
        List<Topic> topicList = topicPage.getContent();
        List<FuzzySearchTopicFormVO> topicVOList = topicList.stream().map(topic -> {
                    FuzzySearchTopicFormVO fuzzySearchTopicFormVO = new FuzzySearchTopicFormVO();
                    fuzzySearchTopicFormVO.setTitle(topic.getName());
                    fuzzySearchTopicFormVO.setLink(linkPre+"/topic/id?id="+topic.getId());
                    fuzzySearchTopicFormVO.setDescription("nothing");
                    return fuzzySearchTopicFormVO;
                }).collect(Collectors.toList());

        if (topicPage.getNumberOfElements() == 0) {
            log.debug("the results of fuzzy searching is null by keyword {}", fuzzySearchTopicVO.getKeyword());
        }
        log.info("<successfully search fuzzily the topic by keyword {}>", fuzzySearchTopicVO.getKeyword());

        return new PageVO<>(fuzzySearchTopicVO.getPage(), fuzzySearchTopicVO.getSize(),
                topicPage.getTotalPages(), topicPage.getTotalElements(), topicVOList);
    }

    /**
     * find topic according by topic id
     * @param id id in database of topic
     * @return topic vo
     */
    @Override
    public TopicVO getTopicById(String id) {
        log.info("<searching the topic by id {}>", id);
        Topic topic = topicRepository.findTopicById(id);
        // if the topic with this topic id cannot be found, an error will be thrown
        if (Objects.isNull(topic)){
            log.error("<Fail to search the topic with id {}: doesn't exist>", id);
            throw new CustomException(ErrorEnum.GET_TOPIC_ERROR_NONEXIST, "getTopic");
        }
        if (topic.getStatus().equals(TopicStatusEnum.STATUS_TOPIC_DELETED.getCode())){
            log.error("<Fail to search the topic with id {}: have been deleted>", id);
            throw new CustomException(ErrorEnum.GET_TOPIC_ERROR_DELETED, "getTopic");
        }

        topic.setViewCount(topic.getViewCount() + 1);
        topicRepository.save(topic);
        TopicVO topicVO = TopicConvert.INSTANCE.do2Vo(topic);
        topicVO.setContent(contentService.getContentsByTopicIdAndStatus(
                topic.getId(),
                ContentStatusEnum.STATUS_CONTENT_RELEASED.getCode()));

        User user = userRepository.findByUserId(topic.getContributor());
        UserVO userVO = UserConvert.INSTANCE.Do2Vo(user);
        topicVO.setContributor(userVO);

        if (CollectionUtils.isNotEmpty(topic.getQuoteTopics())){
            List<QuoteTopicVO> quoteTopics = new ArrayList<>();
            for (String q : topic.getQuoteTopics()){
                QuoteTopicVO temp = new QuoteTopicVO();
                Topic topicTemp = topicRepository.findTopicById(q);
                temp.setTopicId(q);
                temp.setTitle(topicTemp.getName());
                temp.setLink(linkPre+"/topic/id?id="+q);
                temp.setScore("0");
                quoteTopics.add(temp);
            }
            topicVO.setQuoteTopics(quoteTopics);
        }

        log.info("<successfully searching the topic with id {}>", id);
        return topicVO;
    }

    @Override
    public PageVO<TopicPreviewVO> getAllTopics(GetAllTopicsVO getAllTopicsVO){

        Long userId = getOperator();

        log.info("<getting all topics belonged to user {}>", userId);

        Pageable pageable = PageRequest.of(getAllTopicsVO.getPage(),getAllTopicsVO.getSize());
        Page<Topic> topicPage = topicRepository.findTopicsByContributor(pageable, userId);
        List<Topic> topicList = topicPage.getContent();
        List<TopicPreviewVO> topicPreviewVOList = topicList.stream()
                .map(TopicConvert.INSTANCE::do2PreviewVo)
                .collect(Collectors.toList());

        if (topicPage.getNumberOfElements() == 0) {
            log.debug("no topic belonged to user {}", userId);
        }
        log.info("<successfully get all topics belonged to user {}>", userId);

        return new PageVO<>(getAllTopicsVO.getPage(), getAllTopicsVO.getSize(),
                topicPage.getTotalPages(), topicPage.getTotalElements(), topicPreviewVOList);
    }

    /**
     * whether the content with content id and topic id exist or not?
     * @param contentId content id in database
     * @param topicId topic id in database
     * @return content do
     */
    private Content contentExist(String contentId, String topicId){
        Content content = contentRepository.findContentByIdAndTopicId(contentId,topicId);
        if (Objects.isNull(content) ||
                !ContentStatusEnum.STATUS_CONTENT_RELEASED.getCode().equals(content.getStatus())){
            return null;
        }
        return content;
    }

    /**
     * whether quote topic repeatedly, whether the quoted topics exist, whether the quoted topics have been deleted
     * @param quoteTopics the quoted topics list
     */
    private void isQuotedRight(List<String> quoteTopics) {
        if (CollectionUtils.isNotEmpty(quoteTopics)){
            for (String q : quoteTopics){
                if (!topicRepository.existsById(q)){
                    log.error("<Failed: quoted topic {} doesn't exist>", q);
                    throw new CustomException(ErrorEnum.QUOTE_TOPIC_ERROR_NONEXIST,"");
                } else if (topicRepository.findTopicById(q).getStatus()
                        .equals(TopicStatusEnum.STATUS_TOPIC_DELETED.getCode())) {
                    log.error("<Failed: quoted topic {} has been deleted>", q);
                    throw new CustomException(ErrorEnum.QUOTE_TOPIC_ERROR_DELETED,"");
                }
            }
            Set<String> topicSet = new HashSet<>(quoteTopics);
            if (quoteTopics.size() != topicSet.size()){
                log.error("<Failed: quote topic repeatedly>");
                throw new CustomException(ErrorEnum.QUOTE_TOPIC_ERROR_REPEAT,"");
            }
        }
    }

    /**
     * get the user of current http request
     * @return user id
     */
    private Long getOperator(){
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert sra != null;
        HttpServletRequest request = sra.getRequest();
        UserVO userVO = (UserVO) request.getAttribute("user");
        return userVO.getUserId();
    }

    /**
     * if the user is the contributor, the user has permission to modify the topic
     * @param topicId the id of the topic needed to be modified in database
     * @return whether has permission
     */
    private Boolean isPermission(String topicId){
        Topic topic = topicRepository.findTopicById(topicId);
        return topic.getContributor() == getOperator();
    }

    private void createOperationHistory(TopicOperateEnum topicOperateEnum, String topicId){
        OperateHistory operateHistory = new OperateHistory();
        operateHistory.setOperator(getOperator());
        operateHistory.setAction(topicOperateEnum);
        operateHistory.setTime(new Date());
        operateHistory.setTopicId(topicId);
        operateHistoryRepository.save(operateHistory);
    }
}
