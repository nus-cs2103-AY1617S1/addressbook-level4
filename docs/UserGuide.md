# User Guide

1. [Introduction](#1-introduction)
2. [Quick Start](#2-quick-start)
3. [Features](#3-features)
4. [FAQ](#4-faq)
5. [Command Summary](#5-command-summary)

## 1. Introduction 

#### 

## 2. Quick Start

&nbsp;&nbsp;&nbsp;&nbsp; <b>2.1</b> Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
 > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
&nbsp;&nbsp;&nbsp;&nbsp; <b>2.2</b> Download the latest `DoMePlease.jar` from the 'releases' tab. <br>
&nbsp;&nbsp;&nbsp;&nbsp; <b>2.3</b> Copy the file to the folder you want to use as the home folder for your To-do List. <br>
&nbsp;&nbsp;&nbsp;&nbsp; <b>2.4</b> Double-click the file to start the app. The GUI should appear in a few seconds. <br>
   <img src="images/Ui.png" width="600"> <br>
&nbsp;&nbsp;&nbsp;&nbsp; <b>2.5</b> Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
>   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.

&nbsp;&nbsp;&nbsp;&nbsp; <b>2.6</b> Some example commands you can try:
>    `add Feed the Parrot d/Feed timmy the parrot with corn` : Add a floating task.
    `listall` : List all the floating tasks, deadline and events in the application.
    `delete 3` : Deletes the 3rd task shown in the current list.
    `exit` : Exits the application.
    
&nbsp;&nbsp;&nbsp;&nbsp; <b>2.7</b> Refer to the [Features](#3-features) section below for details of each command.<br>


## 3. Features

&nbsp;&nbsp;&nbsp;&nbsp; <b>3.1 Viewing help : `help` </b><br>
>Format: `help` <br>
 Help is also shown if you enter an incorrect command e.g. `abcd`
 
&nbsp;&nbsp;&nbsp;&nbsp; <b>3.2 Adding a task: `add` </b><br>
>Adds a floating task to DoMePlease<br>
Format: `add TASKNAME d/TASK_DESCRIPTION t/TAG...` 

>Adds a deadline to DoMePlease<br>
Format: `add TASKNAME d/TASK_DESCRIPTION @/DATE TIME [t/TAG...]` 

>Adds an event to DoMePlease<br>
Format: `add TASKNAME d/TASK_DESCRIPTION @/STARTDATE STARTTIME ENDDATE ENDTIME [t/TAG...]` <br>
Format: `add TASKNAME d/TASK_DESCRIPTION @/DATE STARTTIME ENDTIME [t/TAG...]` <br>
Format: `add TASKNAME d/TASK_DESCRIPTION @/STARTDATE ENDDATE TIME [t/TAG...]` <br>

\*\* <i>Refer to appendix for Possible formats for DATE & TIME</i>
 
> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional, 
> items with `...` after them can have multiple instances. Order of parameters are fixed. 
> 
>
> Tasks can have any number of tags (including 0)

>Examples: 
* `add Buy pencil d/Pencil to shade OAS sheet`
* `add Wash Clothes d/Wash with detergent @/27.9.2016 9pm t/!!!`
* `add Meeting d/Meet with Jim @/today 5pm 6pm t/!!! t/jim`

&nbsp;&nbsp;&nbsp;&nbsp; <b>3.3 Edit task in the application: `edit` </b><br>

>Edit the details of the floating task, deadline and event. <br>
Format: `edit INDEX`

> Edits the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

>Example:
* `listall`<br>
*  `edit 2`<br>
*  Edit the 2nd floating task in DoMePlease. Example Buy Pencil.
*  The format whereby the 2nd floating task was entered by user in the past will be populated on the command line.
* `add Buy pencil d/Pencil to shade OAS sheet` <br>
* User will edit the inputs accordingly and submit the edit field.
* A message will be displayed to inform the user that the task is edited.

&nbsp;&nbsp;&nbsp;&nbsp; <b>3.4 Listing all tasks : `listall` </b><br>
>Shows a list of all floating tasks, deadline and events in the application.<br>
Format: `listall`

&nbsp;&nbsp;&nbsp;&nbsp; <b>3.5 Listing all overdue deadlines: `listod` </b><br>
>Shows a list of all overdue deadlines. <br>
Format: `listod`

&nbsp;&nbsp;&nbsp;&nbsp; <b>3.6 Listing all completed tasks: `listdone` </b><br>
>Shows a list of completed floating tasks, deadlines and events. <br>
Format: `listdone`

&nbsp;&nbsp;&nbsp;&nbsp; <b>3.6 Finding all floating tasks, deadlines and events containing any keyword in their name and tags: `find` </b><br>
>Finds all floating tasks, deadlines and events which names and tags contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> The search is case insensitive, the order of the keywords does not matter, only the name and tag is searched, 
and task matching at least one keyword will be returned (i.e. `OR` search). <br>
> Only full words will be matched e.g. `Program` will not match `Programming`.

>Examples: 
* `find EE2020`<br>
*  Returns “EE2020” and “ee2020”
  
&nbsp;&nbsp;&nbsp;&nbsp;<b>3.7 Navigating through Calendar: `select` </b><br>
>Populate the list of deadlines and events on the input DATA of current month on the calendar panel <br>
Format: `select DATE`

> The calendar panel of the application will list out all the deadlines on the day and events that start, ends or is on-going on the date. <br>

>Examples:
* `select 5` <br>
* Assume that the calendar panel on the application is on the month of June.
* Output the full list of deadlines on the day and events that start, ends or is on-going on 5th June.

&nbsp;&nbsp;&nbsp;&nbsp;<b>3.8 Deleting a task : `delete` </b><br>
>Deletes the specified task from DoMePlease. Irreversible <br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

>Examples: 
* `listall`<br>
*  `delete 2`<br>
*  Deletes the 2nd task in DoMePlease.
* `find Laundry`<br> 
*  `delete 1`<br>
*  Deletes the 1st person in the result of the `find` command.
  
&nbsp;&nbsp;&nbsp;&nbsp;<b>3.9 Undoing a move: `undo` </b><br>
>Undo the previous command entered by user.<br>
A message will be displayed to inform the user that the previous command has been undone. <br>
Format: `undo`

&nbsp;&nbsp;&nbsp;&nbsp;<b>3.10 Marking floating tasks and deadlines as done: `done` </b><br>
>Marking a completed a floating task and deadline as done. <br>
Format: `done INDEX`


> Marks the floating tasks or deadline as done at the specified `index` <br>
  The index refers to the index number shown in the most recent listing. <br>
  The index **must be a positive integer** 1,2,3, .. <br>
  The completed task will be moved to the archive list <br>

>Examples: 
* `list` <br>
  `done 2` <br>
   Mark the 2nd task in DoMePlease as completed.
  

&nbsp;&nbsp;&nbsp;&nbsp; <b>3.11 Exiting the program : `exit` </b><br>
>Exits the program.<br>
Format: `exit`  

&nbsp;&nbsp;&nbsp;&nbsp; <b>3.12 Saving the data </b><br>
>DoMePlease data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 4. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous DoMePlease.
       
## 5. Command Summary

Command | Format  
-------- | -------- 

Add | `add TASKNAME d/TASK_DESCRIPTION @/DATE TIME [t/TAG...]`

Delete | `delete INDEX`

Find | `find KEYWORD [MORE_KEYWORDS]`

List | `listall` , `listod`, `listdone`

Help | `help`

Select | `select INDEX`

Done | `done INDEX`

Undo | `undo`

Edit | `edit INDEX`