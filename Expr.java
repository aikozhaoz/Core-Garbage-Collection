import java.util.*;

public class Expr {

    int option;

    Term term;
    Expr expr;

    Expr() {
        option = 0;
        term = null;
        expr = null;
    }

    public void parse(Scanner S) {
        // <expr> ::= <term> | <term> + <expr> | <term> – <expr>
        // Regardless of which option we are on.
        // The first tokens is <term>

        // Option 1: <expr> ::= <term>
        option = 1;
        term = new Term();
        term.parse(S);
        // Option 2: <expr> ::= <term> + <expr>
        if (S.currentToken() == Core.ADD) {
            option = 2;
            S.expectedToken(Core.ADD);
            expr = new Expr();
            expr.parse(S);
        }
        // Option 3: <expr> ::= <term> – <expr>
        else if (S.currentToken() == Core.SUB) {
            option = 3;
            S.expectedToken(Core.SUB);
            expr = new Expr();
            expr.parse(S);
        }
    }

    public void semantic(Stack<Map<String, Core>> scopetrack) {
        term.semantic(scopetrack);
        if (option == 2 || option == 3) {
            expr.semantic(scopetrack);
        }
    }

    public int execute() {
        int result = Integer.MIN_VALUE;
        int termnum = term.execute();

        if (option == 1) {
            result = termnum;
        } else {
            int exprnum = expr.execute();
            if (option == 2) {
                result = termnum + exprnum;
            } else if (option == 3) {
                result = termnum - exprnum;
            }
        }
        return result;
    }

    public void print(int indent) {
        term.print(indent);
        if (option == 2) {
            System.out.print("+");
            expr.print(indent);
        } else if (option == 3) {
            System.out.print("-");
            expr.print(indent);
        }
    }
}
