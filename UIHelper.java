import java.util.Scanner;

public class UIHelper {

    // Only the styled menu header
    public static void printMenuHeader(String title) {
        System.out.println("\n===================================");
        System.out.println("         " + title.toUpperCase());
        System.out.println("===================================");
    }

    public static void lower(String title){
        System.out.println(title.toUpperCase());
        System.out.println("------------------------------------");
    }

    
}
