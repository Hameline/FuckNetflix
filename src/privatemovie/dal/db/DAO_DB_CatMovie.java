package privatemovie.dal.db;

import privatemovie.be.Category;
import privatemovie.be.Movie;
import privatemovie.dal.ICatMovieDataAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO_DB_CatMovie implements ICatMovieDataAccess {

    private PrivateMovieDatabaseConnector databaseConnector;

    public DAO_DB_CatMovie() throws Exception {
        databaseConnector = new PrivateMovieDatabaseConnector();
    }

    public List<Movie> getAllMoviesFromCategory(int categoryId) throws Exception {
        ArrayList<Movie> allMovies = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = "SELECT * FROM FuckNetflix.dbo.Movie M, FuckNetflix.dbo.CatMovie CM, FuckNetflix.dbo.Category C  " +
                    "WHERE M.Id = CM.MovieId and C.Id = CM.CategoryId and CM.CategoryId=" + categoryId + ";";

            ResultSet rs = stmt.executeQuery(sql);

            // Loop through rows from the database result set
            while (rs.next()) {

                //Map DB row to Song object
                int id = rs.getInt("Id");
                String name = rs.getString("Name");
                int rating = rs.getInt("rating");
                int ownrating = rs.getInt("ownrating");

                Movie movie = new Movie(id, name, rating, ownrating);

                allMovies.add(movie);
            }

            return allMovies;

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not get Movies from Category from the database", ex);
        }
    }

    public void addMovieToCategory(Movie selectedMovie, Category selectedCategory) throws SQLException {
        String sql = "INSERT INTO FuckNetflix.dbo.CatMovie (CategoryId, MovieId) VALUES (?,?);";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int category = selectedCategory.getId();
            int movie = selectedMovie.getId();
            stmt.setInt(1, category);
            stmt.setInt(2, movie);
            // Run the specified SQL statement
            stmt.executeUpdate();
        }
    }
}