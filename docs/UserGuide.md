# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
> Having any Java 8 version is not enough. <br>
This app will not work with earlier versions of Java 8.

1. Download the latest `addressbook.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Address Book.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
> <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
* **`list`** : lists all tasks
* **`add`**` `add Do Homework d/ 19/02/12 t/13:43 n/ cs2103 homework` : 
adds a task named `Do Homework` to the Task List.
* **`delete`**` 3` : deletes the 3rd task shown in the task list
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

#### Adding a task: `add`
Adds a task to the task list<br>
Format: `add NAME d/DATE t/TIME n/NOTE ...` 


Examples: 
* `add Do Homework d/19/02/12 t/13:43 n/ cs2103 homework`
* `add Brush teeth d/20/03/13 t/20:03 n/ brush teeth at night before sleep`

#### Listing all tasks : `list`
Shows a list of all tasks in the application.<br>
Format: `list`

#### Sorting all tasks : `sort`
Sorts the list of tasks by date and displays a list of all tasks in the application.<br>
Format: `sort`

#### Editing a task : `edit`
Edit the information about the task specified by the index.<br>
Format: `edit INDEX tn/TASK_NAME d/DATE t/TIME n/NOTE r/REMINDER`

#### Finding upcoming tasks: `upcoming`
List the task with the earliest deadline.<br>
Format: `upcoming`

#### Grouping tasks: `group`
Group the given tasks specified by the index numbers into a group named GROUP_NAME.<br>
Format: `GROUP GROUP_NAME [INDEX_DELIMITED_BY_COMMAS] `

#### Finding all tasks containing any keyword in their name: `find`
Finds tasks where its task names contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case sensitive. e.g `homework` will not match `Homework`
> * The order of the keywords does not matter. e.g. `CS 2103` will match `2013 CS`
> * Only the name is searched.
> * Only full words will be matched e.g. `CS` will not match `CS2103’
> * Persons matching at least one keyword will be returned (i.e. `OR` search).
e.g. `Midterm` will match `Midterm Review`

Examples: 
* `find Tutorial`<br>
Returns `CS2103 Tutorial` but not `tutorial`
* `find CS Assignment Errand`<br>
Returns Any task having names `CS`, `Assignment`, or `Errand`

#### Deleting a task:: `delete`
Deletes the specified task from the task list. Irreversible.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. 
The index refers to the index number shown in the most recent listing.<br>
The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
`delete 2`<br>
Deletes the 2nd task in the task list.

#### Clearing all entries : `clear`
Clears all entries from the application.<br>
Format: `clear`  

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data 
Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
the file that contains the data of your previous Address Book folder.

## Command Summary

Command | Format  
-------- | :-------- 
Add | `add NAME d/DATE t/TIME n/NOTE...`
Clear | `clear`
Delete | `delete INDEX`
Find | `find KEYWORD [MORE_KEYWORDS]`
List | `list`
Help | `help`
Edit | `INDEX tn/TASK_NAME d/DATE t/TIME n/NOTE r/REMINDER`
Sort | `sort`
Upcoming | `upcoming`
Group | `group GROUP_NAME [INDEX_DELIMITED_BY_COMMAS]`

