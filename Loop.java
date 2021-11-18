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
            // prevLiveCount = the amount of live variables within the whole while block
            int prevLiveCount = Memory.liveCount;
            // Clear all the local variable's corresponding refCount
            for (String key : Memory.stackSpace.peek().peek().keySet()) {
                String id = key;
                Corevar localCorevar = Memory.stackSpace.peek().peek().get(id);
                Memory.refCount.set(localCorevar.value, 0);
            }
            Memory.countLiveRefs();
            if (prevLiveCount != Memory.liveCount) {
                System.out.println("gc:" + Memory.liveCount);
            }
        }
        // End Scope
        // Pop scope out - double check, allegedly we never really need to run line
        // 63-68.
        Memory.stackSpace.peek().pop();
        int prevLiveCount = Memory.liveCount;
        Memory.countLiveRefs();
        if (prevLiveCount != Memory.liveCount) {
            System.out.println("gc:" + Memory.liveCount);
        }
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
