package Lab5;

import java.io.*;
import java.util.*;

/**
 * Базовий клас, що описує музичну композицію.
 */
class MusicComposition {
    private String title;
    private String artist;
    private String style;
    private double duration;

    public MusicComposition(String title, String artist, String style, double duration) {
        this.title = title;
        this.artist = artist;
        this.style = style;
        if (duration <= 0) {
            throw new IllegalArgumentException("Тривалість треку повинна бути більше нуля.");
        }
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getStyle() {
        return style;
    }

    public double getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return String.format("Композиція: %s, Виконавець: %s, Стиль: %s, Тривалість: %.2f хв", title, artist, style, duration);
    }
}

/**
 * Клас, що представляє музичний альбом.
 */
class Album {
    private String name;
    private List<MusicComposition> compositions;

    public Album(String name) {
        this.name = name;
        this.compositions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addComposition(MusicComposition composition) {
        compositions.add(composition);
    }

    public List<MusicComposition> getCompositions() {
        return compositions;
    }

    public double getTotalDuration() {
        return compositions.stream().mapToDouble(MusicComposition::getDuration).sum();
    }

    public void sortByStyle(String style) {
        compositions.sort((a, b) -> {
            if (a.getStyle().equalsIgnoreCase(style) && !b.getStyle().equalsIgnoreCase(style)) return -1;
            if (!a.getStyle().equalsIgnoreCase(style) && b.getStyle().equalsIgnoreCase(style)) return 1;
            return 0;
        });
    }

    public void saveToFile(String filePath, boolean append) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, append))) {
            writer.write("Альбом: " + name + "\n");
            for (MusicComposition composition : compositions) {
                writer.write(composition.toString());
                writer.newLine();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Альбом: " + name + "\n");
        for (MusicComposition composition : compositions) {
            sb.append(composition).append("\n");
        }
        return sb.toString();
    }
}

/**
 * Основний клас програми.
 */
public class MusicApp {
    private static List<Album> albums = new ArrayList<>();

    public static void main(String[] args) {
        initializeAlbums();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Додати альбом");
            System.out.println("2. Додати композицію до альбому");
            System.out.println("3. Переглянути альбоми і їх композиції");
            System.out.println("4. Зберегти альбом на диск");
            System.out.println("5. Перестановка композицій за стилем у файлі");
            System.out.println("6. Знайти композиції за тривалістю");
            System.out.println("7. Вийти");
            System.out.print("Оберіть пункт: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очищення буфера

            switch (choice) {
                case 1 -> addAlbum(scanner);
                case 2 -> addCompositionToAlbum(scanner);
                case 3 -> viewAlbums(scanner);
                case 4 -> saveAlbumToDisk(scanner);
                case 5 -> sortFileByStyle(scanner);
                case 6 -> findCompositionsByDuration(scanner);
                case 7 -> {
                    System.out.println("Програма завершена.");
                    return;
                }
                default -> System.out.println("Некоректний вибір. Спробуйте ще раз.");
            }
        }
    }

    private static void initializeAlbums() {
        Album album1 = new Album("Relax Vibes");
        album1.addComposition(new MusicComposition("Peaceful Mind", "Zen Artist", "Ambient", 5.3));
        album1.addComposition(new MusicComposition("Ocean Waves", "Nature Sounds", "Ambient", 6.8));

        Album album2 = new Album("Rock Legends");
        album2.addComposition(new MusicComposition("Thunderstrike", "Metal Band", "Rock", 4.5));
        album2.addComposition(new MusicComposition("Guitar Hero", "Classic Rock", "Rock", 5.0));

        Album album3 = new Album("Pop Hits");
        album3.addComposition(new MusicComposition("Love Story", "Pop Star", "Pop", 3.7));
        album3.addComposition(new MusicComposition("Dance Beat", "DJ Max", "Electronic", 4.2));

        Album album4 = new Album("Classical Moments");
        album4.addComposition(new MusicComposition("Moonlight Sonata", "Beethoven", "Classical", 7.1));
        album4.addComposition(new MusicComposition("Four Seasons", "Vivaldi", "Classical", 8.2));

        Album album5 = new Album("Jazz Essentials");
        album5.addComposition(new MusicComposition("Blue in Green", "Miles Davis", "Jazz", 5.5));
        album5.addComposition(new MusicComposition("Take Five", "Dave Brubeck", "Jazz", 5.2));

        albums.add(album1);
        albums.add(album2);
        albums.add(album3);
        albums.add(album4);
        albums.add(album5);
    }

    private static void addAlbum(Scanner scanner) {
        System.out.print("Введіть назву альбому: ");
        String name = scanner.nextLine();
        albums.add(new Album(name));
        System.out.println("Альбом додано.");
    }

    private static void addCompositionToAlbum(Scanner scanner) {
        System.out.print("Виберіть альбом (за назвою): ");
        String albumName = scanner.nextLine();
        Album album = findAlbumByName(albumName);
        if (album == null) {
            System.out.println("Альбом не знайдено.");
            return;
        }

        System.out.print("Введіть назву композиції: ");
        String title = scanner.nextLine();
        System.out.print("Введіть виконавця: ");
        String artist = scanner.nextLine();
        System.out.print("Введіть стиль: ");
        String style = scanner.nextLine();
        System.out.print("Введіть тривалість (хв): ");
        double duration = scanner.nextDouble();
        scanner.nextLine(); // Очищення буфера

        album.addComposition(new MusicComposition(title, artist, style, duration));
        System.out.println("Композиція додана до альбому.");
    }

    private static void viewAlbums(Scanner scanner) {
        while (true) {
            if (albums.isEmpty()) {
                System.out.println("Немає альбомів.");
                return;
            }
            System.out.println("Альбоми:");
            for (int i = 0; i < albums.size(); i++) {
                System.out.println((i + 1) + ". " + albums.get(i).getName());
            }
            System.out.println("Введіть номер альбому для перегляду треків (або 0 для виходу): ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 0) return;
            if (choice < 1 || choice > albums.size()) {
                System.out.println("Некоректний вибір.");
                continue;
            }
            System.out.println(albums.get(choice - 1));
            System.out.println("Натисніть Enter для повернення в меню альбомів.");
            scanner.nextLine(); // Очікування клавіші Enter
        }
    }

    private static void saveAlbumToDisk(Scanner scanner) {
        System.out.print("Виберіть альбом (за назвою): ");
        String albumName = scanner.nextLine();
        Album album = findAlbumByName(albumName);
        if (album == null) {
            System.out.println("Альбом не знайдено.");
            return;
        }
        System.out.print("Введіть назву файлу (без розширення): ");
        String fileName = scanner.nextLine();
        String filePath = fileName + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            for (MusicComposition composition : album.getCompositions()) {
                writer.write(composition + " (" + album.getName() + ")");
                writer.newLine();
            }
            System.out.println("Альбом додано до файлу " + filePath);
        } catch (IOException e) {
            System.out.println("Помилка під час запису: " + e.getMessage());
        }
    }

    private static void sortFileByStyle(Scanner scanner) {
        System.out.print("Введіть стиль для сортування: ");
        String style = scanner.nextLine();

        System.out.print("Введіть назву файлу (без розширення): ");
        String fileName = scanner.nextLine();
        String filePath = fileName + ".txt";

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Помилка при читанні файлу: " + e.getMessage());
            return;
        }

        // Розділення на треки вибраного стилю та інші
        List<String> matchingLines = new ArrayList<>();
        List<String> otherLines = new ArrayList<>();
        for (String line : lines) {
            if (line.contains("Стиль: " + style)) {
                matchingLines.add(line);
            } else {
                otherLines.add(line);
            }
        }

        // Об'єднання у новому порядку
        matchingLines.addAll(otherLines);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : matchingLines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Файл успішно відсортований.");
        } catch (IOException e) {
            System.out.println("Помилка при записі файлу: " + e.getMessage());
        }
    }

    private static void findCompositionsByDuration(Scanner scanner) {
        System.out.print("Введіть мінімальну тривалість (хв): ");
        double minDuration = scanner.nextDouble();
        System.out.print("Введіть максимальну тривалість (хв): ");
        double maxDuration = scanner.nextDouble();
        scanner.nextLine(); // Очищення буфера

        boolean found = false;
        for (Album album : albums) {
            for (MusicComposition composition : album.getCompositions()) {
                if (composition.getDuration() >= minDuration && composition.getDuration() <= maxDuration) {
                    System.out.println(composition + " (Альбом: " + album.getName() + ")");
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("Композицій у заданому діапазоні не знайдено.");
        }
    }

    private static Album findAlbumByName(String name) {
        return albums.stream().filter(album -> album.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
