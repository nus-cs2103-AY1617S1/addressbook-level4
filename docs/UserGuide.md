# User Guide

1. [Introduction](#1-introduction)
2. [Quick Start](#2-quick-start)
3. [Features](#3-features)
4. [FAQ](#4-faq)
5. [Command Summary](#5-command-summary)
6. [Appendix](#6-appendix)

## 1. Introduction 

&nbsp;&nbsp;&nbsp;&nbsp; Have you ever felt like there are too many tasks to do, and you are unable to remember all of them? Or feel that your calendar is overflowing with sticky notes on the tasks to be done each day? Have no fear, as our all-in-one task management application, DoMePlease, is here to save your day!

DoMePlease manages the different types of tasks that you will encounter in your daily life, be it a deadline for submission, or even a date with your significant other, this application can show you what all the tasks you have in a month in one glance, or even a list of tasks for a specific day. DoMePlease also manages your list of ad-hoc tasks, which are non-dated tasks such as "Read the new Harry Potter book!", and displays them beautifully and neatly at the side of the application, so you can refer to them any time you have some free time.

Love typing? You will love DoMePlease, as you only need to use the keyboard to type simple commands to manage your tasks.

#### 

## 2. Quick Start

&nbsp;&nbsp;&nbsp;&nbsp; <b>2.1</b> Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
 > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
&nbsp;&nbsp;&nbsp;&nbsp; <b>2.2</b> Download the latest `DoMePlease.jar` from the 'releases' tab. <br>
&nbsp;&nbsp;&nbsp;&nbsp; <b>2.3</b> Copy the file to the folder you want to use as the home folder for DoMePlease. <br>
&nbsp;&nbsp;&nbsp;&nbsp; <b>2.4</b> Double-click the file to start the app. The GUI should appear in a few seconds. <br>
   <img src="images/Ui.png" width="600"> <br>
&nbsp;&nbsp;&nbsp;&nbsp; <b>2.5</b> Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
>   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.

&nbsp;&nbsp;&nbsp;&nbsp; <b>2.6</b> Some example commands you can try:
>    `add Walk the dog d/Don't forget to pick up poo` : Add a floating task.
    `list all` : List all the floating tasks, deadline and events in the application.
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
Format: `add TASKNAME d/TASK_DESCRIPTION date/DATE TIME [t/TAG...]` 

>Adds an event to DoMePlease<br>
Format: `add TASKNAME d/TASK_DESCRIPTION date/STARTDATE STARTTIME to ENDDATE ENDTIME [t/TAG...]` <br>
Format: `add TASKNAME d/TASK_DESCRIPTION date/DATE STARTTIME to ENDTIME [t/TAG...]` <br>

\*\* <i>Refer to appendix for possible formats for DATE & TIME</i>
 
> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional, 
> items with `...` after them can have multiple instances. Order of parameters are fixed. 
> 
>
> Tasks can have any number of tags (including none)

>Examples: 
* `add Buy pencil d/Pencil to shade OAS sheet`
* `add Wash Clothes d/Wash with detergent date/27-9-2016 9pm t/!!!`
* `add Meeting d/Meet with Jim date/today 5pm to 6pm t/!!! t/jim`

&nbsp;&nbsp;&nbsp;&nbsp; <b>3.3 Listing all tasks : `list all` </b><br>
>Shows a list of all uncompleted floating tasks, deadline and events in the application.<br>
Format: `list all`

&nbsp;&nbsp;&nbsp;&nbsp; <b>3.4 Listing all overdue deadlines: `list od` </b><br>
>Shows a list of all overdue deadlines. <br>
Format: `list od`

&nbsp;&nbsp;&nbsp;&nbsp; <b>3.5 Listing all completed tasks: `list done` </b><br>
>Shows a list of completed floating tasks, deadlines and events. <br>
Format: `list done`

&nbsp;&nbsp;&nbsp;&nbsp; <b>3.6 Edit task in the application: `edit` </b><br>

>Edit the details of the floating task, deadline and event. <br>
Format: `edit INDEX [FIELDS]`

> Edits the field of the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

>Example:
* `list all`<br>
*  `edit 2 d/Pilot 2B`<br><br>
Edits the 2nd floating task in DoMePlease. Example Buy Pencil. <br>
A message will be displayed to inform the user that the task is edited.

&nbsp;&nbsp;&nbsp;&nbsp; <b>3.7 Finding all floating tasks, deadlines and events containing any keyword in their name and tags: `find` </b><br>
>Finds all floating tasks, deadlines and events which names and tags contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> The search is case insensitive, the order of the keywords does not matter, name, description, date, time and tag is searched, and task matching at least one keyword will be returned (i.e. `OR` search). <br>
> Only full words will be matched e.g. `Program` will not match `Programming`.

>Examples: 
* `find EE2020`<br>
*  Returns “EE2020” and “ee2020” <br><br>

>* `find 2020`<br>
*  Returns nothing
  
&nbsp;&nbsp;&nbsp;&nbsp;<b>3.8 Navigating through Calendar: `view` </b><br>
>Populate the list of deadlines and events of the selected DATE <br>
Format: `view DATE`

> The calendar panel of the application will list out all the deadlines on the day and events that start, ends or is on-going on the date. <br>

>Examples:
* `view 5` <br>
* Output the full list of deadlines on the day and events that start, ends or is on-going on 5th of the current month view

>* `view 5-10-2016` <br><br>
This command doesn't require you to be at the selected month.
Output the full list of deadlines on the day and events that start, ends or is on-going on 5th October 2016.

&nbsp;&nbsp;&nbsp;&nbsp;<b>3.9 Deleting a task : `delete` </b><br>
>Deletes the specified task from DoMePlease. <br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

>Examples: 
* `list all`<br>
*  `delete 2`<br><br>
Deletes the 2nd floating task in all the listed. <br><br>

>* `find Laundry`<br> 
*  `delete 11`<br><br>
Deletes the 1st event or deadline task in the result of the `find` command.<br>
  
&nbsp;&nbsp;&nbsp;&nbsp;<b>3.10 Undoing a move: `undo` </b><br>
>Undo the previous command entered by user. (up to 3)<br>
A message will be displayed to inform the user that the previous command has been undone. <br>
Format: `undo`

&nbsp;&nbsp;&nbsp;&nbsp;<b>3.11 Marking floating tasks and deadlines as done: `done` </b><br>
>Marking a completed a floating task and deadline as done. <br>
Format: `done INDEX`

> Marks the floating tasks or deadline as done at the specified `index` <br>
  The index refers to the index number shown in the most recent listing. <br>
  The index **must be a positive integer** 1,2,3, .. <br>
  The completed task will be moved to the archive list <br>

>Examples: 
* `list all` <br>
  `done 2` <br>
   Mark the 2nd task in DoMePlease as completed.

&nbsp;&nbsp;&nbsp;&nbsp;<b>3.12 Change location of the data storage file: `save` </b><br>
>Moves the data storage file to the specified location if possible. <br>
Format: `save FOLDERPATH`


> Moves the data storage location to the specified `FOLDERPATH` <br>
  The folder path you specify must be able to be created in your device.<br>
  The path you specify cannot be a file path eg. `C:\Users\Public\Desktop\data.txt`. <br>
  The data storage file will only exist in the location you specified and the old copy will be deleted. <br>

>Examples: 
* `save data\firstcopy` <br>
   Moves the data storage file from its previous location to ../data/firstcopy/.
  

&nbsp;&nbsp;&nbsp;&nbsp; <b>3.13 Exiting the program : `exit` </b><br>
>Exits the program.<br>
Format: `exit`  

## 4. FAQ
**Q**: Do I need to save manually?
**A**: Data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous DoMePlease.
       
## 5. Command Summary

Command | Format  
-------- | -------- 

Help | `help`
- Shows a help file

Add | `add TASKNAME d/TASK_DESCRIPTION @/DATE TIME [t/TAG...]`
- Inserts a task into DoMePlease

Delete | `delete INDEX`
- Deletes a task from DoMePlease

Edit | `edit INDEX [FIELDS]`
- Edits the field of the task at the specified `INDEX`

Find | `find KEYWORD [MORE_KEYWORDS]`
- Finds an existing task based on the TASK_NAME or TASK_TAG

List | `list all` , `list od`, `list done`
- Lists all/overdue/completed tasks in DoMePlease

View | `view DATE`
- Populates the list of deadlines and events of the selected DATE

Done | `done INDEX`
- Marks the selected task as completed

Save | `save FOLDERPATH`
- Changes location of storage data to specified folder

Undo | `undo`
- Reverts the last reversible action (up to 3)

## 6. Appendix

Possible Date formats
DD-MM-YY 	: 18-10-16 
DD-MM-YYYY  : 27-2-2101
DD MMM YYYY : 15 MAY 2103
relative 	: today || tmr || next tuesday

Not accepted Date formats
DD-MM
DD.MM
DD.MM.YY
DD.MM.YYYY

Possible Time formats
24HR : 2359
am/pm : 2.30pm
relative : 2 hours later || 30 minutes later 

Not accepted Time formats 
2500
230pm

This is not an exhaustive list of formats, please visit the following website for more information
http://natty.joestelmach.com/doc.jsp
