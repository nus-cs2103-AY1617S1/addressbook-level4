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

1. `Logic` uses the `MainParser` class to parse the user command. `MainParser` relies on `Parser` of [Natty by Joe Stelmach](https://github.com/joestelmach/natty) for natural language processing.
2. This results in a `Command` object which is executed by the `LogicManager`.
3. The command execution can affect the `Model` (e.g. adding a person) and/or raise events.
4. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`

### Model component

<img src="images/ModelClassDiagram.png" width="800"><br>

**API** : [`Model.java`](../src/main/java/seedu/address/model/Model.java)

The `Model`,
* Stores a `UserPref` object that represents the user's preferences
* Stores CMDo data
* Exposes a `UnmodifiableObservableList<ReadOnlyTask` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* Does not depend on any of the other three components.

### Storage component

<img src="images/StorageClassDiagram.png" width="800"><br>

**API** : [`Storage.java`](../src/main/java/seedu/address/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save CMDo data in xml format and read it back.

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

A project often depends on third party libraries. For example, CMDo depends on the 
[Natty](http://github.com/joestelmach/natty) for date/time natural language parsing. Managing these _dependencies_
can be automated using Gradle or Maven. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repo (this bloats the repo size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>

## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | new user | see usage instructions | refer to instructions when I forget how to use the App
`* * *` | user | add a new task using natural language | add a task to CMDo
`* * *` | user | delete a task | remove entries that I no longer need
`* * *` | user | edit a task| edit the task by accessing the task and typing the changes
`* * *` | user | find a task by keywords | access details of tasks quickly without having to go through the entire list
`* * *` | user |only view uncompleted task in CMDo| avoid being confused by completed task that are overdue| 
`* * *` | user | my tasks sorted by due date and due time|locate urgent tasks easily | 
`* * *` | user | remove my completed tasks| see only uncompleted tasks | 
`* * *` | user | book by time slots| block out time for unconfirmed events |
`* * *` | user | undo my previous action | make mistakes | 
`* * *` | user | redo my previous action upon undo | make mistakes |
`* * *` | user | set priority to my task | know which task is more important | 
`* *` | user |Simply assign a date due to my todo by typing in the due date| to enter due dates easily
`* *` | user |block out time slots of unconfirmed tasks| avoid scheduling tasks that clash
`* *` | user |auto reschedule a task i am unable to complete due at the moment| save the effort of manual rescheduling

## Appendix B : Use Cases

(For all use cases below, the **System** is `CMDo` and the **Actor** is the `user`, unless specified otherwise)

### Use case: Add a task

**MSS**

1. User requests to add a task
2. CMDo stores the natural language into data
3. CMD0 adds the task <br>
>Use case ends.

**Extensions**

1a. The given input is invalid

> 1a1. CMDo shows help message <br>
  Use case resumes at step 1

2a. Date is not specified

> CMDo stores the task as a floating one
  Use case resumes at step 3	

2b. Date is specified but time is not

> CMDo stores the task with the input date and the time would be 2359 for that date.
  Use case resumes at step 3

2c. Priority is not specified

> CMDo stores it as low priority
  Use case resumes at step 3

2d. Two priorities are specified

> CMDo stores it as the highest priority indicated (eg. low, high, high is stored as the priority)
   Use case resumes at step 3
  
### Use case: Block out time slot

**MSS**
  
  
1. User requests to block time slot
2. CMDo stores the natural language into data
3. CMD0 blocks the specified time slot <br>
>Use case ends.

**Extensions**

1a. The given input is invalid

> 1a. CMDo shows help message <br>
  Use case resumes at step 1

2a. Date is not specified

> 2a. CMDo shows help message <br>
  Use case resumes at step 1

2b. Date is specified but time is not

> 2b. CMDo shows help message <br>
  Use case resumes at step 1

2c. Time slot clash

> 2c1. CMDo shows a message informing of time clash and list all the blocked time slots. <br>
  Use case ends

### Use case: Delete a task

**MSS**

1. User requests to search a task
2. CMDo shows a list of tasks
3. User requests to delete a specific task in the list
4. CMD0 deletes the task <br>
>Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. CMDo shows help message <br>
  Use case resumes at step 2
  
### Use case: Edit a task

**MSS**

1. User requests to find a task or list all tasks
2. CMDo shows a list of tasks
3. User requests to edit a specific task in the list by index
4. User keys in the changes
5. CMD0 edits the task <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

>3a1. CMDo shows help message <br>
> Use case resumes at step 2

  
### Use case: Find a task

**MSS**

1. User requests to find a task
2. CMDo shows a list of tasks <br>
>Use case ends.

**Extensions**

1a. The list is empty

> Use case ends

### Use case: List all tasks

**MSS**

1. User requests to list all tasks
2. CMDo shows a list of tasks <br>
>Use case ends.

**Extensions**

1a. The list is empty

### Use case: List done tasks

> Use case ends

**MSS**

1. User requests to list done tasks
2. CMDo shows a list of done tasks <br>
>Use case ends.

**Extensions**

1a. The list is empty

> Use case ends

**MSS**

1. User requests to list blocked time slots
2. CMDo shows a list of blocked time slots <br>
>Use case ends.

**Extensions**

1a. The list is empty

> Use case ends

### Use case: Mark a task as done

**MSS**

1. User requests to find a task or list all tasks
2. CMDo shows a list of tasks 
3. Mark the task done by index 
4. The task is marked as done and moved to storage<br>
>Use case ends.

**Extensions**

1a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. CMDo shows help message <br>
  Use case resumes at step 2

4a. The task will not show up on list or find.

> Use case ends

### Use case: Undo

**MSS**

1. User requests to undo previous action
2. CMDo requests the user to confirm the action
3. CMDo undos the previous action <br>
>Use case ends.

**Extensions**

1a. There are no more previous actions

> Use case ends

2a. User does not confirm the action

> Use case ends

### Use case: Redo

**MSS**

1. User requests to redo previous action
2. CMDo requests the user to confirm the action
3. CMDo undos the previous action <br>
> Use case ends.

**Extensions**

1a. There are no more previous actions

> Use case ends

2a. User does not confirm the action

> Use case ends

### Use case: Change storage location

**MSS**

1. User requests to to change file storage location
2. CMDo requests the user to confirm the action
3. CMDo changes the file storage location <br>
> Use case ends.

**Extensions**

2a. User does not confirm the action

> Use case ends

### Use case: Exit

**MSS**

1. User requests to Exit CMDo
2. CMDo request the user to confirm the action
3. CMDo is exited <br>
>Use case ends.

**Extensions**

2a. User does not confirm the action

> Use case ends


## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 tasks.
3. Should come with automated unit tests and open source code.
4. Should favor DOS style commands over Unix-style commands.
5. Customize commands to suit user preference
6. Able to toggle layout for dark and light for colour blindness
7. Issue reminders for upcoming tasks
8. Auto-complete functionality for user entries
9. Google search function in CMDo

{More to be added}

## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

## Appendix E : Product Survey

### Product 1: WunderList

**PROS**
1. A true keyboard-only program, able to add new tasks with completion date using only keyboard. It also understands today, tomorrow, next Sunday, etc.
2. Has option to sort by date created, date due, alphabetical order, etc.
3. Shows history of completed tasks
4. Categories

**CONS**
1. Not keyboard-language enough. I would like to enter something like ‘Finish assignment by Tuesday' 'Remind Monday in NOC'.
 
---
### Product 2: Google Keep

**PROS**
1. Functions as To-Do List and note taking and has reminders.
2. Can archive notes and list
3. Have trash to recover past to-dos
4. Can share with another person via email.

**CONS**
1. 

---
### Product 3: Windows Sticky Note

**PROS**
1. Quick glance at tasks on a persisting interface.
2. No need to learn complicated commands because all items are written by themself.


**CONS**
1. Disorganized, no ability to sort by timing, name or done status.
2. Just another note pad.

---
### Product 4: Mac Reminders

**PROS**
1. Intuitive, beautiful interface with use of color headings.
2. Adding a task is as simple as clicking on an empty line in the 'notepad' and typing a name.
3. Syncing with iCloud so that tasks persist on all iDevices.
4. Built in reminders and alarm system.
5. Configurable categories, shown on the leftside toolbar for easy switching.

**CONS**
1. No natural language parsing for dates, due dates must be set manually by double-clicking on the created task. This adds an additional layer of complexity.

---

_Last updated 11 Oct 2016 by @author A0141128R_