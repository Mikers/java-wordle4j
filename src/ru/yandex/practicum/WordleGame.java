package ru.yandex.practicum;

import java.util.*;

public class WordleGame {

    private static final int MAX_STEPS = 6;
    private static final int WORD_LENGTH = 5;

    private final String answer;
    private int stepsRemaining;
    private final Dictionary fullDictionary;
    private Dictionary availableWords;
    private final Logger log;
    private boolean won;

    private final Map<Integer, Character> correctPositions;
    private final Set<Character> presentLetters;
    private final Set<Character> absentLetters;

    public WordleGame(Dictionary dictionary, Logger log) {
        this.fullDictionary = dictionary;
        this.availableWords = dictionary;
        this.log = log;
        this.answer = dictionary.getRandomWord();
        this.stepsRemaining = MAX_STEPS;

        this.correctPositions = new HashMap<>();
        this.presentLetters = new HashSet<>();
        this.absentLetters = new HashSet<>();
        this.won = false;

        log.println("Игра началась. Ответ: " + answer);
    }

    public String makeMove(String guess) throws WordNotFoundInDictionary {
        String normalized = normalize(guess);

        if (normalized.length() != WORD_LENGTH) {
            throw new WordNotFoundInDictionary("Слово должно содержать " + WORD_LENGTH + " букв");
        }

        if (!fullDictionary.contains(normalized)) {
            throw new WordNotFoundInDictionary("Слово не найдено в словаре");
        }

        stepsRemaining--;
        String hint = WordComparator.compare(normalized, answer);
        updateHints(normalized, hint);

        if (normalized.equals(answer)) {
            won = true;
        }

        log.println("Ход: " + normalized + " -> " + hint + " (осталось попыток: " + stepsRemaining + ")");

        return hint;
    }

    private void updateHints(String guess, String hint) {
        for (int i = 0; i < guess.length(); i++) {
            char c = guess.charAt(i);
            char h = hint.charAt(i);

            if (h == '+') {
                correctPositions.put(i, c);
                presentLetters.add(c);
            } else if (h == '^') {
                presentLetters.add(c);
            } else if (h == '-') {
                if (!presentLetters.contains(c) && !correctPositions.containsValue(c)) {
                    absentLetters.add(c);
                }
            }
        }

        availableWords = fullDictionary.filterByHints(correctPositions, presentLetters, absentLetters);
        log.println("Доступных слов после фильтрации: " + availableWords.size());
    }

    public String getHint() {
        if (availableWords.size() == 0) {
            log.println("Подсказок нет");
            return null;
        }

        String hint = availableWords.getRandomWord();
        log.println("Предоставлена подсказка: " + hint);
        return hint;
    }

    public boolean isWon() {
        return won;
    }

    public boolean isLost() {
        return stepsRemaining <= 0;
    }

    public int getStepsRemaining() {
        return stepsRemaining;
    }

    public String getAnswer() {
        return answer;
    }

    private String normalize(String word) {
        return word.toLowerCase().replace('ё', 'е').trim();
    }
}