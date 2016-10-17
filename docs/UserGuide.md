# User Guide
* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

&nbsp;

## Quick Start

0. Ensure you have Java version `1.8.0_60` or above installed in your Computer.

   > Take note that Agendum might not work with earlier versions of Java 8.

1. Download the latest `Agendum.jar` from the [releases](../../../releases) tab.

2. Copy Agendum.jar to the folder you want to use as the home.

3. Double-click the file to start Agendum. The GUI should appear promptly.

4. Type a command in the command box and press <kbd>Enter</kbd> to execute it.

   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will list some information about commands.

5. Go ahead and try some of the commands listed below!
   * **`add`**` Go to shopping mall` : adds a task with description `Go to shopping mall` to Agendum.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`list`** : lists all uncompleted tasks
   * **`exit`** : exits Agendum

6. You can refer to the [Features](#features) section below for more details of each command.


&nbsp;


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Parameters in `SQUARE_BRACKETS` are optional.
> * Parameters with `...` after them can have multiple instances.
> * The order of parameters is fixed.
> * Commands and parameters are not case-sensitive e.g `list` will match `List`  


#### Viewing help : `help`

If you need some reminder or more information about the features available, you can use the `help` command.

Format: `help`  
> Help is also shown if an incorrect command is entered e.g. `run`


#### Adding a task: `add`

You can add a task without a specific time and date.<br>
Format: `add TASK_NAME`
> This will create a task without a start date/time or end date/time.

Examples:  

* `add Workout`
* `add watch Star Wars`

If you need a task to be done by a specific date, you can specify the deadline after the keyword `by`.<br>
Format: `add TASK_NAME [by DATE_TIME]`
> Date formats are not case-sensitive

Examples:  

* `add watch Star Wars by Fri`
* `add watch Star Wars by 9pm`
* `add watch Star Wars by next Wed`
* `add watch Star Wars by 10 Oct, 9.30pm`

If you need a task to be done within a specific date and time, you can specify the start and end time using `from` and `to`.<br>
Format: `add TASK_NAME [from START_DATE_TIME to END_DATE_TIME] `
> If you specify the time but no day or date is given, the date of creation will be used.

Examples:

* `add movie marathon from today 12pm to friday 3pm`
* `add project meeting from 10 oct 12pm to 10 oct 2pm`

The event “project meeting” will start at 12pm on 10 October and end at 2pm on 10 October.


#### Retrieving task list : `list`

You can view tasks sorted by date. Tasks without a date will be appended at the end of the list.<br>
Format: `list [TYPE]`
> `TYPE` refers to a keyword such as `overdue, near, done or all`.

Examples:

* `list` <br>
  Agendum will show you a list of all uncompleted tasks.

* `list overdue`<br>
  Agendum will show you a list of overdue tasks.

* `list near`  <br>
  Agendum will show you a list of upcoming tasks within a week. <br>

* `list done`<br>
  Agendum will show you a list of completed tasks. <br>

* `list all`<br>
  Agendum will show you a list of all tasks including overdue, completed and uncompleted tasks. <br>


#### Finding tasks containing keywords: `find`

If you have a long list of tasks and need to find only specific ones, you can use this command to search for tasks which contain any of the given keywords.<br>
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

You can delete tasks that you no longer want to keep track of.<br>
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
  `delete 2,3,4` <br>
  `delete 2-4` <br>
  Each of the above command will delete the 2nd, 3rd and 4th task in the task list.  


#### Renaming a task : `rename`

If you find that the name of an existing task is not suitable, you can always rename it.<br>
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

Have the deadline of your task been reduced or extended? Have an event been rescheduled? To change the date or time of a task, you can use this command.<br>
Format: `schedule INDEX [NEW_DATE_TIME_RESTRICTIONS]`

> * Schedule the task at the specified `INDEX`.
> * The index refers to the index number shown in the most recent listing.
> * The index **must be a positive integer** 1, 2, 3, ...
> * The time description must follow the format given in the add command examples

Examples:  

* `list` <br>
  `schedule 4` <br>
  Removes the deadline and start and end date/time for task 4 on the list.

* `list` <br>
  `schedule 2 by Fri`<br>
  Removes the deadline and start and end date/time for task 2 and resets the deadline to the coming Friday (If the current day is Friday, it would be the following Friday).

* `list`<br>
  `schedule 3 from 1 Oct 7pm to 1 Oct 9.30pm`<br>
  Sets the start time of task 3 to 1 Oct 7pm and the end time to 1 Oct 9.30pm respectively


#### Marking a task as completed : `mark`

If you have completed a task, you can mark it as completed by using the following command.<br>
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
  `mark 2,3,4` <br>
  `mark 2-4` <br>
  Each of the above command will mark the 2nd, 3rd and 4th task as completed.  


#### Unmarking a task as completed : `unmark`

You might change your mind and want to continue working on a completed task. To reflect these changes in Agendum, follow this command: <br>
Format: `unmark INDEX...`
This works in a same way as the `mark` command.<br>


#### Undo the last command : `undo`  

If you have accidentally made a mistake in the previous commands, you can use the 'undo' command to remedy it.<br>
Multiple undo actions are also supported.<br>
Format: `undo`

Examples:

* `add homework`<br>
  `undo`<br>
  The task "homework" which has been added previously, will be removed.


#### Creating an alias for a command : `alias`

If you are looking for alternatives or want to type a command faster, you can use the `alias` comand.<br>
You can use both new and old command aliases to carry out the same action.<br>
Format: `alias ORIGINAL_COMMAND_NAME NEW_COMMAND_NAME`  

> * NEW_COMMAND_NAME must be a single word.
> * ORIGINAL_COMMAND_NAME must be a command word that is specified in the Command Summary section
> * When creating an alias for a command with a pre-existing alias, the pre-existing alias will be overriden.

Examples:

* `alias mark m` <br>
  you can now use`m` or `mark` to mark a task as completed.<br>
  `alias mark mk`<br>
  Now you can only use `mk` or `mark` to mark a task; `m` has been overriden.


#### Removing an alias command : `unalias`

If you no longer want to use the alternative alias command, you can remove it.
Format: `unalias NEW_COMMAND_NAME` or `unalias ORIGINAL_COMMAND_NAME`

> * NEW_COMMAND_NAME must be a user-defined command word.
> * ORIGINAL_COMMAND_NAME must be a command word that is specified in the Command Summary section

Examples:

* `unalias m`<br>
  `m` can no longer be used to mark tasks.<br>
  `unalias mark`<br>
  The assigned alias for `mark` will be removed; Now you can only use the original command `mark` to mark a task as completed.


#### Specifying the data storage location : `store`

If you want to store the task list data in a different location, you can specifiy it using this command.
The task list data will be moved to the specific directory, and future data will be saved in that location.<br>
Format: `store PATH_TO_FILE`

> * PATH_TO_FILE must be a valid path to a file on the local computer.
> * If a file at PATH_TO_FILE exists, it will be overriden.
> * The previous data storage file will not be deleted.

Examples:
* `store C:/Dropbox/ToDo/mytasklist.xml`


#### Loading from another data storage location : `load`

If you have another data file with existing task data, you can load it into Agendum. <br><br>
Format: `load PATH_TO_FILE`

> * PATH_TO_FILE must be a valid path to a file on the local computer.
> * Existing data will be saved and stored in the existing data storage location.
> * The task list in Agendum will be replaced by the loaded task list.
> * Future data will be stored in PATH_TO_FILE.

Examples:
* `load data/mytasklist.xml`

#### Exiting the program : `exit`

If you have finished using the application, you can use this command to exit the program.<br>
Format: `exit`  


#### Keyboard Shortcuts

1. Use the <kbd>UP ARROW</kbd> and <kbd>DOWN ARROW</kbd> to scroll through earlier commands.
2. If you are entering a new command, use the <kbd>DOWN ARROW</kbd> to instantly clear the command line.
3. Use <kbd>TAB</kbd> to switch between the various task lists e.g. uncompleted, overdue, upcoming


#### Saving the data

Agendum saves its data into the specified data storage location, or by default it saves into `todolist.xml`. This saving automatically happens whenever the task list is changed; There is no need to save manually.


&nbsp;


## FAQ

<html>
<dl>
   <dt> Q: How do I transfer my data to another computer? </dt>
   <dd> Firstly, take note of the data storage location that your current todo list is saved at. You can check this by looking at the            bottom-right of Agendum. Navigate to this location and copy the data file to a portable USB device or hard disk. Then, ensure            that you have installed Agendum in the other computer. Copy the data file from your device onto the other computer, preferrably          in the same folder as Agendum. Use the <code>load</code> command to load it into Agendum. </dd>

   <dt> Q: Why did Agendum complain about an invalid file directory? </dt>
   <dd> Check if the directory you wish to relocate to exists, or if you have enough administrator privileges. </dd>

   <dt> Q: Can Agendum remind me when my task is due soon? </dt>
   <dd> Agendum will always show the tasks that are due soon at the top of list. However, Agendum will not show you a reminder. </dd>

</dl>
</html>

&nbsp;


## Command Summary

Command  | Format  
:-------:| :--------
Add      | `add TASK_NAME [by DATE_TIME] [from START_DATE_TIME to END_DATE_TIME]`
Alias    | `alias ORIGINAL_COMMAND_NAME NEW_COMMAND_NAME`
Delete   | `delete INDEX...`
Exit     | `exit`
Find     | `find KEYWORD...`
Help     | `help`
List     | `list [TYPE]`
Load     | `load PATH_TO_FILE`
Mark     | `mark INDEX...`
Rename   | `rename INDEX NEW_NAME`
Schedule | `schedule INDEX [by DATE_TIME] [from START_DATE_TIME to END_DATE_TIME]`
Select   | `select INDEX`
Store    | `store PATH_TO_FILE`
Unalias  | `unalias NEW_COMMAND_NAME` or `unalias ORIGINAL_COMMAND_NAME`
Undo     | `undo`
Unmark   | `unmark INDEX...`
