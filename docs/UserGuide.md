# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `happyjimtaskmanager.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Address Book.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/Ui.JPG" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`view`** : View floating task of the day
   * **`add`**` Homework d/2409 1800 : 
     adds a task named `Homework` to the Task List.
   * **`delete`**` 212` : deletes the task with ID 212 shown in the current list
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

Example:
* `help` 
 
#### Adding a floating task: `add`
Adds a task to the todo list<br>
Format:`add TASK_NAME [t/TAG]...` 

> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional, 
> items with `...` after them can have multiple instances. Order of parameters are fixed. 
>  
> Tasks can have any number of tags (including 0)

Examples: <br>
* `add Homework`<br>
* `add Homework t/CS1231`

#### Adding a task with deadline: “add”
Format: `add TASK_NAME d/DATE TIME [t/TAG]...`

> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional, 
> items with `...` after them can have multiple instances. Order of parameters are fixed. 
>  
> Tasks can have any number of tags (including 0)

Examples:
* `add Homework d/2409 1800 t/CS1231`

#### Adding a task with start time and end time: “add”
Format: `add TASK_NAME d/DATE TIME - DATE TIME [t/TAG]`

> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional, 
> items with `...` after them can have multiple instances. Order of parameters are fixed. 
>  
> Tasks can have any number of tags (including 0)

Examples:
* `add Homework d/2409 2100 - 2509 1900 t/CS1231`

#### View floating task of the day : “view”
Format: view -OPTION

> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional, 
> OPTION currently supports f : floating

Examples: 
* `view -f`

#### View non-floating task of the day : “view”
Format: view d/DATE

> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional,

Examples: 
* `view d/2409`

#### Edit tasks : “edit”
Format: `edit TASK_ID [d/EDIT_START_DATE EDIT_START_TIME - EDIT_END_DATE EDIT_END_TIME] [t/EDIT_TAG]...`

> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional, 
> items with `...` after them can have multiple instances. Order of parameters are fixed. 
>  
> Tasks can have any number of tags (including 0)

Examples: 
* `edit 213 d/2709 1800 - 3009 1800  t/cs2101`

#### Delete tasks : “delete”
Format: delete TASK_ID

> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional, 

Examples:
* `Delete 212`

#### Block out tasks : “block”
Format: block TASK_NAME d/START_DATE START_TIME - START_DATE START_TIME

> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional, 

Examples:
block cs2103t d/2409 1900 - 2409 2100

#### Redo tasks : “redo”
Format: redo

> Maximum 3 redo

Examples: 
* `redo`

#### Undo tasks : “undo”
Format: undo

> Maximum 3 undo

Examples: 
* `undo`

#### Find tasks : “find”
Format: find [TASK_NAME] [d/DATE [TIME]] [t/TAG]

> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional,

Examples: <br>
* `find cs2103 d/2109 <br>`
* `find d/2109 1800 t/gigi <br>`
* `find cs2103 t/lolo`

#### Undo tasks : “clear”
Format: clear

> clears all the tasks

Examples: 
* `clear`

#### Change directory: “Change directory”
Format: cd FILE_PATH

> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional,

Examples: 
* `cd C://user/saveFolder`


#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Address Book folder.

**Q**: How do i get started using the task manager?<br>
**A**: Type 'help' or any incorrect command will bring you to the help screen.
       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `add TASK_NAME [t/TAG]`
Add | `add TASK_NAME d/DATE TIME [t/TAG]`
Add | `add TASK_NAME d/DATE TIME - DATE TIME [t/TAG]`
View | `view -OPTION`
View | `view d/DATE`
Edit | `edit TASK_ID [d/EDIT_START_DATE EDIT_START_TIME - EDIT_END_DATE EDIT_END_TIME] [t/EDIT_TAG]`
Delete | `delete TASK_ID`
Block | 'block TASK_NAME d/START_DATE START_TIME - START_DATE START_TIME'
Redo | 'redo'
Undo | 'undo'
Find | 'find [TASK_NAME] [d/DATE [TIME]] [t/TAG]'
Clear | 'clear'
Change directory | 'cd FILE_PATH'
Exit | 'exit'