# KRONEsoft Expenses
Don't want the feds onto our tax evasion scheme!

## Downloading Files
1. `git clone git@github.com:7coil/kronesoft-expenses.git`

## Running
1. Open `Visual Studio Code` in NoMachine
2. Open the folder that you cloned in VScode.
3. Install the Java Development Plugins in the plugins tab
4. Press F5 to start debugging Kronesoft Expenses.

## Potential Problems
1. If Maven dependencies aren't installed, try opening and saving the `pom.xml` file, and clicking Yes on the bottom right notification.

## Classes
```java
enum Category {
  ADVERTISING("Advertising"),
  BENEFITS("Employee Benefits");
  // Add more
  // Just only add more BELOW. Don't add any inbetween.

  String label;

  Category(String label) {
    this.label = label;
  }
}

class Database {
  Connection connection;

  static ArrayList<Expense> getExpenses() {}
  static ArrayList<Expense> getExpenses(Category category) {}
}

class Expense {
  // The description of the expense
  String description;

  // One of a set of predefined categories to select from
  Category category;

  // The cost in pence/cent
  int amount;

  // Getter, Setter and Constructors
}

class Summary {
  // The number of items within this summary
  int items;

  // The total cost of the summary
  int amount;

  // The label
  String label;

  // Getter, Setter and Constructors

  int getAverage() {
    // Return the average
    // amount / items
  }

  @Override
  public String toString() {
    // Return a single row of a table
    // 30, 10, 10, 10
    // Category          | Items    | Average   | Total
  }
}

class Entrypoint {
  // Have an arraylist to store the expenses.
  static ArrayList<Expense> expenses = new ArrayList<Expense>();

  static void addExpense(Expense expense) {
    expenses.add(expense);
  }

  static void removeExpense(Expense expense) {
    expences.remove(expense);
  }

  static void writeToFile(String file) {
    // Write to the specific file
  }

  static void readFromFile(String file) {
    // Read from the specific file
    // Set `expenses` to a new array of expenses.
  }

  static Summary categorySummary(Category category) {
    // Find a category, and return a summary of the information.
  }

  static Summary overallSummary() {
    // Return a summary of the entire dataset.
  }

  static void analysisMode() {
    // Print out all the summaries.
    // Get a list of unique categories, and use the Category.toString()
    // method to print them all out
    // Then print the last overall summary out.
  }

  static void printExpenses() {
    // Print out all the expenses
  }

  static void editMenu() {
    // printExpenses(), then ask:
    // 1. Add Expense
    // 2. Edit Expense (actually just replacing expense at index)
    // 3. Delete Index
  }

  static void analysisMenu() {
    // 1. View Expenses
    // 2. Analyse Expenses
  }

  static void mainMenu() {
    // 1. Edit Menu
    // 2. Analysis Menu
  }

  public static void main(String args[]) {
    mainMenu();

    // Exit.
  }
}
```
