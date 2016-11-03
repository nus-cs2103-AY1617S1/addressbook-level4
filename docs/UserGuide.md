# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [Time Format](#time-format)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `FlexiTrack.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Address Book.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`add`**` add CS2103 tutorial 3 by/ Saturday` : 
     adds a task with the title of `CS2103 tutorial 3` to the FlexiTrack.
   * **`delete`**` 3` : deletes the 3rd contact shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>

//@@author A0127686R
## Features

> **Command Format**
> * Words in `square brackets ([])` are the parameters.
> * Items within `arrow signs (<>)` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.

//@@author A0138455Y
#### Viewing help : `help`
#### Shortcut : `h`
Format: `help` or `help [command word]`

Examples: 
* `help add`
* `help edit`
* `h delete`

> Help is also shown if you enter an incorrect command e.g. `abcd`
> default help message will show a list of all command word, e.g. enter `help`
//@@author
 
#### Adding a task: `add`
#### Shortcut : `a`
Adds a task to the FlexiTrack.<br>
Format: `add [task title] < by/ [deadline] >`

#### Adding an recurring task
#### Shortcut : `a`
Adds a task(recursive) to the FlexiTrack.<br>
Format: `add [task title] fr/ [number of occurrence] ty/ [day | week | month] from/ [starting time] to/ [ending time]`

Examples: 
* `add CS2103 tutorial 3 `
* `add CS2103 tutorial 3 by/ Saturday`
* `a CS2103 tutorial 3 by/ tmr 9am`
* `add Submit PC1222 Labsheet fr/ 5 ty/ week by/ Tuesday 5pm`

#### Adding an event: `add`
#### Shortcut : `a`
Adds a event to the FlexiTrack.<br>
Format: `add [event title] from/ [starting time] to/ [ending time]`

#### Adding an recurring event : `add`
#### Shortcut : `a`
Adds a event(recursive) to the FlexiTrack.<br>
Format: `add [event title] fr/ [number of occurrence] ty/ [day | week | month] from/ [starting time] to/ [ending time]`

Examples: 
* `add Bintan trip from/ Saturday to/ Sunday`
* `a CS2103 Lecture from/ Friday 2pm to/ Friday 4pm `
* `add complete CS2103 post-lecture quiz fr/ 10 ty/ week by/ Sunday 10pm`

#### Finding a task or an event containing any keyword in their title: `find`
#### Shortcut : `f`
Finds a task ot an event whose title contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is non case sensitive. e.g `soccer` will match `Soccer`
> * The order of the keywords does not matter. e.g. `soccer dinner` will match `dinner soccer`
> * Only the task/event title is searched.
> * Only full words will be matched e.g. `socc` will not match `soccer` (unless 'f/' keyword is used)
> * Task or event matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `soccer` will match `soccer training`
> * Search by exact task name can be activated with the shortcut 'f/' before the task name.

Examples: 
* `find Soccer`<br>
  Returns `Soccer training` but not `soccer training`
* `find assignment dinner mid-term`<br>
  Returns Any task/event having `assignment`, `dinner`, or `mid-term` in the title
* `f attend CS2103 lecture`<br>
  Returns Any task/event having `attend`, `CS2103`, or `lecture`

#### Finding a specific task or an event containing an exact phrase in their title: `find f/`
#### Shortcut : `f f/`
Finds a task ot an event whose title contain any of the given keywords.<br>
Format: `find f/ EXACT PHRASE`

> * The search is case sensitive. e.g `soccer practice` will not match `Soccer practice`
> * Only the task/event title is searched..

Examples: 
* `find f/ Soccer training`<br>
  Returns `Soccer training` but not `Soccer`
* `find f/ cn homework`<br>
  Returns Any task/event containing the exact phrase `cn homework` in their title
* `f f/ attend CS2103 lecture`<br>
  Returns Any task/event having exact title `attend CS2103 lecture`

#### Deleting a person : `delete`
#### Shortcut : `d`
Deletes the specified task/event from the FlexiTrack. Irreversible.<br>
Format: `delete [index]`

> Deletes the task/event at the specified `index`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `delete 2`<br>
  Deletes the 2nd task/event in the address book. 
* `d 1`<br>
  Deletes the 1st task/event in the results of the `find` command.

#### Undo operations : `undo`
Undo operation a number of times.<br>
Format: `undo < [number of undo] >`

> The number of undo parameter is optional. When it is not satisfied, one undo will be done. 
> The maximum number of undo is 15 

Examples: 
* `undo`<br>
  Undo the operation 1 time.
* `undo 4`<br>
  Undo the operations 4 times. 
  
#### Mark a task as complete : `mark`
#### Shortcut : `m`
Mark an existing task to complete and move it to the bottom of the list.<br>
Format: `mark [index]`  

> Mark the task/event at the specified `index`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `mark 5`<br>	

//@@author A0138455Y
#### Mark a task as complete : `unmark`
#### Shortcut : `u`
Mark an existing task to complete and move it to the bottom of the list.<br>
Format: `unmark [index]`  

> Unmark the taks/event at the specified `index`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `unmark 5`<br>

#### Specify storage location: `cs`
Specify the storage location where the program save the data. <br>
Format: `cs [path]`  

Examples: 
* `cs data/newStorage `<br>	
* `cs newDataStorage `<br>

Limitation: This feature Only allow user to change storage path within the FlexiTrack folder.

> [path] can only contains alphanumeric, forward slash '/' and underscore '_'. 

#### Block multiple time slot for an event : `block`
#### Shortcut : `b`
Block another time slot for an unconfirmed existing event.<br>
Format: `block [Description] from/ [starting time] to/ [ending time]`  

> The new block period must not overlapping current block task.
> New event will not be allow to add in if the period of the new event overlapping any blocked task from block list.

Examples: 
* `block for cs2103 project from/ 5pm to/ 7pm`<br>	
//@@author

#### Find free time slot: `find time`
Find and list free time slot in the schedule that is equal to or longer than the specified timing (in hours).<br>
Format: `find time [number of hours] < [number of slots to find] >`  

> If there is there is a time slot longer than the required free time slot, 
	then the free time period will be return to you
> By default, find time will only give a single free slot when the number of slots required is not keyed in.

Examples: 
* `find time 3 `<br>	
	You have a minimum of 3 hours free time slot between: today 5pm - 9pm. 
* `find time 5 3 `<br>	
	You have a minimum of 5 hours free time slot between: Monday 2pm - 9pm, Tuesday 1pm - 6pm and Saturday 9am - 5pm. 

#### Exiting the program : `exit`
#### Shortcut : `q`
Exits the program.<br>
Format: `exit`  

#### Saving the data 
Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## Time Format 
FlexiTrack support various timing input. Here are some examples! 

#### Exact timing 
User Input | Timing information read by FlexiTrack
---------- | :---------------
21 June 2018 4pm | Jun 21 16:00:00 
1st January 7.20 | Jan 01 07:20
April 22nd 5am | Apr 22 05:00

#### Relative timing 
If today is 1st of February a relative timing input is also possible with FlexiTrack

User Input | Timing information read by FlexiTrack
---------- | :---------------
Tomorrow 4pm | Feb 02 16:00:00 
Next week 720am | Feb 08 07:20
3 weeks 2 pm  | Feb 22 14:00 
next month 8am | Mar 01 08:00 

#### Notes on FlexiTrack timing  
1. FlexiTrack does support year. However, make sure that you also specify the hour of the timing 
	as FlexiTrack will choose timing over year when it is uncertain. 
2. When you do not specify the exact timing, FlexiTrack will assign your task to be 7.59 for due
	date and starting time, and 16.59 for ending time. 

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous FlexiTrack folder.
       
## Command Summary

Command | Shortcut | Format
-------- | ---- | :-------- 
Add task | a | `add [task title] <fr/ [number of recurrances] ty/ [day | week | month] (optional)> <by/ [deadline] (optional)>` 
Add event | a | `add [event title] <fr/ [number of recurrances] ty/ [day | week | month] (optional)> from/ [starting time] to/ [ending time]`
Block | b | `block [description] from/ [starting time] to/ [ending time]`
Gap | g | `gap [duration] <\n [number of slots] (optional)>`
Delete | d | `delete [index]`
Clear | c | `clear`
Edit | e | `edit [index] <by/ [deadline] (optional)> <n/ [title] (optional)> <from/ [starting time] (optional)> <to/ [ending time] (optional)>`
Mark | m | `mark [index]`
Unmark | u | `unmark [index]`
Find | f | `find KEYWORD [MORE_KEYWORDS]`
List | l | `list [filter]`
Select | s | `select [index]`
Undo | un | `undo`
Redo | rd | `redo`
Change Storage Path| cs | `cs [path]`
Exit | q | `exit`
Help | h | `help [command word(optional)]`
