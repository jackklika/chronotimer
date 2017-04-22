import javafx.scene.control.TextArea;

public class Main {

	// The following are settings for the entire project.
	
	public static int MAX_VERBOSITY = 1; /* 
	 * Sets the verbosity of the debug message outputter.
	 * 0 = IMPORTANT MESSAGES ONLY
	 * 1 = General messages
	 * 2 = Specific messages
	 * 3 = MAXIMUM DEBUGGERING.
	 */ 
	 
	 
	public static int GRANULAITY = 100; /*
	 * Simply how often the event loop runs, in milliseconds. 1000ms = 1s
	 * Default should be 1000 (Maybe?)
	 */ 
	 
	/*
	 * This object should be the only thing printing to stdout (besides simulator)
	 * Call this in other classes for debugging messages
	 * EXAMPLE: Main.dbg.printDebug(num, msg)
	 * 0 -- Commands the user of the shell should see and are useful for both user and debugger.
	 * 1 -- Commands the developer should see, very helpful for troubleshooting. Stuff like Chronotimer power, 
	 * 		thread state changes, and non-fatal errors.
	 * 2 -- Commands good for troubleshooting, but not as important as 1.
	 * 3 -- Really finite stuff.
	 */ 
	static class Debugger{
		
		public TextArea output;
		public String outputStream;
		
		public Debugger(){}
		
		public void setOutput(TextArea ts) {
			output = ts;
			output.setWrapText(true);
		}
		
		public void printDebug(int messageVerbosity, String message){
			if (messageVerbosity <= MAX_VERBOSITY) {
				String out = String.format(/*"DEBUG [%d]\t */"%s\n", /*messageVerbosity,*/ message);
				//System.out.printf(/*"DEBUG [%d]\t */"%s\n", /*messageVerbosity,*/ message);
				System.out.print(out);
				if (output != null && out != null) {
					outputStream += out;
					output.setText(outputStream);
					output.setScrollTop(Double.MAX_VALUE);
				}
			}
		}
		
	}
	
	
	public static Debugger dbg = new Debugger();


	// The program all runs here. This might be the "simulator"
	public static void main(String[] args) {	
		
		//for (int i = 0; i <= 3; i++) dbg.printDebug(i, "Debug level ["+ i + "] messages active");
		dbg.printDebug(1, "Current time is: " + Time.printTime());
		
		Simulator sim = new Simulator(new ChronoTimer());
		if (!(args == null || args.length == 0)) sim.filename = args[0];
		sim.run();
		

	}

}
