# chronotimer

## How this all works:

Options are currently set in the first bit of Main.java.
- MAX_VERBOSITY: The level of verbosity of the debug commands emitted from the Main's Debugger object.
- GRANULARITY: Simply how long Chronotimier's event loop waits before looping again.

When running this application, the methods are all called from Main.java. Main will create a Chronotimer thread and call its run() method. This run method contains the EVENT LOOP.

### Event Loop

Each ChronoTimer has an **event loop**. This is a "while(true)" loop that runs until the EXIT command is given. (This might be a problem with the simulator -- chronotimer distinction so we need to figure that out)
The event loop does the following:
- Goes through a Queue<Command> until it is empty
- Wait GRANULARITY ms. 


## This repository
/docs: UML diagrams, documentation, or non-code documents that contribute value or structure to the project

------
## Basic Git Usage

### Getting this program on your computer
Follow the directions on the D2L document. It is encouraged to use the command line to manage this git project.

Get the program on your local machine by typing "git clone [GIT URL]". I would type "git clone https://github.com/jackklika/cs361-chromotimer.git" for example.


### Saving your Changes and Pushing

- Fork the program to your own account. [TODO: ADD HOWTO]
- Once you have modified or created a file, type "git add [FILENAME]" to add the file to the group of files you will commit.
- Once you are finished adding the files you would like to commit, type 'git commit -m "MESSAGE HERE"'. The '-m' flag specifies a message you would like to attach to the commit. Use the message to say what you changed or added to the file.
- Once you have committed, push the file by typing 'git push' in the root directory of this application.

### Aids

- At any time, use "git status" to see the status of what git is prepared to add or commmit.
- "git log" shows a history of the repo 
- Type "man git" to see a reference manual for git. You can find the man pages for git subcommands such as "git commit" by typing "man git-commit".

### Handy Links

- [Commit history of this project](https://github.com/jackklika/cs361-chromotimer/commits/master)
- [Try Git](https://try.github.io/levels/1/challenges/1)
- [Git Reference](http://gitref.org/)
- Add more here!
