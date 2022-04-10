package com.daoki.basic.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Document("t_user")
public class User extends BaseDO implements Serializable {

    @Id
    private Long userId;


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
}
