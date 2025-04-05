package oppweeder;

import java.util.*;

import oppweeder.service.OppweederService;

public class UserInterface {

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        String operation = "";

        while (!operation.equals("0")) {

            System.out.println("""

                Select an operation to run:

                0. Exit
                1. Check Profile Views
                2. Weed opps
                3. View disciples
                4. View mutuals
                """);
            operation = scanner.nextLine().trim();
            switch (operation) {
                case "0" -> System.out.println("Exiting...");
                //case "1" -> checkProfileViews(scanner);
                case "2" -> oppweeder(scanner);
                //case "3" -> disciples(scanner);
                //case "4" -> mutuals(scanner);
                default -> System.out.println("Not a valid option.");
            }

        }

    }

    private static void oppweeder(Scanner scanner) {
        String opps = OppweederService.runOppweederScript();
        System.out.printf("Opps: %s%n", opps);
    }

}
