# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.

1. Download the latest version of JYM from the releases [releases](../../../releases) tab
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

<!--- @@author A0153440R -->

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.

#### Viewing help : `help`
Format: `help`

#### Adding a task or event: `add`
Adds a task or event to the task list. <br>
Format: `add [TASK/EVENT] DESCRIPTION [due/at/by DATE START_TIME END_TIME] [PRIORITY]`

> Adding tasks or events can also be done through simple English.<br>
> If only one time is specified, it will be interpreted as a deadline. Otherwise, the event will use the input as start and end time.
> If no command keyword is specified, the app assumes the given command is to add, and will interpret the input as a task or event depending on whether a start and end time is given or not. <br>
> `priority` can have values of 1 through 9. If a number greater than 9 or lower than 0 is input, the value will simply be either 10 or 1, depending on which boundary is exceeded.
> If `priority` is given the value 0, it will remove the priority from the task. Since priority is optional, this only has relevance when updating, because when creating a task with no priority, simply omit priority at the end.
> `priority` must be preceded with the actual word. Can be shortened to `p` as well. Otherwise, the command is less readable, and ultimately less natural (who appends numbers randomly onto tasks..? The number must be labeled.)

Examples:
* `add task do laundry at JULY 24 5 PM priority 3`
    Adds a task with the description `do laundry` and the deadline `5PM 07/24`
* `write sql queries due tomorrow 9pm`
    Adds a task with the description `write sql queries` and the deadline `9PM [tomorrow]`, with tomorrow being whatever date the next day is.
* `complete software engineering project -t CS2103PROJECT priority 9`
    Adds a floating task with the description `complete software engineering project` and the title `CS2103PROJECT` with no deadline.
* `dinner with jack tomorrow at 5 pm to 6pm`
    Adds an event with the description `dinner with jack` with the time 5 to 6 pm tomorrow.

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
Format: `search KEYWORD [MORE_KEYWORDS]` <br>

> * By appending certain keywords onto the search, one can filter results. e.g. `completed` will search for completed tasks only, `ordered` will search for tasks with all the keywords in the given order and grouped together.
> * Search is not case sensitive. e.g. `ChiCKEN` will match `cHIcken` 
> * Order of the keywords does not matter. e.g. `do this` will match `this do`. This can be changed by adding specific keywords.
> * The description and date are both searched. e.g. `eat dinner july 30` will result in tasks and events from july 30 matching `eat dinner`
> * Tasks matching at least one keyword will be returned (not including dates). These settings can be changed by setting flags in the command.
> * By default search will search for upcoming tasks and events only. By appending `all` to the end of the search, one can search among all events and tasks. `completed` is mentioned earlier as well.

Examples:
* `search write SQL queries july 21` <br>
    Returns upcoming tasks with `write SQL queries` in the description on july 21.
* `search birthday all` <br>
    Returns all events with `birthday` in the description or title.
* `search CS2103 final` <br>
    Returns upcoming tasks and events with `CS2103 final` in the description
    
#### Undo mistaken commands: `undo`
Reverses the last command done. Repeated calls to this command will undo each command in the reverse order of that which they were called. Can only go back up to 20 commands in history via undos. <br>
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
*   `search write test for`<br>
    `complete 1 2`<br>
Marks the 1st two tasks as complete in the results of the search command

#### Updating a task: `update`
Updates a given task. <br>
Format: `update INDEX [DESCRIPTION] [due/at DATE TIME] [PRIORITY]`

> Updates the tasks at the specified `INDEX`. <br>
> Will update the task depending on what is supplied in the input. If no date or time is provided, the original task/event time will stay the same. Likewise with the description.
> To clear a date, use the key word `never` after `due` or `at`. e.g. `due never`. To clear priority, set it to 0. 
> Description cannot be removed, as it is necessary to determine what the task is. To clear the description and not replace it is the same as deleting the task. As such, that function is not implemented.

Examples:
* `update 2 due 09/08/2016 8PM` <br>
    Updates the second task to have an updated deadline.
* `update 1 Redo Mission class because failed code quality check due tomorrow 9pm` <br>
    Updates the first task to have the updated description `Redo Mission class because failed code quality check` and the updated date `9PM [tomorrow]` with tomorrow being whatever date the next day is.
* `update 2 fix program due never priority 0`
    Updates the second task to become a floating task with no priority, changing the description to `fix program`
    
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
Add | `Format: add [TASK/EVENT] DESCRIPTION [due/at/by DATE START_TIME END_TIME] [PRIORITY]` OR `DESCRIPTION [due/at DATE START_TIME END_TIME] [PRIORITY]`
Update | `update INDEX [DESCRIPTION] [due/at DATE START_TIME END_TIME] [PRIORITY]`
Clear | `clear`
Undo | `undo`
Complete | `complete INDEX [MORE_INDICES]`
Search | `search KEYWORD [MORE_KEYWORDS]`
List | `list [TASK/EVENT]`
Calendar | `calendar [TASK/EVENT]`
View | `view [LIST/CALENDAR] [TASK/EVENT]`
Help | `help`
Storage | `storage PATH`
