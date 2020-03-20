import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.ImageManager;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(new View()));
        primaryStage.setTitle("Calc");
        primaryStage.getIcons().add(ImageManager.readImage("resources/x.png"));
        primaryStage.show();
    }
}
