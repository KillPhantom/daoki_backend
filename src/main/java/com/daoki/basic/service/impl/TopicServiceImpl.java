package com.daoki.basic.service.impl;

import com.daoki.basic.VO.request.*;
import com.daoki.basic.VO.response.FuzzySearchTopicFormVO;
import com.daoki.basic.VO.response.PageVO;
import com.daoki.basic.VO.response.TopicVO;
import com.daoki.basic.entity.Content;
import com.daoki.basic.entity.OperateHistory;
import com.daoki.basic.entity.Topic;
import com.daoki.basic.enums.ErrorEnum;
import com.daoki.basic.enums.TopicOperateEnum;
import com.daoki.basic.exception.CustomException;
import com.daoki.basic.mapper.ContentConvert;
import com.daoki.basic.mapper.TopicConvert;
import com.daoki.basic.repository.ContentRepository;
import com.daoki.basic.repository.OperateHistoryRepository;
import com.daoki.basic.repository.TopicRepository;
import com.daoki.basic.service.IContentService;
import com.daoki.basic.service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSInput;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Alan
 * 2022-03-06
 * Description: the implement of the topic related service layer interface
 */
@Service
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
        Topic topic = TopicConvert.INSTANCE.createVo2Do(createTopicVO);
        topic.setGmtCreate(new Date());
        topic.setViewCount(0);
        Topic topicSave = topicRepository.save(topic);
        // save the content in the database one by one
        for (CreateContentVO createContentVO : createTopicVO.getContent()){
            Content content = contentService.createContent(createContentVO);
            content.setTopicId(topicSave.getId());
            contentRepository.save(content);
        }
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
        // if the topic with this topic id dont exist in database, an error will be thrown
        if (Objects.isNull(
                topicRepository.findTopicById(updateTopicVO.getTopicId()))){
            throw new CustomException(ErrorEnum.UPDATE_TOPIC_ERROR_NONEXIST, "updateTopic");
        }
        // get ids of all contents contained in topic with this topic id in database
        Set<String> existedContentId =
                contentRepository.findContentsByTopicId(updateTopicVO.getTopicId())
                        .stream().map(Content::getId).collect(Collectors.toSet());

        for (UpdateContentVO updateContentVO : updateTopicVO.getContent()){
            // if topic id of any updated content isn't equal to this topic id, an error will be thrown
            if (!updateContentVO.getTopicId().equals(updateTopicVO.getTopicId())){
                throw new CustomException(ErrorEnum.UPDATE_TOPIC_ERROR_MISMATCH, "updateTopic");
            }
            // if the updated content contains the content id, the content already exist in database
            // in this case, if the content with this content id cannot be found in database, an error will be thrown
            if (Objects.nonNull(updateContentVO.getContentId())
                    && !existedContentId.contains(updateContentVO.getContentId())){
                throw new CustomException(ErrorEnum.UPDATE_CONTENT_ERROR, "updateTopic");
            }
        }

        // if the updated content dont contain the content id, the content need be created
        // or the original content will be replaced with the updated content
        for (UpdateContentVO updateContentVO : updateTopicVO.getContent()){
            // get the content ids exist in database but dont exist in updated topic
            if (Objects.nonNull(updateContentVO.getContentId())){
                existedContentId.remove(updateContentVO.getContentId());
            }
            contentService.updateContent(updateContentVO);
        }
        // delete the content ids dont exist in updated topic
        for (String deleteContentId : existedContentId){
            contentService.deleteContent(deleteContentId);
        }

        // replace the original topic with the updated topic
        Topic topicSave = TopicConvert.INSTANCE.updateVo2Do(updateTopicVO);
        topicRepository.save(topicSave);

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
        // if the topic with this topic id cannot be found in database, an error will be thrown
        if (Objects.isNull(
                topicRepository.findTopicById(deleteTopicVO.getTopicId()))){
            throw new CustomException(ErrorEnum.DELETE_TOPIC_ERROR, "deleteTopic");
        }
        topicRepository.deleteTopicById(deleteTopicVO.getTopicId());
        for (Content content : contentRepository.findContentsByTopicId(deleteTopicVO.getTopicId())){
            contentService.deleteContent(content.getId());
        }

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
        Pageable pageable = PageRequest.of(fuzzySearchTopicVO.getPage(),fuzzySearchTopicVO.getSize());
        Page<Topic> topicPage = topicRepository.findTopicsByNameLike(pageable, fuzzySearchTopicVO.getKeyword());
        List<Topic> topicList = topicPage.getContent();

        List<FuzzySearchTopicFormVO> topicVOList
                = topicList.stream().map(topic -> {
                    FuzzySearchTopicFormVO fuzzySearchTopicFormVO = new FuzzySearchTopicFormVO();
                    fuzzySearchTopicFormVO.setTitle(topic.getName());
                    fuzzySearchTopicFormVO.setLink(linkPre+"/topic/id?id="+topic.getId());
                    fuzzySearchTopicFormVO.setDescription("nothing");
                    return fuzzySearchTopicFormVO;
                }).collect(Collectors.toList());

        return new PageVO<>(fuzzySearchTopicVO.getPage(),
                fuzzySearchTopicVO.getSize(),
                topicPage.getTotalPages(),
                topicPage.getTotalElements(),
                topicVOList);
    }

    /**
     * find topic according by topic id
     * @param id id in database of topic
     * @return topic vo
     */
    @Override
    public TopicVO getTopicById(String id) {
        Topic topic = topicRepository.findTopicById(id);
        // if the topic with this topic id cannot be found in database, an error will be thrown
        if (Objects.isNull(topic)){
            throw new CustomException(ErrorEnum.GET_TOPIC_ERROR, "getTopic");
        }
        topic.setViewCount(topic.getViewCount() + 1);
        topicRepository.save(topic);
        TopicVO topicVO = TopicConvert.INSTANCE.do2Vo(topic);
        topicVO.setContent(
                contentRepository.findContentsByTopicId(topic.getId())
                        .stream().map(ContentConvert.INSTANCE::do2Vo).collect(Collectors.toList()));
        return topicVO;
    }

}
