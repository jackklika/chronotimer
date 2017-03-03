package chronotimer;

import java.util.*;

public class Race {
	
	public Deque<Racer> toRace;			// Haven't started yet, in line
	public Deque<Racer> inRace;			// In the race
	public ArrayList<Racer> finishRace;	// Finished the race.
	
	public Race (RaceType raceType){
		toRace = new LinkedList<Racer>();
		inRace = new LinkedList<Racer>();
		finishRace = new ArrayList<Racer>();
	}
	
	
	
}
