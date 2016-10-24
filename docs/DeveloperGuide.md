<!-- @@author A0140007B -->

# Developer Guide 

* [Setting Up](#setting-up)
* [Design](#design)
* [Implementation](#implementation)
* [Testing](#testing)
* [Continuous Integration](#continuous-integration)
* [Making a Release](#making-a-release)
* [Managing Dependencies](#managing-dependencies)
* [Appendix A: User Stories](#appendix-a-user-stories)
* [Appendix B: Use Cases](#appendix-b-use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c-non-functional-requirements)
* [Appendix D: Glossary](#appendix-d-glossary)
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

## Design

<img src="images/Architecture.png" width="600"><br>
The **_Architecture Diagram_** given above explains the high-level design of the App.
Given below is a quick overview of each component.

`Main` has only one class called [`MainApp`](../src/main/java/seedu/taskscheduler/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connect them up with each other.
* At shut down: Shuts down the components and invoke clean up method where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.
Two of those classes play an important role at the architecture level.
* `EventsCentre` : This class (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
  is used to by components to communicate with other components using events (i.e. a form of _Event Driven_ design)
* `LogsCenter` : Used by many classes to write log messages to the App's log files.

The rest of the App consists four components.
* [**`UI`**](#ui-component) : The UI of the App.
* [**`Logic`**](#logic-component) : The command executor.
* [**`Model`**](#model-component) : Holds the data of the App in-memory.
* [**`Storage`**](#storage-component) : Reads data from, and writes data to, the hard disk.

Each of the four components
* Defines its _API_ an interface with the same name as the Component. `Logic.java`
* Exposes its functionality using a `{Component Name}Manager` class e.g. `LogicManager.java`

The _Sequence Diagram_ below shows how the components interact for the scenario where the user issues the
command `delete 3`.

<img src="images\SDforDeleteTask.png" width="800">

>Note how the `Model` simply raises a `ModelChangedEvent` when the model is changed,
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

**API** : [`Ui.java`](../src/main/java/seedu/taskscheduler/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `TaskListPanel`,
`StatusBarFooter`, `BrowserPanel` etc. All these, including the `MainWindow` inherits from the abstract `UiPart` class
and they can be loaded using the `UiPartLoader`.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files
 that are in the `src/main/resources/view` folder.<br>
 For example, the layout of the [`MainWindow`](../src/main/java/seedu/taskscheduler/ui/MainWindow.java) is specified in
 [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component,
* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change.
* Responds to events raises from various parts of the App and updates the UI accordingly.

### Logic component

<img src="images/LogicClassDiagram.png" width="800"><br>

**API** : [`Logic.java`](../src/main/java/seedu/taskscheduler/logic/Logic.java)

1. `Logic` uses the `Parser` class to parse the user command.
2. This results in a `Command` object which is executed by the `LogicManager`.
3. The command execution can affect the `Model` (e.g. adding a task) and/or raise events.
4. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `UI`

### Model component

<img src="images/ModelClassDiagram.png" width="800"><br>

**API** : [`Model.java`](../src/main/java/seedu/taskscheduler/model/Model.java)

The `Model`,
* Stores a `UserPref` object that represents the user's preferences
* Stores the Task Scheduler data
* Exposes a `UnmodifiableObservableList<ReadOnlyTask>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* Does not depend on any of the other three components.

### Storage component

<img src="images/StorageClassDiagram.png" width="800"><br>

**API** : [`Storage.java`](../src/main/java/seedu/taskscheduler/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save the Task Scheduler data in xml format and read it back.

### Common classes

Classes used by multiple components are in the `seedu.taskscheduler.commands` package. 

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
      e.g. `seedu.taskscheduler.commons.UrlUtilTest`
   2. _Integration tests_ that are checking the integration of multiple code units 
     (those code units are assumed to be working).<br>
      e.g. `seedu.taskscheduler.storage.StorageManagerTest`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as 
      how the are connected together.<br>
      e.g. `seedu.taskscheduler.logic.LogicManagerTest`
  
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

A project often depends on third party libraries. For example, Task Scheduler depends on the 
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
`* * *` | user | add a new task |
`* * *` | user | list all tasks | 
`* * *` | user | find a task by task name | locate details of tasks without having to go through the entire task list
`* * *` | user | find a task by task date | locate details of tasks without having to go through the entire task list
`* * *` | user | find a task by task time| locate details of tasks without having to go through the entire task list
`* * *` | user | select a task | open the browser without using the mouse 
`* * *` | user | set the data file path | save the data to where i want 
`* * *` | user | delete a task | remove entries that I no longer need
`* * *` | user | clear all tasks | start a fresh task list 
`* * *` | user | edit a task | edit details without re-entry
`* * *` | user | undo a command | revert the previous action 
`* * *` | user | mark a task as complete | manage my Task list easily
`* * *` | user | have flexibility in the command format | type a few natural variations of the command format 
`* * *` | user | create floating tasks | tasks can be created without specific times
`* * *` | user | change the default storage path | sync the cloud services to access data from multiple computers
`* * *` | user | exit the task list | close the task list 
`* *` | user | use up arrow or down arrow to reuse previous command(s) | minimise re-typing
`* *` | user | indicate overdue tasks with color code (red) | easily to track overdue task
`* *` | user | indicate a completed task with color code (green) | easily to track done task  
`* *` | user | make a task recurring | duplicate a task for specific number of days  
`*` | user | lock task scheduler(with a password) | prevent unauthorized access/modification 

## Appendix B : Use Cases

(For all use cases below, the **System** is the `MustDoList` and the **Actor** is the `user`, unless specified otherwise)

#### Use case 1: Help

**MSS**

1. User requests for help
2. MustDoList shows the user guide through a html file <br>
Use case ends.

#### Use case 2: Add task

**MSS**

1. User requests to add tasks
2. MustDoList adds the task
3. MustDoList shows a list of added tasks <br>
Use case ends.

**Extensions**

1a. The add task request has invalid format

> 1a1. MustDoList shows an error message <br>
  Use case resumes at step 1

1b. The add task request has a duplicate task name

> 1b1. MustDoList shows an error message <br>
  Use case resumes at step 1

#### Use case 3: List task

**MSS**

1. User requests to list tasks
2. MustDoList shows a list of tasks <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

#### Use case 4: Find task

**MSS**

1. User requests to find tasks
2. MustDoList shows a list of found tasks <br>
Use case ends.

**Extensions**

1a. The find task request has an invalid parameter

> 1a1. MustDoList shows an error message <br>
  Use case resumes at step 1

2a. The list is empty

> Use case ends

#### Use case 5: Select task

**MSS**

1. User requests to list tasks
2. MustDoList shows a list of tasks
3. User requests to select the index of a specific task in the list
4. MustDoList highlight the selected task in the list
5. MustDoList open the browser and search for task name <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invaild

> 3a1. MustDoList shows an error message <br>
  Use case resumes at step 3


#### Use case 6: Delete task

**MSS**

1. User requests to list tasks
2. MustDoList shows a list of tasks
3. User requests to delete a specific task in the list by the task's index
4. MustDoList deletes the task <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. MustDoList shows an error message <br>
  Use case resumes at step 3

#### Use case 7: Clear task

**MSS**

1. User requests to clear tasks
2. MustDoList clears all entries of tasks <br>
Use case ends.

#### Use case 8: Edit task

**MSS**

1. User requests to list task
2. MustDoList shows a list of tasks
3. User requests to edit a specific task in the list by the task's index
4. MustDoList edits the task <br>
Use case ends.

<img src="images\SDforEditTask.png" width="800">

**Extensions**

2a. The list is empty

> Use case ends

3a. The edit task request has invalid format

> 3a1. MustDoList shows an error message <br>
  Use case resumes at step 3

3b. The given index is invalid

> 3b1. MustDoList shows an error message <br>
  Use case resumes at step 3
  

#### Use case 9: Undo task

**MSS**

1. User requests to undo task
2. MustDoList undo the task <br>
Use case ends.

<img src="images\SDforUndoTask.png" width="800">

**Extensions**

2a. The task list is at initial stage
> 2a1. MustDoList shows an error message <br>
  Use case ends.
  
#### Use case 10: Mark task

**MSS**

1. User requests to list task
2. MustDoList shows a list of tasks
3. User requests to mark a specific task as completed in the list by the task's index
4. MustDoList marks the task as completed <br>
Use case ends.

<img src="images\SDforMarkTask.png" width="800">

**Extensions**

2a. The list is empty
Use case ends

3a. The given index is invalid
> 3a1. MustDoList shows an error message <br>
  Use case resumes at step 2

#### Use case 11: Set storage path

**MSS**

1. User requests to change default storage path
2. MustDoList changes the path of default storage <br>
Use case ends.

<img src="images\SDforSetpath.png" width="800">

<img src="images\SDforFilePathChangedEventHandling.png" width="800">

**Extensions**

1a. The set storage path task request has invalid format

> 1a1. MustDoList shows an error message <br>
  Use case resumes at step 1

#### Use case 12: Exit task list

**MSS**

1. User requests to exit task list
2. MustDoList closes the task list <br>
Use case ends.

<img src="images\SDforExit.png" width="800">

<img src="images\SDforExitAppRequestHandling.png" width="800">

#### Use case 13: Recur a task


**MSS**

1. User requests to list task
2. MustDoList shows a list of tasks
3. User requests to recur a specific task in the list by the task's index
4. MustDoList recurs the task with a specific numbers of days <br>
Use case ends

<img src="images\SDforRecurTask.png" width="800">

**Extensions**

2a. The list is empty

> Use case ends

3a. The recur task request has invalid format

> 3a1. MustDoList shows an error message <br>
  Use case resumes at step 3

3b. The given index is invalid

> 3b1. MustDoList shows an error message <br>
  Use case resumes at step 3

## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 tasks.
3. Should come with automated unit tests and open source code.
4. Should favor DOS style commands over Unix-style commands.
5. Should backup the MustDoList to prevent data-loss
6. Should have a small overall program size
7. Should have fast response time.
8. Should be accessible and usable only by authorized users
9. Should protect task information 

> http://www.comp.nus.edu.sg/~cs2103/AY1617S1/contents/handbook.html#handbook-project-constraints

## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

## Appendix E : Product Survey

#### Todoist

> Summary
* Todoist is a online task management application and to do list.

> Strength
* Access task everywhere (on mobile, web browser)
* Collaborate on shared task in real-time
* Powerful recurring dates by creating repeating due dates.

> Weakness
* A few important features not available to free user
* Search function is limited in the free plan
* Does not have a backup option for free users. Premium level is available at $29-per-year.

#### Google Calendar

> Summary
* Google Calendar is an online calendar that keep track of life's important events all in one place.

> Strength
* Supports multiple calendars for a single user
* Schedule meeting using keyword like Suggested Time or Find a Time 
* Share calendar with others.

> Weakness
* User must sign up for the service
* Offline version is available for viewing only
* Cannot categorize calendar events based on event type.
* User cannot operate primarily using keyboard


