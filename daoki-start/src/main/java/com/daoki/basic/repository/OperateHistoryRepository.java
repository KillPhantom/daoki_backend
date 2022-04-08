package com.daoki.basic.repository;

import com.daoki.basic.entity.OperateHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/**
 * @author Alan
 * 2022-03-09
 * Description: the repository of operation history
 */
public interface OperateHistoryRepository extends MongoRepository<OperateHistory, String> {

    /**
     * find operation history recording according to topic id in database
     * @param topicId id in database of topic
     * @return list of operation history
     */
    List<OperateHistory> findByTopicId(String topicId);

    /**
     * delete operation history recording according to topic id in database
     * @param topicId topicId id in database of topic
     */
    void deleteByTopicId(String topicId);

}
