package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void doUpdate(Resume r, Object index) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void doSave(Resume r, Object index) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Object index) {
        return storage.get(index);
    }

    @Override
    protected void doDelete(Object index) {
        storage.remove(index);
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean isExist(Object index) {
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            if (index.equals(entry.getKey())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }
}