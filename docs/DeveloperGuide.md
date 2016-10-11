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
* [**`UI`**](#ui-component) : The UI of the App.
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


Priority | As a ... | I want to ... | So that ...
-------- | :-------- | :--------- | :-----------
`* * *` | User | Add tasks with deadlines | I won’t forget deadlines
`* * *` | User | Pin tasks | I won’t forget them
`* * *` | User | Undo | I can undo my most recent mistakes
`* * *` | User  | Ignore tasks | I can remove tasks from my to do list even when they are not done, but still save them in the ignore folder
`* * *` | User | Delete tasks | I can remove the task from the memory
`* * *` | User | Archive tasks as done | I can archive the task
`* * *` | User | Edit tasks | I can correct the tasks that were entered wrongly
`* * *` | User  | Search for tasks | I can find tasks quickly
`* * *` | User | Choose where to store the file | I can sync it with dropbox
`* *` | User | Add shortcuts | I can do manage tasks productively
`* *` | User | Sort the lists | I can find tasks quickly
`* *` | User | See my upcoming tasks, expired tasks, high priority tasks easily  | I can manage my time effectively
`* *` | User | Add tags | I can organise my tasks 
`* *` | User | Postpone my tasks | I can do them later
`* *` | User | See my free slots | I can do my tasks during the free time
`* *` | User | Add repeating tasks | I don’t have to add them every time
`* *` | User | Have auto-complete text | I don’t have to type in completely every time
`*` | User | Add tasks with auto-generated tags | I can save time on creating obvious tags for tasks
`*` | User | View my schedule in a monthly calendar format | Viewing tasks is more efficient
`*` | User | Sync my tasks with Google Calendar | I can view my tasks on both platforms
`*` | User | Be provided with intuitive suggestions for available time | I can add tasks efficiently
`*` | User | Have public holidays indicated in the calendar | I can be aware of them when planning my schedule
`*` | User | Natural language processing (NLP)  | I can write my commands in a more intuitive manner


## Appendix B : Use Cases

(For all use cases below, the **System** is the `AddressBook` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: Delete person

#### Use case: Add a task

**MSS**

1.	User requests to add a task. 
2.	User enters the task description (name, tags, venue(optional), date, whether to pin task (optional), priority level (Optional - default: medium)) 
3.	taskBook saves the task details
4.	taskBook displays task on task board (that is sorted according to deadline) with some important details (name, tags)
Case Close

**Extensions**

2a. User wants to pin the task. 
·	taskBook displays task on pinned-task-board ( at the top of task list, with a logo at the side) 
2a. User chooses to prioritise task
·	Task is colour coded ( high-red, low-blue, default-black)
·	High priority task will be sorted to the top of the list according to deadlines, low priority task will be sorted to the bottom of the list, according to deadline

#### Use case: Searching for a task
Precondition: taskBook is already showing a list of tasks on the screen ( either by default main page or when the user is looking through a folder ( done, ignore, active)

**MSS**

1.	User performs a search by the task details or the tag name(#...)
2.	taskBook lists the tasks that contains the String/Tag entered by user
3.	User picks the task that he/she is looking for ( by typing in the index of the task) 
Case Close

**Extensions**

1a. User types in a string that cannot be found in the list of tasks
·	taskBook returns ‘string not found’
1a. There is no tasks stored at all
·	taskBook returns ‘there are no tasks in taskBook’
3. User does not find the task he/she is looking for
·	User types in a different search command or returns to main page.

#### Use case: Sorting the list of tasks to find a task
Precondition: taskBook is already showing a list of tasks on the screen ( either by default main page or when the user is looking through a folder ( done, ignore, active) 

**MSS**

1.	User request to sort the list ( according to when it was entered into taskBook/ date it will expire/ time the event is etc…) 
2.	taskBook sorts the list and displays the sorted list
3.	User selects the task by typing the index of the task it wants to view.

**Extensions**

1a. There are no items displayed on the screen (ie search and could not find results) 
·	taskBook returns ‘there are no items to sort’
3. User selects the wrong task
·	User types in a command to return taskBook to the previous page and re-picks the correct task

#### Use case: Editing a task
Precondition: User has selected the task and is viewing the details of the task that he/she wants to edit either after:
1.	Sorting the list of tasks to find a task
2.	Searching for a task
3.	Selecting a task that is already on the main page
Case close

**MSS**

1.	User requests to edit the task
2.	User states the field he/she wants to edit and the edits he/she wants to make
3.	taskBook edits the details of the task and displays the edited task on the screen.

**Extensions**

2. User did not enter a valid field that she wants to edit
·	taskBook returns a ‘field not valid’ error, and says that edits were not done

#### Use case: Creating a shortcut

**MSS**

1.	User requests to create a shortcut for the current commands that we have
2.	taskBook saves the shortcut and displays the saved command

**Extension**

1a. Command that user typed in does not exist
·	taskBook returns a ‘command does not exist’ error and says that it is unable to save the shortcut.
1a. User did not want to 


## Appendix C : Non Functional Requirements

1.	Should be intuitive for new users
2.	Should come with automated unit tests
3.	Primary source of input should be keyboard
4.	Should be able to work without an active internet connection
5.	Should be able to work on Windows and macOS desktop platforms


{More to be added}

## Appendix D : Glossary

NLP: Natural Language Processing

##### Mainstream OS

> Windows, Linux, Unix, OS-X

##### Private contact detail

> A contact detail that is not meant to be shared with others

## Appendix E : Product Survey

Describe a summary of the product survey.
The product survey revealed that the more successful task scheduler and calendar software in the market have a few common features as follows:
1.	The interface is minimalist and simple with no unnecessary clutter.
2.	The interface is intuitive for users to understand and use, makes use of the way people think when scheduling tasks.
3.	While simple, the interface allows for more complex but useful functionalities like recurring events on a daily, weekly or monthly basis, adding of labels and others.
4.	The software often allows syncing with other popular platforms.
The abovementioned features appear to be popular and important to users of task scheduler and calendar software, and will be implemented and adhered to during the development of our product.

Details:

Google Keep
Pros	
Available on all devices (Desktop, Web, Mobile) and also on all platforms (Android, iOS, macOS, Windows 10)	
Minimalist interface	Need a Google account
Able to collaborate, share with other people	
Able to archive, add labels to each note	
Cons
No command line interface

Apple Calendar
Pros	
Integrated with the Apple ecosystem	
Syncing available with third-party platforms like Google Calendar	
Minimalist interface	
Able to view tasks in multiple formats (list, monthly, weekly, daily, hourly)	
Able to collaborate, share with other people	
Able to archive, add labels to each note	
Intuitive and easy to use	
Cons 
Only available on the Apple ecosystem
No command line interface

Evernote todo list
Pros	
Reminders can be added with specific days before
Can add your own categories so that you can categories tasks according to your own preference	
Can easily add a list of tasks quickly by just entering after each task has been created	
Can create shortcuts	
Cons 
Pricey


