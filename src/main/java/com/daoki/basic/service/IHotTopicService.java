package com.daoki.basic.service;

import com.daoki.basic.VO.response.HotTopicFormVO;
import com.daoki.basic.VO.response.TopicVO;

import java.util.List;

/**
 * @author Alan
 * 2022-03-08
 * Description: the interface of hot topics related service layer
 */
public interface IHotTopicService {

    /**
     * get the form of hot topics
     * @return list of hot topics
     */
    List<HotTopicFormVO> getHotTopics();

    /**
     * get the hot topic by topic id
     * @param topicId id of topic in database
     * @return topic vo
     */
    TopicVO getHotTopic(String topicId);

}
