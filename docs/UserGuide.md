# User Guide
* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.

   > Agendum will not work with earlier versions of Java 8.

1. Download the latest `Agendum.jar` from the [releases](../../../releases) tab.

2. Copy the file to the folder you want to use as the home folder for Agendum.

3. Double-click the file to start Agendum. The GUI should appear in a few seconds.

4. Type a command in the command box and press <kbd>Enter</kbd> to execute it.

   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will list some information about commands.
   
5. Here are some commands you can try:
   * **`add`**` Go to shopping mall` : adds a task with description `Go to shopping mall` to Agendum.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`list`** : lists all tasks
   * **`exit`** : exits Agendum

6. Refer to the [Features](#features) section below for more details of each command.


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.
> * Commands are not case-sensitive e.g `list` will match `List`  

#### Viewing help : `help`

If you need some more information about the features available, you can use the `help` command.

Format: `help`  

> Help is also shown if an incorrect command is entered e.g. `run`

#### Adding a task: `add`

You can add a task without a specific time and date.

Format: `add TASK_NAME`

This will create a floating task without any start time, end time or deadline.

Examples:  

* `add Workout`
* `add watch Star Wars`

If you need a task to be done by a specific date, you can specify it using `by` or `before`.

Format: `add TASK_NAME by DATE_TIME`  or `add TASK_NAME /before DATE_TIME`  

> Date formats are not case-sensitive

Examples:  

* `add watch Star Wars by Fri`
* `add watch Star Wars by tonight`
* `add watch Star Wars by next Wed`
* `add watch Star Wars by 10 Oct, 9.30pm`

If you need a task to be done within a specific date and time, you can specify it using `from` and `to`

Format: `add TASK_NAME [from START_DATE_TIME] [to END_DATE_TIME] ` 

> If you specify the time but no days or dates given, the date of creation will be used.  

Examples:

* `add watch Star Wars from 7pm`
* `add movie marathon from today 12pm to this friday 3pm`
* `add project meeting from 10 oct 12pm to 2pm`

The event “watch Star Wars” will begin from 7pm of the date of creation. No end time is specified.  
The event “project meeting” will start at 12pm on 10 October and end at 2pm on 10 October.


#### Retrieving task list : `list`

Shows a list of all uncompleted tasks.  
Format: `list`  

Shows a list of all overdue tasks. The tasks will be sorted by their dates.  
Format: `list overdue`  

Shows a list of all upcoming tasks within a week. The tasks will be sorted by their dates.  
Format: `list near`  

Shows a list of all completed tasks.  
Format: `list done`  

Shows a list of all tasks including all completed and uncompleted tasks.  
Format: `list all`  

#### Finding tasks containing keywords: `find`

Finds tasks that contain any of the given keywords.

Format: `find KEYWORD``...`  

  > * The search is not case sensitive. e.g `assignment` will match `Assignment`
  > * The order of the keywords does not matter. e.g. `2 essay` will match `essay 2`
  > * Only the name is searched
  > * Only full words will be matched e.g. `work` will not match `homework`
  > * Tasks matching at least one keyword will be returned (i.e. `OR` search). e.g. `2103` will match `2103 assignment`

Examples:  

* `find Dory`<br>
  Returns `Finding Dory` and `dory`  
* `find Nemo Dory`<br>
  Returns all tasks that contain `Dory` or `Nemo`  

#### Deleting a task : `delete`
Deletes the specified task from the task list.<br>
Format: `delete INDEX`  

> Deletes the task at the specified `INDEX`.
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples:  

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the task list.
* `find movie`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

It is possible to delete multiple tasks in the task list.  
Examples:  
* `list`<br>
  `delete 2 3 4`<br>
  Deletes the 2nd, 3rd and 4th task in the task list.  

#### Updating the name of a task : `rename`

Renames the specified task in the task list.

Format: `rename INDEX NEW_TASK_NAME`  

> Rename the task at the specified `INDEX`. The index refers to the index number shown in the most recent listing.
> The index **must be a positive integer** 1, 2, 3, ...

Examples:  

* `list`<br>
  `rename 2 Star Wars II`<br>
  Updates the name of the 2nd task in the task list to “Star Wars II”.
* `find Star Trek`<br>
  `rename 1 Star Wars II`<br>
  Updates the name of the 1st task in the results of the `find` command to “Star Wars II”. 

#### Updating the date/time of a task : `schedule`

Updates the time of the specified task in the task list.

Format: `schedule INDEX NEW_TIME_DESCRIPTION`

> Schedule the task at the specified `INDEX`. The index refers to the index number shown in the most recent listing.
> The index **must be a positive integer** 1, 2, 3, ...
> The time description must follow the format given in the add command examples

Examples:  

* `list`<br>
  `schedule 4`<br>
  Removes the specified start and/or end time and deadline for task 4 on the list.
* `list`<br>
  `schedule 2 by Fri`<br>
  Remove the specified start/end time and deadline for task 2 and set the deadline to the coming Friday (If the current day is Friday, it would be the following Friday).
* `list`<br>
  `schedule 3 from 1 Oct 7pm to 9.30pm`<br>
  Sets task 3's start time as 1 Oct 7pm and end time as 1 Oct 9.30pm

#### Marking a task as completed : `mark`

Marks the specified task in the task list

Format: `mark INDEX...`

> Mark the task(s) at the specified `INDEX(es)`. The index refers to the index number shown in the most recent listing.
> The index **must be a positive integer** 1, 2, 3, ...
> The index can be in any order.

Examples:  

* `list`<br>
  `mark 2`<br>
  Marks the 2nd task in the task list.
* `find Homework`<br>
  `mark 1`<br>
  Marks the 1st task in the results of the `find` command.

It is possible to mark multiple tasks in the task list.  

Examples:  
* `list`<br>
  `mark 2 3 4`<br>
  Marks the 2nd, 3rd and 4th task in the task list.  

#### Unmarking a task as completed : `unmark`
This works simlar to the `mark` command. Unmarks the specified task in the task list.<br>
Format: `unmark INDEX...`

#### Undo the last command : `undo`  
Undo the last command that have modified the task list (excluding undo).<br>
Format: `undo`  
Multiple undo actions are supported.

#### Create an alias command : `alias`
Defines an alternative short-hand command for an original command. Both original and new commands can be used.<br>
Format: `alias ORIGINAL_COMMAND_NAME as NEW_COMMAND_NAME`  

> NEW_COMMAND_NAME must be a single word.<br>
> ORIGINAL_COMMAND_NAME must be a command word that is specified in the help section

Examples:

* `alias mark as m` <br>
- `m` can used to mark tasks as well.

#### Remove an alias command : `unalias`
Removes the short-hand command.<br>
Format: `unalias NEW_COMMAND_NAME`  

> NEW_COMMAND_NAME must be a user-defined command word.

Examples:

* `unalias m` <br>
- `m` can no longer be used to mark tasks.

#### Specifying a data storage location : `store`
Specifies a folder as the data storage location.<br>
Format: `store FILE_PATH`

> FILE_PATH must be a valid directory path on the local computer

Examples:

* `store C:/Dropbox/ToDo` <br>
The task data will be moved to the specific directory, and future data will be saved in that location.

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Keyboard Shortcuts

1. Words that are UNDERLINED are keys on the keyboard.
2. Use the UP ARROW and DOWN ARROW to scroll through earlier commands.
3. If you are entering a command, use the DOWN ARROW to instantly clear the command line.
4. Use TAB to switch between the various task lists e.g. uncompleted, overdue, upcoming

#### Saving the data
Agendum data is saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install Agendum in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Agendum folder.

## Command Summary

Command | Format  
-------- | :--------
Add | `add NAME [DATETIME]`
Alias |`alias OLD_COMMAND as NEW_COMMAND`
Delete | `delete INDEX`
Edit | `edit INDEX NAME`
Find | `find KEYWORD [MORE_KEYWORDS]`
Help | `help`
List | `list`
Mark | `mark INDEX`
Schedule | `schedule INDEX DATETIME`
Select | `select INDEX`
Store | `store DIRECTORY`
Unalias | `Unalias NEW_COMMAND`
Undo | `undo`
Unmark | `unmark INDEX`
