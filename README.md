# chronotimer2 -- Electric Boogaloo

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
- **/src:** The code! This includes the chronotimer and the testing code.
- **/docs:** UML diagrams, documentation, or non-code documents that contribute value or structure to the project
- **/lib:** Project libraries, specifically jar files that we might need to use later. If you're having issues with dependancies, throw the jars in here and configure the build path to point to them in the /lib directory.
- **/target:** Some junk maven needs to do its stuff.
- **/bin:** Binaries.


------

## Basic CLI Git Usage

Setup the git client:
- Windows: Install the [windows client](https://desktop.github.com/) which should install the "Git Shell." Use this to interface with Git.
- Linux/Mac: It should be already installed. Open terminal and type "git --help" to make sure it's installed. Otherwise install it via your package manager.

### Workflow

- `git add .` adds all files in your current directory recursively to the staged files. Use `cd` to get around to different directories. Use `git rm filename` to remove files from the staged status.
- `git commit -m "MESSAGE"` creates a commit from all the staged files.
- `git push` pushes the commits.
- `git pull` pulls commits and should get you up to date.
- `git reset --hard HEAD` is the nuclear option. This resets your repo to whatever the current HEAD is and will delete your changes
- 


### Basic Commands:

- `git status` will list what the status of your local repo is. Use this to see what items are staged or differ from 

## Basic Eclipse Git Usage

Please don't use the Eclipse git client. It's pretty terrible and encourages bad habits. The industry standard is the terminal interface.

## Handy Links

- [Commit history of this project](https://github.com/jackklika/cs361-chromotimer/commits/master)
- [Try Git](https://try.github.io/levels/1/challenges/1)
- [Git Reference](http://gitref.org/)
- Add more here!
