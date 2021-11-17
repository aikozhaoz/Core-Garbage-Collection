import java.util.*;

public class In {

    String line;

    String id;

    In() {
        line = "";
        id = "";
    }

    public void parse(Scanner S) {
        // <in> ::= input id ;
        if (!S.expectedToken(Core.INPUT)) {
            Utility.expectedhelper(Core.INPUT, S.currentToken());
            System.exit(-1);
        }
        if (S.currentToken() == Core.ID) {
            id = S.getID();
            S.nextToken();
        } else {
            Utility.expectedhelper(Core.ID, S.currentToken());
            System.exit(-1);
        }
        if (!S.expectedToken(Core.SEMICOLON)) {
            Utility.expectedhelper(Core.SEMICOLON, S.currentToken());
            System.exit(-1);
        }
    }

    public void semantic(Stack<Map<String, Core>> scopetrack) {
        // Check if the ID is being declared.
        boolean IDdeclared = false;
        String key = id;
        // Loop through the stack to see if the current ID is declared.
        for (Map<String, Core> currentscope : scopetrack) {
            // If the current ID is declared. Check if the declared type is right.
            if (currentscope.containsKey(key)) {
                IDdeclared = true;
            }
        }
        if (!IDdeclared) {
            System.out.println(key);
            Utility.UseUndeclaredIdError(id);
            System.exit(-1);
        }
    }

    public void execute(Scanner inputScanner) {
        String key = id;
        // Check if there's any available data left in the data file
        if (inputScanner.tokens.size() == 1) {
            Utility.InvalidInput();
            System.exit(-1);
        }
        // Call currentToken first to set inputScanner.current = tokens[0]
        inputScanner.currentToken();
        int num = inputScanner.getCONST();
        inputScanner.nextToken();
        Corevar val = new Corevar(Core.INT, num);
        HashMap<String, Corevar> currentscope = Memory.stackSpace.peek().peek();
        currentscope.put(key, val);
    }

    public void print(int indent) {
        for (int i = 0; i < indent; i++) {
            line += "  ";
        }
        System.out.println(line + "input " + id + ";");
    }
}
