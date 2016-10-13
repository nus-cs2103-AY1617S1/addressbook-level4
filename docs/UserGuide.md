# User Guide

<br>
## About Amethyst

Amethyst is a command-line task manager targeted at power users, who would like to store, access and edit information about one's tasks efficiently.

<br>
## Table of Contents

1. [Quick Start](#1-quick-start)  
2. [Features](#2-features)  
    2.1. [View help](#21-view-help--help)  
    2.2. [Add a task](#22-add-a-task-add)  
    2.3. [List tasks](#23-list-tasks-list)  
    2.4. [Find tasks containing particular keyword(s) in task name](#24-find-tasks-containing-particular-keywords-in-task-name-find)  
    2.5. [Delete task(s)](#25-delete-tasks-del)  
    2.6. [Mark task(s) as done](#26-mark-tasks-as-done-done)  
    2.7. [Edit a task](#27-edit-a-task-edit)  
    2.8. [Undo last operation](#28-undo-last-operation-undo)  
    2.9. [Clear all entries](#29-clear-all-entries-clear)  
    2.10. [Set data storage location](#210-set-data-storage-location-set-storage)  
    2.11. [Set an alias for an exisiting command](#211-set-an-alias-for-an-existing-command-add-alias)  
    2.12. [List aliases](#212-list-aliases-list-alias)  
    2.13. [Delete alias(es)](#213-delete-aliases-delete-alias--remove-alias)  
    2.14. [Exit Amethyst](#214-exit-amethyst-exit)  
3. [Command Summary](#3-command-summary)

<br>
## 1. Quick Start

1. **Install Java 8 Update 60 or higher**<br>
The latest version is available for download [here](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

2. **Download Amethyst Task Manager**<br>
Save the latest `.jar` file from the [releases](dummy link) tab to a folder of your choice.

3. **Launch the program**<br>
Double-click the file to start Amethyst. You will see the Graphical User Interface (GUI) below.

4. **Enter a command**<br>
To see a list of all available commands, type `help` and press <kbd>Enter</kbd>.

5. **Try some commands**
    - `add event dinner with wife on 25/12/16 from 7:00pm to 9:00pm`  <br>
    Add an event with name 'dinner with wife' from 7 pm to 9 pm on 25th December 2016. 
    - `list deadline`  <br>
    View all tasks that are deadlines arranged in chronological order.
    - `find lab homework, boy`  <br>
    View all tasks with keywords 'lab homework' or 'boy' in task names.
    

<br>
## 2. Features

**Command Format**
- Words in `UPPER_CASE` are user inputs. <br>
- Items in `[]` are optional. <br>
- A pipe `|` between items indicates an either-or relationship between them. <br>
- Items with `...` after them can have multiple instances. <br>
- The order of parameters is not fixed. <br>
- Valid date formats are dd-mm-yy (eg. 03-12-16) and dd-MMM-yy (eg. 03-Dec-15). The name of the month is case-insensitive. <br>
- Valid time formats are hh:mm (eg. 18:30) and hh:mmam/hh:mmpm (eg. 06:30am/06:30pm).


<br>
#### 2.1. View help : `help`
Opens help window to display program usage instructions and command summary.<br>

Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`


<br>
#### 2.2. Add a task: `add`
Adds a task to the task manager. Three different types of tasks, namely events, deadlines and tasks to be done someday, are supported.<br>
##### Events
Format: `add event 'NAME from hh:mm to hh:mm on dd-mm-yy`
##### Deadlines
Format: `add deadline 'NAME' by hh:mm on dd-mm-yy`
##### Tasks to be done someday
Format: `add someday 'NAME'`

Valid Examples:
* `add event 'dinner with wife' on 25-12-16 from 19:00 to 21:00pm` <br>
Note that the time and date parameters can be entered in any order.
* `add deadline 'Lab Report' by 4:00pm on 03-Mar-15` <br>
Note that the following time and date formats (hh:mmam/hh:mmpm and dd-MMM-yy) are supported too.

Invalid Examples:
* `add someday 'Learn "artistic" sarcasm'` <br>
Note that the above is invalid since double quotes should not be used.
* `add someday 'Read EL James' book 50 Shades of Grey'` <br>
Note that the above is invalid since only one pair of single quotes should be used when indicating the task name. In this case, there is an additional single quote used to indicate apostrophe.


<br>
#### 2.3. List tasks: `list`
Shows a numbered list of tasks, filtered by optional parameters.<br>

Format: `list [TASK_TYPE] [done|not-done] [dd-mm-yy] [hh:mm]`

> The three valid task types are "event", "deadline" and "someday".
> Tasks are listed in chronological order.
> If a time is provided, tasks will be filtered as follows:
> - Events that are occuring at the specified time, start and end time inclusive
> - Deadlines that are due before the specified time

Example:
* `list someday not-done`  <br>
Lists all someday tasks that have not been completed.


<br>
#### 2.4. Find tasks containing particular keyword(s) in task name: `find`
Finds all tasks containing any of the specified keywords in task name, displays identified tasks in a numbered list.<br>

Format: `find KEYPHRASE_WORD_ONE KEYPHRASE_WORD_TWO [KEYPHRASE_MORE_WORDS] ..., [MORE_KEYPHRASES] ...`

> * Keyphrases are separated by commas
> * The search is _case-insensitive_. e.g `hANs bo` will match `Hans Bo`
> * The order of the keywords within each phrase matters. e.g. `Hans Bo` will not match `Bo Hans`
> * The order of the keyphrases does not matter. e.g. `Bo, Hans` will match `Hans Swagtacular Bo`
> * Only the name is searched.
> * Partial phrases will be matched e.g. `ns B` will match `Hans Bo`

Examples:
* `find meeting` <br>
  Returns `Meeting with John` and `Skytok project meeting`.
* `find Physics test, chemistry, biology` <br>
  Returns any task containing any of `Physics test`, `chemistry`, or `biology`.


<br>
#### 2.5. Delete task(s): `del`
Deletes the specified task(s) from the task manager. <br>

Format: `del INDEX [MORE_INDICES] ...`

> Deletes the task at the specified INDICES.
  The indices refer to the index numbers shown in the most recent listing.<br>
  Indices entered **must be positive integers** 1, 2, 3, ...

Examples:
* `list`<br>
  `del 2 4`<br>
  Deletes the 2nd and 4th tasks in the task manager.
* `find birthday`<br>
  `del 1`<br>
  Deletes the 1st birthday task in the results of the `find` command.


<br>
#### 2.6. Mark task(s) as done: `done`
Marks the specified task(s) as done. <br>

Format: `done INDEX [MORE_INDICES] ...`

Examples:
* Refer to 2.5. [Delete task(s)](#25-delete-tasks-del) <br>


<br>
#### 2.7. Edit a task: `edit`
Overwrites specified attributes of the specified task. <br>
Format: `edit INDEX ['NEW_NAME'] [from hh:mm to hh:mm|by hh:mm] [dd-mm-yy] [done|not-done]`

> The `from` and `to` edits are only valid for events.
> The `by` edit is only valid for deadlines.
> The date edit is valid for both events and deadlines, but not somedays.

Example:
* `list event` <br>
  `edit 1 'Hamlet at The Globe Theatre' from 08:00pm to 11:00pm` <br>
  Edits the name of first task listed to 'Hamlet at The Globe Theatre' and its time period to 08:00pm-11:00pm.


<br>
#### 2.8. Undo last operation: `undo`
Returns the program to a state where the last operation performed did not occur. <br>

Format: `undo`

> The command can be called repeatedly and will undo all operations up to
> and including the first operation performed upon starting the program.
> Calling `undo` subsequently will print an error to the console and no change to the data will occur.


<br>
#### 2.9. Clear all entries: `clear`
Clears all entries from the task manager. <br>

Format: `clear`


<br>
#### 2.10. Set data storage location: `set-storage`
Saves all task data to the specified folder. <br>

Format: `set-storage FILEPATH`

> Existing data will be moved to the new folder.

Example:
`set-storage C:\Users\Liang\Desktop`


<br>
#### 2.11. Set an alias for an existing command: `add-alias`
Adds a new shortcut for an existing command. <br>

Format: `add-alias 'COMMAND_ALIAS'='COMMAND_PHRASE'`

> On pressing enter, the entire string specified on the right-hand side of the equals sign will replace the alias.
> If an alias is typed within quotes, however, it will _not_ be replaced.

Examples:
* `add-alias 'add-dl'='add deadline'`  <br>
The command input `add-dl 'Clean the garage' by 17:00 on 04-05-14` can now be used in place of `add deadline 'Clean the garage' by 17:00 on 04-05-1`. However, note that `add deadline 'buy add-dl a cake' by 4:00pm on 12-Oct-16` does not register as `add deadline 'buy add deadline a cake' by 4:00pm on 12-Oct-16`, since `add-dl` was enclosed by quotation marks.


<br>
#### 2.12. List aliases: `list-alias`
Shows a numbered list of all configured aliases. <br>

Format: `list-alias`


<br>
#### 2.13. Delete alias(es): `delete-alias | remove-alias`
Removes previously set aliases. <br>

Format: `(delete-alias | remove-alias) INDEX [MORE_INDICES] ...`

Example:
* `list-alias`
* `remove-alias 2 3` <br>
  Deletes the second and third aliases given by the `list-alias` command.


<br>
#### 2.14. Exit Amethyst: `exit`
Exits the program. <br>

Format: `exit`

<br>
## 3. Command Summary
  
|Command             |Format           |
|:-------------------|:-----------------|
|add event           |`add event 'NAME' from hh:mm to hh:mm on dd-mm-yy`|
|add deadline        |`add deadline 'NAME' by hh:mm dd-mm-yy`|
|add task to be done someday         |`add someday 'NAME'`|
|list                |`list [dd-mm-yy] [TASK_TYPE] [done|not-done] [hh:mm]`|
|find                |`find KEYPHRASE_WORD_ONE KEYPHRASE_WORD_TWO [KEYPHRASE_MORE_WORDS] ..., [MORE_KEYPHRASES] ...`|
|delete              |`del INDEX [MORE_INDICES] ...`|
|update              |`edit INDEX ['NEW_NAME'] [from hh:mm to hh:mm | by hh:mm] [dd-mm-yy] [done|not-done]` |
|mark done           |`done INDEX [MORE_INDICES] ...`|
|undo                |`undo` |
|clear               |`clear`|
|set storage location|`set-storage FILEPATH`|
|add command alias   |`add-alias 'COMMAND_ALIAS'='COMMAND_PHRASE'`|
|list command aliases|`list-alias`|
|delete command alias|`(delete-alias | remove-alias) INDEX [MORE_INDICES] ...`|
|help                |`help`|
|exit                |`exit`|
