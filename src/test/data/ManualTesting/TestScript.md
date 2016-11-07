# Manual Testing for MESS (My Efficient Scheduling System)
## Pre-requisites
The following files/items are required before the manual test can be run!
* Java
## Pre-test Setup
* Run `MESS.jar` and take note of the filepath of the database.
* Quit MESS.
* Using the System Explorer (e.g. Windows Explorer), navigate to that directory.
* Replace the database XML file with the one located in `src\test\data\ManualTesting\SampleData.xml`
## Test Cases
The test cases are grouped based on **functionality**. We assume that each test case is run separately from each other and thus, the results are based on running the command on a MESS with the initial data (i.e. data provided in `SampleData.xml`).
### Test Case Template
**Command:** `example`
>**Expected Behaviour**: Does something cool! (The message inside here is the specific details of the expected behaviour, if command is run on a clean `SampleData.xml`)

### Adding Tasks

**Command:** `add New Task`
>**Expected Behaviour**: A task with the name *New Task* should be added to the task list. (If using `SampleData.xml`, it should be inserted as Task #46)

**Command:** `add Task With Dues ends 2016-12-01 00:00`
>**Expected Behaviour**: A task with the name *Task With Dues* should be added to the task list. When selected, the task should have `01/12/16 00:00` as the end date. (If using `SampleData.xml`, it should be inserted as Task #27)

**Command:** `add Task With Start starts tomorrow`
>**Expected Behaviour**: A task with the name *Task With Start* should be added to the task list. When selected, the task should have a start date which is one day after the current date and time. (Position indeterminate, as it is dependent on the current time)

**Command:** `add Task With Both starts tomorrow ends next week`
>**Expected Behaviour**: A task with the name *Task With Start* should be added to the task list. When selected, the task should have a start date which is one day after the current date and time and an end date which is exactly one week after the current time. (Position indeterminate, as it is dependent on the current time)

**Command:** `add Task With Tags tag important tag CS2103`
>**Expected Behaviour**: A task with the name *ask With Tags* should be added to the task list. On the bottom of the task name, there should be two tags: `important` and `CS2103`. (If using `SampleData.xml`, it should be inserted as Task #47)

**Command:** `add Impossible Task starts tomorrow ends yesterday`
>**Expected Behaviour**: A message will appear: "Please ensure that your start and end time combination is valid.", as the start time cannot be after the end time. (If using `SampleData.xml`, task list should not change)

**Command:** `add Invalid DateTime starts never`
>**Expected Behaviour**: A message will appear: "You have entered an invalid Date/Time format. For a complete list of all acceptable formats, please view our user guide.", as the start time cannot be parsed. (If using `SampleData.xml`, task list should not change)

### Modifying Tasks

**Command:** `update 47`
>**Expected Behaviour**: Task #47 should still remain unchanged. (If using `SampleData.xml`, task list should not be updated)

**Command:** `update 47 name do not tune guitar`
>**Expected Behaviour**: The name for Task #47 should change to *do not tune guitar* and the task should be moved to the correct position (i.e. alphabetical order according to end date). All other properties should remain the same. (If using `SampleData.xml`, task #47 should now be task #39)

**Command:** `update 47 start 2016-02-01 00:00`
>**Expected Behaviour**: The start date of Task #47 should update to `01/02/2016 00:00`  All other properties should remain the same. (If using `SampleData.xml`, task #47 should still be in position #47)

**Command:** `update 2 remove-tag study`
>**Expected Behaviour**: The study tag of Task #2 should be removed. All other tags should remain. All other properties should remain the same. (If using `SampleData.xml`, task #2 should still be in position #2 and the remaining tags are: *CS3230, CS2105, CS2103*)

### Deleting Tasks

**Command:** `delete 1`
>**Expected Behaviour:** Task #1 should be removed. (If using `SampleData.xml`, Task #1 is *CS2103 demo*)

**Command:** `delete 0`
>**Expected Behaviour:** Command input box should turn red. Message in command box should be:
```
Invalid command format!
delete: Deletes the task identified by the index number used in the last task listing.
Parameters: INDEX (must be a positive integer)
Example: delete 1
```

### Finding Tasks

**Command:** `find est`
>**Expected Behaviour:** The task list should display all tasks with a task name that contains `est` (can be a substring of a word). (If using `SampleData.xml`, 3 tasks should be listed: `guest lecture`, `send birthday card to Esther` and `book a restaurant`)

**Command:** `searchbox`
>**Expected Behaviour:** The command input box should now function as a search box. Any keywords typed (e.g. `est`) would match tasks with that particular keyword.

### Listing Tasks

**Command:** `list all`
>**Expected Behaviour:** The task list should display all tasks that are in the XML file. The tasks should be ordered according to end date and name (end date takes priority). (If using `SampleData.xml`, there should be 54 tasks listed)

**Command:** `list pending`
>**Expected Behaviour:** The task list should display all tasks that are not marked as completed (i.e. no tasks in the list should have a strikethrough name). The tasks should be ordered according to end date and name (end date takes priority). (If using `SampleData.xml`, there should be 48 tasks listed)

**Command:** `list completed`
>**Expected Behaviour:** The task list should display all tasks that are marked as completed (i.e. all tasks displayed should have a faded effect and a strikethrough task name). The tasks should be ordered according to end date and name (end date takes priority). (If using `SampleData.xml`, there should be 6 tasks listed)

**Command:** `list pinned`
>**Expected Behaviour:** The task list should display all tasks that are pinned (i.e. all tasks listed should have a golden star). The tasks should be ordered according to end date and name (end date takes priority). (If using `SampleData.xml`, there should be 4 tasks listed)

**Command:** `list overdue`
>**Expected Behaviour:** The task list should display all tasks that have an end date that is before the current time. The tasks should be ordered according to end date and name (end date takes priority). (If using `SampleData.xml`, it is indeterminate, as it is dependent on the current clock)

### Pinning Tasks

**Command:** `pin 48`
>**Expected Behaviour:** A star should appear on the right side of Task #48. The task should now appear at the top of the task list, of which it is ordered amongst all other pinned tasks. (If using `SampleData.xml`, Task #48 [visit uncle new house] should now be Task #5)

**Command:** `pin 999`
>**Expected Behaviour:** The message should appear: "The task index provided is invalid", as there is no Task #999.

### Unpinning Tasks

**Command:** `unpin 1`
>**Expected Behaviour:** The star beside Task #1 should no longer be there. Task #1 would now be sorted amongst the non-pinned and non-completed tasks (i.e. it is no longer positioned on the top, amongst other pinned tasks). (If using `SampleData.xml`, Task #1 [CS2103 demo] should now be Task #22)

**Command:** `unpin 8`
>**Expected Behaviour:** This message would be shown: "The task is not pinned before!", as the task has not been pinned. Task list should not be modified. (If using `SampleData.xml`, Task #8 [buy books] should not be changed)

### Completing Tasks

**Command:** `complete 9`
>**Expected Behaviour:** Task #9 should now be moved to the bottom of the task list. Amongst all other completed tasks, the position of Task #9 should be ordered. The task should appear faded and the name has a strikethrough. (If using `SampleData.xml`, Task #9 [guest lecture] should now be Task #50)

### Uncompleting Tasks

**Command:** `uncomplete 49`
>**Expected Behaviour:** Task #49 should no longer be in a faded colour and its name should not be struck. The Task would be positioned amongst all other non-completed tasks. (If using `SampleData.xml`, Task #49 [hackathon] should now be Task #5)

**Command:** `uncomplete 5`
>**Expected Behaviour:** This message should be shown: "The task is not marked as complete before!", as the task has not been marked as completed. (If using `SampleData.xml`, Task #5 [make personal website] should remain unchanged)

### Undo Previous Command

**Commands:** `undo` after running `add nonsense task`
>**Expected Behaviour:** Before undoing, the "nonsense task" should be in the task list. After undo, the task "nonsense task" should not be in the list. (If using `SampleData.xml`, after adding, Task #46 should be "nonsense task". After undo, Task #46 should be "shopping" and the state of the task list should be the same as the initial `SampleData.xml`)

**Commands:** `undo` after running `delete 6`
>**Expected Behaviour:** Task #6, which was deleted, should be recovered with its details (e.g. start and end times and tags) intact. (If using `SampleData.xml`, Task #6 [find intern] should be deleted and "find intern" task should be reinstated as Task #6 after `undo`)

**Commands:** `undo` after running `pin 5`
>**Expected Behaviour:** After pin command is executed, Task #5 should be pinned (see pin tests for expected pinning behaviour). After the undo command, the task should be unpinned and returns to Task #5. (If using `SampleData.xml`, Task #10 [send birthday card to Esther] should be Task #1, after running pin. After the undo, the task should now be Task #10 and should not have any pinned. All other properties should remain the same throughout)

### Help Window

**Command:** `help`
>**Expected Behaviour:** A popup should appear with a summary of all commands.

