package com.daoki.basic.user.repository;

import com.daoki.basic.user.model.UserDO;

public interface UserRepository {

    UserDO getUser(Long userId);

    void save(UserDO userDO);
}
