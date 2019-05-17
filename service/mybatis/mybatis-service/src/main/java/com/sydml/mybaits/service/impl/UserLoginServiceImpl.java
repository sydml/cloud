package com.sydml.mybaits.service.impl;

import com.sydml.common.api.dto.LoginInfo;
import com.sydml.common.api.dto.UserDTO;
import com.sydml.mybaits.service.IUserLoginService;
import org.springframework.stereotype.Service;

/**
 * @author Liuym
 * @date 2019/5/12 0012
 */
@Service
public class UserLoginServiceImpl implements IUserLoginService {
    @Override
    public void login(LoginInfo loginInfo) {

    }

    @Override
    public UserDTO findById(Long id) {
        throw new RuntimeException("mybatis FeignClient find none server");
    }

    @Override
    public UserDTO findByUsername(String username) {
        throw new RuntimeException("mybatis FeignClient find none server");
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        throw new RuntimeException("mybatis FeignClient find none server");
    }
}
