package com.daoki.basic.service.impl;

import com.alibaba.fastjson.JSON;
import com.daoki.basic.VO.request.*;
import com.daoki.basic.VO.response.FuzzySearchTopicFormVO;
import com.daoki.basic.VO.response.PageVO;
import com.daoki.basic.VO.response.TopicVO;
import com.daoki.basic.entity.Content;
import com.daoki.basic.entity.OperateHistory;
import com.daoki.basic.entity.Topic;
import com.daoki.basic.enums.ContentStatusEnum;
import com.daoki.basic.enums.ErrorEnum;
import com.daoki.basic.enums.TopicOperateEnum;
import com.daoki.basic.enums.TopicStatusEnum;
import com.daoki.basic.exception.CustomException;
import com.daoki.basic.mapper.ContentConvert;
import com.daoki.basic.mapper.TopicConvert;
import com.daoki.basic.repository.ContentRepository;
import com.daoki.basic.repository.OperateHistoryRepository;
import com.daoki.basic.repository.TopicRepository;
import com.daoki.basic.service.IContentService;
import com.daoki.basic.service.ITopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    private TopicRepository topicRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private OperateHistoryRepository operateHistoryRepository;

    @Value("${daoki.web.hotTopicUrlPre:}${server.servlet.context-path:}")
    private String linkPre;

    /**
     * create topic
     * @param createTopicVO a topic when creating a new topic
     */
    @Override
    public void createTopic(CreateTopicVO createTopicVO) {
        log.info("<operator {} is creating a new topic>",
                createTopicVO.getOperator());
        Topic topic = TopicConvert.INSTANCE.createVo2Do(createTopicVO);
        topic.setGmtCreate(new Date());
        topic.setViewCount(0);
        topic.setStatus(TopicStatusEnum.STATUS_TOPIC_RELEASED.getCode());
        Topic topicSave = topicRepository.save(topic);

        // save the content in the database one by one
        for (CreateContentVO createContentVO : createTopicVO.getContent()){
            Content content = contentService.createContent(createContentVO);
            content.setTopicId(topicSave.getId());
            contentRepository.save(content);
            log.info("successfully create a new content with id {}",
                    content.getId());
        }

        log.info("<successfully create a new topic with id {}>",
                topicSave.getId());

        // save the operation history in the database
        OperateHistory operateHistory = new OperateHistory();
        operateHistory.setOperator(createTopicVO.getOperator());
        operateHistory.setAction(TopicOperateEnum.OPERATE_CREATE);
        operateHistory.setTime(new Date());
        operateHistory.setTopicId(topicSave.getId());
        operateHistoryRepository.save(operateHistory);
    }

    /**
     * update topic
     * @param updateTopicVO a topic when updating a topic
     */
    @Override
    public void updateTopic(UpdateTopicVO updateTopicVO) {

        log.info("<operator {} is updating a new topic with id {}>",
                updateTopicVO.getOperator(), updateTopicVO.getTopicId());

        Topic topic = topicExist(updateTopicVO.getTopicId());

        // if the topic with this topic id dont exist, an error will be thrown
        if (Objects.isNull(topic)){
            log.error("<Fail to update the topic with id {}: the topic with id {} doesn't exist>",
                    updateTopicVO.getTopicId(), updateTopicVO.getTopicId());
            throw new CustomException(ErrorEnum.UPDATE_TOPIC_ERROR_TOPIC_NONEXIST, "updateTopic");
        }

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
                    log.error("<Fail to update the topic with id {}: " +
                                    "the updated content with id {} doesn't exist>",
                            updateContentVO.getTopicId(), updateTopicVO.getTopicId());
                    throw new CustomException(ErrorEnum.UPDATE_TOPIC_ERROR_CONTENT_NONEXIST, "updateTopic");
                }
            }
        }

        // get ids of all contents contained in topic with this topic id in database
        Set<String> existedContentId =
                contentRepository.findContentsByTopicIdAndStatus(
                        updateTopicVO.getTopicId(),ContentStatusEnum.STATUS_CONTENT_RELEASED.getCode()
                        ).stream().map(Content::getId).collect(Collectors.toSet());

        // if the updated content dont contain the content id, the content need be created
        // or the original content will be replaced with the updated content
        for (UpdateContentVO updateContentVO : updateTopicVO.getContent()){
            if (Objects.nonNull(updateContentVO.getContentId())){
                // get the content ids exist in database but dont exist in updated topic
                existedContentId.remove(updateContentVO.getContentId());
                contentService.updateContent(updateContentVO);
                log.info("successfully update a content with id {}", updateContentVO.getContentId());
            }else {
                log.debug("one updated content in the updated topic without content id");
                log.info("creating a new content");
                CreateContentVO createContentVO = new CreateContentVO();
                BeanUtils.copyProperties(updateContentVO, createContentVO);
                Content content = contentService.createContent(createContentVO);
                content.setTopicId(updateTopicVO.getTopicId());
                contentRepository.save(content);
                log.info("successfully create a new content with id {}", content.getId());
            }
        }

        // delete the content ids dont exist in updated topic
        log.debug("the contents in database with ids {} doesn't contained in updated topic, " +
                "and these contents will be deleted",
                JSON.toJSONString(existedContentId));
        for (String deleteContentId : existedContentId){
            contentService.deleteContent(deleteContentId);
            log.info("successfully delete the content with content id {}", deleteContentId);
        }

        // replace the original topic with the updated topic
        Topic topicSave = TopicConvert.INSTANCE.updateVo2Do(updateTopicVO);
        topicSave.setGmtCreate(topic.getGmtCreate());
        topicSave.setViewCount(topic.getViewCount());
        topicSave.setStatus(topic.getStatus());
        topicRepository.save(topicSave);
        log.info("<successfully update the topic with id {}>", updateTopicVO.getTopicId());

        // save the operation history recording in the database
        OperateHistory operateHistory = new OperateHistory();
        operateHistory.setOperator(updateTopicVO.getOperator());
        operateHistory.setAction(TopicOperateEnum.OPERATE_UPDATE);
        operateHistory.setTime(new Date());
        operateHistory.setTopicId(updateTopicVO.getTopicId());
        operateHistoryRepository.save(operateHistory);
    }

    /**
     * delete topic according to topic id
     * @param deleteTopicVO vo for deleting a topic
     */
    @Override
    public void deleteTopic(DeleteTopicVO deleteTopicVO) {

        log.info("<operator {} is deleting the topic with id {}>",
                deleteTopicVO.getOperator(), deleteTopicVO.getTopicId());

        Topic topic = topicExist(deleteTopicVO.getTopicId());

        // if the topic with this topic id cannot be found, an error will be thrown
        if (Objects.isNull(topic)){
            log.error("<Fail to delete the topic with id {}: " +
                            "the deleted topic with id {} doesn't exist>",
                    deleteTopicVO.getTopicId(), deleteTopicVO.getTopicId());
            throw new CustomException(ErrorEnum.DELETE_TOPIC_ERROR, "deleteTopic");
        }

        for (Content content : contentRepository.findContentsByTopicId(deleteTopicVO.getTopicId())){
            contentService.deleteContent(content.getId());
            log.info("successfully delete the content with id {}", content.getId());
        }

        topic.setStatus(TopicStatusEnum.STATUS_TOPIC_DELETED.getCode());
        topicRepository.save(topic);
        log.info("<successfully delete the topic with id {}>", deleteTopicVO.getTopicId());

        OperateHistory operateHistory = new OperateHistory();
        operateHistory.setOperator(deleteTopicVO.getOperator());
        operateHistory.setAction(TopicOperateEnum.OPERATE_DELETE);
        operateHistory.setTime(new Date());
        operateHistory.setTopicId(deleteTopicVO.getTopicId());
        operateHistoryRepository.save(operateHistory);
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
        Topic topic = topicExist(id);
        // if the topic with this topic id cannot be found, an error will be thrown
        if (Objects.isNull(topic)){
            log.error("<Fail to search the topic with id {}>", id);
            throw new CustomException(ErrorEnum.GET_TOPIC_ERROR, "getTopic");
        }
        topic.setViewCount(topic.getViewCount() + 1);
        topicRepository.save(topic);
        TopicVO topicVO = TopicConvert.INSTANCE.do2Vo(topic);
        topicVO.setContent(
                contentRepository.findContentsByTopicIdAndStatus(
                        topic.getId(),ContentStatusEnum.STATUS_CONTENT_RELEASED.getCode()
                ).stream().map(ContentConvert.INSTANCE::do2Vo).collect(Collectors.toList()));
        if (Objects.isNull(topicVO.getContent())){
            log.debug("No content contained in the topic with id {}", id);
        }
        log.info("<successfully searching the topic with id {}>", id);
        return topicVO;
    }

    /**
     * whether the topic with topic id exist or not?
     * @param topicId topic id in database
     * @return topic do
     */
    private Topic topicExist(String topicId){
        Topic topic = topicRepository.findTopicById(topicId);
        if (Objects.isNull(topic) ||
                TopicStatusEnum.STATUS_TOPIC_DELETED.getCode().equals(topic.getStatus())){
            return null;
        }
        return topic;
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
                ContentStatusEnum.STATUS_CONTENT_DELETED.getCode().equals(content.getStatus())){
            return null;
        }
        return content;
    }
}
