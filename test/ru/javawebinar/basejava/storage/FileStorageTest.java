package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.strategy.StreamStrategy;

public class FileStorageTest extends AbstractStorageTest {

    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new StreamStrategy()));
    }
}