package com.daoki.basic.user.repository;

import com.daoki.basic.id.IdGenerateService;
import com.daoki.basic.user.convert.UserConvert;
import com.daoki.basic.user.dao.UserDAO;
import com.daoki.basic.user.model.UserDO;
import com.daoki.basic.user.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    UserDAO userDAO;

    @Autowired
    IdGenerateService idGenerateService;

    @Override
    public UserDO getUser(Long userId) {
        UserPO userPOById = userDAO.getUserPoById(userId);
        return UserConvert.instance.userPO2UserDO(userPOById);
    }

    @Override
    public void save(UserDO userDO) {
        UserPO userPO = UserConvert.instance.userDO2UserPO(userDO);

        // insert
        if (userPO.getId() == null) {
            userPO.setId(idGenerateService.nextId());
            userDAO.save(userPO);
        }else {
            // update
            userDAO.save(userPO);
        }
    }
}
