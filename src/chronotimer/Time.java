package chronotimer;

import java.time.*;

public class Time {
	
	public long start;
	public long stop;
	
	public Time(){}
	
	// Start the timer for the object!
	public void startTime(){
		start = Instant.now().toEpochMilli();
	}
	
	public void stopTime(){
		stop = Instant.now().toEpochMilli();
	}
	
	// Difference between start and stop time in MS
	public long runTime(){
		if (start == 0 || stop == 0){ // Default values
			Main.dbg.printDebug(0, "[ERR]: Time.runTime() called before both Time.startTime() and Time.stopTime() were called.");
			return 0;
		} else {
			return (stop - start);
		}
		
		
	}
}
