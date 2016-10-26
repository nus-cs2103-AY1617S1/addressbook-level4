# Developer Guide 

Jimi is a simple task manager specifically catered for people like [Jim](http://www.comp.nus.edu.sg/~cs2103/AY1617S1/contents/handbook.html#handbook-project-product). It is a Java desktop application that has both a Text UI and a GUI. Jimi handles most, if not all, input via the command line interface (CLI).

This guide describes the design and implementation of Jimi. It will help you understand how Jimi works and how you can further contribute to its development. We have organised this guide in a top-down manner so that you can understand the big picture before moving on to the more detailed sections.

<br>

## Guide Map

* [Setting Up](#setting-up)
* [Design](#design)
* [Implementation](#implementation)
* [Testing](#testing)
* [Dev Ops](#dev-ops)
* [Appendix A: User Stories](#app-a)
* [Appendix B: Use Cases](#app-b)
* [Appendix C: Non Functional Requirements](#app-c)
* [Appendix D: Glossary](#app-d)
* [Appendix E : Product Survey](#app-e)

<br>
<br>

## Setting up

### Prerequisites

1. **JDK `1.8.0_60`**  or later<br>

    > Having any Java 8 version is not enough. <br>
    This app will not work with earlier versions of Java 8.
    
2. **Eclipse** IDE
3. **e(fx)clipse** plugin for Eclipse (Do the steps from step 2 onwards given in
   [this page](http://www.eclipse.org/efxclipse/install.html#for-the-ambitious)).
4. **Buildship Gradle Integration** plugin from the Eclipse Marketplace

<br>

### Importing the project into Eclipse

1. Fork this repo, and clone the fork to your computer.
2. Open Eclipse. (Note: Ensure you have installed the **e(fx)clipse** and **buildship** plugins as given 
   in the prerequisites above.)
3. Click `File` > `Import`.
4. Click `Gradle` > `Gradle Project` > `Next` > `Next`.
5. Click `Browse`, then locate the project's directory.
6. Click `Finish`.

  > * If you are asked whether to 'keep' or 'overwrite' config files, choose to 'keep'.
  > * Depending on your connection speed and server load, it can even take up to 30 minutes for the set up to finish.
      (This is because Gradle downloads library files from servers during the project set up process.)
  > * If Eclipse auto-changed any settings files during the import process, you can discard those changes.

<br>

### Troubleshooting project setup

**Problem: Eclipse reports compile errors after new commits are pulled from Git**
<p>* Reason: Eclipse fails to recognize new files that appeared due to the Git pull. </p>
* Solution: Refresh the project in Eclipse:<br> 
  Right click on the project (in Eclipse package explorer), choose `Gradle` -> `Refresh Gradle Project`.
<br>  
**Problem: Eclipse reports some required libraries missing**
<p>* Reason: Required libraries may not have been downloaded during the project import. </p>
* Solution: [Run tests using Gradle](UsingGradle.md) once (to refresh the libraries).
 
<br>
<br>
## Design

/* @@author A0140133B */

### Architecture

<img src="images/Architecture.png" width="600"><br><br>
The **_Architecture Diagram_** given above explains the high-level design of the App.
Given below is a quick overview of each component.

`Main` has only one class called [`MainApp`](../src/main/java/seedu/jimi/MainApp.java). It is responsible for:
* Initializing the components in the correct sequence, and connect them up with each other when app launches.
* Shutting down the components and invoke cleanup method where necessary when app shuts down.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.
Two of those classes play important roles at the architecture level.
<br>* `EventsCenter` : This class (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
  is used by components to communicate with other components using events (i.e. a form of _Event Driven_ design)
<br>* `LogsCenter` : Used by many classes to write log messages to the App's log file.
<br><br>
The rest of the App consists four components.
* [**`UI`**](#ui-component) : Displays interactions with the user
* [**`Logic`**](#logic-component) : Executes the commands
* [**`Model`**](#model-component) : Holds the data of the App in-memory.
* [**`Storage`**](#storage-component) : Reads data from, and writes data to, the hard disk.

Each of the four components
* Defines its [_API (Application program interface)_](#API) in an `interface` with the same name as the Component.
* Exposes its functionality using a `{Component Name}Manager` class.

For example, the `Logic` component (see the class diagram given below) defines it's API in the `Logic.java`
interface and exposes its functionality using the `LogicManager.java` class.<br>
<br>
<img src="images/LogicClassDiagram.png" width="800"><br><br>

The _Sequence Diagram_ below shows how the components interact for the scenario where the user issues the
command `delete t3`.
<br>
<img src="images\SDforDeleteTask.png" width="800">
<br><br>
>Note how the `Model` simply raises a `TaskBookChangedEvent` when Jimi's data changes,
 instead of asking the `Storage` to save the updates to the hard disk.

The diagram below shows how the `EventsCenter` reacts to that event, which eventually results in the updates
being saved to the hard disk and the status bar of the UI being updated to reflect the 'Last Updated' time. 
<br>
<img src="images\SDforDeleteTaskEventHandling.png" width="800">
<br><br>
> Note how the event is propagated through the `EventsCenter` to the `Storage` and `UI` without `Model` having
  to be coupled to either of them. This is an example of how this Event Driven approach helps us reduce direct 
  coupling between components.

The sections below give more details of each component.

<br><br>

/* @@author A0140133B */

### UI component
<br>
<img src="images/UiClassDiagram.png" width="800">
<br><br>

**API** : [`Ui.java`](../src/main/java/seedu/jimi/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `TaskListPanel`,
`StatusBarFooter`, `BrowserPanel` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class
and they can be loaded using the `UiPartLoader`.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files
 that are in the `src/main/resources/view` folder.<br>
 For example, the layout of the [`MainWindow`](../src/main/java/seedu/jimi/ui/MainWindow.java) is specified in
 [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component:
* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change.
* Responds to events raised from various parts of the App and updates the UI accordingly.

<br>

/* @@author A0140133B */

### Logic component
<br>
<img src="images/LogicClassDiagram.png" width="800"><br><br>

**API** : [`Logic.java`](../src/main/java/seedu/jimi/logic/Logic.java)

The `Logic` component:
* uses the `Parser` class to parse the user command.
* Creates a `Command` object which is executed by the `LogicManager`.
* Changes the model (e.g. when adding a task) and/or raise events along with the command execution.
* Encapsulates the result of the command execution as a `CommandResult` object and passes it back to the `Ui`.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete t1")`
 API call.<br>
<img src="images/DeleteTaskSdForLogic.png" width="800"><br><br>

<br>

/* @@author A0140133B */

### Model component

<br>
<img src="images/ModelClassDiagram.png" width="800"><br><br>

**API** : [`Model.java`](../src/main/java/seedu/jimi/model/Model.java)

The `Model` component:
* stores a `UserPref` object that represents the user's preferences.
* stores Jimi's data.
* exposes a `UnmodifiableObservableList<ReadOnlyTask>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.

<br>

/* @@author A0140133B */

### Storage component

<br>
<img src="images/StorageClassDiagram.png" width="800">
<br><br>

**API** : [`Storage.java`](../src/main/java/seedu/jimi/storage/Storage.java)

The `Storage` component:
* saves `UserPref` objects in json format and reads it back.
* saves Jimi's data in xml format and reads it back.

<br>

### Common classes

Classes used by multiple components are in the `seedu.jimi.commons` package.

<br><br>
## Implementation

### Logging

We are using `java.util.logging` package for logging. The `LogsCenter` class is used to manage the logging levels
and logging destinations.

* The logging level can be controlled using the `logLevel` setting in the configuration file
  (See [Configuration](#configuration))
* The `Logger` for a class can be obtained using `LogsCenter.getLogger(Class)` which will log messages according to
  the specified logging level
* Currently log messages are output through: `Console` and to a `.log` file.<br>
<br>

**Logging Levels**

* `SEVERE` : Shows that critical problems, which may possibly cause the termination of the application, are detected.
* `WARNING` : Shows that application can continue operation, but with caution.
* `INFO` : Shows information of the noteworthy actions by the App
* `FINE` : Shows details that is not usually noteworthy but may be useful in debugging
  e.g. print the actual list instead of just its size
  
<br>

### Configuration

Certain properties of the application can be controlled (e.g App name, logging level) through the configuration file 
(default: `config.json`).

<br>
<br>

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
      e.g. `seedu.jimi.commons.UrlUtilTest`<br>
   2. _Integration tests_ that are checking the integration of multiple code units 
     (those code units are assumed to be working).<br>
      e.g. `seedu.jimi.storage.StorageManagerTest`<br>
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as 
      how the are connected together.<br>
      e.g. `seedu.jimi.logic.LogicManagerTest`<br>
  
**Headless GUI Testing** :
Thanks to the [TestFX](https://github.com/TestFX/TestFX) library we use,
 our GUI tests can be run in the _headless_ mode. 
 In the headless mode, GUI tests do not show up on the screen.
 That means the developer can do other things on the Computer while the tests are running.<br>
 See [UsingGradle.md](UsingGradle.md#running-tests) to learn how to run tests in headless mode.
 
 <br>
 
#### Troubleshooting tests
 **Problem: Tests fail because NullPointerException when AssertionError is expected**
 * **Reason**: Assertions are not enabled for JUnit tests. 
   This can happen if you are not using a recent Eclipse version (i.e. _Neon_ or later)<br>
 * **Solution**: Enable assertions in JUnit tests as described 
   [here](http://stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option). <br>
 * Delete run configurations created when you ran tests earlier.

<br>
<br>

## Dev Ops

### Build Automation

See [UsingGradle.md](UsingGradle.md) to learn how to use Gradle for build automation.

<br>

### Continuous Integration

We use [Travis CI](https://travis-ci.org/) to perform _Continuous Integration_ on our projects.
See [UsingTravis.md](UsingTravis.md) for more details.

<br>

### Making a Release

Here are the steps to create a new release.
 
 1. Generate a JAR file [using Gradle](UsingGradle.md#creating-the-jar-file).
 2. Tag the repo with the version number. e.g. `v0.1`
 2. [Crete a new release using GitHub](https://help.github.com/articles/creating-releases/) 
    and upload the JAR file you created.
    
<br>
   
### Managing Dependencies

A project often depends on third-party libraries. For example, Jimi depends on the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing.

These _dependencies_ can be manually managed by:
a. Including those libraries in the repo (this bloats the repo size)
b. Requiring developers to download those libraries manually (this creates extra work for developers)<br>

On the other hand, you can automate the managing of these _dependencies_ using Gradle. For example, Gradle can download the dependencies automatically, which
is better than the above alternatives.<br>


<br>
<br>

/* @@author A0138915X */
<a id="app-a"></a>
## Appendix A : User Stories 

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`

Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | new user | list all commands | see all the functionalities of the application
`* * *` | user | add a new task |
`* * *` | user | add an event | be reminded of upcoming events to attend
`* * *` | user | add a floating task | keep track of things I want to complete without a deadline
`* * *` | user | edit an existing task | modify the details in case a task changes
`* * *` | user | remove an existing task | delete a task I no longer care to track
`* * *` | user | search for tasks with keywords | view all tasks relevant to the keyword easily
`* * *` | user | view all incomplete tasks | see all tasks that I need to complete
`* * *` | user | view all completed tasks | refer to all tasks that I have completed
`* * *` | user | reminded of upcoming tasks | be reminded of incomplete tasks that are due soon
`* * *` | user | specify a storage location for a file to save the tasks | access it from my own personal location within my system
`* * *` | user | undo my previous action | easily undo an unwanted action
`* *` | user | prioritize my tasks | see which tasks are of higher importance/urgency than others
`* *` | user | set repeating tasks | be reminded of repeated tasks on a timely basis
`* *` | user | view all tasks due within a specific period of time | know tasks that are required to be completed within set period of time
`* *` | user | check if I am free at a certain time | know if I can add additional tasks/events to the time-slot
`* *` | user | do a near-match search | find the tasks I require more conveniently
`* *` | user | filter out tasks or events with certain characteristics | find all tasks that match the attributes I require
`*` | user | let the software automatically predict my required command | do what I need more conveniently and quickly
`*` | advanced user | assign custom command shortcuts | suit my preferences for better accessibility and convenience
`*` | user | view current output of the input command in real time | check whether its the expected result of the command

<br>
<br>

/* @@author A0143471L */
<a id="app-b"></a>
## Appendix B : Use Cases 

(For all use cases below, the **System** is the `TaskBook` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: List all commands

**MSS**

1. User requests a list of all commands.
2. App shows list of all commands with guides on how to use the different commands. <br>
Use case ends.

<br>
<br>

#### Use case: Add task/event

**MSS**

1. User requests to add a task/event.
2. App saves task/event and task/event details to the TaskBook, registers the task/event for future notification/reminders and shows confirmation of successful addition. <br>
Use case ends.

**Extensions**

1a. User enters command in invalid format.

> 1a1. App shows user an error message with correct format needed. <br>
> Use case resumes at step 1.

1b. User enters a event with overlapping time with another event.
> 1b1. App shows user a notification and continues with the addition. <br>
> Use case ends.

<br>
<br>

####Use case: Complete task 

**MSS**

1. App shows a list of days/categories. 
2. User requests to list tasks/events from a selected day/category.
3. App shows a list of tasks/events from that day/category.
4. User requests to complete a specific task in the list.
5. App marks the task as completed and shows confirmation to the user. <br>
Use case ends.

**Extensions**

1a. App shows daily agenda and user requests to complete a specific task in the daily agenda.

> Use case jumps to step 5. <br>

3a. The list is empty.

> Use case ends. <br>

4a. The given index is invalid.

> 4a1. App shows an error message.
> Use case resumes at step 3. <br>

<br>
<br>

####Use case: Delete task/event

**MSS**

1. App shows a list of days/categories. 
2. User requests to list tasks/events from a selected day/category.
3. App shows a list of tasks/events from that day/category.
4. User requests to delete a specific task/event in the list.
5. App deletes the task/event and shows confirmation to the user. <br>
Use case ends.

**Extensions**

1a. App shows daily agenda and user requests to delete a specific task/event in the daily agenda.

> Use case jumps to step 5.

3a. The list is empty.

> Use case ends.

4a. The given index is invalid.

> 4a1. App shows an error message to user. <br>
> Use case resumes at step 3.

<br>
<br>

####Use case: Edit task/event.

**MSS** 

1. App shows a list of days/categories. 
2. User requests to list tasks/events from a selected day/category.
3. App shows a list of tasks/events from that day/category.
4. User requests to edit a specific task/event in the list.
5. App edits the details of the task/event and shows confirmation to the user.  <br>
Use case ends.

**Extensions**

1a. App shows daily agenda and user requests to edit a specific task/event in the daily agenda.

> Use case jumps to step 5.

3a. The list is empty.

> Use case ends.

4a. The given index is invalid.

> 4a1. App shows an error message to user. <br>
> Use case resumes at step 3.

4b. User enters command in invalid format.

> 4b1. App shows an error message to user with correct format needed. <br>
> Use case resumes at step 3.

4c. User enters new details that are the same as the original details. 

> 4c1. App shows an error message to user. <br>
> Use case resumes at step 3.

4d. User enters new time details that overlap with another event's time details.

> 4d1. App shows notification and continues the editing of the details. <br>
> Use case ends.

<br>
<br>

####Use case: Shows today's agenda

**MSS**

1. User requests to list the agenda of the day
2. App shows a list of tasks due on that day and events held on that day.
Use case ends

**Extension**

2a. There is no tasks due on that day or events hald on that day.

>2a1. App shows an empty list. <br>
> Use case ends.

<br>
<br>

####Use case: Shows list of tasks & events in a category

**MSS** 

1. User requests to list out the tasks & events in a particular category
2. App shows a list of tasks and/or events that matches that category

2a. There is no tasks or events that matches that category.

>2a1. App shows an empty list. <br>
> Use case ends.

<br>
<br>

####Use case: Undo action

**MSS** 

1. User requests to undo previous action.
2. App undoes the previous action and shows confirmation to user.  <br>
Use case ends.

**Extensions**

1a. No previous action was done before.

>1a1. App shows an error message to user. <br>
> Use case ends.

1b. Previous action is an invalid action to be undone.

>1b1. App shows an error message to user. <br>
> Use case ends.

<br>
<br>

####Use case: Find task/event

**MSS** 

1. User requests to find a particular task/event using a particular keyword used in the details.
2. App shows a list of tasks/events matching that keyword. <br>
Use case ends.

**Extensions**

2a. No such keyword was used before in any task details.

>2a1. App shows message to user and displays an empty list to user. <br>
> Use case ends.

<br>
<br>

####Use case: Set save directory

**MSS**

1. User requests to set a new save directory for all the tasks and events.
2. App switches the save directory to the new save directory given and shows confirmation message to user. <br>
Use case ends.

**Extensions**

1a. The input new save directory is invalid.

>1a1. App shows error message to user. <br>
> Use case ends.

1b. The input new save directory is the same as the original save directory.

>1b1. App shows error message to user. <br>
> Use case ends.

<br>
<br>

####Use case: Clear TaskBook

**MSS**

1. User requests to clear the TaskBook of all tasks and events.
2. App requests for confirmation with user to clear the TaskBook.
3. User confirms.
4. App clears the TaskBook of all tasks and events and show a confirmation message to user. <br>
Use case ends.

**Extensions**

1a. The TaskBook is already empty.

>1a1. App shows error message to user. <br>
> Use case ends.

3a. User rejects the confirmation.

>3a1. App shows message to user. <br>
> Use case ends.

<br>
<br>

####Use case: Exit application

**MSS**

1. User requests to exit the application.
2. Application closes itself. <br>
Use case ends.

<br>
<br>
/* @@author A0138915X */
<a id="app-c"></a>
## Appendix C : Non Functional Requirements 

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 Tasks.
3. Should come with automated unit tests and open source code.
4. Should favor DOS style commands over Unix-style commands.
5. Should support [natural language processing](#natural-language-processing) with [natural language commands](#natural-language-commands).
6. Should be able to do all functions through the [command-line interface](command-line interface).
7. Should be able to be accessed offline.
8. Should load within 1 second of opening the program.
9. Should be able to display full list of tasks within 1 second.
10. Should be able to save and backup tasks into a file for recovery or portability.
11. Should not cause data corruption when program is closed abruptly.
12. Should be able to hold tasks up to one year onwards.
13. Should recover from major errors within 1 second.

Other requirements can be found in the project constraints section of our team's [module handbook](http://www.comp.nus.edu.sg/~cs2103/AY1617S1/).

<br>
<br>

<a id="app-d"></a>
## Appendix D : Glossary 

##### Application program interface
> A set of routines, protocols, and tools for building software applications.


##### Mainstream OS
> Windows, Linux, OS-X

<br>

##### Natural Language Commands
> Commands formatted in a language that has developed naturally in use and is intuitive for humans to understand. (as contrasted with an artificial language or computer code).

<br>

##### Natural Language Processing
> A branch of artificial intelligence that deals with analyzing, understanding and generating the languages that humans use naturally in order to interface with computers in both written and spoken contexts using natural human languages instead of computer languages.

<br>

##### Command-line interface
> User interface to a computer's operating system or an application in which the user responds to a visual prompt by typing in a command on a specified line, receives a response back from the system, and then enters another command, and so forth.

<br>
<br>
/* @@author A0143471L */
<a id="app-e"></a>
## Appendix E : Product Survey 

| Task Managers | Strengths | Weaknesses |
| :------------ | :-------- | :--------- |
| Todoist |  Has a very simple design. <br>Offers a mobile app. <br>Has a feature where users are encouraged to earn "Todoist Karma", to track their productivity trends as they finish their tasks.| Free version is limited in its capabilities and  is not well-encrypted. <br>Some of the mobile apps have design issues (like being unable to sort tasks). <br>Free version does not come up with some features like reminders, filters, labels, and templates. |
| Trello | Can divide projects up by tasks, and then edit those tasks with descriptions, labels, checklists, and even attachments. <br>Is particularly helpful for teams working on separate tasks toward a greater project goal, where the tasks are in need of a pipeline. | Has no good way to use this system to prioritize tasks between projects. |
| Google Keep | Easy on the eyes. <br>Easy to use. <br>Integrates with desktop/mobile very well. <br>As expected from google, it integrates well with other google products too. <br>Voice memos feature. <br>Images feature. <br>Able to retrieve deleted items in archive. <br>Has reminders. <br>Reminders can be set location-based. <br>Can share lists. | No chronological representation of reminders. |
| Google Calendar | Events are shown clearly on a calendar interface. <br>Integrated with Google reminders to give user reminders. <br>Allows addition of tasks with deadlines unlike most to-do apps. <br>Puts floating tasks and tasks with deadlines together for easy reviewing and reassessing of what to do next. | No other way to prioritise tasks other than setting a deadline for it. <br> |
| Wunderlist | Easy to use for beginners. <br>Has reminder features. <br>Allows creation and sharing of lists and setting up deadlines to get them done. <br>Is useful for collaboration on these lists with other people. <br> Comes with a mobile and a smartwatch app. | Advanced users have to pay if they want access to better features that beginners might not want to use as much. <br> More of a power list making tool than a true GTD app. | 
| Any.do | Can sync lists across all devices. <br>Can share lists with other people. <br>Allows creation of recurring tasks. <br>Comes with a voice-entry feature, which allows you to create a list using voice commands. <br>Comes with a feature "Any.do Moment" which focuses on just the tasks due on the day itself. | Comes with limited themes. <br> Users have to pay to use their more advanced features. |
| Remember the Milk | Allows syncing across devices. <br> Tasks can be set with deadlines and priorities. <br>Works well with serveices like Google Calendar and Evernote. <br>Allows easy sharing of lists and tasks with friends and colleagues. <br> Advanced feature to break jobs into sub-tasks.<br> Advanced feature to use colour tags to separate different kinds of lists. | Users have to pay to use their advanced features.<br> Users have to pay for the ability to set reminders. |
| Clear| Comes with a mobile app. <br>Differs from other task managers, as you can use gestures, such as swiping, to create, rearrange and mark tasks as complete on the mobile version, instead of tapping on the mobile.<br> Allows creation of separate lists.<br> Built-in reminder feature. <br> | Users have to pay to use this app. <br> |
| Habitica | Comes with a mobile app. <br>Comes as an RPG-style game to motivate users to complete tasks that are tracked in the app. | May be too quirky for more serious users or users who do not play RPGs. |
| OmniFocus | Flexible in that it can be as simple or as complex as what the user wants it. <br>Allows viewing and organising tasks in different ways to suit the wants of the user. <br> Allows user to just use a keyboard shortcut to add a task anytime while on the desktop. <br>Desktop version syncs with some email clients to turn emails into tasks. <br>iOS 8 devices allows addition of tasks into OmniFocus from other iOS apps. <br>iOs 8 devices has a quick-entry button into the OmniFocus App. <br>Syncs across all iOS devices and Mac. | Only available for iOS devices and Mac. <br> Users have to pay to use this app. |

