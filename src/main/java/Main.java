import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        StringTranslator translator = StringTranslator.getInstance();
        System.out.print("Введите строку для трансляции: ");
        input = scanner.nextLine();
        try {
            System.out.println("Оттранслированная строка: " + translator.translate(input));
        } catch (IllegalArgumentException e) {
            System.out.println("Возникла ошибка при трансляции");
            System.out.println(e.getMessage());
        }
    }
}
