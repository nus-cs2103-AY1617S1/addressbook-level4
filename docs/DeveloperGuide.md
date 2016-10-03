##Appendix A: User Stories

Priority | As a ... | I want to ... | So that I can...
-------- | :--------| :------------ | :-----------
`* * *`  | new user | see usage instructions | refer to instructions when I forget how to use the App
`* * *`  | user | add a new task |
`* * *`  | user | delete a task | remove entries that are no longer relevant
`* * *`  | user | find a task by name | locate details of tasks without having to go through the entire list
`* * *` | user | undo an action |
`* * *` | user  | update a task |
`* * *` | user | mark a task as done | keep track of my progress
`* *`   | advanced user | set data storage location | sync data to the cloud and access tasks from multiple computers
`* *` | user | set command aliases | type more efficiently
`* *` | user | clear all tasks |

##Appendix B: Use Cases

#### Use case: Delete task

**MSS**

1. User requests to list tasks
2. Amethyst shows a list of tasks
3. User requests to delete task(s) in the list
4. Amethyst deletes task(s) <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. Amethyst displays an error message <br>
  Use case resumes at step 2

#### Use case: Add task

**MSS**

1. User requests to add task
2. Amethyst adds the task
3. Amethyst displays successful addition
Use case ends.

**Extensions**

2a. Invalid format entered by user

> 2a1. Amethyst displays error message
> 2a2. Amethyst prompts user to input in correct format
  Use case ends.

2b. Duplicate entry entered by user

> 2b1. Amethyst displays prompt message, requests user to differentiate entry <br>
  Use case ends.

#### Use case: Mark task as done

**MSS**

1. User requests to list tasks
2. Amethyst shows a list of tasks
3. User requests to mark task(s) as done
4. Amethysts marks specified task(s) as done
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. Amethyst displays an error message <br>
  Use case resumes at step 2

##Appendix C: Non-Functional Requirements
1. Amethyst should work on a desktop without network/internet connection
2. Command Line Interface is the primary mode of input
3. Amethyst is a standalone and is not a plug-in to another software
4. Amethyst does not make use of relational database
5. The data is stored locally in human editable text
6. Amethyst should work on windows 7 or later
7. Amethyst contains the critical functionality without the need of an installer
8. Amethyst does not requires the user to pay


##Appendix E: Product Survey
###Any.do
####Any.do is a mobile and online task manager application (not a command-line application).
1.     User can add new lists of tasks, add new tasks to a selected list.
2.     User can include task descriptions, notes and set date, time in 24- or 12-hour format.
3.     User can add sub-tasks under a selected task.
4.     User can set recurring tasks.
5.     User can undo marking a task done.
6.     User can set the priority level of a task. Only two priority levels are supported
7.    User can choose to view completed and uncompleted tasks. For completed tasks, they are listed in the order of completion, rather than in the order of task date or time. The task name, date/time and list name are shown. Viewing by list or date is not supported. For uncompleted tasks, users can choose to view by list, time and priority.
8.     User can choose from different themes of UI.
 
###Taskwarrior
####Taskwarrior is a well-developed command line task manager software. It has implemented many functionalities, but only the ones most applicable to the project are detailed in this document.
1.     User can execute all the must-have commands (CRUD, Undo, Mark Done etc.).
2.     User can also search or list by filters like status of tasks, due date, tags/virtual tags.
3.     User can also update the task name by using the prepend and append commands.
4.     User can set the priority level of a task.
5.     User can add recurring tasks.
6.     User can type very flexibly in terms of date and time. A wide range of accepted date/time formats is supported. User can also set duration of events by typing keywords like “3 weeks”.
7.     User can choose from different themes of UI.
