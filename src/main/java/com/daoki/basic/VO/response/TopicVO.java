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
@ApiModel("topic view object")
public class TopicVO {

    @ApiModelProperty("the id of this topic")
    private String topicId;

    @ApiModelProperty("the name of this topic")
    private String name;

    @ApiModelProperty("the content modules of this topic")
    private List<ContentVO> content;

    @ApiModelProperty("the view count of this topic")
    private String viewCount;

}