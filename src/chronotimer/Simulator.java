package chronotimer;

/*
 * >>> COMMANDS:
 * POWER	Turn on and enter idle state or turn system off but stay in simulator
 * EXIT		Exit the simulator
 * RESET	Resets the System to initial state
 * TIME		Set the current time 							FORMAT: "<hour>:<min>:<sec>"
 * TOG		toggle state of channel 						FORMAT: "<channel>"
 * CONN		connect a type of sensor to channel <num>		FORMAT: "<sensor> <num>"
 * DISC 	disconnect sensor from channel <num>			FORMAT: "<num>"
 * EVENT	type of event (IND, PARIND, GRP, PARGRP)		FORMAT: "<eventcode>"
 * NEWRUN	creates a new run
 * ENDRUN	done with a run
 * PRINT	Prints the run on stdout
 * EXPORT	Exports the run in XML to file "RUN<RUN>"		FORMAT: "<run>"
 * NUM		Set <num> as the next competitor to start		FORMAT: "<num>"
 * CLR		Clear competitor number <num>					FORMAT: "<num>"
 * SWAP		Exchange next to compentitors to finish in IND
 * DNF 		Next competitor to finish will not finish
 * TRIG		Trigger channel <num>							FORMAT: "<num>"
 * START	Start trigger channel 1 -- macro for TRIG 1
 * FINISH	Finish trigger channel 2 -- macro for TRIG 2
 */ 


// I think this is all being handled in Main. We may be able to delete this class

public class Simulator {
	
}
