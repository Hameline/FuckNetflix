package privatemovie.dal.db;

import privatemovie.be.Category;
import privatemovie.dal.ICategoryDataAccess;

import java.io.IOException;
import java.util.List;

public class DAO_DB_Category implements ICategoryDataAccess {

    private PrivateMovieDatabaseConnector databaseConnector;

    public DAO_DB_Category() throws IOException {
        databaseConnector = new PrivateMovieDatabaseConnector();
    }
    @Override
    public List<Category> getAllCategories() throws Exception {
        return null;
    }

    @Override
    public Category addCategory(String name) throws Exception {
        return null;
    }

    @Override
    public void deleteCategory(Category deleteCategory) throws Exception {

    }
}