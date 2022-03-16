package com.daoki.basic.VO.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Alan
 * 2022-03-06
 * Utils: Intellij Idea
 * Description: a element object of form of the hot topics
 */
@Data
@ApiModel(value = "element object of the hot topics' form")
public class HotTopicFormVO {

    @ApiModelProperty(value = "the title of hot topic")
    private String title;

    @ApiModelProperty(value = "the link of hot topic")
    private String link;

    @ApiModelProperty(value = "the description of hot topic")
    private String description;

}
