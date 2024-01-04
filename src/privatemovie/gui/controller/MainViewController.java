package privatemovie.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
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

    public void setup () {
        btnDeleteMovie.setVisible(false);
        btnUpdateMovie.setVisible(false);

        if (movieModel != null) {
            tbwMovie.setItems(movieModel.getListOfMovies());

        }

        tbwMovieTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbwMovieIMDBRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        tbwMoviePersonalRating.setCellValueFactory(new PropertyValueFactory<>("ownrating"));


    }

    @FXML
    private void handleDeleteMovie(ActionEvent actionEvent) {
    }

    @FXML
    private void handleUpdateMovie(ActionEvent actionEvent) {
    }

    @FXML
    private void handleUploadMovie(ActionEvent actionEvent) throws IOException {
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
    }
}
