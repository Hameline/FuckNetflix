package privatemovie.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import privatemovie.be.Category;
import privatemovie.be.Movie;
import privatemovie.bll.CatMovieManager;
import privatemovie.bll.MovieManager;

import java.util.List;

public class MovieModel {
    private ObservableList<Movie> listOfMovies;

    private MovieManager movieManager;
    private Movie selectedMovie;

    public MovieModel() throws Exception {
        movieManager = new MovieManager();
        selectedMovie = new Movie();

        listOfMovies = FXCollections.observableArrayList();
        listOfMovies.addAll(movieManager.getAllMovies());
    }

    public void setListOfMovies(ObservableList<Movie> listOfMovies) {
        this.listOfMovies = listOfMovies;
    }

    public ObservableList<Movie> searchedMovie(String search ) {
        ObservableList<Movie> searchedMovie = FXCollections.observableArrayList();

        for (Movie movie : listOfMovies) {
            String ownRating = String.valueOf(movie.getOwnrating());
            String imdbRating = String.valueOf(movie.getRating());

            if (movie.getName().toLowerCase().contains(search) || ownRating.toLowerCase().contains(search) ||
                    imdbRating.toLowerCase().contains(search)) {
                searchedMovie.add(movie);
            }
        }
        return searchedMovie;
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

    // Method to delete a Movie
    public void deleteMovie(Movie movie) throws Exception {
        Movie m = movieManager.deletedMovie(movie);
        listOfMovies.remove(m);
    }

    // Method to update Movie information
    public void updateMovie(Movie movie) throws Exception {

        Movie m = selectedMovie;
        m.setName(movie.getName());
        m.setOwnrating(movie.getOwnrating());
        m.setRating(movie.getRating());;
        m.setId(movie.getId());

        movieManager.updateMovie(movie);
    }
}