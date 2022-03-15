package com.daoki.basic.repository;

import com.daoki.basic.entity.CollaboratorInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CollaboratorInfoRepository extends MongoRepository<CollaboratorInfo, String> {

    /**
     * find CollaboratorInfo by id in database
     * @param id id of CollaboratorInfo in database
     * @return CollaboratorInfo
     */
    CollaboratorInfo findCollaboratorInfoById(String id);

}
