package chronotimer;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

/*
 * >>> COMMANDS:
 * POWER	Turn on and enter idle state or turn system off but stay in simulator
 * EXIT		Exit the simulator
 * RESET	Resets the System to initial state
 * TIME		Set the current time 							FORMAT: "<hour>:<min>:<sec>"
 * TOG		toggle state of channel 						FORMAT: "<channel>"
 * CONN		connect a type of sensor to channel <num>		FORMAT: "<sensor> <num>"
 * DISC 	disconnect sensor from channel <num>			FORMAT: "<num>"
 * EVENT	type of event (IND, PARIND, GRP, PARGRP)		FORMAT: "<eventcode>"
 * NEWRUN	creates a new run
 * ENDRUN	done with a run
 * PRINT	Prints the run on stdout
 * EXPORT	Exports the run in XML to file "RUN<RUN>"		FORMAT: "<run>"
 * NUM		Set <num> as the next competitor to start		FORMAT: "<num>"
 * CLR		Clear competitor number <num>					FORMAT: "<num>"
 * SWAP		Exchange next to compentitors to finish in IND
 * DNF 		Next competitor to finish will not finish
 * TRIG		Trigger channel <num>							FORMAT: "<num>"
 * START	Start trigger channel 1 -- macro for TRIG 1
 * FINISH	Finish trigger channel 2 -- macro for TRIG 2
 */

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
					Thread.sleep(10);
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
			default:
				Main.dbg.printDebug(1, "UNSUPPORTED COMMAND: " + this);
			}

			return true;

		}
	}

}
