package com.kronesoft.expenses;

class Summary {
  // The number of items within this summary
  int items;

  // The total cost of the summary
  int amount;

  // The label
  String label;

  /**
   * Create a new empty summary with a label
   * @param label The name of the category
   */
  Summary(String label) {
    this(0, 0, label);
  }

  /**
   * Generate a summary with existing values
   * @param items The number of items in the summary
   * @param amount The total sum of the summary
   * @param label The name of the category
   */
  Summary(int items, int amount, String label) {
    this.items = items;
    this.amount = amount;
    this.label = label;
  }

  /**
   * Add an expense to the summary
   * @param expense The expense to add to the summary
   */
  void addExpense(Expense expense) {
    this.items += 1;
    this.amount += expense.amount;
  }

  /**
   * Get the average cost (in pence) of each item in this category
   * @return The average cost
   */
  int getAverage() {
    if (items == 0) return 0;

    // Java auto rounds to the nearest pence! what a killer deal
    return this.amount / this.items;
  }

  /**
   * Get the total amount as a string in pounds and pence
   * @return The string of the value of the total
   */
  String getTotalDecimal() {
    return String.format("£%.2f", ((float) this.amount) / 100);
  }

  /**
   * Get the average as a string in pounds and pence
   * @return The string of the value of the average
   */
  String getAverageDecimal() {
    return String.format("£%.2f", ((float) this.getAverage()) / 100);
  }

  public static final String HEADING_STRING = String.format("%-30s | %-10s | %-10s | %-10s", "Category", "Items", "Average", "Total") + "\n" + String.format("%-30s | %-10s | %-10s | %-10s", "-".repeat(30), "-".repeat(10), "-".repeat(10), "-".repeat(10));
  @Override
  public String toString() {
    return String.format("%-30s | %-10d | %-10s | %-10s", this.label, this.items, this.getAverageDecimal(), this.getTotalDecimal());
  }
}
