package ru.yandex.practicum;

public class ConsoleLogger implements Logger {
    @Override
    public void println(String message) {
        System.out.println(message);
    }

    @Override
    public void close() {
    }
}