import model.User;
import service.ExpenseManager;
import dao.UserDAO;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("💰 Welcome to Expense Tracker 💰");

        while (true) {
            System.out.println("\n1. Register\n2. Login\n3. Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();

            switch (choice.toLowerCase()) {
                case "1":
                case "register":
                    register(sc);
                    break;
                case "2":
                case "login":
                    login(sc);
                    break;
                case "3":
                case "exit":
                    System.out.println("Exiting... Goodbye!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice, try again!");
            }
        }
    }

    private static void register(Scanner sc) {
        UserDAO userDAO = new UserDAO();
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        boolean success = userDAO.registerUser(new User(username, password));
        if (success)
            System.out.println("✅ Registration successful!");
        else
            System.out.println("❌ Username might already exist or error occurred!");
    }

    private static void login(Scanner sc) {
        UserDAO userDAO = new UserDAO();
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        boolean valid = userDAO.validateUser(username, password);
        if (valid) {
            System.out.println("✅ Login successful!");
            ExpenseManager em = new ExpenseManager(username);
            try {
                userMenu(sc, em);
            } catch (IOException e) {
                System.out.println("⚠️ Error: " + e.getMessage());
            }
        } else {
            System.out.println("❌ Invalid credentials!");
        }
    }

    private static void userMenu(Scanner sc, ExpenseManager em) throws IOException {
    while (true) {
        System.out.println("\n1. Add Expense");
        System.out.println("2. Add Income");
        System.out.println("3. View Transactions");
        System.out.println("4. View Summary");
        System.out.println("5. Export to CSV");
        System.out.println("6. Logout");
        System.out.print("Choose: ");

        int c = sc.nextInt();
        sc.nextLine(); // consume newline

        switch (c) {
            case 1:
                em.addTransaction(true);
                break;
            case 2:
                em.addTransaction(false);
                break;
            case 3:
                em.viewTransactions();
                break;
            case 4:
                em.showSummary(); // showing summary
                break;
            case 5:
                em.exportTransactionsToCSV();
                break;
            case 6:
                System.out.println("👋 Logged out!");
                return;
            default:
                System.out.println("Invalid choice, try again.");
        }
    }
}

}
