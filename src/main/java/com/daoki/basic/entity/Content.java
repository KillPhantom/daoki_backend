package com.daoki.basic.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Alan
 * 2022-02-26
 * Description: the entity class of content module
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Document("content_module")
public class Content extends BaseDO implements Serializable {

    /**
     * primary key
     */
    @Id
    private String id;

    /**
     * the id in database of the topic to which this content module belongs
     */
    @Indexed
    private String topicId;

    /**
     * the body of content module
     */
    private String body;

    /**
     * the title of content module
     */
    @Indexed
    private String title;

    /**
     * the type of content module
     */
    private int type;

    /**
     * the last editing time of content module
     */
    @Field("last_edit")
    private Date lastEdit;

    /**
     * the position of content module because many content modules contained in a topic
     */
    private int position;

    /**
     * language for the code data type
     */
    private String language;

    /**
     * the status of content module
     * 0 means not submitted
     * 1 means under-reviewed
     * 2 means released
     * -1 means failed to pass the review
     * -2 deleted
     */
    private String status;


}
