package com.sydml.transaction.controller;

import com.sydml.common.utils.StreamUtil;
import com.sydml.transaction.annotations.Permission;
import com.sydml.transaction.api.IUser2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Liuym
 * @date 2019/3/11 0011
 */
@RestController
@RequestMapping("/aspect-test")
public class AspectTestController {

    @Autowired
    private IUser2Service user2Service;

    @RequestMapping(value = "/say", method = RequestMethod.GET)
    @ResponseBody
    @Permission
    public void say(@RequestParam(value = "name") String name, @RequestParam(value = "content") String content) {
        long id = Thread.currentThread().getId();
        System.out.println("say:" + id);
        System.out.println(name + ":" + content);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void testRequestAndResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Thread.currentThread().getId();
        System.out.println("testRequestAndResponse:" + id);
        ServletInputStream inputStream = request.getInputStream();
        String string = StreamUtil.getString(inputStream);
        response.getWriter().write(string);
    }

    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    @CrossOrigin
    public void test1() {
        long id = Thread.currentThread().getId();
        user2Service.test1();
        System.out.println("test1:" + id);

    }

    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    @ResponseBody
    public void test2() {
        long id = Thread.currentThread().getId();
        System.out.println("test2:" + id);
    }

}
