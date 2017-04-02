package src.test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;

import org.junit.Test;


import src.chronotimer.*;
import src.chronotimer.ChronoTimer.Command;



public class TestMain {

	ChronoTimer c = new ChronoTimer();
	Simulator s = new Simulator(c);
	
	@Test
	public void descriptiveTestNameHere() throws InterruptedException {
		
		assertTrue(Main.GRANULAITY > 0);
		Thread.sleep(1001); // This lets this method wait a bit if needed.
		
		
	}
	
	@Test
	public void failedTest(){
		assertFalse(Main.GRANULAITY > 0);
	}

}
