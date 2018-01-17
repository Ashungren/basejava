package ru.javawebinar.basejava.strategy;

import ru.javawebinar.basejava.model.Resume;

import java.io.*;

public interface Strategy {

    void doWrite(Resume r, OutputStream os) throws IOException;

    Resume doRead(InputStream is) throws IOException;
}