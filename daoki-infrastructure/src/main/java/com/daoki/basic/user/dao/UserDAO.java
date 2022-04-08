package com.daoki.basic.user.dao;

import com.daoki.basic.user.po.UserPO;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 使用jpa对数据模型userpo进行操作
 */
public interface UserDAO extends MongoRepository<UserPO,Long> {

    UserPO getUserPoById(Long id);
}
