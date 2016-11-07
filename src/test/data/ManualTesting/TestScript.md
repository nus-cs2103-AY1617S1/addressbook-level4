------
# Test Script
------
> **Note**
- Dates and times depend on current system time and will differ depending on when the testing is being carried out. <br>

------
## Load Sample Data
------
> **Steps:**
- 1. Copy `SampleData.xml` into the same folder as `FlexiTrack.jar`
- 2. Run `FlexiTrack.jar`

------
## 0. Help Command
------
### 0.1 Overall help
> **Command:** `help` <br>
> **Result:**
- Result display panel posts message: <br>
`help: Shows program usage instructions.` <br>
`List of Commands: add, clear, delete, edit, exit, find, list, mark, unmark, block, cs(Change Storage Path), undo, redo, gap `<br>
`Example: help clear`

### 0.2 Specific command help
> **Command:** `help edit` <br>
> **Result:**
- Result display panel posts message: <br>
`edit, Shortcut [e]: Edits the specified task attributes of the task identified by the index number used in the last task listing.`<br>
`Parameters to edit an event: [index] (must be a posi<br>tive integer) from/ [starting time] to/ [ending time]`
`Example: edit 1 from/ 01062016 to/ 01/072016`<br>
`Parameters to edit a task: [index] (must be a positive integer) by/ [due date]`<br>
`Example: edit 1 by/ 01062016`

------
## 1. Add Command
------
### 1.1 Add a floating task
> **Command:** `add Floating Task` <br>
> **Result:** <br>
- Result display panel posts message: <br>
`New task added: Floating Task`<br>
- TaskList panel displays newly added card which is white in color.

### 1.2 Add a task
> **Command:** `add Task by/Sunday 9am`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`New task added: Task by/Nov 13 2016 09:00`
- TaskList panel displays newly added card which is orange in color.

### 1.3 Add an event
> **Command:** `add Event from/ Sunday to/ Monday 10am`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`New task added: Event from/Nov 13 2016 08:00 to/Nov 14 2016 10:00`
`Duration of the event is: 1 day 2 hours.`
- TaskList panel displays newly added card which is brown in color.

### 1.4 Add a recurring task
> **Command:** `add Recurring Task fr/3 ty/week by/tuesday`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`New task added: Recurring Task by/Nov 08 2016 08:00`. (Deadline of first added task)
- TaskList panel displays 3 newly added cards which are orange in color.

### 1.5 Add a recurring event
> **Command:** `add Recurring Event fr/2 ty/day from/tomorrow 8am to/ tomorrow 9am`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`New task added: Recurring Event from/Nov 08 2016 08:00 to/Nov 08 2016 09:00` <br>
`Duration of the event is: 1 hour.` (Info of the first added event) <br>
- TaskList panel displays 2 newly added cards which are brown in color.



------
## 2. Delete Command
------
### 2.1 Delete task by index
> **Command:** `delete 1`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Deleted Task: visit grandma Deadline: 20.11.2016 done recurring daily`
- TaskList panel removes all occurrences of the task. 

### 2.2 Delete task by name
> **Command:** `delete visit`<br>
> **Result:**<br>
`Please select the item identified by the index number.\n
Parameters: INDEX(must be a positive integer)\n
Example: delete 1`
-Result display panel posts all tasks and events with name matching at least one input parameter.<br>

------
## 3. Done Command
------
### 3.1 Mark task or event as done by index
> **Command:** `done 4`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Marked as done: women day speech Event Date: 08.03.2016-19 to 08.03.2016-21 done`
- TaskList panel reflects the "done" status of the task or event. 

### 3.2 Mark task or event as done by name
> **Command:** `done movie`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Please select the item identified by the index number.\n
Parameters: INDEX(must be a positive integer)\n
Example: done1`
- TaskList panel posts all tasks and events with name matching at least one input parameter.<br> 

------
## 4. List Command
------
### 4.1 List all tasks and events
> **Command:** `list`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Listed all tasks and events`
- TaskList panel lists all tasks and events.

### 4.2 List all tasks
> **Command:** `list tasks`<br>
> **Result:**<br>
-Result display panel posts message:<br>
`Listed all tasks`
- TaskList panel lists all tasks.
- Filter panel highlights the **Task** button

### 4.3 List all events
> **Command:** `list events`<br>
>**Result:**<br>
-Result display panel posts message:<br>
`Listed all events`
- TaskList panel lists all events
- Filter panel highlights the **Events** button

### 4.4 List all done tasks and events
> **Command:** `list done`<br>
>**Result:**<br>
-Result display panel posts message:<br>
`Listed all done tasks or events`
-TaskList panel lists all tasks and events
- Filter panel highlights the **Done** button

### 4.5 List all undone tasks and events
> **Command** `list undone`<br>
>**Result:**<br>
-Result display panel posts message:<br>
`Listed all undone tasks or events`
-TaskList panel lists all tasks and events
- Filter panel highlights the **Undone** button

------
## 5. Find Command
------
### 5.1 Find by name
> **Command:** `find visit`<br>
> **Result:**
- Result display panel posts message:<br>
`3 events and tasks listed!`
- TaskList panel lists all tasks whose name contains visit.

------
## 6. Refresh Command
### 6.1 Refresh all outdated recurring tasks
> **Command:** `refresh` <br>
> **Result:**
-Result display panel posts message:<br>
`Refreshed all tasks and events`
-TaskList panel lists all tasks and events. 
> Recurring tasks or events are updated to the next upcoming date of occurrence<br>

<!--@@author-->

------
## 7. Edit Command
------
### 6.1 Edit name of task
> **Before:** `find a song`<br>
> **Command:** `edit 1 read Harry Potter`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Edit Task: read Harry Potter Tags: [reading]
\nRecurring: NONE`
- Note: TaskList panel updates and shows nothing, because current filter is still `find a song`.
- To see the edited one, type eg. `find harry`.


### 6.2 Edit time of task
#### 6.2.1 Edit a floating task to a deadline
> **Command:** `edit 1 by next monday 11pm`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Edit Task: read Harry Potter Tags: [reading]
\nRecurring: NONE
\nBy: [Formatted date of next monday] 11.00PM`
- TaskList panel navigates to newly edited task card with task date changed.
- Task card background changes from yellow to red to indicate type change.
- Note: Similarly: 
- 1. convert the original floating task to a time slot, 
- 2. or convert the deadline to a time slot, and vice versa,
- are also supported.

#### 6.2.2 Edit the time slot for a recurring task with recurring period specified
> **Before:** `find civ`<br>
> **Command:** `edit 7 from 9 nov 10pm to 10.30pm`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Edit Task: read civ encyclopedia Tags: 
\nRecurring: DAILY repeat 9 times
\nFrom: Wed, Nov 9 10.00PM To: Wed, Nov 9 10.30PM`
- TaskList panel navigates to newly edited task card with task date changed.
- Agenda panel displays the newly edited task in new position.
- Note: Only the selected instance of the recurring task will be affected.

#### 6.2.3 Edit the time slot for a recurring task without recurring period specified
> **Before:** `find jogging`<br>
> **Command:** `edit 2 from tomorrow 9.30pm to 10pm`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Edit Task: jogging Tags: 
\nRecurring: DAILY always
\nFrom: [Formatted date of tomorrow] 09.30PM To: [Formatted date of tomorrow] 10.00PM`
- TaskList panel navigates to newly edited task card with task date changed.
- Agenda panel displays the newly edited task in new position.
- Note: All instances of the recurring task will be affected.

### 6.3 Edit recurring type of task
> **Command:** `edit 2 none`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Edit Task: jogging Tags: 
\nRecurring: NONE
\nFrom: [Formatted date of tomorrow] 09.30PM To: [Formatted date of tomorrow] 10.00PM`
- TaskList panel navigates to newly edited task card with task recurring type changed. 
- Agenda panel updates that no instances of the old daily task displayed.
- The archived instance will not be affected.

### 6.4 Edit tags of task
> **Before:** `find cs2103t` <br>
> **Command:** `edit 2 t/urgent t/assignment`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Edit Task: cs2103t demo Tags: [assignment][urgent]
\nRecurring: NONE
\nFrom: Wed, Nov 9 09.00AM To: Wed, Nov 9 10.00AM`
- TaskList panel navigates to newly edited task card with task tag changed.


### 6.5 Edit multiple attributes
> **Before:** `find blocked`<br>
> **Command:** `edit 2 urgent meeting from tomorrow 5.30pm to 6.30pm`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Edit Task: urgent meeting Tags: [meeting]
\nRecurring: NONE
\nFrom: Tue, Nov 8 05.30PM To: Tue, Nov 8 06.30PM` 
- Agenda panel updates to display the new slot.
- Background changes from brown to blue to indicate type change.
- TaskList panel will only show one other blocked slot, because current filter is still `find blocked`.
- To see the edited copy, type eg. `find urgent meeting`.

------
## 8. Undo/Redo Command
------
### 9.1 Undo/Redo commands that modifies data
> **Command:** <br>
1. `add play CS from 2am to 3am`<br>
2. `u`<br>
3. `r`<br>
> **Result:**<br>
> 1.
- Result display panel posts message:<br>
`New non-floating task added: play CS Tags: 
\nRecurring: NONE`
- TaskList panel navigates to and displays the newly added task.
- Agenda panel displays the newly added task.

> 2.
- Result display panel posts message:<br>
`Undo successfully`
- TaskList panel removes the newly added task.
- Agenda panel removes the newly added task.

> 3.
- Result display panel posts message:<br>
`New non-floating task added: play CS Tags: 
\nRecurring: NONE`
- TaskList panel navigates to and displays the newly added task.
- Agenda panel displays the newly added task.

### 9.2 Undo/Redo commands that mutates list/agenda view
#### 9.2.1 Undo/Redo commands that mutates list view
> **Command:** <br>
1. `find -C`<br>
2. `u`<br>
3. `r`<br>
> **Result:**<br>
> 1.
- Result display panel posts message:<br>
`12 tasks listed!`
- TaskList panel displays all archived tasks.

> 2.
- Result display panel posts message:<br>
`Undo successfully`
- TaskList panel resets to previous display.

> 3.
- Result display panel posts message:<br>
`12 tasks listed!`
- TaskList panel displays all archived tasks.

#### 9.2.2 Undo/Redo commands that mutates agenda view
> **Command:** <br>
1. `view 15 nov`<br>
2. `u`<br>
3. `r`<br>
> **Result:**<br>
> 1.
- Result display panel posts message:<br>
`Agenda Updated to Week specified by: [Formatted date of 15 nov depends on system language]`
- TaskList panel lists all deadline tasks that due on 15 nov.
- Agenda updates to the week of 15 nov.

> 2.
- Result display panel posts message:<br>
`Undo successfully.`
- TaskList panel resets to previous display.
- Agenda panel resets to previous display.

> 3.
- Result display panel posts message:<br>
`Agenda Updated to Week specified by: [Formatted date of 15 nov depends on system language]`
- TaskList panel lists all deadline tasks that due on 15 nov.
- Agenda updates to the week of 15 nov.

###9.3 Undo reaches maximum times
> **Command:** <br>
1. Enter `u` 3 times.<br>
2. Enter `u`<br>
> **Result:**
- Result display panel posts warning message:<br>
`No command to undo.`
- Text stays, command box background turns orange to show warning.
- Note: Same case for Redo.

------
## 9. Select Command
------
### 10.1 Select a task
> **Command:** `select (index)`<br>
- index: Any number within range of tasklist.<br>

> **Result:**<br>
- Result display panel posts message, which is the detailed information of the task.
- TaskList panel navigates to and focuses the task card.
- Note: If key in invalid index number, result display will post `The task index provided is invalid`.

------
## 10. Change Directory Command
------
### 11.1 Changes the default directory of the app
> **Command:** `cd filepath`<br>
- filepath: use `newfile.xml` as example.<br>

> **Result:**
- Result display panel posts message:<br>
`Alert: This operation is irreversible.
\nFile path successfully changed to : newfile.xml`
- Status Foot Bar updates to show the new location.
- Open the folder and the exported file should be there.
- Reboot the app, it will load the file under the new path.
- Note: If key in invalid path, result display will post `Wrong file type/Invalid file path detected.`.

------
## 12. Navigation Bar Utilities
------
### 12.1 Click Today
> **Action:** click `Today`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`[number of tasks] tasks listed!`
- TaskList panel lists all deadlines due today.
- Agenda panel shows all tasks of this week.

### 12.2 Click Tasks
> **Action:** click `Tasks`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Listed all tasks`
- TaskList panel lists all active tasks.

### 12.3 Click Deadlines
> **Action:** click `Deadlines`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`[number of tasks] tasks listed!`
- TaskList panel lists all deadlines due before and by today.

### 12.4 Click Floating
> **Action:** click `Floating`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`[number of tasks] tasks listed!`
- TaskList panel lists all floating tasks.

### 12.5 Click Incoming Deadlines
> **Action:** click `Incoming Deadlines`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`[number of tasks] tasks listed!`
- TaskList panel lists all deadlines due before and by next week today.

### 12.6 Click Completed
> **Action:** click `Completed`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`[number of tasks] tasks listed!`
- TaskList panel lists all archived tasks.

------
## 13. Clear Command
------
### 13.1 Delete all data
> **Command:** `clear`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Task list has been cleared!`
- TaskList panel removes all tasks.
- Agenda panel removes all tasks.

------
## 14. Exit Command
------
### 14.1 Exit the app
> **Command:** `exit`<br>
> **Result:**<br>
- HappyJimTaskMaster closes and quits.

------
## End
------ 