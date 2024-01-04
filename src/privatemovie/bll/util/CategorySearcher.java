package privatemovie.bll.util;

import privatemovie.be.Category;
import privatemovie.be.Movie;

import java.util.ArrayList;
import java.util.List;

public class CategorySearcher {

    public List<Category> search(List<Category> searchBase, String query) {
        List<Category> searchResult = new ArrayList<>();

        // Loop through each category in the searchBase
        for (Category category : searchBase) {
            // Check if any of the categories attributes (title)
            // contain the specified query (case-insensitive)
            if (
                    containsIgnoreCase(query, category.getCategory())
            ) {
                // If any match is found, add the category to the searchResult list
                searchResult.add(category);
            }
        }

        // Return the list of categories that match the search criteria
        return searchResult;
    }

    // Helper method to check if a string contains another string (case-insensitive)
    private boolean containsIgnoreCase(String query, String field) {
        return field.toLowerCase().contains(query.toLowerCase());
    }
}