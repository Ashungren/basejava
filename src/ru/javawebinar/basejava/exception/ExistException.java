package ru.javawebinar.basejava.exception;

public class ExistException extends StorageException {
    public ExistException(String uuid) {
        super("Resume " + uuid + " already exist", uuid);
    }
}