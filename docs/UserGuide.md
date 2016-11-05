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

1. Download the latest `dodobird.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Do-Do Bird application.
3. Double-click the file to start the application. The GUI should appear in a few seconds.

   > ![GUI](./images/UI.png)

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`search on`**` tomorrow`:  see all tasks for tomorrow.
   * **`add`**` Meet with professor from 10/10/17 09:30 till 17:00;`
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
>   * MM/DD/YYYY or MM-DD-YYYY <br>
>       * E.g: 12/25/2017 or 12-25-2017
>   * DD MMM YYYY
>       * E.g: 25 Oct 2017
>   * DD MMMM YYYY
>       * E.g 25 October 2017
>   * Relative dates
>       * E.g tomorrow/yesterday/today/next monday/2 days later
> * **Time**
>   * 24-hours format : 0930
>   * 12-hours format : 0930pm

## Viewing help : `help`
Shows the help page to user.<br>

Format: `help`

> Help is also shown if you enter a valid command word but with an invalid format.


## Adding a task: `add`
Adds a task to Do-Do Bird.<br>

Format:

* `add TASK_NAME [; a line of details]`
* `add TASK_NAME by DATE [TIME] [; a line of details]`
* `add TASK_NAME on DATE [TIME] [; a line of details]`
* `add TASK_NAME on DATE [TIME] by DATE [TIME] [; a line of details]`
* `add TASK_NAME on DATE [TIME] by DATE [TIME] every [RECURRENCE] [; a line of details]`
* `add TASK_NAME on DATE [TIME] by DATE [TIME] priority [PRIORITY] [; a line of details]`


> Date and Time formats follow the above guidelines.

Examples:

* **`add`**` Meet with professor ; consultation for mid-terms`
* **`add`**` CS1010 Lab 4 by 10/10/2017`
* **`add`**` Amy's weddings on 25/10/17;`
* **`add`**` demoAdd on today by tomorrow priority mid ; for show only`
* **`add`**` cooking on today 1400 by 1500 every day ; recurring every day`

Demo:

Type the command

![ADD](./images/ADD.png)

Display result

![ADD_Result](./images/ADD_Result.png)

## Searching tasks: `search`
Search tasks whose names or details contain any of the given keywords. <br>
Search for floating tasks.<br>
Search for tasks before/after a date.<br>
Search for tasks on a particular date.<br>
Search for tasks with the specified tag.<br>
Search for tasks that are done or not done.<br>
Search for tasks based on priority.

Format:
* `search`
* `search done`
* `search all`
* `search floating`
* `search KEYWORD [MORE_KEYWORDS]`
* `search before DATE [TIME]`
* `search after DATE [TIME]`
* `search on DATE [TIME]`
* `search from DATE [TIME] till DATE [TIME]`
* `search tag TAG`


Examples:
* **`search`**` Party fun NIgHt OUTzz`
* **`search`**` before 25/10/17 09:30`
* **`search`**` on 25/10/17 09:30`
* **`search`**` tag birthdays`
* **`search`**` priority mid`
* **`search`**` floating`


> * Add `done` `undone` or `all` at the back of the command to indicate whether you want to see tasks that are completed or not.
> * Default search results will list tasks that are not completed.
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

* **`search`**` HeLlO`<br>
**`mark`**` 2`<br>
Mark the task with `ID #2` in the Do-Do Bird as completed.

Demo:

Type the `search` command

![SEARCH](./images/SEARCH.png)

Display search result

![SEARCH_Result](./images/SEARCH_Result.png)

Type the `mark` command

![MARK](./images/MARK.png)

Display mark result

![MARK_Result](./images/MARK_Result.png)

## Unmarking tasks : `unmark`
Unmark a task in Do-Do Bird to be uncompleted.<br>

Format: `unmark ID`

> The ID must be a positive integer 1, 2, 3, ...

Examples:

* **`search`**` HeLlO`<br>
**`unmark`**` 2`<br>
Mark the task with `ID #2` in the Do-Do Bird as uncompleted.


## Updating a task: `update`
Update an existing task inside Do-Do Bird.<br>

Format:

* `update ID [NEW_NAME] on DATE [Time] by DATE [Time] priority [PRIORITY] every [RECURRENCE] [; a line of new details]`

> * Date and Time formats follow the above guidelines.
> * The ID must be a positive integer 1, 2, 3, ...
> * To remove any pre-existing optional fields, prefix a `-` to the field specifier.

Examples:

* **`search`**<br>
  **`update`**` 2 on thursday every week ; weekly affair` <br>
  Update the task with `ID #2` to reflect new starting date and recurrence

* **`search`**` tomorrow`<br>
  **`update`**` 2 on 14/10/17 by 18/10/17;` <br>
  Update the task with `ID #2` to reflect new dates

* **`search`**` hello`<br>
  **`update`**`  1 test task on Oct 29 1600 by mon priority mid every week; update all fields ` <br>
  Update the task with `ID #3` to remove old on date.

Demo:

Type the `search` command

![SEARCH_1](./images/SEARCH_1.png)

Display search result

![SEARCH_Result_1](./images/SEARCH_Result_1.png)

Type the `update` command

![UPDATE](./images/UPDATE.png)

Display mark result

![UPDATE_Result](./images/UPDATE_Result.png)


## Deleting a task : `delete`
Deletes the specified task from the Do-Do Bird.<br>

Format: `delete ID`

> The ID **must be a positive integer** 1, 2, 3, ...

Examples:

* **`search`**` Tutorial`<br>
  **`delete`**` 1`<br>
Deletes the task with `ID #1` in the Do-Do Bird.


Demo:

Type the `search` command

![SEARCH_1](./images/SEARCH_1.png)

Display search result

![SEARCH_Result_1](./images/SEARCH_Result_1.png)

Type the `delete` command

![DELETE](./images/DELETE.png)

Display delete result

![DELETE_Result](./images/DELETE_Result.png)


<!-- @@author A0142421X -->

## Tagging a task : `tag`
Tags the specified task with the specified tag.<br>

Format: `tag ID TAG`

> The ID **must be a positive integer** 1, 2, 3, ...

Examples:

* **`see`** <br>
**`tag`**` 2 Tutorial`<br>
Tags the task with `ID #2` with `Homework` tag

Demo:

Type the `see` command

![SEE_1](./images/SEE_1.png)

Display see result

![SEE_Result_1](./images/SEE_Result_1.png)

Type the `tag` command

![TAG](./images/TAG.png)

Display tag result

![TAG_Result](./images/TAG_Result.png)


## Untagging a task : `untag`
Untags the specified task from the specified tag.<br>

Format: `untag ID TAG`

> The ID **must be a positive integer** 1, 2, 3, ...

Examples:

* **`untag`**` 2 Tutorial`<br>
Untags the task with `ID #2` from `Homework` tag

Demo:

Type the `see` command

![SEE_2](./images/SEE_2.png)

Display see result

![SEE_Result_2](./images/SEE_Result_2.png)

Type the `untag` command

![UNTAG](./images/UNTAG.png)

Display tag result

![UNTAG_Result](./images/UNTAG_Result.png)

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

Command | Format | Example
------- | :----- | :-------
Add | `add TASKNAME` | add hello
	| `add TASKNAME priority PRIORITY` | add hello priority high/mid/low
	| `add TASKNAME ; DETAILS` | add hello ; first time use
	| `add TASKNAME on/from DATE`|  add hello on/from today/tomorrow/next wed/19:00/05:00pm/sunday 12:34am/Oct 20/every sat
	| `add TASKNAME by/to DATE` | add hello by/to today/tomorrow/next wed/19:00/05:00pm/sunday 12:34am/Oct 20/every sat
	| `add TASKNAME on/from DATE by/to DATE` |add hello on/from today/tomorrow/next wed/19:00/05:00pm by/to sunday 12:34am/Oct 20/every sat
	| `add TASKNAME on/from DATE by/to DATE priority PRIORITY` | add hello on/from today/tomorrow/next wed/19:00/05:00pm by/to sunday 12:34am/Oct 20/every sat priority high/mid/low
	| `add TASKNAME on/from DATE by/to DATE priority PRIORITY ; DETAILS`| add hello on/from today/tomorrow/next wed/19:00/05:00pm by/to sunday 12:34am/Oct 20/every sat priority high/mid/low ; first time use
Clear | `clear` |
Delete | `delete ID` | delete 12
Help | `help` |
Mark | `mark ID`| mark 12
Unmark | `unmark ID`| mark 12
Quitting | `exit` |
Search | `search KEYWORDS`| search hello/project Meeting/PrOjecT MeeTinG
	   | `search before DATE` | search before today/tomorrow/next wed/19:00/05:00pm/sunday 12:34am/Oct 20/every sat
	   | `search after DATE`| search before today/tomorrow/next wed/19:00/05:00pm/sunday 12:34am/Oct 20/every sat
	   | `search priority PRIORITY`| search priority high/mid/low
	   | `search tag TAG` | search tag study   
See | `see` |
Tag | `tag ID TAG` | tag 12 study
Untag | `untag ID TAG`| untag 12 study
Undo | `undo` |
Update | `update ID TASKNAME`| update 10 hello again
	   | `update ID on/from DATE`| update 10 on/from tmr 07:00pm/sunday 12:34am/Oct 20/every sat
	   | `update ID by/to DATE` | update 10 by/to tmr 07:00pm/sunday 12:34am/Oct 20/every sat
	   | `update ID TASKNAME on/from DATE` | update 10 hello again on/from tmr 07:00pm/sunday 12:34am/Oct 20/every sat
	   | `update ID TASKNAME by/to DATE`| update 10 hello again by/to tmr 07:00pm/sunday 12:34am/Oct 20/every sat
	   | `update ID on/from DATE by/to DATE` | update 10 on/from today/tomorrow/next wed/19:00/05:00pm by/to sunday 12:34am/Oct 20/every sat
	   | `update ID TASKNAME on/from DATE by/to DATE`| update 10 hello again on/from today/tomorrow/next wed/19:00/05:00pm by/to sunday 12:34am/Oct 20/every sat
	   | `update ID TASKNAME on/from DATE by/to DATE priority PRIORITY`| update 10 hello again on/from today/tomorrow/next wed/19:00/05:00pm by/to sunday 12:34am/Oct 20/every sat priority high/mid/low
	   | `update ID TASKNAME on/from DATE by/to DATE priority PRIORITY ; DETAILS`| update 10 hello again on/from today/tomorrow/next wed/19:00/05:00pm by/to sunday 12:34am/Oct 20/every sat priority high/mid/low ; again and again
      | `update ID TASKNAME on/from DATE by/to DATE priority PRIORITY every RECURRENCE; DETAILS`| update 10 hello again on/from today/tomorrow/next wed/19:00/05:00pm by/to sunday 12:34am/Oct 20/every sat priority high/mid/low every day ; again and again
