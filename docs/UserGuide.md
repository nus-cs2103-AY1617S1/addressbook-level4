# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `RubyTask.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : lists all tasks to do
   * **`add`** `add Buy vegetables i/From the supermarket d/05102016 s/1400 e/1500 t/2` : 
     adds a task named `Buy vegetables` to the Task Manager.
   * **`delete`**`Buy vegetables` : deletes the 'Buy vegetables' task from the manager.
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
Adds a task to the task manager<br>
Format: `add TASK i/INFORMATION d/DATE s/START_TIME e/END_TIME t/LEVEL_OF_URGENCY_TAG` 

> Level of importance tags range from 1 to 5 (1-Very Low Urgency, 2-Low Urgency, 3-Neutral, 4-High Urgency, 5-Very High Urgency)

Examples: 
* `add Buy vegetables i/From the supermarket d/05102016 s/1400 e/1500 t/2`
* `add CS2013T Tutorial i/Prepare for week 8 Tutorial d/04102016 s/2200 e/2359 t/5`

#### Listing all tasks : `list`
Shows a list of all tasks currently in the task manager.<br>
Format: `list`

#### Finding all tasks containing any keyword in the task name: `find`
Finds tasks which contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The order of the keywords does not matter. e.g. `CS2103T Tutorial` will match `Tutorial CS2103T`
> * Only the task name is searched.
> * Only full words will be matched e.g. `CS2103T` will not match `CS2103`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `CS2103T` will match `CS2103T Tutorial`

Examples: 
* `find CS2130T`<br>
  Returns `CS2130T Tutorial` but not `CS2103 Tutorial`
* `find CS2103T CS2101 CS3235`<br>
  Returns Any tasks having keywords `CS2103T`, `CS2101`, or `CS3235`

#### Deleting a task : `delete`
Deletes the specified task from the task manager. Irreversible.<br>
Format: `delete TASK_NAME`

> Deletes the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the address book.
* `find CS2103T`<br> 
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

#### Select a person : `select`
Selects the task identified by the index number used in the last task listing.<br>
Format: `select INDEX`

> Selects the task and loads the Google search page of the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `select 2`<br>
  Selects the 2nd task in the address book.
* `find CS2103T` <br> 
  `select 1`<br>
  Selects the 1st task in the results of the `find` command.

#### Clearing all entries : `clear`
Clears all entries from the task manager.<br>
Format: `clear`  

#### Exiting the program : `exit`
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
Add | `add NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]...`
Clear | `clear`
Delete | `delete INDEX`
Find | `find KEYWORD [MORE_KEYWORDS]`
List | `list`
Help | `help`
Select | `select INDEX`
