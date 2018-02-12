package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.sql.ConnectionFactory;
import ru.javawebinar.basejava.sql.ExecuteSqlHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;
    private String sqlCommand;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public void setSqlCommand(String sqlCommand) {
        this.sqlCommand = sqlCommand;
    }

    public <T> T execute(ExecuteSqlHelper<T> executeSqlHelper) {
        try {
            Connection conn = connectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlCommand);
            return executeSqlHelper.execute(conn, ps);
        } catch (Exception e) {
            String result[] = e.toString().split(":");
            if (result[0].equals("ru.javawebinar.basejava.exception.NotExistStorageException")) {
                throw new NotExistStorageException(result[1]);
            } else if (result[0].equals("ru.javawebinar.basejava.exception.ExistStorageException")) {
                throw new ExistStorageException(result[1]);
            }
        }
        return null;
    }
}