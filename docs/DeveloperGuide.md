# Developer Guide 

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
* Solution: [Run tests using Gradle](UsingGradle.md) once (to refresh the libraries).
 

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

<img src="images\SDforDeletePerson.png" width="800">

>Note how the `Model` simply raises a `SavvyTaskerChangedEvent` when the Savvy Tasker data are changed,
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
3. The command execution can affect the `Model` (e.g. adding a person) and/or raise events.
4. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")`
 API call.<br>
<img src="images/DeletePersonSdForLogic.png" width="800"><br>

### Model component

<img src="images/ModelClassDiagram.png" width="800"><br>

**API** : [`Model.java`](../src/main/java/seedu/address/model/Model.java)

The `Model`,
* stores a `UserPref` object that represents the user's preferences.
* stores the Savvy Tasker data.
* exposes a `UnmodifiableObservableList<ReadOnlyPerson>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.

### Storage component

<img src="images/StorageClassDiagram.png" width="800"><br>

**API** : [`Storage.java`](../src/main/java/seedu/address/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save the Saavy Tasker data in xml format and read it back.

### Common classes

Classes used by multiple components are in the `seedu.saavytasker.commons` package.

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

A project often depends on third-party libraries. For example, Savvy Tasker depends on the
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
`* * *` | new user |  view more information about a particular command | learn how to use various commands
`* * *` | user | add a new task | record tasks that need to be done some day
`* * *` | user | update a task description/due date/priority level | make modifications to tasks without having delete and re-add it
`* * *` | user | mark completed tasks as done | remind myself that I have completed a task
`* * *` | user | delete a task | get rid of tasks that I no longer care to track 
`* * *` | user | sort ongoing tasks by due date or priority | decide what needs to be done soon
`* * *` | user | view the list of ongoing tasks | decide what needs to be done
`* * *` | user | view the list of archived tasks | see what has been done
`* *` | user | find tasks by partial or full task name or date | locate a tasks / similar tasks in case I forget the exact task name I typed
`*` | user | sort tasks by priority level | see the most important tasks and prioritize accordingly
`*` | user | undo most recent command | undo the most recent operation
`*` | user | redo most recent undo command | redo the operation done by the most recent undo action
`*` | advanced user | alias keywords with shorter versions | type a command faster
`*` | advanced user | remove alias of keywords with shorter versions | get rid of shorter version of certain keywords
{More to be added}

## Appendix B : Use Cases

(For all use cases below, the **System** is the `Savvy Tasker` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: Add task

**MSS**

1. Savvy Tasker waits for user command
2. User enters command to add a task according to some parameters <br>
3. Savvy Tasker adds the task to a list of tasks <br>
Use case ends.

**Extensions**

2a. At least one parameter entered by user is invalid
> 2a1. Savvy Tasker shows an error message and display the expected format.<br>
> Use case resumes at step 1


2b. START_DATE and END_DATE are different, the RECURRING_TYPE has to be larger than the duration between START_DATE and END_DATE. (e.g. A 3d2n camp cannot be recurring daily but it can be recurring weekly)
> 2b1. Savvy Tasker shows an error message <br>
> Use case resumes at step 1


2c. START_DATE and END_DATE are different, END_DATE is before START_DATE
> 2c1. Savvy Tasker shows an error message <br>
> Use case resumes at step 1


2d. START_DATE and END_DATE are the same, END_TIME is before START_TIME
> 2d1. Savvy Tasker shows an error message <br>
> Use case resumes at step 1

#### Use case: List tasks

**MSS**

1. Savvy Tasker waits for user command
2. User requests to list tasks
3. Savvy Tasker shows a list of tasks <br>

**Extensions**

3a. The list is empty

> 3a1. Savvy Tasker shows an error message <br>
  Use case ends

#### Use case: Find task

**MSS**

1. Savvy Tasker waits for user command
2. User requests to find tasks by keyword
3. Savvy Tasker displays the list of tasks that contains the keyword in the name<br>

**Extensions**

2a. No parameter entered after command word
> Savvy Tasker shows a 'no parameter entered' error message.<br>
> Use case resumes at step 1


3a. The list is empty
> 2a1. Savvy Tasker shows a 'no task found' error message.<br>
> Use case ends


#### Use case: Modify task

**MSS**

1. Savvy Tasker waits for user command
2. User requests to modify a certain attribute of a specific task
3. Savvy Tasker modifies the task and saves it in memory <br>
Use case ends.

**Extensions**

1a. The list is empty

> 1a1. Use case ends

2a. The given index is invalid

> 2a1. Savvy Tasker shows an error message <br>
  Use case resumes at step 3

2b. At least one parameter entered by user is invalid

> 2b1. Savvy Tasker shows an error message and display the expected format <br>
  Use case resumes at step 3

#### Use case: Mark task as done

**MSS**

1. Savvy Tasker waits for user command
2. User request to mark specific tasks in the list based on task’s index
3. Savvy Tasker marks the tasks<br>

**Extensions**

1a. The list is empty
> 2a1. Savvy Tasker shows a 'no task found' error message.<br>
> Use case ends

2a. The given index is invalid
> 3a1. Savvy Tasker shows a 'invalid index' error message <br>
> Use case resumes at step 1

2b. The task is already marked as done
> 3b1. Savvy Tasker shows a 'task already marked' error message.<br>
> Use case resumes at step 1

###Use case: Unmark of task

**MSS**

1. Savvy Tasker waits for user command
2. User requests to list archived tasks
3. Savvy Tasker displays a list of archived tasks, sorted by time and date the task has been marked
4. User request to unmark of the specific task in the list based on task’s index
4. Savvy Tasker removes the done mark of the specific task <br>

**Extensions**

2a. The list is empty
> Use case ends

3a. The given index is invalid
> 3a1. Savvy Tasker shows a 'invalid index' error message <br>
> Use case resumes at step 1


#### Use case: Delete task

**MSS**

1. Savvy Tasker waits for user command
2. User requests to list tasks
3. Savvy Tasker shows a list of tasks
4. User requests to delete a specific task in the list
5. Savvy Tasker deletes the task <br>
Use case ends.

**Extensions**

3a. The list is empty

> Use case ends

4a. The given index is invalid

> 4a1. Savvy Tasker shows an error message 
> Use case resumes at step 3 <br>

###Use case: Alias keyword and use shorten keyword

**MSS**

1. Savvy Tasker waits for user command
2. User requests to alias a keyword (can be a command or any other frequently used word), with a shorten keyword
3. Savvy Tasker store the shorten keyword associated with the keyword in its database
4. User request a command
4. Savvy Tasker check if the command contain any shorten keyword, if it does, replace the shorten keyword with the associated keyword from its database
5. Savvy Tasker carry out the command <br>

**Extensions**

2a. The shorten keyword contains only 1 character
> 2a1. Savvy Tasker shows a error message 
> Use case resumes at step 1 <br>

2b. The shorten keyword has already been associated with other keywords
> 2b1. Savvy Tasker shows a error message and the shorten keyword's original associated keyword 
> Use case resumes at step 1 <br>

###Use case: Unalias keyword

**MSS**

1. Savvy Tasker waits for user command
2. User requests to unalias a shorten keyword
3. Savvy Tasker remove the shorten keyword associated with the keyword in its database <br>

**Extensions**

2a. The shorten keyword could not be found in Savvy Tasker database
> 2a1. Savvy Tasker shows a 'not found' error message 
> Use case resumes at step 1 <br>


## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 tasks.
3. Should come with automated unit tests and open source code.
4. Should favor DOS style commands over Unix-style commands.
6. Should work stand-alone and should not be a plug-in to another software.
7. Should work without internet connection.
8. Should store data in text file.
9. Should work without requiring an installer.

{More to be added}

## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

##### Private contact detail

> A contact detail that is not meant to be shared with others

## Appendix E : Product Survey

#### Competing product: Google Calendar

**Pros:**

1. Able to color-code different events
2. Able to set reminders and task/events on repeat
3. Able to add description such as location, remarks and people
4. Able to sync on different devices
5. Able to share calendar
6. Able to undo previous action 
7. Able to drag and drop task/events to another date/timing <br>

**Cons:**

1. Unable to check(tick) completed event
2. Do not have a list of archived task
3. Does not cater for floating task <br>

#### Competing product: Todo

**Pros:**

1. Auto prioritization
2. Unlimited contexts
3. Reminders
4. Auto sync with iCal, Toodledo, Outlook, Todo Online <br>

#### Competing product: Remember The Milk

**Pros:**

1. Unlimited contexts
2. Reminders
3. Calendar tasks
4. Auto sync with Gmail (Firefox plugin), Google Calendar, Twitter (direct integration), Atom/RSS, IM (feed)
5. Email notifications, autoprocess
6. API <br>
