<!-- @@author A0138696L -->

# User Guide

* [Getting Started](#getting-started)
* [Features](#features)
 * [Help](#viewing-help--help)
 * [Add](#adding-a-task--add)
 * [List](#listing-all-tasks--list)
 * [Find](#find-a-task--find)
 * [Select](#select-a-task--select)
 * [Delete](#delete-a-task--delete)
 * [Clear](#clear-all-entries--clear)
 * [Edit](#edit-a-task--edit)
 * [Replace](#replace-a-task--replace)
 * [Undo](#undo-a-previous-task--undo)
 * [Mark](#mark-a-completed-task--mark)
 * [Umnark](#unmark-a-task--ummark)
 * [SetPath](#set-storage-path--setpath)
 * [Exit](#exit-the-program--exit)
 * [Reuse](#reuse-previous-command)
 * [Indicate task](#indicate-overdue-and-completed-task)
 * [Recur](#recur-a-task--recur)
 * [Save](#saving-the-data)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Getting Started

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough.<br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `MustDoList.jar` from the 'releases' tab.
2. Copy the file to the folder you want to use as the home folder for your MustDoList.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/Mock UI.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : lists all task
   * **`add`**`CS2103 Tutorial from 8am today to 9am tomorrow at NUS COM1-B103` : 
     adds a task named `CS2103 Tutorial` to the MustDoList.
   * **`delete`**` 1` : deletes the 1st task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>

## Features

> Command Format
* Words in `UPPER_CASE` are the parameters.<br>
* Items in `SQUARE_BRACKETS` are optional.<br>
* Items in `...` after them can have multiple instances.<br>
* Parameters can be in any order.<br>

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`
 
#### Adding a task: `add`
Adds a floating task, task or an event to the MustDoList.<br>

Format 1: **`add`**`FLOATING TASK_NAME`<br>
Format 2: **`add`**`TASK NAME by END_TIME END_DATE`<br>
Format 3: **`add`**`EVENT NAME from START_TIME_DATE to END_TIME_DATE at LOCATION`<br>

> Date Format: DD-MMM-YYYY<br> 
where `DD` refers to the day, `MMM` refers to the first 3 letters of the month and `YYYY` refers to the year.<br>
> Time Format: HH:MM am/pm<br>
where `HH` refers to hours and `MM` refers to minutes.<br> 

Examples:<br> 
* **`add`**`Do CS2103 Pretut`<br>
	Adding a floating task.
* **`add`**`Do CS2103 Pretut by 8am 01-Oct-2016`<br>
	Adding a task.
* **`add`**`CS2103 Tutorial from 8am today to 9am tomorrow at NUS COM1-B103`<br>
	Adding an event.	

#### Listing all tasks: `list`
Shows a list of all tasks in the MustDoList.<br>

Format: `list`

#### Find a task : `find`
Finds a task by keywords.<br>

Format: **`find`**`KEYWORD`

> KEYWORD refers to: task name, location, date, time, completed, incomplete<br> 
> Date Format: DD-MMM-YYYY<br>
where `DD` refers to the day, `MMM` refers to the first 3 letters of the month and `YYYY` refers to the year.<br>
> Time Format: HH:MM am/pm<br>
where `HH` refers to hours and `MM` refers to minutes.<br> 

Examples:<br> 
* **`find`**`CS2103`<br>
  Returns Any task(s) having names `CS2103`
* **`find`**`09-Oct-2016`<br>
  Returns Any task(s) with date 09-Oct-2016
* **`find`**`completed`<br>
  Returns Any task(s) are marked as completed<br>

#### Select a task : `select`
Selects a task from MustDoList by index.<br>

Format: **`select`**`INDEX`

> Index refers to the index number shown in the most recent listing.<br>
Selecting a task will open the web browser without clicking it with a mouse.<br>

Examples:<br> 
* `list`<br>
  **`select`**`2`<br>
  Selects the 2nd task in the MustDoList.
* **`find`**`CS2103` <br> 
  **`select`**`1`<br>
  Selects the 1st task in the results of the `find` command.

#### Delete a task: `delete`
Deletes a task from the MustDoList by index. <br>

Format: **`delete`**`INDEX`

> INDEX refers to the index number shown in the most recent listing.<br>

Examples:<br>
* `list`<br>
  **`delete`**`2`<br>
  Deletes the 2nd task in the MustDoList.

#### Clear all entries : `clear`
Clears all entries from the TaskListPanel.<br>

Format: `clear`  

#### Edit a task : `edit`
Edits a task specific parameter from the MustDoList by index.<br>

Format: **`edit`**`INDEX [TASK_NAME] [from START_TIME_DATE to END_TIME_DATE] [at LOCATION]`

Examples:<br>
* **`edit`**`1 Must Do CS2103 Pretut`<br>
  Edit the 1st task in the MustDoList by a specific task name.<br>
* **`edit`**`2 at NUS COM1-B103` <br>
  Edit the 2nd task in the MustDoList by a specific location.<br>
* **`edit`**`1 from 8am 11-Oct-2016 to 9am 11-Oct-2016`<br>
  Edit the 1st task in the MustDoList by a specific time and date.<br>
  
> Date Format: DD-MMM-YYYY<br>
where `DD` refers to the day, `MMM` refers to the first 3 letters of the month and `YYYY` refers to the year.<br>
Time Format: HH:MM am/pm<br>
where `HH` refers to hours and `MM` refers to minutes.<br> 

#### Replace a task : `replace`
Edits a task parameter from the MustDoList by index.<br>

Format: **`replace`**`INDEX TASK_NAME from START_TIME_DATE to END_TIME_DATE at LOCATION`

Examples:<br>
* `list`<br>
  **`replace`**`2 new task name from 8am 10-Oct-2016 to 9am 10-Oct-2016 at NUS`<br>
  Edit the 2nd task in the MustDoList by the given PARAMETERS.<br>
* **`find`**`CS2103` <br>
  **`replace`**`1 another new task name from 8am 11-Oct-2016 to 9 am 11-Oct-2016 at there `<br>
  Edit the 1st task in the results of the `find` command by PARAMETERS.<br>
  
> Date Format: DD-MMM-YYYY<br>
where `DD` refers to the day, `MMM` refers to the first 3 letters of the month and `YYYY` refers to the year.<br>
Time Format: HH:MM am/pm<br>
where `HH` refers to hours and `MM` refers to minutes.<br> 

#### Undo a previous task : `undo`
Undo a previously add, edit, delete, mark, clear and recur command in the MustdoList.<br>

Format: `undo`

#### Mark a completed task : `mark`
Marks a completed task by index.<br>

Format: **`mark`**`INDEX`

> INDEX refers to the index number shown in the most recent listing.<br>

Examples:<br>
* `list`<br>
  **`mark`**`2`<br>
  Marks the 2nd task in the MustDoList as completed.
* **`find`**`CS2103`<br> 
  **`mark`**`1`<br>
  Marks the 1st task in the results of the `find` command as completed.  
  
#### Unmark a task : `ummark`
Unmarks a task by index.<br>

Format: **`unmark`**`INDEX`

> INDEX refers to the index number shown in the most recent listing.<br>

Examples:<br>
* `list`<br>
  **`unmark`**`2`<br>
  Unmarks the 2nd task in the MustDoList as completed.
* **`find`**`CS2103`<br> 
  **`unmark`**`1`<br>
  Unmarks the 1st task in the results of the `find` command as completed. 

#### Set storage path : `setpath`
Set path for saved data path.<br>

Format: **`setpath`**`FILENAME`<br>

> FILENAME refers to the filename that you wants to save at.<br>

Examples:<br>
* **`setpath`**`taskData`<br>
  Filename taskData will be created at default location data/taskData.<br>
* **`setpath`**`backup/taskData`<br>
  Filename taskData will be created at location data/backup/taskData.<br>
* **`setpath`**`c:/user/<name>/desktop/taskData`<br>
  Filename taskData will be created at user desktop.<br>

#### Exit the program : `exit`
Exits the program.<br>

Format: `exit` 

#### Reuse previous command
Select and display previously typed command using <kbd>Up</kbd> <kbd>Down</kbd> <br>

#### Indicate overdue and completed task
Indicate overdue and completed task with color code.<br>

Overdue Task: Red Color Code<br>
Completed Task: Green Color Code<br>

> Overdue Task refers to task that has date and time that passes the current date and time.<br>
Completed Task refers to task that are marked as "completed".

####Recur a task : `recur`
Recur a task for a specific numbers of days.<br>

Format 1: **`recur`**`every INTERVAL until END_DATE`<br>
Format 2: **`recur`**`INDEX every INTERVAL until END_DATE`<br>

>INTERVAL refers to the number of days to recur.<br> 
INDEX refers to the index number shown in the most recent listing.<br>
Date Format: DD-MMM-YYYY
where `DD` refers to the day, `MMM` refers to the first 3 letters of the month and `YYYY` refers to the year.<br>

Examples:<br>
* **`recur`**`every 2 days until 19-Oct-2016`<br>
  Recur the latest task for 2 days until 19-Oct-2016 in the MustDoList.
* **`recur`**`1 every 2 months until 1 year later`<br>
  Recur the task with index 1 for 2 months until 1 year from current date in the MustDoList.

####Saving the data 
MustDoList data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous MustDoList.
       
## Command Summary

* Help: `help`

* Add: **`add`**`FLOATING TASK NAME`<br>
**`add`**`TASK NAME by END_TIME END_DATE`<br> 
**`add`**`EVENT NAME from START_TIME_DATE to END_TIME_DATE at LOCATION`<br>
e.g. **`add`**`Do CS2103 Pretut`<br>
e.g. **`add`**`Do CS2103 Pretut by 8am 01-Oct-2016`<br>
e.g. **`add`**`CS2103 Tutorial from 8am today to 9am tomorrow at NUS COM1-B103`

* List: `list`

* Find: **`find`**`KEYWORD`<br>
e.g. **`find`**`CS2103`

* Select: **`select`**`INDEX`<br>
e.g. **`select`**`1`

* Delete: **`delete`**`INDEX`<br>
e.g. **`delete`**`1`

* Clear: `clear`

* Edit: **`edit`**`[INDEX EVENT_NAME][from START_TIME_DATE to END_TIME_DATE][at LOCATION]`<br>
e.g. **`edit`**`1 Must Do CS2103 Pretut`<br>
e.g. **`edit`**`2 at NUS COM1-B103`<br>
e.g. **`edit`**`1 from 8am 11-Oct-2016 to 9am 11-Oct-2016`

* Replace: **`replace`**`INDEX EVENT_NAME from START_TIME_DATE to END_TIME_DATE at LOCATION`<br>
e.g. **`replace`**`2 new task name from 8am 10-Oct-2016 to 9am 10-Oct-2016 at NUS`<br>

* Undo: `undo`

* Mark: **`mark`**`INDEX`<br>
e.g. **`mark`**`1`

* Unmark: **`unmark`**`INDEX`<br>
e.g. **`ummark`**`1`

* SetPath: **`setpath`**`FILENAME`<br>
e.g. **`setpath`**`taskData`

* Recur: **`recur`**`INDEX every INTERVAL until END_DATE`<br>
e.g. **`recur`**`every 2 days until 19-Oct-2016`

* Exit: `exit`

* <kbd>Up</kbd><kbd>Down</kbd>: system display and select previously keyed commands

* ColorCode: system indicate overdue(red) and completed(green) task by color code

* Save: system save automatically