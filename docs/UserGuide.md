# User Guide

* [Getting Started](#getting-started)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Getting Started

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `TaskManager.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
6. Refer to the [Features](#features) section below for details of each command.<br>5. Some example commands you can try:
   * **`add Practice tennis, s/tomorrow 3pm, e/tomorrow 6pm, l/school court 4, #sports`** : adds a task `Practice tennis` for the next day from 3pm to 6pm with a tag `sports`
   * **`edit s/tomorrow 4pm`** :  updates information stored
   * **`list`** : lists all tasks in order of index
   * **`delete`** : deletes the 1st indexed task
   * **`exit`** : exits the app

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
Adds a task to the TaskManager<br>
Format: `add TASK_NAME[, s/START_TIME][, e/END_TIME][, [p/PRIORITY_LEVEL][, d/DEADLINE][, l/LOCATION][, t/TAG1 t/TAG2 ...][, c/COMPLETED_STATUS]` 

> All additional information after `TASK_NAME` are optional
> If left blank, `PRIORITY_LEVEL` defaults to `medium` and `COMPLETED_STATUS` defaults to `uncompleted`
> Each task can have up to 5 tags

Examples: 
* `add project team meeting, s/tomorrow 15:00 e/18:00 p/high `
* `add cs2103 assignment s/tonight 21:00 e/24:00 p/medium d/next friday`

#### Editing a task: `Edit`
Edit task information in the TaskManager<br>
Format: `edit INDEX/TASK_NAME[, TASK_NAME][, s/START_TIME][, e/END_TIME][, [p/PRIORITY_LEVEL][, d/DEADLINE][, l/LOCATION][, t1/TAG1 t4/TAG4 ...][, c/COMPLETED_STATUS] `

> Edit the task at the specific `INDEX` or `TASK_NAME`
     The index refers to the index number shown in the most recent listing.<br>
     The index **must be a positive integer** 1, 2, 3, �
     `TASK_NAME` should be the same as the task name stored in the TaskManager regardless of the word case

Examples: 
* `edit 2 s/3pm` 
* `edit meeting p/low`

#### Deleting a task : `delete`
Deletes the specified task from the TaskManager.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. 
  The index refers to the index number.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
  `delete 2`<br>
  Deletes the task with index '2' in the TaskManager.<br>

#### Listing all tasks : `list`
Shows a list of all tasks in the TaskManager in order of index (default), priority or deadline.<br>
Format: `list [DATA_TYPE]`

>`list deadline` Shows a list of uncompleted tasks in the TaskManager in order of their deadlines, tasks without deadlines will be listed in order of index after it

Examples: 
* `list`
* `list priority`

#### Searching all tasks containing any keyword in their name or location: `search`
Searches tasks with names or location contain any of the given keywords.<br>
Format: `search KEYWORD [MORE_KEYWORDS]`

> * The search is not case sensitive. e.g `tennis` will match `Tennis`
> * The order of the keywords does not matter. e.g. `buy groceries` will match `groceries buy`
> * Only the name and location is searched.
> * Only full words will be matched e.g. `ball` will not match `balls`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `ball` will match `Buy tennis balls`

Examples: 
* `search  tennis`<br>
* `search submit report`<br>
  Returns Any tasks having `practice` or `tennis` in names or locations 

#### Undo the modification : `undo`
Undo the modification in the last step.<br>
Format: `undo`  

#### Redo the undone modification : `redo`
Redo the undone modification in the last step.<br>
Format: `redo`  

#### Change working directory : `directory`
Change data file being accessed, effectively using another TaskManager list.<br>
Format: `directory PATH`  
Examples: 
* `directory C:/Documents and Settings/User/Desktop/TaskManager2`

#### Backup : `backup`
Save a copy of the current TaskManager data file into the specified directory.<br>
Format: `backup PATH`  
Examples: 
* `backup C:/Documents and Settings/User/Desktop/TaskManagerBackup`

#### Clearing all entries : `clear`
Clears all entries from the TaskManager.<br>
Format: `clear`  

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  


#### Saving the data 
TaskManager data is saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.




## FAQ
**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous TaskManager folder.
       
## Command Summary
Command | Format  
-------- | :-------- 
Help | `help`
Add | `add TASK_NAME[, s/START_TIME][, e/END_TIME][, [p/PRIORITY_LEVEL][, d/DEADLINE][, l/LOCATION][, t/TAG1 t/TAG2 ...][, c/COMPLETED_STATUS]`
Edit | `edit INDEX/TASK_NAME[, TASK_NAME][, s/START_TIME][, e/END_TIME][, [p/PRIORITY_LEVEL][, d/DEADLINE][, l/LOCATION][, t1/TAG1 t4/TAG4 ...][, c/COMPLETED_STATUS] `
Delete | `delete INDEX`
List | `list [DATA_TYPE]`
Search | `search KEYWORD`
Undo | `undo`
Redo | `redo`
Set Directory | `directory PATH `
Backup | `backup PATH `
Clear | `clear`
Exit | `exit`
