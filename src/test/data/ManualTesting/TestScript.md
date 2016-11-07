<!---@@author A0142325R-->
------
# Testscript
------

> Note: 
- 1. The actual date and time displayed by the app depends on the day the test is performed.
- 2. Please follow the given order to perform the tests to ensure a less tedious experience.

------
## Before: Load SampleData
------
> **Instructions:**
- 1. Create a folder called `data` at the same directory as `toDoList.jar` executable.
- 2. Copy and paste `SampleData.xml` into that folder.
- 3. Run `toDoList.jar`.

------
## 0. Help Command
------
### 0.1 Open help window
> **Command:** `help` <br>
> **Result:**
- Result display panel posts message: <br>
`Opened help window.`
- Help window pops up and shows user guide.

------
## 1. Add Command
------
### 1.1 Add a floating task
> **Command:** `add n/research project t/academic p/1` <br>
> **Result:** <br>
- Result display panel posts message: <br>
`New task added: research project Tags: [academic] Priority Level: 1`<br>
- TaskList panel navigates to and displays newly added task card.

### 1.2 Add a deadline task
> **Command:** `add n/report t/assignment d/7th Nov at 4pm`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`New task added: report Deadline: 07.11.2016-16 Tags: [assignment]`
- TaskList panel navigates to and displays newly added task card.

### 1.3 Add an event
> **Command:** `add n/cs2103 lecture s/8th Nov at 4pm e/8th Nov at 6pm`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`New event added: cs2103 lecture Event Date: 08.11.2016-16 to 08.11.2016-18`
- TaskList panel navigates to and displays newly added task card.

### 1.4 Add a recurring deadline task
> **Command:** `add n/biz assignment d/7.11.2016 r/weekly`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`New task added: biz assignment Deadline: 07.11.2016 recurring weekly`.
- TaskList panel navigates to and displays newly added task card.

### 1.5 Add a recurring event
> **Command:** `add n/Yoga lesson s/7.11.2016-14 e/7th Nov 2016-16 r/daily`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`New event added: Yoga lesson Event Date: 07.11.2016-14 to 07.11.2016 recurring daily`.
- TaskList panel navigates to and displays newly added task card. There are three instances in total.


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
