import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Клас Letter представляє окрему літеру.
 */
class Letter {
    private final char character;

    public Letter(char character) {
        this.character = character;
    }

    public char getCharacter() {
        return character;
    }
}

/**
 * Клас Word представляє слово, що складається з літер.
 */
class Word {
    private final List<Letter> letters;

    public Word(String word) {
        letters = new ArrayList<>();
        for (char ch : word.toCharArray()) {
            letters.add(new Letter(ch));
        }
    }

    public String getWord() {
        StringBuilder result = new StringBuilder();
        for (Letter letter : letters) {
            result.append(letter.getCharacter());
        }
        return result.toString();
    }
}

/**
 * Клас Sentence представляє речення, що складається зі слів і розділового знака.
 */
class Sentence {
    private final List<Word> words;
    private final String punctuation;

    public Sentence(String sentence) {
        words = new ArrayList<>();
        String trimmed = sentence.trim();
        punctuation = trimmed.matches(".*[.!?]$") ? trimmed.substring(trimmed.length() - 1) : "";
        String content = punctuation.isEmpty() ? trimmed : trimmed.substring(0, trimmed.length() - 1);
        for (String word : content.split("\\s+")) {
            words.add(new Word(word));
        }
    }

    public String getSentence() {
        StringBuilder result = new StringBuilder();
        for (Word word : words) {
            result.append(word.getWord()).append(" ");
        }
        return result.toString().trim() + punctuation;
    }

    public void swapFirstAndLastWords() {
        if (words.size() < 2) {
            return;
        }
        Word first = words.get(0);
        Word last = words.get(words.size() - 1);
        words.set(0, last);
        words.set(words.size() - 1, first);
    }
}

/**
 * Клас Text представляє текст, що складається з речень.
 */
class Text {
    private final List<Sentence> sentences;

    public Text(String text) {
        sentences = new ArrayList<>();
        String cleanedText = text.replaceAll("[\\t\\s]+", " ").trim();
        String[] sentenceParts = cleanedText.split("(?<=[.!?])\\s+");
        for (String sentence : sentenceParts) {
            sentences.add(new Sentence(sentence));
        }
    }

    public String getText() {
        StringBuilder result = new StringBuilder();
        for (Sentence sentence : sentences) {
            result.append(sentence.getSentence()).append(" ");
        }
        return result.toString().trim();
    }

    public void swapWordsInSentences() {
        for (Sentence sentence : sentences) {
            sentence.swapFirstAndLastWords();
        }
    }
}

/**
 * Основний клас для виконання лабораторної роботи.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть текст (для завершення введення натисніть Enter на порожньому рядку):");
        StringBuilder inputText = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).isEmpty()) {
            inputText.append(line).append(" ");
        }
        scanner.close();

        try {
            Text text = new Text(inputText.toString());
            text.swapWordsInSentences();
            System.out.println("\nРезультат:");
            System.out.println(text.getText());
        } catch (Exception e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }
}
