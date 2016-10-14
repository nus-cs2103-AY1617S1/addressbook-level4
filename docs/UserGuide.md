# User Guide

## Table of Contents

* [Introduction](#introduction)
* [Quick Start](#quick-start)
   * [Installing](#installing)
   * [Launching](#launching)
   * [Using the Interface](#using-the-interface)
* [Features](#features)
   * [Tasks](#tasks)
   * [Commands Format](#commands-format)
      * [How to read the format of the commands in the User Guide](#how-to-read-the-format-of-the-commands-in-the-user-guide)
   * [Parameters Format](#parameters-format)
   * [Tags](#tags)
   * [Calendar](#calendar)
* [Commands](#commands)
   * [Creating a new task/event: `add`](#creating-a-new-taskevent-add)
   * [List all tasks/events with specified conditions: `list`](#list-all-tasksevents-with-specified-conditions-list)
   * [Show only listing results with specified type, date or tags: `show`](#show-only-listing-results-with-specified-type-date-or-tags-show)
   * [Hide listing results with specified type, date or tags: `hide`](#hide-listing-results-with-specified-type-date-or-tags-hide)
   * [Finding tasks/events which match keywords: `find`](#finding-tasksevents-which-match-keywords-find)
   * [Changing the details of a task/event: `update`](#changing-the-details-of-a-taskevent-update)
   * [Marking a task as complete: `complete`](#marking-a-task-as-complete-complete)
   * [Deleting a task/event: `delete`](#deleting-a-taskevent-delete)
   * [Undo the last action: `undo`](#undo-the-last-action-undo)
   * [Clearing all data: `clear`](#clearing-all-data-clear)
   * [Switch to a different task list: `switchlist`](#switch-to-a-different-task-list-switchlist)
   * [Relocate the data storage location: `relocate`](#relocate-the-data-storage-location-relocate)
   * [Viewing help : `help`](#viewing-help--help)
* [Other Features](#other-features)
   * [Autocomplete and suggestions](#autocomplete-and-suggestions)
   * [Saving the Data](#saving-the-data)
   * [Multiple Storage Files](#multiple-storage-files)
* [FAQ](#faq)
* [Commands Cheat Sheet](#commands-cheat-sheet)

## Introduction

Organize your tasks with just a *single* line of command.

Need to add, delete or update a task? *One line* is all that it needs.<br>
Want to list, search or filter your tasks? *One line* is all that it takes.

Many of us lead busy lives, with never ending streams of tasks often weighing on our minds. We understand it all too well, and we want to lessen that burden for you.

This is the motivation behind TaSc, our Task Scheduler with keyboard usability at its core. TaSc is quick, simple, and contains all the functionalities you need to plan and record your tasks.

Just type in your command, and hit <kbd>Enter</kbd>. Let us handle the rest - you have more important things to do.


## Quick Start

### Installing
1. Ensure you have Java version `1.8.0_60` or later installed on your computer.<br>
> **Note:**<br>
> Having any Java 8 version is not enough. <br>
> This app will not work with earlier versions of Java 8.

2. Download the latest `TaSc.jar` from the [releases](../../../releases) tab (you can find this tab in our GitHub project website).

   <img src="images/github-download-release.png" width="600">

3. Copy the file to the folder you want to use as the home folder for your TaSc application.

### Launching

Double-click the file to start the application. The program interface should appear in a few seconds.

<img src="images/Ui.png" width="600">

### Using the Interface

Type the command in the command box and press <kbd>Enter</kbd> to execute it. (For example, typing **`help`** and pressing <kbd>Enter</kbd> will open the help window showing this document.)

You can try some of these example commands:
   * **`list`** : lists all uncompleted tasks and upcoming events
   * **`add`**` "Do Research" by 21 Sep 5pm` :
     adds a new task named "Do Research" with the deadline on 21 September, 5pm
   * **`complete`**` 3` : marks the 3rd task shown in the current list as complete
   * **`exit`** : exits the application

> **Tip:**<br>
> You may refer to the [Commands](#commands) section for details of each command.


## Features

### Tasks

The main purpose of TaSc is to help you manage your tasks easily.

Tasks in TaSc can contain the following details:
* **Name:** name of the task (this is the only compulsory field)
* **Deadline:** due date of the task
* **Period:** time slot whereby the task should be executed
* **Recurrence:** future re-occurrences of the same task (on daily, weekly or monthly basis)

> **Note:**<br>
> Tasks which are exact duplicates will be combined into one.

> **Tip:**<br>
> You may manage your tasks in TaSc in any kind of way to suit your needs. For example:
> * A *floating task* is just a task with a name. No deadlines or timings are needed for this kind of tasks (useful if no details for the task are available yet).
> * A *normal task* is a task that has a deadline.
> * An *event* is a "task" that has a starting time and ending time.
> * A *task with an allocated timeslot* is a task that has a deadline, and has been allocated a starting time and ending time in order to work on that task.

### Commands Format
Commands are the main way you would be interacting with TaSc. See the
[Commands](#commands) section for the commands available in the program.

Some commands uses keywords, which allows you to supply additional
parameters to the program. For example, the `add` command, which adds
a new task, has the `by` keyword to allow you to provide a deadline
parameter to the new task (e.g. `by 24 Sep`).

Commands may have multiple keywords. They can be entered in any order
(but the command must always be at the front).

> **Tip:**<br>
> Command and keywords are not case-sensitive.

#### How to read the format of the commands in the User Guide
The user guide provides you with the format of the commands for quick
references. For example, this is the format of the `add` command:

`add "NAME" [by DEADLINE] [from START_TIME to END_TIME] [repeat PATTERN FREQUENCY] [tag "TAG"...]`
* Words in `UPPER_CASE` are the parameters (usually a keyword is at the front).
* Items enclosed within `[ ]` are optional.
* Items with `...` accepts multiple parameters. For example, `[tag "TAG"...]` means you can input multiple tags
* Commas `,` and full stops `.` are optional and will not affect the command.

### Parameters Format
* Accepted date formats: 18 Sep, 18 September, 18 Sep 2016, Sep 18 2016
* Accepted time formats: 5pm, 5:01pm, 5:01:59pm, 17:00
* Accepted recurrence patterns: daily, weekly, monthly

Parameters relating to days of the week are taken relative to the time you input the command. For example, if you enter "Fri", it will be interpreted as the next Friday from *today*.

### Tags
Tags allow you to organize your tasks by different categories (for example, you may use tags to prioritize certain tasks over others).

### Calendar
TaSc provides a calendar view for you to visualise your tasks.

//TODO image of the calendar UI

## Commands

### Creating a new task/event: `add`
Adds a new task/event to the task list.

Format: `add "NAME" [by DEADLINE] [from START_TIME to END_TIME] [repeat PATTERN FREQUENCY] [tag "TAG"...]`

The repeat frequency will represent how many times the recurrence will occur.<br>

> **Note:**<br>
> * `add` command only accepts one task at a time.
> * See the [Command Formats](#command-formats) section to understand the
> format for this guide.
> * See the [Parameters Format](#parameters-format) section regarding
> the accepted date format, recurrence pattern and recurrence frequency.

<img src="images/Ui-Add.png" width="600">

Examples:
* `add "Hello World!"`
* `add "Meeting" from 21 Sep 3pm to 5pm`
* `add "Check sufficient toilet rolls" by 21 Sep 5pm, tag "Important"`
* `add "Lecture" from 7 Oct 2pm to 4pm, repeat weekly, tag "Important"`
* `add "3 Days Conference" from 18 Oct 9am to 21 Oct 5pm`

### List all tasks/events with specified conditions: `list`
Lists all tasks/events.

Format: `list [TYPE...] [by DEADLINE] [from START_TIME] [to END_TIME] [tag "TAG"...] [sort SORTING_ORDER]`

If all parameters are omitted (i.e. you just type `list`),
the command will show a list of uncompleted tasks and upcoming events.

* Types include `uncompleted`, `completed`, `floating`, `recurring`, `events`, `tasks`, and/or `free time`.
* Sorting order includes `earliest first`, `latest first` for date and time, and `a-z`, `z-a` for task descriptions.
* Defaults to `earliest first` for dates in the future, and `latest first` for dates in the past

<img src="images/Ui-List.png" width="600">

<img src="images/Ui-List2.png" width="600">

Examples:
* `list`<br>
  Shows a list of uncompleted tasks
  and upcoming events.
* `list events by 18 Sep`
* `list completed tasks, tag "Important", sort earliest first`
* `list free time from 20 Sep 10am to 8pm`

### Show only listing results with specified type, date or tags: `show`
Shows only tasks/events with specified type, date or tags from
the current task list results.

Format: `show [TYPE...] [on DATE] [by DEADLINE] [from START_TIME] [to END_TIME] [tag "TAG"...]`

Matching results stay on the current task list at their respective positions, while other tasks/events are hidden.

> **Note:**<br>
> This command operates on the current task list. If you wish to filter from all tasks, use `list` instead.  

Examples:
* `list`<br>
  `show tag "CS2103" "Important"`

* `list`<br>
  `show events on 24 Sep, tag "Important"`

* `list`<br>
  `show completed tasks, tag "CS2103", from 18 Sep 8am`

* `list`<br>
  `show tag "Meeting" by 11pm`

### Hide listing results with specified type, date or tags: `hide`
Hides tasks/events with specified type, date or tags from the current task list results.

Format: `hide [TYPE...] [on DATE] [by DEADLINE] [from START_TIME] [to END_TIME] [tag "TAG"...]`

Matching tasks/events will be hidden from the task list results.

> **Note:**<br>
> This command operates on the current task list. If you wish to filter from all tasks, use `list` instead.  

Examples:
* `list`<br>
  `hide completed events from 24 Sep, tag "CS2010"`
  `hide tag "Ungraded"`

### Finding tasks/events which match keywords: `find`
Lists all tasks/events whose name, type, date or tags partially match the entered keywords.

Format: `find KEYWORD...`

> * KEYWORDs enclosed in quotation marks `" "` use exact match.
> * KEYWORDs not enclosed in `" "` are not case-sensitive.
> * The task list results are shown in an order which prioritizes the closest match, followed by completion status and date.

Examples:
* `find grad`<br>
  Shows tasks with names such as "Up**grad**e myself", and tags such as
  "**Grad**ed".
* `find "V0.0 Deliverables" sep`<br>
  Shows the task named "**V0.0 Deliverables**" in **Sep**tember.

### Changing the details of a task/event: `update`
Updates details of a task or event.

Format: `update INDEX [name NAME] [by DEADLINE] [from START_TIME to END_TIME] [repeat PATTERN FREQUENCY] [tag "TAG"...]`

The recurrence of a task can be set with the `repeat` keyword.

The repeat frequency represents how many times the recurrence will occur.

> **Note:**<br>
> * The index refers to the index number shown in the most recent listing (e.g. when you use `list`).<br>
> * The index **must be a positive integer** 1, 2, 3, ...<br>
> * To remove any details for the task, prefix the argument keyword with `remove`.<br>
>     * For example: `update 1 removeby` will remove the deadline.

Examples:
* `list`<br>
  `update 1 name "Submit Proposal" by 23 Sep 3pm.`<br>
  Update the details of the first task in the list.<br>
* `update 2 from 23 Sep 3pm to 5pm`
* `update 1 by 20 Sep 5pm, tag "Not that important"`
* `update 3 removetag "Important"`

### Marking a task as complete: `complete`
Marks a task as completed.

Format: `complete INDEX`

When you are done with the task, you can mark it as complete.

Marking a task as completed will also hide it from the current listing results.

> **Note:**<br>
> * The index refers to the index number shown in the most recent listing (e.g. when you use `list`).<br>
> * The index **must be a positive integer** 1, 2, 3, ...<br>

<img src="images/Ui-Complete.png" width="600">

Example:
* `list`<br>
  `complete 1`<br>
  Mark the first task in the list as complete.

### Deleting a task/event: `delete`
Deletes a task/event.

Format: `delete INDEX`

You may use this instead of `complete` if you want to clean up
your task list to save disk space.

> **Note:**<br>
> * The index refers to the index number shown in the most recent listing (e.g. when you use `list`).<br>
> * The index **must be a positive integer** 1, 2, 3, ...<br>

Examples:
* `list`<br>
  `delete 1`<br>
  Delete the first task in the list.

###  Undo the last action: `undo`
Undoes any previous action that modifies the task list (e.g. deleting task)

Format: `undo [last STEPS]`

You can undo multiple steps by specifying the number of steps to undo.

Example:
* `undo`<br>
  Undo the most recent action.
* `undo last 5`<br>
  Undo the last 5 actions.

### Clearing all data: `clear`
Removes all tasks from the data storage file.<br>

Format: `clear`

> **Note:**<br>
> Since this is a potentially destructive action, a confirmation would be shown
> before the tasks are removed.

### Switch to a different task list: `switchlist`
Switches to a different task list.<br>

Format: `switchlist FILENAME`

If the file does not exist, TaSc will assume that you want to create a new
task list, and will create an empty file for you automatically.

### Relocate the data storage location: `relocate`
Designates a new data storage location.<br>

Format: `relocate PATH`

### Viewing help : `help`
Format: `help`<br>
Opens a new window displaying this document.

> **Tip:**<br>
> If an invalid command (e.g `abcd`) or parameter is entered,
> help messages will also be shown in the output box of the
> program.

## Other Features
### Autocomplete and suggestions
Shows suggested command keywords, dates, sorting order, and tags as you type.

Use the up and down arrows to select a keyword in the list.<br>
Use the <kbd>tab</kbd> key to autocomplete with the highlighted keyword.

> <img src="images/Ui-Autocomplete.png" width="200">

### Saving the Data
TaSc saves automatically after each command that changes the data.
No manual saving is required.

### Multiple Storage Files
You can store different schedules on different storage files. Just pass the file name into the program arguments
when running the program.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Backup the contents of your current TaSc folder. Install the app in the other computer, and run it. It will create an empty data file. Overwrite the empty data file with the backup you made.

## Commands Cheat Sheet

Command | Format  
-------- | :--------
Add | `add NAME [by DEADLINE] [from START_TIME to END_TIME] [repeat PATTERN FREQUENCY] [tag "TAG"...]`
List | `list [TYPE...] [by DEADLINE] [from START_TIME] [to END_TIME] [tag "TAG"...] [sort SORTING_ORDER]`
Show | `show [TYPE...] [on DATE] [by DEADLINE] [from START_TIME] [to END_TIME]  [tag "TAG"...]`
Hide | `hide [TYPE...] [on DATE] [by DEADLINE] [from START_TIME] [to END_TIME] [tag "TAG"...]`
Find | `find KEYWORD...`
Update | `update INDEX [name NAME] [by DEADLINE] [from START_TIME to END_TIME] [repeat PATTERN FREQUENCY] [tag "TAG"...]`
Complete | `complete INDEX`
Delete | `delete INDEX`
Undo | `undo [last STEPS]`
Clear | `clear`
Switch List | `switchlist FILENAME`
Relocate | `relocate PATH`
Help | `help`
