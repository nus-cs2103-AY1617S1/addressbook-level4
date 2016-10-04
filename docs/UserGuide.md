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
   > **Insert Dewi's UI**

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
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.

> **Date and Time Format**
> * **Date**
>   * 25/10/2017 or 25-10-2017
>   * 25 Oct 2017
>   * 25 October 2017
> * **Time**
>   * 09:30
>   * 09:30pm

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

#### Adding a task: `add task`
Adds a task to DoDo-Bird<br>
Format: `add task -n TaskName [-d a line of details] [-before DD/MM/YY[@hh:mm]] [-after DD/MM/YY[@hh:mm]]`
		`add task -n TaskName [-d a line of details] [-datefrom DD/MM/YY] [-dateto DD/MM/YY] [-timeFrom hh:mm] [-timeEnd hh:mm]`
		`add task -n TaskName [-d a line of details] [-date DD/MM/YY] [-timeFrom hh:mm] [-timeEnd hh:mm]`
		`add task -n TaskName [-d a line of details] [-date DD/MM/YY] [--keyword]`

> Date and Time formats follow the above guidelines.

Examples:

* **`add task`**` -n Meet with professor -d CS1234`
* **`add task`**` -n Complete tutorial activites -d CS1234 -before 25/10/17 23:59 -after 18/10/17 12:00`
* **`add task`**` -n Complete Problem set 4 -d CS1234 -datefrom 14/10/17 -dateto 21/10/17`
* **`add task`**` -n Meet with professor -d CS1234 -date 25/10/17 -timefrom 09:30 -timeend 17:00`
* **`add task`**` -n Complete tutorial activites -d CS1234 -date 25/10/17 --wholeday

#### Updating a task: `update task`
Update an existing task inside DoDo-Bird<br>
Format: `update task -n TaskName [-d a line of details] [-before DD/MM/YY[@hh:mm]] [-after DD/MM/YY[@hh:mm]]`
		`update task -n TaskName [-d a line of details] [-datefrom DD/MM/YY] [-dateto DD/MM/YY] [-timeFrom hh:mm] [-timeEnd hh:mm]`
		`update task -n TaskName [-d a line of details] [-date DD/MM/YY] [-timeFrom hh:mm] [-timeEnd hh:mm]`
		`update task -n TaskName [-d a line of details] [-date DD/MM/YY] [--keyword]`

> Date and Time formats follow the above guidelines.

Examples:

* **`update task`**` -n Complete Problem set 4 -d CS1234 -datefrom 14/10/17 -dateto 18/10/17`
* **`update task`**` -n Complete tutorial activites -d CS1234 -before 25/10/17 17:00 -after 18/10/17 12:00`
* **`update task`**` -n Complete tutorial activites -d CS1234 -date 25/10/17 --afternoon`

#### Seeing tasks : `see`
Shows a list of all tasks in DoDo-Bird for a particular date.<br>
Format: **`see`**`DATE`

#### Searching tasks: `search task`
Finds tasks whose names contain any of the given keywords.<br>
Format: `search -n KEYWORD [MORE_KEYWORDS]`

Examples:
* `search Meeting`<br>
* `search Meeting Professor`<br>

> * The search is case insensitive. e.g `meeting` will match `Meeting`, `Meeting` will match `meeting`.
> * The order of the keywords does not matter. e.g. `Meet Professor` will match `Professor Meet`
> * Only the taskname is searched.
> * Only full words will be matched e.g. `Mee` will not match `Meeting`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Meeting` will match `Meeting Professor`

Finds tasks before/after a time.<br>
Format: `search -before DD/MM/YY [@hh:mm]`
		`search -after DD/MM/YY [@hh:mm]`

Examples:
* `search -after 25/10/17@09:30`<br>
* `search -after 25/10/17@09:30`<br>

#### Listing all tasks : `delete`
List all unfinished tasks in the DoDo-Bird.<br>
Format: `list`
 
#### Deleting a task : `delete`
Deletes the specified task from the DoDo-Bird. Irreversible.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`.
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples:
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the DoDo-Bird.
* `find Tutorial`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

#### Select a task : `select`
Selects the task identified by the index number used in the last task listing.<br>
Format: `select INDEX`

> Selects the task and loads the Google search page the task at the specified `INDEX`.
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples:
* `list`<br>
  `select 2`<br>
  Selects the 2nd task in the DoDo-Bird.
* `find Betsy` <br>
  `select 1`<br>
  Selects the 1st task in the results of the `find` command.

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
       the file that contains the data of your previous Address Book folder.

## Command Summary

Command | Format  
-------- | :--------
Add | `add task -n TaskName [relevant details]`
Clear | `clear`
Delete | `delete INDEX`
Find | `find KEYWORD [MORE_KEYWORDS]`
Help | `help`
List | `list`
See | `see DATE`
Select | `select INDEX`
Update | `update task -n TaskName [relevant details]`
