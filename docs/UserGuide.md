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
Format: `add TITLE [p]d/DETAILS [p]s/STARTTIME [p]e/ENDTIME [p]t/tag...`

> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional,
> items with `...` after them can have multiple instances. Order of parameters are fixed.
>
> Put a `p` before the details / group / tags prefixes to mark it as `private`. `private` details can only
> be seen using the `unveil` command.
>
> Tasks can have any number of tags (including 0)

Examples:
* `add CS1020 Tutorial d/many questions e/051016 1200  t/needhelp`
* `add CS2103 Project pp/hard to do s/051016 1200 e/051116 1200  t/priority1`

## Listing all tasks : `list`
Shows a list of all tasks in task list.<br>
Format: `list`

## Finding all tasks containing any keyword in their name: `find`
Finds tasks whose names contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> The search is case insensitive, the order of the keywords does not matter, only the name is searched,
and tasks matching at least one keyword will be returned (i.e. `OR` search).

Examples:
* `find cs2103`<br>
  Returns `CS2103` and `cs2103`
* `find cs1010 cs1020 cs2103`<br>
  Returns Any tasks having names `cs1010`, `cs1020`, or `cs2103`
* `find <group>/<tags>/<dates>’<br>
  Returns Any tasks having the same group/tags/dates.

##Set file location: ‘locate’
Set the storage file location.
Format: ‘locate [NEW FILE DIRECTORY]

Example:
* `locate Desktop/`<br>

##Change command name: ‘change’
Change the command to user’s preference
Format : change [COMMAND WORD] [USER PREFERENCE COMMAND WORD]

Example:
* `change delete d`<br>

##set username: ‘username’
Set the username to indicate the application is belong to which user
Format: username USERS NAME

Example:
* `username Jim`<br>

##Edit a task: ‘edit’
Edit the task’s information from the task list
Format: ‘edit INDEX’

>Edit the task at the specified `INDEX`.
  The index refers to the index number shown in the most recent listing.

## Deleting a task : `delete`
Deletes the specified task from the task list. Reversible with undo.<br>
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

## View non-private details of a tasks : `view`
Displays the non-private details of the specified task.<br>
Format: `view INDEX`

> Views the task at the specified `INDEX`.
  The index refers to the index number shown in the most recent listing.

Examples:
* `list`<br>
  `view 2`<br>
  Views the 2nd task in the task list.
* `find cs2103` <br>
  `view 1`<br>
  Views the 1st task in the results of the `find` command.

## Unveil details of a task : `unveil`
Displays all details (including private details) of the specified task.<br>
Format: `unveil INDEX`

> Unveils all details of the task at the specified `INDEX`.
  The index refers to the index number shown in the most recent listing.

Examples:
* `list`<br>
  `unveil 2`<br>
  Views all details of the 2nd task in the task list.
* `find cs2103`<br>
  `unveil 1`<br>
  Views all details of the 1st task in the results of the `find` command.

## Clearing all entries : `clear`
Clears all task from the task list.<br>
Format: `clear`  

## Undo previous command : `undo`
Undo the last command from the task list.<br>
Format: `undo`

## Mark task as done : `done`
Mark the task as done at the specified ‘INDEX’.<br>
Format: `done INDEX`


## Show number of unfinished tasks in the list : ‘show’
To show the number of unfinished tasks according to today and the future 7 days/all
Format: ‘show KEYWORD’’

Example:
* `show today’
return  the number of uncomplete tasks today
* `show 7days’’
return  the number of uncomplete tasks for the next 7 days
* `show all’
return  the number of all the uncomplete tasks

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
