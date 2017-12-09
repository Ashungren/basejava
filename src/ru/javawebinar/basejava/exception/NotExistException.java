package ru.javawebinar.basejava.exception;

public class NotExistException extends StorageException {
    public NotExistException(String uuid) {
        super("Resume " + uuid + " not exist", uuid);
    }
}