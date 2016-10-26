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

>Note how the `Model` simply raises a `ToDoListChangedEvent` when the To Do List data are changed,
 instead of asking the `Storage` to save the updates to the hard disk.

<!-- @@author A0093896H -->
 The _Sequence Diagram_ below shows how the components interact for the scenario where the user issues the
 command `add buy milk `.

<img src="images\SDforAddTask.png" width="800">

>Note how both sequence diagrams are very similar. This is because of good separation of concerns between the different components.

<!-- @@author -->

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
* stores the To Do List data.
* exposes a `UnmodifiableObservableList<ReadOnlyTask>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.

### Storage component

<img src="images/StorageClassDiagram.png" width="800"><br>

**API** : [`Storage.java`](../src/main/java/seedu/address/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save the To Do List data in xml format and read it back.

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

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
      e.g. `seedu.todo.commons.UrlUtilTest`
   2. _Integration tests_ that are checking the integration of multiple code units
     (those code units are assumed to be working).<br>
      e.g. `seedu.todo.storage.StorageManagerTest`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as
      how the are connected together.<br>
      e.g. `seedu.todo.logic.LogicManagerTest`

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

A project often depends on third-party libraries. For example, To Do List depends on the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repo (this bloats the repo size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>

<!-- @@author A0142421X -->
## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | new user | see usage instructions | refer to instructions when I forget how to use the App
`* * *` | user | add a new task with or without starting date, end date, tags, priority level or details | store different tasks in the to-do list
`* * *` | user | delete a task | remove a task that I no longer need to do
`* * *` | user | search tasks by name, details, starting date, end date, tags or priority level | locate details of tasks without having to go through the entire list
`* * *` | user | update the name, details, starting date, end date, tags or priority level of a task | account for the possibility of change
`* * *` | user | undo the last operation | go back to the previous version
`* * *` | user | type a command in different ways | have flexibility in the command format
`* * *` | user | specify a location to store data | organize files as I like and access data from multiple devices using cloud syncing service
`* * *` | user | mark tasks as "finished" | view all completed tasks
`* * *` | user | unmark finsihed tasks | change status of tasks
`* *` | user | group tasks into similar categories | organise tasks well
`* *` | user | view tasks by categories | have clearer views of similar tasks
`* *` | user | have a daily summary of tasks | manage my time well for the day
`* *` | user | have a weekily summary of tasks | manage my time well for the week
`*` | user with many tasks in the to-do list | sort tasks by name | locate a task easily

<!-- @@author A0121643R -->
## Appendix B : Use Cases

(For all use cases below, the **System** is the `DoDo-Bird` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: Add a task

**MSS**

1. User enter command to add a task
2. DoDo-Bird displays information of the task and all empty fields
Use case ends.

**Extensions**

2a. User adds a task with same name as an existing task

> DoDo-Bird shows an error message <br>
  User case ends

#### Use case: Delete task

**MSS**

1. User requests to list tasks
2. DoDo-Bird shows a list of tasks
3. User requests to delete a specific task in the list
4. DoDo-Bird asks the user to confirm
5. User confirms the deletion
6. DoDo-Bird deletes the task <br>
Use case ends.

**Extensions**

2a. User does not confirm

> Use case ends

2b. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. DoDo-Bird shows an error message <br>
  Use case resumes at step 2

#### Use case: Search tasks by keyword

**MSS**

1. User enter command to search tasks by keywords such as name, details, starting date, end date, tags or priority level.
2. DoDo-Bird displays a list of tasks which contain those keywords.
Use case ends.

<!-- @@author A0093896H -->
## Appendix C : Non Functional Requirements

1. (Availability/Interoperability) Should work on any [mainstream OS](#mainstream-os) as long as it has Java 8 or higher installed.
2. (Capacity) Should be able to hold up to 1000 Tasks and Events.
3. (Maintainability) Should come with automated unit tests and open source code.
4. (Performance) Should be able to respond any command within 3 seconds.
5. (Security/Data Integrity) Should encrypt data.
6. (Reliability/Recoverability) Should be able to attempt to recover data for corrupted data files.

<!-- @@author A0138967J -->
## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

## Appendix E : Product Survey

Google Keep : SWOT
> Google Keep is an easy to use note taking application. It allows users to create different kinds of notes for different purposes. It also has some sort of a checklist which mimics the basic function of a to-do application. The user can also set reminders for a list but not for a specific task.

> A noticeable point about Google Keep is that there is no other way than a list to view the to-dos. Probably this is because Google has other forms of to-dos tracking application and Keep is just meant to be a lightweight option for users who do not need that much functionality.

Wunderlist : SWOT

> Wunderlist is a web and mobile application that allows user to keep track of their tasks. It has a very nice UI where user can change the background image of the application. Wunderlist also allows users to categorise their tasks into different groups which helps with organisation. Furthermore the application will hide all completed tasks by default from the users so the users will only see those that remain to be done.

> Wunderlist is a great application but one issue is that it does not allot tasks to belong to more than one categories. Another problem is that wunderlist as the name suggest, only allows users to view their tasks in a linear list format.

Todoist : SWOT
> Todoist is a cross platform task management application, with access to over 10 different platforms and the ability to collaborate on tasks. There are also multiple categories to choose from to tag tasks for, and with a daily streak, it encourages people to keep up the habit of clearing existing tasks that have been taken down. With Karma Mode, it allows also for users to rack up enough tasks during the week and have 'off days' where they do not have to continuously do tasks to keep their streak up.

> One flaw with Todoist is that the projects, while doing a great job of categorising tasks, do not possess subproject hierarchy and thus would make complex projects hard to split downwards in an orderly fashion.

Priority Matrix : SWOT

> Priority Matrix is a powerful software application that helps individuals be more effective at managing their priorities. It is supported on a number of platforms, including Microsoft Windows, Mac OS X, Android, and iOS. A unique feature of Priority Matrix is that it separates its UI into 4 quadrants (Critical and Immediate, Critical but not Immediate, Not Critical but Immediate, and Uncategorised) which organize tasks based on importance and urgency so that users can have better time management.

> One strength of Priority Matrix is that it offers a cloud-based synchronization of data, allowing for data management across multiple devices.

> One improvement for Priority Matrix is that it could make its UI more aesthetically pleasing.

Any.Do : SWOT

>Any.Do is a cross platform task managment application that categorises the tasks in terms of when they need to be done. The tasks can also be further categorised into custom categories that can also be shared with friends and family. The application has a very simple and intuitive UI with words of encouragement when tasks are cleared.

>However, the application does not provide the option to mark a task with a priority level. This means that it would be difficult the important tasks from the trivial tasks at a glance. Furthermore, tasks are not sorted in date order, tasks that are more recent may be pushed below tasks that are dated relatively later.
