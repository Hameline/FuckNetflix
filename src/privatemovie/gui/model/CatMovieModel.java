package privatemovie.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import privatemovie.be.Category;
import privatemovie.be.Movie;
import privatemovie.bll.CatMovieManager;

import java.sql.SQLException;

public class CatMovieModel {
    private ObservableList<Movie> listOfMovies;
    private CatMovieManager catMovieManager;

    public CatMovieModel() throws Exception {
        catMovieManager = new CatMovieManager();
        listOfMovies = FXCollections.observableArrayList();
        listOfMovies.addAll(catMovieManager.getAllMoviesFromCategory(8));
    }

    public ObservableList<Movie> getListOfMovies() {
        return listOfMovies;
    }

    public ObservableList<Movie> showList(int categoryNumber) throws Exception {
        listOfMovies.clear();
        listOfMovies.addAll(catMovieManager.getAllMoviesFromCategory(categoryNumber));
        return listOfMovies;
    }

    public void addMovieToCategory(Movie selectedMovie, Category selectedCategory) throws SQLException
    {
        catMovieManager.addMovieToCategory(selectedMovie, selectedCategory);
    }
}