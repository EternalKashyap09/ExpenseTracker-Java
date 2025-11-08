package model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Transaction {
    private int id;
    private int userId;
    private String type;  // "income" or "expense"
    private double amount;
    private String category;
    private String description;
    private Timestamp date;

    // Constructor for new transactions
    public Transaction(int userId, String type, double amount, String category, String description) {
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    // Constructor for reading from DB
    public Transaction(int id, int userId, String type, double amount, String category, String description, Timestamp date) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = date;
    }

    public Transaction(double amount2, String category2, String description2, LocalDate date2) {
        //TODO Auto-generated constructor stub
    }

    // Getters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public Timestamp getDate() { return date; }

    public char[] toFileString(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toFileString'");
    }
}
