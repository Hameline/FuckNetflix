package privatemovie.dal;

import privatemovie.be.Movie;

import java.util.List;

public interface IMovieDataAccess {
    public List<Movie> getAllMovies() throws Exception;

    public Movie addMovie(String name, double rating) throws Exception;

    public void deletedMovie(Movie deletedMovie) throws Exception;
}
