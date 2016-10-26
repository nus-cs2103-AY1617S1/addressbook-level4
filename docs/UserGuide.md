# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)
<!-- @@author A0093896H -->
## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.

1. Download the latest `Do-Do Bird.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Do-Do Bird application.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
<<<<<<< Updated upstream

   > ![GUI](./images/UI.png)

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`see`**` tomorrow`:  see all tasks for tomorrow.
   * **`add`**` Meet with professor; CS1234; from 10/10/17 09:30; till 17:00;` :
     adds a task named `Meet with Professor` to the tasks list.
   * **`delete`**` 3` : deletes the task with ID #3.
   * **`exit`** : exits the app.
6. Refer to the [Features](#features) section below for details of each command.<br>


# Features

> **Command Format**
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.

> **Date and Time Format**
> * **Date**
>   * 25/10/2017 or 25-10-2017
>   * 25 Oct 2017
>   * 25 October 2017
>   * tomorrow/yesterday/today/next monday
> * **Time**
>   * 24-hours format : 09:30
>   * 12-hours format : 09:30pm

## Viewing help : `help`
Shows the help page to user.<br>

Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`


## Adding a task: `add`
Adds a task to Do-Do Bird.<br>

Format:

* `add TASK_NAME [; a line of details]`
* `add TASK_NAME by DATE [TIME] [; a line of details]`
* `add TASK_NAME on DATE [TIME] [; a line of details]`
* `add TASK_NAME on DATE [TIME] by DATE [Time] [; a line of details]`


> Date and Time formats follow the above guidelines.

Examples:

* **`add`**` Meet with professor ; consultation for mid-terms`
* **`add`**` CS1010 Lab 4 by 10/10/2017`
* **`add`**` Amy's weddings on 25/10/17;`
* **`add`**` demoAdd on today by tomorrow priority mid ; for show only`

Demo:

Type the command

![ADD](./images/ADD.png)

Display result

![ADD_Result](./images/ADD_Result.png)


## Seeing tasks : `see`
Shows a list of all tasks in Do-Do Bird.<br>

Format: `see`

Demo:

Type the command

![SEE](./images/SEE.png)

Display result

![SEE_Result](./images/SEE_Result.png)

## Searching tasks: `search`
Search tasks whose names or details contain any of the given keywords. <br>
Search for tasks before/after a time.<br>
Search for tasks on a particular date.<br>
Search for tasks with the specified tag.<br>
Search for tasks that are done or not done.<br>

Format:

* `search KEYWORD [MORE_KEYWORDS]`
* `search before DATE [TIME]`
* `search after DATE [TIME]`
* `search on DATE [TIME]`
* `search from DATE [TIME] till DATE [TIME]`
* `search tag TAG`
* `search done`
* `search undone`

Examples:
* **`search`**` Party fun NIgHt OUTzz`
* **`search`**` before 25/10/17 t09:30`
* **`search`**` tag birthdays`
* **`search`**` HeLlO`

> * The search is case insensitive. e.g `meeting` will match `Meeting`.
> * The order of the keywords does not matter. e.g. `Meet Professor` will match `Professor Meet`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Meeting` will match `Meeting Professor`

Demo:

Type the command

![SEARCH](./images/SEARCH.png)

Display result

![SEARCH_Result](./images/SEARCH_Result.png)

<!-- @@author A0121643R -->
## Marking tasks as done : `mark`
Marking a task in Do-Do Bird as completed.<br>

Format: `mark ID`

> The ID must be a positive integer 1, 2, 3, ...

Examples:

* **`search`**` `<br>
  **`mark`**` 2`<br>
  Mark the task with `ID #2` in the Do-Do Bird as completed.

## Unmarking tasks : `unmark`
Unmark a task in Do-Do Bird to be uncompleted.<br>

Format: `unmark ID`

> The ID must be a positive integer 1, 2, 3, ...

Examples:

* **`search`**` HeLlO`<br>
**`unmark`**` 2`<br>
Mark the task with `ID #2` in the Do-Do Bird as uncompleted.

Demo:

Type the `search` command

![SEARCH](./images/SEARCH.png)

Display search result

![SEARCH_Result](./images/SEARCH_Result.png)

Type the `mark` command

![MARK](./images/MARK.png)

Display mark result

![MARK_Result](./images/MARK_Result.png)


## Updating a task: `update`
Update an existing task inside Do-Do Bird.<br>

Format:

* `update ID [NEW_NAME] on [DATE [Time]] by [DATE [Time]] [; a line of new details]`

> * Date and Time formats follow the above guidelines.
> * To remove an optional field, input `-` as the parameter
> * The ID must be a positive integer 1, 2, 3, ...
> * To remove any pre-existing optional fields, prefix a `-` to the field specifier.

Examples:

* **`search`**` tomorrow`<br>
  **`update`**` 2 on 14/10/17 by 18/10/17;` <br>
  Update the task with `ID #2` to reflect new dates/
* **`search`**` 25/10/17`<br>
  **`update`**` 3 -on ;` <br>
  Update the task with `ID #3` to remove old on date.


## Deleting a task : `delete`
Deletes the specified task from the Do-Do Bird.<br>

Format: `delete ID`

> The ID **must be a positive integer** 1, 2, 3, ...

Examples:

* **`search`**` Tutorial`<br>
  **`delete`**` 1`<br>
  Deletes the task with `ID #1` in the Do-Do Bird.
<!-- @@author A0142421X -->
## Tagging a task : `tag`
Tags the specified task with the specified tag.<br>

Format: `tag ID TAG`

> The ID **must be a positive integer** 1, 2, 3, ...

Examples:

* **`see`** <br>
**`tag`**` 2 Tutorial`<br>
Tags the task with `ID #2` with `Homework` tag

## Untagging a task : `untag`
Untags the specified task from the specified tag.<br>

Format: `untag ID TAG`

> The ID **must be a positive integer** 1, 2, 3, ...

Examples:

* **`see`** <br>
**`untag`**` 2 Tutorial`<br>
Untags the task with `ID #2` from `Homework` tag

<!-- @@author A0138967J -->
## Storing : `store`
Change the storage location for the data file.<br>

Format: `store location`

## Undoing : `undo`
Undo the last operation.<br>

Format: `undo`

## Resetting : `reset`
Resets user's config to default.<br>

Format: `reset`


## Clearing all entries : `clear`
Clears all entries from the Do-Do Bird.<br>

Format: `clear`  

## Exiting the program : `exit`
Exits the program.<br>

Format: `exit`  

#### Saving the data
To-do list data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Do-Do Bird folder.
<!-- @@author A0121643R -->
## Command Summary

Command | Format  
-------- | :--------
Add | `add TASKNAME`
	| Example: add hello
	| `add TASKNAME priority PRIORITY`
	| Example: add hello priority high/mid/low
	| `add TASKNAME ; DETAILS`
	| Example: add hello ; first time use
	| `add TASKNAME on/from DATE`	                                              
	| Example: add hello on/from today/tomorrow/next wed/19:00/05:00pm/sunday 12:34am/Oct 20/every sat
	| `add TASKNAME by/to DATE`
	| Example: add hello by/to today/tomorrow/next wed/19:00/05:00pm/sunday 12:34am/Oct 20/every sat
	| `add TASKNAME on/from DATE by/to DATE`
	| Example: add hello on/from today/tomorrow/next wed/19:00/05:00pm by/to sunday 12:34am/Oct 20/every sat
	| `add TASKNAME on/from DATE by/to DATE priority PRIORITY`
	| Example: add hello on/from today/tomorrow/next wed/19:00/05:00pm by/to sunday 12:34am/Oct 20/every sat priority high/mid/low
	| `add TASKNAME on/from DATE by/to DATE priority PRIORITY ; DETAILS`
	| Example: add hello on/from today/tomorrow/next wed/19:00/05:00pm by/to sunday 12:34am/Oct 20/every sat priority high/mid/low ; first time use
Clear | `clear`
Delete | `delete ID`
	   | Example: delete 12
Help | `help`
Mark | `mark ID`
	 | Example: mark 12
Unmark | `unmark ID`
       | Example: mark 12
Quitting | `exit`
Search | `search KEYWORDS` 	
	   | Example: search hello/project Meeting/PrOjecT MeeTinG
	   | `search before DATE`
	   | Example: search before today/tomorrow/next wed/19:00/05:00pm/sunday 12:34am/Oct 20/every sat
	   | `search after DATE`
	   | Example: search before today/tomorrow/next wed/19:00/05:00pm/sunday 12:34am/Oct 20/every sat
	   | `search priority PRIORITY`
	   | Example: search priority high/mid/low
	   | `search tag TAG`
	   | Example: search tag study   
See | `see`
Tag | `tag ID TAG`
    | Example: tag 12 study
Untag | `untag ID TAG`
      | Example: untag 12 study
Undo | `undo`
Update | `update ID TASKNAME`
       | Example: update 10 hello again
	   | `update ID on/from DATE`
	   |Example: update 10 on/from tmr 07:00pm/sunday 12:34am/Oct 20/every sat
	   | `update ID by/to DATE`
	   | Example: update 10 by/to tmr 07:00pm/sunday 12:34am/Oct 20/every sat
	   | `update ID TASKNAME on/from DATE`
	   | Example: update 10 hello again on/from tmr 07:00pm/sunday 12:34am/Oct 20/every sat
	   | `update ID TASKNAME by/to DATE`
	   | Example: update 10 hello again by/to tmr 07:00pm/sunday 12:34am/Oct 20/every sat
	   | `update ID on/from DATE by/to DATE`
	   | Example: update 10 on/from today/tomorrow/next wed/19:00/05:00pm by/to sunday 12:34am/Oct 20/every sat
	   | `update ID TASKNAME on/from DATE by/to DATE`
	   | Example: update 10 hello again on/from today/tomorrow/next wed/19:00/05:00pm by/to sunday 12:34am/Oct 20/every sat
	   | `update ID TASKNAME on/from DATE by/to DATE priority PRIORITY`
	   | Example: update 10 hello again on/from today/tomorrow/next wed/19:00/05:00pm by/to sunday 12:34am/Oct 20/every sat priority high/mid/low
	   | `update ID TASKNAME on/from DATE by/to DATE priority PRIORITY ; DETAILS`
	   | Example: update 10 hello again on/from today/tomorrow/next wed/19:00/05:00pm by/to sunday 12:34am/Oct 20/every sat priority high/mid/low ; again and again
