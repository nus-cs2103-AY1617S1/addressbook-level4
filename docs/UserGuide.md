# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `ToDoIt.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your ToDoIt task file.
3. Double-click the ToDoIt.jar to start the app. The GUI should appear in a few seconds. 
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**` Meeting with John h/14:00 d/05-09-2016 l/2 p/5 a/no r/no i/Meeting with John regarding sales` : 
     adds a task named `Meeting with John` to the task list.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with no parameter specified require no parameter.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`
 
#### Adding a task: `add`
Adds a task to the to do list.<br>
Format: `add DESCRIPTION [h/TIME d/DATE l/LENGTH] [r/RECUR] [p/PRIORITY] [a/] [t/TAG]...` 

> **Flags**
> * `h/` Time: Time in 24 hour HHMM format
> * `d/` Date: Date in DDMMYY format
> * `l/` Length: Specifies the length of time. Defaults to 1 hour if time and date are specified, but length is not specified. Use a number followed by a time interval (min, hr, day, week, mo), e.g. 6d, 1w
> * `r/` Recur: Specifies an interval for recurring task, if any. Use a number followed by a time interval (min, hr, day, week, mo), e.g. 6d, 1w
> * `p/` Priority: Specifies the priority of a task (high/3/h, med/2/m, low/1/l)
> * `a/` Autoschedule: If flag is specified, the task will be automatically scheduled to a free slot. If a time, date and length is specified, this flag is ignored.<br> 
> <br> 
> A task can be dated (has time, date, length), or floating.<br> 
> Both time and date must be specified (dated), or both left out (floating). Tasks with only time or date specified will give an error.<br> 
> Tags specifies any tags that are associated with this task. Tasks can have any number of tags (including 0)

Examples: 
* `add Do stuff h/1100 d/10102016 l/1hr p/high`
* `add CS2101 Lecture h/1400 d/07102016 l/2hr r/1w p/low t/got-webcast`

#### Listing tasks : `tasks`
Shows a list of tasks in ToDoIt specified by the search terms.<br>
Format: `tasks [ds/DATE_START] [ds/DATE_END] [s/SORT_BY] [d/]`

> **Flags**
> * `ds/` Date start: If a start date is specified, program will only display tasks after this date.
> * `de/` Date end: If an end date is specified, program will only display tasks before this date.
> * `s/` Sort by: Sorts the tasks in the order specified (date, time, alpha, priority).
> * `d/` Done tasks: If this flag is specified, tasks that are marked done will be shown.<br> 
> <br> 
> Dates are in DDMMYY format.

Examples: 
* `tasks ds/02102016 de/09102016 s/date d/`

#### Finding all task containing a keyword: `find`
Finds tasks that contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS] [s/SCOPE]`

> **Flags**
> * `s/` Scope: The scope in which to search (all, desc, tags). Defaults to all.<br> 
> <br> 
> The search is not case sensitive. e.g `stuff` will match `Stuff`<br> 
> The order of the keywords does not matter. e.g. `Do stuff` will match `Stuff do`<br> 
> Only full words will be matched e.g. `Work` will not match `Workout`<br> 
> Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Stuff` will match `Do stuff`

Examples: 
* `find work <br>
  Returns `Do work` but not `Do homework`
* `find work s/tags <br>
  Returns tasks contained with the `work` tag.

#### Deleting a task : `delete`
Deletes the specified task from the to do list. Irreversible.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. <br>
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `tasks`<br>
  `delete 2`<br>
  Deletes the 2nd task in the to do list.
* `find work`<br> 
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

#### Editing a task: `edit`
Edits a task in the to do list.<br>
Format: `edit INDEX [desc/DESCRIPTION] [h/TIME] [d/DATE] [l/LENGTH] [r/RECUR] [p/PRIORITY] [t/TAG]...` 

> Edits the task at the specified `INDEX`. <br>
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ... <br>
> Replaces the current task data with the specified task data. Irreversible. <br>
> For more information regarding the parameters, refer to `add` commmand

Examples: 
* `find stuff`<br> 
  `edit 1 desc/Do stuff`<br>

#### Reschduling a task: `reschedule`
Reschedules a task in the to do list.<br>
Format: `reschedule INDEX INTERVAL` 

> Reschedules the task at the specified `INDEX`.  <br>
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ... <br>
> For interval, use a number followed by a time interval (min, hr, day, week, mo), e.g. 6d, 1w. <br>
> Negative numbers are not supported. To reschedule earlier, consider using edit instead.
 
Examples: 
* `find stuff`<br> 
  `reschedule 1 1hr`<br>
  
#### Mark a task as done : `done`
Marks the specified task from the to do list as done.<br>
Format: `done INDEX`

> Marks task at the specified `INDEX` as done. <br>
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...<br>
> If the task is already done, marks the task as undone.

#### Clearing all entries : `clear`
Clears all entries from the address book.<br>
Format: `clear`  

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data 
Task data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I access help?<br>
**A**: Type "help" in the command line of the program and press 'enter' on keyboard.<br>
       <br>
       
**Q**: Why does the program fail to start?<br>
**A**: Ensure that your system meets the requirements stated in the quick start section
	   and that the downloaded program file is not corrupted.<br>
       <br>
       
**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Address Book folder.<br>
       <br>
       
**Q**: How do I uninstall ToDoIt?<br>
**A**: Just delete ToDoIt.jar to remove the program from your computer. 
	   You can also delete the text that stores the task.<br>
       <br>
       
**Q**: Do I require knowledge of command line to use this program?<br>
**A**: No, there is no prior command line knowledge required to use ToDoIt.
	   Instead, just follow the instructions given in the help. See access help faq.<br>
       <br>
       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `add TASK_NAME h/24HR_TIME d/DATE l/DURATION_IN_HOURS p/PRIORITY a/AUTO_SCHEDULE r/RECURRING_TASK i/ADDITIONAL_INFORMATION [t/TAG]...`
Edit | `edit INDEX name/TASK_NAME h/24HR_TIME d/DATE l/DURATION_IN_HOURS p/PRIORITY a/AUTO_SCHEDULE r/RECURRING_TASK i/ADDITIONAL_INFORMATION [t/TAG]...`
Clear | `clear`
Delete | `delete INDEX`
Done | `done INDEX`
Task | `task ds/START_DATE de/END_DATE s/SORT_BY_ATTRIBUTE d/SHOW_DONE_TASKS`
Reschedule | `reschedule INDEX i/TIME_INTERVAL`
Find | `find KEYWORD [MORE_KEYWORDS] s/SCOPE`
List | `list`
Help | `help`
Select | `select INDEX`
Exit | `exit`
