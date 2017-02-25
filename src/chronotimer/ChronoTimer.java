package chronotimer;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

enum RaceType {
	IND, PARIND, GRP, PARGRP
}


public class ChronoTimer implements Runnable {
	
	// This is the stack of commands.
	// Every time the event loop runs, the queue is processed.
	private Queue<Command> cmdQueue = new LinkedList<Command>();
	
	
	// A "command". args[0] is the command itself (PRINT, NEWRUN, etc) 
	// 		while args[1] and args[2] are the arguments.
	public class Command {
		
		String command; // EXPORT, NEWRUN, etc.
		String arg1, arg2;
		
		
		public Command(String[] args){
			command = args[0];
			if (args[1] != null) arg1 = args[1];
			if (args[2] != null) arg2 = args[2];
		}
	}

	// Sends a command to this particular ChonoTimer object
	// Returns true if command is valid, false if not.
	// TODO: THIS DOESN'T CURRENTLY WORK. The problem is that it does not parse command arguments
	public boolean cmd(Command cmd){
		
		switch (cmd.command.toUpperCase()){
		case "POWER":
			
			break;

		case "EXIT":
			//cleanup?
			System.exit(0);
			break;

		case "RESET":
			
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

		}
		
		return true;
		
	}
	
	// Provides an entry point for the ChronoTimer thread.
	// Please read about "Java Threads"
	public void run() {
		
		Main.dbg.printDebug(2, this + " starting up!");
		

		/*
		 * >>> EVENT LOOP
		 * When the application start, we start at Main which creates and runs a Chronotimer thread.
		 * The chronotimer thread will go execute this while loop every 1 second.
		 */
		while (true){
			Main.dbg.printDebug(3, "🕐  " + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));

			// Goes through all pending commands until the queue is empty.
			while ( !cmdQueue.isEmpty() ){
				this.cmd(cmdQueue.poll());
			}
			
			
			try {
				Thread.sleep(Main.GRANULAITY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
}
