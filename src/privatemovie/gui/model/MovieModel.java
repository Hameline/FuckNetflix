package privatemovie.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import privatemovie.be.Category;
import privatemovie.be.Movie;
import privatemovie.bll.CatMovieManager;
import privatemovie.bll.MovieManager;

import java.time.LocalDate;
import java.util.List;

public class MovieModel {
    private ObservableList<Movie> listOfMovies;

    private MovieManager movieManager;
    private Movie selectedMovie;
    private int minimunRating = 0;

    public MovieModel() throws Exception {
        movieManager = new MovieManager();
        selectedMovie = new Movie();

        listOfMovies = FXCollections.observableArrayList();
        listOfMovies.addAll(movieManager.getAllMovies());
    }

    public void setListOfMovies(ObservableList<Movie> listOfMovies) {
        this.listOfMovies = listOfMovies;
    }

    public void setMinimunRating(int number) {
        minimunRating = number;
    }

    public ObservableList<Movie> shouldDeleteOldMovies() {
    ObservableList<Movie> deleteMovies = FXCollections.observableArrayList();

        for (Movie m : listOfMovies) {
            boolean b = m.getOwnrating() < 6 && m.getLastOpenedDate().isBefore(LocalDate.now().minusYears(2));
            if (b == true) {
                deleteMovies.add(m);
            }
        }

        return listOfMovies = deleteMovies;
    }

    public ObservableList<Movie> searchedMovie(String search ) {
        ObservableList<Movie> searchedMovie = FXCollections.observableArrayList();

        for (Movie movie : listOfMovies) {
            if (minimunRating != 0) {
                String ownRating = String.valueOf(movie.getOwnrating());
                String imdbRating = String.valueOf(movie.getRating());
                boolean allowOwnRating = false;
                boolean allowImdbRating = false;
                if (movie.getOwnrating() >= minimunRating) {
                    allowOwnRating = true;
                }
                if (movie.getRating() >= minimunRating) {
                    allowImdbRating = true;
                }
                if (allowOwnRating == true || allowImdbRating == true) {
                    if (movie.getName().toLowerCase().contains(search) || ownRating.toLowerCase().contains(search) ||
                            imdbRating.toLowerCase().contains(search)) {
                        searchedMovie.add(movie);
                    }
                }
            }
            if (minimunRating == 0) {
                String ownRating = String.valueOf(movie.getOwnrating());
                String imdbRating = String.valueOf(movie.getRating());

                if (movie.getName().toLowerCase().contains(search) || ownRating.toLowerCase().contains(search) ||
                        imdbRating.toLowerCase().contains(search)) {
                    searchedMovie.add(movie);
                }
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