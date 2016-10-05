# Developer Guide 

* [Setting Up](#setting-up)
* [Design](#design)
* [Implementation](#implementation)
* [Testing](#testing)
* [Dev Ops](#dev-ops)
* [Appendix A: User Stories](#appendix-a--user-stories)
* [Appendix B: Use Cases](#appendix-b--use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c--non-functional-requirements)
* [Appendix D: Glossary](#appendix-d--glossary)
* [Appendix E : Product Survey](#appendix-e-product-survey)

<br>
## Setting up

#### Prerequisites

1. **JDK `1.8.0_60`**  or later<br>

    > Having any Java 8 version is not enough. <br>
    This app will not work with earlier versions of Java 8.
    
2. **Eclipse** IDE
3. **e(fx)clipse** plugin for Eclipse (Do the steps 2 onwards given in
   [this page](http://www.eclipse.org/efxclipse/install.html#for-the-ambitious))
4. **Buildship Gradle Integration** plugin from the Eclipse Marketplace

<br>
#### Importing the project into Eclipse

0. Fork this repo, and clone the fork to your computer
1. Open Eclipse (Note: Ensure you have installed the **e(fx)clipse** and **buildship** plugins as given 
   in the prerequisites above)
2. Click `File` > `Import`
3. Click `Gradle` > `Gradle Project` > `Next` > `Next`
4. Click `Browse`, then locate the project's directory
5. Click `Finish`

  > * If you are asked whether to 'keep' or 'overwrite' config files, choose to 'keep'.
  > * Depending on your connection speed and server load, it can even take up to 30 minutes for the set up to finish
      (This is because Gradle downloads library files from servers during the project set up process)
  > * If Eclipse auto-changed any settings files during the import process, you can discard those changes.

<br>
#### Troubleshooting

**Problem: Eclipse reports compile errors after new commits are pulled from Git**
* Reason: Eclipse fails to recognize new files that appeared due to the Git pull. 
* Solution: Refresh the project in Eclipse:<br> 
  Right click on the project (in Eclipse package explorer), choose `Gradle` -> `Refresh Gradle Project`.
  
**Problem: Eclipse reports some required libraries missing**
* Reason: Required libraries may not have been downloaded during the project import. 
* Solution: [Run tests using Gardle](UsingGradle.md) once (to refresh the libraries).
 

<br> 
## Design

(TODO include UML diagram)


<br>
## Appendix A : User Stories

#### High Priority `* * *`

As a new user, I want to **see usage instructions** so that I can **refer to instructions when i use the Task Manager.**<br>
As a new user, I want to **view more information about a particular command** so that I can **learn how to use various commands.**<br>
As a user, I want to **add a task by specifying a task description and the date and time**, so that I can **record tasks that need to be done by that date and time.**<br>
As a user, I want to **add a scheduled event by specifying the event name and duration and location**, so that I can **record events that I need to attend.**<br>
As a user, I want to **add a task by specifying a task description only**, so that I can **record tasks that need to be done ‘some day’.**<br>
As a user, I want to **delete a task** so that I can **remove entries that I no longer need.**<br>
As a user, I want to **list all tasks** so that I can **see my tasks in a glance.**<br>
As a user, I want to **edit the task descriptions, date and time** so that I can **update the details of my tasks.**<br>

#### Medium Priority `* *`

As a user, I want to **undo my previous actions** so that I can **revert any mistakes made.**<br>
As a user, I want to **sort the tasks by the date and time** so that I can **see my tasks according to their urgency.**<br>
As a user, I want to **search for my tasks using some keywords** so that I can **easily view tasks based on the specified keywords.**<br>
As a user, I want to **mark tasks as completed or uncompleted** so that I can **keep track of my tasks progress.**<br>
As a user, I want to **specify a folder for data storage location** so that I can **store the data file in a local folder controlled by a cloud syncing service.**<br>

#### Low Priority `*`

As a user, I want to **add tags to my task** so that I can **categorise my tasks.**<br>
As a user, I want to **input my dates and days in any format that I want**, so that **it is easier for my usage.**<br>
As a user, I want to **implement recurring tasks** so that **I don’t have to add the task every week.**<br>
As an advanced user, I want to **use shorter versions of the commands** so that I can **type the commands faster.**<br>
As a busy user, I want to **know what I can do next** so that I can **fill up my free time slots.**<br>
As a busy user, I want to **reserve time slots for one or more tasks that may not be confirmed** so that I **know which to prioritise**


<br>
## Appendix B : Use Cases

(For all use cases below, the **System** is the Task Manager and the **Actor** is the user, unless specified otherwise)

####Use case: Add task

**MSS**

1. User requests to add task
2. TaskManager adds task and confirms that user has added the task 

**Extensions**

2a. The task has the exact same details as an existing task

> TaskManager shows error message: “This task has already been created.”
	
<br>
#### Use case: Edit task

**MSS**

1. User requests to list task
2. TaskManager shows a list of task
3. User requests to edit a specific field of the task
4. TaskManager edits the field

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. TaskManager shows an error message
  Use case resumes at step 2

3b. The given field tag is empty

> 3b1. TaskManager asks to add to the field instead
> 3b2. User confirms addition
> 3b3. TaskManager adds to the specified field

<br>
#### Use case: Delete task

**MSS**

1. User requests to list task
2. TaskManager shows a list of task
3. User requests to delete a specific task in the list
4. TaskManager display confirmation message
5. User enters ‘Yes’ to confirm delete
6. TaskManager deletes the task 
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. TaskManager shows an error message
  Use case resumes at step 2

5a. The user input is invalid

> 5a1. TaskManager shows an error message
> 5a2. User enters input
Step 5a1 and 5a2 are repeated until the user enters a valid input

<br>
#### Use case: List by
	
**MSS**

1. User requests to list by a certain category
2. TaskManager shows a list of task of that category

**Extensions**

2a. Category is unavailable

> Use case ends
	
2b. Category is by date

> TaskManager shows list of tasks by date, using start time of EVENTS and end time of DEADLINES

2c. Category is by tag

> TaskManager shows list of tasks by the particular tag in order of index


<br>
## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 tasks.
3. Should come with automated unit tests and open source code.
4. Should favor DOS style commands over Unix-style commands.

{More to be added}


<br>
## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

<br>
## Appendix E : Product Survey

(TODO: Add a summary of competing products)

