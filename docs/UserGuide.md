<!-- @@author A0138696L -->

# User Guide

* [Getting Started](#getting-started)
* [Features](#features)
 * [Help](#getting-help--help)
 * [Add](#how-to-add-a-task--add)
 * [List](#how-to-list-tasks--list)
 * [Find](#how-to-find-a-task--find)
 * [Delete](#how-to-delete-a-task--delete)
 * [Clear](#how-to-clear-all-entries--clear)
 * [Edit](#how-to-edit-a-task--edit)
 * [Replace](#how-to-replace-a-task--replace)
 * [Undo](#how-to-undo-a-task--undo)
 * [Redo](#how-to-redo-a-task)
 * [Mark](#how-to-mark-a-task--mark)
 * [Unmark](#how-to-unmark-a-task--ummark)
 * [Recur](#how-to-recur-a-task--recur)
 * [Set Path](#how-to-set-storage-path--setpath)
 * [Indicate task](#how-to-identify-overdue-and-completed-task)
 * [Exit](#how-to-exit-the-program--exit)
 * [Save](#how-to-save-the-data)
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

#### Getting help : `help`
The `help` command provides the user guide to guide the user through the application.

Help format: `help`

> Help is also shown if you click on help on the menu bar.
 
#### How to add a task : `add`
The `Add` command allows you to create a floating task, task or an event into the MustDoList.<br>

Floating task format: **`add`**`FLOATING TASK_NAME`<br>
Deadline task format: **`add`**`TASK NAME by END_TIME END_DATE`<br>
Event format		: **`add`**`EVENT NAME from START_TIME_DATE to END_TIME_DATE at LOCATION`<br>

> The command format requires the following date and time format.<br>
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

#### How to list tasks : `list`
The `list` command allows you to show a list of all the tasks that are present in the MustDoList.<br>

Format: `list`

#### How to find a task : `find`
The `find` command allows you to find existing tasks in the MustDoList by keywords.<br>

Find format: **`find`**`KEYWORD`

> The KEYWORD in the command format refers to parameters that you want to search for. Some examples of KEYWORD are task name, location, date, time, completed and incomplete tasks.<br> 
> The command format requires the following date and time format.<br>
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

#### How to delete a task : `delete`
The `delete` command allows you to delete any existing task from the MustDoList by index. <br>

Delete format: **`delete`**`INDEX`

> The INDEX in the command format refers to the index number shown in the most recent listing.<br>

Examples:<br>
* `list`<br>
  **`delete`**`2`<br>
  Deletes the 2nd task in the MustDoList.

#### How to clear all entries : `clear`
The `clear` command allows you to clear all entries present in the MustDoList.<br>

Clear format: `clear`  

#### How to edit a task : `edit`
The `edit` command allows you to edit a specific task's parameter from the MustDoList by the task's index.<br>

Edit format: **`edit`**`INDEX [TASK_NAME][from START_TIME_DATE to END_TIME_DATE][at LOCATION]`

Examples:<br>
* **`edit`**`1 Must Do CS2103 Pretut`<br>
  Edit the 1st task's task name in the MustDoList.<br>
* **`edit`**`2 at NUS COM1-B103` <br>
  Edit the 2nd task's location in the MustDoList.<br>
* **`edit`**`1 from 8am 11-Oct-2016 to 9am 11-Oct-2016`<br>
  Edit the 1st task's time and date in the MustDoList.<br>
  
> The command format requires the following date and time format.<br>
> Date Format: DD-MMM-YYYY<br>
where `DD` refers to the day, `MMM` refers to the first 3 letters of the month and `YYYY` refers to the year.<br>
> Time Format: HH:MM am/pm<br>
where `HH` refers to hours and `MM` refers to minutes.<br> 

#### How to replace a task : `replace`
The `replace` command allows you to replace the entire task's parameter from the MustDoList by task's index.<br>

Replace format: **`replace`**`INDEX TASK_NAME from START_TIME_DATE to END_TIME_DATE at LOCATION`

Examples:<br>
* `list`<br>
  **`replace`**`2 new task name from 8am 10-Oct-2016 to 9am 10-Oct-2016 at NUS`<br>
  Replace the 2nd task's parameters in the MustDoList.<br>
* **`find`**`CS2103` <br>
  **`replace`**`1 another new task name from 8am 11-Oct-2016 to 9 am 11-Oct-2016 at there `<br>
  Replace the 1st task's parameters in the results of the `find` command.<br>
  
> The command format requires the following date and time format.<br>
> Date Format: DD-MMM-YYYY<br>
where `DD` refers to the day, `MMM` refers to the first 3 letters of the month and `YYYY` refers to the year.<br>
> Time Format: HH:MM am/pm<br>
where `HH` refers to hours and `MM` refers to minutes.<br> 

#### How to undo a task : `undo`
The `undo` command allows you to undo the previous entered commands into the MustdoList.<br>

Undo format: `undo`

#### How to redo a task : <kbd>Up</kbd> and <kbd>Down</kbd>
The <kbd>Up</kbd> <kbd>Down</kbd> allows you to select and display previous typed command in the command box.<br>

#### How to mark a task : `mark`
The `mark` command allows you to mark a completed task by the task's index.<br>

Mark format: **`mark`**`INDEX`

> The INDEX in the command format refers to the index number shown in the most recent listing.<br>

Examples:<br>
* `list`<br>
  **`mark`**`2`<br>
  Marks the 2nd task in the MustDoList as completed.
* **`find`**`CS2103`<br> 
  **`mark`**`1`<br>
  Marks the 1st task in the results of the `find` command as completed.  
  
#### How to unmark a task : `ummark`
The `unmark` command allows you to unmark a task by the task's index.<br>

Unmark format: **`unmark`**`INDEX`

> The INDEX in the command format refers to the index number shown in the most recent listing.<br>

Examples:<br>
* `list`<br>
  **`unmark`**`2`<br>
  Unmarks the 2nd task in the MustDoList as completed.
* **`find`**`CS2103`<br> 
  **`unmark`**`1`<br>
  Unmarks the 1st task in the results of the `find` command as completed. 
  
#### How to recur a task : `recur`
The `recur` command allows you to recur a task for a specific numbers of days.<br>

Recur previous format: **`recur`**`every INTERVAL until END_DATE`<br>
Recur index format: **`recur`**`INDEX every INTERVAL until END_DATE`<br>

> The INTERVAL in the command format refers to the number of days you want to recur.<br> 
> The INDEX in the command format refers to the index number shown in the most recent listing.<br>
> The command format requires the following date format.<br>
> Date Format: DD-MMM-YYYY<br>
where `DD` refers to the day, `MMM` refers to the first 3 letters of the month and `YYYY` refers to the year.<br>

Examples:<br>
* **`recur`**`every 2 days until 19-Oct-2016`<br>
  Recur the latest task for 2 days until 19-Oct-2016 in the MustDoList.
* **`recur`**`1 every 2 months until 1 year later`<br>
  Recur the task with index 1 for 2 months until 1 year from current date in the MustDoList.

#### How to set a storage path : `setpath`
The `setpath` command allows you to save your file to your desire set path.<br>

Set path format: **`setpath`**`FILENAME`<br>

> The FILENAME in the command format refers to the path that you wants to save your file to.<br>

Examples:<br>
* **`setpath`**`taskData`<br>
  Filename taskData will be created at default location data/taskData.<br>
* **`setpath`**`backup/taskData`<br>
  Filename taskData will be created at location data/backup/taskData.<br>
* **`setpath`**`c:/user/<name>/desktop/taskData`<br>
  Filename taskData will be created at user desktop.<br>

#### How to exit the program : `exit`
The `exit` command allows you to exits the program.<br>

Exit format: `exit` 

#### How to identify overdue and completed task
Overdue and completed tasks can be identified by the color codes.<br>

Overdue Task: Red Color Code<br>
Completed Task: Green Color Code<br>

> Overdue Task refers to task that has date and time that passes the current date and time.<br>
Completed Task refers to task that are marked as "completed".

#### How to save the data 
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

* Recur: **`recur`**`INDEX every INTERVAL until END_DATE`<br>
e.g. **`recur`**`every 2 days until 19-Oct-2016`

* SetPath: **`setpath`**`FILENAME`<br>
e.g. **`setpath`**`taskData`

* Exit: `exit`

* <kbd>Up</kbd> <kbd>Down</kbd>: system display and select previously keyed commands

* ColorCode: system indicate overdue(red) and completed(green) task by color code

* Save: system save automatically