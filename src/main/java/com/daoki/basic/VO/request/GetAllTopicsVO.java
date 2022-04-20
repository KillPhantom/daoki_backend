package com.daoki.basic.VO.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "a object with user id and page information for obtaining all topics of the current user")
@EqualsAndHashCode(callSuper = false)
public class GetAllTopicsVO {

    @ApiModelProperty(value = "page number")
    @NotNull(message = "the page number mustn't be null")
    private Integer page;

    @ApiModelProperty(value = "page size")
    @NotNull(message = "the page size mustn't be null")
    private Integer size;

}
