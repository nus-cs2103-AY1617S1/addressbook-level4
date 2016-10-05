###User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`

Priority | As a ... | I want to ... | So that I can...
 -------- | :-------- | :--------- | :-----------
***  |  user  |  add events with a start time and an end time  |
***  |  user  |  add tasks without a specified time  | 
***  |  user  |  add tasks without a deadline | 
***  |  user  |  edit existing events  |  update deadlines or venues  
***  |  user  |  delete existing events  |  remove entries that is no longer needed
***  |  user  |  complete events  |  acknowledge the completion of event
***  |  user  |  undo the most recent operations  | undo wrong commands 
***  |  user  |  search by partial keyword  |  find related events containing the keyword
***  |  user  | be able to specify my storage folder | use cloud syncing services on it
**  |  user  |  add tasks that is recurring  |  
**  |  user  |  some variations in command keywords
**  |  user  |  redo the most recent operations   |  redo wrong undos  
**  |  user  |  set priorities  |  indicate tasks that are important
*   |  user  | start the program with a shortcut/ key combination | save mouse clicks
*   | user   | display completed tasks | know what I have done
*  |  user  |  hide completed  |  conceal events that are completed

###Use Cases

#### Use case: Add events with a start time and an end time 

**MSS**
User requests to add events with start time and end time
Todo manager adds events into manager <br>
      Use case ends

**Extensions**
    2a. There is already an event in the time slot
>2a1. Todo manager shows an error message
Use case ends
 

#### Use case: Edit existing events

**MSS**
User requests to edit existing events
Todo manager edits the events<br>
     Use case ends

**Extensions**
   2a. There is no such event requested by the user
    >2a1. Todo manager shows an error message
    Use case ends


#### Use case: Delete existing events

**MSS**
User request to show list of events/task
Todo manager shows list of events/task
User request to delete a specific event / task  
Todo manager deletes the event / task <br>
      Use case ends


**Extensions**
2a. The list is empty
    >Use case ends

3a. The given event/task is non existent
> 3a1. Todo manager shows an error message <br>
      Use case resumes at step 1

> Use case ends


#### Use case: Complete events

**MSS**
User request to update a specific event / task to completed  
Todo manager updates the event / task as completed<br>
      Use case ends

**Extensions**
2a. The given event/task is non existent 
>2a1.Todo manager shows an error message <br>
      Use case resumes at step 1


#### Use case: undo the most recent operations

**MSS**
User request to undo previous operations
Todo manager undos the operations<br>
      Use case ends

**Extensions**
2a. There is no previous operations
> 2a1.Todo manager shows an warning message <br>
    Use case ends





#### Use case: search by partial keyword

**MSS**

User requests to search for an event by keyword
Todo Manager shows a list of events/tasks containing the keyword

**Extensions**

1a1(2a?). The matching keyword is not found
      >A2. Todo manager shows an error message
 Use case ends
