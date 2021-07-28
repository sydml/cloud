package com.sydml.transaction.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @author liuyuming
 * @date 2021-07-27 17:01:42
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MailTest {
    private static final String text = "商品名称：D 商品价格：155 \n" +
            "订单编号：20210720174848547 \n" +
            "交易时间：2021-07-20 17:49:17\n" +
            "此订单一直未退款，请立即处理";
    @Resource
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String from;
    @Test
    public void sendMail() {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo("masgqv@163.com");
//        message.setTo("1817113127@qq.com");
            message.setSubject("订单异常未退款");
            message.setText(text);
        int count = 0;
        for (int i = 0; i < 2000; i++) {
            try {
                Thread.sleep(10);
                javaMailSender.send(message);
                count++;
            } catch (Exception e) {
                System.out.println("error" + e.getMessage());
                System.out.println("count =" + count);
            }
        }

    }
}