package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void doClear() {
        storage.clear();
    }

    @Override
    protected void doUpdate(Resume r, Integer index) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void doSave(Resume r, Integer index) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(String uuid, Integer index) {
        return storage.get(uuid);
    }

    @Override
    protected void doDelete(String uuid, Integer index) {
        storage.remove(uuid);
    }

    @Override
    protected Resume[] doGetAll() {
        Resume[] result = new Resume[storage.size()];
        int i = 0;
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            result[i] = entry.getValue();
            i++;
        }
        return result;
    }

    @Override
    protected int doSize() {
        return storage.size();
    }

    @Override
    protected Integer getIndex(String uuid) {
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            if (uuid.equals(entry.getKey())) {
                return 0;
            }
        }
        return -1;
    }

    @Override
    protected void insertElement(Resume r, int index) {

    }

    @Override
    protected void fillDeletedElement(int index) {

    }
}