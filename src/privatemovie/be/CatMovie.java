package privatemovie.be;

import javafx.scene.text.Text;

public class CatMovie {
    private int movieID, categoryID;
    private String movieName;

    public CatMovie() {
    }

    public CatMovie(int movieID, int categoryID, String movieName) {
        this.movieID = movieID;
        this.categoryID = categoryID;
        this.movieName = movieName;
    }

    public CatMovie(int movieID) {
        this.movieID = movieID;
    }

    public CatMovie(String name) {
        this.movieName = name;
    }

    public CatMovie(int movieID, int categoryID) {
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setCategoryID(int id) {
        this.categoryID = id;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int id) {
        this.movieID = id;
    }
}
