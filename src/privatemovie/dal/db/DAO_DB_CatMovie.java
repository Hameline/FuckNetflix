package privatemovie.dal.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import privatemovie.be.CatMovie;
import privatemovie.be.Movie;
import privatemovie.dal.ICatMovieDataAccess;

import java.sql.*;
import java.util.List;

public class DAO_DB_CatMovie implements ICatMovieDataAccess {

    private PrivateMovieDatabaseConnector databaseConnector;

    public DAO_DB_CatMovie() throws Exception {
        databaseConnector = new PrivateMovieDatabaseConnector();
    }

    public List<Movie> getAllMoviesFromCategory(int categoryId) throws Exception {
        ObservableList<Movie> allMovies = FXCollections.observableArrayList();

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = "SELECT * FROM FuckNetflix.dbo.Movie M, FuckNetflix.dbo.CatMovie CM, FuckNetflix.dbo.Category C  " +
                    "WHERE M.MovieID = CM.MovieID and C.CategoryID = CM.CategoryID and CM.CategoryID=" + categoryId + ";";

            ResultSet rs = stmt.executeQuery(sql);

            // Loop through rows from the database result set
            while (rs.next()) {

                //Map DB row to Song object
                int id = rs.getInt("MovieID");
                String name = rs.getString("MovieName");
                int rating = rs.getInt("IMDBRating");
                int ownrating = rs.getInt("PersonalRating");
                String filePath = rs.getString("FilePath");

                Movie movie = new Movie(id, name, rating, ownrating, filePath);

                allMovies.add(movie);
            }

            return allMovies;

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not get Movies from Category from the database", ex);
        }
    }

    public List<CatMovie> getAllCategoriesWithMovieID(int movieID) throws Exception {
        ObservableList<CatMovie> allCategories = FXCollections.observableArrayList();

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = "SELECT * FROM FuckNetflix.dbo.CatMovie WHERE MovieID = (?)";

            ResultSet rs = stmt.executeQuery(sql);

            // Loop through rows from the database result set
            while (rs.next()) {

                //Map DB row to Song object
                int id = rs.getInt("MovieID");

                CatMovie catMovie = new CatMovie(id);

                allCategories.add(catMovie);
            }

            return allCategories;

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not get Movies from Category from the database", ex);
        }
    }

    public CatMovie addMovieToCategory(CatMovie newCatMovie) throws SQLException {
        String sql = "INSERT INTO FuckNetflix.dbo.CatMovie (MovieID, CategoryID) VALUES (?,?);";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int category = newCatMovie.getCategoryID();
            int movie = newCatMovie.getMovieID();
            stmt.setInt(1, movie);
            stmt.setInt(2, category);
            // Run the specified SQL statement
            stmt.executeUpdate();

            CatMovie addMovieToCategory = new CatMovie(newCatMovie.getMovieID(), newCatMovie.getCategoryID());

            return addMovieToCategory;

        }
    }

    @Override
    public CatMovie removeMovieFromCategory(CatMovie removeCatMovie) throws Exception {
        // SQL statement
        String sqlCatMovieSQL = "delete from FuckNetflix.dbo.CatMovie where MovieID = ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement sqlCatMovieSQLstmt = conn.prepareStatement(sqlCatMovieSQL)) {
            // Start a transaction
            conn.setAutoCommit(false);

            try {
                // Delete the song itself
                sqlCatMovieSQLstmt.setInt(1, removeCatMovie.getMovieID());
                sqlCatMovieSQLstmt.executeUpdate();
                // Commit the transaction if everything is successful
                conn.commit();
            } catch (SQLException ex) {
                // Rollback the transaction if an error occurs
                conn.rollback();
                throw new Exception("Could not delete song", ex);
            } finally {
                // Reset auto-commit to true
                conn.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not delete song", ex);
        }
        return removeCatMovie;
    }
}