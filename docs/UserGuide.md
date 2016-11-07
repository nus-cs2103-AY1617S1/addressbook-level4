# User Guide

* [Quick Start](#quick-start)
* [Features](#features)

# Quick start
 
1. Ensure that you have the latest Java version ‘1.8.0_60’ or later installed in your 	Computer.<br>
	> Having any Java 8 version is not enough<br>
	> The application will not work for any earlier Java versions
2. Find the project in the `Project Explorer` or `Package Explorer` (usually located 	at the left side)
3. Right click on the project
4. Click `Run As` > `Java Application` and choose the `Main` class. The GUI should 	appear within split second.
5. Type the command into the command box and press <kbd>Enter</kbd> to execute the 	command. <br>
	List of commands:<br>
	 	**`help`** : opens instruction<br>
	 	**`add`** : adds a task<br>
	 	**`show`** : shows all tasks<br>
	 	**`find`** : searches for a task<br>
	 	**`edit`** : edits a task<br>
	 	**`delete`** : deletes a task<br>
	 	**`complete`** : marks a task as completed<br>
	  	**`pin`** : pin tasks<br>
	 	**`unpin`** : removes tasks from the pinned list<br>
	 	**`undo`** : marks a task as completed<br>
	 	**`clear`** : deletes every task on the list<br>

7.  Refer to the [Commands](#commands) section below for details of each command.<br>


# Features



## Commands 
### Viewing help : `help`
Format: 

```
help
```

> Help is also shown if you enter an incorrect command e.g. `abcd`

### Adding a task : `add`

Adds a task to the planner <br><br>
Format:<br> 
```
1. add [TASKNAME] s/[START](optional) e/[END](optional) c/[CATEGORY]...(optional)

```

Examples:<br>

1. `add travel` <br>
> Task with no specified timing is added to today's schedule<br>
 
2. `add meeting s/tomorrow 2pm e/4pm` <br>
> Fixed task is added from 2pm to 4pm the next day<br>

3. `add math homework e/7 nov 6pm` <br>
> Task with a deadline is added<br>


### Viewing a schedule : `show`

Shows tasks based on query(date or completion)

Format: 

```
show [DATE]/[COMPLETION](optional)
```

Examples:<br>
`show`<br>
>Shows schedule for today<br>

`show today`<br>
>Shows schedule for today <br>

`show complete`<br>
>Shows completed tasks <br>

`show not complete`<br>
>Shows uncompleted tasks <br>

`show next wednesday`<br>
>Shows schedule for next wednesday <br>


<br>

The show function will sort the users's schedule and display it.

Tasks are sorted by their urgency(how close it is to its deadline).

The user can then use this recommended schedule to follow the order of the tasks and worryless-ly go about their day. 


### Searching for a task: `find`

Searches for a particular task and displays more information about it.<br>

Format: 

```
find TASKNAME
```

Examples:

```
find cs lecture
```

> * The search is case insensitive.
> * The order of the keywords does not matter. e.g. `math assignment` will match `assignment math`
> * Only the name is searched.
> * Task matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `math` will match `math lecture`

### Editing a task: `edit`

Edits a particular task's details<br>
Format:
```
edit [INDEX] [NEWTASKNAME](optional) [NEWSTART](optional) [NEWEND](optional) [NEWCATEGORY](optional)
```
Examples:<br>
`edit 2 tomorrow`<br>
`s/wednesday 4pm e/6pm` (only changes date and time)<br>
  
 
### Deleting a task : `delete`

Description: Deletes a task from the planner. <br>

Format: `delete [INDEX]` 

> Deletes the task at the specified `INDEX`. 
  The index refers to the index number shown on the list that is currently being viewed<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: <br>
```delete 5```
>Deletes task 5 of current list being viewed

### Completing a task : `complete`

Description: Marks a task as completed from the planner. <br>

Format: `complete [INDEX]` 

> Marks the task at the specified `INDEX` as completed. 
  The index refers to the index number shown on the list that is currently being viewed<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: <br>
```complete 5```
>Task 5 of current list being viewed is marked as completed<br>

### Pin important task on the pinned task list: `pin`

Description: Pins task on the list on the left. <br>

Format: `pin [INDEX]` 

> Pins the task at the specified `INDEX`. 
  The index refers to the index number shown on the list that is currently being viewed<br>
  The index **must be a positive integer** 1, 2, 3, ...

  
Examples: <br>
```pin 5```
>Task 5 of current list is being pinned on  the pinned list<br>


### Remove important task on the pinned task list: `unpin`

Description: Unpins task on the list from the pinned tasks list. <br>

Format: `unpin INDEX` 

> Unpins the task at the specified `INDEX`. 
  The index refers to the index number shown on the pinned tasks list(left)<br>
  The index **must be a positive integer** 1, 2, 3, ...

  
Examples:<br>
```unpin 5```
>Task 5 of pinned list is being removed and put back to the schedule list<br>


### Undo last command: `undo`

Description: Undo the latest command. <br>

Format: `undo` 

>Undo the last command. If the last command was `add`, the task added will be removed if `undo` is invoked<br>
  

Examples:<br>
`undo`

Undo previous command <br>


### Delete every task: `clear`

Description: Clears all tasks. <br>

Format: `clear` 

>Clears all task from the list<br>
    

Examples: <br>
`clear`
>The list is now empty <br>


### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  <br>


### Saving the data 
Daily Planner data is saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

<br> 





## Descriptions and Usage
### Tasks
The daily planner stores all of the users events as a `Task`.

A `Task` can be viewed, added, searched, edited or deleted with the right command word as can be found in [Commands](#commands).

**Every `Task` must consist of a mandatory `TASKNAME` field and optional `STARTDATE`, `STARTTIME`, `ENDDATE`, `ENDTIME`, `isRECURRING` fields.**

#### `STARTDATE` and `ENDDATE` Field
The `STARTDATE` and `ENDDATE` field, if specified, tells the Daily Planner which date(s) the task is meant for. If no `ENDDATE` is specified, Daily Planner assumes the task is meant to be done today. 

The `STARTDATE` and `ENDDATE` field can accept natural descriptions of dates. The following are all valid dates:
```

11/11/2016
The 31st of April in the year 2008
Fri, 21 Nov 1997
Jan 21, '97
Sun, Nov 21
jan 1st
february twenty-eighth
next thursday
last wednesday
today
tomorrow
yesterday
next week
next month
next year
3 days from now
three weeks ago
```

#### `STARTTIME` and `ENDTIME` fields

1. No `STARTTIME` or `ENDTIME` fields have to be entered for tasks with no specific timing
2. For a task that must occur within a fixed time period, `STARTTIME` and `ENDTIME` fields must be given in the following format:
    * `s/STARTTIME e/ENDTIME`
    * Example: `s/2pm e/4pm` 
3. For tasks with only a deadline, only the `ENDTIME` has to be entered after `e/` keyword:
    * `e/ENDTTIME`
    * Example: `e/6pm`
4. Both fields require users to specify am or pm after the number.

`STARTTIME` and `ENDTIME` fields can also accept various natural descriptions:

```

6pm
8am

```
