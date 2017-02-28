package chronotimer;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

enum RaceType {
	IND, PARIND, GRP, PARGRP
}


public class ChronoTimer implements Runnable {
	
	public boolean powerOn = true;
	
	// Provides an entry point for the ChronoTimer thread.
	// Please read about "Java Threads"
	public void run() {
		
		Main.dbg.printDebug(2, this + " starting up, but idle.");
		

		/* >>> EVENT LOOP
		 * When the application starts, we start at Main which creates and runs a Simulator thread, which creates a Chronotimer thread.
		 * The chronotimer thread will go execute this while loop every GRANULARITY miliseconds.
		 */ while (true){
			
			if (powerOn) Main.dbg.printDebug(2, this + " switched on!");
			while (powerOn){
				//Main.dbg.printDebug(3, "🕐  " + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));

				// Goes through all pending commands until the queue is empty.
							
				try { Thread.sleep(Main.GRANULAITY);} 
				catch (InterruptedException e) { e.printStackTrace(); }

			}
			
			// This is going to repeat until it's switched on.
			Main.dbg.printDebug(2, this + " switched off!");
			
			
			
			try { Thread.sleep(1000);} 
			catch (InterruptedException e) { e.printStackTrace(); } 
		}
		
		

	}
}
