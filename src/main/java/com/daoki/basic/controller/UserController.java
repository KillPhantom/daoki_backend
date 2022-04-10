package com.daoki.basic.controller;

import com.daoki.basic.VO.request.user.CreateUserVO;
import com.daoki.basic.VO.request.user.FindUserVO;
import com.daoki.basic.VO.request.user.UserAuthenticationVO;
import com.daoki.basic.VO.response.ResultVO;
import com.daoki.basic.VO.response.UserVO;
import com.daoki.basic.VO.response.user.AuthenticationResultVO;
import com.daoki.basic.anno.PassToken;
import com.daoki.basic.service.IUserService;
import com.daoki.basic.utils.ResultVoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "User Related API")
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @CrossOrigin
    @ApiOperation(value = "create user")
    @PostMapping("create-user")
    @PassToken
    public ResultVO createUser(@RequestBody CreateUserVO createUserVO) {
        UserVO user = userService.createUser(createUserVO);
        return ResultVoUtil.success(user, "create user successfully");
    }

    @PostMapping("get-user-info")
    public ResultVO getUserInfo(@RequestBody FindUserVO findUserVO) {
        UserVO user = userService.getUserInfo(findUserVO);
        return ResultVoUtil.success(user, "find user successfully");
    }

    @PostMapping("authentication")
    @PassToken
    public ResultVO userAuthentication(@RequestBody UserAuthenticationVO userAuthenticationVO) {
        AuthenticationResultVO authenticationResultVO = userService.userAuthentication(userAuthenticationVO);
        return ResultVoUtil.success(authenticationResultVO, "user authentication successfully");
    }
}
