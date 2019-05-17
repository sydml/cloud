package com.sydml.transaction.feign;

import com.sydml.common.api.dto.LoginInfo;
import com.sydml.common.api.dto.UserDTO;
import org.springframework.stereotype.Component;

/**
 * @author Liuym
 * @date 2019/5/12 0012
 */
@Component
public class UserLoginServiceImpl implements IUserLoginService {
    @Override
    public String login(LoginInfo loginInfo) {
        return "mybatis FeignClient find none server";
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
