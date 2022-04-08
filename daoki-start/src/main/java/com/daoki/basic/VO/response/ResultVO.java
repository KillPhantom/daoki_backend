package com.daoki.basic.VO.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Data;

/**
 * @author Alan
 * Date: Created in 2018-12-17 11:05
 * Utils: Intellij Idea
 * Description: 固定返回格式
 */
@Data
@ApiModel(value = "response with certain format")
public class ResultVO {

    @ApiModelProperty(value = "status code")
    private String code;

    @ApiModelProperty(value = "message")
    private String message;

    @ApiModelProperty(value = "response data")
    private Object data;

}
