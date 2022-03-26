package com.datasourceswitch.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 动态数据源切面,必须最优先切入
 * @Order(0)：优先级，数字越小，越优先
 */
@Order(0)
@Aspect
@Component
public class DynamicDataSourceAspect {

    private  final  static Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect. class);

    /**
     * 访问方法之前
     *
     * @param joinPoint
     * @param dataSourceSwitch
     */
    @Before("@annotation(dataSourceSwitch))")
    public void beforeSwitchDS(JoinPoint joinPoint, DataSourceSwitch dataSourceSwitch) {
        logger.info("当前进入切面。。。。");
        // 获得当前访问的class
        Class<?> aClass = joinPoint.getTarget().getClass();
        // 获取访问的方法名
        String name = joinPoint.getSignature().getName();
        // 得到方法的参数的类型
        Class[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        // 获取默认数据源
        String dataSource = DataSourceContextHolder.DEFAULT_DS;
        try {
            // 得到访问的方法对象
            Method method = aClass.getMethod(name, parameterTypes);
            // 判断是否存在@DataSourceSwitch注解
            if (method.isAnnotationPresent(DataSourceSwitch.class)) {
                DataSourceSwitch annotation = method.getAnnotation(DataSourceSwitch.class);
                // 取出注解中的数据源名
                dataSource = annotation.Value().name();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 切换数据源
            DataSourceContextHolder.setDB(dataSource);
        }
    }

    /**
     * 访问接口之后
     *
     * @param joinPoint
     */
    @After("@annotation(com.datasourceswitch.config.DataSourceSwitch)")
    public void afterSwitchDS(JoinPoint joinPoint) {
        // 清除数据源
        DataSourceContextHolder.removeDB();
    }
}
