# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `happyjimtaskmanager.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Address Book.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/UI.png" width="800">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : Lists all active tasks.
   * **`add`**` Homework by 24 sep 6pm : 
     adds a task named `Homework` to the Task Master .
   * **`delete`**` 212` : deletes the task with ID 212 shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

Example:
* `help` 

<!-- @@author A0135782Y -->
#### Adding a floating task: `add`
Adds a task to the todo list<br>
Format:`add TASK_NAME [t/TAG]...` 

Examples:
* `add Homework` 
* `add Homework t/CS1231` <br>
	<img src="images/ug_add_floating_before.PNG" width="600">
	<img src="images/ug_add_floating_after.PNG" width="600">

#### Adding a task with deadline: `add`
Format: `add TASK_NAME by DATE TIME [RECURRING_TYPE] [t/TAG]...`

> `RECURRING_TYPE` consists of daily, weekly, monthly and yearly case insensitive. 
> Tasks can have only 1 `RECURRING_TYPE`.
> If multiple `RECURRING_TYPE` are used, only the first instance will be accepted.

Examples: <br>
* `add Homework by 24 sep 8pm t/CS1231`
* `add Homework by 24 sep 6pm daily t/CS1231`
	<img src="images/ug_add_by_date_before.PNG" width="600">
	<img src="images/ug_add_by_date_after.PNG" width="600">

#### Adding a task with start time and end time: `add`
Format: `add TASK_NAME from DATE TIME to DATE TIME [RECURRING_TYPE] [t/TAG]...`

> `RECURRING_TYPE` consists of daily, weekly, monthly and yearly case insensitive. 
> Tasks can have only 1 `RECURRING_TYPE`.
> If multiple `RECURRING_TYPE` are used, only the first instance will be accepted.

Examples:
* `add Homework from 24 sep 8pm to 25 sep 9pm tag/CS1231`
* `add Homework from today 8.03pm to today 8.15pm t/CS1231`

   <img src="images/ug_add_fromto_date_before.PNG" width="600">
   <img src="images/ug_add_fromto_date_after.PNG" width="600">
* `add Homework from 26 oct 10am to 26 oct 11am daily`

   <img src="images/ug_add_fromto_date_recurring_before.PNG" width="600">
   <img src="images/ug_add_fromto_date_recurring_after.PNG" width="600"> <br>
<!-- @@author -->

#### Lists all active tasks : `list`
Format: list

Examples: 
* `list`

<!--@@author A0147995H-->

#### Edit tasks : `edit`
Format: `edit TASK_ID [NEW_TASK_NAME] [from DATE_TIME to DATE_TIME | by DATE_TIME [daily | weekly | monthly | yearly] ] [tag/EDIT_TAG]...`

> Every field in edit is optional. After you specify the task that you are going to edit,
> you are able to change its name, date time and tag.
> For editing date time of a task, you have the following restrictions:
> 1. You cannot change a non-floating task to a floating task.
> 2. You cannot directly change recurring type of a task (need to specify time first).

Examples: 
* `edit 1 cs2103 webcast`<br>
   <img src="images/before_edit.png" width="600">
   <img src="images/edit_command_1.png" width="600">
* `edit 1 t/study`<br>
   <img src="images/edit_command_2.png" width="600">
* `edit 1 from today 4pm to today 5pm`<br>
   <img src="images/edit_command_3.png" width="600">
* `edit 2 by today 7pm`<br>
   <img src="images/edit_command_4.png" width="600">
* `edit 1 from today 4pm to today 5pm daily`<br>
   <img src="images/edit_command_5.png" width="600">

#### Delete tasks : `delete`
Format: delete TASK_ID

Examples:
* `delete 2`

<!--@@author A0147967J-->

#### Archive completed tasks : `done`
Format: done TASK_ID

Examples:
* `done 5`

   <img src="images/beforedone.png" width="600">
   <img src="images/afterdone1.png" width="600">
   <img src="images/afterdone2.png" width="600">
   >Completed tasks can be viewed from navigation bar on the side.

#### Block out timeslot : `block`
Format: block from [START_DATE] START_TIME to [START_DATE] START_TIME [t/TAG] 

Examples:
* `block from tomorrow 3pm to tomorrow 5pm t/meeting`

 > <img src="images/beforeblock.png" width="600">
 > <img src="images/afterblock.png" width="600">

#### Undo tasks : `undo`
Format: u

> Maximum 3 undo

Examples: 
* `u`

   <img src="images/beforeundo.png" width="600">
   <img src="images/afterundo.png" width="600">

#### Redo tasks : `redo`
Format: r

> Maximum 3 redo

Examples: 
* `r`

   <img src="images/beforeredo.png" width="600">
   <img src="images/afterredo.png" width="600">

#### View agenda of a day: `view`
Format: view DATE [TIME]

Examples:
* `view next monday`

   <img src="images/beforeview.png" width="600">
   <img src="images/afterview1.png" width="600">
   <img src="images/afterview2.png" width="600">
   
<!--@@author A0147995H-->

#### Find tasks : `find`
Format: `find [KEY_WORD] [from DATE_TIME to DATE_TIME | by DATE_TIME] [t/TAG]...`

> For find command, all parameters optional.
> You are able to search by key words of a particular task,
> or search by a particular time period, search by deadline,
> or search by particular tags.
> (You can have more than one tags to search)

Examples: <br>
   * `find cs2103`<br>

   <img src="images/before_find.png" width="600">
   <img src="images/find_command_1.png" width="600">
   
   * `find from today 5am to today 6am`<br>

   <img src="images/find_command_2.png" width="600">
   
   * `find by today 10am`<br>

   <img src="images/find_command_3.png" width="600">
   
   * `find cs2103 tag/lolo`<br>

   <img src="images/find_command_4.png" width="600">
	
#### Undo tasks : `clear`
Format: clear

> clears all the tasks

Examples: 
* `clear`

<!--@@author A0147967J-->

#### Change directory: `cd`
Format: cd FILE_PATH

Examples: 
* `cd data\newlist.xml`

   <img src="images/beforecd.png" width="600">
   <img src="images/aftercd1.png" width="600">
   <img src="images/aftercd2.png" width="600">
   
<!--@@author-->

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Address Book folder.

**Q**: How do i get started using the task manager?<br>
**A**: Type 'help' or any incorrect command will bring you to the help screen.
       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `add TASK_NAME [t/TAG]...`
Add | `add TASK_NAME by DATE TIME [RECURRING_TYPE] [t/TAG]...`
Add | `add TASK_NAME from DATE TIME to DATE TIME [RECURRING_TYPE] [t/TAG]...`
Edit | `edit TASK_ID [from EDIT_START_DATE EDIT_START_TIME to EDIT_END_DATE EDIT_END_TIME] [by EDIT_END_DATE EDIT_END_TIME] [t/EDIT_TAG]...`
Delete | `delete TASK_ID`
Complete | `done TASK_ID`
Block | `block TASK_NAME from [START_DATE] START_TIME to [START_DATE] START_TIME [t/TAG]...`
Redo | `r`
Undo | `u`
Find | `find [KEY_WORD] [from DATE_TIME to DATE_TIME | by DATE_TIME] [t/TAG]...`
View | `view DATE [TIME]`
Clear | `clear`
Change directory | `cd FILE_PATH`
Exit | `exit`
