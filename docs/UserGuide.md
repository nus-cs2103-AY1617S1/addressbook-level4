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
2. Copy the file to the folder you want to use as the home folder for your Address Book.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/Ui.jpg" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**` add Visit Dentist d/18-11-2016 l/Mount Elizabeth` : 
     adds a task named `Visit Dentist` to the Address Book.
   * **`delete`**` 3` : deletes the 3rd contact shown in the current list
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
 
#### Adding a person: `add`
Adds a task to the task list<br>
Format: `add NAME before/by/due duedate at location [t/TAG]...` 

> Persons can have any number of tags (including 0)

Examples: 
* `add Visit Dentist before 18-11-2016 at Mount Elizabeth'

#### Listing all tasks : `list`
Shows a list of all tasks in the task list.<br>
Format: `list`

#### Finding all tasks containing any keyword in their name: `find`
Finds tasks whose descriptions contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case sensitive. e.g `hans` will not match `Hans`
> * The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
> * Only the name is searched.
> * Only full words will be matched e.g. `Han` will not match `Hans`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Hans` will match `Hans Bo`

Examples: 
* `find Visit Dentist`<br>
  Returns `John Doe` but not `john`
* `find Homework Dentistry`<br>
  Returns Any person having names `Homework`, `Dentistry` or `Dentist`

#### Deleting a task : `delete`
Deletes the specified task from the address book. Irreversible.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd person in the address book.
* `find Betsy`<br> 
  `delete 1`<br>
  Deletes the 1st person in the results of the `find` command.

#### Select a person : `select`
Selects the person identified by the index number used in the last person listing.<br>
Format: `select INDEX`

> Selects the person and loads the Google Maps for location of the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `select 2`<br>
  Selects the 2nd person in the address book.
* `find Betsy` <br> 
  `select 1`<br>
  Selects the 1st person in the results of the `find` command.

#### Clearing all entries : `clear`
Clears all entries from the task list.<br>
Format: `clear` 

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data 
Task List data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Tasks folder.
       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `add NAME [m/description d/duedate l/location p/priorityrank(1- highest,5-lowest) s/startdate e/enddate t/TAG]...`
Edit | `edit INDEX [Detail Type] x/detail change` where x is either d, l, p, s, e or t
Clear | `clear`
Delete | `delete INDEX`
Find | `find FILTER KEYWORD` FILTER options: tags, title, date
List | `list`
Help | `help`
Select | `select INDEX WIDGET` WIDGET options: location, search, description, calendar
Exit | `exit`
