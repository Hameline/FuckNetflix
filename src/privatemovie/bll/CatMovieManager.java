package privatemovie.bll;

import privatemovie.be.Category;
import privatemovie.be.Movie;
import privatemovie.dal.db.DAO_DB_CatMovie;

import java.sql.SQLException;
import java.util.List;

public class CatMovieManager {

    DAO_DB_CatMovie catMovieDAO;

    public CatMovieManager() throws Exception {
        catMovieDAO = new DAO_DB_CatMovie();
    }

    public List<Movie> getAllMoviesFromCategory(int CategoryId) throws Exception {
        return catMovieDAO.getAllMoviesFromCategory(CategoryId);
    }

    public void addMovieToCategory(Movie selectedMovie, Category selectedCategory) throws SQLException {
        catMovieDAO.addMovieToCategory(selectedMovie, selectedCategory);
    }
}