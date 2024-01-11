package privatemovie.be;

public class CatMovie {
    private int movieID, categoryID;

    public CatMovie() {
    }

    public CatMovie(int movieID, int categoryID) {
        this.movieID = movieID;
        this.categoryID = categoryID;
    }

    public CatMovie(int movieID) {
        this.movieID = movieID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setPCategoryID(int id) {
        this.categoryID = id;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int id) {
        this.movieID = id;
    }
}
