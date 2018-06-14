package com.amber.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
//确保初始化
@Component
public class LogAspect {
    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before(value = "execution(* com.amber.controller.*Controller.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Object arg : joinPoint.getArgs()) {
            stringBuffer.append("arg: " + arg.toString() + " | ");
        }
        logger.info("before method: " + "" + stringBuffer.toString());
    }

    @After(value = "execution(* com.amber.controller.*Controller.*(..))")
    public void afterMethod(JoinPoint joinPoint) {
        logger.info("after method: ");
    }
}
