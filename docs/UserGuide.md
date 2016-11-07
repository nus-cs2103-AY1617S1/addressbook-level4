<!-- @@author A0093907W -->

# User Guide

Please refer to the [Setting up](DeveloperGuide.md#setting-up) section to learn how to set up the project.

<img src="images/GetShitDone-Ui.png" width="600">

## Contents

* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of optional parameters are flexible.

#### Viewing help : `help`

Shows a list of all commands in GetShitDone.

Format: `help`

Examples:

* `help`  
  Shows all available commands and examples 
 
#### Adding a task: `add` or `add task`

Adds a task to GetShitDone

Format: `add [task] NAME [(by|on|at|before|time) DEADLINE] `

> * Tasks can have a deadline, or can do without one as well.
>   * Tasks added without specifying a deadline will be displayed under "No Deadline".
>   * Date formats can be flexible. The application is able to parse commonly-used human-readable date formats.
>     * e.g. `1 Oct`, `Monday`, `next wed`, `tomorrow`, `5 days ago`, etc.
> * Dates can include time as well.
>   * If only time is specified, it will default to today's date.
>   * If time is not specified, it will default to the current time of the particular date.
>   * Time formats are flexible as well. The application supports 24 hour format and AM/PM format.
>     * e.g. `Monday 3pm`, `today 1930`, `5:30pm`, `10.00 am`
> * Tasks can have any number of tags up to 20. (including 0).
> * Using the `add` command without specifying `task` will interpret the command as `add task`.

Examples: 

 * `add CS2103 Project`
 * `add CS2103 V0.3 by next Friday`
 * `add task Buy milk by tmr`
 
#### Adding an event: `add event` 

Adds an event to GetShitDone  

Format: `add event NAME from STARTDATETIME to ENDDATETIME`

> * Events must have both start and end date/time specified.
>   * If there is no start or end date, you have to rectify your command, since it wasn't clear what should be added.
>   * If only time is given, the date is interpreted as today's date.
>   * If only date is given, the time is interpreted as the time now.

Examples: 

 * `add event Orientation Camp from Monday 8am to Friday 9pm`
 * `add event CS2103 Workshop from Sat 10am to 4pm`

#### Listing all tasks and events : `list`

Shows a list of all tasks and events in GetShitDone. Able to filter by type of task (task/event), or based on status of task/event.

Format: `list [TYPE]`

> Valid parameters: 
> * Item type: `events` / `event`/ `tasks` / `task`
> * Task status: `complete` / `completed` / `incomplete` / `incompleted` 
> * Event status: `over` / `past` / `future` 
> * Task deadline: `(by|on|at|before) DATE`
> * Event date: `from STARTDATE to ENDDATE`
> * Tag: `tag TAGNAME`
> 
> The command accepts any combination of the above, with the exception of:
> * Task status cannot be defined for events
> * Event status cannot be defined for tasks
> In the event of such ambiguity, the command will display an error for the user to rectify it.

Examples:

* `list`  
 Lists all tasks and events.
  
* `list events`  
 Lists all events.

* `list completed tasks`  
 Lists all completed tasks

* `list by today`  
 Lists all tasks whose deadline are today or before, and events which end before today

* `list from monday to friday`  
 Lists all tasks due within the coming Monday to Friday, and events which start after the coming Monday and end before Friday

#### Finding all tasks/events containing any keyword in their name & tag: `find`

Finds tasks whose name contains any of the given keywords.  

Format: `find KEYWORD [MORE_KEYWORDS]...`

> The search is not case sensitive, the order of the keywords does not matter, only the item name is searched, and tasks/events matching at least one keyword will be returned (i.e. `OR` search).
> Searching follows wildcard search, i.e. a search term of `pr` will return both `Print notes` and `Make PR to GitHub`.

Examples: 

* `find assignment`  
Returns tasks and events which contain words starting with `assignment`.

#### Editing a task : `update`

Edits the specified task from GetShitDone.

Format: `update INDEX [name NAME] [( (by|on|at|before) DATE] | from STARTDATE to ENDDATE )]` 

> Edits the task at the specified `INDEX`. The index refers to the index number shown in the most recent listing.

Examples: 

* `update 2 name Presentation`  
  Update the 1<sup>st</sup> task's/event's name to CS2107 Project.

* `update 1 name CS2107 Project by saturday`  
  Update the 1<sup>st</sup> task's name to CS2107 Project.
  Change the task's deadline to Saturday.

#### Deleting a task : `destroy`

Deletes the specified task from GetShitDone.

Format: `destroy INDEX`

> Deletes the task at the specified `INDEX`.  
  The index refers to the index number shown in the most recent listing.

Examples: 

* `destroy 3`  
  Deletes the 3<sup>rd</sup> task/event in GetShitDone.

* `find assignment2`  
  `destroy 1`  
  Deletes the 1<sup>st</sup> task/event in the results of the `find` command.

<!--@@author A0139922Y -->

#### Clearing the Database : `clear`

Clear tasks/events by specific instruction from GetShitDone.

Format: `clear [event/task] ([(by|on|at) DATE] | [from STARTDATE to ENDDATE])`

Examples: 

* `clear task`  
  Clear all  tasks in GetShitDone.

* `clear event to yesterday`  
  Clear all events up to yesterday [inclusive].

#### Tagging an item : `tag`

Adds a tag to the task.

Format: `tag INDEX TAG_NAME`

> Adds the tag for the task at the specified `INDEX`.  
  The index refers to the index number shown in the most recent listing.

#### Untagging an item : `untag`

Removes the specified tag of the task. 

Format: `untag INDEX TAG_NAME`

> Removes the tag for the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.

Examples: 

* `untag 2 CS2103`  
  Untag the 2<sup>nd</sup> task/event of the tag name `CS2103` in GetShitDone.

* `untag 1 CS2103`  
  Untag the 1<sup>st</sup> task/event of the tag name `CS2103` in GetShitDone.

#### Completing a task : `complete`

Completes the specified task from GetShitDone.

Format: `complete INDEX`

> completes the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.

Examples: 

* `complete 2`  
  Completes the 2<sup>nd</sup> task/event in GetShitDone.

* `complete 1`  
  Completes the 1<sup>st</sup> task/event in GetShitDone.

#### Uncompleting a task : `uncomplete`

Uncompletes the specified task from GetShitDone.

Format: `uncomplete INDEX`

> uncompletes the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.

Examples: 

* `uncomplete 2`  
  Uncomplete the 2<sup>nd</sup> task in GetShitDone.

* `uncomplete 1`  
  Uncomplete the 1<sup>st</sup> task in GetShitDone.

<!-- @@author A0139812A -->

#### Aliasing: `alias`

Adds aliases for existing commands. *For advanced users.*  

Format: `alias [NEW_ALIAS EXISTING_COMMAND]`

Examples:
* `alias`  
  Lists all current aliases.

* `alias ls list`  
  `ls`
  Aliases `find` to `f`, and subsequently `ls` will list all tasks and events.

* `alias f find`  
  `f Irvin`
  Aliases `find` to `f`, and subsequently `f` can be used to `find` tasks and events.

#### Unaliasing: `unalias`

Removes existing aliases. *For advanced users.*  

Format: `unalias ALIAS`

Examples:
* `unalias f`  
  Removes the alias for `f`.

#### Undo tasks : `undo`

Undo commands in the application.  

Format: `undo [COUNT]`

> Performs undo repeatedly based on the specified `COUNT`. If `COUNT` is not specified, it defaults to 1.
  
Examples: 

* `undo`  
  Performs undo.
  
* `undo 2`  
  Performs undo twice.

#### Redo tasks : `redo`

Redo commands in GetShitDone.  

Format: `redo [COUNT]`

> Performs redos based on the specified `COUNT`. If `COUNT` is not specified, it defaults to 1.
  
Examples: 

* `redo`  
  Performs redo.
  
* `redo 2`  
  Performs redo twice.

#### Changing the app title : `config appTitle`

Format: `config appTitle FILEPATH`

Examples:

* `config appTitle Jim's Todo List`  
Changes the app title to `Jim's Todo List`.

#### Changing the save location : `config databaseFilePath`

The application data is saved in a file called `database.json`, which is saved in the same directory as the application by default.

Format: `config databaseFilePath FILEPATH`

> The file name of the database file must end in `.json`.

Examples:

* `config databaseFilePath movedDatabase.json`  
  Moves the existing database file to `movedDatabase.json`.

* `config databaseFilePath /absolute/path/to/database.json`  
Moves the existing database file to `/absolute/path/to/database.json`.

#### Exiting the program : `exit`

Exits the program.

Format: `exit`  

#### Saving of data
The application data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.


## FAQ

**Q**: How do I transfer my data to another computer?  
**A**: Install the app in the other computer, and replace `database.json` from the root of the application directory.

## Command Summary

**Standard Actions** 

Command | Format  
-------- | :-------- 
Add | `add NAME [s/START_DATE [e/END_DATE]] [d/DEADLINE] [t/TAG]...`
Complete | `complete INDEX`
Uncomplete | `uncomplete INDEX`
Help | `help`

**Viewing** 

Command | Format  
-------- | :-------- 
List | `list [TYPE]`
Find | `find KEYWORD [MORE_KEYWORDS]...`

**Editing** 

Command | Format  
-------- | :-------- 
Update | `update INDEX [s/START_DATE] [e/END_DATE] [d/DEADLINE]`
Delete | `destroy INDEX`
Add Tag | `tag INDEX TAG_NAME`
Untag | `untag INDEX TAG_NAME`
Undo | `undo [COUNT]`
Redo | `redo [COUNT]`

**App Actions** 

Command | Format  
-------- | :-------- 
Change App Title | `config appTitle APPTITLE`
Change Database File Path | `config databaseFilePath FILEPATH`

**Advanced Actions** 

Command | Format  
-------- | :-------- 
Add alias | `alias`
Remove alias | `unalias`
