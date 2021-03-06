package com.daoki.basic.VO.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Alan
 * 2022-03-09
 * Utils: Intellij Idea
 * Description: a element object of form of the topic fuzzy searching results
 */
@Data
@ApiModel(value = "element object of the fuzzy searching results' form")
public class FuzzySearchTopicFormVO {

    @ApiModelProperty(value = "the name of topic")
    private String title;

    @ApiModelProperty(value = "the link of topic")
    private String link;

    @ApiModelProperty(value = "the description of topic")
    private String description;

}
