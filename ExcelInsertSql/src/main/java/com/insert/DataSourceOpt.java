package com.insert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author zhaixinwei
 * @date 2023/1/11
 */
public class DataSourceOpt {

    private final String driver;
    private final String url;
    private final String username;
    private final String password;

    public DataSourceOpt(String driver, String url, String username, String password) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void option(String sql){
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);

            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            int row = statement.executeUpdate(sql);
//            boolean execute = statement.execute(sql);
            if (row > 0){
                connection.commit();
                System.out.println("插入成功");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.err.println("Error:  "+sql);
            try {
                assert connection != null;
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            throw new RuntimeException("end");

        }

    }
}
