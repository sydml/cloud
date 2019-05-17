package com.sydml.authorization.controller;

import com.sydml.common.utils.JsonUtil;
import com.sydml.common.api.dto.LoginInfo;
import com.sydml.common.api.dto.UserDTO;
import com.sydml.authorization.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author Liuym
 * @date 2019/3/25 0025
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IUserService userService;

    @PostMapping("login")
    public void login(@RequestBody(required = true) LoginInfo info) {
        UserDTO userDTO = userService.findByUsername(info.getUsername());
        redisTemplate.opsForValue().set(userDTO.getUsername(), JsonUtil.encodeJson(userDTO), 60 * 60, TimeUnit.SECONDS);

    }

    @PostMapping("save")
    public UserDTO save(@RequestBody(required = true) UserDTO userDTO) {
        UserDTO user = userService.save(userDTO);
        redisTemplate.opsForValue().set(user.getUsername(), JsonUtil.encodeJson(user), 60 * 60, TimeUnit.SECONDS);
        return user;
    }

    @GetMapping("find-by-id")
    public UserDTO findById(@RequestParam(value = "id") Long id) {
        return userService.findById(id);
    }

    @GetMapping("find-by-username")
    public UserDTO findByUsername(@RequestParam(value = "username") String username) {
        return userService.findByUsername(username);
    }
}
