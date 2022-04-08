package com.daoki.basic.repository;

import com.daoki.basic.entity.Content;
import net.bytebuddy.matcher.CollectionOneToOneMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.stream.Stream;

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
     * find contents according to topic id and content status in database
     * @param topicId id in database of topic
     * @param status content status
     * @return list of content entity objects
     */
    List<Content> findContentsByTopicIdAndStatus(String topicId, String status);

    /**
     * find contents according to content id and topic id in database
     * @param id id in database of content
     * @param topic id in database of topic
     * @return content entity object
     */
    Content findContentByIdAndTopicId(String id, String topic);

    /**
     * find contents according to content id, topic id and content status in database
     * @param id id in database of content
     * @param topic id in database of topic
     * @param status content status
     * @return content entity object
     */
    Content findContentByIdAndTopicIdAndStatus(String id, String topic,String status);

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