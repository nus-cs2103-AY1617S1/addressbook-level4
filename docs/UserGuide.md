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
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : lists all contacts
   * **`add`**` John Doe p/98765432 e/johnd@gmail.com a/John street, block 123, #01-01` : 
     adds a contact named `John Doe` to the Address Book.
   * **`delete`**` 3` : deletes the 3rd contact shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.
=======

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

#### Create a new task: `todo`, `deadline` or `event`
Adds a new task to the tasks list or a new event to the event calendar.<br>
Todo format: `todo NAME`<br>
Deadline format: `deadline NAME DATE END_TIME`<br>
Event format: `event NAME DATE START_TIME END_TIME`

> Words in UPPER_CASE are the parameters, items in SQUARE_BRACKETS are optional. Order of parameters are not fixed.

> Tasks are split into 3 categories: todo, deadline, event.
> todo: Tasks that have no specific date/time to be completed by.
> deadline: Tasks that have a specific date/time they must be completed by.
> event: Tasks that have specific start and end date/time.

> DATE can accept different formats. 1 Jan 2015, 010115, 01/01/2015 are all acceptable to represent 1 Jan 2015.

> START_TIME and END_TIME can accept different formats. 3pm, 15:00, 1500 are all acceptable to represent 3pm.

Examples:
* `todo read book`<br>
  Adds a todo task with NAME as `read book`
* `deadline math homework 1 Jan 2015 2pm`<br>
  Adds a deadline task with NAME as `math homework`, DATE as `1 Jan 2015`, END_TIME as `1500`
* `event meeting 1 Jan 2015 21:00 00:00`<br>
  Adds an event task with NAME as `meeting`, DATE as `1 Jan 2015`, START_TIME as `2100`, END_TIME as `0000`

#### View all tasks: `view`
View all tasks for the specified date.<br>
Format: `view [DATE]`

> All tasks for the specified DATE will be displayed. If no date is specified, all tasks for today will be displayed.

Examples: 
* `view`
* `view 1 Jan 2015`


#### Edit task details: `edit`
Edit a task or event already inside the task manager/ event calendar using the index of the task.<br>
Format: `edit INDEX [NEW_NAME] [NEW_DATE] [NEW_START_TIME] [NEW_END_TIME]`

> Words in UPPER_CASE are the parameters, items in SQUARE_BRACKETS are optional. Order of parameters are not fixed.

> Format depends on the type of task being edited. When only 1 TIME is provided, it is treated as END_TIME for both deadline and event.

Examples:
* `view`<br>
  `edit 3 Do math homework 3pm`<br>
  Edit the 3rd task today. Changes the NAME to `Do math homework` and END_TIME to `1500`<br>
  `edit 2 22:00 00:00`<br>
  Edit the 2nd task today. Changes the START_TIME to `2200` and END_TIME to `0000`

#### Delete task: `delete`
Delete a task inside the task list or an event inside the calendar.<br>
Format: `delete INDEX`

> Delete a task at the specified INDEX. The INDEX refers to the index number shown in the most recent listing

Examples:
* `view`<br>
  `delete 1`<br>
  Delete the 1st task today as shown by the `view` command
* `view 1 Jan 2015`<br>
  `delete 2`<br>
  Delete the 2nd task on 1 Jan 2015 as shown by the `view` command

#### Mark task as done: `done`
Mark a task in the task list as done.<br>
Format: `done INDEX`

> Marks a task at the specified INDEX as completed. The INDEX refers to the index number shown in the most recent listing

Examples:
* `view`<br>
  `done 1`<br>
	Marks the 1st task today as shown by the `view` command as completed

#### Undo previous action: `undo`
Undo the last completed action.<br>
Format: `undo`

> The previous version will be restored.
> User can keep retyping undo to undo multiple actions.

Examples:
*  `undo`


#### Clearing all entries : `clear`
Clears all entries from the address book.<br>
Format: `clear`  

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data 
Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Address Book folder.
       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `add NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]...`
Clear | `clear`
Delete | `delete INDEX`
Find | `find KEYWORD [MORE_KEYWORDS]`
List | `list`
Help | `help`
Select | `select INDEX`
