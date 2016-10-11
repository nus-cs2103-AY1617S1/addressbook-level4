# Developer Guide

* [Appendix A: User Stories](#appendix-a-user-stories)
* [Appendix B: Use Cases](#appendix-b-use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c-non-functional-requirements)
* [Appendix D: Glossary](#appendix-d-glossary)
* [Appendix E: Product Survey](#appendix-e-product-survey)

## Appendix A: User Stories

Priorities:

* `* * *` -- High (must have)
* `* *` -- Medium (nice to have)
* `*` -- Low (unlikely to have)

User categories:

* User -- Any user
* New user -- User that has used Task Tracker for less than 100 hours
* Busy user -- User that has a high density of deadlines and/or events over the
  next 1 month
* Power user -- A user that likes to do things with as few interactions
  (keystrokes, mouseclicks, confirmations) as possible.
* Group user -- User that has other people working on the same task

Priority | As a ... | I want to ... | So that I can...
-------- | :------- | :------------ | :---------------
`* * *`  | New user | See the manual | refer to manual when I forget how to use the App
`* * *`  | User | Add an event to the task manager | keep track of it and be notified when it is approaching.
`* * *`  | User | Add a deadline to the task manager | keep track of it and be notified when it is approaching.
`* * *`  | User | Add a floating task to the task manager | Keep track of it and remember to do it when I'm free
`* * *`  | User | View my floating tasks | keep track of them.
`* * *`  | User | Search specific tasks by keywords |
`* * *`  | Power user | Have shortcut keys to launch the app | Launch the app quickly
`* * *`  | Power user | Have shortcut keys to minimise the app | Hide the app with only the keyboard
`* *`    | User | Mark a deadline as finished before the due time | Remove it from the notification list and archive it.
`* *`    | User | Mark a floating task as finished | Remove it from my floating task list.
`* *`    | User who has events taking place at multiple locations | Add the location of an event | Be reminded of where to go.
`* *`    | User | Have the app notify me of the error in my command, and suggest the right command when I make a typo/forget the format of the command | Enter in the correct command immediately without having to open up the manual.
`* *`    | Busy user | View what events or deadlines are scheduled over a range of time | Ensure that the event does not clash with other events or deadlines.
`* *`    | User | Revise the due datetime for a certain deadline. | Keep track of it and avoid creating a new deadline when the time has been revised.
`* *`    | User | Revise the timeslot for a certain event | Keep track of it and avoid creating a new event when the time has been revised.
`* *`    | Busy user | Generate a list of all empty time slots in a given period | Choose a free time slot to create new events or tasks.
`* *`    | User | Undo an action | Restore tasks deleted by accident.
`*`      | User who is unable to remember details of each task | Add a short description under the name of each task in my schedule | Know how to do the task and where, even if I forget these details by any chance.
`*`      | Busy user | Tags such as coloured dots, or icons, for each task which indicates the priority of the task (on a scale of 1-3) | Able to decide which task needs to be completed urgently
`*`      | Group user | Option to categorize my task as a "Group activity" and automatically send notifications (through mail or other social networking platforms) to all other users who are in my team, whenever I make any changes to our work schedule for the group activity; and send them reminders about upcoming deadlines for the tasks. Every time I add a new task, I should have also an option to either include it to an existing group activity or add it to a new group activity (different commands for each of these operations). | Improve my work efficiency, and make sure everyone in my team are aware of the work schedule of our project.
`* `     | User who needs to be reminded of the task before the deadline date. | Set reminders at customized times before the deadline. | Have enough time to complete the task before deadline, even if I forgot to do it.

## Appendix B: Use Cases

**Software System**: TaskTracker

**Actor**: User

### Use case: Add an event

**MSS**

1. User requests to add an event with the specified name, start date/time and
   end date/time.

2. TaskTracker adds the event to the database, and notifies the user that the
   event was successfully added.

   Use case ends.

**Extensions**

1a. The start datetime occurs after the end datetime.

> 1a1. TaskTracker notifies the user that the start datetime and end datetime
>      are invalid.

> Use case ends.

1b. The range of time specified by the start datetime and end datetime occurs
    in the past.

> 1b1. TaskTracker warns the user that the event is in the past.

> Use case resumes from step 2.

1c. The name of the event contains invalid characters.

> 1c1. TaskTracker notifies the user that the event name contains which invalid
>      character(s).

> Use case ends.

1d. The name of the event contains leading/trailing whitespace.

> 1d1. TaskTracker silently strips the leading/trailing whitespace from the
>      name.

> Use case resumes from step 2.

### Use case: Add a deadline

**MSS**

1. User requests to add a deadline with the specified name and end date/time.

2. TaskTracker adds the deadline to the database, and notifies the user that
   the deadline was successfully added.

   Use case ends.

**Extensions**

1a. The end datetime occurs in the past.

> 1a1. TaskTracker warns the user that the end datetime is in the past.

> Use case resumes from step 2.

1b. The name of the deadline contains invalid characters.

> 1b1. TaskTracker notifies the user that the deadline name contains which
>      invalid character(s).

> Use case ends.

1c. The name of the deadline contains leading/trailing whitespace.

> 1c1. TaskTracker silently strips the whitespace from the name.

> Use case resumes from step 2.

### Use case: Add a floating task

**MSS**

1. Use requests to add a floating task with the specified name.

2. TaskTracker adds the floating task to the database at the lowest priority,
   and notifies the user that the floating task was successfully added.

   Use case ends.

**Extensions**

1a. The name of the floating task contains invalid character(s).

> 1a1. TaskTracker notifies the user that the floating task name contains which
>      invalid character(s).

> Use case ends.

1b. The name of the floating task contains leading/trailing whitespace.

> 1b1. TaskTracker silently strips the leading/trailing whitespace from the
>      name.

> Use case resumes from step 2.

### Use case: View all floating tasks

**MSS**

1. User requests to view all floating tasks.

2. TaskTracker displays all floating tasks in the database as a list, ordered
   from highest to lowest priority. <br>
   Use case ends.

**Extensions**

1a. There are no floating tasks in the database.

> 1a1. TaskTracker notifies the user that there are no floating tasks in the
>      database.

> Use case ends.

### Use case: Revise the due time of a deadline task

**MSS**

1. User requests to update the due datetime of a certain deadline with new
   date/time information.

2. TaskTracker revises the deadline to the new time, and notifies the user that
   the due date/time was successfully revised.

   Use case ends.

**Extensions**

1a. The requested deadline task does not exist.

> 1a1. TaskTracker informs the user that the requested deadline does not exist.

> Use case ends.

1b. The new time is the same as the previous due datetime.

> 1b1. TaskTracker informs the user that the datetime remains unchanged.

> Use case ends.

1c. The new date/time occurs in the past.

> 1c1. TaskTracker warns the user that the end datetime is in the past.

> Use case resumes from step 2.

### Use case: Revise the time of an event

**MSS**

1. User requests to revise the the time of a certain deadline with new
   date/time information.

2. TaskTracker revises the event to a new time slot, and notifies the user that
   the time was successfully revised.

   Use case ends.

**Extensions**

1d. The requested event does not exist.

> 1a1. TaskTracker informs the user that the requested event does not exist.

> Use case ends.

1b. The range of time specified by the start datetime and end datetime
    intersects with the start-end datetime range of other event(s).

> 1b1. TaskTracker warns the user that the event clashes with which event(s).

> Use case resumes from step 2.

1c. The start datetime occurs after the end datetime.

> 1c1. TaskTracker notifies the user that the start datetime and end datetime
> are invalid.

> Use case ends.

1d. The range of time specified by the start datetime and end datetime occurs
    in the past.

> 1e1. TaskTracker warns the user that the event is in the past.

> Use case resumes from step 2.

1e. The new datetime is the same as the existing datetime.

> 1e1. TaskTracker informs the user that the datetime remains unchanged.

> Use case ends.

### Use case: mark a floating task/deadline as finished

**MSS**

1. User requests to mark a certain floating task/deadline as finished.

2. TaskTracker marks the task as finished and informs the user.
   Use case ends.

**Extensions**

1a. The floating task does not exist.

> 1a1. TaskTracker informs the user that the floating task does not exist.

> Use case ends.

### Use case: Generate a list of empty time slots

**MSS**

1. User request to generate a list of free time slots in a certain period with
   certain time duration.

2. TaskTracker lists all possible time slots in that period <br>
   Use case ends.

**Extensions**

1a. The time period requested is invalid (wrong format or time in the past)

> 1a1. TaskTracker shows an error message.

> Use case ends.

1b. The input duration is in wrong format

> 1b1. TaskTracker shows an error message to inform the error.

> Use case ends.

2a. There is no feasible time slot

> 2a1. TaskTracker shows an empty list and throw a message saying there is no
> avaliable time slots for the user in the given time period.

> Use case ends.

### Use case: Add priority tags

**MSS**

1. The user requests to add a priority tag for a specified event.

2. TaskTracker prompts the user to select the priority level of the event -
   "Very Important"/ "Important"/ "Not important".

3. The user selects the appropriate priority level for the event from these
   options.

4. TaskManager updates the priority of the event with the specified priority
   and it is displayed on the user’s schedule with a corresponding colored
   circle (for each priority level).

5. The user is notified that the update was successful.

   Use case ends.

**Extensions**

1a. The user places a request to add a priority tag, but does not add it.

> 1a1. User presses "Esc". TaskTracker goes back to the normal view of the
> schedule. Now even if the user clicks on any event, priority tag options will
> not be displayed as the TaskTracker switched from Priority Tag view to Normal
> view.

> Use case ends.

### Use case: Add new event under a specific "group activity"

**MSS**

1. When the user is adding a new event, he selects the option "Group Activity"
   which is present in the same window.

2. The existing group activities are displayed and the user chooses the one
   which is related to the new event that he is adding.

3. If he wants to create a new group activity, then the user places a request
   to add new group activity in the “Group Activity” window.

5. The user then enters the details of the group activity (such as name of the
   group activity and email addresses of the people in the group).

6. TaskTracker automatically adds the event to this new activity.

7. TaskTracker notifies the user that the event has been added to the schedule.

   User case ends.

**Extensions**

1a. The user enters an invalid email address of a group member, while creating
a new group activity.

> 1a1. TaskTracker displays the error message - "Invalid email".

> Use case ends.

1b. The user enters a group activity name that already exists, while adding a
    new group activity.

> 1b1. TaskTracker displays an error message "Name already exists".

> Use case ends.

### Use case: Add event description

**MSS**

1. The user requests to add a new event.

2. The user writes a small description about the event in the "Description"
   field, present in the "Add new event" window.

3. The event is added and the user is notified about the new event added.

   User case ends.

### Use case: Set reminders

**MSS**

1. When adding a new event, the user adds the time at which he wants to set a
   reminder, before the event occurs. The time is entered in day/hours/minutes
   format.

2. The new event is added to the schedule and the user is notified about the
   addition of the new event.

   User case ends.

**Extensions**

1a. The user enters the time in a wrong format.

> 1a1. TaskTracker shows an error message "Invalid time format"

> User case ends.

### See the manual

**Use case: see the manual**

**MSS**

1. User requests to see the manual.

2. TaskTracker shows manual.

   Use case ends.

### Search the manual

**Use case: search the manual**

**MSS**

1. User request to search the manual for a specific command.

2. TaskTracker shows the specific manual command.

   Use case ends

**Extensions**

1a. The command to be searched does not exist

> 1a1. TaskTracker informs the user that the command does not exist.

> Use case ends

### Undo an action

**User case: Undo an action, such as restoring a deleted a task**

**MSS**

1. The user wants to undo a completed action

2. The previous action is undone. Task manager is restored to state before the
   completed action.

   User case ends.

**Extensions**

1a. There is no action to undo

> 1a1. TaskTracker shows an error message "no action to undo"

> User Case ends.

## Appendix C: Non Functional Requirements

1. Should work on any mainstream OS as long as it has Java `1.8.0_60` or higher
   installed.

2. Should come with automated unit tests and open source code.

3. Have shortcut keys to launch/minimise the app.

4. Have the app notify me of the error in my command, and suggest the right
   command when I make a typo/forget the format of the command

## Appendix D: Glossary

<dl>
  <dt>Datetime</dt>
  <dd>Date and Time</dd>

  <dt>Task</dt>
  <dd>A unit of information in the database. Each task has a name.</dd>

  <dt>Event</dt>
  <dd>Task that has a start datetime and end datetime</dd>

  <dt>Deadline</dt>
  <dd>Task that has an end datetime only.</dd>

  <dt>Floating task</dt>
  <dd>A task that has neither a start datetime not end datetime. It has a implicit priority derived from its position in the floating task list  in the database.</dd>

  <dt>Time slot</dt>
  <dd>A time slot is referring to a period of time</dd>
</dl>

## Appendix E: Product Survey

### Todo.txt

Full product survey [here](productsurveys/todo.txt.md)

Jim's Requirement | Todo.txt Support
:---------------- | ----------------
Summoned quickly from anywhere | No
Keyboard-oriented. Jim can type commands in "one shot" | Yes
Works offline | Yes
Simple search | Yes
CRUD support for floating tasks | Full
CRUD support for deadlines | Partial
CRUD support for events | Partial
Keep track of which items are done/not done | Yes
Look for a suitable slot to schedule an item | No
Ability to "block" multiple slots | No
Flexibility in command line format | Tiny
Undo operations | No

Todo.sh, as a command-line application, is fully keyboard oriented and thus
suitable for power users who prefer entering commands in one shot. As it only
operates on local text files, it works fully offline.

Furthermore, its data model and user interface is specially geared towards the
creation, reading, updating and deleting (CRUD) of floating tasks, which it
does quite well. One exception is the interface to edit the name of a task,
which is very clunky as it requires the user to repeat existing
information.

While the simplicity and flexibility of its data model means that CRUD
operations on deadlines and events are technically feasible, the lack of
explicit support from the application and data model means that the user
interface is poor and error checking is lacking. Users who store their
deadlines and events in todo.txt, without understanding how it works under the
hood, would thus be in for some nasty surprises.

Furthermore, the lack of explicit support for deadlines and events means that
the application has no support at all for looking for a slot to schedule an
item and the ability to “block” multiple time slots, as it has no concept of
time at all.

Finally, it has no support for undoing operations. While this is alleviated
somewhat as it asks for confirmation before deleting tasks, all other
operations are done without user confirmation.

### Todoist

Full product survey [here](productsurveys/Todoist.md)

Jim’s Requirement | Todoist support
:---------------- | ------------------
Summoned quickly from anywhere | No
Keyboard-oriented. Jim can type commands in “one shot”. | No
Keyboard-oriented. Jim can use keyboard shortcut to increase efficiency. | Yes
Works offline | Yes
Simple Search | Yes
CRUD support for floating tasks | Full
CRUD support for deadlines | Full
CRUD support for events | None
Keep track of which items are done/not done | Yes
Look for a suitable time schedule a task. | Tiny
Ability to “block” multiple slots. | No
Flexibility in command line format. | No
Undo operations | Partial

Todoist is a half keyboard oriented cross-platform applications, with plenty of
useful keyboard short-cut on both PC and Web clients. It can work both online
or offline on mobile phone or personal computer.

It fully supports creation, reading, updating and deleting (CRUD) function of
floating tasks and deadline-like task. The user interface is well designed and
user can perform their actions quite easily through it. Besides, user can also
conduct simple searching action among all task he or she has created. However,
Todoist also does not support the functionality to add event-like task at all.

Also for premium account, Todoist also gives the support of tracking the task
that has already been done or finished, which gives convenience for user to
record their working history.

Besides, Todoist can automatically suggest how many tasks have already existed
on a certain day when creating the tasks. But since Todoist does not support
event-like task, it also has no support at all for generating suggestion of
suitable time-slots or placing multiple time slots/blocks when creating the
tasks.

Moreover, Todoist does support undo-actions, but only for the last one actions
the user performs.

Furthermore, there are several features that we could learn from Todoist. The
first one is the "Quick-Add" function which enables the user to add either
floating task or deadline task in a single window. The second one is the "Undo"
command, which enables the user to undo the last command immediately when them
perform wrongly.

### Wunderlist

Full product survey [here](productsurveys/Wunderlist.md)

Wunderlist in brief: Written in Javascript and PHP, Wunderlist is a task
manager application with a simple, intuitive,and beautiful User Interface that
is installed locally on the hard disk. It has server functions to share todo
lists and is able to sync information using a Wunderlist account. Thus CRUD of
local tasks work offline, but with online, additional syncing, sharing and
collaborating features are accessible.

So, how well does Wunderlist satisfy Jim's requirements?

| Jim's Requirement | Wunderlist support |
| --- | --- |
| Summoned quickly from anywhere | No |
| Keyboard-oriented. Jim can type commands in "one shot". | Partially |
| Works offline | Yes |
| Simple Search | Yes |
| CRUD support for floating tasks | Full |
| CRUD support for deadlines | Full |
| CRUD support for events | None |
| Keep track of which items are done/not done | Yes |
| Look for a suitable slot to schedule an item. | No |
| Ability to "block" multiple slots. | No |
| Flexibility in command line format. | No |
| Undo operations | Tiny |

### Google Calendar

Full product survey [here](https://docs.google.com/document/d/1ELun1gQUiVAxC6it-16jikFRwAePTXS5Xri7GdhUnL8/edit?usp=sharing)

Jim's Requirement | Google Calendar
:---------------- | ----------------
Summoned quickly from anywhere | Yes
Keyboard-oriented. Jim can type commands in "one shot" | Yes
Works offline | Partially
Simple search | Yes
CRUD support for floating tasks | No
CRUD support for deadlines | Yes
CRUD support for events | Partially
Keep track of which items are done/not done | No
Look for a suitable slot to schedule an item | Yes
Ability to "block" multiple slots | Fully
Flexibility in command line format | No
Undo operations | Yes

Google Calendar is essentially a time-management mobile and web application
developed by Google. It is a good platform for office workers, such as Jimmy to
organize their schedule. It has most of the features that an office worker like
Jim would require; for example, Add/Edit/Delete tasks, automatically find free
slots in the schedule to add new tasks, “quick add”, ability to block multiple
tasks, etc. It can also support features such as import, export and sync with
desktop programs such as Outlook  and also, with mobile devices. However, it
also has a few drawbacks for workers like Jim - New tasks or modification of
existing tasks cannot be performed when the user is offline, it does not
display floating tasks, and also does not display the status of a task as
"done" or "not done".

We have taken some reference features from Google Calendar to implement in our
own time management software. Some of these include having an feature to find
empty time slots in the user’s schedule. While adding an event in Google
Calendar, the users have an option called "Find Time" in which all the free
slots in their schedule are displayed and they can choose any one for adding
their task.
