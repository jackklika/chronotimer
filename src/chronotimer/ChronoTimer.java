package chronotimer;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

enum RaceType {
	IND, PARIND, GRP, PARGRP
}


public class ChronoTimer implements Runnable {
	
	public boolean powerOn = false;
	public boolean reset = false;
	
	// Provides an entry point for the ChronoTimer thread.
	// Please read about "Java Threads"
	public void run() {
		
		Main.dbg.printDebug(2, this + " starting up, but idle.");
		

		/*
		 * >>> EVENT LOOP
		 * When the application start, we start at Main which creates and runs a Chronotimer thread.
		 * The chronotimer thread will go execute this while loop every 1 second.
		 */
		
		while (true){
			
			if (powerOn) Main.dbg.printDebug(2, this + " switched on!");
			while (powerOn){
				//Main.dbg.printDebug(3, "🕐  " + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));

				// Goes through all pending commands until the queue is empty.
							
				try { Thread.sleep(Main.GRANULAITY);} 
				catch (InterruptedException e) { e.printStackTrace(); }
				
				if (powerOn == false) Main.dbg.printDebug(1, this + " switched off!");
			}
			// If it reaches here that means the power was turned off
			
			// Stuff goes here to handle the power being turned off.
			
			
			
			try { Thread.sleep(1000);} 
			catch (InterruptedException e) { e.printStackTrace(); } 
		}
		
		

	}
}
