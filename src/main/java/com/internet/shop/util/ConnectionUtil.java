package com.internet.shop.util;

import com.internet.shop.exceptions.ConnectionException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ConnectionException("Can't find MySQL Driver", e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", "MaksymV");
        dbProperties.put("password", "internetShopDBofVM");
        String url = "jdbc:mysql://localhost:3306/internet-shop?serverTimezone=UTC";

        try {
            return DriverManager.getConnection(url, dbProperties);
        } catch (SQLException e) {
            throw new ConnectionException("Can't establish the connection to DB", e);
        }

    }
}
