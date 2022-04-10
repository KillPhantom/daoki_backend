package com.daoki.basic.aop;

import com.daoki.basic.VO.response.UserVO;
import com.daoki.basic.anno.PassToken;
import com.daoki.basic.enums.ErrorEnum;
import com.daoki.basic.exception.CustomException;
import com.daoki.basic.utils.JwtUtils;
import com.daoki.basic.utils.ResultVoUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 登陆的切面
 */
@Component
@Aspect
@Slf4j
public class LoginAspect {


    @Autowired
    JwtUtils jwtUtils;

    //定义切面，所有的controller层都会监控
    @Pointcut("execution(* com.daoki.basic.controller..*.*(..))")
    public void doHander() {

    }

    @Around("doHander()")
    public Object exception(ProceedingJoinPoint joinPoint) throws Throwable {

        //进入controller层前
        beforePoint(joinPoint);
        //放行
        return joinPoint.proceed();
    }

    private Boolean beforePoint(ProceedingJoinPoint joinPoint) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        // 从 http 请求头中取出 token
        String token = request.getHeader("token");

        //得到要进入的是哪个controller方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        //检查是否有passtoken注释，有则跳过认证，所以在controller层加了@Passtoken注解，这里我就不校验
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }


        // 执行认证
        if (token == null) {
            throw new CustomException(ErrorEnum.AUTHENTICATION_FAILED,"鉴权失败");
        }
        // 获取 token 中的 user id
        String userId;
        UserVO verify = jwtUtils.verify(token);
        if (verify == null) {
            throw new CustomException(ErrorEnum.AUTHENTICATION_FAILED,"鉴权失败");
        }

        request.setAttribute("user", verify);
        return true;
    }


}
