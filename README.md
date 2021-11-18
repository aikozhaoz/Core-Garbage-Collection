# Core-Interpreter

### How to run
* ```cd``` to the ```Interpreter``` folder
* To compile, run ```javac Main.java ``` in your terminal
* To run the actual program, run ```javac Main.java testfile datafile```

### Project Description
* Adding function definitions and function calls on Core-Interpreter for a version of the Core language, a pretend language. Core-Interpreter executes the whole program(including function calls now) and check if there is any runtime error.
* The way I handle function calls is that adding a new stack to stackspace right before each function call and pop them off right after each function call.

### How I handle garbage collection:
* Using an ArrayList refCount to track of all the lived variables.
* Whenever we create a new variable, the variable's corresponding refcount++, total liveCount ++
* Whenever we assign a ref variable to another ref variable, the leftside variable's corresponding refcount--, the rightside variable's corresponding refcount++.
* Whenever we pop a if/else/while scope, cleared out all local variable's corresponding refcount.
* Whenever we call a function, actual parameters' corresponding refcount++.
* Whenever we pop a function, the corresponding refcount of each variable within the function scope decrements.

### How I execute function calls:
* Formals:
    * formals.execute() -> parameters[]
* Funcdecl:
    * funcdecl.execute() -> Check if there are duplicate formal parameters
    * funcdecl.getFunctionBody() -> Get functionbody
* Funccall:
    * funccal.execute(inputScanner) -> Execute the function here.
    * For more specific details, check comments on funccal.execute(inputScanner).

### Main class
* Skeleton of the program's workflow : )

### Core class
* Core Enums.

### Scanner Class
* Scanner:
    * The class constructor takes as input the name of the input file and finds the first token (the current token)
* tokenize:
    * This method should return a tokens list. 
* currentToken: 
    * This method should return the token the scanner is currently on, without consuming that token.
* nextToken:
    * This method should advance the scanner to the next token in the stream (the next token becomes the current token).
* getID: 
    * If the current token is ID, then this method should return the string value of the identifier.
* getCONST: 
    * If the current token is CONST, then this method should return the value of the constant.
* expectedToken:
    * Return if the current token == given Core c. If so, dequeues the tokens.

### Utility class
* expectedhelper:
    * If the current token is an invalid token, print out error message
    * If the current token is not expected token print out error message, expected token and curren token.
* errorhelper:
    * If the current token is an invalid token, print out error message
    * If the current token is not expected token print out error message, expected tokens and curren token.
* DoubleDeclarationError:
    * Print out error message to notify user the file has double declared variable. 
* FunctionParameterDoubleDeclarationError:
    * Print out error message to notify user the file has double declared parameters.
* UseUndeclaredIdError:
    * Print out error message to notify user the file used undeclared variable.
* UseUndeclaredFunctionError:
    * Print out error message to notify user calls on undeclared function.
* unmatchingFunctionParameter:
    * Print out error message to notify user the amount of actual parameters does not align with the amount of formal parameters for function.
* DeclaredTypeError:
    * Print out error message to notify user the file contains wrongly declared variable.
* InvalidInput:
    * Print out runtime error message to notify user all values in the .data file have already been used.
* refIndexNull:
    * Print out runtime error message to notify user the class they are assigning constant to is null.
    
### Memory class
* Construct the memory class using singleton pattern.
* Separated the memory in 4 different scope:
    * Static/Global--> globalSpace
    * Static functions//Global --> functionDeclaration
    * Local/Stack --> stackSpace
    * Heap/Reference variables --> heapSpace
* refcount: keep track of live variables

### Corevar class
* Contains 2 key attributes for each Core variable:
    * Type: int/ref
    * Value: int --> the actual int value | ref --> the pointer

### Grammar classes:
![Core grammar](Core%20Function%20CallsCo/core-grammar.pdf)









