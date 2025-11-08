package model;

import java.time.LocalDate;

public class Expense extends Transaction {
    public Expense(double amount, String category, String description, LocalDate date) {
        super(amount, category, description, date);
    }

    @Override
    public String getType() {
        return "Expense";
    }
}
