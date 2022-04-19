package com.daoki.basic.VO.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "topic preview object in user's page")
public class TopicPreviewVO {

    @ApiModelProperty(value = "the name of this topic")
    private String title;

    @ApiModelProperty(value = "the view count of this topic")
    private String viewCount;

    @ApiModelProperty(value = "the quoted count")
    private String quoteIndex;

    @ApiModelProperty(value = "the id of this topic")
    private String id;

}
