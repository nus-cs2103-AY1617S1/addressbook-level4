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
   * **`add`**` Project Meeting s/05/10/2016T14:00 e/05/10/2016T18:00 r/once d/CS2103 Project Meeting` : 
     adds a task named `Project Meeting` to Savvy Tasker.
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

> Help is also shown if you enter an incorrect command e.g. `abcd`

#### Exiting the program : `exit`
Exits Savvy Tasker.<br>
Format: `exit`  

#### Saving the data 
Savvy Tasker data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.
 
#### Adding a task: `add`
Adds a task to Savvy Tasker.<br>
Format: `add TASK [s/START_DATETIME] [e/END_DATETIME] [l/LOCATION] [r/RECURRING_TYPE] [d/DESCRIPTION]` 

> Tasks can have START_DATETIME and/or END_DATETIME or none.
> For DATETIME the format is as follow: dd/mm/yyyy[Thh:MM{TZD]], hh:MM defaults to 00:00 if not specified. TZD defaults to +08:00
> RECURRING_TYPE can be `once`, `daily`, `weekly` or `monthly`

Examples: 
* `add Project Meeting s/05/10/2016T14:00+08:00 e/05/10/2016T18:00+08:00 r/once d/CS2103 Project Meeting`
* `add Project Meeting s/05/10/2016T14:00 e/05/10/2016T18:00 r/once d/CS2103 Project Meeting`

#### Listing all tasks : `list`
Shows a list of all tasks in Savvy Tasker.<br>
Format: `list`

#### Finding all task containing any keyword in its name: `find`
Finds tasks whose names contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case sensitive. e.g `task` will not match `Task`
> * The order of the keywords does not matter. e.g. `project meeting` will match `meeting project`
> * Only the name is searched.
> * Only full words will be matched e.g. `task` will not match `tasks`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Project` will match `Project Meeting`

Examples: 
* `find Project`<br>
  Returns `Project Meeting` but not `project`
* `find Project meeting CS2103`<br>
  Returns any task containing names `Project`, `meeting`, or `CS2103`

#### Deleting a task : `delete`
Deletes the specified task from Savvy Tasker. Irreversible.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the address book.
* `find CS1010`<br> 
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

#### Select a task : `select`
Selects the task identified by the index number used in the last task listing.<br>
Format: `select INDEX`

> Selects the task and loads the details of the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `select 2`<br>
  Selects the 2nd task in the task book.
* `find CS2103` <br> 
  `select 1`<br>
  Selects the 1st task in the results of the `find` command.

#### Modifies a task : `modify`
Marks the task identified by the index number used in the last task listing.<br>
Format: `modify INDEX [t/TASK] [s/START_DATETIME] [e/END_DATETIME] [l/LOCATION] [r/RECURRING_TYPE] [d/DESCRIPTION]`

> Selects the task and modifies the task as done at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...
> Overwrites any of the specified fields (LOCATION, DESCRIPTION...) with the new values

#### Mark a task as done : `mark`
Marks the task identified by the index number used in the last task listing.<br>
Format: `mark INDEX`

> Selects the task and marks the task as done at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `mark 2`<br>
  Marks the 2nd task in the task book as done.
* `find CS2103` <br> 
  `mark 1`<br>
  Marks the 1st task in the results of the `find` command as done.

#### Unmark a task as done : `unmark`
Unmarks the task identified by the index number used in the last task listing.<br>
Format: `unmark INDEX`

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

#### Clearing all entries : `clear`
Clears all entries from the Savvy Task.<br>
Format: `clear`  

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Address Book folder.
       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `add TASK [s/START_DATETIME] [e/END_DATETIME] [l/LOCATION] [r/RECURRING_TYPE] [d/DESCRIPTION]`
Clear | `clear`
Delete | `delete INDEX`
Find | `find KEYWORD [MORE_KEYWORDS]`
List | `list`
Help | `help`
Select | `select INDEX`
Modify | `modify INDEX [t/TASK] [s/START_DATETIME] [e/END_DATETIME] [l/LOCATION] [r/RECURRING_TYPE] [d/DESCRIPTION]`
Mark | `mark INDEX`
Unmark | `unmark INDEX`
Undo | `undo`
