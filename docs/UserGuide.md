# User Guide

* [About the Task Manager](#about-the-task-manager)
* [Ouick Start Guide](#quick-start-guide)
* [Basic Command Summary](#basic-command-summary)
* [Features](#features)
* [FAQ](#faq)

<!---@@author A0139516B-->
## About the Task Manager
From Google Calendar to Todoist, Easynote and S Planner, there are many task managers out there that help users like yourself manage their lives more effectively. However, you may have noticed that such task managers often require you to go through a multitude of menu options and clicks before you can even input your activity into the manager. This results in you losing time throughout the day as work piles up with no end in sight and task managers that force you to jump through hoops just to keep track of everything.<br>

So, with that in mind our team designed this task manager to give users like yourself a more hassle-free and efficient experience. We have named this application “The Practical Task Manager” or “TPTM” for short.<br>

We believe that TPTM will greatly benefit any user who favours a command line interface and are thus releasing this program to the public to use free of charge. So, if you need a simple application to manage your busy life TPTM is the program for you!<br>

<center>
<img src="images/Ui.png" width="500"><br>

Figure 1: Graphical User Interface (GUI) <br>

<!---@@author A0139516B-->
## Quick Start Guide

0. Make sure you have Java version `1.8.0_60` or later installed in your computer.<br>
   > This app will not work with previous versions of Java 8.

1. Download the latest 'The Practical Task Manager' file (tptm.jar) from the [releases](../../../releases) tab.
2. Copy the file to a suitable location on your computer. This location will serve as the home folder for the Task Manager.<br>

3. Double-click the file to start the application. The GUI should appear as shown below in a few seconds.<br>

4. Type a command in the command box and press <kbd>enter</kbd> to execute it. Depending on the command, the Task Manager will respond by displaying a message in the console window.<br>

5. Some examples of commands you can try:
>   1. **'list'** : lists all uncompleted tasks<br>
>   2. **'add'**` Project due for CS2103 d/121116 p/1` : adds a Task named 'Project due for CS2103' on the 12/11/2016 at a priority of level 1 to the Task Manager.<br>
>   3. **'delete'**` 1` : deletes the first task shown in the current list<br>
>   4. **'exit'** : exits the application <br>

Refer to the [Basic Command Summary](#basic-command-summary) section below for the complete list of commands as well as their basic format and a simple example. If you need any further information the [Features](#features) section will provide you with all you need to know about each command.<br>

<!---@@author A0139516B-->
## Basic Command Summary

Command | Format | Usage Example
-------- | :------- | :--------
Add | `add TASKNAME d/<DEADLINE> p/<PRIORITY>` | add CS2103 Project d/231217 p/4
Edit | `edit INDEX <TASKNAME> d/<DEADLINE> p/<PRIORITY>` | edit CS2103 Project d/071116 p/5
Delete | `delete INDEX` | delete 2
List | `list`
Listall | `listall`
Listtag | `listtag KEYWORD` | listtag important
Find | `find KEYWORD <MORE_KEYWORDS>` | find CS2101 Report
Complete | `complete INDEX` | complete 1
Help | `help`
Undo | `undo`
Revert | `rev`
Update | `update`
Save | `save ./data/FILE_NAME.xml` | save ./data/jimsList.xml
Load | `Load ./data/FILE_NAME.xml` | load ./data/jimsList.xml
Scroll | `Scroll POINT` | scroll top
Clear | `clear`
Repeat | `repeat INDEX SCHEDULE` | repeat 3 weekly
Exit | `exit`

<div style="page-break-after: always;"></div>

<!---@@author A0139097U-->
## Features

**Command Format**
* Words in `UPPER_CASE` are the parameters.
* Items in `ANGLE_BRACKETS` are optional.
* Items with `...` after them can have multiple instances.
* The order of parameters is fixed.

<!---@@author A0139516B-->
#### Adding a task: `add`
Description: Adds a task to TPTM<br>
Format: `add TASKNAME s/<STARTDATE> d/<DEADLINE> p/<PRIORITY> t/<TAG>...`<br>
Shortcut: `+`

> Key things to note:
> * STARTDATE AND DEADLINE parameters can be in ddmmyy, ddmmyy [HH:MM] or dd-mm-yy [HH:MM] formats.
> * Time must be in the 24-hour format.
> * If no time is specified, STARTDATE will be set to a default value of 00:00 whereas DEADLINE will default to 23:59.
> * Tasks can have different priority levels or none at all (from 1 to 5, where 1 is the highest priority and 5 is the lowest priority).<br>
> * Tasks can have any number of tags (even 0).

Examples:
* `add complete report`<br>
  Adds a floating task named ‘complete report’
* `add CS2103 project d/231016 p/1 t/Group`<br>
  Adds a deadline task named ‘CS2103 project’ that is due on ‘23-10-16’ with a priority level of ‘1’ and the tag ‘Group’.
* `add make sandwich s/111016 12:45 d/111016 13:00 p/5 t/hungry`<br>
  Adds an event named ‘make sandwich’ which starts at '12:45' on '11-10-16’ due on the same day at '13:00’ with a priority level of ‘5’ and the tag ‘hungry’


<!---@@author A0139516B-->
#### Editing a task: `edit`
Description: Edits the index selected task.<br>
Format: `edit INDEX INPUT <INPUT> <INPUT>`

> Key things to note:
> * Edits the task by replacing the information stored with the input entered.<br>
> * Inputs are the same as specified in the `add` command function.

Examples:
* `list`<br>
  `edit 3 Finish studying for EE2021 d/121116 p/4`<br>
  Edits the third task in the list by replacing the description, changing the date and the priority.
* `list`<br>
  `edit 1 d/111116 t/Johnwillbelate`<br>
  Added in the tag `Johnwillbelate`


<!---@@author A0139516B-->
#### Deleting a task: `delete`
Description: Deletes the specified task from TPTM.<br>
Format: `delete INDEX`<br>
Shortcut: `-`

> Key thing to note:<br>
> The index refers to the index number shown in the most recent listing.<br>

Examples:
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the list.
* `find CS2101`<br>
  `delete 1`<br>
  Deletes the 1st task based on the results of the `find` command.

<!---@@author A0139516B-->
#### Listing all uncompleted tasks: `list`
Description: Lists all uncompleted tasks in order of task index.<br>
Format: `list`<br>
Shortcut: `l`


<!---@@author A0139516B-->
#### Listing all tasks: `listall`
Description: Lists all tasks except deleted tasks in order of task index.<br>
Format: `listall`<br>
Shortcut: `la`


<!---@@author A0144202Y-->
#### Listing all tasks with the same tag: `listtag`
Description: Lists all tasks that have the same tag in order of task index.<br>
Format: `listtag KEYWORD`<br>
Shortcut: `lt`

> Key things to note:
> * The `KEYWORD` is case insensitive.
> * A partial `KEYWORD` will bring up all tags that relate to it. e.g. `fri` will bring up `friends` and `friday`.<br>


<!---@@author A0139516B-->
#### Finding all tasks containing any keyword in their name: `find`
Description: Finds task/s whose names contain any of the specified keywords.<br>
Format: `find KEYWORD <MORE_KEYWORDS>`<br>
Shortcut: `@`

> Key things to note:
> * The search is not case sensitive. e.g. `cs2103t` will match `CS2103T`.<br>
> * The search can be partial. e.g. `CS21` will display `CS2101`, `CS2103T` and `CS2103 Project`.<br>
> * The order of the keywords does not matter. e.g. `Software Engineering` will match `Engineering Software`<br>
> * Only the keywords are searched.<br>
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).<br>
    e.g. `Software` will match `Software Engineering`

Examples:
* `find Software`<br>
  Returns `Software Engineering` but not `software`
* `find CS2103T Software Engineering`<br>
  Returns any task that has the names `CS2103T`, `Software`, or `Engineering`


<!---@@author A0144202Y-->
#### Completing a task: `complete`
Description: Completes the task at the specified index in TPTM’s task list and appends ‘is completed’ to the end of the tasks name.
Format: `complete INDEX`<br>
Shortcut: `com`

> Key thing to note: <br>
> The task/event will not appear when the command `list` is used and will only appear when `listall` is used.

Examples:
* `find MeaningOfLife`<br>
  `complete 1` <br>
  Completes the 1st task in the results of the `find` command.
* `list`<br>
  `complete 2`<br>
  Completes the 2nd task in the task manager.


<!---@@author A0139516B-->
#### Viewing help: `help`
Description: Displays this user guide<br>
Format: `help`


<!---@@author A0139516B-->
#### Undoing a command: `undo`
Description: Undoes a previously inputted command, by bringing TPTM back to a previous state.
Format: `undo`

> Key thing to note:<br>
> Undo can only be used for up to 10 commands in a row


<!---@@author A0139516B-->
#### Reverting back from an undo command: `rev`
Description: Reverts TPTM back to its initial state, before the undo command was used.
Format: `rev`

> Key thing to note:<br>
> Revert can only be used for up to 10 undoes in a row


<!---@@author A0139097U-->
#### Updating TPTM: `update`
Description: Updates the task manager if the date and time is out of synchronisation with the computer and checks the amount of time left to do a task or prepare for an event.<br>
Format: `update`

> Key things to note:<br>
> * The name of the task/event will be highlighted in blue, if the task or event has only four days left.<br>
> * The name will be highlighted in green, if there are three days left.<br>
> * The name will be highlighted in purple, if there are two days left.<br>
> * The name will be highlighted in orange, if there is only a day left.<br>
> * The name will be highlighted in red, if the task or event is not labelled complete as it will be taken to be overdue or unattended.


<!---@@author A0139516B-->
#### Saving TPTM’s current state: `save`
Description: Saves all tasks currently on TPTM to a new file or a previously saved file.<br>
Format: `save ./data/FILE_NAME.xml`

> Key things to note:<br>
> * Name of the file must be be in a `NAME.xml` format only, the `NAME` can be alphanumerical.<br>
> * If the name of the file does not exist in `data` a new file will automatically be created. <br>
> * If the file already exists in `data`, then the saving the state into that file will overwrite the previous information and store the new state in the file. <br>
> * This function serves as the backup tool for TPTM, it is suggested that regular backups be made using this command.

Example:
* `save ./data/backup1.xml`<br>
  Saves the current state of TPTM into a file known as `backup1`.
* `save ./data/jimsLife.xml`<br>
  Saves the current state of TPTM into a file known as `jimsLife`.


<!---@@author A0139516B-->
#### Loading a saved state into TPTM: `load`
Description: Loads a user specified file that has been previously saved or has been imported to TPTM.
Format: `load ./data/FILE_NAME.xml`

> Key things to note:<br>
> * Follows the same naming convention as `save`.
> * `load` allows the user to load any file that has been saved using TPTM.
> * If the file does not exist no file will be loaded.
> * If load succeeds it will cause TPTM to shut down and upon accessing TPTM again it will reflect the state of the previously loaded file.

Examples:<br>
* `load ./data/backup1.xml`  
   Loads the file previously saved `backup1` into TPTM.
* `load ./data/TomsLife.xml`<br>
  Loads the imported file named `TomsLife` into TPTM.


<!---@@author A0139516B-->
#### Scrolling to a point on the list: `scroll`
Description: Scrolls to a designated point on TPTM's task list.
Format: `scroll POINT`

> Key thing to note:
> `POINT` can be any index number on TPTM's current list or the words `top` or `bottom`.


<!---@@author A0139516B-->
#### Clearing all entries : `clear`
Description: Clears all entries from the task manager.<br>
Format: `clear`

> Key things to note: <br>
> * This process is irreversible, and there will be a prompt before the command proceeds.<br>
> * Please only use this if you need to reset the task manager.
> * Some scenarios where this could be used is if you need to pass the task manager to someone else at your work terminal or if you are changing jobs.


<!---@@author A0139097U-->
#### Repeating a task: `repeat`
Description: Repeats an index specified task per a user defined schedule.<br>
Format: `repeat INDEX SCHEDUELE`<br>

> Key things to note:<br>
> * The `SCHEDUELE` can either be `weekly`, `monthly`, `yearly` or `off`. <br>
> * This means a task can either repeat every next week, month or year to the original deadline of the task till the user inputs off, which would terminate the task repeating. <br>
> * Completing a task that is on repeat will only complete the task for the week.<br>

Examples:
* `list`
  `repeat 3 yearly`
  Repeats the third task on the list every year after the deadline on that date till the user says to stop.
* `find CS2103`
  `repeat 1 monthly`
  Repeats the first task on the CS2103 list every month after the original deadline of the task.


<!---@@author A0141812R-->
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
