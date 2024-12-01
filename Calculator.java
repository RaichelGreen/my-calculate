import java.util.Scanner;

class InvalidExpressionException extends Exception {
    public InvalidExpressionException(String message) {
        super(message);
    }
}

public class Calculator {

    // Основной метод для запуска программы
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение (например, 5 + 3 или IV * II):");

        String input = scanner.nextLine();

        try {
            String result = calculate(input);
            System.out.println("Результат: " + result);
        } catch (InvalidExpressionException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    // Основной метод для вычисления выражения
    public static String calculate(String expression) throws InvalidExpressionException {
        expression = expression.trim();

        String[] parts = expression.split("\\s+");
        if (parts.length != 3) {
            throw new InvalidExpressionException("Неверный формат ввода. Используйте формат: a + b.");
        }

        String operand1 = parts[0];
        String operator = parts[1];
        String operand2 = parts[2];

        boolean isRoman = isRomanNumber(operand1) && isRomanNumber(operand2);
        boolean isArabic = isArabicNumber(operand1) && isArabicNumber(operand2);

        if (isRoman && isArabic) {
            throw new InvalidExpressionException("Нельзя смешивать римские и арабские числа.");
        }

        if (isRoman) {
            return calculateRoman(operand1, operator, operand2);
        } else if (isArabic) {
            return calculateArabic(operand1, operator, operand2);
        } else {
            throw new InvalidExpressionException("Неверный формат чисел.");
        }
    }

    // Вычисление для арабских чисел
    private static String calculateArabic(String operand1, String operator, String operand2) throws InvalidExpressionException {
        int a = Integer.parseInt(operand1);
        int b = Integer.parseInt(operand2);

        if (a < 1 || a > 10 || b < 1 || b > 10) {
            throw new InvalidExpressionException("Числа должны быть в диапазоне от 1 до 10 включительно.");
        }

        int result = performOperation(a, b, operator);
        return String.valueOf(result);
    }

    // Вычисление для римских чисел
    private static String calculateRoman(String operand1, String operator, String operand2) throws InvalidExpressionException {
        int a = romanToArabic(operand1);
        int b = romanToArabic(operand2);

        if (a < 1 || a > 10 || b < 1 || b > 10) {
            throw new InvalidExpressionException("Римские числа должны быть в диапазоне от I до X включительно.");
        }

        int result = performOperation(a, b, operator);

        if (result < 1) {
            throw new InvalidExpressionException("Результат работы с римскими числами должен быть положительным.");
        }

        return arabicToRoman(result);
    }

    // Выполнение арифметической операции
    private static int performOperation(int a, int b, String operator) throws InvalidExpressionException {
        return switch (operator) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> a / b;
            default -> throw new InvalidExpressionException("Неподдерживаемая операция: " + operator);
        };
    }

    // Проверка, является ли строка римским числом
    private static boolean isRomanNumber(String str) {
        return str.matches("^(I|II|III|IV|V|VI|VII|VIII|IX|X)$");
    }

    // Проверка, является ли строка арабским числом
    private static boolean isArabicNumber(String str) {
        return str.matches("^(\\d+)$");
    }

    // Конвертация римского числа в арабское
    private static int romanToArabic(String roman) {
        return switch (roman) {
            case "I" -> 1;
            case "II" -> 2;
            case "III" -> 3;
            case "IV" -> 4;
            case "V" -> 5;
            case "VI" -> 6;
            case "VII" -> 7;
            case "VIII" -> 8;
            case "IX" -> 9;
            case "X" -> 10;
            default -> 0;
        };
    }

    // Конвертация арабского числа в римское
    private static String arabicToRoman(int number) {
        String[] romanNumerals = {
                "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X",
                "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX",
                "XXX", "XL", "L"
        };
        return romanNumerals[number - 1];
    }
}
