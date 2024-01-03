import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import privatemovie.gui.controller.MainViewController;

public class Main extends Application {
    public static void main(String[] args)
    {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/MainWindow.fxml"));
        Parent root = loader.load();
        MainViewController controller = loader.getController();
        controller.setup();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Private Movie Collection");
        primaryStage.show();
    }
}