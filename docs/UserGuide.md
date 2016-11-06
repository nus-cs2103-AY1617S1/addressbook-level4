# User Guide

* [About the Task Manager](#about-the-task-manager)
* [Ouick Start Guide](#quick-start-guide)
* [Basic Command Summary](#basic-command-summary)
* [Features](#features)
* [FAQ](#faq)


## About the Task Manager
From Google Calendar to Todoist, Easynote and S Planner, there are many task managers out there that help users like yourself manage their lives more effectively. However, you may have noticed that such task managers often require you to go through a multitude of menu options and clicks before you can even input your activity into the manager. This results in you losing time throughout the day as work piles up with no end in sight and task managers that force you to jump through hoops just to keep track of everything.  

So, with that in mind our team designed this task manager to give users like yourself a more hassle-free and efficient experience. We have named this application “The Practical Task Manager” or “TPTM” for short. 

We believe that TPTM will greatly benefit any user who favours a command line interface and are thus releasing this program to the public to use free of charge. So, if you need a simple application to manage your busy life TPTM is the program for you!     


## Quick Start Guide

0. Make sure you have Java version `1.8.0_60` or later installed in your computer.<br>
   > This app will not work with previous versions of Java 8.

1. Download the latest 'The Practical Task Manager' file (tptm.jar) from the [releases](../../../releases) tab.
2. Copy the file to a suitable location on your computer. This location will serve as the home folder for the Task Manager.<br>

3. Double-click the file to start the application. The GUI should appear as shown below in a few seconds.<br>
<center>
<img src="images/Ui.png" width="500"><br>
Figure 1: Graphical User Interface (GUI) Mockup
4. Type a command in the command box and press <kbd>enter</kbd> to execute it. Depending on the command, the Task Manager will respond by displaying a message in the console window.<br>

5. Some example commands you can try:
>   1. **'list'** : lists all uncompleted tasks<br>
>   2. **'add'**` Project due for CS2103 d/121116 p/3` : adds a Task named 'Project due for CS2103' on the 12/11/2016 at a priority of level 3 to the Task Manager.<br>
>   3. **'delete'**` 1` : deletes the first task shown in the current list<br>
>   4. **'exit'** : exits the application <br>

6. Refer to the [Basic Command Summary](#basic-command-summary) section below for the extensive list of commands as well as their format and simple examples. If you need any further information the [Features](#features) section will provide you with all you need to know about each command.<br>

## Basic Command Summary
Command | Format | Example  
-------- | :------- | :--------
Add | `add TASKNAME d/<DEADLINE> p/<PRIORITY>` | add CS2103 Project d/231217 p/4
Edit | `edit INDEX <TASKNAME> d/<DEADLINE> p/<PRIORITY>` | edit CS2103 Project d/071116 p/5
Delete | `delete INDEX` | delete 2
Find | `find KEYWORD <MORE_KEYWORDS>` | find CS2101 Report
List | `list`  
Listall | `listall`
Complete | `complete INDEX` | complete 1
Help | `help`
Undo | `undo`
Revert | `rev`
Exit | `exit`
Update | `update`
Clear | `clear`
<div style="page-break-after: always;"></div>

## Features

**Command Format**
* Words in `UPPER_CASE` are the parameters.
* Items in `ANGLE_BRACKETS` are optional.
* Items with `...` after them can have multiple instances.
* The order of parameters is fixed.


#### Adding a task: `add`
Description: Adds a task to TPTM<br>
Format: `add TASKNAME s/<STARTDATE> d/<DEADLINE> p/<PRIORITY> t/<TAG>...`
Shortcut: +

>Key things to note:
>STARTDATE and DEADLINE can be in ddmmyy, ddmmyy [HH:MM] or dd-mm-yy [HH:MM] formats.<br>
>Time must be in 24-hour format.<br>
>If no time is specified, STARTDATE will be set to a default value of 00:00 whereas DEADLINE will default to 23:59.<br>
>Tasks can have different priority levels or none at all (from 1 to 5, where 1 is the highest priority and 5 is the lowest priority).<br>
>Tasks can have any number of tags (even 0).<br>

Examples:
* `add complete report`<br>
  Adds a floating task named ‘complete report’<br>
* `add Meet with SO s/011216 12:00 d/011216`<br>
* `add make sandwich s/111016 [12:45] d/111016 [13:00] p/5 t/hungry`<br>                                         `
  Adds an event named ‘make sandwich’ which starts at ‘12:45’ on ’11-10-16’ due on the same day at ’13:00’ with a priority level of ‘5’ and the tag ‘hungry’<br>

#### Editing a task: `edit`
Description: Edits the last task selected.<br>
Format: `edit INDEX <TASKNAME> s/<STARTDATE> d/<DEADLINE> p/<PRIORITY> t/<TAG>...`

> Key thing to note:
> Inputs are the same as specified in the `add` command function.

Examples:
* `list`<br>
  `edit 3 Finish studying for EE2021 d/121116 p/4`<br>
  Edits the third task in the list of the task manager by replacing the description, changing the date and the priority.
* `find CS2101 meeting`<br>

#### Deleting a task : `delete`
Description: Deletes the specified task from the task manager.<br>
Format: `delete INDEX`
Shortcut: -

> Key thing to note:
> The index refers to the index number shown in the most recent listing.<br>

Examples:
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the task manager.
* `find CS2101`<br>
  `delete 1`<br>
  Deletes the 1st task based on the results of the `find` command.
  
#### Lists all uncomplete tasks: list
Description: Lists all uncomplete tasks in order of task index.
Format: `list`
Shortcut: l

#### Listing all tasks: listall
Description: Lists all tasks except deleted tasks in order of task index.
Format: `listall`
Shortcut: la


#### Listing all tasks: listtag
Description: Lists all tasks that have the same specified tag in order of task index.
Format: `listtag KEYWORD`
Shortcut: lt

> Key things to note:
> The KEYWORD is case insensitive.<br>
> A partial KEYWORD will bring up all tags that relate to it. For example, fri will being up friends and friday. <br>


#### Finding all tasks containing any keyword in their name: `find`
Description: Finds task(s) whose names contain any of the specified keywords.<br>
Format: `find KEYWORD <MORE_KEYWORDS>`

> Key things to note:
> The search is not case sensitive. e.g `cs2103t` will match `CS2103T`<br>
> The order of the keywords does not matter. e.g. `Software Engineering` will match `Engineering Software`<br>
> Only the keywords are searched.<br>
> Tasks matching at least one keyword will be returned (i.e. `OR` search).<br>
    e.g. `Software` will match `Software Engineering`

Examples:
* `find Software`<br>
  Returns `Software Engineering` and `software`
* `find CS2103T Software Engineering`<br>
  Returns any task that has the names `CS2103T`, `Software`, or `Engineering`
* 'find assignment' <br>

#### Completing a task : `complete`
Description: Completes the task at the specified index in TPTM’s task list by appending ‘is completed’ to the end of the tasks name.
Format: `complete INDEX`

> Key thing to note:
> The index must refer to the index numbers shown in the most recent listing.<br>

Examples:
* `list`<br>
* `find CS2101`
  `complete 2`<br>
  Completes the 2nd task in the results of the 'find' command

#### Viewing help : `help`
Description: Displays all commands<br>
Format: `help`

<div style="page-break-after: always;"></div>

#### Undoing a command: `undo`
Description: Undoes a previously inputted command, by bringing TPTM back to a previous state.
Format: `undo`

> Key thing to note:
> Undo can only be used for up to 10 commands in a row <br>

#### Reverting back from an undo command: `rev`
Description: Reverts TPTM back to its initial state, before the undo command was used.
Format: `rev`

> Key thing to note:
> Revert can only be used for up to 10 undoes in a row <br>

<div style="page-break-after: always;"></div>

#### Updating the task manager: `update`
Description: Updates the task manager if the date and time is out of synchronisation with the computer and checks the amount of time left to do a task or prepare for an event.
Format: `update`

> Key things to note:<br>
> The name of the task/event will be highlighted in blue, if the task or event has only four days left.<br>
> The name will be highlighted in green, if there are three days left.<br>
> The name will be highlighted in purple, if there are two days left.<br>
> The name will be highlighted in orange, if there is only a day left.<br>
> The name will be highlighted in red, if the task or event is not labelled complete as it will be taken to be overdue or unattended.

#### Saving a task list : `save`
Description: Saves all tasks on the task manager to a new file.
Format: `save ./data/NEW_FILE.xml`

> Key things to note:<br>
> Name of the file must be in a NAME.xml format only, the NAME can be alphanumerical.
> If the name of the file does not exist in data a new file will automatically be created.
> If the file already exists in the data folder, then the saving the state into that file will overwrite the previous information and store the new state in the file.
> This function serves as the backup tool for TPTM, it is suggested that regular backups be made using this command.

#### Loading a task list : `load`
Description: Loads a user specified file that has been previously saved or has been imported to TPTM.
Format: `load ./data/NEW_FILE.xml`

> Key things to note:
> Follows the same naming convention as save.
> `load` allows the user to load any file that has been saved using TPTM.
> If the file does not exist no file will be loaded.
> If load succeeds it will cause TPTM to shut down and upon accessing TPTM again it will reflect the state if the previously loaded file.

#### Scrolling to a point on the list: `scroll`
Description: Scrolls to a designated point on TPTM’s task list.
Format: `scroll POINT`

> Key thing to note:
> POINT can be any index number on TPTM’s current list or the words top or bottom.

Example:
*list 


#### Clearing all entries : `clear`
Description: Clears all entries from the task manager.<br>
Format: `clear`

> Key things to note: <br>
> This process is irreversible, and there will be a prompt before the command proceeds.
> Please only use this if you need to reset the task manager. 
> Some scenarios would be if you need to pass the task manager to someone else at your work terminal or if you are changing jobs.


#### Exiting the program : `exit`
Description: Exits the program.<br>
Format: `exit`

#### Saving the data
Task Manager data is saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Task Manager folder.          
