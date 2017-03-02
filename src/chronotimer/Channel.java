package chronotimer;

import java.awt.event.ActionEvent;

public class Channel {
	
	private Sensor s;
	private ChronoTimer ct;
	private boolean state = false;
	
	public void toggle(){
		state = state ? false: true;
	}
	
	public boolean getState(){
		return state;
		
	}
	//turns off the state 
	public boolean disarm(){
		return state = false;
	}
	
	public Channel(ChronoTimer parent){
		this.ct = parent;
		this.s = new GateSensor(this);
	}
	
	public void setSensor(Sensor s){
		this.s = s;
	}
	
	public void trigger(){
		ActionEvent a = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null);
		ct.score(a);
	}
}