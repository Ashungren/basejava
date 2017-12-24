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
        return (Resume[]) storage.values().toArray();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean isExist(Object index) {
        return index != null;
    }

    @Override
    protected String getIndex(String uuid) {
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            if (uuid.equals(entry.getKey())) {
                return uuid;
            }
        }
        return null;
    }

    @Override
    protected void insertElement(Resume r, Object index) {

    }

    @Override
    protected void fillDeletedElement(Object index) {

    }
}