package com.kronesoft.expenses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

class Database {
  private Connection connection = null;
  /**
   * Set up the SQLite database
   * @throws ClassNotFoundException
   */
  public Database() throws ClassNotFoundException {
    Class.forName("org.sqlite.JDBC");
    final String url = "jdbc:sqlite:database.db";

    try {
      final Connection connection = DriverManager.getConnection(url);
      this.connection = connection;

      setupDatabase();
    } catch(final SQLException e) {
      System.err.println(e.getMessage());
    }
  }

  /**
   * Close the database
   * @throws SQLException
   */
  void close() throws SQLException {
    this.connection.close();
  }

  /**
   * Set up the database in case it might not exist
   * @throws SQLException
   */
  void setupDatabase() throws SQLException {
    // final DatabaseMetaData meta = this.connection.getMetaData();
    // System.out.println("The driver name is " + meta.getDriverName());

    final Statement statement = connection.createStatement();
    statement.setQueryTimeout(10);

    statement.executeUpdate("CREATE TABLE IF NOT EXISTS expenses (id INTEGER PRIMARY KEY AUTOINCREMENT, description STRING, category STRING, amount INTEGER)");
  }

  /**
   * Retrieve the list of all expenses
   * @return The list of expenses
   * @throws SQLException
   */
  ArrayList<Expense> getExpenses() throws SQLException {
    final ArrayList<Expense> expenses = new ArrayList<Expense>();
    
    final Statement statement = connection.createStatement();
    statement.setQueryTimeout(10);
    final ResultSet results = statement.executeQuery("SELECT * FROM expenses");

    while(results.next()) {
      final int id = results.getInt("id");
      final String description = results.getString("description");
      final Category category = Category.getFromId(results.getString("category"));
      final int amount = results.getInt("amount");
      final Expense expense = new Expense(id, description, category, amount);

      expenses.add(expense);
    }

    return expenses;
  }

  /**
   * Add an expense to the database
   * @param expense The object of the class Expense
   * @throws SQLException
   */
  void addExpense(final Expense expense) throws SQLException {
    if (expense == null) return;
    if (expense.category == null) return;

    // If (id < 0), it is not in the database yet.
    if (expense.id < 0) {
      final PreparedStatement preparedStatement = connection.prepareStatement("INSERT OR REPLACE INTO expenses(description, category, amount) VALUES (?, ?, ?)");
      preparedStatement.setString(1, expense.description);
      preparedStatement.setString(2, expense.category.name());
      preparedStatement.setInt(3, expense.amount);
      preparedStatement.executeUpdate();
    } else {
      final PreparedStatement preparedStatement = connection.prepareStatement("INSERT OR REPLACE INTO expenses(id, description, category, amount) VALUES (?, ?, ?, ?)");
      preparedStatement.setInt(1, expense.id);
      preparedStatement.setString(2, expense.description);
      preparedStatement.setString(3, expense.category.name());
      preparedStatement.setInt(4, expense.amount);
      preparedStatement.executeUpdate();
    }
  }

  /**
   * Remove a specific expense from the database
   * @param id The ID of the expense
   * @throws SQLException
   */
  void removeExpense(final int id) throws SQLException {
    final PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM expenses WHERE id=?");
    preparedStatement.setInt(1, id);

    preparedStatement.executeUpdate();
  }

  /**
   * Removes all expenses from database
   * @throws SQLException
   */
  void removeAllExpenses() throws SQLException {
    final Statement statement = connection.createStatement();
    statement.executeUpdate("DELETE FROM expenses");
  }
  
  /**
   * Remove a specific expense from the database
   * @param expense The expense to remove
   * @throws SQLException
   */
  void removeExpense(final Expense expense) throws SQLException {
    if (expense == null) return;
    if (expense.id < 0) return;

    removeExpense(expense.id);
  }
}
