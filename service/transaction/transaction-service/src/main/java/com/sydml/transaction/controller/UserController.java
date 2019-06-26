package com.sydml.transaction.controller;

import com.sydml.common.api.dto.LoginInfo;
import com.sydml.common.api.dto.UserDTO;
import com.sydml.transaction.api.IUser1Service;
import com.sydml.transaction.api.IUser2Service;
import com.sydml.transaction.domain.User1;
import com.sydml.transaction.domain.User2;
import com.sydml.transaction.feign.IUserLoginService;
import com.sydml.transaction.feign.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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

    @Autowired
    private IUserService userService;

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

    @RequestMapping(value = "/find-by-username", method = RequestMethod.GET)
    public UserDTO findByUsername(@RequestParam("username") String username) {
        new Thread(()->{
            try {
                System.out.println("child thread sleep...");
                Thread.sleep(61000);
                System.out.println("child thread sleep over");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
      return userService.findByUsername("ming");
    }

    public static void main(String[] args){
      System.out.println("sdfa");
    }
}
