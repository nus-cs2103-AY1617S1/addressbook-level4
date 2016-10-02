# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `addressbook.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your To-do Manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**` event participate in competition from 5th Sep 12:30 to 17:30` : 
     adds an event with the description: "participate in competition" 
	 with starting datetime: 5th Sep 12:30 and ending datetime: 17:30 to the To-do Manager.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Words in UPPERCASE are the parameters.
> * Items in [square_brackets] are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`
 
#### Adding either of the 3 types of tasks: `add`
Adds an event to the To-do Manager<br>
Format: `add event DESCRIPTION from START_DATETIME to END_DATETIME [#tags]...`
Adds a deadline to the To-do Manager<br>
Format: `add deadline DESCRIPTION BY END_DATETIME [#tags]...` 
Adds a floating task to the To-do Manager<br>
Format: `add task DESCRIPTION [#tags]...` 

> Tasks can have any number of tags (including 0)

Examples: 
* `add event participate in competition from 5th Sep 12:30 to 17:30 #important`
* `add event meeting with boss from 30th Sep 9:00 to 10:00`
* `add task grocery list - banana #important`
* `add task watch this movie`
* `add deadline do this by 23th Sep`

#### Keywords that represents a certain time

> To be entered as START_DATETIME or END_DATETIME

keyword | keyword | ... || date / time

tmr | tomorrow | 2moro || Tomorrow's date
today                  || Today's date
morning                || 9:00
afternoon              || 12:00
evening                || 19:00
night                  || 21:00

#### Time and Date formats

> 3 letter versions of months are allowed

ISO Date	YYYY-DD-MM      "2015-03-25"
			DD-MM		    "03-25"
Short Date	YYYY/DD/MM      "03/25/2015" or "2015/03/25"
			DD/MM           "03/25"
Long Date	MONTH DATE YEAR "Mar 25 2015" or "25 Mar 2015"
			MONTH DATE      "Mar 25"
			DATE MONTH      "25 Mar"
			
> Ordinal indicators are optional

			DATE MONTH      "25th Mar"
			DATE MONTH      "21st Mar"

#### Finding all tasks containing any keyword in their description: `find`
Finds tasks whose description contain any of the given keywords.<br>
Format: `find KEYWORD...`

> * The search is case sensitive. e.g `hans` will not match `Hans`
> * The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
> * Only the description is searched.
> * Substrings will be matched e.g. `Han` will match `Hans`
> * Tasks matching all keywords will be returned (i.e. `AND` search).
    e.g. `Hans` will match `Hans Bo`

Examples: 
* `find John`<br>
  Returns `John Doe` but not `john`
* `find Shopping list`<br>
  Returns Any task containing both `Shopping`, `list`

#### Editing a task : `edit`
Edits the specified task in the To-do Manager. Reversible by undo command.<br>
Format: `edit INDEX [description] [from start_datetime] [#tags]...`

> Edits the task at the specified `INDEX`
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...
> Edits the fields specified
  See [Command Summary](#command-summary) for more examples of how to use the edit command
  
#### Deleting a task : `delete`
Deletes the specified task from the To-do Manager. Reversible by undo command.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `delete 2`<br>
  Deletes the 2nd task in the To-do Manager.
* `find shopping list`<br> 
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

#### Undo an action : `undo`
Undo the last action that modified any tasks.
Format: `undo`

#### Clearing all tasks : `clear`
Clears all tasks from the To-do Manager. Reversible by undo command.<br>
Format: `clear`  

#### Save Location : `Setstorage PATH`
To-do Manager data save location can be specified using the following command. Reversible by undo command.
Format: `setstorage PATH`

#### Exporting : `export PATH`
To-do Manager data are exported to the given path. Reversible by undo command.
Format: `export PATH`

#### Importing : `import PATH`
To-do Manager data are imported from the given path and replaces the current save file. Reversible by undo command.
> Only if the data is valid.
Format: `import PATH`

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`

#### Saving the data 
To-do Manager data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous To-do Manager folder, through the use
	   of import and export commands.
       
## Command Summary

Command | Format  
-------- | :-------- 
Add Event          | `add event DESCRIPTION from START_DATETIME to END_DATETIME [#tags]...`
Add Deadline       | `add deadline DESCRIPTION BY END_DATETIME [#tags]...`
Add Floating Task  | `add task DESCRIPTION [#tags]...`
Edit Event         | `edit INDEX [description] [from start_datetime] [#tags]...`
                   | `edit INDEX [description] [to end_datetime] [#tags]...`
				   | `edit INDEX [description] [from start_datetime] [to end_datetime] [#tags]...`
Edit Deadline      | `edit INDEX [description] [by end_datetime] [#tags]...`
Edit Floating Task | `edit INDEX [description] [#tags]...`
Clear              | `clear`
Delete             | `delete INDEX...`
Undo               | `undo`
Find               | `find KEYWORD...`
Help               | `help`
Export             | `export PATH`
Import             | `Import PATH`
Set Save Location  | `Setstorage PATH`