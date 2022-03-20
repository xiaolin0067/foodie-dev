package com.zzlin.performance.objectpool.datasource;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.sql.*;

/**
 * @author zlin
 * @date 20220320
 */
@Slf4j
public class ConnectionPooledObjectFactory implements PooledObjectFactory<Connection> {

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("加载驱动异常", e);
        }
    }

    @Override
    public PooledObject<Connection> makeObject() throws Exception {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://192.168.3.26:3306/foodie-shop-dev?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false",
                "root",
                "123456"
        );
        log.info("创建一个数据库连接");
        return new DefaultPooledObject<>(connection);
    }

    @Override
    public void destroyObject(PooledObject<Connection> pooledObject) throws Exception {
        log.info("关闭一个数据库连接");
        pooledObject.getObject().close();
    }

    @Override
    public boolean validateObject(PooledObject<Connection> pooledObject) {
        Connection connection = pooledObject.getObject();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select 1");
            ResultSet resultSet = preparedStatement.executeQuery();
            int resultInt = resultSet.getInt(1);
            boolean b = resultInt == 1;
            log.info("数据库连接校验：{}", b);
            return b;
        } catch (SQLException e) {
            log.error("数据库连接校验异常", e);
            return false;
        }
    }

    @Override
    public void activateObject(PooledObject<Connection> pooledObject) throws Exception {
        // 初始化从池中获取的对象，创建时已激活此处无需处理，可以对connection做定制操作
    }

    @Override
    public void passivateObject(PooledObject<Connection> pooledObject) throws Exception {
        // 空闲返回到对象池时，将对象取消初始化的操作
    }
}
