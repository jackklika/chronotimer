import java.util.*;

public class Race {

	public long startTime;
	public Deque<Racer> toRace;			// Haven't started yet, in line
	public Deque<Racer> inRace;			// In the race
	public ArrayList<Racer> finishRace;	// Finished the race.
	public RaceType currentRaceType;
	public boolean raceEnded = false; // True if the race is ended.
	
	// Races are numbered.
	public int raceNum;
	public static int maxRaceNum = 1;
	
	public Race (RaceType raceType){
		toRace = new LinkedList<Racer>();
		inRace = new LinkedList<Racer>();
		finishRace = new ArrayList<Racer>();
		raceNum = maxRaceNum++;
		currentRaceType = raceType;
	}
	
	
	@Override
	public String toString(){
		
		String out = "";
	
		out += "** Race " + raceNum + " ***\n";
		out += "Race Ended? " + raceEnded + "\n\n";
		out += "Pending Racers: ";
		for (Racer r : toRace) out += r.bib + " [" + r.t.runTime() + "], ";
		out += "\nCurrently Racing: ";
		
		for (Racer r : inRace) out += r.bib + " [" + r.t.runTime() + "], ";
		out += "\nFinished Racers: ";
		
		for (Racer r : finishRace) out += r.bib + " [" + r.t.runTime() + "], ";


		
		return out;
	}
	
	
}
