package com.sydml.gateway;

import com.sydml.common.utils.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GatewayApplicationTests {
    @Test
    public void contextLoads() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("token", "test");
        headers.add("LIMIT_KEY", "redisRequestLimtKey");
        for (int i = 0; i < 1000; i++) {
            ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:9000/api-authorization/redis/test", String.class);
            System.out.println("限流返回结果："+JsonUtil.encodeJson(forEntity));
        }

    }

}
