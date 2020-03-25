package calculator;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CalculatorView extends VBox {
    private static final int INITIAL_WIDTH = 300;
    private static final int MINIMUM_BUTTON_WIDTH = 40;
    private static final int INITIAL_FONT = 22;
    private static final int FONT_DELTA = 12;
    private static final int INPUT_FONT_DELTA = 10;
    private static final int MINIMUM_LAYOUT_WIDTH = 220;

    private TextField input = new TextField();
    private GridPane buttonsLayout = new GridPane();
    private CalculatorViewModel calculatorViewModel = new CalculatorViewModel();

    CalculatorView() {
        initView();
        initButtons();
    }

    private void initView() {
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

        // Resizing window
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
                calculatorViewModel.setOperand(input.getText());
                input.clear();
                calculatorViewModel.setOperation(operation);
            }
        });
        return operationButton;
    }

    private void signPressed() {
        if (!input.getText().isEmpty()) {
            if (input.getText().contains("-")) {
                input.setText(input.getText().substring(1));
            } else {
                input.setText("-" + input.getText());
            }
        }
    }

    private void backspacePressed() {
        String text = input.getText();
        input.setText(text.substring(0, text.length() - 1));
    }

    private void clearPressed() {
        calculatorViewModel.resetLastSavedOperand();
        input.clear();
    }

    private void degreePressed() {
        if (!input.getText().isEmpty()) {
            calculatorViewModel.setOperand(input.getText());
            input.clear();
            calculatorViewModel.setOperation(Operation.EXTENSION);
        }
    }

    private void equalsPressed() {
        calculatorViewModel.setOperand(input.getText());
        input.setText(calculatorViewModel.calculate().toString());
    }

    //Setting buttons

    private void initButtons() {
        for(Integer i = 0; i <= 9; ++i) {
            buttonsLayout.add(createNumberButton(i), i % 3,i / 3 + 1);
        }

        Button signButton = new Button("+/-");
        signButton.setOnAction(event -> signPressed());
        buttonsLayout.add(signButton, 1, 4);

        Button commaButton = new Button(",");
        commaButton.setOnAction(event -> input.appendText("."));
        buttonsLayout.add(commaButton, 2, 4);

        Button degreeButton = new Button("^");
        degreeButton.setOnAction(event -> degreePressed());
        buttonsLayout.add(degreeButton, 0, 0);

        Button clearButton = new Button("Clc");
        clearButton.setOnAction(event -> clearPressed());
        buttonsLayout.add(clearButton, 0, 0);

        Button backspaceButton = new Button("<");
        backspaceButton.setOnAction(event -> backspacePressed());
        buttonsLayout.add(backspaceButton, 1, 0);

        int i = 0;
        for(Operation operation : Operation.values()) {
            buttonsLayout.add(createOperationButton(operation), 3, i++);
        }

        Button equalsButton = new Button("=");
        equalsButton.setOnAction(event -> equalsPressed());
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
