package com.sydml.transaction.feign;

import com.sydml.common.api.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Liuym
 * @date 2019/5/20 0020
 */
@FeignClient(value = "mybatis", fallback = UserServiceImpl.class)
public interface IUserService {
    @RequestMapping(value ="/mybatis/find-by-username", method = RequestMethod.GET)
    UserDTO findByUsername(@RequestParam("username") String username);
}
