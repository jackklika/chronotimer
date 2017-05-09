
public class Racer implements Comparable<Racer>{
	public Time t = new Time();
	public int bib;
	public long startTime;
	public long endTime;
	public long runTime;
	public String prettyTime;

	public Racer(int bib) {
		this.bib = bib;
		Main.dbg.printDebug(3, this + "Created at " + Time.printTime());
	}

	public void getRunTime() {
		if (runTime != Long.MAX_VALUE) runTime = t.runTime();
	}
	
	public long finish(){
		return endTime = t.stopTime();
	}
	
//Sort racers by race result order (shortest to longest with all DNFs at the end)
 
	public int compareTo(Racer r){
		this.getRunTime();
		r.getRunTime();
		if (r.runTime > this.runTime) return -1;
		else if (r.runTime < this.runTime) return 1;
		else return 0;
	}	

}
