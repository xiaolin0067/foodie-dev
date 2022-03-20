package com.zzlin.performance.objectpool.datasource.jdbc;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author zlin
 * @date 20220320
 */
@Slf4j
public class JDBCTest {

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://192.168.3.26:3306/foodie-shop-dev?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false",
                "root",
                "123456"
        );
        PreparedStatement preparedStatement = connection.prepareStatement("select * from users");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            log.info(resultSet.getString("id"));
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

}
