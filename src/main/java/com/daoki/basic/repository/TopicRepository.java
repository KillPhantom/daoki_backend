package com.daoki.basic.repository;

import com.daoki.basic.entity.Topic;
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
 */
public interface TopicRepository extends MongoRepository<Topic, String> {

    /**
     * 通过topic实体类的id查找topic
     * @param id topic实体类的id属性
     * @return 返回topic
     */
    Topic findTopicById(String id);

    /**
     * 通过topic的name查找topic
     * @param name topic实体类的name属性
     * @return 返回topic
     */
    Topic findTopicByName(String name);

    /**
     * 通过topic的name模糊查找topic
     * @param pageable
     * @param name topic的name
     * @return 返回topic
     */
    Page<Topic> findTopicsByNameLike(Pageable pageable, String name);

    /**
     * 通过topic实体类的id删除topic
     * @param id
     */
    void deleteTopicById(String id);

}
