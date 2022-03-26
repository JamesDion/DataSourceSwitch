package com.datasourceswitch.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.datasourceswitch.DataSourceEnum;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


/**
 * 多数据元配置类，配置N个数据源
 */
@Configuration
public class DataSourceConfig {

    /**
     * 注册主数据源
     *  @Primary:用于指定其中一个作为主数据源,
     *           即如果数据库操作没有指明使用哪个数据源的时候,
     *           默认使用主数据源,这个时候我们就使用到了@primary这个注解!
     * @return
     */
    @Primary
    @Bean(name = "master")
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource master() {
        return new DruidDataSource();
    }

    /**
     * 注册从数据源
     */
    @Bean(name = "follow")
    @ConfigurationProperties("spring.datasource.druid.follow")
    public DataSource follow() {
        return new DruidDataSource();
    }

    /**
     * 动态数据源进行数据源的切换
     *
     * @return
     */
    @Primary
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource(@Qualifier("master")DataSource master,@Qualifier("follow")DataSource follow) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 设置默认数据源
        dynamicDataSource.setDefaultTargetDataSource(master());
        // 配置多数据源
        Map<Object, Object> map = new HashMap<>();
        map.put(DataSourceEnum.MASTER.name(), master);
        map.put(DataSourceEnum.FOLLOW.name(), follow);
        // 将多数据源添加到数据源池中
        dynamicDataSource.setTargetDataSources(map);
        return dynamicDataSource;
    }

    /**
     * 通过动态数据源配置sqlsessionfactory
     *
     * @param dynamicDataSource
     * @return
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource);
        // 导入包
       // sqlSessionFactoryBean.setTypeAliasesPackage("com.plusEntity.*");
        // 获取mapper的xml文件位置,必须classpath*这个开头,源码中是以这个判断
        PathMatchingResourcePatternResolver path = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(path.getResources("classpath*:mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 通过动态数据源配置sqlsessionTemplate
     *
     * @param sqlSessionFactory
     * @return
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 配置事务管理器
     *
     * @param dynamicDataSource
     * @return
     */
    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }
}
