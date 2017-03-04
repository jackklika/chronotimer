# chronotimer

## How this all works:

Options are currently set in the first bit of Main.java.
- MAX_VERBOSITY: The level of verbosity of the debug commands emitted from the Main's Debugger object.
- GRANULARITY: Simply how long Chronotimier's event loop waits before looping again.

Main creates a Simulator which takes a new ChronoTimer as an argument. This creates a thread for the Simulator, which creates a thread for a shell (a way to type commands in the console)

### Event Loop

Each Simulator has an **event loop**. This is a loop that runs in the Simulator until the EXIT command is given. 

Each Simulator has a shell that allows you to type in commands. These commands are stored as strings, converted into String array {"COMMAND", "argument1", "argument2"} and put in a Command object. These command objects have an execute() method that executes methods from the Simulator's chronotimer object.

The event loop checks to see what commands need to be performed. When commands are created, their constructors add them to a queue (cmdQueue). The event loop checks the cmdQueue every cycle, and executes all the commmands until there are no more left.

### Commands

Check out all the commands by typing "HELP" into the shell. Some notable ones that are helpful for debugging are:

- LIST: Lists all the racers, their times, and which queue they are in.
- DEBUG <num>: Change the debug output level (aka the MAX_VERBOSITY variable) to a number between 0 and 3.

## This repository
/docs: UML diagrams, documentation, or non-code documents that contribute value or structure to the project

------
## Basic Eclipse Git Usage

### Eclipse Git - Getting the chronotimer program on Eclipse:

- Copy this link to your clipboard -- [https://github.com/jackklika/chronotimer.git](https://github.com/jackklika/chronotimer.git)
- File > Import > Git > Clone URI -- All the URL data should be pulled into the forms from your clipboard.
- Enter your **github** username and password in Authentication

### Eclipse Git - Syncing the repo, Saving your Changes, and Pushing

- Pull the code to get the most recent version and make sure that you don't conflict with anyone when you push: Right click on project > Team > Pull
- Create and push a commit -- This chooses which files should be pushed to the git repository and updated. Press Ctl+Shift+3, drag desired changes to the "Staged Changes". Press "Commit and Push".
- Google any errors if something goes wrong.

### Handy Links

- [Commit history of this project](https://github.com/jackklika/cs361-chromotimer/commits/master)
- [Try Git](https://try.github.io/levels/1/challenges/1)
- [Git Reference](http://gitref.org/)
- Add more here!
