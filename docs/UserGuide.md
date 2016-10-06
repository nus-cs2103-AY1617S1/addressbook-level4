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
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : lists all task
   * **`add`**` Submission of CS2103 Project ed/11112016 et/0900hrs : 
     adds a task named `Submission of CS2103 Project` to the MustDoList.
   * **`delete`**` 1` : deletes the 1st task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

####6.1 Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`
 
####6.2.1 Adding a task: `add`
Adds a task to the MustDoList.<br>

Format1: `add TASK_NAME`
Format2: `add TASK_NAME by ed/END_DATE`
Format3: `add TASK_NAME by [sd/START_DATE] ed/END_DATE [st/START_TIME] et/END_TIME`

>[parameter] are optional, with or without this parameter it will work.
 >*Note: <br>
 *Enter without “[]”.
 *DATE Format: DDMMYYYY*
 *TIME Format: 0000 to 2359*

Examples: 
1. `add Submission of CS2103 Project`
2. `add Submission of CS2103 Project by ed/11112016`
3. `add Submission of CS2103 Project by ed/11112016 et/0900`
4. `add Submission of CS2103 Project by sd/01102016 ed/11112016 st/0900 et/0900`

####6.2.2 Adding parameters to existing task: `add PARAMETER` 

>You will be able to add extra informations for any existing task you have added or edited or deleted, by inserting this command ‘add PARAMETER’.
The PARAMETER include:
 ‘sd/START_DATE’ , ‘ed/END_DATE’ , ‘sd/START_DATE ed/END_DATE’ ,<br>
 ‘st/START_TIME’ , ‘et/END_TIME’ , ‘st/START_TIME et/END_TIME’ .<br>

Format1: add sd/START_DATE
Format2: add ed/END_DATE
Format3: add sd/START_DATE ed/END_DATE
Format4: add st/START_TIME
Format5: add et/END_TIME
Format6: add st/START_TIME et/ END_TIME

>*Note: <br>
 *DATE Format: DDMMYYYY*
 *TIME Format: 0000 to 2359*

Examples:
1. `add sd/01102016`
2. `add ed/11112016`
3. `add sd/01102016 ed/11112016`
4. `add st/0900`
5. `add et/0900`
6. `add st/0900 et/0900`

####6.3 Listing all tasks: `list`
Shows a list of all tasks in the MustDoList.<br>
Format: `list`

####6.4 Finding all tasks containing any keyword in their task_name, date, time: `find`
Finds task that contain any of the given keywords.<br>
Format: `find KEYWORD`

> The search is case sensitive, the order of the keywords does not matter (wait for feedback) and task matching at least one keyword will be returned (i.e. `OR` search).

Examples: 
* `find CS2103`<br>
  Returns `Submission of CS2103 Project` but not `CS2103`
* `find Submission of CS2103 Project`<br>
  Returns Any task having names `Submission `, `CS2103 `, or `Project`
* `find 0900`<br>
  Returns Any task having `0900`

####6.5 Select a task : `select`
Selects the task identified by the index number used in the last task listing.<br>
Format: `select INDEX`

> Selects the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `select 2`<br>
  Selects the 2nd task in the MustDoList.
* `find Submission` <br> 
  `select 1`<br>
  Selects the 1st task in the results of the `find` command.

####6.6.1 Deleting a task: `delete`
Deletes the specified task from the MustDoList. Irreversible.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the MustDoThis.
* `find Submission`<br> 
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

####6.6.2 Deleting a task: `delete`
Deletes the specified parameter from the MustDoList.<br>
Format: `delete PARAMETER`

> Deletes the task’s parameter at the specified `PARAMETER`.
 The parameter refers to: 
 ‘sd/START_DATE’ , ‘ed/END_DATE’ , ‘st/START_TIME’ , ‘et/END_TIME’ . <br>

Examples:
* `list`<br>
  `select 2`<br>
  `delete st/START_TIME`
  Selects the 2nd task in the MustDoList and delete its parameter st/START_TIME.
* `find Submission` <br> 
  `select 1`<br>
  `delete et/END_TIME`<br>
  Selects the 1st task in the results of the `find` command and delete its parameter et/END_TIME.

####6.7 edit a task : `edit`
Edits the specified parameter from the MustDoList.<br>
Format: `edit PARAMETER`

> Edits the task’s parameter at the specified ‘PARAMETER’.
 The parameter refers to: 
 ‘sd/START_DATE’ , ‘ed/END_DATE’ , ‘st/START_TIME’ , ‘et/END_TIME’ . <br>

Examples:
* `list`<br>
  `select 2`<br>
  `edit st/START_TIME`
  Selects the 2nd task in the MustDoList and edit its parameter st/START_TIME.
* `find Submission` <br> 
  `select 1`<br>
  `edit et/END_TIME`<br>
  Selects the 1st task in the results of the `find` command and edit its parameter et/END_TIME.

####6.8 Toggle between UI elements : `tab`   (Pressing)
Toggle between UI elements in the UI when pressing `tab`.

####6.9 Switching to next or previous (reuse) commands : `up-arrow or down-arrow`   (Pressing)
Switching to next or previous commands when press up-arrow or down-arrow.

####6.10 Undo action : `ctrl+z`
Pressing `ctrl+z` to return to previous action or previous state.

####6.11 Sorting the task list : `sort`
Sorting the task list based on parameters.<br>
Format: `sort PARAMETER`

> The PARAMETER refers to: <br>
 ‘task_name’ , ‘sd/START_DATE’ , ‘ed/END_DATE’ , ‘st/START_TIME’ , ‘et/END_TIME’ . 
> Sort by: 
  `task_name` will display the uncompleted task list in alphabetical order.
  `Date` or `Time` will display the earliest uncompleted task.

####6.12 Clearing all entries : `clear`
Clears all entries from the MustDoThis.<br>
Format: `clear`  

####6.13 Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

####6.14 Saving the data 
MustDoList data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous MustDoList.
       
## Command Summary

Command | Format  
-------- | :-------- 
Help | `help`
Add | `add TASK_NAME d/DAY m/MONTH y/YEAR t/TIME`
Add | `add PARAMETER`
List | `list`Select | `select INDEX`
Find | `find KEYWORD`
Select | `select INDEX`
Delete | `delete INDEX`
Delete | `delete PARAMETER`
Edit | `edit PARAMETER`
Toggle UI windows | `tab` (press)
Switching to next or previous commands | `Up-Arrow` or `Down-Arrow` (press)
Return to previous state or undo | `ctrl+z` (press)
Sort | `sort PARAMETER`
Clear | `clear`
Exit | `exit`