package com.daoki.basic.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDO {
    /**
     * primary key
     */
    private String userId;

    /**
     * 用户名字
     */
    private String userName;

    /**
     * 钱包hash
     */
    private String walletHash;

    /**
     * 噪声
     */
    private String nonce;


    public void genNonce() {
        this.nonce = "sss";
    }
}
