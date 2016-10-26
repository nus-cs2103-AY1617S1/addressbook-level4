# Developer Guide 

* [Setting Up](#setting-up)
* [Design](#design)
* [Implementation](#implementation)
* [Testing](#testing)
* [Dev Ops](#dev-ops)
* [Appendix A: User Stories](#appendix-a-user-stories)
* [Appendix B: Use Cases](#appendix-b-use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c-non-functional-requirements)
* [Appendix D: Glossary](#appendix-d-glossary)
* [Appendix E: Product Survey](#appendix-e-product-survey)


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

`Main` has only one class called [`MainApp`](../src/main/java/seedu/forgetmenot/MainApp.java). It is responsible for,
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

>Note how the `Model` simply raises a `TaskManagerChangedEvent` when the Task Manager data are changed,
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

**API** : [`Ui.java`](../src/main/java/seedu/forgetmenot/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `TaskListPanel`,
`StatusBarFooter`, `BrowserPanel` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class
and they can be loaded using the `UiPartLoader`.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files
 that are in the `src/main/resources/view` folder.<br>
 For example, the layout of the [`MainWindow`](../src/main/java/seedu/forgetmenot/ui/MainWindow.java) is specified in
 [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component,
* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change.
* Responds to events raised from various parts of the App and updates the UI accordingly.

### Logic component

<img src="images/LogicClassDiagram.png" width="800"><br>

**API** : [`Logic.java`](../src/main/java/seedu/forgetmenot/logic/Logic.java)

1. `Logic` uses the `Parser` class to parse the user command.
2. This results in a `Command` object which is executed by the `LogicManager`.
3. The command execution can affect the `Model` (e.g. adding a task) and/or raise events.
4. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")`
 API call.<br>
<img src="images/DeleteTaskSdForLogic.png" width="800"><br>

### Model component

<img src="images/ModelClassDiagram.png" width="800"><br>

**API** : [`Model.java`](../src/main/java/seedu/forgetmenot/model/Model.java)

The `Model`,
* stores a `UserPref` object that represents the user's preferences.
* stores ForgetMeNot's data.
* exposes a `UnmodifiableObservableList<ReadOnlyTask>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.

### Storage component

<img src="images/StorageClassDiagram.png" width="800"><br>

**API** : [`Storage.java`](../src/main/java/seedu/forgetmenot/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save the Task Manager data in xml format and read it back.

### Common classes

Classes used by multiple components are in the `seedu.forgetmenot.commons` package.

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
      e.g. `seedu.forgetmenot.commons.UrlUtilTest`
   2. _Integration tests_ that are checking the integration of multiple code units 
     (those code units are assumed to be working).<br>
      e.g. `seedu.forgetmenot.storage.StorageManagerTest`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as 
      how the are connected together.<br>
      e.g. `seedu.forgetmenot.logic.LogicManagerTest`
  
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

A project often depends on third-party libraries. For example, Task Manager depends on the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repo (this bloats the repo size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>

## Appendix A: User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | user | add floating tasks without date or time | I can keep track of tasks which need to be done whenever I have time.
`* * *` | user | add deadline tasks with a date and end time | I can keep track of deadlines.
`* * *` | user | add event tasks with a date, start time and end time| I can keep track of events.
`* * *` | user | add event tasks with a date and start time only | I can keep track of events.
`* * *` | user | search for tasks | review the details of the task. 
`* * *` | user | delete a task | can get rid of tasks that I no longer care to track. 
`* * *` | user | view more information about various command | learn how to use those commands. 
`* * *` | user | edit the details of a specific task | reschedule the task if the deadline has changed.
`* * *` | new user | view the availability of all the possible commands | understand what features there are in the product.
`* * *` | user | have a few natural variations in my command inputs | key in my task more efficiently.
`* * *` | user | view all my tasks | I have an idea about the pending tasks.
`* * *` | user | mark a task as done | it will be removed from my list of things to do.
`* * *` | user | add floating tasks without date or time | I can do that task whenever I want.
`* * *` | user | specify a specific folder as the data storage location | I can decide where to place my file for the task manager.
`* * *` | user | have a done list | see what has been done for the day to know how productive I've been.
`* * *` | user | clear my tasks | delete all the tasks in my task manager at once.
`* * *` | user | clear my done tasks | delete all my tasks that are done from the done list.
`* *` | user | add a recurring tasks | add the task once and not every time it occurs.
`* *` | user | undo a command | go back to the previous command if I have made a mistake.
`* *` | user | redo an undo | go back to the previous state if I have made an accidental undo.
`* *` | user | rearrange my task based on certain commands | make my schedule more flexible.
`* *` | user | set the priority of the task when I'm adding a new task | know the urgency of the task.
`*` | user | sort my task according to the priority | can work on the important task first.




{More to be added}

## Appendix B: Use Cases

(For all the use cases below, the **System** is the `ForgetMeNot` and the **Actor** is the `user`, unless specified otherwise)

	
#### Use Case: Add task

**MSS** <br>
1. User types in a task to be added. <br>
2. ForgetMeNot adds the task in the list of tasks <br>
      Use case ends.

**Extensions**

	1a. User enters an incorrect command

> 1a1. ForgetMeNot shows an error and help message

#### Use Case: Clear Task

**MSS**

1. User types in clear command <br>
2. ForgetMeNot clears the list of task <br>
	Use case ends <br>
	
**Extensions**

	1a. User types in wrong command

> 1a1. ForgetMeNot shows error and help message


#### Use Case: Clear Done

**MSS**

1. User types in clear done command
2. ForgetMeNot clears the list of done task
	Use case ends
	
**Extensions**

	1a. User types in wrong command
	
> 1a1. ForgetMeNot shows error and help message

	2a. The done list is empty
	
> 2a1. ForgetMeNot shows error message
	
#### Use Case: Delete Task

**MSS**

1. User requests to list tasks
2. ForgetMeNot shows the list of tasks to the user
3. User requests to delete a particular task
4. ForgetMeNot deletes the task
      Use case ends.

**Extensions**

2a. The list is empty

> 2a1. Use case ends

3a. The task is not found

> 3a1. ForgetMeNot displays an error message
> 2a2. Use case resumes at step 2

#### Use Case: View information on various commands

**MSS**

1. User requests help to find information about various commands
2. ForgetMeNot shows the user the list of commands the user can use, along with the format in which they are supposed to be used
3. User requests to view more information about a particular command
4. ForgetMeNot show the user more examples on how the particular command can be used
     Use case ends.

**Extensions**

3a. Invalid command entered

> 3a1. ForgetMeNot displays an error message

> 3a2. Use case resumes at step 2

#### Use case: Edit a task

**MSS**

1. User requests to edit a task.
2. System prompts for confirmation.
3. User confirms.
4. System shows user that the task is edited.
	 Use case ends.

**Extensions**

	1a. Input command incorrect.
	
> 1a1. System shows help message

	1b. The task does not exist.
	
> 1b1. System suggests user to check the input or add a new task

	2a. User changed his mind
	
> 2a1. Command is removed.

#### Use case: List Commands

**MSS**

1. User requests to view a list of commands.
2. System shows list of commands
	 Use case ends.
	 
**Extensions**

	1a. Input command incorrect.
	
> 1a1. System shows help message

#### Use case: Mark task as done

**MSS**

1. User request to mark a specific task as done.
2. System prompts for confirmation.
3. User confirms.
4. System shows user that the task is marked as done.
     Use case ends.
       
**Extensions**

    1a. Input command incorrect.
    
> 1a1. System shows help message.

	1b. Task entered does not exist
	
> 1b1. System prompts user to check input or add a new task
	
	2a. User changed his mind
	
> 2a1. Command is removed.

#### Use case: List task

**MSS**

1. User request to list tasks.
2. System shows user the list of task.
      Use case ends
      
**Extensions**

	1a. Input command incorrect.
	
> 1a1. System shows help message.
	
    1b. No task inside the list.
    
> 1b1. System shows error message
> 1b2. Prompt user to add tasks


#### Use Case: Undo a task

**MSS**

1. User undoes a task
2. ForgetMeNot undo the most recent command executed
      Use case ends
      
**Extension**

	1a. No command to be undone
	
> 1a1. ForgetMeNot shows error message

	1b. User inputs an invalid input
	
> 1b1.ForgetMeNot shows help message

#### Use Case: Redo a task

**MSS**

1. User redoes a task
2. ForgetMeNot redoes the most recent undo command executed
	Use case ends

**Extension**
	
	1a. No command to be redone
	
> 1a1. ForgetMeNot shows error message
	
	1b. User inputs an invalid input
	
> 1b1. ForgetMeNot shows help message

#### User Case: Set priority while creating a task

**MSS**

1. User sets priority when creating a task
2. ForgetMeNot adds the task to the list with high priority
3. ForgetMeNot automatically shows the task at the top of the list
      Use case ends
      
**Extension**

    1a. Priority entered by user is of invalid format
    
> 1a1. ForgetMeNot shows error message and displays the correct format to input priority when creating a task 



{More to be added}

## Appendix C: Non Functional Requirements

1. Should be able to hold up to 100 tasks.
2. Should be able to display request under 0.5 seconds.
3. Should work on any mainstream OS as long as it has Java 8 or higher installed.
4. Should be able to add task up to 1 year ahead.
5. Should be able to operate without internet connection.
6. Should come with automated unit tests.
7. Should be able to use the product efficiently after using it for 30 minutes.


{More to be added}

## Appendix D: Glossary

Mainstream OS: 
> Windows, Linux, Unix, OS-X


Day:
> From 0000 to 2359 of the current day

## Appendix E: Product Survey

1. **Fantastical**

1a. It has a good and clean UI, really simple to use. <br>
1b. It has integration with all iOS products, i.e. mac, iphone, ipad etc. <br>
1c. Includes all CRUD features. <br>
1d. It has a reminder function. <br>
1e. Has a list of all upcoming tasks for the week at the left hand side. <br>
1f. Has natural language processing, can add events using Siri. <br>
1g. Locations added when creating events are automatically shown in google/apple map when clicked. <br>
1h. Automatically syncs with apple calendar, updates and syncs on the go. <br>

## Google Cal
##### Strengths
1. It has CRUD features. <br>
2. It can link to external applications such as Gmail and Contacts. <br>
3. It has cross-platform features.<br>
4. It has a reminder function.<br>
5. It can support multiple accounts in one device.<br>
6. It can create Event, Reminder or Goal.<br>
7. All task created are automatically grouped and colour coded.<br>
8. Clean and simple UI.<br>
9. It can be used online or offline.<br>
10. It has different kind of viewing options such as Day, 3-day, Week and Month.<br>

##### Weaknesses
1. It is not keyboard friendly.<br>
2. It requires user to have a google account to use it.<br>
3. It does not have a done function.<br>
4. It has a steep learning curve.<br>

### Any.do
##### Strengths

1. It has support for events, deadlines, floating tasks.<br>
2. It has CRUD.<br>
3. It has a power search function for all tasks.<br>
4. It has a way to keep track of which items are done and yet to be done in reminders.<br>
5. It has categories, predefined ones and customizable ones.<br>
6. It allows priority settings for each tasks.<br>
7. It has an alarm feature for tasks.<br>
8. It has a very useful tasks descriptive features such as subtask, note, images, audio attachments, video attachments
9. It has the option to make tasks recurring.<br>
10. It has location reminder.<br>
11. It has the option for Any.do to walk the user through his/her tasks to make tasks organization better<br>
12. It can be synced across all devices such as computers, phones, tablets.<br>
13. Easy to shift tasks between categories.<br>

##### Weaknesses

1. It has premium features that require payment.<br>
2. No auto clear of done tasks or the option to auto clear done tasks after a certain period.<br>
3. No levels of priority. Only priority or no priorty.<br>
4. Floating tasks are not always displayed.<br>
5. Only one color scheme in the basic version. <br>
6. Requires an account to start using.<br>
7. Not keyboard friendly. Requires substantial mouse usage.<br>

### Reminders
##### Strengths

1. It has CRUD features. <br>
2. It has a search function. <br>
3. It has different tabs for today, tomorrow and date-wise events. <br>
4. It allows to set priorities for different tasks. <br>
5. It allows to repeat reminders on a periodic basis. <br>
6. It can show all previous events which are marked ‘done’. <br>
7. It has great syncing facilities. <br>

##### Weaknesses

1. It is not cross-platform.<br>
2. No auto clear of done tasks or the option to auto clear done tasks after a certain period.<br>
