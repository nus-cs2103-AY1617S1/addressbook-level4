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
* [Appendix E : Product Survey](#appendix-e--product-survey)

&nbsp;

## Setting up

### Prerequisites

1. **JDK `1.8.0_60`**  or later<br>

    > Having any Java 8 version is not enough. <br>
    This application will not work with earlier versions of Java 8.

2. **Eclipse** IDE

3. **e(fx)clipse** plugin for Eclipse (Do the steps 2 onwards given in
   [this page](http://www.eclipse.org/efxclipse/install.html#for-the-ambitious))
   
4. **Buildship Gradle Integration** plugin from the 
   [Eclipse Marketplace](https://marketplace.eclipse.org/content/buildship-gradle-integration)

4. **Buildship Gradle Integration** plugin from the 
   [Eclipse Marketplace](https://marketplace.eclipse.org/content/buildship-gradle-integration)

### Importing the project into Eclipse

0. Fork this repo, and clone the fork to your computer

1. Open Eclipse (Note: Ensure you have installed the **e(fx)clipse** and **buildship** plugins as given 
   in the prerequisites above)
   
2. Click `File` > `Import`

3. Click `Gradle` > `Gradle Project` > `Next` > `Next`

4. Click `Browse`, then locate the project's directory

5. Click `Finish`

  > * If you are prompted to 'keep' or 'overwrite' config files, choose to 'keep'.
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
 

&nbsp;

## Design

### Architecture

<img src="images/Architecture.png" width="600"><br>
The **_Architecture Diagram_** given above explains the high-level design of the App.
Given below is a quick overview of each component.

`Main` has only one class called [`MainApp`](../src/main/java/seedu/address/MainApp.java). Listed below are its functions:
* At app launch: Initializes the components in the correct sequence, and connect them up with each other.
* At shut down: Shuts down the components and invoke cleanup method where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.
Two of those classes play important roles at the architecture level:
* `EventsCentre` : This class (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
  is used by components to communicate with other components using events (i.e. a form of _Event Driven_ design)
* `LogsCenter` : Used by many classes to write log messages to the App's log file.

The rest of the App consists four components:
* [**`UI`**](#ui-component) : The UI of tha App.
* [**`Logic`**](#logic-component) : The command executor.
* [**`Model`**](#model-component) : Holds the data of the App in-memory.
* [**`Storage`**](#storage-component) : Reads data from, and writes data to, the hard disk.

Each of the four components
* Defines its _API_ in an `interface` with the same name as the Component.
* Exposes its functionality using a `{Component Name}Manager` class.

For example, the `Logic` component (see the class diagram given below) defines its API in the `Logic.java`
interface and exposes its functionality using the `LogicManager.java` class.

<img src="images/LogicClassDiagram.png" width="800">

The _Sequence Diagram_ below shows how the components interact for the scenario where the user issues the
command `delete 3`.

<img src="images\SDforDeletePerson.png" width="800">

>Note how the `Model` simply raises a `AddressBookChangedEvent` when the Address Book data are changed,
 instead of asking the `Storage` to save the updates to the hard disk.

The diagram below shows how the `EventsCenter` reacts to that event, which eventually results in the updates
being saved to the hard disk and the status bar of the UI being updated to reflect the 'Last Updated' time. 

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
 that are in the `src/main/resources/view` folder.
 
For example, the layout of the [`MainWindow`](../src/main/java/seedu/address/ui/MainWindow.java) is specified in
 [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component has the following functions:
* Execute user commands using the `Logic` component
* Bind itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change.
* Respond to events raised from various parts of the App and update the UI accordingly

### Logic component

<img src="images/LogicClassDiagram.png" width="800"><br>

**API** : [`Logic.java`](../src/main/java/seedu/address/logic/Logic.java)

The `Logic` component works in the following manner:
1. `Logic` uses the `Parser` class to parse the user command.
2. This results in a `Command` object which is executed by the `LogicManager`.
3. The command execution can affect the `Model` (e.g. adding a person) and/or raise events.
4. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")` API call.
 
<img src="images/DeletePersonSdForLogic.png" width="800"><br>

### Model component

<img src="images/ModelClassDiagram.png" width="800"><br>

**API** : [`Model.java`](../src/main/java/seedu/address/model/Model.java)

The `Model` component has the following functions:
* Store a `UserPref` object that represents the user's preferences
* Store Agendum data
* Expose a `UnmodifiableObservableList<ReadOnlyPerson>` that can be 'observed' 
  e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.

> This component does not depend on any of the other three components.

### Storage component

<img src="images/StorageClassDiagram.png" width="800"><br>

**API** : [`Storage.java`](../src/main/java/seedu/address/storage/Storage.java)

The `Storage` component has the following functions:
* Save `UserPref` objects in json format and read it back
* Save the Address Book data in xml format and read it back

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

&nbsp;

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

&nbsp;

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
  

&nbsp;

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
 2. [Create a new release using GitHub](https://help.github.com/articles/creating-releases/) 
    and upload the JAR file your created.
   
### Managing Dependencies

A project often depends on third-party libraries. For example, Address Book depends on the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repo (this bloats the repo size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>

&nbsp;

## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`

Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | New user | See usage instructions | Refer to instructions when I forget how to use the App
`* * *` | User | Add a task | Keep track of tasks which I need to do
`* * *` | User | Delete a task/multiple tasks | Remove tasks that I no longer need to keep track of
`* * *` | User | Edit a task name | Update task details to reflect the latest changes
`* * *` | User | View all my tasks | Have a quick and clear reference of everything I need to do
`* * *` | User | Mark a task/multiple tasks as completed | Know that it is completed without deleting it, distinguish between completed and uncompleted tasks
`* * *` | User | Unmark a task from completed | Update the status of my task
`* * *` | User | Undo my last action(s) | Easily correct any accidental mistakes in the last command(s)
`* * *` | User | Search based on task name | Find a task without going through the entire list if I remember a few key words
`* * *` | User | Specify my data storage location | Easily locate the raw text file for editing and sync the file to a cloud storage service
`* * *` | User | Clear all existing tasks | Easily start afresh with a new task list
`* * *` | User | Exit the application by typing a command | Close the app easily
`* * *` | Busy user | Specify start and end time when creating tasks | Keep track of events with defined start and end dates
`* * *` | Busy User | Specify deadlines when creating tasks | Keep track of tasks which must be done by a certain and date and time
`* * *` | Busy User | Edit and remove start and end time of tasks | Update events with defined start and end dates
`* * *` | Busy User | Edit and remove deadlines of tasks | Update tasks which must be done by a certain and date and time
`* *` | User | Sort tasks by alphabetical order and date | Organise and easily locate tasks
`* *` | User | Filter upcoming and overdue tasks | Decide on what needs to be done soon
`* *` | User | Filter tasks based on whether they are marked/unmarked | View my tasks grouped by their state of completion. Review my completed tasks and decide on what I should do next
`* *` | User | See the count/statistics for upcoming/ overdue and pending tasks | Know how many tasks I need to do
`*` | User | Clear the command I am typing with a key | Enter a new command without having to backspace the entire command line
`*` | Advanced user | Specify my own short-hand alias commands | Enter commands faster
`*` | Advanced user | Remove or edit the short-hand alias commands | Update to use more suitable command aliases
`*` | Advanced user | Scroll through my past few commands | Check what I have done and redo actions easily
`* Unlikely` | Google calendar user | Sync my tasks with Google calendar | Keep track of my tasks online
`* Unlikely` | User | Add multiple time slots for a task | “Block” multiple time slots when the exact timing of a task is certain
`* Unlikely` | User | Add tags for my tasks | Group tasks together and organise my task list
`* Unlikely` | User | Search based on tags | Find all the tasks of a similar nature
`* Unlikely` | User | Add/Remove tags for existing tasks | Update the grouping of tasks
`* Unlikely` | User | Be notified of deadline/time clashes | Resolve these conflicts manually
`* Unlikely` | User | Key in emojis/symbols and characters from other languages e.g. Mandarin | Capture information in other languages
`* Unlikely` | Advanced User | Import tasks from an existing text file | Add multiple tasks efficiently without relying on multiple commands
`* Unlikely` | Advanced User | Save a backup of the application in a custom file | Restore it any time at a later date
`* Unlikely` | Busy user | Add recurring events or tasks | Keep the same tasks in my task list without adding them manually
`* Unlikely` | Busy User | Search for tasks by date (e.g. on/before a date) | Easily check my schedule and make plans accordingly
`* Unlikely` | Busy User | Search for a time when I am free | Find a suitable slot to schedule an item
`* Unlikely` | Busy user | Can specify a priority of a task | Keep track of what tasks are more important

&nbsp;

## Appendix B : Use Cases

(For all use cases below, the **System** is `Agendum` and the **Actor** is the `user`, unless specified otherwise)

### Use case 01 - Add a task

**MSS**

1. System prompts the Actor to enter a command
2. Actor enters an add command with the task name into the input box.
3. System adds the task.
4. System shows a feedback message (“Task <name> added”) and displays the updated list.
5. Use case ends.

**Extensions**

2a. No task description is provided

> 2a1. System shows an error message (“Please provide a task name/description”)
> Use case resumes at step 1

2b. There is an existing task with the same description and details

> 2b1. System shows an error message (“Please use a new task description”)
> Use case resumes at step 1

### Use case 02 - Delete a task

**MSS**

1. Actor requests to list tasks
2. System shows a list of tasks
3. Actor requests to delete a specific task in the list by its index
4. System deletes the task.
5. System shows a feedback message (“Task <index> deleted”) and displays the updated list.
6. Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. System shows an error message (“Please select a task on the list with a valid index”)
> Use case resumes at step 2

### Use case 03 - Rename a task

**MSS**

1. Actor requests to list tasks
2. System shows a list of tasks
3. Actor inputs index followed by new name of task to be modified
4. System updates the task 
5. System shows a feedback message (“Task <index> updated”) and displays the updated list.
6. Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. System shows an error message (“Please select a task on the list with a valid index”)
> Use case resumes at step 2

3b. No task description is provided

> 3b1. System shows an error message (“Please include a new task name”)
> Use case resumes at step 2

3c. There is an existing task with the same description and details

> 3c1. System shows an error message (“Please use a new task name”)
> Use case resumes at step 2

### Use case 04 - Modify a task’s start and end time and deadlines

**MSS**

1. Actor requests to list tasks
2. System shows a list of tasks
3. Actor inputs index followed by the new start/end time or deadline
4. System updates the task 
5. System shows a feedback message (“Task <index> ’s time updated”) and displays the updated list.
6. Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. System shows an error message (“Please select a task on the list with a valid index”)
> Use case resumes at step 2

3b. The new input time format is invalid

> 3b1. System shows an error message (“Please follow the given time format”)
> Use case resumes at step 2

### Use case 05 - Undo the previous command that modified the task list

**MSS**

1. Actor enters an undo command
2. System finds the most recent command that modified the task list
3. System undoes the identified command
4. System shows a feedback message (“The command <last-command> has been undone”) and displays the updated list.
5. Use case ends.

**Extensions**

2a. There are no previous commands that modify the list (since the launch of the application)

> 2a1. System shows an error message (“No previous command to undo”)
> Use case ends

### Use case 06 - Mark a task as completed

**MSS**:

1. Actor requests to list tasks
2. System show a list of tasks
3. Actor inputs index of the task to be marked
4. System updates the task
5. System shows a feedback message (“Task <index> is marked as completed”) and hides the marked task.
6. Use case ends

**Extensions**

2a. The list is empty

> 2a1. Use case ends

3a. The given index is invalid

> 3a1. System shows an error message (“Please select a task on the list with a valid index”)
> Use case resumes at step 2

### Use case 07 - Add short hand commands

**MSS**

1. Actor enters an alias command followed by the original command
2. System stores the new alias
3. System shows a feedback message (“The command <original-command> can now be keyed in as <new-command>”)
4. Use case ends.

**Extensions**

1a. There is no existing command with the original name specified

> 1a1. System shows an error message (“There is no such existing command”)
> Use case ends

1b. The new alias name is already reserved/used for other commands

> 1b1. System shows an error message (“The name is already in use”)
> Use case ends

*a. At any time, Actor choose to exit System

> *a1. System displays a goodbye message
> *a2. System closes the program

*b. At any time, Actor enters a invalid command

> *b1.  System gives an error message (“We do not understand the command: <invalid-command>”)
> *b2. System displays a short list of valid commands

&nbsp;

## Appendix C : Non Functional Requirements

1.  Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2.	Should be able to hold up to 1000 total tasks.
3.	Should come with automated unit tests.
4.	Should use a Continuous Integration server for real time status of master’s health.
5.	Should be kept open source code.
6.	Should favour DOS style commands over Unix-style commands.
8.	Should adopt an object oriented design.
9.	Should not violate any copyrights.
10.	Should have a response time of less than 1 second, for every action performed.
11.	Should work offline without an internet connection.
12.	Should work as a standalone application.
13.	Should not use relational databases to store data.
14.	Should store data in an editable text file.
15.	Should not require an installer.
16.	Should not use paid libraries and frameworks.

&nbsp;

## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

## Appendix E : Product Survey

#### Wunderlist

Strengths:
- Clearly displays tasks that have not been completed
- Tasks can be categorized under different lists
- Tasks can have sub tasks
- Possible to highlight tasks by marking as important (starred) or pinning tasks
- Can set deadlines for tasks
- Can create recurring tasks
- Can associate files with tasks
- Can be used offline
- Keyboard friendly – keyboard shortcuts to mark tasks as completed and important
- Search and sort functionality makes finding and organizing tasks easier
- Possible to synchronize across devices
- Give notifications and reminders for tasks near deadline or overdue

Weaknesses:
- Wunderlist has a complex interface and might require multiple clicks to get specific tasks done. For example, it has separate field to add tasks, search for tasks and a sort button. There are various lists & sub-lists. Each list has a completed/uncompleted  section and each task needs to be clicked to display the associated subtasks, notes, files and comment.
- New users might not know how to use the advanced features e.g. creating recurring tasks

