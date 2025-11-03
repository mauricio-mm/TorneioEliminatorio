// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {            
        Scanner scanner = new Scanner(System.in);
        BinaryTreeOfInteger championship = new BinaryTreeOfInteger(); 
                
        //Criar Campeonato        
        int totalNodes = 31;
        championship.addRoot(1);

        for (int i = 1; i <= totalNodes / 2; i++) {
            championship.addLeft(2 * i, i);
            championship.addRight(2 * i + 1, i);
        }


        //Registrar 16 participantes
        for(int i = 0; i < 16; i++)
        {
            System.out.print("Registrar participante " + i + ": ")
                               
        }


           

        System.out.println("Total de nodos da arvore: " + b.size());

        System.out.println("[1] Caminhamento pre:");
        System.out.println(b.positionsPre());

        b.setRoot(10);

        System.out.println("[2] Caminhamento pre:");
        System.out.println(b.positionsPre());

        System.out.println(b.contains(4));
    }


}