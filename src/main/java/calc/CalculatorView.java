package main.java.calc;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.java.operation.Operation;

public class CalculatorView extends VBox {

    private TextField input = new TextField();
    private GridPane buttonsLayout = new GridPane();
    private CalculatorModel calculator = new CalculatorModel();
    private static final int INITIAL_WIDTH = 300;
    private static final int MINIMUM_BUTTON_WIDTH = 40;
    private static final int INITIAL_FONT = 22;
    private static final int FONT_DELTA = 12;
    private static final int INPUT_FONT_DELTA = 10;
    private static final int MINIMUM_LAYOUT_WIDTH = 220;

    CalculatorView() {
        setFrontEnd();
        setButtons();
    }

    private void setFrontEnd() {
        input.setPrefWidth(INITIAL_WIDTH);
        input.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, INITIAL_FONT));
        input.textProperty().addListener((observable, oldValue, newValue) -> {
            String testValue = newValue;
            if(newValue.startsWith("-")) {
                testValue = newValue.substring(1);
            }
            if (!testValue.matches("\\d*([\\.]\\d*)?")) {
                input.setText(oldValue);
            }
        });
        heightProperty().addListener(((observable, oldValue, newValue) -> {
            Double doubleValue = (Double)newValue;
            if (doubleValue > MINIMUM_LAYOUT_WIDTH && doubleValue % FONT_DELTA == 0 && doubleValue < 2 * widthProperty().doubleValue()) {
                input.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, doubleValue / INPUT_FONT_DELTA));
                for(Node node : buttonsLayout.getChildren()) {
                    ((Button)node).setPadding(Insets.EMPTY);
                    ((Button)node).setFont(new Font(doubleValue / FONT_DELTA));
                }
            }
        }));
    }

    //Buttons logic

    private Button createNumberButton(Integer number) {
        Button numberButton = new Button(number.toString());
        numberButton.setOnAction(event -> {
            input.appendText(number.toString());
        });
        return numberButton;
    }

    private Button createOperationButton(Operation operation) {
        Button operationButton = new Button(operation.getOperation().toString());
        operationButton.setOnAction(event -> {
            if (!input.getText().isEmpty()) {
                calculator.setOperand(input.getText());
                input.clear();
                calculator.setOperation(operation);
            }
        });
        return operationButton;
    }

    private void signButtonLogic() {
        if (!input.getText().isEmpty()) {
            if (input.getText(0, 1).equals("-")) {
                input.setText(input.getText().substring(1));
            } else {
                input.setText("-" + input.getText());
            }
        }
    }

    private void backspaceButtonLogic() {
        String text = input.getText();
        input.setText(text.substring(0, text.length() - 1));
    }

    private void clearButtonLogic() {
        calculator.resetLast();
        input.clear();
    }

    private void degreeButtonLogic() {
        calculator.setOperand(input.getText());
        input.clear();
        calculator.setOperation(Operation.EXTENSION);
    }

    private void equalsButtonLogic() {
        calculator.setOperand(input.getText());
        input.setText(calculator.calculate().toString());
    }

    //Setting buttons

    private void setButtons() {
        for(Integer i = 0; i <= 9; ++i) {
            buttonsLayout.add(createNumberButton(i), i % 3,i / 3 + 1);
        }

        Button signButton = new Button("+/-");
        signButton.setOnAction(event -> signButtonLogic());
        buttonsLayout.add(signButton, 1, 4);

        Button commaButton = new Button(",");
        commaButton.setOnAction(event -> input.appendText("."));
        buttonsLayout.add(commaButton, 2, 4);

        Button degreeButton = new Button("^");
        degreeButton.setOnAction(event -> degreeButtonLogic());
        buttonsLayout.add(degreeButton, 0, 0);

        Button clearButton = new Button("Clc");
        clearButton.setOnAction(event -> clearButtonLogic());
        buttonsLayout.add(clearButton, 0, 0);

        Button backspaceButton = new Button("<");
        backspaceButton.setOnAction(event -> backspaceButtonLogic());
        buttonsLayout.add(backspaceButton, 1, 0);

        int i = 0;
        for(Operation operation : Operation.values()) {
            buttonsLayout.add(createOperationButton(operation), 3, i++);
        }

        Button equalsButton = new Button("=");
        equalsButton.setOnAction(event -> equalsButtonLogic());
        buttonsLayout.add(equalsButton, 2, 0);

        this.getChildren().addAll(input, buttonsLayout);
        for(Node node : buttonsLayout.getChildren()) {
            Button button = (Button)node;
            button.prefWidthProperty().bind(widthProperty());
            button.prefHeightProperty().bind(heightProperty());
            button.setMinWidth(MINIMUM_BUTTON_WIDTH);
            button.setMinHeight(MINIMUM_BUTTON_WIDTH);
            button.setFont(new Font(MINIMUM_BUTTON_WIDTH * 0.5));
        }
    }
}
