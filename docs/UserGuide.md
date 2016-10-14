# User Guide
* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

&nbsp;

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

&nbsp;

## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.
> * Commands are not case-sensitive e.g `list` will match `List`  

#### Viewing help : `help`

If you need some more information about the features available, you can use the `help` command.<br>
Format: `help` 
> Help is also shown if an incorrect command is entered e.g. `run`

#### Adding a task: `add`

You can add a task without a specific time and date.<br>
Format: `add TASK_NAME`
> This will create a task without a start date/time or end date/time.

Examples:  

* `add Workout`
* `add watch Star Wars`

If you need a task to be done by a specific date, you can specify it using `by` or `before`.<br>
Format: `add TASK_NAME [by DATE_TIME]`  or `add TASK_NAME [before DATE_TIME]`
> Date formats are not case-sensitive

Examples:  

* `add watch Star Wars by Fri`
* `add watch Star Wars by 9pm`
* `add watch Star Wars by next Wed`
* `add watch Star Wars by 10 Oct, 9.30pm`

If you need a task to be done within a specific date and time, you can specify it using `from` and `to`.<br>
Format: `add TASK_NAME [from START_DATE_TIME] [to END_DATE_TIME] ` 
> If you specify the time but no day or date is given, the date of creation will be used.

Examples:

* `add watch Star Wars from 7pm`
* `add movie marathon from today 12pm to friday 3pm`
* `add project meeting from 10 oct 12pm to 2pm`

The event “watch Star Wars” will begin at 7pm on the date of creation. No end time will be attached to the task.

The event “project meeting” will start at 12pm on 10 October and end at 2pm on 10 October.


#### Retrieving task list : `list`

You can see tasks sorted by date. Tasks without a date will be appended at the end of the list.

You can view a list of all uncompleted tasks. <br>
Format: `list`  

You can view a list of overdue tasks. <br>
Format: `list overdue`  

You can view a list of upcoming tasks within a week. <br>
Format: `list near`  

You can view a list of completed tasks. <br>
Format: `list done`  

You can view a list of all tasks, which includes overdue, completed and uncompleted tasks. <br>
Format: `list all`  

#### Finding tasks containing keywords: `find`

If you have a huge list of tasks and need to find only specific ones, you can use this command to search for tasks which contain any of the given keywords.<br>
Format: `find KEYWORD``...`  

  > * The search is not case sensitive. e.g `assignment` will match `Assignment`
  > * The order of the keywords does not matter. e.g. `2 essay` will match `essay 2`
  > * Only the name is searched
  > * Only full words will be matched e.g. `work` will not match `homework`
  > * Tasks matching at least one keyword will be returned (i.e. `OR` search). e.g. `2103` will match `2101 and 2103 assignment`

Examples:  

* `find Dory` <br>
  Returns `Shark & Dory` and `dory`  
  
* `find Nemo Dory` <br>
  Returns all tasks that contain `Dory` or `Nemo`  

#### Deleting a task : `delete`

You can delete tasks that are no longer required.<br>
Format: `delete INDEX...`  

> * Deletes the task at the specified `INDEX`.
> * The index refers to the index number shown in the most recent listing.
> * The index **must be a positive integer** 1, 2, 3, ...

Examples:  

* `list` <br>
  `delete 2` <br>
  Deletes the 2nd task in the task list.
  
* `find movie` <br>
  `delete 1` <br>
  Deletes the 1st task in the results of the `find` command.

You can also delete multiple tasks in the task list with a single command.

Examples:

* `list` <br>
  `delete 2 3 4` <br>
  Deletes the 2nd, 3rd and 4th task in the task list.  

#### Renaming a task : `rename`

If you find that the name of a task is not suitable, you can rename it.<br>
Format: `rename INDEX NEW_TASK_NAME`  

> * Renames the task at the specified `INDEX`. 
> * Index refers to the index number shown in the most recent listing.
> * The index **must be a positive integer** 1, 2, 3, ...

Examples:  

* `list` <br>
  `rename 2 Star Wars II` <br>
  Renames the 2nd task in the list to “Star Wars II”
  
* `find Star Trek`   <br>
  `rename 1 Star Wars II` <br>
  Renames the 1st task in the results of the `find` command to “Star Wars II”

#### Updating the date/time of a task : `schedule`

If your deadline has been reduced or extended and you need to change the date of time of a task, you can use this command.<br>
Format: `schedule INDEX [NEW_DATE_TIME]`

> * Schedule the task at the specified `INDEX`. 
> * The index refers to the index number shown in the most recent listing.
> * The index **must be a positive integer** 1, 2, 3, ...
> * The time description must follow the format given in the add command examples

Examples:  

* `list` <br>
  `schedule 4` <br>
  Removes the start and end date/time or deadline for task 4 on the list.
  
* `list` <br>
  `schedule 2 by Fri`<br>
  Removes the start and end date/time or deadline for task 2 and sets the deadline to the coming Friday (If the current day is Friday, it would be the following Friday).
  
* `list`<br>
  `schedule 3 from 1 Oct 7pm to 9.30pm`<br>
  Sets task 3 to start on 1 Oct at 7pm and end at 9.30pm

#### Marking a task as completed : `mark`

If you have completed a task, you can mark it as completed by using this command.<br>
Format: `mark INDEX...`

> * Mark the task at the specified `INDEX`.
> * The index refers to the index number shown in the most recent listing.
> * The index **must be a positive integer** 1, 2, 3, ...
> * The index can be in any order.

Examples:  

* `list`<br>
  `mark 2`<br>
  Marks the 2nd task in the list.
  
* `find Homework`<br>
  `mark 1`<br>
  Marks the 1st task in the list of results of the `find` command.

You can also mark multiple tasks as completed with a single command.

Examples:

* `list`<br>
  `mark 2 3 4`<br>
  Marks the 2nd, 3rd and 4th task in the task list.  

#### Unmarking a task as completed : `unmark`

If a certain task still has work remaining, you can remove the marking.<br>
This works simlar to the `mark` command.<br>
Format: `unmark INDEX...`


#### Undo the last command : `undo`  

If you have accidentally made a mistake in the previous commands, you can use the 'undo' command to remedy it.<br>
Multiple undo actions are also supported.<br>
Format: `undo`

Examples:

* `add homework`<br>
  `undo`<br>
  The task "homework" which has been added previously, will be removed.

#### Create an alias for a command : `alias`

If you are looking for alternatives or want to type a command faster, you can use the `alias` comand.<br>
You can use both new and old command aliases to carry out the same action.<br>
Format: `alias ORIGINAL_COMMAND_NAME NEW_COMMAND_NAME`  

> * NEW_COMMAND_NAME must be a single word.
> * ORIGINAL_COMMAND_NAME must be a command word that is specified in the Command Summary section
> * Only one alias can be used
> * When creating a new alias with a pre-existing alias, the previous alias will be overriden.

Examples:

* `alias mark m` <br>
  `m` and `mark` can now be used to mark a task.<br>
  `alias mark mk`<br>
  only `mk` and `mark` can be used to mark a task, as `m` has been overriden.

#### Remove an alias command : `unalias`

If you no longer want to use the alternative alias command, you can remove it.
Format: `unalias NEW_COMMAND_NAME` or `unalias ORIGINAL_COMMAND_NAME`

> * NEW_COMMAND_NAME must be a user-defined command word.
> * ORIGINAL_COMMAND_NAME must be a command word that is specified in the Command Summary section

Examples:

* `unalias m`<br>
  `m` can no longer be used to mark tasks.<br>
  `unalias mark`<br>
  The assigned alias for `mark` will be removed, and only `mark` can be used to mark a task as completed.

#### Specifying a data storage location : `store`

If you want to store the task list data in a different location, you can specifiy it using this command.<br>
Format: `store FILE_PATH`

> FILE_PATH must be a valid path on the local computer
> If a file at FILE_PATH exists, it will be overriden.

Examples:
* `store C:/Dropbox/ToDo`

The task list data will be moved to the specific directory, and future data will be saved in that location.

#### Exiting the program : `exit`

If you have finished using the application, you can use this command to exit the program.<br>
Format: `exit`  

#### Keyboard Shortcuts

1. Use the <kbd>UP ARROW</kbd> and <kbd>DOWN ARROW</kbd> to scroll through earlier commands.
2. If you are entering a command, use the <kbd>DOWN ARROW</kbd> to instantly clear the command line.
3. Use <kbd>TAB</kbd> to switch between the various task lists e.g. uncompleted, overdue, upcoming

#### Saving the data

Agendum data is saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

&nbsp;

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install Agendum in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Agendum folder.

&nbsp;

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
