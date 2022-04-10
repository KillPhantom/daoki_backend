package com.daoki.basic.service;

import com.daoki.basic.VO.request.user.CreateUserVO;
import com.daoki.basic.VO.request.user.FindUserVO;
import com.daoki.basic.VO.request.user.UserAuthenticationVO;
import com.daoki.basic.VO.response.UserVO;
import com.daoki.basic.VO.response.user.AuthenticationResultVO;

public interface IUserService {

    public UserVO getUserById(Long userId);

    public UserVO createUser(CreateUserVO createUserVO);

    public UserVO getUserInfo(FindUserVO findUserVO);

    public AuthenticationResultVO userAuthentication(UserAuthenticationVO userAuthenticationVO);
}
