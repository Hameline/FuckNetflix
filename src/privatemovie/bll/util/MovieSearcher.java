package privatemovie.bll.util;

import privatemovie.be.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieSearcher {

    public List<Movie> search(List<Movie> searchBase, String query) {
        List<Movie> searchResult = new ArrayList<>();

        // Loop through each Movie in the searchBase
        for (Movie movie : searchBase) {
            // Check if any of the movie's attributes (title, artist name, genre type, duration)
            // contain the specified query (case-insensitive)
            if (
                    containsIgnoreCase(query, movie.getMovieTitle())
                            || containsIgnoreCase(query, movie.getArtistName())
                            || containsIgnoreCase(query, movie.getGenreType())
                            || containsIgnoreCase(query, movie.getMovieDuration())
            ) {
                // If any match is found, add the movie to the searchResult list
                searchResult.add(movie);
            }
        }

        // Return the list of movies that match the search criteria
        return searchResult;
    }

    // Helper method to check if a string contains another string (case-insensitive)
    private boolean containsIgnoreCase(String query, String field) {
        return field.toLowerCase().contains(query.toLowerCase());
    }
}
