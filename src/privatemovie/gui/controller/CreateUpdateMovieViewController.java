package privatemovie.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import privatemovie.be.Category;

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

    @FXML
    private void handleUpdate(ActionEvent actionEvent) {
    }

    @FXML
    private void handleCreate(ActionEvent actionEvent) {
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
