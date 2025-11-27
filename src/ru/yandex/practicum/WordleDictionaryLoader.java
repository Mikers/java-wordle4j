package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WordleDictionaryLoader {

    private final Logger log;

    public WordleDictionaryLoader(Logger log) {
        this.log = log;
    }

    public Dictionary load(String filename, int wordLength) throws DictionaryException {
        List<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new FileReader(filename, StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String normalized = normalize(line);
                if (normalized.length() == wordLength) {
                    words.add(normalized);
                }
            }

        } catch (IOException e) {
            throw new DictionaryException("Не удалось загрузить словарь из файла: " + filename, e);
        }

        if (words.isEmpty()) {
            throw new DictionaryException("Словарь пуст или не содержит слов длиной " + wordLength);
        }

        log.println("Загружено " + words.size() + " слов из словаря");
        return new WordleDictionary(words);
    }

    private String normalize(String word) {
        return word.toLowerCase().replace('ё', 'е').trim();
    }
}
