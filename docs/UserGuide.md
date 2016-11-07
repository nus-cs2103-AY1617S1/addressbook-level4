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
   * **`list`** : lists all items
   * **`add`**` n/Lecture s/Friday 2pm e/Friday 4pm r/weekly t/CS2103 p/3` :
     adds an event named `Lecture` to the toDoList.
   * **`delete`**` 3` : deletes the 3rd item shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Tasks or Events are collectively referred to as Items. For simplicity, we use both 
> * terms interchangeably
> * The order of parameters is flexible.

#### Viewing help : `help`
Shows a list of various commands available.<br>
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

<!-- @@author A0142325R-->
 
#### Adding an item: `add`
Add an event with a starting and ending date to the toDoList.<br>
Format: `add n/EVENT_NAME s/START_DATE e/END_DATE [t/TAG]... [p/PRIORITY_LEVEL][r/RECURRING_FREQUENCY]`

Add a task (with or without deadline) to the toDoList.<br>
Format: `add n/TASK_NAME d/DEADLINE [t/TAG]... [p/PRIORITY_LEVEL][r/RECURRING_FREQUENCY]`<br>
Format: `add n/TASK_NAME [t/TAG]...[p/PRIORITY_LEVEL]`

> With the exception of command word "add", the order of parameters are not fixed. START_DATE, END_DATE
>and DEADLINE can be entered in natural language. For example, entering words like today, tommorrow, today
>at 4pm, the day after tommorrow are recognized. For events, START_DATE and END_DATE must be present at the
>same time

> Items to be added can have any number of tags (including 0)<br>

Examples:
* `add n/Lecture s/7.10.2016-14 e/7.10.2016-16 t/CS2103 p/3 r/daily`
* `add n/Project Deadline d/14.10.2016 t/CS2103 p/3 r/weekly`
* `add n/Read Book`

#### Listing items : `list`
Shows a list of all items.<br>
Format: `list`<br>
Example:<br>
* `list`<br>

Shows a list of all tasks.<br>
Format: `list tasks`<br>
Example:<br>
* `list tasks`<br>

Shows a list of all events.<br>
Format: `list events`<br>
Example:<br>
* `list events`<br>

Shows a list of all done items.<br>
Format: `list done`<br>
Example:<br>
* `list done`<br>

Shows a list of all undone items.<br>
Format: `list undone`<br>
Example:<br>
* `list undone`<br>

#### Deleting items: `delete`
Deletes an existing task or event from toDoList storage completely.<br>

Format: `delete INDEX`<br>

> Delete the task or event identified by the specific INDEX in the most recent listing.<br>
> INDEX must be positive integers like 1 , 2 , 3 ...

Format:`delete NAME...`<br>

> All tasks or events with names matching one or more of the input parameters will be listed.<br>
> User deletes the task or event by index in the last shown listing.<br>

Example:<br>
* `delete do homework`<br>
A list of items with keywords "do" or "homework" or both will be shown.<br>

* `delete 1`<br>
Delete the first item in the last shown list<br>

#### Refresh toDoList: `refresh`
Refresh the current toDoList<br>

Format: `refresh`<br>

> refreshes all outdated recurring tasks to reflect next upcoming date<br>

Example:<br>
* `refresh`<br>

#### Mark an item as done: `done`
Mark an event or task as done <br>

Format: `done INDEX`<br>

> Mark the task identified by the INDEX number as done, and reflect it in toDoList<br>
> INDEX of a task or event refers to the most recent listing<br>
> INDEX must be positive integer, such as 1, 2, 3 ...<br>

Format: `done NAME...`<br>

> A list of tasks or events with name matching one or more of the input parameters will be shown<br>
> User is then required to select the corresponding index in the last shown list and mark as done<br>

Example:<br>
* `done 1`<br>
Marks the first item in the last shown list as done<br>

* `done homework`<br>
A list of all items with names matching the keyword "homework" will be shown<br>

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
<!-- @@author A0138717X-->
####Edit a todo: `edit`
Edit an existing field of an todo.<br>
Format:<br> 
  `edit EVENT_NAME [s/START_DATE] [e/END_DATE] [n/NEW_EVENT_NAME] [r/RECURRING_EVENT] [p/PRIORITY_LEVEL]`<br>
  `edit TASK_NAME [d/DEADLINE] [n/NEW_TASK_NAME] [r/RECURRING_TASK] [p/PRIORITY_LEVEL]`

Example:

* `edit Lecture s/7.10.2016-14 `
* `edit Project deadline d/14.10.2016`
* `edit Read book n/Borrow a book`

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
Undo the most recent action.<br>
Format: `undo`

> Able to undo add, delete, edit, clear, done commands from this session up to 10 times.

Undo the most recent change of the default storage location and clear data saved in the new location if specified. 
Format: `undochange [clear]` <br>

> There is no limit on the number of times.<br>

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
> * `d/` will match tasks without deadline

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

> * Only items that matching all attributes will be returned (i.e. `AND` search).
> * `NIL` in the deadline text field will match tasks without deadline.

The filter panel will also be updated after `list` and `filter` commands.
(Screen shot to be added)

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
Add | `add EVENT_NAME s/START_DATE e/END_DATE [t/TAG][r/RECURRING_FREQUENCY][p/PRIORITY_LEVEL]`<br> `add DEADLINE_TASK_NAME d/DEADLINE [t/TAG] [r/RECURRING_FREQUENCY][p/PRIORITY_LEVEL]`<br> `add FLOATING_TASK_NAME [t/TAG][p/PRIORITY_LEVEL]`
Change | `change FILE_PATH [clear]`<br> e.g. `change /Desktop/folder/taskManager.xml clear`
Clear | `clear`
Delete | `delete INDEX`<br> `delete NAME`
Done | `done INDEX`<br> `done NAME`
Edit | `edit TASK_NAME [d/DEADLINE] [n/NEW_TASK_NAME] [r/RECURRING_TASK] [p/PRIORITY_LEVEL]`<br> `edit EVENT_NAME [s/START_DATE] [e/END_DATE] [n/NEW_EVENT_NAME] [r/RECURRING_EVENT] [p/PRIORITY_LEVEL]`
Filter | `filter [s/START_DATE] [e/END_DATE] [d/DEADLINE] [p/PRIORITY_LEVEL] [t/TAG]...`<br>  e.g. `filter s/7.10.2016-14 t/CS2103`
Find | `find KEYWORD [AND] [MORE_KEYWORDS] [exact!]` <br> e.g. `find CS2103 Software Project`, `find CS2103 AND Software Project`, `find lecture exact!`
Help | `help`
Jump |  `s`, `e`, `d`, `r`, `t`, `p`
List | `list`,`list tasks`,`list events`,`list done`,`list undone`
Redo | `redo` <br> `redochange`
Undo | `undo` <br> `undochange [clear]` e.g. `undochange clear`
