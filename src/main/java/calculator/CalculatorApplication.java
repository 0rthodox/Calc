package calculator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.ImageManager;

public class CalculatorApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(new CalculatorView()));
        primaryStage.setTitle("Calc");
        primaryStage.getIcons().add(ImageManager.readImage("src/main/resources/x.png"));
        primaryStage.show();
    }
}
