[TOC]

## Introduction

Welcome to the Uncle Jim's Discount To-do App!

This guide will teach you how to set up your development environment, explain the basic architecture of the application, teach you how to perform some common development tasks, as well as provide contact information for the times when you require additional help.

### Tooling

This project uses 

- **Git** - Version control 
- **[Eclipse][eclipse]** - IDE 
- **Gradle** - Build automation 
- **[Travis][travis], [Coveralls][coveralls] and [Codacy][codacy]** - Continuous integration and quality control
- **[GitHub][repo]** - Source code hosting and issue tracking  

## Setting up

### Prerequisites

1. **Git client**
    If you are using Linux, you should already have one installed on your command line. If you are using Windows or OS X you can use [SourceTree][sourcetree] if you are more comfortable with using a GUI.
2. [**JDK 1.8.0_60**][jdk] or later
    Please use Oracle's jdk because it comes with JavaFX, which is needed for developing the application's UI.
3. **Eclipse** IDE
4. **e(fx)clipse** plugin for Eclipse
    Perform steps 2 onwards as listed in [this page](http://www.eclipse.org/efxclipse/install.html#for-the-ambitious){: .print-url } to install the plugin.
5. **Buildship Gradle Integration** plugin from the Eclipse Marketplace
    You can find Eclipse Marketplace from Eclipse's `Help` toolbar.

#### Importing the project into Eclipse

0. Fork this repository, and clone the fork to your computer with Git.
1. Open Eclipse
!!! note
    
    Ensure that you have installed the **e(fx)clipse** and **buildship** plugins as listed in the prerequisites above.

2. Click `File` > `Import`
3. Click `Gradle` > `Gradle Project` > `Next` > `Next`
4. Click `Browse`, then locate the project's directory
5. Click `Finish`

!!! note
    
    * If you are asked whether to 'keep' or 'overwrite' config files, choose to 'keep'.
    * Depending on your connection speed and server load, this step may take up to 30 minutes to finish
      (This is because Gradle downloads library files from servers during the project set up process)
    * If Eclipse has changed any settings files during the import process, you can discard those changes.
  
### Contributing 

We use the [feature branch git workflow][workflow]. Thus when you are working on a task, please remember to assign the relevant issue to yourself [on the issue tracker][issues] and branch off from `master`. When the task is completed, do remember to push the branch to GitHub and [create a new pull request][pr] so that the integrator can review the code. For large features that impact multiple parts of the code it is best to open a new issue on the issue tracker so that the design of the code can be discussed first.

[Test driven development][tdd] is encouraged but not required. If possible, all of your incoming code should have 100% accompanying tests - Coveralls will fail any incoming pull request which causes coverage to fall.

### Coding Style

We use the Java coding standard found at <https://oss-generic.github.io/process/codingstandards/coding-standards-java.html>.
 

## Design

### Architecture

Now let us explore the architecture of Uncle Jim's Discount To-do App to help you understand how it works.

<img src="diagrams/Architecture Diagram.png" />

<figcaption>Simplistic overview of the application</figcaption>

The architecture diagram above explains the high-level design of the application. Here is a quick overview of each component:

* `Main` has only one class called [`MainApp`](../src/main/java/seedu/todo/MainApp.java). It is responsible for:

    * Bootstrapping the application at app launch by initializing the components in the correct sequence and injecting the dependencies needed for each component.
    * Shutting down the components and invoke cleanup method where necessary during shut down.

* [**`Commons`**](#common-modules) represents a collection of modules used by multiple other components.
* [**`UI`**](#ui-component): The user facing elements of tha App, representing the view layer. 
* [**`Logic`**](#logic-component): The parser and command executer, representing the controller 
* [**`Model`**](#model-component): Data manipulation and storage, representing the model and data layer 

The UI, Logic and Model components each define their API in an `interface` with the same name and is bootstrapped at launch by `MainApp`.

For example, the `Logic` component (see the class diagram given below) defines its API in the `Logic.java` interface and exposes its functionality using the `TodoLogic.java` class.

<img src="diagrams/Logic Component.png" />

<figcaption>Example of a Logic class diagram exposing its API to other components</figcaption>

<img src="diagrams/Sequence Diagram.png" />

<figcaption>The interaction of major components in the application through a sequence diagram</figcaption>


The sequence diagram above shows how the components interact with each other when the user issues a generic command.

The diagram below shows how the `EventsCenter` reacts to a `help` command event, where the UI does not know or contain any business side logic.

<img src="diagrams/Events Center Diagram.png" />

<figcaption>A sequence diagram showing how EventsCenter work</figcaption>

!!! note "Event Driven Design" 

    Note how the event is propagated through the `EventsCenter` to `UI` without `Model` having
    to be coupled to either of them. This is an example of how this Event Driven approach helps us reduce direct
    coupling between components.

The sections below will provide you with more details for each component.

### UI component

<img src="diagrams/Ui Component.png" />

<figcaption>The relation between the UI subcomponents</figcaption>

The UI component handles the interaction between the user and application. In particular, the UI is responsible for passing the textual command input from the user to the `Logic` for execution, and displaying the outcome of the execution to the user via the GUI.

<img src="diagrams/Ui Image.png" />

<figcaption>Visual identification of view elements in the UI</figcaption>

**API** : [`Ui.java`](../src/main/java/seedu/todo/ui/Ui.java)

The UI mainly consists of a `MainWindow`, as shown in the diagram above. This is where most of the interactions between the user and the application happen here. The `MainWindow` contains several major view elements that are discussed in greater detail below:

#### Command Line Interface
The UI aims to imitate the Command Line Interface (CLI) closely by accepting textual commands from users, and displaying textual feedback back to the users. The CLI consists of:

- `CommandInputView` - a text box for users to key in their commands
- `CommandFeedbackView` - a single line text that provides a response to their commands
- `CommandErrorView` - a detailed breakdown of any erroneous commands presented with a table

These view classes are represented by the `CommandXView` class in the UML diagram above. 

The `CommandController` class is introduced to link the three classes together, so they can work and communicate with each other. The `CommandController`:

1. Obtains a user-supplied command string from the `CommandInputView`
2. Submits the command string to `Logic` for execution
3. Receives a `CommandResult` from `Logic` after the execution
4. Displays the execution outcome via the `CommandResult` to the `CommandFeedbackView` and `CommandErrorView`

#### To-do List Display
A to-do list provides a richer representation of the tasks than the CLI to the users. The To-do List Display consists of: 

- `TodoListView` - a [`ListView`](http://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/ListView.html) that displays a list of `TaskCard`.
- `TaskCard` - an item in the `TodoListView` that displays details of a specific task.

Specifically, the `TodoListView` attaches an `ObservableList` of `ImmutableTask` from the `Model` and listens to any changes that are made to the `ObservableList`. If there are any modifications made, the `TaskCard` and `TodoListView` are updated automatically.

#### Additional Information
All these view classes, including the `MainWindow`, inherit from the abstract `UiPart` class. They can be loaded using the utility class `UiPartLoader`.

The UI component uses [JavaFX](http://docs.oracle.com/javase/8/javafx/get-started-tutorial/jfx-overview.htm#JFXST784) UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`CommandInputView`](../src/main/java/seedu/todo/ui/view/CommandInputView.java) is specified in [`CommandInputView.fxml`](../src/main/resources/view/CommandInputView.fxml)

Other than through `CommandResult` and `ObservableList`, you may also invoke changes to the GUI outside the scope of UI components by raising an event. `UiManager` will then call specific view elements to update the GUI accordingly. For example, you may show the `HelpView` by raising a `ShowHelpPanel` via the `EventsCentre`.

### Logic component

<img src="diagrams/Logic Component.png" />

<figcaption>The relation between the Logic subcomponents</figcaption>
<img src="diagrams/Logic Component 1.png" />

<figcaption>Continuation of the relation between the Logic subcomponents</figcaption>

**API** : [`Logic.java`](../src/main/java/seedu/todo/logic/Logic.java)

The logic component is the glue sitting between the UI and the data model. It consists of three separate subcomponents, each of which also defines their own API using interfaces or abstract classes - 

- `Parser` - turns user input into command and arguments 
- `Dispatcher` - maps parser results to commands 
- `Command` - validates arguments and execute command

When the logic component is instantiated each of these subcomponents is injected via dependency injection. This allows them to be tested more easily. 

The flow of a command being executed is -

1. `Logic` parse the user input into a `ParseResult` object
2. The `ParseResult` is sent to the dispatcher which instantates a new `Command` object representing
the command the user called
3. `Logic` binds the model and arguments to the `Command` object and executes it 
4. The command execution can affect the `Model` (e.g. adding a person), and/or raise events.
 
<img src="diagrams/Logic Sequence Diagram.png" />

<figcaption>The process of deleting a person within the Logic component</figcaption>

Given above is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")` API call. See [the implementation section](#logic) below for the implementation details of the logic component.  

### Model component

<img src="diagrams/Model Component.png" />

<figcaption>The relation between the Model subcomponents</figcaption>

**API** : [`Model.java`](../src/main/java/seedu/todo/model/Model.java)

The model component represents the application's state and data layer. It is implemented by `TodoModel`, which is a composite of of the individual data models for the application, as well as higher level information about the state of the application itself, such as the current view and the undo/redo stack. 

- `TodoModel` - represents the todolist tasks
- `UserPrefs` - represents saved user preferences 

Each individual data model handles their own CRUD operations, with the `Model` acting as a facade to present a simplified and uniform interface for external components to work with. Each of the data model holds a `Storage` object that is initalized by the `Model` and injected into them. The storage interfaces exposes a simple interface for reading and writing to the appropriate storage mechanism (read more below).

To avoid tight coupling with the command classes, the model exposes only a small set of generic functions. The UI component binds to the  the model through the `getObservableList` function which returns an `UnmodifiableObseravbleList` object that the UI can bind to.

The model ensure safety by exposing as much of its internal state as possible as immutable objects using interfaces such as `ImmutableTask`.

<img src="diagrams/Storage Component.png" />

<figcaption>The relation between the Storage subcomponents</figcaption>

**API** : [`Storage.java`](../src/main/java/seedu/todo/storage/Storage.java)

The storage component represents the persistence layer of the data. It is implemented by `TodoListStorage` which holds and contains `ImmutableTodoList`. Similarly, `JsonUserPrefsStorage` stores the user preferences. 

Both classes implement `FixedStorage`, which exposes methods to read and save data from storage. Users can choose to move their storage file, hence `MovableStorage` is exposed to allow them to do so. User preferences cannot be exported.

The file extension currently chosen is XML. Hence, classes have been written to serialize and parse the data to and fro XML.

### Common modules

Modules used by multiple components are in the `seedu.todo.commons` package.

#### Core

The core module contains many important classes used throughout the application.

* `UnmodifiableObservableList` :  Used by the UI component to be listen to changes to the data through the Observer pattern.
* `Events` : Used by components to marshal information around and inform other components that things have happened. (i.e. a form of _Event Driven_ design)
* `LogsCenter` : Used by many classes to write log messages to the App's log file.

#### Util

The util module contains many different helper methods and classes used throughout the application. The things that can, and should be reused can be found in here.

* `TimeUtil` : Used by Ui to display time that is readable and user friendly.
* `XmlUtil`: Used by storage to save and read .xml files.

#### Exceptions

The exceptions module contains all common exceptions that will be used and thrown throughout the application.

* `ValidationException` : Used by many classes to signal that the command or model parsed is not valid and something should be done.

## Implementation

### Logic

See the [Logic component architecture](#logic-component) section for the high level overview of the Logic component.  

#### Parser 

The `TodoParser` subcomponent implements the `Parser` interface, which defines a single `parse` function that takes in the user input as a string and returns an object implementing the `ParseResult` interface. The implementing class for `TodoParser` can be found as an inner class inside `TodoParser`.

The parser tokenizes the user input by whitespace characters then splits it into three parts: 

- Command `string` - the first word of the user input
- Positional argument `string` - everything from the command to the first flag
- Named argument `Map<String, String>` - a map of flags to values 

For example, the command `add The Milk /d tomorrow 3pm /p` will produce 

``` yaml
command: "add"
positional: "The Milk" 
named: 
  d: "tomorrow 3pm"
  p: "" # Empty String
```

This is then passed on to the dispatcher. 
  
#### Dispatcher 

The `TodoDispatcher` subcomponent implements the `Dispatcher` interface, which defines a single `dispatch` function. The dispatch function simply tries to find the command that has matching name to the user input, instantiates and returns a new instance of the command.

#### Command

All commands implement the `BaseCommand` abstract class, which provides argument binding and validation. To implement a new command, you can use the following template 

```java
package seedu.todo.logic.commands;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.arguments.Argument;
import seedu.todo.logic.arguments.IntArgument;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.model.task.ImmutableTask;

public class YourCommand extends BaseCommand {
    // TODO: Define additional parameters here
    private Argument<Integer> index = new IntArgument("index").required();

    @Override
    protected Parameter[] getArguments() {
        // TODO: List all command argument objects inside this array
        return new Parameter[]{ index };
    }
    
    @Override
    public String getCommandName() {
        return ""; // TODO: Enter command name here
    }
    
    @Override
    public List<CommandSummary> getCommandSummary() {
        // TODO: Return a list of CommandSummary objects for help to display to the user
        return null;
    }

    @Override
    public void execute() throws IllegalValueException {
        // TOOD: Complete this command stub
    }
}
```

If you need to do additional argument validation, you can also override the `validateArgument` command, which is run after all arguments have been set.

#### Arguments 

Command arguments are defined using argument objects. Representing arguments as objects have several benefit - it makes them declarative, it allows argument parsing and validation code to be reused across multiple commands, and the fluent setter allows each individual property to be set indepently of each other. The argument object's main job is to convert the user input from string into the type which the command object can use, as well as contain information about the argument that the program can show to the user.

The generic type `T` represents the return type of the command argument. To implement a new argument type, extend the abstract base class `Argument`, then implement the `setValue` function. Remember to call the super class's `setValue` function so that the `required` argument check works. 

```java
package seedu.todo.logic.arguments;

import seedu.todo.commons.exceptions.IllegalValueException;

public class MyArgument extends Argument<T> {
    // TODO: Replace the generic type T here and below with a concrete type  

    public FlagArgument(String name) {
        super(name);
        this.value = false;
    }

    public FlagArgument(String name, T defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public void setValue(String input) throws IllegalValueException {
        // TODO: Implement the argument parsing logic. Do not remove the super call below
        super.setValue(input);
    }
}
```

### Model
See the [Model component architecture](#model-component) section for the high level overview of the Model and Storage components.

##### Task
A `Task` is a representation of a task or event in the todolist. This object implements `MutableTask` which allows us to edit the fields.

##### ImmutableTask
This interface is used frequently to expose fields of a `Task` to external components. It prevents external components from having access to the setters.

##### TodoList
This class represents the todolist inside the memory and implements the `TodoListModel`. This interface is internal to Model and represents only CRUD operations to the todolist.

##### TodoModel
This class represents the data layer of the application and implements the `Model` interface. The `TodoModel` handles any interaction with the application state that are not persisted, such as the view (sort and filtering), undo and redo commands.

##### ErrorBag
The `ErrorBag` is a wrapper around all the errors produced while processing a model. This class exposes the errors to the Ui to show the user understand what went wrong.

#### Storage

##### FixedStorage
This interface simply exposes the read and save methods for external usage in order to store and read data from the persistence layer.

##### TodoListStorage
The main class that is exposed to the Model. In addition from reading and saving, methods are exposed to enable user to switch where the storage file is saved and read.

##### Xml Classes
Classes prefixed with `Xml` are classes used to enable serialization of the Model. As the prefix suggests, the critical data is stored in the `.xml` file format and uses `JAXB` to read and save to the persistence layer.

### Logging

We are using the [`java.util.logging`][jul] package for logging. The `LogsCenter` class is used to manage the logging levels and logging destinations.

* The logging level can be controlled using the `logLevel` setting in the configuration file (See [Configuration](#configuration))
* The `Logger` for a class can be obtained using `LogsCenter.getLogger(Class)` which will log messages according to the specified logging level
* Currently log messages are output through: Console and to a log file.
* The logs rolls over at 5MB such that every log file is smaller than 5MB. Five log files are kept, after which the oldest will be deleted. 

To use the logger in your code, simply include 

``` java
private static final Logger logger = LogsCenter.getLogger(<YOUR CLASS>.class);
```

at the top of your class, and replace `<YOUR CLASS>` with the class the logger is used in.

#### Logging Levels

Level      | Used for
---------- | -------------------------------------------------
 `SEVERE`  | Critical problem detected which may possibly cause the termination of the application
 `WARNING` | Can continue, but with caution
 `INFO`    | Information showing the noteworthy actions by the App
 `FINE`    | Details that is not usually noteworthy but may be useful in debugging e.g. print the actual list instead of just its size

### Configuration

Certain properties of the application can be controlled (e.g App name, logging level) through the configuration file (default: `config.json`)


## Testing

Tests can be found in the `./src/test/java` folder.

### In Eclipse

* To run all tests, right-click on the `src/test/java` folder and choose
  `Run as` > `JUnit Test`
* To run a subset of tests, you can right-click on a test package, test class, or a test and choose to run as a JUnit test.

!!! note
    If you are not using a recent Eclipse version (Neon or later), enable assertions in JUnit tests
    as described [in this Stack Overflow question](http://stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option) (url: http://stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option).


### Using Gradle

See [UsingGradle.md](#appendix-f-using-gradle) for how to run tests using Gradle.

We have two types of tests:

1. **GUI Tests** - These are _System Tests_ that test the entire App by simulating user actions on the GUI. These are in the `guitests` package.

2. **Non-GUI Tests** - These are tests not involving the GUI. They include,
    1. **Unit tests** - targeting the lowest level methods/classes.  
      e.g. `seedu.todo.commons.UrlUtilTest`
    2. **Integration tests** - that are checking the integration of multiple code units (those code units are assumed to be working).  
      e.g. `seedu.todo.model.TodoModelTest`
    3. **Hybrids of unit and integration tests.** These test are checking multiple code units as well as how the are connected together.    
      e.g. `seedu.todo.logic.BaseCommandTest`

### Headless GUI Testing 

Thanks to the [TestFX](https://github.com/TestFX/TestFX) library we use,
 our GUI tests can be run in the _headless_ mode. In the headless mode, GUI tests do not show up on the screen. That means the developer can do other things on the Computer while the tests are running.
 
 See [UsingGradle.md](UsingGradle.md#running-tests) to learn how to run tests in headless mode.

## Dev Ops

### Build Automation

We use [Gradle][gradle] for build automation. Gradle handles project dependencies, build tasks and testing. If you have configured Eclipse by importing the project as shown in the [setting up](#setting-up) section Gradle should already be properly configured and can be executing from within Eclipse to build, test and package the project from the Run menu.

See the appendix [Using Gradle](#appendix-f-using-gradle) for all of the details and Gradle commands. 

### Continuous Integration

We use [Travis CI][travis] to perform Continuous Integration on our projects. See [UsingTravis.md](UsingTravis.md) for more details.

### Making a Release

Here are the steps to create a new release.

 1. Update the `Version` number in `MainApp.java`
 2. Generate a JAR file [using Gradle](UsingGradle.md#creating-the-jar-file).
 3. Tag the repo with the version number. e.g. `v0.1`
 4. [Crete a new release using GitHub](https://help.github.com/articles/creating-releases/) and upload the JAR file your created.
 5. Update `README` to show the new release 

### Managing Dependencies

Our project depends on third-party libraries. We use Gradle to automate our dependency management. 

To add a new dependency, look for instructions on the project's documentation for Gradle configuration required. For most projects it will look something like this
 
``` gradle
// https://mvnrepository.com/artifact/org.mockito/mockito-all
compile group: 'org.mockito', name: 'mockito-all', version: '2.0.2-beta'
```

This should be copied into the `dependencies` section of the `build.gradle` file. If you wish to you can split the version string out so that the shorter `compile` property can be used instead, but this is not strictly necessary 

```gradle
project.ext {
    // ... other variables 
    mockitoVersion = '8.40.11'
}

dependencies {
    // .. other dependencies
    compile "org.mockito:mockito-all:mockitoVersion"
}
```

After that rerun the Gradle `build` command and make sure the file has been edited properly, then right click on the project folder in Eclipse, select `Gradle` > `Refresh Gradle Project`. You should now be able to use the package in your code. Remember to inform all other team members to refresh their Gradle Project when the commit is merged back into master.

!!! warning "Add external dependencies with care"
    Be mindful of the impact external dependencies can have on project build time and maintenance costs. Adding an external package should always be discussed with the other project members in the issue tracker.

## Documentation 

Our documentation and user guides are written in [GitHub Flavor Markdown][gfm] with a number of extensions including tables, definition lists and warning blocks that help enable richer styling options for our documentation. These extensions are documented on the [Extensions page of the Python Markdown package][py-markdown], the package we use to help transform the Markdown into HTML. We use HTML because it allows greater flexibility in styling and is generally more user friendly than raw Markdown. To set up the script:

1. Make sure you have Python 3.5+ installed. Run `python3 --version` to check
2. Install the dependencies - `pip3 install markdown pygments beautifulsoup4` 
3. Run the script - `python3 docs/build/converter.py`

### Syntax Highlighting 

To get syntax highlighting for your code, use three backticks before and after your code without any additional indentation, and indicate the language on the opening backticks. This is also known as fenced code block. 

For example, 

``` java
public class YourCommand extends BaseCommand {
    // TODO: Define additional parameters here
    private Argument<Integer> index = new IntArgument("index").required();

    @Override
    protected Parameter[] getArguments() {
        // TODO: List all command argument objects inside this array
        return new Parameter[]{ index };
    }
}
```

is produced by 

    ``` java
    public class YourCommand extends BaseCommand {
        // TODO: Define additional parameters here
        private Argument<Integer> index = new IntArgument("index").required();

        @Override
        protected Parameter[] getArguments() {
            // TODO: List all command argument objects inside this array
            return new Parameter[]{ index };
        }
    }
    ```


### Admonition blocks 

To draw reader's attention to specific things of interest, use the admonition extension 

!!! warning
    This is an example of a admonition block 

The syntax for this the block above is 

    !!! warning
        This is an example of a admonition block 

`warning` is the style of the box, and also used by default as the title. To add a custom title, add the title after the style in quotation marks. 


    !!! warning "This is a block with a custom title"
        This is an example of a admonition block 

The following styles are available 

Style    | Color    | Used for 
-------- | -------- | --------------------------------------------------
note     | Blue     | Drawing reader's attention to specific points of interest 
warning  | Yellow   | Warning the reader about something that may be harmful 
danger   | Red      | A stronger warning to the reader about things they should not do 
example  | Green    | Showing the reader an example of something, like a command in the user guide 

### Captions 

To add a caption to a table, image or a piece of code, use the [`<figcaption>`][figcaption] HTML tag. 

```html 
<img src="..." alt="...">

<figcaption>
  The sequence of function calls resulting from the user input 
  <code>execute 1</code>
</figcaption>
```

The document processor will automatically wrap the `<figcaption>` and the preceding element in a `<figure>` element and automatically add a count to it, producing the following code:

```html 
<figure>
  <img src="..." alt="...">

  <figcaption>
    <strong>Figure 2.</strong>
    The sequence of function calls resulting from the user input 
    <code>execute 1</code>
  </figcaption>
</figure>
```

Note that Markdown is not processed inside HTML, so you must use HTML to write any additional inline markup you need inside the caption. 


[figcaption]: https://developer.mozilla.org/en/docs/Web/HTML/Element/figcaption


## Appendix A : User Stories

Priorities: High (must have) - ★★★ , Medium (nice to have) - ★★ ,  Low (unlikely to have) - ★


Priority | As a ...  | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
★★★      | new user  | see usage instructions | refer to instructions when I forget how to use the app
★★★      | user      | add a new task |
★★★      | user      | mark a task as complete | so I know which are the tasks are not complete.
★★★      | user      | delete a task | remove entries that I no longer need
★★★      | user      | edit a task | change or update details for the task
★★★      | user      | set a deadline for a task | track down deadlines of my tasks
★★★      | user      | set events with start and end dates | keep track of events that will happen
★★★      | user      | view tasks | see all the tasks I have
★★★      | user      | view incomplete tasks only | to know what are the tasks I have left to do.
★★★      | user with multiple computers | save the todo list file to a specific location | move the list to other computers
★★       | user with a lot tasks | add tags to my tasks | organize my tasks 
★★       | user      | set recurring tasks | do not need to repeatedly add tasks
★★       | user      | sort tasks by various parameters| organize my tasks and locate a task easily
★★       | user      | set reminders for a task | do not need to mentally track deadlines
★        | user      | know the number of tasks I have left | gauge how many tasks I have left to do.
★        | user      | be notified about upcoming deadlines without opening the app | so that I can receive timely reminders  


## Appendix B : Use Cases

(For all use cases below, the **System** is the `TodoApp` and the **Actor** is the `user`, unless specified otherwise)

### Adding an event

**MSS**

1. User types out an event with start time, end time and location
2. TodoApp adds event with specified fields and saves it to disk

Use case ends.

**Extensions**

1a. The task has no title

> 1a1. TodoApp shows an error message  
  Use case resumes at step 1 
  
1b. The task's date field is empty

> 1b1. TodoApp creates a task with no start and end date  
  Use case resumes at step 2

1c. The task has a start time later than end time

> 1c1. TodoApp assumes the dates are inverted    
  Use case resumes at step 2
 
1d. The event's timing overlaps with an existing event's timing 

> 1d1. TodoApp displays a warning to the user that he has another event at the same time  
  Use case resumes at step 2
  
### Adding a task with deadline

**MSS**

1. User enters a task while specifying a deadline for the task.
2. TodoApp creates new todo item with deadline specified and saves it to disk

Use case ends.

**Extensions**

1a. The task has no title

> 1a1. TodoApp shows an error message  
  Use case resumes at step 1 

1b. The task's date field is empty

> 1b1. TodoApp creates a task with no deadline  
  Use case ends

### Adding a recurring task

**MSS**

1. User enters a task with a recurring time period 
2. TodoApp creates a new recurring todo item with the specified time period 
3. At the start of the specified time period (eg. every week, month) TodoApp creates a copy of the original task for the user 

Use case ends.

**Extensions**


2a. The given recurring time period is invalid 

> 2a1. TodoApp shows an error message  
  Use case resumes at step 1

### Marking a task complete

**MSS**

1. User requests to see a list of uncompleted tasks.
2. TodoApp shows a list of uncompleted tasks.
3. User marks complete a specific task in the list.
4. TodoApp marks the task as complete by striking through the task and saving its new state to disk

Use case ends.

**Extensions**

1a. User uses another method to list tasks (e.g. search)

> 1a1. TodoApp shows the list of tasks requested  
  Use case resumes at step 2

2a. The list is empty

> 1a1. TodoApp informs the user the list is empty  
  Use case ends

3a. The given index is invalid

> 3a1. TodoApp shows an error message  
  Use case resumes at step 2

3b. The given index is a task which has already been completed

> 3b1. TodoApp informs the user the task has already been completed 
  Use case ends

### Delete task

**MSS**

1. User requests to delete a specific task from the list
2. TodoApp deletes the person  

Use case ends.

**Extensions**

1a. The given index is invalid

> 1a1. TodoApp shows an error message  
  Use case resumes at step 1

### Viewing a specific tab (i.e. intelligent views)

**MSS**

1. User requests to view specific tab
2. TodoApp shows a list of tasks under specific tab

Use case ends.

**Extensions**

1a. User enters invalid view (eg. a view that doesn't exist )

> 1a1. TodoApp shows an error message  
  Use case ends

### Finding for a task

**MSS**

1. User searches for task with specific tag or fragmented title
2. TodoApp returns a list of tasks matching search fragment

Use case ends.

**Extensions**

1a. User enters an invalid tag/search fragment

> 1a1. TodoApp returns an empty list  
  Use case ends

### Editing a task

1. User searches for specific task to edit
2. TodoApp returns list of tasks matching search query
3. User edits specific task on the list, changing any of its fields
4. TodoApp accepts changes, reflects them on the task and 

**Extensions**

2a. List returned is empty  
>  Use case ends

3a. User enters invalid task index

> 3a1. TodoApp shows error message indicating invalid index   
> Use case resumes at Step 2

3b. User enters invalid arguments to edit fields

> 3b1. TodoApp shows error message indicating invalid fields  
> Use case resumes at Step 2

### Pinning a task

**MSS**

1. User searches for specific task to pin using the find command
2. TodoApp returns a list of tasks matching the search query
3. User selects a specific task to pin
4. TodoApp pins selected task and updates the storage file on disk 

Use case ends.

**Extensions**

2a. List returned by TodoApp is empty

> Use case ends

3a. Selected task is already pinned

>  3a1. TodoApp unpins selected task
>  Use case ends

3b. User provides an invalid index

> 3b1. TodoApp shows an error message  
> Use case resumes at Step 3


### Undoing an action

1. User carries out a mutating command (see [glossary](#appendix-d-glossary))
2. User finds they have made a mistake and instructs TodoApp to undo last action
3. TodoApp rolls back the todolist to the previous state and updates the stored todolist on disk

**Extensions**

2a1. The user calls the undo command without having made any changes 

> 2a1. TodoApp shows an error message  
> Use case ends 


## Appendix C : Non Functional Requirements

The project should -

1. work on any mainstream OS as long as it has Java 8 or higher installed.
2. use a command line interface as the primary input mode
3. have a customizable colour scheme.
4. be able to hold up to 1000 todos, events and deadlines. 
5. come with automated unit tests.
6. have competitive performance with commands being executed within 5 seconds of typing into the CLI
7. be open source. 

## Appendix D : Glossary

Mainstream OS

:   Windows, Linux, OS X

Task 

:   A single todo task, deadline or item

Pinning

:   Marking a task with higher importance/priority than others. Pinned tasks will always appear first in any view. 

Mutating Command

:   Any command which causes a change in the state of the the TodoApp (E.g. add, delete, edit, pin, complete)


## Appendix E : Product Survey

{TODO: Add a summary of competing products}

## Appendix F: Using Gradle 

[Gradle][gradle] is a build automation tool for Java projects. It can automate build-related tasks such as
 
* Running tests
* Managing library dependencies
* Analyzing code for style compliance
* Packaging code for release

The gradle configuration for this project is defined in the build script [`build.gradle`](../build.gradle).
 
!!! note 
    To learn more about gradle build scripts refer to [Build Scripts Basics](https://docs.gradle.org/current/userguide/tutorial_using_tasks.html).

### Running Gradle Commands

To run a Gradle command, open a command window on the project folder and enter the Gradle command. Gradle commands look like this:

* On Windows :`gradlew <task1> <task2> ...` e.g. `gradlew clean allTests`
* On Mac/Linux: `./gradlew <task1> <task2>...`  e.g. `./gradlew clean allTests`

!!! note
    If you do not specify any tasks, Gradlew will run the default tasks `clean` `headless` `allTests` `coverage`

### Cleaning the Project

**`clean`** - Deletes the files created during the previous build tasks (e.g. files in the `build` folder).<br>
e.g. `./gradlew clean`
  
!!! note "`clean` to force Gradle to execute a task"
    When running a Gradle task, Gradle will try to figure out if the task needs running at all.  If Gradle determines that the output of the task will be same as the previous time, it will not run the task. For example, it will not build the JAR file again if the relevant source files have not changed since the last time the JAR file was built. If we want to force Gradle to run a task, we can combine that task with `clean`. Once the build files have been `clean`ed, Gradle has no way to determine if the output will be same as before, so it will be forced to execute the task.
    
### Creating the JAR file

**`shadowJar`** - Creates the `addressbook.jar` file in the `build/jar` folder, if the current file is outdated.  
e.g. `./gradlew shadowJar`

To force Gradle to create the JAR file even if the current one is up-to-date, you can '`clean`' first.  
e.g. `./gradlew clean shadowJar` 

!!! note "Why do we create a fat JAR?"
    If we package only our own class files into the JAR file, it will not work properly unless the user has all the other JAR files (i.e. third party libraries) our classes depend on, which is rather inconvenient. Therefore, we package all dependencies into a single JAR files, creating what is also known as a _fat_ JAR file. To create a fat JAR file, we use the [shadow jar](https://github.com/johnrengelman/shadow) Gradle plugin.

### Running Tests

**`allTests`** - Runs all tests.

**`guiTests`** - Runs all tests in the `guitests` package
  
**`nonGuiTests`** - Runs all non-GUI tests in the `seedu.address` package
  
**`headless`** - Sets the test mode as _headless_. The mode is effective for that Gradle run only so it should be combined with other test tasks.
  
Here are some examples:

* `./gradlew headless allTests` -- Runs all tests in headless mode
* `./gradlew clean nonGuiTests` -- Cleans the project and runs non-GUI tests


### Updating Dependencies

There is no need to run these Gradle tasks manually as they are called automatically by other relevant Gradle tasks.

**`compileJava`** - Checks whether the project has the required dependencies to compile and run the main program, and download any missing dependencies before compiling the classes.  

See `build.gradle` > `allprojects` > `dependencies` > `compile` for the list of dependencies required.

**`compileTestJava`** - Checks whether the project has the required dependencies to perform testing, and download any missing dependencies before compiling the test classes.
  
See `build.gradle` > `allprojects` > `dependencies` > `testCompile` for the list of dependencies required.

*[CRUD]: Create, Retrieve, Update, Delete
*[GUI]: Graphical User Interface
*[UI]: User interface
*[IDE]: Integrated Development Environment

[repo]: https://github.com/CS2103AUG2016-W10-C4/main/

[sourcetree]: https://www.sourcetreeapp.com/
[jdk]: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[eclipse]: https://eclipse.org/downloads/
[travis]: https://travis-ci.org/CS2103AUG2016-W10-C4/main
[coveralls]: https://coveralls.io/github/CS2103AUG2016-W10-C4/main
[codacy]: https://www.codacy.com/app/Logical-Reminding-Apartment/main/dashboard
[gradle]: https://gradle.org/ 

[workflow]: https://www.atlassian.com/git/tutorials/comparing-workflows/feature-branch-workflow/
[issues]: https://github.com/CS2103AUG2016-W10-C4/main/issues
[pr]: https://github.com/CS2103AUG2016-W10-C4/main/compare
[tdd]: https://en.wikipedia.org/wiki/Test-driven_development

[jul]: https://docs.oracle.com/javase/8/docs/api/java/util/logging/package-summary.html

[gfm]: https://guides.github.com/features/mastering-markdown/
[py-markdown]: https://pythonhosted.org/Markdown/extensions/index.html
