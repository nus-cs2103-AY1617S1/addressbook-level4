# Developer Guide

## Appendix A: User Stories
Priorities: 
- `* * *` -- high priority, must have
- `* *` -- medium priority, good to have
- `*` -- low priority, unlikely to have

Priority | As a ... | I want to ... | So that I can...
-------- | :------- | :------------ | :-----------
`* * *` | new user | see usage instructions | refer to instructions when I forget how to use the App
`* * *` | user | add a new event | 
`* * *` | user | mark an event as completed |
`* * *` | user | change details of an event | update events according to my schedule
`* * *` | user | delete an event | remove entries that I cannot complete
`* * *` | user | list all uncompleted events | see what I have left to do
`* *`	| user | schedule multiple time blocks for an event | tentatively plan events
`*`		| user | sort events by deadline | know which events are urgent
`*` 	| user with many tasks | sort events by priority | know which upcoming events are important

(more to be added!)


## Appendix B: Use Cases
(For all use cases below, the `System` is Wudodo and the `Actor` is the user, unless specified otherwise)

### Use case: Add task
**MSS**
1. User inputs task details
2. System stores task details in database (???? WTF)

**Extensions**
1a. User inputs details in incorrect format
> System displays error message
> Use case resumes at step 1



### Use case: Find next task
**MSS**
1. User requests next task
2. System shows suggested next task
3. System prompts user for confirmation of task
3. User confirms task
(Use case ends)

**Extensions**
2a. No remaining incomplete tasks
> System shows error message
> Use case ends

4a. User rejects suggested task
> System looks for next suggested task
> Use case resumes at step 2

### Use case: Mark task as completed

**MSS**
1. User requests to complete a specific task in the list
4. System marks task as completed
(Use case ends)

**Extensions**

1a. The given task is invalid
> 3a1. System shows an error message
> Use case resumes at step 2

2a. Task is already completed
> Use case ends

### Use case: Delete task
**MSS**
1. User requests to list tasks
2. System shows a list of tasks
3. User requests to delete a task in the list
4. System deletes selected task from the list
(Use case ends)

**Extensions**

2a. The list is empty
> Use case ends

3a. The given task is invalid
> 3a1. System shows an error message
> Use case resumes at step 2

## Appendix C: Product Review

### Desired features
This list of features is taken from the [Handbook](http://www.comp.nus.edu.sg/~cs2103/AY1617S1/contents/handbook.html#handbook-project-product).
1. Command line-based UI
2. Take in events with specified start and end time
2. Block out multiple tentative start/end times for an event
2. Take in events with deadlines but no specified start/end time
3. Take in floating tasks  without user-specified start end times
5. Mark items as done
4. Track uncompleted to-do items past end time
5. Quick-add events
6. Access to-do list without internet connection
7. Easily choose which to-do item to do next
8. Keyword search
8. Specify data storage location

### Todoist[^1]

Meets specifications:
- Setting deadlines allowed
- Floating tasks allowed
- Can easily mark items as done by clicking on them
- Keeps track of uncompleted deadlines in chronological order
- Natural language quick-add function gives flexibility when adding to-do items
- Desktop app allows offline access
- Organization of inbox, as well as list of items due today and in the new week allows easy choice of what to-do item to do next
- Keyword search (implemented with nifty shortcut!)


Does not meet specifications:
- Events cannot block off specific start and end times
- Not Command Line Interface
- Specify data storage location (but has its own cloud storage)

Interesting features:
- Use of tagging to split to-do items into different categories
- Use of colours to mark different levels of priority, drawing visual attention to high-priority items
- Shortcut allows postponing to tomorrow, next week
- Reminder feature (requires in-app purchase)
- Can schedule recurring events using natural language commands
- Use of keyboard shortcuts while in app [^2]


### Wunderlist


### Google Calendar

### Apple Reminders
Meets specifications:
- Allows events with deadlines
- Allows floating events
- Click to mark item as done
- Lists uncompleted items in chronological order past end-time (under "Scheduled")
- Allows variable natural language input (buggy)

Does not meet specifications:
- Not command line-based UI
- Cannot take in events with specified start time
- Cannot specify data storage location
- Keyword search is not very user-friendly

Interesting features:
- Desktop reminders
- Multiple separate to-do lists

[^1]: https://en.todoist.com/
[^2]: https://support.todoist.com/hc/en-us/articles/205063212-Keyboard-shortcuts