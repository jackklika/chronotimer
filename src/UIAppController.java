
import java.io.PrintStream;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class UIAppController {
	/*
	 * I feel like making a new chronotimer is wrong, but just trying to get something started...
	 * all the buttons are connected so there should not be a need to worry about that
	 */
	ChronoTimer ct = new ChronoTimer();
	String numEntered = "";
	
	//Todo's that don't have an associated method below... not sure if they are done here or in ct
	//TODO: Running display in the center
	//TODO: displaying on the "printer"

	
	//starting to try textarea
	@FXML
	private TextArea console;
	private PrintStream ps;
	

	
	
	@FXML
	private void powerBtnPressed() {
		ct.toCommand("POWER");
	}
	
	@FXML
	private void printerBtnPressed() {
		//TODO
		System.out.println("Printer power button pressed");
	}

	@FXML
	private void sensorOptionChanged() {
		// activates when the dropdown indicating which type of sensor a channel
		// should be connected to changes
		// TODO: figure out how to set the options for a dropdown, make sure
		// this is connected to the right actionevent
		System.out.println("Sensor option changed!");
	}

	@FXML
	private void swap() {
		// if racetype = ind
		ct.toCommand("SWAP");
	}

	@FXML
	private void functionButtonPressed() {
		// TODO: a) figure out how this works b) do it
		System.out.println("function button pressed");
	}

	// arrow buttons
	@FXML
	private void leftArrowPressed() {
		// TODO:
		System.out.println("left arrow button pressed");
	}

	@FXML
	private void rightArrowPressed() {
		// TODO:
		System.out.println("right arrow button pressed");
	}

	@FXML
	private void upArrowPressed() {
		// TODO:
		System.out.println("up arrow button pressed");
	}

	@FXML
	private void downArrowPressed() {
		// TODO:
		System.out.println("down arrow button pressed");
	}

	// keypad buttons
	@FXML
	private void key0() {
		numEntered += "0";
	}

	@FXML
	private void key1() {
		numEntered += "1";
	}

	@FXML
	private void key2() {
		numEntered += "2";
	}

	@FXML
	private void key3() {
		numEntered += "3";
	}

	@FXML
	private void key4() {
		numEntered += "4";
	}

	@FXML
	private void key5() {
		numEntered += "5";
	}

	@FXML
	private void key6() {
		numEntered += "6";
	}

	@FXML
	private void key7() {
		numEntered += "7";
	}

	@FXML
	private void key8() {
		numEntered += "8";
	}

	@FXML
	private void key9() {
		numEntered += "9";
	}

	@FXML
	private void keyStar() {
		numEntered += "*";
	}

	@FXML
	private void keyPound() {
		ct.toCommand("NUM " + numEntered);
	}

	// all events channel related
	@FXML
	private void chan1Toggle() {
		ct.toCommand("TOG 1");
	}

	@FXML
	private void chan1Trig() {
		ct.toCommand("TRIG 1");
	}

	@FXML
	private void chan1Conn() {
		System.out.println("TODO: connect channel 1");
		// TODO: Get the sensor type selected from the dropdown menu
		// ct.toCommand( "CONN " + <SensorType> +" 1");
	}

	@FXML
	private void chan2Toggle() {
		ct.toCommand("TOG 2");
	}

	@FXML
	private void chan2Trig() {
		ct.toCommand("TRIG 2");
	}

	@FXML
	private void chan2Conn() {
		System.out.println("TODO: connect channel 2");
		// TODO: Get the sensor type selected from the dropdown menu
		// ct.toCommand( "CONN " + <SensorType> +" 1");
	}

	@FXML
	private void chan3Toggle() {
		ct.toCommand("TOG 3");
	}

	@FXML
	private void chan3Trig() {
		ct.toCommand("TRIG 3");
	}

	@FXML
	private void chan3Conn() {
		System.out.println("TODO: connect channel 3");
		// TODO: Get the sensor type selected from the dropdown menu
		// ct.toCommand( "CONN " + <SensorType> +" 1");
	}

	@FXML
	private void chan4Toggle() {
		ct.toCommand("TOG 4");
	}

	@FXML
	private void chan4Trig() {
		ct.toCommand("TRIG 4");
	}

	@FXML
	private void chan4Conn() {
		System.out.println("TODO: connect channel 4");
		// TODO: Get the sensor type selected from the dropdown menu
		// ct.toCommand( "CONN " + <SensorType> +" 1");
	}

	@FXML
	private void chan5Toggle() {
		ct.toCommand("TOG 5");
	}

	@FXML
	private void chan5Trig() {
		ct.toCommand("TRIG 5");
	}

	@FXML
	private void chan5Conn() {
		System.out.println("TODO: connect channel 5");
		// TODO: Get the sensor type selected from the dropdown menu
		// ct.toCommand( "CONN " + <SensorType> +" 1");
	}

	@FXML
	private void chan6Toggle() {
		ct.toCommand("TOG 6");
	}

	@FXML
	private void chan6Trig() {
		ct.toCommand("TRIG 6");
	}

	@FXML
	private void chan6Conn() {
		System.out.println("TODO: connect channel 6");
		// TODO: Get the sensor type selected from the dropdown menu
		// ct.toCommand( "CONN " + <SensorType> +" 1");
	}

	@FXML
	private void chan7Toggle() {
		ct.toCommand("TOG 7");
	}

	@FXML
	private void chan7Trig() {
		ct.toCommand("TRIG 7");
	}

	@FXML
	private void chan7Conn() {
		System.out.println("TODO: connect channel 7");
		// TODO: Get the sensor type selected from the dropdown menu
		// ct.toCommand( "CONN " + <SensorType> +" 1");
	}

	@FXML
	private void chan8Toggle() {
		ct.toCommand("TOG 8");
	}

	@FXML
	private void chan8Trig() {
		ct.toCommand("TRIG 8");
	}

	@FXML
	private void chan8Conn() {
		System.out.println("TODO: connect channel 8");
		// TODO: Get the sensor type selected from the dropdown menu
		// ct.toCommand( "CONN " + <SensorType> +" 1");
	}

}
