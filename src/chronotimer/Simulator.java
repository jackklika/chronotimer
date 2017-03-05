package chronotimer;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;



public class Simulator implements Runnable {
	ChronoTimer timer;
	Shell sh;
	String filename;
	
	class Shell implements Runnable {
		String input = "";
		public void run() {
			if (filename != null) {
				try { Thread.sleep(2000); } // run commands every 2 seconds
				catch (InterruptedException e) { e.printStackTrace(); }
				try {
					Path path = Paths.get(filename);
					Files.lines(path).forEach(l ->{
						input = l;
						timer.toCommand(input);
						try { Thread.sleep(2000); } // run commands every 2 seconds
						catch (InterruptedException e) { e.printStackTrace(); }
					});
				}
				catch (IOException e){
					System.out.println("Error encountered opening file: " + e);
				}
			}
			else {
				Scanner in = new Scanner(System.in);
				while (true){
					System.out.print(">");
					input = in.nextLine();
					// Check if input is valid command. If it is, add to queue
					
					//The command constructor adds the command to the queue
					//new Command(input);
					
					timer.toCommand(input);
					
					//cmdQueue.add(toCommand(input)); // change to CT.cmdQueue...
					
					try { Thread.sleep(300); } 
					catch (InterruptedException e) { e.printStackTrace(); }
				}
			}
		}
		
	}
	
	

	public Simulator(ChronoTimer timer) {
		this.sh = new Shell();
		this.timer = timer;
	}

	public void run() { // move to CT?
		Thread shell = new Thread(sh);
		Thread time = new Thread(timer); 
		shell.start();
		time.start(); // starts the timer

		while (true){
			
			// Check this method out
			
			try { Thread.sleep(1000); }
			catch (InterruptedException e) { e.printStackTrace(); }

		}
		
	}
	

}
