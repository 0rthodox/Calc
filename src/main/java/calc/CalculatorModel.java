package main.java.calc;

import main.java.operation.Operation;
import main.java.operation.OperationException;

public class CalculatorModel {
    Double leftOperand;
    Double rightOperand;
    Operation operation;

    public void setOperand(String operand) {
        Double parsedOperand;
        if (operand == null || operand.isEmpty()) {
            parsedOperand = 0.;
        } else {
            parsedOperand = Double.parseDouble(operand);
        }
        if (leftOperand == null) {
            leftOperand = parsedOperand;
        } else {
            rightOperand = parsedOperand;
        }
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Number calculate() {
        Double result;
        if (leftOperand == null || rightOperand == null) {
            throw new OperationException("An operand is null", operation);
        }
        switch (operation) {
            case ADDITION: result = leftOperand + rightOperand; break;
            case SUBTRACTION: result = leftOperand - rightOperand; break;
            case DIVISION: result = leftOperand / rightOperand; break;
            case MULTIPLICATION: result = leftOperand * rightOperand; break;
            case EXTENSION: result = Math.pow(leftOperand, rightOperand); break;
            default:
                throw new OperationException("Wrong operation", operation);
        }
        leftOperand = null;
        rightOperand = null;
        operation = null;
        if (result.equals(Math.floor(result))) {
            return result.intValue();
        }
        return result;
    }

    public void resetLast() {
        if (rightOperand != null) {
            rightOperand = null;
        } else {
            leftOperand = null;
        }
    }
}
