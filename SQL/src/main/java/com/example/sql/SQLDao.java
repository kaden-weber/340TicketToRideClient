package com.example.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLDao {
    private Connection connection;

    public SQLDao(Connection connection) {
        this.connection = connection;
    }
}
