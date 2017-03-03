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
	}

	public void getRunTime() {
		runTime = t.runTime();
	}
	
	public long finish(){
		endTime = Instant.now().toEpochMilli();
		return endTime;
	}

}
