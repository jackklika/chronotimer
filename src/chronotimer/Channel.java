package chronotimer;

public class Channel {
	private boolean state = false;
	
	public void toggle(){
		state = state ? false: true;
	}
	
	public boolean getState(){
		return state;
	}
}
