package ru.javawebinar.basejava;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        File dir = new File(".");
        Search(dir);
    }

    private static void Search(File dir) {
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                File check = new File(dir + "/" + name);
                System.out.println(check);
                if (check.isDirectory()) {
                    Search(check);
                }
            }
        }
    }
}