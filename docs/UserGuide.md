# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `MustDoList.jar` from the 'releases' tab.
2. Copy the file to the folder you want to use as the home folder for your MustDoList.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/Mock UI.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : lists all task
   * **`add`**`CS2103 Tutorial s/today 8am e/tomorrow 9am at NUS COM1-B103` : 
     adds a task named `CS2103 Tutorial` to the MustDoList.
   * **`delete`**` 1` : deletes the 1st task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`
 
#### Adding a task: `add`
Adds a floating task, task or an event to the MustDoList.<br>

Format 1: **`add`**`FLOATING TASK_NAME`<br>
Format 2: **`add`**`TASK NAME by END_TIME END_DATE`<br>
Format 3: **`add`**`EVENT NAME s/START_DATE_TIME e/END_DATE_TIME at LOCATION`<br>

> Date Format: DD-MMM-YYYY<br>
> Time Format: HH:MM am/pm<br> 

Examples:<br> 
* **`add`**`Do CS2103 Pretut`<br>
	Adding a floating task.<br>
* **`add`**`Do CS2103 Pretut by 8am 01-Oct-16 `<br>
	Adding a task.<br>
* **`add`**`CS2103 Tutorial s/today 8am e/tomorrow 9pm at NUS COM1-B103`<br>
* **`add`**`CS2103 Tutorial s/today 0800 e/tomorrow 2100 at NUS COM1-B103`<br>
	Adding an event.<br>	

#### Listing all tasks: `list`
Shows a list of all tasks in the MustDoList.<br>

Format: `list`

#### Find a task : `find`
Finds a task by keywords.<br>

Format: **`find`**`KEYWORD`

> KEYWORD refers to: task name, location, date, time, mark<br> 
> Date Format: DD-MMM-YYYY<br>
> Time Format: HH:MM am/pm<br>

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
  
#### Select a task : `setpath`
Set user specified filename and/or file directory.<br>

Format: **`setpath`**`FILENAME`

> FILENAME refers to the filename that you wants to save at.<br>

Examples:<br> 
* **`setpath`**`taskData`<br>
  Filename taskData will be created at default location data/taskData .
* **`setpath`**`backup/taskData`<br>
  Filename taskData will be created at location data/backup/taskData .
* **`setpath`**`c:/user/<name>/desktop/taskData`<br>
  Filename "taskData" will be created at user desktop.

#### Deleting a task: `delete`
Deletes a task from the MustDoList by index. <br>

Format: **`delete`**`INDEX`

> INDEX refers to the index number shown in the most recent listing.<br>

Examples:<br>
* `list`<br>
  **`delete`**`2`<br>
  Deletes the 2nd task in the MustDoList.

#### Clearing all entries : `clear`
Clears all entries from the TaskListPanel.<br>

Format: `clear`  

#### Edit a task : `edit`
Edits a task parameter from the MustDoList by index.<br>

Format: **`edit`**`INDEX TASK_NAME s/START_DATE_TIME e/END_DATE_TIME at LOCATION`

Examples:<br>
* `list`<br>
  **`edit`**`2 new task name s/10-Oct-2016 8am e/10-Oct-2016 9am at NUS`<br>
  Edit the 2nd task in the MustDoList by the given PARAMETERS.<br>
* **`find`**`CS2103` <br>
  **`edit`**`1 another new task name s/11-Oct-2016 8am e/11-Oct-2016 9am at there `<br>
  Edit the 1st task in the results of the `find` command by PARAMETERS.<br>
  
> Date Format: DD-MMM-YYYY<br>
Time Format: HH:MM am/pm<br>

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

#### Exiting the program : `exit`
Exits the program.<br>

Format: `exit` 

#### Reuse previous command : `up down arrow`
Select and display previously typed command using up or down arrow.<br>

#### Indicate overdue and completed task : `color code`
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

Command | Format  
-------- | :-------- 
Help | `help`
Add | **`add`**`FLOATING TASK NAME`
| 	| **`add`**`TASK NAME by END_DATE END_TIME`
|	| **`add`**`EVENT NAME s/START_DATE_TIME e/END_DATE_TIME at LOCATION`
List | **`list`**
Find | **`find`**`KEYWORD`
Select | **`select`**`INDEX`
Delete | **`delete`**`INDEX`
Clear | **`clear`**
Edit | **`edit`**`INDEX EVENT_NAME s/START_DATE_TIME e/END_DATE_TIME at LOCATION`
Undo | **`undo`**
|	 | `undo modification commands to task such as `
|	 | `add, clear, delete, edit, mark, recur.`
Mark | **`mark`**`INDEX` 
SetPath | **`setpath`**`FILENAME`
Exit | **`exit`**
UpDownArrow | `system display and select previously keyed commands`
ColorCode | `system indicate overdue(red) and completed(green) task by color code`
Recur | **`recur`**`INDEX every INTERVAL until END_DATE`
Save | system save automatically