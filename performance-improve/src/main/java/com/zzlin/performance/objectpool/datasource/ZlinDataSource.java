package com.zzlin.performance.objectpool.datasource;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * 注意：用作测试连接信息写死在ConnectionPooledObjectFactory中
 * @author zlin
 * @date 20220320
 */
@Slf4j
public class ZlinDataSource implements DataSource {

    private GenericObjectPool<MyConnection> pool;

    public GenericObjectPool<MyConnection> getPool() {
        return pool;
    }

    public ZlinDataSource() {
        ConnectionPooledObjectFactory factory = new ConnectionPooledObjectFactory();
        this.pool = new GenericObjectPool<>(factory);
        factory.setObjectPool(pool);
        log.info("初始化数据库连接池");
    }

    @Override
    public Connection getConnection() throws SQLException {
        try {
            log.info("获取一个数据库连接");
            return this.pool.borrowObject();
        } catch (Exception e) {
            throw new SQLException("获取连接失败", e);
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new UnsupportedOperationException("未实现的操作");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException("未实现的操作");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException("未实现的操作");
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new UnsupportedOperationException("未实现的操作");
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new UnsupportedOperationException("未实现的操作");
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new UnsupportedOperationException("未实现的操作");
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new UnsupportedOperationException("未实现的操作");
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new UnsupportedOperationException("未实现的操作");
    }
}
