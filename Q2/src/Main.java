
public class Main {
    public static void main(String[] args) {

        GeneralTreeOfInteger arv = new GeneralTreeOfInteger();
        //raiz
        arv.add(1, null);        

        //nivel 1
        arv.add(2, 1);        
        arv.add(3, 1);    

        //nivel 2
        arv.add(4, 2);
        
        arv.setName(1, "App");
        arv.setName(1, "Configurações");
        arv.setName(4, "Notificações");

    }
}