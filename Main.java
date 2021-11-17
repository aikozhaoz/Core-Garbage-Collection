class Main {
	public static void main(String[] args) {
		// Initialize the scanner with the input file
		Scanner S = new Scanner(args[0]);
		Scanner inputScanner = new Scanner(args[1]);
		Prog root = new Prog();
		root.parse(S);
		root.execute(inputScanner);

		// root.print(1);
		// root.semantic();
	}
}