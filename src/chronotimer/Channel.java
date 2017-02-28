package chronotimer;

public class Channel {
	
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
}
