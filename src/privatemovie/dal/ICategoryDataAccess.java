package privatemovie.dal;

import privatemovie.be.Category;

import java.util.List;

public interface ICategoryDataAccess {

    public List<Category> getAllCategories() throws Exception;
    public Category addCategory(Category category) throws Exception;
    public void deleteCategory(Category deleteCategory) throws Exception;
}