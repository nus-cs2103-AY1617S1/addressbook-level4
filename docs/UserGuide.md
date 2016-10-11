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
3. Double-click the file to start the app. The GUI should appear in a few seconds.<br>
   <img src="images/UserGuide Mock up2.png" alt="Task manager GUI on start up">
<figcaption>Fig. 1: Task manager GUI on start up</figcaption>
4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`** `Midterms pr/high time/wednesday t/important` : adds a task "Midterms" to the Task Manger.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
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

#### Adding a task/tag

##### Adding a task: `add`
Adds a task to the task manager.<br>
Format: `add DESCRIPTION [pr/RANK] [time/TIME] [t/TAG]...`
Format for TIME: numeric date/month/year/24hour_format

> * Tasks can have different priorities, normal by default, high or low
> * Deadlines can be set for tasks
> * Tasks can have any number of tags (including 0)

Examples:
* `add Midterms pr/high time/wednesday a/MPSH2A t/important`
* `add get eggs pr/low t/family`
* `add organize room`

##### Adding tags: `add tag`
Add tags to specified task.<br>
Format: `add tag INDEX TAG [MORE_TAGS]`

Example:
* `list`<br>
  `add tag 2 friend NUS`<br>
  Adds the tags `friend` and `NUS` to the 2nd contact selected


#### Listing all tasks/tags

##### Listing all tasks: `list`
Shows a list of all tasks in the task manager.<br>
Format: `list [-pr] [-t/TAGS]...`

> * Tasks are sorted chronologically by default
> * Tasks without deadlines are listed at the end when chronologically sorted

Modifiers | Action
---|:---
-pr | Tasks are listed by priority
-t/TAG | Tasks with the specified tag are listed

<img src="images/UserGuide Mock up1.png" alt="Task manager listing tasks by priority">
<figcaption>Fig. 2: Task manager listing tasks by priority</figcaption>

##### Listing all tags used: `list tags`
Lists all the tags used in the task manager.<br>
Format: `list tags`


#### Finding all tasks containing any keyword in their description: `find`
Find tasks whose description contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case insensitive. e.g `Gives` will not match `give`
> * The order of the keywords does not matter. e.g. `Give Eggs` will match `Eggs Give`
> * Only the description is searched.
> * Only words with fewer or equal characters will return e.g. `Exam` will return `Exams` but `Exams` will not return `Exam` 
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Return` will match `Return Car`

Examples:
* `find Tutorial`<br>
  Returns `Tutorial 8` and `tutorial`
* `find Return lunch Meeting`<br>
  Returns Any tasks having description containing `Return`, `lunch`, or `Meeting`

#### Deleting a task/tag

##### Delete a task: `delete`
Delete the specified task from the task manager.<br>
Format: `delete INDEX`

Examples:
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the list

##### Deleting tags from a task: `delete tag`
Delete tags from specified task.<br>
Format: `delete tag INDEX TAG [MORE_TAGS]`

Example:
* `list`<br>
  `delete tag 3 foe NTU`<br>
  Removes the tags `foe` and `NTU` from the 3rd task in the list


#### Editing a task: `edit`
Edit the specified task.<br>
Format: `edit INDEX INPUT [MORE_INPUT]`

> * Edits the task by replacing the information stored with the input accordingly
> * Inputs are the same as specified in the `add` functions
> * Entries can be removed by calling the modifier but not specifying anything

Examples:
* `list`<br>
  `edit 3 Complete tutorial 7 pr/ time/`<br>
	Edits the 3rd task in the list by replacing the description, resetting the priority and removing the deadline

#### Completing a task: `complete`
Tag the task last selected as 'Complete' and remove it from the calendar.<br>
Format: `complete INDEX`

Examples:
* `list`<br>
  `complete 2`<br>
  Adds a tag `COMPLETE` to the 2nd task in the list.

#### Undo action: `undo`
Undoes the most recent change from the task manager.<br>
Format: `undo`

#### Clearing entries: `clear`
Clears all entries from the task manager.<br>
Format: `clear`

#### Exiting the program: `exit`
Exits the program.<br>
Format: `exit`

#### Saving the data
Task manager data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Task Manager folder.

## Command Summary

Command | Format
-------- | :--------
Add Task | `add DESCRIPTION [pr/RANK] [time/TIME] [a/VENUE] [t/TAG]...`
Add Tag | `add tag INDEX TAG [MORE_TAGS]`
Clear | `clear`
Delete Task | `delete INDEX`
Delete Tags | `delete tag INDEX TAG [MORE_TAGS]`
Edit | `edit INDEX INPUT [MORE_INPUT]`
Find Tasks | `find KEYWORD [MORE_KEYWORDS]`
List Tasks | `list [-pr] [-t/TAG]...`
List Tags | `list tags`
Undo | `undo`
Help | `help`
Complete | `complete INDEX`
