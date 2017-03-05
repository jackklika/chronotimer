package chronotimer;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;



public class Racer {
	public Time t = new Time();
	public int bib;
	
	public long startTime;
	public long endTime;
	
	public long runTime;

	public Racer(int bib) {
		this.bib = bib;
		Main.dbg.printDebug(3, this + "Created at " + Time.printTime());
	}

	public void getRunTime() {
		runTime = t.runTime();
	}
	
	public long finish(){
		return endTime = t.stopTime();
	}

}
