package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistException;
import ru.javawebinar.basejava.exception.NotExistException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_test = "uuid test";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test(expected = NotExistException.class)
    public void clearNotExist() throws Exception {
        storage.clear();
        storage.get(UUID_1);
    }

    @Test
    public void update() throws Exception {
        storage.update(new Resume(UUID_1));
        storage.update(new Resume(UUID_2));
        storage.update(new Resume(UUID_3));
    }

    @Test(expected = NotExistException.class)
    public void updateNotExist() throws Exception {
        storage.update(new Resume(UUID_test));
    }

    @Test
    public void save() throws Exception {
        storage.save(new Resume(UUID_test));
    }

    @Test(expected = ExistException.class)
    public void saveExist() throws Exception {
        storage.save(new Resume(UUID_1));
    }

    @Test
    public void get() throws Exception {
        storage.get(UUID_1);
        storage.get(UUID_2);
        storage.get(UUID_3);
    }

    @Test(expected = NotExistException.class)
    public void getNotExist() throws Exception {
        storage.get(UUID_test);
    }

    @Test
    public void delete() throws Exception {
        storage.delete(UUID_1);
        storage.delete(UUID_2);
        storage.delete(UUID_3);
    }

    @Test(expected = NotExistException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(UUID_test);
    }

    @Test
    public void getAll() throws Exception {
        Resume[] storageTest = storage.getAll();
        Assert.assertEquals(3, storageTest.length);
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }
}