# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

1. Download the latest `taskmanager.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>

5. Some example commands you can try:
   ***`list`** : lists all to-dos in order of index
   ***`add`**`Practice tennis, s/tomorrow 3pm, e/tomorrow 6pm, l/school court 4, #sports`** : adds a task ‘Practice tennis’ for the next day from 3pm to 6pm with a tag ‘sports’
   ***`delete`**` 3` : deletes the 3rd contact shown in the current list
   ***`exit`** : exits the app
   *** `edit`**:  update information stored

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
 
 #### Adding a task: `add`
Adds a task to the task manager<br>
Format: `add TASK_NAME, s/[start_time], e/[end_time], p/[low/medium/high], d/[deadline], l/[location], #[tag1], #[tag2], c/[completed_status]` 
> Tasks can have any number of tags (including 0)
Examples: 
* `add John Doe project team meeting, s/tomorrow 15:00 e/18:00p/high `
* `add Betsy Crowe cs2103 assignment s/tonight 21:00 e/24:00 p/medium d/next friday`

#### Listing all tasks : `list`
Shows a list of all tasks in the to-do list in order of index.<br>
Format: `list`

#### Listing all tasks in order of priority : `listByPriority`
Shows a list of uncompleted tasks in the to-do list in order of priority.<br>
Format: `listByPriority`

#### Listing all tasks : `listByDeadline`
Shows a list of uncompleted tasks in the to-do list in order of their deadlines, tasks without deadlines will be listed in order of index after it<br>
Format: `listByDeadline`

#### Editing a task: `Edit`
Edit task information in the task manager<br>
Format: Edit | `edit TASK_NAME/INDEX s/ [start time]`, `edit TASK_NAME/INDEX e/ [end time]`,`edit TASK_NAME/INDEX p/[low/medium/high]` `edit TASK_NAME/INDEX t/[tag]`, ‘edit TASK_NAME/INDEX c/[completed_status]’, ‘edit TASK_NAME/INDEX l/[location]’

> Edit the task at the specific `INDEX` or ‘TASK_NAME’
     The index refers to the index number shown in the most recent listing.<br>
     The index **must be a positive integer** 1, 2, 3, …
     `TASK_NAME` should be the same as the task name stored in task manager regardless the word case

Examples: 
* `edit 2 s/3pm` 
`edit meeting p/low`

#### Deleting a task : `delete`
Deletes the specified task from the task manager.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the task manager.

#### Clearing all entries : `clear`
Clears all entries from the task manager.<br>
Format: `clear`  

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data 
Task manager data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

#### Undo the modification : `undo`
Undo the modification in the last step.<br>
Format: `undo`  

#### Redo the undone modification : `redo`
Redo the undone modification in the last step.<br>
Format: `redo`  

#### Change working directory : `directory`
Change directory being accessed, effectively using another task manager.<br>
Format: `directory [PATH]`  

#### Backup : `backup`
Save a copy of the current TaskManager list into the specified directory.<br>
Format: `backup [PATH]`  

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Task Manager folder.
       
## Command Summary
Command | Format  
-------- | :-------- 
Add | `add TASK_NAME, s/[start_time] e/[end_time] p/[low/medium/high] d/[deadline] #[tag1],[tag2] c/[completed_status]`
Delete | `delete [INDEX]` `delete TAG`
Search | `search [KEYWORD]`
List | `list`, `listByPriority`, `listByDeadline` 
Help | `help`
Clear | `clear`
Exit | `exit`
Undo | `undo`
Redo | `redo`
Edit | `edit TASK_NAME/INDEX s/ [start time]`, `edit TASK_NAME/INDEX e/ [end time]`,`edit TASK_NAME/INDEX p/[low/medium/high]`, `edit TASK_NAME/INDEX t/[tag]`,`‘edit TASK_NAME/INDEX c/[completed_status]`,  `edit TASK_NAME/INDEX l/[location]`
Set Directory | `directory [PATH] `
Backup | `backup [PATH] `
