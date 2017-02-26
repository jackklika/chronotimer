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
				
				//TODO: check
				new Command(input);
				
				//cmdQueue.add(toCommand(input));
				
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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

	 
	 /* Takes a string and converts it to a command object
	  * Example: CONN EYE 1 --> new Command(new String[]{"CONN", "EYE", "1"})
	  * 
	  */ public Command toCommand(String str){	
		  return new Command(str.split(" "));
	 	}
	  
	  
	/*
	 * A "command" object. args[0] is the command itself (PRINT, NEWRUN, etc) while
	 * args[1] and args[2] are the arguments.
	 */ public class Command {

		String command = "", arg1 = "", arg2 = "";

		public Command(String[] args) {
			command = args[0];
			if (args.length > 1)
				arg1 = args[1];
			if (args.length > 2)
				arg2 = args[2];
			
			cmdQueue.add(this);

		}
		
		public Command(String cmd){
			
			// TODO: Sanitzie / check input here

			Main.dbg.printDebug(3, "Command Constructor in: " + cmd);
			String[] args = cmd.split(" ");
			command = args[0];
			if (args.length > 1)
				arg1 = args[1];
			if (args.length > 2)
				arg2 = args[2];
			Main.dbg.printDebug(3, "Command Constructor stored: " + Arrays.toString(cmd.split(" ")));
			Main.dbg.printDebug(2, this.command);

			cmdQueue.add(this);
		}
		
		public boolean execute() {

			/*
			 * Run the command on the Simulator's ChronoTimer
			 */
			
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
				timer.powerOn = true;
				break;

			case "TIME":

				break;

			case "TOG":

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
