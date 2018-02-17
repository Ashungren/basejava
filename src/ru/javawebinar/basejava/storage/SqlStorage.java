package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM contact");
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "     WHERE r.uuid =? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    GetContacts(rs, r);
                    return r;
                }
        );
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("" +
                            "   UPDATE resume " +
                            "      SET full_name = ? " +
                            "    WHERE uuid = ?")) {
                        EditResumes(ps, r);
                    }
                    try (PreparedStatement ps = conn.prepareStatement("" +
                            "   UPDATE contact " +
                            "      SET (type, value) = (?,?) " +
                            "    WHERE resume_uuid =? ")) {
                        EditContacts(ps, r);
                    }
                    return null;
                }
        );
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (full_name,uuid) VALUES (?,?)")) {
                        EditResumes(ps, r);
                    }
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (type, value,resume_uuid) VALUES (?,?,?)")) {
                        EditContacts(ps, r);
                    }
                    return null;
                }
        );
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        sqlHelper.execute("" +
                        "    SELECT * FROM resume r " +
                        "  ORDER BY full_name,uuid ",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
                    }
                    return null;
                });
        for (Resume resume : resumes) {
            sqlHelper.execute("" +
                            "    SELECT * FROM resume r " +
                            " LEFT JOIN contact c " +
                            "        ON r.uuid = c.resume_uuid " +
                            "     WHERE r.uuid =? ",
                    ps -> {
                        ps.setString(1, resume.getUuid());
                        ResultSet rs = ps.executeQuery();
                        if (!rs.next()) {
                            throw new NotExistStorageException(resume.getUuid());
                        }
                        GetContacts(rs, resume);
                        return null;
                    });
        }
        return resumes;
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", st -> {
            ResultSet rs = st.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void EditContacts(PreparedStatement ps, Resume r) throws SQLException {
        for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
            ps.setString(1, e.getKey().name());
            ps.setString(2, e.getValue());
            ps.setString(3, r.getUuid());
            ps.addBatch();
        }
        ps.executeBatch();
    }

    private void EditResumes(PreparedStatement ps, Resume r) throws SQLException {
        ps.setString(1, r.getFullName());
        ps.setString(2, r.getUuid());
        ps.execute();
    }

    private void GetContacts(ResultSet rs, Resume r) throws SQLException {
        do {
            String value = rs.getString("value");
            if (value != null) {
                ContactType type = ContactType.valueOf(rs.getString("type"));
                r.addContact(type, value);
            }
        } while (rs.next());

    }
}