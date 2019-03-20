package com.example.cms.datasource;


import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class DynamicDataSource extends AbstractRoutingDataSource {
    private static DynamicDataSource instance = null;
    private static final byte[] lock = new byte[0];
    private static final HashMap<Object,Object> datasourceMap = new HashMap<>(16);

    private DynamicDataSource(){}

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        datasourceMap.putAll(targetDataSources);
        super.afterPropertiesSet();// 必须添加该句，否则新添加数据源无法识别到
    }

    @Override
    protected String determineCurrentLookupKey() {
        String dbKey = DataSourceContextHolder.getDBKey();
        if (StringUtils.isEmpty(dbKey)) throw new IllegalArgumentException("dbkey is empty");
        return dbKey;
    }

    public Map getDatasourceMap(){
        return datasourceMap;
    }

    public static DynamicDataSource getInstance(){
        if (instance ==null){
            synchronized (lock){
                if (instance ==null){
                    instance = new DynamicDataSource();
                    return instance;
                }
            }
        }
        return instance;
    }
}
