//@@author A0127855W
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
- 2. Rename `SampleData.xml` into `tasktracker.xml`
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
> **Command:** `h edit` <br>
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
`New task added: Task by/Nov 13 2016 09:00`<br>
- TaskList panel displays newly added card which is orange in color.

### 1.3 Add an event
> **Command:** `a Event from/ Sunday to/ Monday 10am`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`New task added: Event from/Nov 13 2016 08:00 to/Nov 14 2016 10:00`<br>
`Duration of the event is: 1 day 2 hours.`<br>
`Warning: this event is overlaping a existing event!`<br>
- TaskList panel displays newly added card which is brown in color.

### 1.4 Add a recurring task
> **Command:** `add Recurring Task fr/3 ty/week by/tuesday`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`New task added: Recurring Task by/Nov 08 2016 08:00`. (Deadline of first added task)<br>
- TaskList panel displays 3 newly added cards which are orange in color.

### 1.5 Add a recurring event
> **Command:** `a Recurring Event fr/2 ty/day from/tomorrow 8am to/ tomorrow 9am`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`New task added: Recurring Event from/Nov 08 2016 08:00 to/Nov 08 2016 09:00` <br>
`Duration of the event is: 1 hour.` (Info of the first added event) <br>
`Warning: this event is overlaping a existing event!` <br>
- TaskList panel displays 2 newly added cards which are brown in color.

------
## 2. Block Command
------
### 2.1 Block Time
> **Command:** `block Visit Gramdma?? from/christmas to/christmas`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Block the date for (Blocked) Visit Grandma?? from/Dec 25 2016 08:00 to/Dec 25 2016 17:00`<br>
`Duration of the event is: 9 hours.`<br>
- TaskList panel displays the newly added card which is brown in color and contains the prefix '(Blocked)'. 

### 2.asdfsadfasfasfs2 Add event over blocked time
> **Command:** `add Shopping from/christmas 10am to/christmas 12pm`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`This period of time has already taken by other event, Please choose another time.`

------
## 3. Gap Command
------
### 3.1 Find next available slot
> **Command:** `gap 2 hours n/1`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`The earliest 2 hours free time are found... ` <br>
`Between:  Nov 07 2016 21:00  to: Nov 08 2016 08:00`

### 3.2 Find next few available slots
> **Command:** `g 4 hours`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`The earliest 4 hours free time are found... `<br>
`Between:  Nov 07 2016 21:00  to: Nov 08 2016 08:00`<br>
`Between:  Nov 08 2016 11:00  to: Nov 09 2016 08:00`<br>
`Between:  Nov 14 2016 10:00  to: Nov 14 2016 19:00`

------
## 4. Delete Command
------
### 4.1 Delete by index
> **Command:** `delete 1`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Deleted task: Cook dinner for family` <br>
- TaskList panel updates without the deleted task. 

### 4.2 Delete index out of bounds
> **Command:** `delete 100`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`The task index provided is invalid `<br>

------
## 5. Edit Command
------
### 5.1 Edit name
> **Command:** `edit 1 n/Name Edited`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Edited: Cut hair into Cut hair @ beauty salon` <br>
- TaskList panel updates with the edited task card. 

### 5.2 Edit due date
> **Command:** `edit 9 by/ nov 9 11:59pm`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Edited: CN4122 Project into CN4122 Project` <br>
- TaskList panel updates with the edited task card. 

### 5.3 Edit start time
> **Command:** `edit 7 from/nov 6 11am`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Edited: Tuition @Serangoon into Tuition @Serangoon`<br>
`Duration of the event is: 1 hour.` <br>
- TaskList panel updates with the edited task card. 

### 5.4 Edit end time
> **Command:** `edit 7 to/nov 6 1pm`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Edited: Tuition @Serangoon into Tuition @Serangoon`<br>
`Duration of the event is: 2 hours.` <br>
- TaskList panel updates with the edited task card. 

### 5.5 Edit floating task into task
> **Command:** `edit 1 by/ next tuesday 4pm`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Edited: Cut hair @ beauty salon into Cut hair @ beauty salon` <br>
- TaskList panel updates with the edited task card which is now orange.  

### 5.6 Edit floating task into event
> **Command:** `edit 2 from/ tomorrow 5pm to/ tomorrow 6pm`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Edited: Run 5km into Run 5km` <br>
`Duration of the event is: 1 hour.` <br>
- TaskList panel updates with the edited task card which is now brown. 

### 5.7 Edit multiple parameters in any order (valid)
> **Command:** `edit 6 to/ nov 7 7pm from/ nov 7 5pm n/Chemistry Tuition @ Cantonment`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Edited: chemistry tuition into Chemistry Tuition @ Cantonment`
`Duration of the event is: 2 hours.` <br>
- TaskList panel updates with the edited task card. 

------
## 6. Undo Command
------
### 6.1 Undo
> **Command:** `undo`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Your last command has been undone!` <br>
`Undid edit: chemistry tuition into Chemistry Tuition @ Cantonment` <br>
- TaskList panel reverts to the state before part 5.7. 

### 6.2 Undo shortcut
> **Command:** `ud`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Your last command has been undone!`<br>
`Undid edit: Run 5km into Run 5km `<br>
- TaskList panel reverts to the state before part 5.6.

------
## 7. Redo Command
------
### 7.1 Redo
> **Command:** `redo`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Your last command has been redone!`<br>
`Edited: Run 5km into Run 5km`<br>
`Duration of the event is: 1 hour.` <br>
- TaskList panel reverts to the state after part 5.6.

### 7.2 Redo shortcut
> **Command:** `rd`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Your last command has been redone!`<br>
`Edited: chemistry tuition into Chemistry Tuition @ Cantonment`<br>
`Duration of the event is: 2 hours. `<br>
- TaskList panel reverts to the state after part 5.7.

------
## Test Storage
------
> **Steps:**
- 1. Close application
- 2. Run `FlexiTrack.jar`
> **Result:**<br>
- Application should have loaded with the same data as before exiting.

------
## 8. Mark Command
------
### 8.1 Mark by index
> **Command:** `mark 5`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Marked Task: 5` <br>
- TaskList panel updates with the updated card which is now green with the prefix '(Done)'. 

### 8.2 Mark something already marked
> **Command:** `m 59`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Task already marked! `<br>

------
## 9. Unmark Command
------
### 9.1 Unmark by index
> **Command:** `unmark 51`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Unmark Task: 51` <br>
- TaskList panel updates with the updated card which is now orange. 

### 9.2 Unmark something not marked
> **Command:** `unmark 1`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Task already unmarked!`<br>

------
## 10. Find Command
------
### 10.1 Find by any keyword
> **Command:** `find chemistry tuition`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`12 tasks listed!` <br>
- TaskList panel updates to show any card with 'chemistry' or 'tuition' in the name. 

### 10.2 Find by exact phrase
> **Command:** `f f/ Chemistry Tuition`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`3 tasks listed!`<br>
- TaskList panel updates to show any card with the exact phrase 'Chemistry Tuition' (not case sensitive) in the name. 

------
## 11. List Command
------
### 11.1 List future
> **Command:** `list future`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Listed all tasks` <br>
- TaskList panel updates to show only future unmarked cards. Floating tasks are also shown.

### 11.2 List past
> **Command:** `list past`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Listed all tasks` <br>
- TaskList panel updates to show only past and marked cards. Floating tasks are also shown. 

### 11.3 List specific date
> **Command:** `list christmas`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Listed all tasks` <br>
- TaskList panel updates to show only cards which either are due on the specific date entered, or occur on that day (may start earlier and end later).

### 11.4 List past time frame
> **Command:** `list last week`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Listed all tasks` <br>
- TaskList panel updates to show only cards that fall in that time frame.

### 11.5 List future time frame
> **Command:** `list next month`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Listed all tasks` <br>
- TaskList panel updates to show only cards that fall in that time frame.

### 11.6 List marked items
> **Command:** `l mark`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Listed all tasks` <br>
- TaskList panel updates to show only marked cards.

### 11.7 List unmarked items
> **Command:** `l unmark`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Listed all tasks` <br>
- TaskList panel updates to show only unmarked cards.

### 11.8 List blocks
> **Command:** `l block`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Listed all tasks` <br>
- TaskList panel updates to show only blocks.

### 11.9 List all
> **Command:** `l`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Listed all tasks` <br>
- TaskList panel updates to show all items.

------
## 12. Clear Command
------
### 12.1 Clear
> **Command:** `clear`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`FlexiTrack has been cleared!` <br>
- TaskList panel updates to become empty.
- All data is deleted.

------
## 13. Clear Command
------
### 13.1 Change storage
> **Command:** `cs newfile`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Storage location changed: newfile.xml` <br>
- newfile.xml is created in the FlexiTrack.xml directory and will be used as the new storage file.

------
## 14. Exit Command
------
### 14.1 exit
> **Command:** `q`<br>
> **Result:**<br>
- FlexiTrack exits.

------
## End
------ 
