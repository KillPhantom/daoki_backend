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
@ApiModel("content view object")
public class ContentVO {

    @ApiModelProperty("the id of this content module")
    private String contentId;

    @ApiModelProperty("the body of this content module")
    private String body;

    @ApiModelProperty("the title of this content module")
    private String title;

    @ApiModelProperty("the type of this content module")
    private Integer type;

    @ApiModelProperty("the contributors of this content module")
    private List<String> contributors;

    @ApiModelProperty("the last editing time of this content module")
    private Date lastEdit;

    @ApiModelProperty("the position of this content module")
    private Integer position;

}
