package com.daoki.basic.entity;

import com.daoki.basic.VO.response.TopicVO;
import com.daoki.basic.mapper.TopicConvert;
import com.daoki.basic.repository.ContentRepository;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;
import java.util.List;

/**
 * @author Alan
 * 2022-02-26
 * Description: the entity class of topic
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Document("topic")
public class Topic extends BaseDO implements Serializable {

    /**
     * primary key
     */
    @Id
    private String id;

    /**
     * the name of topic
     */
    @Indexed
    private String name;

    /**
     * the status of topic
     * 0 means not submitted
     * 1 means under-reviewed
     * 2 means released
     * -1 means failed to pass the review
     * -2 deleted
     */
    private String status;

    /**
     * the number of views of the topic
     */
    @Field("view_count")
    @Indexed
    private int viewCount;

    /**
     * the contributor (user id) of topic
     */
    private long contributor;

    /**
     * the ids of the topics quoted by this topic
     */
    private List<String> quoteTopics;

    /**
     * quoted index
     */
    private String quoteIndex;
}
