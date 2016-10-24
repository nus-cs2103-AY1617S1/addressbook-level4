# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `DearJim.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for DearJim.
3. Double-click the file to start the app. The GUI should appear in a few seconds. <br>
<img src="images/dearjim_initial.png" width="550">

<p align="center">
Figure 1: GUI of DearJim
</p>

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : lists all tasks. This is the default view.
   * **`add`**` Learn how to use DearJim` : 
     adds a task to DearJim.
   * **`delete`**` 1` : deletes the first task shown in the current list.
   * **`exit`** : exits the app.
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Format: `<command word> <parameters>`
> * Words in `UPPER_CASE` are the parameters.
> * Fields in `SQUARE_BRACKETS` are optional.
> * The order of parameters is fixed.

### Viewing help : `help`
Opens the user guide with a new window.<br>
Format: `help`<br>


 <img src="images/dearjim_help.png" width="550">

<p align="center">
Figure 2: Help Command
</p>



 
### Adding a task: `add`
Adds a task into DearJim.<br>
Format: `[add] NAME [start DATE_TIME] [end DATE_TIME] [repeat every RECURRING_INTERVAL] [-PRIORITY]`

>To make the command format more natural, you can substitute `start` with `from/at`, `end` with `to/by`.

>We do not require an explicit command for `add`. We make it the default thing to do, when you type in anything! Hence typing in `add` itself is optional. However, if you want to add a task that begins with other command words, please include the `add` to override the other command words. For e.g, `add help my mom to buy cooking ingredients`


**_Adding a task_**

Format: `NAME `

> The simplest form of a task. Type away!

Example:
* `Buy coffee powder`

**_Specifying task priority_**

You can assign your task a `PRIORITY` of `low`, `medium` or `high`. <br>
Tasks have `medium` `PRIORITY` by default.<br>
Keyword: `-PRIORITY`


PRIORITY | Variations  
-------- | :--------:
Low | l<br /> low
Medium | m<br /> med<br /> medium
High | h<br /> high
 

Examples:
* `Do something later -l`
* `Buy coffee powder -med`
* `Buy washing powder -high`


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
>   * Relative dates such as `3 days later`, `1 week later` can be used as well
>* If no `TIME` is specified, `TIME` will be assumed to be 11:59pm
>* `TIME` formats:
>   * `am`, `AM`, `pm`, `PM` can be used to specify time of the day
>   * `midnight` can be used to specify 12AM
>   * `noon` can be used to specify 12PM
>   * 24-hour clock format such as `20:15` are also accepted
>   * Take note to demarcate the hours and minutes with a colon. The following examples are not allowed: `730am`, `1930`.

<br>

**_Valid Dates & Times_**

Date| Format
-------- | :-------- 
DD/MM/YYYY| 12/12/2016
DD/MM/YY| 12/12/16
DD/MM| 12/12
Month Word| May <br /> dec
Day|monday<br />Sunday
Relative Date | tommorow
<br>

Time| Format
-------- | :-------- 
24Hr| 20:50<br />2210
am/pm| 8:50pm<br />1130am
Preset|midnight<br /> noon
<br>

Examples:
* `Do project proposal by 5pm tomorrow`
* `eat lunch by 1pm today -h`
* `Buy coffee for boss by 7:00`
* `finish CS2101 assignment by 13th Sep`

> Note: The keyword `end` can be substituted with `by`.


**_Adding a task with time interval_**

Having that company meeting? Planning to have lunch with a friend next week? <br> 
Format:
`NAME start DATE_TIME [end DATE_TIME] [repeat every RECURRING_INTERVAL] [-PRIORITY]` 
> Note: Use `from` or `at` to indicate the start time and `to` or `by` to indicate the end time.
> `end DATE_TIME` can be unspecified.

Example: 
* `Company meeting tonight at 7pm to 9pm`
* `Family dinner at noon`
* `Meet Akshay from 1pm to 2pm -h`

**_Specifying repeated tasks_**

Have one of those pesky tasks you need to do every now and then? DearJim also allows you to specify tasks that need to be repeated at a specific `RECURRING_INTERVAL`. Never forget them again!<br>
Format: `repeat every RECURRING_INTERVAL`

Recurring Interval| Format  
-------- | :-------- 
Hour | 8 hours
Day | 3 days<br /> monday
Week | 5 weeks
Month |  2 months
Year | year

Examples: 
* `Go run at track at 7am repeat every day`
* `Go visit mom repeat every sun`



### Editing a task: `edit`
Just in case you need to change any details, or add in missing ones into your task, simply edit them in DearJim.  <br>
Format: `edit INDEX [NAME] [start DATE_TIME] [end DATE_TIME] [repeat every RECURRING_INTERVAL] [-PRIORITY]`
> `INDEX` refers to the task number in the currently displayed list.<br>
> Notice that this is similar to the `add` command format!  

Examples:
* `Company meeting tonight at 7pm to 9pm`
* `edit 2 Company meeting tomorrow morning at 7am to 9am -high`

* `Buy coffee for boss by 8am repeat every day`
* `edit 3 Buy coffee for boss by 7am repeat every 2 days`

**_Editing out details in a task_**

 You can also remove any sections if they are no longer relevant! <br>
 Format: `edit INDEX [-reset parameter] [repeat] [start] [end] `
 > `INDEX` refers to the task number in the current displayed list.<br>
> Use `[-reset repeat]` to remove the recurring time<br>
> Use `[-reset start]` to remove the start time<br>
> Use `[-reset end]` to remove the end time 

Examples:
* `Buy coffee for boss, by 8am repeat every day`
* `edit 1 -reset repeat start`
* `edit 2 -reset end`

>Do note that the reset will override the editing if done on the same line, allowing you to easily remove any parts at the end of the typing instead of continuously pressing the backspace.


### Deleting a task: `delete`
Deletes an existing task in DearJim. This will remove them from the storage. <br>
Format: `delete INDEX`
> If you need to delete multiple tasks, simply key in the additional indexes.  

Example:
* `delete 2`
* `delete 3 5 9`


### Clearing the task in DearJim: `clear`
Deletes all tasks in DearJim.<br>
Format: `clear`


### Archiving a task: `done`
Marks a task as completed and archive it in DearJim.<br>
Format: `done INDEX`
> Multiple done task is also supported.

Example:
* `done 3`
* `done 1 5`

### Undoing a command: `undo`
Reverses the effects of the previous command, if the command is reversible. Helps you get out of sticky situations! <br>
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
Shows a list of all tasks in DearJim.<br>
Format: `list`
> Displays all `uncompleted` tasks in DearJim.

Example:
* `list`
<br>


Format: `list done`
> Displays all `completed` tasks in DearJim.

Example:
* `list done`

 <img src="images/dearjim_list.png" width="550">



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
<img src="images/dearjim_hint_add.png" width="550">

<p align="center">
Figure 4: Hints for add command
</p>
<br>

* Typing `delete` in the command input generates the `delete` command format in the result display
<img src="images/dearjim_hint_delete.png" width="550">

<p align="center">
Figure 5: Hints for delete command
</p>
<br>

### Saving the data 
DearJim data are saved on the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous *DearJim* folder.

**Q**: How do I install the program?<br>
**A**: Double-click the DearJim.jar file.
       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `[add] NAME [start DATE_TIME] [end DATE_TIME] [repeat every RECURRING_INTERVAL] [-PRIORITY]`
Edit | `edit INDEX [NAME] [start DATE_TIME] [end DATE_TIME] [repeat every RECURRING_INTERVAL] [-PRIORITY] [-reset parameter]`
Delete | `delete INDEX`
Undo | `undo`
Redo | `redo`
Done | `done INDEX`
List | `list [done]`
Find | `find NAME`
Help | `help`
Exit | `exit`