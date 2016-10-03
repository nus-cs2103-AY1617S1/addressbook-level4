# User Guide

* [Quick Start](#quick-start)
* [Features](#features)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `forgetmenot.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Address Book.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Words in angle brackets `<>` are parameters.
> * The order of parameters is fixed.

#### Finding more information about various commands: `help`
Displays the list of commands(along with their formats) for the user to help him use those commands.<br>
Format: `help`

The user can also view more information and examples of using a particular command.
Format: `help <Command Name>`

Example:
* `help add`
Displays more ways(eg: shortcuts) to use the add command 

#### Adding a Task: `add`
Adds a task to the task manager<br>
Format : `add <task name>`
   		 `add <task name> by <date/day>`
   		 `add <task name> by <date/day> at <time>`

Date Format: dd/mm/yyyy
Time Format: 12-hour clock ( Eg: 10am, 5pm, etc)

> If the date and time are not mentioned, the task is passed as a “Floating Task” and would be displayed separately.
> For the date/day, the user can type either the date, which would add the task to that specific day or type the day, which would add the task to the nearest day of the week which matches ‘day’

Examples:
* `add Read Harry Potter`
* `add CS2103T Tutorial by 01/10/2016`
* `add CS2101 reflection by Friday` 
	(if you type this on a Wednesday, it marks the slot for the coming Friday, that is, day after tomorrow)
* `add EE2021 assignment by next Thursday`
	(if you type this on a Wednesday, it marks the slot not for the next day, but for the Thursday after that, that is, 8 days later)
* `add EE2020 project by 30/10/2016 at 6pm`

#### Priority tasks: `priority`
Allows the user to set the priority of a task when creating it.<br>
Format : `priority <priority number>

> Priority 1 or 2 or 3 is based on how important the task is, with 3 being the highest priority and 1 being the lowest. It should always follow the add command.

Example:
* `add CS2101 by 01/01/2016 priority 3`

#### Undo a Task: `undo`
Undo the most recent task entered in the command line<br>
Format: `undo`

#### Deleting a Task: `delete`
Deletes a particular task in the task manager<br>
Format: `delete <task name>`

Example:
* `delete CS2103T Tutorial`

#### Adding a subtask: `add-s`
Adds a subtask to a main task<br>
Format: `add-s <main task name> <subtask>`
Example: 
* `add-s CS2103Tutorial Question1`

#### View: `donelist`
Displays all the completed task on that day for the user to view.<br>
Format: `donelist`

#### Editing a Task: `edit`
Allows the user to edit a particular task<br>
Format: `edit <name of task> <detail to edit> <new value>`

Example:
* `edit project meeting time 11am`
* `edit tutorial name CS2103T Tutorial`
* `edit project meeting date 21/11/2016`

#### View all task: `view`
Allows the user to view a list of all the undone tasks.<br>
Format: `view`

#### Mark as done: `done`
Marks a task as done. <br>
Format: `done <task name>`

Example:
* `done CS2103 tutorial`

##### Set alarms for tasks: `remind`
There are 2 ways to set an alarm for tasks. The first is to set the alarm when creating the task, and the second is to set it manually for a task that is already present.<br>
Format: 
* To set an alarm while creating a task, `add <task name> by <date/time>priority(optional) <priority type> remind by <date>  at <time>`
* To manually set an alarm for a currently present task, `remind <task name> by <date>  at <time>`

Examples:
* `add CS2101 by 21/09/2016 remind by 20/09/2016 at 5pm`
* `remind CS2101 by 20/09/2016 at 5pm`

#### Saving the data 
Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

