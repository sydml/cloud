package com.sydml.mybatis.api.feign.impl;

import com.sydml.common.api.dto.UserDTO;
import com.sydml.mybatis.api.feign.IUserService;
import org.springframework.stereotype.Component;

/**
 * @author Liuym
 * @date 2019/5/17 0017
 */
@Component
public class UserServiceImpl implements IUserService {
    @Override
    public UserDTO findByUsername(String username) {
        return null;
    }
}
