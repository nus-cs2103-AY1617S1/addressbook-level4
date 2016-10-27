# User Guide

* [Description](#description)
* [String the program](#starting-the-program)
* [Features](#features)
* [Command Summary](#command-summary)

##Description
Tdoo Schedule Manager is a Command Line Interface Task Management Application that helps you manage your workflow. There are two main parts in the app: Todo List in which you can plan what tasks are to be done for the day, and Calendar that you indicate deadlines of the tasks or events to attend weekly/monthly. Data in Todo List and Calendar are synchronized. For instance, if a task is done before a deadline, it will be indicated as `done` in Calendar as well. At each starting time of tasks in Todo List, the app will give a notification that you should start working.

## Starting the program

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.

1. Download the latest `Tdoo.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your TodoList.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
4. Type the command in the command box and press <kbd>Enter</kbd> to execute it.<br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Refer to the [Features](#features) section below for details of each command.<br>

## Features

#### Viewing help : `help`
Show features and respective commands of the app.<br>
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

#### Adding a task: `add`
Adds a task to the task-list.<br>
Different Types of tasks (Todo/Event/Deadline) have different command format.<br>

###### Adding a Todo:
Todos will be rearranged in the Todo-List based on their priority.<br>
> Format: `add TASK_NAME p/PRIORITY`<br>
> Example: `add Assignment 3 from/25-12-2016 to/26-12-2016 p/1`

###### Adding an Event:
> Format: `add TASK_NAME from/DATE to/ENDDATE at/START_TIME to/END_TIME`<br>
> Example: `add Time's birthday party from/25-12-2016 to/26-12-2016 at/14:00 to/16:00`

###### Adding a Deadline:
> Format: `add TASK_NAME on/DATE at/END_TIME`<br>
> Example: `add CS2103 v0.2 on/25-12-2016 at/16:00`

#### Editing a task: `edit`
Edits information of the task in the task-list.<br>
Different Types of tasks (Todo/Event/Deadline) have different command format.<br>

###### Editing a Todo:
Todos will be rearranged in the Todo-List based on their priority.<br>
> Format: `edit TASK_TYPE INDEX_NUMBER name/TASK_NAME p/PRIORITY`<br>
> Example: `edit todo 1 name/Assignment p/2`

###### Editing an Event:
> Format: `edit TASK_TYPE INDEX_NUMBER name/TASK_NAME from/DATE to/ENDDATE at/START_TIME to/END_TIME`<br>
> Example: `edit event 1 name/Time's birthday party from/25-12-2016 to/26-12-2016 at/12:00 to/16:00`

###### Editing a Deadline:
> Format: `edit TASK_TYPE INDEX_NUMBER name/TASK_NAME on/DATE at/END_TIME`<br>
> Example: `edit deadline 1 name/CS2103 v0.2 on/25-12-2016 at/14:00`

#### Deleting a task : `delete`
Delete a task with given type and index number.<br>
> Format: `delete TASK_TYPE INDEX_NUMBER`<br>
> Example(Todo): `delete todo 1` <br>
> Example(Event): `delete event 1` <br>
> Example(Deadline): `delete deadline 1`

#### Marking a completed task : `done`
Mark a Todo-task with given index number as done.<br>
> Format: `done TASK_TYPE INDEX_NUMBER`<br>
> Example(Todo): `done todo 1` <br>
> Example(Event): `done event 1` <br>
> Example(Deadline): `done deadline 1`

#### Un-Marking a completed task : `undone`
Mark a Todo-task with given index number as undone.<br>
> Format: `undone TASK_TYPE INDEX_NUMBER`<br>
> Example(Todo): `undone todo 1` <br>
> Example(Event): `undone event 1` <br>
> Example(Deadline): `undone deadline 1`

#### Listing all tasks : `list`
Shows a list of all tasks in the task-list.<br>
> Format: `list TASK_TYPE` <br>
> Example(Todo): `list todo` <br>
> Example(Event): `list event` <br>
> Example(Deadline): `list deadline`

#### Finding all tasks containing any keyword in their name: `find`
Finds tasks whose names contain any of the given keywords.<br>
The order of the keywords does not matter, only the name is searched, and tasks matching at least one keyword will be returned (i.e. `OR` search).<br>
> Format: `find TASK_TYPE KEYWORD [MORE_KEYWORDS]` <br>
> Example(all): `find all School` <br>
> Example(Todo): `find todo Study` <br>
> Example(Event): `find event Party` <br>
> Example(Deadline): `find deadline Assignment`

#### Clearing all tasks : `clear`
Clears all data in the given task type list.<br>
> Format: `clear TASK_TYPE` <br>
> Example(all): `clear all` <br>
> Example(Todo): `clear todo` <br>
> Example(Event): `clear event` <br>
> Example(Deadline): `clear deadline`

#### Undo the previous command: `undo`
Undo the latest command. If there is no previous command, nothing will happen.<br>
> Format: `undo`

#### Change the Storage directory : `storage`
Change the storage directory of the app. If the given directory does not exist, the app will create a new directory with given name.<br>
> Format: `storage DIRECTORY` <br>
> Example: `storage /Desktop/Tdoo/date`

#### Exiting the program : `exit`
Exits the program.<br>
> Format: `exit`  

#### Saving the data
Task-list data are saved in the hard disk automatically after any command that changes the data.<br>


## Command Summary

Command 	| Format  
--------------- | :--------
Add	Todo	| `add TASK_NAME from/DATE to/ENDDATE p/PRIORITY`
Add	Event	| `add TASK_NAME from/DATE to/ENDDATE at/START_TIME to/END_TIME`
Add	Deadline| `add TASK_NAME on/DATE at/END_TIME`
Edit	Todo	| `edit TASK_TYPE INDEX_NUMBER name/TASK_NAME from/DATE to/ENDDATE p/PRIORITY`
Edit	Event	| `edit TASK_TYPE INDEX_NUMBER name/TASK_NAME from/DATE to/ENDDATE at/START_TIME to/END_TIME`
Edit	Deadline| `edit TASK_TYPE INDEX_NUMBER name/TASK_NAME on/DATE at/END_TIME`
--------------- | :--------
Add	Todo	| `add TASK_NAME d/DATE p/PRIORITY`
Add	Event	| `add TASK_NAME d/DATE s/START_TIME e/END_TIME`
Add	Deadline| `add TASK_NAME d/DATE e/END_TIME`
Edit	Todo	| `edit TASK_TYPE INDEX_NUMBER n/TASK_NAME d/DATE p/PRIORITY`
Edit	Event	| `edit TASK_TYPE INDEX_NUMBER n/TASK_NAME d/DATE s/START_TIME e/END_TIME`
Edit	Deadline| `edit TASK_TYPE INDEX_NUMBER n/TASK_NAME d/DATE e/END_TIME`
Delete		| `delete TASK_TYPE INDEX_NUMBER`
Done		| `done TASK_TYPE INDEX_NUMBER`
Undone		| `undone TASK_TYPE INDEX_NUMBER`
List		| `list TASK_TYPE`
Find		| `find TASK_TYPE KEYWORD [MORE_KEYWORDS]`
Help		| `help`
Clear		| `clear TASK_TYPE`
Undo		| `undo`
Storage		| `storage DIRECTORY`
Exit		| `exit`
