package privatemovie.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import privatemovie.gui.model.CategoryModel;
import privatemovie.gui.model.MovieModel;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController extends BaseController implements Initializable {
    public TableColumn tbwMovieIMDBRating;
    public TableColumn tbwMovieTitle;
    public TableColumn tbwMoviePersonalRating;
    public Button btnCreateCategory;
    public TableColumn tbwCategoryTitle;
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
    private String errorText;
    private MovieModel movieModel;
    private CategoryModel categoryModel;

    public MainViewController() throws Exception {
        try {
            movieModel = new MovieModel();
            categoryModel = new CategoryModel();
        } catch (Exception e) {
            displayError(e);
        }
    }

    private void handleBtns() {
        btnDeleteMovie.setVisible(false);
        btnUpdateMovie.setVisible(false);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setup () throws Exception {
        handleBtns();

        if (movieModel != null) {
            tbwMovie.setItems(movieModel.getListOfMovies());
            tbwCategory.setItems(categoryModel.showList());

        }

        tbwCategoryTitle.setCellValueFactory(new PropertyValueFactory<>("category"));
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
    private void handleUpdateMovie(ActionEvent actionEvent) throws Exception {
        Movie selectedMovie = (Movie) tbwMovie.getSelectionModel().getSelectedItem();

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreateUpdateMovie.fxml"));
        Parent popupWindow = loader.load();

        CreateUpdateMovieViewController controller = loader.getController();
        controller.setMovie(selectedMovie);

        Stage PopupWindow = new Stage();
        PopupWindow.setTitle("Upload/Update Movie");
        PopupWindow.initModality(Modality.APPLICATION_MODAL);
        PopupWindow.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());

        PopupWindow.setScene(new Scene(popupWindow));
        PopupWindow.showAndWait();

        tbwMovie.setItems(movieModel.showList());

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

    private void displayError(Throwable t) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(errorText);
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    public void handleCreateCategory(ActionEvent actionEvent) throws Exception {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreateCategory.fxml"));
        Parent popupWindow = loader.load();

        CreateCategoryViewController controller = loader.getController();

        Stage PopupWindow = new Stage();
        PopupWindow.setTitle("Create Category");
        PopupWindow.initModality(Modality.APPLICATION_MODAL);
        PopupWindow.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());

        PopupWindow.setScene(new Scene(popupWindow));
        PopupWindow.showAndWait();

        tbwCategory.setItems(categoryModel.showList());
    }
}
