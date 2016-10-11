# User Guide *needs further refinement*

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ] (#faq) - not done 
* [Command Summary] (#command-summary) - not done 

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer. Having any Java 8 version is not enough.This app will not work with earlier versions of Java 8
<br>.  

1. Download the latest `taskmanager.jar` from the [releases](../../../releases) tab.<br>
<br>

2. Copy the file to the folder you want to use as the home folder for your Task Manager.
<br>

3. Double-click the file to start the app. The GUI should appear in a few seconds. 

   > <img src="images/Ui.png" width="0">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it.
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
	   * **`list`** : lists all items
	   * **`add task n/Eat`** adds a task named `Eat` to the taskmanager
	   * **`delete`**` 3` : deletes the 3rd task shown in the current list
	   <br>
   * **`exit`** : exits the app
   <br>
6. Refer to the [Features](#features) section below for details of each command.<br>


### Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.

## Getting Started

1. Open the Application 
<br>

2. You will see a welcome message and a list of commands that you can use.

3. At any time, you can view the list of commands again by typing ‘help’.

4. If you type an incorrect command, help-screen auto pops out 
	
## When you need help (To see a list of all commands)

1. Type ‘help’. 

2. Press Enter.

3. The list of commands, their format and their function will be shown.

## When you have a new deadline, task or event

Add a deadline

1. Type `add deadline n/NAME ed/DATE et/TIME`.

2. If et is not specified, et is assumed to be 23:59.

3. If ed is not specified, ed is assumed to be today.

4. If both et and ed are not specified, created task is a floating task (see b). 

5. Press Enter.

6. The deadline will be added to your to do list.

Add a task 

1. Type `add task n/NAME`. 

2. Press Enter.

3. The task will be added to your to do list.

Add an event 

1. Add an event by typing `add event n/NAME sd/START_DATE st/START_TIME ed/END_DATE et/END_TIME`.

2. If st is empty, st is assumed to be 00:00:00.

3. If et is empty, et is assumed to be 23:59:59.

4. If sd or ed is empty, sd or ed is assumed to be the current system date.

5. Press Enter.

##When you need to edit a deadline, task or event 

####Edit a task’s name 
For tasks, you can only edit the name.

If you know a keyword in the task's name

1. Type `find KEYWORD`. 

2. Type `edit INDEX n/NEW_NAME`.

3. Press Enter.

If you know the index of the task in the displayed list

1. Type `edit INDEX n/NEW_NAME`.

2. Press Enter.


####Edit a deadline's name, end date and end time
For deadlines, you can only edit the name, end date and time.

If you know the keyword of the deadline

1. Type `find KEYWORD`. 

2. Press Enter.

3. Type `edit INDEX n/[NEW_NAME] ed/[NEW_END_DATE] et/[NEW_END_TIME]`.

4. Press Enter. 

If you know the index of the deadline in the displayed list

1. Type `edit INDEX n/[NEW_NAME] ed/[NEW_END_DATE] et/[NEW_END_TIME]`. 

2. Press Enter.


####Edit an event’s name, start date, start time, end date and end time
For events, you can edit the name and both start and end dates and times.

If you know the keyword of the event

1. Type `find KEYWORD`. 

2. Press Enter.

3. Type `edit INDEX n/[NEW_NAME] sd/[NEW_START_DATE] st/[NEW_START_TIME]  ed/[NEW_END_DATE] et/[NEW_END_TIME]`.

4. Press Enter. 

If you know the index of the event in the displayed list

1. Type `edit INDEX n/[NEW_NAME] sd/[NEW_START_DATE] st/[NEW_START_TIME]  ed/[NEW_END_DATE] et/[NEW_END_TIME]`. 

2. Press Enter.


##When you need to view your deadlines, tasks and events

View all deadlines, tasks and events

1. View the entire todo list by typing ‘viewAll’. 

2. Press Enter.

View undone deadlines, tasks and events

1. View only undone items by typing ‘viewUndone’. 

2. Press Enter.

##When you can’t find a specific deadline, task or event

1. Search for a task or event by typing ‘search [keyword]’. 

2. Press Enter.

3. A list of corresponding deadlines, tasks, and events will be shown.

##When you have finished a task

If you know the name of the task

1. Type ‘done [taskname]’.

If you know the keyword of the task

1. Type ‘searchAndDone [keyword]’.

If you know the index of the task 

1. Type ‘doneByIndex [index]’.

##When you want to delete a deadline, task or event

If you know the name of the task

1. Type ‘delete [taskname]’. 


If you know the keyword of the task

1. Type ‘searchAndDelete [keyword]’. 


If you know the index of the task
1. Type ‘deleteByIndex [index]’.

##When you need to undo your last action
1. Type ‘undo’.
2. Press Enter.

## When you need to specify a different data storage location

To store in an absolute path

1. Type ‘store /path/to/storage/folder’.
<br>

2. Press Enter.

To store in a relative path

1. Type ‘store path/to/storage/folder’
<br>

2. Press Enter.

