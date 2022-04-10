package com.daoki.basic.VO.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "create a new user")
@EqualsAndHashCode(callSuper = false)
public class CreateUserVO {

    @ApiModelProperty(value = "the address of public")
    @NotNull(message = "the publicAddress mustn't be null")
    private String publicAddress;
}
