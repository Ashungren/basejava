package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.util.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlStorage implements Storage {
    private SqlHelper sqlHelper;

    public SqlStorage(SqlHelper sqlHelper) {
        this.sqlHelper = sqlHelper;
    }

    @Override
    public void clear() {
        sqlHelper.setSqlCommand("DELETE FROM resume");
        sqlHelper.execute(
                (conn, ps) -> {
                    ps.execute();
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        sqlHelper.setSqlCommand("SELECT * FROM resume r WHERE r.uuid =?");
        return sqlHelper.execute(
                (conn, ps) -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    return new Resume(uuid, rs.getString("full_name"));
                }
        );
    }

    @Override
    public void update(Resume r) {
        sqlHelper.setSqlCommand("UPDATE resume SET full_name =? WHERE uuid =?");
        sqlHelper.execute(
                (conn, ps) -> {
                    ps.setString(1, r.getFullName());
                    ps.setString(2, r.getUuid());
                    ps.execute();
                    return null;
                }
        );
    }

    @Override
    public void save(Resume r) {
        sqlHelper.setSqlCommand("INSERT INTO resume (uuid, full_name) VALUES (?,?)");
        sqlHelper.execute(
                (conn, ps) -> {
                    ps.setString(1, r.getUuid());
                    ps.setString(2, r.getFullName());
                    try {
                        ps.execute();
                    } catch (org.postgresql.util.PSQLException e) {
                        throw new ExistStorageException(r.getUuid());
                    }
                    return null;
                }
        );
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.setSqlCommand("DELETE FROM resume r WHERE r.uuid =?");
        sqlHelper.execute(
                (conn, ps) -> {
                    ps.setString(1, uuid);
                    try {
                        ps.executeQuery();
                    } catch (org.postgresql.util.PSQLException e) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                }
        );
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumeList = new ArrayList<>();
        sqlHelper.setSqlCommand("SELECT * FROM resume r WHERE r.uuid IS NOT NULL");
        return sqlHelper.execute(
                (conn, ps) -> {
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        resumeList.add(new Resume(rs.getString(1).replaceAll(" ", "")
                                , rs.getString(2).replaceAll(" ", "")));
                    }
                    Collections.sort(resumeList);
                    return resumeList;
                }
        );
    }

    @Override
    public int size() {
        sqlHelper.setSqlCommand("SELECT * FROM resume r WHERE r.uuid IS NOT NULL");
        return sqlHelper.execute(
                (conn, ps) -> {
                    ResultSet rs = ps.executeQuery();
                    int i = 0;
                    while (rs.next()) {
                        i++;
                    }
                    return i;
                }
        );
    }
}