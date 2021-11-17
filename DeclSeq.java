import java.lang.reflect.Parameter;
import java.util.*;

public class DeclSeq {

    int option;
    String funcName;

    Decl decl;
    DeclSeq declseq;
    Funcdecl funcdecl;

    DeclSeq() {
        option = 0;
        funcName = "";
        decl = null;
        declseq = null;
        funcdecl = null;
    }

    public void parse(Scanner S) {
        // If the first token is int/ref, option = 1/2
        // Option 1: <decl-seq> ::= <decl>
        // Option 2: <decl-seq> ::= <decl><decl-seq>
        if (S.currentToken() == Core.INT || S.currentToken() == Core.REF) {
            option = 1;
            decl = new Decl();
            decl.parse(S);
            if (S.currentToken() != Core.BEGIN) {
                option = 2;
                declseq = new DeclSeq();
                declseq.parse(S);
            }
        }
        // If the first token is ID, option = 3/4
        // Option 3: <decl-seq> ::= <func-decl>
        // Option 4: <decl-seq> ::= <func-decl><decl-seq>
        else if (S.currentToken() == Core.ID) {
            option = 3;
            funcName = S.getID();
            funcdecl = new Funcdecl();
            funcdecl.parse(S);
            if (S.currentToken() != Core.BEGIN) {
                option = 4;
                declseq = new DeclSeq();
                declseq.parse(S);
            }
        } else {
            Core[] expectedones = new Core[] { Core.ID, Core.INT, Core.REF };
            Utility.errorhelper(expectedones, S.currentToken());
            System.exit(-1);
        }
    }

    public void semantic(Stack<Map<String, Core>> scopetrack) {
        decl.semantic(scopetrack);
        if (option == 2) {
            declseq.semantic(scopetrack);
        }
    }

    public void execute() {
        if (option == 1) {
            decl.execute();
        } else if (option == 2) {
            decl.execute();
            declseq.execute();
        } else if (option == 3) {
            // Check if function is already declared. If not then put the <funcName,
            // funcdecl> onto functionDeclaration.
            // Otherwise, duplicate function name error.
            if (!Memory.functionDeclaration.containsKey(funcName)) {
                Memory.functionDeclaration.put(funcName, funcdecl);
            } else {
                Utility.DoubleDeclarationError(funcName);
            }
            // Execute funcdecl to check if there is any duplicates in formal parameters.
            funcdecl.execute();
        } else if (option == 4) {
            // Check if function is already declared. If not then put the <funcName,
            // funcdecl> onto functionDeclaration.
            // Otherwise, duplicate function name error.
            if (!Memory.functionDeclaration.containsKey(funcName)) {
                Memory.functionDeclaration.put(funcName, funcdecl);
            } else {
                Utility.DoubleDeclarationError(funcName);
            }
            // Execute funcdecl to check if there is any duplicates in formal parameters.
            funcdecl.execute();
            declseq.execute();
        }
    }

    public void print(int indent) {
        // System.out.println("print declseq"+option);
        if (option == 1) {
            decl.print(indent);
        } else if (option == 2) {
            decl.print(indent);
            declseq.print(indent);
        } else if (option == 3) {
            // System.out.print("option =3");
            funcdecl.print();
        } else if (option == 4) {
            funcdecl.print();
            declseq.print(indent);
        }
    }
}