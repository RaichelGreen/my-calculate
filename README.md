import java.util.Scanner;

class InvalidExpressionException extends Exception {
    public InvalidExpressionException(String message) {
        super(message);
    }
}

public class StringCalculator {

    // Константы для максимальной длины строки и результата
    private static final int MAX_STRING_LENGTH = 10;
    private static final int MAX_RESULT_LENGTH = 40;

    // Метод для сложения строк
    public String addStrings(String str1, String str2) {
        String result = str1 + str2;
        return formatResult(result);
    }

    // Метод для вычитания строки из строки
    public String subtractStrings(String str1, String str2) {
        String result = str1.replace(str2, "");
        return formatResult(result);
    }

    // Метод для умножения строки на число
    public String multiplyString(String str, int times) throws InvalidExpressionException {
        if (times < 1 || times > 10) {
            throw new InvalidExpressionException("Число для умножения должно быть от 1 до 10");
        }
        String result = str.repeat(times);
        return formatResult(result);
    }

    // Метод для деления строки на число
    public String divideString(String str, int divisor) throws InvalidExpressionException {
        if (divisor < 1 || divisor > 10) {
            throw new InvalidExpressionException("Число для деления должно быть от 1 до 10");
        }
        int newLength = str.length() / divisor;
        String result = str.substring(0, newLength);
        return formatResult(result);
    }

    // Метод для сокращения строки до 40 символов, если она слишком длинная
    private String formatResult(String result) {
        if (result.length() > MAX_RESULT_LENGTH) {
            return result.substring(0, MAX_RESULT_LENGTH) + "...";
        }
        return result;
    }

    // Основной метод для обработки выражения и вызова нужного метода
    public String calculate(String expression) throws InvalidExpressionException {
        expression = expression.trim();

        // Простейшая валидация выражения по оператору
        if (expression.contains("+")) {
            String[] parts = expression.split("\\+");
            return addStrings(trimQuotes(parts[0]), trimQuotes(parts[1]));
        } else if (expression.contains("-")) {
            String[] parts = expression.split("-");
            return subtractStrings(trimQuotes(parts[0]), trimQuotes(parts[1]));
        } else if (expression.contains("*")) {
            String[] parts = expression.split("\\*");
            return multiplyString(trimQuotes(parts[0]), Integer.parseInt(parts[1].trim()));
        } else if (expression.contains("/")) {
            String[] parts = expression.split("/");
            return divideString(trimQuotes(parts[0]), Integer.parseInt(parts[1].trim()));
        } else {
            throw new InvalidExpressionException("Неподдерживаемая операция");
        }
    }

    // Метод для обрезки кавычек у строк
    private String trimQuotes(String str) throws InvalidExpressionException {
        str = str.trim();
        if (str.startsWith("\"") && str.endsWith("\"")) {
            str = str.substring(1, str.length() - 1);
            if (str.length() > MAX_STRING_LENGTH) {
                throw new InvalidExpressionException("Длина строки не должна превышать 10 символов");
            }
            return str;
        } else {
            throw new InvalidExpressionException("Неправильный формат строки");
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringCalculator calculator = new StringCalculator();

        System.out.println("Введите выражение (например, \"Java\" + \"Code\"): ");
        String expression = scanner.nextLine();

        try {
            String result = calculator.calculate(expression);
            System.out.println("Результат: \"" + result + "\"");
        } catch (InvalidExpressionException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: ожидается целое число после оператора");
        } finally {
            scanner.close();
        }
    }
}
