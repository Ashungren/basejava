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
        storage.put(((String) index).substring(1), r);
    }

    @Override
    protected void doSave(Resume r, Object index) {
        storage.put((String) index, r);
    }

    @Override
    protected Resume doGet(Object index) {
        return storage.get(((String) index).substring(1));
    }

    @Override
    protected void doDelete(Object index) {
        storage.remove(((String) index).substring(1));
    }

    @Override
    public Resume[] getAll() {
        Resume[] result = new Resume[storage.size()];
        int i = 0;
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            result[i] = entry.getValue();
            i++;
        }
        return result;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean isExist(Object index) {
        String result = ((String) index).substring(0, 1);
        return result.equals("-");
    }

    @Override
    protected Object getIndex(String uuid) {
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            if (uuid.equals(entry.getKey())) {
                return "-" + uuid;
            }
        }
        return uuid;
    }

    @Override
    protected void insertElement(Resume r, Object index) {

    }

    @Override
    protected void fillDeletedElement(Object index) {

    }
}