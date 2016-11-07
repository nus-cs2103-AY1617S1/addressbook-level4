<!-- @@author A0139817U -->
# Tusk: User Guide

* [Introduction](#introduction)
* [Quick Start](#quick-start)
* [Features](#features)
* [Command Autocomplete](#command-autocomplete)
* [Command History](#command-history)
* [Scrolling](#scrolling)
* [FAQ](#faq)
* [Command Summary](#command-summary)
* [Date Format](#date-format)
* [Time Format](#time-format)
* [Date-Time Format](#date-time-format)

<br>
## Introduction
With a never-ending stream of deadlines and tasks, it's easy to be overwhelmed without an effective task management system. Traditional task managers require you to interact with them through a graphical user interface, which is too slow for users like you! <i>Tusk</i> implements a new paradigm of task management.

While traditional task managers require you to interact with them through a graphical user interface, <i> Tusk </i> allows you to manage your tasks quickly through keyboard commands.

With <i>Tusk</i>, you can add, delete and edit tasks without a fuss. Through shortcuts that you define, you can create custom commands that make <i> Tusk </i> entirely yours over time. Managing tasks has never been such a breeze.

Get started with <i>Tusk</i> now!

<br>  
## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.

1. Download the latest `tusk.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for <i>Tusk</i>.
3. Double-click the file to start the app. The GUI should appear in a few seconds.

 > <img src="images/UIDemo.png" width="600"><br>

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`help`** : lists all commands
   * **`add`**` Meeting on July 10 5pm` :
     adds a `Meeting` task on `July 10 5pm` to <i>Tusk</i>
   * **`alias`**` am add Meeting` : binds `am` as a shortcut for `add Meeting`
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>

<br>
## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items with `/` imply that only one of them is chosen.
> * Items enclosed in open(`[`) and closed(`]`) square brackets are optional.
> * Items enclosed in open(`(`) and closed(`)`) brackets are grouped together.
> * The order of parameters is fixed.

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

<br>
#### Adding a task: `add`
Adds a task to <i>Tusk</i>. <i>Tusk</i> supports three kinds of task: <i>Floating, Deadline</i> and <i>Event</i>, and a flexible date format. <br>

> **Date-Time Format**
> * Note that dates and timings follow [these formats](#date-time-format) <br>
> * Any deviations from the format may not be interpreted correctly.


##### Floating Tasks
These tasks only have a description. <br>
Format: `add TASK` or `add "TASK"` (double-inverted commas ("") forces the task to be a Floating task) <br>

Example:
* `add Project meeting`
* `add "Exam on 1 Oct"`

##### Deadline Tasks
These tasks have a description and a deadline (due date). <br>
Format: `add TASK by/on/at DATE` <br>

Examples:
* `add Project meeting by Oct 10`
* `add Project meeting on 10 October 2016`
* `add Project meeting at OcToBeR 10 2017`

##### Event Tasks
These tasks have a description, start date and an end date.<br>
Format: `add TASK from START_DATE to/- END_DATE` <br>

Examples:
* `add Project meeting from Oct1-Oct2`
* `add Project meeting from Oct 1 to Oct 2`
* `add Overseas work from 1 Aug 2016 to 31 Aug 2017`
* `add Overseas work from 1 August 2016 - 31 August 2017` <br>
  For event tasks, make sure that `START_DATE` is earlier than `END_DATE` or it will be rejected.


<br>
#### Listing all tasks : `list`
Shows a list of all the tasks.<br>
Format: `list`

<br>
#### Searching for tasks using keywords: `find`
Finds tasks that contain any of the keywords entered.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> Descriptions that contain a substring of any of the keywords entered will be returned as well (e.g. 'meeting' will match 'meetings'). <br>
> Tasks matching _at least_ one keyword will be returned

Examples:
* `find meeting` <br>
  Returns all tasks with the substring "meeting" in the description.
* `find lunch dinner` <br>
  Returns any task with "lunch" or "dinner" as substrings in the description.

<br>
#### Updating a task: `update`
Updates the <i>entire task</i>, only the <i>description</i> of the task or the <i>date</i> of the task.<br>

##### Update Task
Format (task): `update INDEX task UPDATED_VALUE` <br>
> `INDEX` refers to the index shown in the most recent listing. The index must be a positive integer 1, 2, 3… <br>
> `task`: Updates the entire task as if `add UPDATED_VALUE` has been executed <br>

Examples:
* `list`
* `update 1 task overseas from oct 31 to nov 1` <br>
  Updates the entire task as though so that the description is `overseas`, the start date is `oct 31` and the end date is `nov 1`
  <br>

##### Update Description
Format (description): `update INDEX desc UPDATED_VALUE` <br>
> `INDEX` refers to the index shown in the most recent listing. The index must be a positive integer 1, 2, 3… <br>
> `description`: Updates the description of the task with `UPDATED_VALUE` <br>

Examples:
* `list`
* `update 2 desc project discussion` <br>
  Updates the description of the 2nd task on the list with `project discussion`
  <br>

##### Update Date
Format (date): `update INDEX date UPDATED_VALUE` <br>
> `INDEX` refers to the index shown in the most recent listing. The index must be a positive integer 1, 2, 3… <br>
> `date`: Updates the date of the task with `UPDATED_VALUE` if it is already has a date

Examples:
* `list`
* `update 1 date 31 October 2016` <br>
  Updates the date of the 1st task on the list to `31 October 2016`
  <br><br>
* `find dinner`
* `update 1 date 6pm-7pm` <br>
  Updates the 1st task in the results of the `find` command with the new start time and end time if the task has a start date and an end date (event task)

<br>
#### Deleting a task: `delete`
Deletes the specified task.<br>
Format: `delete INDEX`

> `INDEX` refers to the index shown in the most recent listing. The index must be a positive integer 1, 2, 3… <br>

Examples:
* `list`
* `delete 2` <br>
  Deletes the 2nd task on the list
  <br><br>
* `find dinner`
* `delete 1` <br>
  Deletes the 1st task in the results of the `find` command

<br>
#### Clearing all entries : `clear`
Clears all entries from the currently displayed list in <i>Tusk</i>.<br>
Format: `clear`

<br>
#### Undoing an action: `undo`
Undoes the previous command.<br>
Format: `undo`

> Only 1 consecutive `undo` command is allowed. Therefore, typing `undo` twice will only undo the previous command and not the one before.

Examples:
* `list`
* `delete 1`
* `undo` <br>
  Undoes your latest `delete` command

<br>
#### Redoing an undo: `redo`
Redoes the previous undo command.<br>
Format: `redo`

> Only 1 consecutive `redo` command after an `undo` command is allowed.

Examples:
* `list`
* `delete 1`
* `undo` 
* `redo` <br>
  Redoes your latest `undo` command

<br>
#### Marking a task as completed: `complete`
Marks the specified task as completed.<br>
Format: `complete INDEX`

> `INDEX` refers to the index shown in the most recent listing. The index must be a positive integer 1, 2, 3… <br>
> Completed tasks can be accessed with `list complete[d]`.

Examples:
* `list`
* `complete 2` <br>
  Mark the 2nd task on the list as completed

<br>
#### Marking a task as incomplete: `uncomplete`
Marks the specified task as incompleted.<br>
Format: `uncomplete INDEX`

> `INDEX` refers to the index shown in the most recent listing. The index must be a positive integer 1, 2, 3… <br>

Examples:
* `list`
* `uncomplete 2` <br>
  Marks the 2nd task on the list as incomplete

<br>
#### Listing Completed Tasks: `list complete[d]`
Lists all the tasks that you have completed. <br>
Format: `list complete[d]`

> You can type either `complete` or its past tense form, `completed`.

Examples:
* `list complete`
* `list completed` <br>

<br>
#### Pinning a task: `pin`
Pins the specified task at the top of all lists. <br>
Format: `pin INDEX`

> `INDEX` refers to the index shown in the most recent listing. The index must be a positive integer 1, 2, 3… <br>
> The pinned task will appear at the top of any lists that include it. <br>

Examples:
* `list`
* `pin 2` <br>
  Pins the 2nd task in the task list.
<br><br>
* `find Dinner`
* `pin 1` <br>
  Pins the 1st task in the results of the `find` command.

<br>
#### Unpinning a task: `unpin`
Unpins the specified task (the default state). <br>
Format: `unpin INDEX`

> `INDEX` refers to the index shown in the most recent listing. The index must be a positive integer 1, 2, 3… <br>

Examples:
* `list`
* `unpin 2` <br>
  Unpins the 2nd task in the task list.

<br>
#### Setting an alias: `alias`
Sets a one-word alias for any sentence to be used as a command.<br>
Format: `alias SHORTCUT [ANY_SENTENCE]`

> `ANY_SENTENCE` is tagged to `SHORTCUT` so that if `SHORTCUT` is used as the first command, `ANY_SENTENCE` will be inserted in it's place instead. <br>
> `SHORTCUT` can only consist of one word.

Examples:
* `alias am add Meeting`
* `am` <br>
  Typing `am on July 10 5pm` is the same as `add Meeting on July 10 5pm`
<br><br>
* `alias s find Dinner`
* `s` <br>
  Typing `s` is the same as `find Dinner`

<br>
#### Deleting an alias: `unalias`
Deletes the alias that you have created previously.<br>
Format: `unalias SHORTCUT`

> If you have created `SHORTCUT` as an alias previously, it will be removed. <br>
> `SHORTCUT` must be an aliased command.

Examples:
* `alias am add Meeting` <br>
  Typing `am on July 10 5pm` is the same as `add Meeting on July 10 5pm`
* `unalias am` <br>
  Typing `am` no longer translates into `add Meeting`

<br>
#### Listing aliases: `list alias`
Lists all the aliases that you have created. <br>
Format: `list alias`

Examples:
* `list alias`
* `list aliases` <br>

<br>
#### Changing the storage location : `setstorage`
Changes the storage location for alias.xml and tasks.xml to wherever you prefer. <br>
Format: `setstorage PATH` <br>

Examples:
* `setstorage C:/Users/Bob/Documents`
* `setstorage relativefolder`

<br>
#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`

<br>
#### Saving the data
Task data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

<br>
## Command Autocomplete
Pressing <kbd>TAB</kbd> will autocomplete the first word by looking up all possible commands. <br>
If there is only one possible command, the command will be autocompleted. <br>
However, if there are multiple commands, subsequent <kbd>TAB</kbd>s will cycle through all possible commands one at a time. <br>

Examples:
* `al`<kbd>TAB</kbd><br>
  `alias` completed for you.
* `cl`<kbd>TAB</kbd><br>
  `clear` completed for you.
* `un`<kbd>TAB</kbd><br>
  `unpin`, `unalias`, `undo` and `uncomplete` are autocompleted as possible commands one at a time on subsequent presses of <kbd>TAB</kbd>.
* `unp`<kbd>TAB</kbd><br>
  `unpin` completed for you.

<br>
## Command History
Pressing the <kbd>UP</kbd> or <kbd>DOWN</kbd> arrow keys will cycle through all the commands that you have entered since <i>Tusk</i> was started. This also allows you to fix any incorrectly entered commands, make minor adjustments to commands previously entered, or simply enter the same command many times consecutively.

<br>
## Scrolling
Pressing <kbd>Ctrl</kbd> + <kbd>UP</kbd> or <kbd>Ctrl</kbd> + <kbd>DOWN</kbd> scrolls through the list of tasks.

<br>
## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the data folder it creates with the data folder from your original <i>Tusk</i> installation.

<br>
## Command Summary

Command | Format
-------- | :--------
Help | `help`
Add Floating tasks | `add TASK` or `add "TASK"`
Add Deadline tasks | `add TASK by/on/at DATE`
Add Event tasks | `add TASK from START_DATE to/- END_DATE`
List | `list`
Find | `find KEYWORD [MORE_KEYWORDS]`
Update/Edit task | `update/edit INDEX task UPDATED_VALUE`
Update/Edit description | `update/edit INDEX desc UPDATED_VALUE`
Update/Edit date | `update/edit INDEX date UPDATED_VALUE`
Delete | `delete INDEX`
Clear | `clear`
Undo | `undo`
Redo | `redo`
Complete | `complete INDEX`
Uncomplete | `uncomplete INDEX`
List completed| `list complete[d]`
Pin | `pin INDEX`
Unpin | `unpin INDEX`
Alias | `alias SHORTCUT ANY_SENTENCE`
Unalias | `unalias SHORTCUT`
List aliases | `list alias`
Set storage location | `setstorage PATH`
Exit | `exit`

<br>
## Date Format

Supported Date Format | Example
-------- | :--------
DD MM YYYY | 1 October 2017 <br> 01 October 2017 <br> 1 Oct 2017 <br> 01 Oct 2017
MM DD YYYY | October 1 2017 <br> October 01 2017 <br> Oct 1 2017 <br> Oct 01 2017
DD MM | 1 October <br> 1 Oct
MM DD | October 1 <br> Oct 1
Day | Today <br> Tomorrow <br> Tmr <br> Day after tmr <br> Thursday <br> Thu <br> Next Thursday <br> 


<br>
## Time Format

Supported Time Format | Example
-------- | :--------
HH:MM | 1:30 <br> 16:25
HH.MM | 1.30 <br> 16.25
HH:MM am/pm | 1:30am <br> 4:25pm
HH.MM am/pm | 1.30am <br> 4.25pm


<br>
## Date-Time Format
> If no timing is specified, a default value of 12am is assumed.
Supported Date-Time Format | Example
-------- | :--------
DATE TIME | 1 October 2017 1.30am <br> 1 Oct 1.30am <br> Today 10pm <br> Next Tues 16:25
TIME DATE | 1.30am 1 October 2017 <br> 1.30am 1 Oct <br> 10pm Today <br> 16:25 Next Tues
