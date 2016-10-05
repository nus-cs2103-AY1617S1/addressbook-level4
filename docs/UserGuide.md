# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.

1. Download the latest '[insert program name here]' from the releases (../../../release) tab
2. Copy the file to the folder you want to use as the home folder for your [program name]
3. Double click the file to start the app. The GUI should appear in a few seconds.
4. Type the command in the command box and press <kbd>Enter</kbd> to execute it.<br>
    e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:

*    **`list`** : lists all tasks and events.
*    **`add`**` Insert Program Stub for Testing due tomorrow at 5pm` :
adds a task named with the given description to the task list.
*    **`delete`**` 3` : deletes the 3rd task shown in the current list.
*    **`exit`** : exits the app

Refer to the [Features](#features) section below for details of each command.<br>

## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.

#### Viewing help : `help`
Format: `help`

#### Adding a task or event: `add`
Adds a task or event to the task list. <br>
Format: `add [TASK/EVENT] DESCRIPTION [-t TITLE] [due/at DATE TIME] [PRIORITY]`

> If the flag -t is omitted, the task or event will have no title.<br>
> Adding tasks or events can also be done through simple English.<br>
> If no command keyword is specified, the app assumes the given command is to add, and will interpret the input as a task. <br>
> `priority` can have values of 1 through 10. If a number greater than 10 or lower than 1 is input, the value will simply be either 10 or 1, depending on which boundary is exceeded.

Examples:
* `add task do laundry at JULY 24 5 PM priority 3`
    Adds a task with the description `do laundry` and the deadline `5PM 07/24`
* `write sql queries due tomorrow 9pm`
    Adds a task with the description `write sql queries` and the deadline `9PM [tomorrow]`, with tomorrow being whatever date the next day is.
* `complete software engineering project -t CS2103PROJECT priority 10`
    Adds a floating task with the description `complete software engineering project` and the title `CS2103PROJECT` with no deadline.


#### Listing all tasks and events: `list`
Shows a list of all the tasks and/or events in the program. <br>
Format: `list [TASK/EVENT] [COMPLETED/ALL]`

> The list command alone lists all upcoming tasks and events.<br>
> When appended with task or event, the program will show only the requested input (tasks or events). <br>
> Can be combined with `view` to achieve a popup window of the task list.<br>
> If `completed` or `all` is appended to the end of the command, the list will only show either active or completed tasks/events. <br>
> By default list shows upcoming active tasks and events.

#### List all tasks and events in calendar format: `calendar`
Shows all the tasks and events in calendar format. <br>
Format: `calendar [TASK/EVENT]`

> The calendar command alone shows all tasks and events. <br>
> When appended with task or event, the program will show only the requested input (tasks or events). <br>
> Can be combined with `view` to achieve a popup window of the calendar

#### Viewing information in a pop-out window: `view`
Puts the information into a separate window for easier viewing. Should mainly be used in conjunction with `list` and `calendar` commands. <br>
Format: `view [LIST/CALENDAR]`

> Must be followed by either list or calendar-`view` cannot be called on its own.

#### Finding all tasks and events with a given keyword in the description or title: `search`
Searches for tasks and events whose descriptions or titles contain any of the given keywords or dates.<br>
Format: `search KEYWORD [MORE_KEYWORDS] [FLAGS]` <br>
Flags:  -s: all keywords must be matched<br>
        -d: search for date as well<br>
        -t: search tasks only<br>
        -e: search events only<br>
        -b: search in description body only<br>

> * Search is not case sensitive. e.g. `ChiCKEN` will match `cHIcken` 
> * Order of the keywords does not matter. e.g. `do this` will match `this do`
> * The description, title, and date (if appropriate flag is given) are all searched. e.g. `eat dinner -d july 30` will result in tasks and events from july 30 matching `eat dinner`
> * Tasks matching at least one keyword will be returned (not including dates). These settings can be changed by setting flags in the command.

Examples:
* `search write SQL queries -s -t` <br>
    Returns all tasks with `write SQL queries` in the description or title.
* `search birthday -e` <br>
    Returns all events with `birthday` in the description or title.
* `search CS2103 final -b -s` <br>
    Returns all tasks and events with `CS2103 final` in the description
    
#### Undo mistaken commands: `undo`
Reverses the last command done. Repeated calls to this command will undo each command in the reverse order of that which they were called. <br>
Format: `undo`

#### Marking a task complete: `complete`
Marks the given task as completed from the active task list. Can be reversed if done immediately after. <br>
Format: `complete INDEX [OTHER_INDICES]`

> Marks the task at the specified `INDEX`.
 The index refers to the index number shown in the most recent listing <br>
 The index **must be a positive integer** 1, 2, 3, ...<br>
> Can mark multiple indices to be completed.

Examples:
*   `list`<br>
    `complete 2`<br>
Marks the 2nd task in the active task list complete.
*   `search write test for -s -b`<br>
    `complete 1 2`<br>
Marks the 1st two tasks as complete in the results of the search command

#### Updating a task: `update`
Updates a given task. <br>
Format: `update INDEX [DESCRIPTION] [-t TITLE] [due/at DATE TIME] [PRIORITY]`

> Updates the tasks at the specified `INDEX`. <br>
> Will update the task depending on what is supplied in the input. If no date or time is provided, the original task/event time will stay the same. Likewise with the description and title.

Examples:
* `update 2 due 09/08/2016 8PM` <br>
    Updates the second task to have an updated deadline.
* `update 1 Redo Mission class because failed code quality check due tomorrow 9pm` <br>
    Updates the first task to have the updated description `Redo Mission class because failed code quality check` and the updated date `9PM [tomorrow]` with tomorrow being whatever date the next day is.

#### Clearing all entries: `clear`
Clears all tasks and events from the program. <br>
Format: `clear`

#### Exiting the program: `exit`
Exits the program <br>
Format: `exit`

#### Setting the data storage location: `storage`
Sets the data storage path. Must be a valid path. <br>
Format: `storage PATH`

#### Saving the data
Data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

##FAQ
**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous session.

## Command Summary

Command | Format  
-------- | :--------
Add | `add [TASK/EVENT] DESCRIPTION [-t TITLE] [due/at DATE TIME] [PRIORITY]\n` `DESCRIPTION [-t TITLE] [due/at DATE TIME] [PRIORITY]`
Update | `update INDEX [DESCRIPTION] [-t TITLE] [due/at DATE TIME] [PRIORITY]`
Clear | `clear`
Complete | `complete INDEX [MORE_INDICES]`
Search | `search KEYWORD [MORE_KEYWORDS] [FLAGS]`
List | `list [TASK/EVENT]`
Calendar | `calendar [TASK/EVENT]`
View | `view [LIST/CALENDAR] [TASK/EVENT]`
Help | `help`
Storage | `storage PATH`
