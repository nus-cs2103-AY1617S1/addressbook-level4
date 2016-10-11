# User Guide

* [Quick Start](#quick-start)
	* [Quick Start Summary](#qss)
* [Features](#features)
	* [Data Models](#dm)
    * [Command Format](#cf)
    * [Parameter Keywords](#pk)
    * [Date Format](#DateFormat)
    * [Time Format](#TimeFormat)
    * [Viewing Help](#help)
    * [Add Floating Task](#addft)
    * [Add Deadline Task](#adddt)
    * [Add Event](#adde)
    * [list](#list)
    * [View Task index](#i)
    * [Delete](#del)
    * [Edit Floating Task](#editft)
    * [Edit Deadline Task](#editdt)
    * [Edit Event](#adde)
    * [Mark Finish](#fin)
    * [Show Empty Timeslots](#slot)
    * [Toggle Event views](#view)
    * [Search](#search)
    * [Undo](#undo)
    * [Redo](#redo)
    * [Clear](#clear)
    * [Exit](#exit)
* [Command Summary](#command-summary)

## Quick Start

1. Ensure you have Java version `1.8.0_60` or later installed in your computer.

  > Having any Java 8 version is not enough.
  > This app will not work with earlier versions of Java 8.

2. Download the latest `TaskTracker.jar` from the 'releases' tab.

3. Copy the file to the folder you want to use as the home folder for your task
   database.

4. Double-click the file to start the app. The GUI should appear in a few
   seconds.

5. Set up: enter your name and verify the current date and time.

6. You're good to go! Try adding your first Task. Press ';' to launch/hide the
   command bar. Press Enter to enter a command. For floating task, `add
   "taskname"`!

   ![alt text](./images/userguide/command_bar_add.png "command bar")

7. Add task with due date and time, example commands are:

	* `add "bake cookies" 31dec 3pm`

      bake cookies by 31 December of this year, 3pm. Undeclared year in date field will be taken as the current year.

	* `add "learn task-tracker" tdy 3pm`

      learn task tracker by today, 3pm.

   ![alt text](./images/userguide/task.png "")

8. Add event. An event is a task with a start time and end time, example
   commands are:

	* `add "CS2103t summer camp" 30aug 1pm 3jan2017 6pm`

      Event CS2103t summer camp starts on 30 Aug 2016, 1pm, and ends on 3
      January 2017,6pm.

	* `add "potato peeling" tdy 3pm to 7pm`

      Event potato peeling starts today 3pm and ends today 7pm.

	* `add "cupcake festival" tdy 8pm tmr 12pm`

      Event cupcake festival starts on 8am and ends on 12pm of 24 Oct of this
      year.

	* `add "Trick or treat" 31oct 8pm to 9pm`

      Event Trick or treat starts today 8pm and ends on 31 Oct 9pm.

   The events for current selected day will show up under schedule.

   ![alt text](./images/userguide/toggle_calender.png "")

9. To see the schedule of a particular day, enter `view DATE`.
   The schedule for the day will show up in the schedule column.

	`view 10oct2016`

   ![alt text](./images/userguide/event.png "")

   To view all events, enter `view events`

   ![alt text](./images/userguide/event_list.png "")

10. Let's view the index of task. Enter `i`.

    ![alttext](./images/userguide/index.png "")

    Enter anything into command line to hide index view.

11. To delete a task, try:

        del 5

    Delete Event Movie.

12. To edit a deadline, try:

	* `i` to view index
	* `edit 1 dd-29dec dt-2pm`

      The following properties of task are modified: due date, due time.  (`dd`
      refers to due date, `dt` refers to due time)

	* `edit 1 dt-3pm`

      Only due time of bake potato modified.

13. To edit an event, try:

	* `i` to view index
	* `edit 2 loc-NUS`

      Location of cupcake festival set to NUS.

	* `edit 2 st-3pm sd-12oct et-5pm ed-13oct loc-Yishun`

      All fields modified. (`st` : `starting time`, `sd` : `starting date`,
      `et` : `ending time`, `ed` : `ending date`)

    * `edit 2 st-3pm`

      Start time of cupcake festival modified.

14. To edit a floating task, try:

	* `i` to view index
	* `edit 6 p-1`

       Floating Task bake potato is given a priority of 1.

15. You've finished a task. Congrations! Let's mark a task as finshed.

    * `i` to view index
    * `fin 3`

    Dateline task learn task-tracker is marked as finished.

16. To exit the program, try:

	* `exit`

      Close the Task-tracker.

17. Refer to the [Features](#features) section below for details of each
    command.

<a name="qss">**Quick Start Summary**</a>

| Command | Essential Parameters|
|---------|:----------|
|add| `"taskname"` <br> `"deadline task name"` `date` `time` <br> `"event name"` `start date` `start time` `end date` `end time`|
|i|  |
| edit | `floating task index` `n-` `p-` <br> `deadline task index` `n-` `dd-` `dt-` <br> `event index` `n-` `sd-` `st-` `ed-` `et` `loc-` <br>
| del | `index`|
| fin |`index`|
| view | `date` <br> `events` |
| exit |          |

## Features

<a name="dm">**Data models**</a>

The task stored in Task Tracker will be automatically grouped into three
different type of task: floating task, deadline task, and event, depending on
the type and number of fields entered when creating the task.

|A/An... | has...|
|----| :--------:|:------|
|Floating Task | only a task name|
|Deadline task |  end time and date |
|Event | start time and date,  end time and date|

All the time and date fields of the task have to be entered in order to create the task.

<a name="cf">**Command Format**</a>

* Words in `UPPER_CASE` are parameters to be defined by the user.

* Words in `lower_case` are the reserved keywords.

* Items in `[SQUARE_BRACKETS]` are optional parameters.

* Items in `<ANGLE_BRACKETS>` essential parameters. All essential parameters have to be included in the command.

* Items separated by `|` are in parallel relation, only one of them should be
  use in each command.

* Items with `...` after them can have multiple instances.

* The parameters have to follow the order in which they are presented, unless otherwise specified.

<a name="pk">**parameter keywords**</a>

|Keyword | Definition|
|----| :--------:|
|`tdy` | today|
|`tmr` | tommorow|
|`yst` | yesteday|
| `to` | to |
|`jan` | January|
|`feb` | February|
|`mar` | March|
|`apr` | April|
|`may` | May|
|`jun` | June|
|`jul` | July|
|`aug` | August|
|`sep`| September|
|`oct` | October|
|`nov` | November|
|`dec` | December|

<a name="DateFormat">**Date Format**</a>

Valid input examples:

ddmonthyy

	31dec2016

ddmonth

	31dec

* If the year is not defined, the year is assumed to be the current year. The
  year this document was last updated was 2016, therefore all examples
  protraying the current year will read 2016.

dd

	31

* If the month is not defined, the month is assumed to be the current month.

Today

	tdy

* Means today, the current date as logged by the local machine.

Tommorow

	tmr

* Means tommorow, the day after today as logged by the local machine.

To

    to

*  This keyword is only applicable for adding an event. `to` can only be
   entered into an `<END_DATE>` field. `to` would mean the `<END_DATE>` is the
   same as the `<START_DATE>` for an event. See [example](#to).

**<a name="TimeFormat">Time Format</a>**

The 12 hour clock is used. hh:mm am/pm

Valid input examples:

    830am

8:30am in the morning

	1145pm

11:45pm at night

	8pm

8:00pm at night

If the minute field is 00, it may be ommitted from the command.

### <a name="help">Viewing help : `help`</a>

    help

Help is also shown if you enter an incorrect command e.g. `abcd`

![alt text](./images/userguide/help.png "")

### <a name="addft">Adding a floating task : `add`</a>

Adds a floating task to TaskTracker.

    add <"FLOATING_TASK_NAME"> [PRIORITY]

* Task name should be in a pair of quotation marks. And quotations marks are
not allowed in task name.

* Task name could be a single word or a phrase, white space are allowed.

* The floating task will be shown according to their `PRIORITY`.

* Two floating tasks with the same `PRIORITY` will be shown according to
  the order of the time they are created.

* The `PRIORITY` attribute is an integer which ranges from `0` to `5`, with 0
  being the highest pririoty and 5 the lowest.

* The default priority of a floating task is 0.

#### Examples

* `add "EE2020 lab report" 5`

  To create a task called `EE2020 lab report` with `PRIORITY` of 5.

* `add Progress Reflection`

  To create a task called `Progress Reflection` with default `PRIORITY` of 0.

### <a name="adddt"> Adding a deadline task: `add`</a>

Adds a deadline with specific due date and time to TaskTracker.

    add <"DEADLINE_NAME"> <DATE> <TIME> [PRIORITY]

* Deadline name should be in a pair of quotation marks. And quotations marks are not allowed in deadline name.

* keywords like tdy, tmr, yst, are allowed in the date field.

* Formats of `DATE` and `TIME` should follow those stated above in this user guide. See [Date Format](#DateFormat) and  [Time Format](#TimeFormat)

#### Examples

* `add "CS2103 V1.1" 16dec 2pm`

  To create a deadline task named `CS2103 V1.1` with dateline of 16th December 2016, 2pm.

* `add "spend pizza vouchers" 20nov2018 2pm`

  To create a deadline task named `spend pizza vouchers` with dateline of 20 November 2018, 6pm.

* `add "event proposal" tdy 6pm`

  To create a deadline named `event proposal` with due date today, 6 pm.

* `add "EE2024 homework 1" tmr 6am`

  To create a deadline named `EE2024 homework 1` tommorow, 6 am.

### <a name="adde"> Adding an event: `add`</a>

Adds an event with specific start date, start time and end date, end time to TaskTracker.

    add <“EVENT_NAME”> <START_DATE> <START_TIME> <END_DATE> <END_TIME> [loc-LOCATION]

* Event name should be within a pair of quotation marks. Quotations marks are
  not allowed in event name.

* The four parameters `START_DATE`, `START_TIME`, `END_DATE`,
  `END_TIME` are all required for adding an event.

* Formats of `START_DATE`, `START_TIME`, `END_DATE`,
  `END_TIME` should follow those stated above in this user guide. See [Date Format](#DateFormat) and [Time Format](#TimeFormat)


* `[LOCATION]` is a String which could contain any characters.

#### Examples

* <a name="to">`add "CS2103 week8 lecture" 7oct 2pm to 4pm`</a>

  To create an event `CS2103 week8 lecture` with starting date 7 October 2016, starting time 2pm, ending date 7 Oct 2016, ending time 4pm.

* `add "programming workshop" tdy 10am to 5pm loc-LT15`

  To create an event `programming workshop` that starts today, 10am, and last till 5pm, at LT15.

* `add "sports training camp" 1dec2016 7pm 10jan2017 1pm`

  To create an event `sports training camp` with starting date 1 December 2016, starting time 7pm, ending date 10 January 2017 and ending time 1pm.

###<a name="list">List tasks: `list`</a>

List certain type of task stored in the database

    list task|event|due

* `list task` will show all floating tasks according to the order of their priority.

* `list event` will show all events according to the order of their starting time.

* `list due` will show all deadline according to their due time and due date. (when sorting
  the deadline without due time, the sorting will use 11:59 pm of the due date as default)

* Every tasks (floating tasks / events / deadlines) will have a unique index to differentiate
  their uniqueness. And their index will also be listed besides them when `list` command is
  executed.

* The overdue/past/finished tasks will not be listed.

### <a name="i"> View task index: `i`</a>

UI will show the index of all floating task, deadline task and events.

	i

* The index is mainly used to refer to a specific task when editing it's properties.

* index is also used to refer to a specific task to be deleted.

* To switch out of index view, enter anything into the command bar.

### <a name="del"> Deleting a floating task/event/deadline: `del`</a>

Delete a useless floating task/event/deadline on TaskTracker.

    del TASK_UNIQUE_INDEX

* `TASK_UNIQUE_INDEX` will be different from each to each single task.

* A task's `TASK_UNIQUE_INDEX` will be the same as what is shown when `list` command is executed.
  The user can refer `list` command to look for the index of a certain task.

* `TASK_UNIQUE_INDEX` will never be changed once a index is assigned to a task when created.

#### Examples

* `i`

  List all the task stored in the database with their unique index number.

* `del 00123`

  Delete the task with the unique index of `00123`.

### Edit a floating task/deadline/event: `edit`

* Edit command can only edit the parameters of each commands but cannot change the type of task.
For example, `edit` cannot transform a floating task to a event task.

* To edit an task, key in the index of the event followed by the properties to be modified. Label the new properties with their respective field references.

* The [optional parameters] need not follow the order shown.

#### <a name="editft"> Edit a floating task: `edit` </a>

Edit a floating task to revise its name or priority.

|Field reference | Definition |
|:----------------:|:-----------:|
| n- | name |
| p- | priority |

    edit <TASK_UNIQUE_INDEX> [n-NEW_NAME | p-PRIORITY]...

* Quotation marks are not necessary for `NEW_NAME`.

* `PRIORITY` should only be the integer ranges from `0` to `5`.

#### Examples

* `edit 00124 p-0`

  Edit task with unique index of `00124`'s priority to 0.

* `edit 00124 n-buy stationary`

  Edit task with unique index of `00124`'s name to `"buy stationary"`.

* `edit 00125 n-"go to Nanyang Mart" p-1`

  Edit task with unique index of `00125`'s name to `"go to Nanyang Mart"` and priority to 1.

#### <a name="editdl">Edit a deadline : `edit`</a>

Edit a deadline to revise its name and due date/time.

|Field reference | Definition |
|:----------------:|:-----------:|
| n- | name |
| dd- | due date |
| dt- | due time |

    edit <TASK_UNIQUE_INDEX> [dd-DUE_DATE | dt-DUE_TIME | n-NEW_NAME]...

* Quotation marks are not necessary for `NEW_NAME`.

* `[dd-DUE_DATE | dt-DUE_TIME]` are of the same format when creating
  event-like task. Please refer `event` command for reference.

#### Examples

* `edit 00128 dt-5pm`

  Edit deadline with unique index of `00128`'s due time to 5 pm.

* `edit 00128 dd-23/11/2016`

  Edit deadline with unique index of `00128`'s due date to 2016 23th November.

#### <a name="edite">Edit an event : `edit`</a>

Edit an event to revise its name, starting/ending date/time and location.

|Field reference | Definition |
|----------------|:-----------|
| n- | name |
| sd- | start date |
| st- | start time |
| ed- | end date |
| et- | end time |
| loc-| location |

    edit <TASK_UNIQUE_INDEX> [n-NEW_NAME | sd-NEW_START_TIME | st-NEW_START_DATE | ed-NEW_END_DATE | et-NEW_END_TIME | n-NEW_NAME | loc-NEW_LOCATION]...

* Quotation marks are not necessary for `NEW_NAME`.

* `[sd-NEW_START_TIME | st-NEW_START_DATE | ed-NEW_END_DATE | et-NEW_END_TIME]`
  are of the same format when creating event-like task. Please refer `event` command for reference.

#### Examples

* `edit 00126 loc-LT6`

  Edit event with unique index of `00126`'s location to `LT6`.

* `edit 00126 st-4pm et-6pm`

  Edit event with unique index of `00126`'s starting time to 4pm and ending time to 6pm.

* `edit 00127 n-"proposal meeting" st-7pm`

  Edit event with unique index of `00127`'s starting time to 7pm and name to `proposal meeting`.

###<a name="fin"> Mark a floating task/deadline as done/finished: `fin`</a>

Mark a floating task/event/deadline as done on TaskTracker, the marked tasks
will be archived.

    fin <TASK_UNIQUE_INDEX>

* Events that have already passed it `DUE_TIME` will be marked as done
  automatically.

* Deadlines that have already passed it `DUE_TIME` will not be marked as done,
  but will be marked as "overdue" automatically.

#### Examples

* `fin 00123`

  Marked task `00123` as finished.

###<a name="slot"> Show empty time slots : `slot`</a>

Show all empty time slots in a given time period with a given duration.

    slot <STARTING_DATE> <STARTING_TIME> <ENDING_DATE> <ENDING_TIME> <h-HOUR> <m-MINUTE>

* At least one of `<STARTING_DATE> <STARTING_TIME>` is required.

* At least one of `<ENDING_DATE> <ENDING_TIME>` is required.

* At least one of `<h-HOUR> <m-MINUTE>` is required.

#### Examples

* `slot 1nov2016 3nov2016 h-4`

  The TaskTracker will generate all empty time slots that are equal or greater than 4 hours
  between 2016 1st November 0am to 3rd 11:59pm.

* `slot 5nov2016 2pm 11pm m-45`

  The TaskTracker will generate all empty time slots that are equal or greater than 45 minutes
  between 2016 5st November 2pm to 3rd 11:00pm.

* `slot 5nov2016 2pm m-45`

  The TaskTracker will generate all empty time slots that are equal or greater than 45 minutes
  between 2016 5st November 0am to 2pm.

###<a name="view"> Toggle event views: `view` </a>

###View all the events of a specific date in TaskTracker.

    view <DATE>

* `DATE` will follow thw format shown in [Date Format](#DateFormat)

* The deadlines and events will be listed according to the order of deadlines' due time and
  events' starting time.

* The `TASK_UNIQUE_INDEX` will also be shown after `view` command is executed.

### View all events

	view events

* All the events in the database will be displayed in a list

#### Examples

* `view 1/12`

  View all the tasks that are of the date of 1st December of the current year.

### <a name="search"> Search by keywords: `search`</a>

Search task that contains specific keywords.

    search <KEY_WORDS>

* The `KEY_WORDS` are CASE-SENSITIVE

* Quotations marks are not allowed in `KEY_WORDS`

* The results will be listed according to their `TASK_UNIQUE_INDEX`

#### Examples

* `search lecture`

  Search for all the tasks that contain keyword `lecture`, TaskTracker will
  generate a list for view.

* `search training SESSION`

  Search for all the tasks that contain keyword `training SESSION`, TaskTracker
  will generate a list for view.

###<a name="undo"> Undo an action : `undo`</a>

Undo the previous action that modifies data. Undo can be performed many times until the first action since the app was launched has been undone.

    undo

View the stack of actions that undo will perform: `undo stack`

    undo stack

### <a name="redo"> Redo an action : `redo`</a>

Redo the previous action that was undone by undo. The amount of consecutive redos doable is equal to the number of consecutive undos performed right before redo is entered.

    redo

View the stack of actions that redo will perform: `redo stack`

    redo stack

###<a name="clear"> Clearing all entries : `clear`</a>

Clears all entries from TaskTracker.

    clear

* The `TASK_UNIQUE_NUMBER` will be reset.

###<a name="exit"> Exiting the program : `exit`</a>

Exits the program.

    exit

## Command Summary

Command | Format
------------ | :--------
Add Floating Task | `add "FLOATING_TASK_NAME" [PRIORITY]`
Add Event |`add “EVENT_NAME” <STARTING_DATE> <STARTING_TIME> <ENDING_DATE> <ENDING_TIME> [loc-LOCATION]`
Add Deadline |`add “DEADLINE_NAME” <DATE> <TIME>`
List all tasks | `list task|event|due`
Delete a task | `del TASK_UNIQUE_INDEX`
Edit Floating Tasks | `edit TASK_UNIQUE_INDEX [n-NEW_NAME | p-PRIORITY]... `
Edit Event |`edit TASK_UNIQUE_INDEX [sd-NEW_START_DATE | st-NEW_START_TIME | ed-NEW_END_DATE | et-NEW_END_TIME | n-NEW_NAME | loc-NEW_LOCATION]... `
Edit Deadline |`edit TASK_UNIQUE_INDEX [dd-DUE_DATE | dt-DUE_TIME | n-NEW_NAME]... `
Generate recommended time slots | `slot <STARTING_DATE> <STARTING_TIME> <ENDING_DATE> <ENDING_TIME> <h-HOUR> <m-MINUTE>`
Mark a task as finished | `fin TASK_UNIQUE_INDEX`
View a date in calendar | `view DATE`
Search for keywords | `search KEY_WORDS`
Help | `help`
Undo | `undo`
Redo | `redo`
Clear | `clear`
Exit | `exit`
