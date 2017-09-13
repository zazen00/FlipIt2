package com.globalappdev.flipit.models;

import java.util.ArrayList;

/**
 * Created by Owner on 9/2/2017.
 */

public class Expenses {
    ArrayList<Expense> expenses;

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public Expenses() {

    }

    public Expenses(ArrayList<Expense> expenses) {

        this.expenses = expenses;
    }
}
