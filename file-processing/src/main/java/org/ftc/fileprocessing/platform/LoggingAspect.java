package org.ftc.fileprocessing.platform;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("@annotation(LogBefore)")
    void logBeforePointcut() {}

    @Pointcut("@annotation(LogAfterThrowing)")
    void logAfterThrowingPointcut() {}

    @Before("logBeforePointcut()")
    void logBefore(JoinPoint joinPoint) {
        logger.info(
                """
                    Before:
                    method=%s,
                    args=%s
                """.formatted(
                        joinPoint.getSignature().toShortString(),
                        String.join(
                                ", ",
                                Arrays.stream(joinPoint.getArgs())
                                        .map(Object::toString)
                                        .toArray(String[]::new)
                        )
                )
        );
    }

    @AfterThrowing(
            pointcut = "logAfterThrowingPointcut()",
            throwing = "throwable"
    )
    void logAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        logger.info(
                """
                    AfterThrowing:
                    method=%s,
                    args=%s
                    exception=%s
                """.formatted(
                        joinPoint.getSignature().toShortString(),
                        joinPoint.getArgs(),
                        throwable.getMessage()
                )
        );
    }
}
