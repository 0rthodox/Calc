package calculator;

public enum Operation {
    EXTENSION('^'),
    DIVISION('/'),
    MULTIPLICATION('*'),
    SUBTRACTION('-'),
    ADDITION('+');

    private Character operation;

    Operation(Character operation) {
        this.operation = operation;
    }

    public Character getOperation() {
        return operation;
    }
}
