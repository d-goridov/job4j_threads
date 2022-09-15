package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> filter) {
        StringBuilder rsl = new StringBuilder();
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = input.read()) > 0) {
                if (filter.test((char) data)) {
                    rsl.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rsl.toString();
    }

    public String getContentWithoutUnicode() {
        return getContent(data -> data < 0x80);
    }
}
