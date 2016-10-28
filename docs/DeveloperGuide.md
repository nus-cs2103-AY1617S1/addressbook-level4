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
      e.g. `seedu.flexitrack.commons.UrlUtilTest`
   2. _Integration tests_ that are checking the integration of multiple code units 
     (those code units are assumed to be working).<br>
      e.g. `seedu.flexitrack.storage.StorageManagerTest`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as 
      how the are connected together.<br>
      e.g. `seedu.flexitrack.logic.LogicManagerTest`
  
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

A project often depends on third-party libraries. For example, Address Book depends on the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repo (this bloats the repo size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>

//@@ author A0127686R
## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | As a user | I want add a task by specifying a task description only, | So that I can record tasks that need to be done ‘some day’. 
`* * *` | As a user | I want to add an event, | So that I can track the events that I have. 
`* * *` | As a user | I want to add a deadline to a task, | So that I can organised my time better. 
`* * *` | As a user | I want to find upcoming tasks, | So that I can decide what needs to be done soon. 
`* * *` | As a user | I want to edit a task, | So that I can change the details of a task. 
`* * *` | As a user | I want to delete a task, | So that I can get rid of tasks that I no longer care to track. 
`* * *` | As a user | I want to undo an operation, | So that I do not need to be afraid to make a mistake. 
`* * *` | As a new user | I want to view more information about a particular command, | So that I can learn how to use various commands. 
`* * *` | As a new user | I want to mark a task as done, | So that I will be reminded that I have completed the task and not to worry about it. 
`* * *` | As an advanced user | I want to use shorter versions of a command, | So that type a command faster. 
`* * *` | As a user | I want to specify a specific location to store my data, | So that I can choose to store the data file in a local folder controlled by a cloud syncing service, which allowing me to access data from other computers. 
`* * *` | As a user | I want to block a multiple time slot for uncertain time of an event, | So that I will not plan something on the uncertain timing.
`* * *` | As a user | I want to find a free time slot in my schedule, | So that can plan my time better. 
`* *` | As a user | I want to add a recurring task, | So that I do not have to keep adding the same task every week/day. 
`* *` | As a user | I want to have an auto complete search tool, | So that it will be easier for me to find a task. 
`* *` | As a user | I want to find a task that is similar to what I’m searching, | So that I can see similar tasks.
`* *` | As a user | I want to find a task with the exact name, | So that I can locate the exact task I'm looking for.
`* *` | As a user | I want to launch the program with key combination (ALT+SPACE), | So that I can minimized time spent on clicking. 
`* *` | As a user | I want to do everything by typing, | So that I can do everything much faster. 
`* *` | As a user | I want to do multiple undo, | So that If I have a mistake i do not have to trace back and correct it manually. 
`* *` | As a user | I want to do multiple redo, | So that if I decided not to take the changes from undo, I can go back to use my original file. 
`* *` | As a user | I want to have a high flexibility of command format, | So that I do not need to memorize or worry about the format of the input. 
`* *` | As a new user | I want to have a guided tour for the app, | So that I will know how to use the app. 
`* *` | As a user | I want to receive feedback while typing, | So that I know that i have successfully key in what I typed. 
`*` | As a user | I want to add a tag to a task, | So that I know at a glance what the task is for. 
`*` | As a user | I want to sort the task by the tag, | So that I can see related tasks together. 
`*` | As a user | I want to add a sub-task under a task, | So that I can break down my task into smaller tasks. 
`*` | As a user | I want to add a note to a task, | So that I will not forget the details about the task, if there is any. 
`*` | As a user | I want to mark a sub-task as done, | So that I will not worry about it anymore. 
`*` | As a user | I want to set reminder for a task, | So that I will not forget to do the task. 
`*` | As a user | I want to set/pick the reminder time, | So that I can assign reminder according to how long the task need to be completed. 


## Appendix B : Use Cases

(For all use cases below, the **System** is the `FlexiTrack` and the **Actor** is the `user`, unless specified otherwise)


####Use case:  UC01 – add new task or event

**MSS**

1.User request to add a new task or an event 
2.System add the data 
Use case ends
 
**Extensions**

1a. The user input is invalid 
	
  > 1a1. System output an invalid input message
  > Use case ends 

1b. The user input existing active task or event title 

  > 1b1. System output warning message that there is existing active task or event title
  > Use case ends 
	
	
####Use case:  UC02 – Edit a task 

**MSS**

1.User request to edit an existing task giving the index number and the new task
2.System edit the task 
Use case ends
 
**Extensions**

1a. The user input is invalid 
	
  > 1a1. System output an invalid input message
  > Use case ends 


####Use case:  UC03 – sort 

**MSS**

1.User request to sort the existing tasks  
2.System sort the tasks 	
Use case ends


####Use case:  UC04 – delete 

**MSS**

1.User request to delete an existing task/event  
2.System delete the task/event 
Use case ends
 
**Extensions**

1a. The user input an invalid list number
	
  > 1a1. System output an invalid input message
  > Use case ends 

####Use case:  UC06 – unmark 

**MSS**

1.User request to unmark an event as not done  
2.System unmark the event   
Use case ends
 
**Extensions**

1a. The user input an invalid list number 
	
  > 1a1. System output an invalid list number 
  > Use case ends 

####Use case:  UC06 – mark 

**MSS**

1.User request to mark an event as done  
2.System mark the event   
Use case ends
 
**Extensions**

1a. The user input an invalid list number 
	
  > 1a1. System output an invalid list number 
  > Use case ends 


####Use case:  UC07 – specify storage  

**MSS**

1.User request to specify storage   
2.System move the storage to the specify directory  
Use case ends
 
**Extensions**

1a. The user input an invalid directory
	
  > 1a1. System output an invalid directory message 
  > Use case ends 


####Use case:  UC08 – block multiple time slot 

**MSS**

1.User request to block an extra time slot for an event   
2.System block the extra time slot  
Use case ends
 
**Extensions**

1a. The user input an invalid list number
	
  > 1a1. System output an invalid list number message
  > Use case ends 

1b. The user select a busy timing (unavailable timing) 

  > 1b1. System output an unavailable timing message
  > Use case ends 


## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should come with automated unit tests and open source code.
3. User must get the command done within three mouse clicks 
4. The total file size must be less than 15MB
5. Search result must be returned in 0.3ms
6. Buttons and text field must be clearly labeled
7. Make the more important/urgent tasks stand out
8. The longer events will look different from shorter events
9. An event will look differently from a task thus easy to differentiate 
10.Easy to see free slots 
11.Intuitive vocabulary and design 

{More to be added}

## Appendix D : Glossary

#####Active task 
> A task that have not passed the deadline and have not been marked as done 

#####Active event
> An event that has not passed yet

#####Event 
> An occasion that goes on for a period of time. It has a starting time and an ending time. 

#####Task
> An occasion that need to be completed by a certain date called deadline 

##### Mainstream OS

> Windows, Linux, Unix, OS-X


## Appendix E : Product Survey

####Google Calendar

#####Pros: 
> Help to organise schedules and pre-planned them in advance
> Able to show schedules in multiple view (3 days, day, month or week) format
> Easy to synchronise planned schedules across multiple devices


#####Cons: 
> Unable to use single line command to add new events
> Each event creation take up around 2 minutes of the user time to fill up the details
> Unable to do partial search for a particular event name
> Confusing timing display on each event. (E.g 11p for night event and 11 for morning event)


####Any.Do

#####Pros:
> Help to add a quick task to the list
> Able to invite friends in the same event or task
> Able to send notification to the user before the event start
> Able to add new task(s) to personalised folder

