import java.util.*;

public class DeclClass {

    String line;

    IdList idlist;

    DeclClass() {
        line = "";
        idlist = null;
    }

    public void parse(Scanner S) {
        // <decl-class> ::= ref <id-list> ;
        if (!S.expectedToken(Core.REF)) {
            Utility.expectedhelper(Core.REF, S.currentToken());
            System.exit(-1);
        }
        idlist = new IdList();
        idlist.parse(S);
        if (!S.expectedToken(Core.SEMICOLON)) {
            Utility.expectedhelper(Core.SEMICOLON, S.currentToken());
            System.exit(-1);
        }
    }

    public void semantic(Stack<Map<String, Core>> scopetrack) {
        idlist.semantic(scopetrack, Core.REF);
    }

    public void execute() {
        idlist.execute(Core.REF);
    }

    public void print(int indent) {
        for (int i = 0; i < indent; i++) {
            line += "  ";
        }
        System.out.print(line + "ref ");
        idlist.print(indent);
        System.out.println(";");
    }
}
