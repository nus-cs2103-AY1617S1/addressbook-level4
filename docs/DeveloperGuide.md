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
  > * Depending on your connection speed and server load, it can even take up to 30 minutes for the setup to finish
      (This is because Gradle downloads library files from servers during the project set up process)
  > * If Eclipse auto-changed any settings files during the import process, you can discard those changes.

## Design

### Architecture

<img src="images/Architecture.png" width="600"><br>
The **_Architecture Diagram_** given above explains the high-level design of the App.
Given below is a quick overview of each component.

`Main` has only one class called [`MainApp`](../src/main/java/seedu/whatnow/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connect them up with each other.
* At shut down: Shuts down the components and invoke cleanup method where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.
Two of those classes play important roles at the architecture level.
* `EventsCentre` : This class (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
  is used by components to communicate with other components using events (i.e. a form of _Event Driven_ design)
* `LogsCenter` : Used by many classes to write log messages to the App's log file.

The rest of the App consists four components.
* [**`UI`**](#ui-component) : The UI of that App.
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

>Note how the `Model` simply raises a `WhatNowChangedEvent` when the What Now data are changed,
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

**API** : [`Ui.java`](../src/main/java/seedu/whatnow/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `TaskListPanel`,
`StatusBarFooter`, `BrowserPanel` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class
and they can be loaded using the `UiPartLoader`.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files
 that are in the `src/main/resources/view` folder.<br>
 For example, the layout of the [`MainWindow`](../src/main/java/seedu/whatnow/ui/MainWindow.java) is specified in
 [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component,
* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change.
* Responds to events raised from various parts of the App and updates the UI accordingly.

### Logic component

<img src="images/LogicClassDiagram.png" width="800"><br>

**API** : [`Logic.java`](../src/main/java/seedu/whatnow/logic/Logic.java)

1. `Logic` uses the `Parser` class to parse the user command.
2. This results in a `Command` object which is executed by the `LogicManager`.
3. The command execution can affect the `Model` (e.g. adding a task) and/or raise events.
4. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")`
 API call.<br>
<img src="images/DeleteTaskSdForLogic.png" width="800"><br>

### Model component

<img src="images/ModelClassDiagram.png" width="800"><br>

**API** : [`Model.java`](../src/main/java/seedu/whatnow/model/Model.java)

The `Model`,
* stores a `UserPref` object that represents the user's preferences.
* stores the WhatNow data.
* exposes a `UnmodifiableObservableList<ReadOnlyTask>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.

### Storage component

<img src="images/StorageClassDiagram.png" width="800"><br>

**API** : [`Storage.java`](../src/main/java/seedu/whatnow/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save the WhatNow data in xml format and read it back.

### Common classes

Classes used by multiple components are in the `seedu.whatnow.commons` package.

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
> If you are not using a recent Eclipse version (i.e. _Neon_ or later), enable assertions in JUnit tests
  as described [here](http://stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option).

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
      e.g. `seedu.whatnow.commons.UrlUtilTest`
   2. _Integration tests_ that are checking the integration of multiple code units 
     (those code units are assumed to be working).<br>
      e.g. `seedu.whatnow.storage.StorageManagerTest`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as 
      how the are connected together.<br>
      e.g. `seedu.whatnow.logic.LogicManagerTest`
  
**Headless GUI Testing** :
Thanks to the [TestFX](https://github.com/TestFX/TestFX) library we use,
 our GUI tests can be run in the _headless_ mode. 
 In the headless mode, GUI tests do not show up on the screen.
 That means the developer can do other things on the Computer while the tests are running.<br>
 See [UsingGradle.md](UsingGradle.md#running-tests) to learn how to run tests in headless mode.
  
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

A project often depends on third-party libraries. For example, WhatNow depends on the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repo (this bloats the repo size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>

## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`

Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | new user | see user instructions | learn how to use the application
`* * *` | new user | see user instructions | refer to instructions when I forget how to use the App
`* * *` | user | add a new task | write down tasks that I have to do
`* * *` | user | delete a task | remove completed tasks
`* * *` | user | find a task by task name | locate the specific task without having to go through the entire list
`* * *` | user | view all tasks | look at the list of all tasks to be done
`* * *` | user | edit a task | change any task without removing the old task and creating a new one
`* *` | user | add a recurring task | add a task once only without having to add it multiple times 
`* *` | user | undo a command | change my mind without deleting and creating or changing anything
`* *` | user | redo a command |  change my mind without deleting and creating or changing anything
`* *` | user | choose the data file location | store the file on the cloud (For e.g. Dropbox) to sync the data across multiple computers or other preferable locations.
`*` | user with many tasks in WhatNow | sort tasks by priority | locate the most important and immediate tasks easily

{More to be added}

## Appendix B : Use Cases

System: WhatNow
Actor: User


#### **Use case: Help**
**MSS**
User requests to see the list of available commands
System show a list of available commands
	Use case ends

#### **Use case: Add task**
**MSS**
User requests to add a task into the system
System adds this task into its list of tasks
	Use case ends
**Extensions**
2a. The task is already existing
	>2a1. System displays “task is already existing” message.
	>Use case ends
2b. The given syntax is invalid
	>2b1. System displays “Invalid syntax” error message.
	>2b2. ‘Help’ command is launched.
	>2b3. System awaits user input.
	>Use case ends

#### **Use case: List task**
**MSS**
User requests to add an incoming into the system
System adds this task into its list of tasks
	Use case ends
**Extensions**
2a. The task is already existing
	>2a1. System displays “task already exists” message.
	>Use case ends
2b. The given syntax is invalid
	>2b1. System displays “Invalid syntax” error message.
	>2b2. ‘Help’ command is launched.
	>2b3. System awaits user input.
	>Use case ends

#### **Use case: Delete task**
**MSS**
User requests to list all the tasks
System shows a list of task
User requests to delete a specific task in the list
System deletes the task
	Use case ends
**Extensions**
2a. The list is empty
	>Use case ends
3a. The given index is invalid
	>3a1. System displays an error message
     >Use case ends at step 2

#### **Use case: Recurring task**
**MSS**
User adds a recurring task.
System asks for date period and time. 
User inputs date and time.
System creates a recurring task.
	Use case ends.

#### **Use case: Undo**
**MSS**
User requests to revert back to the state the system was previously in.
System reverts to the state before the user has entered a command
**Extensions**
2a. The user just launched the system and did not type a prior command.
	>2a1. System displays “Nothing was undone” message
	>Use case ends

#### **Use case: Redo**
**MSS**
User requests to revert back to the state that the system was previously in during ‘undo’
System reverts to the state of ‘redo’
**Extensions**
2a. User did not type an ‘undo’ command previously
	>2a1. System displays “Nothing to redo” message
	>Use case ends

#### **Use case: Search for a task**
**MSS**
User requests to search a particular task. 
System goes through every task to find the target task.
System displays the task along with its details.
**Extensions**
1a. If no such task exists. 
	>1a1.System will display “No such task.”
            >Use case ends

#### **Use case: Filter by priority**
**MSS**
User requests to list all tasks by priority
System displays all tasks by the priority specified
**Extensions**
1a. There are no tasks of such priority. 
	>1a1. System displays “No such task” message
	>Use case ends


{More to be added}

## Appendix C : Non Functional Requirements

1. Should work on any mainstream OS that has Java 8 or higher installed.
2. Should be able to contain up to 20000 tasks.
3. Should come with automated unit tests and open source code.
4. Should favour natural language commands than unix commands.
5. Should have a response time of less than 1 second per command.
6. Should have an organised display of information.
7. Should backup data every quarterly. 
8. Should autoarchive tasks that are more than a year old.
9. Should be able to retrieve backup or archived data. 
10. Should be secured. 

{More to be added}

## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

##### Private contact detail

> A contact detail that is not meant to be shared with others

## Appendix E : Product Survey

**Google Calendar**
Strengths:
* Free
* Automatic syncs to all devices
* Reminders can be configured

Weakness:
* Cannot edit offline
* Need to have a google account
* Need to use a mouse to navigate most of the time

**Todoist**
Strengths:
* The interface is clean and simple. Easy to understand. Good for taskal use.
* Quick access to check on everyday's task
* Quick add of task is particularly helpful for lazy users
* Freedom of adding more category of task besides the default(E.g. Taskal, Shopping, Work)
* Allows user to have an immediate view of the task lying ahead on current day or week
* Additional feature of showing productivity of user is useful to motivate user to be on the ball
* Priority can be set for every task to help decision making in performing task
* Typos are predicted e.g. "Ev Thursday" is registered as "Every Thursday"
Weakness:
* Free version may be limited as we are unable to add to labels to all tasks.
* Reminders are not available in the free version.
* Unable to add notes/details onto the specific task in free version.

**Wunderlist**
Strengths:
* Good GUI. Pleasing to the eye. 
* Ability to share events with others. (Family, Friends)
* Reminders in place for upcoming tasks. [Alarms, email notification, notification light colour]
* Smart Due Dates: Automatically detects words like “tomorrow” or “next week” and adds an event for that day.
* Set priority for tasks.
* Star To-dos: Moves starred tasks to the top of the list automatically.
* Quick add notification
* Different taskalizable folders (Family, Private, School, Work, …).
* Connects to Facebook and Google account.
* Duplication of the list.
* Completed to-do list hidden unless selected.
Weakness:
* Slow in updating changes

**Remember The Milk**
Strengths:

Weakness:


