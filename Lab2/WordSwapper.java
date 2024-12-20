import java.util.Scanner;

public class WordSwapper {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть текст (для завершення введення натисніть Enter на порожньому рядку):");
        StringBuilder text = new StringBuilder();

        String line;
        while (!(line = scanner.nextLine()).isEmpty()) {
            text.append(line).append(" ");
        }

        scanner.close();

        try {
            String result = swapFirstAndLastWordsInSentence(text.toString());
            System.out.println("\nРезультат:");
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    public static String swapFirstAndLastWordsInSentence(String text) throws Exception {
        if (text == null || text.isEmpty()) {
            throw new Exception("Текст не може бути порожнім");
        }

        // Розбиваємо текст на речення за кінцевими розділовими знаками
        String[] sentences = text.split("(?<=[.!?])\\s+");
        StringBuilder result = new StringBuilder();

        for (String sentence : sentences) {
            sentence = sentence.trim();

            if (!sentence.isEmpty()) {
                // Виділяємо кінцевий розділовий знак, якщо він є
                String punctuation = sentence.matches(".*[.!?]$") ? sentence.substring(sentence.length() - 1) : "";
                sentence = punctuation.isEmpty() ? sentence : sentence.substring(0, sentence.length() - 1);

                // Розбиваємо речення на слова
                String[] words = sentence.split("\\s+");

                if (words.length < 2) {
                    result.append(sentence);
                } else {
                    // Виділяємо перше і останнє слова
                    String firstWord = words[0];
                    String lastWord = words[words.length - 1];

                    // Розділяємо слова на префікси, основи і суфікси
                    String[] firstWordParts = splitWord(firstWord);
                    String[] lastWordParts = splitWord(lastWord);

                    // Замінюємо перше і останнє слово місцями
                    words[0] = lastWordParts[0] + lastWordParts[1] + lastWordParts[2];
                    words[words.length - 1] = firstWordParts[0] + firstWordParts[1] + firstWordParts[2];

                    result.append(String.join(" ", words));
                }

                result.append(punctuation).append(" ");
            }
        }

        return result.toString().trim();
    }

    /**
     * Розділяє слово на префікс, основу та суфікс
     * @param word слово для розбиття
     * @return масив із трьох частин: префікс, основа, суфікс
     */
    private static String[] splitWord(String word) {
        String prefix = word.replaceAll("^[a-zA-Zа-яА-Я0-9]+.*", "");
        String core = word.replaceAll("^[^a-zA-Zа-яА-Я0-9]+|[^a-zA-Zа-яА-Я0-9]+$", "");
        String suffix = word.replaceAll(".*[a-zA-Zа-яА-Я0-9]+", "");
        return new String[] { prefix, core, suffix };
    }
}
