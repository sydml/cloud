package com.sydml.authorization.service;

import com.sydml.common.api.dto.UserDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @author Liuym
 * @date 2019/3/25 0025
 */
@Path("user")
public interface IUserService {

    @GET
    UserDTO findByUsername(@RequestParam(value = "username") String username);

    UserDTO save(UserDTO userDTO);

    UserDTO findById(@RequestParam(value = "id") Long id);
}
