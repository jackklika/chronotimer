package chronotimer;

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
}