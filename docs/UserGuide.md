# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

1. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
   Click [here](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) to download the latest Java version.
   
2. Download the latest `tars.jar` from the 'releases' tab. [*No releases available yet*]
3. Copy the file to the folder you want to use as the home folder for your TARS App.
4. Double-click the file to start the app. The GUI should appear in a few seconds. 
5. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
6. Some example commands you can try:
   * **`ls`** : lists all tasks
   * **`add`**` Complete CS2103 Quiz 3 -dt 23/09/2016 -p h -t Quiz -t CS2103` : 
     adds a task `Complete CS2103 Quiz 3` to TARS.
   * **`del`**` 3` : deletes the 3rd task shown in TARS.
   * **`exit`** : exits the app
7. Refer to the [Features](#features) section below for details of each command.<br>


## Features

#### Displaying a list of available commands : `help`
Displays a list of available commands with their individual usage information <br>
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`
 
#### Adding a task : `add`
Adds a task to TARS<br>
Format: `add <TASK> -dt <START_DATE/TIME> to <END_DATE/TIME> -p <PRIORITY> -t <TAG>[, <TAG>, <TAG>,...] -r <NUM_TIMES> <FREQUENCY>` 
 
> Words in `UPPER_CASE` are the parameters. Other than `<TASK>`, all parameters are optional. 
> You can add a floating task (i.e. tasks without date or time).
> Order of parameters are not fixed.
>
> Time is in a 24 hour format (e.g. `1330`) 
> 
> Priority options are: `h` for High, `m` for Medium, `l` for Low

Examples: 
* `add Meet John Doe -dt 26/09/2016 0900 to 26/09/2016 1030 -t CATCH UP`
* `add Complete CS2103 Quiz -dt 23/09/2016 -p h, -t Quiz -t CS2103, -r 13 EVERY WEEK`
* `add Floating Task`

#### Reserving timeslots for a task : `rsv` 
Reserves one or more timeslot for a task<br>
Format: `rsv <TASK> -dt <START_DATE/TIME> to <END_DATE/TIME> [, <START_DATE/TIME> to <END_DATE/TIME>, …]`

> Words in `UPPER_CASE` are the parameters. 
>
> Time is in a 24-hr format. More than one date/time can be added.

Examples:
* `rsv Meet John Doe -dt 26/09/2016 0900 to 1030, 28/09/2016 1000 to 1130`

#### Deleting a task with reserved timeslots : `rsv -del`
Deletes a task with all its reserved time slots <br>
Format: `rsv -del <INDEX>`

> Deletes the task at the specific `<INDEX>`. 
> The index refers to the index number shown in the task list.
> The index **must be a positive integer** 1, 2, 3, ...

Examples:
* `rsv -del 5`

#### Confirming a reserved timeslot : `confirm`  
Confirms a reserved timeslot for a particular tasks and removed all the other reserved time slots. <br>
Format: `confirm <INDEX_TASK> <INDEX_TIMESLOT> -p <PRIORITY> -t <TAG(s)>`

> Confirm the task of a specific `<INDEX_TASK>` at a timeslot of a specific `<INDEX_TIMESLOT>` <br>
> The `<INDEX_TASK>` refers to the index number shown in the task list. <br>
> The `<INDEX_TIMESLOT>` refers to the index number of the timeslot shown under each task. <br>
> Both indexes **must be a positive integer** 1, 2, 3, ...

Examples:
* `confirm 3 2 -p l -t tagname`

#### Editing a task : `edit`
Edits any component of a particular task <br>
Format: `edit <INDEX> -n <TASK> -dt <START_DATE/TIME> to <END_DATE/TIME> -p <PRIORITY> -t <TAG(s)>`

> Edits the task at the specific `<INDEX>`. 
> The index refers to the index number shown in the task list.
> The index **must be a positive integer** 1, 2, 3, ... 
>
> Words in `UPPER_CASE` are the parameters. Other than `<INDEX>`, all parameters are optional. <br>
> Order of parameters are **not** fixed.
>
> Time is in 24-hr format (e.g. 1330)

Examples:
* `edit 3 -n Meet John Tan -dt 08/10/2016 1000 to 1200 -p H -t friend`

#### Editing a tag's name : `tag -e`
Edits a tag’s name <br>
Format: `tag -e <INDEX> <TAG>`

> Edits the name of the tag at the specific `<INDEX>`. 
> The index refers to the index number shown in the tag list.
> The index **must be a positive integer** 1, 2, 3, ... 

Examples:
* `tag -e 5 Assignment`

#### Deleting a tag : `tag -del`
*[Under Development]* <br>
Deletes a particular tag <br>
Format: `tag -del <INDEX>`

> Deletes the tag at the specific `<INDEX>`
> The index refers to the index number shown in the tag list.
> The index **must be a positive integer** 1, 2, 3, ...

Examples:
* `tag -d 4` deletes the tag at Index 4

#### Listing all tags : `tag -ls`
Lists all tags in TARS <br>
Format: `tag -ls`

#### Marking tasks : `mark`
Marks a particular task(s) with the status `done` or `undone` <br>
Format: `mark -do <INDEX>[<INDEX> <INDEX> ...] -ud <INDEX>[<INDEX> <INDEX> ...]` <br>
Format: `mark -do <START_INDEX>..<END_INDEX> -ud <START_INDEX>..<END_INDEX>`

> Marks the task at the specific `<INDEX>`
> The index refers to the index number shown in the tag list.
> The index **must be a positive integer** 1, 2, 3, ..
> Start index of range must be before end index
> Use -do to mark a task(s) as `done`
> Use -ud to mark a task(s) as `undone`

Examples:
* `mark -do 2 4 6`
* `mark -ud 3 5 7`
* `mark -do 3 5 7 -ud 2 4 6`
* `mark -do 1..3 -ud 4..6`

#### Deleting a task : `del`
Deletes a particular task, or a list of task based on a specific criteria (i.e. INDEX, done/undone status, date, tags, priority) <br>
Formats: 
* `del <INDEX> [<INDEX> <INDEX> ...]` <br>
* `del <START_INDEX>..<END_INDEX>`

> Deletes the task at the specific `<INDEX>`
> The index refers to the index number shown in the tag list.
> The index **must be a positive integer** 1, 2, 3, ..
> Start index of range must be before end index

Examples:
* `del 3 6`
* `del 1..3`

#### Listing tasks : `ls`
Lists all tasks in TARS with available list filters.<br>
Format: 
* `ls`
* `ls -dt [dsc]`
* `ls -p [dsc]`

> default is to list all tasks <br>
> use -dt to list all tasks by earliest end dateTime s<br>
> use -p to list all task by priority from low to high <br>
> use dsc to list task in reverse order

Examples:
* `ls`
* `ls -dt`
* `ls -dt dsc`
* `ls -p`
* `ls -p dsc`


#### Finding tasks : `find`
Finds all tasks containing a list of keywords (i.e. AND search).<br>
Two modes: Quick Search & Filter Search. <br>
Format:
* [Quick Search]: `find <KEYWORD>[, KEYWORD, KEYWORD]` <br>
* [Filter Search]: `find -n <NAME_KEYWORD>[, NAME_KEYWORD, NAME_KEYWORD] -dt [START_DATE] to [END_DATE] -p [PRIORITY(h/m/l)] -do (or -ud) -t <TAG_KEYWORD>[, TAG_KEYWORD, TAG_KEYWORD]`

> **Quick Search Mode**: Find tasks quickly by entering keywords that match what is displayed in the task list <br><br>
> **Filter Search Mode**: Find tasks using task filters (i.e. -n, -p, -dt, -do, -ud, -t) <br>
> Use -n to filter tasks by task name <br>
> Use -p to filter tasks by priority level <br>
> Use -dt to filter tasks by date (in a date range) <br>
> Use -do to filter all done tasks (Cannot be used together with -ud)<br>
> Use -ud to filter all undone tasks (Cannot be used together with -do)<br>
> Use -t to filter tasks by tags <br><br>
> `<KEYWORD>` are **case-insensitive**. The order of the `<KEYWORD>` or `Flags` (for filter search) does not matter.

Examples: 
* `find meet John` uses Quick Search and returns all tasks containing BOTH the keywords “meet” and “John” (e.g. meet John Doe)
* `find -n meet -dt 17/10/2016 1300 to 18/10/2016 1400` uses Filter Search and returns all tasks whose name contains "meet" and whose task date falls within the range "17/10/2016 1300 to 18/10/2016 1400" (e.g. meet Tim for dinner, 17/10/2016 1800 to 17/10/2016 1900)

#### Undoing a command : `undo`
Undo a command executed by the user. <br>
Format: `undo` 

> Able to undo all `add`, `delete`, `edit` and `del` commands from the time the app starts running.

#### Redoing a command : `redo`
Redo a command executed by the user. <br>
Format: `redo` 

> Able to redo all `add`, `delete`, `edit` and `del` commands from the time the app starts running.

#### Clearing the data storage file : `clear`
Clears the whole To-Do List storage file.<br>
Format: `clear`  

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit` 

#### Changing data storage location : `cd`
Changes the directory of which the data storage file is saved in. <br>
Format: `cd <FILE_PATH>`

> Returns an error if the there is an error in the directory chosen <br>
> Note: `<FILE_PATH>` must end with the file type extension, `.xml`. 

Examples:
* `cd C:\Users\John_Doe\Documents\tars.xml`  

#### Saving the data 
TARS data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous TARS app.
       
## Command Summary

Command | Format  
-------- | :-------- 
[Add](#adding-a-task--add)| `add <TASK> -dt <START_DATE/TIME> to <END_DATE/TIME> -p <PRIORITY> -t <TAG(s)> -r <NUM_TIMES> <FREQUENCY>`
[Change Storage Location](#changing-data-storage-location--cd) | `cd <FILE_PATH>`
[Clear](#clearing-the-data-storage-file--clear) | `clear`
[Confirm](#confirming-a-reserved-timeslot--confirm) | `confirm <INDEX_TASK> <INDEX_TIMESLOT>`
[Delete](#deleting-a-task--del) | `del <INDEX> [, <INDEX>, <INDEX>, …]`
[Delete [by Date]](#deleting-a-task--del) | `del -dt [<START_DATE> to <END_DATE>] <INDEX>[, <INDEX>, <INDEX>,...]`
[Delete [by Tags]](#deleting-a-task--del) | `del -t <TAG>[ , <TAG>, <TAG>] <INDEX>[, <INDEX>, <INDEX>,...]`
[Edit](#editing-a-task--edit) | `edit <INDEX> -n <TASK> -dt <START_DATE/TIME> to <END_DATE/TIME> -p <PRIORITY> -t <TAG(s)>`
[Edit [Append]](#editing-a-task-by-appending-details-to-a-task--edit--ap) | `edit <INDEX> -ap <TO APPEND>`
[Exit](#exiting-the-program--exit) | `exit`
[Find [Quick Serach]](#finding-tasks--find) | `find KEYWORD [MORE_KEYWORDS]`
[Find [Filter Serach]](#finding-tasks--find) | `find -n <NAME_KEYWORD> -dt <START_DATE/TIME> to <END_DATE/TIME> -p <PRIORITYLEVEL> -do [or -ud] -t <TAG_KEYWORD>`
[Help](#displaying-a-list-of-available-commands--help) | `help`
[List](#listing-tasks--ls) | `ls`
[List [Date]](#listing-tasks--ls) | `ls -dt`
[List [Priority]](#listing-tasks--ls) | `ls -p`
[Mark Done](#marking-tasks--mark) | `mark -do <INDEX>[ , <INDEX>, <INDEX>, ...]`
[Mark Undone](#marking-tasks--mark) | `mark -ud <INDEX>[ , <INDEX>, <INDEX>, ...]`
[Reserve](#reserving-timeslots-for-a-task--rsv) | `rsv -n <TASK> -dt <START_DATE/TIME> to <END_DATE/TIME> [, <START_DATE/TIME> to <END_DATE/TIME>, …]`
[Reserve [Delete]](#deleting-a-task-with-reserved-timeslots--rsv--d) | `rsv -d <INDEX>`
[Tag [Delete]](#deleting-a-tag--tag--d) | `tag -d <INDEX>`
[Tag [Edit]](#editing-a-tags-name--tag--e) | `tag -e <INDEX> <TAG>`
[Tag [List]](#listing-all-tags--tag--ls) | `tag -ls`
[Undo](#undoing-a-command--undo) | `undo`
[Redo](#redoing-a-command--redo) | `redo`

