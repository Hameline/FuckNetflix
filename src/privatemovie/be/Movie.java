package privatemovie.be;

public class Movie {

    private int id;
    private String name;
    private double rating;
    private double ownrating;

    public Movie(int id, String name, double rating, double ownrating) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.ownrating = ownrating;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
    public void setOwnrating(double ownrating) {
        this.ownrating = ownrating;
    }
    public double getOwnrating(){
        return ownrating;
    }

    public String getMovieTitle() {
        return null;
    }

    public String getArtistName() {
        return null;
    }

    public String getGenreType() {
        return null;
    }

    public String getMovieDuration() {
        return null;
    }
}

