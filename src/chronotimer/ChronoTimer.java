package src.chronotimer;

import java.awt.event.ActionEvent;
import java.util.*;

import com.google.gson.Gson;
import java.io.File.*;
import java.io.*;

enum RaceType {
	IND, PARIND, GRP, PARGRP
}


public class ChronoTimer implements Runnable {
	
	public Channel[] channels;
	public boolean powerOn = false;
	
	ArrayList<Race> raceList = new ArrayList<Race>();
	Race currentRace;
	RaceType raceType;
	
	public Queue<Command> cmdQueue;

	public ChronoTimer(){
		//powerOn = true;
		channels = new Channel[8];
		cmdQueue = new LinkedList<Command>();
	}
	// Provides an entry point for the ChronoTimer thread.
	// Please read about "Java Threads"
	@Override
	public void run() {
		
		//channels[0] = new Channel(this);
		//channels[1] = new Channel(this);
		//channels[2] = new Channel(this);
		//channels[3] = new Channel(this);
		Main.dbg.printDebug(1, "ChronoTimer starting up, but idle.");
		

		
		/* >>> EVENT LOOP
		 * When the application starts, we start at Main which creates and runs a Simulator thread, which creates a Chronotimer thread.
		 * The chronotimer thread will go execute this while loop every GRANULARITY miliseconds.
		 */ while (true){
			 while (powerOn){

				// Goes through all pending commands until the queue is empty.
				while (cmdQueue.isEmpty() == false) {

					// Simulator runs the command
					cmdQueue.poll().execute();
					
					try { Thread.sleep(100); }
					catch (InterruptedException e) { e.printStackTrace(); }
					
				}
				
							
				try { Thread.sleep(Main.GRANULAITY);} 
				catch (InterruptedException e) { e.printStackTrace(); }

			}
			
			// This is going to repeat until it's switched on.
			
			while (cmdQueue.isEmpty() == false) {

				// Simulator runs the command
				cmdQueue.poll().execute();
				
				try { Thread.sleep(100); }
				catch (InterruptedException e) { e.printStackTrace(); }
				
			}
			
			
			try { Thread.sleep(1000);} 
			catch (InterruptedException e) { e.printStackTrace(); } 
		}
		


	}
	
	// Could be put in Race class?
	public void score(ActionEvent e){
		
		if (e.getSource().equals(channels[0]) || e.getSource().equals(channels[2])){ // if start is tripped
			try{
				Racer popped = currentRace.toRace.pollFirst();
				popped.t.startTime();
				currentRace.inRace.addLast(popped);
			}
			catch (NoSuchElementException err){
				Main.dbg.printDebug(0, "[ERR] No racers are ready to start!");
			} catch (NullPointerException ex){
				Main.dbg.printDebug(0, "[ERR] Noone to start! Add more racers or start finishing.");
			}
			
			
		} else if (e.getSource().equals(channels[1]) || e.getSource().equals(channels[3])){ // if finish is tripped
		
			try {
				Racer popped = currentRace.inRace.pollFirst();
				popped.t.stopTime();
				currentRace.finishRace.add(popped);
			} catch (NoSuchElementException err){
				Main.dbg.printDebug(0, "[ERR] No racers are ready to finish!");
			}
			catch (NullPointerException err){
				Main.dbg.printDebug(0, "[ERR] No racers are ready to finish!");
			}
			
		}
	}

	public Command toCommand(String cmd){
		return new Command(cmd);
	}

/*
 * A "command" object. args[0] is the command itself (PRINT, NEWRUN, etc) while
 * args[1] and args[2] are the arguments.
 * There are two constructors. Both are functionally identical.
 * 	- One takes a String array of arguments, like classic command parsers.
 * 	- The other takes a single string, which parses according to the format "COMMAND ARG1 ARG2"
 * When a Command is constructed, the Command adds itself to the command queue.
 */ public class Command {

	String command = "", arg1 = "", arg2 = "";

	// Takes String Array
	public Command(String[] args) {
		command = args[0];
		if (args.length > 1)
			arg1 = args[1];
		if (args.length > 2)
			arg2 = args[2];
		
		cmdQueue.add(this);

	}
	
	// Takes string of format "COMMAND ARG1 ARG2"
	public Command(String cmd){
		
		// TODO: Sanitize / check input here

		Main.dbg.printDebug(3, "Command Constructor in: " + cmd);
		String[] args = cmd.split(" ");
		command = args[0];
		if (args.length > 1) arg1 = args[1];
		if (args.length > 2) arg2 = args[2];
		 
		Main.dbg.printDebug(3, "Command Constructor stored: " + Arrays.toString(cmd.split(" ")));
		Main.dbg.printDebug(0, Time.printTime() + "\t" + this.command + " " + arg1 + " " + arg2);

		cmdQueue.add(this); 
	}

	
	/* Run the command on the Simulator's ChronoTimer
	 */ public boolean execute() {
		
		Main.dbg.printDebug(3, "EXECUTING " + command + " " + arg1 + " " + arg2);

		switch (command.toUpperCase()) {
		case "POWER":

			// Should toggle the power.
			powerOn = powerOn ? false : true;
			if (powerOn) Main.dbg.printDebug(1,"ChronoTimer switched on!");
			else Main.dbg.printDebug(1, "ChronoTimer switched off!");
			break;

		case "EXIT":
			// cleanup?
			System.exit(0);
			break;

		case "RESET":
			powerOn = false;
			try { Thread.sleep(1100); } // Stops just long enough for Chronotimer to loop
			catch (InterruptedException e) { e.printStackTrace(); }
			powerOn = true;
			break;

		case "TIME":
			
			Main.dbg.printDebug(3, "INPUT TO TIME COMMAND: " + arg1);
			int hour, min; 
			double sec;
			String[] input = arg1.split(":");
			
			if (input.length == 2){
				min		= Integer.valueOf(input[0]);
				sec		= Double.valueOf(input[1]);
				Time.setTime(0, min, sec);
				Main.dbg.printDebug(3, "MIN: " + min + "  SEC: " + sec);
				
			}else if (input.length == 3){
				hour	= Integer.valueOf(input[0]);
				min		= Integer.valueOf(input[1]);
				sec		= Double.valueOf(input[2]);
				Main.dbg.printDebug(3, "HOUR: " + hour + "  MIN: " + min + "  SEC: " + sec);
				Time.setTime(hour, min, sec);
			} else {
				System.out.println("Inproper formatting! Proper Usage is HOUR:MIN:SEC, ie 3:00:00.1");
			}
			
			
			break;

		case "TOG":
			// command arg1 arg2
			int c = Integer.parseInt(arg1);
			
			// Handles channels as starting at 1 vs. arrays starting at 0.
			channels[c-1].toggle();
			String state = channels[c-1].getState() ? "on" : "off";
			Main.dbg.printDebug(1, "Channel " + Integer.toString(c) + " is now toggled " + state); //removed -1 to match the project description
			break;

		case "CONN":

			break;

		case "DISC":

			break;

		case "EVENT":
			
			if (arg1.equals("IND")){
				raceType = RaceType.IND;
				Main.dbg.printDebug(1, "Event set to IND");
				channels[0] = new Channel(ChronoTimer.this);
				channels[1] = new Channel(ChronoTimer.this);
				if (currentRace != null){
					currentRace.currentRaceType = (RaceType.IND);
				}
			}
			else if (arg1.equals("PARIND")) {
				raceType = RaceType.PARIND;
				Main.dbg.printDebug(1, "Event set to PARIND");
				channels[0] = new Channel(ChronoTimer.this);
				channels[1] = new Channel(ChronoTimer.this);
				channels[2] = new Channel(ChronoTimer.this);
				channels[3] = new Channel(ChronoTimer.this);
				if (currentRace != null){
					currentRace.currentRaceType = (RaceType.PARIND);
				}
			}
			break;

		case "NEWRUN":
	
			if (currentRace != null && currentRace.raceEnded == false){
				Main.dbg.printDebug(0, "Race is not ended. Use ENDRUN to end the race.");
				break;
			}
			if (currentRace != null) raceList.add(currentRace); // Saves the current race into an array.
			// This can be simplified when we are using more race types.
			if (raceType == RaceType.IND){
				currentRace = new Race(raceType);
				Main.dbg.printDebug(1, "New IND race created: " + currentRace.hashCode() + ", race #" + currentRace.raceNum);
				
			}
			else if (raceType == RaceType.PARIND){
				currentRace = new Race(raceType);
				Main.dbg.printDebug(1,  "New PARIND race created");
			}

			break;

		case "ENDRUN":
				
			currentRace.raceEnded = true;
			Main.dbg.printDebug(1, String.format("Race %d was set to finished.", currentRace.raceNum));
			Gson gson = new Gson();
			String json = gson.toJson(currentRace);
			try (PrintStream out = new PrintStream(new FileOutputStream("RUN" + currentRace.raceNum + ".txt"))) {
			    out.print(json);
			    out.flush();
			    out.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case "PRINT":
			try {
				for (Racer r : currentRace.finishRace){
					long time = r.t.runTime();
					if (time == Long.MAX_VALUE){
						Main.dbg.printDebug(0, "Racer " + r.bib + " DNF");
					}
					else {
						Main.dbg.printDebug(0, "Racer " + r.bib + " " + (r.t.runTime())/1000.0 + " seconds");
					}
				}
			} catch (NullPointerException ex){
				Main.dbg.printDebug(0, "[ERR] NPE at Print function. Did you initialize the racers?");
			}
			break;

		case "EXPORT":

			break;

		case "NUM":
			int bib = Integer.parseInt(arg1);
			try {
				currentRace.toRace.add(new Racer(bib));
				Main.dbg.printDebug(1, "Racer " + bib + " added");
			} catch (Exception ex) {
				Main.dbg.printDebug(0, "[ERR] Not a valid number, or race was incorrectly created.");
			}
			break;

		case "CLR":

			break;
			
		case "SWAP": // Not required for sprint 1
			ArrayList<Racer> list = new ArrayList<Racer>(currentRace.inRace);
			Collections.swap(list, 0, 1);
			Deque<Racer> newQ = new ArrayDeque<Racer>();
			for (Racer r : list) newQ.push(r);
			currentRace.inRace = newQ;
			break;

		case "DNF":
			// TODO test/fix
			currentRace.finishRace.add(currentRace.inRace.pollFirst());
			
			break;
			
		case "CANCEL":
			// TODO test/fix
			currentRace.toRace.push(currentRace.inRace.pollLast());
			break;

		case "TRIG":
			// TODO test/fix
			// Find the sensor object associated with arg1
			// Send a trigger command to it
			int chan = Integer.parseInt(arg1);
			if (chan == 1 || chan == 2 || chan == 3 || chan == 4){
				try {
					channels[(chan-1)].trigger(); // Converting between array (0..) to natural integers (1..)
					Main.dbg.printDebug(1, "Channel " + (chan) + " tripped!"); //Changed this so that channel names match the project description
				} catch (Exception ex) {
					Main.dbg.printDebug(0, "[ERR] in TRIG function -- " + ex.getMessage());
				}
			} else {
				System.out.printf("Channel %f out of scope\n", chan);
			}
			break;

		case "START":
			if (currentRace.currentRaceType == RaceType.IND){
				channels[0].trigger();
			} else if (currentRace.currentRaceType == RaceType.PARIND){
				if (channels[0] != null && channels[0].getState()) channels[0].trigger();
				if (channels[2] != null && channels[2].getState()) channels[2].trigger();
			}
			break;

		case "FINISH":
			if (currentRace.currentRaceType == RaceType.IND){
				channels[1].trigger();
			} else if (currentRace.currentRaceType == RaceType.PARIND){
				if (channels[1] != null && channels[1].getState()) channels[1].trigger();
				if (channels[3] != null && channels[3].getState()) channels[3].trigger();
			}
			break;
		
		case "DEBUG":
			Main.MAX_VERBOSITY = Integer.parseInt(arg1);
			for (int i = 0; i <= 3; i++) Main.dbg.printDebug(i, "MAX_VERBOSITY CHANGED -- Debug level ["+ i + "] messages active");
			break;
			
		case "LIST":
			Main.dbg.printDebug(0, currentRace.toString());
			break;
			
			
		case "HELP":
			System.out.println(
				">>> COMMANDS:\n" + 
				"POWER	Turn on and enter idle state or turn system off but stay in simulator\n" +
				"EXIT	Exit the simulaton\n" +
				"RESET	Resets the System to initial state\n" +
				"TIME	Set the current time 						FORMAT: <hour>:<min>:<sec>\n" + 
				"TOG	toggle state of channel 					FORMAT: <channel>\n" +
				"CONN	connect a type of sensor to channel <num>			FORMAT: <sensor> <num>\n" +
				"DISC 	disconnect sensor from channel <num>				FORMAT: <num>\n" + 
				"EVENT	type of event (IND, PARIND, GRP, PARGRP)			FORMAT: <eventcode>\n" + 
				"NEWRUN	creates a new run\n" +
				"ENDRUN	done with a run\n" +
				"PRINT	Prints the run on stdout\n" +
				"EXPORT	Exports the run in XML to file RUN<RUN>				FORMAT: <run>\n" +
				"NUM	Set <num> as the next competitor to start			FORMAT: <num>\n" +
				"LR	Clear competitor number <num>					FORMAT: <num>\n"+ 
				"SWAP	Exchange next to compentitors to finish in IND\n" +
				"DNF 	Next competitor to finish will not finish\n" +
				"TRIG	Trigger channel <num>						FORMAT: <num>\n" +
				"START	Start trigger channel 1 -- macro for TRIG 1\n" +
				"FINISH	Finish trigger channel 2 -- macro for TRIG 2\n" + 
				"DEBUG	Change the debug output's verbosity.				FORMAT: <1...3>\n" +
				"LIST	Show the current race, their queues, and racers.\n"
			);
			break;
			
			
		default:
			Main.dbg.printDebug(0, "UNSUPPORTED COMMAND: " + this);
		}

		return true;

	}
}
}