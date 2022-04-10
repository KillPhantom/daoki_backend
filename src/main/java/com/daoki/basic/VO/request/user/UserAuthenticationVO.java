package com.daoki.basic.VO.request.user;

import lombok.Data;

@Data
public class UserAuthenticationVO {

    /**
     * 用户的公钥
     */
    String publicAddress;

    /**
     * 用户链接钱包后的签名
     */
    String signature;

}
