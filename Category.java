package com.kronesoft.expenses;

enum Category {
  ADVERTISING("Advertising"),
  SOFTWARE("Software"),
  BENEFITS("Employee Benefits"),
  TOILETRIES("Bathroom Supplies");

  String label;

  Category(String label) {
    this.label = label;
  }

  @Override
  public String toString() {
    return this.label;
  }
  /**
   * Returns the category that matches the ID.
   * @param id The ID of the category to obtain
   * @return The category
   */
  static Category getFromId(String id) {
    Category[] values = Category.values();

    for (Category category : values) {
      if (category.name().equals(id)) {
        return category;
      }
    }

    return null;
  }
}
