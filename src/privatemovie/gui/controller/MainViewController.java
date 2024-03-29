package privatemovie.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import privatemovie.be.CatMovie;
import privatemovie.be.Category;
import privatemovie.be.Movie;
import privatemovie.bll.CatMovieManager;
import privatemovie.gui.model.CatMovieModel;
import privatemovie.gui.model.CategoryModel;
import privatemovie.gui.model.MovieModel;

import java.net.URL;
import java.util.*;

public class MainViewController extends BaseController implements Initializable {
    @FXML
    private TableColumn tbwMovieIMDBRating;
    @FXML
    private TableColumn tbwMovieTitle;
    @FXML
    private TableColumn tbwMoviePersonalRating;
    @FXML
    private TableColumn tbwCategoryTitle;
    @FXML
    private ComboBox menuSearchCategories;
    @FXML
    private TextField txtMinimunRating;
    @FXML
    private TextField txtFieldSearch;
    @FXML
    private Button btnDeleteMovie, btnUpdateMovie, btnCreateCategory;
    private String errorText;
    @FXML
    private TableView tbwCategory;
    @FXML
    protected TableView tbwMovie;
    private MovieModel movieModel;
    private CategoryModel categoryModel;
    private Movie storeMovie = new Movie();
    private Category storeCategory = new Category();
    private CatMovieManager catMovieManager= new CatMovieManager();
    private CatMovieModel catMovieModel;
    private boolean deleteCategory = false;
    private ObservableList<CatMovie> searchedCategoriesList = FXCollections.observableArrayList();
    private ObservableList<Category> searchCategoriesList = FXCollections.observableArrayList();
    private ObservableList<Movie> movieObservableList = FXCollections.observableArrayList();

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
        if (movieModel == null) {
            try {
                movieModel = new MovieModel();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        tbwCategory.setItems(categoryModel.getListOfCategories());
    }

    // Sets up a bunch of lists, cell values and some methods that gets called. There are also a listener where you
    // double-click a movie to open media view
    public void setup () throws Exception {
        //handleBtns();
        if (movieModel != null) {
            tbwMovie.setItems(movieModel.getListOfMovies());
            tbwCategory.setItems(categoryModel.showList());
        }
        tbwCategory.setItems(categoryModel.getListOfCategories());

        tbwCategoryTitle.setCellValueFactory(new PropertyValueFactory<>("category"));
        tbwMovieTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbwMovieIMDBRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        tbwMoviePersonalRating.setCellValueFactory(new PropertyValueFactory<>("ownrating"));

        menuSearchCategories.setItems(categoryModel.showList());
        resetCreateCategory();

        tbwMovie.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 1 && tbwMovie.getSelectionModel().getSelectedItem() != null) {
                try {
                    storeMovie = (Movie) tbwMovie.getSelectionModel().getSelectedItem();
                    System.out.println(storeMovie.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (mouseEvent.getClickCount() == 2 && tbwMovie.getSelectionModel().getSelectedItem() != null) {
                try {
                    movieModel.updateMovieDate((Movie) tbwMovie.getSelectionModel().getSelectedItem());
                    openMediaView(mouseEvent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        handleCheckOldMovies();
   }

   // Checks for movies that haven't been opened in 2 years with a rating less than 6
   private void handleCheckOldMovies() {
       if (!movieModel.shouldDeleteOldMovies().isEmpty()) {
           try {
               confirmationAlertDeleteMovies();
           } catch (Exception e) {
               throw new RuntimeException(e);
           }
       }
   }

   // Deletes a selected movie
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
        resetCreateCategory();
    }

    // Opens our upload/update movie window
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

    // Opens our upload/update movie window
    @FXML
    private void handleUploadMovie(ActionEvent actionEvent) throws Exception {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreateUpdateMovie.fxml"));
        Parent popupWindow = loader.load();

        CreateUpdateMovieViewController controller = loader.getController();

        CreateUpdateMovieViewController createUpdateMovieViewController = loader.getController();
        createUpdateMovieViewController.setMainViewController(this);

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

    protected void displayError(Throwable t) {
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

        resetCreateCategory();
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

                txtFieldSearch.setText(" ");
                searchedCategoriesList.removeAll();
                searchCategoriesList.removeAll();
                tbwCategory.setItems(categoryModel.showList());
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
            searchCategoriesList.clear();
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

    // Alert box for deleting of category
    public void confirmationAlertCategory() throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("You are about to delete Category");
        alert.setContentText("Are you sure you want to delete?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            categoryModel.deleteCategory(storeCategory);
            resetCreateCategory();

        } else {

        }
    }

    // Alert box for deleting of movie
    public void confirmationAlertDeleteMovies() throws Exception {
        System.out.println(movieModel.shouldDeleteOldMovies());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("You are about to delete old Movies with less than 6 Personal Rating");
        alert.setContentText("Are you sure you want to delete?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            for (Movie m : movieModel.shouldDeleteOldMovies()) {
                movieModel.deleteMovie(m);
            }

        } else {

        }
    }

    private void resetCreateCategory() {
        deleteCategory = false;
        btnCreateCategory.setText("Create Category");
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

    public void handleSearch(KeyEvent keyEvent) throws Exception {
        if (!(txtFieldSearch.getText().isEmpty())) {
            String search = txtFieldSearch.getText().toLowerCase();
            int minimunRating = Integer.parseInt(txtMinimunRating.getText());
            if (minimunRating != 0) {
                movieModel.setMinimunRating(minimunRating);
            }

            tbwMovie.setItems(movieModel.searchedMovie(search));
            tbwCategory.setItems(searchCategoriesList);


        }
        if (txtFieldSearch.getText().isEmpty()) {
            tbwMovie.setItems(movieModel.getListOfMovies());
            tbwCategory.setItems(categoryModel.getListOfCategories());
        }

        Movie selectedMovie = (Movie) tbwMovie.getSelectionModel().getSelectedItem();
        storeMovie = selectedMovie;
        resetCreateCategory();
    }

    public void handleSearchCategories(ActionEvent actionEvent) throws Exception {
        /*
        CatMovie catMovie = new CatMovie();
        Category category = (Category) menuSearchCategories.getSelectionModel().getSelectedItem();
        catMovie.setCategoryID(category.getId());
        searchedCategoriesList.add(catMovie);

         */
        Category category = (Category) menuSearchCategories.getSelectionModel().getSelectedItem();
        searchCategoriesList.add(category);
        tbwCategory.setItems(searchCategoriesList);

        Set<Movie> movieSet = new HashSet<>(); // Stores all the movies so that we won't get duplicate Movies

        for (Category c : searchCategoriesList) {
            if (c != null) {
                List<Movie> movies = catMovieManager.getAllMoviesFromCategory(c.getId());
                movieSet.addAll(movies);
            }
        }

        ObservableList<Movie> movieObservableList = FXCollections.observableArrayList();
        movieObservableList.addAll(movieSet);


        movieModel.setListOfMovies(movieObservableList);
        tbwMovie.setItems(movieObservableList);

        resetCreateCategory();

    }

    // Open the media viewer where we can see our uploaded movies
    public void openMediaView(MouseEvent mouseEvent) throws Exception{
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MediaView.fxml"));
        Parent mediaViewRoot = loader.load();

        MediaViewController controller = loader.getController();

        MediaViewController mediaViewController = loader.getController();
        mediaViewController.setMainViewController(this);

        Stage PopupWindow = new Stage();
        PopupWindow.setTitle("Media View");
        PopupWindow.initModality(Modality.APPLICATION_MODAL);
        PopupWindow.initOwner(((Node) mouseEvent.getSource()).getScene().getWindow());

        PopupWindow.setScene(new Scene(mediaViewRoot));
        PopupWindow.showAndWait();
    }

    // Alert box that get used when we either put in the wrong file or the wrong rating
    public void informationUser(String information){
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Regarding movie");
        info.setHeaderText(information + "");
        info.showAndWait();
    }
}