import java.util.*;

public class Race {

	public long startTime;
	public Deque<Racer> toRace; // Haven't started yet, in line
	public Deque<Racer> inRace; // In the race
	public ArrayList<Racer> finishRace; // Finished the race.
	public RaceType currentRaceType;
	public boolean raceEnded = false; // True if the race is ended.

	private Integer finishLength;
	private int index;

	// Races are numbered.
	public int raceNum;
	public static int maxRaceNum = 1;

	public Race(RaceType raceType) {
		toRace = new LinkedList<Racer>();
		inRace = new LinkedList<Racer>();
		finishRace = new ArrayList<Racer>();
		raceNum = maxRaceNum++;
		currentRaceType = raceType;
	}

	@Override
	public String toString() {

		String out = "";
		if (currentRaceType == currentRaceType.IND) {
			out += "** Race " + currentRaceType + " #" + raceNum + " ***\n";
			//out += "Race Ended? " + raceEnded + "\n\n";
			//out += "Pending Racers: ";
			for (Racer r : toRace)
				out += r.bib + " [" + r.t.runTime() + ((toRace.getFirst() == r) ? "] <\n" : "]\n");
			out += "\n";
			for (Racer r : inRace)
				out += r.bib + " [" + r.t.runTime() + "]\n";
			out += "\n";

			for (Racer r : finishRace)
				out += r.bib + " [" + r.t.runTime() + "]\n";

		} else if (currentRaceType == currentRaceType.PARIND) {

		} else if (currentRaceType == currentRaceType.GRP) {
			for (Racer r : inRace)
				out += r.bib + " [" + r.t.runTime() + "], ";
			out += "\nFinished Racers: ";
			for (Racer r : finishRace)
				out += r.bib + " [" + r.t.runTime() + "], ";
		} else if (currentRaceType == currentRaceType.PARGRP) {

		}
		// out += "** Race " + currentRaceType + " #" + raceNum + " ***\n";
		// out += "Race Ended? " + raceEnded + "\n\n";
		// out += "Pending Racers: ";
		// for (Racer r : toRace) out += r.bib + " [" + r.t.runTime() + "], ";
		// out += "\nCurrently Racing: ";
		//
		// for (Racer r : inRace) out += r.bib + " [" + r.t.runTime() + "], ";
		// out += "\nFinished Racers: ";
		//
		// for (Racer r : finishRace) out += r.bib + " [" + r.t.runTime() + "],
		// ";

		return out;
	}

	/**
	 * Assigns a bib number sequentially to the race's racers.
	 * 
	 * @return boolean saying if a bib was set.
	 */
	public boolean giveBib(int arg1) {
		if (finishLength == null) { // finishlength hasn't been set yet
			finishLength = finishRace.size();
			index = finishLength;
		}

		if (index <= 0) {
			Main.dbg.printDebug(0, "Already assigned bibs to every finished racer.");
			return false;
		}

		finishRace.get(finishLength - (index--)).bib = arg1;

		return true;

	}
	
	/**
	 * Removes racer with specified bib number.
	 * 
	 * @param bib The bib corresponding with racer to remove
	 * @return boolean if someone was removed.
	 */
	public boolean removeBib(int bib){
		
		for (Racer r : toRace){
			if (r.bib == bib){
				toRace.remove(r);
				Main.dbg.printDebug(3, "Racer " + r + " with bib " + bib + " removed from toRace.");
				return true;
			}
		}
		
		for (Racer r : inRace){
			if (r.bib == bib){
				inRace.remove(r);
				Main.dbg.printDebug(3, "Racer " + r + " with bib " + bib + " removed from inRace.");
				return true;
			}
		}
		
		for (Racer r : finishRace){
			if (r.bib == bib){
				finishRace.remove(r);
				Main.dbg.printDebug(3, "Racer " + r + " with bib " + bib + " removed from finishRace.");
				return true;
			}
		}
		
	
		Main.dbg.printDebug(0, "[ERR] Racer with bib " + bib + " not found!");
		return false;
		
		
	}

}
