# Introduction


[//]: # (@@author A0139515A)

Menion is a simple activity manager for students to track their activities so they can better manage their schedule. It is a command line interface that minimizes mouse usage and focuses on keyboard commands.

This guide will bring you through the design and implementation of Menion. It's purpose is to help you understand how Menion works and how you can further contribute to its development. The content of this guide is structured from a top-down manner to best help you understand how our application works before going into the minute details. Let's begin!

[//]: # (@@author)

# Table of Contents
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

1. JDK `1.8.0_60`  or later<br>

    > * Having any Java 8 version is not enough. 
    > * This app will not work with earlier versions of Java 8.
2. Eclipse IDE
3. e(fx)clipse plugin for Eclipse 
4. Buildship Gradle Integration plugin from the Eclipse Marketplace


#### Importing the project into Eclipse

1. Fork this repo, and clone the fork to your computer

2. Open Eclipse

	> * Ensure you have installed the e(fx)clipse and buildship plugins as given in the 		prerequisites above
3. Click `File` > `Import`
4. Click `Gradle` > `Gradle Project` > `Next` > `Next`
5. Click `Browse`, then locate the project's directory
6. Click `Finish`

  > * If you are asked whether to 'keep' or 'overwrite' config files, choose to 'keep'.
  > * Depending on your connection speed and server load, it can even take up to 30 minutes for the set up to finish
      (This is because Gradle downloads library files from servers during the project set up process)
  > * If Eclipse auto-changed any settings files during the import process, you can discard those changes.

## Design

### Architecture

<img src="images/Architecture.png" width="600"><br> 
> Diagram 1: Layout for architecture<br>

The Architecture Diagram given above explains the high-level design of the App.
Given below is a quick overview of each component.

[*Main*]() has only one class called [`MainApp`](../src/main/java/seedu/menion/MainApp.java).

* On app launch: Initializes the components in the correct sequence, and connect them up with each other.
* On exit: Shuts down the components and saves the activity manager.


[*Commons*](#common-classes) represents a collection of classes used by multiple other components.
Two of those classes play important roles at the architecture level.

* `EventsCentre` : This class (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
  is used by components to communicate with other components using events (i.e. a form of Event Driven design)
* `LogsCenter` : Used by many classes to write log messages to the App's log file.

<img src="images/LogicClassDiagram.png" width="800"><br>
> Diagram 2: Model diagam<br>

The rest of the App consists four components.

* [*`UI`*](#ui-component) : The UI of tha App.
* [*`Logic`*](#logic-component) : The command executor.
* [*`Model`*](#model-component) : Holds the data of the App in-memory.
* [*`Storage`*](#storage-component) : Reads data from, and writes data to, the hard disk.

Each of the four components

* Defines its API in an `interface` with the same name as the Component.
* Exposes its functionality using a `{Component Name}Manager` class.

For example, the `Logic` component (see the class diagram given below) defines it's API in the `Logic.java`
interface and exposes its functionality using the `LogicManager.java` class.


<img src="images\SDforDeletePerson.png" width="800"><br>
> Diagram 3: Sequence diagram

The Sequence Diagram above shows how the components interact for the scenario where the user issues the
command `delete task 1`.

> Notice how the `Model` simply raises a `ActivityManagerChangedEvent` when the Activity Manager data is changed,
 instead of asking the `Storage` to save the updates to the hard disk.


<img src="images\SDforDeletePersonEventHandling.png" width="800"><br>
> Diagram 4: Sequence diagram

The diagram above shows how the `EventsCenter` reacts to that event, which eventually results in the updates
being saved to the hard disk and the status bar of the UI being updated to reflect the 'Last Updated' time. <br>
> * Notice how the event is propagated through the `EventsCenter` to the `Storage` and `UI` without `Model` being coupled to either of them. This is an example of how this Event Driven approach helps us reduce direct coupling between components.

The sections below give more details of each component.

### UI component

<img src="images/UiClassDiagram.png" width="800"><br>
> Diagram 5: UI component<br>

API : [`Ui.java`](../src/main/java/seedu/menion/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts. 
For example, `CommandBox`, `ResultDisplay`, `AcitivtyListPanel`, `StatusBarFooter`, `BrowserPanel` etc... All these, including the `MainWindow`, inherit from the abstract `UiPart` class and they can be loaded using the `UiPartLoader`.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder.<br>
 For example, the layout of the [`MainWindow`](../src/main/java/seedu/menion/ui/MainWindow.java) is specified in [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component

* executes user commands using the `Logic` component.
* binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change.
* responds to events raised from various parts of the App and updates the UI accordingly.


### Storage component

<img src="images/StorageClassDiagram.png" width="800"><br>
> Diagram 6: Storage component<br>

API : [`Storage.java`](../src/main/java/seedu/menion/storage/Storage.java)

The `Storage` component

* saves `UserPref` objects in json format and feeds it back.
* saves the Activity Manager data in xml format and feeds it back.


### Logic component

<img src="images/LogicClassDiagram.png" width="800"><br>
> Diagram 7: Logic component <br>

API : [`Logic.java`](../src/main/java/seedu/menion/logic/Logic.java)

 [`Logic`](../src/main/java/seedu/menion/logic/Logic.java) uses the `ActivityParser` class to parse the user's command.For example, `ActivityParser` uses `AddParser` to parse argument for `AddCommand`. This results in a `Command` object, which is executed by the `LogicManager`.<br>
The command execution can affect the `Model` (e.g. adding an Activity) and/or raise events. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.

<img src="images/DeletePersonSdForLogic.png" width="800"><br>
> Diagram 8: Sequence Diagram<br>

Given above is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete task 1")`API call.<br><br>
### Model component

<img src="images/ModelClassDiagram.png" width="800"><br>
> Diagram 9: Model component<br>

*API* : [`Model.java`](../src/main/java/seedu/menion/model/Model.java)

> The model class is not coupled to the other three components.


The `Model` component

* stores a `UserPref` object that represents the user's preferences.
* stores the Activity Manager data.
* exposes a `UnmodifiableObservableList<ReadOnlyActivity>` that can be 'observed' For example, the UI can be bound to this list so that the UI automatically updates when the data in the list change.
<br><br>

### Common classes

Classes used by multiple components are in the `seedu.menion.commons` package.

## Implementation

### Logging

We are using `java.util.logging` package for logging. The `LogsCenter` class is used to manage the logging levels
and logging destinations.

* The logging level can be controlled using the `logLevel` setting in the configuration file
  (See [Configuration](#configuration))
* The `Logger` for a class can be obtained using `LogsCenter.getLogger(Class)` which will log messages according to the specified logging level
* Currently log messages are output through: `Console` and to a `.log` file

*Logging Levels*

* `SEVERE`: Critical problems may cause the termination of the application
* `WARNING` : Can continue, but with caution
* `INFO` : Information showing the noteworthy actions by the App
* `FINE` : Details that is not usually noteworthy but may be useful in debugging

### Configuration

Certain properties of the application can be controlled (e.g App name, logging level) through the configuration file 
(default: `config.json`)

## Testing

Tests can be found in the `./src/test/java` folder. There are two available options for tesing.

1. Eclipse
	* To run all tests, right-click on the `src/test/java` folder and choose
  `Run as` > `JUnit Test`
	* To run a subset of tests, you can right-click on a test package, test class, or a test and choose
  to run as a JUnit test

2. Gradle
	* See [UsingGradle.md](UsingGradle.md) for how to run tests using Gradle



Testing is split into two components:

1. *GUI Tests* - These are System Tests that test the entire App by simulating user actions on the GUI. 
   These are in the `guitests` package.
  
2. *Non-GUI Tests* - These are tests not involving the GUI. They include,
   * Unit tests targeting the lowest level methods/classes. 
      e.g. `seedu.menion.commons.UrlUtilTest`
      <br>
   * Integration tests that are checking the integration of multiple code units (those code units are assumed to be working).
      e.g. `seedu.menion.storage.StorageManagerTest`
      <br>
   * Hybrids of unit and integration tests. These test are checking multiple code units as well as how the are connected together.
      e.g. `seedu.menion.logic.LogicManagerTest`
      <br>
  
>*Headless GUI Testing* :
Thanks to the [TestFX](https://github.com/TestFX/TestFX) library we use,our GUI tests can be run in the headless mode.In the headless mode, GUI tests do not show up on the screen.That means the developer can do other things on the Computer while the tests are running.<br>
 See [UsingGradle.md](UsingGradle.md#running-tests) to learn how to run tests in headless mode.
 
#### Troubleshooting tests
 *Problem: Tests fail because NullPointException when AssertionError is expected*
 
 * Reason: Assertions are not enabled for JUnit tests. 
   This can happen if you are not using a recent Eclipse version (i.e. Neon or later)
 * Solution: Enable assertions in JUnit tests as described 
   [here](http://stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option). 
   
Delete run configurations created when you ran tests earlier.
   
## Dev Ops

### Build Automation

See [UsingGradle.md](UsingGradle.md) to learn how to use Gradle for build automation.

### Continuous Integration

We use [Travis CI](https://travis-ci.org/) to perform Continuous Integration on our projects. See [UsingTravis.md](UsingTravis.md) for more details.

### Making a Release

Here are the steps to create a new release:
 
 1. Generate a JAR file [using Gradle](UsingGradle.md#creating-the-jar-file).
 2. Tag the repo with the version number. e.g. `v0.1`
 2. [Crete a new release using GitHub](https://help.github.com/articles/creating-releases/) and upload the JAR file your created.
   
### Managing Dependencies

A project often depends on third-party libraries. For example, Activity Manager depends on the [Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these dependencies can be automated using Gradle. For example, Gradle can download the dependencies automatically, which is better than these alternatives.<br>
Remember to include those libraries in the repo (this bloats the repo size). Developers are required to download those libraries manually (this creates extra work for developers).<br>

## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`

[//]: # (@@author A0139277U)

Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | new user | see usage instructions | refer to instructions when I forget how to use the App
`* * *` | user | add a new activity | track my activities
`* * *` | user | delete an activity | remove activities that I no longer need
`* * *` | user | edit a command | modify the details of an activity
`* * *` | user | undo a command | revert back to the state before I made a wrong command
`* * *` | user | find an activity by name | locate details of activities without having to go through the entire list
`* * *` | user | have a one-shot approach to add activities | minimize clicking and save time
`* * *` | user | indicate a completion of an activity | keep track of activities which I have completed
`* * *` | user | set recurring activities| add repeated activities just once
`* * *` | user | track completed/uncompleted activities | better manage my schedule
`* * *` | user | modify storage path | store data in my desired location
`* * *` | user | search for my activities using keywords | locate activities quickly
`* *` | user | upload my schedule online and sync them across devices | view my schedules when I am using different devices
`* *` | user with many activities in the activity manager | sort activities by different datelines | have a clearer view of what needs to be completed first

{More to be added}


[//]: # (@@author A0139515A)

## Appendix B : Use Cases

(For all use cases below, the **System** is the `Menion` and the **Actor** is the `user`, unless specified otherwise)


#### Use Case : Add Activity
**MSS**

1. User enters add command followed by the details of the Activity.
2. Menion creates the Activity based on the details of the Activity and puts it in the database.
3. Menion displays the Activity added.

Use case ends.

**Extensions**

1a. Details of the Activity do not match format.

> 1a1. Menion prompts user to re-enter Activity.<br>
> 1a2. User inputs a new task.<br>
> Repeat 1a1 - 1a2 until user inputs the correct format.<br>
> Use case resumes at step 2.



#### Use Case : Delete Activity

**MSS**

1. User enters command followed by the index of the Activity to be deleted.
2. Menion does a search through the database and deletes the Activity.
3. Menion displays the list of Activity left in the database.

Use case ends.

**Extensions**

1a. The index input by the user is not in the range of indices available.

> 1a1. Menion prompts user to re-input the index of the Activity.<br>
> 1a2. User reinputs the name of the Activity.<br>
> Repeat 1a1 - 1ab until user inputs valid index of the Activity.<br>
> Use case resumes at step 2.

#### Use Case : Undo

**MSS**

1. User enters undo command.
2. System reverts back to the state of the previous command.

Use case ends.

**Extensions**

1a. There is no previous command available to undo.

> 1a1. System prompts user to enter another command.<br>
> 1a2. Use case ends.


[//]: # (@@author A0139164A)

#### Use Case : Edit Activity

**MSS**

1. User enters edit command followed by the name of the Activity to be edited and the information that is to be edited.
2. Menion does a search for the name of the Activity in the database and updates the entry.
3. Menion displays the updated information of the Activity.

Use case ends.

**Extensions**

1a. The name of the Activity entered by the user does not exist.

> 1a1. Menion prompts user to re-input name of the Activity.<br>
> 1a2. User re-inputs name of Activity.<br>
> Repeat 1a1 - 1a2 until the user inputs a valid name of Activity.<br>
> Use case resumes at step 2.

1b. The information entered by the user does not follow the format.

> 1b1. Menion prompts user to re-input details of the Activity in the given format.<br>
> 1b2. User re-inputs details of the Activity.<br>
> Repeat 1b1 - 1b2 until the user inputs a valid format for the Activity.<br>
> Use case resumes at step 2.


#### Use Case : List

**MSS**

1. User enters list command followed by the addition filters of the listing.
2. System displays the list of activities according to the filters input by the user.

Use case ends.

**Extensions**

1a. The filter input by the User is not valid.
> 1a1. System prints out error message and requests for another input.<br>
> 1a2.  Repeat step 1a1 until user inputs a valid filter for the list command.

1b. User requests for Menion to list all the Activity.
> 1b1. Menion shows a list of all the Activities in the Menion.<br>
> 1b2. Resume to step 2 in MSS.

1c. User requests for Menion to list all Activity of a specified date / week / month.
> 1c1. Menion shows a list of all the Activities in the Menion which has a deadline of the specified date.<br>
> 1c2. Resume to step 3 in MSS.


#### Use Case: Find

**MSS**

1. User enters find command followed by the keywords of the search.
2. Menion performs the find command.
3. Menion displays the details of the Activity.

Use case ends.

**Extensions**

3a. There is no Activity with the keyword stated.
> 3a1. Menion displays 'No particular Activity' message.<br>
> Use case ends.


#### Use Case : Modify Storage Path
**MSS**

1. User requests to modify the storage path.
2. Menion prompts user to key in new desired storage path.
3. User types in the new desired storage path.
4. Menion updates the new storage path.

Use case ends.

**Extensions**

2a. The given storage path is invalid.
> 2a1. Menion shows an error message.<br>
> Use case resumes at step 2.


[//]: # (@@author A0146752B)

## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 Tasks.
3. Should come with automated unit tests and open source code.
4. Should favor DOS style commands over Unix-style commands.
5. Should be able to save activities offline, so that the user can use the application when the user does not have internet access.
6. Should be able to use the application without constantly refering to instructions.

## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

## Appendix E : Product Survey


[//]: # (@@author A0139515A)

**1. WunderList**

_1.1 Pros_
> 1.1.1 Able to do type in the details of the activity in a command line. One shot approach.<br>
> 1.1.2 Able to be used offline.<br>
> 1.1.3 When online, able to sync across platforms.<br>
> 1.1.4 Able to sync to calendar which can be exported.<br>
> 1.1.5 Simple user interface.

_1.2 Cons_
> 1.2.1 Unable to block out uncertain schedules.<br>
> 1.2.2 Unable to start application just by a short command.<br>
> 1.2.3 Requires a lot of mouse clicking.<br>
> 1.2.4 Unable to set the time of the dateline.<br>
> 1.2.5 Unable to synchronize schedule without 3rd party calendar app.


[//]: # (@@author A0139277U)

**2. Fantastical**

_2.1 Pros_
> 2.1.1 Calendar view for all activities.<br>
> 2.1.2 Beautiful user interface.<br>
> 2.1.3 Able to be used offline.<br>
> 2.1.4 Able to sync across platforms when online.

_2.2 Cons_
> 2.2.1 No one shot approach of typing details of activity into a command line.<br>
> 2.2.2 Unable to block out uncertain schedules.<br>
> 2.2.3 Requires a lot of mouse clicking.


[//]: # (@@author A0146752B)

**3. Any.do**

_3.1 Pros_
> 3.1.1 Very simple user interface.<br>
> 3.1.2 Able to sync across platforms when online.<br>
> 3.1.3 Simple and clean user interface.<br>
> 3.1.4 Has list view, time view, or combined view.

_3.2 Cons_
> 3.2.1 No one shot approach of typing details of activity into a command line.

