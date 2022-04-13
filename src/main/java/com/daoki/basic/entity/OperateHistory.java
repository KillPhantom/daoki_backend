package com.daoki.basic.entity;

import com.daoki.basic.enums.TopicOperateEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Alan
 * 2022-03-04
 * Description: the entity class of topic operation history
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Document("operate_history")
public class OperateHistory extends BaseDO implements Serializable {

    /**
     * primary key
     */
    @Id
    private String id;

    /**
     * the id in database of topic to which this operation history recording belongs
     */
    @Indexed
    private String topicId;

    /**
     * the operation time
     */
    private Date time;

    /**
     * the user id of the operator
     */
    private Long operator;

    /**
     * the operation action
     */
    private TopicOperateEnum action;

}
