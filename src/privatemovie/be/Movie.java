package privatemovie.be;

public class Movie {

    private int id;
    private String name;
    private int rating;
    private int ownrating;

    public Movie(int id, String name, int rating, int ownrating) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.ownrating = ownrating;
    }

    public Movie(int id, String name, int rating) {
        this.id = id;
        this.name = name;
        this.rating = rating;
    }

    public Movie() {

    }

    public Movie(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    public void setOwnrating(int ownrating) {
        this.ownrating = ownrating;
    }
    public int getOwnrating(){
        return ownrating;
    }

    public String getMovieTitle() {
        return null;
    }

    public String getGenreType() {
        return null;
    }

    public String getMovieDuration() {
        return null;
    }
}

