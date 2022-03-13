package com.daoki.basic.repository;

import com.daoki.basic.entity.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/**
 * @author Alan
 * 2022-02-27
 * Description: the repository of content module
 */
public interface ContentRepository extends MongoRepository<Content, String> {

    /**
     * find content according to content id in database
     * @param id id in database of content
     * @return content entity object
     */
    Content findContentById(String id);

    /**
     * find contents according to topic id in database
     * @param topicId id in database of topic
     * @return list of content entity objects
     */
    List<Content> findContentsByTopicId(String topicId);

    /**
     * fuzzy searching for contents according to content title
     * @param pageable the page information
     * @param title title of content
     * @return page of content entity objects
     */
    Page<Content> findContentsByTitleLike(Pageable pageable, String title);

    /**
     * delete content according content id
     * @param id id in database of content
     */
    void deleteContentById(String id);

}