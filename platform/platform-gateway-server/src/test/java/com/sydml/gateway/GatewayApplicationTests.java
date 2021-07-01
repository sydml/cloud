package com.sydml.gateway;

import com.sydml.common.utils.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
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
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        for (int i = 0; i < 1000; i++) {
            try {
                ResponseEntity<String> forEntity = restTemplate.exchange("http://localhost:9000/api-authorization/redis/test", HttpMethod.GET, entity, String.class);
                System.out.println("限流返回结果：" + JsonUtil.encodeJson(forEntity));
            } catch (Exception e) {
                if (e instanceof HttpClientErrorException.TooManyRequests) {
                    System.out.println("错误信息："+e.getMessage());
                }

            }

        }

    }

}
