package privatemovie.dal;

import privatemovie.be.CatMovie;
import privatemovie.be.Movie;

import java.sql.SQLException;
import java.util.List;

public interface ICatMovieDataAccess {

    public List<Movie> getAllMoviesFromCategory(int categoryId) throws Exception;

    CatMovie addMovieToCategory(CatMovie newCatMovie) throws SQLException;
}