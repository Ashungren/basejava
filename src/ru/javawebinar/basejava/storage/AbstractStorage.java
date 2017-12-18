package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistException;
import ru.javawebinar.basejava.exception.NotExistException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Collection;

public abstract class AbstractStorage implements Storage {
    protected Collection<Resume> collection;

    @Override
    public void clear() {
        collection.clear();
    }

    @Override
    public void update(Resume r) {
        if (elementNotExist(r.getUuid())) {
            throw new NotExistException(r.getUuid());
        } else {
            updateElement(r);
        }
    }

    @Override
    public void save(Resume r) {
        if (!(elementNotExist(r.getUuid()))) {
            throw new ExistException(r.getUuid());
        } else {
            insertElement(r);
        }
    }

    @Override
    public Resume get(String uuid) {
        if (elementNotExist(uuid)) {
            throw new NotExistException(uuid);
        } else {
            return getElement(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        if (elementNotExist(uuid)) {
            throw new NotExistException(uuid);
        } else {
            fillDeletedElement(uuid);
        }
    }

    @Override
    public Resume[] getAll() {
        return (Resume[]) collection.toArray();
    }

    @Override
    public int size() {
        return collection.size();
    }

    protected abstract void updateElement(Resume r);

    protected abstract void insertElement(Resume r);

    protected abstract Resume getElement(String uuid);

    protected abstract void fillDeletedElement(String uuid);

    private boolean elementNotExist(String uuid) {
        for (Resume resume : collection) {
            if (resume.getUuid().equals(uuid)) {
                return false;
            }
        }
        return true;
    }
}