package com.daoki.basic.VO.request.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserAuthenticationVO {

    /**
     * 用户的公钥
     */
    @ApiModelProperty(value = "the address of public")
    @NotNull(message = "the publicAddress mustn't be null")
    String publicAddress;

    /**
     * 用户链接钱包后的签名
     */
    @ApiModelProperty(value = "signature")
    @NotNull(message = "the signature mustn't be null")
    String signature;

}
