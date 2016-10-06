# Developer Guide 

* [Appendix A: User Stories](#appendix-a--user-stories)
* [Appendix B: Use Cases](#appendix-b--use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c--non-functional-requirements)
* [Appendix D: Glossary](#appendix-d--glossary)
* [Appendix E : Product Survey](#appendix-e-product-survey)



## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | new user | see usage instructions | refer to instructions when I forget how to use the App
`* * *` | user | add a new task |
`* * *` | user | delete a task | remove tasks that I no longer need
`* * *` | user | undo a command | undo mistakes easily
`* * *` | user | find a task by name | locate details of tasks without having to go through the entire list
`* * *` | user | have a one-shot approach to add tasks | minimize clicking and saves time
`* * *` | user | indicate a completion of Task | keep track of the Tasks which I have completed
`* * *` | user | set recurring tasks| repeatedly input the same event
`* * *` | user | track completed/uncompleted tasks | better manage my schedule
`* * *` | user | modify storage path | store data in my desired location
`* * *` | user | upload my schedule online and snyc them across devices | view my schedules when I am using different devices
`* *` | user | set priorities to my tasks | prioratise tasks that are more important to me
`* *` | user with many tasks in the address book | sort tasks by different priority/dateline | view them differently

{More to be added}

## Appendix B : Use Cases

(For all use cases below, the **System** is the `Task Manager` and the **Actor** is the `user`, unless specified otherwise)

#### Use Case : Add Task

**MSS**

1. User enters add command followed by the details of the task.
2. Task Manager creates the Task based on the details of the task and puts it in the database.
3. Task Manager displays the Task added.

Use case ends.

**Extensions**

1a. Details of the Task do not match format.

> 1a1. Task Manager prompts user to reenter Task.<br>
> 1a2. User inputs a new task.<br>
> Repeat 1a1 - 1a2 until user inputs the correct format.<br>
> Use case resumes at step 2.<br>

#### Use case: Delete Task

**MSS**

1. User enters command followed by the index of the Task to be deleted.
2. Task Manager does a search through the database and deletes the task.
3. Task Manager displays the list of Tasks left in the database.

Use case ends.

**Extensions**

1a. The index input by the user is not in the range of indices available.

> 1a1. Task Manager prompts user to reinput the index of the Task.<br>
> 1a2. User reinputs the name of the Task.<br>
> Repeat 1a1 - 1ab until user inputs valid index of the Task.<br>
> Use case resumes at step 2.<br>

#### Use Case : Edit Task

**MSS**

1. User enters edit command followed by the name of the Task to be edited and the information that is to be edited.
2. Task Manager does a search for the name of the Task in the database and updates the entry.
3. Task Manager displays the updated information of the Task.

Use case ends.

**Extentions**

1a. The name of the Task entered by the user does not exist.

> 1a1. Task Manager prompts user to reinput name of the Task.<br>
> 1a2. User reinputs name of Task.<br>
> Repeat 1a1 - 1a2 until the user inputs a valid name of Task.<br>
> Use case resumes at step 2.

1b. The information entered by the user does not follow the format.

> 1b1. Task Manager prompts user to reinput details of the Task in the given format.<br>
> 1b2. User reinputs details of the Task.<br>
> Repeat 1b1 - 1b2 until the user inputs a valid format for the Task.<br>
> Use case resumes at step 2.

#### Use Case : List

**MSS**

1. User enters list command followed by the addition filters of the listing.

Use case ends.

**Extensions**

1a. The filter input by the User is not valid.
> 1a1. System prints out error message and requests for another input.<br>
> Repeat step 1a1 until user inputs a valid filter for the list command.

1b. User wishes the Task Manager to list all the tasks.
> 1b1. Task Manager shows a list of all the Tasks in the Task Manager.

1c. User wishes the Task Manager to list only Tasks which has a time interval.
> 1c1. Task Manager shows a list of all the Tasks in the Task Manager with a time interval.

1d. User wishes the Task Manager to list only Tasks which does not have a time interval.
> 1d1. Task Manager shows a list of all the Tasks in the Task Manager without a time interval.

1e. User wishes the Task Manager to list all Tasks of a specified date.
> 1e1. Task Manager shows a list of all the Tasks in the Task Manager which has a deadline of the specified date.

1f. User wishes the Task Manager to list all Tasks within a specified range of dates.
> 1f1. Task Manager shows a list of all the Tasks in the Task Manager which has deadlines that fall between the specified range of dates.


#### Use Case: Find

**MSS**

1. User enters find command followed by the keywords of the search.
2. Task Manager performs the find command.
3. Task Manager displays the details of the Task.

Use case ends.

**Extensions**

3a. There are no Tasks with the keyword stated.
> 3a1. Task Manager displays 'No particular Task' message.<br>
> Use case ends.

#### Use Case : Notification at startup
Actor : `System`

**MSS**

1. User boots up the program.
2. System detects that the program has started.
3. System notifies the user of all the scheduled Tasks for the day.

Use case ends.

#### Use Case : Notification of reminders
Actor : `System`

**MSS**

1. System checks if there are reminders at the moment.
2. System prompts the user if there is reminder for any Tasks.
3. System requests for an update of reminder for the Task.
4. System saves the changes.

Use case ends.

**Extensions**

3a. User changes the timing of reminder. <br>
3b. User removes the reminder for the Task. <br>
Use case resumes at step 4.

#### Use Case : Modify Storage Path

**MSS**

1. User requests to modify the storage path.
2. Task Manager prompts user to key in new desired storage path.
3. User types in the new desired storage path.
4. Task Manager updates the new storage path.

Use case ends.

**Extensions**

2a. The given storage path is invalid.
> 2a1. Task Manager shows an error message.<br>
> Use case resumes at step 2.


## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 Tasks.
3. Should come with automated unit tests and open source code.
4. Should favor DOS style commands over Unix-style commands.
5. Should be able to use the application without access to internet.
6. Users should be able to use the application without much instructions.

{More to be added}

## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

## Appendix E : Product Survey

**1. WunderList**

_1.1 Pros_
> 1.1.1 Able to do type in the details of the activity in a command line. One shot approach.<br>
> 1.1.2 Able to be used offline.<br>
> 1.1.3 When online, able to sync across platforms.<br>
> 1.1.4 Able to sync to calendar which can be exported.<br>
> 1.1.5 Simple user interface.

_1.2 Cons_
> 1.2.1 Unable to block out uncertain schedules.<br>
> 1.2.2 Unable to start application just by a short command.<br>
> 1.2.3 Requires a lot of mouse clicking.<br>
> 1.2.4 Unable to set the time of the dateline.<br>
> 1.2.5 Unable to synchronize schedule without 3rd party calendar app.

**2. Fantastical**

_2.1 Pros_
> Calendar view for all activities.<br>
> Beautiful user interface.<br>
> Able to be used offline.<br>
> ble to sync across platforms when online.

_2.2 Cons_
> No one shot approach of typing details of activity into a command line.<br>
> Unable to block out uncertain schedules.<br>
> Requires a lot of mouse clicking.

**3. Any.do**

_3.1 Pros_
> Very simple user interface.<br>
> Able to sync across platforms when online.<br>
> Simple and clean user interface.<br>
> Has list view, time view, or combined view.

_3.2 Cons_
> No one shot approach of typing details of activity into a command line.

