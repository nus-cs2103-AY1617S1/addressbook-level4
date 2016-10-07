# User Guide

* [Quick Start](#quick-start)
* [Features](#features)

## Quick start
 
1. Ensure that you have the latest Java version ‘1.8.0_60’ or later installed in your 	Computer.<br>
	> Having any Java 8 version is not enough<br>
	> The application will not work for any earlier Java versions
2. Find the project in the `Project Explorer` or `Package Explorer` (usually located 	at the left side)
3. Right click on the project
4. Click `Run As` > `Java Application` and choose the `Main` class. The GUI should 	appear within split second.
5. Type the command into the command box and press <kbd>Enter</kbd> to execute the 	command. <br>
	List of commands:
	 * **`add`** : adds a task
	 * **`show`** : shows all tasks
	 * **`find`** : searches for a task
	 * **`edit`** : edits a task
	 * **`delete`** : deletes a task

7.  Refer to the [Features](#features) section below for details of each command.<br>


## Features

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

#### Adding a task : `add`

Description: Adds a task to the planner <br>
Format:<br> 
`add TASKNAME DATE(optional)` <br>
`add TASKNAME DATE(optional) STARTTIME to ENDTIME isRECURRING(optional)`<br>
`add TASKNAME DATE(optional) by ENDTIME(optional) isRECURRING(optional)` <br>

Examples:<br>
`add gym today` <br>
Floating task with no specified time is added<br><br>
`add meeting tomorrow 2pm to 4pm` <br>
Fixed task is added <br><br>
`add math homework by 6pm` <br>
Task with a deadline, no date is specified so today’s date is assumed<br>

>All tasks will be stored with a date. If user adds a task with no date specified, it is assumed the task is to be done today. 
>Daily Planner splits all tasks into three categories - floating tasks(tasks which have no start time or end time), fixed tasks(tasks with a specified start time and end time) and deadline tasks(tasks with only an end time). 
>Daily Planner will use this categorization to automatically create the user’s schedule.


#### Viewing a schedule : `show`

Description: Shows tasks on a particular day

Format: `show DATE(optional)`

Examples:<br>
`show today`<br>
Shows schedule for today<br><br>
`show next wednesday` <br>
Shows schedule for next wednesday <br>

>The show function will generate the user’s schedule when queried. Starting from the current time, it will consider all tasks in hand for the day and assign them to timeslots. 
>Urgent tasks will be scheduled first(tasks with nearing deadlines). Fixed tasks will be scheduled only during their specified timeslot and floating tasks will be inserted to remaining empty timeslots throughout the day. Daily Planner will even account for breaks in between certain hours of consecutive tasks(say, every 3 hours). 
>The user can then use this recommended schedule to worryless-ly go about their day. 


#### Searching for a task: `find`

Description: Searches for a particular task and displays more information about it.<br>

Format: `find TASKNAME`
> * The search is case insensitive.
> * The order of the keywords does not matter. e.g. `math assignment` will match `assignment math`
> * Only the name is searched.
> * Task matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Hans` will match `Hans Bo`

Examples:
`find cs lecture` <br>

#### Editing a task: `edit`

Description: edits a particular task’s details<br>
Format:<br>
`edit INDEX DATE(optional)`<br> 
	`NEWTASKNAME(optional) NEWDATE(optional) NEWSTARTTIME(optional) to NEWENDTIME(optional)`<br>
Examples:<br>
`edit 2 tomorrow`<br>
  `wednesday 4pm to 6pm`<br>
(only changes date and time)<br>
  
#### Deleting a task : `delete`

Description: Deletes a task from the planner. <br>

Format: `delete INDEX DATE(optional)` 

> Deletes the task at the specified `INDEX`. 
  The index refers to the index number shown on that day's schedule listing<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: <br>
*`Delete 5 12 oct`<br>
Deletes task 5 of 5th october <br>
*`Delete 1`<br> 
No date specified, so deletes first task today <br>
 

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data 
Daily Planner data is saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.













