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
> **Command:** `add s/8th Nov at 4pm e/8th Nov at 6pm n/cs2103 lecture `<br>
> **Result:**<br>
- Result display panel posts message:<br>
`New event added: cs2103 lecture Event Date: 08.11.2016-16 to 08.11.2016-18`
- TaskList panel navigates to and displays newly added task card.

### 1.4 Add a recurring deadline task
> **Command:** `add n/biz assignment d/7.11.2016 r/weekly`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`New task added: biz assignment Deadline: 07.11.2016 recurring weekly`
- TaskList panel navigates to and displays newly added task card.

### 1.5 Add a recurring event
> **Command:** `add n/Yoga lesson s/7.11.2016-14 e/7th Nov 2016-16 r/daily`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`New event added: Yoga lesson Event Date: 07.11.2016-14 to 07.11.2016 recurring daily`
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
- Result display panel posts message:<br>
`Please select the item identified by the index number.`<br>
`Parameters: INDEX(must be a positive integer)`<br>
`Example: delete 1`<br>
- TaskList panel shows all tasks and events with name matching at least one input parameter.<br>

------
## 3. Done Command
------
### 3.1 Mark task or event as done by index
> **Command:** `done 3`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Marked as done: women day speech Event Date: 08.03.2016-19 to 08.03.2016-21 done`
- TaskList panel reflects the "done" status of the task or event.

### 3.2 Mark task or event as done by name
> **Command:** `done movie`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Please select the item identified by the index number.`<br>
`Parameters: INDEX(must be a positive integer)`<br>
`Example: done 1`<br>
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
- Result display panel posts message:<br>
`Listed all tasks`
- TaskList panel lists all tasks.
- Filter panel highlights the **Task** button

### 4.3 List all events
> **Command:** `list events`<br>
>**Result:**<br>
- Result display panel posts message:<br>
`Listed all events`
- TaskList panel lists all events
- Filter panel highlights the **Events** button

### 4.4 List all done tasks and events
> **Command:** `list done`<br>
>**Result:**<br>
- Result display panel posts message:<br>
`Listed all done tasks or events`
-TaskList panel lists all tasks and events
- Filter panel highlights the **Done** button

### 4.5 List all undone tasks and events
> **Command** `list undone`<br>
>**Result:**<br>
- Result display panel posts message:<br>
`Listed all undone tasks or events`
- TaskList panel lists all tasks and events
- Filter panel highlights the **Undone** button

------
## 5. Refresh Command
------
### 5.1 Refresh all outdated recurring tasks
> **Command:** `refresh` <br>
> **Result:**
- Result display panel posts message:<br>
`Refreshed all tasks and events`
- TaskList panel lists all tasks and events.
> Recurring tasks or events are updated to the next upcoming date of occurrence<br>

<!---@@author A0138717X-->
------
## 6. Edit Command
------
### 6.1 Edit name of task
> **Command:** `edit tomb sweeping n/do housework`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`This task has been edited: do housework Deadline: 05.03.2016 done`
- TaskList panel updates and shows the the last shown list with the updated field.

### 6.2 Edit time of task
> **Command:** `edit watch an old movie d/next monday 11pm`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`This task has been edited: watch an old movie Deadline: 14.11.2016-23`
- TaskList panel shows the newly edited task card with task date changed.
- Note: Similarly:
- 1. convert the original floating task to a time slot,
- 2. or convert the deadline to a time slot, and vice versa,
- are also supported.

### 6.3 Edit recurring frequency of task
> **Command:** `edit learn korea r/daily`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`This task has been edited: learn korea Deadline: 02.03.2016 recurring daily`
- TaskList panel shows the newly edited task card with recurring frequency changed.

#### 6.4 Edit the priority level of task
> **Command:** `edit watch an old movie p/2`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`This task has been edited: watch an old movie Deadline: 14.11.2016-23 Priority Level: 2`
- TaskList panel shows the newly edited task card with priority level changed.

<!--@@author A0146123R-->
------
## 7. Clear Command
------
### 7.1 Delete all data
> **Command:** `clear`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`To-do List has been cleared!`
- TaskList panel removes all tasks.

------
## 8. Undo/Redo Command
------
### 8.1 Undo/Redo clear command
> **Command:** <br>
1. `undo`<br>
2. `redo`<br>
3. `undo`<br>
> **Result:**<br>
> 1.
- Result display panel posts message:<br>
`Undid the most recent action.`
- TaskList panel displays the list of tasks before executre clear command.

> 2.
- Result display panel posts message:<br>
`Redid the most recent action that is undone.`<br>
`To-do List has been cleared!`
- TaskList panel removes all tasks.

> 3.
The result is the same as `1`

### 8.2 Undo/Redo add and delete command
> **Command:** <br>
> 1. `add n/cs2103 testing d/tonight p/3`<br>
> 2. `delete cs2103` <br>
> 3. `delete 1`<br>
> 4. `undo`<br>
> 5. `undo`<br>
> 6. `redo`<br>
> 7. `redo`<br>

> **Result:**<br>
> 1 and 2: normal add task and delete event results<br>
> 3. 
- Reseult display panel posts message:<br>
`Deleted Event: cs2103 lecture Event Date: 11.11.2016 to 11.11.2016 recurring weekly Priority Level: 1`
- TaskList panel displays 2 task cards, without the task card `cs2103 lecture 11.11.2016 to 11.11.2016`

> 4.
- Result display panel posts message:<br>
`Undid the most recent action.`
- TaskList panel displays 3 task cards, including the task card `cs2103 lecture 11.11.2016 to 11.11.2016`

> 5.
- Result display panel posts message:<br>
`Undid the most recent action.`
- TaskList panel displays 2 task cards, without the task card `cs2103 testing`
 
> 6.
- Result display panel posts message:<br>
`Redid the most recent action that is undone.`<br>
`New task added: cs2103 testing Deadline: 07.11.2016-20 Priority Level: 3`
- TaskList panel displays 3 task cards, including the task card `cs2103 testing`

> 7.
- Result display panel posts message:<br>
`Redid the most recent action that is undone.`<br>
`Deleted Task: cs2103 testing Deadline: 07.11.2016-20 Priority Level: 3`
- TaskList panel displays 2 task cards, without the task card `cs2103 lecture 11.11.2016 to 11.11.2016`

------
## 9. Find Command
------
### 9.1 Find by name
> **Command:** `find project meet`<br>
> **Result:**
- Result display panel posts message:<br>
`10 events and tasks listed!`
- TaskList panel lists all events and tasks whose name contains project, meet or meeting (whose stem word is meet)<br>

### 9.2 Find by name and `AND` parameter
> **Command:** `find project AND meet`<br>
> **Result:**
- Result display panel posts message:<br>
`2 events and tasks listed!`
- TaskList panel lists all events and tasks whose name contains project and meet or meeting (whose stem word is meet)<br>

### 9.3 Find by name and `exact!` parameter
> **Command:** `find meet exact!`<br>
> **Result:**
- Result display panel posts message:<br>
`0 events and tasks listed!`
- TaskList panel lists all events and tasks whose name contains the exact form of `meet`<br>

------
## 10. Filter Command
------
### 10.1 Filter in filter panel
> **Command:** 
1. `s`<br>
2. `22.04.2016`
> **Result:**
> 1.
- Result display panel post message:<br>
`Enter start date:`
- The start date text field in filter panel is focused
> 2.
- Result display panel posts message:<br>
`Filter the todoList`
- TaskList panel shows one event `earth day cca event` whose start day is on 22.04.2016
- Enter `d`, `s`, `e`, `r`, `t` and `p` to jump to deadline, start date, end date, recurring, tag and priority text fields respectively,

### 10.2 Filter by deadline and recurring
> **Command:** `filter d/Nov 11 2016 r/weekly`<br>
> **Result:**
- Result display panel posts message:<br>
`2 events and tasks listed!`
- TaskList panel lists all events and tasks whose deadline is on 11.11.2016 and recurring frequency is weekly
- The deadline text fiedd in filter panel shows `11.11.2016` 

### 10.3 Filter by start date and end date
> **Command:** `filter s/08.11.2016 e/08.11.2016`<br>
> **Result:**
- Result display panel posts message:<br>
`1 events and tasks listed!`
- TaskList panel lists all events whose start date and end date are on 08.11.2016
- The start date and end date text fields in filter panel shows `08.11.2016`

### 10.4 Filter by priority and tag
> **Command:** `filter p/1 t/academic`<br>
> **Result:**
- Result display panel posts message:<br>
`1 events and tasks listed!`
- TaskList panel lists all events whose priority is 1 and has tag academic
- The priority choice box in filter panel showss `1` and the tag text field shows `academic`

------
## 11. Change Directory Command
------
### 11.1 Changes the default storage location of the app
> **Command:** `change data/newFile.xml`<br>
> **Result:**
- Result display panel posts message:<br>
`Storage location changed: data/newFile.xml`
- Status Foot Bar updates to show the new location.
- Open the data folder and the `sampleData.xml` and `newFile.xml` should be there.
- Reboot the app, it will load the file under the new path.
- Note: If key in invalid path, result display will post `The file path provided is invalid. It must end with the file type extension, .xml`.

### 11.2 Changes the default storage location of the app and deletes the previous storage file
> **Command:** `change data/toDoList.xml clear`<br>
> **Result:**
- Result display panel posts message:<br>
`Storage location changed: data/toDoList.xml`
- Status Foot Bar updates to show the new location.
- Open the folder and `toDoList.xml` should be there. `newFile.xml` should not be there.

------
## 12. Undo/Redo change Directory Command
------
### 12.1 Undo changes the default storage location of the app
> **Command:** `undochange`<br>
> **Result:**
- Result display panel posts message:<br>
`Storage location changed back.`
- Status Foot Bar updates to show `newFile.xml`
- Open the folder and the `toDoList.xml` and `newFile.xml` should be there.

### 12.2 Undo changes the default storage location of the app and deletes the new storage file
> **Command:** `undochange clear`<br>
> **Result:**
- Result display panel posts message:<br>
`Storage location changed back.`
- Status Foot Bar updates to show the `sampleData.xml`
- Open the folder and `sampleData.xml` should be there and `newFile.xml` should not be there.

### 12.3 Redo change the default storage location of the app
> **Command:** 1. `redochange` 2. `redochange` (enter twice)
> **Result:**
- Result display panel posts message:<br>
`Storage location changed.`
- Status Foot Bar updates to show the `toDoList.xml`
- Open the folder and `toDoList.xml` should be there. `newFile.xml` should not be there.
- Reboot the app, it will load the file under the new path.

------
## 13. Exit Command
------
### 14.1 Exit the app
> **Command:** `exit`<br>
> **Result:**<br>
- toDoList closes and quits.

------
## End
------ 
