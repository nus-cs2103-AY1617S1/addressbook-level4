# Test Script
There are three vertical task lists based on their respective task type: Todo/Event/Deadline.<br>
Following testing command will test each of those data type and the result will be shown in the respective task list. <br>
Add, Delete, Edit, Done, Undone, Clear, Clear_done commands are undoable commands. Undo command tests will be followed by each of undoable command test.

## Viewing help : `help`
Show features and respective commands of the app.<br>
> Command: `help`
> Expected Outcome: A webpage with the User Guide is showned.


## Adding tasks
Adds a task to the task-list.

#### Adding a Todo:
Todos will be rearranged in the Todo-List based on their priority.<br>
> Command: `add (Give command arguments)`<br>
> Expected Outcome: A Todo task (Give a task description) is added to (index)th entry of the Todo List. The list will be scrolled to the location of the Todo task added. <br>

(add more commands if needed...)

> Undo Command: `undo`<br>
> Expected Outcome: A Todo task (Give a task description) is deleted from the (index)th entry of the Todo List.

#### Adding an Event:
Events will be rearranged in the Event-List based on their starting time.<br>
> Command: `add (Give command arguments)`<br>
> Expected Result: An Event task (Give a task description) is added to (index)th entry of the Event List. The list will be scrolled to the location of the Event task added. <br>

> Undo Command: `undo`<br>
> Expected Outcome: An Event task (Give a task description) is deleted from the (index)th entry of the Event List.

#### Adding a Deadline:
Deadlines will be rearranged in the Deadline-List based on their ending time.<br>
> Command: `add (Give command arguments)`<br>
> Expected Result: A Deadline task (Give a task description) is added to (index)th entry of the Deadline List. The list will be scrolled to the location of the Deadline task added. <br>

> Undo Command: `undo`<br>
> Expected Outcome: A Deadline task (Give a task description) is deleted from the (index)th entry of the Deadline List.

## Deleting tasks
Delete a task with given type and index number.<br>

#### Deleting a Todo:
> Command: `delete todo (Give command arguments)`<br>
> Expected Outcome: A Todo task (Give a task description) is deleted from the (index)th entry of the Todo List. <br>

> Undo Command: `undo`<br>
> Expected Outcome: A Todo task (Give a task description) is added back to the (index)th entry of the Todo List.

#### Deleting an Event:
> Command: `delete event (Give command arguments)`<br>
> Expected Result: An Event task (Give a task description) is deleted from the (index)th entry of the Event List. <br>

> Undo Command: `undo`<br>
> Expected Outcome: An Event task (Give a task description) is added back to the (index)th entry of the Event List.

#### Deleting a Deadline:
Deadlines will be rearranged in the Deadline-List based on their ending time.<br>
> Command: `delete deadline (Give command arguments)`<br>
> Expected Result: A Deadline task (Give a task description) is deleted from the (index)th entry of the Deadline List. <br>

> Undo Command: `undo`<br>
> Expected Outcome: A Deadline task (Give a task description) is added back to the (index)th entry of the Deadline List.


## Editing tasks
Edits information of the task in the task-list.<br>
Different Types of tasks (Todo/Event/Deadline) have different command format.<br>
You can change the data type of a task by editing it with a different command format. For example, if you edit a Todo task with an Event data format, the Todo task will be transfromed into an Event task.

#### Editing a Todo:
Todos will be rearranged in the Todo-List based on their priority.<br>
> Command: `edit (Give command arguments)`<br>
> Expected Outcome: A Todo task (Give a task description) at the (index)th entry of the Todo List is edited by the given parameters. The list will be scrolled to the location of the Todo task edited. <br>

(add more commands if needed...)


> Undo Command: `undo`<br>
> Expected Outcome: A Todo task (Give a task description) is edited back to the (Give a task descriptipn).

#### Editing an Event:
Events will be rearranged in the Event-List based on their starting time.<br>
> Command: `edit (Give command arguments)`<br>
> Expected Result: An Event task (Give a task description) at the (index)th entry of the Event List is edited by the given parameters. The list will be scrolled to the location of the Event task edited. <br>

> Undo Command: `undo`<br>
> Expected Outcome: An Event task (Give a task description) is edited back to the (Give a task descriptipn).

#### Editing a Deadline:
Deadlines will be rearranged in the Deadline-List based on their ending time.<br>
> Command: `edit (Give command arguments)`<br>
> Expected Result: A Deadline task (Give a task description) at the (index)th entry of the Deadline List is edited by the given parameters. The list will be scrolled to the location of the Deadline task edited. <br>

> Undo Command: `undo`<br>
> Expected Outcome: A Deadline task (Give a task description) is edited back to the (Give a task descriptipn).


## Marking tasks 'done'
Mark a Todo-task with given index number as done.<br>

#### Marking a Todo 'done':
> Command: `done todo (Give command arguments)`<br>
> Expected Outcome: A Todo task (Give a task description) at the (index)th entry of the Todo List is marked 'Completed' and changes color from white to green. The list will be scrolled to the location of the Todo task marked.

(add more commands if needed...)

> Undo Command: `undo`<br>
> Expected Outcome: A Todo task (Give a task description) is Marked back as 'Not Completed'.

#### Marking an Event 'done':
> Command: `done event (Give command arguments)`<br>
> Expected Result: An Event task (Give a task description) at the (index)th entry of the Event List is marked 'Completed' and changes color from white to green. The list will be scrolled to the location of the Event task marked.

> Undo Command: `undo`<br>
> Expected Outcome: An Event task (Give a task description) is Marked back as 'Not Completed'.

#### Marking a Deadline 'done':
> Command: `done deadline (Give command arguments)`<br>
> Expected Result: A Deadline task (Give a task description) at the (index)th entry of the Deadline List is marked 'Completed' and changes color from white to green. The list will be scrolled to the location of the Deadline task marked.

> Undo Command: `undo`<br>
> Expected Outcome: A Deadline task (Give a task description) is Marked back as 'Not Completed'.


## Unmarking 'done' tasks
Mark a Todo-task with given index number as undone.<br>

#### Marking a Todo 'done':
> Command: `undone todo (Give command arguments)`<br>
> Expected Outcome: A Todo task (Give a task description) at the (index)th entry of the Todo List is marked 'Not Completed' and changes color from green to white. The list will be scrolled to the location of the Todo task marked.

(add more commands if needed...)

> Undo Command: `undo`<br>
> Expected Outcome: A Todo task (Give a task description) is Marked back as 'Completed'.

#### Marking an Event 'done':
> Command: `undone event (Give command arguments)`<br>
> Expected Result: An Event task (Give a task description) at the (index)th entry of the Event List is marked 'Not Completed' and changes color from green to white. The list will be scrolled to the location of the Event task marked.

> Undo Command: `undo`<br>
> Expected Outcome: An Event task (Give a task description) is Marked back as 'Completed'.

#### Marking a Deadline 'done':
> Command: `undone deadline (Give command arguments)`<br>
> Expected Result: A Deadline task (Give a task description) at the (index)th entry of the Deadline List is marked 'Not Completed' and changes color from green to white. The list will be scrolled to the location of the Deadline task marked.

> Undo Command: `undo`<br>
> Expected Outcome: A Deadline task (Give a task description) is Marked back as 'Completed'.


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
> Command: `find all (Give keywords here)`<br>
> Expected Result: All tasks that contain given keywords are displayed. <br>

#### Find from Todo list:
> Command: `find todo (Give keywords here)`<br>
> Expected Result: All Todo tasks that contain given keywords are displayed. <br>

#### Find from Event list:
> Command: `find event (Give keywords here)`<br>
> Expected Result: All Event tasks that contain given keywords are displayed. <br>

#### Find from Deadline list:
> Command: `find deadline (Give keywords here)`<br>
> Expected Result: All Deadline tasks that contain given keywords are displayed. <br>


## Listing all tasks
List command can be done after task lists are filtered by the find command. It will display all the tasks stored in the storage. <br>

## list all three task types:
> Command: `list all`<br>
> Expected Result: All task types are displayed. <br>

## list all three task types:
> Command: `list todo`<br>
> Expected Result: All Todo tasks are displayed. <br>

## list all three task types:
> Command: `list event`<br>
> Expected Result: All Event tasks are displayed. <br>

## list all three task types:
> Command: `list deadline`<br>
> Expected Result: All Deadline tasks are displayed. <br>

## Changing data storage location
Change the storage directory of the app. If the given directory does not exist, the app will create a new directory with given name.<br>
The Storage directory is OS sensitive. The testings below are set to change storage to desktop. <br>
> Command(Windows): `storage c:\Users\(username)\Desktop` <br>
> Command(Os X): `storage (DIRECTORY)` <br>
> Command(Linux): `storage /home/(username)/Desktop` <br>
> Expected Result: On ui, there is no difference except the 'Storage Location' in the status footer is changed to the new storage directory. All the data from the previous storage is moved to the Desktop.

## Exiting the program
Exits the program.<br>
> Command: `exit` <br>
> Expected Result: Excited

