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

As a ... | I want to... | so that ...
---------|--------------|------------
new user | see usage instructions | I can refer to instructions when i use the Task Manager.<br>
As a new user | view more information about a particular command | I can learn how to use various commands.<br>
user | add a task by specifying a task description and the date and time | I can record tasks that need to be done by that date and time.<br>
user | add a scheduled event by specifying the event name and duration and location | I can record events that I need to attend.<br>
user | add a task by specifying a task description only | I can record tasks that need to be done ‘some day’.<br>
user | delete a task | I can remove entries that I no longer need.<br>
user | list all tasks | I can see my tasks in a glance.<br>
user | edit the task descriptions | date and time | I can update the details of my tasks.<br>

<br>
#### Medium Priority `* *`

As a ... | I want to... | so that ...
---------|--------------|------------
user | undo my previous actions | I can revert any mistakes made.<br>
user | sort the tasks by the date and time | I can see my tasks according to their urgency.<br>
user | search for my tasks using some keywords | I can easily view tasks based on the specified keywords.<br>
user | mark tasks as completed or uncompleted | I can keep track of my tasks progress.<br>
user | specify a folder for data storage location | I can store the data file in a local folder controlled by a cloud syncing service.<br>

<br>
#### Low Priority `*`

As a ... | I want to... | so that ...
---------|--------------|------------
user | add tags to my task | I can categorise my tasks.<br>
user | input my dates and days in any format that I want | | it is easier for my usage.<br>
user | implement recurring tasks | I don’t have to add the task every week.<br>
advanced user | use shorter versions of the commands | I can type the commands faster.<br>
busy user | know what I can do next | I can fill up my free time slots.<br>
busy user | reserve time slots for one or more tasks that may not be confirmed | I know which to prioritise


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

#### Wunderlist
 **Strengths**
  * Able to add items to any list and assign deadlines and reminders
  * Requires few taps/interaction to create a simple to-do item
  * Natural language feature lets user type simple due dates, such as “tomorrow”, and Wunderlist will interprets and assigns accordingly
  * Available on multiple platforms, access easily from laptop or phone
  * Simple interface
  * Able to share lists with others for collaboration
  * Able to sort list

#### Todoist
 **Strengths**
  * Able to identify dates in tasks statements e.g 18oct, mon. Useful for Jim’s command line habits
  * Has smartphone and desktop applications. Useful for Jim’s requirement for portability
  * Has undo option
  * Allows the use of tags to classify different tasks e.g personal, school work.
  * Allows users to set priority levels to tasks
  * Has smartphone notifications for tasks due today
  * Multiple display formats: today, next 7 days, by tags

**Weaknesses**
  * Synced to the cloud. Users may have problems when there is no internet connection

#### Centrallo
 **Strengths**
  * Supports email forwarding from your personal mail into Centrallo
  * Allows the creation of list, notes and checklist, where the notes and checklist can be filed under a particular list
  * Items can be tagged as priorities which is consolidated in a ‘priorities’ tab
  * Notes, lists and checklists can be given different colours (7 to choose from)
  * Supports attachment of files (up to 25mb) to the notes
  * Offers 3 methods to sort the notes i.e. alphabetically, by created date and by updated date

**Weaknesses**
  * Only can move 1 note/checklist at a time
  * Limitations of 100 notes in total for a free account
  * Requires internet connection to access (no offline version)
  * No calendar view for a quick overview of all notes by dates
  * No quick overview of all notes in general (GUI highly resembles that of an email inbox - the notes occupy a narrow column on the screen with the rest of the screen occupied by the content of the notes)

#### Trello
 **Strengths**
  * Able to create various lists, allows good organisation
  * Able to add friends into the lists which allows communication between colleagues
  * Different types of lists can be created such as checklists, description texts, deadlines, labels and attachments
  * Can move, copy, subscribe, archive
  * Can be used on both mobile and desktop
  * Can set lists to be visible or invisible to others
  * Can use powerups which has special add ons to the lists, thus Jim can customise it to his needs
  * Has reminders
  * Activity summary to allow Jim to know what he did earlier
  * Can share lists which Jim can tell his colleagues tasks he needs to do/the team needs to do

**Weaknesses**
  * Too many options, too troublesome for Jim’s need to use single line command
  * Difficult to look for things Jim needs
  * No calendar view
  * More useful for teams than individuals
