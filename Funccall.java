import java.util.*;

public class Funccall {

    String id;
    Formals formals;
    ArrayList<String> actualParam;

    Funccall() {
        id = "";
        formals = null;
    }

    public void parse(Scanner S) {
        // <func-call> ::= begin id ( <formals> )
        if (!S.expectedToken(Core.BEGIN)) {
            Utility.expectedhelper(Core.BEGIN, S.currentToken());
            System.exit(-1);
        }

        if (S.currentToken() == Core.ID) {
            id = S.getID();
            S.nextToken();
        } else {
            Utility.expectedhelper(Core.ID, S.currentToken());
            System.exit(-1);
        }

        if (!S.expectedToken(Core.LPAREN)) {
            Utility.expectedhelper(Core.LPAREN, S.currentToken());
            System.exit(-1);
        }

        formals = new Formals();
        formals.parse(S);

        if (!S.expectedToken(Core.RPAREN)) {
            Utility.expectedhelper(Core.RPAREN, S.currentToken());
            System.exit(-1);
        }
        if (!S.expectedToken(Core.SEMICOLON)) {
            Utility.expectedhelper(Core.SEMICOLON, S.currentToken());
            System.exit(-1);
        }
    }

    public void execute(Scanner inputScanner) {
        String funcName = id;
        // Check if function is declared before execution
        if (!Memory.functionDeclaration.containsKey(funcName)) {
            Utility.UseUndeclaredFunctionError(funcName);
            System.exit(-1);
        }

        // If the function is already declared. Get the corresponding function
        Funcdecl function = Memory.functionDeclaration.get(funcName);

        // Getting all the actual parameters.
        actualParam = new ArrayList<String>();
        formals.execute(actualParam);

        // Check if the amount of the formal parameters == the amount of the actual
        // parameters.
        // If not, unmatching function parameter error.
        if (function.formalParameters.size() != actualParam.size()) {
            Utility.unmatchingFunctionParameter(funcName);
            System.exit(-1);
        }

        // Get mainstack so we can peek to get all the actual parameters' corresponding
        // value.
        Stack<HashMap<String, Corevar>> mainstack = Memory.stackSpace.peek();
        // Create FuncSpace map
        HashMap<String, Corevar> funcSpace = new HashMap<String, Corevar>();

        // Loop through the actual parameters[].
        // Set up each formal parameter with the corresponding actual parameter's value.
        // FuncSpace = {<formalparameter's id:actualparameter's val>}
        for (int i = 0; i < actualParam.size(); i++) {
            String key = function.formalParameters.get(i);
            Corevar param = new Corevar();
            boolean inGlobal = true;
            // Loop through mainstack's maps to get actual parameter's corresponding value.
            for (HashMap<String, Corevar> m : mainstack) {
                if (m.containsKey(actualParam.get(i))) {
                    inGlobal = false;
                    param = m.get(actualParam.get(i));

                }
            }
            // Check globalSpace if the actual parameter does not exist in mainstack.
            // If not, actual parameter is not declared yet.
            if (inGlobal) {
                param = Memory.globalSpace.get(actualParam.get(i));
            } else if (param.type == null) {
                Utility.UseUndeclaredIdError(id);
                System.exit(-1);
            }

            // After gathering key = formal parameter and value = corresponding actual
            // parameter's Corevar, put the pair to funcSpace
            funcSpace.put(key, param);
        }
        // Allocate a funcstack frame for current function call.
        Stack<HashMap<String, Corevar>> funcstack = new Stack<HashMap<String, Corevar>>();
        // Put funccall map into funcstack frame.
        funcstack.push(funcSpace);
        // Put funcstack to stackSpace.
        Memory.stackSpace.push(funcstack);
        // Now execute function.
        function.getFunctionBody().execute(inputScanner);
        // After funtion's execution, pop the function call from the stackSpace.
        Memory.stackSpace.pop();
    }

    public void print() {
        System.out.print("begin " + id + " ( ");
        formals.print();
        System.out.print(" );");
    }
}
