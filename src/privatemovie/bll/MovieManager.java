package privatemovie.bll;

import privatemovie.be.Movie;
import privatemovie.dal.IMovieDataAccess;
import privatemovie.dal.db.DAO_DB_Movie;

import java.io.IOException;
import java.util.List;

public class MovieManager {
    private IMovieDataAccess movieDAO;

    public MovieManager() throws IOException {
        movieDAO = new DAO_DB_Movie();
    }

    public List<Movie> getAllMovies() throws Exception {
        return movieDAO.getAllMovies();
    }

    public Movie addMovie(String name, double rating) throws Exception {

        return movieDAO.addMovie(name, rating);
    }
    public void deletedMovie(Movie deletedMovie) throws Exception {
        movieDAO.deletedMovie(deletedMovie);
    }
}
