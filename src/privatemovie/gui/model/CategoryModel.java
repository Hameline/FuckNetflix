package privatemovie.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import privatemovie.be.Category;
import privatemovie.be.Movie;
import privatemovie.bll.CategoryManager;

public class CategoryModel {
    private ObservableList<Category> listOfCategories;
    private CategoryManager categoryManager = new CategoryManager();

    public CategoryModel() throws Exception {
        categoryManager = new CategoryManager();
        listOfCategories = FXCollections.observableArrayList();
        listOfCategories.addAll(categoryManager.getAllCategories());
    }

    public ObservableList<Category> getObservablePlaylist() {
        return listOfCategories;
    }

    public void addCategory(Category category) throws Exception {
        Category c = categoryManager.addCategory(category);
        listOfCategories.add(c);
    }

    public void deleteCategory(Category deletedCategory) throws Exception {
        categoryManager.deleteCategory(deletedCategory);
    }

    public ObservableList<Category> showList() throws Exception {
        listOfCategories.clear();
        listOfCategories.addAll(categoryManager.getAllCategories());
        return listOfCategories;
    }
}
