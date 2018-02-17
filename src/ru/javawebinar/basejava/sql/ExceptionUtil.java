package ru.javawebinar.basejava.sql;

import org.postgresql.util.PSQLException;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.SQLException;

public class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static StorageException convertException(SQLException e) {
        if (e instanceof PSQLException) {

//            http://www.postgresql.org/docs/9.3/static/errcodes-appendix.html
            String result = e.getSQLState();
            if (result.equals("23505")) {
                return new ExistStorageException(null);
            } else if (result.equals("23503")||result.equals("24000")) {
                return new NotExistStorageException(null);
            }
        }
        return new StorageException(e);
    }
}