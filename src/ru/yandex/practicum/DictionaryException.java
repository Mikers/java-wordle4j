package ru.yandex.practicum;

import java.io.Serial;

public class DictionaryException extends Exception {
    @Serial
    private static final long serialVersionUID = -8783705196300464038L;

    public DictionaryException(String message) {
        super(message);
    }

    public DictionaryException(String message, Throwable cause) {
        super(message, cause);
    }
}