import java.util.*;

public class Loop {

    String line;

    Cond cond;
    StmtSeq stmtseq;

    Loop() {
        line = "";
        cond = new Cond();
        stmtseq = new StmtSeq();
    }

    public void parse(Scanner S) {
        // <loop> ::= while <cond> begin <stmt-seq> endwhile
        if (!S.expectedToken(Core.WHILE)) {
            Utility.expectedhelper(Core.WHILE, S.currentToken());
            System.exit(-1);
        }
        cond.parse(S);
        if (!S.expectedToken(Core.BEGIN)) {
            Utility.expectedhelper(Core.BEGIN, S.currentToken());
            System.exit(-1);
        }
        stmtseq.parse(S);
        if (!S.expectedToken(Core.ENDWHILE)) {
            Utility.expectedhelper(Core.ENDWHILE, S.currentToken());
            System.exit(-1);
        }
    }

    public void semantic(Stack<Map<String, Core>> scopetrack) {
        cond.semantic(scopetrack);
        // New scope starts after "BEGIN".
        Map<String, Core> scopeone = new HashMap<>();
        scopetrack.push(scopeone);
        stmtseq.semantic(scopetrack);
    }

    public void execute(Scanner inputScanner) {
        while (cond.execute()) {
            // Start scope
            HashMap<String, Corevar> scopeone = new HashMap<>();
            Memory.stackSpace.peek().push(scopeone);
            stmtseq.execute(inputScanner);
        }
        // End Scope
        Memory.stackSpace.peek().pop();

    }

    public void print(int indent) {
        for (int i = 0; i < indent; i++) {
            line += "  ";
        }
        System.out.print(line + "while ");
        cond.print(indent);
        System.out.println(" begin");
        indent++;
        stmtseq.print(indent);
        System.out.println(line + "endwhile");
    }
}
