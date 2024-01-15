package privatemovie.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import privatemovie.be.Movie;
import privatemovie.gui.model.CatMovieModel;
import privatemovie.gui.model.CategoryModel;
import privatemovie.gui.model.MovieModel;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MediaViewController<moviePath> extends BaseController implements Initializable {
    private CategoryModel categoryModel;
    private CatMovieModel catMovieModel;
    private MovieModel movieModel;
    private MainViewController mainViewController;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button playPauseBtn, stopBtn;
    @FXML
    private Slider volumeSlider;
    private MediaPlayer mediaPlayer;
    private int switchFromPlayAndPause = 1;
    private Movie selectedMovie;

    public MediaViewController() throws Exception {
        try {
            movieModel = new MovieModel();
            categoryModel = new CategoryModel();
            catMovieModel = new CatMovieModel();
            mainViewController = new MainViewController();
        } catch (Exception e) {
            mainViewController.displayError(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void setup() throws Exception {
    }

    @FXML
    private void handlePlayPause(ActionEvent actionEvent) throws Exception {
        if (switchFromPlayAndPause == 1) {
            playPauseBtn.setText("||");
            switchFromPlayAndPause = 2;
            play();
        } else if (switchFromPlayAndPause == 2) {
            playPauseBtn.setText("▶");
            switchFromPlayAndPause = 1;
            pause();
        }
    }

    public void playMovie(String moviePath) throws  Exception {
        File file = new File(moviePath);
        Media mMovie = new Media(file.getAbsoluteFile().toURI().toString());

        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.stop();
        }
        try {
            mediaPlayer = new MediaPlayer(mMovie);
            mediaPlayer.play();
            mediaPlayer.setOnEndOfMedia(() -> {
                Movie movie;
                if (mainViewController.tbwMovie.getSelectionModel().getSelectedIndex() != -1) {
                    int nextSongIndex = mainViewController.tbwMovie.getSelectionModel().getSelectedIndex() + 1;
                    mainViewController.tbwMovie.getSelectionModel().select(nextSongIndex);
                    movie = (Movie) mainViewController.tbwMovie.getSelectionModel().getSelectedItem();
                } else {
                    return;
                }
                try {
                    playMovie(movie.getFilePath());

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new Exception("Could not play song", exc);
        }
    }

    public void handleStop(ActionEvent actionEvent) throws Exception {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    @FXML
    private void play() throws Exception {
        Movie movieToPlay = (Movie) mainViewController.tbwMovie.getSelectionModel().getSelectedItem();

        if (movieToPlay != null) {
            playMovie(movieToPlay.getFilePath());
        }
    }

    @FXML
    private void pause() throws Exception {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }
}