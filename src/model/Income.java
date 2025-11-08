package model;

import java.time.LocalDate;

public class Income extends Transaction {
    public Income(double amount, String category, String description, LocalDate date) {
        super(amount, category, description, date);
    }

    @Override
    public String getType() {
        return "Income";
    }
}
