<center><h1>  Welcome to Menion </h1> </center>



# Table of Contents

* [Introduction](#Introduction)
* [Quick Start](#quick-start)
* [Features](#features)
  * [Add Activity](#adding-an-activity)
  * [Delete Activities](#deleting-an-activity)
  * [Edit Activities](#editting-an-activity)
  * [List Activities](#listing-all-activities)
  * [Find](#finding-all-activities-containing-any-keyword-in-their-name)
  * [Undo]
  * [Mark Completed Activities]
  * [Modify Storage Path](#modifying-the-storage-path)
  * [Help](#viewing-help)
  * [Exit](#exiting-the-program)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Introduction

Ever felt overwhelmed from the multitude of tasks you have to complete and have no idea where to start? Are you looking for an easy to work with application to help you track all your activities? Well look no further! Your very own Menion is here to assist you!

Menion is your personal assistant that tracks all your activities and displays them in a simple to read display. It saves you the hassle of remembering what needs to be done and is able to help you prioratise your tasks.

Unlike other complicated task managers, Menion is simple and intuitive. It relies completely on the keyboard and only requires a single line of command, removing the inconvenience of clicking and navigating through multiple interfaces. It also has a flexible command interface, accepting many variations of the same command, removing the need to memorise a certain format for every command. You don't even have to read this to start using Menion! It's that easy!
## Quick Start

1. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
2. Download Menion: You can then download TaskManager.jar from the latest release here: 
   **COMING SOON**
3. Copy the file to the folder you want to use as the home folder for  Menion.
4. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/MainPageLayout.jpg" width="600">

5. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
6. Some example commands you can try:
   * **`list`** : lists all contacts
   * **`add`**` Assignment 2 TASK_DEADLINE TASK_REMINDER PRIORITY NOTES...` : 
     adds a task named `Assignment 2` to the Menion, with high priority.
   * **`delete`**` 3` : deletes the 3rd task / event shown in the current list
   * **`exit`** : exits the app
7. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**<br>
> Words in `UPPER_CASE` are the parameters.<br>
> Items in `[SQUARE_BRACKETS]` are optional.<br>
> Items with `...` after them can have multiple instances.<br>
> The order of parameters is fixed.

 
#### Adding an activity
Adds an activity to Menion<br>
Format : <br>
`add TASK_NAME by TASK_DEADLINE [n:NOTES...]` <br>
`add EVENT_NAME from EVENT_START_TIME EVENT_START_DATE to EVENT_END_TIME EVENT_END_DATE [n:NOTES...]`<br>
`add FLOATING_TASK_NAME [n:NOTES...]`<br> 

> Task and event are differentiated by the input of time tag. Each activity can have any number of notes (including 0). Each note is limited to 140 characters.


Examples: 
* `add Upload CS3230 Programming Assignment 2 d/16-10-16 r/false p/low n/Upload it onto Coursemology Portal`
* `add Dinner With Family d/21-11-16 t/1800-2000 r/true p/high n/Wear formal`

#### Deleting an activity
Deletes the specified task/event  from the Menion. Irreversible.<br>
Format : `delete INDEX`

>Deletes the task/event at the specified `INDEX`. The index refers to the index number shown beside it.

The index must be a positive integer 1,2,3,...

Examples:
* `delete 2`
* `deletes the 2nd task/event in the Menion.`

#### Editting an activity
Updates a specified task from the Menion.

Format : `edit INDEX`
> Edits the task at the specified `INDEX`. The index refers to the index number shown beside the task. The order of the tag to edit is not important.

> The index must be a positive integer 1,2,3, ...

Examples :
* `edit 3`
* `d/dd-mm-yy p/low`
* `edit 2`
* `r/false d/dd-mm-yy`
* `edit 5`
* `t/2030-2200`

#### Listing all activities
Shows a list of all activities in the Menion.<br>
Format : `list all`

#### Listing all events/tasks/floating tasks
Shows a list of all events/tasks/floating tasks in the Menion for the day sorted according to the start date and time.<br>
Format : <br> 
`list event` <br>
`list task` <br>
`list floatingTask` <br>

> Displays an individual list consisting of either events, tasks, or floating tasks for the day.

#### Listing all activities of the specified time period
Shows a list of all activities in the Menion for the specified time period: day, week, month, date.<br>
Format : <br>
`list DAY` <br>
`list WEEK` <br>
`list MONTH` <br>
`list DATE` <br>

Examples:
* `list MONDAY`
* `list WEEK`
* `list JANUARY`
* `list 12/3/2016`

#### Listing all events and tasks of the specified range of dates
Shows a list of all tasks and events in the Menion for the range of days sorted according to date.<br>
Format : `list day to day`

Examples:
* `list 10-10-16 to 20-10-16`


#### Finding all activities containing any keyword in their name
Finds all tasks whose names contain any of the given keywords.<br>
Format : `find KEYWORD [MORE_KEYWORDS]`
>* The search is not case sensitive. e.g. `sleep` will match `Sleep`
>* The order of the keywords does not matter. e.g. `Pack Bag` will match `Bag Pack`
>* Only the name of activity is searched.
>* Only full words will be matched e.g. `sleep` will not match `sleeping`
>* Activity name matching at least one keyword will be returned (i.e `OR` search). e.g. `sleep` will match `sleep for 8 hours`

Examples
* find `Sleep`
* Displays : sleep for 8 hours
* find Go to gym
* Displays: any task having the keywords go, to, gym


#### Clearing all entries
Clear all entries from the Menion.<br>
Format : `clear`

#### Modifying the storage path
Modify the storage path that stores all the data.<br>
Format : `modify storage path STORAGE_LOCATION`

#### Viewing help
Format : `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

#### Exiting the program
Exits the program.
Format : `exit`

> Help is also shown if you enter an incorrect command e.g. `abcd`

#### Saving the data
Menion data are saved in the hard disk automatically after any command that changes the data.
There is no need to save manually.<br>



## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Menion folder.
       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `add TASK_NAME d/DEADLINE_TASK r/TASK_REMINDER p/PRIORITY n/NOTES...`
Clear | `clear`
Delete | `delete INDEX`
Exit | `exit`
Find | `find KEYWORD [MORE_KEYWORDS]`
List | `list`
Help | `help`
Modify Storage Path | `modify storage path STORAGE_LOCATION`
