# These are the instructions for testing the IvoryTasks application.

1. Download the latest `IvoryTasks.jar` from the [releases](../../../releases) tab.<br>

2. Copy the file to the folder you want to use as the home folder for your task manager.<br>

3. Load the sample data by editing the config.json file.

  > ### Change "taskManagerFilePath" : "src/test/data/ManualTesting/SampleData.xml"

4. Double-click the file to start the app. The GUI should appear in a few seconds. There should be about 60 items uploaded into the task manager. These items have various parameters optimized for testing various conditions.

# Run the following commands

Note:
> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * `/` should only be used in user input after a parameter indicator (e.g. `n/`). Usage anywhere else may lead to unexpected results.
> * The order of parameters is fixed unless otherwise specified.

# Command Summary - Try these commands - (Commands elaborated below Command Summary)

Command | Format  | Example | Expected Respose
-----: | ----- | :------------------
Add Task | `a[dd] t[ask] n/NAME [#TAG_TO_ADD]` | `add task tutorial` | The task will be added to your to do list and message "Added task" is displayed on console.
Add Deadline | `a[dd] d[eadline] n/NAME ed/DATE et/TIME` or `a[dd] d[eadline] [n/]NAME edt/DATE_TIME_TEXT [#TAG_TO_ADD]` | `add deadline reach v1.0 edt/thursday 2pm` |  The task will be added to your to do list and message "Added task" is displayed on console.
Add Event | `a[dd] e[vent] [n/]NAME sd/START_DATE st/START_TIME  ed/END_DATE et/END_TIME [#TAG_TO_ADD]` or `a[dd] e[vent] [n/]NAME sdt/START_DATE_TIME_TEXT edt/END_DATE_TIME_TEXT [#TAG_TO_ADD]` | `add event 2103 guest lecture sdt/friday 2pm edt/friday 4pm` | The event will be added to your to do list and message "Added event" is displayed on console
Delete | `del[ete] INDEX ...` | `delete 1` | Console displays deleted item in second box and refreshes list of items.
Done | `d[one] INDEX` | `done 1` | Console refreshes list of items, with updated status for the updated item.
Edit Task | `e[dit] INDEX [n/NEW_NAME] [edt/NEW_END_DATE_TIME] [ed/NEW_END_DATE et/NEW_END_TIME] [#TAG_TO_ADD] [#-TAG_TO_DELETE]` | `edit 1 n/CS2103` | App will display edited item's details in the results panel.
Edit Deadline | `e[dit] INDEX [n/NEW_NAME] [edt/NEW_END_DATE_TIME] [ed/NEW_END_DATE] [et/NEW_END_TIME] [#TAG_TO_ADD] [#-TAG_TO_DELETE]` | `edit 6 edt/tomorrow 6pm` | App will display edited item's details in the results panel. |
Edit Event | `e[dit] INDEX [n/NEW_NAME] [sdt/NEW_START_DATE_TIME] [sd/NEW_START_DATE] [st/NEW_START_TIME] [edt/NEW_END_DATE_TIME] [ed/NEW_END_DATE] [et/NEW_END_TIME] [#TAG_TO_ADD] [#-TAG_TO_DELETE]` | `edit 11 sdt/yesterday` | App will display edited item's details in the results panel.
Find | `f[ind] KEYWORD [MORE_KEYWORDS]` | `find tutorial` | App will display a list of items with names containing the keyword in the bottom left panel.
Help | `h[elp]` | `help` | The list of commands, their format and their function will be shown.
List Tasks | `l[ist]t[ask]` |`lt` | Console refreshes with all tasks displayed
List Deadlines | `l[ist]d[eadline]` | `ld` | Console refreshes with all deadlines displayed
List Events | `l[ist]e[vent]` | `le` | Console refreshes with all events displayed
List all uncompleted items | `l[ist]n[ot]d[one]` | `lnd` | Console refreshes with all uncompleted items displayed
List all items | `l[ist]` | `list` | Console refreshes with all items displayed
Notdone | `n[ot]d[one] INDEX` | `nd 1` | Console refreshes with all not done items displayed
Undo | `u[ndo]` | `undo` | Console shows action undone and refreshes
Redo | `r[edo]` | `redo` | Console shows action redone and refreshes
Specify custom save location | `save VALID_FILE_PATH_NAME` | `save Users/Jim/data.xml` | File save location is refreshed at the bottom of the application
Select | `s[elect] INDEX` | `select 1` | Console shows selected item
Clear | `cl[ear]` | `clear` | Application is cleared of data

______________________________________________________

# Commands Elaborated

### When you need help (To see a list of all commands)

1. Type ‘h[elp]’ and press `Enter`.

2. The list of commands, their format and their function will be shown.

### When you have a new deadline, task or event

#### Add a deadline

1. Type `a[dd] d[eadline] [n/]NAME ed/DATE et/TIME` or `a[dd] d[eadline] n/NAME edt/DATE_TIME_TEXT(e.g. next wed 3pm)`.

2. `add` can be replaced by `a`. `deadline` can be replaced by `d`.

3. If et is not specified, et is assumed to be 23:59. Press `Enter`.

4. The deadline will be added to your to do list and message "Added deadline" is displayed on console.

#### Add a task

1. Type `a[dd] t[ask] [n/]NAME`.

2. `add` can be replaced by `a`. `task` can be replaced by `t`. Press `Enter`.

3. The task will be added to your to do list and message "Added task" is displayed on console.

#### Add an event

1. Type `a[dd] e[vent] [n/]NAME sd/START_DATE st/START_TIME ed/END_DATE et/END_TIME` or `a[dd] e[vent] [n/]NAME sdt/START_DATE_TIME_TEXT(e.g. two hours later) edt/END_DATE_TIME_TEXT(e.g. next wed 3pm)`.

2. `add` can be replaced by `a`. `event` can be replaced by `e`.

3. If st is empty, st is assumed to be 00:00.

4. If et is empty, et is assumed to be 23:59. Press `Enter`.

5. The event will be added to your to do list and message "Added event" is displayed on console.

### When you need to find a deadline, task or event

1. Type `f[ind] KEYWORD` where `KEYWORD` is part of the item's name and press `Enter`.

2. App will display a list of items with names containing the keyword in the bottom left panel.

### When you need to edit a deadline, task or event

* At least one optional parameter must be specified.
* All optional parameters can be in any order.
* sdt/edt supports Natural Language Input. (Note: sdt/edt will be favoured if sdt/edt and sd/ed or st/et are entered together)
* Editing tags: `#` prefix to add a tag, `#-` to delete an existing tag. (Note: You cannot add a duplicate tag or delete a non-existent tag.)

#### Edit a task’s name
For tasks, you can only edit the name, its tags and the end date and time. Note: editing the end date and time automatically converts the task into a deadline.

##### If you know a keyword in the task's name

1. Type `f[ind] KEYWORD`. Press `Enter`.

2. Type `e[dit] INDEX [n/NEW_NAME] [edt/NEW_END_DATE_TIME] [ed/NEW_END_DATE et/NEW_END_TIME] [#TAG_TO_ADD] [#-TAG_TO_DELETE]`. Press `Enter`.

3. App will display edited item's details in the results panel (below the command box).

##### If you know the index of the task in the displayed list

1. Type `e[dit] INDEX [n/NEW_NAME] [edt/NEW_END_DATE_TIME] [ed/NEW_END_DATE et/NEW_END_TIME] [#TAG_TO_ADD] [#-TAG_TO_DELETE]`. Press `Enter`.

2. App will display edited item's details in the results panel.

#### Edit a deadline's name, end date and end time
For deadlines, you can only edit the name, end date and time and its tags.

##### If you know the keyword of the deadline

1. Type `f[ind] KEYWORD`. Press `Enter`.

2. Type `e[dit] INDEX [n/NEW_NAME] [edt/NEW_END_DATE_TIME] [ed/NEW_END_DATE] [et/NEW_END_TIME] [#TAG_TO_ADD] [#-TAG_TO_DELETE]`. Press `Enter`.

3. App will display edited item's details in the results panel.

##### If you know the index of the deadline in the displayed list

1. Type `e[dit] INDEX [n/NEW_NAME] [edt/NEW_END_DATE_TIME] [ed/NEW_END_DATE] [et/NEW_END_TIME] [#TAG_TO_ADD] [#-TAG_TO_DELETE]`. Press `Enter`.

2. App will display edited item's details in the results panel.

#### Edit an event’s name, start date, start time, end date and end time

For events, you can edit all optional parameters.
(Note: editing the end datetime to be before the start datetime is an illegal operation and is not allowed)

##### If you know the keyword of the event

1. Type `f[ind] KEYWORD`. Press `Enter`.

2. Type `e[dit] INDEX [n/NEW_NAME] [sdt/NEW_START_DATE_TIME] [sd/NEW_START_DATE] [st/NEW_START_TIME] [edt/NEW_END_DATE_TIME]  [ed/NEW_END_DATE] [et/NEW_END_TIME] [#TAG_TO_ADD] [#-TAG_TO_DELETE]`. Press `Enter`.

3. App will display edited item's details in the results panel.

##### If you know the index of the event in the displayed list

1. Type `e[dit] INDEX [n/NEW_NAME] [sdt/NEW_START_DATE_TIME] [sd/NEW_START_DATE] [st/NEW_START_TIME] [edt/NEW_END_DATE_TIME] [ed/NEW_END_DATE] [et/NEW_END_TIME] [#TAG_TO_ADD] [#-TAG_TO_DELETE]`. Press `Enter`.

2. App will display edited item's details in the results panel.

### When you need to view the details of your deadlines, tasks and events

1. Type `s[elect] INDEX` and press `Enter`. Alternatively, click on the item in the list in the bottom left panel.

2. App will display the item's details in the panel on the right.

### When you need to view your deadlines, tasks and events

#### View all deadlines, tasks and events

1. Type `l[ist]`. Press `Enter`.

2. Console refreshes with all deadlines, tasks, and events displayed.

#### View all uncompleted deadlines, tasks and events

1. Type `l[ist]n[ot]d[one]`. Press `Enter`.

2. App will display all uncompleted items in the bottom left panel.

#### View all tasks

1. Type `l[ist]t[ask]`. Press `Enter`.

2. Console refreshes with all tasks displayed.

#### View all deadlines

1. Type `l[ist]d[eadline]`. Press `Enter`.

2. Console refreshes with all deadlines displayed.

#### View all events

1. Type `l[ist]e[vent]`. Press `Enter`.

2. Console refreshes with all events displayed.

### When you want to delete a deadline, task or event
* The index(es) specified refers to the index number(s) shown in the most recent listing.
* The index(es) must be positive integers 1, 2, 3...
* This action is irreversible.

#### Delete one deadline, task or event

1. Type `del[ete] INDEX`. Press `Enter`.

2. Console displays deleted item in second box and refreshes list of items.

#### Delete multiple deadlines, tasks or events

1. Type `del[ete] INDEX ...`. Press `Enter`.

2. Console displays deleted item in second box and refreshes list of items.

### When you are done with a deadline, task or event

1. Type `d[one] INDEX`. Press `Enter`.

2. Console refreshes list of items, with updated status for the updated item. You can also use your mouse to click on done alternatively.

### When you are not done with a deadline, task or event

1. Type `n[ot]d[one] INDEX`. Press `Enter`.

2. Console refreshes list of items, with updated status for the updated item. You can also use your mouse to click on not done alternatively.

### When you want to undo your last action that caused a changed in your todo list

1. Type `u[ndo]`. Press `Enter`. <br>

2. Bottom left panel displays items as per previous state.

### When you want to redo your last undone action

1. Type `r[edo]`. Press `Enter`. <br>

2. App's bottom left panel reverts items to before the latest undo action.

### When you want to specify a custom save location for your data

> * File to save in is limited to .xml format.

1. Type `save VALID_FILE_PATH`.
2. Press `Enter`.
