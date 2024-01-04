package privatemovie.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import privatemovie.be.Category;
import privatemovie.be.Movie;
import privatemovie.gui.model.MovieModel;

import java.util.ArrayList;
import java.util.List;

public class CreateUpdateMovieViewController {
    @FXML
    private TextField txtMovieName;
    @FXML
    private TextField txtIMBDScore;
    @FXML
    private TextField txtFilePath;
    @FXML
    private TextField txtPersonalRating;
    @FXML
    private TextField txtCategory;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnCreate;
    @FXML
    private ComboBox menuTotalCategories;
    @FXML
    private Button chooseFIle;
    @FXML
    private Button btnCancel;

    private ObservableList<Category> categories = FXCollections.observableArrayList();
    private MovieModel movieModel = new MovieModel();
    private Movie selectedMovie = new Movie();

    public CreateUpdateMovieViewController() throws Exception {
    }

    public void setMovie(Movie movie) {
        selectedMovie = movie;
        txtMovieName.setText(movie.getName());
        txtIMBDScore.setText(String.valueOf(movie.getRating()));
        if (movie.getOwnrating() != 0) {
            txtPersonalRating.setText(String.valueOf(movie.getOwnrating()));
        }

    }

    @FXML
    private void handleUpdate(ActionEvent actionEvent) {
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

    @FXML
    private void handleCreate(ActionEvent actionEvent) {
        int rating = Integer.parseInt(txtIMBDScore.getText());
        Movie movie = new Movie(-1, txtMovieName.getText(), rating);

        try {
            movieModel.addMovie(movie);
        }
        catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        } finally {

            btnCreate.getScene().getWindow().hide();
        }
    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }


    @FXML
    private void handleChooseFile(ActionEvent actionEvent) {
    }

    @FXML
    private void handleCancel(ActionEvent actionEvent) {
    }

    @FXML
    private void handleAddCategory(KeyEvent keyEvent) {
        // Detects if the ENTER KEY have been PRESSED
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (!(txtCategory.getText().isEmpty())) {
                Category c = new Category();
                c.setCategories(txtCategory.getText().toString());
                categories.add(c);


                txtCategory.setText("");


                menuTotalCategories.setItems(categories);
            }
            // Looks TO SEE if the TXT SEARCH FIELD is EMPTY
            if (txtCategory.getText().isEmpty()) {
                // to ensure nothing happens if the Field is empty
            }
        }
    }
}
