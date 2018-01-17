package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        File dir = new File("./src/ru/javawebinar/basejava");
        printDirectoryDeeply(dir, "");
    }

    public static void printDirectoryDeeply(File dir,String indent) {
        File[] files = dir.listFiles();
        File[] dirs;
        if (files != null) {
            dirs = new File[files.length];
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()){
                    System.out.println(indent + files[i].getPath());
                } else if (files[i].isDirectory()) {
                    dirs[i]=files[i];
                }
            }
            for (File file : dirs) {
                if (file!=null) {
                    System.out.println(file.getPath());
                    indent = "    ";
                    printDirectoryDeeply(file, indent);
                }
            }
        }
    }
}