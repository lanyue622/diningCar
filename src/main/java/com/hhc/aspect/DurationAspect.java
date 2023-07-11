package com.hhc.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.time.Duration;

/**
 * 记录导入时间
 */
@Component
@Aspect
public class DurationAspect {

    private static final Log logger = LogFactory.getLog(DurationAspect.class);

    @Around("execution(public void com.hhc.controller.DiningCarController.importExcel*(..))")
    public void importExcel(ProceedingJoinPoint joinPoint) {
        long startTime = System.nanoTime();
        logger.info("开始导入：" + joinPoint.getSignature().getName());
        try {
            joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            Duration time = Duration.ofNanos(System.nanoTime() - startTime);
            logger.info("导入结束，消耗了：" + time.getSeconds() + "s");
        }

    }
}
