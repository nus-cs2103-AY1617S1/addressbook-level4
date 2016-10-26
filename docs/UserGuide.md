# User Guide

* [About the Task Manager](#about-the-task-manager)
* [Ouick Start Guide](#quick-start-guide)
* [Basic Command Summary](#basic-command-summary)
* [Features](#features)
* [FAQ](#faq)


## About the Task Manager

Our client Jim had a desire to have an efficient and intuitive task manager that he would be able to use to schedule his work more effectively. Jim stated that his main criteria for his own ease of use was that the task manager should function almost exclusively with a command line interface. <br> 

Therefore with this in mind our team designed this task manager, which we have named “The Practical Task Manager” or “TPTM” for short. <br>

We believe that our program will be very beneficial to any user like Jim and are thus releasing it to the public to use free of charge (with permission given by our client). So, if you need a simple program to manage your busy life TPTM is the program for you!     


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
>   1. **'list'** : lists all tasks<br>
>   2. **'add task'**` Project due for CS2103 d/121116 p/3` : adds a Task named 'Project due for CS2103' on the 12/11/2016 at a priority of level 3 to the Task Manager.<br>
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
List | `list` | N.A
Listall | `listall` | N.A
Complete | 'complete INDEX' | complete 1
Help | `help` | N.A
Select | `select INDEX` | select 4
View | `view TASKNAME` | view Reservist
Clear | `clear` | N.A

<div style="page-break-after: always;"></div>

## Features

**Command Format**
* Words in `UPPER_CASE` are the parameters.
* Items in `ANGLE_BRACKETS` are optional.
* Items with `...` after them can have multiple instances.
* The order of parameters is fixed.


#### Adding a task: `add`
Description: Adds a task to the task manager<br>
Format: `add TASKNAME s/<STARTDATE> d/<DEADLINE> p/<PRIORITY> t/<TAG>...`

> Key things to note:
> STARTDATE AND DEADLINE parameters have to be in ddmmyy [HH:MM] or dd-mm-yy [HH:MM].
> Time must be in 24-hour format.
> If no time is specified, the Task Manager will set it to a default value of 00:00.
> Tasks can have different priority levels or none at all (from 1 to 5, where 1 is the lowest priority and 5 is the highest priority).<br>
> Tasks can have any amount of tags (even 0).

Examples:
* `add complete report`
  Adds a floating task named ‘complete report’
* `add CS2103 project d/231016 p/5 t/Group`
  Adds a deadline task named ‘CS2103 project’ that is due on ‘23-10-16’ with a priority level of ‘5’ and the tag ‘Group’. 
* `add make sandwich s/111016 [12:45] d/111016 [13:00] p/5 t/hungry`                                         `
  Adds an event named ‘make sandwich’ which starts at ‘12:45’ on ’11-10-16’ due on the same day at ’13:00’ with a priority level of ‘5’ and the tag ‘hungry’

#### Editing a task: `edit`
Description: Edits the last task selected.<br>
Format: `edit INDEX INPUT <INPUT> <INPUT>`

> Key things to note:
> Edits the task by replacing the information stored with the input entered.<br>
> Inputs are the same as specified in the `add` command function.

Examples:
* `list`<br>
  `edit 3 Finish studying for EE2021 d/121116 p/4`<br>
  Edits the third task in the list of the task manager by replacing the description, changing the date nd the priority.
* `find CS2101 meeting`<br>
  `edit 1 CS2101 meeting d/131016 p/5 t/John will be late`<br>
  Added in the tag `John will be late` and changed the priority.
  
#### Deleting a task : `delete`
Description: Deletes the specified task from the task manager.<br>
**Note: This process is irreversible.**<br>
Format: `delete INDEX`

> Key things to note:
> Deletes the task at the specified `INDEX`.<br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the task manager.
* `find CS2101`<br>
  `delete 1`<br>
  Deletes the 1st task based on the results of the `find` command.

#### Finding all tasks containing any keyword in their name: `find`
Description: Finds task/s whose names contain any of the given keywords.<br>
Format: `find KEYWORD <MORE_KEYWORDS>`

> Key things to note:
> The search is not case sensitive. e.g `cs2103t` will match `CS2103T`<br>
> The order of the keywords does not matter. e.g. `Software Engineering` will match `Engineering Software`<br>
> Only the keywords are searched.<br>
> Only full words will be matched e.g. `CS2103` will not match `CS2103T`<br>
> Tasks matching at least one keyword will be returned (i.e. `OR` search).<br>
    e.g. `Software` will match `Software Engineering`

Examples:
* `find Software`<br>
  Returns `Software Engineering` but not `software`
* `find CS2103T Software Engineering`<br>
  Returns any task that has the names `CS2103T`, `Software`, or `Engineering`

#### Listing all uncomplete tasks : `list`
Format: `list`

> Key thing to note:
> `list` will show in order of tasks added.

#### Listing all tasks : `listall`
Format: `listall`

> Key thing to note:
> `listall` will show in order of tasks added.

#### Completing a task : `complete`
Description: Completes the specified task based on the task manager.
Format: `complete INDEX`

> Key thing to note:
> Completes the task at the specified `INDEX`.<br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...
> The task/event will have the words 'is completed' attached to their names (e.g. CS2103 Project is completed)
> The task/event will not appear when the command `list` is used and will only appear when `listall` is used.

Examples:
* `find MeaningOfLife`<br>
  `complete 1` <br>
  Completes the 1st task in the results of the `find` command.
* `list`<br>
  `complete 8`<br>
  Completes the 8th task in the task manager.
  
#### Viewing help : `help`
Description: Displays all commands<br>
Format: `help`

> Key thing to note:
> Help is also shown if you enter an incorrect command e.g. `abcd`

<div style="page-break-after: always;"></div>

#### Selecting a task : `select`
Description: Selects the task identified by the index number used in the last task listing.<br>
Format: `select INDEX`

> Key things to note:
> Selects the task and loads the Google search page the task at the specified `INDEX`.
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:
* `list`<br>
  `select 2`<br>
  Selects the 2nd task in the address book.<br>
* `find CS2103T` <br>
  `select 1`<br>
  Selects the 1st person in the results of the `find` command.

#### View details of a task: `view`
Description: Displays all details of a specified task or all tasks due on a certain day.
Format: `view TASKNAME` or `view DEADLINE`

Examples:
* `view CS2103T`
* `view d/121116`

<div style="page-break-after: always;"></div>

#### Exiting the program : `exit`
Description: Exits the program.<br>
Format: `exit`

#### Clearing all entries : `clear`
Description: Clears all entries from the task manager.<br>
Format: `clear`

> Please only use this if you need to reset the task manager. For example if you need to pass the task manager to someone else at your work terminal or if you are changing jobs this would be a good way to remove all your personal effects in one command.

#### Saving the data
Task Manager data is saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Task Manager folder.          

