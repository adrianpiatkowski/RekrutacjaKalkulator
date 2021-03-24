import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print("Podaj równanie: ");
        Scanner odczyt = new Scanner(System.in);
        Calculator example = new Calculator(odczyt.nextLine());
        odczyt.close();
        System.out.println("Postać pierwotna: " + example.getInput());
        System.out.println("Wynik: " + example.calculate());
    }
}
