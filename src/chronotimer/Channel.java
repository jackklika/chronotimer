package chronotimer;

import java.nio.channels.Channels;

public class Channel {

	private boolean state = false;

	// we need this to determine if it is a start or finish channel
	private int channelNum = -1;

	// since when we create the channel array, they are not given a number, this
	// assigns them a number based on their location in the array
	public void assignChannel() {
		for (int i = 0; i < chronotimer.ChronoTimer.channels.length; i++) {
			if (this == chronotimer.ChronoTimer.channels[i]) {
				channelNum = i + 1;
			}
		}
	}

	public void toggle() {
		state = state ? false : true;
	}

	public boolean getState() {
		return state;

	}

	// turns off the state
	public boolean disarm() {
		return state = false;
	}

	// this is called by sensor when the sensor is "triggered", could also be
	// manually called from the simulator
	public void trigger() {
		// by convention we know that odd numbered channels indicate a start and
		// even channels indicate a finish (see project description pg 4)
		if (state) {
			if (channelNum == -1){
				assignChannel();
			}
			//indicates odd numbered channel
			if(channelNum % 2 == 1){
				//start a timer, where should the timer object be stored?
				
				
			}
			//indicates even numbered channel
			else{
				//finish a timer... need to find a way to finish the timer that already started or delegate that task somewhere else
			}
			
			

		}
	}
}
