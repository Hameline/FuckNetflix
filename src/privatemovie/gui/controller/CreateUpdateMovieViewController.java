package privatemovie.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import privatemovie.be.CatMovie;
import privatemovie.be.Category;
import privatemovie.be.Movie;
import privatemovie.gui.model.CatMovieModel;
import privatemovie.gui.model.CategoryModel;
import privatemovie.gui.model.MovieModel;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class CreateUpdateMovieViewController extends BaseController implements Initializable {
    public ComboBox menuSelectedCategories;
    @FXML
    private TextField txtMovieName, txtIMBDScore, txtFilePath, txtPersonalRating, txtCategory;
    @FXML
    private Button btnUpdate, btnCreate;
    @FXML
    private ComboBox menuTotalCategories;
    @FXML
    private Button chooseFIle, btnCancel;
    private ObservableList<Category> allCategories = FXCollections.observableArrayList();
    private ObservableList<Category> selectedCategories = FXCollections.observableArrayList();
    private Movie selectedMovie = new Movie();
    private MovieModel movieModel;
    private Movie storedMovie = new Movie();
    private Category selectedCategory = new Category();
    private boolean updateCategories = false;
    private CatMovieModel catMovieModel = new CatMovieModel();
    private CategoryModel categoryModel;
    private MainViewController mainViewController;

    public CreateUpdateMovieViewController() throws Exception {
        try {
            movieModel = new MovieModel();
            categoryModel = new CategoryModel();
            catMovieModel = new CatMovieModel();
        } catch (Exception e) {
            displayError(e);
        }
    }

    public void setMovie(Movie movie) {
        selectedMovie = movie;
        txtMovieName.setText(movie.getName());
        txtIMBDScore.setText(String.valueOf(movie.getRating()));
        if (movie.getOwnrating() != 0) {
            txtPersonalRating.setText(String.valueOf(movie.getOwnrating()));
        }
    }

    // Initializing models
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            categoryModel = new CategoryModel();
            movieModel = new MovieModel();
            addToComboBox();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setup() {
        if (movieModel != null) {
            mainViewController.tbwMovie.setItems(movieModel.getListOfMovies());
        }
    }

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    // Here you can update the movies title imdb score and give it your own personal score.
    @FXML
    private void handleUpdate(ActionEvent actionEvent) throws Exception {
        if (updateCategories == true) {
            try {
                CatMovie c = new CatMovie(selectedMovie.getId());
                catMovieModel.removeMovieFromCategory(c);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            handleAddToCategories(selectedMovie);
        }
        try {
            selectedMovie.setName(txtMovieName.getText());
            selectedMovie.setRating(Integer.parseInt(txtIMBDScore.getText()));
            selectedMovie.setOwnrating(Integer.parseInt(txtPersonalRating.getText()));


            movieModel.updateMovie(selectedMovie);
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            btnUpdate.getScene().getWindow().hide();
        }
    }

    // This is where the movie gets created. Keep in mind that even though you get the option to fill in "personal rating"
    // it doesn't work since you cant rate a movie you haven't seen yet,you have to rate it in the update method.
    @FXML
    private void handleCreate(ActionEvent actionEvent) {
        int imdbRating = Integer.parseInt(txtIMBDScore.getText());
        Date localDate = Date.from(Instant.now());
        Movie movie = new Movie(-1, txtMovieName.getText(), imdbRating, txtFilePath.getText(), localDate);

        if (imdbRating > 0 && imdbRating <= 10) {
            try {
                movieModel.addMovie(movie);
            }
            catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            } finally {
                storedMovie = movie;
                handleAddToCategories(storedMovie);
                btnCreate.getScene().getWindow().hide();
            }
        } else {
            mainViewController.informationUser("Your rating value needs to be between 1-10");
        }
    }

    private void handleAddToCategories(Movie addMovieToCategory) {
        try {
            for (Category pCategory : selectedCategories){
                CatMovie catMovie = new CatMovie(addMovieToCategory.getId(), pCategory.getId(), addMovieToCategory.getName());
                catMovieModel.addMovieToCategory(catMovie);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    private void addToComboBox(){
        try {
            allCategories = categoryModel.showList();
            menuTotalCategories.setItems(allCategories);
            menuTotalCategories.getSelectionModel().selectFirst();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // File chooser to put in the movie file which needs to be mp4 or mpeg4
    @FXML
    private void handleChooseFile(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        File f = fc.showOpenDialog(stage);
        if (f.getPath().endsWith(".mp4") || f.getPath().endsWith("mpeg4")) {
            txtFilePath.setText(f.getPath());
        }
        else{
            mainViewController.informationUser("Your file needs to be in mp4 or mpeg4 format");
        }
    }

    @FXML
    private void handleCancel(ActionEvent actionEvent) {
        btnCancel.getScene().getWindow().hide();
    }

    @FXML
    private void handleAddCategory(KeyEvent keyEvent) {
        // Detects if the ENTER KEY have been PRESSED
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (!(txtCategory.getText().isEmpty())) {
                Category c = new Category();
                c.setCategories(txtCategory.getText().toString());
                allCategories.add(c);


                txtCategory.setText("");


                menuTotalCategories.setItems(allCategories);
            }
            // Looks TO SEE if the TXT SEARCH FIELD is EMPTY
            if (txtCategory.getText().isEmpty()) {
                // to ensure nothing happens if the Field is empty
            }
        }
    }

    public void handleSelectCategory(ActionEvent actionEvent) {
        updateCategories = true;

        selectedCategories.add((Category) menuTotalCategories.getSelectionModel().getSelectedItem());
        menuSelectedCategories.setItems(selectedCategories);
    }
}
