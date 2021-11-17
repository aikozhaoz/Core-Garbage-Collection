public class Utility {

    public static void expectedhelper(Core expected, Core current) {
        if (current == Core.ERROR) {
            System.out.println("ERROR: Invalid token!");
            System.out.println("Invalid token: " + current);
        } else {
            System.out.println("Syntax error!  ");
            System.out.println("Expected token: " + expected);
            System.out.println("Current token: " + current);
        }
    }

    public static void errorhelper(Core[] expectedones, Core current) {
        if (current == Core.ERROR) {
            System.out.println("ERROR: Invalid token!");
            System.out.println("Invalid token: " + current);
        } else {
            System.out.println("Syntax error:  ");
            String errormessage = "Expected tokens:  ";
            for (int i = 0; i < expectedones.length; i++) {
                if (i < expectedones.length - 1) {
                    errormessage += expectedones[i].name() + ", ";
                } else {
                    errormessage += expectedones[i].name();
                }
            }
            System.out.println(errormessage);
            System.out.println("Current token: " + current);
        }
    }

    public static boolean checkIfTokenIsExpected(Core[] expectedones, Core current) {
        boolean check = false;
        for (int i = 0; i < expectedones.length; i++) {
            if (current == expectedones[i]) {
                check = true;
            }
        }
        return check;
    }

    public static void DoubleDeclarationError(String id) {
        System.out.println("Semantic Error! ");
        System.out.println("The current ID is already declared within the same scope: " + id);
    }

    public static void FunctionParameterDoubleDeclarationError() {
        System.out.println("Semantic Error! ");
        System.out.println("Double declaration on functions' parameters");
    }

    public static void UseUndeclaredIdError(String id) {
        System.out.println("Semantic Error! ");
        System.out.println("The current ID has not been declared yet! " + id);
    }

    public static void UseUndeclaredFunctionError(String id) {
        System.out.println("Semantic Error! ");
        System.out.println("The current function has not been declared yet! Function: " + id);
    }

    public static void unmatchingFunctionParameter(String id) {
        System.out.println("Semantic Error! ");
        System.out.println(
                "The amount of actual parameters does not align with the amount of formal parameters for function: "
                        + id);
    }

    public static void DeclaredTypeError(String id) {
        System.out.println("Semantic Error! ");
        System.out.println("The current ID was declared with the wrong type  " + id);
    }

    public static void InvalidInput() {
        System.out.println("Runtime Error! ");
        System.out.println("All values in the .data file have already been used.");
    }

    public static void refIndexNull() {
        System.out.println("Runtime Error! ");
        System.out.println("The class you are assigning constant to is null. ");
    }
}
