package com.daoki.basic.VO.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Alan
 * 2022-03-06
 * Utils: Intellij Idea
 * Description: this Object is used for updating the user-defined content
 */
@Data
@ApiModel(value = "a updated content module")
public class UpdateContentVO {

    @ApiModelProperty(value = "the id in database of this content module. ",
            notes = "if the updated content module was already exist in the database, the content id is required, " +
            "while the content id should be null when creating a new content module")
    private String contentId;

    @ApiModelProperty(value = "the id in database of the topic to which this content module belongs")
    @NotNull(message = "the topic id mustn't be null")
    private String topicId;

    @ApiModelProperty(value = "the body of a content module")
    @Length(min = 1, max = 1000, message = "the length of body is limited to 1~1000")
    private String body;

    @ApiModelProperty(value = "the title of content module")
    @NotNull(message = "the title mustn't be null")
    @Length(min = 1 , max = 30 , message = "the length of title is limited to 1~30")
    private String title;

    @ApiModelProperty(value = "the type of content module")
    @NotNull(message = "the type mustn't be null")
    private Integer type;

    @ApiModelProperty(value = "the contributor of content module")
    @NotNull(message = "the contributor mustn't be null")
    @Size(max = 10, message = " The maximum contributor number limit is 10")
    private List<String> contributors;

    @ApiModelProperty(value = "the position of this content module")
    @NotNull(message = "the position information mustn't be null")
    private Integer position;

}
