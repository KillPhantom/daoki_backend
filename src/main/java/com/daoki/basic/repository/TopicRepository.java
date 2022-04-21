package com.daoki.basic.repository;

import com.daoki.basic.entity.Topic;
import net.sf.jsqlparser.statement.select.Top;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * @author Alan
 * 2022-02-27
 * Description: the repository of topic
 */
public interface TopicRepository extends MongoRepository<Topic, String> {

    /**
     * 通过topic实体类的id查找topic
     * @param id topic实体类的id属性
     * @return 返回topic
     */
    Topic findTopicById(String id);

    /**
     * 通过topic实体类的id查找topic
     * @param id topic实体类的id属性
     * @param status topic status
     * @return 返回topic
     */
    Topic findTopicByIdAndStatus(String id, String status);

    /**
     * 通过topic的name查找topic
     * @param name topic实体类的name属性
     * @return 返回topic
     */
    Topic findTopicByName(String name);

    /**
     * 通过topic的name模糊查找topic
     * @param pageable
     * @param status topic的status
     * @param name topic的name
     * @return 返回topic
     */
    Page<Topic> findTopicsByStatusAndNameLike(Pageable pageable, String status, String name);

    /**
     * 通过topic实体类的id删除topic
     * @param id topic id in database
     */
    void deleteTopicById(String id);

    /**
     * 通过topic实体类的id删除topic
     * @param pageable
     * @param status topic的status
     * @return topic的page
     */
    Page<Topic> findTopicsByStatus(Pageable pageable, String status);

    Page<Topic> findTopicsByContributorAndStatus(Pageable pageable, long userId, String status);

}
