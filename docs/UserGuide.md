# User Guide

This product is not meant for end-users and therefore there is no user-friendly installer.
Please refer to the [Setting up](DeveloperGuide.md#setting-up) section to learn how to set up the project.

## Starting the program

1. Find the project in the `Project Explorer` or `Package Explorer` (usually located at the left side)
2. Right click on the project
3. Click `Run As` > `Java Application` and choose the `Main` class.
4. The GUI should appear in a few seconds.

<img src="images/Ui.png">

## Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

## Adding a task: `add`
Adds a task to the task list<br>
Format: `add TITLE [p][d/DETAILS] [p][s/STARTTIME] [p][e/ENDTIME] [p][t/TAG]...`

> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional,
> items with `...` after them can have multiple instances. Order of parameters are fixed.
>
> Put a `p` before the details / group / labels prefixes to mark it as `private`. `private` details can only
> be seen using the `unveil` command.
>
> DateTime format is in `DDMMYY HHMM`
>
> Tasks can have any number of tags (including 0)

Examples:
* `add CS1020 Tutorial d/many questions e/051016 1200  t/needhelp`
* `add CS2103 Project pd/hard to do s/051016 1200 e/051116 1200  t/priority1`

## Listing all tasks : `list`
Shows a list of all the task <br>
Format: `list [KEYWORD]`

Example:
* `list’
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

##Edit a task: `edit`
Edit the task’s information from the task list <br>
Format: ‘edit INDEX [TITLE] [p][d/DETAILS] [p][s/STARTTIME] [p][e/ENDTIME]’

>Edit the task at the specified `INDEX`.  
  The index refers to the index number shown in the most recent listing.

Examples:
* `edit 1 d/new updates`<br>
  Edits the 1st task in the task list.
* `edit 2 d/new updates e/051016 1200`<br>
  Edits the 1st task in the task list.

## Deleting a task : `delete`
Deletes the specified task from the task list. Reversible with undo. <br>
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

## Clearing all entries : `clear`
Clears all task from the task list.<br>
Format: `clear`  

## Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

## Saving the data
Task List ‘s data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## Changing the save location
Task list data are saved in a file called `tasklist.txt` in the project root folder.
You can change the location by specifying the file path as a program argument.<br>

> The file name must end in `.txt` for it to be acceptable to the program.
>
> When running the program inside Eclipse, you can
  [set command line parameters before running the program](http://stackoverflow.com/questions/7574543/how-to-pass-console-arguments-to-application-in-eclipse).
