package project.studymatching.factory.entity;

import project.studymatching.entity.category.Category;

public class CategoryFactory {
    public static Category createCategory() {
        return new Category("name", null);
    }

    public static Category createCategory(String name, Category parent) {
        return new Category(name, parent);
    }

    public static Category createCategoryWithName(String name) {
        return new Category(name, null);
    }
}
