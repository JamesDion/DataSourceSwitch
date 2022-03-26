package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 扫描interface mapper
 */
@MapperScan("com.plusMapper")
/**
 * 配置多数据源需要关闭自动配置单数据源。
 * 手动配置数据源，
 * 因为要配置多数据源，上边有提到DataSourceAutoConfiguration.class默认会帮我们自动配置单数据源，
 * 所以，如果想在项目中使用多数据源就需要排除它，手动指定多数据源。
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
