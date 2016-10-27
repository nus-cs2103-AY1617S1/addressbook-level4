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
command 
`add Activity1`.

<img src="images\SDforAddActivity.png" width="800"> 

The _Sequence Diagram_ below shows how they interact when the user issues the command `delete 1`.

<img src="images\SDforDeleteTask.png" width="800">

>Note how the `Model` in both cases simply raises a `AddressBookChangedEvent` when the Lifekeeper data are changed,
 instead of asking the `Storage` to save the updates to the hard disk.

The diagram below shows how the `EventsCenter` reacts to those events, which eventually results in the updates
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

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("add Activity1")`
 API call.<br>
<img src="images/DeleteTaskSdForLogic.png" width="800"><br>

### Model component

<img src="images/ModelClassDiagram.png" width="800"><br>

**API** : [`Model.java`](../src/main/java/seedu/address/model/Model.java)

The `Model`,
* stores a `UserPref` object that represents the user's preferences.
* stores the Lifekeeper data.
* exposes a `UnmodifiableObservableList<ReadOnlyActivity>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.

### Storage component

<img src="images/StorageClassDiagram.png" width="800"><br>

**API** : [`Storage.java`](../src/main/java/seedu/address/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save the Lifekeeper data in xml format and read it back.

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

A project often depends on third-party libraries. For example, Lifekeeper depends on the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repo (this bloats the repo size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>

## Appendix A : User Stories
<!-- @@author A0131813R -->
Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | user | be able to quickly make changes onto his lifekeeper with just one single step | focus on tasks at hand
`* * *` | user | be able to set reminders for tasks | track his schedule despite being overwhelmed by work
`* * *` | user | mark out completed task | remove entries that I no longer need
`* * *` | user | undo the previous command that made change to the list
`* * *` | user | find a task by name | locate details of tasks without having to go through the entire list
`* * *` | user | be able to view his schedule in a visual display |  understand the flow of tasks.
`* * *` | user | see uncompleted tasks that are overdue marked out in red | visually see what are the task that I failed to complete on time
`* * *` | user | delete the unwanted task | keep my Lifekeep neat and clean
`* * *` | user | add task due date | I can set due time for my tasks
`* * *` | user | add event | I can set start and end time for the events
`* * *` | user | be able to add details such as priority and deadline only if I want to | add task even when I dont know the due date for it yet
`* *` | user | be able to block out multiple possible timings and confirm the exact timeslot at a later time
`* *` | user | have access to his schedule even without internet access.
`* *` | user | find out the next upcoming task without having to scan through the calendar or the task list.
`* *` | user | see his tasks ranked in terms of priority
`*` | user | share tasks with other colleagues working on the same tasks.
`*` | user | see how his tasks are interrelated, since many of the tasks have prerequisites.
{More to be added}
<!-- @@author -->
## Appendix B : Use Cases

(For all use cases below, the **System** is the `Lifekeeper` and the **Actor** is the `user`, unless specified otherwise)


#### Use case: Delete task

**MSS**

Prerequisite steps:
1. User requests to search for task that (s)he wishes to delete
2. Lifekeeper shows an indexed list of tasks that matches the search 

Preconditions:
Lifekeeper has returned an indexed list of tasks that matches the search

1. User finds the index of the task (s)he wants to delete and enters the index of the task to be deleted
2. Lifekeeper asks the user to confirm the decision.
3. User enters 'Yes' into the CLI
4. Lifekeeper deletes the task, shows an acknowledgement message to user. <br>
Use case ends.

**Extensions**

1a. User cannot find the index of the task that (s)he wants to delete

> 1a1. User types in command to go back to the previous menu. <br>
  Users repeats the prerequisite steps 1-2. <br>
  Use case ends

1b. User does not want to delete any task

> 1b1. User types in command to go back to previous menu
  Use case ends

1c. User keys in invalid index

> 1c1. Lifekeeper gives an error message and returns an empty Command Box
  Use case resumes at step 1

3a. User types 'No' into the CLI

> 3a1. Lifekeeper returns to the indexed list of tasks that was searched by the user. <br>
  Use case ends

3a. The given index is invalid

> 3a1. Lifekeeper shows an error message <br>
  Use case resumes at step 2

#### Use case: Edit task

**MSS**

Prerequisite steps:
1. User requests to search for task that (s)he wishes to edit
2. Lifekeeper shows an indexed list of tasks that matches the search
3. User selects the correct task that (s)he wants to edit by entering the index of task

Preconditions:
User has already selected the task that (s)he wants to edit.

Editing steps:
1. Lifekeeper will return the Task with its corresponding parameters onto the CLI for users to directly edit.
2. User will edit the Task parameters directly through the CLI, or add new parameters through the CLI
3. Lifekeeper will update the Task parameters accordingly, and then show the updated Task and its parameters to the user.
Use case ends.

**Extensions**

2a. Parameters input by user is invalid

> 2a1. Lifekeeper shows the relevant error message to user (depending on the error found by Lifekeeper).
> 2a2. Lifekeeper return the Task with its corresponding parameters onto the CLI for users to directly edit, and highlights the parameter that is causing the error
> 2a3. User makes edits the parameters by changing the words on the CLI
Repeat steps 2a1-2a3 until Lifekeeper judges that the input parameters are all valid
Use case resumes from step 3.

## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 tasks.
3. Should come with automated unit tests and open source code.
4. Should favor DOS style commands over Unix-style commands.
5. Should be able to be opened by hotkey
6. Should have clear documentations
7. Should be user-friendly with neat layout
8. Output should be exportable to another computer (example through XML files)

## Appendix D : Glossary


##### Mainstream OS

> Windows, Linux, Unix, OS-X

##### User-Friendly

> Intuitive to majority of the targeted users, they should be able to use the software with ease.

##### Command-Line Interface

> interface which works based on taking string of command as input

## Appendix E : Product Survey

#### Google Calendar

> 1. Reminder: allows user to set reminder ahead of the time of event/task

> 2. Calendar display: user can view tasks in a calendar format

#### Todoist

> 1. Prioritize: allows user to set priority to see what tasks should be done first

> 2. Categorization: user can categorize tasks, so that tasks can be viewed by categories

#### Trello

> 1. Selective visibility: enables members to view only the projects or tasks that are relevant to them

> 2. Detailed categorisation: allows 3 levels of categorisations for tasks

> 3. Message boards: permits members delegated to a certain task to hold discussions or ask questions

#### Fantastical



> 1. Widget on desktop/phone: users have an overview of the upcoming tasks without opening the application



> 2. Calendar Sets: users can toggle between different calendars for different purposes - for example a work calendar and a family calendar



> 3. Add events using Natural Language: users can add tasks and events using natural language and expect the details to be interpret and input automatically