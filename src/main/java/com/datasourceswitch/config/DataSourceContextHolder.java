package com.datasourceswitch.config;


import com.datasourceswitch.DataSourceEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 创建数据源管理切换类
 */
public class DataSourceContextHolder {

    private  final  static Logger logger = LoggerFactory.getLogger(DataSourceContextHolder. class);


    /**
     * 设置默认数据源
     */
    public static String DEFAULT_DS = DataSourceEnum.MASTER.name();

    /**
     * 线程内部存储类
     */
    private static ThreadLocal<String> context = new ThreadLocal<String>();

    /**
     * 获取据源
     *
     * @return
     */
    public static void setDB(String dbType) {
        logger.info("切换的数据源为：" + dbType);
        context.set(dbType);
    }

    /**
     * 获取据源
     *
     * @return
     */
    public static String getDB() {
        return context.get();
    }

    /**
     * 删除数据源
     */
    public static void removeDB() {
        context.remove();
    }
}
