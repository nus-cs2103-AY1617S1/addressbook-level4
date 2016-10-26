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
* [Appendix E: Product Survey](#appendix-e--product-survey)


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

<img src="images/Architecture.png" width="600"><br>
The **_Architecture Diagram_** given above explains the high-level design of the App.
Given below is a quick overview of each component.

`Main` has only one class called [`MainApp`](../src/main/java/tars/MainApp.java). It is responsible for,
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

**API** : [`Ui.java`](../src/main/java/tars/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `TaskListPanel`,
`StatusBarFooter`, `BrowserPanel` etc. All these, including the `MainWindow` inherits from the abstract `UiPart` class
and they can be loaded using the `UiPartLoader`.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files
 that are in the `src/main/resources/view` folder.<br>
 For example, the layout of the [`MainWindow`](../src/main/java/tars/ui/MainWindow.java) is specified in
 [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component,
* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change.
* Responds to events raises from various parts of the App and updates the UI accordingly.

### Logic component

<img src="images/LogicClassDiagram.png" width="800"><br>

**API** : [`Logic.java`](../src/main/java/tars/logic/Logic.java)

1. `Logic` uses the `Parser` class to parse the user command.
2. This results in a `Command` object which is executed by the `LogicManager`.
3. The command execution can affect the `Model` (e.g. adding a task) and/or raise events.
4. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`

### Model component

<img src="images/ModelClassDiagram.png" width="800"><br>

**API** : [`Model.java`](../src/main/java/tars/model/Model.java)

The `Model`,
* Stores a `UserPref` object that represents the user's preferences
* Stores the TARS data
* Exposes a `UnmodifiableObservableList<ReadOnlyTask>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* Does not depend on any of the other three components.

### Storage component

<img src="images/StorageClassDiagram.png" width="800"><br>

**API** : [`Storage.java`](../src/main/java/tars/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save the TARS data in xml format and read it back.

### Common classes

Classes used by multiple components are in the `tars.commons` package. 

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
      e.g. `tars.commons.UrlUtilTest`
   2. _Integration tests_ that are checking the integration of multiple code units 
     (those code units are assumed to be working).<br>
      e.g. `tars.storage.StorageManagerTest`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as 
      how the are connected together.<br>
      e.g. `tars.logic.LogicManagerTest`
  
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

A project often depends on third party libraries. For example, TARS depends on the 
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repo (this bloats the repo size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>

## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :---------- | :--------- | :-----------
`* * *` | new user | see usage instructions | refer to instructions when I forget how to use the App
`* * *` | user | add a new events (with start and end timings) | keep track of it and complete it in the future
`* * *` | user | add a new task (tasks that have to be done before a specific deadline) | keep track of the deadline
`* * *` | user | add a floating task (tasks without specific times) | have a task that can roll over to the next day if I did not get to it
`* * *` | user | delete a task | remove tasks that I no longer need to do
`* * *` | user | edit a task | change the details of the tasks
`* * *` | user | view tasks | decide on the follow-up action for each task
`* * *` | user | clear all the data | remove all my information
`* *` | user | prioritize my task | do the more important ones first
`* *` | user | search for a task by keywords | view the details of task and complete it
`* *` | user | undo a command | undo the last action that I just performed
`* *` | user | redo a command | redo the last action that I just performed
`* *` | user | add recurring tasks | save time entering the same task over multiple dates
`* *` | user | choose my data storage location | have the flexibility to use the program on multiple computers as they can read from the same file stored on the cloud e.g. Google Drive
`* *` | user | add a tag on tasks | categorize my task
`* *` | user | edit a tag | rename the tag without the need to delete and add it again
`* *` | user | mark my tasks as done | indicate that the task has been completed
`* *` | user | mark my tasks as undone | indicate that the task has not been completed
`* *` | user | view tasks by tags/priority/date | group my tasks based on a field of my choice
`* *` | user | reserve dates for a task/event | block out time slots and add them upon confirmation of the time and date details
`* *` |user| can view all tags and edit them | edit a specific tag of all tasks with that tag in one command
`*` | user | have flexibility in entering commands | type in commands without having to remember the exact format
`*` | user | use a keyboard shortcut to launch the program | launch the program quickly
`*` | user | have suggestions on free slots | decide when to add a new task or shift current tasks

{More to be added}

## Appendix B : Use Cases

(For all use cases below, the **System** is the `TARS` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: UC01 - View help

**MSS**

1. User requests to view help
2. TARS shows a list of usage intructions<br>
Use case ends.

#### Use case: UC02- Add task

**MSS**

1. User requests to submit a new task
2. TARS save the task and add the command to command history<br>
Use case ends.

**Extensions**

2a. The format is invalid

> 2a1. TARS shows an error message<br>
  Use case resumes at step 1

2b. The end datetime is smaller than start datetime

> 2b1. TARS shows an error message<br>
  Use case resumes at step 1
  
#### Use case: UC03- Delete task

**MSS**

1. User requests to list tasks
2. TARS shows a list of tasks
3. User requets to delete a specific task in the list
4. TARS deletes the task<br>
Use case ends.

**Extensions**

2a. The list is empty

> 2a1. Use case ends

3a. The given index is invalid

> 3a1. TARS shows an error message<br>
  Use case resumes at step 2
  
#### Use case: UC04 - Edit task

**MSS**

1. User requests to list tasks
2. TARS shows a list of tasks
3. User requests to edit a specific task in the list
4. TARS updates the task<br>
Use case ends.

**Extensions**

2a. The list is empty

> 2a1. Use case ends

3a. The given index is invalid

> 3a1. TARS shows an error message<br>
  Use case resumes at step 2
  
3b. The format is invalid

> 3b1. TARS shows an error message<br>
  Use case resumes at step 2
  
#### Use case: UC05 - Edit task by appending details

**MSS**

1. User requests to list tasks
2. TARS shows a list of tasks
3. User requests to edit a specific task in the list by appending details
4. TARS updates the task<br>
Use case ends.

**Extensions**

2a. The list is empty

> 2a1. Use case ends

3a. The given index is invalid

> 3a1. TARS shows an error message<br>
  Use case resumes at step 2
  
3b. The format is invalid

> 3b1. TARS shows an error message<br>
  Use case resumes at step 2
  
#### Use case: UC06 - Edit tag name
  
**MSS**

1. User requests to list tags
2. TARS shows a list of tags
3. User requests to edit a specific tag in the list
4. TARS updates the tag<br>
Use case ends.

**Extensions**

2a. The list is empty

> 2a1. Use case ends

3a. The given index is invalid

> 3a1. TARS shows an error message<br>
  Use case resumes at step 2
  
3b. The format is invalid

> 3b1. TARS shows an error message<br>
  Use case resumes at step 2
  
#### Use case: UC07 - Delete tag

**MSS**

1. User requests to list tags
2. TARS shows a list of tags
3. User requests to delete a specific tag in the list
4. TARS deletes the tag<br>
Use case ends.

**Extensions**

2a. The list is empty

> 2a1. Use case ends

3a. The given index is invalid

> 3a1. TARS shows an error message<br>
  Use case resumes at step 2
  
#### Use case: UC08 - List tags

**MSS**

1. User requests to list tags
2. TARS shows a list of tags<br>
Use case ends.

#### Use case: UC09 - Undo a previous command

**MSS**

1. User requests to undo a previous command
2. TARS reinstates (undo) the last command in the undo history list and add the command to the redo history list<br>
Use case ends.

**Extensions**

2a. The undo history list is empty

> 2a1. Use case ends

#### Use case: UC10 - Redo a previous undo command

**MSS**

1. User requests to redo a previous command
2. TARS redo the last command in the redo history list and add the command to the undo history list<br>
Use case ends.

**Extensions**

2a. The redo history list is empty

> 2a1. Use case ends

{More to be added}

## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 tasks.
3. Should come with automated unit tests and open source code.
4. Should favor DOS style commands over Unix-style commands.

{More to be added}

## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

## Appendix E : Product Survey

Product | Strength | Weaknesses
-------- | :-------- | :--------
[Wunderlist](https://www.wunderlist.com/)|<ol type="1"><li>Cloud-based<ul><li>Ability to sync tasks</li></ul></li><li>Multiple-device Usage</li><li>Data is stored on the device and syncs with cloud storage when there’s internet access<ul><li>Faster than internet based todo apps like Google Calendar</li></ul></li><li>Provides reminders</li><li>Simple user interface not too cluttered</li><li>Able to set a deadline (for dates only) for a task</li></ol>|<ol type="1"><li>Requires a lot of ‘clicks’ and fields to fill to save a task</li><li>Unable to “block” multiple slots when the exact timing of a task is uncertain</li><li>Unable to set a due time for tasks</li></ol>
[Todo.txt](http://todotxt.com/)|<ol type="1"><li>Quick & easy unix-y access</li><li>Solves Google calendar being too slow</li><li>One shot approach</li><li>Manage tasks with as few keystrokes as possible</li><li>Works without Internet connectivity</li></ol>|<ol type="1"><li>No block feature</li><li>Unable to look for suitable slot</li></ol>
[Fantastical](https://flexibits.com/fantastical)|<ol type="1"><li>Flexible<ul><li>Choose between dark and light theme</li><li>Works with Google, iCloud, Exchange and more</li></ul></li><li>Use natural language to quickly create events and reminders</li></ol>|<ol type="1"><li>No block feature</li><li>Need to click to create an event</li><li>Only available for Mac</li></ol>
[Todoist](https://en.todoist.com/)|<ol type="1"><li>Good parser<ul><li>Extensive list of words to use that it is able to recognize (e.g. “every day/week/month, every 27th, every Jan 27th”)</li></ul></li><li>Able to reorganize task or sort by date, priority or name</li><li>Ability to tag labels</li><li>Able to see a week’s overview of tasks or only today’s task</li><li>Able to import and export task in CSV format</li><li>Able to search tasks easily (search bar at the top)</li><li>Able to add task at anytime and at any page (add task button next to search bar)</li></ol>|<ol type="1"><li>No block feature</li><li>Certain features can only be accessed by paying</li></ol>


