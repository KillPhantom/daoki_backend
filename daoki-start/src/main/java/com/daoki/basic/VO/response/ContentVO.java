package com.daoki.basic.VO.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * @author Alan
 * 2022-02-26
 * Utils: Intellij Idea
 * Description: the content view object
 */
@Data
@ApiModel(value = "content")
public class ContentVO {

    @ApiModelProperty(value = "the id of this content module")
    private String contentId;

    @ApiModelProperty(value = "the body of this content module")
    private String body;

    @ApiModelProperty(value = "the title of this content module")
    private String title;

    @ApiModelProperty(value = "the type of this content module")
    private Integer type;

    @ApiModelProperty(value = "the contributors of this content module")
    private List<String> contributors;

    @ApiModelProperty(value = "the last editing time of this content module")
    private Date lastEdit;

    @ApiModelProperty(value = "the position of this content module")
    private Integer position;

}
