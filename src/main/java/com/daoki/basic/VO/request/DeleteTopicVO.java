package com.daoki.basic.VO.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Alan
 * 2022-03-11
 * Utils: Intellij Idea
 * Description: this Object is used for deleting a topic
 */
@Data
@ApiModel("delete a topic")
@EqualsAndHashCode(callSuper = false)
public class DeleteTopicVO {

    @ApiModelProperty("the id of topic")
    @NotNull(message = "the topic id in database mustn't be null")
    private String topicId;

    @ApiModelProperty("the operator of deleting this topic")
    @NotNull(message = "the operator mustn't be null")
    private String operator;


}
