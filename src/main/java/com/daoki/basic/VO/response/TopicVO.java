package com.daoki.basic.VO.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

/**
 * @author Alan
 * 2022-02-26
 * Utils: Intellij Idea
 * Description: the topic view object
 */
@Data
@ApiModel(value = "topic")
public class TopicVO {

    @ApiModelProperty(value = "the id of this topic")
    private String topicId;

    @ApiModelProperty(value = "the name of this topic")
    private String name;

    @ApiModelProperty(value = "the content modules of this topic")
    private List<ContentVO> content;

    @ApiModelProperty(value = "the view count of this topic")
    private String viewCount;

}