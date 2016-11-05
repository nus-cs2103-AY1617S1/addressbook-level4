# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `malitio.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your malitio.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/Ui.jpg" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list deadlines`** : lists all deadlines
   * **`add`**` drink water` : 
     adds `drink water` to the to-do-list.
   * **`delete`**` e2` : deletes the 2nd event shown in the current schedule list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.
> * The following format of DATE/TIME is recognised: 2016-10-24 12pm, Oct 24th noon, day after tomorrow 3pm, next wed.
> * If one of the field of year, month, day or time is not specified, the default is current year/month/day/time.

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

 <!--- @@author A0129595N --->
#### Adding a task: `add`
Adds a task to the to-do list<br>
There are three types of tasks that can be added in Malitio: To-Do, Deadline and Event.<br>
Alphanumeric (alphabets and/or numbers) tags can be added.<br>

Floating Task Format: `add TASK_NAME [t/TAG]`<br>
Deadline Format: `add TASK_NAME by DATE/TIME [t/TAG]`<br>
Event Format: `add TASK_NAME start DATE/TIME end DATE/TIME [t/TAG]`<br>
Note: TASK_NAME cannot contain any of the following key words: "by", "start" and "end".<br>

Examples: 
* `add drink water t/healthy`
* `add CS2103 homework by 09-10 1100`
* `add buy present for friend by tomorrow`
* `add lunch with mom start 05-10 1400 end 05-10 1700 t/dontbelate t/restaurant`
* `add wedding dinner start this sat 6pm end this sat 10pm t/buypresent`

<!--- @@author A0153006W --->

#### Listing tasks: `list`
Shows a list of everything in the to-do list.<br>
Format: `list [tasks|events|deadlines]`

Shows a list of all events and/or deadlines in the to-do list on and after that date.<br>
Format: `list [deadlines|events] DATE/TIME`

Examples:
* `list`
* `list deadlines`
* `list deadlines 05-10 1400`
* `list 05-10 1400`
<!--- @@author A0122460W --->

#### List all task from beginning of time: `listall`
List all the task from beginning of time even if those task are already past current time.<br>
Format: `listall`

> List all the task from beginning of time

Examples: 

* `listall`<br>
  List all the task from beginning of time in Malitio.  

<!--- @@author a0126633j --->
#### Finding tasks: `find`
Finds all input entries specified by the type (deadlines/ floating tasks/ events) whose names or tags is a substring of the given keywords.<br>
Find MM-DD-YYY also returns deadlines/events on the specified date time.
If the type is not specified, all entries containing the keyword will be displayed. <br>
Format: `find [f|d|e] KEYWORD  [MORE KEYWORDS]`

> * The search is case insensitive.
> * The order of the keywords does not matter. e.g. `Race Car` will match `Car race`
> * Only the task names are searched.
> * Part of the words will be matched e.g. `Han` will match `Hans`
> * Task matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Work` will match `workout daily`

Examples: 
* `find f work`<br>
  Returns `workout with mom` in floating task list
* `find d lunch`<br>
  Returns `lunch with mom` in deadlines list 
* `find e 10-31-2016`<br>
  Returns all events that has start or end date as `10-31-2016`
* `find lunch dinner breakfast`<br>
  Returns all tasks having names `lunch`, `dinner`, or `breakfast`


#### Deleting a task : `delete`
Deletes the specified task from lists.<br>

Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. 
  The index **must have either 'f','d' or 'e' as a prefix and also a positive integer** f1, e2, d3, ...<br>

Examples: 
* `delete e2`<br>
  Deletes the 2nd task in the currently showing events list.
* `delete f1`<br>
  Deletes the 1st task in the currently showing floating task list. 
<!--- @@author --->

<!--- @@author A0129595N --->

#### Edit a task : `edit`
Edits the specified task from the to-do list.<br>
Edit Floating Task Format: `edit 'f'INDEX [TASK_NAME] [t/TAG]`<br>
Edit Deadline Format: `edit 'd'INDEX [TASK_NAME] [by DATE/TIME] [t/TAG]` <br>
Edit Event Format `edit 'e'INDEX [TASK_NAME] [start DATE/TIME] [end DATE/TIME]` <br>
To remove all tags from a task, use the parameter: t/null <br>


> Edits the task at the specified `INDEX` with the given one or more parameters.
  The index refers to the index number shown in the most recent listing.<br>
  The index **must have either 'f','d' or 'e' as a prefix and also a positive integer** f1, e2, d3, ...<br>
  At least one of the optional parameters must be present <br>
  The prefix is not case sensitive. <br>
  The edit function can only edit the details within the same type of task. <br>
  No changing of task type supported. <br>
  Editting the tags will result in a replacement of all tags. <br>

Examples: 
  `edit e1 end 12-21 2359` <br>
  Edits the 1st event in the schedule list, replacing its original end time with 21 December. <br>
  `edit f1 lunch with mom`<br>
  Edits the 1st task in the to-do list, replacing its original name with "lunch with mom".<br>
  `edit d2 t/wedding t/love`<br>
  Edits the 2nd deadline in the deadline list, replacing its existing tags with the tags: 'wedding' and 'love'.<br>
<!--- @@author --->

<!--- @@author A0122460W--->
#### Completing a floating task or deadline: `complete`
complete the specified floating task or deadline from Malitio by striking out them.<br>
Format: `complete f/d+INDEX`

> Complete the floating task or deadline at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must have either 'f' or 'd' as a prefix and also a positive integer** eg. f1, d2, ...

Examples: 

* `complete f2`<br>
  Complete the 2nd floating task in Malitio.  
* `complete d1`<br>
  Complete the 1st deadline in Malitio.
  
#### Uncompleting a floating task or deadline: `uncomplete`
complete the specified floating task or deadline from Malitio by unstriking out them.<br>
Format: `uncomplete f/d+INDEX`

> Uncomplete the floating task or deadline at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must have either 'f' or 'd' as a prefix and also a positive integer** eg. f1, d2, ...

Examples: 

* `uncomplete f2`<br>
  Complete the 2nd floating task in Malitio.  
* `uncomplete d1`<br>
  Complete the 1st deadline in Malitio.

<!--- @@author A0153006W --->

#### Marking as priority : `mark`
Marks the specified task in the to-do list <br>
Format: `mark INDEX`

Examples:
* `mark f1`

#### Marking as priority : `unmark`
Unmarks the specified task in the to-do list <br>
Format: `unmark INDEX`

Examples:
* `unmark f1`
<!--- @@author --->

<!--- @@author a0126633j --->
#### Clearing multiple entries : `clear`
Clears multiple entries from Malitio.<br>
Format: `clear [expired]` 

Examples: 
* `clear`<br>
  Clears all entries in Malitio.
* `clear expired`<br>
  Clears all completed floating tasks and deadlines, and events in the past.
<!--- @@author --->

<!--- @@author A0129595N --->
#### Undo the most recent action: `undo`
Undo the most recent data-related command and reverts Malitio to previous state. <br>
Data-related commands include add, delete, edit, clear, mark, unmark, complete, uncomplete. <br>
Format: `undo`

#### Redo the most recent action: `redo`
Redo the most recent data-related command and reverts Malitio to previous state before undo. <br>
Redo will no longer be possible after a new data-related command is executed. <br>
Data-related commands include add, delete, edit, clear, mark, unmark, complete, uncomplete. <br>
Format: `redo`

<!--- @@author --->
#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data 
Malitio data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually. <br>

<!--- @@author a0126633j --->
#### Specifying location of local data file: `save`
Users can specify which directory to save their data file. Only valid directory will be created if it does not exist already. <br>
The old data file will automatically be deleted.
Format: `save DIRECTORY`
* DIRECTORY can be in absolute or relative format

Example: 
* `save C://Users`<br>
  Saves data in C://Users/malitio.xml
<!--- @@author --->

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous malitio folder.
       
## Command Summary

Command | Format  
------- | :------- 
Add	| `add TASK_NAME [by DATE/TIME] [start DATE/TIME end DATE/TIME] [t/TAG]...`
Clear 	| `clear [expired]`
Delete  | `delete [f|d|e]INDEX`
Find 	| `find KEYWORD [MORE_KEYWORDS] [t/TYPE]`
List 	| `list`
Listall 	| `listall`
Edit 	| `edit [f|d|e]INDEX [NAME] [by DATE/TIME] [start DATE/TIME] [end DATE/TIME] [t/TAG]...`
Complete| `complete [f|d]INDEX`
Uncomplete| `uncomplete [f|d]INDEX`
Mark 	| `mark [f|d|e]INDEX`
Unmark 	| `unmark [f|d|e]INDEX`
Help 	| `help`
Undo 	| `undo`
Redo 	| `redo`
Save 	| `save DIRECTORY`


