package com.daoki.basic.user.convert;

import com.daoki.basic.user.model.UserDO;
import com.daoki.basic.user.po.UserPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserConvert {

    UserConvert instance = Mappers.getMapper(UserConvert.class);

    @Mappings({
            @Mapping(target = "userId", source = "u_id")
    })
    UserDO userPO2UserDO(UserPO userPO);
    @Mappings({
            @Mapping(target = "u_id", source = "userId")
    })
    UserPO userDO2UserPO(UserDO userDO);
}
