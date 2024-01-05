package privatemovie.dal;

import privatemovie.be.Category;
import privatemovie.be.Movie;

import java.sql.SQLException;
import java.util.List;

public interface ICatMovieDataAccess {

    public List<Movie> getAllMoviesFromCategory(int categoryId) throws Exception;

    public void addMovieToCategory(Movie selectedMovie, Category selectedcategory) throws SQLException;
}