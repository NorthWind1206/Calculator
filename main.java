import calculator.Calculator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();

        System.out.println("=== Expression Calculator ===");
        System.out.println("Введите выражение или 'exit' для выхода:");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Завершение работы.");
                break;
            }

            try {
                double result = calculator.evaluate(input);
                System.out.println("Результат = " + result);
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }
}
