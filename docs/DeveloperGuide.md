# Introduction
(to be done by brehmer)


# Table of Contents
* [Setting Up](#setting-up)
* [Product Architecture](#product-architecture)
* [Appendix A: User Stories](#appendix-a--user-stories)
* [Appendix B: Use Cases](#appendix-b--use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c--non-functional-requirements)
* [Appendix D: Glossary](#appendix-d--glossary)
* [Appendix E : Product Survey](#appendix-e-product-survey)

## Setting Up

## Product Architecture


## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | new user | see usage instructions | refer to instructions when I forget how to use the App
`* * *` | user | add a new activity |
`* * *` | user | delete an activity | remove activities that I no longer need
`* * *` | user | edit a command | modify the details of an activity
`* * *` | user | undo a command | revert back to the state before I made a wrong command
`* * *` | user | find an activity by name | locate details of activities without having to go through the entire list
`* * *` | user | have a one-shot approach to add activities | minimize clicking and save time
`* * *` | user | indicate a completion of an activity | keep track of activities which I have completed
`* * *` | user | set recurring activities| add repeated activities just once
`* * *` | user | track completed/uncompleted activities | better manage my schedule
`* * *` | user | modify storage path | store data in my desired location
`* * *` | user | search for my activities using keywords | locate activities quickly
`* * *` | user | upload my schedule online and snyc them across devices | view my schedules when I am using different devices
`* *` | user | set priorities to my activities | prioratise activities that are more important to me
`* *` | user with many activities in the address book | sort activities by different priority/dateline | have a clearer view of what needs to be completed first

{More to be added}

## Appendix B : Use Cases

(For all use cases below, the **System** is the `Menion` and the **Actor** is the `user`, unless specified otherwise)

<br>

#### Use Case : Add Task
**MSS**

1. User enters add command followed by the details of the task.
2. Menion creates the Task based on the details of the task and puts it in the database.
3. Menion displays the Task added.

Use case ends.

**Extensions**

1a. Details of the Task do not match format.

> 1a1. Menion prompts user to reenter Task.<br>
> 1a2. User inputs a new task.<br>
> Repeat 1a1 - 1a2 until user inputs the correct format.<br>
> Use case resumes at step 2.

<br>


#### Use Case : Delete Task

**MSS**

1. User enters command followed by the index of the Task to be deleted.
2. Menion does a search through the database and deletes the task.
3. Menion displays the list of Tasks left in the database.

Use case ends.

**Extensions**

1a. The index input by the user is not in the range of indices available.

> 1a1. Menion prompts user to reinput the index of the Task.<br>
> 1a2. User reinputs the name of the Task.<br>
> Repeat 1a1 - 1ab until user inputs valid index of the Task.<br>
> Use case resumes at step 2.

<br>

#### Use Case : Edit Task

**MSS**

1. User enters edit command followed by the name of the Task to be edited and the information that is to be edited.
2. Menion does a search for the name of the Task in the database and updates the entry.
3. Menion displays the updated information of the Task.

Use case ends.

**Extensions**

1a. The name of the Task entered by the user does not exist.

> 1a1. Menion prompts user to reinput name of the Task.<br>
> 1a2. User reinputs name of Task.<br>
> Repeat 1a1 - 1a2 until the user inputs a valid name of Task.<br>
> Use case resumes at step 2.

1b. The information entered by the user does not follow the format.

> 1b1. Menion prompts user to reinput details of the Task in the given format.<br>
> 1b2. User reinputs details of the Task.<br>
> Repeat 1b1 - 1b2 until the user inputs a valid format for the Task.<br>
> Use case resumes at step 2.

<br>

#### Use Case : Undo

**MSS**
1. User enters undo command.

Use case ends.

**Extensions**
1a. There is no previous command available to undo.

> 1b1. Menion displays error message.

Use Case Ends

<br>

#### Use Case : List

**MSS**

1. User enters list command followed by the addition filters of the listing.

Use case ends.

**Extensions**

1a. The filter input by the User is not valid.
> 1a1. System prints out error message and requests for another input.<br>
> Repeat step 1a1 until user inputs a valid filter for the list command.

1b. User wishes the Menion to list all the tasks.
> 1b1. Menion shows a list of all the Tasks in the Menion.

1c. User wishes the Menion to list only Tasks which has a time interval.
> 1c1. Menion shows a list of all the Tasks in the Menion with a time interval.

1d. User wishes the Menion to list only Tasks which does not have a time interval.
> 1d1. Menion shows a list of all the Tasks in the Menion without a time interval.

1e. User wishes the Menion to list all Tasks of a specified date.
> 1e1. Menion shows a list of all the Tasks in the Menion which has a deadline of the specified date.

1f. User wishes the Menion to list all Tasks within a specified range of dates.
> 1f1. Menion shows a list of all the Tasks in the Menion which has deadlines that fall between the specified range of dates.

<br>

#### Use Case: Find

**MSS**

1. User enters find command followed by the keywords of the search.
2. Menion performs the find command.
3. Menion displays the details of the Task.

Use case ends.

**Extensions**

3a. There are no Tasks with the keyword stated.
> 3a1. Menion displays 'No particular Task' message.<br>
> Use case ends.

<br>

#### Use Case : Modify Storage Path
**MSS**

1. User requests to modify the storage path.
2. Menion prompts user to key in new desired storage path.
3. User types in the new desired storage path.
4. Menion updates the new storage path.

Use case ends.

**Extensions**

2a. The given storage path is invalid.
> 2a1. Menion shows an error message.<br>
> Use case resumes at step 2.

<br>

## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 Tasks.
3. Should come with automated unit tests and open source code.
4. Should favor DOS style commands over Unix-style commands.
5. Should be able to save activities offline, so that I can use the application when the user does not have internet access.
6. Should be able to use the application without much instructions.

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
> 2.1.1 Calendar view for all activities.<br>
> 2.1.2 Beautiful user interface.<br>
> 2.1.3 Able to be used offline.<br>
> 2.1.4 Able to sync across platforms when online.

_2.2 Cons_
> 2.2.1 No one shot approach of typing details of activity into a command line.<br>
> 2.2.2 Unable to block out uncertain schedules.<br>
> 2.2.3 Requires a lot of mouse clicking.

**3. Any.do**

_3.1 Pros_
> 3.1.1 Very simple user interface.<br>
> 3.1.2 Able to sync across platforms when online.<br>
> 3.1.3 Simple and clean user interface.<br>
> 3.1.4 Has list view, time view, or combined view.

_3.2 Cons_
> 3.2.1 No one shot approach of typing details of activity into a command line.


