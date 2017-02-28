package chronotimer;

public abstract class Sensor {
	// Generates a single "trigger" event indicating a sensor has been activated
	// Sensors can be armed or disarmed
	// Input from a sensor can be blocked in the timing system, regardless of
	// its armed state.
	// Odd number channels are start and even numbered are finish
	private boolean isArmed;
	private boolean isBlocked = false;
	private Channel myChannel;

	public Sensor(Channel c) {
		isArmed = false;
		isBlocked = false;
		myChannel = c;
	}

	public void trigger() {
		if (isArmed && !isBlocked && myChannel != null) {
			// trigger channel associated with this sensor
			 myChannel.trigger();
		}
		else {
			System.out.println("Unable to trigger this sensor, make sure sensor is armed, not blocked, and connected to a channel");
			//report unable to trigger 
		}

	}

	public void connect(Channel c) {
		myChannel = c;
		// should this automatically arm the sensor?
	}

	public void disconnect() {
		myChannel = null;
		// should this automatically disarm the sensor too?
	}

	public void disarm() {
		isArmed = false;
	}

	public void arm() {
		isArmed = true;
	}

	public void block() {
		isBlocked = true;
	}

	public void unblock() {
		isBlocked = false;
	}

}
