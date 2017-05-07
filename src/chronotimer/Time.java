package chronotimer;
import java.time.*;
import java.util.Calendar;

/* USAGE:
 * 1: Create a time object -- "Time timer = new Time()"
 * 2: Start the timer -- "timer.startTime()"
 * 3: Stop the timer -- "Timer.stopTime()" (This also returns the stop - start time!)
 * 4: Get the difference between start and stop times -- "long time = timer.runTime)
 */
public class Time {
	private static Calendar date = Calendar.getInstance();
	private static long milliDate = date.getTimeInMillis(); // THIS MORNING 12AM'S TIME

	// The difference between NOW and the desired time.
	// If up to date, it is simply 0. 
	// If the time is set to 13:00:00.0 and it is in reality 13:00:00.0005 it should be 5.
	public static long currentMs = 3600000*18;
	
	
	public long start;
	public long stop;
	
	public Time() {
	}
	
	public static void setTime(int hours, int minutes, double seconds){
		// x = midnight until the time set in milli. 0:00:05.0000 would be 5000.
		long x = (hours*(3600000) + minutes*60000 + (int)(seconds*1000)) + milliDate;
		
		x-= milliDate;
		// Difference between now and desired time today.
		currentMs = x - System.currentTimeMillis();
		
		Main.dbg.printDebug(1, "Time is now: " + printTime());
		
		
	}
	
	// Returns the time as a string.
	public static String printTime(){
		
		long mili = System.currentTimeMillis() + currentMs;
		long second = (mili / 1000) % 60;
		long minute = (mili / (1000 * 60)) % 60;
		long hour = (mili / (1000 * 60 * 60)) % 24; 

		return String.format("%02d:%02d:%02d", hour, minute, second);
	}

public static String convert(Long mili){
	
		if (mili == Long.MAX_VALUE) return "DNF";
		
		//long mili = System.currentTimeMillis() + currentMs;
		long hundrs = (mili / 10) % 100;
		long second = (mili / 1000) % 60;
		long minute = (mili / (1000 * 60)) % 60;
		long hour = (mili / (1000 * 60 * 60)) % 24; 

		return String.format("%02d:%02d:%02d", minute, second, hundrs);
	}
	
	// Start the timer for the object!
	public void startTime() {
		start = Instant.now().toEpochMilli()+currentMs;
	}

	public long stopTime() {
		if (start == 0) { // Default values
			Main.dbg.printDebug(3, "[ERR]: Time.stopTime() called before Time.starTime was called.");
			return 0;
		} else {
			stop = Instant.now().toEpochMilli()+currentMs;
			return (stop - start);
		}
	}

	// Difference between start and stop time in MS
	public long runTime() {
		if (start == 0 && stop == 0) { // errors if racers haven't gone
			Main.dbg.printDebug(3,
					"[ERR]: Time.runTime() called before both Time.startTime() and Time.stopTime() were called.");
			return 0;
		} else if (start != 0 && stop == 0) {
			return Instant.now().toEpochMilli()+currentMs - start;
		} else {
			return (stop - start);
		}

	}
}
