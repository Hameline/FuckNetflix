package privatemovie.dal.db;

import privatemovie.be.Movie;
import privatemovie.dal.IMovieDataAccess;

import java.io.IOException;
import java.util.List;

public class DAO_DB_Movie implements IMovieDataAccess {

    private PrivateMovieDatabaseConnector databseConnector;

    public DAO_DB_Movie() throws IOException {
        databseConnector = new PrivateMovieDatabaseConnector();
    }
    @Override
    public List<Movie> getAllMovies() throws Exception {
        return null;
    }

    @Override
    public Movie addMovie(String name, double rating) throws Exception {
        return null;
    }

    @Override
    public void deletedMovie(Movie deletedMovie) throws Exception {

    }
}