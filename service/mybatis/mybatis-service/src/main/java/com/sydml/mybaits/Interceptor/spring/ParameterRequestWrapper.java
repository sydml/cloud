package com.sydml.mybaits.Interceptor.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Liuym
 * @date 2019/3/23 0023
 * request中的inputStream如果在这里获取，下面就获取不到了，这个包装类解决不了这个问题，不过我可以把处理后的流改写后再回填到request中
 */
public class ParameterRequestWrapper extends HttpServletRequestWrapper {
    private Map<String, String[]> params = new HashMap<>();

    public ParameterRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.params.putAll(request.getParameterMap());
    }

}
