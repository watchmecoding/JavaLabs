package Lab6;

import java.util.*;

/**
 * Типізована колекція, яка реалізує інтерфейс Set та використовує двозв’язний список як внутрішню структуру.
 *
 * @param <T> Тип елементів у колекції
 */
public class LinkedSet<T> implements Set<T> {
    private final LinkedList<T> elements;

    public LinkedSet() {
        elements = new LinkedList<>();
    }

    public LinkedSet(T element) {
        elements = new LinkedList<>();
        add(element);
    }

    public LinkedSet(Collection<? extends T> collection) {
        elements = new LinkedList<>();
        addAll(collection);
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return elements.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return elements.iterator();
    }

    @Override
    public Object[] toArray() {
        return elements.toArray();
    }

    @Override
    public <E> E[] toArray(E[] a) {
        return elements.toArray(a);
    }

    @Override
    public boolean add(T t) {
        if (!elements.contains(t)) {
            elements.add(t);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return elements.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return elements.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean modified = false;
        for (T item : c) {
            if (add(item)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return elements.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return elements.removeAll(c);
    }

    @Override
    public void clear() {
        elements.clear();
    }

    @Override
    public String toString() {
        return elements.toString();
    }

    /**
     * Виконавчий метод для взаємодії з користувачем.
     */
    public static void main(String[] args) {
        LinkedSet<String> linkedSet = new LinkedSet<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Меню:");
            System.out.println("1. Додати елемент");
            System.out.println("2. Видалити елемент");
            System.out.println("3. Показати всі елементи");
            System.out.println("4. Перевірити наявність елемента");
            System.out.println("5. Очистити колекцію");
            System.out.println("6. Вийти");
            System.out.print("Оберіть дію: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очищення буфера після числа

            switch (choice) {
                case 1:
                    System.out.print("Введіть елемент для додавання: ");
                    String elementToAdd = scanner.nextLine();
                    if (linkedSet.add(elementToAdd)) {
                        System.out.println("Елемент додано. \n");
                    } else {
                        System.out.println("Елемент вже існує. \n");
                    }
                    System.out.println("Натисніть Enter, щоб повернутись в Меню.");
                    scanner.nextLine();
                    break;

                case 2:
                    System.out.print("Введіть елемент для видалення: ");
                    String elementToRemove = scanner.nextLine();
                    if (linkedSet.remove(elementToRemove)) {
                        System.out.println("Елемент видалено. \n");
                    } else {
                        System.out.println("Елемент не знайдено. \n");
                    }
                    System.out.println("Натисніть Enter, щоб повернутись в Меню.");
                    scanner.nextLine();
                    break;

                case 3:
                    System.out.println("Усі елементи: " + linkedSet + " \n");
                    System.out.println("Натисніть Enter, щоб повернутись в Меню.");
                    scanner.nextLine();
                    break;

                case 4:
                    System.out.print("Введіть елемент для перевірки: ");
                    String elementToCheck = scanner.nextLine();
                    if (linkedSet.contains(elementToCheck)) {
                        System.out.println("Елемент існує. \n");
                    } else {
                        System.out.println("Елемент не знайдено. \n");
                    }
                    System.out.println("Натисніть Enter, щоб повернутись в Меню.");
                    scanner.nextLine();
                    break;

                case 5:
                    linkedSet.clear();
                    System.out.println("Колекцію очищено. \n");
                    System.out.println("Натисніть Enter, щоб повернутись в Меню.");
                    scanner.nextLine();
                    break;

                case 6:
                    System.out.println("Вихід з програми.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Некоректний вибір. Спробуйте ще раз.");
            }
        }
    }
}
