import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(new View()));
        primaryStage.setTitle("Calc");
        primaryStage.show();
    }
}
