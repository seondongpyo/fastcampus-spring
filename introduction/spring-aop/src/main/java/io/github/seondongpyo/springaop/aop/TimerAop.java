package io.github.seondongpyo.springaop.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class TimerAop {

	@Pointcut("execution(* io.github.seondongpyo.springaop.controller..*.*(..))")
	private void pointcut() {}

	@Pointcut("@annotation(io.github.seondongpyo.springaop.annotation.Timer)")
	private void enableTimer() {}

	@Around("pointcut() && enableTimer()")
	public void around(ProceedingJoinPoint joinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		Object result = joinPoint.proceed(); // 실제 메서드 실행

		stopWatch.stop();

		System.out.println("total taken time : " + stopWatch.getTotalTimeSeconds());
	}

}
