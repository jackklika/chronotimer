import java.util.*;

public class Race {

	public long startTime;
	public Deque<Racer> toRace;			// Haven't started yet, in line
	public Deque<Racer> inRace;			// In the race
	public ArrayList<Racer> finishRace;	// Finished the race.
	public RaceType currentRaceType;
	public boolean raceEnded = false; // True if the race is ended.
	
	private Integer finishLength;
	private int index;
	
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
	
		out += "** Race " + currentRaceType + " #" + raceNum + " ***\n";
		out += "Race Ended? " + raceEnded + "\n\n";
		out += "Pending Racers: ";
		for (Racer r : toRace) out += r.bib + " [" + r.t.runTime() + "], ";
		out += "\nCurrently Racing: ";
		
		for (Racer r : inRace) out += r.bib + " [" + r.t.runTime() + "], ";
		out += "\nFinished Racers: ";
		
		for (Racer r : finishRace) out += r.bib + " [" + r.t.runTime() + "], ";


		
		return out;
	}
	
	/**
	 * Assigns a bib number sequentially to the race's racers.
	 * @return boolean saying if a bib was set.
	 */
	public boolean giveBib(int arg1){
		if (finishLength == null){ // finishlength hasn't been set yet
			finishLength = finishRace.size();
			index = finishLength;
		}
		
		if (index <= 0){
			Main.dbg.printDebug(0, "Already assigned bibs to every finished racer.");
			return false;
		}
		
		finishRace.get(finishLength - (index--)).bib = arg1;
		
		return true;
		
	}
	
}
