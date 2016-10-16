# User Guide

## Table of Contents

- [Introduction](#introduction)
- [Quick Start](#quick-start)
	- [Installing](#installing)
	- [Launching](#launching)
	- [Using the Interface](#using-the-interface)
- [Features](#features)
	- [Adding a new task/event: `add`](#adding-a-new-taskevent-add)
	- [Listing all tasks/events with specified conditions: `list`](#listing-all-tasksevents-with-specified-conditions-list)
	- [Narrowing listing results with specified type, date or tags: `show`](#narrowing-listing-results-with-specified-type-date-or-tags-show)
	- [Hiding listing results with specified type, date or tags: `hide`](#hiding-listing-results-with-specified-type-date-or-tags-hide)
	- [Finding tasks/events which match keywords: `find`](#finding-tasksevents-which-match-keywords-find)
	- [Changing the details of a task/event: `update`](#changing-the-details-of-a-taskevent-update)
	- [Marking a task as complete: `complete`](#marking-a-task-as-complete-complete)
	- [Undoing the last action: `undo`](#undoing-the-last-action-undo)
	- [Deleting a task/event: `delete`](#deleting-a-taskevent-delete)
	- [Clearing all data: `clear`](#clearing-all-data-clear)
	- [Switching to a different task list: `switchlist`](#switching-to-a-different-task-list-switchlist)
	- [Renaming the task list file: `renamelist`](#renaming-the-task-list-file-renamelist)
	- [Relocating the data storage location: `relocate`](#relocating-the-data-storage-location-relocate)
	- [Viewing help : `help`](#viewing-help-help)
- [Other Features](#other-features)
	- [Calendar](#calendar)
	- [Autocomplete and suggestions](#autocomplete-and-suggestions)
	- [Saving the Data](#saving-the-data)
	- [Multiple Storage Files](#multiple-storage-files)
- [FAQ](#faq)
- [Commands Cheat Sheet](#commands-cheat-sheet)
  
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

   <img src="images/github-download-release.png" width="600"><br>
   *Figure 1: Download TaSc.jar from GitHub*

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

### Adding a new task/event: `add`

You can easily add a new task by giving a name for it when using the `add`
command:

`add "A new task"`

> **Tip:**<br>
> Commands are not case-sensitive.

If your task is due by a certain date, you may provide a deadline using the `by`
keyword:

`add "CS2101 homework" by 21 Oct 5pm`

> **Tip:**<br>
> Accepted date formats: 18 Sep, 18 September, 18 Sep 2016, Sep 18 2016,
  Today, Monday<br>
> Accepted time formats: 5pm, 5:01pm, 5:01:59pm, 17:00

Events can also be added easily by providing the period that it will happen
with the `from` and `to` keywords:

`add "CS2101 Meeting" from 24 Oct 3pm to 5pm`

Some events happen repeatly (for example, lectures are conducted every week).
You can specify a recurring task/event by using the `repeat` keywords:

`add "GET1006 Lecture" from Monday 8am to 10am repeat weekly 18`

> **Note:** <br>
> `weekly 18` means that the lecture will happen for 18 weeks.

<br>

> **Tip:**<br>
> Accepted recurrence patterns: daily, weekly, monthly

Finally, tags can be added to tasks for you to categorize them. For example,
you may choose to use tags as a way to prioritize tasks.

`add "CS2101 Submission" by 5 Nov tag "Very Important"`

<img src="images/Ui-Add.png" width="600"><br>
*Figure 2: The `add` command allows you to add a new task easily*

### Listing all tasks/events with specified conditions: `list`

You may have a lot of tasks in your task list after using TaSc for a
certain amount of time. The `list` command allows you to filter your tasks.

First of all, if you just want to see a list of uncompleted tasks and upcoming
events, just type:

`list`

You may want to view tasks that happen on certain periods:

`list from 18 Sep 1pm to 6pm`

You may also want to view tasks that needs to be done by a certain time
which may need your attention:

`list by 20 Sep tag "Submissions"`

Don't have deadlines and periods for the tasks at all? Those are known as
*floating* tasks, and you can list them by:

`list floating`

> **Tip:**<br>
> Other possible types include:
> `all`, `uncompleted`, `completed`, `overdue`, `floating`, `recurring`, `events`, `tasks`, and/or `free time`

Finally, if you need to sort your tasks, do the following:

`list sort earliest first`

> **Tip:**<br>
> Sorting order includes `earliest first`, `latest first` for date and time,
  and `a-z`, `z-a` for task descriptions, or `none` to use the default order.<br>
> For your convenience, `earliest first` is automatically applied whenever you enter `list`.

<img src="images/Ui-List.png" width="600">

<img src="images/Ui-List2.png" width="600"><br>

*Figure 3: The `list` command allows you to filter your tasks easily.*

### Narrowing listing results with specified type, date or tags: `show`

Already typed your list command, only to find out that you have more filters
to add? Don't retype your `list` command, simply use the `show` command
to further narrow your results.

For example, you may want to list out the uncompleted tasks, so you typed this:

`list uncompleted tasks`

However, you realise that only want to see those for CS2103. Instead of retyping
the whole `list` command (`list uncompleted tasks tag "CS2103"`),
do this instead:

`show tag "CS2103"`

> **Tip:**<br>
> Because this is an extension of the `list` command, any parameters that is
> accepted by the `list` command is also accepted by the `show` command.

### Hiding listing results with specified type, date or tags: `hide`

Same as `show`, but instead `hide` those that you specified.

So to list every uncompleted tasks **except** for CS2103, do this:

`list uncompleted tasks`<br>
`hide tag "CS2103"`

### Finding tasks/events which match keywords: `find`

If you cannot remember the exact name of the task, you may use the `find`
command to partially match names, dates, types or tags.

To show tasks with names such as "Up**grad**e myself", and tags such as
"**Grad**ed":

`find grad`

To show the task named "**V0.0 Deliverables**" in **Sep**tember:

`find "V0.0 Deliverables" sep`

> **Note:**<br>
> Words enclosed in quotation marks `" "` use exact match.
  Words not enclosed in `" "` are not case-sensitive.

<br>

> **Tip:**<br>
> The task list results are shown in an order which prioritizes the closest match, followed by completion status and date.

### Changing the details of a task/event: `update`

You have a list of tasks, but you realise that the 1st task in the list has
the wrong deadline (it should have been 20 Sep). You can do an update by using:

`update 1 by 20 Sep`

> **Note:**<br>
> The number used is relative to the position of the task in the list.

Any other details of the tasks that you have added can be updated easily (see
the `add` command section to see the details that tasks can have). For example,
we can change the name of the 3rd task:

`update 3 name "New Task Name"`

Or change the tags of the 4th task:

`update 4 removetag "Important" tag "Low Priority"`

Or if the deadline is no longer valid, remove it by adding `remove` in front
of the keyword `by`:

`update 5 removeby`

> **Tip:**<br>
> This works for any other keywords you may have used in your `add` command,
> like `removefrom`, `removeto`, `removerepeat`, etc.

### Marking a task as complete: `complete`

When you are done with the task, you can mark it as complete.

`complete 3`

<img src="images/Ui-Complete.png" width="600"><br>
*Figure 5: Marking a task as complete*

###  Undoing the last action: `undo`

If you make any mistakes, you may undo any previous action that
modified the task list (for example, deleting task)

`undo`

You can undo the last X amount of actions. For example, to undo the
last 5 actions taken:

`undo last 5`

### Deleting a task/event: `delete`

Sometimes, instead of marking it as `complete`, you may want to clean up
your task list to save disk space.

`delete 3`

> **Caution:**<br>
> Tasks deleted will not be undo-able **after** you close the program. If you
> want the details of the task, it is advisable that you use `complete` instead.

### Clearing all data: `clear`

Same as `delete`, but does it for the entire list.

`clear`

> **Caution:**<br>
> Tasks deleted will not be undo-able **after** you close the program. If you
> want the details of the task, it is advisable that you use `complete` instead.

### Switching to a different task list: `switchlist`

It is advisable that you keep different tasks to different lists (for example,
one list `work.xml` for your tasks in your daily job, another list `life.xml`
for your tasks outside your job).

Simply use the switch list command:

`switchlist life`

> **Tip:**<br>
> If the file does not exist, TaSc will assume that you want to create a new
> task list, and will create an empty file for you automatically.

### Renaming the task list file: `renamelist`

You may use this to rename your task list. For example, if your list is
currently named `life.xml` and you want to rename it to `family.xml`, do:

`renamelist family`

### Relocating the data storage location: `relocate`

For convienence, you may want to move the entire folder, that all your task lists
are stored, into another location. For example, you may want to move it into Dropbox
so that you can access your task list on another computer. Presuming your Dropbox
folder is at `/dropbox/`, you may do so by typing:

`relocate /dropbox/tasklist/`

### Viewing help : `help`

Finally, if you forgot any of our commands and want to see the user guide again,
you may display this document by using the `help` command:

`help`

> **Tip:**<br>
> If an invalid command (e.g `abcd`) or parameter is entered,
> help messages will also be shown in the output box of the
> program.

## Other Features
### Calendar
TaSc provides a calendar view for you to visualise your tasks.

//TODO image of the calendar UI

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

> Parameters in [ ] are optional.

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
Rename List | `renamelist FILENAME`
Relocate | `relocate PATH`
Help | `help`
