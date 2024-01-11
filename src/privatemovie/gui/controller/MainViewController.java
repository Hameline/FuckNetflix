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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import privatemovie.be.CatMovie;
import privatemovie.be.Category;
import privatemovie.be.Movie;
import privatemovie.bll.CatMovieManager;
import privatemovie.dal.ICategoryDataAccess;
import privatemovie.gui.model.CatMovieModel;
import privatemovie.gui.model.CategoryModel;
import privatemovie.gui.model.MovieModel;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainViewController extends BaseController implements Initializable {
    public TableColumn tbwMovieIMDBRating;
    public TableColumn tbwMovieTitle;
    public TableColumn tbwMoviePersonalRating;
    public TableColumn tbwCategoryTitle;
    public TextField txtFieldSearch;
    public ComboBox menuSearchCategories;
    @FXML
    private Button btnDeleteMovie, btnUpdateMovie, btnCreateCategory;
    private String errorText;
    @FXML
    private TableView tbwCategory;
    @FXML
    private TableView tbwMovie;
    private MovieModel movieModel;
    private CategoryModel categoryModel;
    private Movie storeMovie = new Movie();
    private Category storeCategory = new Category();
    private CatMovieManager catMovieManager= new CatMovieManager();
    private CatMovieModel catMovieModel;
    private boolean deleteCategory = false;
    private ObservableList<CatMovie> searchedCategoriesList = FXCollections.observableArrayList();

    public MainViewController() throws Exception {
        try {
            movieModel = new MovieModel();
            categoryModel = new CategoryModel();
            catMovieModel = new CatMovieModel();
        } catch (Exception e) {
            displayError(e);
        }
    }

    private void handleBtns() {
        btnDeleteMovie.setDisable(true);
        btnUpdateMovie.setDisable(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tbwCategory.setItems(categoryModel.getObservablePlaylist());
    }

    public void setup () throws Exception {
        //handleBtns();
        if (movieModel != null) {
            tbwMovie.setItems(movieModel.getListOfMovies());
            tbwCategory.setItems(categoryModel.showList());
        }
        tbwCategory.setItems(categoryModel.getObservablePlaylist());

        tbwCategoryTitle.setCellValueFactory(new PropertyValueFactory<>("category"));
        tbwMovieTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbwMovieIMDBRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        tbwMoviePersonalRating.setCellValueFactory(new PropertyValueFactory<>("ownrating"));


        menuSearchCategories.setItems(categoryModel.showList());
    }

    @FXML
    private void handleDeleteMovie(ActionEvent actionEvent) throws Exception {
        if (storeMovie != null) {
            try {
                // Delete movie in DAL layer (through the layers)
                movieModel.deleteMovie(storeMovie);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        }
        btnDeleteMovie.setDisable(false);
        //btnUpdateMovie.setVisible(false);

       tbwMovie.setItems(movieModel.showList());
    }

    @FXML
    private void handleUpdateMovie(ActionEvent actionEvent) throws Exception {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreateUpdateMovie.fxml"));
        Parent popupWindow = loader.load();

        CreateUpdateMovieViewController controller = loader.getController();
        controller.setMovie(storeMovie);

        Stage PopupWindow = new Stage();
        PopupWindow.setTitle("Upload/Update Movie");
        PopupWindow.initModality(Modality.APPLICATION_MODAL);
        PopupWindow.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());

        PopupWindow.setScene(new Scene(popupWindow));
        PopupWindow.showAndWait();

        tbwMovie.setItems(movieModel.showList());
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
        storeMovie = selectedMovie;
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

    public void handleUnCatMovies(ActionEvent actionEvent) {
        if (movieModel != null) {
            tbwMovie.setItems(movieModel.getListOfMovies());

        }
        tbwMovieTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbwMovieIMDBRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        tbwMoviePersonalRating.setCellValueFactory(new PropertyValueFactory<>("ownrating"));
    }

    public void handleSelectedCategory(MouseEvent mouseEvent) throws Exception {
        storeCategory = (Category) tbwCategory.getSelectionModel().getSelectedItem();
        if (storeCategory != null) {
            int categoryId = storeCategory.getId(); //get the id of the selected playlist.
            try {
                // here we fetch the songs from the database that is connected the the playlist with the id.
                List<Movie> movies = catMovieManager.getAllMoviesFromCategory(categoryId);
                ObservableList<Movie> movieObservableList = FXCollections.observableArrayList(movies);
                tbwMovie.setItems(movieObservableList); // set the items(songs) in the the view.
                tbwMovie.refresh();
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
            tbwMovie.setItems(catMovieManager.getAllMoviesFromCategory(storeCategory.getId()));
            tbwMovieTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
            tbwMovieIMDBRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
            tbwMoviePersonalRating.setCellValueFactory(new PropertyValueFactory<>("ownrating"));
            tbwMovie.refresh();

            btnCreateCategory.setText("Delete Category");
            deleteCategory = true;
        } else {
            setup();
        }
    }

    public void deselectMovie(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case ESCAPE:
                tbwMovie.getSelectionModel().clearSelection();
                break;
        }
    }

    public void confirmationAlertCategory() throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("You are about to delete Category");
        alert.setContentText("Are you sure you want to delete?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            categoryModel.deleteCategory(storeCategory);
            deleteCategory = false;
            btnCreateCategory.setText("Create Category");

        } else {

        }
    }

    public void handleCreateCategory(ActionEvent actionEvent) throws Exception {
        if (deleteCategory == false) {
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
        if (deleteCategory == true) {
            confirmationAlertCategory();
            tbwCategory.setItems(categoryModel.showList());
        }
    }

    public void handleSearch(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (!(txtFieldSearch.getText().isEmpty())) {

            }
            // Looks TO SEE if the TXT SEARCH FIELD is EMPTY
            if (txtFieldSearch.getText().isEmpty()) {
                // to ensure nothing happens if the Field is empty
            }
        }
    }

    public void handleSearchCategories(ActionEvent actionEvent) throws Exception {
        CatMovie catMovie = new CatMovie();
        Category category = (Category) menuSearchCategories.getSelectionModel().getSelectedItem();
        catMovie.setCategoryID(category.getId());
        searchedCategoriesList.add(catMovie);
    }
}