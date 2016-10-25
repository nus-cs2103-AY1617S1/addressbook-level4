# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `malitio.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your malitio.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/Ui.jpg" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list deadlines`** : lists all deadlines
   * **`add`**` drink water` : 
     adds `drink water` to the to-do-list.
   * **`delete`**` 3` : deletes the 3rd item shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`
 
#### Adding a task: `add`
Adds a task to the to-do list<br>
There are three types of tasks that can be added in Malitio<br>
Namely Floating Task, Deadline and Event. Floating Task are tasks which have no due dates.<br>
Floating Task Format: `add TASK_NAME [t/TAG] [p/priority]`<br>
Deadline Format: `add TASK_NAME by DDMMYYYY TTTT [t/TAG] [p/priority]`<br>
Event Format: `add TASK_NAME start DDMMYYYY TTTT end DDMMYYYY TTTT [t/TAG]`


Examples: 
* `add drink water p/high`
* `add CS2103 homework by 09102016 1100  p/high`
* `add lunch with mom start 05102016 1400 end 05102016 1700 t/don’t be late`
* `time format is from 0000 to 2359`

#### Listing tasks: `list`
Shows a list of everything in the to-do list.<br>
Format: `list [tasks|events|deadlines]`

Shows a list of all events and/or deadlines in the to-do list on and after that date.<br>
Format: `list [deadlines|events] DDMMYYYY TTTT`

Examples:
* `list`
* `list deadlines`
* `list deadlines 05102016 1400`
* `list 05102016 1400`
* `time format is from 0000 to 2359`

#### Finding all deadlines/floating tasks/events containing any keyword in their names and tags: `find`
Finds all input entries specified by the type (deadlines/ floating tasks/ events) whose names contain any of the given keywords.<br>
If the type is not specified, all entries containing the keyword will be displayed. <br>
Format: `find KEYWORD  [MORE KEYWORDS] [t/TYPE]`

> * The search is case insensitive.
> * The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
> * Only the task name and tags are searched.
> * Only full words will be matched e.g. `Han` will not match `Hans`
> * Task matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Hans` will match `Hans Bo`

Examples: 
* `find lunch t\task`<br>
  Returns `lunch with mom in task` 
* `find lunch t\deadlines`<br>
  Returns `lunch with mom in deadlines` 
* `find lunch t\events`<br>
  Returns `lunch with mom in events` 
* `find lunch dinner breakfast`<br>
  Returns Any task having names `lunch`, `dinner`, or `breakfast`

#### Deleting a task: `delete`
Deletes the specified task from the to-do list.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must have either 'f','d' or 'e' as a prefix and also a positive integer** eg. f1, e2, d3, ...

Examples: 
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the to-do list.
* `find lunch`<br> 
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` or ‘ command.

#### Edit a task : `edit`
Edits the specified task from the to-do list.<br>
Edit Floating Task Format: `edit 'f'INDEX [TASK_NAME] [t/TAG]`<br>
Edit Deadline Format: `edit 'd'INDEX [TASK_NAME] [by DDMMYYYY TTTT] [t/TAG]` <br>
Edit Event Format `edit 'e'INDEX [TASK_NAME] [start DDMMYYYY TTTT] [end DDMMYYYY TTTT]` <br>


> Edits the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must have either 'f','d' or 'e' as a prefix and also a positive integer** f1, e2, d3, ...<br>
  The prefix is not case sensitive. <br>
  The edit function can only edit the details within the same type of task. <br>
  No changing of task type supported. <br>

Examples: 
* `list`<br>
  `edit f2 p/low`<br>
  Edit the 2nd floating task in the to-do list replacing the priority. <br>
  `edit e1 end 21122016 2359` <br>
  Edit the 1st event in the to-do list replacing its orginial end time with 21122016 2359. <br>
* `find lunch`<br> 
  `edit 1 n/lunch with mom`<br>
  Edits the 1st task in the results of the `find` or ‘ command.<br>
  Need to put at least one field

#### Clearing all entries : `clear`
Clears all entries from the to-do list.<br>
Format: `clear`  

#### Undo the most recent action: `undo`
Undo the most recent action and reverts the to-do list to previous state. <br>
Format: `undo`

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data 
Malitio data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually. <br>

#### Specifying location of local data file: `save`
Users can specify which directory to save their data file. Only valid directory will be created if it does not exist already. <br>
The old data file will automatically be deleted.
Format: `save DIRECTORY`

Example: 
* `save C://Users`<br>
  Saves data in C://Users/malitio.xml


## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous malitio folder.
       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `add TASK_NAME [by DDMMYYYY TTTT] [start DDMMYYYY TTTT end DDMMYYYY TTTT] [t/TAG]...`
Clear | `clear`
Delete | `delete f/d/e+INDEX`
Find | `find KEYWORD [MORE_KEYWORDS] [t/TYPE]`
List | `list`
Edit | `edit f\d\e+INDEX [NAME] [by DDMMYYYY TTTT] [start DDMMYYYY TTTT] [end DDMMYYYY TTTT] [t/TAG] `
Help | `help`
Undo | `undo`
Save | `save DIRECTORY`


