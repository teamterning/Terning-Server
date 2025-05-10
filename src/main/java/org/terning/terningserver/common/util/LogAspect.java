package org.terning.terningserver.common.util;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
@RequiredArgsConstructor
public class LogAspect {

    Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Around("@annotation(org.terning.terningserver.common.util.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        //@LogExecutionTime이 붙어있는 타켓 메소드의 성능 측정
        Object proceed = joinPoint.proceed();

        stopWatch.stop();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        logger.info("실행한 메소드: " + signature.getMethod().getName());
        logger.info("수행 시간: " + stopWatch.getTotalTimeMillis() + "ms");

        return proceed;
    }
}
