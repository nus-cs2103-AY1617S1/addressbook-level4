<!--@@author A0143378Y-->
# Developer Guide

* [Introduction](#introduction)
* [Target Audience](#target-audience)
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

## Introduction
Welcome to _Mastermind_'s Developer Guide!  

_Mastermind_ is a to-do task manager that helps user keep track of their tasks. We aim to provide our users with an effective and efficient solution to managing their tasks so they are able to better handle their time, as well as completing tasks required.

This developer guide is for both existing and new developers of the team who are interested in working on _Mastermind_ in the future.  

This guide will show you the Product Architecture, APIs and the details regarding the different components.  

The next segment will show you how to set-up to make sure that you have the necessary tools before getting started. Feel free to approach our team for any clarifications that you may face during the process. Good luck and have fun coding!


## Setting up

#### Prerequisites

1. **JDK `1.8.0_60`**  or later.  
    > Having any Java 8 version is not enough.  
    This app will not work with earlier versions of Java 8.

2. **Eclipse** IDE.
3. **e(fx)clipse** plugin for Eclipse (Follow from step 2 onwards given in
   [this page](http://www.eclipse.org/efxclipse/install.html#for-the-ambitious)).
4. **Buildship Gradle Integration** plugin from the Eclipse Marketplace.


#### Importing the project into Eclipse

1. Fork this repo, and clone the fork to your computer.
2. Open Eclipse (Note: Ensure you have installed the **e(fx)clipse** and **buildship** plugins as given in the prerequisites above).
3. Click `File` > `Import`.
4. Click `Gradle` > `Gradle Project` > `Next` > `Next`.
5. Click `Browse`, then locate the project's directory.
6. Click `Finish`.

  > * If you are asked whether to 'keep' or 'overwrite' config files, choose to 'keep'.
  > * Depending on your connection speed and server load, it can even take up to 30 minutes for the set up to finish
      (This is because Gradle downloads library files from servers during the project set up process).
  > * If Eclipse auto-changed any settings files during the import process, you can discard those changes.

### Troubleshooting Project Setup
1. **Eclipse reports compile errors after new commits are pulled from Git:**  

    >Reason: Eclipse fails to recognize new files that appeared due to the Git pull.  
    >
    >Solution: Refresh the project in Eclipse. Right click on the project (in Eclipse package explorer), choose Gradle -> Refresh Gradle Project.  

2. **Eclipse reports some required libraries missing:**  

    >Reason: Required libraries may not have been downloaded during the project import.
    >
    >Solution: Run tests using Gradle once (to refresh the libraries).



## Design

### Software Architecture

To start off, let us introduce you to the overall structure of _Mastermind_. Do have a basic understanding of _Mastermind_'s different components before focusing on them individually.  

_Mastermind_ is split up into 5 main components, namely the `UI`, `Logic`, `Model`, `Storage` and `Commons`, as shown below, in Figure 1.


<img src="images/Architecture_diagram.png" width="600">  
The **_Architecture Diagram_** given above explains the high-level design of the App.
Given below is a quick overview of each component.

**`Main`** has only one class called [`MainApp`](../src/main/java/harmony/mastermind/MainApp.java). It is responsible for:
* At app launch: Initializes the components in the correct sequence, and connect them up with each other.
* At shut down: Shuts down the components and invoke clean up method where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.
Two of those classes play an important role at the architecture level.
* `EventsCenter` : This class (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
  is used by components to communicate with other components using events (i.e. a form of _Event Driven_ design).
* `LogsCenter` : Used by many classes to write log messages to the App's log files.

The rest of the App consists four components.
* [**`UI`**](#ui-component) : The UI of the App.
* [**`Logic`**](#logic-component) : The command executor.
* [**`Model`**](#model-component) : Holds the data of the App in-memory.
* [**`Storage`**](#storage-component) : Reads data from, and writes data to, the hard disk.
* [**`Memory`**](#memory-component) : Reads data from, and writes data to, the hard disk.

Each of the four components:
* Defines its _API_, which is an interface with the same name as the component. `Logic.java`
* Exposes its functionality using a `{Component Name}Manager` class e.g. `LogicManager.java`


The sections below give more details of each component.

#### UI component

`UI` is implemented by JavaFX 8 and consists of the main panel Main Window. This component primarily handles user input such as text input which will be entered via Command Line Input (CLI) as shown in Figure 2. On top of text input, users are also allowed to use keypress or mouse click. Inputs are passed on to the `Logic` component.  

If you are intending to work on the `UI`, you will need to update the application's internal state, which also includes:  
1.  UiManager.java  
2.  UiPartLoader.java  
3.  UiPart.java  


<img src="images/UI_diagram.png" width="800">  

**API** : [`Ui.java`](../src/main/java/harmony/mastermind/ui/Ui.java)

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](../src/main/java/harmony/mastermind/ui/MainWindow.java) is specified in [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml).

The `UI` component:
* Executes user commands using the `Logic` component.
* Binds itself to data in the `Model` so that the UI can automatically update when data in the `Model` change.
* Responds to some of the events raised from various parts of the App and updates  `UI` accordingly.

[insert ui class diagram]

We separated the UI into smaller components. Each components should handles the events raised by `EventCenter` if needed.

- `MainWindow.java`: handles the Tab name updates, initialize smaller components.
    - `CommandBox.java`: handles user input, listen to textfield change event.
    - `ActionHistoryPane.java`: display command result.
        - `ActionHistoryEntry.java`: renders each entry in the action history list.
    - `DefaultTableView.java`: an abstract class that define default table rendering algorithms. It should be extended by the following classes:
        - `HomeTableView.java`: display all tasks.
        - `TasksTableView.java`: display only floating tasks.
        - `EventsTableView.java`: display only events.
        - `DeadlinesTableView.java`: display only deadlines.
        - `ArchivesTableView.java`: display marked tasks.
- `HelpPopup.java`: display help window.

<!--@@author A0138862W -->
#### Logic component
`Logic` is the brain of the application as it controls and manages the overall flow of the application. Upon receiving the user input from `UI`, it will process the input using the `Parser` and return the result of executing the user input back to the `UI`. The inputs `Logic` takes in are command words such as `add`, `edit`, `delete`, etc. `Logic` will then execute them accordingly based on their functionality. If you were to work on this execution of user input, you will need to access `Storage` through the `EventsCenter` to retrieve and update the state of tasks.

<img src="images/Logic_diagram.png" width="800">  

**API** : [`Logic.java`](../src/main/java/harmony/mastermind/logic/Logic.java)

1. `Logic` uses the `Parser` class to parse the user command.
2. This results in a `Command` object which is executed by the `LogicManager`.
3. The command execution can affect the `Model` (e.g. adding a person) and/or raise events.
4. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `UI`.

##### Parser
`Parser` class uses [Regular Expression](https://www.tutorialspoint.com/java/java_regular_expressions.htm) (regex) to extract parameters from raw input. Each `Command` defines the regex in their respective class variable `COMMAND_ARGUMENTS_REGEX`.

`parseCommand()` method registers all the recognized `Command` under `switch` statement.

The regular expression can be tested at [regex101](https://regex101.com).

You can visit the following link to inspect the regex test result for `AddCommand` and `EditCommand`:

- `AddCommand`: [https://regex101.com/r/M2A3tB/15](https://regex101.com/r/M2A3tB/15)

- `EditCommand`: [https://regex101.com/r/VcdUmR/4](https://regex101.com/r/VcdUmR/4)

##### Add
The `add` command allows the user to add tasks, deadlines or events to _Mastermind_. Tasks, deadlines and events are differentiated by which attribute is updated.
> * Floating tasks are tasks without start and end date.
> * Deadlines are tasks with an end date.
> * Events are tasks with both start and end date.
>
All tasks are stored as object attributes such as name, description, end date, start date and type.  

You can refer to Figure 4 and Figure 5 below and the next page for the sequence diagram of `add`.

<img src="images/Add Command Sequence Diagram part 1.png" width="800">  

> Note how the `Model` simply raises a `TaskManagerChangedEvent` when _Mastermind_'s data is changed, instead of directly asking the `Storage` to save the updates to the hard disk.

The diagram below shows how the `EventsCenter` reacts to that event, which eventually results in the updates being saved to the hard disk.

<img src="images/Add Command Sequence Diagram part 2.png" width="800">  

> Note how the event is propagated through the `EventsCenter` to the `Storage` and `UI` without `Model` having to be coupled to either of them. This is how we reduce direct coupling between 2 components.


##### Edit
This command allows the users to update attributes of items they have already added in _Mastermind_.  

The user can update the task by choosing the index of the task they want to change. They will then choose the specific field, such as start date, that they want to change.   

However, the user can only update one item at a time. To update, the item being updated must be found, and removed from the `Storage`. After updating the attribute, the item is re-added back into `Storage`. If the update is successful, the details of the item will be shown to the user at the console output. Otherwise, an error message is generated.

The details are shown in the following diagrams.

<img src="images/Edit Command Sequence Diagram part 1.png" width="800">  

2 events are raised during the execution of `Edit`.

<img src="images/Edit Command Sequence Diagram part 2.png" width="800">  


##### Undo & Redo
The `undo` and `redo` commands allow user to recover from mistakes. The user can execute `undo` or `redo` command multiple times to the earliest entry.

Note that _Mastermind_ does not support `undo` or `redo` for `import`, `export`, `list`, `upcoming` and `clear` commands. The undo and redo history will be cleared upon executing such commands.

The `undo` and `redo` commands are session based, meaning that the history will be cleared when the user exit the application.

[insert undo class diagram]

The `undo` command makes use of [Strategy Pattern](http://www.tutorialspoint.com/design_pattern/strategy_pattern.htm) to manage the logic flow. All commands that support `undo` operation must implement the `Undoable` interface. Similarly, to support `redo` operation, the command should implement the `Redoable` interface.

We chose this design pattern because different command has different `undo` behaviour during runtime. Furthermore, it's more intuitive and reasonable to tell the specialized command how to `undo` its own execution.

It's also aligned with the [Open/Close Principle](http://www.oodesign.com/open-close-principle.html) since the `UndoCommand.java` is close for modification, but open for extension to anticipate more `undo` strategies in the future.

[insert undo sequence diagram]

We combine this design with the `Stack` data structure to make sure the commands `undo` in proper sequence. The `ModelManager` will manage this stack as `undoHistoryStack`.

At the end of the command execution, the command should push itself to the `undoHistoryStack`.

Similarly, `redo` command makes use of the Strategy Pattern as well. `ModelManager` keeps two different stacks to control the undo and redo command flow.


##### Mark
The `mark` command allows users to mark their tasks/deadlines/events as completed. This removes the task from the tasks/deadlines/events field, and moves it into the `Archives`. The `mark` command will not delete the task immediately. In the event that users want to unmark the task, users can do so by using the `unmark` Command. Additionally, User can specify `mark due` to bulk mark all tasks that are due.


##### Unmark
The `unmark` command allows users to unmark the tasks in the `Archives` tab. _Mastermind_ will add them back to their original position in their orginal tab.

> This command will only work in the `Archives` tab.


##### Delete
This command allows the user to delete any task they have input into the program beforehand. Users have to specify the index to delete after the command word.

Details are illustrated in the following diagrams.

<img src="images/Delete Command Sequence Diagram part 1.png" width="800">  

> Again, note how that `Model` simply raises an event instead of relying on `Storage` directly.

And `EventsCenter` reacts to the event accordingly.

<img src="images/Delete Command Sequence Diagram part 2.png" width="800">  


##### Clear
`Clear` wipes all tasks(floating tasks, deadlines and events) currently registered in _Mastermind_.  

After inputting the command, the data is cleared from the `Storage` by raising an event to the `EventCenter`.   

> Undo and redo histories are also cleared from _Mastermind_.


##### Find
To find an item by name, the user will search through the `Storage` by calling "find `<task>`". It calls `FindCommand` to find the exact terms of the keywords entered by the user.

##### Findtag
To find an item by tag, the user will search through the `Storage` by calling "findtag `<tag>`". It calls `FindTagCommand` to find the exact terms of the keywords entered by the user.

##### List
Calling `list [tab_name]` will display the items in that category.


##### Upcoming
Calling `upcoming [task_type]` will updates the list to show the user task in the upcoming week. If `task_type` is indicated, it will show only tasks of that specified type.


##### Relocate
This command allows the user to specify the save location of the data file. The file path the user inputs is contained in an event and passed to the `Storage` component from the `Logic` component via the `EventsCenter`. `Relocate` relies on `FileUtil` to check whether the input file path is valid and writable first. It then raises an event to the `EventsCenter` to inform `Storage` to handle the command. Hence there is no coupling from `Logic` to `Storage`.


##### Import
The `import` command allows user to migrate their existing task list from other application to _Mastermind_. It supports `.csv` and `.ics` format currently. This command should append the imported task lists in _Mastermind_ instead of overwriting it.

Note that the `.csv` format must be compatible with [Google Calendar specification](https://support.google.com/calendar/answer/37118?hl=en) in order for _Mastermind_ succesfully parses the data.

The `.ics` format must comply with the [iCalendar](http://icalendar.org/) format. Please refer to the [RFC5545](http://icalendar.org/RFC-Specifications/iCalendar-RFC-5545/) for specification details.

The `import` command makes use of the following 3rd party libraries:
- [BiWeekly](https://github.com/mangstadt/biweekly) for parsing `.ics` format.
- [Apache Commons CSV](https://commons.apache.org/proper/commons-csv/) for parsing `.csv` format.

##### Export
The `export` command allows user to export their task list from _Mastermind_ to `.csv` format. The exported format should comply with [Google Calendar specification](https://support.google.com/calendar/answer/37118?hl=en).

Due to the limitation of Google Calendar, it's mandatory to specify a valid start date and end date. In order to export floating and deadline tasks, we have made the following decisions:

- Floating Task: start & end date are set to exporting date; `All Day Event` field set to `TRUE`.
- Deadline Task: start & end date share the same date; `All Day Event` field set to `FALSE`.
- Event Task: start & end date are exported directly from _Mastermind_; `All Day Event` field set to`FALSE`.

In addition, the tags defined in _Mastermind_ will be exported in `Description` field.

##### History
This command allows users to toggle the Action History bar via the command line. The command will raise an event which will be handled by `UI`.


##### Help
Calling `help` will show a popup containing a command summary. To close the popup, press any key.

> An event will be raised in `Logic`, which will be handled by the `UI`.



##### Exit
This `exit` command runs when the user tries to exit the program, allowing the program to close.

> The undo and redo history will <b>not</b> be kept.

<!--@@author A0124797R-->
### Model component

<img src="images/Model_diagram.png" width="800">  

**API** : [`Model.java`](../src/main/java/harmony/mastermind/model/Model.java)

The `Model`:
* Stores a `UserPref` object that represents the user's preferences
* Stores _Mastermind_'s data
* Exposes a `UnmodifiableObservableList<ReadOnlyTask>` that can be 'observed' e.g. the `UI` can be bound to this list so that the `UI` automatically updates when the data in the list change.
* Does not depend on any of the other three components.

##### Task & TaskBuilder

The `Task.java` and `TaskBuilder.java` can be found in `harmony.mastermind.model.task` package.

We identified three different types of Tasks (Floating, Deadlines, Events), which differ only by the existence of start date and end date in their respective attributes. While inheritance seems to be a viable solution, it's not necessary true that a floating task is interchangeable with deadline and event, or vice versa. Moreover, how can we prevent the user from entering a start date without end date?

Eventually, we resolved this issue by using the [Builder Pattern](https://www.tutorialspoint.com/design_pattern/builder_pattern.htm). This gives us the following benefits:

- **Avoid long constructor**: `Task` object has many optional attributes, therefore it's impractical to keep multiple constructors with long parameter list. Builder pattern mitigates such problem by using step by step approach to build the `Task` object.

- **Increase Readability**: The object creation process uses fluent method name that is much more readable than conventional constructor approach.

- **Enforce mandatory attributes**: The only way to set the start and end date through `TaskBuilder` is by using `asEvent()`, `asDeadline()`, `asFloating()` methods. Ideally, the `TaskBuilder` should return an immutable `ReadOnlyTask` upon building. There's no way that the user can create a `Task` with start date but without an end date.

_Example:_
```java
TaskBuilder taskBuilder = new TaskBuilder(taskName);
taskBuilder.withCreationDate(createdDate);
taskBuilder.withTags(tags);
taskBuilder.asRecurring(recur);

if (isEvent()){
    taskBuilder.asEvent(startDate,endDate);
} else if (isDeadline()){
    taskBuilder.asDeadline(endDate);
} else if (isFloating()){
    taskBuilder.asFloating();
}

Task task = taskBuilder.build();
```

<!--@@author A0139194X-->
### Storage component

<img src="images/Storage_diagram.png" width="800">  

**API** : [`Storage.java`](../src/main/java/harmony/mastermind/storage/Storage.java)

The `Storage` component:
* can save `UserPref` objects in json format and read it back.
* can save the _Mastermind_'s data in xml format and read it back.

`Storage` is completely decoupled from the other components. It functions by taking events from the `EventsCenter`.

### Common classes

Classes used by multiple components are in the `harmony.mastermind.commons` package.
This component will be maintained by developers working on any of the other components because of its wide scope of application. You can find 4 packages, namely: `core`, `events`, `exceptions` and `utils`.

<!--@@author A0143378Y-->
## Memory Component
This is the temporary memory where most changes regarding memory take place. This is before the information to be stored is transferred permanently in the storage.



## Implementation

### Logging

We are using `java.util.logging.Logger` as our logger, and `LogsCenter` is used to manage the logging levels of loggers and handlers (for output of log messages).

- The logging level can be controlled using the `logLevel` setting in the configuration file.
    > See [Configuration](#configuration).
- The `Logger` for a class can be obtained using `LogsCenter.getLogger(Class)` which will log messages according to the specified logging level.

- Currently log messages are output through: `Console` and `.log`.

**Logging Levels**

- SEVERE
  - Critical use case affected, which may possibly cause the termination of the application.

- WARNING
  - Can continue, but with caution.

- INFO
  - Information important for the application's purpose.
    - e.g. update to local model/request sent to cloud
  - Information that the layman user can understand.

- FINE
  - Used for superficial debugging purposes to pinpoint components that the fault/bug is likely to arise from.
  - Should include more detailed information as compared to `INFO` i.e. log useful information!
    - e.g. print the actual list instead of just its size.

### Configuration

Certain properties of the application can be controlled (e.g App name, logging level) through the configuration file. _Mastermind_ will load the xml data file from the file path specified in `config.json`.
> Stored as: `config.json`.


## Testing

**In Eclipse**:
> If you are not using a recent Eclipse version (i.e. _Neon_ or later), enable assertions in JUnit tests as described [here](http://stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option).

* To run all tests, right-click on the `src/test/java` folder and choose
  `Run as` > `JUnit Test`.
* To run a subset of tests, you can right-click on a test package, test class, or a test and choose to `Run as` a `JUnit Test`.

**Using Gradle**:
* See [UsingGradle.md](UsingGradle.md) for how to run tests using Gradle.

Tests can be found in the `./src/test/java` folder.

1. **GUI Tests** - These are _System Tests_ that test the entire App by simulating user actions on the GUI. These are in the `guitests` package.

2. **Non-GUI Tests** - These are tests not involving the GUI. They include,
   1. _Unit tests_ targeting the lowest level methods/classes.  
      e.g. `harmony.mastermind`
   2. _Integration tests_ that are checking the integration of multiple code units
     (those code units are assumed to be working).  
      e.g. `harmony.mastermind.commons.core`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as how they are connected together.  
      e.g. `harmony.mastermind.storage`

**Headless GUI Testing** :
Thanks to the ([TestFX](https://github.com/TestFX/TestFX)) library we use, our GUI tests can be run in the _headless_ mode.
 In the headless mode, GUI tests do not show up on the screen.
 That means the developer can do other things on the Computer while the tests are running. See [UsingGradle.md](UsingGradle.md#running-tests) to learn how to run tests in headless mode.

## Dev Ops

### Build Automation
See [UsingGradle.md](UsingGradle.md) to learn how to use Gradle for build automation.

### Continuous Integration
We use [Travis CI](https://travis-ci.org/) to perform Continuous Integration on our projects to ensure that every time we merge a new feature into the main branch, automated testing is done to verify that the app is working. See [UsingTravis.md](UsingTravis.md) for more details.

## Making a Release

Here are the steps to create a new release.

 1. Generate a JAR file [using Gradle](UsingGradle.md#creating-the-jar-file).
 2. Tag the repo with the version number. e.g. `v0.1`
 3. [Create a new release using GitHub](https://help.github.com/articles/creating-releases/) and upload the JAR file you created.

## Managing Dependencies

A project often depends on third party libraries. For example, _Mastermind_ depends on the [Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_ can be automated using Gradle. For example, Gradle can download the dependencies automatically, which is better than these alternatives:  
* Include those libraries in the repo (this bloats the repo size).  
* Require developers to download those libraries manually (this creates extra work for developers).  

## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | new user | see usage instructions | refer to instructions when I forget how to use the app
`* * *` | user | add a new task | so I can register my things to do
`* * *` | user | add a floating task | have a task without a deadline
`* * *` | user | add a recurring task | add repeating tasks only once
`* * *` | user | delete a task | remove entries that I no longer need
`* * *` | user | edit a task | update entries as needed
`* * *` | user | find a task by name | locate details of the task without having to go through the entire list
`* * *` | user | find a task by deadline | locate tasks that are due soon without having to go through the entire list
`* * *` | user | undo a task entered | undo a mistake easily
`* * *` | user | re-do a task entered | redo a mistake easily
`* * *` | user | sort list by alphabetical order and date | find my tasks easily
`* * *` | user | mark tasks as done | archive my completed tasks
`* * *` | user | specify the location of file storage | choose where to save the to do list
`* *` | user | see my tasks in user interface | have an easier time using the app
`* *` | user | show upcoming tasks | can see tasks that are nearing
`*` | user | specify my own natural language | customise the app
`*` | user | set categories | organise my tasks
`*` | user | block out timings | reserve time slots for tasks
`*` | user | create subtasks | breakdown my tasks into smaller problems
`*` | user | set reminders for tasks | reduce chances of forgetting to do a task
`*` | user | import a list of to do tasks | add in tasks without registering them individually
`*` | user | export a list of tasks | export to another computer



## Appendix B : Use Cases

(For all use cases below, the **System** is _Mastermind_ and the **Actor** is the _User_, unless specified otherwise)

| # | Use Case | Descriptions  |
|---|---|---|
| [UC1](#uc1-display-help) | Display Help  | Display help when requested. |
| [UC2](#uc2-adddo-a-task) | Add/Do a Task  | Adding a task. A task can be concrete (have defined date/time) or floating (without date/time). |
| [UC3](#uc3-edit-a-task) | Edit a Task  | Edit the details of a single task. The command only update fields specified by the User. Unspecified field remains untouched. |
| [UC4](#uc4-mark-task-as-done) | Mark Task as done  | Mark a task as done by index. A marked task should be automatically archived and exclude from display and search. |
| [UC5](#uc5-unmark-a-task) | Unmark a Task  | Unmark a task as done by index. The Archived task will add back to the respective tabs.|
| [UC6](#uc6-delete-a-task) | Delete a task  | Remove a task entry by index. |
| [UC7](#uc7-undo-action) | Undo Action  | Undo last action performed. |
| [UC8](#uc8-redo-action) | Redo Action  | Redo an action performed in UC7. |
| [UC9](#uc9-lists-tasks) | Lists Tasks  | Display lists of tasks added into the System. |
| [UC10](#uc10-find-tasks) | Find Tasks  | Search for task by name with keywords. |
| [UC11](#uc11-find-tasks-by-tag) | Find Tasks by tag  | Search for task by tag with keywords. |
| [UC12](#uc12-upcoming-task) | Show upcoming Tasks  | Display floating tasks and task that is due in a weeks time. |
| [UC13](#uc13-relocate-storage-location) | Relocate storage location  | Change the current storage to other directory specified by the user. |
| [UC14](#uc14-importing-files-to-mastermind) | Import File  | Adds tasks identified in file |
| [UC15](#uc15-exporting-files-to-.csv-or-.ical) | Export | Exports data as .csv or .ical |
| [UC16](#uc16-history) | History | Toggles the Action History Bar |
| [UC17](#uc17-clear-everything) | Clears everything | System performs bulk delete on the data (Deadlines, events, tasks). |
| [UC17](#uc17-exit-application) | Exit application  | Quit the application |

---

### UC1: Display help

Display help when requested.

##### Main Success Scenario:

1. User requests to display help.

2. System display the help popup.

3. User presses any key to close the popup.

4. Use case ends.

##### Extensions

1a. User entered an invalid command.

* 1a1. System display unsuccessful message.

* 1a2. Use case ends.

---

### UC2: Add/Do a Task

Adding a task. A task can be concrete (have defined date/time), recurring (repeats with defined date/time) or floating (without date/time).

##### Main Success Scenario:

1. User requests to add a task.

2. System accepts the command & parameters, creates the task and displays successful message to User.

3. Use case ends.

##### Extensions

1a. User entered an invalid command.

* 1a1. System display unsuccessful message.

* 1a2. Use case ends.

1b. User enters task with date/time.

* 1b1. System accepts the command as concrete task.

* 1b2. Use case resume at 2.

1c. User enters task without date/time.

* 1c1. System accepts the command as floating task.

* 1c2. Use case resume at 2.

1d. User enters task with start date after end date.

* 1d1. System display error message.

* 1d2. Use case ends.

1e. User enters recurring task without date/time.

* 1e1. System accepts the command as floating task.

* 1e2. Use case resumes at 2.

1f. User enters recurring task with date/time.

* 1f1. System accepts the command as recurring task.

* 1f2. Use case resume at 2.


---

### UC3: Edit a Task

Edit the details of a single task. The command only update fields specified by the User. Unspecified field remains untouched.

##### Main Success Scenario

1. User request to edit a task by index.

2. System find and update the task.

3. System display successful message.

4. Use case ends.

##### Extensions

1a. User entered an invalid command.

* 1a1. System display unsuccessful message.

* 1a2. Use case ends.

2a. Invalid index.

* 2a1. System cannot find the task associated with the index.

* 2a2. System display unsuccessful message.

* 2a3. Use case ends.

---

### UC4: Mark Task as done

Mark a task entry by index or keyword 'due'.

##### Main Success Scenario

1. User request to mark a task by index.

2. System find and mark the task and remove from respective tab and add to archives tab.

3. System display successful message.

4. Use case ends.

##### Extensions

1a. User entered an invalid command.

* 1a1. System display unsuccessful message.

* 1a2. Use case ends.

2a. Invalid index.

* 2a1. System cannot find the task associated with the index.

* 2a2. System display unsuccessful message.

* 2a3. Use case ends.

2b. User tries to mark a Task that is already marked.

* 2b1. System check that task associated with the index is already marked.

* 2b2. System display unsuccessful message.

* 2b3. Use case ends.

2c. User wants to mark all due tasks.

* 2c1. System checks for all tasks that are due

* 2c2. System removes all tasks that are due

* 2c3. Use case continues to 3.

---

### UC5: Unmark a Task

Unmark a task entry by index.

##### Main Success Scenario

1. User request to unmark a task by index.

2. System find and unmark the task and remove from archives tab and add to the respective tab.

3. System display successful message.

4. Use case ends.

##### Extensions

1a. User entered an invalid command.

* 1a1. System display unsuccessful message.

* 1a2. Use case ends.

2a. Invalid index.

* 2a1. System cannot find the task associated with the index.

* 2a2. System display unsuccessful message.

* 2a3. Use case ends.

2b. User tries to Unmark a Task that not marked yet.

* 2b1. System check that task associated with the index is not marked yet.

* 2b2. System display unsuccessful message.

* 2b3. Use case ends.

---

### UC6: Delete a Task

Remove a task entry by index.

##### Main Success Scenario

1. User request to delete a task by index.

2. System find and remove the task.

3. System display successful message.

4. Use case ends.

##### Extensions

1a. User entered an invalid command.

* 1a1. System display unsuccessful message.

* 1a2. Use case ends.

2a. Invalid index.

* 2a1. System cannot find the task associated with the index.

* 2a2. System display unsuccessful message.

* 2a3. Use case ends.

---

### UC7: Undo Action

Undo last action performed.

##### Main Success Scenario

1. User requests to undo last action performed.

2. System pop from Undo stack and performs undo on the last action performed.

3. System put the action into Redo stack.

3. System display successful message and details of the undo operation.

4. Use case ends.

##### Extensions

1a. User entered an invalid command.

* 1a1. System display unsuccessful message.

* 1a2. Use case ends.

2a. System cannot find any previous action in Undo stack.

* 2a1. System display unsuccessful message.

* 2a2. Use case ends.

---

### UC8: Redo Action

Redo an action performed in UC7.

##### Main Success Scenario

1. User requests to redo an action performed in UC7.

2. System pop from Redo stack and performs redo on the last action performed in UC7.

3. System display successful message and details of the redo operation.

4. Use case ends.

##### Extensions

1a. User entered an invalid command.

* 1a1. System display unsuccessful message.

* 1a2. Use case ends.

2a. System cannot find any previous action in Redo stack.

* 2a1. System display unsuccessful message.

* 2a2. Use case ends.

---

### UC9: Lists Tasks

Display lists of tasks added into the System.

##### Main Success Scenario:

1. User requests to lists tasks.

2. System display list of tasks.

3. Use case ends.

##### Extensions

1a. User entered an invalid command.

* 1a1. System display unsuccessful message.

* 1a2. Use case ends.

2a. System cannot find any task.

* 2a1. System display empty list.

* 2a2. Use case ends.

---

### UC10: Find Tasks by name

Search for task by name.

Note: The combination is filtered using **OR** operation.  


##### Main Success Scenario:

1. User request to search for a task by name.

2. System Search for a task that matches the parameters.

3. System displays the matching results to the User.

4. Use case ends.

##### Extensions

1a. User entered an invalid command.

* 1a1. System display unsuccessful message.

* 1a2. Use case ends.

2a. System cannot find any task.

* 2a1. System display empty list.

* 2a2. Use case ends.

---

### UC11: Find Tasks by tags.

Search for tasks by tags.

Note: The combination is filtered using **OR** operation.

##### Main Success Scenario:

1. User request to search for a tasks by tags.

2. System Search for a task that matches the parameters.

3. System displays the matching results to the User.

4. Use case ends.

##### Extensions

1a. User entered an invalid command.

* 1a1. System display unsuccessful message.

* 1a2. Use case ends.

2a. System cannot find any task.

* 2a1. System display empty list.

* 2a2. Use case ends.

---

### UC11: Upcoming Tasks

Shows all floating tasks, deadlines and events that are due in the upcoming week.

##### Main Success Scenario

1. User requests to load upcoming command.

2. System filter and display upcoming tasks.

3. Use case ends.

##### Extensions

1a. User entered an invalid command.

* 1a1. System display unsuccessful message.

* 1a2. Use case ends.


1b. User requests to show upcoming deadlines.

* 1b1. System filters only deadlines.

* 1b2. Use case resumes at 2.

1c. User requests to show upcoming deadlines.

* 1c1. System filters only deadlines.

* 1c2. Use case resumes at 2.

2a. System cannot find any task.

* 2a1. System display empty list.

* 2a2. Use case ends.



---

### UC12: Relocate storage location

Change the current storage to other directory specified by the user.

##### Main Success Scenario

1. User requests to relocate the storage directory.

2. System changes the storage directory according to user input.

3. System copies current storage to the new location.

4. System deletes old file at old storage location.

5. System displays successful message.

6. Use case ends.

##### Extensions

1a. User entered an invalid command.

* 1a1. System displays unsuccessful message.

* 1a2. Use case ends.

2a. Invalid storage location.

* 2a1. System displays invalid path message.

* 2a2. Use case ends.

2b. Storage location is not accessible/writable.

* 2b1. System displays unwrittable file message.

* 2b2. Use case ends.

---

### UC13: Importing files to Mastermind

Import `.ics` or `.csv` file and add the relevant tasks into Mastermind.

##### Main Success Scenario

1. User requests to import file.

2. System locate the file and attempt to read.

3. System adds tasks identified from file into Mastermind.

4. System displays successful message.

5. Use case ends.

##### Extensions

1a. User entered an invalid command.

* 1a1. System display unsuccessful message.

* 1a2. Use case ends.

2a. Invalid file location.

* 2a1. System display unsuccessful message.

* 2a2. Use case ends.

2b. Invalid file type.

* 2b1. System display unsuccessful message.

* 2b2. Use case ends.

---

### UC14: Exporting files to .csv
Exports selected data to `.csv`.

##### Main Success Scenario


1. User requests to export file.

2. System locates the export location.

3. System attempts to write tasks identified from Mastermind into file

4. System displays successful message.

5. Use case ends.

##### Extensions
1a. User entered an invalid command.

* 1a1. System display unsuccessful message.

* 1a2. Use case ends.

2a. Invalid file location.

* 2a1. System display unsuccessful message.

* 2a2. Use case ends.

---

### UC15: History
Toggles the Action History Bar

##### Main Success Scenario
1. User requests to toggle Action Histroy Bar

2. Action History Bar toggled.

3. Use case ends.

##### Extensions
1a. User entered an invalid command.

* 1a1. System displays unsuccessful message.

* 1a2. Use case ends.

---

### UC16: Clear everything

System performs bulk delete on the data (Deadlines, events, tasks).
##### Main Success Scenario

1. User requests to clear Mastermind

2. System proceed to perform bulk action described in UC6 for the specified category.

3. Use case ends.

##### Extensions

1a. User entered an invalid command.

* 1a1. System displays unsuccessful message.

* 1a2. Use case ends.

---

### UC17: Exit application

Quit the application.

##### Main Success Scenario

1. User requests to exit application.

2. System perform synchronization and save it to the storage.

3. System exit application.

4. Use case ends.

##### Extensions

1a. User entered an invalid command.

* 1a1. System display unsuccessful message.

* 1a2. Use case ends.

---

## Appendix C : Non Functional Requirements

1. Should backup tasks list.
2. Should work as a stand alone.
3. Should be able to store 1000 tasks.
4. Should be maintainable and scalable.
5. Should not use relational databases.
6. Should be user friendly for new users.
7. Should be able to be accessed offline.
8. Should come with automated unit testing.
9. Should follow the Object-oriented paradigm.
10. Should work without requiring an installer.
11. Should be able to startup and quit within 1 second.
12. Should display up to date tasks when command is given.
13. Should store data locally and should be in a .xml file.
14. Should work well without any third party framework/library.
15. Should have a Command Line Interface as the primary mode of input.
16. Should be able to display tasks within 1 second when command is given.
17. Should be able to run on all [mainstream OS](#mainstream-os) for desktop.
18. Should have a simple GUI that displays [relevant information](#relevant-information).


## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X.

##### Relevant Information

> Tasks, due dates, tags.


## Appendix E : Product Survey

##### Google Calendar

> Pros:
> *  Able to sync calendars from other people
> *  Chrome extension for offline connectivity
> *  Multiple viewing options (Calendar/To do list view)
> *  Has a Command Line Interface (CLI)

> Cons:
> *  Unable to support floating task
> *  Unable to mark tasks as done
> *  Unable to block out and free up timings
> *  CLI commands only for addition of tasks
> *  Bad interface

##### Wunderlist

> Pros:
> *  Able to set categories
> *  Able to mark tasks as done
> *  Able to read tasks from e-mails
> *  Able to assign tasks to someone
> *  Able to search for tasks easily
> *  Able to migrate tasks from one category to another easily
> *  Web and offline desktop version available

> Cons:
> *  Unable to create subtask
> *  Unable to support recurring tasks
> *  Unable to block out time slots
> *  Unable to set start date for tasks
> *  Only has a list view

##### Todoist

> Pros:
> *  Able to set categories
> *  Able to collaborate with others
> *  Able to have sub-projects and sub-tasks
> *  Able to support recurring tasks
> *  Able to sort tasks by priority level
> *  Able to integrate from e-mail
> *  Able to backup automatically

> Cons:
> *  Unable to block out timings
> *  Unable to export out To-do list
> *  Minimal CLI
> *  Have to do a lot of clicking

##### Any.Do

> Pros:
> *  Able to set categories by type and day
> *  Able to show completed tasks
> *  Able to collaborate with others
> *  Able to support sub-tasks
> *  Able to add attachments
> *  Able to support recurring tasks
> *  Able to mark task as done
> *  Able to notify and remind user
> *  Able to have action shortcuts
> *  Able to have different types of views

> Cons:
> *  Unable to support floating tasks
> *  No CLI

##### Evernote

> Pros:
> *  Able to quick search
> *  Able to support handwriting, embedded images/audio and links
> *  Able to work with camera

> Cons:
> *  No CLI
> *  No Calendar view

##### Trello

> Pros:
> *  Able to mark tasks as "in-progress"
> *  Able to view as calendar

> Cons:
> *  Unable to import or export
> *  Relies on UI interaction
> *  No CLI
> *  Need to pay for premium use to access 3rd party features
> *  No desktop version
