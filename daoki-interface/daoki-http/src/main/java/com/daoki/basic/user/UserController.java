package com.daoki.basic.user;

import com.daoki.basic.user.model.UserDO;
import com.daoki.basic.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/user")
    public void createUser() {
        UserDO userDO = UserDO.builder()
                .userName("123456")
                .nonce("111")
                .build();

        userRepository.save(userDO);
    }
}
