# Test Script
There are three vertical task lists based on their respective task type: `Todo`/`Event`/`Deadline`.<br>
Following testing command will test each of those data type and the result will be shown in the respective task list. <br>
`Add`, `Delete`, `Edit`, `Done`, `Undone`, `Clear`, `Clear_done` commands are undoable commands. Undo command tests will be followed by each of undoable command test.

## Preparing test environment
1. Copy the Tdoo_v0.5.jar to a directory of your choice.
2. Under the directory where Tdoo_v0.5.jar is at, create a folder called "data".
3. Copy the following xml files in this directory to the newly created folder "data". *(DO NOT CHANGE THE FILE NAME!)*
  * DeadlineList.xml
  * EventList.xml
  * TodoList.xml
4. Now you may run Tdoo_v0.5.jar app and follow the following steps for manual testing.

## Viewing help : `help`
Show features and respective commands of the app.<br>
> Command: `help`  
> Expected Outcome: A new window point to the webpage of the User Guide is showed.

## Adding tasks

#### Adding a Todo:
Todos will be rearranged in the Todo-List based on their priority.<br>
> Command: `add prep cs2103 v0.5 demo p/1`<br>
> Expected Outcome: A Todo task *(prep cs2103 v0.5 demo)* is added to the 7th entry of the Todo List. The list will be scrolled to the location of the Todo task added. <br>

> Command: `add prep pc1222 test 2 p/2`<br>
> Expected Outcome: A Todo task *(prep pc1222 test 2)* is added to the 11th entry of the Todo List. The list will be scrolled to the location of the Todo task added. <br>

> Command: `add read news p/3`<br>
> Expected Outcome: A Todo task *(read news)* is added to the 13th entry of the Todo List. The list will be scrolled to the location of the Todo task added. <br>

> Undo Command: `undo`<br>
> Expected Outcome: A Todo task *(read news)* is deleted from the 13th entry of the Todo List.

> Undo Command: `undo`<br>
> Expected Outcome: A Todo task *(prep pc1222 test 2)* is deleted from the 11th entry of the Todo List.

> Undo Command: `undo`<br>
> Expected Outcome: A Todo task *(prep cs2103 v0.5 demo)* is deleted from the 7th entry of the Todo List.

#### Adding an Event:
Events will be rearranged in the Event-List based on their starting time.<br>
> Command: `add cs2103 v0.5 demo from/10-11-2016 to/10-11-2016 at/14:00 to/15:00`<br>
> Expected Result: An Event task *(cs2103 v0.5 demo)* is added the 2nd entry of the Event List. The list will be scrolled to the location of the Event task added. <br>

> Undo Command: `undo`<br>
> Expected Outcome: An Event task *(cs2103 v0.5 demo)* is deleted from the 2nd entry of the Event List.

#### Adding a Deadline:
Deadlines will be rearranged in the Deadline-List based on their ending time.<br>
> Command: `add cs2103 v0.5 submission on/07-11-2016 at/23:59`<br>
> Expected Result: A Deadline task *(cs2103 v0.5 submission)* is added to the 1st entry of the Deadline List. The list will be scrolled to the location of the Deadline task added. <br>

> Undo Command: `undo`<br>
> Expected Outcome: A Deadline task *(cs2103 v0.5 submission)* is deleted from the 1st entry of the Deadline List.

## Deleting tasks
Delete a task with given type and index number.<br>

#### Deleting a Todo:
> Command: `delete todo 6`<br>
> Expected Outcome: A Todo task *(Prepare CS2103 v0.5 rc demo)* is deleted from the 6th entry of the Todo List. <br>

> Undo Command: `undo`<br>
> Expected Outcome: A Todo task *(Prepare CS2103 v0.5 rc demo)* is added back to the 6th entry of the Todo List.

#### Deleting an Event:
> Command: `delete event 1`<br>
> Expected Result: An Event task *(PC1222 Test 2)* is deleted from the 1st entry of the Event List. <br>

> Undo Command: `undo`<br>
> Expected Outcome: An Event task *(PC1222 Test 2)* is added back to the 1st entry of the Event List.

#### Deleting a Deadline:
Deadlines will be rearranged in the Deadline-List based on their ending time.<br>
> Command: `delete deadline 1`<br>
> Expected Result: A Deadline task *(Prepare for ICMY fest)* is deleted from the 1st entry of the Deadline List. <br>

> Undo Command: `undo`<br>
> Expected Outcome: A Deadline task *(Prepare for ICMY fest)* is added back to the 1st entry of the Deadline List.


## Editing tasks
Edits information of the task in the task-list.<br>
Different Types of tasks (Todo/Event/Deadline) have different command format.<br>
You can change the data type of a task by editing it with a different command format. For example, if you edit a Todo task with an Event data format, the Todo task will be transfromed into an Event task.

#### Editing a Todo:
Todos will be rearranged in the Todo-List based on their priority.<br>
> Command: `edit todo 6 name/Prepare CS2103 v0.5rc demo p/2`<br>
> Expected Outcome: A Todo task *(Prepare CS2103 v0.4 demo)* at the 6th entry of the Todo List is edited by the given parameters. The list will be scrolled to the location of the Todo task edited. <br>

> Undo Command: `undo`<br>
> Expected Outcome: A Todo task *(Prepare CS2103 v0.4 demo)* is edited back to the original description.

#### Editing an Event:
Events will be rearranged in the Event-List based on their starting time.<br>
> Command: `edit event 1 name/PC1222 Test 2 from/09-11-2016 to/09-11-2016 at/17:30 to/18:15`<br>
> Expected Result: An Event task *(PC1222 Test 2)* at the 1st entry of the Event List is edited by the given parameters. The list will be scrolled to the location of the Event task edited. <br>

> Undo Command: `undo`<br>
> Expected Outcome: An Event task *(PC1222 Test 2)* is edited back to the original description.

#### Editing a Deadline:
Deadlines will be rearranged in the Deadline-List based on their ending time.<br>
> Command: `edit deadline 1 name/Prepare for ICMY fest on/12-11-2016 at/23:59`<br>
> Expected Result: A Deadline task *(Prepare for ICMY fest)* at the 1st entry of the Deadline List is edited by the given parameters. The list will be scrolled to the location of the Deadline task edited. <br>

> Undo Command: `undo`<br>
> Expected Outcome: A Deadline task *(Prepare for ICMY fest)* is edited back to the original description.


## Marking tasks 'done'
Mark a Todo-task with given index number as done.<br>

#### Marking a Todo 'done':
> Command: `done todo 1`<br>
> Expected Outcome: A Todo task *(Captive portal mapping)* at the 1st entry of the Todo List is marked 'Completed' and changes color from white to green. The list will be scrolled to the location of the Todo task marked.

> Undo Command: `undo`<br>
> Expected Outcome: A Todo task *(Captive portal mapping)* is Marked back as 'Not Completed'.

#### Marking an Event 'done':
> Command: `done event 1`<br>
> Expected Result: An Event task *(PC1222 Test 2)* at the 1st entry of the Event List is marked 'Completed' and changes color from white to green. The list will be scrolled to the location of the Event task marked.

> Undo Command: `undo`<br>
> Expected Outcome: An Event task *(PC1222 Test 2)* is Marked back as 'Not Completed'.

#### Marking a Deadline 'done':
> Command: `done deadline 1`<br>
> Expected Result: A Deadline task *(Prepare for ICMY fest)* at the 1st entry of the Deadline List is marked 'Completed' and changes color from white to green. The list will be scrolled to the location of the Deadline task marked.

> Undo Command: `undo`<br>
> Expected Outcome: A Deadline task *(Prepare for ICMY fest)* is Marked back as 'Not Completed'.


## Unmarking 'done' tasks
Mark a Todo-task with given index number as undone.<br>

#### Marking a Todo 'Not Completed':
> Command: `undone todo 4`<br>
> Expected Outcome: A Todo task *(Operationalize RPi images)* at the 4th entry of the Todo List is marked 'Not Completed' and changes color from green to white. The list will be scrolled to the location of the Todo task marked.

> Undo Command: `undo`<br>
> Expected Outcome: A Todo task *(Operationalize RPi images)* is Marked back as 'Completed'.

#### Marking an Event 'Not Completed':
> Command: `undone event 3`<br>
> Expected Result: An Event task *(MA1505 Exam)* at the 3rd entry of the Event List is marked 'Not Completed' and changes color from green to white. The list will be scrolled to the location of the Event task marked.

> Undo Command: `undo`<br>
> Expected Outcome: An Event task *(MA1505 Exam)* is Marked back as 'Completed'.

#### Marking a Deadline 'Not Completed':
> Command: `undone deadline 4`<br>
> Expected Result: A Deadline task *(CS2103 v0.1)* at the 4th entry of the Deadline List is marked 'Not Completed' and changes color from green to white. The list will be scrolled to the location of the Deadline task marked.

> Undo Command: `undo`<br>
> Expected Outcome: A Deadline task *(CS2103 v0.1)* is Marked back as 'Completed'.


## Clearing task lists
Clears all data in the given task type list.<br>

#### Clearing all task types:
> Command: `clear all`<br>
> Expected Result: All three task lists are emptied. <br>

> Undo Command: `undo`<br>
> Expected Outcome: All three deleted lists are restored back.

#### Clearing Todo list:
> Command: `clear todo`<br>
> Expected Result: All Todo tasks are deleted. <br>

> Undo Command: `undo`<br>
> Expected Outcome: All deleted Todo tasks are restored back.

#### Clearing Event list:
> Command: `clear event`<br>
> Expected Result: All Event tasks are deleted. <br>

> Undo Command: `undo`<br>
> Expected Outcome: All deleted Event tasks are restored back.

#### Clearing Deadline list:
> Command: `clear deadline`<br>
> Expected Result: All Deadline tasks are deleted. <br>

> Undo Command: `undo`<br>
> Expected Outcome: All deleted Deadline tasks are restored back.


## Clearing done tasks from the list
Clears all completed tasks from the given task type list.<br>

#### Clearing from all task types:
> Command: `clear_done all`<br>
> Expected Result: All completed tasks from all three lists are deleted. <br>

> Undo Command: `undo`<br>
> Expected Outcome: Deleted tasks are restored back.

#### Clearing from Todo list:
> Command: `clear_done todo`<br>
> Expected Result: All completed Todo tasks are deleted. <br>

> Undo Command: `undo`<br>
> Expected Outcome: Deleted tasks are restored back.

#### Clearing from Event list:
> Command: `clear_done event`<br>
> Expected Result: All completed Event tasks are deleted. <br>

> Undo Command: `undo`<br>
> Expected Outcome: Deleted tasks are restored back.

#### Clearing from Deadline list:
> Command: `clear_done deadline`<br>
> Expected Result: All copmleted Deadline tasks are deleted. <br>

> Undo Command: `undo`<br>
> Expected Outcome: Deleted tasks are restored back.


## Finding tasks with keywords
Finds tasks whose names contain any of the given keywords. The task list will be filtered and only tasks with given keywords are displayed.<br>

#### Find from all three task lists:
> Command: `find all 2103`<br>
> Expected Result: All tasks that contain given keywords *(2103)* are displayed. <br>

#### Find from Todo list:
> Command: `find todo 2103`<br>
> Expected Result: All Todo tasks that contain given keywords *(2103)* are displayed. <br>

#### Find from Event list:
> Command: `find event 2103`<br>
> Expected Result: All Event tasks that contain given keywords *(2103)* are displayed. <br>

#### Find from Deadline list:
> Command: `find deadline 2103`<br>
> Expected Result: All Deadline tasks that contain given keywords are displayed. <br>


## Listing all tasks
List command can be done after task lists are filtered by the find command. It will display all the tasks stored in the storage. <br>

#### List all three task types:
> Command: `list all`<br>
> Expected Result: All task types are displayed. <br>

#### List all three task types:
> Command: `list todo`<br>
> Expected Result: All Todo tasks are displayed. <br>

#### List all three task types:
> Command: `list event`<br>
> Expected Result: All Event tasks are displayed. <br>

#### List all three task types:
> Command: `list deadline`<br>
> Expected Result: All Deadline tasks are displayed. <br>

## Changing data storage location
Change the storage directory of the app. If the given directory does not exist, the app will create a new directory with given name.<br>
The Storage directory is OS sensitive. The testings below are set to change storage to desktop. <br>
> Command(Windows): `storage c:\Users\(username)\Desktop\newdata` <br>
> Command(Os X): `storage /Volumes/(volume name)/Users/(username)/Desktop/newdata` <br>
> Command(Linux): `storage /home/(username)/Desktop/newdata` <br>
> Expected Result: On ui, there is no difference except the 'Storage Location' in the status footer is changed to the new storage directory. All the data from the previous storage is moved to the Desktop.

## Exiting the program
Exits the program.<br>
> Command: `exit` <br>
> Expected Result: Excited
