import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Arrays;

public class MatrixOperations {

	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int rowsA = 0, colsA = 0, rowsB = 0, colsB = 0;
        long[][] A, B, C;
        long sumMax, sumMin;

        try {
            // Введення розміру матриці A
            System.out.print("Введіть кількість рядків матриці A: ");
            rowsA = scanner.nextInt();
            System.out.print("Введіть кількість стовпців матриці A: ");
            colsA = scanner.nextInt();

            // Введення розміру матриці B
            System.out.print("Введіть кількість рядків матриці B: ");
            rowsB = scanner.nextInt();
            System.out.print("Введіть кількість стовпців матриці B: ");
            colsB = scanner.nextInt();

            // Перевірка можливості множення матриць
            if (colsA != rowsB) {
                throw new IllegalArgumentException("Множення неможливе! Кількість стовпців матриці A повинна дорівнювати кількості рядків матриці B.");
            }

            // Ініціалізація матриць A та B
            A = new long[rowsA][colsA];
            B = new long[rowsB][colsB];

            // Введення елементів матриці A
            System.out.println("Введіть елементи матриці A:");
            inputMatrix(scanner, A);

            // Введення елементів матриці B
            System.out.println("Введіть елементи матриці B:");
            inputMatrix(scanner, B);

            // Перемноження матриць
            C = multiplyMatrices(A, B);
            System.out.println("Перемножена матриця C:");
            printMatrix(C);

            // Обчислення суми найбільших і найменших елементів
            sumMax = calculateMaxSum(C);
            sumMin = calculateMinSum(C);

            // Виведення результатів
            System.out.println("Сума найбільших елементів з парних рядків: " + sumMax);
            System.out.println("Сума найменших елементів з непарних рядків: " + sumMin);

        } catch (InputMismatchException e) {
            System.err.println("Помилка введення! Переконайтеся, що вводите цілі числа.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Невідома помилка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    // Метод для введення елементів матриці
    public static void inputMatrix(Scanner scanner, long[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print("Елемент [" + i + "][" + j + "]: ");
                matrix[i][j] = scanner.nextLong();
            }
        }
    }

    // Метод для перемноження матриць
    public static long[][] multiplyMatrices(long[][] A, long[][] B) {
        int rowsA = A.length;
        int colsA = A[0].length;
        int colsB = B[0].length;
        long[][] C = new long[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                C[i][j] = 0;
                for (int k = 0; k < colsA; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }

    // Метод для обчислення суми найбільших елементів із парних рядків
    public static long calculateMaxSum(long[][] matrix) {
        long sumMax = 0;

        for (int i = 0; i < matrix.length; i++) {
            if (i % 2 == 0) {  // Парні рядки
                long max = matrix[i][0];
                for (int j = 1; j < matrix[i].length; j++) {
                    if (matrix[i][j] > max) {
                        max = matrix[i][j];
                    }
                }
                sumMax += max;
            }
        }

        return sumMax;
    }

    // Метод для обчислення суми найменших елементів із непарних рядків
    public static long calculateMinSum(long[][] matrix) {
        long sumMin = 0;

        for (int i = 0; i < matrix.length; i++) {
            if (i % 2 != 0) {  // Непарні рядки
                long min = matrix[i][0];
                for (int j = 1; j < matrix[i].length; j++) {
                    if (matrix[i][j] < min) {
                        min = matrix[i][j];
                    }
                }
                sumMin += min;
            }
        }

        return sumMin;
    }

    // Метод для виведення матриці на екран
    public static void printMatrix(long[][] matrix) {
        for (long[] row : matrix) {
            for (long element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }
}