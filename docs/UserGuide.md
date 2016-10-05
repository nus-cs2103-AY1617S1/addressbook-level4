# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `schema.jar` from the 'releases' tab.
2. Copy the file to the folder you want to use as the home folder for your To Do List.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**` CS2103T tutorial work, 1100, tomorrow,t TUTORIAL` : 
     adds a task named `CS2103T tutorial work` to the To Do List.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`
 
#### Adding a task: `add`
Adds a task to the To Do List<br>
Format: `add TASK, [TIME], [DATE],[t TAG...]`  <br>
Format: `do TASK, [TIME], [DATE],[t TAG...]` 

 
> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional, 
> items with `...` after them can have multiple instances. Order of parameters are fixed. 
> 
> Tasks can have any number of tags (including 0)

Examples: 
* `add Dinner, 1900, 10/10/16,t Date`
* `do CS2010 PS10, 1000, 11/10/16,t Assignment`
* `add Pay school fees`

#### Listing all tasks in current tab: `list`
Shows a list of all tasks in the To Do List.<br>
Format: `list`
Format: `list [TAB_NAME]`

> TAB_NAME includes: <br>
> 1. Home <br>
> 2. Tasks <br>
> 3. Events <br>
> 4. Deadlines <br>
> 5. Archive <br>


#### Finding all tasks containing any keyword in their description: `find`
Finds tasks whose description contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> The search is not case sensitive, the order of the keywords does not matter, only the description is searched, 
and tasks matching at least one keyword will be returned (i.e. `OR` search).

Examples: 
* `find Dinner`<br>
  Returns `Dinner on 10/10/16 at 1900hrs (Date)`
* `find 2010`<br>
  Returns any task having description `2010`.

#### Deleting a task : `delete`
Deletes the specified task from the To Do List. Irreversible.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the To Do List.
* `find buy groceries`<br> 
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

#### Editing a task : `edit`
Edits the task identified by the index number used in the last task listing.<br>
Format: `edit INDEX DETAILS`

> DETAILS can be the task itself, time, date or tag

Examples: 
* `list`<br>
  `edit 2`<br>
  Selects the 2nd task in the To Do List.
* `find CS5000` <br> 
  `edit 1`<br>
  Selects the 1st task in the results of the `find` command.

#### Clearing all entries : `clear`
Clears all tasks from the To Do List.<br>
Format: `clear`  

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data 
Schema's data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Schema.
       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `add TASK, [TIME], [DATE],[t TAG...]`
Clear | `clear`
Delete | `delete INDEX`
Find | `find KEYWORD [MORE_KEYWORDS]`
List | `list [TAB_NAME]`
Help | `help`
Edit | `edit INDEX DETAILS`
Undo | `undo`
Redo | `redo`
Mark | `mark INDEX`
Relocate | `relocate FILE_PATH`
