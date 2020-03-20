import java.util.HashMap;
import java.util.Map;

public enum Operation {
    EXTENSION,
    DIVISION,
    MULTIPLICATION,
    SUBTRACTION,
    ADDITION;

    private static Map<Operation, Character> characterMap = new HashMap<Operation, Character>() {{
        put(EXTENSION, '^');
        put(DIVISION, '÷');
        put(MULTIPLICATION, '×');
        put(SUBTRACTION, '-');
        put(ADDITION, '+');
    }};


    public static Character getSymbol(Operation operation) {
        return characterMap.get(operation);

    }
}
