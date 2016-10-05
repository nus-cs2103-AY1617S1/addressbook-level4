
# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `dowat.jar` from the 'releases' tab.
2. Copy the file to the folder you want to use as the home folder for your task book.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/MockUI.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list task`** : lists all tasks that are not done. 
   * **`add`** `CS2103 Lab 6 /desc finish lab /by 30-12-16` : 
     adds a task named `CS2103 Lab 6` with a description of `finish lab` by the deadline of `30-12-16`.
   * **`mark`**` 3` : deletes the 3rd task shown in the current task list.
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

 
#### Adding a task
Adds a task to the TaskBook<br>
Format: `add TASK_NAME /desc DESCRIPTION /by DEADLINE` 
 
> Words in `UPPER_CASE` are the parameters, parameters will follow behind their corresponding keyword. With the exception of `TASK_NAME`, all other parameters are optional. The order of parameters are not fixed. 
> Dates are represented as DD-MM-YY.


Examples: 
* `add CS2103 Lab 6 /desc hand in through codecrunch /by 30-12-16`


#### Adding an event
Similar to adding a task, you can also add an event to the TaskBook<br>
Format: `add EVENT_NAME /desc DESCRIPTION /from START_DATE END_DATE`

> With the exception of `EVENT_NAME`, all other parameters are optional. The order of parameters are not fixed. 
> Dates are represented as DD-MM-YY.
> If only one date parameter is given after “/from”, the start and end dates will be the same.

Examples:
* `add CS2103 Exam /desc final examination @ MPSH3 /from 21-11-16`
* `add CS2103 Workshop /desc OOP workshop /from 21-9-16 25-9-16`


#### Listing tasks
Shows a list of all tasks that are marked done. <br>
Format: `list task [all]`

> Tasks that are marked done will not be shown by default.
> An [all] optional flag will request the TaskBook to list all tasks, both marked done and not yet marked done. 

Examples: 
* `list task`  
  Lists tasks that are not marked done.
* `list task all`  
  All tasks will be shown.


#### Listing event
Shows a list of all tasks that are marked done. <br>
Format: `list task [all]`

> Tasks that are marked done will not be shown by default.
> An [all] optional flag will request the TaskBook to list all tasks, both marked done and not yet marked done. 

Examples: 
* `list task `<br>
  Lists tasks that are not marked done.
* `list task all` <br>
  All tasks will be shown.



#### Editing a task
Edits an existing task/event in TaskBook<br>
Format: `edit task INDEX /name NEW_TASK_NAME /desc NEW_TASK_DESCRIPTION /by NEW_DEADLINE`


> Edits the task at the specified ‘INDEX’. The index refers to the index number shown in the most recent listing of tasks.
> Edits any number of fields of the task. This includes name and/or description and/or deadline.

Examples: 
* `edit task 1 /desc CS2103 Project /by 30-12-16`<br>
  Edits the description of the 1st task to “CS2103 Project” and the deadline to 30 Sept
* `edit task 4 /desc CS2103 TaskBook`<br>
  Edits the description of the 4th task to “CS2103 TaskBook”


#### Editing an event
Edits an existing event in TaskBook<br>
Format: `edit event INDEX /name NEW_EVENT_NAME /desc NEW_EVENT_DESCRIPTION /from START_DATE END_DATE`

> Edits the event at the specified ‘INDEX’. The index refers to the index number shown in the most recent listing of events.
> Edits any number of fields of the event. This includes name and/or description and/or duration.

Examples:
* `edit event 1 /desc CS2103 Workshop /from 3-10-16 5-10-16`  
  Edits the description of the 1st event to “CS2103 Workshop” and the duration to the period of 3-10-16 to  5-10-16
* `edit event 4 /desc CS2103 TaskBook Project Meeting 4`  
  Edits the description of the 4th task to “CS2103 TaskBook Project Meeting 4”


#### Marking a task as completed
Mark an existing task as completed in the TaskBook.  
Format: `mark INDEX`

> Marks the task at the specified `INDEX` as completed. The index refers to the index number shown in the most recent listing of tasks.
> Completed tasks will not be shown in the list of tasks and will be archived in the TaskBook.

Examples: 
* `mark 1`  
  Marks the 1st task as completed
  


#### Deleting a task/event
Deletes an existing task/event in the TaskBook.  

Format: `delete task|event INDEX`

> Deletes the task/event at the specified `INDEX` in the most recent task/event listing.
> Deleted tasks/event will not be shown even with `list event|task all` command. 

Examples:
* `delete task 1`  
  Deletes the 1st task in the most recent listing



#### Changing the save location
Taskbook data are saved in a file called taskbook.txt in the project root folder. You can change the location by specifying the file path as a program argument.  

Format: `save FILEPATH`

Examples:
* `save C:\Desktop`  
  The filename must end in .txt for it to be acceptable to the program.


#### Viewing help
You can refer to the manual with `help` command. Or you can specify a command which you need help for using `[KEY_WORD]` flag.  

Format: `help [KEY_WORD]`

>Help is also shown if you enter an incorrect command.

Examples:
* `help add `

#### Undo modifications
Can go back to historical versions of the TaskBook with the use of undo commands. Only commands that modify the TaskBook in the same session will be restored. Any versions of current session will not be accessible after restarting the TaskBook.  

Format: `undo`

#### Searching for events/tasks
With the search command, you can search for tasks or events which contain some keywords in their name. 

Format: `search event|task KEYWORD [MORE_KEYWORDS]`

> `KEYWORDS` are case sensitive. Events/Tasks which contain at least one keyword in their names will be returned. 

Examples:
* `search event CS2103`
  Returns relative information of "CS2103 Exam" but not "cs2106 Exam"
* `search task CS2106 CS2103`
  Returns any tasks or events having CS2106, CS2103 in their names. 

#### Exiting the program
Format : `exit`


## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your preious Task Book.
       
## Command Summary

 Command | Format  
-------- | :-------- 
[Add Task](#adding-a-task) | `add TASK_NAME /desc DESCRIPTION /by DEADLINE`
[Add Event](#adding-an-event) | `add EVENT_NAME /desc DESCRIPTION /from START_DATE END_DATE`
[List Task or Event](#listing-tasks) | `list task|event [all]`
[Edit Task](#editing-a-task) | `edit task INDEX /name NEW_TASK_NAME /desc NEW_TASK_DESCRIPTION /by NEW_DEADLINE`
[Edit Event](#editing-an-event) | `edit event INDEX /name NEW_EVENT_NAME /desc NEW_EVENT_DESCRIPTION /from START_DATE END_DATE`
[Mark Task](#marking-a-task-as-completed) | `mark INDEX`
[Delete Task or Event](#deleting-a-task/event) |`delete task|event INDEX`
[Save](#changing-the-save-location) | `save FILEPATH`
[Help](#viewing-help) | `help [COMMAND]`
[Undo](#undo-modifications) | `undo`
[Search](#searching-for-events/tasks) | `search event|task KEYWORD [MORE_KEYWORDS]`
[Exit](#exiting-the-program) | `exit`

