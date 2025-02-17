import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Starter extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/dash_form.fxml"))));
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/place_order_form.fxml"))));
        stage.show();
    }
}
