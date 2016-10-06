# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `addressbook.jar` from the 'releases' tab.
2. Copy the file to the folder you want to use as the home folder for your MustDoList.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : lists all task
   * **`add`**` Submission of CS2103 Project d/11 m/11 y/16 t/0900hrs : 
     adds a task named `Submission of CS2103 Project` to the MustDoList.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`
 
#### Adding a task: `add`
Adds a task to the MustDoList.<br>
Format: `add TASK_NAME d/DAY m/MONTH y/YEAR t/TIME` 

Examples: 
* `add Submission of CS2103 Project d/11 m/11 y/16 t/0900hrs`

#### Listing all persons : `list`
Shows a list of all tasks in the MustDoList.<br>
Format: `list`

#### Finding all task containing any keyword in their name: `find`
Finds persons whose task_names contain any of the given keywords.<br>
Format: `find KEYWORD`

> The search is case sensitive, the order of the keywords does not matter, only the task_name is searched, 
and task matching at least one keyword will be returned (i.e. `OR` search).

Examples: 
* `find CS2103`<br>
  Returns `Submission of CS2103 Project` but not `CS2103`
* `find Submission of CS2103 Project`<br>
  Returns Any task having names `Submission `, `CS2103 `, or `Project`

#### Deleting a task: `delete`
Deletes the specified task from the MustDoList. Irreversible.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the MustDoThis.
* `find Submission`<br> 
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

#### Select a task : `select`
Selects the task identified by the index number used in the last task listing.<br>
Format: `select INDEX`

> Selects the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `select 2`<br>
  Selects the 2nd task in the MustDoList.
* `find Submission` <br> 
  `select 1`<br>
  Selects the 1st task in the results of the `find` command.

#### Clearing all entries : `clear`
Clears all entries from the MustDoThis.<br>
Format: `clear`  

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data 
MustDoList data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous MustDoList.
       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `add TASK_NAME d/DAY m/MONTH y/YEAR t/TIME`
Clear | `clear`
Delete | `delete INDEX`
Find | `find KEYWORD`
List | `list`
Help | `help`
Select | `select INDEX`
