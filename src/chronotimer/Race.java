package chronotimer;

import java.util.*;

public class Race {

	public long startTime;
	public Deque<Racer> toRace;			// Haven't started yet, in line
	public Deque<Racer> inRace;			// In the race
	public ArrayList<Racer> finishRace;	// Finished the race.
	public RaceType currentRaceType;
	
	public Race (RaceType raceType){
		toRace = new LinkedList<Racer>();
		inRace = new LinkedList<Racer>();
		finishRace = new ArrayList<Racer>();
	}
	
	
	public String toString(){
		
		String out = "";
		
		out += "toRace: ";
		for (Racer r : toRace) out += r.bib + " [" + r.t.runTime() + "], ";
		out += "\nInRace: ";
		
		for (Racer r : inRace) out += r.bib + " [" + r.t.runTime() + "], ";
		out += "\nfinishRace: ";
		
		for (Racer r : finishRace) out += r.bib + " [" + r.t.runTime() + "], ";


		
		return out;
	}
	
	
}
