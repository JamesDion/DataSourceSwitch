package com.datasourceswitch.config;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.datasourceswitch.DataSourceEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

/**
 * 创建动态数据源获取类
 * extends  AbstractRoutingDataSource   根据用户定义的规则选择当前的数据，实现动态数据源的切换
 */
@Component
public class DynamicDataSource extends AbstractRoutingDataSource {

    private  final  static Logger logger = LoggerFactory.getLogger(DynamicDataSource. class);

    @Override
    protected Object determineCurrentLookupKey() {
        String DB = DataSourceContextHolder.getDB();
        if (StringUtils.isBlank(DB)) DB = DataSourceEnum.MASTER.name();
        logger.info("数据源为：" + DB);
        return DataSourceContextHolder.getDB();
    }

}
