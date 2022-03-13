package com.daoki.basic.VO.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Alan
 * 2022-03-01
 * Utils: Intellij Idea
 * Description: this Object is used for updating the user-defined topic
 */
@Data
@ApiModel("update topic: All content modules must be contained in the updated topic whether it is updated or not, " +
        "or the content module didn't contained will be deleted in database")
public class UpdateTopicVO {

    @ApiModelProperty("the id in database of this topic")
    @NotNull(message = "the id mustn't be null")
    private String topicId;

    @ApiModelProperty("the name of topic")
    @NotNull(message = "the name mustn't be null")
    @Length(min = 1 , max = 30 , message = "the length of name is limited to 1~30")
    private String name;

    @ApiModelProperty("the content modules of topic")
    private List<UpdateContentVO> content;

    @ApiModelProperty("the operator of topic updating")
    @NotNull(message = "the operator mustn't be null")
    private String operator;

}
