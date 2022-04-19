package com.daoki.basic.service;

import com.daoki.basic.VO.request.*;
import com.daoki.basic.VO.response.FuzzySearchTopicFormVO;
import com.daoki.basic.VO.response.PageVO;
import com.daoki.basic.VO.response.TopicPreviewVO;
import com.daoki.basic.VO.response.TopicVO;

import java.util.List;

/**
 * @author Alan
 * 2022-02-28
 * Description: the interface of topic related service layer
 */
public interface ITopicService {

    /**
     * create topic
     * @param createTopicVO a topic when creating a new topic
     */
    String createTopic(CreateTopicVO createTopicVO);

    /**
     * update topic
     * @param updateTopicVO a topic when updating a topic
     */
    void updateTopic(UpdateTopicVO updateTopicVO);

    /**
     * delete topic according to topic id
     * @param topicId topic id
     */
    void deleteTopic(String topicId);

    /**
     * search topics fuzzily according content title
     * @param fuzzySearchTopicVO key word used for searching fuzzily
     * @return page of fuzzy searching topic vo
     */
    PageVO<FuzzySearchTopicFormVO> getTopicByName(FuzzySearchTopicVO fuzzySearchTopicVO);

    /**
     * find topic according topic id
     * @param id id in database of topic
     * @return topic vo
     */
    TopicVO getTopicById(String id);

    /**
     * find topic according user id
     * @param getAllTopicsVO user id
     * @return topic vo
     */
    PageVO<TopicPreviewVO> getAllTopics(GetAllTopicsVO getAllTopicsVO);

}
