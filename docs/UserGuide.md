[//]: # (@@author A0146752B)
<center><h1> Menion User Guide </h1> </center>



# Table of Contents
* [Introduction](#introduction)
* [Quick Start](#quick-start)
* [Features](#features)
  * [Add Activity](#adding-an-activity)
  * [Delete Activities](#deleting-an-activity)
  * [List Activities](#listing-all-activities)
  * [Clear Activities](#clearing-all-entries)
  * [Edit Activities](#editing-an-activity)
  * [Complete Activities](#complete-an-activity)
  * [Uncomplete Activities](#uncomplete-an-activity)
  * [Undo Command](#undo)
  * [Redo Command](#redo)
  * [Modify Storage Path](#modifying-the-storage-path)
  * [Help](#viewing-help)
  * [Exit](#exiting-the-program)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Introduction
Ever felt overwhelmed from the multitude of tasks you have to complete and have no idea where to start? Are you looking for an easy to work with application to help you track all your activities? Well look no further! Your very own Menion is here to assist you!

Menion is your personal assistant that tracks all your activities and displays them in a simple to read display. It saves you the hassle of remembering what needs to be done and is able to help you prioritise your tasks.

Unlike other complicated task managers, Menion is simple and intuitive. It relies completely on the keyboard and only requires a single line of command, removing the inconvenience of clicking and navigating through multiple interfaces. It also has a flexible command interface, accepting many variations of the same command, removing the need to memorise a certain format for every command. 

Let's get started!
## Quick Start

1. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
2. Download Menion: You can download Menion.jar from the latest release here: 
   **COMING SOON**
3. Copy the file to the folder you want to use as the home folder for  Menion.
4. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/MainPageLayout.jpg" width="600">

5. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
6. Some example commands you can try:
   * **`list`** : lists all contacts
   * **`add`**` Assignment 2 by: TASK_DEADLINE_DATE TASK_DEADLINE_TIME n: NOTES` : 
     adds a task named `Assignment 2` to Menion.
   * **`delete`**`event 3` : deletes the 3rd event shown in the current list
   * **`exit`** : exits the app
7. Refer to the [Features](#features) section below for details of each command.<br>


##Features

> **Command Format**<br>
> Words in `UPPER_CASE` are the parameters.<br>
> Items with `...` after them can have multiple instances.<br>
> The order of parameters is fixed.


> **Important** (change to diagram) <br> 
> Menion supports 3 types of activities. Tasks, Events and Floating Tasks. 
> <li style="padding-top:1px">Task has a deadline.
> <li>Event has a starting date/time and an ending date/time.
> <li> Floating Task does not have any dates attached to it.

[//]: # (@@author A0139277U)

#### Adding an activity `add`
Adds an activity to Menion<br>

Formats : <br>
`add TASK_NAME by TASK_DEADLINE_DATE TASK_DEADLINE_TIME n:NOTES...` <br>
`add EVENT_NAME from: EVENT_START_DATE EVENT_START_TIME to: EVENT_END_DATE EVENT_END_TIME n:NOTES...`<br>
`add FLOATING_TASK_NAME n:NOTES...`<br> 
> Task and event are differentiated by the input of time tag. Each activity can have any number of notes (can be left blank).
<br><br>
> DATE and TIME Formats
> 
> - Formal dates : mm-dd-yyyy hhmm, mm/dd/yyyy hhmm
> - Informal dates : tomorrow, next monday 12pm
> 

Examples: <br>
`add Upload CS3230 Programming Assignment 2 by: 08-12-2016 1900 n: important!`<br>
`add Dinner With Family from: tomorrow 1900 to: tomorrow 2000 n: bring flowers`<br>
`add Buy lunch n: hawker food`

#### Deleting an activity `delete`
Deletes an activity from Menion at the specified `INDEX`.<br>
Format: 
`delete ACTIVITY_TYPE INDEX`

>The `Index` refers to the index number shown beside the activity.<br>
>The index must be a positive integer 1,2,3,...<br>
>There are 3 `ACTIVITY_TYPE`: event, task, floating


Examples:<br>
`delete event 2`<br>
`delete task 2`<br>
`delete floating 2`



#### List Activities `list`
Shows a list of activities in Menion for specified parameters such as date, month, completion status and keywords.

Formats: <br>
`list all` <br>
`list MONTH` <br>
`list DATE` <br>
`list KEYWORDS` <br>
`list COMPLETION_STATUS` <br>

Examples:<br>
`list all`<br>
`list january`<br>
`list 08-18-2016`<br>
`list cs2103t`<br>
`list completed`<br>

> Listing parameters are case-insensitive. Cs2103T will match cs2103t. 


#### Clearing all entries `clear`
Clear all entries from Menion.<br>

Format : `clear`


[//]: # (@@author A0139164A)

#### Editing an activity `edit`
Edits an activity from Menion at the specified `INDEX`.

Format : `edit ACTIVITY_TYPE INDEX PARAMETERS`
> The index refers to the index number shown beside the activity. <br>
> The input parameters are the same as Add command. <br>
> The index must be a positive integer 1,2,3, ...

Examples :

`edit event 3 by: 19-08-2016 1900`<br>
`edit task 4 n: buy extra stuff`<br>
`edit task 1 name Hello World`


#### Complete an activity `complete`
Marks an activity as completed.

Format : `complete ACTIVITY_TYPE ACTIVITY_INDEX`

Examples : 

`complete event 3`<br>
`complete task 3`


#### Uncomplete an activity `uncomplete`
Marks an activity as uncompleted.

Format : `uncomplete ACTIVITY_TYPE ACTIVITY_INDEX`

Example : 

`uncomplete event 3`<br>
`uncomplete task 3`

#### Set reminder for tasks `remind`
Allows menion to send notifications to specified `EMAIL_ADDRESS` for uncompleted overdue tasks.

Format: `remind EMAIL_ADDRESS`

Example:
`remind jondoe@gmail.com`

#### Unset reminder for tasks `unremind`
Disallows menion to send notifications to previously specified `EMAIL_ADDRESS` for uncompleted overdue tasks.

Format : `unremind`


[//]: # (@@author A0139515A)

#### Undo `undo`
Undo the most recent command.

Format : `undo`

#### Redo `redo`
Redo the most recent command.

Format : `redo`


#### Modifying the storage path `modify`
Modify the storage path that stores all the data.<br>

Format : `modify STORAGE_LOCATION`

Example:

`modify storage path user/Desktop`

#### Viewing help `help`
Shows a list of available commands and how to use them.<br>

Format : `help`


#### Exiting the program `exit`
Exits the program.<br>

Format : `exit`

#### Saving the data
Menion data are saved in the hard disk automatically after any command that changes the data.
There is no need to save manually.<br>



## FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Menion folder.
       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `add FLOATING_TASK_NAME n:NOTES...`<br>`add TASK_NAME by: TASK_DEADLINE_DATE TASK_DEADLINE_TIME n:NOTES...`<br>`add EVENT_NAME from: EVENT_START_DATE EVENT_START_TIME to: EVENT_END_DATE EVENT_END_TIME n:NOTES...`
Delete | `delete ACTIVITY_TYPE INDEX`
List | `list` <br> `list DATE` <br> `list MONTH` <br> `list KEYWORDS`
Clear | `clear`
Edit | `edit ACTIVITY_TYPE ACTIVITY_INDEX ACTIVITY_PARAMETER_TO_CHANGE ACTIVITY_PARAMETER_CHANGES`
Complete | `complete ACTIVITY_TYPE INDEX`
Uncomplete | `uncomplete ACTIVITY_TYPE INDEX`
Set Reminder | `remind EMAIL_ADDRESS`
Unset Reminder | `unremind`
Undo| `undo`
Redo | `redo`
Modify Storage Path | `modify STORAGE_LOCATION`
Help | `help`
Exit | `exit`


## GLOSSARY

Word | Meaning  
-------- | :-------- 
GUI | Graphic User Interface. <br> The interface presented to users to interact with the program.
Storage Path | This is the directory where your data will be saved.


