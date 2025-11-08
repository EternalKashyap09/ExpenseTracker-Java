package service;

import dao.TransactionDAO;
import dao.UserDAO;
import model.Transaction;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ExpenseManager {
    private String username;
    private int userId;
    private TransactionDAO transactionDAO = new TransactionDAO();

    public ExpenseManager(String username) {
        this.username = username;
        this.userId = new UserDAO().getUserIdByUsername(username);
    }
    public void showSummary() {
    transactionDAO.showSummary(userId);
}
    public void exportTransactionsToCSV() {
    transactionDAO.exportTransactionsToCSV(userId, username);
}



    public void addTransaction(boolean isExpense) throws IOException {
        Scanner sc = new Scanner(System.in);
        String type = isExpense ? "expense" : "income";

        System.out.print("Enter amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter category: ");
        String category = sc.nextLine();

        System.out.print("Enter description: ");
        String desc = sc.nextLine();

        Transaction t = new Transaction(userId, type, amount, category, desc);
        transactionDAO.addTransaction(t);
    }

    public void viewTransactions() throws IOException {
        List<Transaction> transactions = transactionDAO.getTransactions(userId);

        if (transactions.isEmpty()) {
            System.out.println("📭 No transactions yet!");
            return;
        }

        System.out.println("\n📋 Your Transactions:");
        System.out.println("------------------------------------------------------------");
        for (Transaction t : transactions) {
            System.out.printf("[%s] ₹%.2f | %s | %s | %s%n",
                    t.getType().toUpperCase(), t.getAmount(), t.getCategory(), t.getDescription(), t.getDate());
        }
        System.out.println("------------------------------------------------------------");
    }
}
