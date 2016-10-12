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
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list deadlines`** : lists all deadlines
   * **`add`**` drink water` : 
     adds `drink water` to the to-do-list.
   * **`delete`**` 3` : deletes the 3rd item shown in the current list
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
 
#### Adding a task: `add`
Adds a task to the to-do list<br>
Floating Task Format: `add TASK_NAME [t/TAG] [p/priority]`<br>
Deadline Format: `add TASK_NAME e/DDMMYYYY TTTT [t/TAG] [p/priority]`<br>
Event Format: `add TASK_NAME s/DDMMYYYY TTTT e/DDMMYYYY TTTT [t/TAG]`


Examples: 
* `add drink water p/high`
* `add CS2103 homework s/09102016 1100  p/high`
* `add lunch with mom s/05102016 1400 e/05102016 1700 t/don’t be late`
* `time format is from 0000 to 2359`

#### Listing tasks: `list`
Shows a list of all events/deadlines in the to-do list.<br>
Format: `list`

#### Listing tasks: `list deadlines`
Shows a list of all deadlines in the to-do list.<br>
Format: `list deadlines`

#### Listing tasks: `list DATE`
Shows a list of all events/deadlines in the to-do list on that date.<br>
Format: `list 07102016`

#### Finding all deadlines/floating tasks/events containing any keyword in their names and tags: `find`
Finds all input entries specified by the type (deadlines/ floating tasks/ events) whose names contain any of the given keywords.<br>
If the type is not specified, all entries containing the keyword will be displayed. <br>
Format: `find KEYWORD  [MORE KEYWORDS] [t/TYPE]`

> * The search is case insensitive.
> * The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
> * Only the task name and tags are searched.
> * Only full words will be matched e.g. `Han` will not match `Hans`
> * Task matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Hans` will match `Hans Bo`

Examples: 
* `find lunch t\task`<br>
  Returns `lunch with mom in task` 
* `find lunch t\deadlines`<br>
  Returns `lunch with mom in deadlines` 
* `find lunch t\events`<br>
  Returns `lunch with mom in events` 
* `find lunch dinner breakfast`<br>
  Returns Any task having names `lunch`, `dinner`, or `breakfast`

#### Deleting a task : `delete`
Deletes the specified task from the to-do list. Irreversible.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the to-do list.
* `find lunch`<br> 
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command. 

#### Deleting a task: `delete`
Deletes the specified task from the to-do list.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the to-do list.
* `find lunch`<br> 
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` or ‘ command.

#### Edit a task : `edit`
Edits the specified task from the to-do list.<br>
Format: `edit INDEX [n/TASK_NAME] [s/DDMMYYYY TTTT] [e/DDMMYYYY TTTT] [t/TAG]`

> Edits the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `edit 2 p/low`<br>
  Edit the 2nd task in the to-do list replacing the priority.
* `find lunch`<br> 
  `edit 1 n/lunch with mom`<br>
  Edits the 1st task in the results of the `find` or ‘ command.<br>
  Need to put at least one field

#### Select a task : `select`
Selects the task identified by the index number used in the last task listing.<br>
Format: `select INDEX`

> Selects the task and loads the Google search page the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `select 2`<br>
  Selects the 2nd task in Malitio.
* `find Betsy` <br> 
  `select 1`<br>
  Selects the 1st task in the results of the `find` command.

#### Clearing all entries : `clear`
Clears all entries from the to-do list.<br>
Format: `clear`  

#### Undo the most recent action: `undo`
Undo the most recent action and reverts the to-do list to previous state. <br>
Format: `undo`

#### Redo the most recent undo action: `redo` 
Redo the action<br>
Format: `redo`

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data 
Malitio data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.



## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous malitio folder.
       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `add TASK_NAME [s/DDMMYYYY TTTT] [e/DDMMYY TTTT] [t/TAG]...`
Clear | `clear`
Delete | `delete INDEX`
Find | `find KEYWORD [MORE_KEYWORDS] [t/TYPE]`
List | `list`
Help | `help`
Select | `select INDEX`
Undo | `undo`
Edit | `edit INDEX [n/TASK_NAME] [s/DDMMYYYY TTTT] [e/DDMMYYYY TTTT] [t/TAG]`


