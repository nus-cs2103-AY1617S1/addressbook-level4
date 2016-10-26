//@@author A0147890U

###User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`

Priority | As a ... | I want to ... | So that I can...
 -------- | :-------- | :--------- | :-----------
***  |  user  |  add tasks with a start time and an end time  |
***  |  user  |  add tasks without a specified time  | 
***  |  user  |  add tasks without a deadline | 
***  |  user  |  edit existing tasks  |  update deadlines or venues  
***  |  user  |  delete existing tasks  |  remove entries that is no longer needed
***  |  user  |  complete tasks  |  acknowledge the completion of event
***  |  user  |  undo the most recent operations  | undo wrong commands 
***  |  user  |  search by keyword  |  find related events containing the keyword
***  |  user  | be able to specify my storage folder | use cloud syncing services on it
**  |  user  |  add tasks that is recurring  |  
**  |  user  |  some variations in command keywords
**  |  user  |  set priorities  |  indicate tasks that are important
*   |  user  | start the program with a shortcut/ key combination | save mouse clicks
*   | user   | display completed tasks | know what I have done
*  |  user  |  hide completed  |  conceal events that are completed

###Use Cases

#### Use case: Add events with a start time and an end time 

**MSS**

1. User requests to add events with start time and end time
2. Task manager adds events into manager <br>
Use case ends

**Extensions**

2a. There is already an event in the time slot

>2a1. Task manager shows an error message
 Use case ends
 

#### Use case: Edit existing events

**MSS**

1. User requests to edit existing events
2. Task manager edits the events<br>
Use case ends

**Extensions**

2a. There is no such event requested by the user

>2a1. Task manager shows an error message
 Use case ends


#### Use case: Delete existing events

**MSS**

1. User request to show list of events/task
2. Task manager shows list of events/task
3. User request to delete a specific event / task  
4. Task manager deletes the event / task <br>
Use case ends


**Extensions**

2a. The list is empty

>Use case ends

3a. The given event/task is non existent

>3a1. Task manager shows an error message <br>
 Use case ends


#### Use case: Complete events

**MSS**

1. User request to update a specific event / task to completed  
2. Task manager updates the event / task as completed<br>
Use case ends

**Extensions**

2a. The given event/task is non existent 

>2a1.Task manager shows an error message <br>
 Use case ends


#### Use case: undo the most recent operations

**MSS**

1. User request to undo previous operations
2. Task manager undoes the operations<br>
Use case ends

**Extensions**

2a. There are no previous operations

> 2a1.Task manager shows an warning message <br>
  Use case ends


#### Use case: search by keyword

**MSS**

1. User requests to search for an event by keyword
2. Task Manager shows a list of events/tasks containing the keyword
Use case ends

**Extensions**

1a. The matching keyword is not found

>1a1.Task manager shows an error message
 Use case ends
