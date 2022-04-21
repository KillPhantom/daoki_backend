package com.daoki.basic.VO.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * @author Alan
 * 2022-03-06
 * Utils: Intellij Idea
 * Description: this Object is used for updating the user-defined content
 */
@Data
@ApiModel(value = "a updated content module")
public class UpdateContentVO extends CreateContentVO {

    @ApiModelProperty(value = "the id in database of this content module. ",
            notes = "if the updated content module was already exist in the database, the content id is required, " +
            "while the content id should be null when creating a new content module")
    private String contentId;

    @ApiModelProperty(value = "the id in database of the topic to which this content module belongs")
    @NotNull(message = "the topic id of content mustn't be null")
    private String topicId;

}
