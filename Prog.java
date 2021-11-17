import java.util.*;

public class Prog {

    int option;
    Stack<Map<String, Core>> scopetrack;

    DeclSeq declseq;
    StmtSeq stmtseq;

    Prog() {
        option = 0;
        scopetrack = new Stack<Map<String, Core>>();
        declseq = null;
        stmtseq = new StmtSeq();
    }

    public void parse(Scanner S) {
        // Option 1: <prog> ::= program <decl-seq> begin <stmt-seq> end
        if (!S.expectedToken(Core.PROGRAM)) {
            Utility.expectedhelper(Core.PROGRAM, S.currentToken());
            System.exit(-1);
        }
        option = 1;
        // If the next token is not BEGIN, it should be decl_seq.
        // If the next token is BEGIN, skip the if and jump to expectedToken(BEGIN)

        // Option 2: <prog> ::= program begin <stmt-seq> end
        if (S.currentToken() != Core.BEGIN) {
            option = 2;
            declseq = new DeclSeq();
            declseq.parse(S);
        }

        if (!S.expectedToken(Core.BEGIN)) {
            Utility.expectedhelper(Core.BEGIN, S.currentToken());
            System.exit(-1);
        }
        stmtseq.parse(S);
        if (!S.expectedToken(Core.END)) {
            Utility.expectedhelper(Core.END, S.currentToken());
            System.exit(-1);
        }
        if (!S.expectedToken(Core.EOF)) {
            Utility.expectedhelper(Core.EOF, S.currentToken());
            System.exit(-1);
        }
    }

    public void semantic() {
        if (option == 2) {
            Map<String, Core> global = new HashMap<>();
            scopetrack.add(global);
            declseq.semantic(scopetrack);
        }
        Map<String, Core> localLone = new HashMap<>();
        scopetrack.add(localLone);
        stmtseq.semantic(scopetrack);
    }

    public void execute(Scanner inputScanner) {
        // Option 1: <prog> ::= program <decl-seq> begin <stmt-seq> end
        if (declseq != null) {
            Memory.inGlobal = true;
            declseq.execute();
        }

        Memory.inGlobal = false;
        // Create basemap and mainspace
        HashMap<String, Corevar> basemap = new HashMap<String, Corevar>();
        Stack<HashMap<String, Corevar>> mainsapce = new Stack<HashMap<String, Corevar>>();
        mainsapce.push(basemap);
        Memory.stackSpace.push(mainsapce);
        // Option 2: <prog> ::= program begin <stmt-seq> end
        stmtseq.execute(inputScanner);
        Memory.stackSpace.pop();
    }

    public void print(int indent) {
        System.out.println("program");
        if (option == 2) {
            declseq.print(indent);
        }
        System.out.println("begin");
        stmtseq.print(indent);
        System.out.println("end");
    }
}
