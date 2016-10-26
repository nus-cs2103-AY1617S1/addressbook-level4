# Developer Guide

<!-- @@author A0138420N --> 

* [Introduction](#introduction)
* [Setting Up](#setting-up)
* [Design](#design)
* [Implementation](#implementation)
* [Testing](#testing)
* [Dev Ops](#dev-ops)
* [Appendix A : User Stories](#appendix-a--user-stories)
* [Appendix B : Use Cases](#appendix-b--use-cases)
* [Appendix C : Non Functional Requirements](#appendix-c--non-functional-requirements)
* [Appendix D : Glossary](#appendix-d--glossary)
* [Appendix E : Product Survey](#appendix-e--product-survey)



## Introduction
Welcome talented programmers, we are glad that you are willing to be part of the team and help us in the development and improvement of GGist. To get you started, we have created this developer guide to aid new members like you to get familiar with overall coding design of GGist and clarify your doubts if any.

In this guide, we will walk you through different components of GGist and how they interact with one another when a command is issued. To aid your understanding, component diagrams of GGist will be included to give you a clear overview of the program. We welcome you to create new commands or modify the existing commands that you think are necessary and you can use Gradle Testing to examine if they are working the way you intended. 

Are you ready to begin this exciting and challenging journey? Let's go!


## Setting up

#### Prerequisites

1. **JDK `1.8.0_60`**  or later<br>

    > Having any Java 8 version is not enough. <br>
    This application will not work with earlier versions of Java 8.
    
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

  > * If you are asked whether to 'keep' or 'overwrite' configuration files, choose to 'keep'.
  > * Depending on your connection speed and server load, it can take up to 30 minutes for the set up to complete
      (This is because Gradle downloads library files from servers during the project set up process)
  > * If Eclipse auto-change any settings files during the importing process, you can discard those changes.
  
#### Troubleshooting project setup

**Problem: Eclipse reports compilation errors after new commits are pulled from Git**
* Reason: Eclipse fails to recognize new files pulled from Git. 
* Solution: Refresh the project in Eclipse:<br> 
  Right click on the project (in Eclipse package explorer), choose `Gradle` -> `Refresh Gradle Project`.
  
**Problem: Eclipse reports some required libraries missing**
* Reason: Required libraries may not have been downloaded during the project import. 
* Solution: [Run tests using Gardle](UsingGradle.md) once (to refresh the libraries).
 

## Design


### Architecture

<img src="images/Architecture.png" width="600"><br>
>**_Figure 1_**: Architecture Diagram - explains the high-level design of the application

###An overview of each component.

**`Main`** has only one class called [`MainApp`](../src/main/java/seedu/ggist/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup method where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by other components.
Two of these classes play important roles at the architecture level.
* `EventsCentre` : This class (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
  is used by components to communicate with other components using events (i.e. a form of _Event Driven_ design)
* `LogsCenter` : This class is used by other classes to write log messages to the application's log file.

The remaining four components of the application are:
* [**`UI`**](#ui-component) : The UI of the application.
* [**`Logic`**](#logic-component) : The command executor.
* [**`Model`**](#model-component) : Holds the data of the application in-memory.
* [**`Storage`**](#storage-component) : Reads data from, and writes data to the hard disk.

Each of the four components
* Defines its _API_ in an `interface` with the same name.
* Exposes its functionality using a `{Component Name}Manager` class.

For example, the `Logic` component  defines its API in the `Logic.java`
interface, and it exposes its functionality using the `LogicManager.java` class (refer to Figure 2).<br>

###An overview of interactions between components

Figure 2 shows how components interact with each other when the user issues the
command `delete 3` (refer to Figure 2).

<img src="images\SDforDeleteTask.png" width="800">
>**_Figure 2_**: Sequence Diagram - shows the interaction between components when issued the command `delete 3`

>Note how the `Model` simply raises a `TaskMangerChangedEvent` when the GGist data are changed,
 instead of asking the `Storage` to save the updates to the hard disk.

Figure 3 shows how the `EventsCenter` reacts to the event (`delete 3`). This process eventually results in saving the updates to the hard disk, and updating the status bar of the UI to reflect the 'Last Updated' time (refer to Figure 3).<br>

<img src="images\SDforDeleteTaskEventHandling.png" width="800">
>**_Figure 3_**: EventsCentre Diagram - shows how EventsCentre reacts to the event (`delete 3`)

> Note how the event passes through the `EventsCenter` to the `Storage` and `UI`. This process is done without `Model` being coupled to other components. Thus, this Event-Driven approach helps us to reduce direct coupling between components.

###Additional details of each component.

### UI component

<img src="images/UiClassDiagram.png" width="800"><br>
>**_Figure 4_**: UI Diagram

**API** : [`Ui.java`](../src/main/java/seedu/ggist/ui/Ui.java)


The `UI` consists of a `MainWindow` that is made up of parts such as `CommandBox`, `ResultDisplay`,`TaskListPanel`,
`StatusBarFooter` and `BrowserPanel`.These UI parts inherit from the abstract `UiPart` class ,and they can be loaded using the `UiPartLoader`.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files which are in the `src/main/resources/view` folder.<br>

For example, the layout of the [`MainWindow`](../src/main/java/seedu/ggist/ui/MainWindow.java) is specified in [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component,
* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` changes.
* Responds to events raised from various parts of the application and updates the UI accordingly.

### Logic component

<img src="images/LogicClassDiagram.png" width="800"><br>
>**_Figure 5_**: Logic Diagram

**API** : [`Logic.java`](../src/main/java/seedu/ggist/logic/Logic.java)

The `Logic` component uses the `Parser` class to parse the user command. This results in a `Command` object being executed by the `LogicManager`. The command execution can affect the `Model` (e.g. adding a task) and/or raise events. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.

Figure 6 displays the interactions within the `Logic` component for the `execute("delete 1")` API call(refer to Figure 6).<br>
 
<img src="images/DeleteTaskSdForLogic.png" width="800"><br>
>**_Figure 6_**: Sequence Diagram - shows interactions within the `Logic` component for the `execute("delete 1")` API call

### Model component

<img src="images/ModelClassDiagram.png" width="800"><br>
>**_Figure 7_**: Model Diagram

**API** : [`Model.java`](../src/main/java/seedu/ggist/model/Model.java)

The `Model` component,
* stores a `UserPref` object that represents the user's preferences.
* stores the GGist data.
* exposes a `UnmodifiableObservableList<ReadOnlyTask>` that can be 'observed' e.g. the UI binds to this list
  so that the UI updates automatically when the data in the list changes.
* does not depend on any of the other three components.

### Storage component

<img src="images/StorageClassDiagram.png" width="800"><br>
>**_Figure 8_**: Storage Diagram

**API** : [`Storage.java`](../src/main/java/seedu/ggist/storage/Storage.java)

The `Storage` component,
*saves `UserPref` objects in json format and reads it back.
*saves the GGist data in xml format and reads it back.

### Common classes

Classes used by multiple components are in the `seedu.ggist.commons` package.

## Implementation

### Logging

`java.util.logging` package is used for logging. The `LogsCenter` class is used to manage the logging levels
and logging destinations.

* The logging levels can be controlled using the `logLevel` setting in the configuration file
  (See [Configuration](#configuration)).
* The `Logger` for a class can be obtained using `LogsCenter.getLogger(Class)` which will log messages according to
  the specified logging level.
* Current log messages are output through: `Console` and to a `.log` file.

**Logging Levels**

* `SEVERE` : Critical problem detected which may possibly cause the termination of the application
* `WARNING` : Can continue, but with caution
* `INFO` : Information showing the noteworthy actions by the application
* `FINE` : Details that is not usually noteworthy but may be useful in debugging
  e.g. print the actual list instead of just its size

### Configuration

Certain properties of the application can be controlled (e.g application name, logging level) through the configuration file 
(default: `config.json`):


## Testing

Tests can be found in the `./src/test/java` folder.

**In Eclipse**:
* To run all tests, right-click on the `src/test/java` folder and choose
  `Run as` > `JUnit Test`.
* To run a subset of tests, right-click on a test package, test class, or a test and choose to run as a JUnit test.

**Using Gradle**:
* See [UsingGradle.md](UsingGradle.md) for how to run tests using Gradle.

There are two types of tests:

1. **GUI Tests** - These are _System Tests_ that test the entire application by simulating user actions on the GUI. 
   These are in the `guitests` package.
  
2. **Non-GUI Tests** - These are tests not involving the GUI. They include: 
   1. _Unit tests_ targeting the lowest level methods/classes. <br>
      e.g. `seedu.ggist.commons.UrlUtilTest`
   2. _Integration tests_ that are checking the integration of multiple code units 
     (those code units are assumed to be working).<br>
      e.g. `seedu.ggist.storage.StorageManagerTest`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as 
      how they are connected together.<br>
      e.g. `seedu.ggist.logic.LogicManagerTest`
  
**Headless GUI Testing** :
The [TestFX](https://github.com/TestFX/TestFX) library allows
 GUI tests to be run in the headless mode. 
 In the headless mode, GUI tests do not show up on the screen.
 This means that the developer can do other things on the computer while the tests are running.<br>
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
 3. [Crete a new release using GitHub](https://help.github.com/articles/creating-releases/) 
    and upload the JAR file your created.
   
### Managing Dependencies

A project often depends on third-party libraries. For example, GGist depends on the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repo (this bloats the repo size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>
<!-- @@author A0147994J --> 
## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | new user | see usage instructions | refer to instructions when I forget how to use the App
`* * *` | user | add a task without deadline | keep track of my tasks
`* * *` | user | Set a start and end time for a task | Have a clearer idea of my schedule
`* * *` | user | Set a deadline for a task | Be reminded of impending deadline
`* * *` | user | Delete a task | Totally remove task that I no longer care about
`* * *` | user | Strike off a task | View my completed task if I want to
`* * *` | user | Edit a task | Make changes to a task as necessary
`* * *` | user | Undo an action | Recover unwanted changes
`* * *` | user | View all tasks in specific day | Easily identify my remaining tasks for the day
`* * *` | user | Search for tasks using keywords | Find all relevant tasks with ease
`* * *` | user | Backup my data on local storage | Ensure my recorded tasks will not be lost
`* * *` | user | Have some flexibility in my inputs | Use some variations while entering a task
`* *` | user | Set a recurring task | Avoid constantly entering the same task for different days 
`* *` | user | Block off time slots | Avoid scheduling conflicts tasks with unconfirmed timings
`* *` | user | Set priority level for a task | Easily identify a task that require immediate attention
`* *` | user | Launch the application with a keyboard shortcut | Access the application without using the mouse
`* *` | user | View the day’s tasks when the application is launched | Know which tasks to complete for the day without any additional input
`*` | user | Sync my data to Google Calendar | Sync my data across multiple desktops
`*` | user | Carry forward incomplete tasks to the following day | Be reminded to complete the task the following day without any input

## Appendix B : Use Cases

(For all use cases below, the **System** is the `GGist` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: Add a task

**MSS**

1. Users request to add a task by entering an input command
2. GGist adds a new task to the list <br>

**Extensions**

1a. The input command format is not correct

> GGist returns an error message with the correct input format                              
> Use case ends

1b. The new task conflicts with an existing task

> GGist informs user with a message and does not add the task to the list
>Use case ends

#### Use case: Edit a task

**MSS**

1. User requests to list all tasks
2. GGist shows a list of tasks
3. User requests to change a specific feature of a specific task
4. GGist change that task’s feature <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The task is not in the list

> 3a1. GGist shows an error message <br>
  Use case resumes at step 2

3b. Original task does not have that feature

> 3a1. Add a new feature to that task <br>
  Use case ends
  
## Appendix C : Non Functional Requirements

1. Should work on any mainstream OS as long as it has Java 1.8.0_60 or higher installed.
2. Should be able to hold up to 1000 tasks.
3. Should launch within 3 seconds
4. Should be user friendly
5. Should respond command within 1 second
6. Should be able to work offline
7. Should be able to launch with keyboard shortcut
8. Should have enough capacity to store all the information and tasks
9. Should sync with calendars such as Google calendar
10. Should look pleasant and easy on the eyes
11. Should be stable and reliable with as few breakdowns as possible

## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

##### Private contact detail

> A contact detail that is not meant to be shared with others


## Appendix E : Product Survey

#### iOS Calendar

**Strengths**

*Set task using the “one-shot” approach
*Syncs with Google Calendar
*All tasks are set in calendar view
*Alerts and prompts appear as notifications
*Search for Keywords

**Weakness**

*Unable to colour code for events
*Unable to strike off completed task
*Able to set recurring task, but unable to set using one “one-shot” approach
*Does not carry forwards uncompleted task 

#### Errands

**Strengths**

*Set priority for tasks
*Sort tasks based on priority level, due date and alphabetical order
*Strike off finished tasks
*Notification function
*View tasks in a particular day

**Weakness**

*Cannot search for a particular task
*Unable carry forward the incomplete tasks
*Cannot set recurring task
*Can only see a maximum of 10 tasks at the one time

#### Fantastical

**Strengths**

*Natural language processing
*Able to view tasks in day, week, month, year views
*Syncs with calendars
*Use shading to intensity of tasks
*Supports fixed and floating time zones for events

**Weakness**

*Cannot set recurring tasks
*Cannot transfer data to another computer
*Unable to sync account information with other computer

#### Google Calendar

**Strengths**

*Easy to choose a time slot and set task
*Can have multiple calendars
*Invite people to events
*Set reminders for events

**Weakness**

*Difficult to read if too many appointments 
*Must use with internet

<!-- @@author --> 

