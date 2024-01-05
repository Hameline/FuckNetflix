package privatemovie.dal.db;

import privatemovie.be.Category;
import privatemovie.dal.ICategoryDataAccess;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO_DB_Category implements ICategoryDataAccess {

    private PrivateMovieDatabaseConnector databaseConnector;

    public DAO_DB_Category() throws IOException {
        databaseConnector = new PrivateMovieDatabaseConnector();
    }

    @Override
    public List<Category> getAllCategories() throws Exception {
        ArrayList<Category> allCategories = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = "SELECT * FROM FuckNetflix.dbo.Category;";

            ResultSet rs = stmt.executeQuery(sql);

            // Loop through rows from the database result set
            while (rs.next()) {

                //Map DB row to Category object
                int id = rs.getInt("Id");
                String name = rs.getString("Name");

                Category category = new Category(id, name);

                allCategories.add(category);
            }
            return allCategories;

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Could not get Category from database", ex);
        }
    }

    @Override
    public Category addCategory(String name) throws Exception {
        String sql = "INSERT INTO FuckNetflix.dbo.Category (name) VALUES (?);";

        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Bind parameters
            stmt.setString(1, name);

            // Run the specified SQL statement
            stmt.executeUpdate();

            // Get the generated ID from the DB
            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;

            if (rs.next()) {
                id = rs.getInt(1);
            }

            // Create Category object and send up the layers
            Category category = new Category(id, name);

            return category;

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not create Category", ex);
        }
    }

    @Override
    public void deleteCategory(Category deleteCategory) throws Exception {
        try (Connection conn = databaseConnector.getConnection()) {

            String deleteCatMovieSql = "DELETE FROM FuckNetflix.dbo.CatMovie WHERE CategoryId = ?";
            try (PreparedStatement catMovieStmt = conn.prepareStatement(deleteCatMovieSql)) {
                catMovieStmt.setInt(1, deleteCategory.getId());
                catMovieStmt.executeUpdate();
            }

            String deleteCategorySql = "DELETE FROM FuckNetflix.dbo.Category WHERE Id = ?";
            try (PreparedStatement categoryStmt = conn.prepareStatement(deleteCategorySql)) {
                categoryStmt.setInt(1, deleteCategory.getId());
                categoryStmt.executeUpdate();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not delete Category", ex);
        }
    }
}