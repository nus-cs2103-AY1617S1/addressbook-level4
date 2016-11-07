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
2. Copy the file to the folder you want to use as the home folder for your JYM.
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
> * Parameters can be in any order.

#### Viewing help : `help`
Format: `help`

#### Adding a task or event: `add`
Adds a task or event to the task list. <br>
Format: `add [TASK/EVENT] DESCRIPTION [due/at/by DATE START_TIME END_TIME] [PRIORITY]`

> Adding tasks or events can also be done through simple English.<br>
> If only one time is specified, it will be interpreted as a deadline. Otherwise, the event will use the input as start and end time.
> If no command keyword is specified, the app assumes the given command is to add, and will interpret the input as a task or event depending on whether a start and end time is given or not. <br>

<<<<<<< HEAD
Examples:
* `do laundry at home JULY 24 5 PM`
    Adds a task with the description `do laundry` and the deadline `5PM 07/24`
* `write sql queries by tomorrow 9pm`
    Adds a task with the description `write sql queries` and the deadline `9PM [tomorrow]`, with tomorrow being whatever date the next day is.
* `dinner with jack tomorrow at 5 pm to 6pm`
    Adds an event with the description `dinner with jack` with the time 5 to 6 pm tomorrow.
=======
Examples: 
* `add John Doe p/98765432 e/johnd@gmail.com a/John street, block 123, #01-01`
* `add Betsy Crowe t/friend e/betsycrowe@gmail.com a/Newgate Prison p/1234567 t/criminal`
>>>>>>> nus-cs2103-AY1617S1/master

#### Listing all tasks and events: `list`
Shows a list of all the tasks and/or events in the program. <br>
Format: `list`

> The list command alone lists all upcoming tasks and events.<br>
> When appended with task or event, the program will show only the requested input (tasks or events). <br>
> By default list shows upcoming active tasks and events.

<!-- #### List all tasks and events in calendar format: `calendar`
Shows all the tasks and events in calendar format. <br>
Format: `calendar [TASK/EVENT]` -->

<!-- > The calendar command alone shows all tasks and events. <br>
> When appended with task or event, the program will show only the requested input (tasks or events). <br>
> Can be combined with `view` to achieve a popup window of the calendar -->

<!-- #### Viewing information in a pop-out window: `view`
Puts the information into a separate window for easier viewing. Should mainly be used in conjunction with `list` and `calendar` commands. <br>
Format: `view [LIST/CALENDAR]`

> Must be followed by either list or calendar-`view` cannot be called on its own. -->

#### Finding all tasks and events with a given keyword in the description or title: `find`
Searches for tasks and events whose descriptions or titles contain any of the given keywords or dates.<br>
Format: `find KEYWORD [MORE_KEYWORDS]` <br>

> * By appending certain keywords onto the search, one can filter results. e.g. `completed` will search for completed tasks only, `ordered` will search for tasks with all the keywords in the given order and grouped together.
> * Search is not case sensitive. e.g. `ChiCKEN` will match `cHIcken` 
> * Order of the keywords does not matter. e.g. `do this` will match `this do`. This can be changed by adding specific keywords.
> * The description and date are both searched. e.g. `eat dinner july 30` will result in tasks and events from july 30 matching `eat dinner`
> * Tasks matching at least one keyword will be returned (not including dates). These settings can be changed by setting flags in the command.

<!-- > * By default search will search for all tasks and events. By appending `all` to the end of the search, one can search among all events and tasks. `completed` is mentioned earlier as well. -->

Examples:
* `find write SQL queries july 21` <br>
    Returns upcoming tasks with `write SQL queries` in the description on july 21.
* `find birthday all` <br>
    Returns all events with `birthday` in the description or title.
* `find CS2103 final` <br>
    Returns upcoming tasks and events with `CS2103 final` in the description
    
#### Undo mistaken commands: `undo`
Reverses the last command done. Repeated calls to this command will undo each command in the reverse order of that which they were called. Can only go back up to 20 commands in history via undos. <br>
Format: `undo`

<!--- @@author a0153617e -->

#### Marking a task complete: `complete`
Marks the given task as completed from the active task list. Can be reversed if done immediately after. <br>
Format: `complete INDEX`

> Marks the task at the specified `INDEX`.
 The index refers to the index number shown in the most recent listing <br>
 The index **must be a positive integer** 1, 2, 3, ...<br>
> Can mark multiple indices to be completed.

Examples:
*   `list`<br>
    `complete 2`<br>
Marks the 2nd task in the active task list complete.
*   `find write test for`<br>
    `complete 1`<br>
Marks the 1st tasks as complete in the results of the find command

#### Updating a task: `update`
Updates a given task. <br>
Format: `update INDEX [DESCRIPTION] [by/at DATE TIME]`

> Updates the tasks at the specified `INDEX`. <br>
> Will update the task depending on what is supplied in the input. If no date or time is provided, the original task/event time will stay the same. Likewise with the description.

<!-- > To clear a date, use the key word `never` after `due` or `at`. e.g. `due never`. To clear priority, set it to 0. 
> Description cannot be removed, as it is necessary to determine what the task is. To clear the description and not replace it is the same as deleting the task. As such, that function is not implemented. -->

Examples:
* `update 2 by 09/08/2016 8PM` <br>
    Updates the second task to have an updated deadline.
* `update 1 Redo Mission class because failed code quality check by tomorrow 9pm` <br>
    Updates the first task to have the updated description `Redo Mission class because failed code quality check` and the updated date `9PM [tomorrow]` with tomorrow being whatever date the next day is.

<!-- * `update 2 fix program due never priority 0`
    Updates the second task to become a floating task with no priority, changing the description to `fix program` -->
    
#### Clearing all entries: `clear`
Clears all tasks and events from the program. <br>
Format: `clear`

#### Exiting the program: `exit`
Exits the program <br>
Format: `exit`

#### Setting the data storage location: `saveto`
Sets the data storage path. Must be a valid path. <br>
Format: `saveto PATH`
Example:
* `saveto MyDropbox` <br>
    It will create a folder `MyDropbox` under the current path where the program is and save the data file when user start to add task.

#### Deleting tasks: `delete` 
Deletes tasks or events for when you wish to remove them entirely from the list. <br>
<!-- Format: `delete INDEX [LEFT/RIGHT/INCOMPLETED/COMPLETED]` -->
Format: `delete INDEX`

#### Saving the data
Data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

##FAQ
**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous session.

**Q**: How do I backup my data?<br>
**A**: The easy way is to use the `saveto PATH` command, where the PATH points to your cloud folder (Google drive, dropbox, iCloud). In that way, everytime when you save your data, it will automatically save inside the cloud folder. Also, remember to set your cloud folder sync automatically.

## Command Summary

<<<<<<< HEAD
Command | Format  
-------- | :--------
Add | `DESCRIPTION [at LOCATION] [at/by DATE TIME]` OR `DESCRIPTION [by/at DATE START_TIME to END_TIME]`
Update | `update INDEX [DESCRIPTION] [by/at DATE START_TIME to END_TIME]`
Clear | `clear`
Undo | `undo`
Complete | `complete INDEX`
Find | `find KEYWORD [MORE_KEYWORDS]`
List | `list`
Help | `help`
Saveto | `saveto PATH`
Delete | `delete INDEX`
=======
* **Add**  `add NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]...` <br>
  e.g. `add James Ho p/22224444 e/jamesho@gmail.com a/123, Clementi Rd, 1234665 t/friend t/colleague`

* **Clear** : `clear`
  
* **Delete** : `delete INDEX` <br> 
   e.g. `delete 3`
  
* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find James Jake`
  
* **List** : `list` <br>
  e.g.
  
* **Help** : `help` <br>
  e.g.
  
* **Select** : `select INDEX` <br>
  e.g.`select 2`
  

>>>>>>> nus-cs2103-AY1617S1/master
