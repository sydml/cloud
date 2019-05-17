package com.sydml.mybaits.Interceptor.spring;

import com.sydml.mybaits.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Liuym
 * @date 2019/3/23 0023
 */
@Component
@WebFilter("/*")
public class Myfilter implements Filter {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        //todo 根据request传入的验证key查库验证一些信息，例如登录，这里做前置拦截，并将查询信息回填到request中，便于下层统一处理，不再在每个接口都查询
        //todo 感觉完全不需要开发ParameterRequestWrapper 去处理这些验证步骤，直接在request配置token 验证token即可，不用绕来绕去
        String token = req.getHeader("token");
        ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper(req);
        /*if ("asdf".equals(token)) {
            User user = userMapper.queryAllPageDetail().get(0);
            Map<String, Object> otherParams = new HashMap<>();
            otherParams.put("test", "new Test params");
        }*/
        chain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {

    }
}
