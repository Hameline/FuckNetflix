package privatemovie.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import privatemovie.be.CatMovie;
import privatemovie.be.Category;
import privatemovie.be.Movie;
import privatemovie.bll.CatMovieManager;

import java.sql.SQLException;

public class CatMovieModel {
    private ObservableList<Movie> listOfMovies;
    private ObservableList<CatMovie> listOfCatMovies;
    private CatMovieManager catMovieManager;

    public CatMovieModel() throws Exception {
        catMovieManager = new CatMovieManager();
        listOfMovies = FXCollections.observableArrayList();
        listOfCatMovies = FXCollections.observableArrayList();
    }

    public ObservableList<Movie> getListOfMovies() {
        return listOfMovies;
    }

    public ObservableList<Movie> showList(int categoryNumber) throws Exception {
        listOfMovies.clear();
        listOfMovies.addAll(catMovieManager.getAllMoviesFromCategory(categoryNumber));
        return listOfMovies;
    }
    public ObservableList<CatMovie> showListCategory(int movieID) throws Exception {
        listOfCatMovies.clear();
        listOfCatMovies.addAll(catMovieManager.getAllCategoriesWithMovieID(movieID));
        return listOfCatMovies;
    }

    public void addMovieToCategory(CatMovie newCatMovie) throws SQLException {
        catMovieManager.addMovieToCategory(newCatMovie);
    }

    public void removeMovieFromCategory(CatMovie removeCatMovie) throws Exception {
        catMovieManager.removeMovieFromCategory(removeCatMovie);
    }
}