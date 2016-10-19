# User Guide

At this point, we know you are just as excited as we are [about Jimi](https://github.com/CS2103AUG2016-T09-C2/main/blob/master/README.md). But before you start throwing your money at us (even though Jimi is entirely free), you should first learn **how to use Jimi properly**. What follows should guide you on how to setup, install and use Jimi easily. 

<br>

## Guide Map

* [Quick Start](#quick-start)
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
> <img src="images/Ui.png" width="600">

4. Type the command in the command box below and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try: 
   * **`list`** : lists all contacts
   * **`add`**` "do laundry" `**`by`**` tomorrow` : 
     adds a task named `do laundry` due `tomorrow` to Jimi.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>

<br>
<br>

## Command Summary


> **Command Format**
> * Replace words in `UPPER_CASE` with your input.
> * Items in `[]` are optional.
> * The order of your input text is fixed. For instance, `add [DATE/TIME] by [TASK DETAILS]` is invalid.


Command | Format  
-------- | :-------- 
[Help](#help) | `help`
[Add](#add) | `add "TASK_DETAILS" [t/TAG]`
&nbsp;| `add "TASK_DETAILS" by DATE/TIME [t/TAG]`
&nbsp;| `add "EVENT_DETAILS" on START_DATE/TIME [to END_DATE/TIME] [t/TAG]`
[Complete](#com)| `complete TASK_INDEX`
[Delete](#del) | `delete TASK_INDEX`
[Edit](#edit) | `edit INDEX DETAIL_MODIFIER EDITS`
[Undo](#undo) | `undo`
[Find](#find) | `find KEYWORD [MORE_KEYWORDS]`
[Setdir](#set) | `setdir NEW_DIRECTORY`
[Clear](#clear) | `clear`
[Exit](#exit) | `exit`

<br>
<br>

## Features


#### <a id="help"></a> Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

<br><br>

#### <a id="add"></a> Adding a task: `add`
Adds a floating task to Jimi.<br>
Format: `add "TASK_DETAILS"` 

> * Floating tasks are tasks without any deadlines.
> * Remember to put your `TASK_DETAILS` in quotation marks!

Examples: 
* `add "Buy groceries"`

<br>
Adds a task with a deadline to Jimi.<br>
Format: `add "TASK_DETAILS" by DATE/TIME`

> * You can include time as an optional detail.
> * Remember to put your `TASK_DETAILS` in quotation marks!


Examples:
* `add "Get a haircut" by Tuesday`
* `add "Pick up Jimmy" by Monday at 2pm`

<br>
Adds an event to Jimi.<br>
Format: `add "EVENT_DETAILS" on START_DATE/TIME [to END_DATE/TIME]`

> * If the event is more than a day long, you may include the end date/time. <br>
> * You may define the start and end time of the event if you wish.
> * Remember to put your `EVENT_DETAILS` in quotation marks!

Examples:
* `add "Attend Timmy's" orchestra on 5th July`
* `add "Show up for dentist appointment" on 8/7/2016, 5:00pm to 7:30pm`
* `add "Have school camp" on 10 October to 18 October, 10am to 5pm`

<br><br>

#### <a id="com"></a>Complete a task: `complete`
Mark a task as completed. 
Format: `complete TASK_INDEX`

> * Jimi will note the task as completed at the specified `TASK_INDEX`. 
> * If you want to revert the task back as incomplete, use the [`undo`](#undo) command.
> * The index refers to the index number shown in the most recent listing.<br>
> * The index **must be a positive integer** e.g. 1, 2, 3.

<br><br>

#### <a id="del"></a>Deleting a task: `delete`
Deletes the specified task/ event from Jimi.<br>
Format: `delete TASK_INDEX`

> * Jimi will delete the task specified by `TASK_INDEX`. 
> * If you need to recover your deleted task, use the [`undo`](#undo) command.

Examples: 
* `delete 2`<br>
  Deletes the 2nd task in the task manager.
* `find Essay`<br> 
  `delete 1`<br>
  Deletes the 1st task in the results of the [`find`](#find) command.

> * The index refers to the index number shown in the most recent listing.<br>
> * The index **must be a positive integer** 1, 2, 3.

<br><br>

#### <a id="edit"></a>Editing a detail: `edit`
Edits the specified detail from the list of tasks. 
Format: `edit TASK_INDEX DETAIL_MODIFIER EDITS` 

Jimi edits the task specified by `TASK_INDEX`.
`EDITS` are simply the edits you want to make. 
`DETAIL_MODIFIER` is used to indicate which detail you want to edit. You may choose one of the following modifiers: 
* `/n` : name of task
* `/sd` and `/ed`: start date-time and end date-time <br>
<br>

> * If you want to undo your edit, use the [`undo`](#undo) command.
> * The index refers to the index number shown in the most recent listing.
> * The index **must be a positive integer** 1, 2, 3.

Examples:
* `edit 2 /n buy food`
* `edit 3 /sd mon`

<br><br>

#### <a id="undo"></a>Undo previous action: `undo`
Undos the previous action done in the task manager. <br>
Format: `undo`

<br><br>

#### <a id="find"></a>Find all tasks relevant to keywords you input: `find`
Finds tasks which details contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The order of the keywords you type in does not matter. e.g. `Essay writing` will match `Writing essay`
> * Only the task details are searched.
> * Only full words will be matched e.g. `Essay` will not match `Essays`
> * Tasks with details matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Writing` will match `Writing essay`

Examples: 
* `find Remember`
* `find Buy Get Do`

<br><br>

#### <a id="set"></a>Setting save directory : `saveas`
Set a new save path for all your tasks.
Format: `saveas NEW_DIRECTORY`

> * `NEW_DIRECTORY` should be in the format: `[desired_path]/[file_name].xml`

Example:
* `saveas C:\Users\jimmy\Desktop\my_tasks.xml`

<br><br>

#### <a id="clear"></a>Clearing all entries : `clear`
Clears all entries from the task manager.<br>
Format: `clear`  

> * You will receive a confirmation notice before Jimi clears its database.
> * This action is irreversible.

<br><br>

#### <a id="exit"></a>Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

> Before exiting the program, ensure you have no unwanted actions that need to be reverted. Exiting the program will not save the actions you have done and the `undo` command will not be able to retrieve back your changes.

<br><br>

#### <a id="save"></a>Saving the data 
Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

<br><br>

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Address Book folder.

**Q**: Is there a way to be notified of upcoming tasks or events that are due soon?<br>
**A**: Jimi will display all overdue tasks, upcoming tasks and events at the top Agenda box, so you will always be notified of the most important details first.
       



