package privatemovie.dal;

import privatemovie.be.Movie;

import java.util.List;

public interface IMovieDataAccess {
    public List<Movie> getAllMovies() throws Exception;

    public Movie addMovie(Movie movie) throws Exception;

    public Movie deletedMovie(Movie deletedMovie) throws Exception;

    public Movie updateMovie(Movie movie) throws Exception;

    public Movie updateMovieDate(Movie movie) throws Exception;
}
