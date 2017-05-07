import java.time.Instant;
import java.util.*;

public class Race {

	public long startTime;
	public long finishTime;
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

	@SuppressWarnings("static-access")
	@Override
	/**
	 * Assembles a string containing the current race and its participants (with their times)
	 * Used in UIAppController.raceDisplay, called from the ChronoTimer command loop
	 * 
	 * @return string representation of race
	 */
	public String toString() {
		if (!(finishRace.isEmpty())) Collections.sort(finishRace);
		String out = "** Race #" + raceNum + " " + currentRaceType + " ***\n";
		switch (currentRaceType){
			case IND:
				for (Racer r : toRace)
					out += r.bib + "\t[" + Time.convert(r.t.runTime()) + ((toRace.getLast() == r) ? "] >\n" : "]\n");
				out += "\n";
				for (Racer r : inRace)
					out += r.bib + "\t[" + Time.convert(r.t.runTime()) + ((inRace.getLast() == r) ? "] R\n" : "]\n");
				out += "\n";
				for (Racer r : finishRace)
					out += r.bib + "\t[" + Time.convert((r.runTime == Long.MAX_VALUE) ? r.runTime : r.t.runTime()) + ((finishRace.get(finishRace.size()-1) == r) ? "] F\n" : "]\n");
				break;
			case PARIND:
				for (Racer r : toRace)
					out += r.bib + "\t[" + Time.convert(r.t.runTime()) + ((toRace.getLast() == r) ? "] >\n" : "]\n");
				out += "\n";
				for (Racer r : inRace)
					out += r.bib + "\t[" + Time.convert(r.t.runTime()) + ((inRace.getLast() == r) ? "] R\n" : "]\n");
				out += "\n";
				for (Racer r : finishRace)
					out += r.bib + "\t[" + Time.convert((r.runTime == Long.MAX_VALUE) ? r.runTime : r.t.runTime()) + ((finishRace.get(finishRace.size()-1) == r) ? "] F\n" : "]\n");
				break;
			case GRP:
				out += "R\t[" + ((startTime == 0) ? Time.convert((long) 0) : (raceEnded ? Time.convert(finishTime-startTime) : Time.convert(Instant.now().toEpochMilli()+Time.currentMs - startTime))) +"]\n";
				out += "\n";
				for (Racer r : finishRace)
					out += r.bib + "\t[" + Time.convert(r.t.runTime()) + ((finishRace.get(finishRace.size()-1) == r) ? "] F\n" : "]\n");
				break;
			case PARGRP:
				out += "\n";
				if (!(toRace.isEmpty())) {
					for (Racer r : toRace) out += r.bib + "\t[" + Time.convert(r.t.runTime()) + ((toRace.getLast() == r) ? "] >\n" : "]\n");
					out += "\n";
				}
				if (!(inRace.isEmpty())){
					for (Racer r : inRace) out += r.bib + "\t[" + Time.convert(Instant.now().toEpochMilli()+Time.currentMs - startTime) + ((inRace.getLast() == r) ? "] R\n" : "]\n");
					out += "\n";
				}
				for (Racer r : finishRace)
					out += r.bib + "\t[" + Time.convert(r.t.runTime()) + ((finishRace.get(finishRace.size()-1) == r) ? "] F\n" : "]\n");
				break;
		}
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

		if (currentRaceType == RaceType.GRP || currentRaceType == RaceType.PARGRP) {
			finishRace.get(finishLength - (index--)).bib = arg1;
		}
		
		return true;

	}

	/**
	 * Removes racer with specified bib number.
	 * 
	 * @param bib The bib corresponding with racer to remove
	 * @return boolean if someone was removed.
	 */
	public Racer removeBib(int bib){
		
		for (Racer r : toRace){
			if (r.bib == bib){
				toRace.remove(r);
				Main.dbg.printDebug(3, "Racer " + r + " with bib " + bib + " removed from toRace.");
				return r;
			}
		}
		
		for (Racer r : inRace){
			if (r.bib == bib){
				inRace.remove(r);
				Main.dbg.printDebug(3, "Racer " + r + " with bib " + bib + " removed from inRace.");
				return r;
			}
		}
		
		for (Racer r : finishRace){
			if (r.bib == bib){
				finishRace.remove(r);
				Main.dbg.printDebug(3, "Racer " + r + " with bib " + bib + " removed from finishRace.");
				return r;
			}
		}
		
	
		Main.dbg.printDebug(0, "[ERR] Racer with bib " + bib + " not found!");
		return null;
		
		
	}

}
