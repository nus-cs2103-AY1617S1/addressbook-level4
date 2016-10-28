# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `taskmanager.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > UI mock up

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all events and tasks
   * **`add`**` Lecture s/7.10.2016-14 e/7.10.2016-16 t/CS2103 p/3` :
     adds a event named `Lecture` to the Task Manager.
   * **`delete`**` 3` : deletes the 3rd event/task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.

#### Viewing help : `help`
Shows a list of various commands available.<br>
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`
 
#### Adding a event or task: `add`
Add an event with a starting and ending date to the calendar.<br>
Format: `add EVENT_NAME s/START_DATE e/END_DATE [t/TAG]... [p/PRIORITY_LEVEL]`

Add a task (with or without deadline) to the calendar.<br>
Format: `add TASK_NAME [d/DEADLINE] [t/TAG]... [p/PRIORITY_LEVEL]`

> Events and tasks can have any number of tags (including 0)

Examples:
* `add Lecture s/7.10.2016-14 e/7.10.2016-16 t/CS2103 p/3 `
* `add Project Deadline d/14.10.2016 t/CS2103 p/3`
* `add Read Book`

#### Listing all events and tasks : `list`
Shows a list of all events and tasks.<br>
Format: `list`

#### Finding all events and tasks containing any keyword in their name: `find`
Finds events and tasks whose names contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is not case sensitive. e.g hans will match Hans
> * The order of the keywords does not matter. e.g. `Project Deadline` will match `Deadline Project`
> * Only the name is searched.
> * Events or Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Project` will match `Project Deadline`

Examples:
* `find lecture`<br>
  Returns `CS2103 Lecture` and `lecture`
* `find CS2103 Software Project`<br>
  Returns any event or task having names `CS2103`, `Software`, or `Project`

#### Deleting an event or task : `delete`
Deletes the specified event or task from the list. Irreversible.<br>
Format: `delete INDEX`

> Deletes an event or task at the specified `INDEX`.
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, …

Delete the event or task with the exact name: `delete`<br>
Format: `delete EVENT_NAME/TASK_NAME`

> Deletes the event or task with the specified `EVENT_NAME/TASK_NAME`<br>
  The event name or task name refers to the full name of the event or task that the user wants to delete.

Examples:
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task or event in the list.
* `delete Lecture`<br>
  Deletes the task with name `Lecture`  in the results of the `find` command.

####Edit an event or task: `edit`
Edit an existing event or task.<br>
Format: `edit EVENT_NAME s/START_DATE e/END_DATE [t/TAG] [p/PRIORITY_LEVEL]`<br>
  `edit TASK_NAME [d/DEADLINE] [t/TAG] [p/PRIORITY_LEVEL]`

Example:

* `edit Lecture s/7.10.2016-14 e/7.10.2016-16 t/CS2103 p/3 `
* `edit Project Deadline d/14.10.2016 t/CS2103 p/3`
* `edit Read Book`

#### Mark as done : `done`
Mark an event or task identified by the index number used in the last listing as done.<br>
Format: `done INDEX`

> Selects the event or task at the specified `INDEX` and mark it as done.<br>
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, …<br>
  
Examples:
* `list`<br>
  `done 2`<br>
  Mark the 2nd event or task in the task manager as done.

#### Clearing all entries : `clear`
Clears all entries from the calendar.<br>
Format: `clear`  

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data: 
Data will be automatically save to the default location after any command that changes the data.<br>
If user has changed the file location using the ‘change’ command, data will be save to the file path that user indicate.<br>
There is no need to save manually.

####Changing default storage location: `change`
Default storage location will be changed to the location specified by the user. Any data saved in the previous location will be cleared if specified.<br>
Format: `change FILE_PATH`

> The file path must end with the file type extension, .xml

Example: `change /Desktop/folder/taskManager.xml clear`

#### Undo operations: `undo`
Undo the most recent action.<br>
Format: `undo` 

#### Filter events and tasks: `filter`
Filter list for attributes such as event or task, done or yet to be done, start time, deadline, tag, and priority level.<br>
Format: `filter [n/EVENT/TASK] [s/START_DATE] [d/DEADLINE] [c/DONE/UNDONE] [t/TAG] [p/PRIORITY_LEVEL]` 

Examples:
* `filter n/event s/7.10.2016-14 t/CS2103 `
  List events that start from 7.10.2016-14 and have tag CS2103.
* `filter n/task c/undone p/3 `
  List tasks that are yet to be done and have priority 3.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous TaskManager folder.
       
## Command Summary

Command | Format  
-------- | :--------
Add | `add EVENT_NAME s/START_DATE e/END_DATE [t/TAG] [p/PRIORITY_LEVEL]`<br> `add TASK_NAME [d/DEADLINE] [t/TAG] [p/PRIORITY_LEVEL]`
Change | `change FILEPATH`
Clear | `clear`
Delete | `delete INDEX`<br> `delete EVENT_NAME/TASK_NAME`
Done | `done INDEX`
Edit | `edit TASK_NAME [d/DEADLINE] [t/TAG] [p/PRIORITY_LEVEL]`<br> `edit EVENT_NAME s/START_DATE e/END_DATE [t/TAG] [p/PRIORITY_LEVEL]`
Filter | `[n/EVENT/TASK] [s/START_DATE] [d/DEADLINE] [c/DONE/UNDONE] [t/TAG]`
Find | `find KEYWORD [MORE_KEYWORDS]`
Help | `help`
List | `list`
Undo | `undo`
