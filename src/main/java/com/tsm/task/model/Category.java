package com.tsm.task.model;

public class Category {
    private int categoryId;
    private String name;
    private String description;
    private String color;

    public Category() {}

    public Category(int categoryId, String name, String description, String color) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.color = color;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Category{id=" + categoryId + ", name='" + name + "', description='" + description + "', color='" + color + "'}";
    }
}
