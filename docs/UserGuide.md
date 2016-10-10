# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `supertasker.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/uiMockUp.jpg" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : lists all tasks due today
   * **`add`**` follow up with Jack on sales report` : 
     adds a task named `follow up with Jack on sales report` to the Task Manager.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `[SQUARE_BRACKETS]` are optional.
> * The order of parameters is fixed.

> **Task Structure**<br>

> There are 3 types of tasks.
> * Floating Task
> * Deadline
> * Event<br>

> Every task is either a floating task, deadline or event. It cannot be both or all 3 or none at all.<br>
> Refer to [Glossary](DeveloperGuide.md#appendix-d--glossary) for definitions of each type of task.

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`
 
#### Adding a task : `add`
Adds a task to the task manager.<br>
Format: `add TASK_NAME [-d e/END_DATETIME] [-e s/START_DATETIME e/END_DATETIME]` 

> To add a floating task, user is required to provide `TASK_NAME` only.<br>
  To add a deadline, user is required to provide `END_DATETIME` in addition to `TASK_NAME`.<br>
  To add an event, user is required to provide `START_DATETIME` and `END_DATETIME` in addition to `TASK_NAME`.<br>
  The optional parameters for this command are mutually exclusive.
  For example, if `-d` is specified, then the task is a deadline. Thus it is invalid to specify `-e` to make it an event.<br>
  Date/Time parameters can be entered in a more flexible way, e.g. `16 Nov`, `6pm`.

Examples: 
* `add follow up with Jack on sales report`<br>
  Adds a floating task named `follow up with Jack on sales report`.
* `add assignment 3 -d e/12 Oct`<br>
  Adds a deadline named `assignment 3` due on 12th October 2016.
* `add project Highlight -e s/1-10-2016 e/14-11-2016`<br>
  Adds an event named `project Highlight` starting on 1st October 2016 to 14th November 2016.
* `add driving test -d e/16 Nov -e s/10 Oct e/16 Nov`<br>
  This is an invalid command, as a task cannot be both a deadline and an event.
  
#### Set task as complete : `complete`
Sets the specified task as complete.<br>
Format: `complete TASK_INDEX`

> Sets the task at the specified `TASK_INDEX` as complete.<br>
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...
  
Examples: 
* `list`<br>
  `complete 2`<br>
  Set the 2nd task as complete in the task manager.

#### Listing tasks : `list`
Shows a list of tasks in the task manager.<br>
Additional options include: `-all`, `-float`, `-deadline`, `-event`, `-complete`<br>
Format: `list [-OPTION]`

> The default option shows a list of all tasks due today.
> * `-all` : Shows a list of all tasks in the task manager.
> * `-float` : Shows a list of all floating tasks.
> * `-deadline` : Shows a list of all deadlines.
> * `-event` : Shows a list of all events.
> * `-complete` : Shows a list of all completed tasks.

#### Deleting a task : `delete`
Deletes the specified task from the task manager.<br>
Format: `delete TASK_INDEX`

> Deletes the task at the specified `TASK_INDEX`.<br>
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the task manager.
* `find report`<br> 
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.
  
#### Finding all tasks containing any keyword in their name : `find`
Finds tasks whose names contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS] [s/START_DATETIME] [e/END_DATETIME]`

> * The search is not case sensitive. e.g `complete` will match `Complete`
> * The order of the keywords does not matter. e.g. `meeting John` will match `John meeting`
> * Partial words will be matched e.g. `meet` will match `meeting`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `meeting` will match `meeting with John`
> * Specifying `s/` or `e/` will search for tasks containing either specified dates.
> * Specifying both `s/` and `e/` will search for tasks whose dates fall between the specified range.
> * Date/Time parameters can be entered in a more flexible way, e.g. `16 Nov`, `6pm`.

Examples: 
* `find Highlight`<br>
  Returns any tasks having names `Highlight` or `highlight`
* `find product highlight showcase`<br>
  Returns any task having names `product`, `highlight`, or `showcase`
* `find s/23-10-2016 e/30-10-2016`<br>
  Returns any task whose dates fall between 23-10-2016 and 30-10-2016  
  
#### Editing a task : `edit`
Edits the specified task from the task manager.<br>
Format: `edit TASK_INDEX [-d e/END_DATETIME] [-e [s/START_DATETIME] e/END_DATETIME] [-e s/START_DATETIME [e/END_DATETIME]]`

> Edits the task at the specified `TASK_INDEX`.<br>
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...<br>
  Specified parameters will overwrite previous data.<br>
  User can specify `END_DATETIME` along with `-d` to turn the task into a deadline.<br>
  User can specify `START_DATETIME` or/both `END_DATETIME` to turn the task into an event.<br>
  Date/Time parameters can be entered in a more flexible way, e.g. `16 Nov`, `6pm`.

Examples: 
* `list`<br>
  `edit 2 -d e/23-10-2016`<br>
  Edit the 2nd task in the task manager into a deadline 23rd October 2016.
  
#### Viewing a task : `view`
Views details of the specified task from the task manager.<br>
Format: `view TASK_INDEX`

> Views details of the task at the specified `TASK_INDEX`.<br>
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `view 2`<br>
  Displays details of the 2nd task in the task manager.

#### Undoing the last command : `undo`
Undo the last command executed.<br>
Format: `undo`

> Able to undo up to the last 100 commands.<br>
  Only commands that changes data are included (`add`, `delete`, `clear`, `edit`, `complete`).

#### Clearing completed tasks : `clear`
Clears all completed tasks from the task manager.<br>
Format: `clear` 

#### Specifying data storage location : `store`
Specifies data storage location.<br>
Format: `store [FILE_LOCATION]`

> Stores data of the task manager at the specified `FILE_LOCATION`.<br>
  If `FILE_LOCATION` is not specified, a dialog box will appear and the user can browse for the storage location.

Examples: 
* `store C:\Users\Jim\Desktop\Work`<br>
  Specifies data storage location at 'C:\Users\Jim\Desktop\Work'.
  
* `store`<br>
  Displays a dialog box for user to browse for the storage location.

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data 
Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ
       
## Command Summary

Command | Format | Description 
----------- | ------------------------------- | :--------- 
Help | `help` | View help on command usage
Add | `add TASK_NAME [-d e/END_DATETIME] [-e s/START_DATETIME e/END_DATETIME]` | Add a task
Complete | `complete TASK_INDEX` | Set task as complete
List | `list` | List tasks due today
Delete | `delete TASK_INDEX` | Delete a task
Find | `find KEYWORD [MORE_KEYWORDS] [s/START_DATETIME] [e/END_DATETIME]` | Find all tasks containing any keywords
Edit | `edit TASK_INDEX [-d e/END_DATETIME] [-e [s/START_DATETIME] e/END_DATETIME] [-e s/START_DATETIME [e/END_DATETIME]]` | Edit a task
View | `view TASK_INDEX` | View details of a task
Undo | `undo` | Undo last command
Clear | `clear` | Clear completed tasks
Store | `store FILE_LOCATION` | Specify storage location
Exit | `exit` | Exit program
