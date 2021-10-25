import java.util.Scanner;

public class HelloGoodbye {
    public static void main(String[] arg) {
        Scanner name1 = new Scanner(System.in);
        Scanner name2 = new Scanner(System.in);
        name1.next(); 
        name2.next(); 
        System.out.println("Hello " + name1 + " and " + name2 + ".");
        System.out.println("Goodbye " + name2 + " and " + name1 + ".");
        name1.close();
        name2.close();
    }
}