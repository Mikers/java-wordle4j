package ru.yandex.practicum;

import java.util.Map;
import java.util.Set;

public interface Dictionary {
    boolean contains(String word);
    String getRandomWord();
    int size();
    Dictionary filterByHints(Map<Integer, Character> correctPositions,
                            Set<Character> presentLetters,
                            Set<Character> absentLetters);
}