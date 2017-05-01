
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
	
//	public int compareTo(Object o) {
//		if (o instanceof Racer) {
//			Racer other = (Racer) o;
//			if(this.prettyTime.equals("DNF")) return 1; // Less than others
//			
//			try {
//				String[] ttimes = this.prettyTime.split(":");
//				int thours = Integer.parseInt(ttimes[0]);
//				int tminutes = Integer.parseInt(ttimes[1]);
//				double tseconds = Double.parseDouble(ttimes[2]);
//				
//				String[] otimes = other.prettyTime.split(":");
//				int ohours = Integer.parseInt(otimes[0]);
//				int ominutes = Integer.parseInt(otimes[1]);
//				double oseconds = Double.parseDouble(otimes[2]);
//				
//				
//				if (thours > ohours){
//					return 1;
//				} else if (thours < ohours){
//					return -1;
//				} else {
//					if (tminutes > ominutes){
//						return 1;
//					} else if (tminutes < tminutes){
//						return -1;
//					} else {
//						if (tseconds > oseconds){
//							return 1;
//						} else if (tseconds < oseconds){
//							return -1;
//						} else {
//							return 0;
//						}
//					}
//				}
//			} catch(NumberFormatException e) {
//				e.printStackTrace();
//			}
//			
//			
//			//return time.compareTo(other.time);
//		}
//		return 0;
//	}
	
	

}
