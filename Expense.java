package com.kronesoft.expenses;

class Expense {
  int id;

  // The description of the expense
  String description;

  // One of a set of predefined categories to select from
  Category category;

  // The cost in pence/cent
  int amount;

  /**
   * Generate an expense object without an existing expense ID.
   * For later insertion into a database
   * @param description The description of the expense
   * @param category The category of the expense
   * @param amount The amount that expense costs
   */
  Expense(String description, Category category, int amount) {
    this.id = -1;
    this.description = description;
    this.category = category;
    this.amount = amount;
  }

  /**
   * Generate an expense object with an existing expense ID.
   * @param id The ID of the existing expense
   * @param description The description of the expense
   * @param category The category of the expense
   * @param amount The amount that expense costs
   */
  Expense(int id, String description, Category category, int amount) {
    this.id = id;
    this.description = description;
    this.category = category;
    this.amount = amount;
  }

  /**
   * Get a string of the amount the expense costs in pounds and pence
   * @return
   */
  public String getDecimal() {
    float decimal = ((float) amount) / 100;
    return String.format("£%.2f", decimal);
  }

  /**
   * Get the name of the category.
   * @return The name of the category. Returns <code>&lt;Invalid Category&gt;</code> if the category is null.
   */
  public String getCategoryLabel() {
    if (this.category == null) {
      return "<Invalid Category>";
    } else {
      return this.category.label;
    }
  }

  @Override
  public String toString() {
    return String.format("'%s' costs £%s, and is categorised as %s", this.description, this.getDecimal(), this.category.label);
  }

  public static final String HEADING_STRING = String.format("%-5s | %-30s | %-10s | %-20s", "ID", "Description", "Cost", "Category") + "\n" + String.format("%-5s | %-30s | %-10s | %-20s", "-".repeat(5), "-".repeat(30), "-".repeat(10), "-".repeat(20));
  
  /**
   * Get a row that can be printed out
   * @return A row that can be printed out
   */
  public String toRow() {
    return String.format("%-5d | %-30s | %-10s | %-20s", this.id, this.description, this.getDecimal(), this.getCategoryLabel());
  }

  // Getter, Setter and Constructors
}
