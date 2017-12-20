package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistException;
import ru.javawebinar.basejava.exception.NotExistException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            throw new NotExistException(r.getUuid());
        } else {
            fillDeletedElement(index);
            insertElement(r, index);
        }
    }

    @Override
    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            throw new ExistException(r.getUuid());
        } else {
            insertElement(r, index);
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistException(uuid);
        } else {
            return storage.get(index);
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistException(uuid);
        } else {
            fillDeletedElement(index);
        }
    }

    @Override
    public Resume[] getAll() {
        Resume[] result = new Resume[storage.size()];
        for (int i = 0; i < storage.size(); i++) {
            result[i] = storage.get(i);
        }
        return result;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertElement(Resume r, int index) {
        if (index < 0) {
            index = storage.size();
        }
        storage.add(index, r);
    }

    @Override
    protected void fillDeletedElement(int index) {
        storage.remove(index);
    }
}