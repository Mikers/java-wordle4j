package ru.yandex.practicum;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileLogger implements Logger {
    private final PrintWriter writer;

    public FileLogger(String filename) throws IOException {
        this.writer = new PrintWriter(new FileWriter(filename, true));
    }

    @Override
    public void println(String message) {
        writer.println(message);
        writer.flush();
    }

    @Override
    public void close() {
        writer.close();
    }
}