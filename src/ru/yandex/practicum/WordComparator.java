package ru.yandex.practicum;

public class WordComparator {

    public static String compare(String guess, String answer) {
        StringBuilder result = new StringBuilder();
        char[] answerChars = answer.toCharArray();
        char[] guessChars = guess.toCharArray();
        boolean[] used = new boolean[answer.length()];

        for (int i = 0; i < guess.length(); i++) {
            if (guessChars[i] == answerChars[i]) {
                result.append('+');
                used[i] = true;
            } else {
                result.append('?');
            }
        }

        for (int i = 0; i < guess.length(); i++) {
            if (result.charAt(i) == '?') {
                boolean found = false;
                for (int j = 0; j < answer.length(); j++) {
                    if (!used[j] && guessChars[i] == answerChars[j]) {
                        result.setCharAt(i, '^');
                        used[j] = true;
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    result.setCharAt(i, '-');
                }
            }
        }

        return result.toString();
    }
}