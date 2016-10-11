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
  
#### Troubleshooting project setup

**Problem: Eclipse reports compile errors after new commits are pulled from Git**
* Reason: Eclipse fails to recognize new files that appeared due to the Git pull. 
* Solution: Refresh the project in Eclipse:<br> 
  Right click on the project (in Eclipse package explorer), choose `Gradle` -> `Refresh Gradle Project`.
  
**Problem: Eclipse reports some required libraries missing**
* Reason: Required libraries may not have been downloaded during the project import. 
* Solution: [Run tests using Gardle](UsingGradle.md) once (to refresh the libraries).
 

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

>Note how the `Model` simply raises a `AddressBookChangedEvent` when the Address Book data are changed,
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
* stores the Address Book data.
* exposes a `UnmodifiableObservableList<ReadOnlyPerson>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.

### Storage component

<img src="images/StorageClassDiagram.png" width="800"><br>

**API** : [`Storage.java`](../src/main/java/seedu/address/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save the Address Book data in xml format and read it back.

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

A project often depends on third-party libraries. For example, Address Book depends on the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repo (this bloats the repo size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>

## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | user | search for tasks | review the details of the task. 
`* * *` | user | delete a task | can get rid of tasks that I no longer care to track. 
`* * *` | user | view more information about various command | learn how to use those commands. 
`* * *` | user | edit the details of a specific task | reschedule the task if the deadline has changed.
`* * *` | new user | view the availability of all the possible commands | understand what features there are in the product.
`* * *` | user | view all my tasks | I have an idea about the pending tasks.
`* * *` | user | mark a task as done | it will be removed from my list of things to do.
`* * *` | user | add floating tasks without date or time | I can do that task whenever I want.
`* *` | user | undo a command | go back to the previous command if I have made a mistake.
`* *` | user | rearrange my task based on certain commands | make my schedule more flexible.
`* *` | user | set the priority of the task when I�m adding a new task | know the urgency of the task.
`*` | user | have a �done� list | see what has been done for the day to know how productive I�ve been.
`*` | user | sort my task according to the priority | can work on the important task first.




{More to be added}

## Appendix B : Use Cases

(For all use cases below, the **System** is the `AddressBook` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: Delete person

**MSS**

1. User requests to list persons
2. AddressBook shows a list of persons
3. User requests to delete a specific person in the list
4. AddressBook deletes the person <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. AddressBook shows an error message <br>
  Use case resumes at step 2
  
Appendix B: Use Cases
(For all the use cases below, the **System** is the `ForgetMeNot` and the **Actor** is the `user`, unless specified otherwise)

	
#### Use Case: Add task

**MSS**
1. User types in a task to be added.
2. ForgetMeNot adds the task in the list of tasks
      Use case ends.

**Extensions**

1a. User enters an incorrect command

> 1a1. ForgetMeNot shows an error and help message



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
> Use case resumes at step 2

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

> 3a1. Use case resumes at step 2

#### Use case: Sort tasks

**MSS**

1. User requests to sort
2. ForgetMeNot displays a list of sort options
3. User requests to sort in a specific way
4. ForgetMeNot displays the list of tasks after sorting
      Use case ends
      
**Extensions**
	1a. List of tasks is empty
	
> ForgetMeNot shows list is empty message

	3a. Requested sorting manner does not exists
	
> ForgetMeNot shows error message

#### Use case: Edit a task

**MSS**

1. User requests to edit a task.
2. System prompts for confirmation.
3. User confirms.
4. System shows user that the task is edited.
	 Use case ends.

**Extensions**

	1a. Input command incorrect.
	
> System shows help message

	1b. The task does not exist.
	
> System suggests user to check the input or add a new task

	2a. User changed his mind
	
> Command is removed.

#### Use case: List Commands

**MSS**

1. User requests to view a list of commands.
2. System shows list of commands
	 Use case ends.
	 
**Extensions**

	1a. Input command incorrect.
	
> System shows help message

#### Use case: Mark task as done

**MSS**
1. User request to mark a specific task as done.
2. System prompts for confirmation.
3. User confirms.
4. System shows user that the task is marked as done.
       Use case ends
**Extensions**

    1a. Input command incorrect.
    
> System shows help message.

	1b. Task entered does not exist
	
> System prompts user to check input or add a new task
	
	2a. User changed his mind
	
> Command is removed.

#### Use case: List task

**MSS**

1. User request to list tasks.
2. System shows user the list of task.
      Use case ends
**Extensions**

	1a. Input command incorrect.
	
> System shows help message.
	
    1b. No task inside the list.
    
> System shows error message
> Prompt user to add tasks

#### Use Case: Rearrange task

**MSS**

1. User request to rearrange tasks
2. ForgetMeNot rearrages task
3. ForgetMeNot displays the tasks after rearranging
      Use case ends
**Extensions** 
    
    1b. Task to be rearranged does not exist 
    
> ForgetMeNot tells User to check the name of the task to be arranged

    1c. No priority was specified during creation of all tasks in schedule.
          
> ForgetMeNot tells User no priority was set for all tasks

#### Use Case: Priority task

**MSS**

1. User adds a priority to the task when creating it
2. ForgetMeNot creates the task with the specific priority
     Use case ends
     
**Extensions**

    1a. User enters an invalid input
           
> ForgetMeNot shows help message

    1b. User enters a priority that is not �1� �2� or �3�
           
> ForgetMeNot tells User that only priorities �1� �2� or �3� is useable

#### Use Case: Undo a task

**MSS**

1. User undo a task
2. ForgetMeNot undo the most recent command executed

**Extension**

	1a. No command to be undone
	
> ForgetMeNot shows error message

	1b. User input an invalid input
	
> ForgetMeNot shows help message



	




{More to be added}

## Appendix C : Non Functional Requirements

1. Should be able to hold up to 100 tasks.
2. Should be able to display request under 0.5 seconds.
3. Should work on any mainstream OS as long as it has Java 8 or higher installed.
4. Should be able to add task up to 1 year ahead.
5. Should be able to operate without internet connection.
6. Should come with automated unit tests.
7. Should be able to use the product efficiently after using it for 30 minutes.


{More to be added}

## Appendix D : Glossary

Mainstream OS: 
> Windows, Linux, Unix, OS-X


Day:
> From 0000 to 2359 of the current day

## Appendix E : Product Survey

1. **Fantastical**

1a. It has a good and clean UI, really simple to use
1b. It has integration with all iOS products, i.e. mac, iphone, ipad etc.
1c. Includes all CRUD features
1d. It has a reminder function
1e. Has a list of all upcoming tasks for the week at the left hand side
1f. Has natural language processing, can add events using Siri
1g. Locations added when creating events are automatically shown in google/apple map when clicked
1h. Automatically syncs with apple calendar, updates and syncs on the go.

2. **Google Cal**

2a. It has CRUD features.
2b. It can link to external applications such as Gmail and Contacts.
2c. It has cross-platform features.
2d. It has a reminder function.
2e. It can support multiple accounts in one device.
2f. It can create Event, Reminder or Goal.
2g. All task created are automatically grouped and colour coded.
2h Clean and simple UI.
3i. It can be used online or offline.
3j. It has different kind of viewing options such as Day, 3-day, Week and Month.

3. **Any.do**

3a. It has support for events, deadlines(Today category/Notification reminder), floating task(Someday category)
3b. It has CRUD
3c. It has a search function
3d. It has a way to keep track of which items are done and yet to be done reminders
3e. It has CRUD features.
3f. It has a search function.
3g. It has different tabs for today, tomorrow and date-wise events.
3h. It allows to set priorities for different tasks.
3i. It allows to repeat reminders on a periodic basis.
3j. It can show all previous events which are marked �done�.



