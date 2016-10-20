# User Guide

* [Introduction](#introduction)
* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)


## Introduction
This application is a task manager created to solve Jim's problems. Jim is a person who prefers typing over clicking, and to that end this task manager has Command Line Interface as the primary mode of input. A Graphical User Interface is present for visual feedback purposes.

This user guide covers the features of the application and has a short summary of commands at the end for reference.


## Quick Start
0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.

1. Download the latest `taskmanager.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds.<br>
<div style="text-align:center" markdown="1">
<img src="images/UserGuide Mock up1.png" title="Task manager GUI on start up" width="900">
<figcaption>Fig. 1: Task manager GUI on start up</figcaption>
</div>
4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`** `Midterms pr/high start/wednesday t/important` : adds a task "Midterms" to the Task Manger.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features
> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.


#### View help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`


#### Add a task/tag
##### Add a task: `add`
Adds a task to the task manager.<br>
Format: `add DESCRIPTION [pr/PRIORITY] [start/STARTTIME] [start/ENDTIME] [t/TAG]...`

> * Tasks can have different priorities, normal by default, high or low
> * Deadlines are set for tasks if a endtime entered without a start time
> * Tasks can have any number of tags (including 0)

Examples:
* `add Midterms pr/high start/14:00 end/16:00 t/important`
* `add work pr/high end/17:00`
* `add AFA pr/low start/9:00 t/anime`
* `add get eggs pr/low t/family`
* `add organize room`

##### Add tags: `addTag`
Add tags to specified task.<br>
Format: `addTag INDEX TAG`

Example:
* `list`<br>
  `addTag 2 NUS`<br>
  Adds the tag `NUS` to the task with the index 2


#### List all tasks/tags
##### List all tasks: `list`
Shows a list of all tasks in the task manager.<br>
Format: `list [-pr] [-t/TAGS]...`

> * Tasks are sorted chronologically by default
> * Tasks without deadlines are listed at the end when chronologically sorted

Modifiers | Action
--- | :---
-pr | Tasks are listed by priority
-t/TAG | Tasks with the specified tag are listed

<div style="text-align:center" markdown="1">
<img src="images/UserGuide Mock up2.png" title="Task manager listing tasks by priority" width="900">
<figcaption>Fig. 2: Task manager listing tasks by priority</figcaption>
</div>

##### List all tags used: `list tags`
Lists all the tags used in the task manager.<br>
Format: `list tags`


#### Finding tasks
##### Find all tasks containing any keyword in their description: `find`
Finds tasks whose description contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is not case sensitive. e.g. `Gives` will match `gives`
> * The order of the keywords does not matter. e.g. `Give Eggs` will match `Eggs Give`
> * Only the description is searched.
> * Only full words will be matched e.g. `Return` will not match `Returns`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Return` will match `Return Car`

Examples:
* `find Tutorial`<br>
  Returns `Tutorial 8` but not `tutorial`
* `find Return lunch Meeting`<br>
  Returns Any tasks having description containing `Return`, `lunch`, or `Meeting`

##### Find tasks using specified information: `find pr/`
Find tasks with the specified priority.<br>
Format: `find pr/PRIORITY`


##### Find tasks starting after given time: `find start/`
Find tasks that starts after the specified time.<br>
Format: `find start/TIME`

> * When only DayOfWeek is inputted, tasks that start on that day, current time is listed

##### Find task due before given time: `find end/`
Find tasks that are due before the specified time.<br>
Format: `find end/TIME`

> * When only DayOfWeek is inputted, tasks that are due by that day, current time is listed


##### Find task with given tags: `find t/`
Find tasks that have any of the specified tags.<br>
Format: `find t/TAG [MORE_TAGS]`


#### Delete a task/tag
##### Delete a task: `delete`
Deletes the specified task from the task manager.<br>
Format: `delete INDEX`

Examples:
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the task manager.

##### Delete tags from a task: `deleteTag`
Delete tags from specified task.<br>
Format: `deleteTag INDEX TAG`

Example:
* `list`<br>
  `deleteTag 3 NTU`<br>
  Removes the tag `NTU` from the task with the index 3


#### Edit a task: `update`
Edits the specified task.<br>
Format: `update INDEX PROPERTY INPUT`

> * Edits a property of the task with the input index by replacing the information stored with the new information accordingly
> * Inputs are the same as specified in the `add` functions
> * Entries can be removed by calling the modifier but not specifying anything

Examples:
* `list`<br>
  `update 3 descrition Go to SOC`<br>
  Edits the 3rd task in the task manager by replacing the previous description with `Go to SOC`.


#### Complete a task: `complete`
Mark the task specified as 'Complete' and remove it from the calendar.<br>
Format: `complete INDEX`

Examples:
* `list`<br>
  `complete 2`<br>
  Add a tag `DONE` the 2nd task in the task manager.


#### Undo action: `undo`
Undoes the most recent change from the task manager.<br>
Format: `undo`


#### Clear entries: `clear`
Clears all entries from the task manager.<br>
Format: `clear`


#### Exit the program: `exit`
Exits the program.<br>
Format: `exit`


#### Save data
Task manager data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.


## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Task Manager folder.


## Command Summary

Command | Format
-------- | :--------
Add Task | `add DESCRIPTION [pr/RANK] [time/TIME] [d/DATE] [t/TAG]...`
Add Tag | `addTag INDEX TAG`
Clear | `clear`
Delete Task | `delete INDEX`
Delete Tags | `deleteTag INDEX TAG`
Edit | `update INDEX PROPERTY NEW_INFORMATION `
Find Tasks | `find KEYWORD [MORE_KEYWORDS]`
Find Tasks with PRIORITY | `find pr/PRIORITY`
Find Tasks starting after TIME | `find start/TIME`
Find Tasks due by TIME | `find end/TIME`
Find Tasks with TAGS | `find t/TAG [MORE_TAGS]`
List Tasks | `list [-pr] [-t/TAG]...`
List Tags | `list tags`
Undo | `undo`
Help | `help`
Complete | `complete INDEX`
