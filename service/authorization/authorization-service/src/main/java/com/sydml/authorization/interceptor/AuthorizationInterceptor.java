package com.sydml.authorization.interceptor;

import com.sydml.common.utils.JsonUtil;
import com.sydml.common.api.dto.UserDTO;
import com.sydml.authorization.service.IUserService;
import com.sydml.authorization.service.UserService;
import com.sydml.common.utils.StringUtil;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author Liuym
 * @date 2019/3/25 0025
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    private static final String TOKEN = "token";

    public AuthorizationInterceptor() {
        super();
    }

    /**
     * 已经确认
     * 启动顺序问题导致无法注入服务
     */
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /*String username = request.getHeader(TOKEN);
        if (redisTemplate == null) {
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            redisTemplate = (RedisTemplate) factory.getBean("redisTemplate");
            userService = factory.getBean(UserService.class);
        }

        Boolean result = false;
        if (StringUtil.isEmpty(username)) {
            result = false;
        }
        Object o = redisTemplate.opsForValue().get(username);

        // todo 可以增加注解的方式，根据注解拦截需要授权的接口
        // todo 可以根据是否授权在这里直接写入返回结果到response中
        if (o != null) {
            UserDTO userDTO = JsonUtil.decodeJson(o.toString(), UserDTO.class);
            UserDTO user = userService.findByUsername(userDTO.getUsername());
            result = user != null ? true : false;
        }
        if (!result) {
            PrintWriter writer = response.getWriter();
            writer.write("login.error");
            response.setStatus(403);
        }*/
        return true;
    }
}
