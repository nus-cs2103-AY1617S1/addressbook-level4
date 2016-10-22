# Developer Guide


&nbsp;


## Table of contents

* [Introduction](#introduction)
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


## Introduction

Agendum is a task manager for busy users to manage their schedules and tasks via keyboard commands. It is a Java desktop application that has a **GUI** implemented with JavaFX.

This guide describes the design and implementation of Agendum. It will help developers (like you) understand how Agendum works and how to further contribute to its development. We have organized this guide in a top-down manner so that you can understand the big picture before moving on to the more detailed sections. Each sub-section is mostly self-contained to provide ease of reference.


## Setting up

### Prerequisites

* **JDK `1.8.0_60`**  or above<br>

    > This application will not work with any earlier versions of Java 8.

* **Eclipse** IDE

* **e(fx)clipse** plugin for Eclipse (Do the steps 2 onwards given in
   [this page](http://www.eclipse.org/efxclipse/install.html#for-the-ambitious))

* **Buildship Gradle Integration** plugin from the
   [Eclipse Marketplace](https://marketplace.eclipse.org/content/buildship-gradle-integration)


### Importing the project into Eclipse

1. Fork this repo, and clone the fork to your computer

2. Open Eclipse (Note: Ensure you have installed the **e(fx)clipse** and **buildship** plugins as given in the prerequisites above)

3. Click `File` > `Import`

4. Click `Gradle` > `Gradle Project` > `Next` > `Next`

5. Click `Browse`, then locate the project's directory

6. Click `Finish`

  > * If you are asked whether to 'keep' or 'overwrite' config files, choose to 'keep'.
  > * Depending on your connection speed and server load, it can even take up to 30 minutes for the set up to finish
      (Gradle needs time to download library files from servers during the project set up process)
  > * If Eclipse automatically changed any settings during the import process, you can discard those changes.

    > After you are done importing Agendum, it will be a good practice to enable assertions before developing. This will enable Agendum app to verify assumptions along the way. To enable assertions, follow the instructions [here](http://stackoverflow.com/questions/5509082/eclipse-enable-assertions)

### Troubleshooting project setup

* **Problem: Eclipse reports compile errors after new commits are pulled from Git**
	* Reason: Eclipse fails to recognize new files that appeared due to the Git pull.
	* Solution: Refresh the project in Eclipse:<br>

* **Problem: Eclipse reports some required libraries missing**
	* Reason: Required libraries may not have been downloaded during the project import.
	* Solution: [Run tests using Gardle](UsingGradle.md) once (to refresh the libraries).


&nbsp;


## Design

### 1. Architecture

<img src="images/Architecture.png" width="600"><br>

The **_Architecture Diagram_** given above summarizes the high-level design of Agendum.
Here is a quick overview of the main components of Agendum and their main responsibilities.

#### `Main`
The **`Main`** component has only one class called [`MainApp`](../src/main/java/seedu/agendum/MainApp.java). It is responsible for initializing all components in the correct sequence and connecting them up with each other at app launch. It is also responsible for shutting down the other components and invoking the necessary clean up methods when Agendum is shut down.


#### `Commons`
[**`Commons`**](#6-common-classes) represents a collection of classes used by multiple other components.
Two of those classes play important roles at the architecture level.

* `EventsCentre` : This class (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
  is used by components to communicate with other components using events (i.e. a form of _Event Driven_ design)
* `LogsCenter` : This class is used by many classes to write log messages to Agendum's log file to record noteworthy system information and events.


#### `UI`
The [**`UI`**](#2-ui-component) component is responsible for interacting with the user by accepting commands, displaying data and results such as updates to the task list.


#### `Logic`
The [**`Logic`**](#3-logic-component) component is responsible for processing and executing the user's commands.


#### `Model`
The [**`Model`**](#4-model-component) component is responsible for representing and holding Agendum's data.


#### `Storage`
The [**`Storage`**](#5-storage-component) component is responsible for reading data from and writing data to the hard disk.


Each of the `UI`, `Logic`, `Model` and `Storage` components:

* Defines its _API_ in an `interface` with the same name as the Component
* Exposes its functionality using a `{Component Name}Manager` class.

For example, the `Logic` component (see the class diagram given below) defines it's API in the `Logic.java`
interface and exposes its functionality using the `LogicManager.java` class.<br>
<img src="images/LogicClassDiagram.png" width="800"><br>


#### Event Driven Approach
Agendum applies an Event-Driven approach to reduce direct coupling between components. For example, consider the scenario where the user inputs `delete 1` described in the  _Sequence Diagram_ below. The `UI` component will invoke the `Logic` component’s  _execute_ method to carry out the given command, `delete 1`. The `Logic` component will identify the corresponding task and will call the `Model` component _deleteTasks_ method to update Agendum’s data and raise a `ToDoListChangedEvent`.

The _Sequence Diagram_ below illustrates how the components interact for the scenario where the user issues the
command `delete 1` to delete the first task in the displayed list. The `UI` component will invoke the `Logic` component's _execute_ method to carry out the given command. In this scenario, the `Logic` component will identify the corresponding task and invoke `Model`'s  _deleteTask(task)_ method to update the in-app memory and raise a `ToDoListChangedEvent`.

<img src="images\SDforDeleteTask.png" width="800">

> Note: When Agendum's data is changed, the `Model` simply raises a `ToDoListChangedEvent`.
  It does not directly request the `Storage` component to save the updates to the hard disk.
  Hence, `Model` is not directly coupled to `Storage`.

The diagram below shows what happens after a `ToDoListChangedEvent` is raised. `EventsCenter` will inform the subscribers (the `UI` and `Storage` components). Both components will then respond accordingly. `UI` will update the status bar to reflect the 'Last Updated' time while `Storage` will save the updates to the task data to hard disk. <br>

<img src="images\SDforDeleteTaskEventHandling.png" width="800">

The following sections will then give more details of each individual component.


### 2. UI component

<img src="images/UiClassDiagram.png" width="800"><br>

**API** : [`Ui.java`](../src/main/java/seedu/agendum/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `TaskListPanel`,
`StatusBarFooter`, `BrowserPanel` etc. All these, including the `MainWindow`, inherit the abstract `UiPart` class. They can be loaded using `UiPartLoader`.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files
 that are in the `src/main/resources/view` folder.<br>
 For example, the layout of the [`MainWindow`](../src/main/java/seedu/agendum/ui/MainWindow.java) is specified in
 [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component has the following functions:

* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change.
* Responds to events raised from various parts of the App and updates the UI accordingly.


### 3. Logic component

<img src="images/LogicClassDiagram.png" width="800"><br>

**API** : [`Logic.java`](../src/main/java/seedu/agendum/logic/Logic.java)

1. `Logic` uses the `Parser` class to parse the user command.
2. This results in a `Command` object which is executed by the `LogicManager`.
3. The command execution can affect the `Model` (e.g. adding a task) and/or raise events.
4. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.

You can view the Sequence Diagram below for interactions within the `Logic` component for the `execute("delete 1")` API call.<br>

<img src="images/DeleteTaskSdForLogic.png" width="800"><br>


### 4. Model component

As mentioned above, the `Model` component stores and manage Agendum's task list data and user's preferences. It also exposes a `UnmodifiableObservableList<ReadOnlyTask>` that can be 'observed' by other components e.g. the UI can be bound to this list and will automatically update when the data in the list change. It does not depend on other components such as `Logic` and `Storage`.  

The `Model` class is the interface of the `Model` component. It provides several APIs for the `Logic` and `UI` components to update and retrieve Agendum’s task list data. The **API** of the model component can be found at [`Model.java`](../src/main/java/seedu/agendum/model/Model.java).  

The structure and relationship of the various classes in the `Model` component is described in the diagram below.    

<img src="images/ModelClassDiagram.png" width="800"><br>

`ModelManager` implements the `Model` Interface. It stores a `UserPref` Object which represents the user’s preference. It stores multiple `ToDoList` objects, including the current and recent lists.  

Each `ToDoList` object has one `UniqueTaskList` object. A `UniqueTaskList` can contain multiple `Task` objects but does not allow duplicates.  

The `ReadOnlyToDoList` and `ReadOnlyTask` interfaces allow other classes and components, such as the `UI`, to access but not modify the list of tasks and their details.  

> * `ToDoList` can potentially be extended to have another `UniqueTagList` object to keep track of tags associated with each task and `ToDoList` will be responsible for syncing the tasks and tags.
> * `Name` is a class as it might be modified to have its own validation regex e.g. can only contain alphanumeric characters.

Using the same example, if the `Logic` component requests `Model` to _deleteTasks(task)_, the subsequent interactions between objects can be described by the following sequence diagram.  

<img src="images\SDforDeleteTaskModelComponent.png" width="800">

The identified task is removed from the `UniqueTaskList`. The `ModelManager` raises a `ToDoListChangedEvent` and back up the new to-do list to its history of saved lists.  

> `Model`’s _deleteTasks_ methods actually take in `ArrayList<ReadOnlyTask>` instead of a single task. We use _deleteTasks(task)_ for simplicity in the sequence diagram.


### 5. Storage component

<img src="images/StorageClassDiagram.png" width="800"><br>

**API** : [`Storage.java`](../src/main/java/seedu/agendum/storage/Storage.java)

The `Storage` component has the following functions:

* can save `UserPref` objects in json format and read it back.
* can save the Agendum data in xml format and read it back.


### 6. Common classes

Classes used by multiple components are in the `seedu.agendum.commons` package.

They are further separated into sub-packages - namely `core`, `events`, `exceptions` and `util`.

#### Core
This package consists of the essential classes that are required by multiple components.

* `ComponentManager` - Registers its event handlers in the EventsCenter. It is the base class of Agendum's manager classes, namely `LogicManager`, `ModelManager`, `StorageManager` and `UiManager`
* `Config` - Stores the configuration values of Agendum; these include:
   * Application title
   * Logging level
   * User preferences file path
   * ToDoList data path
* `EventsCenter` - Manages all events in the Agendum. Uses _Event Driven_ design to help other components communicate. Whenever a apecific event is raised, this class will be dispatch it to all the classes that have subscribed to this event. It is written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained)
* `GuiSettings` - Saves the setting of the GUI, such as the width, height and coordinates on the screen.
* `LogsCenter` -  Uses java.logging to write log messages to Agendum's log file.
* `Messages` - Stores generalised messages that will be visible to the user.
* `UnmodifiableObservableList` - Wraps an observable list, making it into an unmodifiable observable list
* `Version` - Stores information about the version of Agendum

#### Events
This package consists of the different type of events that can occur; these are used mainly by EventManager and EventBus.

* `SaveLocationChangedEvent` - Indicate the user uses the `store` command to request a change of data save location; will be dispatched to the UI and Storage classes.
* `ToDoListChangedEvent` - Indicates the user has mutated the todo list; will be dispatch to UI and Storage.
* `DataSavingExceptionEvent` - Indicates an exception during a file saving.
* `ExitAppRequestEvent` - Indicates the user has requested to exit the app.
* `IncorrectCommandAttemptedEvent` - TRepresents an incorrect input by the user
* `JumpToListRequestEvent` - Indicates a request to jump to the list of tasks
* `ShowHelpRequestEvent` - Indicates user has used the help `command`
* `TaskPanelSelectionChangedEvent` - Indicates the user has selected a different panel

#### Exceptions
This package consists of exceptions that may occur with the use of Agendum.

* `DataConversionException` - Conversion from one data format to another has failed.
* `DuplicateDataException` - There are multiple occurrences of the same data, when it is not allowed.
* `FileDeletionException` - A valid file cannot be deleted, e.g. the file is being used
* `IllegalValueException` - The given data does not fulfil requirements, e.g. Only positive numbers allowed, but -1 is given

#### Util
This package consists of additional utilities for the different components.
* `AppUtil` - Used for app specific functions, e.g. loading an image
* `CollectionUtil` - Used for checking or validating collections, e.g. ensure no null objects in a list
* `ConfigUtil` - Used for accessing the Config file, such as loading and saving
* `FileUtil` - Used for writing, reading and deleting files, as well as checking existence and validation
* `FxViewUtil` - Used for JavaFX views
* `JsonUtil` - Used for conversion between Java Objects and json files
* `StringUtil` - Used for additional functions in handling strings, such as checking if the string has only unsigned integers
* `UrlUtil` - Used for handling URLs to websites
* `XmlUtil` - Used for reading and writing XML files.


&nbsp;


## Implementation

### 1. Logging

We are using `java.util.logging` package for logging. The `LogsCenter` class is used to manage the logging levels
and logging destinations.

* The logging level can be controlled using the `logLevel` setting in the configuration file
  (See [Configuration](#2-configuration))
* The `Logger` for a class can be obtained using `LogsCenter.getLogger(Class)` which will log messages according to
  the specified logging level
* Currently log messages are output through `Console` and to a `.log` file.

**Logging Levels**

Currently, Agendum has 4 logging levels: `SEVERE`, `WARNING`, `INFO` and `FINE`. They record information pertaining to:

* `SEVERE` : A critical problem which may cause the termination of Agendum<br>
   e.g. fatal error during the initialization of Agendum's main window
* `WARNING` : A problem which requires attention and caution but allows Agendum to continue working<br>
   e.g. error reading from/saving to config file
* `INFO` : Noteworthy actions by Agendum<br>
  e.g. valid and invalid commands executed and their results
* `FINE` : Less significant details that may be useful in debugging<br>
  e.g. print the elements in actual list instead of just its size

### 2. Configuration

You can alter certain properties of our Agendum application (e.g. logging level) through the configuration file.
(default: `config.json`):


&nbsp;

## Testing

You can find all the test files in the `./src/test/java` folder.

### Types of Tests

#### 1. GUI Tests

These are _System Tests_ that test the entire App by simulating user actions on the GUI.
They are in the `guitests` package.

#### 2. Non-GUI Tests

These are tests that do not involve the GUI. They include,
   * _Unit tests_ targeting the lowest level methods/classes. <br>
      e.g. `seedu.agendum.commons.StringUtilTest` tests the correctness of StringUtil methods e.g. if a source string contains a query string, ignoring letter cases.
   * _Integration tests_ that are checking the integration of multiple code units
     (individual code units are assumed to be working).<br>
      e.g. `seedu.agendum.storage.StorageManagerTest` tests if StorageManager is correctly connected to other storage components such as JsonUserPrefsStorage.
   * Hybrids of _unit and integration tests_. These tests are checking multiple code units as well as
      how the are connected together.<br>
      e.g. `seedu.agendum.logic.LogicManagerTest` will check various code units from the `Model` and `Logic` components.

#### 3. Headless Mode GUI Tests

Thanks to the [TestFX](https://github.com/TestFX/TestFX) library we use,
our GUI tests can be run in [headless mode](#headless-mode). <br>
See [UsingGradle.md](UsingGradle.md#running-tests)  for instructions on how to run tests in headless mode.

### How to Test

#### 1. Using Eclipse

* To run all tests, right-click on the `src/test/java` folder and choose `Run as` > `JUnit Test`
* To run a subset of tests, you can right-click on a test package, test class, or a test and choose to run as a JUnit test.

#### 2. Using Gradle

* Launch a terminal on Mac or command window in Windows. Navigate to Agendum’s project directory. We recommend cleaning the project before running all tests in headless mode with the following command `./gradlew clean headless allTests` on Mac and `gradlew clean headless allTests` on Windows.
* See [UsingGradle.md](UsingGradle.md) for more details on how to run tests using Gradle.

>#### Troubleshooting tests
>**Problem: Tests fail because NullPointException when AssertionError is expected**

>* Reason: Assertions are not enabled for JUnit tests.
   This can happen if you are not using a recent Eclipse version (i.e. _Neon_ or later)
>* Solution: Enable assertions in JUnit tests as described
   [here](http://stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option). <br>
   Delete run configurations created when you ran tests earlier.


&nbsp;


## Dev Ops

### 1. Build Automation

We use Gradle to run tests and manage library dependencies. The Gradle configuration for this project is defined in _build.gradle_.

### 2. Continuous Integration

We use [Travis CI](https://travis-ci.org/) to perform _Continuous Integration_ on our project. When code is pushed to this repository, Travis CI will run the project tests automatically to ensure that existing functionality will not be negatively affected by the changes.

### 3. Making a Release

To contribute a new release:

 1. Generate a JAR file [using Gradle](UsingGradle.md#creating-the-jar-file).
 2. Tag the repo with the version number. e.g. `v1.1`
 2. [Create a new release using GitHub](https://help.github.com/articles/creating-releases/)
    and upload the JAR file you created.

### 4. Managing Dependencies

Agendum depends on third-party libraries, such as the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these dependencies have been automated using Gradle. Gradle can download the dependencies automatically hence the libraries are not included in this repo and you do not need to download these libraries manually. To add a new dependency, update _build.gradle_.


&nbsp;


## Appendix A : User Stories

>Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`

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
`* *` | User | Filter overdue tasks and upcoming tasks (due within a week) | Decide on what needs to be done soon
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

>For all use cases below, the **System** is `Agendum` and the **Actor** is the `user`, unless specified otherwise

### Use case 01 - Add a task

**MSS**

1. System prompts the Actor to enter a command
2. Actor enters an add command with the task name into the input box.
3. System adds the task.
4. System shows a feedback message ("Task `name` added") and displays the updated list
5. Use case ends.

**Extensions**

2a. No task description is provided

> 2a1. System shows an error message (“Please provide a task name/description”) <br>
> Use case resumes at step 1

2b. There is an existing task with the same description and details

> 2b1. System shows an error message (“Please use a new task description”) <br>
> Use case resumes at step 1

### Use case 02 - Delete a task

**MSS**

1. Actor requests to list tasks
2. System shows a list of tasks
3. Actor requests to delete a specific task in the list by its index
4. System deletes the task.
5. System shows a feedback message (“Task `index` deleted”) and displays the updated list
6. Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. System shows an error message (“Please select a task on the list with a valid index”) <br>
> Use case resumes at step 2

### Use case 03 - Rename a task

**MSS**

1. Actor requests to list tasks
2. System shows a list of tasks
3. Actor requests to rename a specific task in the list by its index and also input the new task name
4. System updates the task
5. System shows a feedback message (“Task `index` updated”) and displays the updated list
6. Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. System shows an error message (“Please select a task on the list with a valid index”) <br>
> Use case resumes at step 2

3b. No task description is provided

> 3b1. System shows an error message (“Please include a new task name”) <br>
> Use case resumes at step 2

3c. There is an existing task with the same description and details

> 3c1. System shows an error message (“Please use a new task name”) <br>
> Use case resumes at step 2

### Use case 04 - Schedule a task’s start and end time and deadlines

**MSS**

1. Actor requests to list tasks
2. System shows a list of tasks
3. Actor inputs index and the new start/end time or deadline of the task to be modified
4. System updates the task
5. System shows a feedback message (“Task `index`'s time/date has been updated”) and displays the updated list
6. Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. System shows an error message (“Please select a task on the list with a valid index”) <br>
> Use case resumes at step 2

3b. The new input time format is invalid

> 3b1. System shows an error message (“Please follow the given time format”) <br>
> Use case resumes at step 2

### Use case 05 - Undo previous command that modified the task list

**MSS**

1. Actor enters the undo command
2. System finds the latest command that modified the task list
3. System undo the identified command
4. System shows a feedback message (“The command `last-command` has been undone”) and displays the updated list.
5. Use case ends.

**Extensions**

2a. There are no previous commands that modify the list (since the launch of the application)

> 2a1. System shows an error message (“No previous command to undo”) <br>
> Use case ends

### Use case 06 - Mark a task as completed

**MSS**:

1. Actor requests to list tasks
2. System show a list of tasks
3. Actor requests to mark a task specified by its index in the list as completed
4. System updates the task
5. System shows a feedback message (“Task `index` is marked as completed”) and hides the marked task.
6. Use case ends

**Extensions**

2a. The list is empty

> 2a1. Use case ends

3a. The given index is invalid

> 3a1. System shows an error message (“Please select a task on the list with a valid index”) <br>
> Use case resumes at step 2

### Use case 07 - Add short hand commands

**MSS**

1. Actor enters a alias command and specify the name and new alias name of the command
2. System alias the command
3. System shows a feedback message (“The command `original-command` can now be keyed in as <new-command>”)
4. Use case ends.

**Extensions**

1a. There is no existing command with the original name specified

> 1a1. System shows an error message (“There is no such existing command”) <br>
> Use case ends

1b. The new alias name is already reserved/used for other commands

> 1b1. System shows an error message (“The name is already in use”) <br>
> Use case ends

*a. At any time, Actor choose to exit System

> *a1. System displays a goodbye message <br>
> *a2. System closes the program

*b. At any time, Actor enters a invalid command

> *b1.  System gives an error message (“We do not understand the command: `invalid-command`”)<br>
> *b2. System displays a short list of valid commands


### Use case 08 - Specify data storage location

**MSS**

1. Actor enters store command followed by a path to file
2. System updates data storage location to the specified path to file
3. System shows a feedback message ("New save location: `path-to-file`")
4. Use case ends.

**Extensions**

1a. Path to file is input as 'default'

> 1a1. System updates data storage location to default <br>
> 1a2. System shows a feedback message ("Save location set to default: `path-to-file`") <br>
> Use case ends

1b. File exists
> 1b1. System shows an error message ("The specified file exists; would you like to use LOAD instead?") <br>
> Use case ends

1c. Path to file is invalid

> 1c1. System shows an error message ("The specified path to file is invalid.") <br>
> Use case ends



### Use case 09 - Load from data file

**MSS**

1. Actor enters load command followed by a path to file
2. System saves current task list into existing data storage location
3. System loads task list from specified path to file
2. System updates data storage location to the specified path to file
3. System shows a feedback message ("Data successfully loaded from: `path-to-file`")
4. Use case ends.

**Extensions**

1a. Path to file is invalid

> 1a1. System shows an error message ("The specified path to file is invalid.") <br>
> Use case ends

3a. File is in the wrong format

> 3a1. System shows an error message ("File is in the wrong format.")<br>
> Use case ends


&nbsp;


## Appendix C : Non Functional Requirements

1.  Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2.	Should be able to hold up to 800 tasks in total (including completed tasks).
3.	Should come with automated unit tests.
4.	Should use a Continuous Integration server for real time status of master’s health.
5.	Should be kept open source code.
6.	Should favour DOS style commands over Unix-style commands.
7.	Should adopt an object oriented design.
8.	Should not violate any copyrights.
9.	Should have a response time of less than 1 second, for every action performed.
10.	Should work offline without an internet connection.
11.	Should work as a standalone application.
12.	Should not use relational databases to store data.
13.	Should store data in an editable text file.
14.	Should not require an installer.
15.	Should not use paid libraries and frameworks.


&nbsp;


## Appendix D : Glossary

##### Mainstream OS:

Windows, Linux, Unix, OS-X

##### Headless Mode:

In the headless mode, GUI tests do not show up on the screen. <br>
This means you can do other things on the Computer while the tests are running.


&nbsp;


## Appendix E : Product Survey

We conducted a product survey on other task managers. Here is a summary of the strengths and weaknesses of each application. The criteria used for evaluation are own preferences and Jim's requirements.

#### Wunderlist

*Strengths:*

* Clearly displays tasks that have not been completed
* Tasks can be categorized under different lists
* Tasks can have sub tasks
* Possible to highlight tasks by marking as important (starred) or pinning tasks
* Can set deadlines for tasks
* Can create recurring tasks
* Can associate files with tasks
* Can be used offline
* Keyboard friendly – keyboard shortcuts to mark tasks as completed and important
* Search and sort functionality makes finding and organizing tasks easier
* Possible to synchronize across devices
* Give notifications and reminders for tasks near deadline or overdue

*Weaknesses:*

* Wunderlist has a complex interface and might require multiple clicks to get specific tasks done. For example, it has separate field to add tasks, search for tasks and a sort button. There are various lists & sub-lists. Each list has a completed/uncompleted  section and each task needs to be clicked to display the associated subtasks, notes, files and comment.
* New users might not know how to use the advanced features e.g. creating recurring tasks

#### Google calendar

*Strengths:*

* Have a weekly/monthly/daily calendar view which will make it easy for users to visualize their schedules
* Can create recurring events
* Integrated with Gmail. A user can add events from emails easily and this is desirable since Jim's to do items arrive by emails
* Can be used offline
* Possible to synchronize across devices
* Calendar can be exported to CSV/iCal for other users
* CLI to quick add an event to a calendar instead of clicking through the screen
* Comprehensive search by name/details/people involved/location/time


*Weaknesses:*

* Not possible to mark tasks as completed
* Not possible to add tasks without deadline or time
* CLI does not support updating of tasks/deleting etc. Still requires clicking.
* New users might not know of the keyboard shortcuts
