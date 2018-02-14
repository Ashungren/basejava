package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.util.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SqlStorage implements Storage {
    private SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.execute(
                ps -> {
                    ps.execute();
                    return null;
                }
                , "DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute(
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    return new Resume(uuid, rs.getString("full_name"));
                }
                , "SELECT * FROM resume r WHERE r.uuid =?");
    }

    @Override
    public void update(Resume r) {
        sqlHelper.execute(
                ps -> {
                    ps.setString(1, r.getFullName());
                    ps.setString(2, r.getUuid());
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(r.getUuid());
                    }
                    return null;
                }
                , "UPDATE resume SET full_name =? WHERE uuid =?");
    }

    @Override
    public void save(Resume r) {
        sqlHelper.execute(
                ps -> {
                    ps.setString(1, r.getUuid());
                    ps.setString(2, r.getFullName());
                    try {
                        ps.execute();
                    } catch (SQLException e) {
                        if (Objects.equals(e.getSQLState(), "23505")) {
                            throw new ExistStorageException(r.getUuid());
                        }
                    }
                    return null;
                }
                , "INSERT INTO resume (uuid, full_name) VALUES (?,?)");
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute(
                ps -> {
                    ps.setString(1, uuid);
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                }
                , "DELETE FROM resume r WHERE r.uuid =?");
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumeList = new ArrayList<>();
        return sqlHelper.execute(
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        resumeList.add(new Resume(rs.getString(1).trim(),
                                rs.getString(2).trim()));
                    }
                    return resumeList;
                }
                , "SELECT * FROM resume r WHERE r.uuid IS NOT NULL ORDER BY uuid");
    }

    @Override
    public int size() {
        return sqlHelper.execute(
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    int result = 0;
                    while (rs.next()) {
                        result = rs.getInt(1);
                    }
                    return result;
                }
                , "SELECT COUNT(uuid) FROM resume");
    }
}