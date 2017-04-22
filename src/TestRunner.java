import org.junit.*;
import static org.junit.Assert.*;

public class TestRunner {

	public static void main(String[] args) {
		
		Simulator sim = new Simulator(new ChronoTimer()); // New simulator
		sim.run(); // Start the simulator, which starts the chronotimer and GUI
		
		
		
	}

}
