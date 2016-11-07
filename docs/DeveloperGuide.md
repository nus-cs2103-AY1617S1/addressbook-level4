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

<!-- @@author A0139812A -->

### Architecture

<img src="images/Architecture Diagram.png"><br>
The **_Architecture Diagram_** given above explains the high-level design of the App.
Given below is a quick overview of each component.

`Main` has only one class called [`MainApp`](../src/main/java/seedu/address/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connect them up with each other.
* At shut down: Shuts down the components and invoke cleanup method where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.
Three of those classes play important roles at the architecture level.

* `EventsCenter` : This class (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
  is used by components to communicate with other components using events (i.e. a form of _Event Driven_ design)
* `LogsCenter` : Used by many classes to write log messages to the App's log file.
* `ConfigCenter` : Used by many classes to access and save the config
* `EphemeralDB` : Used by the UI as well as the Controller, so that the Controller is able to refer to items in the UI level. One example would be for the controller to get the index each item was listed, since the ordering of items is only determined at the UI level.

The rest of the App consists of the following.

* [**`UI`**](#ui-component) : The UI of tha App.
* [**`InputHandler`**](#inputhandler-component) : The command receiver.
* [**`Controller`**](#controller-component) : The command executor.
* [**`Model`**](#model-component) : Holds the data of the App in-memory.
* [**`Storage`**](#storage-component) : Reads data from, and writes data to, the hard disk.

Each of the components defines an _API_ in an `interface` with the same name as the component.

The sections below give more details of each component.

### UI component

<img src="images/UiArchitecture.png"><br>

**API** : [`Ui.java`](../src/main/java/seedu/todo/ui/Ui.java)

The `UI` uses the JavaFX UI framework. The UI consists of a `MainWindow`, which contains the application shell components such as the `Header` and the `Console`, and a currently displayed `View`, denoted by `currentView`. Each `View` will define the layout and subcomponents that will be rendered within the `View`.

#### Components

The `UI` is predicated on the concept of a **Component**. A Component is a single sub-unit of the UI, and should preferably only be responsible for a single item or functionality in the UI. For example, a task item in the UI is a single Component, as it is responsible for purely displaying the task information. A task list is also a Component, as it contains multiple task items, and it is responsible just for rendering each task item.

Hence, a Component has the following properties:

- Associated with FXML files
- Loaded with `load`
- Able to accept **props**
- Rendered in placeholder panes
- Can load sub-Components

*Note: The concept of Components and their associated behaviours came from [React](https://facebook.github.io/react/), a modern JavaScript library for the web.*

##### Associated with FXML files

Each Component is associated with a matching `.fxml` file in the `src/main/resources/ui` folder. For example, the layout of the [`TaskList`](../src/main/java/seedu/todo/ui/components/TaskList.java) Component is specified in [`TaskList.fxml`](../src/main/resources/ui/components/TaskList.fxml).

To learn more about FXML, check out this [tutorial](http://docs.oracle.com/javafx/2/get_started/fxml_tutorial.htm).

##### Loaded with `load`

To load a Component from FXML, use the `load` method found on Component, which calls `UiPartLoader` to read the FXML file, loads a JavaFX Node onto the Stage, and returns the Component which can control the Node on the Stage.

Example usage:

``` java
TaskList taskList = load(primaryStage, placeholderPane, TaskList.class);
```

##### Able to accept props

Components should define a set of public fields or **props** so that dynamic values can be passed into the Component and displayed. These props can be displayed in the UI at the `componentDidMount` phase, which will be explained more below.

For example, to pass in tasks to a TaskList component, simply set the value of `taskList.tasks`, or however it is defined in `TaskList`.

##### Rendered in placeholder panes

After props have been passed, the loaded node can be rendered into a placeholder `Pane`. Typically, these panes are `AnchorPane`s defined in the layout in the FXML file.

##### Can load sub-Components

Every Component has a method called `componentDidMount`, which is run after `render` is called. Hence, there are a few uses of `componentDidMount`:

- Control UI-specific properties which cannot be done in FXML
- Set UI component values (e.g. using `setText` on an FXML `Text` object)
- Load sub-Components and propogate the chain

Hence, a Component can contain further sub-Components, where each Component is not aware of its parent and only renders what it is told to (via props).

Example usage:

``` java
public void componentDidMount() {
    // Set Text field value
    textField.setText(textProp);

    // Load and render sub-components
    SubComponent sub = load(primaryStage, placeholderPane, SubComponent.class);
    sub.value = subTextValue;
    sub.render();
}
```

#### Views

A `View` is essentially a special type of Component, with no implementation differences at the moment. However, a `View` is the grouping of Components to form the whole UI experience. In the case of this app, the `View` corresponds with the portion between the Header and the Console. Different `View`s can be loaded depending on the context.

#### MultiComponents

A `MultiComponent` is also a special type of Component, except that the `render` method behaves differently. Successive calls to `render()` would cause the node to the rendered to the placeholder multiple times, instead of replacing the old node. This is especially useful for rendering lists of variable items, using a loop.

To clear the placeholder of previously rendered items, use `MultiComponent.reset(placeholder)`.

Example usage:

``` java
public void componentDidMount() {
    // Reset items
    TaskItem.reset(placeholder);

    // Load multiple components
    for (Task task : tasks) {
        TaskItem item = load(primaryStage, placeholder, TaskItem.class);
        item.value = task.value;
        item.render();
    }
}
```

### InputHandler component

<img src="images/InputHandler.png"><br>

The InputHandler is the bridge facilitating the handoff from the View to the Controller when the user enters an input.

**API** : [`InputHandler.java`](../src/main/java/seedu/todo/ui/components/InputHandler.java)

1. The console input field will find a `Controller` which matches the command keyword (defined to be the first space-delimited word in the command).
2. The matched `Controller` selected will process the commands accordingly.
3. The InputHandler also maps aliased commands back to their original command keyword.
3. If no Controllers were matched, the console would display an error, to indicate an invalid command.

<!--- @@author A0093907W -->

### Controller component

<img src="images/Controller.png"><br>

The Controllers are responsible for most of the back-end logic responsible for processing the user's input. They take in the full input command, parse, process, and construct the response messages which are handed over to the Renderer to be rendered on the View.

**API** : [`Controller.java`](../src/main/java/seedu/todo/logic/Logic.java)

1. `Controller`s have a `process()` method which processes the command passed in by `InputHandler`.
2. The command execution can affect the `Model` (e.g. adding a person), raise events, and/or have other auxilliary effects like updating the config or modifying the UI directly.
3. After doing the required processing, the `Controller` calls the `Renderer` concern with appropriate parameters to be rendered on the user window. This is regardless of whether the command was successful (if not, then an error message or disambiguation prompt is rendered).

#### Controller concerns

Controller concerns are intended to contain helper methods to be shared across Controllers, in the spirit of code reuse. These are methods that are not generic enough to be considered utility functions (in `commons.utils`), but are at the same time not specific to a single Controller.

A brief description of each concern:

* **`CalendarItemFilter`** extracts out the parsing and filtering logic that is used by `ListController`, `ClearController` and to a small extent, `FindController`. These controllers depend on being able to filter out  CalendarItems before doing some processing on it. Extracting this out into a concern allows us to maintain a consistent filtering syntax for the user.
* **`Disambiguator`** contains the disambiguation helper methods to be used by Controllers which rely heavily on CalendarItemFilter. Since the token parsing is extracted out into a common concern, so should the code for populating disambiguation fields. 
* **`DateParser`** extracts out the parsing methods for single and paired dates. Virtually all Controllers need some support for converting a natural date input to a LocalDateTime object.
* **`Renderer`** contains the bulk of the code required for renderering a success or failure message, as well as disambiguation prompts. We want disambiguation prompts from all Controllers to be more or less consistent in their wording, hence it makes sense to extract this out allow each Controller to provide a more detailed explanation that will be rendered together with the generic message.
* **`Tokenizer`** contains the heavy logic that parses an input into its component token keys and values, while respecting the presence of quotes. All but the simplest of Controllers need to use this for parsing user input. Each Controller defines its own tokenDefinitions which the `Tokenizer` uses to parse the raw user input.

### Model component

**API** : [`CalendarItem.java`](../src/main/java/seedu/todo/models/CalendarItem.java)

A Model represents a single database record that is part of the persistent state of the TodoList app.

`CalendarItem`
* is subclassed by two record types, namely `Event` and `Task`
* Both subclasses contain setters and getters to be used to manipulate records
* Both subclasses implement dynamic predicate constructors to be chained together for use in a `.where()` query
* Has **NO** support for dirty records. In the spirit of Java's LBYL (and against my personal preferences...), all Controllers doing database operations are expected to validate parameters before updating a record. Once a record field is changed, if a validation fails, the only way to rollback the change is by reloading from disk or calling `undo`.

`TodoListDB`
* is a class that holds the entire persistent database for the TodoList app
* is a singleton class. For obvious reasons, the TodoList app should not be working with multiple DB instances simultaneously
* is recursively serialized to disk - hence object-to-object dynamic references should not be expected to survive serialization/deserialization 

### Storage component

<img src="images/Storage.png"><br>

**API** : [`Storage.java`](../src/main/java/seedu/todo/storage/Storage.java)

The `Storage` module should be considered to be a black box which provides read/write functionality and a few bonus features to the TodoListDB. This can be compared to a MySQL database implementation - no one needs to know how this is implemented, and in actual fact our implementation does little more than wrap around a serializer / deserializer in order to provide undo/redo functionality.

The `Storage` component,
* holds the logic for saving and loading the TodoListDB from disk
* maintains the required information to undo/redo the state of the TodoListDB in steps. One step represents the changes made in a single atomic transaction
* will discard all redo information the moment a new operation (i.e. not `redo`) is committed

*Some notes on the `JsonStorage` implementation of `Storage`*:
* The undo/redo information is stored using a stack of memory-efficient diffs containing the required patches to the data. When we undo, we construct a diff in the opposite direction so that we can redo.
* Average case time complexity for an undo/redo operation is constant with undo/redo history, linear with DB size.
* The space complexity of the undo/redo operation is constant with the DB size (this is the reason we are able to support up to 1000 undo/redos even though Jim likely isn't that much of a keyboard warrior).
<!--- @@author -->


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

Certain properties of the application can be controlled (e.g app title, database file path) through the configuration file 
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
  
2. **Non-GUI Tests** - These are tests not involving the GUI. They are _unit tests_ targeting the lowest level methods/classes.
   * e.g. `seedu.todo.commons.DateUtilTest` and `seedu.todo.models.CalendarItemTests`

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

A project often depends on third-party libraries, such as [Jackson](http://wiki.fasterxml.com/JacksonHome) for JSON parsing. Managing these _dependencies_ can be automated using Gradle. For example, Gradle can download the dependencies automatically, which is better than these alternatives.

  1. Include those libraries in the repo (this bloats the repo size)<br>
  2. Require developers to download those libraries manually (this creates extra work for developers)<br>

## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`

Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | new user | see usage instructions | refer to instructions when I forget how to use the App
`* * *` | user | add a new task/event | remind myself on the incomplete tasks and upcoming events
`* * *` | user | find items by name | search for tasks/events without looking through the entire list
`* * *` | user | list all items | list all the tasks/events that are created
`* * *` | user | delete an item | remove tasks/events that I no longer need
`* * *` | user | update an item | update tasks/events that need to be updated
`* * *` | user | exit | save and quit the application
`* * ` | user | mark a task as complete | keep track of what I have already completed
`* * ` | user | mark a task as incomplete | reset marking of task as complete
`* * ` | user | add tag to item | organise my tasks/events
`* * ` | user | untag from item | organise my tasks/events
`* * ` | user | undo | undo the previous command
`* * ` | user | redo | redo the previous undo
`* * ` | user | clear the list | quickly clear all my items
`* ` | user | change the app title | customize the application
`* ` | user | change the database file path | store the database in a different directory such as Dropbox
`* ` | advanced user | add alias | enter commands more quickly
`* ` | advanced user | unalias | remove aliases associated with commands
`* ` | advanced user | view aliases | view all aliases currently set
`* ` | advanced user | use keyboard arrows to scroll through command history | perform previous commands without re-typing the entire command

## Appendix B : Use Cases

(For all use cases below, the **System** is the `Application` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: UC01 - Usage Instructions
**MSS**

1. User wants to view all the available commands in the application.
2. Application shows a list of available commands and instructions for all commands. 
Use Case ends

#### Use case : UC02 - Add task

**MSS**

1. User requests to add new tasks with its details.
2. Application adds the task with specified details.
3. Application displays successful message.
Use case ends.  

**Extensions**  

1a. User specifies to add without any date.

> 1a1. User specifies an invalid command syntax.  
Application shows an error message.  
Use case ends.

1b. User specifies a deadline.

> 1b1. User specifies an invalid date deadline format.  
Application shows an error message.  
Use case ends.


<!--@@author A0139922Y -->
#### Use case : UC03 - Add Event

**MSS**

1. User requests to add new events with its details.
2. Application adds the events with specified details.  
3. Application displays successful message.  
Use case ends.  

**Extensions**  

1a. User specifies a start date and end date.

> 1a1. User specifies an invalid date format.  
Application shows an error message.  
Use case ends.  

1b. User specifies a deadline.

> 1b1. User specifies an invalid date deadline format.  
Application shows an error message.
Use case ends.
<!-- @author -->

<!-- @author A0093907W -->
#### Use case : UC04 - Find by keyword

**MSS**

1. User requests to find records with specified keyword(s)
2. Application shows the list of records with the specified keyword(s)
Use case ends.

**Extensions**  

2a. The list is empty.
> 2a1. Application shows an error message.  
Use case ends.
<!-- @@author -->

#### Use case : UC05 - List all tasks and events

**MSS**

1. User requests to list all the tasks and events.
2. Application shows the list of tasks and events with respective details.    
Use case ends.  

**Extensions**  

2a. The list is empty.

> 2a1. Application shows error message.  
Use case ends.

<!-- @@author A0139922Y -->

#### Use case : UC06 - List by date

**MSS**

1. User requests to list all the tasks and events by date.
2. Application shows the list of tasks and events by the date with respective details.  
Use case ends.  

**Extensions**  

2a. The list is empty.

> 2a1. Application shows error message.  
Use case ends.

2b. User did not provide any date.
> Use case ends.

2c. User provides a single date.
> 2c1. User specifies an invalid date format.  
> Application shows error message.  
> Use case ends.

2d. User provide a start date and end date.
> 2c1. User specifies invalid date format for either start or end date.  
> Application shows error message.  
Use case ends.

#### Use case : UC07 - List by status

**MSS**

1. User requests to list all the tasks and events by status.
2. Application shows the list of tasks or events by status with respective details.  
Use case ends.  

**Extensions**  

2a. The list is empty.

> 2a1. Application shows error message.  
Use case ends.

2b. User specifies status
>2b1. User specifies an invalid task/event status.  
Use case ends.

#### Use case: UC08 - Delete task/event

**MSS**

1. Application shows a list of tasks and events.
2. User requests to delete a specific task or event in the list by its respective index.
3. Application deletes the task or event.  
4. Application shows a updated list of task and events.  
Use case ends.  

**Extensions**  

2a. The list is empty.  
> Use case ends.

3a. The given index is invalid.
> 3a1. Application shows an error message.  
Use case ends.

#### Use case: UC10 - Update task

**MSS**

1. Application shows a list of tasks and events.
2. User requests to update a specific task in the list by respective index.
3. Application edits the task.  
4. Application shows a updated list of tasks and events.  
Use case ends.  

**Extensions**  

2a. The list is empty.  
> Use case ends.

3a. The given index is invalid.
> 3a1. Application shows an error message.  
Use case ends.

3b. The given details are invalid.
> 3b1. User specifies an invalid date format.  
> Application shows an error message.  
Use case ends.

> 3b2. User specifies more than one date.  
> Application shows an error message.  
Use case ends.

#### Use case: UC11 - Update Events

**MSS**

1. Application shows a list of tasks and events.
2. User requests to update a specific event in the list by respective index.
3. Application edits the event.  
4. Application shows a updated list of tasks and events.  
Use case ends.  

**Extensions**  

2a. The list is empty.  
> Use case ends.

3a. The given index is invalid.
> 3a1. Application shows an error message.  
Use case ends.

3b. The given details are invalid.
> 3b1. User specifies an invalid date format.  
> Application shows an error message.  
Use case ends.

<!-- @@author-->

<!-- @@author A0093907W -->

#### Use case : UC12 - Undo command

**MSS**  

1. User requests to undo a specified number of commands which defaults to 1.
2. Application undoes this number of commands.
Use case ends.

**Extensions**  

1a. The given number exceeds the total number of possible undo states.
> Application shows an error message.  
Use case ends.

#### Use case : UC13 - Redo command

**MSS**  

1. User requests to redo a specified number of commands which defaults to 1.
2. Application redoes this number of commands.  
Use case ends.

**Extensions**  

1a. The given number exceeds the total number of possible redo states.
> Application shows an error message.
Use case ends.

<!-- @@author -->

<!-- @@author A0139922Y -->

#### Use case: UC14 - Complete task

**MSS**

1. Application shows a list of tasks and events.
2. User requests to complete a specific task in the list by respective index.
3. Application completes the task. 
4. Application shows a updated list of tasks and events.  
Use case ends.  

**Extensions**  

2a. The list is empty.
> Use case ends.

2a. The given index is invalid.

> 2a1. The given index is out of range.  
> Application shows an error message.  
Use case ends. 

>2a2. The given index belongs to an event.  
> Application shows an error message.  
Use case ends.

2b. Index is not specified.

> 2b1. Application shows an error message.  
Use case ends.

#### Use case: UC15 - Uncomplete task

**MSS**

1. User requests a list of completed tasks or find completed task with keyword.
2. Application shows a list of completed tasks.
3. User requests to uncomplete a task by respective index.
4. Application uncompletes the task.
5. Application shows the updated list of tasks and events.  
Use case ends.  

**Extensions**  

2a. The list is empty.
> Use case ends.

3a. The given index is invalid.

> 3a1. The given index is out of range.  
> Application shows an error message.  
Use case ends. 

3b. Index is not specified.

> 3b1. Application shows an error message.  
Use case ends.

#### Use case: UC16 - Add tag to a task/event

**MSS**

1. Application shows a list of tasks and events.
2. User requests to tag a specific task/event in the list by respective index.
3. Application adds the tag and associated it with the task/event.
4. Application shows a updated list of tasks and events.  
Use case ends.  

**Extensions**  

2a. The list is empty.
> Use case ends

2a. The given index is invalid.

> 2a1. The given index is out of range.  
> Application shows an error message.  
Use case ends. 

2b. Index is not specified.

> 2b1. Application shows an error message.  
Use case ends.

2c. Invalid tag name
> 2c1. Tag name is not specified  
> Application shows an error message.  
Use case ends.

> 2c2. Tag name specified is already associated to the task/event.  
> Application shows an error message.  
Use case ends.

2d. Tag list size is full
> 2d1. Application shows an error message.  
Use case ends.

#### Use case: UC17 - Untag tag from a task/event

**MSS**

1. Application shows a list of tasks and events.
2. User requests to untag the tag of a specific task/event in the list by respective index.
3. Application deletes the tag that is associated to the task/event.
4. Application shows a updated list of tasks and events.  
Use case ends.

**Extensions**  

2a. The list is empty.
> Use case ends

2a. The given index is invalid.

> 2a1. The given index is out of range.  
> Application shows an error message.  
Use case ends. 

2b. Index is not specified.

> 2b1. Application shows an error message.  
Use case ends.

2c. Invalid tag name
> 2c1. Tag name is not specified.
> Application shows an error message.  
Use case ends.

>2c2. Tag name specified does not belong to the task/event.  
> Application shows and error message.  
Use case ends.

<!--@@author -->

<!-- @@author A0093907W -->
#### Use case : UC18 - Config

**MSS**

1. User requests to set a config variable.
2. Application sets the config variable.
Use case ends.

**Extensions**

1a. The config variable key does not exist.
> 1a1. Application shows an error message.
Use case ends.

1b. The config variable value is invalid.
> 1b1. Application shows an error message.
Use case ends.
<!-- @@author -->

<!-- @@author A0139812A -->
#### Use case : UC19 - Add alias

**MSS**

1. User requests to set an alias mapping.
2. Application commits the alias mapping.  
Use case ends.

**Extensions**

1a. The specified alias key is already set.
> 1a1. Application shows an error message.  
Use case ends.

1b. The command syntax is invalid.
>1b1. Application shows an error message.  
Use case ends.

#### Use Case: UC20 - Remove alias

**MSS**

1. User requests for a list of existing alias mappings.
2. Application shows the list of existing alias mappings.
3. User requests to remove an existing alias mapping.
4. Application removes the alias mapping.
Use case ends.

**Extensions**  

1a. The list is empty.  
> Use case ends.

3a. The specified alias key does not exist.
> 3a1. Application will show an error message.  
Use case ends.

3b. The commmand syntax is invalid.
> 3b1. Application will show an error message.  
Use case ends.
<!-- @@author -->

<!-- @@author A0093907W -->
#### Use case: UC21 - Clear all tasks and events

**MSS**

1. User requests to clear all tasks and events
2. Application clears all tasks and events
Use case ends

#### Use case: UC22 - Clear by type

**MSS**

1. User requests to clear by task/event.
2. Application clears all tasks/events.
Use case ends.

**Extensions**

1a. User specifies an invalid type.
> 1a1. Application shows an error message.
Use case ends.

#### Use case: UC23 - Clear by date

**MSS**

1. User requests to clear by date range.
2. Application clears all records in the date range.
Use case ends.

**Extensions**

1a. User enters unparseable invalid date.
> 1a1. Application prompts for disambiguation.
> 1a2. User enters parseable date.
> 1a3. Application clears all records in the date range.
Use case ends.

1b. There are no records in the date range.
> 1b1. Application shows error message.
Use case ends.

#### Use case: UC24 - Clear by tags

**MSS**

1. User requests to clear by tag.
2. Application clears all records with the specified tag.
Use case ends.

**Extensions**

2a. There are no records matching the tag.
> 2a1. Application shows error message.
Use case ends.

<!-- @@author -->

## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 tasks and events.
3. Should come with automated unit tests and open source code.
4. Should favor DOS style commands over Unix-style commands.
<!-- @@author A0139922Y -->
5. Should be able to respond to any command within 3 seconds.
6. User-friendly interface
<!-- @@author -->

## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

## Appendix E : Product Survey

<!-- @@author A0139922Y -->
#### Todoist: Strength and Weaknesses

> Todoist is a task management application, with access to over ten different platforms and the ability to collaborate on tasks. The application is straightforward and quick in providing the user with easy access to the important details of the to-do item. It also encourages people to keep up the habit of clearing existing tasks with its Karma Mode. 

> Moreover, its ease of use and its integration with other services are its true strength. It can integrate with the latest technologies such as Trello and Amazon Echo to keep every to-do item in a single place.

> However, one flaw with Todoist is that it does not possess any capabilities of having subproject hierarchy. Hence, it would make complex projects' task to be split among the team in an orderly fashion.
<!-- @@author -->
