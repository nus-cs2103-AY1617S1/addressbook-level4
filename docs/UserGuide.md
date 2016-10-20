# User Guide

This product is not meant for end-users and therefore there is no user-friendly installer.
Please refer to the [Setting up](DeveloperGuide.md#setting-up) section to learn how to set up the project.

## Starting the program

0. Ensure that you have installed Java version `1.8.0_60` or later on your computer.<br>
> This program will not work with earlier versions of java including java 8.

1. Download the latest version of `Task.List.jar` from the releases tab.

2. Copy the file to your desired folder.

3. Double click the file to start the app. The app should run and GUI should appear shortly.<br>
<img src="images/Ui.png">

4. Type the command in the command box at the top and press <kbd>Enter</kbd> to execute the command.<br>
Type `help` followed by pressing <kbd>Enter</kbd> will open the help window.

5. Some example commands you can try:
   * **`add`** `CS2103 d/tutorial e/20102016` :
     adds a task named `CS2103` with a description of `tutorial` with a deadline of `20/10/16`.
   * **`delete`**` 1` : deletes the first item on the task list.
   * **`exit`** : exits the app

6. Refer to the [Features](#features) section below for details of each command.<br>





## Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

## Adding a task: `add`
Adds a task to the task list<br>
Format: `add TITLE [d/DETAILS] [s/STARTTIME] [e/ENDTIME] [t/TAG]...`

> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional,
> items with `...` after them can have multiple instances. Order of parameters are fixed.
>
> DateTime format is in `DDMMYY HHMM`
>
> Tasks can have any number of tags (including 0)

Examples:
* `add CS1020 Tutorial d/many questions e/051016 1200  t/needhelp` (task)
* `add Meeting d/for project s/051016 1200 e/051016 1400  t/priority1` (event)
* `add CS1010 Take home lab d/hard to do s/051016 1200`
* `add CS2103 Project d/hard to do`
* `add CS1231 Mid-Term Test`

## Listing all tasks : `list`
Shows all tasks <br>
Format: `list`

Example:
* `list’<br>
list all task

## Finding all tasks containing any keyword in their title/description/datetime/tag: `find`
Finds tasks whose title/description/datetime/tag contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> The search is case insensitive, the order of the keywords does not matter, only the title/description/datetime/tag is searched,
and tasks matching at least one keyword will be returned (i.e. `OR` search).

Examples:
* `find cs2103`<br>
  Display tasks containing `CS2103` and `cs2103`
* `find cs1010 cs1020 cs2103`<br>
  Display tasks containing `cs1010`, `cs1020`, or `cs2103`
* `find lab`<br>
  Display tasks containing `lab`.

## Set storage file location: `storage`
Set the storage file location. <br>
Format: storage FILEPATH

Example:
* `storage C:\Users\` <br>

## Changing the storage file location
Task list data are saved in a file called `tasklist.xml` in the project root folder.
You can change the location by specifying the file path as a program argument. <br>

> The file name must end in `.xml` for it to be acceptable to the program.

## Edit a task: `edit`
Edit the task’s information from the task list <br>
Format: ‘edit INDEX [TITLE] [d/DETAILS] [s/STARTTIME] [e/ENDTIME]’

>Edit the task at the specified `INDEX`.  
  The index refers to the index number shown in the most recent listing.

Examples:
* `edit 1 d/new updates`<br>
  Edits the 1st task in the task list.
* `edit 2 d/new updates e/051016 1200`<br>
  Edits the 1st task in the task list.

## Deleting a task : `delete`
Deletes the specified task from the task list. <br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`.
  The index refers to the index number shown in the most recent listing.

Examples:
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the task list.
* `find cs2103`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

#### Select a task : `select`
Selects the task identified by the index number used in the last task listing.<br>
Format: `select INDEX`

> Selects the task and loads the Google search page the task at the specified `INDEX`.
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples:
* `list`<br>
  `select 2`<br>
  Selects the 2nd task in the task list.
* `find CS2103` <br>
  `select 1`<br>
  Selects the 1st task in the results of the `find` command.

## Mark task : `mark`
Mark the task as completed at the specified ‘INDEX’.<br>
Format: `mark INDEX`

Examples:
* `list`<br>
  `mark 2`<br>
  Marks the 2nd task in the task list.
* `find CS2103` <br>
  `mark 1`<br>
  Marks the 1st task in the results of the `find` command.

## Unmark task : `unmark`
Unmark the task as not completed at the specified ‘INDEX’.<br>
Format: `unmark INDEX`

Examples:
* `list`<br>
  `unmark 2`<br>
  Unmarks the 2nd task in the task list.
* `find CS2103` <br>
  `unmark 1`<br>
  Unmarks the 1st task in the results of the `find` command.

## Clearing all entries : `clear`
Clears all task from the task list.<br>
Format: `clear`  

## Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

## Saving the data
Task List ‘s data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.
