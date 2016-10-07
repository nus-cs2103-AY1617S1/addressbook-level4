# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `schema.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Schema.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : lists all floating tasks, events and tasks due today
   * **`add`** `"CS2103T tutorial work" by/1100 on/tomorrow t/TUTORIAL` : 
     adds a task named `CS2103T tutorial work` with deadline due tomorrow at 11am to the Schema.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`
 
### Adding a task: `add` `do` `complete`
#### Add an event to Schema  
Format: `add "TASK", at/TIME, on/DATE [t/TAG...]`  
#### Add a task with deadline to Schema  
Format: `complete "TASK" by/TIME on/DATE [t/TAG...]`  
#### Add a floating task to Schema  
Format: `do "TASK" [t/TAG...]` 

 
>* Words in `UPPER_CASE` are the parameters, items in `[SQUARE_BRACKETS]` are optional, 
> items with `...` after them can have multiple instances.
>* Tasks will be added to the categories (event, deadlines, floating task) according to the keywords (`add`, `complete`, `do`).
>* DATE is in DDMM format.
>* Parameters can be in any order.
>* Separate different tags with ','.
>* There are no limit to the number of tags a task can have (including 0).

Examples: <br>
* Adding an event
    * `add "Dinner", at/1900 to 2000 on/1010 t/Date,meals`
* Adding a task with deadline
    * `complete "CS2010 PS10" by/1000 on/1110 t/Assignment`
* Adding a floating task
    * `do "Pay school fees"`

### Listing all tasks in current tab: `list`
Shows a list of all tasks in the To Do List.<br>
Format: `list [TAB_NAME]`

> TAB_NAME includes:
> 1. Home 		<br>
> 2. Tasks  	<br>
> 3. Events 	<br>
> 4. Deadlines 	<br>
> 5. Archive 

### Finding all tasks containing any keyword in their description: `find`
Finds tasks whose description contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

>* The search is not case sensitive.
>* The order of the keywords does not matter.
>* Tasks matching at least one keyword will be returned.

Examples: 
* `find Dinner`  
  Returns `Dinner on 10/10/16 at 1900hrs (Date)`.
* `find "cs2010 ps10"`  
  Returns `CS2010 PS10 on 11 Oct by 1000hrs (Assignment)`.
  
### Finding all tasks containing any tag keyword in their description: `findtag`
Finds tasks whose tag contain any of the given keywords.<br>
Format: `findtag KEYWORD [MORE_KEYWORDS]`

>* The search is not case sensitive
>* The order of the tag keywords does not matter
>* Tasks matching at least one tag keyword will be returned

Examples: 
* `find date`  
  Returns `Dinner on 10 Oct at 1900hrs (Date,meals)`.
* `find "exam,assignment"`  
  Returns `CS2010 PS10 on 11 Oct by 1000hrs (Assignment)`.

### Editing a task : `edit`
Edits the task identified by the index number used in the last task listing.  
Format: `edit INDEX [TASK] [at/TIME] [on/DATE] [t/TAGS...]`

>* At least one optional argument is required.
>* Can edit only one of the field for the task.

Examples: 
* `list`<br>
  `edit 1 Lunch`<br>
  Selects the 2nd task in Schema and edit the task to Lunch from Dinner.
* `find CS2010` <br> 
  `edit 1 on/2010`<br>
  Selects the 1st task in the results of the `find` command and edit the date from 1110 to 2010.

### Deleting a task : `delete`
Deletes the specified task from the To Do List.<br>
Format: `delete INDEX`

>* Deletes the task at the specified `INDEX`.
>* The index refers to the index number shown in the most recent listing.
>* The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the Schema.
* `find buy groceries`<br> 
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

### Undo a command : `undo`
Format: `undo`

>* Undo is not a command, so you can not undo a 'undo'. See `redo`.
>* Can only undo commands that make changes to database.

Example:
* `undo`  
  COMMAND removed.

### Redo a command : `redo`
Format: `redo`

>* Can only redo commands that make changes to database.
>* Only available when undo has been used before.

Example:
* `redo`  
  COMMAND repeated.

### Completing tasks : `mark`
Marks the task identified by the index number used in the last task listing as completed and put in the archived tab.  
Format: `mark INDEX`

>* Can only mark task that are not complete yet.

Examples: 
* `list`  
  `mark 1 `  
  Selects the 2nd task in Schema and mark the task as completed.
* `find CS2010`   
  `mark 1 `  
  Selects the 1st task in the results of the `find` command and mark the task as completed.

### Clearing of entries : `clear`
Clears all tasks in the current tab.<br>
Format: `clear [TAB_NAME]`

Example:
* `clear deadlines`
  All tasks in deadlines are cleared.

### Clearing all entries: `clearall`
Clears all tasks available from Schema.  
Format: `clearall`

Example:
* `clearall`  
  All tasks in Schema are cleared.
  
### Changing save location : `relocate`
Relocates the destination that data is saved.  
Format: `relocate FILEPATH`

Example:
* `relocate ~/document/schema`  
  Data has been relocated to ~/document/schema

### Exiting the program : `exit`
Exits the program.  
Format: `exit`  

Example:
* `exit`  
  Exiting Schema...

### Saving the data 
Schema's data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the application in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Schema.
       
**Q**: Is my data secure?  
**A**: Your data is stored locally on your hard drive as a .xml file. Your data is as secure as your computer

       
## Command Summary

Command | Format  
-------- | :-------- 
Help | `help`
Add | `add "TASK" [at/TIME] [on/DATE] [t/TAG...]`
Complete | `complete "TASK" [by/time] [on/DATE] [t/TAG...]`
Do | `do "TASK" [t/TAG...]`
List | `list [TAB_NAME]`
Find | `find KEYWORD [MORE_KEYWORDS]`
Find Tag | `findtag KEYWORD [MORE_KEYWORDS]`
Edit | `edit INDEX DETAILS`
Delete | `delete INDEX`
Undo | `undo`
Redo | `redo`
Mark | `mark INDEX`
Clear | `clear [TAB_NAME]`
Clear all | `clearall`
Relocate | `relocate FILE_PATH`
Exit | `exit`
