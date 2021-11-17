import java.util.*;

public class IdList {

    int option;
    String id;
    IdList idlist;
    String line;

    IdList() {
        option = 0;
        id = "";
        line = "";
        idlist = null;
    }

    public void parse(Scanner S) {
        // Option 1: <id-list> ::= id
        if (S.currentToken() == Core.ID) {
            option = 1;
            id = S.getID();
            S.nextToken();
        } else {
            Utility.expectedhelper(Core.ID, S.currentToken());
            System.exit(-1);
        }
        // Option 2: <id-list> ::= id , <id-list>
        if (S.currentToken() == Core.COMMA) {
            option = 2;
            S.expectedToken(Core.COMMA);
            idlist = new IdList();
            idlist.parse(S);
        }
    }

    public void semantic(Stack<Map<String, Core>> scopetrack, Core intOrRef) {
        // Pop off the current scope from the stack
        Map<String, Core> currentscope = scopetrack.pop();
        String key = id;

        // Check if the id is already declared within the current scope.
        // If so output error messages
        if (currentscope.containsKey(key)) {
            Utility.DoubleDeclarationError(key);
            System.exit(-1);
        }
        // If not, add <ID, intOrRef> to currentscope
        // Put currentscope back to stack
        else {
            currentscope.put(key, intOrRef);
            scopetrack.add(currentscope);
        }
        if (option == 2) {
            idlist.semantic(scopetrack, intOrRef);
        }
    }

    public void execute(Core intOrRef) {
        String key = id;
        Corevar val = new Corevar();
        if (intOrRef == Core.INT) {
            val.setCorevar(intOrRef, 0);
        } else if (intOrRef == Core.REF) {
            val.setCorevar(intOrRef, null);
        }
        // Check if we are currently in globalSpace
        // If so we are only going to add Core, <String, Corevar> pair to corresponding
        // globalSpace
        // Otherwise, add to stackSpace's top stack's hashmap.
        HashMap<String, Corevar> correspondingMap = new HashMap<String, Corevar>();
        if (Memory.inGlobal) {
            correspondingMap = Memory.globalSpace;
        } else {
            correspondingMap = Memory.stackSpace.peek().peek();
        }

        if (!correspondingMap.containsKey(key)) {
            correspondingMap.put(key, val);
        } else {
            Utility.DoubleDeclarationError(key);
            System.exit(-1);
        }

        if (option == 2) {
            idlist.execute(intOrRef);
        }
    }

    public void print(int indent) {
        System.out.print(line + id);
        if (option == 2) {
            System.out.print(",");
            idlist.print(indent);
        }
    }
}