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

<p align="center">
Figure 1: GUI of DearJim
</p>

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
* `help`

> <img src="images/dearjim_help.png">

<p align="center">
Figure 2: Help Command
</p>



 
### Adding a task: `add`
Adds a task into the task manager.<br>
Format: `[add] NAME [start DATE_TIME] [end DATE_TIME] [repeat every RECURRING_INTERVAL] [-PRIORITY]`

>To make the command format more natural, we allow you to substitute `start` with `from/at`, `end` with `to/by`.

>We do not require an explicit command for `add`. We make it the default thing to do, when you type in anything! Hence typing in `add` itself is optional. However, if you want to add a task that starts with other command words, please include the `add` to override the other command words!


**_Adding a task_**

Format: `NAME `

> The simplest form of a task. Type away!

Examples:
* `Buy coffee powder`
* `Buy baby powder`

**_Specifying task priority_**

You can assign your task a `PRIORITY` of `low`, `medium` or `high`. <br>
Tasks have `medium` `PRIORITY` by default.<br>
Keyword: `-PRIORITY`

`PRIORITY` also accepts variations of `low`, `medium` and `high`.

`PRIORITY` | Variations  
-------- | :--------:
`low` | `l`, `low`
`medium` | `m`, `med`, `medium`
`high` | `h`, `high`
 
> To assign a `PRIORITY`, simply enter `-PRIORITY` as part of the add command, where `PRIORITY` can be replaced by `low`, `medium` or `high`, e.g `-low`, `-medium`, `-high`.

Examples:
* `Do something later -l`
* `Do something later -low`
* `Buy coffee powder -medium`
* `Buy washing powder -h`



**_Specifying repeated tasks_**

Have one of those pesky tasks you need to do every now and then? DearJim also allows you to specify tasks that need to be repeated at a specific `RECURRING_INTERVAL`. Never forget them again!<br>
Format: `repeat every RECURRING_INTERVAL`
> To assign a `RECURRING_INTERVAL`, simply enter `repeat every RECURRING_INTERVAL` as part of the add/edit command, where `RECURRING_INTERVAL` can be replaced by the appropriate `RECURRING_INTERVAL` below.

Supported `RECURRING_INTERVAL`
* `hour`, `8 hours`, ...
* `day`, `3 days`, ...
* `week`, `5 weeks`, ...
* `month`, `2 months`, ...
* `year`, ...
* Any day of the week e.g `mon`, `monday`, `thurs`, `sun`, ...

Examples: 
* `Go run at track at 7am repeat every 3 days`
* `Go visit mum repeat every sun`

> You cannot have a repeated task without specifying a time. Read on to see how to do so.

**_Adding a task with deadline_**

Nobody likes deadlines. What is worse, is missing them. <br>
Format: `NAME end DATE_TIME [repeat every RECURRING_INTERVAL] [-PRIORITY]`
> The `end` keyword denotes a deadline. 

>`DATE_TIME` is flexible!
>* If no `DATE` is specified, `DATE` will be assumed to be the current date
>* `DATE` formats:
>   * `today`, `tonight` can be used to refer to the current day
>   * `tmr`, `tomorrow` can be used to refer to the next day
>   * `Monday`, `Tuesday`, `Wednesday`, `Thursday`, `Friday`, `Saturday` and `Sunday` refers to the nearest matching day from the current date
>   * Dates such as `13th Sep`, `10 October 2016`, `02/10/2016 (mm/dd/yyyy)` are acceptable too. Note that the year must be specified in full e.g `10 October 2016` is allowed, but not `10 October 16`
>   * Relative dates such as `3 days later`, `1 week later`, `next sat` can be used as well
>* If no `TIME` is specified, `TIME` will be assumed to be 11:59pm
>* `TIME` formats:
>   * `am`, `AM`, `pm`, `PM` can be used to specify time of the day
>   * `midnight` can be used to specify 12AM
>   * `noon` can be used to specify 12PM
>   * 24-hour clock format such as `20:15` are also accepted
>> Take note to demarcate the hours and minutes with a colon. The following examples are not allowed: `730am`, `2015`.

Examples:
* `Do project proposal by 5pm tomorrow`
* `eat lunch by 1pm today -h`
* `Buy coffee for boss by 7:00 repeat every day`
* `finish CS2101 assignment by 13th Sep`

> Notice how the `end` keyword can be substituted with `by`.


**_Adding a task with time interval_**

Having that company meeting? Planning to have lunch with a friend next week? <br> 
Format:
`NAME start DATE_TIME [end DATE_TIME] [repeat every RECURRING_INTERVAL] [-PRIORITY]` 
> We accept `from` and `at` to indicate the start time and `to` and `by` to indicate the end time.
> `end DATE_TIME` can be unspecified.

Example: 
* `Company meeting tonight at 7pm to 9pm`
* `Family dinner at noon`
* `Meet Akshay from 1pm to 2pm -h`

>Tip: If you do not know the end time for your event yet, you can leave it blank first, and `edit` it in later!


### Editing a task: `edit`
Edits an existing task in the task manager. Just in case you need to change any details, or add in missing ones! <br>
Format: `edit INDEX [NAME] [start DATE_TIME] [end DATE_TIME] [repeat every RECURRING_INTERVAL] [-PRIORITY]`
> `INDEX` refers to the task number in the current displayed list.<br>
> Notice that this is similar to the `add` command format!  

Examples:
* `Company meeting tonight at 7pm to 9pm`
* `edit 2 Company meeting tomorrow morning at 7am to 9am -high`
* `Buy coffee for boss by 8am repeat every day`
* `edit 3 Buy coffee for boss by 7am repeat every 2 days`

**_Editing out details in a task_**

 You can also remove any section if they are no longer relevant! <br>
 Format: `edit INDEX [repeat] [start] [end] [-reset parameter]`
 > `INDEX` refers to the task number in the current displayed list.<br>
> Use [-reset repeat] to remove the recurring time
> Use [-reset start] to remove the start time
> Use [-reset end] to remove the end time 

Examples:
* `Buy coffee for boss by 8am repeat every day`
* `edit 1 -reset repeat start`
* `edit 2 -reset end`

### Deleting a task: `delete`
Deletes an existing task in your task manager. This will remove them from the storage. If you want to mark them as done instead, look at the `done` command. <br>
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
Archives a task in your task manager. So that you can look at all the tasks you have completed, and be proud of yourself! <br>
Format: `done INDEX`
> Marks a task as `done` as sends it to the archive for future viewing.
> `INDEX` refers to the task number in the current displayed list.

Examples:
* `done 1`
* `done 3`

### Undoing a command: `undo`
Reverses the effects of the previous command, if the command is a reversible one. Helps you get out of sticky situations! <br>
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
Reverses a previous `undo` command, if possible. <br>
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
* `list`

> <img src="images/dearjim_list.png">

<p align="center">
Figure 3: List View
</p>

### Finding a task : `find`
Forgot the details about a task you added? Find an existing task by name.<br>
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
* Typing `add` in the command input generates the `add` command format in the result display
><img src="images/dearjim_hint_add.png">

<p align="center">
Figure 4: Hints for add command
</p>
<br>

* Typing `delete` in the command input generates the `delete` command format in the result display
><img src="images/dearjim_hint_delete.png">

<p align="center">
Figure 5: Hints for delete command
</p>
<br>

### Saving the data 
Task manager data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous *DearJim* folder.

**Q**: How do I install the program?<br>
**A**: Double click the icon.
       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `[add] NAME [start DATE_TIME] [end DATE_TIME] [repeat every RECURRING_INTERVAL] [-PRIORITY]`
Edit | `edit INDEX [NAME] [start DATE_TIME] [end DATE_TIME] [repeat every RECURRING_INTERVAL] [-PRIORITY] [-reset parameter]`
Delete | `delete INDEX`
Undo | `undo`
Redo | `redo`
Done | `done INDEX`
List | `list`
Find | `find NAME`
Help | `help`
Exit | `exit`
