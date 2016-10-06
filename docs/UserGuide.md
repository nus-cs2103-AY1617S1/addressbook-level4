# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `SmartScheduler.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for the program.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`display`**` today` : lists all tasks scheduled for today
   * **`add`**` buy eggs by 5pm today` : 
     remind yourself to get some eggs by 5pm today
   * **`delete`**` 1` : deletes the first task shown in the to-do list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional parameters.
> * Items with `...` after them can have multiple instances.
> * Flexible ordering of parameters.

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`.
 
#### Adding a task : `add`
Adds a task, deadline, event or a floating task to the list. <br>
Format: `add TASKNAME [at/from TIMEDATE] [by/to TIMEDATE] [PRIORITY]` 

> For tasks and events, at least one of the two TIMEDATE values must be included.
> For floating tasks, none of the TIMEDATE values are specified.
> If priority is not specified, default priority of * is set.

Examples: 
* `add Buy eggs at 5pm 13/09/2016 ***`
* `add Meeting from 13/09/2016 5pm to 13/09/2016 7pm **`
* `add Pay bills by 13/09/2016 5pm`
* `add Do laundry *`

#### Displaying tasks : `show`
Displays tasks and their indexes in the specified timeframe.<br>
Format: `show [TYPE] [PERIOD]`

> Atleast one of the two parameters TYPE and PERIOD must be provided.
> TYPE format: all, overdue, events.
> PERIOD format: today, tomorrow, DATE, or floating.

Examples:
* `show float 13/09/2016`. Lists floating tasks for that particular date
* `show tmrw`. Lists all tasks for tomorrow
* `show overdue`. List all the overdue tasks

#### Searching for tasks : `find`
Lists tasks whose names match the given input.<br>
Format: `find SEARCHSTRING`

> * The search is case insensitive. e.g `buy` will match `Buy`
> * Wild cards can be indicated with the asterisk `*` e.g. `B*s` will match `Buy eggs`
> * Only the name is searched.
> * Only full words will be matched e.g. `Buy` will not match `Buys`

Examples: 
* `find b*y`<br>
  Returns `buy` but not `buy eggs`
* `find b*y*`<br>
  Returns `buy eggs` but not `must buy eggs`

#### Deleting a task : `delete`
Deletes the specified task.<br>
Format: `delete INDEX/TASKNAME`

> Deletes the task at the specified index
> If TASKNAME is entered, tasks are sought out in the same way the `find` command does. Matching names and their indices are then displayed. The user can then proceed with deletion using the index of the appropriate task.

Examples:
* `delete 1`
  Deletes task at index 1
* `delete b*y*`
  Lists tasks matching `b*y*` for the user to choose from.

#### Updating a task : `update`
Update the date/time for a task<br>
Format: `update INDEX/TASKNAME [at/from START_TIMEDATE] [to/by END_TIMEDATE]`

> Replaces the start and end times of the task at the specified index
> If TASKNAME is entered, tasks are sought out in the same way the `find` command does. Matching names and their indices are then displayed. The user can then proceed with updating using the index of the appropriate task.
> Omitting START_TIMEDATE will remove START_TIMEDATE from the task
> Omitting END_TIMEDATE will remove END_TIMEDATE from the task

Examples:
* `update 1 at 13/09/2016 5pm`<br>
> `Meeting from 13/09/2016 4pm to 13/09/2016 6pm` will be replaced with `Meeting at 13/09/2016 5pm`
* `update b*y* from 13/09/2016 4pm to 13/09/2016 6pm`
> Lists tasks matching `b*y*` for the user to choose from

#### Marking a task as complete : `complete`
Marks a task as complete.<br>
Format: `complete INDEX/TASKNAME`

> Marks the task at the specified index as complete
> If TASKNAME is entered, tasks are sought out in the same way the `find` command does. Matching names and their indices are then displayed. The user can then proceed with marking a task as complete using the index of the appropriate task.

Examples:
* `complete 1`
  Marks task at index 1 as complete
* `complete b*y*`
  Lists tasks matching `b*y*` for the user to choose from

#### Setting the storage location: `setstorage`
Sets the data storage location <br>
Format: `setstorage FILEPATH`

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data 
The task manager data is saved on the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous TaskLine folder.
       
## Command Summary

Command | Format  
-------- | :--------
help | `help` 
add | `add TASKNAME [at/from TIMEDATE] [to/by TIMEDATE] [PRIORITY]`
display | `display [TYPE] [PERIOD]`
find | `find SEARCHSTRING`
delete | `delete INDEX/TASKNAME`
update | `update INDEX/TASKNAME [at/from START_TIMEDATE] [to/by END_TIMEDATE]`
complete | `complete INDEX/TASKNAME`
setstorage | `setstorage FILEPATH`
exit | `exit`
