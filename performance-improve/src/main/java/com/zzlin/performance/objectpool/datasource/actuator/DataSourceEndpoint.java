package com.zzlin.performance.objectpool.datasource.actuator;

import com.zzlin.performance.objectpool.datasource.MyConnection;
import com.zzlin.performance.objectpool.datasource.ZlinDataSource;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义actuator端点
 * 注解Endpoint(id = "my-datasource") 指定了端点 /actuator/my-datasource
 * @author zlin
 * @date 20220320
 */
@Endpoint(id = "mydatasource")
public class DataSourceEndpoint {

    private ZlinDataSource dataSource;

    public DataSourceEndpoint(ZlinDataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * ReadOperation GET的方式
     */
    @ReadOperation
    public Map<String, Object> poolInfo() {
        Map<String, Object> resultMap = new HashMap<>(3);
        GenericObjectPool<MyConnection> pool = dataSource.getPool();
        resultMap.put("NumActive", pool.getNumActive());
        resultMap.put("NumIdle", pool.getNumIdle());
        resultMap.put("CreatedCount", pool.getCreatedCount());
        return resultMap;
    }

}
