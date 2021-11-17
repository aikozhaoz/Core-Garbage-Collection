import java.util.*;

public class Funcdecl {

    String id;
    Formals formals;
    StmtSeq stmtseq;
    ArrayList<String> formalParameters;

    Funcdecl() {
        id = "";
        formals = null;
        stmtseq = null;
        formalParameters = new ArrayList<String>();
    }

    public void parse(Scanner S) {
        // <func-decl> ::= id ( ref <formals> ) begin <stmt-seq> endfunc
        if (S.currentToken() == Core.ID) {
            id = S.getID();
            S.nextToken();
        }
        if (!S.expectedToken(Core.LPAREN)) {
            Utility.expectedhelper(Core.LPAREN, S.currentToken());
            System.exit(-1);
        }
        if (!S.expectedToken(Core.REF)) {
            Utility.expectedhelper(Core.REF, S.currentToken());
            System.exit(-1);
        }

        formals = new Formals();
        formals.parse(S);

        if (!S.expectedToken(Core.RPAREN)) {
            Utility.expectedhelper(Core.RPAREN, S.currentToken());
            System.exit(-1);
        }
        if (!S.expectedToken(Core.BEGIN)) {
            Utility.expectedhelper(Core.BEGIN, S.currentToken());
            System.exit(-1);
        }

        stmtseq = new StmtSeq();
        stmtseq.parse(S);

        if (!S.expectedToken(Core.ENDFUNC)) {
            Utility.expectedhelper(Core.ENDFUNC, S.currentToken());
            System.exit(-1);
        }
    }

    public void execute() {
        // Get all the formal parameters
        formals.execute(formalParameters);
        // Check if there are duplicate parameter names.
        // If so out print duplicate formal parameter errors
        Set<String> parameters = new HashSet<String>(formalParameters);
        if (parameters.size() < formalParameters.size()) {
            Utility.FunctionParameterDoubleDeclarationError();
            System.exit(-1);
        }
    }

    public StmtSeq getFunctionBody() {
        return stmtseq;
    }

    public void print() {
        System.out.print(id + " ( ref ");
        formals.print();
        System.out.print(" ) begin ");
        stmtseq.print(0);
        System.out.println(" endfunc ");
    }

}