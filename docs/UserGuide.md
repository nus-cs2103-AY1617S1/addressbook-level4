# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## About Lazyman’s Friend

Have you ever had too many assignments and tasks to keep track of? Whilst a calendar or organizer might be a solution, it sure is cumbersome to lug one around wherever you go.<br>

Say hello to Lazyman's Friend, your very own personal assistant that keeps your tasks in order. Lazyman's Friend takes your preferences into consideration and sorts tasks in order of date, time and priority. It also has a default view which shows you the tasks lined up for the week.<br>

Lazyman's Friend is equipped with a command line which removes the painstaking process of using a trackpad or a mouse to navigate around the application. You can use our application and communicate your preferences using short length command words. Keeping track of tasks and events can’t get any simpler than this.<br>

So, what are you waiting for? Let’s get started!


## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `LazymansFriend.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for the program.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`show`**` today` : lists all tasks scheduled for today
   * **`add`**` buy eggs by 5pm today` : 
     remind yourself to get some eggs by 5pm today
   * **`delete`**` 1` : deletes the first task shown in the to-do list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional parameters.
> * The ordering of parameters is flexible.

[comment]: # (@@author A0146107M)

#### Adding a task : `add`
Adds a task, deadline, event or a floating task to the list. <br>
Format: `add TASKNAME [at/from START_TIMEDATE] [by/to END_TIMEDATE] [r/RECURRING_FREQUENCY] [p/PRIORITY]`

> * For tasks, deadlines and events, either `START_TIMEDATE` or `END_TIMEDATE` must be provided.<br>
> * If `START_TIMEDATE` exceeds `END_TIMEDATE`, the addition of task will not be successful.
> * For floating tasks, `START_TIMEDATE` and `END_TIMEDATE` are unspecified.<br>
> * If `TASKNAME` contains keywords like "add", the user can enter it using the escape character '\'.
* `add Shop \at Topshop at 5pm`
> * `TASKNAME` should not contain `'`.
> * Priority must be low/med/high. If priority is not specified, default priority of low is set.
> * `RECURRING_FREQUENCY` can be set as `daily`, `weekly`, `monthly` or `yearly`.
> * Floating tasks do not have `RECURRING_FREQUENCY`. In the event that this is attempted, the floating task will be added but the `RECURRING_FREQUENCY` entered will be ignored, so the task will be added as a non-recurring one.

Examples: 
* `add Buy eggs at 5pm 13/09/2016 r/weekly p/high`
* `add Meeting from 13/09/2016 5pm to 13/09/2016 7pm p/med`
* `add Pay bills by friday 5pm`
* `add Do laundry p/low`

[comment]: # (@@author A0142102E)

#### Displaying tasks : `show`
Displays tasks and their indexes in the specified category.<br>
Format: `show [CATEGORY]`

> * `CATEGORY` can be replaced with `all`, `complete`, `done`, `incomplete`, `overdue`, `floating`, `p/high`, `p/med`, `p/low`, `today`, `tomorrow`, or any specific date.
> * If `CATEGORY` is not specified and only `show` is entered, then the incomplete tasks are shown as the default view.
> * `p/high`, `p/med` and `p/low` stand for tasks with high, medium and low priority respectively.
> * Other than `show complete`, all other inputs for `CATEGORY` will only display incomplete tasks, i.e., the tasks which have not been marked as done.
> * The user can also enter the keywords `today`, `tomorrow`, `floating` and `overdue` without specifying the `show` keyword in front to quickly retrieve the corresponding tasks.

Examples:
* `show incomplete`<br>

> * Lists all incomplete tasks

* `show p/high`<br>

> * Lists all tasks marked as high priority

* `show tomorrow`<br>

> * Lists all tasks for tomorrow

* `show 30/10/2016`<br>

> * Lists all the tasks scheduled on 30th October, 2016

* `overdue`<br>

> * Lists all the tasks that are overdue

[comment]: # (@@author A0146107M)

#### Searching for tasks : `find`
Lists tasks whose names match the given input.<br>
Format: `find SEARCHSTRING`

> * The search is case insensitive. e.g `buy` will match `Buy`
> * Wild cards can be indicated with an asterisk `*` e.g. `B* eggs` will match `Buy eggs`
> * Only the name is searched.
> * Tasks containing the `SEARCHSTRING` will be matched e.g. `Buy` will match `Must buy eggs`

Examples: 
* `find b*y`<br>

> * Returns both `buy` and `buy eggs`

#### Deleting a task : `delete`
Deletes the specified task.<br>
Format: `delete INDEX/TASKNAME`

> * If `INDEX` is specified, the task at the specified index will be deleted.
> * If `TASKNAME` is specified, and only one task matches `TASKNAME`, that task will be deleted. 
> * If `TASKNAME` is entered, and there are multiple tasks with matching `TASKNAME`, then they are all displayed along with their indices. The user can then proceed with deletion using the `INDEX` of the appropriate task.

Examples:
* `delete 1`

> * Deletes task at index 1

* `delete b*y`

> * If only 1 task matches the entered text, it is deleted. Otherwise, the tasks matching `b*y*` will be listed for the user to choose from.

[comment]: # (@@author A0144919W)

#### Updating a task : `update`
Updates information for a task referred by its index.<br>
Format: `update INDEX [TASKNAME] [at/from START_TIMEDATE] [to/by END_TIMEDATE] [r/RECURRING_FREQUENCY] [p/PRIORITY]`

> * Replaces the entered information for the task at the specified index
> * If the newly updated `START_TIMEDATE` exceeds `END_TIMEDATE` of the task, the update will not be successful

Examples:
* `update 1 at 13/09/2016 5pm`<br>

> * `Meeting from 13/09/2016 4pm to 13/09/2016 6pm` will be replaced with `Meeting at 13/09/2016 5pm`

* `update 1 from 13/09/2016 4pm to 13/09/2016 6pm`

> * `Meeting at 13/09/2016 5pm` will be replaced with `Meeting from 13/09/2016 4pm to 13/09/2016 6pm`

* `update 1 p/high`<br>

> * The priority of the task at index 1 is updated to high.

[comment]: # (@@author)

#### Marking a task as complete : `done`
Marks a task as complete.<br>
Format: `done INDEX/TASKNAME`

> * If `INDEX` is specified, the task at the specified index will be marked as complete.
> * If `TASKNAME` is specified, tasks are sought out in the same way the `find` command does. If only one task matches `TASKNAME`, it will be marked as done. Otherwise, matching tasks and their indices are displayed. The user can then proceed with marking a task as complete using the `INDEX` of the appropriate task.

Examples:
* `done 1`
* `done b*y`

[comment]: # (@@author A0135769N)

#### Setting the storage location: `setstorage`
Sets the data storage location to the specified file path. <br>
Format: `setstorage FILEPATH`
Examples:
* `setstorage C:\Users\User\Google Drive`
* `setstorage main\tasklist.xml`

> * If the `FILEPATH` does not contain a .xml file at the end, a default .xml file with a default name is appended to the file path.

[comment]: # (@@author A0144919W)

#### Undo previous action(s): `undo`
Undo the latest change made to the task list. <br>
Format: `undo`

> * There is no limit on the number of times undo can be called, up to the first previous change.

#### Redo previous action(s): `redo`
Redo the latest change that was reverted using undo. <br>
Format: `redo`

> * Allows redo to up to unlimited consecutive undo operations done.
> * Does not work if the undo command was not used just before this command.

[comment]: # (@@author A0146107M)

#### Autocomplete feature: `TAB`/`SPACEBAR`
Autocompletes the command that has been entered halfway in the command box, upon pressing `TAB` or `SPACEBAR`. <br>

> * If no commands match the current text, nothing will happen
> * If 2 or more commands match the current text, nothing will happen

Examples:
* Pressing `TAB`/`SPACEBAR` after entering `de` will complete `delete`
* Pressing `TAB`/`SPACEBAR` after entering `u` will do nothing, as both `undo` and `update` match `u`
* Pressing `TAB`/`SPACEBAR` after entering `un` will complete `undo`

#### Command History: `UP` and `DOWN` arrows
Traverse through previously entered commands. <br>

> `UP` displays the next older command.
> `DOWN` displays the next newer command.
> The currently entered text will remain available in the command history.

#### Viewing help : `help`
Displays the help window containing a command summary. <br>
Format: `help`

> The help tooltip will open in a new window.
> On pressing any key while in the help window, the help window will be closed and the user will be taken back to the command line.

[comment]: # (@@author)

#### Clear all tasks : `clear`
Clears all tasks from the to-do list.<br>
Format: `clear`

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data 
The task manager data is saved on the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous SmartScheduler folder.
       
## Command Summary

Command | Format | Example
-------- | :-------- | :--------
add | `add TASKNAME [at/from START_TIMEDATE] [to/by END_TIMEDATE] [r/RECURRING_FREQUENCY] [p/PRIORITY]` | `add Buy eggs at 5pm 13/09/2016 r/weekly p/high`
show | `show [CATEGORY]` | `show 31/01/2017`
find | `find SEARCHSTRING` | `find b*y*`
delete | `delete INDEX/TASKNAME` | `delete Study for midterms`
update | `update INDEX [TASKNAME] [at/from START_TIMEDATE] [to/by END_TIMEDATE] [r/RECURRING_FREQUENCY] [p/PRIORITY]` | `update 5 at 6pm`
done | `done INDEX/TASKNAME` | `done 3`
setstorage | `setstorage FILEPATH` | `setstorage C:\Users\USER\Google Drive`
undo | `undo` | 
redo | `redo` | 
help | `help` | 
clear | `clear` | 
exit | `exit` | 
