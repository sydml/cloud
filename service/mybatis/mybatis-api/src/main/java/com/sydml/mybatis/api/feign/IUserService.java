package com.sydml.mybatis.api.feign;

import com.sydml.common.api.dto.UserDTO;
import com.sydml.mybatis.api.feign.impl.UserServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Liuym
 * @date 2019/5/17 0017
 */
@FeignClient(value = "authorization",fallback = UserServiceImpl.class)
public interface IUserService {
    @RequestMapping(value="/user/find-by-username", method = RequestMethod.GET)
    UserDTO findByUsername(@RequestParam("username") String username);


}
