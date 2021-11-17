import java.util.*;

// Using Singleton pattern since we only need ONE memory obj
public class Memory {
    public static HashMap<String, Corevar> globalSpace;
    public static HashMap<String, Funcdecl> functionDeclaration;
    public static Stack<Stack<HashMap<String, Corevar>>> stackSpace;
    public static ArrayList<Integer> heapSpace;
    private static ArrayList<Integer> refCount;
    public static boolean inGlobal;

    // Make the constructor private so that this class cannot be instantiated
    // outside of this class
    private Memory() {
        globalSpace = new HashMap<String, Corevar>();
        functionDeclaration = new HashMap<String, Funcdecl>();
        stackSpace = new Stack<Stack<HashMap<String, Corevar>>>();
        heapSpace = new ArrayList<Integer>();
    }

    // Create a private instance of Memory obj
    private static Memory memory = new Memory();

    // Get the only instance available
    public static Memory getMemory() {
        return memory;
    }

    // public static count


}
