# User Guide

## Table of Contents

* [About](#about)
* [Quick Start](#quick-start)
* [Features](#features)
    * [Viewing help : `help`](#viewing-help--help)
    * [Create a new task/event: `add`](#create-a-new-taskevent-add)
    * [Change the details of a task/event: `update`](#change-the-details-of-a-taskevent-update)
    * [Delete a task/event: `delete`](#delete-a-taskevent-delete)
    * [Mark a task as complete: `complete`](#mark-a-task-as-complete-complete)
    * [Undo the last action: `undo`](#undo-the-last-action-undo)
    * [List all tasks/events with specified conditions: `list`](#list-all-tasksevents-with-specified-conditions-list)
    * [Finding tasks/events which match keywords: `find`](#finding-tasksevents-which-match-keywords-find)
    * [Show only listing results with specified type, date or tags: `show`](#show-only-listing-results-with-specified-type-date-or-tags-show)
    * [Hide listing results with specified type, date or tags: `hide`](#hide-listing-results-with-specified-type-date-or-tags-hide)
    * [Clearing all data: `clear`](#clearing-all-data-clear)
    * [Switch to a different task list: `switchlist`](#switch-to-a-different-task-list-switchlist)
    * [Relocate the data storage location: `relocate`](#relocate-the-data-storage-location-relocate)
    * [Autocomplete and suggestions](#autocomplete-and-suggestions)
    * [Saving the Data](#saving-the-data)
    * [Multiple Storage Files](#multiple-storage-files)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## About

Many of us lead busy lives, with neverending streams of tasks often weighing on our minds.
We understand it all too well, and we want to lessen that burden on you.
This is the motivation behind TaSc, our Task Scheduler with usability at its core.

Organize your tasks with a single line of command.<br>
Need to add, delete or update a task? One line is all that it needs.<br>
Want to list, search or filter your tasks? One line is all that it takes.

TaSc is quick, simple, and contains all the functionalities you need to plan and record your tasks.<br>
Just type in your command, and hit enter. Let us handle the rest - you have more important things to do.


## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.

1. Download the latest `tasc.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your TaSc.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. You can try some of these example commands:
   * **`list`** : lists all uncompleted tasks and upcoming events
   * **`new`**` "Do Research" by 21 Sep 5pm` :
     adds a new task named "Do Research" with the deadline on 21 September, 5pm
   * **`complete`**` 3` : marks the 3rd task shown in the current list as complete
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Parameters can be input in any order
> * Items enclosed within `[ ]` are optional.
> * Items with `...` accept multiple instances. For example, [tag "TAG"...] means you can input multiple tags
> * Commas `,` and full stops `.` are optional and will not affect the command.
> * Command keywords are not case-sensitive.
> * Parameters relating to days of the week are taken relative to the time you input the command. For example, if you enter "Fri", it will be interpreted as the next Friday from *today*.

> **Date Format**
> * Accepted date formats: 18 Sep, 18 September, 18 Sep 2016, Sep 18 2016
> * Accepted time formats: 5pm, 5:01pm, 5:01:59pm, 17:00

> **Tasks**<br>
> Each task has a name, and belongs to one of these categories:
> * **Normal Task** has a deadline
> * **Event** has a starting time and ending time
> * **Task with Allocated Timeslot** has a deadline, starting time and ending time
> * **Floating Task** has no associated time limitation


> **Recurring tasks and events**
> Tasks and events may be set to repeat daily/weekly/monthly<br>
> They will recur after their respective deadlines or endtimes

>> Tasks or events which are exact duplicates are combined into one.

#### Viewing help : `help`
Format: `help`
Opens a new window displaying this document.

> If an invalid command (e.g `abcd`) or parameter is entered,
> help messages will be shown in the output box of the
> program.

#### Create a new task/event: `add`
Adds a new task or event to the task list.<br>
Format: `add "NAME" [by DEADLINE] [from START_TIME to END_TIME] [repeat PATTERN FREQUENCY] [tag "TAG"...]`

> Creates a new task with the specified name. The type of task may vary:
> * For a normal task, set a deadline
> * For an event, set a start time and end time
> * For a task with an allocated timeslot, set a deadline, start time and end time
> * For a floating task, no need to set deadline or start/end time <br>

> The recurrence of a task can be set with the `repeat` keyword.<br>
> The repeat pattern can be `daily`, `weekly`, or `monthly`.<br>
> The repeat frequency will represent how many times the recurrence will occur.<br>
>
> Tags allow you to organize your tasks by different categories (for example,
> you may use tags to prioritize certain tasks over others).
>
> *NOTE*: `add` command only accepts one task at a time.

Examples:
* `add "Hello World!"`
* `add "Meeting" from 21 Sep 3pm to 5pm`
* `add "Check sufficient toilet rolls" by 21 Sep 5pm, tag "Important"`
* `add "Lecture" from 7 Oct 2pm to 4pm, repeat weekly, tag "Important"`
* `add "3 Days Conference" from 18 Oct 9am to 21 Oct 5pm`

> <img src="images/Ui-Add.png" width="600">

#### Change the details of a task/event: `update`
Updates a task or event.<br>
Format: `update INDEX [name NAME] [by DEADLINE] [from START_TIME to END_TIME] [repeat PATTERN COUNT] [tag "TAG"...]`

> Updates the specified task with the given information.<br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...<br>
> The recurrence of a task can be set with the `repeat` keyword.<br>
> The repeat pattern can be `daily`, `weekly`, or `monthly`.<br>
> The repeat count represents how many times the recurrence will occur.<br>
>
> To remove any details for the task, prefix the argument keyword with `remove`.<br>
> For example: `update 1 removeby` will remove the deadline.

Examples:
* `list`<br>
  `update 1 name "Submit Proposal" by 23 Sep 3pm.`<br>
  Update the details of the first task in the list.<br>
* `update 2 from 23 Sep 3pm to 5pm`
* `update 1 by 20 Sep 5pm, tag "Not that important"`
* `update 3 removetag "Important"`

#### Delete a task/event: `delete`
Deletes a task/event.<br>
Format: `delete INDEX`

> Delete a task/event.<br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...<br>

Examples:
* `list`<br>
  `delete 1`<br>
  Delete the first task in the list.

#### Mark a task as complete: `complete`
Marks a task as completed.<br>

Format: `complete INDEX`

> When you are done with the task, you can mark it as complete instead
> of deleting it.<br>
>> Marking a task as completed will also hide it from the current listing results.<br>
>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...<br>

> <img src="images/Ui-Complete.png" width="600">

Example:
* `list`<br>
  `complete 1`<br>
  Mark the first task in the list as complete.

#### Undo the last action: `undo`
Undoes any previous action that modifies the task database (e.g. deleting task).<br>
Format: `undo [last STEPS]`

> You can undo multiple steps by specifying the number of steps to undo.

Example:
* `undo`<br>
  Undo the most recent action.
* `undo last 5`<br>
  Undo the last 5 actions.

#### List all tasks/events with specified conditions: `list`
Lists all tasks/events.<br>

Format: `list [TYPE...] [by DEADLINE] [from START_TIME] [to END_TIME] [tag "TAG"...] [sort SORTING_ORDER]`

> * Types include `uncompleted`, `completed`, `floating`, `recurring`, `events`, `tasks`, and/or `free time`.
> * Sorting order includes `earliest first`, `latest first` for date and time,
> and `a-z`, `z-a` for task descriptions.
> * Defaults to `earliest first` for dates in the future, and `latest first` for dates in the past
>
> If no parameters are specified, the command will show a list of uncompleted tasks
> and upcoming events.

> <img src="images/Ui-List.png" width="600">

> <img src="images/Ui-List2.png" width="600">

Examples:
* `list`<br>
  Shows a list of uncompleted tasks
  and upcoming events.
* `list events by 18 Sep`
* `list completed tasks, tag "Important", sort earliest first`
* `list free time from 20 Sep 10am to 8pm`

#### Finding tasks/events which match keywords: `find`
Lists all tasks/events whose name, type, date or tags partially match the entered keywords.<br>
Format: `find KEYWORD...`

> * KEYWORDs enclosed in quotation marks `" "` use exact match.
> * KEYWORDs not enclosed in `" "` are not case-sensitive.
> * The task list results are shown in an order which prioritizes the closest match, followed by completion status and date.

Examples:
* `find grad`<br>
  Shows tasks with names such as "Up**grad**e myself", and tags such as "**Grad**ed".
* `find "V0.0 Deliverables" sep`<br>
  Shows the task named "**V0.0 Deliverables**" in **Sep**tember.

#### Show only listing results with specified type, date or tags: `show`
Shows only tasks/events with specified type, date or tags from
the current task list results.<br>

Format: `show [TYPE...] [on DATE] [by DEADLINE] [from START_TIME] [to END_TIME] [tag "TAG"...]`

> Operates on the current task list. If you wish to filter from all tasks, use `list` instead.  
> * Matching results stay on the current task list at their respective positions, while other tasks/events are hidden.

Examples:
* `list`<br>
  `show tag "CS2103" "Important"`

* `list`<br>
  `show events on 24 Sep, tag "Important"`

* `list`<br>
  `show completed tasks, tag "CS2103", from 18 Sep 8am`

* `list`<br>
  `show tag "Meeting" by 11pm`

#### Hide listing results with specified type, date or tags: `hide`
Hides tasks/events with specified type, date or tags from the current task list results.<br>

Format: `hide [TYPE...] [on DATE] [by DEADLINE] [from START_TIME] [to END_TIME] [tag "TAG"...]`

> Operates on the current task list. If you wish to filter from all tasks, use `list` instead.  
> * Matching tasks/events will be hidden from the task list results.

Examples:
* `list`<br>
  `hide completed events from 24 Sep, tag "CS2010"`
  `hide tag "Ungraded"`


#### Clearing all data: `clear`
Removes all tasks from the data storage file.<br>

Format: `clear`

> Since this is a potentially destructive action, a confirmation would be shown
> before the tasks are removed.

#### Switch to a different task list: `switchlist`
Switches to a different task list.<br>

Format: `switchlist FILENAME`

> If the file does not exist, TaSc will assume that you want to create a new
> task list, and will create an empty file for you automatically.

#### Relocate the data storage location: `relocate`
Designates a new data storage location.<br>

Format: `relocate PATH`

#### Autocomplete and suggestions
Shows suggested command keywords, dates, sorting order, and tags as you type.

Use the up and down arrows to select a keyword in the list.<br>
Use the `tab` key to autocomplete with the highlighted keyword.

> <img src="images/Ui-Autocomplete.png" width="200">

#### Saving the Data
TaSc saves automatically after each command that changes the data.
No manual saving is required.

#### Multiple Storage Files
You can store different schedules on different storage files. Just pass the file name into the program arguments
when running the program.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Backup the contents of your current TaSc folder. Install the app in the other computer, and run it. It will create an empty data file. Overwrite the empty data file with the backup you made.

## Command Summary

Command | Format  
-------- | :--------
Help | `help`
Add | `add NAME [by DEADLINE] [from START_TIME to END_TIME] [repeat PATTERN FREQUENCY] [tag "TAG"...]`
List | `list [TYPE...] [by DEADLINE] [from START_TIME] [to END_TIME] [tag "TAG"...] [sort SORTING_ORDER]`
Find | `find KEYWORD...`
Show | `show [TYPE...] [on DATE] [by DEADLINE] [from START_TIME] [to END_TIME]  [tag "TAG"...]`
Hide | `hide [TYPE...] [on DATE] [by DEADLINE] [from START_TIME] [to END_TIME] [tag "TAG"...]`
Update | `update INDEX [name NAME] [by DEADLINE] [from START_TIME to END_TIME] [repeat PATTERN FREQUENCY] [tag "TAG"...]`
Delete | `delete INDEX`
Complete | `complete INDEX`
Undo | `undo [last STEPS]`
Clear | `clear`
Relocate | `relocate PATH`
