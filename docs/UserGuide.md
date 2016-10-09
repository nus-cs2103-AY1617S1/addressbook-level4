
# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `dearjim.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/dearjim_initial.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : lists all tasks. This is the default view.
   * **`add`**` Learn how to use DearJim` : 
     adds a task to the Task Manager.
   * **`delete`**` 1` : deletes the first task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * The order of parameters is fixed.

#### Viewing help : `help`
Opens the user guide with a new window<br>
Format: `help`

 
#### Adding an item: `add`
Adds an item into the task manager<br>
Format: `add NAME [from/at START_DATE START_TIME][to/by END_DATE END_TIME][repeat every RECURRING_INTERVAL][-PRIORITY]`

>DearJim allows you to assign your task a `PRIORITY` of low, medium or high. By default (if unspecified), tasks
will have medium `PRIORITY`.
> To assign a `PRIORITY`, simply enter `-PRIORITY` as part of the add/edit command, where `PRIORITY` can be replaced by low, medium or high.
>
> `PRIORITY` also accepts variations of low, medium and high.
> * `-l` and `-low` means `-low`
> * `-m`, `-med` and `-medium` means `-medium`
> * `-h` and `-high` means `-high`
>
>
> DearJim also allows you to specify tasks that need to be repeated at a specific `RECURRING_INTERVAL`.
> To assign a `RECURRING_INTERVAL`, simply enter `repeat every RECURRING_INTERVAL` as part of the add/edit command, where `RECURRING_INTERVAL` can be replaced by the appropriate `RECURRING_INTERVAL` below.
> 
> Supported `RECURRING_INTERVAL`
> * `day`, `2 days`, `3 days`, ...
> * `week`, `2 weeks`, `3 weeks`, ...
> * `month`, `2 months`, `3 months`, ...




*With deadline*

Format: `add NAME by END_DATE END_TIME [repeat every RECURRING_INTERVAL][-PRIORITY]`
>Notice the `by` keyword? We use `by` to denote a deadline.

>`END_DATE` and `END_TIME` are flexible!
>* If no `END_DATE` is specified, `END_DATE` will be assumed to be the current date
>* `END_DATE`
>   * `today`, `tonight` can be used to refer to the current day
>   * `tmr`, `tomorrow` can be used to refer to the next day
>   * `Monday`, `Tuesday`, `Wednesday`, `Thursday`, `Friday`, `Saturday` and `Sunday` refers to the nearest matching day from the current date
>* `END_TIME`
>   * `am`, `AM`, `pm`, `PM` can be used to specify time of the day
>   * `midnight` can be used to specify 12AM
>   * `noon` can be used to specify 12PM
>   * `24:00` - 24-hour clock format is also accepted

Examples:
* `add Do project proposal by 5pm tomorrow`
* `add eat lunch by 1pm today -h`
* `add Buy coffee for boss by 7am repeat every day`

*Without deadline*

Format: `add NAME [-PRIORITY]`

> Simply enter the name of the task!

Example:
* `add Buy coffee powder`
* `add Buy coffee powder -medium`
* `add Buy washing powder -h`
* `add Buy baby powder -l`

*With time interval*

`add NAME from/at START_DATE START_TIME [to END_DATE END_TIME] [repeat every RECURRING_INTERVAL][-PRIORITY]` 
> For events, meetings, we use `from` and `at` to indicate the start time and `to` and `by` to indicate the end time.
> End time can be unspecified.

>`START_DATE`, `START_TIME`, `END_DATE` and `END_TIME` are flexible!
>* If no `END_DATE` is specified, `END_DATE` will be assumed to be the current date
>* `START_DATE` and `END_DATE`
>   * `today`, `tonight` can be used to refer to the current day
>   * `tmr`, `tomorrow` can be used to refer to the next day
>   * `Monday`, `Tuesday`, `Wednesday`, `Thursday`, `Friday`, `Saturday` and `Sunday` refers to the nearest matching day from the current date
>* `START_TIME` and `END_TIME`
>   * `am`, `AM`, `pm`, `PM` can be used to specify time of the day
>   * `midnight` can be used to specify 12AM
>   * `noon` can be used to specify 12PM
>   * `24:00` - 24-hour clock format is also accepted

Example: 
* `add Company meeting tonight at 7pm to 9pm`
* `add Family dinner at noon`
* `add Meet Akshay from 1pm to 2pm -h`

#### Editing an item: `edit`
Edits an item in the task manager<br>


Format: Replace 	`add` with `edit INDEX`
>Tip: We make the edit commands similar to add commands, so that you only have to learn one of them! Simply replace `add` with `edit INDEX` to do an edit.

Examples:
* `edit 10 Company meeting tomorrow morning at 7am to 9am`
* `edit 3 Buy coffee for boss by 7am repeat every 2 days`

*Tentative events*

Format: Replace `add` with  `edit INDEX (new OR SUB-INDEX)`
>Note: If you want to confirm the time slot, use the `confirm` command instead.

Example:
> <img src="images/edit_tentative_ui.png" width="600">

* `edit 12 1 (at 24 Sep 8pm to 9pm) 3 (at 25 Sep 8pm to 9pm)`

#### Deleting an item: `delete`
Deletes an item in your task manager.<br>
This process is reversible with the `undo` command.<br>
Format: `delete INDEX`
> Check out the `undo` command below to reverse an accidental `delete`!

Examples:
* `delete 1`
* `delete 2`

#### Undoing a command: `undo`
Reverses the effects of the previous command, if the command is a reversible one.<br>
Format: `undo`
> Commands that you can `undo`
> * `add`
> * `edit`
> * `delete`
> * `clear`
> * `done`
>
Example:
* `undo`

#### Clearing the task manager: `clear`
Deletes all items in your task manager.<br>
This process is reversible with the `undo` command.<br>
Format: `clear`
> `clear` allows you to `delete` all items with a single command!

Example:
* `clear`


#### Redoing a command: `redo`
Reverses a previous `undo` command, if possible.<br>
Format: `redo`
> `redo` allows your to reverse your previous `undo` to get back your data!
>
> Note: `redo` can only remember the last `undo` if no `add`, `edit`, `delete`, `clear` or `done` commands have been entered after the last `undo`.

Example: 
* `redo`

#### Archiving an item: `done`
Archives an item in your task manager.<br>
Format: `done INDEX`
> Marks an item as `done` as sends it to the archive for future viewing.

Examples:
* `done 1`
* `done 2`

#### Listing all items : `list`
Shows a list of all items in the task manager.<br>
Format: `list`
> Displays all uncompleted tasks in the task manager.

#### Finding an item : `find`
Find an item by name.<br>
Format: `find NAME`
> `find` is case-insensitive.

Examples:
* `find Akshay`
* `find Michelle`
* `find johnny`

#### Saving the data 
Task manager data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous *DearJim* folder.

**Q**: How do I install the program?<br>
**A**: Double click the icon
       
## Command Summary

Command | Format  
-------- | :-------- 
Add/Edit | `add`/`edit INDEX` `NAME by DUE_DATE DUE_TIME [repeat every RECURRING_INTERVAL]`
Add/Edit | `add`/`edit INDEX` `NAME [rank PRIORITY]`
Add/Edit | `add`/`edit INDEX` `NAME at DATE START_TIME to [DATE] END_TIME [repeat every RECURRING_INTERVAL]` 
Add/Edit | `add`/`edit INDEX (new OR SUB-INDEX)` `NAME (at DATE START_TIME to [DATE] END TIME)`
Confirm | `confirm INDEX SUB-INDEX`
Delete | `delete INDEX`
Undo | `undo`
Redo | `redo`
Done | `done INDEX`
List | `list`
Find | `find NAME`
Help | `help`
