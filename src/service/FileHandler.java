package service;

import model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String USER_FILE = "data/users.txt";
    private static final String TRANS_FILE = "data/transactions.txt";

    // Save a new user
    public static void saveUser(User user) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            bw.write(user.toFileString());
            bw.newLine();
        }
    }

    // Verify user credentials
    public static boolean validateUser(String username, String password) throws IOException {
        File file = new File(USER_FILE);
        if (!file.exists()) return false;

        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Save transaction
    public static void saveTransaction(Transaction t, String username) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TRANS_FILE, true))) {
            bw.write(t.toFileString(username));
            bw.newLine();
        }
    }

    // Load all transactions for a user
    public static List<Transaction> loadTransactions(String username) throws IOException {
        List<Transaction> list = new ArrayList<>();
        File file = new File(TRANS_FILE);
        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(TRANS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p[0].equals(username)) {
                    String type = p[1];
                    double amount = Double.parseDouble(p[2]);
                    String category = p[3];
                    String description = p[4];
                    LocalDate date = LocalDate.parse(p[5]);

                    Transaction t = type.equals("Expense")
                            ? new Expense(amount, category, description, date)
                            : new Income(amount, category, description, date);
                    list.add(t);
                }
            }
        }
        return list;
    }
}
