[comment]: # (@@author A0124333U)
# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Support Date Format](#supported-date-formats)
* [Command Summary](#command-summary)

## Quick Start

1. Ensure you have Java version `1.8.0_60` or later installed in your Computer.  
   > Having any Java 8 version is not enough.  
   This app will not work with earlier versions of Java 8.
   
   Click [here](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) to download the latest Java version.
   
2. Download the latest `tars.jar` from the '[releases](https://github.com/CS2103AUG2016-F10-C1/main/releases)' tab.
3. Copy the file to the folder you want to use as the home folder for your TARS App.
4. Double-click the file to start the app. The GUI should appear in a few seconds. 
5. Type the command in the command box and press <kbd>Enter</kbd> to execute it.  
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
6. Some example commands you can try:
   * **`ls`** : lists all tasks
   * **`add`**` Complete CS2103 Quiz 3 /dt 23/09/2016 /p h /t Quiz /t CS2103` : 
     adds a task `Complete CS2103 Quiz 3` to TARS.
   * **`del`**` 3` : deletes the 3rd task shown in TARS.
   * **`exit`** : exits the app
7. Refer to the [Features](#features) section below for details of each command. 
8. NOTE
	- All text in `< >` are required fields whereas those in `[ ]` are optional.
	- <INDEX> refers to the index number of a task shown in the task list.
	- The index **must be a positive integer** 1, 2, 3, ... 
	- Priority options are: `h` for High, `m` for Medium, `l` for Low


## Features
 
#### Adding a task : `add`
Adds a task to TARS  
Format: `<TASK_NAME> [/dt DATETIME] [/p PRIORITY] [/t TAG_NAME ...] [/r NUM_TIMES FREQUENCY]\n` 
 
> Support for events (i.e., has a start time and end time), deadlines (tasks that have to be done before a specific deadline), and floating tasks (tasks without specific times).
> Parameters can be in any order.

Examples: 
* `add Meet John Doe /dt 26/09/2016 0900 to 26/09/2016 1030 /t CATCH UP`
* `add Complete CS2103 Quiz /dt 23/09/2016 /p h /t Quiz /t CS2103, /r 13 EVERY WEEK`
* `add Floating Task`

[comment]: # (@@author A0124333U)
#### Changing data storage location : `cd`
Changes the directory of the TARS storage file.
Format: `cd <FILE_PATH.xml>`

> Returns an error if the directory chosen is invalid. 
> `<FILE_PATH>` must end with the file type extension, `.xml`

Examples:
* `cd C:\Users\John_Doe\Documents\tars.xml`

#### Clearing the data storage file : `clear`
Clears the whole To-Do List storage file.  
Format: `clear` 

[comment]: # (@@author A0124333U)
#### Confirming a reserved timeslot : `confirm`  
Confirms a dateTime for a reserved task and adds it to the task list.  
Format: `confirm <RSV_TASK_INDEX> <DATETIME_INDEX> [/p PRIORITY] [/t TAG_NAME ...]`

> Confirm the task of a specific `<RSV_TASK_INDEX>` at a dateTime of a specific `<DATETIME_INDEX>`. 
> The `<RSV_TASK_INDEX>` refers to the index number shown in the reserved task list.  
> The `<DATETIME_INDEX>` refers to the index number of the dateTime.

Examples:
* `confirm 3 2 /p l /t Tag` 

[comment]: # (@@author A0121533W)
#### Deleting a task : `del`
Deletes the task based on its index in the task list.  
Formats: 
* `del <INDEX> [INDEX ...]`  
* `del <START_INDEX>..<END_INDEX>`

> Deletes the task at the specific `<INDEX>`.
> Start index of range must be before end index.

Examples:
* `del 3 6`
* `del 1..3`

[comment]: # (@@author A0121533W)
#### Marking tasks as done : `do`
Marks the task based on its index in the task list as done.
Format: `do <INDEX> [INDEX ...]`  
Format: `do <START_INDEX>..<END_INDEX>`

> Marks the task at the specific `<INDEX>` as `done`.
> Start index of range must be before end index.

Examples:
* `do 2 4 6`
* `do 1..3`

[comment]: # (@@author A0121533W)
#### Editing a task : `edit`
Edits any component of a particular task.  
Format: `edit <INDEX> [/n TASK_NAME] [/dt DATETIME] [/p PRIORITY] [/ta TAG_TO_ADD ...] [/tr TAG_TO_REMOVE ...]`

> Edits the task at the specific `<INDEX>`. 
> `/ta` adds a tag to the task.
> `/tr` removes a tag from the task.
> Parameters can be in any order.

Examples:
* `edit 3 /n Meet John Tan /dt 08/10/2016 1000 to 1200 /p h /ta friend`


#### Exiting the program : `exit`
Exits the program.  
Format: `exit` 

[comment]: # (@@author A0124333U)
#### Finding tasks : `find`
Finds all tasks containing a list of keywords (i.e. AND search).  
Two modes: Quick Search & Filter Search.  
Format:
* [Quick Search]: `find <KEYWORD> [KEYWORD ...]`  
* [Filter Search]: `find [/n NAME_KEYWORD ...] [/dt DATETIME] [/p PRIORITY] [/do] [/ud] [/t TAG_KEYWORD ...]`

> **Quick Search Mode**: Find tasks quickly by entering keywords that match what is displayed in the task list.
> **Filter Search Mode**: Find tasks using task filters (i.e. /n, /p, /dt, /do, /ud, /t).
> Use /n to filter tasks by task name.
> Use /p to filter tasks by priority level.
> Use /dt to filter tasks by date (in a date range).
> Use /do to filter all done tasks (Cannot be used together with /ud).
> Use /ud to filter all undone tasks (Cannot be used together with /do).
> Use /t to filter tasks by tags.
> `<KEYWORD>` are **case-insensitive**. 
> Parameters can be in any order.

Examples: 
* `find meet John` uses Quick Search and returns all tasks containing BOTH the keywords “meet” and “John” (e.g. meet John Doe)
* `find /n meet /dt 17/10/2016 1300 to 18/10/2016 1400` uses Filter Search and returns all tasks whose name contains "meet" and whose task date falls within the range "17/10/2016 1300 to 18/10/2016 1400" (e.g. meet Tim for dinner, 17/10/2016 1800 to 17/10/2016 1900)

[comment]: # (@@author A0124333U)
#### Suggesting free timeslots : `free`
Suggests free timeslots in a specified day.
Format: `free <DATETIME>`

> Does not check for tasks without dateTime nor tasks without a start dateTime.

Examples:
* `free next tuesdsay`
* `free 26/10/2016`

[comment]: # (@@author A0139924W)
#### Displaying a list of available commands : `help`
Shows program usage instructions in help panel.
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`.

[comment]: # (@@author A0140022H)
#### Listing tasks : `ls`
Lists all tasks. 
Format: 
* `ls`
* `ls /dt [dsc]`
* `ls /p [dsc]`

> All tasks listed by default.
> Use /dt to list all tasks by earliest end dateTime.
> Use /p to list all task by priority from low to high.
> Use dsc with previous two prefixes to reverse the order.

Examples:
* `ls`
* `ls /dt`
* `ls /dt dsc`
* `ls /p`
* `ls /p dsc`

[comment]: # (@@author A0139924W)
#### Redoing a command : `redo`
Redo a previous command
Format: `redo` 

> Able to redo all `add`, `delete`, `edit`, `tag`, `rsv`, `confirm` and `del` commands from the time the app starts running.

[comment]: # (@@author A0124333U)
#### Reserving timeslots for a task : `rsv` 
Reserves one or more timeslot for a task  
Format: `rsv <TASK_NAME> </dt DATETIME> [/dt DATETIME ...]`

> Multiple dateTimes can be added.

Examples:
* `rsv Meet John Doe /dt 26/09/2016 0900 to 1030 /dt 28/09/2016 1000 to 1130`

[comment]: # (@@author A0124333U)
#### Deleting a task with reserved timeslots : `rsv /del`
Deletes a task with all its reserved time slots  
Format: `rsv /del <INDEX>`
Format: `rsv /del <START_INDEX>..<END_INDEX>`

> Deletes the task at the specific `<INDEX>`. 
> Start index of range must be before end index.

Examples:
* `rsv /del 5`
* `rsv /del 1..4`

[comment]: # (@@author A0139924W)
#### Editing a tag's name : `tag /e`
Edits a tag's name  
Format: `tag /e <INDEX> <TAG_NAME>`

> Edits the name of the tag at the specific `<INDEX>`. 

Examples:
* `tag /e 5 Assignment`

[comment]: # (@@author A0139924W)
#### Deleting a tag : `tag /del`
Deletes a particular tag  
Format: `tag /del <INDEX>`

> Deletes the tag at the specific `<INDEX>`.

Examples:
* `tag /del 4` deletes the tag at Index 4

[comment]: # (@@author A0139924W)
#### Listing all tags : `tag /ls`
Lists all tags in TARS  
Format: `tag /ls`

[comment]: # (@@author A0121533W)
#### Marking tasks as undone : `ud`
Marks the task based on its index in the task list as undone.
Format: `ud <INDEX> [INDEX ...]`  
Format: `ud <START_INDEX>..<END_INDEX>`

> Marks the task at the specific `<INDEX>` as `undone`.
> Start index of range must be before end index.

Examples:
* `ud 2 4 6`
* `ud 1..3`

[comment]: # (@@author A0139924W)
#### Undoing a command : `undo`
Undo a command executed by the user.  
Format: `undo` 

> Able to undo all `add`, `delete`, `edit`, `tag`, `rsv`, `confirm` and `del` commands from the time the app starts running.



#### Saving the data 
TARS data are saved in the hard disk automatically after any command that changes the data.  
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?  
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous TARS app.

[comment]: # (@@author A0139924W)
## Supported Date Formats
#### formal dates
Formal dates are those in which the day, month, and year are represented as integers separated by a common separator character. The year is optional and may preceed the month or succeed the day of month. If a two-digit year is given, it must succeed the day of month.

Examples:
* `28-01-2016`
* `28/01/2016`
* `1/02/2016`
* `2/2/16`

#### relaxed dates
Relaxed dates are those in which the month, day of week, day of month, and year may be given in a loose, non-standard manner, with most parts being optional.

Examples:
* `The 31st of April in the year 2008`
* `Fri, 21 Nov 1997`
* `Jan 21, '97`
* `Sun, Nov 21`
* `jan 1st`
* `february twenty-eighth`

#### relative dates
Relative dates are those that are relative to the current date.

Examples:
* `next thursday`
* `last wednesday`
* `today`
* `tomorrow`
* `yesterday`
* `next week`
* `next month`
* `next year`
* `3 days from now`
* `three weeks ago`

#### prefixes
Most of the above date formats may be prefixed with a modifier.

Examples:
`day after`
`the day before`
`the monday after`
`the monday before`
`2 fridays before`
`4 tuesdays after`

#### time
The above date formats may be prefixed or suffixed with time information.

Examples:
* `0600h`
* `06:00 hours`
* `6pm`
* `5:30 a.m.`
* `5`
* `12:59`
* `23:59`
* `8p`
* `noon`
* `afternoon`
* `midnight`

#### relative times

Examples:
* `10 seconds ago`
* `in 5 minutes`
* `4 minutes from now`

[comment]: # (@@author A0124333U)
## Command Summary

Command | Format  
-------- | :-------- 
[Add](#adding-a-task--add)| `add <TASK_NAME> [/dt DATETIME] [/p PRIORITY] [/t TAG_NAME ...] [/r NUM_TIMES FREQUENCY]`
[Change Storage Location](#changing-data-storage-location--cd) | `cd <FILE_PATH.xml>`
[Clear](#clearing-the-data-storage-file--clear) | `clear`
[Confirm](#confirming-a-reserved-timeslot--confirm) | `confirm <RSV_TASK_INDEX> <DATETIME_INDEX> [/p PRIORITY] [/t TAG_NAME ...]`
[Delete](#deleting-a-task--del) | `del <INDEX> [INDEX ...]`  <br> `del <START_INDEX>..<END_INDEX>`
[Done](#marking-tasks-as-done--do) | `do <INDEX> [INDEX ...]` <br> `do <START_INDEX>..<END_INDEX>`
[Edit](#editing-a-task--edit) | `edit <INDEX> [/n TASK_NAME] [/dt DATETIME] [/p PRIORITY] [/ta TAG_TO_ADD ...] [/tr TAG_TO_REMOVE ...]`
[Exit](#exiting-the-program--exit) | `exit`
[Find [Quick Search]](#finding-tasks--find) | `find <KEYWORD> [KEYWORD ...]`
[Find [Filter Search]](#finding-tasks--find) | `find [/n NAME_KEYWORD ...] [/dt DATETIME] [/p PRIORITY] [/do] [/ud] [/t TAG_KEYWORD ...]`
[Free](#suggesting-free-timeslots--free) | `free <DATETIME>`
[Help](#displaying-a-list-of-available-commands--help) | `help`
[List](#listing-tasks--ls) | `ls`
[List [Date]](#listing-tasks--ls) | `ls /dt`
[List [Priority]](#listing-tasks--ls) | `ls /p`
[Redo](#redoing-a-command--redo) | `redo`
[Reserve](#reserving-timeslots-for-a-task--rsv) | `rsv <TASK_NAME> </dt DATETIME> [/dt DATETIME ...]`
[Reserve [Delete]](#deleting-a-task-with-reserved-timeslots--rsv-del) | `rsv /del <INDEX>` <br> `rsv /del <START_INDEX>..<END_INDEX>`
[Tag [Delete]](#deleting-a-tag--tag-del) | `tag /del <INDEX>`
[Tag [Edit]](#editing-a-tags-name--tag-e) | `tag /e <INDEX> <TAG_NAME>`
[Tag [List]](#listing-all-tags--tag-ls) | `tag /ls`
[Undone](#marking-tasks-as-undone--ud) | `ud <INDEX> [INDEX ...]` <br> `ud <START_INDEX>..<END_INDEX>`
[Undo](#undoing-a-command--undo) | `undo`


