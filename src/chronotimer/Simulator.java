package chronotimer;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;



public class Simulator implements Runnable {

	
	class Shell implements Runnable {
		String input = "";
		public void run() {
			Scanner in = new Scanner(System.in);
			while (true){
				System.out.print(">");
				input = in.nextLine();
				// Check if input is valid command. If it is, add to queue
				
				//The command constructor adds the command to the queuet
				new Command(input);
				
				//cmdQueue.add(toCommand(input));
				
				try { Thread.sleep(300); } 
				catch (InterruptedException e) { e.printStackTrace(); }
			}
			
		}
		
	}
	
	ChronoTimer timer;
	Shell sh;

	public Simulator(ChronoTimer timer) {
		this.sh = new Shell();
		this.timer = timer;
	}

	public void run() {
		Thread shell = new Thread(sh);
		Thread time = new Thread(timer);
		shell.start();
		time.start(); // starts the timer

		while (true){
			
			while (cmdQueue.isEmpty() == false) {
				System.out.println(2);

				// Simulator runs the command
				cmdQueue.poll().execute();
				
				try { Thread.sleep(100); }
				catch (InterruptedException e) { e.printStackTrace(); }
				
			}
			try { Thread.sleep(1000); }
			catch (InterruptedException e) { e.printStackTrace(); }

		}
		
	}

	/*
	 * This is the stack of commands. Every time the event loop runs, the queue
	 * is processed.
	 */ public Queue<Command> cmdQueue = new LinkedList<Command>();	  
	  
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
			Main.dbg.printDebug(2, this.command);

			cmdQueue.add(this);
		}

		
		/* Run the command on the Simulator's ChronoTimer
		 * This is where a lot of logic is. The ChronoTimer will probably have a lot of handlers for it.
		 */ public boolean execute() {
			
			Main.dbg.printDebug(2, "EXECUTING " + command + " " + arg1 + " " + arg2);

			switch (command.toUpperCase()) {
			case "POWER":

				// Should toggle the power.
				timer.powerOn = timer.powerOn ? false : true;
				break;

			case "EXIT":
				// cleanup?
				System.exit(0);
				break;

			case "RESET":

				timer.powerOn = false;
				try {
					Thread.sleep(1100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				timer.powerOn = true;
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
				timer.channels[c].toggle();
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

				break;

			case "EXPORT":

				break;

			case "NUM":

				break;

			case "CLR":

				break;

			case "SWAP":

				break;

			case "DNF":

				break;

			case "TRIG":

				break;

			case "START":

				break;

			case "FINISH":

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
