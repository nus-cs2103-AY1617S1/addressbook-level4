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
2. Copy the file to the folder you want to use as the home folder for your Scheduler.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : lists all items
   * **`add`**` `add Meeting st/ 19-02-12 13:00 et/19-02-12 14:00` : 
     adds an item named `Meeting` to the scheduler.
   * **`delete`**` 3` : deletes the 3rd item shown in the scheduler
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
 
#### Adding an item: `add`
Adds an event to the scheduler<br>
Format: `add NAME st/START_DATE_TIME et/END_DATE_TIME` 

Adds a task to the scheduler<br>
Format: `add NAME d/DATE` 

Adds a floating task to the scheduler<br>
Format: `add NAME` 

Examples: 
* `add Meeting st/20-03-12 13:00 et/20-03-12 14:00`
* `add Do Homework1 d/19-02-12`
* `add Do Homework2`

#### Listing all items: `list`
Shows a list of all items in the scheduler.<br>
Format: `list [by/SORT_CATEGORY]`
Examples: 
* `list`
* `list by/date`

#### Editing an item : `edit`
Edits an event to the scheduler specified by the index <br>
Format: `edit INDEX NAME st/START_DATE_TIME et/END_DATE_TIME` 

Edits a task to the scheduler specified by the index <br>
Format: `edit INDEX NAME d/DATE` 

Edits a floating task to the scheduler specified by the index <br>
Format: `edit INDEX NAME` 

Examples: 
* `edit 1 Meeting st/20-03-12 13:00 et/20-03-12 14:00`
* `edit 3 Do Homework1 d/19-02-12`
* `edit 2 Do Homework2`

#### Deleting an item: `delete`
Delete the item selected by the user.<br>
Format: `delete INDEX`

> * The user needs to view Scheduler in a list to find index of item

Examples: 
* `delete 23`

#### Finding all items containing any keyword in their name: `find`
Finds items where their item names contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is non-case sensitive. e.g `homework` will match `Homework`
> * Only the name is searched.
> * Only full words will be matched e.g. `CS` will not match `CS2103`
> * Persons matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Midterm` will match `Midterm Review`

Examples: 
* `find Tutorial`<br>
  Returns `CS2103 Tutorial` and words related to `tutorial`
* `find CS Assignment Errand`<br>
  Returns Any item having names `CS`, `Assignment`, or `Errand`

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data: `save`
Scheduler data are saved in the hard disk automatically after any command that changes the data. Data is saved at path previously assigned by the user.<br>
Format: `save FILE_SAVE`

#### Changing the file save path: `path`
File path can be manually changed to a user-specified location.<br>
Format: `path FILE_PATH`

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Scheduler folder.
       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `add NAME st/START_DATE_TIME et/END_DATE_TIME`
Add | `add NAME d/DATE`  
Add | `add NAME`
Delete | `delete INDEX`
Find | `find KEYWORD [MORE_KEYWORDS]`
Edit | `edit INDEX NAME st/START_DATE_TIME et/END_DATE_TIME`
Edit | `edit INDEX NAME d/DATE` 
Edit | `edit INDEX NAME` 
List | `list [by/SORT_CATEGORY]`
Path | `path FILE_PATH`
Help | `help`
