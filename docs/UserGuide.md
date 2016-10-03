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
4. Type the command in the command box and press <kbd>Enter<kbd> to execute it.<br>
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
    format: `help`

#### Adding a task or event: `add`
Adds a task or event to the task list.
Format: `add [TASK/EVENT] DESCRIPTION [-t TITLE] [due/at DATE TIME]`

> If the flag -t is omitted, the task or event will have no title.
> Adding tasks or events can also be done through simple English.
> If no command keyword is specified, the app assumes the given command is to add, and will interpret the input as a task.

Examples:
* `add task do laundry at JULY 24 5 PM`
    Adds a task with the description `do laundry` and the deadline `5PM 07/24`
* `write sql queries due tomorrow 9pm`
    Adds a task with the description `write sql queries` and the deadline `9PM [tomorrow]`, with tomorrow being whatever date the next day is.
* `complete software engineering project -t CS2103PROJECT`
    Adds a floating task with the description `complete software engineering project` and the title `CS2103PROJECT` with no deadline.


#### Listing all tasks and events: `list`
Shows a list of all the tasks and/or events in the program.
Format: `list [TASK/EVENT]`

> The list command alone lists all upcoming tasks and events.
> When appended with task or event, the program will show only the requested input (tasks or events).
> Can be combined with `view` to achieve a popup window of the task list.

#### List all tasks and events in calendar format: `calendar`
Shows all the tasks and events in calendar format.
Format: `calendar [TASK/EVENT]`

> The calendar command alone shows all upcoming tasks and events.
> When appended with task or event, the program will show only the requested input (tasks or events).
> Can be combined with `view` to achieve a popup window of the calendar

#### Viewing information in a pop-out window: `view`
Puts the information into a separate window for easier viewing. Should mainly be used in conjunction with `list` and `calendar` commands.
Format: `view [LIST/CALENDAR]`

> Must be followed by either list or calendar--`view` cannot be called on its own.
#### Finding all tasks and events with a given keyword in the description or title: `search`
Searches for tasks and events whose descriptions or titles contain any of the given keywords or dates.
Format: `search KEYWORD [MORE_KEYWORDS] [FLAGS]`
Flags:  -s: all keywords must be matched
        -d: search for date as well
        -t: search tasks only
        -e: search events only
        -b: search in description body only

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

#### Marking a task complete: `complete`
Marks the given task as completed from the active task list. Can be reversed if done immediately after.
Format: `complete INDEX [OTHER_INDICES]`

> Marks the task at the specified `INDEX`.
 The index refers to the index number shown in the most recent listing <br>
 The index **must be a positive integer** 1, 2, 3, ...
> Can mark multiple indices to be completed.

Examples:
*   `list`<br>
    `complete 2`<br>
Marks the 2nd task in the active task list complete.
*   `search write test for -s -b`<br>
    `complete 1 2`<br>
Marks the 1st two tasks as complete in the results of the search command

#### Updating a task: `update`
Updates a given task.
Format: `update INDEX [DESCRIPTION] [-t TITLE] [due/at DATE TIME]`

> Updates the tasks at the specified `INDEX`.
> Will update the task depending on what is supplied in the input. If no date or time is provided, the original task/event time will stay the same. Likewise with the description and title.

Examples:
* `update 2 due 09/08/2016 8PM`
    Updates the second task to have an updated deadline.
* `update 1 Redo Mission class because failed code quality check due tomorrow 9pm`
    Updates the first task to have the updated description `Redo Mission class because failed code quality check` and the updated date `9PM [tomorrow]` with tomorrow being whatever date the next day is.

#### Clearing all entries: `clear`
Clears all tasks and events from the program.
Format: `clear`

#### Exiting the program: `exit`
Exits the program <br>
Format: `exit`

#### Setting the data storage location: `storage`
Sets the data storage path. Must be a valid path.
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
Add | `add [TASK/EVENT] DESCRIPTION [-t TITLE] [due/at DATE TIME]` OR `DESCRIPTION [-t TITLE] [due/at DATE TIME]`
Update | `update INDEX [DESCRIPTION] [-t TITLE] [due/at DATE TIME]`
Clear | `clear`
Complete | `complete INDEX [MORE_INDICES]`
Search | `search KEYWORD [MORE_KEYWORDS] [FLAGS]`
List | `list [TASK/EVENT]`
Calendar | `calendar [TASK/EVENT]`
View | `view [LIST/CALENDAR] [TASK/EVENT]`
Help | `help`
Storage | `storage PATH`
