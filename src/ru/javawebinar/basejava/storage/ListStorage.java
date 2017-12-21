package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    List<Resume> storage = new ArrayList<>();

    @Override
    protected void doClear() {
        storage.clear();
    }

    @Override
    protected void doUpdate(Resume r, Integer index) {
        fillDeletedElement(index);
        insertElement(r, index);
    }

    @Override
    protected void doSave(Resume r, Integer index) {
        insertElement(r, index);
    }

    @Override
    protected Resume doGet(String uuid, Integer index) {
        return storage.get(index);
    }

    @Override
    protected void doDelete(String uuid, Integer index) {
        fillDeletedElement(index);
    }

    @Override
    protected Resume[] doGetAll() {
        Resume[] result = new Resume[storage.size()];
        for (int i = 0; i < storage.size(); i++) {
            result[i] = storage.get(i);
        }
        return result;
    }

    @Override
    protected int doSize() {
        return storage.size();
    }

    @Override
    protected Integer getIndex(String uuid) {
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
            storage.add(r);
        } else {
            storage.add(index, r);
        }
    }

    @Override
    protected void fillDeletedElement(int index) {
        storage.remove(index);
    }
}