# User Guide

## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.
> * Commands are not case-sensitive e.g `list` will match `List`  

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `run`
 
#### Adding a person: `add`
Adds a floating task to the task list  
Format: `add TASK_NAME` 
This will create a floating task without any start time, end time or deadline.
Examples:

> add watch Star Wars

Adds a task to be done by a specific date
Format: `add TASK_NAME by DATE_TIME`  or `add TASK_NAME before DATE_TIME` 

> Date formats are highly flexible and case-insensitive

Examples:

* `add watch Star Wars by Fri`
* `add watch Star Wars by tonight`
* `add watch Star Wars by next Wed`
* `add watch Star Wars by 10 Oct, 9.30pm`

Adds a task (event) to be done on a specific date with a start and end time
Format: `add TASK_NAME [from START_DATE_TIME] [to END_DATE_TIME] ` 
If dates are not specified, the task will be created without start or end date/time
If only one date is specified, it will be set as the end date and time.

Examples:

* `add watch Star Wars from 7pm`
* `add movie marathon from today 12pm to this friday 3pm`
* `add project meeting from 10 oct 12pm to 2pm`

The event “watch Star Wars” start date time will be 7pm of the date of creation. No end time is saved.
The event “project meeting” will start at 10 October 12pm and end at 10 October, 2pm.

#### Listing all tasks : `list`
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

#### Finding all tasks containing any keyword in their name: `find` 
Finds tasks that contain any of the given keywords.  
Format: `find KEYWORD``...`

  > * The search is not case sensitive. e.g `assignment` will match `Assignment` 
  > * The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans` 
  > * Only the name is searched
  > * Only full words will be matched e.g. `Han` will not match `Hans` 
  > * Tasks matching at least one keyword will be returned (i.e. `OR` search). e.g. `2103` will match `2103 assignment` 

Examples: 
* `find Dory`<br>
  Returns `Finding Dory` and `dory`
* `find Nemo Dory`<br>
  Returns all tasks that contain `Dory` or `Nemo`

#### Deleting a person : `delete`
Deletes the specified task from the task list.  
Format: `delete INDEX`

> Deletes the person at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the task list.
* `find Betsy`<br> 
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.
  
It is possible to delete multiple tasks in the task list.  
Examples:

    list 
    delete 2 3 4 
- Deletes the 2nd, 3rd and 4th tasks in the task list.

#### Updating the name of a task : `rename` 
Renames the specified task in the task list. 
Format: `rename INDEX NEW_TASK_NAME` 

> Rename the task at the specified `INDEX`. The index refers to the index number shown in the most recent listing.
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

    list 
    rename 2 Star Wars II
- Updates the name of the 2nd task in the task list to “Star Wars II”.
    find Star Trek 
    rename 1 Star Wars II
- Updates the name of the 1st task in the results of the `find` command to “Star Wars II”.

#### Updating the date/time of a task : `schedule` 
Updates the time of the specified task in the task list. 
Format: `schedule INDEX NEW_TIME_DESCRIPTION` 

> Schedule the task at the specified `INDEX`. The index refers to the index number shown in the most recent listing.
> The index **must be a positive integer** 1, 2, 3, ...
> The time description must follow the format given in the add command examples

Examples:

    list 
    schedule 4 
- Removes the specified start and/or end time and deadline for task 4 on the list.
    list 
    schedule 2 by Fri
- Remove the specified start/end time and deadline for task 2 and set the deadline to the coming Friday (If the current day is Friday, it would be the following Friday).
    list 
    schedule 3 from 1 Oct 7pm to 9.30pm
- Sets the start time as 7pm, the end time as 9.30pm and the date as 1 October for task 3 on the list.

#### Marking a task as completed : `mark` 
Marks the specified task in the task list
Format: `mark INDEX...` 

> Mark the task(s) at the specified `INDEX(es)`. The index refers to the index number shown in the most recent listing.
> The index **must be a positive integer** 1, 2, 3, ...
> The index can be in any order.

Examples:

    list 
    mark 2 
- Marks the 2nd task in the task list.
    find Homework 
    mark 1 
- Marks the 1st task in the results of the `find` command.
    list 
    mark 3 2 4 
- Marks the 2nd, 3rd and 4th tasks in the task list.

#### Unmarking a task as completed : `unmark` 
This works simlar to the `mark` command. Unmarks the specified task in the task list
Format: `unmark INDEX...` 

> Mark the task(s) at the specified `INDEX(es)`. The index refers to the index number shown in the most recent listing.
> The index **must be a positive integer** 1, 2, 3, ...
> The index can be in any order.

Examples:

    list 
    unmark 3 
- Unmarks the 3rd task in the task list.
    find Homework 
    unmark 2 
- Unmarks the 2nd task in the results of the `find` command.
    list 
    unmark 5 6 7 
- Unmarks the 5th, 6th and 7th tasks in the task list.

#### Selecting a task : `select` 
Selects the task identified by the index number used in the last task listing.
Format: `select INDEX` 

> Selects the task and loads the Google search page the task at the specified `INDEX`. The index refers to the index number shown in the most recent listing.
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

    list 
    select 2 
- Selects the 2nd task in the task list
    find Homework 
    select 1 
- Selects the 1st task in the results of the `find` command.

#### Undo the last command : `undo` 
Undo the last command that have modified the task list (excluding undo).
Format: `undo` 
Multiple undo actions are supported.

### Create an alias command : `alias` 
Defines an alternative short-hand command for an original command. Both original and new commands can be used.
Format: `alias ORIGINAL_COMMAND_NAME as NEW_COMMAND_NAME` 

> NEW_COMMAND_NAME must be a single word. 
> ORIGINAL_COMMAND_NAME must be a command word that is specified in the help section

Examples:

    alias mark as m
- `m` can used to mark tasks as well.

#### Remove an alias command : `unalias` 
Removes the short-hand command
Format: `unalias NEW_COMMAND_NAME` 

> NEW_COMMAND_NAME must be a user-defined command word.

Examples:

    unalias m
- `m` can no longer be used to mark tasks.

#### Clearing all tasks : `clear` 
Clears all tasks from the task list.
Format: `clear` 

#### Specifying a data storage location : `store` 
Specifies a folder as the data storage location
Format: `store FILE_PATH` 

> FILE_PATH must be a valid directory path on the local computer

 Examples:

    store C:/Dropbox/ToDo

The task data will be moved to the specific directory, and future data will be saved in that location.


#### Exiting the program : `exit` 
Exits the program.
Format: `exit` 


#### Keyboard Shortcuts

1. Words that are UNDERLINED are keys on the keyboard.
2. Use the UP ARROW and DOWN ARROW to scroll through earlier commands. 
3. If you are entering a command, use the DOWN ARROW to instantly clear the command line.
4. Use TAB to switch between the various task lists e.g. uncompleted, overdue, upcoming

