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
`New task added: Task by/Nov 13 2016 09:00`<br>
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
## 2. Block Command
------
### 2.1 Block Time
> **Command:** `block Visit Gramdma?? from/christmas to/christmas`<br>
> **Result:**<br>
- Result display panel posts message:<br>
`Block the date for (Blocked) Visit Grandma?? from/Dec 25 2016 08:00 to/Dec 25 2016 17:00`<br>
`Duration of the event is: 9 hours.`<br>
- TaskList panel removes all occurrences of the task. 

### 2.2 Add event over blocked time
> **Command:** `delete visit`<br>
> **Result:**<br>
`Please select the item identified by the index number.\n
Parameters: INDEX(must be a positive integer)\n
Example: delete 1`
-Result display panel posts all tasks and events with name matching at least one input parameter.<br>


------
## End
------ 
