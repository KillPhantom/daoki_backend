package com.daoki.basic.VO.response.user;

import lombok.Data;

@Data
public class AuthenticationResultVO {

    /**
     * 认证状态
     */
    public Boolean status;

    /**
     * 认证结果描述
     */
    public String message;


    public String token;
}
