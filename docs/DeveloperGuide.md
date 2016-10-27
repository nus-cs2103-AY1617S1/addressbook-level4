# Developer Guide 

* [Setting Up](#setting-up)
* [Design](#design)
* [Implementation](#implementation)
* [Testing](#testing)
* [Continuous Integration](#continuous-integration)
* [Making a Release](#making-a-release)
* [Managing Dependencies](#managing-dependencies)
* [Appendix A: User Stories](#appendix-a--user-stories)
* [Appendix B: Use Cases](#appendix-b--use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c--non-functional-requirements)
* [Appendix D: Glossary](#appendix-d--glossary)
* [Appendix E: Product Survey](#appendix-e--product-survey)


## Setting up

#### Prerequisites

1. **JDK `1.8.0_60`**  or later<br>

    > Having any Java 8 version is not enough. <br>
    This app will not work with earlier versions of Java 8.
    
2. **Eclipse** IDE
3. **e(fx)clipse** plugin for Eclipse (Do the steps 2 onwards given in
   [this page](http://www.eclipse.org/efxclipse/install.html#for-the-ambitious))
4. **Buildship Gradle Integration** plugin from the Eclipse Marketplace


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

## Design

<img src="images/Architecture.png" width="600"><br>
The **_Architecture Diagram_** given above explains the high-level design of the App.
Given below is a quick overview of each component.

`Main` has only one class called [`MainApp`](../src/main/java/seedu/address/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connect them up with each other.
* At shut down: Shuts down the components and invoke clean up method where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.
Two of those classes play an important role at the architecture level.
* `EventsCentre` : This class (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
  is used to by componnents to communicate with other components using events (i.e. a form of _Event Driven_ design)
* `LogsCenter` : Used by many classes to write log messages to the App's log files.

The rest of the App consists four components.
* [**`UI`**](#ui-component) : The UI of tha App.
* [**`Logic`**](#logic-component) : The command executor.
* [**`Model`**](#model-component) : Holds the data of the App in-memory.
* [**`Storage`**](#storage-component) : Reads data from, and writes data to, the hard disk.

Each of the four components
* Defines its _API_ an interface with the same name as the Component. `Logic.java`
* Exposes its functionality using a `{Component Name}Manager` class e.g. `LogicManager.java`

The _Sequence Diagram_ below shows how the components interact for the scenario where the user issues the
command `delete 3`.

<img src="images\SDforDeletePerson.png" width="800">

>Note how the `Model` simply raises a `ModelChangedEvent` when the model is changed,
 instead of asking the `Storage` to save the updates to the hard disk.

The diagram below shows how the `EventsCenter` reacts to that event, which eventually results in the updates
being saved to the hard disk and the status bar of the UI being updated to reflect the 'Last Updated' time. <br>
<img src="images\SDforDeletePersonEventHandling.png" width="800">

> Note how the event is propagated through the `EventsCenter` to the `Storage` and `UI` without `Model` having
  to be coupled to either of them. This is an example of how this Event Driven approach helps us reduce direct 
  coupling between components.

The sections below give more details of each component.

### UI component

<img src="images/UiClassDiagram.png" width="800"><br>

**API** : [`Ui.java`](../src/main/java/seedu/address/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`,
`StatusBarFooter`, `BrowserPanel` etc. All these, including the `MainWindow` inherits from the abstract `UiPart` class
and they can be loaded using the `UiPartLoader`.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files
 that are in the `src/main/resources/view` folder.<br>
 For example, the layout of the [`MainWindow`](../src/main/java/seedu/address/ui/MainWindow.java) is specified in
 [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component,
* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change.
* Responds to events raises from various parts of the App and updates the UI accordingly.

### Logic component

<img src="images/LogicClassDiagram.png" width="800"><br>

**API** : [`Logic.java`](../src/main/java/seedu/address/logic/Logic.java)

1. `Logic` uses the `Parser` class to parse the user command.
2. This results in a `Command` object which is executed by the `LogicManager`.
3. The command execution can affect the `Model` (e.g. adding a person) and/or raise events.
4. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`

<!-- @@author A0138601M -->
### Model component

<img src="images/ModelClassDiagram.png" width="800"><br>

**API** : [`Model.java`](../src/main/java/seedu/address/model/Model.java)

The `Model`,
* Stores a `UserPref` object that represents the user's preferences
* Stores the To Do List data
* Exposes a `UnmodifiableObservableList<ReadOnlyTask` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* Does not depend on any of the other three components.
<!-- @@author -->

### Storage component

<img src="images/StorageClassDiagram.png" width="800"><br>

**API** : [`Storage.java`](../src/main/java/seedu/address/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save the Address Book data in xml format and read it back.

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commans` package. 

## Implementation

### Logging

We are using `java.util.logging.Logger` as our logger, and `LogsCenter` is used to manage the logging levels 
of loggers and handlers (for output of log messages)

- The logging level can be controlled using the `logLevel` setting in the configuration file 
  (See [Configuration](#configuration))
- The `Logger` for a class can be obtained using `LogsCenter.getLogger(Class)` which will log messages according to 
  the specified logging level

- Currently log messages are output through: `Console` and `.log`

**Logging Levels**

- SEVERE
  - Critical use case affected, which may possibly cause the termination of the application

- WARNING
  - Can continue, but with caution

- INFO
  - Information important for the application's purpose
    - e.g. update to local model/request sent to cloud
  - Information that the layman user can understand

- FINE
  - Used for superficial debugging purposes to pinpoint components that the fault/bug is likely to arise from
  - Should include more detailed information as compared to `INFO` i.e. log useful information!
    - e.g. print the actual list instead of just its size

### Configuration

Certain properties of the application can be controlled (e.g App name, logging level) through the configuration file 
(default: `config.json`):


## Testing

**In Eclipse**: 
> If you are not using a recent Eclipse version (i.e. _Neon_ or later), enable assertions in JUnit tests
  as described [here](http://stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option).

* To run all tests, right-click on the `src/test/java` folder and choose 
  `Run as` > `JUnit Test`
* To run a subset of tests, you can right-click on a test package, test class, or a test and choose 
  to run as a JUnit test.
  
**Using Gradle**:
* See [UsingGradle.md](UsingGradle.md) for how to run tests using Gradle. 

Tests can be found in the `./src/test/java` folder.

1. **GUI Tests** - These are _System Tests_ that test the entire App by simulating user actions on the GUI. 
   These are in the `guitests` package.
  
2. **Non-GUI Tests** - These are tests not involving the GUI. They include,
   1. _Unit tests_ targeting the lowest level methods/classes. <br>
      e.g. `seedu.address.commons.UrlUtilTest`
   2. _Integration tests_ that are checking the integration of multiple code units 
     (those code units are assumed to be working).<br>
      e.g. `seedu.address.storage.StorageManagerTest`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as 
      how the are connected together.<br>
      e.g. `seedu.address.logic.LogicManagerTest`
  
**Headless GUI Testing** :
Thanks to the ([TestFX](https://github.com/TestFX/TestFX)) library we use,
 our GUI tests can be run in the _headless_ mode. 
 In the headless mode, GUI tests do not show up on the screen.
 That means the developer can do other things on the Computer while the tests are running.<br>
 See [UsingGradle.md](UsingGradle.md#running-tests) to learn how to run tests in headless mode.
  
## Continuous Integration

We use [Travis CI](https://travis-ci.org/) to perform _Continuous Integration_ on our projects.
See [UsingTravis.md](UsingTravis.md) for more details.

## Making a Release

Here are the steps to create a new release.
 
 1. Generate a JAR file [using Gradle](UsingGradle.md#creating-the-jar-file).
 2. Tag the repo with the version number. e.g. `v0.1`
 2. [Crete a new release using GitHub](https://help.github.com/articles/creating-releases/) 
    and upload the JAR file your created.
   
## Managing Dependencies

A project often depends on third party libraries. For example, Address Book depends on the 
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repo (this bloats the repo size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>

## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | new user | see usage instructions | refer to instructions when I forget how to use the App
`* * *` | user | add a task | keep a list of task
`* * *` | user | set timing and deadline for a task | meet all the timing and deadlines
`* * *` | user | set the location for a task | know where to go for a task
`* * *` | user | add remarks to a task | have quick access to additional information related to the task
`* * *` | user | view task list sorted by date and time | keep track of tasks that have to be done on a specific day
`* * *` | user | find an existing task by name using keywords | locate an existing task without having to go through the entire task list
`* * *` | user | modify the information of a task | update the details, requirements and deadline of a task if they are changed
`* * *` | user | undo the most recent operation done by the user | revert the previous state of tasks in case if a mistake is made
`* * *` | user | mark the completed tasks as done | keep track of which items are done and which are yet to be done
`* * *` | user | delete a task | discard cancelled tasks
`* * *` | user | specify a specific folder as the data storage location | choose a folder that is more convenient for me to access the data
`* *` | user | set notification for a task | remind myself of the deadlines and upcoming events
`*` | user | summon the software quickly by pressing a keyboard shortcut | save time
`*` | user | synchronize my tasklist among all my devices | check my tasks wherever I am

<!-- @@author A0138601M -->
## Appendix B : Use Cases

(For all use cases below, the **System** is the `Task!t` and the **Actor** is the `user`, unless specified otherwise)
#### Use case: Add task

**MSS**

1. User requests to add task
2. Task!t displays the task added<br>
Use case ends.

**Extensions**

2a. The command format is invalid
> 2a1. Task!t shows a 'invalid command' message and display the expected format. <br>
> Use case ends

2b. The task already exist
> 2b1. Task!t shows a 'the task already exist' message. <br>
> Use case resumes at step 1

#### Use case: View task

**MSS**

1. User requests to view tasks for a particular day/week/month/date
2. Task!t display the list of tasks for that date sorted by date and/or time<br>
Use case ends.

**Extensions**

2a. The list is empty
> 2a1. Task!t shows a 'no task found' message.<br>
> Use case ends

#### Use case: Find task

**MSS**

1. User requests to find tasks by keyword
2. Task!t displays the list of tasks that contains the keyword in the name<br>
Use case ends.

**Extensions**

1a. No parameter entered after command word
> Task!t shows a 'no parameter entered' message.<br>
> Use case resumes at step 1


2a. The list is empty
> 2a1. Task!t shows a 'no task found' message.<br>
> Use case ends

#### Use case: Edit task

**MSS**

1. User requests to **view** tasks for a particular date/week/month/date
2. Task!t displays the list of tasks for that date sorted by date and/or time
3. User request to edit a specific task in the list based on task’s index with certain details
6. Task!t updates the task details<br>
Use case ends.

**Extensions**

2a. The list is empty
> 2a1. Task!t shows a 'no task found' message.<br>
> Use case ends

3a. The given index is invalid
> 3a1. Task!t shows a 'invalid index' message <br>
> Use case resumes at step 2

3b. The command format is invalid
> 3b1. Task!t shows a 'invalid command' message and display the expected format.<br>
> Use case resumes at step 2

#### Use case: Undo operation

**MSS**

1. User requests to undo last operation performed
2. Task!t undo the last operation <br>
Use case ends.

**Extensions**

2a. There is no last operation
> 2a1. Task!t shows a 'there is no last operation' message.<br>
> Use case ends

#### Use case: Mark task as done

**MSS**

1. User requests to view tasks for a particular date/week/month/date
2. Task!t displays the list of tasks for that date sorted by date and/or time
3. User request to mark specific tasks in the list based on task’s index
4. Task!t marks the tasks<br>
Use case ends.

**Extensions**

2a. The list is empty
> 2a1. Task!t shows a 'no task found' message.<br>
> Use case ends

3a. The given index is invalid
> 3a1. Task!t shows a 'invalid index' message <br>
> Use case resumes at step 2

3b. The command format is invalid
> 3b1. Task!t shows a 'invalid format' message and display the expected format<br>
> Use case ends

3c. The task is already marked as done
> 3c1. Task!t shows a 'task already marked' message.<br>
> Use case resumes at step 3

###Use case: Remove the done mark of task

**MSS**

1. User requests to **view** tasks for a particular date
2. Task!t displays a list of tasks for that date sorted by time
3. User request to remove the done mark of the specific task in the list based on task’s index
4. Task!t removes the done mark of  the specific task <br>
Use case ends.

**Extensions**

2a. The list is empty
> Use case ends

3a. The given index is invalid
> 3a1. Task!t shows a 'invalid index' message <br>
> Use case resumes at step 2

3b. The task is not marked as done
> 3b1. Task!t shows a 'the task is not marked as done' message <br>
> Use case resumes at step 2

#### Use case: Delete task

**MSS**

1. User requests to **view** tasks for a particular date/week/month/date
2. Task!t displays the list of tasks for that date sorted by date and/or time
3. User requests to delete a specific task in the list based on task’s index
4. Task!t deletes the task <br>
Use case ends.

**Extensions**

2a. The list is empty
> 2a1. Task!t shows a 'no task found' message<br>
> Use case ends

3a. The given index is invalid
> 3a1. Task!t shows a 'invalid index' message <br>
> Use case resumes at step 2

#### Use case: Set storage location

**MSS**

1. User requests to change storage location wo a specified file path
2. Task!t changes the storage location<br>
Use case ends.

**Extensions**

2a. The given path is invalid
> 2a1. Task!t shows a 'invalid path' message<br>
> Use case ends
<!-- @@author -->

## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 tasks.
3. Should come with automated unit tests and open source code.
4. Should favor DOS style commands over Unix-style commands.
5. Should display response within 1 second.
6. Should work stand-alone and should not be a plug-in to another software.
7. Should work without internet connection.
8. Should store data in text file.
9. Should work without requiring an installer.
10. Should work only with Command Line Interface as the main input with minimal use of mouse/clicking.
11. Should follow the Object-oriented paradigm.
12. Should be free and come with open source code


## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

##### Task

> An item in Task!t, could be an event, a deadline, or a reminder.

## Appendix E : Product Survey
<!-- @@author A0138601M -->
#### Google Calendar
Pros:
* Able to color-code different events
* Able to set notifications
* Able to set repeat
* Able to add notes (i.e. location, remarks)
* Able to sync on different devices
* Able to share calendar
* Able to create layers to manege events
* Able to undo last action <br>

Cons:
* Unable to check off completed event (only reminder can)
* Unable to label event with categories
* Event name gets truncated <br>
<!-- @@author -->
#### S Planner
Pros:
* Able to sync with another calendar product(i.e. Google calendar)
* Able to set notifications
* Able to set repeat
* Able to add notes
* Able to add location
* Able to set start and end time
* Able to set different time zone
* Able to share as text <br>

Cons:
* Unable to search events
* Unable to view event list
* Unable to mark event as done
* Unable to color-code different events <br>

#### Wunderlist

Pros:
* Able to create many layers - folders, lists, tasks, and subtasks, which allow the user to organize their multi-layered tasks systematically
* Able to add alarm to a task
* Able to repeat a task daily, weekly, monthly, or yearly
* Able to add notes to a task
* Able to attach files to a task
* Able to mark a task as complete by a simple click
* Able to unmark a “completed” task by a simple click
* Able to share a task list with team members by name or email address
* Able to sort a list alphabetically, by due date, or creation date
* Includes two tabs of “today” and “week” which highlight the most recent tasks
* Includes a search bar for tasks
* Able to synchronize tasks to cloud
* Able to star a task to mark its importance
* Able to create a task without a date, thus allowing the user to keep track of reminders
* Keeps track of the creation date of a task
* Worded tabs automatically minimize themselves into icons when the window size is reduced, so as to keep the view uncluttered
* Keeps track of completed tasks for reference

Cons:
* Unable to add the start and end time of an event. User is only allowed to set the deadline of a task
* Unable to undo a deletion of a task
* Unable to set a specific timing of a due date, e.g. 23:59, 6 am etc.
* Slow to open / close the window since the user can only do it by mouse movements
* Slow to resize the window since the user has to do it by mouse movements
* Does not display today’s date
* Does not display a calendar
* Does not allow the addition of venue to a task

#### Cal
Pros:
* With a very convenient interface for setting events’ date and time quickly
* Able to add other participants’ contacts in an event
* Able to send or receive invitation of an event to others directly through the app
* Able to include map when adding location of an event
* Able to provide recommendation when deciding the location of an event 
* Able to set alarm to an event
* Able to set repeat to an event
* Able to connect with Facebook and import Facebook friends’ birthdays into the calendar
* Able to sync with another calendar product (eg. Google calendar) <br>

Cons:
* Does not have a task function itself so it requires to work together with another app Any.do
* Unable to show events of the following days directly through the calendar
* Unable to show events that last for a few days in the calendar
* Does not show public holidays in the calendar
* Unable to add additional notes in an event <br>


