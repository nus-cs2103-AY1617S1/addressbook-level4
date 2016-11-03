# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [GUI](#gui)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `toDoList.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your toDoList.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all todos
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
> * The order of parameters is flexible.

#### Viewing help : `help`
Shows a list of various commands available.<br>
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`
 
#### Adding a todo: `add`
Add an event with a starting and ending date to the calendar.<br>
Format: `add n/EVENT_NAME s/START_DATE e/END_DATE [t/TAG]... [p/PRIORITY_LEVEL]`

Add a task (with or without deadline) to the calendar.<br>
Format: `add n/TASK_NAME [d/DEADLINE] [t/TAG]... [p/PRIORITY_LEVEL]`

> Todos can have any number of tags (including 0)

Examples:
* `add n/Lecture s/7.10.2016-14 e/7.10.2016-16 t/CS2103 p/3 `
* `add n/Project Deadline d/14.10.2016 t/CS2103 p/3`
* `add n/Read Book`

#### Listing all todos : `list`
Shows a list of all todos.<br>
Format: `list`

<!-- @@author A0146123R-->
#### Finding all todos containing any keyword in their name: `find`
Finds todos whose names contain any of the given keywords.<br>
Format: `find KEYWORD [AND] [MORE_KEYWORDS] [exact!]`

> * The search is not case sensitive. e.g hans will match Hans
> * The order of the keywords does not matter. e.g. `Project Meeting` will match `Meeting Project`
> * Only the name is searched.
> * By default, todos matching at least one keyword will be returned (i.e. `OR` search).
    The matching will only compare word stems of keywords. e.g. `Project Meeting` will match `Project Meeting` and `Meet teammates`
> * Only todos matching the exact keyword will be returned if the command contains the `exact!` parameter.
    e.g. `Meeting exact!` will match `Project Meeting` but will not match `Meet teammates`
> * Only todos matching both groups of keywords will be returned if the two groups of keywords are connected by `AND`
    (i.e. `AND` search). e.g. `Project AND Meeting` will match `Project Meeting` but will not match `Meet teammates`

Examples:
* `find lecture`<br>
  Returns `CS2103 Lecture` and `lectures`
* `find lecture exact!`<br>
  Returns `CS2103 Lecture` but not `lectures`
* `find CS2103 Software Project`<br>
  Returns any todo having names `CS2103`, `Software`, or `Project`
* `find CS2103 AND Software Project`<br>
  Returns todos having names `CS2103` and at least one of `Software` or `Project`
<!-- @@author -->

#### Deleting an todo : `delete`
Deletes the specified todo from the list. Irreversible.<br>
Format: `delete INDEX`

> Deletes an todo at the specified `INDEX`.
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, …

Delete the todo with the exact name: `delete`<br>
Format: `delete EVENT_NAME/TASK_NAME`

> Deletes the todo with the specified `EVENT_NAME/TASK_NAME`<br>
  The event name or task name refers to the full name of the event or task that the user wants to delete.

Examples:
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd todo in the list.
* `delete Lecture`<br>
  Deletes the task with name `Lecture`  in the results of the `find` command.

####Edit an todo: `edit`
Edit an existing field of an todo.<br>
Format: `edit EVENT_NAME [s/START_DATE] [e/END_DATE] [n/NEW_EVENT_NAME] [r/RECURRING_EVENT] [p/PRIORITY_LEVEL]`<br>
  `edit TASK_NAME [d/DEADLINE] [n/NEW_TASK_NAME] [r/RECURRING_TASK] [p/PRIORITY_LEVEL]`

Example:

* `edit Lecture s/7.10.2016-14 `
* `edit Project deadline d/14.10.2016`
* `edit Read book n/Borrow a book`

#### Mark as done : `done`
Mark an todos identified by the index number used in the last listing as done.<br>
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
Data will be automatically save to the storage file, by default toDoList.xml in the data folder, after any command that changes the data.<br>
If user has changed the file location using the `change` command, data will be save to the file path that user indicate.<br>
There is no need to save manually.

<!-- @@author A0146123R-->
#### Changing default storage location: `change`
Default storage location will be changed to the location specified by the user. Any data saved in the previous location will be cleared if specified.<br>
Format: `change FILE_PATH [clear]`

> The file path must end with the file type extension, .xml

Example: 
* `change /Desktop/folder/taskManager.xml`
* `change /Desktop/folder/taskManager.xml clear`

#### Undo operations: `undo`
Undo the most recent action (up to 5 times).<br>
Format: `undo`

> Able to undo add, delete, edit, clear, done commands from this session.

Undo the most recent change of the default storage location (up to 1 time) and clear data saved in the new location if specified.<br>
Format: `undochange [clear]` <br>

Example: 
* `undochange clear`

#### Redo operations: `redo`
Redo the most recent action that is undone.<br>
Format: `redo`<br><br>
Redo change the default storage location back to the new location.<br>
Format: `redochange` <br>

#### Filter todos: `filter`
Filter list for attributes such as start date, end date, deadline and tag.<br>
Format: `filter [s/START_DATE] [e/END_DATE] [d/DEADLINE] [p/PRIORITY_LEVEL] [t/TAG]...` 

> * Only todos that matching all attributes will be returned (i.e. `AND` search).
> * If the given start date, end date or deadline is a day without time, corresponding dates that are on that day will be considered as matched. e.g. `3 Nov` will match `03.11.2016-12`, but `3 Nov 12 noon` will not match `03.11.2016`

Examples:
* `filter s/7.10.2016-14 t/CS2103` <br>
  List events that start from 7.10.2016-14 and have tag CS2103.

## GUI
<img src="images/Ui.png" width="600"><br>

#### Command box
It is for users to enter commands.

#### Result Display
It is for displaying results of commands.

#### Filter Panel
It provides a shortcut for user to filter and list todos. User can switch to the panel by using mouse or following shortcut-commands:
* s: Jump to the start date text field
* e: Jump to the end date text field
* d: Jump to the deadline text field
* r: Jump to the recurring text field
* t: Jump to the tags text field
* p: Jump to the priority choice box

#### Todos Panel
It is for displaying todos.
<!-- @@author -->

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous TaskManager folder.
       
## Command Summary

Command | Format  
-------- | :--------
Add | `add EVENT_NAME s/START_DATE e/END_DATE [t/TAG] [p/PRIORITY_LEVEL]`<br> `add TASK_NAME [d/DEADLINE] [t/TAG] [p/PRIORITY_LEVEL]`
Change | `change FILE_PATH [clear]`<br> e.g. `change /Desktop/folder/taskManager.xml clear`
Clear | `clear`
Delete | `delete INDEX`<br> `delete EVENT_NAME/TASK_NAME`
Done | `done INDEX`
Edit | `edit TASK_NAME [d/DEADLINE] [n/NEW_TASK_NAME] [r/RECURRING_TASK] [p/PRIORITY_LEVEL]`<br> `edit EVENT_NAME [s/START_DATE] [e/END_DATE] [n/NEW_EVENT_NAME] [r/RECURRING_EVENT] [p/PRIORITY_LEVEL]`
Filter | `filter [s/START_DATE] [e/END_DATE] [d/DEADLINE] [p/PRIORITY_LEVEL] [t/TAG]...`<br>  e.g. `filter s/7.10.2016-14 t/CS2103`
Find | `find KEYWORD [AND] [MORE_KEYWORDS] [exact!]` <br> e.g. `find CS2103 Software Project`, `find CS2103 AND Software Project`, `find lecture exact!`
Help | `help`
Jump |  `s`, `e`, `d`, `r`, `t`, `p`
List | `list`
Redo | `redo` <br> `redochange`
Undo | `undo` <br> `undochange [clear]` e.g. `undochange clear`
