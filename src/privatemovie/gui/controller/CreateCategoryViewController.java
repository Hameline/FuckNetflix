package privatemovie.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import privatemovie.be.Category;
import privatemovie.gui.model.CategoryModel;

public class CreateCategoryViewController extends BaseController{
    public TextField txtCategoryName;
    public ComboBox menuTotalCategories;
    public Button btnUpdate;
    public Button btnCreate;
    public Button btnCancel;
    public Button btnDelete;

    private ObservableList<Category> categories = FXCollections.observableArrayList();
    private Category category = new Category();
    private Category selectedCategory = new Category();
    private CategoryModel categoryModel = new CategoryModel();

    public CreateCategoryViewController() throws Exception {
    }

    @Override
    public void setup() {

    }

    public void handleSelectedCategory(ActionEvent actionEvent) {
        selectedCategory = (Category) menuTotalCategories.getSelectionModel().getSelectedItem();
    }

    public void handleUpdate(ActionEvent actionEvent) {
    }

    public void handleCreate(ActionEvent actionEvent) {
        for (Category c : categories) {
            try {
                categoryModel.addCategory(c);
            }
            catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            } finally {

                btnCreate.getScene().getWindow().hide();
            }
        }
    }

    public void handleCancel(ActionEvent actionEvent) {
        btnCancel.getScene().getWindow().hide();
    }

    public void handleDelete(ActionEvent actionEvent) {
        categories.remove(selectedCategory);
    }

    public void handleAddAnotherCategory(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (!(txtCategoryName.getText().isEmpty())) {

                Category c = new Category();
                c.setCategories(txtCategoryName.getText().toString());
                categories.add(c);


                txtCategoryName.setText("");


                menuTotalCategories.setItems(categories);
            }
            // Looks TO SEE if the TXT SEARCH FIELD is EMPTY
            if (txtCategoryName.getText().isEmpty()) {
                // to ensure nothing happens if the Field is empty
            }
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
