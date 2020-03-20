import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class View extends VBox {

    private static final double INIT_BUTTON_WIDTH = 40;

    TextField field = new TextField();
    GridPane buttons = new GridPane();
    Model calculator = new Model();

    View() {
        final int initialWidth = 300;
        final int minimumButtonWidth = 40;
        field.setPrefWidth(initialWidth);
        field.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 22));
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            String testValue = newValue;
            if(newValue.startsWith("-")) {
                testValue = newValue.substring(1);
            }
            if (!testValue.matches("\\d*([\\.]\\d*)?")) {
                field.setText(oldValue);
            }
        });
        heightProperty().addListener(((observable, oldValue, newValue) -> {
            Double doubleValue = (Double)newValue;
            if (doubleValue > 220 && doubleValue % 12 == 0 && doubleValue < 2 * widthProperty().doubleValue()) {
                field.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, doubleValue / 10));
                for(Node node : buttons.getChildren()) {
                    ((Button)node).setPadding(Insets.EMPTY);
                    ((Button)node).setFont(new Font(doubleValue / 12));
                }
            }
        }));

        for(Integer i = 0; i <= 9; ++i) {
            buttons.add(createNumberButton(i), i % 3,i / 3 + 1);
        }

        Button plusMinusButton = new Button("+/-");
        plusMinusButton.setOnAction(event -> {
            if (!field.getText().isEmpty()) {
                if (field.getText(0, 1).equals("-")) {
                    field.setText(field.getText().substring(1));
                } else {
                    field.setText("-" + field.getText());
                }
            }
        });
        buttons.add(plusMinusButton, 1, 4);

        Button commaButton = new Button(",");
        commaButton.setOnAction(event -> {
            field.appendText(".");
        });
        buttons.add(commaButton, 2, 4);

        Button degreeButton = new Button("^");
        degreeButton.setOnAction(event -> {
            calculator.setOperand(Double.parseDouble(field.getText()));
            field.clear();
            calculator.setOperation(Operation.EXTENSION);
        });
        buttons.add(degreeButton, 0, 0);

        Button clearButton = new Button("Clc");
        clearButton.setOnAction(event -> {
            calculator.resetLast();
            field.clear();
        });
        buttons.add(clearButton, 0, 0);

        Button backspaceButton = new Button("<");
        backspaceButton.setOnAction(event -> {
            String text = field.getText();
            field.setText(text.substring(0, text.length() - 1));
        });
        buttons.add(backspaceButton, 1, 0);

        int i = 0;
        for(Operation operation : Operation.values()) {
            buttons.add(createOperationButton(operation), 3, i++);
        }

        Button equalsButton = new Button("=");
        equalsButton.setOnAction(event -> {
            calculator.setOperand(Double.parseDouble(field.getText()));
            field.setText(calculator.calculate().toString());
        });
        buttons.add(equalsButton, 2, 0);

        this.getChildren().addAll(field, buttons);
        for(Node node : buttons.getChildren()) {
            Button button = (Button)node;
            button.prefWidthProperty().bind(widthProperty());
            button.prefHeightProperty().bind(heightProperty());
            button.setMinWidth(minimumButtonWidth);
            button.setMinHeight(minimumButtonWidth);
            button.setFont(new Font(minimumButtonWidth * 0.5));
        }

    }

    private Button createNumberButton(Integer number) {
        Button numberButton = new Button(number.toString());
        numberButton.setOnAction(event -> {
            field.appendText(number.toString());
        });
        return numberButton;
    }

    private Button createOperationButton(Operation operation) {
        Button operationButton = new Button(Operation.getSymbol(operation).toString());
        operationButton.setOnAction(event -> {
            if (!field.getText().isEmpty()) {
                calculator.setOperand(Double.parseDouble(field.getText()));
                field.clear();
                calculator.setOperation(operation);
            }
        });
        return operationButton;
    }
}
