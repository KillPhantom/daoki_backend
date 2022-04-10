package com.daoki.basic.mapper;

import com.daoki.basic.VO.response.UserVO;
import com.daoki.basic.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserConvert {
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);


    UserVO user2VO(User user);

}
