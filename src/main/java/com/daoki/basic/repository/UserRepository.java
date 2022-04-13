package com.daoki.basic.repository;

import com.daoki.basic.entity.CollaboratorInfo;
import com.daoki.basic.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {

    User findByWalletPublicAddress(String walletPublicAddress);

    User findByUserId(Long userId);
}
