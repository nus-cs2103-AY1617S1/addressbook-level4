# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

<br>
## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `addressbook.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Address Book.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > (TODO add GUI snap)

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
   
5. Refer to the [Features](#features) section below for details of each command.<br>

<br>
## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.

<br>
#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`
 
<br>
#### Adding a task: `add`
Adds a task to the task manager <br>
Format: `add t/TASK_DESCRIPTION [st/START_TIME] [et/END_TIME] [d/DATE] [tg/TAGS]...`

<img src="../assets/add_1.png" width="1000"><br>
First, type a command.
<img src="../assets/add_2.png" width="1000"><br>
Command will be added.

> If the time is specified, the date must also be specified.
> If the start time is specified, the end time must also be specified.
>
> Tasks can have any number of tags (including 0)

Examples: 
* `add t/Do CS2103T Pre-tutorial et/7pm d/8 Oct 2016`
* `add t/CS2103T Lecture st/2pm et/4pm d/7 Oct 2016 tg/Important`

<br>
#### Listing all tasks : `list`
Shows a list of all tasks in the Task Manager.<br>
Format: `list`


<br>
#### Listing all tasks by categories : `list`
Shows a list of all tasks in the Task Manager according to a stated category
Format: `list [CATEGORIES]`

> By default, Tasks will be sorted in order of task, floating, event, and then by index
> Alternatively, list will be sorted by the category requested on top first, then by index.
>
> Categories can be either **Pre-defined categories** or **User-defined tags**: 
>   Pre-defined categories include: today, tomorrow, priority, completed
>   User-defined tags

Examples:
* `list today`
* `list tomorrow`
* `list priority`
* `list completed`
* `list USER-DEFINED TAGS`

<br>
#### Finding all tasks containing any keyword in their title: `find`
Finds tasks whose titles contain any of the given keywords.
Format: `find KEYWORD [MORE_KEYWORDS]`

> * Finds all tasks that has the keyword in the task title, and shows in the list sorted in the one of the following order:
> * By default it will be sorted by the most keyword matches first, but the sorting can be changed
> * 1. Show the task with the most keyword matches first
> * 2. Sorted in index order
>
> * The search is not case sensitive. e.g `homework` will match `Homework`
> * The order of the keywords does not matter. e.g. `to do homework` will match `homework to do`


Examples: 
* `find homework`<br>
  Returns `homework/Homework/HomeWoRk`
* `find dinner meeting project`<br>
  Returns any task having names `dinner`, `meeting`, or `project`

<br> 
#### Deleting a task : `delete`
Deletes the specified task from the Task Manager.<br>
Format: `delete INDEX`

> Task will be removed from the list

Examples: 
* `list`<br>
  `delete 2`<br>
  Deletes task with index 2 in the list
* `find KEYWORDS`
  `delete 459`
  Delete task with index 459 in the list

<br> 
#### Undo a command: `undo`
Undo the previous action
Format: `undo`

> Task Manager will revert the last action done

Examples: 
* `delete 2`<br>
  `undo`<br>
  Restores task which had an index of 2 back to the list
* `add do housework`<br>
  `undo`<br>
  Deletes task of “add do housework”

<br>  
#### Mark task as complete : `completed`
Marks a task as completed as a normal tag
Format: `completed INDEX`

> Task Manager will mark task as completed with a predefined tag “completed” 

<br>
#### Clearing all entries : `clear`
Clears all entries from the Task Manager in the save data.<br>
Format: `clear`  

<br>
#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

<br>
#### Saving the data 
Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

> The file name must end in `.txt` for it to be acceptable to the program.
>
> When running the program inside Eclipse, you can set command line parameters before running the program.

<br>
## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Task Manager folder.

<br>       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `add t/TASK_DESCRIPTION [st/START_TIME] [et/END_TIME] [d/DATE] [tg/TAGS]...`
Clear | `clear`
Completed | `completed INDEX`
Delete | `delete INDEX`
Exit | `exit`
Find | `find KEYWORD [MORE_KEYWORDS]`
Help | `help`
List | `list`
Listby | `listby CATEGORIES`
Undo | `undo`




