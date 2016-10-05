##Appendix A: User Stories

Priority | As a ... | I want to ... | So that I can...
-------- | :--------| :------------ | :-----------
`* * *`  | new user | see usage instructions | refer to instructions when I forget how to use the application
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
Use case ends

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
3. Amethyst displays successful addition <br>
Use case ends

**Extensions**

2a. Invalid format entered by user

> 2a1. Amethyst displays error message <br>
  2a2. Amethyst prompts user to input in correct format <br>
  Use case ends

2b. Duplicate entry entered by user

> 2b1. Amethyst displays prompt message, requests user to differentiate entry <br>
  Use case ends

#### Use case: Mark task as done

**MSS**

1. User requests to list tasks
2. Amethyst shows a list of tasks
3. User requests to mark task(s) as done
4. Amethyst marks specified task(s) as done <br>
Use case ends

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. Amethyst displays an error message <br>
  Use case resumes at step 2
  
4a. Invalid format entered by user

> 4a1. Amethyst displays error message <br>
  4a2. Amethyst prompts user to input in correct format <br>
  Use case ends

#### Use case: Undo previous operation

**MSS**

1. User requests to execute a command
2. Amethyst executes specified command
3. User requests to undo previous action
4. Amethyst program returns to state before command was executed
5. (Steps 3 and 4 can be repeated to undo all operations up to and including first operation performed upon starting program) <br>
Use case ends

**Extensions**

2a. Invalid command format entered by user

> 2a1. Amethyst displays error message <br>
  2a2. Amethyst prompts user to input in correct command format <br>
  Use case ends

4a. No more operations to undo/ Program is at original state

> 4a1. Amethyst displays an error message, stating no more operations to undo <br>
  Use case ends

### Use case: Update task

**MSS**

1. User requests to list tasks
2. Amethyst shows a list of tasks
3. User requests to update specified task
4. Amethyst updates task(s) <br>
Use case ends

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. Amethyst displays an error message
  Use case resumes at step 2

4a. Invalid format entered by user

> 4a1. Amethyst displays error message
  4a2. Amethyst prompts user to input in correct format
  Use case ends

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
1.	User can add new lists of tasks, add new tasks to a selected list.
2.	User can include task descriptions, notes and set date, time in 24- or 12-hour format.
3.	User can add sub-tasks under a selected task.
4.	User can delete tasks and lists of tasks.
5.	User can set recurring tasks.
6.	User can undo marking a task done.
7.	User can set the priority level of a task. Only two priority levels are supported
8.	User can choose to view completed and uncompleted tasks. For completed tasks, they are listed in the order of completion, rather than in the order of task date or time. The task name, date/time and list name are shown. Viewing by list or date is not supported. For uncompleted tasks, users can choose to view by list, time and priority.
9.	User can choose from different themes of UI.

###Taskwarrior
####Taskwarrior is a well-developed command line task manager software. It has implemented many functionalities, but only the ones most applicable to the project are detailed in this document.
1.	User can execute all the must-have commands (CRUD, Undo, Mark Done etc.).
2.	User can also search or list by filters like status of tasks, due date, tags/virtual tags.
3.	User can also update the task name by using the prepend and append commands.
4.	User can set the priority level of a task.
5.	User can add recurring tasks.
6.	User can type very flexibly in terms of date and time. A wide range of accepted date/time formats is supported. User can also set duration of events by typing keywords like “3 weeks”.
7.	User can choose from different themes of UI.

###Wunderlist
####Wunderlist is a task manager application for mobile phones, tablets, and computers.
1.	User can add new lists of tasks, add new tasks to a selected list.
2.	User can set due date/time as well as reminder before the due date/time.
3.	User can add subtasks, notes and comments to a task.
4.	User can delete tasks and lists of tasks.
5.	User can set recurring tasks.
6.	User can search for tasks by entering keywords.
7.	User can undo marking a task done.
8.	User can “star” a task to indicate priority.
9.	User can sort the displayed tasks alphabetically, by creation date, due day, and priority.
10.	User can use the default short-cuts or customize the short-cuts.

###Todoist
####Todoist is an online and mobile task manager application.
1.	User can add new projects (lists) of tasks, add new tasks to a selected project (list).
2.	User can set due date/time as well as reminder before the due date/time.
3.	User can add comments to a task.
4.	User can delete tasks and projects of tasks.
5.	User can set recurring tasks.
6.	User can search for tasks by entering keywords.
7.	User can undo marking a task done.
8.	User can set different priority levels.
9.	User can sort the displayed tasks by name, priority, date.
10.	User can set new filters and labels to categorize different tasks. For example, the existing priority level is implemented as a filter.
11.	User can choose from different themes of UI.


