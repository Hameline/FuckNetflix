package privatemovie.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import privatemovie.be.Movie;
import privatemovie.bll.MovieManager;

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