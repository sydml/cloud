package com.sydml.transaction.feign;

import com.sydml.common.api.dto.LoginInfo;
import com.sydml.common.api.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Liuym
 * @date 2019/5/12 0012
 */
@FeignClient(value = "authorization", fallback = UserLoginServiceImpl.class)
public interface IUserLoginService {
    @PostMapping(value = "user/login")
    String login(LoginInfo loginInfo);

    @RequestMapping(value = "user/find-by-id", method = RequestMethod.GET)
    UserDTO findById(@RequestParam("id") Long id);

    @RequestMapping(value = "user/find-by-username", method = RequestMethod.GET)
    UserDTO findByUsername(@RequestParam("username") String username);

    @RequestMapping(value = "user/save", method = RequestMethod.POST)
    UserDTO save(UserDTO userDTO);

}
