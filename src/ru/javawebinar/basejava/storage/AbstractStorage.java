package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public abstract void clear();

    @Override
    public abstract void update(Resume r);

    @Override
    public abstract void save(Resume r);

    @Override
    public abstract Resume get(String uuid);

    @Override
    public abstract void delete(String uuid);

    @Override
    public abstract Resume[] getAll();

    @Override
    public abstract int size();

    protected abstract int getIndex(String uuid);

    protected abstract void insertElement(Resume r, int index);

    protected abstract void fillDeletedElement(int index);
}