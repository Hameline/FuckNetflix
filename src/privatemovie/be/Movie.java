package privatemovie.be;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Movie {

    private int id;
    private String name;
    private int rating;
    private int ownrating;
    private String filePath;
    private Date lastOpenedDate;

    public Movie(int id, String name, int rating, int ownrating, String filePath, Date lastOpenedDate) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.ownrating = ownrating;
        this.filePath = filePath;
        this.lastOpenedDate = lastOpenedDate;
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


    public Movie(int id, String name, int rating, String filePath) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.filePath = filePath;
    }

    public Movie() {
    }

    public Movie(int id, String name, int rating, String filePath, Date localDate) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.filePath = filePath;
        this.lastOpenedDate = localDate;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getLastOpenedDate() {
        return lastOpenedDate;
    }
}

