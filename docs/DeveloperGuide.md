[TOC]

## Introduction

Welcome to the project! This guide will get you up to speed with how to set up your development environment, 
the basic architecture of the application, how to perform some common development tasks as well as 
who to contact when you're lost.

### Tooling

This project uses 

- **git** - Version control 
- **[Eclipse][eclipse]** - IDE 
- **Gradle** - Build automation 
- **[Travis][travis], [Coveralls][coveralls] and [Codacy][codacy]** - Continuous integration and quality control
- **[GitHub][repo]** - Source code hosting and issue tracking  

## Setting up

### Prerequisites

1. **JDK `1.8.0_60`**  or later. 
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

!!! note
    
    * If you are asked whether to 'keep' or 'overwrite' config files, choose to 'keep'.
    * Depending on your connection speed and server load, it can even take up to 30 minutes for the set up to finish
      (This is because Gradle downloads library files from servers during the project set up process)
    * If Eclipse auto-changed any settings files during the import process, you can discard those changes.
  
### Contributing 

We use the [feature branch git workflow][workflow]. When working on a task please remember to assign the relavant issue 
to yourself [on the issue tracker][issues] and branch off from `master`. When the task is complete remember to 
push the branch to GitHub and [create a new pull request][pr] so that the integrator can review the code. 
For large features that impact multiple parts of the code it is best to open a new issue on issue tracker
so that the design of the code can be discussed first. 

Test driven development is encouraged but not required. All incoming code should have accompanying tests.

### Coding Style

We use the Java coding standard found at <https://oss-generic.github.io/process/codingstandards/coding-standards-java.html>.
 

## Design

### Architecture

<img src="images/Architecture.png" width="600">

The Architecture Diagram given above explains the high-level design of the App. Here is a quick 
overview of each component.

`Main` has only one class called [`MainApp`](../src/main/java/seedu/todo/MainApp.java). It is responsible for,

* At app launch: Bootstrapping the application by initializing the components in the correct sequence and 
injecting the dependencies needed for each component. 
* At shut down: Shuts down the components and invoke cleanup method where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.
Two of those classes play important roles at the architecture level.

* `EventsCentre` : This class (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
  is used by components to communicate with other components using events (i.e. a form of _Event Driven_ design)
* `LogsCenter` : Used by many classes to write log messages to the App's log file.

The rest of the App consists three components.

* [**`UI`**](#ui-component): The UI of tha App, representing the view layer. 
* [**`Logic`**](#logic-component): The parser and command executer, representing the controller 
* [**`Model`**](#model-component): Data manipulation and storage, representing the model and data layer 

Each of the four components defines its API in an `interface` with the same name as the Component and 
are bootstrapped at launch by `MainApp`.
 

For example, the `Logic` component (see the class diagram given below) defines it's API in the `Logic.java`
interface and exposes its functionality using the `LogicManager.java` class.

<img src="images/LogicClassDiagram.png" width="800">

The Sequence Diagram below shows how the components interact for the scenario where the user issues the
command `delete 3`.

<img src="images\SDforDeletePerson.png" width="800">

The diagram below shows how the `EventsCenter` reacts to that event, which eventually results in the updates
being saved to the hard disk and the status bar of the UI being updated to reflect the 'Last Updated' time. 

<img src="images\SDforDeletePersonEventHandling.png" width="800">

> Note how the event is propagated through the `EventsCenter` to the `Storage` and `UI` without `Model` having
  to be coupled to either of them. This is an example of how this Event Driven approach helps us reduce direct
  coupling between components.

The sections below give more details of each component.

### UI component

<img src="images/UiClassDiagram.png" width="800">

**API** : [`Ui.java`](../src/main/java/seedu/todo/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`,
`StatusBarFooter`, `BrowserPanel` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class
and they can be loaded using the `UiPartLoader`.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files
 that are in the `src/main/resources/view` folder. 
 For example, the layout of the [`MainWindow`](../src/main/java/seedu/todo/ui/MainWindow.java) is specified in
 [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component,

* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change.
* Responds to events raised from various parts of the App and updates the UI accordingly.

### Logic component

<img src="images/LogicClassDiagram.png" width="800">

**API** : [`Logic.java`](../src/main/java/seedu/todo/logic/Logic.java)

The logic component is the glue sitting between the UI and the data model. It consists of three separate 
subcomponents - 

- `Parser` - turns user input into command and arguments 
- `Dispatcher` - maps parser results to commands 
- `Command` - validates arguments and execute command

The flow of a command being executed is -

1. `Logic` uses the `Parser::parse` to parse the user input into a `ParseResult` object
2. The `ParseResult` is sent to `Dispatcher::dispatch` which instantates a new `Command` object representing
the command the user called
3. `Logic` binds the model and arguments to the `Command` object and executes it 
4. The command execution can affect the `Model` (e.g. adding a person) and/or raise events.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")`
 API call.
 
<img src="images/DeletePersonSdForLogic.png" width="800">

### Model component

<img src="images/ModelClassDiagram.png" width="800">

**API** : [`Model.java`](../src/main/java/seedu/todo/model/Model.java)

The `Model`,

* stores a `UserPref` object that represents the user's preferences.
* stores the Todo list data and persists it to the 
* exposes a `UnmodifiableObservableList<ReadOnlyPerson>` that can be 'observed' ie. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* exposes a simplified interface to the 

### Storage component

<img src="images/StorageClassDiagram.png" width="800">

**API** : [`Storage.java`](../src/main/java/seedu/todo/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save the Address Book data in xml format and read it back.

### Common classes

Classes used by multiple components are in the `seedu.todo.commons` package.

## Implementation

### Logic

See the [Logic component architecture](#logic-component) section for the high level overview of the Logic component.  

#### Parser 

The `TodoParser` subcomponent implents the `Parser` interface, which defines a single `parse` function that 
takes in the user input as a string and returns an object implementing the `ParseResult` interface. The 
implementing class for `TodoParser` can be found as an inner class inside `TodoParser`.

The parser tokenizes the user input by whitespace characters then splits it into three parts: 

- Command `string` - the first word of the user input
- Positional argument `string` - everything from the command to the first flag
- Named argument `Map<String, String>` - a map of flags to values 

For example, the command `add The Milk -d tomorrow 3pm -p` will produce 

- Command: `add`
- Positional argument: `The Milk`
- Named argument: 
    - `d`: `tomorrow 3pm` 
    - `p`: - (empty string)
  
#### Dispatcher 

The `TodoDispatcher` subcomponent implements the `Dispatcher` interface, which defines a single 
`dispatch` command. The dispatch function simply maps the provided `ParseResult` object to the 
correct command and returns it.

#### Command

All commands implement the `BaseCommand` abstract class, which provides argument binding and validation. 



#### Arguments 





### Logging

We are using `java.util.logging` package for logging. The `LogsCenter` class is used to manage the logging levels
and logging destinations.

* The logging level can be controlled using the `logLevel` setting in the configuration file
  (See [Configuration](#configuration))
* The `Logger` for a class can be obtained using `LogsCenter.getLogger(Class)` which will log messages according to
  the specified logging level
* Currently log messages are output through: Console and to a log file.
* The logs 

#### Logging Levels

Level      | Used for
---------- | -------------------------------------------------
 `SEVERE`  | Critical problem detected which may possibly cause the termination of the application
 `WARNING` | Can continue, but with caution
 `INFO`    | Information showing the noteworthy actions by the App
 `FINE`    | Details that is not usually noteworthy but may be useful in debugging e.g. print the actual list instead of just its size

### Configuration

Certain properties of the application can be controlled (e.g App name, logging level) through the configuration file
(default: `config.json`):


## Testing

Tests can be found in the `./src/test/java` folder.

### In Eclipse

* To run all tests, right-click on the `src/test/java` folder and choose
  `Run as` > `JUnit Test`
* To run a subset of tests, you can right-click on a test package, test class, or a test and choose
  to run as a JUnit test.

!!! note
    If you are not using a recent Eclipse version (Neon or later), enable assertions in JUnit tests
    as described [in this Stack Overflow question](http://stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option).


### Using Gradle

See [UsingGradle.md](#appendix-f-using-gradle) for how to run tests using Gradle.

We have two types of tests:

1. **GUI Tests** - These are _System Tests_ that test the entire App by simulating user actions on the GUI.
   These are in the `guitests` package.

2. **Non-GUI Tests** - These are tests not involving the GUI. They include,
    1. **Unit tests** - targeting the lowest level methods/classes.  
      e.g. `seedu.todo.commons.UrlUtilTest`
    2. **Integration tests** - that are checking the integration of multiple code units
     (those code units are assumed to be working).  
      e.g. `seedu.todo.storage.StorageManagerTest`
    3. Hybrids of unit and integration tests. These test are checking multiple code units as well as
      how the are connected together.  
      e.g. `seedu.todo.logic.LogicManagerTest`

### Headless GUI Testing 

Thanks to the [TestFX](https://github.com/TestFX/TestFX) library we use,
 our GUI tests can be run in the _headless_ mode.
 In the headless mode, GUI tests do not show up on the screen.
 That means the developer can do other things on the Computer while the tests are running.
 
 See [UsingGradle.md](UsingGradle.md#running-tests) to learn how to run tests in headless mode.

## Dev Ops

### Build Automation

We use [Gradle][gradle] for build automation. Gradle handles project dependencies, build tasks and testing.
If you have configured Eclipse by importing the project as shown in the [setting up](#setting-up) section
Gradle should already be properly configured and can be executing from within Eclipse to build, test and 
package the project from the Run menu.

See the appendix [Using Gradle](#appendix-f-using-gradle) for all of the details and Gradle commands. 

### Continuous Integration

We use [Travis CI][travis] to perform Continuous Integration on our projects.
See [UsingTravis.md](UsingTravis.md) for more details.

### Making a Release

Here are the steps to create a new release.

 1. Generate a JAR file [using Gradle](UsingGradle.md#creating-the-jar-file).
 2. Tag the repo with the version number. e.g. `v0.1`
 2. [Crete a new release using GitHub](https://help.github.com/articles/creating-releases/)
    and upload the JAR file your created.

### Managing Dependencies

Our project depends on third-party libraries. We use Gradle to automate our dependency management. 
To add a new dependency to the project, 


## Documentation 

Our documentation and user guides are written in [GitHub Flavor Markdown][gfm] with a number of 
extensions including tables, definition lists and warning blocks that help enable richer styling options
for our documentation. These extensions are documented on the [Extensions page of the Python Markdown package][py-markdown],
the package we use to help transform the Markdown into HTML. We use HTML because it allows greater 
flexibility in styling and is generally more user friendly than raw Markdown. To set up the script:

1. Make sure you have Python 3.5+ installed. Run `python3 --version` to check
2. Install the dependencies - `pip3 install markdown pygments` 
3. Run the script - `python3 docs/build/converter.py`

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
★★       | user      | set recurring tasks | do not need to repeatedly add tasks
★★       | user      | sort tasks by various parameters| organize my tasks and locate a task easily
★★       | user      | set reminders for a task | do not need to mentally track deadlines
★        | user      | know the number of tasks I have left | gauge how many tasks I have left to do.
★        | user      | be notified about upcoming deadlines without opening the app | so that I can receive timely reminders  


## Appendix B : Use Cases

(For all use cases below, the **System** is the `TodoApp` and the **Actor** is the `user`, unless specified otherwise)

### Use case: Adding an event

**MSS**

1. User types out an event with start time end time and location
2. TodoApp adds event with specified fields to 'TodoList.xml'
Use case ends.

**Extensions**

1a. The task has no title

> 1a1. TodoApp shows an error message <br>
  Use case resumes at step 1 
1b. The task's date field is empty

> 1b1. TodoApp creates a task with no start and end date <br>
  
  Use case ends

1c. The task has a start time later than end time

> 1c1. TodoApp shows an error message <br>
  Use case resumes at step 1 
  
### Use case: Adding a deadline

**MSS**

1. User enters a deadline.
2. TodoApp creates new todo item with deadline specified

Use case ends.

**Extensions**

1a. The task has no title

> 1a1. TodoApp shows an error message <br>
  Use case resumes at step 1 
1b. The task's date field is empty

> 1b1. TodoApp creates a task with no start and end date <br>
  
  Use case ends

### Use case: Adding a recurring task

**MSS**

1. User requests to list persons
2. TodoApp shows a list of persons
3. User requests to delete a specific person in the list
4. TodoApp deletes the person  

Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. TodoApp shows an error message <br>
  Use case resumes at step 2

### Use case: Marking a task complete

**MSS**

1. User requests to list of uncompleted tasks.
2. TodoApp shows a list of uncompleted tasks.
3. User marks complete a specific task in the list.
4. TodoApp marks the task as complete by striking through the task  

Use case ends.

**Extensions**
1a. User uses another method to list tasks (e.g. search)

> 1a1. TodoApp shows the list of tasks requested
  Use case resumes at step 2

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. TodoApp shows an error message <br>
  Use case resumes at step 2

3b. The given index is a task which is already complete

> Use case ends

### Use case: Delete task

**MSS**

1. User requests to list persons
2. TodoApp shows a list of persons
3. User requests to delete a specific person in the list
4. TodoApp deletes the person  

Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. TodoApp shows an error message <br>

  Use case resumes at step 2

### Use case: Viewing a specific tab (i.e. intelligent views)

**MSS**

1. User requests to view specific tab
2. TodoApp shows a list of ToDo items under specific tag

Use case ends.

**Extensions**

1a. User enters invalid view command

> 1a1. TodoApp shows an error message <br>
  Use case ends

### Use case: Finding for a task

**MSS**

1. User searches for task with specific tag or fragmented title
2. TodoApp returns a list of tasks matching search fragment

Use case ends.
**Extensions**

1a. User enters an invalid tag/ search fragment

>1a1. TodoApp returns an empty list
Use case ends

### Use case: Editing a task
1. User searches for specific task to edit
2. TodoApp returns list of tasks matching search field
3. User edits specific task on the list, changing any of its fields
4. TodoApp accepts changes and reflects them on the task

**Extensions**

2a. List returned is empty
>Use case ends

3a. User enters invalid task index
>3a1.TodoApp shows error message indicating invalid index <br>
>Use case resumes at Step 2

3b. User enters invalid arguments to edit fields
>3b1. TodoApp shows error message indicating invalid fields <br>
>Use case resumes at Step 2

### Use case: Pinning a task

**MSS**

1. User searches for specific task to pin using find command
2. TodoApp returns a list of tasks matching search field.
3. User selects specific task to pin
4. TodoApp pins selected task

Use case ends.
**Extensions**

2a. List returned by TodoApp is empty
>Use case ends

3a. Selected task is already pinned
>3a1. TodoApp unpins selected task
>Use case ends

3b. User provides an invalid index
>3b1. TodoApp shows an error message<br>
> Use case resumes at Step 3


### Use case: Undoing an action
1. User carries out a mutable command (See Glossary[#appendix-d-glossary])
2. Address book executes command

### Use case: Redoing an action

### Use case: 

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

Mutable Command

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
    To learn more about gradle build scripts refer to 
    [Build Scripts Basics](https://docs.gradle.org/current/userguide/tutorial_using_tasks.html).

### Running Gradle Commands

To run a Gradle command, open a command window on the project folder and enter the Gradle command.
Gradle commands look like this:

* On Windows :`gradlew <task1> <task2> ...` e.g. `gradlew clean allTests`
* On Mac/Linux: `./gradlew <task1> <task2>...`  e.g. `./gradlew clean allTests`

!!! note
    If you do not specify any tasks, Gradlew will run the default tasks `clean` `headless` `allTests` `coverage`

### Cleaning the Project

**`clean`** - Deletes the files created during the previous build tasks (e.g. files in the `build` folder).<br>
e.g. `./gradlew clean`
  
!!! note "`clean` to force Gradle to execute a task"
    When running a Gradle task, Gradle will try to figure out if the task needs running at all. 
    If Gradle determines that the output of the task will be same as the previous time, it will not run
    the task. For example, it will not build the JAR file again if the relevant source files have not changed
    since the last time the JAR file was built. If we want to force Gradle to run a task, we can combine
    that task with `clean`. Once the build files have been `clean`ed, Gradle has no way to determine if
    the output will be same as before, so it will be forced to execute the task.
    
### Creating the JAR file

**`shadowJar`** - Creates the `addressbook.jar` file in the `build/jar` folder, if the current file is outdated.  
e.g. `./gradlew shadowJar`

To force Gradle to create the JAR file even if the current one is up-to-date, you can '`clean`' first.  
e.g. `./gradlew clean shadowJar` 

!!! note "Why do we create a fat JAR?"
    If we package only our own class files into the JAR file, it will not work properly unless the user has all the other
    JAR files (i.e. third party libraries) our classes depend on, which is rather inconvenient. 
    Therefore, we package all dependencies into a single JAR files, creating what is also known as a _fat_ JAR file. 
    To create a fat JAR file, we use the [shadow jar](https://github.com/johnrengelman/shadow) Gradle plugin.

### Running Tests

**`allTests`** - Runs all tests.

**`guiTests`** - Runs all tests in the `guitests` package
  
**`nonGuiTests`** - Runs all non-GUI tests in the `seedu.address` package
  
**`headless`** - Sets the test mode as _headless_. 
  The mode is effective for that Gradle run only so it should be combined with other test tasks.
  
Here are some examples:

* `./gradlew headless allTests` -- Runs all tests in headless mode
* `./gradlew clean nonGuiTests` -- Cleans the project and runs non-GUI tests


### Updating Dependencies

There is no need to run these Gradle tasks manually as they are called automatically by other 
relevant Gradle tasks.

**`compileJava`** - Checks whether the project has the required dependencies to compile and run the main program, and download 
any missing dependencies before compiling the classes.  

See `build.gradle` > `allprojects` > `dependencies` > `compile` for the list of dependencies required.

**`compileTestJava`** - Checks whether the project has the required dependencies to perform testing, and download 
any missing dependencies before compiling the test classes.
  
See `build.gradle` > `allprojects` > `dependencies` > `testCompile` for the list of 
dependencies required.


[repo]: https://github.com/CS2103AUG2016-W10-C4/main/

[eclipse]: https://eclipse.org/downloads/
[travis]: https://travis-ci.org/CS2103AUG2016-W10-C4/main
[coveralls]: https://coveralls.io/github/CS2103AUG2016-W10-C4/main
[codacy]: https://www.codacy.com/app/Logical-Reminding-Apartment/main/dashboard
[gradle]: https://gradle.org/ 

[workflow]: https://www.atlassian.com/git/tutorials/comparing-workflows/feature-branch-workflow/
[issues]: https://github.com/CS2103AUG2016-W10-C4/main/issues
[pr]: https://github.com/CS2103AUG2016-W10-C4/main/compare
[gfm]: https://guides.github.com/features/mastering-markdown/
[py-markdown]: https://pythonhosted.org/Markdown/extensions/index.html