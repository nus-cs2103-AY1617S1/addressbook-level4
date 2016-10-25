# User Guide

* [Introduction](#introduction)
* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

<br>
## Introduction

Welcome! This user guide will provide you with all the essential information required for you to make full use of our task manager, TasKitty.

TasKitty is a task manager that can help you manage events, deadlines that you have to meet, or simply tasks that you want to get done whenever you have free time.

If you are a keyboard lover and dislike clicking, then TasKitty is the right task manager for you! It boasts an intuitive command line interface with minimal clicking required, and the commands you have to type in are short and sweet. 

To get started, proceed to the Quick Start section below.

<br>
## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `TasKitty.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your TasKitty.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`add`**` read book` : adds a new task named `read book`. 
   * **`add`**` math homework 1 Jan 2015 2pm` : adds a new task that has the deadline `1 Jan 2015 2pm`.
   * **`view`** : lists all tasks for today.
   * **`view`**` 1 Jan 2015` : lists all tasks for specific date.
   * **`delete`**` d1` : deletes the 1st task shown in the current list of deadline tasks.
   * **`exit`** : exits the program.
6. Refer to the [Features](#features) section below for details of each command.<br>

<br>
## Features

<br>
#### View help : `help`
Format: `help`

> A pop-up window displaying the command summary will be shown. Help is also shown if you enter an incorrect command e.g. `abcd`.<br>
<img src="images/UIhelp.png" width="600">


<br>
#### View all tasks: `view`
Lists all tasks for the specified date and deadlines up to the specified date.<br>
Format: `view [DATE]`

All tasks for the specified date and deadlines up to the specified date will be displayed. If no date is specified, all tasks for today and all deadlines will be displayed.

Examples:

* `view`<br> 
  <img src="images/UIview.png" width="600">

* `view 16 Oct 2016`<br> 
  <img src="images/UIviewDate.png" width="600">

<br>  
#### Create a new task: `add`
Adds a new task to the todo or deadlines list, or a new event to the event calendar.<br>
* Todo format: `add NAME`<br>
* Deadline format: `add NAME END_DATE_TIME`<br>
* Event format: `add NAME START_DATE_TIME to END_DATE_TIME`

> **Command Format**<br><br>
> Tasks are split into 3 categories: `todo`, `deadline`, `event`.<br>
> `todo`: Tasks that have no specific date/time to be completed by.<br>
> `deadline`: Tasks that have a specific date/time they must be completed by.<br>
> `event`: Tasks that have specific start and end date/time.<br>

> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * `DATE_TIME` parameter can accept different formats. 2 Jan 2015 3pm, 15:00 2/1/2015 are acceptable formats to represent 2 Jan 2015, 15:00.
> * `DATE_TIME` parameter can also accept relative date formats such as tomorrow and next friday.
> * The order of parameters is fixed.

> Depending on the input format, the task will be saved into 1 of 3 categories: `todo`, `deadline` or `event`.

Examples:

* `add study for test`<br>
  Adds a `todo` task with NAME as `study for test`.
  <img src="images/UItodo.png" width="600">

* `add assignment 4 17 Oct 2016 2pm`<br>
  Adds a `deadline` task with NAME as `assignment 4`, DATE as `17 Oct 2016`, END_TIME as `2pm`.
  <img src="images/UIdeadline.png" width="600">

* `add walk dog 5 Oct 2016 17:00 to 18:00`<br>
  Adds an `event` task with NAME as `walk dog`, DATE as `5 Oct 2016`, START\_TIME as `17:00`, END\_TIME as `18:00`.
  <img src="images/UIevent.png" width="600">

* `add bring dog to vet 8 Oct 2016 17:00 to 18:00`<br>
  Adds an `event` task with NAME as `bring dog to vet`, DATE as `8 Oct 2016`, START\_TIME as `17:00`, END\_TIME as `18:00`.
  > Note that this event is set in the future, so please enter `view 8 Oct 2016` to view the timetable for the added event.<br>
  <img src="images/UIeventFuture.png" width="600">

<br>
#### Find tasks: `find`
Finds tasks based on keywords.<br>
Format: `find KEYWORDS...`

Tasks that partly or completely match the keywords entered will be displayed.<br><br>

Example: 
* `find assignment`<br>
  <img src="images/UIfind.png" width="600">


<br>
#### Edit task details: `edit`
Edits a todo, deadline or event already inside the task manager using the index of the task.<br>
* Format: `edit INDEX [NEW_NAME] [NEW_DATE] [NEW_START_TIME] [NEW_END_TIME]`

> Edits a task at the specified `INDEX` under the todos `t`, deadlines `d` or events `e` section. The `INDEX` refers to the category and index number shown in the most recent listing. eg. `t1` `d2` `e3` <br>
If no or an invalid category was listed, the app will default to todo format `t`. eg. `1` and `+1` becomes `t1` <br><br>
> Format depends on the type of task being edited. When only 1 `TIME` is provided, it is treated as `END_TIME` for both deadline and event.<br><br>
> Note that you are required to enter the `view` command before the `edit` command, in order to view the list of tasks and events and edit the specified task accordingly. Alternatively, you can use the [`find`](#find-tasks-find) command to narrow down the displayed list of tasks and events.

Examples:

* `view`<br>
  `edit d1 assignment 2 15 Oct 2016`<br>
  Edits the 1st task under the delete tasks section. Change the NAME to `assignment 2` and DATE to `15 Oct 2016`.<br>
  <img src="images/UIedit.png" width="600">

* `edit e2 22:00 00:00`<br>
  Edits the 2nd task under the events section. Change the START_TIME to `22:00` and END_TIME to `00:00`.<br>

<br>
#### Delete task: `delete`
Deletes a todo, deadline or event already inside the task manager using the index of the task.<br>
* Format: `delete INDEX...`

Examples:

* `view`<br>
  `delete d1`<br>
  Deletes the 1st task under the deadlines section as shown by the `view` command.<br>
  
  Before:<br>
  <img src="images/UIdeleteBefore.png" width="600"><br>
  
  After:<br>
  <img src="images/UIdeleteAfter.png" width="600"><br>
  
* `view 5 Oct 2016`<br>
  `delete e3`<br>
  Deletes the 3rd task under the events section for 5 Oct 2016 as shown by the `view` command.<br>
  
  Before:<br>
  <img src="images/UIdeleteBeforeDate.png" width="600"><br>
  
  After:<br>
  <img src="images/UIdeleteAfterDate.png" width="600"><br>

<br>
#### Mark task as done: `done`
Marks a task in the task list as done.<br>
* Format: `done INDEX...`

Tasks that are marked as done are moved to the bottom of the list in their respective sections.<br><br>

Example:

* `view`<br>
  `done t1`<br>
  Marks the 1st task today under the todo section shown by the `view` command as completed.<br>
  
  Before:<br>
  <img src="images/UIdoneBefore.png" width="600"><br>
  
  After:<br>
  <img src="images/UIdoneAfter.png" width="600"><br>
 
<br>
#### Undo previous action: `undo`
Undoes the last completed action.<br>
Format: `undo`

The previous version will be restored.<br>
User can keep retyping undo to undo multiple actions.<br>

Example:
* `undo`<br>
  Undoes the last deleted item.<br>
  
  Before:<br>
  <img src="images/UIundoBefore.png" width="600"><br>
  
  After:<br>
  <img src="images/UIundoAfter.png" width="600"><br>

<br>
#### Save data: `save`
Saves data to a specified folder.<br>
Format: `save FILEPATH`

* Windows OS FILEPATH format example: `C:\\Users\\<username>\\Desktop\\CS2103 Tutorial`
* Mac OS FILEPATH format example: `/Users/<username>/Desktop/CS2103 Tutorial`

TasKitty will save any other FILEPATH format in the same directory as TasKitty<br>
TasKitty will automatically create the folder if the folder is not present<br>

Example:
* `save /Users/<username>/Desktop/CS2103 Tutorial`<br>
  Saves TasKitty data into the folder CS2103 Tutorial<br>
  If folder CS2103 Tutorial is not present, TasKitty will create the folder.

<br>
#### Clearing all entries : `clear`
Clears all tasks from the task manager.<br>
Format: `clear`  
> <img src="images/UIclear.png" width="600">

<br>
#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

<br>
#### Saving the data 
Task manager data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

<br>
## FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous task manager's folder.      

<br>
## Command Summary

Command | Format  
-------- | :-------- 
Add todo | `add NAME`
Add deadline | `add NAME DATE END_TIME`
Add event | `add NAME DATE START_TIME END_TIME`
View | `view [DATE]`
View done | `view done`
Find | `find KEYWORDS...`
Edit | `edit INDEX [NEW_NAME] [NEW_DATE] [NEW_START_TIME] [NEW_END_TIME]`
Delete | `delete INDEX...`
Done | `done INDEX...`
Save | `save FILEPATH`
Undo | `undo`
Help | `help`
Clear | `clear`
Exit | `exit`
