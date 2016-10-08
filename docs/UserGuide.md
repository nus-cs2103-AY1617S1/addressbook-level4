# User Guide

* [Introduction](#introduction)
* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)


## Introduction

Welcome user! This user guide will provide you with all essential information required for you to make full use of our task manager, TasKitty.

TasKitty is a task manager that can help you manage events, deadlines that you have to meet, or simply tasks that you want to get done whenever you have free time.

If you are a keyboard lover and dislike clicking, then TasKitty is the right task manager for you! It boasts an intuitive command line interface with minimal clicking required, and the commands you have to type in are short and sweet. 

To get started, proceed to the Quick Start section below.


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
   * **`delete`**` 1` : deletes the 1st task shown in the current list of tasks.
   * **`exit`** : exits the program.
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**<br>
> Tasks are split into 3 categories: `todo`, `deadline`, `event`.<br>
> `todo`: Tasks that have no specific date/time to be completed by.<br>
> `deadline`: Tasks that have a specific date/time they must be completed by.<br>
> `event`: Tasks that have specific start and end date/time.<br>

> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * `DATE` parameter can accept different formats. 1 Jan 2015, 010115, 01/01/2015 are all acceptable to represent 1 Jan 2015.
> * `START_TIME` and `END_TIME` parameters can accept different formats. 3pm, 15:00, 1500 are all acceptable to represent 3pm.
> * The order of parameters is not fixed.

#### Viewing help : `help`
Format: `help`

> A pop-up windonw displaying the command summary will be shown. Help is also shown if you enter an incorrect command e.g. `abcd`.<br>
> <img src="images/UIhelp.png" width="600">

#### Create a new task: `add`
Add a new task to the tasks list or a new event to the event calendar.<br>
Todo format: `add NAME`<br>
Deadline format: `add NAME DATE END_TIME`<br>
Event format: `add NAME DATE START_TIME END_TIME`

> Depending on the input format, the task will be saved into 1 of 3 categories: todo, deadline or event.

Examples:
* `add study for test`<br>
  Add a todo task with NAME as `study for test`.
  > <img src="images/UItodo.png" width="600">

* `add assignment 4 17 Oct 2016 2pm`<br>
  Add a deadline task with NAME as `assignment 4`, DATE as `17 Oct 2016`, END_TIME as `2pm`.
  > <img src="images/UIdeadline.png" width="600">

* `add walk dog 5 Oct 2016 17:00 18:00`<br>
  Add an event task with NAME as `walk dog`, DATE as `5 Oct 2016`, START_TIME as `17:00`, END_TIME as `18:00`.
  > <img src="images/UIevent.png" width="600">

* `add bring dog to vet 8 Oct 2016 17:00 18:00`<br>
  Add an event task with NAME as `walk dog`, DATE as `8 Oct 2016`, START_TIME as `17:00`, END_TIME as `18:00`.
  > Note that this event is set in the future, so please enter `view 8 Oct 2016` to view the timetable for the added event.<br>
  > <img src="images/UIeventFuture.png" width="600">
  

#### View all tasks: `view`
View all tasks for the specified date and deadlines up to the specified date.<br>
Format: `view [DATE]`

> All tasks for the specified date and deadlines up to the specified date will be displayed. If no date is specified, all tasks for today and all deadlines will be displayed.

Examples: 
* `view`
  > <img src="images/UIview.png" width="600">

* `view 1 Jan 2015`
  > <img src="images/UIviewDate.png" width="600">

#### Find tasks: `find`
Find tasks based on keywords.<br>
Format: `find KEYWORDS`

> Tasks that partly or completely match the keywords entered will be displayed.<br>
> If keyword entered is a date, this command will return the same results as the view command.

Examples: 
* `find assignment`<br>
  > <img src="images/UIfind.png" width="600">

#### Edit task details: `edit`
Edit a task or event already inside the task manager/ event calendar using the index of the task.<br>
Format: `edit INDEX [NEW_NAME] [NEW_DATE] [NEW_START_TIME] [NEW_END_TIME]`

> Format depends on the type of task being edited. When only 1 TIME is provided, it is treated as END_TIME for both deadline and event.

Examples:
* `view`<br>
  `edit d 1 assignment 2 15 Oct 2016`<br>
  Edit the 1st task under the todo tasks section. Changes the NAME to `assignment 2` and DATE to `15 Oct 2016`.<br>
  > <img src="images/UIedit.png" width="600">

* `edit e 2 22:00 00:00`<br>
  Edit the 2nd task under the events section. Changes the START_TIME to `22:00` and END_TIME to `00:00`.<br>

#### Delete task: `delete`
Delete a task inside the task or deadline list or an event inside the calendar.<br>
Delete task format: `delete t INDEX`
Delete deadline format: `delete d INDEX`
Delete event format: `delete e INDEX`

> Delete a task at the specified INDEX under the tasks `t`, deadlines `d` or events `e` section. The INDEX refers to the index number shown in the most recent listing.

Examples:
* `view`<br>
  `delete d 1`<br>
  Delete the 1st task under the deadlines section as shown by the `view` command.<br>
  
  Before:<br>
  > <img src="images/UIdeleteBefore.png" width="600"><br>
  
  After:<br>
  > <img src="images/UIdeleteAfter.png" width="600"><br>
  
* `view 5 Oct 2016`<br>
  `delete e 3`<br>
  Delete the 3rd task under the events section for 5 Oct 2016 as shown by the `view` command.<br>
  
  Before:<br>
  > <img src="images/UIdeleteBeforeDate.png" width="600"><br>
  
  After:<br>
  > <img src="images/UIdeleteAfterDate.png" width="600"><br>

#### Mark task as done: `done`
Mark a task in the task list as done.<br>
Todo done format: `done t INDEX`
Deadline done format: `done d INDEX`
Event done format: `done e INDEX`

> Marks a todo `t`, deadline `d` or event `e` at the specified INDEX as completed. The INDEX refers to the index number shown in the most recent listing.<br>
> Note that tasks that are done are moved to the bottom of the list in their respective sections.

Examples:
* `view`<br>
  `done t 1`<br>
  Mark the 1st task today under the todo section shown by the `view` command as completed.<br>
  
  Before:<br>
  > <img src="images/UIdoneBefore.png" width="600"><br>
  
  After:<br>
  > <img src="images/UIdoneAfter.png" width="600"><br>
  
#### Undo previous action: `undo`
Undo the last completed action.<br>
Format: `undo`

> The previous version will be restored.<br>
> User can keep retyping undo to undo multiple actions.<br>

Examples:
* `undo`<br>
  Undo the last deleted item.<br>
  
  Before:<br>
  > <img src="images/UIundoBefore.png" width="600"><br>
  
  After:<br>
  > <img src="images/UIundoAfter.png" width="600"><br>

#### Clearing all entries : `clear`
Clear all tasks from the task manager.<br>
Format: `clear`  
> <img src="images/UIclear.png" width="600">

#### Exiting the program : `exit`
Exit the program.<br>
Format: `exit`  

#### Saving the data 
Task manager data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Address Book folder.
       
## Command Summary

Command | Format  
-------- | :-------- 
Add todo | `add NAME`
Add deadline | `add NAME DATE END_TIME`
Add event | `add NAME DATE START_TIME END_TIME`
View | `view [DATE]`
Find | `find KEYWORDS`
Edit | `edit INDEX [NEW_NAME] [NEW_DATE] [NEW_START_TIME] [NEW_END_TIME]`
Delete | `delete INDEX`
Done | `done INDEX`
Undo | `undo`
Help | `help`
Clear | `clear`
Exit | `exit`
