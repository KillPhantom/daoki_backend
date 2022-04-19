package com.daoki.basic.mapper;

import com.daoki.basic.VO.request.user.CreateUserVO;
import com.daoki.basic.VO.response.user.UserVO;
import com.daoki.basic.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserConvert {
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserVO Do2Vo(User user);

    @Mappings({
            @Mapping(target = "userId", ignore = true),
            @Mapping(target = "walletPublicAddress", source = "publicAddress"),
            @Mapping(target = "nonce", ignore = true)
    })
    User createVo2Do(CreateUserVO createUserVO);

}
