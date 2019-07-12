package com.kongling.bourse.data.kline.common.aspect;

import com.util.request.HttpRequestUtil;
import config.annotation.LogMenthodName;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author 张子艺
 * @packge com.controller.aspect
 * @data 2019-01-09 14:03
 * @project Currency
 */
@Aspect
@Component
public class WebLogAspect {


    /**
     * 注入请求request 上下文
     */
    @Autowired
    private HttpServletRequest request;

    private Logger logger = LoggerFactory.getLogger(WebLogAspect.class);
    /**
     * 计算时间
     */
    ThreadLocal<Long> startTime = new ThreadLocal<>();
    /**
     * 定义切入点，切入点为com.controller下的所有函数
     */
    @Pointcut("execution(public * com.kongling.bourse.data.kline.controller.*.*(..))")
    public void webLog() {
    }

    /**
     * 前置通知：在连接点之前执行的通知
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());
        // 接收到请求，记录请求内容
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Object params[] = joinPoint.getArgs();
        //自定义注解的方法操作别名
        LogMenthodName logMenthodName = method.getAnnotation(LogMenthodName.class);
        //判断方法是否存在这个注解
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 记录下请求内容
        logger.info("------------------------------------------------------------------------------------------------");
        logger.info("请求地址: " + request.getRequestURL().toString());
        logger.info("请求方式: " + request.getMethod());
        logger.info("请求时间: " + df.format(new Date()));
        logger.info("IP : " + HttpRequestUtil.getIpAddr(request));
        logger.info("方法地址 : " + signature.getDeclaringTypeName() + "." + signature.getName());
        if (logMenthodName == null) {
            logger.info("方法别名 : 未知");
        } else {
            logger.info("方法别名 : " + logMenthodName.name());
        }
        if (params.length == 0) {
            logger.info("请求参数 : 无参");
        } else {
            logger.info("请求参数 : " + Arrays.toString(params));
        }
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) throws Throwable {
        String time = (System.currentTimeMillis() - startTime.get()) + "ms";
        // 处理完请求，返回内容
        logger.info("返回内容: " + Arrays.asList(ret));
        logger.info("耗时 : " + time);
        startTime.remove();

    }
}
