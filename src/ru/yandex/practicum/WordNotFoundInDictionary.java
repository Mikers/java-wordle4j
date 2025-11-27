package ru.yandex.practicum;

import java.io.Serial;

public class WordNotFoundInDictionary extends Exception {
    @Serial
    private static final long serialVersionUID = 2204003125402437247L;

    public WordNotFoundInDictionary(String message) {
        super(message);
    }
}