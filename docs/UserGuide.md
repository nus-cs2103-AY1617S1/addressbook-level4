# User Guide
Are you feeling stressed or overwhelmed with the number of things you have to do? When you are drowning in the pool of to-dos, even mundane tasks like buying milk may bring tears to your eyes. Well you can hold back those precious tears because WhatNow will help you to manage all your tasks. Now that you know WhatNow is [about](../README.md), you can follow this guide to learn how to use WhatNow effectively. Welcome to WhatNow! 

# Table of Contents
* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `WhatNow.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your WhatNow.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** then press <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : lists all tasks in WhatNow
   * **`add`**` Buy groceries start 6pm` : adds a task called `Buy groceries` to WhatNow.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits WhatNow
6. Refer to the [Features](#features) section below for details of each command.<br>

## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.
 
#### Adding a task: `add`
Adds a task to WhatNow
Format: `add "TASK_NAME" [on] DATE [from/by] [START_TIME] [to] [END_TIME] [every] [PERIOD] [low/medium/high]` 

Examples:
* `add "Do CS2103T tutorial" on 4 Oct 2016 from 10am to 11am every week`
* `add "Watch Storks movie" on 10/10 from 1pm to 3pm` 
* `add "CS2103 Project" on 20/11/2016 high`
* `add "Buy milk and eggs!"`
>Tasks without a deadline specified will be added to Veto as a floating task and will be<br> displayed under the heading "Floating Tasks".


#### Clearing all entries : `clear`
Clears all entries from WhatNow.<br>
Format: `clear`  

#### Deleting a task : `delete`
Deletes the unwanted task from the Now What. Reversible.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in Now What.
* `find Read`<br> 
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Finding tasks containing any keyword in their name: `find`
Finds tasks whose names contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case sensitive. e.g `Read` will not match `read`
> * The order of the keywords does not matter. e.g. `Read books` will match `books Read`
> * Only the name is searched.
> * Only full words will be matched e.g. `Book` will not match `Books`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Read` will match `Read books`

Examples: 
* `find Read`<br>
  Returns `Read books` but not `read`
* `find Read books lecture notes`<br>
  Returns Any tasks having names `Read`, `books`, `lecture` or `notes`.

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

#### Listing all persons : `list`
Shows a list of all tasks in WhatNow.<br>
Format: `list [TYPE]`

Examples: 
* `list events`
* `list`

#### Redoing the previous action : `redo`
Redo the previous action<br>
Format: `redo`  

Examples: 
* `redo`<br>
   Redo the previous action

#### Select a task : `select`
Selects the task identified by the index number used in the last task listing.<br>
Format: `select INDEX`

> Selects the task and loads the Google search page the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `select 2`<br>
  Selects the 2nd task in the address book.
* `find Books` <br> 
  `select 1`<br>
  Selects the 1st task in the results of the `find` command.

#### Undoing the previous action : `undo`
Undo the previous action<br>
Format: `undo`  

Examples: 
* `undo`<br>
   Undo the previous action

#### Update a task : `update`
Updates a task from the list displayed<br>
Format: `update INDEX [name/date/startTime/endTime/status/priority]  NEW_VALUE`

> Updates the name/date/startTime/endTime/status/priority of the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `update 3 name Avengers`<br>
   Selects the 3rd task from the displayed list and changes the task name to ‘Avengers’
* `update 5 startTime 10am endTime 12pm`<br>
   Selects the 5th task from the displayed list and changes the start time to 10am and end time to 12pm.

#### Saving the data 
WhatNow data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous WhatNow folder.
       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `add "TASK_NAME" [on] DATE [from/by] [START_TIME] [to] [END_TIME] [every] [PERIOD] [low/medium/high]`
Clear | `clear`
Delete | `delete INDEX`
Exit | `exit`
Find | `find KEYWORD [MORE_KEYWORDS]`
Help | `help`
List | `list`
Redo | `redo`
Select | `select INDEX`
Undo | `undo`
Update | `update INDEX [name/date/startTime/endTime/status/priority]  NEW_VALUE`
