package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistException;
import ru.javawebinar.basejava.exception.NotExistException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    public abstract void clear();

    public void update(Resume r) {
        Object index = getIndex(r.getUuid());
        if (!isExist(index)) {
            throw new NotExistException(r.getUuid());
        } else {
            doUpdate(r, index);
        }
    }

    public void save(Resume r) {
        Object index = getIndex(r.getUuid());
        if (isExist(index)) {
            throw new ExistException(r.getUuid());
        } else {
            doSave(r, index);
        }
    }

    public Resume get(String uuid) {
        Object index = getIndex(uuid);
        if (!isExist(index)) {
            throw new NotExistException(uuid);
        }
        return doGet(index);
    }

    public void delete(String uuid) {
        Object index = getIndex(uuid);
        if (!isExist(index)) {
            throw new NotExistException(uuid);
        } else {
            doDelete(index);
        }
    }

    public abstract Resume[] getAll();

    public abstract int size();

    protected abstract void doUpdate(Resume r, Object index);

    protected abstract void doSave(Resume r, Object index);

    protected abstract Resume doGet(Object index);

    protected abstract void doDelete(Object index);

    protected abstract boolean isExist(Object index);

    protected abstract Object getIndex(String uuid);

    protected abstract void insertElement(Resume r, Object index);

    protected abstract void fillDeletedElement(Object index);
}