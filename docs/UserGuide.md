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
<br><br>

## Introduction

Organize your tasks with just a *single* line of command.

Need to add, delete or update a task? *One line* is all that it needs.<br>
Want to list, search or filter your tasks? *One line* is all that it takes.

Many of us lead busy lives, with never ending streams of tasks often weighing on our minds. We understand it all too well, and we want to lessen that burden for you.

This is the motivation behind TaSc, our Task Scheduler with keyboard usability at its core. TaSc is quick, simple, and contains all the functionalities you need to plan and record your tasks.

Just type in your command, and hit <kbd>Enter</kbd>. Let us handle the rest - you have more important things to do.
<br><br>

## Quick Start

### Installing
1. Ensure that you have Java version `1.8.0_60` or later installed on your computer.<br>
> **Note:**<br>
> Having any Java 8 version is not enough. <br>
> This app will not work with earlier versions of Java 8.

2. Download the latest `TaSc.jar` from the [releases](../../../releases) tab (on our GitHub project website) as shown in *figure 1* below.

   <img src="images/github-download-release.png" width="600"><br>
   *Figure 1: Download TaSc.jar from GitHub*

3. Copy `TaSc.jar` to the folder you would like to use as the home folder for your TaSc application.
<br><br>

### Launching

Double-click on `TaSc.jar` to start the application. The application window *(figure 2)* should appear within a few seconds.

<img src="images/Ui.png" width="600"><br>
*Figure 2: TaSc application window*
<br><br>

### Using the Interface

Type your command in the command box and press <kbd>Enter</kbd> to execute it.<br> 

You can try some of these example commands:
   * **`list`** : lists all uncompleted tasks and upcoming events
   * **`add`**` "Do Research" by 21 Sep 5pm` :
     adds a new task named "Do Research" with the deadline on 21 September, 5pm
   * **`complete`**` 1` : marks the 1st task shown in the current task list as complete
   * **`exit`** : exits the application

> **Tip:**<br>
> You may refer to the [Features](#features) section for details of each command.

<br>

## Features

### Adding a new task/event: `add`

You can easily add a new task by giving it a name using the `add` command:

`add "A new task"`

> **Tip:**<br>
> Commands are not case-sensitive.

<br>
If your task is due on a certain date, you may provide a deadline by adding the `by` keyword:

`add "CS2101 homework" by 21 Oct 5pm`

> **Tip:**<br>
> Acceptable date formats: 18 Sep, 18 September, 18 Sep 2016, Sep 18 2016,
  Today, Monday<br>
> Acceptable time formats: 5pm, 5:01pm, 5:01:59pm, 17:00

<br>
Events can also be added easily by providing the period that it will happen
with the `from` and `to` keywords:

`add "CS2101 Meeting" from 24 Oct 3pm to 5pm`

<br>
Some events happen repeatly (for example, lectures are conducted every week).
You can specify a recurring task/event by using the `repeat` keywords:

`add "GET1006 Lecture" from Monday 8am to 10am repeat weekly 18`

> **Note:** <br>
> `repeat weekly 18` means that the lecture will take place for 18 weeks.<br>
> Acceptable recurrence patterns: daily, weekly, monthly

<br>
Finally, tags can be added to tasks for you to categorize them. For example,
you may choose to use tags as a way to prioritize tasks.

`add "CS2101 Submission" by 5 Nov tag "Very Important"`

<img src="images/Ui-Add.png" width="600"><br>
*Figure 3: The `add` command allows you to add a new task easily*
<br><br>

### Listing all tasks/events with specified conditions: `list`

You may have a lot of tasks in your task list after using TaSc for a
certain amount of time. The `list` command allows you to filter your tasks in the list.

First of all, if you just want to see a list of uncompleted tasks and upcoming
events, just type:

`list`

> **Note:**<br>
> For your convenience, this command automatically sorts the tasks by their dates `earliest first`.

<br>
You may want to view tasks that happen during certain periods:

`list from 18 Sep 1pm to 6pm`

<br>
You may also want to view tasks that need to be done by a certain time
and require your attention:

`list by 20 Sep tag "Submissions"`

<br>
What about tasks with no deadlines and periods? They are known as
*floating* tasks, and you can list them using:

`list floating`

> **Tip:**<br>
> You can even combine these types:
> `all`, `uncompleted`, `completed`, `period`, `deadline`, `overdue`, `floating`, `recurring`, `events`, `tasks`, and `free time`.

<br>
Finally, if you need to sort your tasks, enter the following:

`list sort earliest first`

> **Tip:**<br>
> Sorting order includes `earliest first`, `latest first` for date and time,
  and `a-z`, `z-a` for task descriptions.

<br>
<img src="images/Ui-List.png" width="600"><br>
*Figure 4a: Filter your tasks by deadlines.*
<br><br>

<img src="images/Ui-List2.png" width="600"><br>
*Figure 4b: The `list` command allows you to filter your tasks easily.*
<br><br>

### Narrowing listing results with specified type, date or tags: `show`

Already typed your list command, only to find out that you have more filters
to add? Don't retype your `list` command, simply use the `show` command
to further narrow your task list results.

For example, you may want to list out the uncompleted tasks, so you typed this:

`list uncompleted tasks`

However, you realise that only want to see those for your module CS2103. Instead of typing
the entire `list` command again (`list uncompleted tasks tag "CS2103"`),
enter this instead:

`show tag "CS2103"`

> **Tip:**<br>
> Because this is an extension of the `list` command, any parameters that is
> accepted by the `list` command is also accepted by the `show` command.

<br>

### Hiding listing results with specified type, date or tags: `hide`

Similar to `show`, you may want to `hide` some tasks instead.

To list every uncompleted tasks **except** for those tagged as "CS2103", enter these:

`list uncompleted tasks`<br>
`hide tag "CS2103"`
<br><br>

### Finding tasks/events which match keywords: `find`

You don't have to remember every details of the tasks you added, just use the `find`
command which returns the list of tasks which partially match by their names, dates, types or tags.

To show tasks with names such as "Up**grad**e myself", or tags such as
"**Grad**ed":

`find grad`

To show your task named "**V0.0 Deliverables**" in **Sep**tember:

`find "V0.0 Deliverables" sep`

> **Note:**<br>
> Words enclosed in quotation marks `" "` use exact match and are case-sensitive.<br>
> The task list results are shown in an order which prioritizes the closest match, followed by completion status and date.

<br>

### Changing the details of a task/event: `update`

You have a list of tasks, and you realised that the deadline for 1st task in the list was entered incorrectly (it should have been 20 Sep). You can do an update by using:

`update 1 by 20 Sep`

> **Note:**<br>
> The number used is relative to the position of the task in the list.

<br>
Any other details of the tasks that you have added can be updated easily (see
the `add` command section to see the details that tasks can have). For example,
we can change the name of the 3rd task:

`update 3 name "New Task Name"`

Or change the tags of the 4th task:

`update 4 removetag "Important" tag "Low Priority"`

Or if the deadline is no longer valid, remove it by adding `remove` before the keyword `by`:

`update 5 removeby`

> **Tip:**<br>
> This works for any other keywords you may have used in your `add` command,
> like `removefrom`, `removeto`, `removerepeat`, etc.

<br>

### Marking a task as complete: `complete`

Once you have completed a task, you can mark it as complete.

`complete 3`

<img src="images/Ui-Complete.png" width="600"><br>
*Figure 5: Marking a task as complete*
<br><br>

###  Undoing the last action: `undo`

Mistakes in TaSc have very little consequence. You can easily undo any previous action which
modified the task list (for example, deleting a task):

`undo`

<br>
You can undo the last *X* number of actions. For example, to undo the
last 5 actions taken:

`undo last 5`

<br>

### Deleting a task/event: `delete`

Sometimes, instead of marking it as `complete`, you may want to clean up
your task list to save disk space on your computer.

`delete 3`

> **Caution:**<br>
> Tasks deleted cannot be recovered **after** you exit the application. If you
> wish to keep the details of the task, use `complete` instead.

<br>

### Clearing all data: `clear`

Same as `delete`, but deletes the entire list.

`clear`

> **Caution:**<br>
> Tasks deleted cannot be recovered **after** you exit the application. If you
> wish to keep the details of the task, use `complete` instead.

<br>

### Switching to a different task list: `switchlist`

You should keep different schedules on separate lists (for example,
one list `work.xml` for your tasks in your daily job, and another list `life.xml`
for your activities outside your work).

To do so, simply type the switch list command:

`switchlist life`

> **Tip:**<br>
> If the file does not exist, TaSc will assume that you want to create a new
> task list, and will create an empty file for you automatically.

<br>

### Renaming the task list file: `renamelist`

You may wish to rename your task list. For example, if your list is
currently named `life.xml` and you would like to rename it to `family.xml`, enter:

`renamelist family`

<br>

### Relocating the data storage location: `relocate`

For convenience, you may want to move the entire folder, where all your task lists
are stored, to another location. For example, you want to move your task list into 
your Dropbox folder so that you can access it on another computer. Presuming your Dropbox
folder is at `/dropbox/`, you may do so by typing:

`relocate /dropbox/tasklist/`

<br>

### Viewing help: `help`

There are so many commands in TaSc, but you don't have to memorise them. 
Just open this document anytime again by typing the `help` command:

`help`

> **Tip:**<br>
> If an invalid command (e.g `abcd`) or parameter is entered,
> help messages will also be shown in the output box of the
> program.

<br>

## Other Features
### Calendar
TaSc provides a calendar view for you to visualise your tasks.

//TODO image of the calendar UI
<br><br>

### Autocomplete and suggestions
Shows suggested command keywords, dates, sorting order, and tags as you type.

Use the <kbd>up</kbd> and <kbd>down</kbd> arrow keys to select a keyword in the list,<br>
and the <kbd>tab</kbd> key to autocomplete with the highlighted keyword.

><img src="images/Ui-Autocomplete.png" width="200">

<br>

### Saving the Data
TaSc saves automatically after every command that changes the data.
Don't worry about your data getting lost.

<br>

### Multiple Storage Files
You can store different schedules on different storage files. Just pass the file name into the program arguments
when running the program.

<br>

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Backup the contents of your current TaSc folder. Install and run `TaSc.jar` on the other computer, 
and a new data file will be created. Overwrite the new data file with the backup you made.

<br>

## Commands Cheat Sheet

> Parameters in `[ ]` are optional.<br>

Command | Format  
-------- | :--------
Add | `add NAME [by DEADLINE] [from START_TIME to END_TIME] [repeat PATTERN FREQUENCY] [tag TAG...]`
List | `list [TYPE...] [by DEADLINE] [from START_TIME] [to END_TIME] [tag TAG...] [sort SORTING_ORDER]`
Show | `show [TYPE...] [on DATE] [by DEADLINE] [from START_TIME] [to END_TIME]  [tag TAG...]`
Hide | `hide [TYPE...] [on DATE] [by DEADLINE] [from START_TIME] [to END_TIME] [tag TAG...]`
Find | `find KEYWORD...`
Update | `update INDEX [name NAME] [by DEADLINE] [from START_TIME to END_TIME] [repeat PATTERN FREQUENCY] [tag TAG...]`
Complete | `complete INDEX`
Delete | `delete INDEX`
Undo | `undo [last STEPS]`
Clear | `clear`
Switch List | `switchlist FILENAME`
Rename List | `renamelist FILENAME`
Relocate | `relocate PATH`
Help | `help`
