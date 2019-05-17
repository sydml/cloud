package com.sydml.transaction.annotations;

import com.sydml.common.utils.JsonUtil;
import com.sydml.common.utils.ReflectionUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author Liuym
 * @date 2019/3/11 0011
 */
@Aspect
@Component
public class PermissionAspect {

    @Pointcut("@annotation(com.sydml.transaction.annotations.Permission)")
    public void controllerAspect() {

    }

    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String name = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            System.out.println(parameter);
        }
        Class<?>[] parameterTypes = method.getParameterTypes();
        int i = 0;
        for (Class<?> parameterType : parameterTypes) {
            // 遇到接口会失败
            Object o = ReflectionUtil.newInstance(parameterType);
            o = args[i];
            i++;
            System.out.printf(parameterType.getName());
        }

    }

    @Around("controllerAspect()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            System.out.println(arg);
        }
        System.out.println("before");
        Object proceed = pjp.proceed();
        System.out.println(JsonUtil.encodeJson(proceed));
        System.out.println("after");
        return proceed;
    }
}
