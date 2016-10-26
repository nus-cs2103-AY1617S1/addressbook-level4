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
* [**`UI`**](#ui-component) : The UI of the App.
* [**`Logic`**](#logic-component) : The command executor.
* [**`Model`**](#model-component) : Holds the data of the App in-memory.
* [**`Storage`**](#storage-component) : Reads data from, and writes data to, the hard disk.

Each of the four components
* Defines its _API_ in an `interface` with the same name as the Component.
* Exposes its functionality using a `{Component Name}Manager` class.

For example, the `Logic` component (see the class diagram given below) defines it's API in the `Logic.java`
interface and exposes its functionality using the `LogicManager.java` class.<br>
<img src="images/LogicClassDiagram_v0.2.png" width="800"><br>

The _Sequence Diagram_ below shows how the components interact for the scenario where the user issues the
command `delete 1`.

<img src="images\SDForDeleteTask.png" width="800">

>Note how the `Model` simply raises a `AddressBookChangedEvent` when the Address Book data are changed,
 instead of asking the `Storage` to save the updates to the hard disk.

The diagram below shows how the `EventsCenter` reacts to that event, which eventually results in the updates
being saved to the hard disk and the status bar of the UI being updated to reflect the 'Last Updated' time. <br>
<img src="images\SDForDeleteTaskEventHandling.png" width="800">

> Note how the event is propagated through the `EventsCenter` to the `Storage` and `UI` without `Model` having
  to be coupled to either of them. This is an example of how this Event Driven approach helps us reduce direct 
  coupling between components.

The sections below give more details of each component.

### UI component

<img src="images/UIClassDiagram_v0.2.png" width="800"><br>

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

<img src="images/LogicClassDiagram_v0.2.png" width="800"><br>

**API** : [`Logic.java`](../src/main/java/seedu/address/logic/Logic.java)

1. `Logic` uses the `Parser` class to parse the user command.
2. This results in a `Command` object which is executed by the `LogicManager`.
3. The command execution can affect the `Model` (e.g. adding a person) and/or raise events.
4. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")`
 API call.<br>
<img src="images/DeleteTaskSdForLogic.png" width="800"><br>

### Model component

<img src="images/ModelClassDiagram_v0.2.png" width="800"><br>

**API** : [`Model.java`](../src/main/java/seedu/address/model/Model.java)

The `Model`,
* stores a `UserPref` object that represents the user's preferences.
* stores the Address Book data.
* exposes a `UnmodifiableObservableList<ReadOnlyPerson>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.

### Storage component

<img src="images/StorageClassDiagram_v0.2.png" width="800"><br>

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

//@@author A0147890U

## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`

Priority | As a ... | I want to ... | So that I can...
 -------- | :-------- | :--------- | :-----------
***  |  user  |  add events with a start time and an end time  |
***  |  user  |  add tasks without a specified time  | 
***  |  user  |  add tasks without a deadline | 
***  |  user  |  edit existing events  |  update deadlines or venues  
***  |  user  |  delete existing events  |  remove entries that is no longer needed
***  |  user  |  complete events  |  acknowledge the completion of event
***  |  user  |  undo the most recent operations  | undo wrong commands 
***  |  user  |  search by partial keyword  |  find related events containing the keyword
***  |  user  | be able to specify my storage folder | use cloud syncing services on it
**  |  user  |  add tasks that is recurring  |  
**  |  user  |  some variations in command keywords
**  |  user  |  set priorities  |  indicate tasks that are important
*   |  user  | start the program with a shortcut/ key combination | save mouse clicks
*   | user   | display completed tasks | know what I have done
*  |  user  |  hide completed  |  conceal events that are completed


## Appendix B : Use Cases

#### Use case: Add events with a start time and an end time 

**MSS**

1.User requests to add events with start time and end time
2.Task manager adds events into manager <br>
Use case ends

**Extensions**

2a. There is already an event in the time slot

>2a1. Task manager shows an error message
 Use case ends
 

#### Use case: Edit existing events

**MSS**

1.User requests to edit existing events
2.Task manager edits the events<br>
Use case ends

**Extensions**
2a. There is no such event requested by the user

>2a1. Task manager shows an error message
 Use case ends


#### Use case: Delete existing events

**MSS**

1.User request to show list of events/task
2.Task manager shows list of events/task
3.User request to delete a specific event / task  
4.Task manager deletes the event / task <br>
Use case ends


**Extensions**

2a. The list is empty

>Use case ends

3a. The given event/task is non existent

>3a1. Task manager shows an error message <br>
 Use case ends


#### Use case: Complete events

**MSS**

1.User request to update a specific event / task to completed  
2.Task manager updates the event / task as completed<br>
Use case ends

**Extensions**
2a. The given event/task is non existent 

>2a1.Task manager shows an error message <br>
 Use case ends


#### Use case: undo the most recent operations

**MSS**

1.User request to undo previous operations
2.Task manager undoes the operations<br>
Use case ends

**Extensions**
2a. There are no previous operations

> 2a1.Task manager shows an warning message <br>
  Use case ends


#### Use case: search by partial keyword

**MSS**

1.User requests to search for an event by keyword
2.Task Manager shows a list of events/tasks containing the keyword
Use case ends

**Extensions**

1a. The matching keyword is not found

>1a1.Task manager shows an error message
 Use case ends

## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to max 100 tasks per day.
3. Should come with automated unit tests and open source code.
4. Should favor DOS style commands over Unix-style commands.

You can also visit the following page for more information on Project Constraints 
http://www.comp.nus.edu.sg/~cs2103/AY1617S1/


## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

##### Private task detail

> A task detail that is not meant to be shared with others


## Appendix E : Product Survey

##Google Calendar

###Pros

1. Quick add function
2. Similar to what we are trying to achieve, you can type the entire add command in one line with event description, time, location and it will interpret it for you and add as an event. It ties into Google Maps for event locations. So if it is recognised in Google Maps, you can choose an exact meeting place.
3. Support for recurring events
4. Google Calendar is remarkably social
5. Calendar can be shared.
6. Supports shared events.
7. Ability to find a common time for everyone to attend a shared event created by you based on their Google Calendars.
8. Can add a video call such that all attendees to a shared event can join the same video call
9. Ability to set a specific time zone for each event. For the frequent travellers.
10. You can add documents, spreadsheets, and other files directly to an event so that your guests have all the information they need right in the event.
11. Support for multiple calendars for different aspects of life. Eg. one for work, one for family, one for play
12. Google Calendar automatically synchronises with google account which is heavily integrated into android. 
13. Colour coding for events so that you can self-categorize according to colour.
14. Calendar view time frame can be switched. View by week, month.

###Cons

1. Lots of mouse clicks; lots of different columns to fill.
2. Security. Online so it can be hacked, revealing your life schedule.

##Todoist

###Pros

1. Colour code for priority, not time
2. Command allows for recurring days eg every day/every Tuesday/ holidays etc.
3. User able to choose subcatergories like task today, task tomorrow but (involves 1 click)
4. Task can divide to further catagories called projects like shopping , work personal, errands, or create your own(involves 1 click) >  for bigger projects subcategories can be created. Projects are shareable with others
6. Filters > priority setting priority1, priority 2, assign to me or assign to others
7. Adding priority (click 2 times) one for flag menu, two for flag colour 4 priority choices or add
8. Able to exploit templates for other projects
9. Lots of filters to choose from 
10. Add labels/ tags
11. Premium acc – add files to task. Supports third party integration eg link to dropbox/ integrates with other services like Zapier, Google Drive, Cloud Magic, Sunrise Calendar, and others
12. Uses drag and drop to shift tasks
13. Karma system> trend>> shows productivity over time > graph> bar(shows type for task done) / sets goals for the week / enable/ disable for vacations etc… a karma level eg grandmaster 5000+ to make user feel good
14. History : no limit
15. Bold italic control i control b

###Cons

1. not jim friendly, involves clicking form filling style eg. Click add> type task>click schedule>click to choose date or for recurring dates > type every day or every week. Edit>click and change
2. Able to link schedules with other people. Eg assign task to others(jim do not need it?)
3. Most of Jim’s todo items arrive as emails. 
4. Divides into too many catagories too messy for jim.


##Wunderlist

###Pros

1. It is available across windows, mac, android and ios.
2. Easy search function
3. Free for non-premium users
4. Nice interface
5. Intuitive options and menu selections that is not complicated
6. Customizable backgrounds to your own liking
7. Able to accept recurring events
8. Can set reminders and notifications
9. Can share between people
10. Multiple task lists
11. Short cuts to starred task, overdues and today task
12. Cloud syncing


###Cons

1. There is no options for subtasks.
2. No start dates for events
3. Need to pay for premium
4. Excel files cannot be uploaded
5. Have to do organization of tasks, events or other stuff by ourselves, they do not group them up for us.
6. Does not sync with google

##Trello

###Pros

1. Works both on web and mobile devices
2. Free subscription, but also offers a professional package, with the privacy and administrative settings required by large enterprises.
3. Good visual
4. Track task easily
5. Able to collaborate with multiple people on a single task
6. Receive email update when other teammates make changes
7. Always in sync 

###Cons

1. Does not provide a lot of tools to sort cards or reports
2. Inability to put due dates
3. Inability to put tag on members in checklist
4. Does not have calendar


