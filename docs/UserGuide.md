# User Guide

* 1. [About the Task Manager](#about-the-task-manager)
* 2. [Starting the program](#starting-the-program) 
* 3. [Features](#features)
* 4. [FAQ](#faq)
* 5. [Command Summary](#command-summary)


##	About the Task Manager
This product is meant to address the concerns of users who wish to schedule their tasks using a simple and 	easy command-line interface.

## Starting the program

**Installation**

1. Make  sure you have Java version `1.8.0_60` or later installed in your computer.
2. Download the latest Practical Task Manager from the Releases tab.
3. Copy the file to a suitable location on your computer. This location will serve as the home folder for the 	Task Manager.

**Running the program**

1. Double-click the file to start the application. The user interface below should appear in a few seconds.
  ![Image of Loading Screen](https://github.com/Halo3fanz/main/blob/master/docs/images/Loading.png)
  ![Image of UI](https://github.com/Halo3fanz/main/blob/master/docs/images/Ui.png)
2. Type a command in the command box and press <kbd>enter</kbd> to execute it. Depending on the command, the 	Task Manager will respond by displaying a message in the console window.
3. Refer to the Features section below for details of each command.


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.

#### Viewing help : `help`
Description: displays all commands
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`
 
#### Adding a task: `add`
Description: Adds a task to the task manager<br>
Format: `add TASKNAME DEADLINE` 

> Tasks can have no deadline. In which case, leave the DEADLINE field blank.

Examples: 
* `add CS2103 23101026`


#### Listing all tasks : `list`
Description: Shows a list of all tasks in the task manager.<br>
Format: `list`

#### Finding all tasks containing any keyword in their name: `find`
Description: Finds task whose names contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case sensitive. e.g `cs2103t` will not match `CS2103T`
> * The order of the keywords does not matter. e.g. `Software Engineering` will match `Engineering Software`
> * Only the name is searched.
> * Only full words will be matched e.g. `CS2103` will not match `CS2103T`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Software` will match `Software Engineering`

Examples: 
* `find Software`<br>
  Returns `Software Engineering` but not `software`
* `find CS2103T Software Engineering`<br>
  Returns Any task having names `CS2103T`, `Software`, or `Engineering`

#### Deleting a task : `delete`
Description: Deletes the specified task from the task manager. 
*Note: This process is irreversible.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the task manager.
* `find CS2101`<br> 
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

#### Selecting a task : `select`
Description: Selects the task identified by the index number used in the last task listing.<br>
Format: `select INDEX`

> Selects the task and loads the Google search page the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `select 2`<br>
  Selects the 2nd task in the task manager.
* `find CS2103T` <br> 
  `select 1`<br>
  Selects the 1st task in the results of the `find` command.
  
#### Attaching a note to the task: `attach`  
Description: Adds an additional note to that particular task
Format: `attach TASKNAME note`

Examples:
* `attach CS2103T exam_20/11/2016` <br>

#### View details of a task: `view`
Description: Displays all details of a specified task.
Format: `view TASKNAME`

Examples:
* `view CS2103T`

#### Checking for clashing tasks: `clash`
Description: Shows all the tasks that have conflicting deadlines.
Format: `clash`


#### Clearing all entries : `clear`
Description: Clears all entries from the task manager.<br>
Format: `clear`  

#### Exiting the program : `exit`
Description: Exits the program.<br>
Format: `exit`  

#### Saving the data 
Task Manager data is saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Task Manager folder.          
       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `add TASKNAME DEADLINE`
Clear | `clear`
Delete | `delete INDEX`
Find | `find KEYWORD [MORE_KEYWORDS]`
List | `list`
Help | `help`
Select | `select INDEX`
Clash | `delete TASKNAME`
View | `view TASKNAME`
