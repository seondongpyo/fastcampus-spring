package io.github.seondongpyo.springaop.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ParameterAop {

	@Pointcut("execution(* io.github.seondongpyo.springaop.controller..*.*(..))")
	private void pointcut() {

	}

	@Before("pointcut()")
	public void before(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		System.out.println("method = " + method.getName());

		for (Object arg : args) {
			System.out.println("type = " + arg.getClass().getSimpleName());
			System.out.println("arg = " + arg);
		}
	}

	@AfterReturning(value = "pointcut()", returning = "object")
	public void afterReturning(JoinPoint joinPoint, Object object) {
		System.out.println("object = " + object);
	}

}
