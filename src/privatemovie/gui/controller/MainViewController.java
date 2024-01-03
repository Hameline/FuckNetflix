package privatemovie.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class MainViewController {
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

    public void setup () {
        btnDeleteMovie.setVisible(false);
        btnUpdateMovie.setVisible(false);
    }

    @FXML
    private void handleDeleteMovie(ActionEvent actionEvent) {
    }

    @FXML
    private void handleUpdateMovie(ActionEvent actionEvent) {
    }

    @FXML
    private void handleUploadMovie(ActionEvent actionEvent) {
    }
}
