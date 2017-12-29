package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> map = new HashMap<>();

    @Override
    protected Resume getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        map.put(((Resume)searchKey).getUuid(),r);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null && map.containsKey(((Resume) searchKey).getUuid());
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        map.put(r.getUuid(),r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return map.get(((Resume)searchKey).getUuid());
    }

    @Override
    protected void doDelete(Object searchKey) {
        map.remove(((Resume)searchKey).getUuid());
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume>list= new ArrayList<>(map.values());
        list.sort(RESUME_COMPARATOR);
        return list;
    }

    @Override
    public int size() {
        return map.size();
    }
}
