# Script for Manual Testing

This test script is to assist testers in testing `Dowat` by providing them with sample test cases which covers the full range of functionality of `Dowat`.

## Loading sample data

* Before running `Dowat.jar`, copy the `SampleData.xml` into same file location as the jar file
* Rename the `SampleData.xml` file to `Dowat.xml`
* Run `Dowat.jar`

## Manual Tests

### Adding a task

1. Adding a task
>  To type: `add computing project`
	> * Added a task named 'computing project'
	> * Newly added task is selected in both task list panel and calendar panel
	
2. Adding a task with description
> To type: `add arts project /desc soci chapter 5`
	> * Added a task named 'arts project' with description 'soci chapter 5'
	> * Newly added task is selected in both task list panel and calendar panel

3. Adding a task with deadline
> To type: `add engineering project /by tomorrow 7pm`
	> * Added a task named 'engineering project' with deadline 7pm one day from current day
	> * Newly added task is selected in both task list panel and calendar panel

4. Adding a task with description and deadline
> To type: `add nus project /desc orientation /by 30 Dec 7pm` 
	> * Added a task named 'nus project' with description 'orientation' with deadline '19:00 30 December 2016'
	> * Newly added task is selected in both task list panel and calendar panel

5. Adding a task with deadline and description (Flexible Ordering)
> To type: `add nus project 2 /by 30 Dec 7pm /desc marketing proposal` 
	> * Added a task named 'nus project 2' with description 'marketing proposal' with deadline '19:00 30 December 2016'
	> * Newly added task is selected in both task list panel and calendar panel

6. Undo recently added task
> To type: `undo` 
	> * Newly added task named 'nus project 2' with description 'marketing proposal' with deadline '19:00 30 December 2016' is removed

7. Undo next recently added task
> To type: `undo`
	> * Newly added task named 'nus project' with description 'orientation' with deadline '19:00 30 December 2016' is removed

### Adding an event

1. Adding an event with start duration
> To type: `add arts workshop /from next monday 2pm` 
	> * Newly added event named 'arts workshop' with duration from 2pm to 3pm on next Monday
	> * Newly added event is selected in both event list panel and calendar panel

2. Adding an event with end duration
> To type: `add arts workshop 2 /to next monday 2pm` 
	> * Newly added event named 'arts workshop 2' with duration from 1pm to 2pm on next Monday
	> * Newly added event is selected in both event list panel and calendar panel

3. Adding an event with description
> To type: `add arts workshop 3 /desc at AS7-01 /from next friday 2pm /to next friday 6 pm` 
	> * Newly added event named 'arts workshop 3' with description 'at AS7-01' with duration from 2pm to 6pm on next Friday
	> * Newly added event is selected in both event list panel and calendar panel

4. Adding an event with description (Flexible Ordering)
> To type: `add arts workshop 4 /from next friday 2pm /desc at iCube Auditorium /to next friday 6 pm` 
	> * Newly added event named 'arts workshop 4' with description 'at iCube Auditorium' with duration from 2pm to 6pm on next Friday
	> * Newly added event is selected in both event list panel and calendar panel

5. Undo recently added event
> To type: `undo` 
	> * Newly added event named 'arts workshop 4' with description 'at iCube Auditorium' with duration from 2pm to 6pm on next Friday is removed

### Listing tasks or events

1. Listing all uncompleted tasks
> To type: `list /t` 
	> * Task list is updated to show all uncompleted tasks

2. Listing all completed and uncompleted tasks
> To type: `list /t /a` 
	> * Task list is updated to show all uncompleted and completed tasks
	> * Completed tasks have a completed task card display format

3. Listing all upcoming events
> To type: `list /e` 
	> * Event list is updated to show all upcoming events

4. Listing all upcoming and past events
> To type: `list /e /a` 
	> * Event list is updated to show all past and upcoming events

5. Listing all uncompleted tasks and upcoming events
> To type: `list` 
	> * Task and event lists are updated to show all uncompleted tasks and upcoming events

6. Listing ALL tasks and events
> To type: `list /a` 
	> * Task and event lists are updated to show all tasks and events

### Editing a task

1. Editing the name of a task
> To type: `edit /t 1 /name new task name` 
	> * Newly edited task with new name 'new task name'
	> * Completed tasks are marked uncompleted
	> * Newly edited task is selected in both task list panel and calendar panel

2. Editing the description of a task
> To type: `edit /t 1 /desc new task description` 
	> * Newly edited task with new description 'new task description'
	> * Completed tasks are marked uncompleted
	> * Newly edited task is selected in both task list panel and calendar panel

3. Editing the deadline of a task
> To type: `edit /t 1 /by today 11 pm` 
	> * Newly edited task with new deadline of 11pm for today
	> * Completed tasks are marked uncompleted
	> * Newly edited task is selected in both task list panel and calendar panel

4. Editing all fields of a task
> To type: `edit /t 1 /name new name /desc new description /by today 11 pm` 
	> * Newly edited task with new name 'new name', new description 'new description' and new deadline of 11pm for today
	> * Completed tasks are marked uncompleted
	> * Newly edited task is selected in both task list panel and calendar panel

6. Removing the deadline of a task
> To type: `edit /t 1 /by rm` 
	> * Removes deadline for an existing deadline task
	> * Completed tasks are marked uncompleted
	> * Newly edited task is selected in both task list panel and calendar panel

7. Undo recently edited task
> To type: `undo` 
	> * Newly edited task with new deadline of 11pm for today is reverted to pre-edited state

### Editing an event

1. Editing start duration of an event
> To type: `edit /e 1 /from today 10 pm` 
	> * Newly edited event with new start duration of 10 pm for today
	> * Newly edited event is selected in both event list panel and calendar panel

2. Editing end duration of an event
> To type: `edit /e 1 /to today 11.30 pm` 
	> * Newly edited event with new end duration of 11.30 pm for today
	> * Newly edited event is selected in both event list panel and calendar panel

3. Editing all fields of an event
> To type: `edit /e 1 /name new name /desc new description /from today 10 pm /to today 11pm` 
	> * Newly edited event with new name 'new name', new description 'new description' and new duration from 10 pm to 11 pm for today
	> * Newly edited event is selected in both event list panel and calendar panel

4. Undo recently edited event
> To type: `undo` 
	> * Newly edited event with new name 'new name', new description 'new description' and new duration from 10 pm to 11 pm for today is reverted to pre-edited state

### Marking a task as completed

1. Marking an uncompleted task
> To type: `mark 1` 
	> * The task at index 1 is marked completed and will not appear in the task list

2. Undo recently marked task
> To type: `undo` 
	> * Previously marked completed task will appear in the task list and is now uncompleted

### Deleting a task/event

1. Deleting a task
> To type: `delete /t 1` 
	> * Task at index 1 is deleted and will not exist in the model
	> * Listing all tasks will not show the deleted task anymore

2. Deleting an event
> To type: `delete /e 1` 
	> * Event at index 1 is deleted and will not exist in the model
	> * Listing all events will not show the deleted event anymore

3. Undo recently deleted event
> To type: `undo` 
	> * Latest event deletion is reversed
	> * Event will appear in the event list and calendar panel

### Selecting a task/event

1. Selecting a task
> To type: `select /t 1` 
	> * Task at index 1 is selected and will have task card background highlighted in grey
	> * Task at calendar panel will be selected with yellow background

2. Selecting an event
> To type: `select /e 1` 
	> * Event at index 1 is selected and will have event card background highlighted in grey
	> * Event at calendar panel will be selected with yellow background

### Changing save location

1. Changing save location
> To type: `save D:\` 
	> * New save location of 'Dowat.txt' in file path 'D:\'
	> * Relaunching 'Dowat' will load data from new file location

### Viewing help

1. Viewing help via user guide pop-up
> To type: `help` 
	> * Pop-up window of User Guide
	> * User Guide pop-up is a html file which can be opened without internet connection

2. Viewing command specific help
> To type: `help add` 
	> * To display Add command message usage in the result display box
	> * Full add message can be accessed by traversing to the result display window with 'Tab' and scrolling with 'Up' and 'Down' keys

### Undo

All commands applicable for undo are incorporated within each command text. Undo commands can be executed up to the last command executed in the current session.

### Finding for tasks and events

1. Finding items based on keyword
> To type: `find Ricky` 
	> * All tasks and events with name and descriptions containing the keyword 'Ricky' is displayed in the task and event list panel
	> * Find is not case-sensitive

2. Finding items based on a few keywords
> To type: `find Ricky / Keith` 
	> * All tasks and events with name and descriptions containing the keyword 'Ricky' or 'Keith' are displayed in the task and event list panel
	> * Find is not case-sensitive

3. Power finding for items based on keywords
> To type: `find Rickk / Keth /power` 
	> * All tasks and events with name and descriptions containing the keyword 'Rickk' or 'Keth' is displayed in the task and event list panel
	> * Power finding allows for minor typos and will display results with similar keywords

### Toggling calendar view

1. Showing day view
> To type: `show today /day` 
	> * Displays today's schedule in day view format in the calendar panel

2. Showing week view
> To type: `show next week 8 pm /wk` 
	> * Displays next week's schedule in week view format in the calendar panel

### Controlling UI

1. Accessing past history of commands
> Key press in command box: `UP` or `DOWN`
	> * Historical commands will be displayed in command box from most recently typed commands to earliest typed commands in current session
	> * Nothing will be displayed if there have been no commands entered before in current session

2. Clearing command box
> Key press in command box: `DELETE`
	> * Existing text in command box is cleared

3. Traversing between windows and scrolling
> Key press: `TAB` and `UP` or `DOWN`
	> * Traverse between command box, result box, task list panel and event list panel
	> * Borders of current focus is highlighted
	> * Scrolling achieved by using `UP` and `DOWN` keys

### Clearing of tasks and events

1. Clearing all completed tasks
> To type: `list /t /a` followed by `clear /t` 
	> * All uncompleted and completed tasks are first listed
	> * Completed tasks are cleared from task list panel, calendar panel and `Dowat` memory

2. Undo latest clearing
> To type: `undo`
	> * Recently cleared completed tasks are added back to `Dowat`

3. Clearing all completed and uncompleted tasks
> To type: `list /t /a` followed by `clear /t /a` 
	> * All uncompleted and completed tasks are first listed
	> * All uncompleted and completed tasks are cleared from task list panel, calendar panel and `Dowat` memory

4. Undo latest clearing
> To type: `undo` 
	> * Recently cleared uncompleted and completed tasks are added back to `Dowat`

5. Clearing all past events
> To type: `list /e /a` followed by `clear /e` 
	> * All past and upcoming events are first listed
	> * All past events are cleared from task list panel, calendar panel and `Dowat` memory

6. Undo latest clearing
> To type: `undo` 
	> * Recently cleared past events are added back to `Dowat`

7. Clearing all past and upcoming events
> To type: `list /e /a` followed by `clear /e /a` 
	> * All past and upcoming events are first listed
	> * All past and upcoming events are cleared from task list panel, calendar panel and `Dowat` memory

8. Undo latest clearing
> To type: `undo` 
	> * Recently cleared past and upcoming events are added back to `Dowat`

9. Clearing all completed tasks and past events
> To type: `list` followed by `clear` 
	> * All task and events are first listed
	> * All completed tasks and past events are cleared from task list panel, calendar panel and `Dowat` memory

10. Undo latest clearing
> To type: `undo` 
	> * Recently cleared completed tasks and past events are added back to `Dowat`

11. Clearing ALL tasks and events
> To type: `list` followed by `clear /a` 
	> * ALL task and events are first listed
	> * ALL ctasks and events are cleared from task list panel, calendar panel and `Dowat` memory

12. Undo latest clearing
> To type: `undo` 
	> * Recently cleared tasks and events are added back to `Dowat`
