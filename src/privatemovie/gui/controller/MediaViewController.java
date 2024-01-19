package privatemovie.gui.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import privatemovie.be.Movie;
import privatemovie.gui.model.CatMovieModel;
import privatemovie.gui.model.CategoryModel;
import privatemovie.gui.model.MovieModel;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MediaViewController<moviePath> extends BaseController implements Initializable {

    @FXML
    private Label movieTimer, movieTimer2;
    private CategoryModel categoryModel;
    private CatMovieModel catMovieModel;
    private MovieModel movieModel;
    private MainViewController mainViewController;
    @FXML
    private MediaView movieView;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button playPauseBtn, stopBtn;
    @FXML
    private Slider volumeSlider;
    private MediaPlayer mediaPlayer;
    private int switchFromPlayAndPause = 1;
    private Movie selectedMovie;
    private TableView<Movie> tbwMovie;

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

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void setup() throws Exception {
        if (movieModel != null) {
            mainViewController.tbwMovie.setItems(movieModel.getListOfMovies());
        }

        volumeSlider.setMin(0);
        volumeSlider.setMax(100);

        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(newValue.doubleValue() / 100.0);
            }
        });
    }

    // Play/pause button that changes between the icons
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

    // Plays the movie file
    public void playMovie(String moviePath) throws  Exception {
        if (moviePath == null) {
            throw new IllegalArgumentException("moviePath cannot be null");
        }
        File file = new File(moviePath);
        Media mMovie = new Media(file.getAbsoluteFile().toURI().toString());

        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.stop();
        }
        try {
            mediaPlayer = new MediaPlayer(mMovie);
            movieProgress();
            playingTimerUp();
            //playingTimerDown();
            movieView.setMediaPlayer(mediaPlayer);

            movieView.setFitWidth(700);
            movieView.setFitHeight(500);

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
            throw new Exception("Could not play movie", exc);
        }
    }

    // Simple stop button for the player
    public void handleStop(ActionEvent actionEvent) throws Exception {
        if (switchFromPlayAndPause == 2) {
            playPauseBtn.setText("▶");
            switchFromPlayAndPause = 1;
            pause();
        }
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    // Calls the playMovie method
    @FXML
    private void play() throws Exception {
        Movie movieToPlay = (Movie) mainViewController.tbwMovie.getSelectionModel().getSelectedItem();

        if (movieToPlay != null) {
            playMovie(movieToPlay.getFilePath());
        }
    }

    // Pauses the movie, but couldn't figure out how to store the current time, so we can unpause it to continue
    @FXML
    private void pause() throws Exception {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    // Follows the movie progress but the program gets an error, but it still works
    public void movieProgress() {
        ReadOnlyObjectWrapper<Number> progressWrapper = new ReadOnlyObjectWrapper<>(0);

        // Bind the progress bar's progress to the progress of the mediaPlayer
        progressWrapper.bind(Bindings.createObjectBinding(
                () -> mediaPlayer.getCurrentTime().toMillis() / mediaPlayer.getTotalDuration().toMillis(),
                mediaPlayer.currentTimeProperty(),
                mediaPlayer.totalDurationProperty()
        ));

        // Bind the progress bar's progress property to the progress wrapper
        progressBar.progressProperty().bind(progressWrapper);
    }

    // A timer to show how far along you are
    private void playingTimerUp() {
        movieTimer.textProperty().bind(
                new StringBinding() {
                    {
                        super.bind(mediaPlayer.currentTimeProperty());
                    }
                    // Makes the timer show in minutes and seconds
                    @Override
                    protected String computeValue() {
                        int time = (int) (mediaPlayer.getCurrentTime().toMillis() / 1000);
                        int minutes = time / 60;
                        int seconds = time % 60;
                        String textSeconds;
                        if (seconds <= 9) {
                            textSeconds = "0" + seconds;
                        } else {
                            textSeconds = "" + seconds;
                        }
                        return minutes + ":" + textSeconds;
                    }
                });
    }

    // Shows the full length of the movie and counts down
    private void playingTimerDown() {
        movieTimer2.textProperty().bind(
                new StringBinding() {
                    {
                        super.bind(mediaPlayer.currentTimeProperty());
                    }
                    // Makes the timer show in minutes and seconds, and count backwards.
                    @Override
                    protected String computeValue() {
                        int totalTime = (int) (mediaPlayer.getTotalDuration().toMillis() / 1000);
                        int currentTime = (int) (mediaPlayer.getCurrentTime().toMillis() / 1000);
                        int remainingTime = totalTime - currentTime;
                        int minutes = remainingTime / 60;
                        int seconds = remainingTime % 60;
                        String textSeconds;
                        if (seconds <= 9) {
                            textSeconds = "0" + seconds;
                        } else {
                            textSeconds = "" + seconds;
                        }
                        return minutes + ":" + textSeconds;
                    }
                });
    }
}