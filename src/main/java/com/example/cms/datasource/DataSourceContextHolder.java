package com.example.cms.datasource;

/**
 * 存储动态数据库变量类
 */
public class DataSourceContextHolder {
    private static final ThreadLocal<String> cotext = new ThreadLocal<>();

    public static String getDBKey(){
        return cotext.get();
    }

    public static void setDBKey(String dbKey){
        cotext.set(dbKey);
    }

    public static void clearDBKey(){
        cotext.remove();
    }

}
