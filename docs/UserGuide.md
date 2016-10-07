# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `savvytasker.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Savvy Tasker.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**` Project Meeting s/05-10-2016 st/14:00 et/18:00 r/daily n/2 c/CS2103 d/Discuss about roles and milestones` : 
     adds a task named `Project Meeting` to Savvy Tasker for 2 days.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an invalid command e.g. `abcd`

#### Exiting the program : `exit`
Exits Savvy Tasker.<br>
Format: `exit`  

#### Saving the data 
Savvy Tasker data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.
 
#### Adding a task: `add`
Adds a task to Savvy Tasker.<br>
Format: `add TASK_NAME [s/START_DATE] [st/START_TIME] [e/END_DATE] [et/END_TIME] [l/LOCATION] [p/PRIORITY_LEVEL] [r/RECURRING_TYPE] [n/NUMBER_OF_RECURRENCE] [c/CATEGORY] [d/DESCRIPTION]` 

> If DATE is entered but TIME is not entered, the task is assumed to occur all-day <br>
> If TIME is entered but DATE is not entered, DATE is assumed to be current date <br>
> Tasks with START_TIME only, are assumed to occur for 1 hour from specified START_TIME only <br>
> Tasks with either one of or both END_DATE and/or END_TIME, are assumed to be tasks with deadlines <br>
> Tasks with START_DATE only, are assumed to have the same START_DATE and END_DATE <br>
> Tasks with either or both START_DATE and/or START_TIME, are assumed to be events instead of task <br>
> If START_DATE and END_DATE are different, the RECURRING_TYPE has to be larger than the duration between START_DATE and END_DATE. (e.g. A 3d2n camp cannot be recurring daily but can be recurring weekly) <br>
> If START_DATE and END_DATE are different, END_DATE must be later than START_DATE, and END_TIME does not have to be later than START_TIME <br>
> If START_DATE and END_DATE are the same, END_TIME must be later than START_TIME <br>
> For DATE the format is as follows: dd-mm-yyyy <br>
> For TIME the format is as follows: hh:MM, hh:MM defaults to 00:00 if not specified. <br>
> Having more than 1 task for any specific timing is allowed (Multitasking) <br>
> RECURRING_TYPE can be `none`, `daily`, `weekly` or `monthly`, `yearly`, by default RECURRING_TYPE set as `none`, NUMBER_OF_RECURRENCE set as 0 <br>
> PRIORITY_LEVEL can be `high`, `medium`, `low`, by default PRIORITY_LEVEL is set as `low` <br>

Examples: 
* `add Project Meeting s/05-10-2016 st/14:00 et/18:00 r/daily n/2 c/CS2103 d/Discuss about roles and milestones` <br>
  Add task named, Project Meeting, under CS2103 category. The task is schedule to take place on 5th and 6th of October 2016 from 2pm to 6pm each day.
* `add NUSSU Leadership Camp s/05-10-2016 st/14:00 e/08-10-2016 et/18:00 c/NUSSU`
  Add task named, NUSSU Leadership Camp, under NUSSU category. The 4 day 3 night is schedule to take place from 5th October, 2pm to 8th of October 2016, 6pm.

#### Listing all tasks: `list`
Shows a list of all tasks in Savvy Tasker <br>
Format: `list [t/LIST_TYPE]`

> LIST_TYPE can be `Due Date`, `Priority Level`, `Archived`, by default LIST_TYPE is set as `Due Date`<br>
> If TYPE is `Due Date`, the tasks and events are sorted according to due date and time of tasks and start date and time of events, earliest first.<br>
> If no ENDTIME specified (floating tasks), sorted to bottom of list.<br>
> If TYPE is `Priority Level`, the tasks and events are sorted according to priority level, high first.<br>
> If TYPE is `Archived`, the Archived tasks and events are listed. They are sorted according to date the task has been marked, most recent first.<br>

#### Finding all task containing any keyword in its name: `find`
Finds tasks whose names contain any of the given keywords.<br>
Format: `find [t/FIND_TYPE] KEYWORD [MORE_KEYWORDS]`

> FIND_TYPE can be `Partial`, `Full`, `Exact`, by default FIND_TYPE is set as `Partial` <br>
> If FIND_TYPE is `Partial`, partial keywords will be matched e.g. `task` will match ` 2103 tasks` <br>
> If FIND_TYPE is `Full`, only full keywords will be matched e.g. `task` will not match `2103 tasks` <br>
> If FIND_TYPE is `Exact`, the exact set of keywords will be matched e.g. `Project Meeting` will match `2103 Project Meeting` and not match `2103 Meeting` <br>
> The search is case insensitive. e.g `task` will match `Task`<br>
> The order of the keywords does not matter. e.g. `project meeting` will match `meeting project` <br>
> Only the TASK_NAME and DATE are searched. <br>
> Only full words will be matched e.g. `task` will not match `tasks` <br>
> If FIND_TYPE is not `Exact`, tasks matching at least one keyword will be returned (i.e. `OR` search) e.g. `Project` will match `Project Meeting`<br>

Examples: 
* `find t/Full Project meeting`<br>
  Returns any task containing names `Project` or `Meeting`, but not `meet` 
* `find t/Exact Project meeting`<br>
  Returns any task containing names `Project Meeting` exactly
* `find meet CS2103`<br>
  Returns any task containing names `meet`, or `CS2103`, including `meeting` and other word containing `meet`

#### Deleting a task : `delete`
Deletes the specified task from Savvy Tasker. Irreversible.<br>
Format: `delete INDEX [MORE_INDEX]`

> Deletes the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...
> Allow multiple deletion

Examples: 
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the address book.
* `find CS1010`<br> 
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

#### Select a task : `select`
Selects the task identified by the index number used in the last task listing.<br>
Format: `select INDEX [MORE_INDEX]`

> Selects the task and loads the details of the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...
> Allow multiple selection

Examples: 
* `list`<br>
  `select 2`<br>
  Selects the 2nd task in the task book.
* `find CS2103` <br> 
  `select 1`<br>
  Selects the 1st task in the results of the `find` command.

#### Modifies a task : `modify`
Marks the task identified by the index number used in the last task listing.<br>
Format: `modify INDEX [t/TASK_NAME] [s/START_DATE] [st/START_TIME] [e/END_DATE] [et/END_TIME] [l/LOCATION] [p/PRIORITY_LEVEL] [r/RECURRING_TYPE] [n/NUMBER_OF_RECURRENCE] [c/CATEGORY] [d/DESCRIPTION]`

> Selects the task and modifies the task as done at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...
> Overwrites any of the specified fields ('LOCATION', 'DESCRIPTION'...) with the new values

#### Mark a task as done : `mark`
Marks the task as completed identified by the index number used in the last task listing. Completed task will be remove from the normal list and placed under archived list<br>
Format: `mark INDEX [MORE_INDEX]`

> Selects the task and marks the task as done at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `mark 2`<br>
  Marks the 2nd task in the task book as completed.
* `find CS2103` <br> 
  `mark 1`<br>
  Marks the 1st task in the results of the `find` command as completed.

#### Unmark a task as done : `unmark`
Unmarks the task identified by the index number used in the last task listing.<br>
Format: `Unmark INDEX [MORE_INDEX]`

> Selects the task and marks the task as done at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `unmark 2`<br>
  Unmarks the 2nd task in the task book as done.
* `find CS2103` <br> 
  `unmark 1`<br>
  Unmarks the 1st task in the results of the `find` command as done.

#### Undo the most recent operation : `undo`
Undo the most recent command that was executed.<br>
Format: `undo`  

#### Redo the most recent undo operation : `redo`
Redo the most recent command that was executed by the undo.<br>
Format: `redo`  
>Redo is unavailable if the most recent command is not undo

#### Clearing all entries : `clear`
Clears all entries from the Savvy Task.<br>
Format: `clear`  

#### Alias a keyword : `alias`
Alias a keyword with shorter version of keyword <br>
Format: `alias k/KEYWORD s/SHORT_KEYWORD`

Examples: 
* `alias k/Project Meeting s/pjm`<br>
System will interpret subsequent user command of "pjm" as "Project Meeting"<br>
  `add pjm s/05-10-2016 st/14:00 et/18:00 r/daily n/2 c/CS2103`<br>
Add "Project Meeting" to task list
  
#### Unalias a keyword : `unalias`
Unalias of shorter version of keyword <br>
Format: `unalias s/SHORT_KEYWORD`

Examples: 
* `unalias s/pjm`<br>
Remove replacement of shorter version of "pjm" back to "Project Meeting"<br>
  `add pjm s/05-10-2016 st/14:00 et/18:00 r/daily n/2 c/CS2103`<br>
Add task named "pjm" to task list
  

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Savvy Tasker folder.
       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `add TASK_NAME [s/START_DATE] [st/START_TIME] [e/END_DATE] [et/END_TIME] [l/LOCATION] [p/PRIORITY_LEVEL] [r/RECURRING_TYPE] [n/NUMBER_OF_RECURRENCE] [c/CATEGORY] [d/DESCRIPTION]`
Alias | `alias k/KEYWORD s/SHORT_KEYWORD`
Clear | `clear`
Delete | `delete INDEX [MORE_INDEX]`
Exit | `exit`
Find | `find [t/FIND_TYPE] KEYWORD [MORE_KEYWORDS]`
List | `list [t/LIST_TYPE]`
Help | `help`
Select | `select INDEX [MORE_INDEX]`
Modify | `modify INDEX [t/TASK_NAME] [s/START_DATE] [st/START_TIME] [e/END_DATE] [et/END_TIME] [l/LOCATION] [p/PRIORITY_LEVEL] [r/RECURRING_TYPE] [n/NUMBER_OF_RECURRENCE] [c/CATEGORY] [d/DESCRIPTION]`
Mark | `mark INDEX [MORE_INDEX]`
Unmark | `unmark INDEX [MORE_INDEX]`
Undo | `undo`
Redo | `redo`
Unalias | `unalias  s/SHORT_KEYWORD`
