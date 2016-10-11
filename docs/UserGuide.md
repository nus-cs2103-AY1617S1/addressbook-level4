
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
   > <img src="images/dearjim_initial.png">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : lists all tasks. This is the default view.
   * **`add`**` Learn how to use DearJim` : 
     adds a task to the Task Manager.
   * **`delete`**` 1` : deletes the first task shown in the current list.
   * **`exit`** : exits the app.
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Fields in `SQUARE_BRACKETS` are optional.
> * The order of parameters is fixed.

### Viewing help : `help`
Opens the user guide with a new window.<br>
Format: `help`<br>

Example:
> <img src="images/dearjim_help.png"><br>

* `help`

 
### Adding a task: `add`
Adds a task into the task manager.<br>
Format: `add NAME [from/at START_DATE START_TIME][to/by END_DATE END_TIME][repeat every RECURRING_INTERVAL][-PRIORITY]`

**_Specifying task priority_**

You can assign your task a `PRIORITY` of `low`, `medium` or `high`. <br>
Tasks have `medium` `PRIORITY` by default.<br>
Format: `-PRIORITY`

`PRIORITY` also accepts variations of `low`, `medium` and `high`.

`PRIORITY` | Variations  
-------- | :--------:
`low` | `l`, `low`
`medium` | `m`, `med`, `medium`
`high` | `h`, `high`
 
> To assign a `PRIORITY`, simply enter `-PRIORITY` as part of the add command, where `PRIORITY` can be replaced by `low`, `medium` or `high`, e.g `-low`, `-medium`, `-high`.

Examples:
* `add Do something later -l`
* `add Do something later -low`


**_Specifying repeated tasks_**

DearJim also allows you to specify tasks that need to be repeated at a specific `RECURRING_INTERVAL`. <br>
Format: `repeat every RECURRING_INTERVAL`
> To assign a `RECURRING_INTERVAL`, simply enter `repeat every RECURRING_INTERVAL` as part of the add/edit command, where `RECURRING_INTERVAL` can be replaced by the appropriate `RECURRING_INTERVAL` below.

Supported `RECURRING_INTERVAL`
* `day`, `2 days`, `3 days`, ...
* `week`, `2 weeks`, `3 weeks`, ...
* `month`, `2 months`, `3 months`, ...

Examples: 
* `add Go run at track, repeat every 3 days`


**_Adding a task with deadline_**

Format: `add NAME, by END_DATE END_TIME [repeat every RECURRING_INTERVAL][-PRIORITY]`
> The `by` keyword denotes a deadline.
> Take note of the `,` after the `NAME`, it is use to mark the end of your task's name.

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
>   * `20:15` - 24-hour clock format is also accepted

Examples:
* `add Do project proposal, by 5pm tomorrow`
* `add eat lunch, by 1pm today -h`
* `add Buy coffee for boss, by 7am repeat every day`

**_Adding a task without deadline_**

Format: `add NAME [-PRIORITY]`

> Simply enter the name of the task!

Example:
* `add Buy coffee powder`
* `add Buy coffee powder -medium`
* `add Buy washing powder -h`
* `add Buy baby powder -l`

**_Adding a task with time interval_**

`add NAME, from/at START_DATE START_TIME [to END_DATE END_TIME] [repeat every RECURRING_INTERVAL][-PRIORITY]` 
> For events, meetings, use `from` and `at` to indicate the start time and `to` and `by` to indicate the end time.
> Take note of the `,` after the `NAME`, it is use to mark the end of your task's name.
> `END_DATE` and `END_TIME` can be unspecified.

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
>   * `20:15` - 24-hour clock format is also accepted

Example: 
* `add Company meeting tonight, at 7pm to 9pm`
* `add Family dinner, at noon`
* `add Meet Akshay, from 1pm to 2pm -h`

### Editing a task: `edit`
Edits an existing task in the task manager<br>
Format: `edit INDEX NAME, [from/at START_DATE START_TIME][to/by END_DATE END_TIME][-PRIORITY][repeat every RECURRING_INTERVAL]`
> `INDEX` refers to the task number in the current displayed list.<br>
> Notice that this is similar to the `add` command format! 

Examples:
* `add Company meeting tonight, at 7pm to 9pm`
* `edit 2 Company meeting tomorrow morning, at 7am to 9am -high`
* `add Buy coffee for boss, by 8am repeat every day`
* `edit 3 Buy coffee for boss, by 7am repeat every 2 days`
* `edit 3 Buy coffee for boss for the last time, by 7am`

### Deleting a task: `delete`
Deletes an existing task in your task manager.<br>
Format: `delete INDEX`
> `INDEX` refers to the task number in the current displayed list.<br>

Examples:
* `delete 1`
* `delete 2`

### Clearing the task manager: `clear`
Deletes all tasks in your task manager.<br>
This process is reversible with the `undo` command.<br>
Format: `clear`
> `clear` allows you to `delete` all tasks with a single command!

Example:
* `clear`

### Archiving a task: `done`
Archives a task in your task manager.<br>
Format: `done INDEX`
> Marks a task as `done` as sends it to the archive for future viewing.
> `INDEX` refers to the task number in the current displayed list.

Examples:
* `done 1`
* `done 3`

### Undoing a command: `undo`
Reverses the effects of the previous command, if the command is a reversible one.<br>
Format: `undo`
> Commands that you can `undo`
> * `add`
> * `edit`
> * `delete`
> * `clear`
> * `done`

Example:
* `undo`

### Redoing a command: `redo`
Reverses a previous `undo` command, if possible.<br>
Format: `redo`
> `redo` allows your to reverse your previous `undo` to get back your data!
>
> Note: `redo` only works if no `add`, `edit`, `delete`, `clear` or `done` commands have been entered after the last `undo`.

Example: 
* `redo`

### Listing all tasks : `list`
Shows a list of all tasks in the task manager.<br>
Format: `list`
> Displays all uncompleted tasks in the task manager.

Example:
> <img src="images/dearjim_list.png">

* `list`

### Finding a task : `find`
Find an existing task by name.<br>
Format: `find NAME`
> `find` is case-insensitive - `find AKSHAY` will match `find akshay`
>
> If no NAME is provided, all uncompleted tasks will be displayed

Examples:
* `find Akshay`
* `find Michelle`
* `find company meeting`
* `find`

### Exiting the application: `exit`
Closes the application.<br>
Format: `exit`

Example: 
* `exit`


### Getting hints for command format
Format: none, just type a command and let DearJim provide you hints on the command format that you might want to use!
>DearJim provides you hints on command formats as you type the command!

Examples:
><img src="images/dearjim_hint_add.png">

* Typing `add` in the command input generates the `add` command format in the result display

><img src="images/dearjim_hint_delete.png">

* Typing `delete` in the command input generates the `delete` command format in the result display

### Saving the data 
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
Add | `add NAME, [from/at START_DATE START_TIME][to/by END_DATE END_TIME][repeat every RECURRING_INTERVAL][-PRIORITY]`
Edit | `edit INDEX NAME, [from/at START_DATE START_TIME][to/by END_DATE END_TIME][repeat every RECURRING_INTERVAL][-PRIORITY]`
Delete | `delete INDEX`
Undo | `undo`
Redo | `redo`
Done | `done INDEX`
List | `list`
Find | `find NAME`
Help | `help`
Exit | `exit`
