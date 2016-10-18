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
   * **`add`**` Project Meeting s/05-10-2016 r/daily n/2 c/CS2103 d/Discuss about roles and milestones` : 
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
Format: `add TASK_NAME [s/START_DATE] [e/END_DATE] [l/LOCATION] [p/PRIORITY_LEVEL] [r/RECURRING_TYPE] [n/NUMBER_OF_RECURRENCE] [c/CATEGORY] [d/DESCRIPTION]` 

> Parameters | Description  
> -------- | :-------- 
> TASK_NAME | `Mandatory` Specifies the name of the task.
> START_DATE **(See DATE)** | `Optional` Specifies the starting date and time of the task.
> END_DATE **(See DATE)** | `Optional` Specifies the ending date and time of the task.
> **DATE** | If only the DATE is specified, it is assumed that the TIME begins at 12am or ends at 11:59pm.<br>If only the TIME is specified, it is assumed that the DATE refers to the today.<br><br>If only START_DATE is supplied, the task will be a 1-day event starting from the specified START_DATE and ends on the same day at 11:59pm.<br>If only END_DATE is supplied, the task will be assumed to start today at 12am.
> LOCATION | `Optional` Specifies the location where the task happens.
> PRIORITY_LEVEL | `Optional` Specifies the priority level of the task.<br>`Accepts` values `low`, `medium`, `high`<br>`Defaults` to `???`
> RECURRING_TYPE | `Optional` Specifies the recurring type of the task.<br>`Accepts` values `none`, `daily`, `weekly`, `monthly`, `yearly`<br>`Defaults` to `none`
> NUMBER_OF_RECURRENCE | `Optional` Specifies the number of times the task recurrs.<br>`Defaults` to `0`<br>`Ignored` if RECURRING_TYPE is `none`
> CATEGORY | `Optional` Specifies the category of the task.
> DESCRIPTION | `Optional` Describes the task.

Examples: 
* `add Project Meeting s/05-10-2016 2pm e/6pm r/daily n/2 c/CS2103 d/Discuss about roles and milestones` <br>
  Add task named, Project Meeting, under CS2103 category. The task is schedule to take place on 5th and 6th of October 2016 from 2pm to 6pm each day.
* `add NUSSU Leadership Camp s/05-10-2016 2pm e/08-10-2016 6pm c/NUSSU`
  Add task named, NUSSU Leadership Camp, under NUSSU category. The 4 day 3 night is schedule to take place from 5th October, 2pm to 8th of October 2016, 6pm.

#### Listing all tasks: `list`
Shows a list of all tasks in Savvy Tasker <br>
Format: `list [t/LIST_TYPE]`

> Parameters | Description  
> -------- | :-------- 
> LIST_TYPE | `Optional` Specifies the name of the task.<br>`Accepts` values `DueDate`, `PriorityLevel`, `Archived`<br>`Defaults` to `DueDate`
>   | `DueDate` | DueDate
>   | `PriorityLevel` | PriorityLevel
>   | `Archived` | Archived

> If TYPE is `Due Date`, the tasks and events are sorted according to due date and time of tasks and start date and time of events, earliest first.<br>
> If no ENDTIME specified (floating tasks), sorted to bottom of list.<br>
> If TYPE is `Priority Level`, the tasks and events are sorted according to priority level beginning with the highest.<br>
> If TYPE is `Archived`, the Archived tasks and events are listed. They are sorted according to the time of creation of the task.<br>

#### Finding all task containing any keyword in its name: `find`
Finds tasks whose names contain any of the given keywords.<br>
Format: `find [t/FIND_TYPE] KEYWORD [MORE_KEYWORDS...]`

> Parameters | Description  
> -------- | :-------- 
> FIND_TYPE | `Optional` Specifies the name of the task.<br>`Accepts` values `Partial`, `Full`, `Exact`<br>`Defaults` to `Partial`

> If FIND_TYPE is `Partial`, partial keywords will be matched e.g. `task` will match ` 2103 tasks` <br>
> If FIND_TYPE is `Full`, only full keywords will be matched e.g. `task` will not match `2103 tasks` <br>
> If FIND_TYPE is `Exact`, the exact set of keywords will be matched e.g. `Project Meeting` will match `Project Meeting` and not match `2103 Project Meeting` <br>
> The search is case insensitive. e.g `task` will match `Task`<br>
> The order of the keywords does not matter for `Partial` and `Full`. e.g. `project meeting` will match `meeting project` <br>
> Only the TASK_NAME is searched. <br>
> If FIND_TYPE is not `Exact`, tasks matching at least one keyword will be returned (i.e. `OR` search) e.g. `Project` will match `Project Meeting`<br>

Examples: 
* `find t/Full Project meeting`<br>
  Returns any task containing names `Project` or `Meeting`, but not `meet` 
* `find t/Exact Project meeting`<br>
  Returns any task containing names `Project Meeting` exactly
* `find meet CS2103`<br>
  Returns any task containing names `meet`, or `CS2103`. This matches `meeting` and any other words containing `meet` or `CS2103`

#### Deleting a task : `delete`
Deletes the specified task from Savvy Tasker.<br>
Format: `delete INDEX [MORE_INDEX...]`

> Deletes the task at the specified `INDEX` and `[MORE_INDEX...]`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `delete 2 3 5`<br>
  Deletes the 2nd, 3rd and 5th task listed by Savvy Tasker.
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
Modifies the task identified by the index number used in the last task listing.<br>
Format: `modify INDEX [t/TASK_NAME] [s/START_DATE] [e/END_DATE] [l/LOCATION] [p/PRIORITY_LEVEL] [r/RECURRING_TYPE] [n/NUMBER_OF_RECURRENCE] [c/CATEGORY] [d/DESCRIPTION]`

> Parameters | Description  
> -------- | :-------- 
> INDEX | `Mandatory` Specifies the index of the listing shown to modify.
> TASK_NAME<br>START_DATE<br>END_DATE<br>LOCATION<br>PRIORITY_LEVEL<br>RECURRING_TYPE<br>NUMBER_OF_RECURRENCE<br>CATEGORY<br>DESCRIPTION | See [Adding a task](#adding-a-task-add)
> <br>
> Selects the task and modifies the task as done at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ... <br>
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
