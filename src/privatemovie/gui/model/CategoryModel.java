package privatemovie.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import privatemovie.be.Category;
import privatemovie.be.Movie;
import privatemovie.bll.CategoryManager;

public class CategoryModel {
    private final ObservableList<Category> listOfCategories = FXCollections.observableArrayList();
    private final CategoryManager categoryManager = new CategoryManager();

    public CategoryModel() throws Exception {
        listOfCategories.addAll(categoryManager.getAllCategories());
    }
    public void addCategory(Category category) throws Exception {
        Category c = categoryManager.addCategory(category);
        listOfCategories.add(c);
    }

    public ObservableList<Category> showList() throws Exception {
        listOfCategories.clear();
        listOfCategories.addAll(categoryManager.getAllCategories());
        return listOfCategories;
    }
}
