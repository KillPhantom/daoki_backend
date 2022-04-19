package com.daoki.basic.VO.response.user;

import lombok.Data;

/**
 * @author alan
 */
@Data
public class UserVO {

    /**
     *
     */
    private Long userId;

    /**
     * 用户名字
     */
    private String userName;

    /**
     * 钱包hash
     */
    private String walletPublicAddress;

    /**
     * 噪声
     */
    private String nonce;

}
