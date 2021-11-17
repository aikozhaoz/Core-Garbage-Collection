import java.util.*;

public class Cmpr {

    int option;

    Expr exprone;
    Expr exprtwo;

    Cmpr() {
        option = 0;
        exprone = new Expr();
        exprtwo = new Expr();
    }

    public void parse(Scanner S) {
        // <cmpr> ::= <expr> == <expr> | <expr> < <expr> | <expr> <= <expr>
        // Regardless of which option we are on.
        // The first tokens is <expr>
        exprone.parse(S);
        // Option 1: <cmpr> ::= <expr> == <expr>
        if (S.currentToken() == Core.EQUAL) {
            option = 1;
            S.expectedToken(Core.EQUAL);
            exprtwo.parse(S);
        }
        // Option 2: <cmpr> ::= <expr> < <expr>
        else if (S.currentToken() == Core.LESS) {
            option = 2;
            S.expectedToken(Core.LESS);
            exprtwo.parse(S);
        }
        // Option 3: <cmpr> ::= <expr> <= <expr>
        else if (S.currentToken() == Core.LESSEQUAL) {
            option = 3;
            S.expectedToken(Core.LESSEQUAL);
            exprtwo.parse(S);
        }
        // So if the currentToken != ""== or < or <="", then syntax is invalid.
        else {
            Core[] expectedones = new Core[] { Core.EQUAL, Core.LESS, Core.LESSEQUAL };
            Utility.errorhelper(expectedones, S.currentToken());
            System.exit(-1);
        }
    }

    public void semantic(Stack<Map<String, Core>> scopetrack) {
        exprone.semantic(scopetrack);
        exprtwo.semantic(scopetrack);
    }

    public boolean execute() {
        boolean condition = true;
        int exprOne = exprone.execute();
        int exprTwo = exprtwo.execute();
        if (option == 1) {
            condition = (exprOne == exprTwo);
        } else if (option == 2) {
            condition = (exprOne < exprTwo);
        } else if (option == 3) {
            condition = (exprOne <= exprTwo);
        }
        return condition;
    }

    public void print(int indent) {
        exprone.print(indent);
        if (option == 1) {
            System.out.print("==");
        } else if (option == 2) {
            System.out.print("<");
        } else if (option == 3) {
            System.out.print("<=");
        }
        exprtwo.print(indent);
    }

}
