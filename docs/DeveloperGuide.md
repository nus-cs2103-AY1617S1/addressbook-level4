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

**API** : [`InputHandler.java`](../src/main/java/seedu/todo/ui/components/InputHandler.java)

1. The console input field will pass the user commands to the relevant controller, and according to the `Controller` 
   method `inputConfidence()`, a `Controller` will be best selected and returned based on the input.
2. The `Controller` selected will process the commands accordingly.

### Controller component

<img src="images/LogicClassDiagram.png" width="800"><br>

**API** : [`Controller.java`](../src/main/java/seedu/todo/logic/Logic.java)

1. `Controller`s have a `process()` method which processes the command passed in by `InputHandler`.
2. The command execution can affect the `Model` (e.g. adding a person) and/or raise events.
3. After invoking `process()`, a new `View` will be created and loaded to `MainWindow` whether it was successful or an exception occured.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")`
 API call.<br>
<img src="images/DeletePersonSdForLogic.png" width="800"><br>

### Model component

<img src="images/ModelClassDiagram.png" width="800"><br>

**API** : [`Model.java`](../src/main/java/seedu/todo/model/Model.java)

The `Model`,
* stores a `UserPref` object that represents the user's preferences.
* stores the Address Book data.
* exposes a `UnmodifiableObservableList<ReadOnlyPerson>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.

### Storage component

<img src="images/StorageClassDiagram.png" width="800"><br>

**API** : [`Storage.java`](../src/main/java/seedu/todo/storage/Storage.java)

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
`* * *` | new user | see usage instructions | refer to instructions when I forget how to use the App
`* * *` | user | add a new task/event |
`* * *` | user | delete an item | remove tasks/events that I no longer need
`* * *` | user | edit an item | edit tasks/events that need to be updated
`* * *` | user | list all items | list all the tasks/events that are created
`* * *` | user | find items by name | search for tasks/events without looking through the entire list
`* * *` | user | exit | save and quit the application
`* * ` | user | mark a task as complete | keep track of what I have already completed
`* * ` | user | mark a task as incomplete | reset marking of task as complete
`* * ` | user | add tag to item | organise my tasks/events
`* * ` | user | untag from item | organise my tasks/events
`* * ` | user | delete a tag | organise my tasks/events
`* * ` | user | undo | undo the previous command
`* * ` | user | redo | redo the previous undo
`* * ` | user | clear the list | quickly clear all my items
`* ` | advanced user | add alias | enter commands more quickly
`* ` | advanced user | unalias | remove aliases associated with commands
`* ` | advanced user | view aliases | view all aliases currently set
`* ` | advanced user | use keyboard arrows to scroll through command history | perform previous commands without re-typing the entire command

## Appendix B : Use Cases

(For all use cases below, the **System** is the `Application` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: Usage Instructions
**MSS**

1. User want to view all the available commands in the application.
2. Application show a list of available commands and instructions for all commands.
    Use Case ends

#### Use case : Add task

**MSS**

1. User request to add new tasks with its details.
2. Application add the task with specified details.  
3. Application display successful message.
Use case ends.  

**Extensions**  
1a. User specify with start date and end date.

> 1a1. User specify invalid date format.
  Application shows an error message.
  Use case ends.
  
1b. User specify with deadline.  

> 1b1. User specify invalid date deadline format.
   Application shows an error message.
   Use case ends.

#### Use case : Find tasks with specific keyword

**MSS**

1. User request to find task with specific keyword.
2. Application show the list of task that its names & tag contain the keyword.
Use case ends.  

**Extensions**  
2a. The list is empty.

> 2a1. Application show an error message.
   Use case ends.

#### Use case : List all task

**MSS**

1. User request to list all the tasks.
2. Application show the list of task with respective details.  
Use case ends.  

**Extensions**  
2a. The list is empty.

> 2a1. Application show error message.
   Use case ends.
   
#### Use case: Delete task

**MSS**

1. User requests a list of tasks or find task with keyword.
2. Application shows a list of tasks.
3. User requests to delete a specific task in the list.
4. Application request confirmation from the user.
5. User confirm the confirmation.
4. Application deletes the task. <br>
Use case ends.  

**Extensions**  

2a. The list is empty.

> Use case ends.

3a. The given index is invalid.

> 3a1. Application shows an error message.  
  Use case ends.

#### Use case: edit task

**MSS**

1. User requests a list of tasks or find task with keyword.
2. Application shows a list of tasks.
3. User requests to edit a specific task in the list.
4. Application edit the task. <br>
Use case ends.  

**Extensions**  

2a. The list is empty.

> Use case ends.

3a. The given index is invalid.

> 3a1. Application shows an error message.  
  Use case ends.

#### Use case : Undo command

**MSS**
1. User request to undo command by a specific number.
2. Application request confirmation from the user.
3. User confirm the confirmation.
2. Application undo the command repeatedly based on the given number.
Use case ends.

**Extensions**
1a. The given number exceed the total number of tasks.
> Application will show an error message.
Use case ends.

#### Use case : Redo command

**MSS**
1. User request to redo command by a specific number.
2. Application request confirmation from the user.
3. User confirm the confirmation.
4. Application redo the command repeatedly based on the given number.
Use case ends.

**Extensions**
1a. The given number exceed the total number of undo commands.
> Application will show an error message.
Use case ends.

#### Use case: Complete task

**MSS**

1. User requests a list of tasks or find task with keyword.
2. Application shows a list of tasks.
3. User requests to complete a task.
4. Application complete the task and remove it from the current task list and add into the completed task list.<br>
Use case ends.  

**Extensions**  

2a. The list is empty.

> Use case ends.

3a. The given index is invalid.

> 3a1. Application shows an error message.  
  Use case ends.
  
#### Use case: Uncomplete task

**MSS**

1. User requests a list of completed tasks or find compelted task with keyword.
2. Application shows a list of completed tasks.
3.  User requests to uncomplete a task.
4. Application request confirmation from the user.
5. User confirm the confirmation.
6. Application uncomplete the task and remove it from the completed task list and add it back to the current task list. <br>
Use case ends.  

**Extensions**  

2a. The list is empty.

> Use case ends.

3a. The given index is invalid.

> 3a1. Application shows an error message.
  Use case ends.

#### Use case: Remove tag from a task

**MSS**

1. User requests a list of tasks or find task with keyword.
2. Application shows a list of tasks.
3. User requests to remove the tag of a specific task in the list.
4. Application request confirmation from the user.
5. User confirm the confirmation.
6. Application deletes the tag that is associated to the task. <br>
Use case ends.  

**Extensions**  

2a. The list is empty.

> Use case ends

3a. The given index is invalid.

> 3a1. Application shows an error message  
  Use case ends.

#### Use case: Sorting of tasks

**MSS**

1. User request to sort the list of tasks based on either start date, end date, deadline or alphabetical order.
2. Application sort the tasks according to the user preference. <br>
Use case ends.

#### Use case : Alias

**MSS**

1. User request to set alias for specific command.
2. Application set the Alias for the command.<br>
Use case ends.

**Extensions**

1a. The given alias is already set.
> 1a1. Application will show an error message.
Use case ends.

1b. The given command is invalid.
>1b1. Application will show an error message.
Use case ends.

#### Use Case: Remove Alias

**MSS**

1. User request for a list of alias that is currently set.
2. Application show a list of alias and its respective command.
3. User request to remove alias from the specific command.
4. Application remove the alias of the specific command.<br>
Use case ends.

**Extensions**
1a. The list is empty.
> Use case ends.

3a. The given index of the alias is invalid.
> 3a1. Application will show an error message.
Use case ends.

3b. The given command is invalid.
> 3b1. Application will show an error message.
Use case ends.

#### Use case: Change theme of the Application

**MSS**

1. User requests to change the theme of the application.
2. Application shows a list of available themes.
3. User request the specific theme in the list.
4. Application change the theme. <br>
Use case ends.  

**Extensions**  

3a. The given index is invalid.

> 3a1. Application shows an error message.  
  Use case ends.


## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 persons.
3. Should come with automated unit tests and open source code.
4. Should favor DOS style commands over Unix-style commands.
5. User-friendly interface

{More to be added}

## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

**##### Private contact detail**

> A contact detail that is not meant to be shared with others

## Appendix E : Product Survey

**{TODO: Add a summary of competing products}**
