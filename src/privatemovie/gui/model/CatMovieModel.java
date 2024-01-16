package privatemovie.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import privatemovie.be.CatMovie;
import privatemovie.be.Category;
import privatemovie.be.Movie;
import privatemovie.bll.CatMovieManager;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

public class CatMovieModel {
    private ObservableList<Movie> listOfMovies;
    private ObservableList<CatMovie> listOfCatMovies;
    private CatMovieManager catMovieManager;

    public CatMovieModel() throws Exception {
        catMovieManager = new CatMovieManager();
        listOfMovies = FXCollections.observableArrayList();
        listOfCatMovies = FXCollections.observableArrayList();
    }

    /*public ObservableList<CatMovie> searchedCatMovie(String search) {
        ObservableList<CatMovie> searchedCatMovie = FXCollections.observableArrayList();
        for (CatMovie catMovie : listOfMovies) {
            if (catMovie.getName().toLowerCase().contains(search)) {
                searchedCatMovie.add(catMovie);
            }
        }
        return searchedCatMovie;
    }*/

    public ObservableList<Movie> getListOfMovies() {
        return listOfMovies;
    }

    public ObservableList<CatMovie> getListOfCatMovies(Stream<Category> stream) {
        return listOfCatMovies;
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