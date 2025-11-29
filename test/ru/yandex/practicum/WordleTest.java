package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleTest {

    private static Logger log;
    private Dictionary dictionary;
    private WordleGame game;

    @BeforeAll
    static void initLog() {
        log = new ConsoleLogger();
    }

    @BeforeEach
    void setUp() {
        List<String> words = Arrays.asList("герой", "город", "голос", "гонец", "грант");
        dictionary = new WordleDictionary(words);
        game = new WordleGame(dictionary, log);
    }

    @Test
    void testCompareWords_AllCorrect() {
        String result = WordComparator.compare("герой", "герой");
        assertEquals("+++++", result);
    }

    @Test
    void testCompareWords_SomeCorrect() {
        String result = WordComparator.compare("гонец", "герой");
        assertEquals("+^-^-", result);
    }

    @Test
    void testCompareWords_NoneCorrect() {
        String result = WordComparator.compare("лиана", "город");
        assertEquals("-----", result);
    }

    @Test
    void testCompareWords_WrongPosition() {
        String result = WordComparator.compare("йогер", "герой");
        assertEquals("^^^^^", result);
    }

    @Test
    void testDictionaryContains() {
        assertTrue(dictionary.contains("герой"));
        assertTrue(dictionary.contains("город"));
        assertFalse(dictionary.contains("абвгд"));
    }

    @Test
    void testDictionarySize() {
        assertEquals(5, dictionary.size());
    }

    @Test
    void testGameValidMove() throws WordNotFoundInDictionary {
        String result = game.makeMove("город");
        assertNotNull(result);
        assertEquals(5, result.length());
        assertEquals(5, game.getStepsRemaining());
    }

    @Test
    void testGameInvalidWord() {
        assertThrows(WordNotFoundInDictionary.class, () -> game.makeMove("абвгд"));
    }

    @Test
    void testGameInvalidLength() {
        assertThrows(WordNotFoundInDictionary.class, () -> game.makeMove("абв"));
    }

    @Test
    void testGameWin() throws WordNotFoundInDictionary {
        String answer = game.getAnswer();
        game.makeMove(answer);
        assertTrue(game.isWon());
    }

    @Test
    void testGameLost() throws WordNotFoundInDictionary {
        for (int i = 0; i < 6; i++) {
            game.makeMove("город");
        }
        assertTrue(game.isLost());
    }

    @Test
    void testHintProvided() {
        String hint = game.getHint();
        assertNotNull(hint);
        assertTrue(dictionary.contains(hint));
    }

    @Test
    void testNormalization() throws WordNotFoundInDictionary {
        List<String> words = Arrays.asList("город");
        WordleDictionary dict = new WordleDictionary(words);
        WordleGame testGame = new WordleGame(dict, log);

        String result = testGame.makeMove("ГОРОД");
        assertNotNull(result);
    }

    @Test
    void testDictionaryLoader() throws DictionaryException {
        WordleDictionaryLoader loader = new WordleDictionaryLoader(log);
        Dictionary loaded = loader.load("words_ru.txt", 5);
        assertNotNull(loaded);
        assertTrue(loaded.size() > 0);
    }

    @Test
    void testDictionaryLoaderInvalidFile() {
        WordleDictionaryLoader loader = new WordleDictionaryLoader(log);
        assertThrows(DictionaryException.class, () -> loader.load("nonexistent.txt", 5));
    }
}
