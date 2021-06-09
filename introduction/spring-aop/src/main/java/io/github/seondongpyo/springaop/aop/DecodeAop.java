package io.github.seondongpyo.springaop.aop;

import io.github.seondongpyo.springaop.dto.UserDto;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@Aspect
public class DecodeAop {

    @Pointcut("execution(* io.github.seondongpyo.springaop.controller..*.*(..))")
    private void pointcut() {}

    @Pointcut("@annotation(io.github.seondongpyo.springaop.annotation.Decode)")
    private void enableDecode() {}

    @Before("pointcut() && enableDecode()")
    public void before(JoinPoint joinPoint) throws UnsupportedEncodingException {
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            if (arg instanceof UserDto) {
                UserDto userDto = (UserDto) arg;
                String base64Email = userDto.getEmail();
                userDto.setEmail(new String(Base64.getDecoder().decode(base64Email), StandardCharsets.UTF_8));
            }
        }
    }

    @AfterReturning(value = "pointcut() && enableDecode()", returning = "returnObj")
    public void afterReturn(JoinPoint joinPoint, Object returnObj) {
        if (returnObj instanceof UserDto) {
            UserDto userDto = (UserDto) returnObj;
            String email = userDto.getEmail();
            userDto.setEmail(Base64.getEncoder().encodeToString(email.getBytes()));
        }
    }

}
