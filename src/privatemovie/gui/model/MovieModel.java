package privatemovie.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import privatemovie.be.Movie;
import privatemovie.bll.MovieManager;

public class MovieModel {
    private ObservableList<Movie> listOfMovies;

    private MovieManager movieManager;

    public MovieModel() throws Exception {
        movieManager = new MovieManager();

        listOfMovies = FXCollections.observableArrayList();
        listOfMovies.addAll(movieManager.getAllMovies());

    }


    public ObservableList<Movie> getListOfMovies() {
        return listOfMovies;
    }

    // Method to create a new Movie
    public void addMovie(Movie movie) throws Exception {
        Movie m = movieManager.addMovie(movie);
        listOfMovies.add(m);
    }

    public ObservableList<Movie> showList() throws Exception {
        listOfMovies.clear();
        listOfMovies.addAll(movieManager.getAllMovies());
        return listOfMovies;
    }
}