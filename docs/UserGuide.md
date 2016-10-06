# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `DoMePlease.jar` from the 'releases' tab.
2. Copy the file to the folder you want to use as the home folder for your To-do List.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**` Feed the Parrot d/Feed timmy the parrot with corn` : 
     adds a task named `Feed the parrot` to the Floating Task in the To Do List.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`
 
#### Adding a task: `add`
Adds a floating task to DoMePlease<br>
Format: `add TASKNAME d/TASK_DESCRIPTION t/TAG...` 

Adds a deadline to DoMePlease<br>
Format: `add TASKNAME d/TASK_DESCRIPTION @/DATE TIME [t/TAG...]` 

Adds an event to DoMePlease<br>
Format: `add TASKNAME d/TASK_DESCRIPTION @/STARTDATE STARTTIME ENDDATE ENDTIME [t/TAG...]` <br>
Format: `add TASKNAME d/TASK_DESCRIPTION @/DATE STARTTIME ENDTIME [t/TAG...]` <br>
Format: `add TASKNAME d/TASK_DESCRIPTION @/STARTDATE ENDDATE TIME [t/TAG...]` <br>

\*\* <i>Refer to appendix for Possible formats for \<DATE & TIME\></i>
 
> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional, 
> items with `...` after them can have multiple instances. Order of parameters are fixed. 
> 
>
> Tasks can have any number of tags (including 0)

Examples: 
* `add Buy pencil d/Pencil to shade OAS sheet`
* `add Wash Clothes d/Wash with detergent @/27.9.2016 9pm t/!!!`
* `add Meeting d/Meet with Jim @/today 5pm 6pm t/!!! t/jim`

#### Listing all Tasks : `list`
Shows a list of all floating and dated tasks in the address book.<br>
Format: `list`

#### Finding all tasks containing any keyword in their name: `find`
Finds all tasks whose names contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> The search is case insensitive, the order of the keywords does not matter, only the name is searched, 
and task matching at least one keyword will be returned (i.e. `OR` search).
> Only full words will be matched e.g. `Program` will not match `Programming`

Examples: 
* `find EE2020`<br>
  Returns “EE2020” and “ee2020”

#### Deleting a task : `delete`
Deletes the specified task from DoMePlease. Irreversible.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd person in DoMePlease.
* `find Laundry`<br> 
  `delete 1`<br>
  Deletes the 1st person in the results of the `find` command.

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data 
DoMePlease data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous DoMePlease.
       
## Command Summary

Command | Format  
-------- | -------- 

Add | `add NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]...`

Delete | `delete INDEX`

Find | `find KEYWORD [MORE_KEYWORDS]`

List | `list`

Help | `help`

