package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistException;
import ru.javawebinar.basejava.exception.NotExistException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final Resume UUID_1 = new Resume("uuid1");
    private static final Resume UUID_2 = new Resume("uuid2");
    private static final Resume UUID_3 = new Resume("uuid3");
    private static final Resume UUID_TEST = new Resume("uuid test");

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(UUID_1);
        storage.save(UUID_2);
        storage.save(UUID_3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertTrue(0 == storage.size());
    }

    @Test
    public void update() throws Exception {
        Resume newResume = UUID_1;
        storage.update(newResume);
        Assert.assertTrue(newResume == storage.get(UUID_1.getUuid()));
    }

    @Test(expected = NotExistException.class)
    public void updateNotExist() throws Exception {
        storage.update(UUID_TEST);
    }

    @Test
    public void save() throws Exception {
        storage.save(UUID_TEST);
        Assert.assertTrue(UUID_TEST == storage.get(UUID_TEST.getUuid()));
    }

    @Test(expected = ExistException.class)
    public void saveExist() throws Exception {
        storage.save(UUID_1);
    }

    @Test
    public void saveWithoutOverflow() throws Exception {
        for (int i = 4; i < AbstractArrayStorage.STORAGE_LIMIT + 1; i++) {
            storage.save(new Resume("uuid" + i));
        }
    }

    @Test(expected = StorageException.class)
    public void saveWithOverflow() throws Exception {
        for (int i = 4; i < AbstractArrayStorage.STORAGE_LIMIT + 2; i++) {
            storage.save(new Resume("uuid" + i));
        }
    }

    @Test
    public void get() throws Exception {
        Assert.assertTrue(UUID_1 == storage.get(UUID_1.getUuid()));
    }

    @Test(expected = NotExistException.class)
    public void getNotExist() throws Exception {
        storage.get(UUID_TEST.getUuid());
    }

    @Test(expected = NotExistException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1.getUuid());
        storage.get(UUID_1.getUuid());
    }

    @Test(expected = NotExistException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(UUID_TEST.getUuid());
    }

    @Test
    public void getAll() throws Exception {
        Resume[] storageTest = storage.getAll();
        Assert.assertTrue(3 == storageTest.length);
    }

    @Test
    public void size() throws Exception {
        Assert.assertTrue(3 == storage.size());
    }
}