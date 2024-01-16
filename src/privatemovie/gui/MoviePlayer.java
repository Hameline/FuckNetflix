package privatemovie.gui;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import privatemovie.be.Movie;
import privatemovie.dal.db.DAO_DB_Movie;

import java.io.File;

public class MoviePlayer {
    protected MoviePlayer mediaPlayer;
    protected Media media;
    protected Movie movie;

    public MoviePlayer(Media media) {
    }

    public Media getMedia() {
        return media;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        if (movie != null && this.movie != movie) {
            this.movie = movie;

            //gets filepath to play song
            if (!movie.getFilePath().isBlank()) {
                media = new Media(new File(DAO_DB_Movie.getFilePath()).toURI().toString());
                mediaPlayer = new MoviePlayer(media);
            }
        }
    }

    public MoviePlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public double getVolume() {
        if (mediaPlayer != null) {
            return mediaPlayer.getVolume();
        }
        return 0;
    }

    public void setVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }
}