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
   * **`add`**` Submission of CS2103 Project ed/11112016 et/0900hrs` : 
     adds a task named `Submission of CS2103 Project` to the MustDoList.
   * **`delete`**` 1` : deletes the 1st task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`
 
#### Adding a task: `add`
Adds a task to the MustDoList.<br>

Format1: **`add`**`TASK_NAME`<br>
Format2: **`add`**`TASK_NAME by ed/END_DATE`<br>
Format3: **`add`**`TASK_NAME by [sd/START_DATE] ed/END_DATE [st/START_TIME] et/END_TIME`

> [parameter] are optional, with or without this parameter it will work.<br>
DATE Format: DDMMYY<br>
TIME Format: 12HR<br>

Examples:<br> 
* **`add`**`Submission of CS2103 Project`<br>
* **`add`**`Submission of CS2103 Project by ed/11112016`<br>
* **`add`**`Submission of CS2103 Project by ed/11112016 et/0900`<br>
* **`add`**`Submission of CS2103 Project by sd/01102016 ed/11112016 st/0900 et/0900`<br>

#### Listing all tasks: `list`
Shows a list of all tasks in the MustDoList.<br>

Format: `list`

#### Find a task : `find`
Finds a task by keywords.<br>

Format: **`find`**`KEYWORD`

> KEYWORD refers to: task_name, location, date, time<br> 

Examples:<br> 
* **`find`**`CS2103`<br>
  Returns `Submission of CS2103 Project`
* **`find`**`Submission of CS2103 Project`<br>
  Returns Any task having names `Submission `, `CS2103 `, or `Project`
* **`find`**`9am`<br>
  Returns Any task having `9am`

#### Select a task : `select`
Selects a task from MustDoList by index.<br>

Format: `select INDEX`

> Index refers to the index number shown in the most recent listing.<br>
Selecting a task will open the web browser without clicking it with a mouse.<br>

Examples:<br> 
* `list`<br>
  **`select`**`2`<br>
  Selects the 2nd task in the MustDoList.
* **`find`**`Submission` <br> 
  **`select`**`1`<br>
  Selects the 1st task in the results of the `find` command.

#### Deleting a task: `delete`
Deletes a task from the MustDoList by index. Irreversible.<br>

Format: **`delete`**`INDEX`

> INDEX refers to the index number shown in the most recent listing.<br>

Examples:<br>
* `list`<br>
  **`delete`**`2`<br>
  Deletes the 2nd task in the MustDoList.
* **`find`**`Submission`<br> 
  **`delete`**`1`<br>
  Deletes the 1st task in the results of the `find` command.

#### Clearing all entries : `clear`
Clears all entries from the MustDoThis.<br>

Format: `clear`  

#### Edit a task : `edit`
Edits a task parameter from the MustDoList by index.<br>

Format: **`edit`**`INDEX PARAMETER`

> PARAMETER refers to: ‘sd/START_DATE’ , ‘ed/END_DATE’ , ‘st/START_TIME’ , ‘et/END_TIME’ . <br>

Examples:<br>
* `list`<br>
  **`edit`**`2 st/1000`<br>
  Edit the 2nd task in the MustDoList by PAREMETER st/START_TIME.
* **`find`**`Submission` <br>
  **`edit`**`1 et/1200`<br>
  Edit the 1st task in the results of the `find` command by PARAMETER et/END_TIME.

#### Undo a previous task : `undo`
Undo a previously added or edited task parameter from the MustdoList.<br>

Format: `undo`

#### Mark a completed task : `mark`
Marks a completed task by index.<br>

Format: `mark INDEX`

> INDEX refers to the index number shown in the most recent listing.<br>

Examples:<br>
* `list`<br>
  **`mark`**`2`<br>
  Marks the 2nd task in the MustDoList as completed.
* **`find`**`Submission`<br> 
  **`mark`**`1`<br>
  Marks the 1st task in the results of the `find` command as completed.  

#### Change preference for default storage : `setsavepath`
Changes the default storage path to user's preferred path.<br>

Format: `setsavepath ADDRESS`

> ADDRESS refers to the address of user's preferred storage path.<br>

Examples:<br>
* **`setsavepath`**`task`<br>

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit` 

#### Switching to next or previous (reuse) commands : `up-arrow or down-arrow`   (Pressing)
Switching to next or previous commands when press up-arrow or down-arrow.

#### Indicating overdue task : `color code (red)`
Indicating overdue task with color code (red)

####Saving the data 
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
Add | **`add`**`TASK_NAME by [sd/START_DATE] ed/END_DATE [st/START_TIME] et/END_TIME`
List | `list`
Find | **`find`**`KEYWORD`
Select | **`select`**`INDEX`
Delete | **`delete`**`INDEX`
Clear | `clear`
Edit | **`edit`**`INDEX PARAMETER`
Undo | `undo`
Mark | **`mark`**`INDEX`
Change preference for default storage | **`setsavepath`**`ADDRESS` 
Exit | `exit`
Switching to next or previous commands | `up-arrow or down-arrow`
Indicate overdue task | color code (RED)
Save | system save automatically

