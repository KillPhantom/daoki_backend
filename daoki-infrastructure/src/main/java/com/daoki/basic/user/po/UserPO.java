package com.daoki.basic.user.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户的数据库存储字段
 *
 * po表示数据库存储，do表示领域数据
 */
@Data
@Document("user_po")
public class UserPO {
    /**
     * primary key
     */
    @Id
    private Long id;

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
