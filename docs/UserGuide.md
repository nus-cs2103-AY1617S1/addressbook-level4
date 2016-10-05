# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.

1. Download the latest `DoDo-Bird.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your DoDo-Bird application.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > ![GUI](./images/UpdatedUI_041016.png)

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`see`**` tomorrow`:  see all tasks for tomorrow.
   * **`add task`**` -n Meet with professor -d CS1234 -date 10/10/17 -timefrom 09:30 -timeend 17:00` :
     adds a task named `Meet with Professor` to the tasks list.
   * **`delete`**` 3` : deletes the task with ID #3.
   * **`exit`** : exits the app.
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.

> **Date and Time Format**
> * **Date**
>   * 25/10/2017 or 25-10-2017
>   * 25 Oct 2017
>   * 25 October 2017
>   * Tomorrow/Yesterday/Today
> * **Time**
>   * 09:30
>   * 09:30pm

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

#### Adding a task: `add task`
Adds a task to DoDo-Bird<br>

* `add task -n TASKNAME
[-d a line of details] [-before DD/MM/YY[@hh:mm]] [-after DD/MM/YY[@hh:mm]]` <br>
* `add task -n TASKNAME
[-d a line of details] [-date DD/MM/YY]`

> Date and Time formats follow the above guidelines.

Examples:

* **`add task`**` -n Meet with professor -d CS1234`
* **`add task`**` -n Complete tutorial activites -d CS1234 -before 25/10/17@23:59 -after 18/10/17@12:00`
* **`add task`**` -n Meet with professor -d CS1234 -date 25/10/17`

#### Updating a task: `update task`
Update an existing task inside DoDo-Bird<br>
*
`update task -n TaskName [-d a line of details] [-before DD/MM/YY[@hh:mm]] [-after DD/MM/YY[@hh:mm]]`



> Date and Time formats follow the above guidelines.

Examples:

* **`update task`**` -n Complete Problem set 4 -d CS1234 -datefrom 14/10/17 -dateto 18/10/17`
* **`update task`**` -n Complete tutorial activites -d CS1234 -date 25/10/17`

#### Seeing tasks : `see`
Shows a list of all tasks in DoDo-Bird for a particular date.<br>
Format: **`see`**`DATE`

#### Searching tasks: `search task`
Finds tasks whose names contain any of the given keywords or before/after a time.<br>
Format: `search -n KEYWORD [MORE_KEYWORDS]`
		`search -before DD/MM/YY[@hh:mm]`
		`search -after DD/MM/YY[@hh:mm]`

Examples:
* `search Meeting`<br>
* `search Meeting Professor`<br>
* `search -before 25/10/17@09:30`<br>

> * Only the task name is searched.
> * The search is case insensitive. e.g `meeting` will match `Meeting`, `Meeting` will match `meeting`.
> * The order of the keywords does not matter. e.g. `Meet Professor` will match `Professor Meet`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Meeting` will match `Meeting Professor`

#### Deleting a task : `delete`
Deletes the specified task from the DoDo-Bird.<br>
Format: `delete ID`

> Deletes the task at the specified `ID`. The ID **must be a positive integer** 1, 2, 3, ...

Examples:
* `search tomorrow`<br>
  `delete 2`<br>
  Deletes the task with `ID #2` in the DoDo-Bird.
* `search Tutorial`<br>
  `delete 1`<br>
  Deletes the task with `ID #1` in the DoDo-Bird.

#### Undoing : `undo`
Undoes the last operation
Format: `undo`

#### Clearing all entries : `clear`
Clears all entries from the DoDo-Bird.<br>
Format: `clear`  

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data
To-do list data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous DoDo-Bird folder.

## Command Summary

Command | Format  
-------- | :--------
Add | `add task -n TaskName [parameters]`
Clear | `clear`
Delete | `delete ID`
Help | `help`
Quitting | `exit`
Search | `search KEYWORD [MORE_KEYWORDS]`
See | `see DATE`
Undo | `undo`
Update | `update task -n TaskName [parameters]`

