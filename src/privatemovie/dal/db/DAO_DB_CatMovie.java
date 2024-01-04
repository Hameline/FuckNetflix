package privatemovie.dal.db;

import privatemovie.be.Category;
import privatemovie.be.Movie;
import privatemovie.dal.ICatMovieDataAccess;

import java.sql.SQLException;
import java.util.List;

public class DAO_DB_CatMovie implements ICatMovieDataAccess {

    private PrivateMovieDatabaseConnector databaseConnector;

    public DAO_DB_CatMovie() throws Exception {
        databaseConnector = new PrivateMovieDatabaseConnector();
    }

    public List<Movie> getAllMoviesFromCategory(int categoryId) throws SQLException {
        return null;
    }

    public void addMovieToCategory(Movie selectedMovie, Category selectedcategory) {
    }
}