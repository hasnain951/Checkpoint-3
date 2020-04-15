package com.kronesoft.expenses;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Entrypoint {
  // Have an arraylist to store the expenses.
  static Scanner scanner = null;
  static Database database = null;

  /**
   * Print the list of all expenses
   */
  static void printExpenses() {
    try {
      ArrayList<Expense> expenses = database.getExpenses();

      System.out.println(Expense.HEADING_STRING);
      for (Expense expense : expenses) {
        System.out.println(expense.toRow());
      }
    } catch (SQLException e) {
      System.out.println("An error occured while trying to fetch expenses from the database");
    }
  }
  
  /**
   * Generate and print an analysis
   */
  static void analysisMode() {
    try {
      ArrayList<Expense> expenses = database.getExpenses();
      HashMap<Category, Summary> summaries = new HashMap<Category, Summary>();

      for (Category category : Category.values()) {
        summaries.put(category, new Summary(category.label));
      }

      for (Expense expense : expenses) {
        summaries.get(expense.category).addExpense(expense);
      }

      System.out.println(Summary.HEADING_STRING);
      summaries.forEach((Category category, Summary summary) -> {
        System.out.println(summary);
      });
    } catch(SQLException e) {
      System.out.println("An error occured while trying to fetch expenses from the database");
    }
  }

  /**
   * User selects which category their expense fits under
   * @return Category name of the users chosen category
   */
  static Category selectCategoryMode() {
    while (true) {
      System.out.println("Please select a category. Valid categories include:");
      System.out.println(String.format("%-30s | %s", "ID", "Label") + "\n" + String.format("%-30s | %s", "-".repeat(30), "-".repeat(30)));
      for (Category category : Category.values()) {
        System.out.println(String.format("%-30s | %s", category.name(), category.label));
      }

      String input = scanner.nextLine();
      Category category = Category.getFromId(input);

      if (category == null) {
        System.out.println("That was not a valid category ID");
      } else {
        return category;
      }
    }
  }

  /**
   * Create an expense from user input
   * @return The new expense from the user's input
   */
  static Expense askExpenseMode() {
    System.out.println("Please enter a description");
    String description = scanner.nextLine();

    Category category = selectCategoryMode();

    System.out.println("Please enter the cost (in pence)");
    int amount = scanner.nextInt();
    scanner.nextLine();

    Expense expense = new Expense(description, category, amount);

    return expense;
  }
  
  /**
   * Add an expense to the database
   */
  static void addExpenseMode() {
    Expense expense = askExpenseMode();
    try {
      database.addExpense(expense);
    } catch (SQLException e) {
      System.out.println("An error occured while trying to insert the expense into the database");
    }
  }

  /**
   * Edit an expense in the database
   */
  static void editExpenseMode() {
    printExpenses();
    System.out.println("Please enter an expense ID");
    int id = scanner.nextInt();
    scanner.nextLine();

    Expense expense = askExpenseMode();
    expense.id = id;

    try {
      database.addExpense(expense);
    } catch (SQLException e) {
      System.out.println("An error occured while trying to edit the expense in the database");
    }
  }

  /**
   * Delete an expense from the database
   */
  static void deleteExpenseMode() {
    printExpenses();
    System.out.println("Please enter an expense ID");
    int id = scanner.nextInt();
    scanner.nextLine();

    try {
      database.removeExpense(id);
    } catch (SQLException e) {
      System.out.println("An error occured while trying to remove the expense from the database");
    }
  }

  /**
   * Delete all expenses
   */
  static void deleteAllExpensesMode() {
    try {
      database.removeAllExpenses();
    } catch(SQLException e) {
      System.out.println("An error occured while trying to remove all expenses from the database");
    }
  }

  /**
   * The editing menu system
   */
  static void editMenu() {
    while (true) {
      System.out.println("Edit Menu");
      System.out.println("1. Add Expense");
      System.out.println("2. Edit Expense");
      System.out.println("3. Delete Expense");
      System.out.println("4. Delete ALL Expenses");
      System.out.println("0. Back");

      int option = scanner.nextInt();
      scanner.nextLine();

      if (option == 1) {
        addExpenseMode();
      } else if (option == 2) {
        editExpenseMode();
      } else if (option == 3) {
        deleteExpenseMode();
      } else if (option == 4) {
        deleteAllExpensesMode();
      } else if (option == 0) {
        break;
      }
    }
  }

  /**
   * The analysis menu system
   */
  static void analysisMenu() {
    while (true) {
      System.out.println("Analysis Menu");
      System.out.println("1. View Expenses");
      System.out.println("2. Analyse Expenses");
      System.out.println("0. Back");

      int option = scanner.nextInt();
      scanner.nextLine();

      if (option == 1) {
        printExpenses();
      } else if (option == 2) {
        analysisMode();
      } else if (option == 0) {
        break;
      }
    }
  }

  /**
   * The main menu system
   */
  static void mainMenu() {
    while (true) {
      System.out.println("Welcome to the Expenses system!");
      System.out.println("1. Edit Menu");
      System.out.println("2. Analysis Menu");
      System.out.println("0. Exit");

      int option = scanner.nextInt();
      scanner.nextLine();

      if (option == 1) {
        editMenu();
      } else if (option == 2) {
        analysisMenu();
      } else if (option == 0) {
        break;
      }
    }
  }

  public static void main(String args[]) throws SQLException, ClassNotFoundException {
    database = new Database();
    scanner = new Scanner(System.in);

    mainMenu();

    System.out.println("Thank you for using this application!");

    scanner.close();
    database.close();
  }
}
