# User Guide

* [Introduction](#introduction)
* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)


## Introduction
This application is a task manager created to solve Jim's problems. Jim prefers typing over clicking, so you will primarily use Command Line Interface (CLI) for input. A Graphical User Interface (GUI) is present for your visual feedback.

This user guide covers the features of the application and has a short summary of commands at the end for your reference.


## Quick Start
0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.

1. Download the latest `taskmanager.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds. <br>
<div style="text-align:center" markdown="1">
<img src="images/UserGuide Mock up1.png" title="Task manager GUI on start up" width="900">
<figcaption>Fig. 1: Task manager GUI on start up</figcaption>
</div>
4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`** `Midterms pr/high st/wednesday t/important` : adds a task "Midterms" to the Task Manger.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features
> **Command Format**
> * Words in `UPPER_CASE` are the parameters
> * Items in `SQUARE_BRACKETS` are optional
> * Items with `...` after them can have multiple instances

<br>
> **Parameters**
>  * The order of parameters is fixed
>  * The following are the parameters used in the task manager:
>   * `DESCRIPTION` : Your task's description
>     * Should be in alphanumeric
>   * `PRIORITY` : Your task's priority ranking
>     * Either `high`, `low`, or `normal`
>   * `STARTTIME`/`ENDTIME` : Your task's starting time and ending time respectively, if any
>     * Entered in the following order: `DAYOFWEEK DATE TIME`
>     * `DAYOFWEEK` : The day of the week (i.e. Monday to Sunday)
>     * `DATE` : Date in `DD.MM.YYYY` or `D.M.YYYY`
>     * `TIME` : 24H time (i.e. `HH:MM` or `H:MM`)
>   * `TAG` : Your task's tag(s), if any
>     * Should be in alphanumeric
>   * `INDEX` : The index of the task as currently displayed in the GUI
>   * `PROPERTY` : The parameters of your task as mentioned above
>     * Entering the following would edit the corresponding parameter
>       * `des` : `DESCRIPTION`
>       *  `pr` : `PRIORITY`
>       *  `st` : `STARTTIME`
>        * `ed` : `ENDTIME`

<br>
### View help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

<br>
### Adding a task: `add`
Adds a task to the task manager.<br>
General Format: `add DESCRIPTION [pr/PRIORITY] [st/STARTTIME] [ed/ENDTIME] [t/TAG]...`

> * You can only set the task's `DESCRIPTION` alphanumerically (i.e. no special characters)
> * You can set tasks with three different `PRIORITY` levels: `high`, `normal` or `low`
>  * Each task will only accept one priority level (i.e.`add task pr/high` or `add task pr/low`)
>  * If `PRIORITY` is not specified, the task's `PRIORITY` will be set to `normal`
> * You can enter `STARTTIME` and `ENDTIME` in the following format: `DAYOFWEEK DATE TIME`
>  * If `DATE` is entered, `DAYOFWEEK` is ignored
>  * At least one parameter (i.e. `DAYOFWEEK` `DATE` `TIME`) is needed to specify `STARTTIME` or `ENDTIME`
>  * For `DAYOFWEEK`, common shorthand are accepted
>    * i.e. thu, thur, thurs or thursday are accepted
>  * For `DATE`, only dates in `DD.MM.YYYY` or `D.M.YYYY` are recognised
>  * For `TIME`, only 24H time formats are recognised (i.e. `HH:MM` or `H:MM`)
> * Deadlines can be set for your tasks by entering a `ENDTIME`
> * Your tasks can have any number of tags (including 0)
>  * `TAG` should be alphanumeric (i.e. no special characters are allowed)
> * If `STARTIME`/`ENDTIME`/`TAG` is not entered, the respective parameter for your task will be empty
> * Any, if not all, of the parameters can be not specified when adding your task

Examples:
* `add Midterms pr/high st/14:00 ed/16:00 t/important`
* `add report pr/high ed/17:00`
* `add AFA pr/low st/9:00 t/anime`
* `add get groceries pr/low t/family`
* `add organize room`

#### Adding a simple task
Adds a generic task with only the description. <br>
Format: `add DESCRIPTION`

Example:
* `add organise room` <br>
Adds a task with the description `organise room` into the task manager <br>
`PRIORITY` is set to normal, all other parameters are empty

#### Adding a prioritised task
Format: `add DESCRIPTION pr/PRIORITY`

Example:
* `add get groceries pr/high` <br>
Adds a task with the description `get groceries` with `high` priority to the task manager <br>
All other parameters are empty

#### Adding a deadline
Format: `add DESCRIPTION ed/ENDTIME` <br>

Example:
* `add project ed/27.10.2016` <br>
Adds a task with the description `project` due on (i.e. with `ENDTIME` entered as) `27.10.2016` <br>
`PRIORITY` is set to normal, all other parameters are empty


#### Adding a event
Format: `add DESCRIPTION st/STARTTIME ed/ENDTIME` <br>

Example:
* `add AFA st/25.11.2016 ed/27.11.2016` <br>
Adds a task with the description `AFA` <br>
starting from (i.e with `STARTTIME` entered as) `25.11.2016` till (i.e. with `ENDTIME` entered as) `27.11.2016` <br>
`PRIORITY` is set to normal, all other parameters are empty

#### Adding a tagged task
Format: `add DESCRIPTION t/TAG...`

 Examples:
 * `add finish LN t/index` <br>
Adds a task with the description `finish LN`, with the tag `index` <br>
`PRIORITY` is set to normal, all other parameters are empty
 * `add finish runthrough t/skyrim t/video` <br>
 Adds a task with the description `finish playthrough` with the tags `skyrim` and `video` <br>
 `PRIORITY` is set to normal, all other parameters are empty

<br>
### Listing
#### Listing all tasks: `list`
Shows a list of all tasks in the task manager.<br>
Format: `list [-pr] [-st] [-ed]`

> * Tasks are listed in the order of your input into the task manager by default.
> * With different modifiers (i.e. `-pr`, `-st`, `-ed`), tasks is listed in different order.
> * With `list -pr`, your tasks are listed by `PRIORITY`
>  * Tasks with `high` priority are listed first, followed by `normal` priority, then `low` priority
> * With `list -st`, your tasks are listed by `STARTTIME` chronologically
>  * Tasks without start time will be listed at the end of the list.
> * With `list -ed`, your tasks are listed by `ENDTIME` chronologically
>  * Tasks without end time will be listed at the end of the list.


<div style="text-align:center" markdown="1">
<img src="images/UserGuide Mock up2.png" title="Task manager listing tasks by priority" width="900">
<figcaption>Fig. 2: Task manager listing tasks by priority</figcaption>
</div>

#### Listing all tags used: `list tags`
Lists all the tags used in the task manager.<br>
Format: `list tags`

<br>
### Deleting a task: `delete`
Deletes the specified task from the task manager.<br>
Format: `delete INDEX`

Examples:
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the task manager.

<br>
### Finding
#### Finding all tasks with keyword: `find`
Finds tasks whose description contains any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is not case sensitive. e.g. `Gives` will match `gives`
> * The order of the keywords does not matter. e.g. `Give Eggs` will match `Eggs Give`
> * Only your task's description is searched
> * Only full words will be matched e.g. `Return` will not match `Returns`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).<br>
    e.g. `Return` will match `Return Car`

Examples:
* `find Tutorial`<br>
  Returns `Tutorial 8` but not `tutorial`
* `find Return lunch Meeting`<br>
  Returns any tasks having description containing `Return`, `lunch`, or `Meeting`

#### Finding tasks with specified priority: `find pr/`
Format: `find pr/PRIORITY`<br>

Example:
* `find pr/high`<br>
Returns any tasks with high priority

#### Find tasks starting after given time: `find st/`
Format: `find st/STARTTIME`

> * When only DayOfWeek is inputted, tasks that start on that day, current time is listed

Examples:
* `find st/tues`<br>
Returns any task that starts after the current time of the specified day of the week, this Week
* `find st/27.10.2016`<br>
Returns any task that starts after `27.10.2016`, `00:00`
* `find st/16:00`<br>
Returns any tasks that starts after `16:00` `today`


#### Finding tasks due before given time: `find ed/`
Format: `find ed/ENDTIME`

> * When only DayOfWeek is inputted, tasks that are due by that day, current time is listed

Examples:
* `find ed/tues`<br>
Returns any task that ends before the current time of the specified day of the week, this Week
* `find ed/27.10.2016`<br>
Returns any task that ends before **27.10.2016**, **00:00**
* `find ed/16:00`<br>
Returns any tasks that end before **16:00 today**

#### Finding tasks with given tags: `find t/`
Format: `find t/TAG [MORE_TAGS]`

Examples:
* `find t/blue`<br>
Returns any tasks that have the tag `blue`
* `find t/cheese cake`<br>
Returns any tasks that have the tags `cheese` or `cake`

<br>
### Editing:
#### Edit a task: `update`
Update a detail of a task with the specific index in the list.<br>
Format: `update INDEX PROPERTY INPUT`

> * Edits a parameter of your specified task (specified with `INDEX`) by replacing the parameter stored with the new parameter accordingly
> * Your `INPUT` should follow the same format as that of the respective parameter referenced by `PROPERTY`


Examples:
* `list`<br>
  `update 3 des/ Go to SOC`<br>
  Edits the 3rd task listed in the task manager by replacing the previous description with `Go to SOC`.

#### Adding a tag: `addtag`
Add a tag to a task with specific index in the list.<br>
Format: `addtag INDEX TAG`

Example:
* `list`<br>
  `addtag 2 NUS`<br>
  Adds the tag `NUS` to the 2nd task listed.

#### Deleting a tag: `deleteTag`
Delete a tag of a task with specific index in the list.<br><br>
Format: `deletetag INDEX TAG`

Example:
* `list`<br>
  `deletetag 3 NTU`<br>
  Removes the tag `NTU` from the 3rd task listed.

<br>
### Complete a task: `complete`
Marks the task with the specified index as 'Completed' and updates the GUI accordingly.<br>
Format: `complete INDEX`

Examples:
* `list`<br>
  `complete 2`<br>
  Marks the second task listed as 'Completed'.<br>

<br>
### Undo action: `undo`
Undoes the most recent change from the task manager.<br>
Format: `undo`

<br>
### Clear entries: `clear`
Clears all entries from the task manager.<br>
Format: `clear`

<br>
### Exit the program: `exit`
Exits the program.<br>
Format: `exit`

<br>
### Save data
Task manager data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.


## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Task Manager folder.


## Command Summary

Command | Format
-------- | :--------
Add Tag | `addtag INDEX TAG`
Add Task | `add DESCRIPTION [pr/PRIORITY] [st/STARTTIME] [ed/ENDTIME] [t/TAG]...`
Clear | `clear`
Complete | `complete INDEX`
Delete Tags | `deletetag INDEX TAG`
Delete Task | `delete INDEX`
Edit | `update INDEX PROPERTY NEW_INFORMATION `
Find Tasks | `find KEYWORD [MORE_KEYWORDS]`
Find Tasks due by `ENDTIME` | `find ed/ENDTIME`
Find Tasks starting after `STARTTIME` | `find st/STARTTIME`
Find Tasks with `PRIORITY` | `find pr/PRIORITY`
Find Tasks with `TAG`s | `find t/TAG [MORE_TAGS]`
Help | `help`
List Tags | `list tags`
List Tasks | `list [-pr] [-st] [-ed]`
Undo | `undo`
