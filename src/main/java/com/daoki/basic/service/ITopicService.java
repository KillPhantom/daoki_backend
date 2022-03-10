package com.daoki.basic.service;

import com.daoki.basic.VO.request.CreateTopicVO;
import com.daoki.basic.VO.request.UpdateTopicVO;
import com.daoki.basic.VO.response.FuzzySearchTopicFormVO;
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
    void createTopic(CreateTopicVO createTopicVO);

    /**
     * update topic
     * @param updateTopicVO a topic when updating a topic
     */
    void updateTopic(UpdateTopicVO updateTopicVO);

    /**
     * delete topic according to topic id
     * @param topicId id in database of topic
     */
    void deleteTopic(String topicId);

    /**
     * search topics fuzzily according content title
     * @param name content title
     * @return list of fuzzy searching topic vo
     */
    List<FuzzySearchTopicFormVO> getTopicByName(String name);

    /**
     * find topic according by topic id
     * @param id id in database of topic
     * @return topic vo
     */
    TopicVO getTopicById(String id);

}
