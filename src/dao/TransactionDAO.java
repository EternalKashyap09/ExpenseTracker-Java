package dao;

import util.DBConnection;
import model.Transaction;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public void addTransaction(Transaction t) {
        String query = "INSERT INTO transactions (user_id, type, amount, category, description) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, t.getUserId());
            stmt.setString(2, t.getType());
            stmt.setDouble(3, t.getAmount());
            stmt.setString(4, t.getCategory());
            stmt.setString(5, t.getDescription());
            stmt.executeUpdate();

            System.out.println("✅ Transaction added successfully!");

        } catch (SQLException e) {
            System.out.println("❌ Error adding transaction: " + e.getMessage());
        }
    }
    public void showSummary(int userId) {
    String query = """
        SELECT
            SUM(CASE WHEN type='income' THEN amount ELSE 0 END) AS total_income,
            SUM(CASE WHEN type='expense' THEN amount ELSE 0 END) AS total_expense
        FROM transactions WHERE user_id = ?
    """;
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            double income = rs.getDouble("total_income");
            double expense = rs.getDouble("total_expense");
            double balance = income - expense;

            System.out.println("\n💰 Summary Report:");
            System.out.println("---------------------------");
            System.out.printf("Total Income:  ₹%.2f\n", income);
            System.out.printf("Total Expense: ₹%.2f\n", expense);
            System.out.printf("Net Balance:   ₹%.2f\n", balance);
        }
    } catch (SQLException e) {
        System.out.println("❌ Error fetching summary: " + e.getMessage());
    }
}


    public List<Transaction> getTransactions(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE user_id = ? ORDER BY date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction t = new Transaction(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("type"),
                    rs.getDouble("amount"),
                    rs.getString("category"),
                    rs.getString("description"),
                    rs.getTimestamp("date")
                );
                transactions.add(t);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching transactions: " + e.getMessage());
        }

        return transactions;
    }
    public void exportTransactionsToCSV(int userId, String username) {
    String query = "SELECT * FROM transactions WHERE user_id = ?";
    String fileName = "transactions_" + username + ".csv";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();

        java.io.FileWriter csvWriter = new java.io.FileWriter(fileName);
        csvWriter.append("ID,UserID,Type,Amount,Category,Date,Description\n");

        while (rs.next()) {
            csvWriter.append(rs.getInt("id") + ",");
            csvWriter.append(rs.getInt("user_id") + ",");
            csvWriter.append(rs.getString("type") + ",");
            csvWriter.append(rs.getDouble("amount") + ",");
            csvWriter.append(rs.getString("category") + ",");
            csvWriter.append(rs.getTimestamp("date") + ",");
            csvWriter.append(rs.getString("description") + "\n");
        }

        csvWriter.flush();
        csvWriter.close();

        System.out.println("✅ Transactions exported successfully to " + fileName);

    } catch (Exception e) {
        System.out.println("❌ Error exporting to CSV: " + e.getMessage());
    }
}

}



