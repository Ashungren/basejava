package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistException;
import ru.javawebinar.basejava.exception.NotExistException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void clear() {
        doClear();
    }

    public void update(Resume r) {
        Integer index = getIndex(r.getUuid());
        if (!isExist(index)) {
            throw new NotExistException(r.getUuid());
        } else {
            doUpdate(r, index);
        }
    }

    public void save(Resume r) {
        Integer index = getIndex(r.getUuid());
        if (isExist(index)) {
            throw new ExistException(r.getUuid());
        } else {
            doSave(r, index);
        }
    }

    public Resume get(String uuid) {
        Integer index = getIndex(uuid);
        if (!isExist(index)) {
            throw new NotExistException(uuid);
        }
        return doGet(uuid, index);
    }

    public void delete(String uuid) {
        Integer index = getIndex(uuid);
        if (!isExist(index)) {
            throw new NotExistException(uuid);
        } else {
            doDelete(uuid, index);
        }
    }

    public Resume[] getAll() {
        return doGetAll();
    }

    public int size() {
        return doSize();
    }

    protected abstract void doClear();

    protected abstract void doUpdate(Resume r, Integer index);

    protected abstract void doSave(Resume r, Integer index);

    protected abstract Resume doGet(String uuid, Integer index);

    protected abstract void doDelete(String uuid, Integer index);

    protected abstract Resume[] doGetAll();

    protected abstract int doSize();

    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    protected abstract Integer getIndex(String uuid);

    protected abstract void insertElement(Resume r, int index);

    protected abstract void fillDeletedElement(int index);
}