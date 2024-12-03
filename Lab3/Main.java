package Lab3;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Створення масиву об'єктів
        Clothing[] clothes = {
                new Clothing("Куртка", "L", "Чорний", 1200.50, "Adidas"),
                new Clothing("Сорочка", "M", "Білий", 500.00, "H&M"),
                new Clothing("Джинси", "L", "Синій", 800.75, "Levis"),
                new Clothing("Сукня", "S", "Червоний", 1500.20, "Gucci"),
                new Clothing("Футболка", "M", "Зелений", 300.00, "Nike")
        };

        // Сортування: за ціною (зростання), а потім за розміром (спадання)
        Arrays.sort(clothes, Comparator.comparingDouble(Clothing::getPrice)
                .thenComparing(Comparator.comparing(Clothing::getSize).reversed()));

        // Вивід відсортованого масиву
        System.out.println("Відсортований масив:");
        for (Clothing clothing : clothes) {
            System.out.println(clothing);
        }

        // Введення параметрів пошуку
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nВведіть параметри для пошуку:");
        System.out.print("Тип: ");
        String type = scanner.nextLine();
        System.out.print("Розмір: ");
        String size = scanner.nextLine();
        System.out.print("Колір: ");
        String color = scanner.nextLine();
        System.out.print("Ціна: ");
        double price = scanner.nextDouble();
        scanner.nextLine();  // Зчитати залишок після числа
        System.out.print("Бренд: ");
        String brand = scanner.nextLine();

        // Створення об'єкта для пошуку
        Clothing searchItem = new Clothing(type, size, color, price, brand);

        // Пошук об'єкта
        int index = Arrays.asList(clothes).indexOf(searchItem);

        // Вивід результату пошуку
        if (index != -1) {
            System.out.println("\nЗнайдено об'єкт: " + clothes[index]);
        } else {
            System.out.println("\nОб'єкт не знайдено.");
        }

        scanner.close();
    }
}

