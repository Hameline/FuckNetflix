package privatemovie.be;

import java.util.Objects;

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

    public Movie(int id) {
        this.id = id;
    }

    public Movie(String name) {
        this.name = name;
    }

    public Movie() {

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
        return name;
    }

    public String getGenreType() {
        return null;
    }

    public String getMovieDuration() {
        return null;
    }

    @Override
    public boolean equals(Object movieID) {
        if (this == movieID) return true;
        if (movieID == null || getClass() != movieID.getClass()) return false;
        Movie movie = (Movie) movieID;
        return getId() == movie.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

