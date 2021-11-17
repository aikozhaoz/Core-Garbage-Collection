import java.util.*;

public class Decl {

    int option;

    DeclInt declint;
    DeclClass declclass;

    Decl() {
        option = 0;
        declint = null;
        declclass = null;
    }

    public void parse(Scanner S) {
        // Option 1: <decl> ::= <decl-int>
        if (S.currentToken() == Core.INT) {
            option = 1;
            declint = new DeclInt();
            declint.parse(S);
        }
        // Option 2: <decl> ::= <decl-class>
        else if (S.currentToken() == Core.REF) {
            option = 2;
            declclass = new DeclClass();
            declclass.parse(S);
        }
        // So if the currentToken != id or ref, then syntax is invalid.
        else {
            Core[] expectedones = new Core[] { Core.REF, Core.ID };
            Utility.errorhelper(expectedones, S.currentToken());
            System.exit(-1);
        }
    }

    public void semantic(Stack<Map<String, Core>> scopetrack) {
        if (option == 1) {
            declint.semantic(scopetrack);
        } else if (option == 2) {
            declclass.semantic(scopetrack);
        }
    }

    public void execute() {
        if (option == 1) {
            declint.execute();
        } else if (option == 2) {
            declclass.execute();
        }
    }

    public void print(int indent) {
        if (option == 1) {
            declint.print(indent);
        } else if (option == 2) {
            declclass.print(indent);
        }
    }
}
