package ru.yandex.practicum;

import java.util.*;

public class WordleDictionary implements Dictionary {

    private final Set<String> wordSet;
    private final List<String> wordList;
    private final Random random;

    public WordleDictionary(List<String> words) {
        this.wordList = new ArrayList<>(words);
        this.wordSet = new HashSet<>(words);
        this.random = new Random();
    }

    @Override
    public boolean contains(String word) {
        return wordSet.contains(word);
    }

    @Override
    public String getRandomWord() {
        if (wordList.isEmpty()) {
            throw new RuntimeException("Словарь пуст");
        }
        return wordList.get(random.nextInt(wordList.size()));
    }

    @Override
    public int size() {
        return wordList.size();
    }

    @Override
    public Dictionary filterByHints(Map<Integer, Character> correctPositions,
                                   Set<Character> presentLetters,
                                   Set<Character> absentLetters) {
        List<String> filtered = new ArrayList<>();

        for (String word : wordList) {
            if (matchesHints(word, correctPositions, presentLetters, absentLetters)) {
                filtered.add(word);
            }
        }

        return new WordleDictionary(filtered);
    }

    private boolean matchesHints(String word, Map<Integer, Character> correctPositions,
                                  Set<Character> presentLetters, Set<Character> absentLetters) {
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            if (absentLetters.contains(c)) {
                return false;
            }

            if (correctPositions.containsKey(i) && correctPositions.get(i) != c) {
                return false;
            }
        }

        for (char letter : presentLetters) {
            if (word.indexOf(letter) == -1) {
                return false;
            }
        }

        return true;
    }
}
