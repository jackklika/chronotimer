package chronotimer;

import java.util.ArrayList;
import java.util.Collections;



public class Racer {
	public Time t = new Time();
	public int bib;
	public long runTime;

	public Racer(int bib) {
		this.bib = bib;
	}

	public void getRunTime() {
		runTime = t.runTime();
	}
	
public void swap(){
		
		ArrayList list = new ArrayList(racers);
		Collections.swap(list, 0, 1);
	
	}
}