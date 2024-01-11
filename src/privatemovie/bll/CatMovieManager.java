package privatemovie.bll;

import javafx.collections.ObservableList;
import privatemovie.be.CatMovie;
import privatemovie.be.Category;
import privatemovie.be.Movie;
import privatemovie.dal.db.DAO_DB_CatMovie;

import java.sql.SQLException;

public class CatMovieManager {

    DAO_DB_CatMovie catMovieDAO;

    public CatMovieManager() throws Exception {
        catMovieDAO = new DAO_DB_CatMovie();
    }

    public ObservableList getAllMoviesFromCategory(int CategoryId) throws Exception {
        return (ObservableList) catMovieDAO.getAllMoviesFromCategory(CategoryId);
    }
    public ObservableList getAllCategoriesWithMovieID(int movieID) throws Exception {
        return (ObservableList) catMovieDAO.getAllCategoriesWithMovieID(movieID);
    }

    public void addMovieToCategory(CatMovie newCatMovie) throws SQLException {
        catMovieDAO.addMovieToCategory(newCatMovie);
    }

    public void removeMovieFromCategory(CatMovie removeCatMovie) throws Exception {
        catMovieDAO.removeMovieFromCategory(removeCatMovie);
    }
}