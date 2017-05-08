
import java.io.PrintStream;
  
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;

public class UIAppController {
	/*
	 * I feel like making a new chronotimer is wrong, but just trying to get something started...
	 * all the buttons are connected so there should not be a need to worry about that
	 */
	
	ChronoTimer ct = Main.sim.timer;//new ChronoTimer();
	
	String numEntered = "";
	boolean active1 = false;
	boolean active2 = false;
	boolean active3 = false;
	boolean active4 = false;
	boolean active5 = false;
	boolean active6 = false;
	boolean active7 = false;
	boolean active8 = false;
	
	@FXML ToggleGroup group; 
	@FXML ToggleGroup race;
	  
	@FXML
	public TextArea console;
	
	@FXML
	public TextArea raceDisplay;
	
	@FXML
	public CheckBox tog1;
	public CheckBox tog2;
	public CheckBox tog3;
	public CheckBox tog4;
	public CheckBox tog5;
	public CheckBox tog6;
	public CheckBox tog7;
	public CheckBox tog8;
	
	@FXML
	private void serverBtnPressed(){
		//TODO
		ct.toCommand("SERVER");
	}
	
	@FXML
	private void powerBtnPressed() {
		ct.toCommand("POWER");
		if (ct.raceDisplay == null){
			ct.raceDisplay = raceDisplay;
			raceDisplay.setText("ChronoTimer powered on\nSelect a race type and hit New Run");
		}
		else {
			ct.raceDisplay.clear();
			ct.raceDisplay = null;
		}
	}
	
	@FXML
	private void printerBtnPressed() {
		//TODO
		System.out.println("Printer power button pressed");
		if (Main.dbg.output == null) {
			Main.dbg.setOutput(console);
			Main.dbg.printDebug(1, Time.printTime() + "\tPrinter on");
		}
		else {
			Main.dbg.printDebug(1, Time.printTime() + "\tPrinter off");
			Main.dbg.setOutput(null);
		}
	}


	@FXML
	private void swap() {
		RadioButton selectedRadioButton = (RadioButton) race.getSelectedToggle();
		  //this is the name of the selected radio button
		 String toggleGroupValue = selectedRadioButton.getText();
		 if(toggleGroupValue.equals("IND")){
				ct.toCommand("SWAP");
		 }
		 else{
			 Main.dbg.printDebug(1, Time.printTime() + "\tCannot swap unless racetype is IND");
		 }
	
	}

	@FXML
	private void newRun() {
		RadioButton selectedRadioButton = (RadioButton) race.getSelectedToggle();
		  //this is the name of the selected radio button
		 String toggleGroupValue = selectedRadioButton.getText();
		ct.toCommand("EVENT "+ toggleGroupValue);
		ct.toCommand("NEWRUN");
		raceDisplay.clear();
		
		//reset checkboxes
		if (ct.currentRace != null && ct.currentRace.raceEnded) {
			tog1.setIndeterminate(false);
			tog1.setSelected(false);
			tog2.setIndeterminate(false);
			tog2.setSelected(false);
			tog3.setIndeterminate(false);
			tog3.setSelected(false);
			tog4.setIndeterminate(false);
			tog4.setSelected(false);
			tog5.setIndeterminate(false);
			tog5.setSelected(false);
			tog6.setIndeterminate(false);
			tog6.setSelected(false);
			tog7.setIndeterminate(false);
			tog7.setSelected(false);
			tog8.setIndeterminate(false);
			tog8.setSelected(false);
		}
	}
	
	@FXML
	private void endRun() {
		ct.toCommand("ENDRUN");
	}
	
	@FXML
	private void DNF() {
		ct.toCommand("DNF");
	}
	
	@FXML
	private void reset() {
		ct.toCommand("RESET");
		tog1.setIndeterminate(false);
		tog1.setSelected(false);
		tog2.setIndeterminate(false);
		tog2.setSelected(false);
		tog3.setIndeterminate(false);
		tog3.setSelected(false);
		tog4.setIndeterminate(false);
		tog4.setSelected(false);
		tog5.setIndeterminate(false);
		tog5.setSelected(false);
		tog6.setIndeterminate(false);
		tog6.setSelected(false);
		tog7.setIndeterminate(false);
		tog7.setSelected(false);
		tog8.setIndeterminate(false);
		tog8.setSelected(false);
	}
	
	@FXML
	private void cancel() {
		ct.toCommand("CANCEL");
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
		numEntered = "";
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
		if (active1){
			ct.toCommand("DISC 1");
			active1 = false;
		}
		else{
		RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
		  //this is the name of the selected radio button
		 String toggleGroupValue = selectedRadioButton.getText().replace(" ","");
		 ct.toCommand("CONN "+ toggleGroupValue + " 1" );
		 active1 = true;
		}
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
		if (active2){
			ct.toCommand("DISC 2");
			active2 = false;
		}
		else{
		RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
		 String toggleGroupValue = selectedRadioButton.getText().replace(" ","");
		 ct.toCommand("CONN "+ toggleGroupValue + " 2" );
		 active2 = true;
		}
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
		if (active3){
			ct.toCommand("DISC 3");
			active3 = false;
		}
		else{
		RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
		 String toggleGroupValue = selectedRadioButton.getText().replace(" ","");
		 ct.toCommand("CONN "+ toggleGroupValue + " 3" );
		 active3 = true;
		}
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
		if (active4){
			ct.toCommand("DISC 3");
			active4 = false;
		}
		else{
		RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
		 String toggleGroupValue = selectedRadioButton.getText().replace(" ","");
		 ct.toCommand("CONN "+ toggleGroupValue + " 4" );
		 active4 = true;
		}
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
		if (active5){
			ct.toCommand("DISC 5");
			active3 = false;
		}
		else{
		RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
		 String toggleGroupValue = selectedRadioButton.getText().replace(" ","");
		 ct.toCommand("CONN "+ toggleGroupValue + " 5" );
		 active5 = true;
		}
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
		if (active6){
			ct.toCommand("DISC 6");
			active6 = false;
		}
		else{
		RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
		 String toggleGroupValue = selectedRadioButton.getText().replace(" ","");
		 ct.toCommand("CONN "+ toggleGroupValue + " 6" );
		 active6 = true;
		}
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
		if (active7){
			ct.toCommand("DISC 7");
			active7 = false;
		}
		else{
		RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
		 String toggleGroupValue = selectedRadioButton.getText().replace(" ","");
		 ct.toCommand("CONN "+ toggleGroupValue + " 7" );
		 active7 = true;
		}
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
		if (active8){
			ct.toCommand("DISC 8");
			active8 = false;
		}
		else{
		RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
		 String toggleGroupValue = selectedRadioButton.getText().replace(" ","");
		 ct.toCommand("CONN "+ toggleGroupValue + " 8" );
		 active8 = true;
		}
	}

}
