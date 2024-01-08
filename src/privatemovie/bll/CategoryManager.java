package privatemovie.bll;

import privatemovie.be.Category;
import privatemovie.dal.ICategoryDataAccess;
import privatemovie.dal.db.DAO_DB_Category;

import java.util.List;

public class CategoryManager {
    public ICategoryDataAccess categoriesDAO;
    public CategoryManager() throws Exception {
        categoriesDAO = new DAO_DB_Category();
    }

    public List<Category> getAllCategories() throws Exception {
        return categoriesDAO.getAllCategories();
    }

    public Category addCategory(Category category) throws Exception {
        return categoriesDAO.addCategory(category);
    }

    public void deleteCategory(Category deletedCategories) throws Exception {
        categoriesDAO.deleteCategory(deletedCategories);
    }
}