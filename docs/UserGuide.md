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
4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**` add Midterms pr/high time/wednesday t/important` :
     adds a task `Midterms to the Task Manger.
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

#### Adding a task: `add `
Adds a task to the task manager.<br>
Format: `add DESCRIPTION [pr/RANK] [time/TIME] [t/TAG]...`
Format for TIME: numeric date/month/year/24hour_format
> * Tasks can have different priorities, normal by default, high or low
> * Deadlines can be set for tasks
> * Tasks can have any number of tags (including 0)

Examples:
* `add Midterms pr/high time/wednesday t/important`
* `add get eggs pr/low t/family`
* `add organize room`

#### Listing all tasks : `list`
Shows a list of all tasks in the task manager.<br>
Format: `list [-t] [-pr] [-t/TAGS]...`

> * Tasks are sorted chronologically by default
> * Tasks without deadlines are listed at the end when chronologically sorted

Modifiers | Action
---|:---
-pr | Tasks are listed by priority
-t/TAG | Tasks with the specified tag are listed

#### Finding all tasks containing any keyword in their description: `find`
Finds tasks whose description contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case sensitive. e.g `Gives` will not match `give`
> * The order of the keywords does not matter. e.g. `Give Eggs` will match `Eggs Give`
> * Only the description is searched.
> * Only full words will be matched e.g. `Return` will not match `Returns`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Return` will match `Return Car`

Examples:
* `find Tutorial`<br>
  Returns `Tutorial 8` but not `tutorial`
* `find Return Lunch Meeting`<br>
  Returns Any tasks having description containing `Return`, `Lunch`, or `Meeting`

#### Deleting a task: `delete`
Deletes the specified task from the task manager.<br>
Format: `delete INDEX`

Examples:
* `list`<br>
  `delete task 2`<br>
  Deletes the 2nd task in the task manager.

#### Select a task: `select`
Selects the person identified by the index number used in the last task listing.<br>
Format: `select INDEX`

Examples:
* `list`<br>
  `select 2`<br>
  Selects the 2nd task in the task manager.

#### Editing a task: `edit`
Edits the task last selected.<br>
Format: `edit INPUT [MORE_INPUT]`

> * Edits the task by replacing the information stored with the input accordingly
> * Inputs are the same as specified in the `add` functions
> * Entries can be removed by calling the modifier but not specifying anything

Examples:
* `list`<br>
  `select 3`<br>
  `edit Complete tutorial 7 priority/ time/`<br>
  Edits the 3rd task in the task manager by replacing the description, resetting the priority and removing the deadline

#### Listing all tags used: `list tags`
Lists all the tags used in the task manager.<br>
Format: `list tags [-t] [-c]`

> Lists all tags used in both task manager by default

Modifiers | Action
---|:---
-t | List tags used in task manager

#### Adding tags to a task: `add tag`
Add tags to last selected task.<br>
Format: `add tag [MORE_TAGS]`

Example:
* `list`<br>
  `select 2`<br>
  `add tag friend NUS`<br>
  Adds the tags `friend` and `NUS` to the 2nd contact selected

#### Removing tags from a task: `delete tag`
Remove tags from last selected task.<br>
Format: `delete tag [MORE_TAGS]`

Example:
* `list`<br>
  `select 3`<br>
  `delete tag foe NTU`<br>
  Removes the tags `foe` and `NTU` from the 3rd contact selected

#### View the tasks in a calendar: `show in calendar`
Open a calendar GUI and show all the tasks in the calendar.<br>
Format: `show in calendar`

#### Completing a task: `complete`
Tag the task last selected as 'Complete' and remove it from the calendar.<br>
Format: `complete INDEX`

Examples:
* `list`<br>
  `complete 2`<br>
  Add a tag `COMPLETE` the 2nd task in the task manager.


#### Undo action: `undo`
Undoes the most recent change from the task manager.<br>
Format: `undo`

#### Clearing entries: `clear`
Clears entries from the task manager.<br>
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
Add Task | `add DESCRIPTION [pr/RANK] [time/TIME] [t/TAG]...`
Add Tag | `add tag [MORE_TAGS]`
Clear | `clear [-a] [-t] [-c]`
Delete | `delete INDEX`
Remove Tags | `delete TAG [MORE_TAGS]`
Edit | `edit INPUT [MORE_INPUT]`
Find Tasks | `find KEYWORD [MORE_KEYWORDS]`
List Tasks | `list [-pr] [-t/TAG]...`
List Tags | `list tags [-t] [-c]`
Undo | `undo`
Help | `help`
Select | `select INDEX`
Complete | `complete INDEX`
