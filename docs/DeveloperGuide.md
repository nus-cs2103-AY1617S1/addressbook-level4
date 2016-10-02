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

## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | New user | See usage instructions | Refer to instructions when I forget how to use the App
`* * *` | New user | Create a new data file to store my tasks | Store my schedule for future reference
`* * *` | User | List all the tasks that are incomplete, in order of deadline | See the next task that needs to be completed
`* * *` | User | Create a new task with a start time and end time | Create an event for my calendar
`* * *` | User | Create a new task with the deadline (but no time slot) | Create a task that needs to be completed on my calendar
`* * *` | User | Create a new tag | Label tasks with my new tags
`* * *` | User | Add location to a task | Make my event happen on a location
`* * *` | User | Update a task with completely different information from the original one | Correct the information of my task
`* * *` | User | Update a task with new information pieces | Correct the information of my task
`* * *` | User | Delete a task | Remove outdated tasks or events.
`* * *` | User | Mark a task as completed | Manage and view my tasks more effectively
`* * *` | User | Show all tasks/events | See my completed task/event and also know what is upcoming
`* * *` | User | Search tasks/events by providing keywords | Locate a task/event easily
`* * *` | User | Load previously written data | Refer to previously created schedule
`* * *` | User | Save changes to my schedule | Be able update schedule and refer to updated schedule
`* * *` | User | Clear the data storage file | Remove unwanted schedule to free up disk space, be able to redo schedule from scratch
`* * *` | User | Choose which folder to save schedule file | Have control over file locations, easier to find file in computer, can store in folder controlled by cloud syncing service
`* * *` | User | Store my schedule offline | Be able to access schedule even without internet connection
`* *` | User | Create a new task with recurring deadlines | Create multiple instances of tasks that is repeatable (e.g. Finish quiz every Sunday)
`* *` | User | Delete a specific information piece in my task/event | Remove unnecessary information from my tasks
`* *` | User | Show only tasks/events with specified tag(s) and date | See only the related tasks/events from the list of tasks/events
`* *` | User | Hide tasks/events with specified tag(s) | Hide tasks/events which I do not care about
`*` | User | Convert an email to a task | Create a task based on email that I have received
`*` | User | Recover my deleted task(s) | Recover the tasks and events that are mistakenly deleted
`*` | User | See all free time within a time range | Easily schedule other tasks/events into a free time
`*` | User | See keyword and tag suggestions as I type | Save time by typing the correct command which yields the results I want


## Appendix B : Use Cases

(For all use cases below, the **System** is the `TaSc` and the **Actor** is the `user`, unless specified otherwise)

### Use case: Create new event

**MSS:**

1. User creates new task with start and end time.
2. Program saves the task with given start and end time.
Use case ends

**Extensions:**

1a. The start time / end time given is invalid
> 1a1. Program returns error message, saying that time given is invalid<br>
> 1a2. Program prompts for re-type of task details.<br>
> 1a3. User retypes the start time / end time<br>
  Use case resumes at step 2

1b. The start time is later than the end time
> 1b1. Program returns error message, saying that the start time starts later than the end time<br>
> 1b2. Program gives two options: Swap time around, or re-type task details.<br>
>     * 1b2i. User request swap time around.<br>
>           * Program swap time around<br>
>           * Use case ends
>     * 1b2ii. User intends to retype timing<br>
>           * Use case resumes at 1a3.

#### Use case: Create new tag

**MSS:**

1. User requests to create a new tag.
2. Program saves the tag to storage.
Use case ends

**Extensions:**

1a. Tag given is empty
> 1a1. Program returns error message, saying that tag is invalid<br>
  Use case resumes at step 1

1b. Tag given already exist
> 1b1. Program returns error message, saying that tag already exist<br>
  Use case resumes at step 1

#### Use case: List uncompleted tasks

**MSS:**

1. User requests to list uncompleted tasks.
2. Program lists uncompleted tasks, by order of tasks with earliest deadline.
Use case ends

**Extensions:**

> 1a. The list is empty<br>
  Use case ends

#### Use case: Completely update a task

**MSS:**

1. User requests to change all the information of a task to a new information set.
2. Program replaces all the old information with the new information.
Use case ends

**Extensions:**

1a. The requested task name does not exist
> 1a1. Program returns error message, saying that the task does not exist.<br>
> 1a2. Program prompts whether to add the task with information provided.<br>
> 1a3i. User inputs `Y`.<br>
>       * 1a3i1. program adds the new task.
>           * Use case ends.
> 1a3ii. User inputs `N`<br>
> Use case ends.<br>

#### Use case: Partially update and/or add new information to a task

**MSS:**

1. User requests to change part of the information of a task and/or add new information to a task
2. Program replaces old information requested for changing with new information provided and/or add new information to the task
Use case ends.

**Extensions:**

1a. The requested task name does not exist
> 1a1. Program returns error message, saying that the task does not exist.<br>
> 1a2. Program prompts whether to add the task with information provided.<br>
> 1a3i. User inputs `Y`.
>       * 1a3i1. program adds the new task.
>           * Use case ends.
> 1a3ii. User inputs `N`<br>
> Use case ends.

#### Use case: Delete a task

**MSS:**

1. User requests to delete a task.
2. Program removes the task from storage.
Use case ends

**Extensions:**

1a. The requested task name does not exist
> 1a1. Program returns error message, saying that the task does not exist.<br>
  Use case ends.

#### Use case: Mark a task as completed

**MSS:**

1. User requests to mark a task as completed.
2. Program adds a property ‘completed’ to the task requested.
Use case ends

**Extensions:**

1a. The requested task name does not exist
> 1a1. Program returns error message, saying that the task does not exist.<br>
  Use case ends.

1b. The requested task has already been marked as completed
> 1b1. Program returns error message, saying that the task is already completed.<br>
  Use case ends.

#### Use case: Show all tasks/events with some specified conditions

**MSS:**

1. User requests to show all tasks/events with some specified conditions.
2. Program retrieves and lists the tasks/events which matches all the conditions in the specified order.
Use case ends

**Extensions:**

1a. The list of tasks/events which matches all the conditions is empty
> 1a1. Program shows a no result message<br>
> Use case ends

#### Use case: Find tasks/events by providing some keywords

**MSS:**

1. User requests to find tasks/events by providing some keywords.
2. Program retrieves and lists the found tasks/events.
Use case ends

**Extensions:**

1a. No task/event is found

> 1a1. Program shows a no result message<br>
  Use case ends

#### Use case: Delete schedule data

**MSS:**

1. User requests to delete data
2. Program confirms deletion with user
3. Program deletes schedule.
Use case ends

**Extensions:**

2a. User cancels the deletion

> File not deleted<br>
  Use case ends

#### Use case: Designate data storage location

**MSS:**

1. User requests to designate data storage location
2. User chooses new location to save data
3. Program changes data storage location
4. If schedule data existed in previous data storage location, program copies data to new location
5. If schedule data existed in previous data storage location, program deletes data at previous location
Use case ends

**Extensions:**

*a. User cancels the operation

> Data storage location not changed <br>
  Use case ends

2a. Location is not valid/ Not allowed to access location

> 2a1. Program shows error message<br>
  Use case resumes at step 2

## Appendix C : Non Functional Requirements

1. The application should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. The application s hould come with automated unit tests and open source code.
3. The application should be able to handle at least 500 tasks.
    * Assume for each day in the calendar, you have 5 tasks per day, and you store tasks up to 3 months. 30*5*3 = 450 ~= 500.
4. The application should be able to display the result of the query in less than 5 seconds.
5. The application should run primarily on commandline-like interface.
6. The application should be able to work offline.
7. The application should work on a computer running Windows 7 or later with Java 8 installed.
8. The application should ship with regression testing available.
9. The application should be able to handle both tasks and events as the same concept.
10. The source code should be well-documented and ready to be continued by someone else.
11. The application should allow users to undo up to at least 10 most recent commands.
12. The application should be usable straight away, without requiring any installation.
13. The application should allow users to change the stored data location.
14. Version control should be used during development of product.
15. The application should provide help when the user enters an invalid command.
16. The data storage file should be in a human-editable format.

## Appendix D : Glossary

**Command:** A keyword that the program understands, to allow user to tell the program to execute a certain action.

**Data Storage File:** A database that contains all our tasks.

**Human Editable Format:** The task list is stored in a way such that you can literally open the file and edit in Notepad if you want (data can be preserved even if the program no longer exist in the future).

**Local Storage:** A database stored on the user’s computer system so that it does not require an Internet connection to function.

**Query:** An attempt by the user to ask something from the program.

**Regression Testing:** A tool to ensure correct implementation of the program, and to detect possible bugs that might resurface when making changes to the code.

**Source Code:** The underlying framework that the program is built from.

**Task:** Either (a) something that needs to be done, or (b) an event.

**Tag:** Classification of a task (can also be used for prioritizing tasks)

## Appendix E : Product Survey

This section indicates the strengths and weaknesses of our competitors’ products with regards to their capability in catering to Jim’s requirements.

#### Google Calendar

Strengths:

* Wide keyboard support
    * Q: Quick Add which resembles natural language
    * Ctrl+Z: undo
    * Esc: Back
    * A/M/W/D: Switch to Agenda/Month/Week/Daily view
    * J/K: Next/Previous in view
    * T: Today
    * /: Search
    * Arrow Keys + Enter: Expand event details
    * Arrow Keys + E: Show event details
* Integrated with Gmail, one-click to add event from inbox
* Offline support with Chrome
* Mini-calendar at sidebar
* Multiple calendars (can show/hide all)
* Email/Chrome/mobile notifications
* Syncs to cloud when online
* Has “Find a time” feature
* Supports recurring events
* Drag and drop event from one day to another
* Import/Export iCal/CSV calendar if offline

Weaknesses:

* Only color tags
* Keyboard shortcuts “has to be discovered”
* Unable to delete/edit event using one command
* Event time must be specific
* No tasks view with priority/deadlines
* Unable to “reserve” multiple slots
* Unable to mark event as”done”, “canceled”, etc.


#### Todoist

Strengths:

* “One shot approach” - Can enter tasks and details in one typed in command
* Typed in command resembles natural language
* Supports recurring tasks
* Allows postponement of tasks
* Allows entering tasks that need to be done before a specific date/time, or after a specific date/time, and items without specific times.
* Sync Online, but can be used offline too
* Multiplatform (Android, IOS, Windows, OSX, Browser extensions - Chrome, Firefox, Safari)
* Can set priorities for tasks

Weaknesses:

* Does not help find suitable slot to schedule task
* Marking item as done simply deletes it
* Cannot “block” or “release” multiple slots when timing is uncertain
* Mouse input required for most actions. Only inputting task is based on keyboard input. Rescheduling and deleting tasks require mouse clicks

#### Microsoft Outlook

Strengths:

* Can flag emails as task (i.e. Emails can be turned into tasks)
* Keyboard shortcuts discovery simple (press Alt, they will show you the letters to press)
* Allow tasks without specific time
* Can still work without internet, will sync back to the online server when internet connection is restored.
* Can block multiple slots as having multiple appointments in a time slot is allowed (However it is not built-in feature, have to manually use tags).
* Task can be sorted in due date (to allow us to know which task needs to be completed first)
* Tasks can be recurring in nature (lecture quiz task only needs to be created one time, a reminder will pop up every week)
* Tasks can be marked as done.
* Tasks can be set to different priorities (high/medium/low)
* You can indicate the amount of work done for the task (0% complete? 5% complete?)
* Tasks can be set with a reminder

Weaknesses:

* Tasks from emails cannot be renamed.
* Cannot allocate task to a certain time slot in the calendar
* Keyboard shortcuts does not always make sense (sometimes it is the first letter of the name, sometimes it is the second letter, etc)
* Keyboard shortcuts too long winded sometimes
* Tasks only appear as a list in ‘Daliy Task List’ if you force it to appear in calendar view
* No sense of context awareness at all, no GCal’s quick add (if you type “Eat Cheese in NUS at 9am”, it does not specify the location at NUS and the time is not set to 9am)
* No way to find suitable slots painlessly (no “Find a Time” feature).
* A lot of mouse action required (while keyboard shortcuts, exist they are not designed with keyboard shortcuts in mind, but rather with a mouse user).
* Only one reminder can be created for each task

#### iCalendar

Strengths:

* User can view upcoming tasks easily. Both on Mac and iOS(Siri)
* Add tasks with natural language input
* A very complete command list provided for users(Keyboard shortcut), and easily to remember
* Event has many properties, e.g. All day or not, tag, location, date, time, description, alert before event, description, repeat, even attachment
* Can choose specific functions for tags, e.g. whether to sync, universal alert time
* Specify location on map
* Invitation function
* Sharing with friends via email, iMessage
* Importing events from other calendar apps
* Add event from email, and even from date/time expressions in some other chatting apps
* Festivals and holidays are already stored, according to user’s location(country).
* Can set whether private
* Informs on the dates of friends’ birthdays and any important dates(Found in Facebook, Apple ID, etc).

Weaknesses:

* User has to specify the time period.
* Events/tasks cannot be marked as done
* Problem in importing from other app: sometimes will create several tasks of exact properties(duplicates).
* No feature for reserving time blocks
* No priority/importance feature
