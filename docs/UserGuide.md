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
5. Refer to the [Features](#features) section below for details of each command.<br>
6. Some example commands you can try:
   * **`add do homework, from noon to 1pm by 3pm #homework`** : adds a task `do homework` for today from 12pm to 1pm with deadline set at 3pm tagged `homework`
   * **`edit 2 morning class at 08.00am to 10.00am`** :  updates information stored
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
Format: `add TASKNAME, [from START_TIME] [to END_TIME] [by DEADLINE] [#TAG...]` 

> All additional information after `TASK_NAME` are optional
> Each task can have unlimited number of tags

Examples: 
* `add project team meeting, from 01.00pm to 03.00pm #CS2103`
* `add cs2103 assignment by 11.59pm #CS2103`

#### Editing a task: `Edit`
Edit task information in the TaskManager<br>
Format: `INDEX TASKNAME at START_TIME to END_TIME [by DEADLINE] [#TAG...]`

> Edit the task at the specific `INDEX` or `TASK_NAME`
     The index refers to the index number shown in the most recent listing.<br>
     The index **must be a positive integer** 1, 2, 3, …
     `TASK_NAME` should be the same as the task name stored in the TaskManager regardless of the word case

Examples: 
* `edit 2 morning class at 08.00am to 10.00am` 
* `edit 4 dinner at 08.00pm to 10.00pm by 12.00am`

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

#### Finding all tasks containing any keyword in their name or location: `find`
Find tasks with names or location contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The check is not case sensitive. e.g `tennis` will match `Tennis`
> * The order of the keywords does not matter. e.g. `buy groceries` will match `groceries buy`
> * Only the name is checked.
> * Partial words will be matched e.g. `ball` will match `balls`
> * Tasks matching at least one keyword will be returned (i.e. `OR` find).
    e.g. `ball` will match `Buy tennis balls`

Examples: 
* `find  tennis`<br>
* `find submit report`<br>
  Lists any tasks having `practice` or `3pm` in names or time

#### Undo the modification : `undo`
Undo the modification in the last step. Only includes add, delete, edit, clear, done and undone commands<br>
Format: `undo`  

#### Done a specific task : `done`
Done a task to show that it is completed with a green marker<br>
Format: `done INDEX` 

#### Undone a specific task : `undone`
Undone a task. Reverse action of done command. Green marker will disappear.<br>
Format: `undone INDEX` 
  
#### Change working directory : `directory`
Change data file being accessed, effectively using another TaskManager list.<br>
A manual restart of the application is required for non-Windows OS, thus TaskManager will close itself.
Format: `directory PATH`  
Examples: 
* `directory C:/Documents and Settings/User/Desktop/TaskManager2`
* `directory data/olddata`

#### Backup : `backup`
Save a copy of the current TaskManager data file into the specified directory.<br>
Format: `backup PATH`  
Examples: 
* `backup C:/Documents and Settings/User/Desktop/TaskManagerBackup`
* `backup data/backup/backup1`
Wrong Examples: 
* `backup C:/TaskManagerBackup` - TaskManager does not have permission to write in root folder of C drive
* `backup data/backup/backup<1>` - Invalid characters `<` and `>`

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
**A**: Install the app in the other computer and replace the new data file it creates with the file that contains the data of your previous TaskManager folder.
Default: data/taskmanager.xml
       
## Command Summary
Command | Format  
-------- | :-------- 
Help | `help`
Add | `add TASKNAME [from START_TIME] [to END_TIME] [by DEADLINE] [#TAG...]` 
Edit | `edit INDEX TASKNAME at START_TIME to END_TIME [by DEADLINE] [#TAG...]`
Delete | `delete INDEX`
List | `list [DATA_TYPE]`
Find | `find KEYWORD`
Undo | `undo`
Redo | `redo`
Set Directory | `directory PATH `
Backup | `backup PATH `
Clear | `clear`
Exit | `exit`
