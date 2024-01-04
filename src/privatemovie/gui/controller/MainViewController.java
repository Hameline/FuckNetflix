package privatemovie.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import privatemovie.be.Movie;
import privatemovie.gui.model.MovieModel;

import java.io.IOException;
import java.util.List;

public class MainViewController {
    public TableColumn tbwMovieIMDBRating;
    public TableColumn tbwMovieTitle;
    public TableColumn tbwMoviePersonalRating;
    @FXML
    private Button btnDeleteMovie;
    @FXML
    private Button btnUpdateMovie;
    @FXML
    private Button btnUploadMovie;
    @FXML
    private TableView tbwCategory;
    @FXML
    private TableView tbwMovie;

    private MovieModel movieModel = new MovieModel();

    public MainViewController() throws Exception {
    }

    private void handleBtns() {
        btnDeleteMovie.setVisible(false);
        btnUpdateMovie.setVisible(false);

    }

    public void setup () {
        handleBtns();

        if (movieModel != null) {
            tbwMovie.setItems(movieModel.getListOfMovies());

        }

        tbwMovieTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbwMovieIMDBRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        tbwMoviePersonalRating.setCellValueFactory(new PropertyValueFactory<>("ownrating"));


    }

    @FXML
    private void handleDeleteMovie(ActionEvent actionEvent) {
        Movie selectedMovie = (Movie) tbwMovie.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            try {
                // Delete movie in DAL layer (through the layers)
                movieModel.deleteMovie(selectedMovie);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        }
        btnDeleteMovie.setVisible(false);
        btnUpdateMovie.setVisible(false);

        refreshTableViews();
    }

    @FXML
    private void handleUpdateMovie(ActionEvent actionEvent) {
        btnDeleteMovie.setVisible(false);
        btnUpdateMovie.setVisible(false);

    }

    @FXML
    private void handleUploadMovie(ActionEvent actionEvent) throws Exception {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreateUpdateMovie.fxml"));
        Parent popupWindow = loader.load();

        CreateUpdateMovieViewController controller = loader.getController();

        Stage PopupWindow = new Stage();
        PopupWindow.setTitle("Upload/Update Movie");
        PopupWindow.initModality(Modality.APPLICATION_MODAL);
        PopupWindow.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());

        PopupWindow.setScene(new Scene(popupWindow));
        PopupWindow.showAndWait();

        tbwMovie.setItems(movieModel.showList());
    }

    public void refreshTableViews() {
        tbwMovie.refresh();
        tbwCategory.refresh();
    }

    @FXML
    private void handleSelectedMovie(MouseEvent mouseEvent) {
        Movie selectedMovie = (Movie) tbwMovie.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            btnDeleteMovie.setVisible(true);
            btnUpdateMovie.setVisible(true);
        }
    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }
}
