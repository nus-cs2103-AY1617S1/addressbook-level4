# User Guide

At this point, we know you are just as excited as we are [about Jimi](https://github.com/CS2103AUG2016-T09-C2/main/blob/master/README.md). But before you start throwing your money at us (even though Jimi is entirely free), you should first learn **how to use Jimi properly**. What follows should guide you on how to setup, install and use Jimi easily. 

<br>

## Guide Map

* [Quick Start](#quick-start)
* 
* [Command Summary](#command-summary)
* [Features](#features)
* [FAQ](#faq)

<br>
<br>

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `jimi.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for Jimi.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
> <img src="images/WelcomeScreenUi.png" width="600">

4. Type the command in the command box below and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open this user guide in another window.
5. Some example commands you can try: 
   * **`add`**` "do laundry" `**`due`**` tomorrow` : 
     adds a task named `do laundry` due `tomorrow` to Jimi.
   * **`delete`**` t1` : deletes the 1st task shown in the current task list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>

<br>
<br>

## Commands Summary
Command | Format  
-------- | :-------- 
[Help](#help) | `help`
[Add](#add) | `add "TASK_DETAILS" [t/TAG] [p/PRIORITY]`
&nbsp;| `add "TASK_DETAILS" due DATE_TIME [t/TAG][p/PRIORITY]`
&nbsp;| `add "EVENT_DETAILS" on START_DATE_TIME [to END_DATE_TIME] [t/TAG] [p/PRIORITY]`
[Complete](#com)| `complete INDEX`
[Delete](#del) | `delete INDEX`
[Edit](#edit) | `edit INDEX KEYWORDS NEW_DETAILS`
[Show](#show) | `show SECTION`
[Undo](#undo) | `undo`
[Redo](#redo) | `redo`
[Find](#find) | `find KEYWORD [MORE_KEYWORDS]`
[SaveAs](#saveas) | `saveas NEW_DIRECTORY`
[Clear](#clear) | `clear`
[Exit](#exit) | `exit`


> **Command Format**
> * Commands have to follow a certain format as shown in the table above.
> * Replace words in `UPPER_CASE` with your input.
> * Items in `[]` are optional.
> * The order of your input text is fixed. For instance, `add [DATE_TIME] due [TASK DETAILS]` is invalid.

> **Input of Date and Time in Commands**
> * The input of date and time is flexible.
> * Eg:
>    * Tomorrow 2pm
>    * Next Monday
>     * 7/11/2016
> * You can either input date, time or both.
>     * If no time is given, the current time will be used instead.
>     * If no date is given, the current date will be used instead.
> * However, you cannot input none of them.
> * The start-date & time of the events cannot be earlier prior to the end-date & time.

> **Input of Index**
> * In order to differentiate the indexes of the tasks and events in the command inputs:
>     * The index of tasks should be preceded by the letter 't'.
>     * The index of events should be preceded by the letter 'e'.
>     * Eg:
>         * complete **t1**
>         * delete **e3**

<br>
<br>

## Features


#### <a id="help"></a> Viewing help : `help`
Format: `help`

> The user guide will open in another window.

<img src="images/Help.png" width="600">

<br><br>

#### <a id="add"></a> Adding a task: `add`
> Remember to put your `TASK_DETAILS` or `EVENT_DETAILS` in quotation marks!

<br>


Adding a floating task to Jimi.<br>
Format: `add "TASK_DETAILS" [t/tag] [p/priority]` 

> * Floating tasks are tasks without any deadlines.

Examples: 
* `add "Buy groceries" t/NTUC`
* `add "Visit parents" p/HIGH`
<img src="images/AddFloatingTasks.png" width="600">

<br>
<br>

Adds a task with a deadline to Jimi.<br>
Format: `add "TASK_DETAILS" due DATE_TIME [t/tag] [p/priority]`

Examples:
* `add "Get a haircut" due Tuesday p/LOW`
* `add "Pick up Jimmy" due Monday 2pm t/tuition`
<img src="images/AddDeadlineTasks.png" width="600">
<br>

Adds an event to Jimi.<br>
Format: `add "EVENT_DETAILS" on START_DATE_TIME [to END_DATE_TIME] [t/tag] [p/priority]` 

> * If the event is more than a day long, you may include the end date_time. <br>
> * You may define the start and end time of the event if you wish.

Examples:
* `add "Attend Timmy's orchestra" on 5th July t/Timmy`
* `add "Show up for dentist appointment" on 8-7-2016 5:00pm to 7:30pm p/MED`
* `add "Have school camp" on 10 October 10am to 18 October 5pm`
<img src="images/AddEvents.png" width="600">
<br><br>

#### <a id="com"></a>Marking a task as complete: `complete`
Marks an existing task as complete. <br>
Format: `complete tTASK_INDEX`

> * Jimi will mark the task as completed at the specified `TASK_INDEX`. 
> * If you want to revert the task back as incomplete, use the [`undo`](#undo) command.

Example:
* `complete t1`
<img src="images/Complete.png" width="600">

<br><br>

#### <a id="del"></a>Deleting a task/event: `delete`
Deletes the specified task/ event from Jimi.<br>
Format: `delete INDEX`

> * Jimi will delete the task specified by `INDEX`. 
> * If you need to recover your deleted task/event, use the [`undo`](#undo) command.

Examples: 
* `delete e2`
  Deletes the 2nd event in Jimi.
* `delete t1`
  Deletes the 1st task in the Jimi.
  <img src="images/Delete.png" width="600">


<br><br>

#### <a id="edit"></a>Editing a detail: `edit`
Edits the specified detail of any task or event. <br>
Format: `edit INDEX KEYWORDS NEW_DETAILS` 

Jimi edits the task/event specified by `TASK_INDEX`.
`NEW_DETAILS` are simply the edits you want to make. 
`KEYWORDS` are the keywords used when you add a task.
The keywords are as such: 
 *  `"TASK_DETAILS/EVENT_DETAILS"` : Edit task or event details
 * `due DATE_TIME` : Edit deadline of deadline tasks
 * `on START_DATE_TIME`: Edit start date-time of event 
 * `to END_DATE_TIME`: Edit end date-time of event <br>

> If you want to undo your edit, use the [`undo`](#undo) command.
> If you want to remove all dates and times from a particular task/event, use `edit float`


Examples:
* `edit e2 "Have orientation camp"`
* `edit e1 on 5th July 7pm`
<img src="images/Edit.png" width="600">
<br><br>

#### <a id="show"></a>Showing section: `show`
Shows certain sections of the task panel <br>
Format: `show SECTION`

> * The sections are case-sensitive, with the first letter of the word being capitalised.
> * For the sections with two words, you can input just the first word of the two. 

Examples:
* `show Monday`
* `show Completed`
<img src="images/Complete.png" width="600">
<br><br>

#### <a id="undo"></a>Undoing previous action: `undo`
Undos the previous action done in Jimi. <br>
Format: `undo`

<br><br>


#### <a id="redo"></a>Redoing previously undone action: `redo`
Redoes the previously undone action done in the task manager. <br>
Format: `redo`

<br><br>

#### <a id="find"></a>Finding all tasks relevant to keywords you input: `find`
Finds and lists all tasks in Jimi whose name contains any of the argument keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The order of the keywords you type in does not matter. e.g. `Essay writing` will match `Writing essay`
> * Only the task details are searched.
> * Only full words will be matched e.g. `Essay` will not match `Essays`
> * Tasks with details matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Writing` will match `Writing essay`

Examples: 
* `find Jimmy`
* `find Haircut`
<img src="images/Find.png" width="600">
<br><br>

#### <a id="saveas"></a>Setting save directory : `saveas`
Sets new save directory for the tasks and events in Jimi.
Format: `saveas NEW_DIRECTORY`

> * `NEW_DIRECTORY` should be in the format: `[desired_path]/[file_name].xml`
> * If you want to reset the save directory back to default, use `saveas reset`

Example:
* `saveas Jimi_tasks.xml`
<img src="images/SaveAs.png" width="600">
<br><br>

#### <a id="clear"></a>Clearing all entries : `clear`
Clears all entries from Jimi.<br>
Format: `clear`  
> If you want to undo your clear, use the [`undo`](#undo) command.
> 
<img src="images/Clear.png" width="600">
<br><br>

#### <a id="exit"></a>Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

> Before exiting the program, ensure you have no unwanted actions that need to be reverted. You can only undo actions done in the current session.

<br><br>

#### <a id="save"></a>Saving the data 
Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

<br><br>

## FAQ

**Q**: How do I transfer my data to another Computer?
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Address Book folder.
<br>
**Q**: Is there a way to be notified of upcoming tasks or events that are due soon?
**A**: Jimi will display all overdue tasks, upcoming tasks and events at the top Agenda box, so you will always be notified of the most important details first.
<br>
**Q**: What happens if I typed in a wrong command?
**A**: An unknown command message will be shown to you.
<br>
**Q**: What happens if I typed in the format wrongly?
**A**: An invalid command format message will be shown to you, along with the correct format you should use instead.



