package chronotimer;

import java.time.*;


/* USAGE:
 * 1: Create a time object -- "Time timer = new Time()"
 * 2: Start the timer -- "timer.startTime()"
 * 3: Stop the timer -- "Timer.stopTime()" (This also returns the stop - start time!)
 * 4: Get the difference between start and stop times -- "long time = timer.runTime)
 */ public class Time {
	
	 public long start;
	 public long stop;
	
	public Time(){}
	
	// Start the timer for the object!
	public void startTime(){
		start = Instant.now().toEpochMilli();
	}
	
	public long stopTime(){
		if (start == 0){ // Default values
			Main.dbg.printDebug(0, "[ERR]: Time.stopTime() called before Time.starTime was called.");
			return 0;
		} else {
			stop = Instant.now().toEpochMilli();
			return (stop - start);
		}
	}
	
	// Difference between start and stop time in MS
	public long runTime(){
		if (start == 0 && stop == 0){ // errors if racers haven't gone
			Main.dbg.printDebug(0, "[ERR]: Time.runTime() called before both Time.startTime() and Time.stopTime() were called.");
			return 0;
		}
		else if (start != 0 && stop == 0){
			return Long.MAX_VALUE;
		}
		else {
			return (stop - start);
		}	
		
	}
}
