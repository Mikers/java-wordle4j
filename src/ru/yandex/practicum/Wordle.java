package ru.yandex.practicum;

import java.io.IOException;
import java.util.Scanner;

public class Wordle {

    private static final String DICTIONARY_FILE = "words_ru.txt";
    private static final String LOG_FILE = "wordle.log";
    private static final int WORD_LENGTH = 5;

    public static void main(String[] args) {
        try (Logger log = new FileLogger(LOG_FILE)) {

            log.println("=== Запуск игры Wordle ===");

            WordleDictionaryLoader loader = new WordleDictionaryLoader(log);
            Dictionary dictionary = loader.load(DICTIONARY_FILE, WORD_LENGTH);

            WordleGame game = new WordleGame(dictionary, log);
            playGame(game);

            System.out.println("\nПравильный ответ: " + game.getAnswer());
            log.println("=== Игра завершена ===");

        } catch (DictionaryException e) {
            System.err.println("Ошибка загрузки словаря: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Ошибка создания лог-файла: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Непредвиденная ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void playGame(WordleGame game) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Добро пожаловать в Wordle!");
            System.out.println("Угадайте слово из " + WORD_LENGTH + " букв за 6 попыток.");
            System.out.println("Подсказка: нажмите Enter для получения подсказки.\n");

            while (!game.isLost() && !game.isWon()) {
                System.out.print("Введите слово (" + game.getStepsRemaining() + " попыток осталось): ");
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    String hint = game.getHint();
                    if (hint != null) {
                        System.out.println("Подсказка: " + hint);
                    } else {
                        System.out.println("Подсказок нет.");
                    }
                    continue;
                }

                try {
                    String result = game.makeMove(input);
                    System.out.println(input);
                    System.out.println(result);

                    if (game.isWon()) {
                        System.out.println("\nПоздравляем! Вы угадали слово!");
                        return;
                    }

                } catch (WordNotFoundInDictionary e) {
                    System.out.println("Ошибка: " + e.getMessage());
                }
            }

            if (game.isLost()) {
                System.out.println("\nИгра окончена. Попытки закончились.");
            }
        }
    }
}
