# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `taskman.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your TaskMan.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`**: lists all tasks
   * **`add`**` CS2103T Tutorial d/wed 9.59am start/tue 11.59pm end/wed 4am` :
     adds a task titled `CS2103T Tutorial` to TaskMan
   * **`delete`**` 3`: deletes the 3rd task shown in the current list
   * **`exit`**: exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.

#### Viewing help: `help`
Command Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`
 
#### Adding a task: `add`
Adds a task to TaskMan<br>
Command Format: `add TITLE [d/DEADLINE] [start/DATETIME] [end/DATETIME] [c/] [r/FREQUENCY] [t/TAG]...`

Parameter | Format
-------- | :-------- 
`DEADLINE` and `DATETIME` | `[next] ddd [hh[.mm]am/pm]`
`FREQUENCY` | `X[d/w/m/y]` where X is a natural number, d is day, m is month, and y is year.

`start` and `end` are the dates and times the task is scheduled to be worked on. The presence of just `c/` will mark the task as completed.

> Tasks can have any number of tags. Tags may contain spaces and are case-insensitive (i.e. tags "school", "School", and "SCHOOL" should not co-exist).

Examples:
* `add CS2103T Tutorial d/wed 9.59am start/tue 11.59pm end/wed 4am`
* `add CS2101 Tutorial d/next mon 11.59am start/sun 2am end/sun 6am t/CS2101 t/V0.0`

#### Listing all tasks: `list`
Shows a list of all tasks in TaskMan.<br>
Command Format: `list`

#### Finding all tasks containing any keyword in their title: `find`
Finds tasks whose titles contain any of the given keywords.<br>
Command Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case-insensitive. e.g `cs3244` will match `CS3244`
> * The order of the keywords does not matter. e.g. `CS3244 Homework` will match `Homework CS3244`
> * Only the title is searched.
> * Only full words will be matched e.g. `CS` will not match `CS3244`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `CS3244` will match `CS3244 Homework`

Examples:
* `find CS2103T`<br>
  Returns `CS2103T Tutorial`
* `find CS2101 CS3230 CS2103T`<br>
  Returns any task having titles `CS2101`, `CS3230`, or `CS2103T`

#### Completing a Task: `complete`
Marks the specified task as completed.
Command Format: `complete INDEX` or `complete list`

#### Editing a task: `edit`
Edits a task to TaskMan<br>
Command Format: `edit TITLE [d/DEADLINE] [start/DATETIME] [end/DATETIME] [c/STATUS] [r/FREQUENCY] [t/TAG]...`

Parameter | Format
-------- | :-------- 
`DEADLINE` and `DATETIME` | `[next] ddd [hh[.mm]am/pm]`
`STATUS` | `y/n` where y denotes complete and n denotes incomplete
`FREQUENCY` | `X[d/w/m/y]` where X is a natural number, d is day, m is month, and y is year.

`start` and `end` are the dates and times the task is scheduled to be worked on. The presence of `c/` will mark the task as completed.

Examples:
* `edit CS2103T Tutorial start/mon 10pm end/tue 2am`
* `edit CS2101 Tutorial d/thu 11.59am`

#### Deleting a task: `delete`
Deletes the specified task from TaskMan. Irreversible.<br>
Command Format: `delete INDEX` or `delete list`

> Deletes the task at the specified `INDEX` or `list`.
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples:
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in TaskMan.
* `find CS2101`<br>
  `delete list`<br>
  Deletes all of the tasks in the result(s) of the `find` command.

#### Select a task: `select`
Selects the task identified by the index number used in the last task listing.<br>
Command Format: `select INDEX`

> Selects the task and loads the Google search page the task at the specified `INDEX`.
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples:
* `list`<br>
  `select 2`<br>
  Selects the 2nd task in TaskMan.
* `find CS2101`<br>
  `select 1`<br>
  Selects the 1st task in the results of the `find` command.

#### Showing all tags: `tag`
Shows all tags used by the user<br>
Command Format: `tag list`

Examples:
* `tag list`<br>
  V0.1  V0.2  V0.3  Apple  Pear  Orange

#### Adding tags to tasks: `tag`
Adds tags to the specified task from TaskMan<br>
Command Format: `tag INDEX [t/TAG]...`

Examples:
* `find CS2103T`
  `tag 1 t/V0.1`
  Tags the first task in the result(s) of `find CS2103T` with the tag V0.1.

#### Removing tags from Tasks: `untag`
Removes tags from the specified task from TaskMan
Command Format: `untag INDEX [t/TAG]...` or `untag all`
Examples:
* `find CS2103T`
  `untag 1 t/V0.1`
  Untags the tag V0.1 from the first task in the result(s) of `find CS2103T`.
* `list`
  `untag 1 all`
  Untags all tags from the the first task in list result(s).

#### Editing tag name: `retag`
Edits names of tag of specified Task from TaskMan.<br>
Command Format: `retag INDEX [t/TAG]... to [t/TAG]...`

Examples:
* `find t/CS2103T`
  `retag 1 t/CS2103T t/V0.1 to t/CS2101 t/V0.0`
  Renames the tag CS2103T and V0.1 from the 1st task in the result(s) of `find t/CS2103T` to CS2101 and V0.0

#### Sorting tasks: `sort`
Sorts the recent listing of tasks according to the specified attribute. Default sort order is ascending.<br>
Command Format: `sort ATTRIBUTE` [desc]

`ATTRIBUTE` can be either deadline, start, end or title.

Examples:
* sort start desc

#### Viewing command history: `history`
List the latest 10 commands **which have made changes to the data** in reverse chronological order.<br>
Command Format: `history`

#### Undoing commands: `undo`
Undo the last X commands in the command history. Irreversible. The command history stores a maximum of the 10 latest commands **which have made changes to the data**.<br>
Command Format: `undo [number]` or `undo all`

Examples:
* `undo`<br>
  Undo the latest command in TaskMan.
* `undo 2`<br>
  `undo 3`<br>
  Undo the latest 5 commands in TaskMan.
* `undo all`<br>
  Undo the latest 10 commands in Taskman.

#### Clearing all entries: `clear`
Clears all entries from TaskMan.<br>
Command Format: `clear`

#### Exiting the program: `exit`
Exits the program.<br>
Command Format: `exit`

#### Saving the data
TaskMan data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

#### Setting the save and load location: `sotrageloc`
Saves to the specified file name and location and sets the application to load from the specified location in the future.<br>
TaskMan data are saved in a file called tasks.txt in the application folder by default.<br>
The filename **must end in .txt**.<br>
Format: `storageloc [LOCATION]`

Examples:
* `storageloc C:/Users/Owner/Desktop/new_tasks.txt`<br>
	Sets the new save and load location to C:/Users/Owner/Desktop/new_tasks.txt
* `storageloc default`<br>
	Sets the new save and load location to tasks.txt in the current application folder

#### Saved data file format:
Each Task is separated by newline.<br>
`TITLE [d/DEADLINE] [s/DATETIME] [e/DATETIME] [r/FREQUENCY] [c/STATUS] [t/TAG]...`<br>
* DEADLINE/DATETIME: 'DD-MM-YYYY TTTT'
* FREQUENCY: 'Xd'
* STATUS: '{y/n}'

#### Adding tasks manually to data file
Format:
`TITLE [d/DEADLINE] [s/DATETIME] [e/DATETIME] [r/FREQUENCY] [c/STATUS] [t/TAG]...`<br>
Format of each field should follow that of add command.<br>

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous TaskMan folder.

## Command Summary

Command | Format
-------- | :--------
Add | `add TITLE [d/DEADLINE] [start/DATETIME] [end/DATETIME] [c/] [r/FREQUENCY] [t/TAG]...	`
Clear | `clear`
Complete | `complete`
Delete | `delete INDEX` or `delete list`
Edit | `edit TITLE [d/DEADLINE] [start/DATETIME] [end/DATETIME] [c/STATUS] [r/FREQUENCY] [t/TAG]...`
Exit | `exit`
Find | `find KEYWORD [MORE_KEYWORDS]`
Help | `help`
History | `history`
List | `list`
Retag | `retag INDEX [t/TAG]... to [t/TAG]...`
Select | `select INDEX`
Sort | `sort ATTRIBUTE`
Sotrageloc | `storageloc [LOCATION]` or `storageloc default`
Tag List | `tag list`
Tag | `tag INDEX [t/TAG]...`
Undo | `undo`
Untag | `untag INDEX [t/TAG]...` or `untag all`
