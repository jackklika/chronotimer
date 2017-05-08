import java.awt.event.ActionEvent;
import java.util.*;

import com.google.gson.Gson;

import javafx.scene.control.TextArea;
import llm.lab7.client.DirectoryProxy;
import llm.lab7.server.DirectoryServer;

import java.time.Instant;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

enum RaceType {
	IND, PARIND, GRP, PARGRP
}

/**
 * The main class. This represents the inner workings of the Chronotimer.
 * 
 * @author LLM
 *
 */
public class ChronoTimer implements Runnable {

	public Channel[] channels;
	public boolean powerOn = false;

	ArrayList<Race> raceList = new ArrayList<Race>();
	Race currentRace;
	RaceType raceType;

	TextArea raceDisplay;

	public Queue<Command> cmdQueue;

	public ChronoTimer() {
		channels = new Channel[8];
		cmdQueue = new LinkedList<Command>();
	}

	/**
	 * The entry point for the chronotimer when its thread begins.
	 */
	public void run() {

		// Create new channels
		for (int i = 0; i <= 7; i++) {
			channels[i] = new Channel(this);
		}

		Main.dbg.printDebug(1, "Welcome to Chronotimer. Use the GUI or type 'HELP' in the console.");

		/*
		 * EVENT LOOP When the application starts, we start at Main which
		 * creates and runs a Simulator thread, which creates a Chronotimer
		 * thread. The chronotimer thread will go execute this while loop every
		 * GRANULARITY miliseconds.
		 */
		while (true) {
			while (powerOn) {

				// Handles the scrolling on the gui
				if (raceDisplay != null && currentRace != null) {
					double scroll = raceDisplay.getScrollTop();
					raceDisplay.setText(currentRace.toString()); // current race
																	// output
					raceDisplay.setScrollTop(scroll);
				}

				// Goes through all pending commands until the queue is empty.
				while (cmdQueue.isEmpty() == false) {

					// Simulator runs the command
					cmdQueue.poll().execute();

					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}

				try {
					Thread.sleep(Main.GRANULAITY);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

			// Runs the commands in the command queue.
			while (cmdQueue.isEmpty() == false) {

				// Simulator runs the command
				cmdQueue.poll().execute();

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Handles scoring by race type.
	 */
	public void score(ActionEvent e) {

		if (currentRace == null || currentRace.currentRaceType == null) {
			Main.dbg.printDebug(0, "[ERR] score was called while currentRace or its racetype is null.");
			return;
		}

		switch (currentRace.currentRaceType) {
		case IND:
			// if start is tripped
			if (e.getSource().equals(channels[0])) {
				try {
					Racer popped = currentRace.toRace.pollFirst();
					popped.t.startTime();
					currentRace.inRace.addLast(popped);
				} catch (NoSuchElementException err) {
					Main.dbg.printDebug(0, "[WARN] No racers are ready to start!");
				} catch (NullPointerException ex) {
					Main.dbg.printDebug(0, "[WARN] Noone to start! Add more racers or start finishing.");
				}

				// if finish is tripped
			} else if (e.getSource().equals(channels[1])) { 

				try {
					Racer popped = currentRace.inRace.pollFirst();
					popped.t.stopTime();
					currentRace.finishRace.add(popped);
				} catch (NoSuchElementException err) {
					Main.dbg.printDebug(0, "[WARN] No racers are ready to finish!");
				} catch (NullPointerException err) {
					Main.dbg.printDebug(0, "[WARN] No racers are ready to finish!");
				}

			}
			break;
		case PARIND:
			// if start is tripped
			if (e.getSource().equals(channels[0]) || e.getSource().equals(channels[2])) {
				try {
					Racer popped = currentRace.toRace.pollFirst();
					popped.t.startTime();
					currentRace.inRace.addLast(popped);
				} catch (NoSuchElementException err) {
					Main.dbg.printDebug(0, "[WARN] No racers are ready to start!");
				} catch (NullPointerException ex) {
					Main.dbg.printDebug(0, "[WARN] Noone to start! Add more racers or start finishing.");
				}

				// if finish is tripped
			} else if (e.getSource().equals(channels[1]) || e.getSource().equals(channels[3])) { 

				try {
					Racer popped = currentRace.inRace.pollFirst();
					popped.t.stopTime();
					currentRace.finishRace.add(popped);
				} catch (NoSuchElementException err) {
					Main.dbg.printDebug(0, "[ERR] No racers are ready to finish!");
				} catch (NullPointerException err) {
					Main.dbg.printDebug(0, "[ERR] No racers are ready to finish!");
				}

			}
			break;
		case GRP:
			if (e.getSource().equals(channels[0])) { // if start is tripped
				currentRace.startTime = Instant.now().toEpochMilli() + Time.currentMs;

			}

			else if (e.getSource().equals(channels[1])) { // if finish is tripped
				int i = 1;
				if (!(currentRace.finishRace.isEmpty())) {
					int s = currentRace.finishRace.size();
					i = currentRace.finishRace.get(s - 1).bib + 1;
				}
				Racer r = new Racer(i);
				r.t.start = currentRace.startTime;
				r.finish();
				currentRace.finishRace.add(r);
			}
			break;
		case PARGRP:
			if (currentRace.startTime == 0 && e.getSource().equals(channels[0])) {
				currentRace.startTime = Instant.now().toEpochMilli() + Time.currentMs;
				currentRace.inRace.addAll(currentRace.toRace);
				currentRace.toRace.removeAll(currentRace.inRace);
			} else {
				if (e.getSource().equals(channels[0]) && channels[0].getState()) {
					Racer r = currentRace.removeBib(1);
					r.t.start = currentRace.startTime;
					r.finish();
					currentRace.finishRace.add(r);
				} else if (e.getSource().equals(channels[1]) && channels[1].getState()) {
					Racer r = currentRace.removeBib(2);
					r.t.start = currentRace.startTime;
					r.finish();
					currentRace.finishRace.add(r);
				} else if (e.getSource().equals(channels[2]) && channels[2].getState()) {
					Racer r = currentRace.removeBib(3);
					r.t.start = currentRace.startTime;
					r.finish();
					currentRace.finishRace.add(r);
				} else if (e.getSource().equals(channels[3]) && channels[3].getState()) {
					Racer r = currentRace.removeBib(4);
					r.t.start = currentRace.startTime;
					r.finish();
					currentRace.finishRace.add(r);
				} else if (e.getSource().equals(channels[4]) && channels[4].getState()) {
					Racer r = currentRace.removeBib(5);
					r.t.start = currentRace.startTime;
					r.finish();
					currentRace.finishRace.add(r);
				} else if (e.getSource().equals(channels[5]) && channels[5].getState()) {
					Racer r = currentRace.removeBib(6);
					r.t.start = currentRace.startTime;
					r.finish();
					currentRace.finishRace.add(r);
				} else if (e.getSource().equals(channels[6]) && channels[6].getState()) {
					Racer r = currentRace.removeBib(7);
					r.t.start = currentRace.startTime;
					r.finish();
					currentRace.finishRace.add(r);
				} else if (e.getSource().equals(channels[7]) && channels[7].getState()) {
					Racer r = currentRace.removeBib(8);
					r.t.start = currentRace.startTime;
					r.finish();
					currentRace.finishRace.add(r);
				}
			}
			break;

		default:
			Main.dbg.printDebug(0, "[ERR] score called while racetype is invalid.");
			break;
		}
	}

	/**
	 * A quick command to create a command.
	 */
	public Command toCommand(String cmd) {
		return new Command(cmd);
	}

	/**
	 * A "command" object. 
	 * args[0] is the command itself (PRINT, NEWRUN, etc), while
	 * args[1] and args[2] are the arguments.
	 * The constructor takes a single string, which parses according to the format "COMMAND ARG1 ARG2" 
	 *  
	 * IMPORTANT: When a Command is constructed, the Command adds itself to the command queue.
	 */
	public class Command {

		String command = "", arg1 = "", arg2 = "";

		// Takes string of format "COMMAND ARG1 ARG2"
		public Command(String cmd) {

			// If the power is off, don't process anything besides POWER commands.
			if (powerOn == false && cmd.equalsIgnoreCase("POWER") == false) {
				return;
			}

			Main.dbg.printDebug(3, "Command Constructor in: " + cmd);
			String[] args = cmd.split(" ");
			command = args[0];
			if (args.length > 1)
				arg1 = args[1];
			if (args.length > 2)
				arg2 = args[2];

			Main.dbg.printDebug(3, "Command Constructor stored: " + Arrays.toString(cmd.split(" ")));
			Main.dbg.printDebug(0, Time.printTime() + "\t" + this.command + " " + arg1 + " " + arg2);

			// Every created command is added to the command queue.
			cmdQueue.add(this);
		}

		/**
		 * Calling execute on a command will run the function associated with it.
		 * @return true if the command is a valid command, false otherwise.
		 */
		public boolean execute() {

			Main.dbg.printDebug(3, "EXECUTING " + command + " " + arg1 + " " + arg2);

			switch (command.toUpperCase()) {
			case "POWER":

				// Should toggle the power.
				powerOn = powerOn ? false : true;
				if (powerOn)
					Main.dbg.printDebug(1, "ChronoTimer switched on!");
				else
					Main.dbg.printDebug(1, "ChronoTimer switched off!");
				break;

			case "EXIT":
				// cleanup?
				System.exit(0);
				break;

			case "RESET":
				powerOn = false;
				try {
					Thread.sleep(1100);
				} // Stops just long enough for Chronotimer to loop
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				powerOn = true;
				break;

			case "TIME":

				Main.dbg.printDebug(3, "INPUT TO TIME COMMAND: " + arg1);
				int hour, min;
				double sec;
				String[] input = arg1.split(":");

				try {

					if (input.length != 3) {
						System.out.println("Inproper formatting! Proper Usage is HOUR:MIN:SEC, ie 3:00:00.1");
						break;
					}

					if (input.length == 2) {
						min = Integer.valueOf(input[0]);
						sec = Double.valueOf(input[1]);
						Time.setTime(0, min, sec);
						Main.dbg.printDebug(3, "MIN: " + min + "  SEC: " + sec);

					} else if (input.length == 3) {
						hour = Integer.valueOf(input[0]);
						min = Integer.valueOf(input[1]);
						sec = Double.valueOf(input[2]);
						Main.dbg.printDebug(3, "HOUR: " + hour + "  MIN: " + min + "  SEC: " + sec);
						Time.setTime(hour, min, sec);

					} else {
						System.out.println("Inproper formatting! Proper Usage is HOUR:MIN:SEC, ie 3:00:00.1");
					}
				} catch (NumberFormatException e) {
					System.out.println("Inproper formatting! Proper Usage is HOUR:MIN:SEC, ie 3:00:00.1");
					break;
				}

				break;

			case "TOG":
				// command arg1 arg2
				int c = Integer.parseInt(arg1);

				// Handles channels as starting at 1 vs. arrays starting at 0.
				if (channels[c - 1] != null) {
					channels[c - 1].toggle();
					String state = channels[c - 1].getState() ? "on" : "off";
					if (currentRace.currentRaceType == RaceType.PARGRP && channels[c - 1].getState()) {
						currentRace.toRace.add(new Racer(c));
					}
					Main.dbg.printDebug(1, "Channel " + Integer.toString(c) + " is now toggled " + state); 
																											
				} else {
					Main.dbg.printDebug(0, "You need to initialize channnels");
				}
				break;

			// arg1 is sensortype, arg2 is number of sensor
			case "CONN":
				int sensorNum = Integer.parseInt(arg2);
				Sensor s;
				if (arg1.equals("GateSensor")) {
					s = new PadSensor(channels[sensorNum - 1]);

				} else if (arg1.equalsIgnoreCase("EyeSensor")) {
					s = new EyeSensor(channels[sensorNum - 1]);

				} else if (arg1.equalsIgnoreCase("PadSensor")) {
					s = new PadSensor(channels[sensorNum - 1]);

				} else {
					Main.dbg.printDebug(0, "[ERR] Invalid sensor type.");
					break;
				}
				channels[sensorNum - 1].setSensor(s);
				Main.dbg.printDebug(3, arg1 + " " + sensorNum + " connected.");

				break;

			// arg1 is sensor number
			case "DISC":
				int sensorNumm = Integer.parseInt(arg1);

				channels[sensorNumm - 1].s.disconnect();

				Main.dbg.printDebug(3, "Sensor " + sensorNumm + " disconnected.");

				break;

			case "EVENT":
				if (arg1 == null || arg1 == "") {
					Main.dbg.printDebug(0, "[ERR] Please specify a race type. See 'HELP'.");
					break;
				}
				switch (arg1.toUpperCase()) {
				case "IND":
					raceType = RaceType.IND;
					Main.dbg.printDebug(1, "Event set to IND");
					channels[0] = new Channel(ChronoTimer.this);
					channels[1] = new Channel(ChronoTimer.this);
					if (currentRace != null) {
						currentRace.currentRaceType = RaceType.IND;
					}
					break;

				case "PARIND":
					raceType = RaceType.PARIND;
					Main.dbg.printDebug(1, "Event set to PARIND");
					for (int i = 0; i < 4; i++)
						channels[i] = new Channel(ChronoTimer.this);
					if (currentRace != null) {
						currentRace.currentRaceType = RaceType.PARIND;
					}
					break;

				case "GRP":
					raceType = RaceType.GRP;
					Main.dbg.printDebug(1, "Event set to GRP");
					channels[0] = new Channel(ChronoTimer.this);
					channels[1] = new Channel(ChronoTimer.this);
					if (currentRace != null) {
						currentRace.currentRaceType = RaceType.GRP;
					}
					break;

				case "PARGRP":
					raceType = RaceType.PARGRP;
					Main.dbg.printDebug(1, "Event set to PARGRP");
					for (int i = 0; i < 8; i++)
						channels[i] = new Channel(ChronoTimer.this);
					if (currentRace != null) {
						currentRace.currentRaceType = RaceType.PARGRP;
					}
					break;

				default:
					Main.dbg.printDebug(0, "[ERR] Unsupported race type '" + arg1 + "'. Use the HELP command.");
					break;
				}
				break;

			case "NEWRUN":

				if (raceType == null) {
					Main.dbg.printDebug(0, "[WARN] No racetype selected. Use EVENT or HELP for more info.");
					break;

				} else if (currentRace != null && currentRace.raceEnded == false) {
					Main.dbg.printDebug(0, "Race is not ended. Use ENDRUN to end the race.");
					break;

				} else if (currentRace != null) {
					raceList.add(currentRace); // Saves the current race into an array.
				}

				if (raceType == RaceType.IND) {
					currentRace = new Race(raceType);
					Main.dbg.printDebug(1,
							"New IND race created: " + currentRace.hashCode() + ", race #" + currentRace.raceNum);

				} else if (raceType == RaceType.PARIND) {
					currentRace = new Race(raceType);
					Main.dbg.printDebug(1, "New PARIND race created");

				} else if (raceType == RaceType.GRP) {
					currentRace = new Race(raceType);
					Main.dbg.printDebug(1, "New GRP race created");

				} else if (raceType == RaceType.PARGRP) {
					currentRace = new Race(raceType);
					Main.dbg.printDebug(1, "NEW PARGRP race created");

				}
				break;

			case "ENDRUN":

				if (currentRace == null) {
					Main.dbg.printDebug(0, "[ERR] no race to end.");
					break;
				}

				currentRace.raceEnded = true;
				if (raceType == RaceType.GRP || raceType == RaceType.PARGRP)
					currentRace.finishTime = Instant.now().toEpochMilli() + Time.currentMs;

				// This is what will be displayed in the webserver as the time.
				for (Racer r : currentRace.finishRace) {
					r.prettyTime = Time.convert(r.t.runTime());
				}
				if (raceType == RaceType.GRP)
					currentRace.finishTime = Instant.now().toEpochMilli() + Time.currentMs;
				Main.dbg.printDebug(1, String.format("Race %d was set to finished.", currentRace.raceNum));
				Gson gson = new Gson();
				String json = gson.toJson(currentRace.finishRace);
				
//				try (PrintStream out = new PrintStream(new FileOutputStream("RUN" + currentRace.raceNum + ".json"))) {
//					out.print(json);
//					out.flush();
//					out.close();
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
				// Client will connect to this location
				URL site;
				try {
					site = new URL("http://localhost:8000/sendresults");
					HttpURLConnection conn = (HttpURLConnection) site.openConnection();

					// now create a POST request
					conn.setRequestMethod("POST");
					conn.setDoOutput(true);
					conn.setDoInput(true);
					DataOutputStream out = new DataOutputStream(conn.getOutputStream());
					out.writeBytes(json);
					out.flush();
					out.close();
					
					InputStreamReader inputStr = new InputStreamReader(conn.getInputStream());

					// string to hold the result of reading in the response
					StringBuilder sb = new StringBuilder();

					// read the characters from the request byte by byte and build up
					// the Response
					int nextChar;
					while ((nextChar = inputStr.read()) > -1) {
						sb = sb.append((char) nextChar);
					}
					System.out.println("Return String: " + sb);
					
				} catch (Exception ex){
					Main.dbg.printDebug(0, "[ERR] HTTP/JSON Problems.");
					ex.printStackTrace();
				}
				
				
				break;

			case "PRINT":
				try {
					if (currentRace.finishRace.isEmpty()) {
						Main.dbg.printDebug(0, "[WARN] No racers found in finishing queue.");
					} else {
						for (Racer r : currentRace.finishRace) {
							long time = r.t.runTime();
							if (time == Long.MAX_VALUE) {
								Main.dbg.printDebug(0, "Racer " + r.bib + " DNF");
							} else {
								Main.dbg.printDebug(0, "Racer " + r.bib + " " + (r.t.runTime()) / 1000.0 + " seconds");
							}
						}
					}
				} catch (NullPointerException ex) {
					Main.dbg.printDebug(0, "[WARN] Could not print. Did you initialize the race or racers?");
				}
				break;

			case "EXPORT":

				break;

			case "NUM":

				boolean duplicate = false;

				try {
					if (arg1 == null || arg1.equals("")) {
						Main.dbg.printDebug(0, "[ERR] Please enter a bib number.");
						break;
					}

					if (currentRace == null || currentRace.currentRaceType == null) {
						Main.dbg.printDebug(0,
								"[ERR] No race type selected. Define events with 'EVENT' first, or see 'HELP'");
					} else if (currentRace.currentRaceType == RaceType.IND
							|| currentRace.currentRaceType == RaceType.PARIND) {
						int bib = Integer.parseInt(arg1);
						try {
							for (Racer r : currentRace.toRace) {
								if (r.bib == bib) {
									Main.dbg.printDebug(0, "[ERR] A racer already has bib " + bib + ".");
									duplicate = true;
									break;
								}
							}
							if (duplicate)
								break;

							currentRace.toRace.add(new Racer(bib));
							Main.dbg.printDebug(1, "Racer " + bib + " added");
						} catch (Exception ex) {
							Main.dbg.printDebug(0, "[ERR] Not a valid number, or race was incorrectly created.");
						}
					} else if (currentRace.currentRaceType == RaceType.GRP
							|| currentRace.currentRaceType == RaceType.PARGRP) {
						if (currentRace.raceEnded == false) {
							Main.dbg.printDebug(0, "[ERR] End run before numbering contestants. in GRP races.");
						} else {
							currentRace.giveBib(Integer.parseInt(arg1));
						}
					}
				} catch (NumberFormatException e) {
					Main.dbg.printDebug(0, "[ERR] Please enter a valid bib number.");
				}

				break;

			case "CLR":

				try {
					if (currentRace != null) {
						if (currentRace.removeBib(Integer.parseInt(arg1)) != null) {
							Main.dbg.printDebug(3, "Racer removed.");
						} else {
							Main.dbg.printDebug(0, "[WARN] No racer removed. Could not find racer.");
						}
					}
				} catch (NumberFormatException e) {
					Main.dbg.printDebug(0, "[ERR] Please enter a valid bib number.");
				}

				break;

			case "SWAP":
				// Only functions during IND races (according to spec)
				if (currentRace == null) {
					Main.dbg.printDebug(0, "[ERR] Cannot SWAP when there's no race!");
					break;
				}

				if (currentRace.currentRaceType == RaceType.IND || currentRace.currentRaceType == RaceType.PARIND || currentRace.inRace.size() ==1) {
					ArrayList<Racer> list = new ArrayList<Racer>(currentRace.inRace);
					Collections.swap(list, 0, 1);
					Deque<Racer> newQ = new ArrayDeque<Racer>();
					for (Racer r : list)
						newQ.push(r);
					currentRace.inRace = newQ;
				} else {
					Main.dbg.printDebug(0, "[ERR] Only works during IND races.");
				}

				break;

			case "DNF":
				if (currentRace == null || currentRace.currentRaceType == null) {
					Main.dbg.printDebug(0, "[ERR] Cannot DNF when there's no race or race type!!");
					break;
				}
				if (currentRace.currentRaceType != RaceType.GRP || currentRace.currentRaceType != RaceType.PARGRP) {
					if(!(currentRace.inRace.isEmpty() ||currentRace.inRace == null)){
					Racer r = currentRace.inRace.pollFirst();
					r.runTime = Long.MAX_VALUE;
					currentRace.finishRace.add(r);
					}
				}
				break;

			case "CANCEL":
				if (currentRace == null) {
					Main.dbg.printDebug(0, "[ERR] Cannot CANCEL when there's no race!");
					break;
				}

				if (currentRace.currentRaceType != RaceType.GRP)
					currentRace.toRace.push(currentRace.inRace.pollLast());
				break;

			case "TRIG":
				// Find the sensor object associated with arg1
				// Send a trigger command to it

				try {
					int chan = Integer.parseInt(arg1);
					channels[(chan - 1)].trigger(); 

					Main.dbg.printDebug(2, "Channel " + (chan) + " tripped!"); 
				} catch (NumberFormatException e) {
					Main.dbg.printDebug(0, "[ERR] Please enter a valid number.");
				}

				break;

			case "START":
				if (currentRace.currentRaceType == RaceType.IND) {
					channels[0].trigger();
				} else if (currentRace.currentRaceType == RaceType.PARIND) {
					if (channels[0] != null && channels[0].getState())
						channels[0].trigger();
					if (channels[2] != null && channels[2].getState())
						channels[2].trigger();
				}
				break;

			case "FINISH":
				if (currentRace.currentRaceType == RaceType.IND || currentRace.currentRaceType == RaceType.GRP) {
					channels[1].trigger();
				} else if (currentRace.currentRaceType == RaceType.PARIND) {
					if (channels[1] != null && channels[1].getState())
						channels[1].trigger();
					if (channels[3] != null && channels[3].getState())
						channels[3].trigger();
				}
				break;

			case "DEBUG":
				Main.MAX_VERBOSITY = Integer.parseInt(arg1);
				for (int i = 0; i <= 3; i++)
					Main.dbg.printDebug(i, "MAX_VERBOSITY CHANGED -- Debug level [" + i + "] messages active");
				break;

			case "LIST":
				if (currentRace != null) {
					Main.dbg.printDebug(0, currentRace.toString());
				} else { // handles null pointer exception
					Main.dbg.printDebug(0, "Race hasn't started yet.");
				}
				break;
				
			case "SERVER":
				Main.dbg.printDebug(0, "Server now on!");
				
				DirectoryServer ds = new DirectoryServer();
				
				break;

			case "HELP":
				System.out.println(">>> COMMANDS:\n"
						+ "POWER	Turn on and enter idle state or turn system off but stay in simulator\n"
						+ "EXIT	Exit the simulaton\n" + "RESET	Resets the System to initial state\n"
						+ "TIME	Set the current time 						FORMAT: <hour>:<min>:<sec>\n"
						+ "TOG	toggle state of channel 					FORMAT: <channel>\n"
						+ "CONN	connect a type of sensor to channel <num>			FORMAT: <sensor> <num>\n"
						+ "DISC 	disconnect sensor from channel <num>				FORMAT: <num>\n"
						+ "EVENT	type of event (IND, PARIND, GRP, PARGRP)			FORMAT: <eventcode>\n"
						+ "NEWRUN	creates a new run\n" + "ENDRUN	done with a run\n"
						+ "PRINT	Prints the run on stdout\n"
						+ "EXPORT	Exports the run in XML to file RUN<RUN>				FORMAT: <run>\n"
						+ "NUM	Set <num> as the next competitor to start			FORMAT: <num>\n"
						+ "LR	Clear competitor number <num>					FORMAT: <num>\n"
						+ "SWAP	Exchange next to compentitors to finish in IND\n"
						+ "DNF 	Next competitor to finish will not finish\n"
						+ "TRIG	Trigger channel <num>						FORMAT: <num>\n"
						+ "CONN	Attaches channel <num> to a style of sensor <sensortype>		FORMAT: <sensortype> <num>\n"
						+ "DISC	Disconnects sensor <num> connected with CONN.		FORMAT: <num>\n"
						+ "START	Start trigger channel 1 -- macro for TRIG 1\n"
						+ "FINISH	Finish trigger channel 2 -- macro for TRIG 2\n"
						+ "DEBUG	Change the debug output's verbosity.				FORMAT: <0...3>\n"
						+ "LIST	Show the current race, their queues, and racers.\n");
				break;

			default:
				Main.dbg.printDebug(0, "UNSUPPORTED COMMAND: " + this);
				return false;
			}

			return true;

		}
	}
}
