package com.datasourceswitch.config;

import com.datasourceswitch.DataSourceEnum;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据源切换注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface DataSourceSwitch {
    DataSourceEnum Value() default DataSourceEnum.MASTER;

}
