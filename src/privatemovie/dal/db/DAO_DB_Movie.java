package privatemovie.dal.db;

import privatemovie.be.Movie;
import privatemovie.dal.IMovieDataAccess;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO_DB_Movie implements IMovieDataAccess {

    private PrivateMovieDatabaseConnector databseConnector;

    public DAO_DB_Movie() throws IOException {
        databseConnector = new PrivateMovieDatabaseConnector();
    }
    @Override
    public List<Movie> getAllMovies() throws Exception {
        ArrayList<Movie> allMovies = new ArrayList<>();

        try (Connection conn = databseConnector.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM FuckNetflix.dbo.Movie;";
            ResultSet rs = stmt.executeQuery(sql);
            // Loop through rows from the database result set
            while (rs.next()) {
                //Map DB row to Playlist object
                int id = rs.getInt("MovieID");
                String name = rs.getString("MovieName");
                int imbdRating = rs.getInt("IMDBRating");
                int personalRating = rs.getInt("PersonalRating");

                Movie movie = new Movie(id, name, imbdRating, personalRating);
                allMovies.add(movie);
            }
            return allMovies;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not get Movies from database", ex);
        }
    }

    @Override
    public Movie addMovie(Movie movie) throws Exception {
        // SQL statement for creating a new Movie
        String sqlMovie = "INSERT INTO FuckNetflix.dbo.Movie (MovieName, IMDBRating) VALUES (?,?);";

        try (Connection conn = databseConnector.getConnection()) {
            // this makes it able for us to run one statement at the time, so that both will have an effect on the database.
            conn.setAutoCommit(false);
            try (PreparedStatement stmtMovie = conn.prepareStatement(sqlMovie, Statement.RETURN_GENERATED_KEYS)) {
                // Bind parameters for Movie
                stmtMovie.setString(1, movie.getName());
                stmtMovie.setInt(2, movie.getRating());
                // Run the SQL statement for Movie.
                stmtMovie.executeUpdate();
                // Get the Movie ID from the DB
                try (ResultSet rs = stmtMovie.getGeneratedKeys()) {
                    if (rs.next()) {
                        int newMovieId = rs.getInt(1);
                        movie.setId(newMovieId); // set the ID of the Movie
                        // Commit the transaction
                        conn.commit();
                        return movie; // The movie now has an ID set
                    } else {
                        // If we didn't get an ID, something went wrong
                        conn.rollback();
                        throw new SQLException("Creating Movie failed, no ID obtained.");
                    }
                }
            } catch (SQLException e) {
                // If something goes wrong, roll back the transaction
                conn.rollback();
                throw e;
            } finally {
                // here we make the effect to database.
                conn.setAutoCommit(true);
            }
        }
    }

    @Override
    public Movie deletedMovie(Movie deletedMovie) throws Exception {
        // SQL statement
        String sqlCatMovieSQL = "delete from FuckNetflix.dbo.CatMovie where MovieID = ?";
        String sqlMovie = "DELETE FROM FuckNetflix.dbo.Movie WHERE MovieID = ? ";

        try (Connection conn = databseConnector.getConnection();
             PreparedStatement sqlCatMovieSQLstmt = conn.prepareStatement(sqlCatMovieSQL);
             PreparedStatement sqlMoviestmt = conn.prepareStatement(sqlMovie)) {
            // Start a transaction
            conn.setAutoCommit(false);

            try {
                // Delete the song itself
                sqlCatMovieSQLstmt.setInt(1, deletedMovie.getId());
                sqlCatMovieSQLstmt.executeUpdate();

                sqlMoviestmt.setInt(1, deletedMovie.getId());
                sqlMoviestmt.executeUpdate();
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
        return deletedMovie;
    }

    @Override
    public Movie updateMovie(Movie movie) throws Exception {
        String sql = "UPDATE FuckNetflix.dbo.Movie SET MovieName = ?, IMDBRating = ?, PersonalRating = ? WHERE MovieID = ?;";

        try (Connection conn = databseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            // Bind parameters
            stmt.setString(1, movie.getName());
            stmt.setInt(2, movie.getRating());
            stmt.setInt(3, movie.getOwnrating());
            stmt.setInt(4, movie.getId());

            // Run the specified SQL statement
            stmt.executeUpdate();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not update Movie", ex);
        }
        return movie;
    }
}