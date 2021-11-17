import java.util.*;

public class Cond {

    int option;
    String line;

    Cond cond;
    Cmpr cmpr;

    Cond() {
        option = 0;
        line = "";
        cmpr = null;
        cond = null;
    }

    public void parse(Scanner S) {
        // <cond> ::= <cmpr> | ! ( <cond> ) | <cmpr> or <cond>
        // Option 1: <cond> ::= ! ( <cond> )
        if (S.currentToken() == Core.NEGATION) {
            option = 1;
            S.expectedToken(Core.NEGATION);
            if (!S.expectedToken(Core.LPAREN)) {
                Utility.expectedhelper(Core.LPAREN, S.currentToken());
                System.exit(-1);
            }
            cond = new Cond();
            cond.parse(S);
            if (!S.expectedToken(Core.RPAREN)) {
                Utility.expectedhelper(Core.RPAREN, S.currentToken());
                System.exit(-1);
            }
        }
        // Option 2: <cond> ::= <cmpr>
        // <cmpr> ::= <expr> == <expr> | <expr> < <expr> | <expr> <= <expr>
        // <expr> ::= <term> | <term> + <expr> | <term> – <expr>
        // <term> ::= <factor> | <factor> * <term>
        // <factor> ::= id | const | ( <expr> )
        else if (S.currentToken() == Core.ID || S.currentToken() == Core.CONST || S.currentToken() == Core.LPAREN) {
            option = 2;
            cmpr = new Cmpr();
            cmpr.parse(S);
            // Option 3: <cmpr> or <cond>
            if (S.currentToken() == Core.OR) {
                option = 3;
                S.expectedToken(Core.OR);
                cond = new Cond();
                cond.parse(S);
            }
        }
        // So if the currentToken != "! or id or const or ("", then syntax is invalid.
        else {
            Core[] expectedones = new Core[] { Core.NEGATION, Core.ID, Core.CONST, Core.LPAREN };
            Utility.errorhelper(expectedones, S.currentToken());
            System.exit(-1);
        }
    }

    public void semantic(Stack<Map<String, Core>> scopetrack) {
        if (option == 1) {
            cond.semantic(scopetrack);
        } else if (option == 2) {
            cmpr.semantic(scopetrack);
        } else if (option == 3) {
            cmpr.semantic(scopetrack);
            cond.semantic(scopetrack);
        }
    }

    public boolean execute() {
        boolean condition = true;
        if (option == 2) {
            // System.out.println("secondwhileloop condition!!!");
            condition = cmpr.execute();
        } else if (option == 1) {
            condition = !(cond.execute());
        } else if (option == 3) {
            condition = (cmpr.execute()) || (cond.execute());
        }
        return condition;
    }

    public void print(int indent) {
        if (option == 1) {
            System.out.print("!(");
            cond.print(indent);
            System.out.print(")");
        } else if (option == 2) {
            cmpr.print(indent);
        } else if (option == 3) {
            cmpr.print(indent);
            System.out.print(" or ");
            cond.print(indent);
        }
    }

}
