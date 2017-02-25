package chronotimer;

public class Main {

	// Ideally these constants could be set by command line arguments. TODO!
	
	final static int MAX_VERBOSITY = 3; /* 
	 * Sets the verbosity of the debug message outputter.
	 * 0 = No messages
	 * 1 = General messages
	 * 2 = Specific messages
	 * 3 = MAXIMUM DEBUGGERING.
	 */ 
	 
	 
	final static int GRANULAITY = 10; /*
	 * Simply how often the event loop runs, in milliseconds. 1000ms = 1s
	 * Default should be 1000 (Maybe?)
	 */ 
	 
	/*
	 * This object should be the only thing printing to stdout (besides simulator)
	 * Call this in other classes for debugging messages
	 * EXAMPLE: Main.dbg.printDebug(num, msg)
	 */
	static class Debugger{
		
		public Debugger(){}
		
		public void printDebug(int messageVerbosity, String message){
			if (messageVerbosity <= MAX_VERBOSITY) System.out.printf("DEBUG [%d]\t%s\n", messageVerbosity, message);
		}
		
	}
	
	
	public static Debugger dbg = new Debugger();


	// The program all runs here. This might be the "simulator"
	public static void main(String[] args) {	
		
		
		for (int i = 1; i <= 3; i++) dbg.printDebug(i, "Debug level ["+ i + "] messages active");
		
		ChronoTimer ct = new ChronoTimer();
		ct.run();
		

	}

}
