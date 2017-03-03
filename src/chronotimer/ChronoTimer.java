package chronotimer;

import java.awt.event.ActionEvent;
import java.util.*;

enum RaceType {
	IND, PARIND, GRP, PARGRP
}


public class ChronoTimer implements Runnable {
	
	public Channel[] channels;
	public boolean powerOn = true;
	public Deque<Racer> toRace;
	public Deque<Racer> racers;
	public ArrayList<Racer> finished;
	public Queue<Command> cmdQueue;

	public ChronoTimer(){
		//powerOn = true;
		channels = new Channel[8];
		finished = new ArrayList<Racer>();
		cmdQueue = new LinkedList<Command>();
		toRace = new ArrayDeque<Racer>();
		racers = new ArrayDeque<Racer>();
	}
	// Provides an entry point for the ChronoTimer thread.
	// Please read about "Java Threads"
	public void run() {
		
		channels[0] = new Channel(this);
		channels[1] = new Channel(this);
		
		Main.dbg.printDebug(2, this + " starting up, but idle.");
		

		
		/* >>> EVENT LOOP
		 * When the application starts, we start at Main which creates and runs a Simulator thread, which creates a Chronotimer thread.
		 * The chronotimer thread will go execute this while loop every GRANULARITY miliseconds.
		 */ while (true){
			
			if (powerOn) Main.dbg.printDebug(2, this + " switched on!");
			while (powerOn){
				//Main.dbg.printDebug(3, "[CLOCK EMOJI]" + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));

				// Goes through all pending commands until the queue is empty.
				while (cmdQueue.isEmpty() == false) {
					System.out.println(2);

					// Simulator runs the command
					cmdQueue.poll().execute();
					
					try { Thread.sleep(100); }
					catch (InterruptedException e) { e.printStackTrace(); }
					
				}
				
							
				try { Thread.sleep(Main.GRANULAITY);} 
				catch (InterruptedException e) { e.printStackTrace(); }

			}
			
			// This is going to repeat until it's switched on.
			Main.dbg.printDebug(2, this + " switched off!");
			
			while (cmdQueue.isEmpty() == false) {
				System.out.println(2);

				// Simulator runs the command
				cmdQueue.poll().execute();
				
				try { Thread.sleep(100); }
				catch (InterruptedException e) { e.printStackTrace(); }
				
			}
			
			
			try { Thread.sleep(1000);} 
			catch (InterruptedException e) { e.printStackTrace(); } 
		}
		


	}
	
	public void score(ActionEvent e){
		if (e.getSource().equals(channels[0])){ // if start is tripped
			try{
				Racer popped = toRace.pop();
				popped.t.startTime();
				racers.push(popped);
			}
			catch (NoSuchElementException err){
				Main.dbg.printDebug(0, "No racers are ready to start!");
			}
			
			
		} else if (e.getSource().equals(channels[1])){ // if finish is tripped
			
			Racer popped = racers.pop();
			popped.t.stopTime();
			finished.add(popped);
			
		}
	}


/*
 * A "command" object. args[0] is the command itself (PRINT, NEWRUN, etc) while
 * args[1] and args[2] are the arguments.
 * There are two constructors. Both are functionally identical.
 * 	- One takes a String array of arguments, like classic command parsers.
 * 	- The other takes a single string, which parses according to the format "COMMAND ARG1 ARG2"
 * When a Command is constructed, the Command adds itself to the command queue.
 */ 
 // move to CT?
	
	
	public boolean toCommand(String cmd){
		new Command(cmd);
		return true;
	}

 public class Command {

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
		Main.dbg.printDebug(2, this.command);

		cmdQueue.add(this); 
	}

	
	/* Run the command on the Simulator's ChronoTimer
	 */ public boolean execute() {
		
		Main.dbg.printDebug(2, "EXECUTING " + command + " " + arg1 + " " + arg2);

		switch (command.toUpperCase()) {
		case "POWER":

			// Should toggle the power.
			powerOn = powerOn ? false : true;
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
			int hour, min, sec;
			String[] input = arg1.split(":");
			
			// POSSIBLE BUG: check here to make sure there are no exceptions for misformatted strings				
			
			if (input.length == 2){
				min		= Integer.valueOf(input[0]);
				sec		= Integer.valueOf(input[1]);
				Main.dbg.printDebug(3, "MIN: " + min + "  SEC: " + sec);
				
			}else if (input.length == 3){
				hour	= Integer.valueOf(input[0]);
				min		= Integer.valueOf(input[1]);
				sec		= Integer.valueOf(input[2]);
				Main.dbg.printDebug(3, "HOUR: " + hour + "  MIN: " + min + "  SEC: " + sec);
			} else {
				System.out.println("Inproper formatting! Proper Usage is HOUR:MIN:SEC, ie 3:00:00");
			}
			
			
			break;

		case "TOG":
			// command arg1 arg2
			int c = Integer.parseInt(arg1);
			channels[c].toggle();
			break;

		case "CONN":

			break;

		case "DISC":

			break;

		case "EVENT":

			break;

		case "NEWRUN":

			break;

		case "ENDRUN":

			break;

		case "PRINT":
			for (Racer r : finished){
				long time = r.t.runTime();
				if (time == Long.MAX_VALUE){
					System.out.println("Racer " + r.bib + " DNF");
				}
				else {
					System.out.println("Racer " + r.bib + " " + r.t.runTime() + " ms");
				}
			}
			break;

		case "EXPORT":

			break;

		case "NUM":
			toRace.add(new Racer(Integer.parseInt(arg1)));
			break;

		case "CLR":

			break;

		case "SWAP":
			ArrayList<Racer> list = new ArrayList<Racer>(racers);
			Collections.swap(list, 0, 1);
			Deque<Racer> newQ = new ArrayDeque<Racer>();
			for (Racer r : list) newQ.push(r);
			racers = newQ;
			break;

		case "DNF":
			// TODO test/fix
			finished.add(racers.pop());
			break;
			
		case "CANCEL":
			// TODO test/fix
			toRace.push(racers.pop());
			break;

		case "TRIG":
			// TODO test/fix
			// Find the sensor object associated with arg1
			// Send a trigger command to it
			int chan = Integer.parseInt(arg1);
			channels[chan].trigger();
			break;

		case "START":
			channels[0].trigger();
			break;

		case "FINISH":
			channels[1].trigger();
			break;
		
		case "DEBUG":
			Main.MAX_VERBOSITY = Integer.parseInt(arg1);
			for (int i = 0; i <= 3; i++) Main.dbg.printDebug(i, "MAX_VERBOSITY CHANGED -- Debug level ["+ i + "] messages active");
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
				"DEBUG	Change the debug output's verbosity.				FORMAT: <1...3>\n"
			);
			break;
			
			
		default:
			Main.dbg.printDebug(0, "UNSUPPORTED COMMAND: " + this);
		}

		return true;

	}
}
}