package ru.yandex.practicum;

public interface Logger extends AutoCloseable {
    void println(String message);
    @Override
    void close();
}