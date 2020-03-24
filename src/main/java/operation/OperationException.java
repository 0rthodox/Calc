package main.java.operation;

public class OperationException extends RuntimeException {
    private Operation currentOperation;

    public OperationException(String message, Operation causingOperation) {
        super(message);
        this.currentOperation = causingOperation;
    }
}
