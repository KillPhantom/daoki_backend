package com.daoki.basic.service.impl;

import com.daoki.basic.VO.request.user.CreateUserVO;
import com.daoki.basic.VO.request.user.FindUserVO;
import com.daoki.basic.VO.request.user.UserAuthenticationVO;
import com.daoki.basic.VO.response.UserVO;
import com.daoki.basic.VO.response.user.AuthenticationResultVO;
import com.daoki.basic.entity.User;
import com.daoki.basic.enums.ErrorEnum;
import com.daoki.basic.exception.CustomException;
import com.daoki.basic.mapper.UserConvert;
import com.daoki.basic.repository.UserRepository;
import com.daoki.basic.service.IUserService;
import com.daoki.basic.utils.DebugSwitchs;
import com.daoki.basic.utils.IdGenUtil;
import com.daoki.basic.utils.JwtUtils;
import com.daoki.basic.utils.Web3SignatureVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Arrays;

import org.web3j.crypto.ECDSASignature;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.crypto.Sign.SignatureData;
import org.web3j.utils.Numeric;

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
        Optional<User> byId = userRepository.findById(userId);
        if (byId.isPresent()) {
            User user = byId.get();
            return UserConvert.INSTANCE.user2VO(user);
        } else {
            return null;
        }
    }

    @Override
    public UserVO createUser(CreateUserVO createUserVO) {

        User byWalletHash = userRepository.findByWalletHash(createUserVO.getPublicAddress());
        if (byWalletHash != null) {
            throw new CustomException(ErrorEnum.USER_ALREADY_EXIST, "createUser");
        }

        User user = new User();
        user.setUserId(idGenUtil.nextId());
        user.setWalletHash(createUserVO.getPublicAddress());
        Random u = new Random();
        user.setNonce(String.valueOf(u.nextInt()));
        User save = userRepository.save(user);
        return UserConvert.INSTANCE.user2VO(save);
    }

    @Override
    public UserVO getUserInfo(FindUserVO findUserVO) {
        User byWalletHash = userRepository.findByWalletHash(findUserVO.getPublicAddress());
        return UserConvert.INSTANCE.user2VO(byWalletHash);
    }


    @Override
    public AuthenticationResultVO userAuthentication(UserAuthenticationVO userAuthenticationVO) {
        AuthenticationResultVO resultVO = new AuthenticationResultVO();

        String address = userAuthenticationVO.getPublicAddress();

        FindUserVO findUserVO = new FindUserVO();
        findUserVO.setPublicAddress(address);
        UserVO userInfo = getUserInfo(findUserVO);
        if (userInfo == null) {
            resultVO.setStatus(Boolean.FALSE);
            resultVO.setMessage("user not found");
        }

        // debug 用，不去做校验。
        String token = jwtUtils.createToken(userInfo.getUserId().toString(), userInfo.getUserName());
//        if (!DebugSwitchs.doWeb3Check) {
//            // 更新random
//            Random u = new Random();
//            userInfo.setNonce(String.valueOf(u.nextInt()));
//            userRepository.save(UserConvert.INSTANCE.VO2User(userInfo));
//            // 认证成功，返回session
//            resultVO.setToken(token);
//            resultVO.setStatus(Boolean.TRUE);
//            resultVO.setMessage("authentication success.");
//            return resultVO;
//        }
        // nonce
        boolean match = false;
        try{
            match = Web3SignatureVerification.verify(userAuthenticationVO.getSignature(), address, PERSONAL_MESSAGE_PREFIX + userInfo.getNonce());
        }catch (Exception e){
            // TODO @Alan 加一下Api Exception handle 表示签名验证出问题了
            System.out.println(e.toString());
        }


        if (match) {
            // 更新random
            Random u = new Random();
            userInfo.setNonce(String.valueOf(u.nextInt()));
            userRepository.save(UserConvert.INSTANCE.VO2User(userInfo));
            // 认证成功，返回session
            resultVO.setToken(token);
            resultVO.setStatus(Boolean.TRUE);
            resultVO.setMessage("authentication success.");
        } else {
            resultVO.setStatus(Boolean.FALSE);
            resultVO.setMessage("authentication failed");
        }

        return resultVO;
    }
}
