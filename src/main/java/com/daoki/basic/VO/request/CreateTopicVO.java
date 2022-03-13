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
 * 2022-02-26
 * Utils: Intellij Idea
 * Description: this Object is used for creating the user-defined topic
 */
@Data
@ApiModel("create a new topic")
@EqualsAndHashCode(callSuper = false)
public class CreateTopicVO {

    @ApiModelProperty("the name of topic")
    @NotNull(message = "the name mustn't be null")
    @Length(min = 1 , max = 30 , message = "the length of name is limited to 1~30")
    private String name;

    @ApiModelProperty("the content modules of topic")
    private List<CreateContentVO> content;

    @ApiModelProperty("the creator of this topic")
    @NotNull(message = "the operator mustn't be null")
    private String operator;

}
