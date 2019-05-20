package com.sydml.transaction.feign;

import com.sydml.common.api.dto.UserDTO;
import org.springframework.stereotype.Component;

/**
 * @author Liuym
 * @date 2019/5/20 0020
 */
@Component
public class UserServiceImpl implements IUserService {
    @Override
    public UserDTO findByUsername(String username) {
        return new UserDTO();
    }

}
