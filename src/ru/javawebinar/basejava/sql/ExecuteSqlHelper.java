package ru.javawebinar.basejava.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ExecuteSqlHelper<T> {
    T execute(Connection conn, PreparedStatement ps) throws SQLException;
}