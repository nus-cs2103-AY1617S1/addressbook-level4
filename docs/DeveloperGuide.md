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
  > * If Eclipse auto-changed any settings files during the import process, you can discard those changes.
  
#### Troubleshooting project setup

**Problem: Eclipse reports compile errors after new commits are pulled from Git**
* Reason: Eclipse fails to recognize new files that appeared due to the Git pull. 
* Solution: Refresh the project in Eclipse:<br> 
  Right click on the project (in Eclipse package explorer), choose `Gradle` -> `Refresh Gradle Project`.
  
**Problem: Eclipse reports some required libraries missing**
* Reason: Required libraries may not have been downloaded during the project import. 
* Solution: [Run tests using Gardle](UsingGradle.md) once (to refresh the libraries).
 

## Design

### Architecture

<img src="images/Architecture.png" width="600"><br>
The **_Architecture Diagram_** given above explains the high-level design of the App.
Given below is a quick overview of each component.

`Main` has only one class called [`MainApp`](../src/main/java/seedu/address/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connect them up with each other.
* At shut down: Shuts down the components and invoke cleanup method where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.
Two of those classes play important roles at the architecture level.
* `EventsCentre` : This class (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
  is used by components to communicate with other components using events (i.e. a form of _Event Driven_ design)
* `LogsCenter` : Used by many classes to write log messages to the App's log file.

The rest of the App consists four components.
* [**`UI`**](#ui-component) : The UI of tha App.
* [**`Logic`**](#logic-component) : The command executor.
* [**`Model`**](#model-component) : Holds the data of the App in-memory.
* [**`Storage`**](#storage-component) : Reads data from, and writes data to, the hard disk.

Each of the four components
* Defines its _API_ in an `interface` with the same name as the Component.
* Exposes its functionality using a `{Component Name}Manager` class.

For example, the `Logic` component (see the class diagram given below) defines it's API in the `Logic.java`
interface and exposes its functionality using the `LogicManager.java` class.<br>
<img src="images/LogicClassDiagram.png" width="800"><br>

The _Sequence Diagram_ below shows how the components interact for the scenario where the user issues the
command `delete 3`.

<img src="images\SDforDeleteTask.png" width="800">

>Note how the `Model` simply raises a `TaskManagerChangedEvent` when the Address Book data are changed,
 instead of asking the `Storage` to save the updates to the hard disk.

The diagram below shows how the `EventsCenter` reacts to that event, which eventually results in the updates
being saved to the hard disk and the status bar of the UI being updated to reflect the 'Last Updated' time. <br>
<img src="images\SDforDeleteTaskEventHandling.png" width="800">

> Note how the event is propagated through the `EventsCenter` to the `Storage` and `UI` without `Model` having
  to be coupled to either of them. This is an example of how this Event Driven approach helps us reduce direct 
  coupling between components.

The sections below give more details of each component.

### UI component

<img src="images/UiClassDiagram.png" width="800"><br>

**API** : [`Ui.java`](../src/main/java/seedu/address/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `TaskListPanel`,
`StatusBarFooter`, `BrowserPanel` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class
and they can be loaded using the `UiPartLoader`.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files
 that are in the `src/main/resources/view` folder.<br>
 For example, the layout of the [`MainWindow`](../src/main/java/seedu/address/ui/MainWindow.java) is specified in
 [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component,
* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change.
* Responds to events raised from various parts of the App and updates the UI accordingly.

### Logic component

<img src="images/LogicClassDiagram.png" width="800"><br>

**API** : [`Logic.java`](../src/main/java/seedu/address/logic/Logic.java)

1. `Logic` uses the `Parser` class to parse the user command.
2. This results in a `Command` object which is executed by the `LogicManager`.
3. The command execution can affect the `Model` (e.g. adding a task) and/or raise events.
4. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")`
 API call.<br>
<img src="images/DeleteTaskSdForLogic.png" width="800"><br>

### Model component

<img src="images/ModelClassDiagram.png" width="800"><br>

**API** : [`Model.java`](../src/main/java/seedu/address/model/Model.java)

The `Model`,
* stores a `UserPref` object that represents the user's preferences.
* stores the Address Book data.
* exposes a `UnmodifiableObservableList<ReadOnlyTask>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.

### Storage component

<img src="images/StorageClassDiagram.png" width="800"><br>

**API** : [`Storage.java`](../src/main/java/seedu/address/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save the Address Book data in xml format and read it back.

### Common classes

Classes used by multiple components are in the `seedu.taskmanager.commons` package.

## Implementation

### Logging


We are using `java.util.logging` package for logging. The `LogsCenter` class is used to manage the logging levels
and logging destinations.

* The logging level can be controlled using the `logLevel` setting in the configuration file
  (See [Configuration](#configuration))
* The `Logger` for a class can be obtained using `LogsCenter.getLogger(Class)` which will log messages according to
  the specified logging level
* Currently log messages are output through: `Console` and to a `.log` file.

**Logging Levels**

* `SEVERE` : Critical problem detected which may possibly cause the termination of the application
* `WARNING` : Can continue, but with caution
* `INFO` : Information showing the noteworthy actions by the App
* `FINE` : Details that is not usually noteworthy but may be useful in debugging
  e.g. print the actual list instead of just its size

### Configuration

Certain properties of the application can be controlled (e.g App name, logging level) through the configuration file 
(default: `config.json`):


## Testing

Tests can be found in the `./src/test/java` folder.

**In Eclipse**:
* To run all tests, right-click on the `src/test/java` folder and choose
  `Run as` > `JUnit Test`
* To run a subset of tests, you can right-click on a test package, test class, or a test and choose
  to run as a JUnit test.

**Using Gradle**:
* See [UsingGradle.md](UsingGradle.md) for how to run tests using Gradle.

We have two types of tests:

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
Thanks to the [TestFX](https://github.com/TestFX/TestFX) library we use,
 our GUI tests can be run in the _headless_ mode. 
 In the headless mode, GUI tests do not show up on the screen.
 That means the developer can do other things on the Computer while the tests are running.<br>
 See [UsingGradle.md](UsingGradle.md#running-tests) to learn how to run tests in headless mode.
 
#### Troubleshooting tests
 **Problem: Tests fail because NullPointException when AssertionError is expected**
 * Reason: Assertions are not enabled for JUnit tests. 
   This can happen if you are not using a recent Eclipse version (i.e. _Neon_ or later)
 * Solution: Enable assertions in JUnit tests as described 
   [here](http://stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option). <br>
   Delete run configurations created when you ran tests earlier.
  
## Dev Ops

### Build Automation

See [UsingGradle.md](UsingGradle.md) to learn how to use Gradle for build automation.

### Continuous Integration

We use [Travis CI](https://travis-ci.org/) to perform _Continuous Integration_ on our projects.
See [UsingTravis.md](UsingTravis.md) for more details.

### Making a Release

Here are the steps to create a new release.
 
 1. Generate a JAR file [using Gradle](UsingGradle.md#creating-the-jar-file).
 2. Tag the repo with the version number. e.g. `v0.1`
 2. [Crete a new release using GitHub](https://help.github.com/articles/creating-releases/) 
    and upload the JAR file your created.
   
### Managing Dependencies

A project often depends on third-party libraries. For example, Address Book depends on the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repo (this bloats the repo size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>

## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`

Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *`| user | add a task | record tasks that need to be done 'some day'. 
`* * *`| user |view upcoming tasks | decide what needs to be done soon. 
`* * *`| user | delete a task | get rid of tasks that I no longer care to track. 
`* * *`| new user | view more information about a particular command | learn how to use various commands 
`* * *`| user | keep track my task | check which task have completed or on going
`* * *`| user | specify which folder path to save my file | organise my task manager easily
`* *`| advanced user |  use shorter versions of a command | that can type a command faster.
`* *`|user | set reminder for my task | can complete the task on time. 
`* *` |user | view task that is due today when I launch the application | verify what is done and what is to be done. 
`* *` |user |add category to a task | view task in a more organise manner. 
`* *` | user | edit my task details, if some changes is needed | So that I can update the latest details.
`* *`| user | set priority to each of my task | prioritise which task to focus on. 
`* *`| user | add duplicate tasks for specfic period of time | see a duplicate appears on regular intervals, e.g. once per week. 
`*`| user | color code a task | group my task easily




## Appendix B : Use Cases

(For all use cases below, the **System** is the `TaskManager` and the **Actor** is the `user`, unless specified otherwise)


#### Use case: UC01 add task.
   Actor: User
   Preconditions: User is logged in

**MSS**

1. User requests to add new task description.
2. System will update the database of the newly created task.

   Use Case end.

**Extension**

    1a. User enter invalid text.
    >1a1. System shows an error message
    Use case resume at step 1.

#### Use case: UC02 view upcoming task.

   Actor: User
   Preconditions: User is logged in

**MSS**

1. User requests to view upcoming task
2. System will show the list of upcoming task
   Use Case end.


**Extension**

    2a. There are no upcoming task to be found
    >2a1. System shows a message no upcoming task are found.
    Use case ends

#### Use case: UC03 Delete task
   Actor: User
   Precondition: User is logged in

**MSS**

1. User request to delete a specific task<br>
2. System request confirmation of deletion of task
3. User confirm to delete task
4. System will delete the specific task.
   Use Case ends

**Extension**
 
       1a.Specific task cannot be found
       >1a1. System will show message task cannot be found
       4a. System is unable to delete due to failed to connect to database.
       >4a1. System shows connection has failed message.
       Use case resume at step 1.

#### Use case: UC04 Information of the a particular command.
   Actor: User
   Precondition: User is logged in

**MSS**

1. User enter help a particular command
2. System show the user guide of the particular command
   Use Case ends

**Extension**
 
       1a.User enter an invalid command
       >1a1. System show error message
       Use case ends.

#### Use case: UC05 use shorter version of a command
Actor: Advance user
Preconditions: User is familiar with the available command

**MSS**

1. User enter a shorter version of a command
2. System identify which command to execute based on user input
   Use case ends.

**Extension** 

    1a. User enter invalid command
    > 1a1. System shows an error message
    Use case ends

    2a. System could not connect with the database
    > 2a1. System shows connection failed message.
    Use case ends

#### Use case UC06 set reminder for tasks
   Actor: User
   Preconditions: User is logged in, tasks is created.

**MSS**

1. User requests to list all tasks
2. System shows a list of tasks
3. User request to set reminder for specific task in the list
4. System set reminder for the selected task and display action successful message
Use case ends

**Extension**

    2a. The task list is empty
    > Use case ends

    3a. User enter invalid index
    > 3a1. System shows an error message
    Use case resume at step 2

#### Use case UC07 view task that is due on the day after launching application
   Actor: User
   Preconditions: User have not launch application

**MSS**

1. User launch application
2. System display a list of task that is due on the day
3. Use case ends

**Extension**

    2a. The task list is empty
    > Use case ends

#### Use case UC08 add category to task
   Actor: User
   Preconditions: User is logged in, task is created.

**MSS**

1. User requests to list all tasks
2. System shows a list of tasks
3. User request to add category for specific task in the list
4. System prompt for category to set with
5. User enter category
6. System set category for the selected task and display action successful message
Use case ends

**Extension**

    2a. The task list is empty
    > Use case ends

    3a. User enter invalid index
    > 3a1. System shows an error message
    Use case resume at step 2

#### Use case: UC09 edit task details
   Actor: User
   Preconditions: User is logged in.

**MSS**

1. User requests to list all tasks
2. System shows a list of tasks
3. User chooses one task to edit its details
4. System retrieves details of chosen task from file
5. User edits details of the task
6. System requests for confirmation
7. User confirms changes 
8. System updates the details of the task in file and displays the changed task
Use Case ends

**Extension**

    2a. System detects an error with retrieving data from file
    > 2a1. System displays the information about the problem with a file
    Use Case ends

    4a. System detects an error with retrieving data from file
    >4a1. System displays the information about the problem with a file
    Use Case ends

    7a. User does not confirm changes 
        7a1. System displays the information about not saved changes
        Use case resumes from step 5.

    8a. System detects an error with retrieving data from file
    > 8a1. System displays the information about the problem with a file
    Use Case ends

    *a. At any time, User can cancel editing the task
    *a1. System requests for confirmation
    *a2. User confirms the cancellation 
    Use Case ends. 

#### Use case: UC10 set priority to tasks
   Actor: User
   Preconditions: User is logged in.

**MSS**

1. User requests to list all tasks
2. System shows a list of tasks
3. User chooses to set priority of tasks 
4. System shows the view for setting priority of tasks 
5. User set priority to tasks 
6. System requests for confirmation
7. User confirms changes 
8. System updates prioritizing for tasks in database and displays current prioritizing for tasks
   Use Case ends

**Extensions**

    2a. System detects an error with retrieving data from file
    > 2a1. System displays the information about the problem with a file
    Use Case ends

    7a. User does not confirm changes 
    5a1. System displays the information about not saved changes
    Use case resumes from step 5.

    8a. System detects an error with retrieving data from file
    6a1. System displays the information about the problem with a file
    Use Case ends

    *a. At any time, User can cancel setting priority for tasks
    *a1. System requests for confirmation
    *a2. User confirms the cancellation 
    Use Case ends. 

#### Use case: UC11 create a task that happens repeatedly by specifying an interval
   Actor: User
   Preconditions: User is logged in.

**MSS**

1. User requests to add task with specified start date and interval
2. System updates the database with information.
   Use Case ends

**Extensions**

    1a. User enters invalid command
    > 1a1. System displays the error message.
    Use Case resumes at step 1.
    
    2a. System detects an error in the connection with database
    > 2a1. System displays the information about the connection problem with database
    Use Case ends

#### Use case: UC12 Color coding a task
Actor: User
Preconditions: User is logged in, tasks are created.

**MSS**

1. User requests to list all tasks.
2. System displays a list of all created tasks.
3. User selects a task to edit
4. User changes the color of the task.
5. System updates the database with the changed details.
   Use Case ends

**Extensions**

    2a. System detects an error in the connection with database
    > 2a1. System displays the information about the connection problem with database
    Use Case ends
        
    4a. User does not confirm the color change
    > 4a1. System displays message about cancellation 
    Use Case resumes from step 1.
    
    *a. At any time, User can cancel color coding
    *a1. System requests for confirmation 
    *a2. User cancels color coding
    Use Case ends. 

#### Use case: Delete task

**MSS**

1. User requests to list tasks
2. TaskManager shows a list of tasks
3. User requests to delete a specific task in the list
4. TaskManager deletes the task
   Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. TaskManager shows an error message <br>
  Use case resumes at step 2

{More to be added}

## Appendix C : Non Functional Requirements

   1. System should work on any mainstream OS as long as it has Java 8 or higher installed.<br> 
   2. System should come with automated unit tests 
   3. Should be free and come with open source code. 
   4. Interface should be user friendly and intuitive.
   5. System should be able to hold up to 1000 tasks.
   6. System should be able to run system within 5 seconds after launching
   7. System should be able to export task details
   8. System should have backup option
   9. Exported task details should be intuitive and easily readable
   10. System should not allow information to be accessed outside of the program
   11. System should be able to recover from errors quickly
   12. System should be able to report errors


{More to be added}

## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

##### Private contact detail

> A contact detail that is not meant to be shared with others

## Appendix E : Product Survey

{TODO: Add a summary of competing products}

