# br
###### \UserGuide.md
``` md
<div style="page-break-after: always;"></div>

## Features

**Command Format**
* Words in `UPPER_CASE` are the parameters.
* Items in `ANGLE_BRACKETS` are optional.
* Items with `...` after them can have multiple instances.
* The order of parameters is fixed.


#### Adding a task: `add`
Description: Adds a task to TPTM<br>
Format: `add TASKNAME s/<STARTDATE> d/<DEADLINE> p/<PRIORITY> t/<TAG>...`<br>
Shortcut: `+`

> Key things to note:
> * STARTDATE AND DEADLINE parameters can be in ddmmyy, ddmmyy [HH:MM] or dd-mm-yy [HH:MM] formats.
> * Time must be in the 24-hour format.
> * If no time is specified, STARTDATE will be set to a default value of 00:00 whereas DEADLINE will default to 23:59.
> * Tasks can have different priority levels or none at all (from 1 to 5, where 1 is the highest priority and 5 is the lowest priority).<br>
> * Tasks can have any number of tags (even 0).

Examples:
* `add complete report`<br>
  Adds a floating task named ‘complete report’
* `add CS2103 project d/231016 p/1 t/Group`<br>
  Adds a deadline task named ‘CS2103 project’ that is due on ‘23-10-16’ with a priority level of ‘1’ and the tag ‘Group’.
* `add make sandwich s/111016 12:45 d/111016 13:00 p/5 t/hungry`<br>
  Adds an event named ‘make sandwich’ which starts at '12:45' on '11-10-16’ due on the same day at '13:00’ with a priority level of ‘5’ and the tag ‘hungry’

#### Editing a task: `edit`
Description: Edits the index selected task.<br>
Format: `edit INDEX INPUT <INPUT> <INPUT>`

> Key things to note:
> * Edits the task by replacing the information stored with the input entered.<br>
> * Inputs are the same as specified in the `add` command function.

Examples:
* `list`<br>
  `edit 3 Finish studying for EE2021 d/121116 p/4`<br>
  Edits the third task in the list by replacing the description, changing the date and the priority.
* `list`<br>
  `edit 1 d/111116 t/Johnwillbelate`<br>
  Added in the tag `Johnwillbelate`


#### Deleting a task: `delete`
Description: Deletes the specified task from TPTM.<br>
Format: `delete INDEX`<br>
Shortcut: `-`

> Key thing to note:<br>
> The index refers to the index number shown in the most recent listing.<br>

Examples:
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the list.
* `find CS2101`<br>
  `delete 1`<br>
  Deletes the 1st task based on the results of the `find` command.

#### Listing all uncompleted tasks: `list`
Description: Lists all uncompleted tasks in order of task index.<br>
Format: `list`<br>
Shortcut: `l`


#### Listing all tasks: `listall`
Description: Lists all tasks except deleted tasks in order of task index.<br>
Format: `listall`<br>
Shortcut: `la`

#### Listing all tasks with the same tag: `listtag`
Description: Lists all tasks that have the same tag in order of task index.<br>
Format: `listtag KEYWORD`<br>
Shortcut: `lt`

> Key things to note:
> * The `KEYWORD` is case insensitive.
> * A partial `KEYWORD` will bring up all tags that relate to it. e.g. `fri` will bring up `friends` and `friday`.<br>


```
###### \UserGuide.md
``` md
#### Finding all tasks containing any keyword in their name: `find`
Description: Finds task/s whose names contain any of the specified keywords.<br>
Format: `find KEYWORD <MORE_KEYWORDS>`<br>
Shortcut: `@`

> Key things to note:
> * The search is not case sensitive. e.g. `cs2103t` will match `CS2103T`.<br>
> * The search can be partial. e.g. `CS21` will display `CS2101`, `CS2103T` and `CS2103 Project`.<br>
> * The order of the keywords does not matter. e.g. `Software Engineering` will match `Engineering Software`<br>
> * Only the keywords are searched.<br>
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).<br>
    e.g. `Software` will match `Software Engineering`

Examples:
* `find Software`<br>
  Returns `Software Engineering` but not `software`
* `find CS2103T Software Engineering`<br>
  Returns any task that has the names `CS2103T`, `Software`, or `Engineering`
```
###### \UserGuide.md
``` md


```
###### \UserGuide.md
``` md
#### Completing a task: `complete`
Description: Completes the task at the specified index in TPTM’s task list and appends ‘is completed’ to the end of the tasks name.
Format: `complete INDEX`<br>
Shortcut: `com`

> Key thing to note: <br>
> The task/event will not appear when the command `list` is used and will only appear when `listall` is used.

Examples:
* `find MeaningOfLife`<br>
  `complete 1` <br>
  Completes the 1st task in the results of the `find` command.
* `list`<br>
  `complete 2`<br>
  Completes the 2nd task in the task manager.
```
