package com.sydml.transaction.controller;

import com.sydml.common.api.dto.LoginInfo;
import com.sydml.transaction.api.IUser1Service;
import com.sydml.transaction.api.IUser2Service;
import com.sydml.transaction.domain.User1;
import com.sydml.transaction.domain.User2;
import com.sydml.transaction.feign.IUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Liuym
 * @date 2019/3/13 0013
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUser1Service user1Service;

    @Autowired
    private IUser2Service user2Service;

    @Autowired
    private IUserLoginService userLoginService;

    @RequestMapping("/trx")
    @ResponseBody
    public void saveUserTrx() {
//        User1 user1 = new User1();
//        user1.setName("aa");
        User2 user2 = new User2();
        user2.setName("trx");
        user2Service.saveUserWithTrx(user2);

    }

    @RequestMapping("/non-trx")
    @ResponseBody
    public void saveUserNonTrx() {
//        User1 user1 = new User1();
//        user1.setName("aa");
        User2 user2 = new User2();
        user2.setName("non-trx");
        user2Service.saveUserWithoutTrx(user2);
    }

    @RequestMapping("/save")
    @ResponseBody
    public void saveUser() {
//        User1 user1 = new User1();
//        user1.setName("aa");
        User2 user2 = new User2();
        user2.setName("new");
        user2Service.save(user2);
    }

    @RequestMapping("/test")
    @ResponseBody
    public void testRequest() {
        User1 user1 = new User1();
        user1.setName("testRequest");
        user2Service.testBaseTypeRequest("a", 1, true, 2L, user1);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestBody(required = true) LoginInfo loginInfo) {
        return userLoginService.login(loginInfo);
    }
}
