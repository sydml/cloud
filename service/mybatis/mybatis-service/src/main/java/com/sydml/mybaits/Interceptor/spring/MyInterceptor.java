package com.sydml.mybaits.Interceptor.spring;

import com.sydml.common.utils.JsonUtil;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Liuym
 * @date 2019/3/23 0023
 */
@Component
public class MyInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从这里获取Myfilter 中设置在ParameterRequestWrapper 的另个一参数进行校验，根据校验结果填写response
        //todo 感觉完全不需要开发ParameterRequestWrapper 去处理这些验证步骤，直接在request配置token 验证token即可，不用绕来绕去
        //todo 作者之所以这么做是否要为了避免request 请求有敏感信息，需要查询验证，不暴露在外面，那直接给我token 我在此处查询也可以啊
        /*System.out.println("preHandle");
        String token = request.getHeader("token");
        if ("ming".equals(token)) {
            return true;
        }else{
            return writeFailResponse(response);
        }*/
        return true;
    }

    private boolean writeFailResponse(HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 500);
        map.put("message", "登录失效，请登录");
        map.put("data", null);
        String s = JsonUtil.encodeJson(map);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(s);
        return false;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView)
            throws Exception {
        System.out.println("postHandle");

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        System.out.println("afterCompletion");
    }
}
