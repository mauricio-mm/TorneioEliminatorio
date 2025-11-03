import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        BinaryTreeOfInteger championship = new BinaryTreeOfInteger();

        System.out.println("Digite o número de participantes (n): ");
        int number = scanner.nextInt();
        scanner.nextLine();

        createChampionLeague(championship, number);
    }

    // Criar Campeonato
    public static void createChampionLeague(BinaryTreeOfInteger championship, Integer n) {
        championship.addRoot(1);

        for (int i = 1; i <= n * 2; i++) {
            championship.addLeft(2 * i, i);
            championship.addRight(2 * i + 1, i);
        }

        for (int i = n * 2; i > n; i--) {
            System.out.println("Nome do " + i + "° participante: ");
            championship.setNameNode(i, scanner.nextLine());
        }

    }

}
