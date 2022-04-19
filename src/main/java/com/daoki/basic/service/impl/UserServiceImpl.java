package com.daoki.basic.service.impl;

import com.daoki.basic.VO.request.user.CreateUserVO;
import com.daoki.basic.VO.request.user.FindUserVO;
import com.daoki.basic.VO.request.user.UserAuthenticationVO;
import com.daoki.basic.VO.response.user.UserVO;
import com.daoki.basic.VO.response.user.AuthenticationResultVO;
import com.daoki.basic.entity.User;
import com.daoki.basic.enums.ErrorEnum;
import com.daoki.basic.exception.CustomException;
import com.daoki.basic.mapper.UserConvert;
import com.daoki.basic.repository.UserRepository;
import com.daoki.basic.service.IUserService;
import com.daoki.basic.utils.IdGenUtil;
import com.daoki.basic.utils.JwtUtils;
import com.daoki.basic.utils.Web3SignatureVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;


@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    IdGenUtil idGenUtil;

    @Autowired
    JwtUtils jwtUtils;

    public static final String PERSONAL_MESSAGE_PREFIX = "Ethereum Signed Message:";


    @Override
    public UserVO getUserById(Long userId) {
        User user = userRepository.findByUserId(userId);
        if (Objects.nonNull(user)) {
            return UserConvert.INSTANCE.Do2Vo(user);
        } else {
            throw new CustomException(ErrorEnum.USER_NOT_EXIST,"getUserById");
        }
    }

    @Override
    public UserVO createUser(CreateUserVO createUserVO) {

        User byWalletHash = userRepository.findByWalletPublicAddress(createUserVO.getPublicAddress());
        if (byWalletHash != null) {
            throw new CustomException(ErrorEnum.USER_ALREADY_EXIST, "createUser");
        }

        User user = UserConvert.INSTANCE.createVo2Do(createUserVO);
        user.setUserId(idGenUtil.nextId());
        Random noise = new Random();
        user.setNonce(String.valueOf(noise.nextInt()));
        return UserConvert.INSTANCE.Do2Vo(userRepository.save(user));
    }

    @Override
    public UserVO getUserByAddress(FindUserVO findUserVO){
        User user = userRepository.findByWalletPublicAddress(findUserVO.getPublicAddress());
        if (Objects.isNull(user)){
            throw new CustomException(ErrorEnum.USER_NOT_EXIST,"getByAddress");
        }
        return UserConvert.INSTANCE.Do2Vo(user);
    }

    @Override
    public AuthenticationResultVO userAuthentication(UserAuthenticationVO userAuthenticationVO) {

        AuthenticationResultVO resultVO = new AuthenticationResultVO();

        User user = userRepository.findByWalletPublicAddress(userAuthenticationVO.getPublicAddress());
        if (user == null) {
            resultVO.setStatus(Boolean.FALSE);
            resultVO.setMessage("user not found");
        }

        // debug 用，不去做校验。
        assert user != null;
        String token = jwtUtils.createToken(user.getUserId().toString(), user.getUserName());
//        if (!DebugSwitchs.doWeb3Check) {
//            // 更新random
//            Random u = new Random();
//            user.setNoise(String.valueOf(u.nextInt()));
//            userRepository.save(user);
//            // 认证成功，返回session
//            resultVO.setToken(token);
//            resultVO.setStatus(Boolean.TRUE);
//            resultVO.setMessage("authentication success.");
//            return resultVO;
//        }
        // noise
        boolean match = true;
        try{
            match = Web3SignatureVerification.verify(
                    userAuthenticationVO.getSignature(),
                    userAuthenticationVO.getPublicAddress(),
                    PERSONAL_MESSAGE_PREFIX + user.getNonce());
        }catch (Exception e){
            // TODO @Alan 加一下Api Exception handle 表示签名验证出问题了
            System.out.println(e.toString());
        }

        if (match) {
            // 更新random
            Random u = new Random();
            user.setNonce(String.valueOf(u.nextInt()));
            userRepository.save(user);
            // 认证成功，返回session
            resultVO.setToken(token);
            resultVO.setStatus(Boolean.TRUE);
            resultVO.setMessage("authentication success.");
        } else {
            throw new CustomException(ErrorEnum.AUTHENTICATION_FAILED,"userAuthentication");
        }

        return resultVO;
    }
}
