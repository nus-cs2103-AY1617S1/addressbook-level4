# Developer Guide 

* [Setting Up](#setting-up)
* [Design](#design)
* [Implementation](#implementation)
* [Testing](#testing)
* [Dev Ops](#dev-ops)
* [Appendix A: User Stories](#appendix-a-user-stories)
* [Appendix B: Use Cases](#appendix-b-use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c-non-functional-requirements)
* [Appendix D: Glossary](#appendix-d-glossary)
* [Appendix E: Product Survey](#appendix-e-product-survey)

## Setting Up

#### Prerequisites

1. **JDK `1.8.0_60`** or later (Will not work with earlier version of Java 8)
2. **Eclipse** IDE
3. **e(fx)clipse** plugin for Eclipse (Do the steps 2 onwards given in [this page](http://www.eclipse.org/efxclipse/install.html#for-the-ambitious))
4. **Buildship Gradle Integration** plugin from the Eclipse Marketplace

#### Importing the Project into Eclipse

0. Fork this repo, and clone the fork to your computer
1. Open Eclipse (Note: Ensure you have installed the **e(fx)clipse** and **buildship** plugins as given 
   in the prerequisites above)
2. Click `File` > `Import`
3. Click `Gradle` > `Gradle Project` > `Next` > `Next`
4. Click `Browse`, then locate the project's directory
5. Click `Finish`
    - If you are asked whether to 'keep' or 'overwrite' config files, choose to 'keep'.
    - Depending on your connection speed and server load, it can even take up to 30 minutes for the set up to finish (This is because Gradle downloads library files from servers during the project set up process).
    - If Eclipse auto-changed any settings files during the import process, you can discard those changes.

## Design

### Architecture Diagram

<img src="images/Architecture.png" width="600"><br>

### Main

`MainApp` is the entry point of the application. It is responsible for:

- On application launch: Initializes the components in the correct sequence, and connect them up with each other
- On application exit: Shuts down the components and invokes cleanup methods where necessary

The application consists 4 components:

* [**`UI`**](#ui-component): The UI of the application
* [**`Logic`**](#logic-component): Executes commands from the UI, using the interfaces provided by `Model` and `Storage`
* [**`Model`**](#model-component): Holds the data of the application in-memory
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the file system

Each of the 4 components defines a single interface and exposes its fuctionality using a concrete class implementing that interface:

| Component     | Interface     | Class         |
|---            |---            |---            |
| UI            | UI            | UIManager     |
| Logic         | Logic         | LogicManager  |
| Model         | Model         | ModelManager  |
| Storage       | Storage       | StorageManager|

### UI Component **_(OUTDATED)_**

<img src="images/UiClassDiagram.png" width="800"><br>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `ToDoListPanel`, `StatusBarFooter`, `BrowserPanel` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class and they can be loaded using the `UiPartLoader`.

The `UI` component uses _JavaFX_ UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder.

The `UI` component:

* Executes user commands using the `Logic` component
* Binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change
* Responds to events raised from various parts of the App and updates the UI accordingly

### Logic Component **_(OUTDATED)_**

<img src="images/LogicClassDiagram.png" width="800"><br>

1. `Logic` uses the `Parser` class to parse the user command.
2. This results in a `Command` object which is executed by the `LogicManager`.
3. The command execution can affect the `Model` (e.g. adding a person) and/or raise events.
4. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")` API call.

<img src="images/DeletePersonSdForLogic.png" width="800"><br>

### Storage Component **_(OUTDATED)_**

<img src="images/StorageClassDiagram.png" width="800">

The `Storage` component:

* can save `UserPref` objects in json format and read it back.
* can save the Address Book data in xml format and read it back.

### Model Component **_(OUTDATED)_**

<img src="images/ModelClassDiagram.png" width="800">

The `Model`:

* stores a `UserPref` object that represents the user's preferences.
* stores the Address Book data.
* exposes a `UnmodifiableObservableList<ReadOnlyPerson>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.

### Commons

The `Commons` package contains a collection of classes used by all components in the application.

Two of those classes play important roles at the architecture level:

* `EventsCenter` : This class (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
  is used by components to communicate with other components using events (i.e. a form of _Event Driven_ design)
* `LogsCenter` : Used by components to write log messages to the application's log file.

## Implementation

### Logging

We are using `java.util.logging` package for logging. The `LogsCenter` class is used to manage the logging levels
and logging destinations.

* The logging level can be controlled using the `logLevel` setting in the configuration file (See [Configuration](#configuration))
* The `Logger` for a class can be obtained using `LogsCenter.getLogger(Class)` which will log messages according to the specified logging level
* Currently log messages are output through: `Console` and to a `.log` file

#### Logging Levels

* `SEVERE`: Critical problem detected which may possibly cause the termination of the application
* `WARNING`: Can continue, but with caution
* `INFO`: Information showing the noteworthy actions by the application
* `FINE`: Details that is not usually noteworthy but may be useful in debugging e.g. print the actual list instead of just its size

### Configuration

Certain properties of the application can be controlled (e.g application name, logging level) through the configuration file (default: `config.json`).

## Testing

Tests can be found in the `./src/test/java` folder.

### In Eclipse
> If you are not using a recent Eclipse version (i.e. _Neon_ or later), enable assertions in JUnit tests
  as described [here](http://stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option).

* To run all tests, right-click on the `src/test/java` folder and choose `Run as` > `JUnit Test`
* To run a subset of tests, you can right-click on a test package, test class, or a test and choose to run as a JUnit test.

### Using Gradle
* See [UsingGradle.md](UsingGradle.md) for how to run tests using Gradle.

We have two types of tests:

1. **GUI Tests** - These are _System Tests_ that test the entire App by simulating user actions on the GUI. These are in the `guitests` package.
  
2. **Non-GUI Tests** - These are tests not involving the GUI. They include,
   1. _Unit tests_ targeting the lowest level methods/classes. <br>
      e.g. `seedu.address.commons.UrlUtilTest`
   2. _Integration tests_ that are checking the integration of multiple code units 
     (those code units are assumed to be working).<br>
      e.g. `seedu.address.storage.StorageManagerTest`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as 
      how the are connected together.<br>
      e.g. `seedu.address.logic.LogicManagerTest`
  
### Headless GUI Testing
Thanks to the [TestFX](https://github.com/TestFX/TestFX) library we use, our GUI tests can be run in the _headless_ mode. In the headless mode, GUI tests do not show up on the screen. That means the developer can do other things on the Computer while the tests are running.

See [UsingGradle.md](UsingGradle.md#running-tests) to learn how to run tests in headless mode.
  
## Dev Ops

### Build Automation

See [UsingGradle.md](UsingGradle.md) to learn how to use Gradle for build automation.

### Continuous Integration

We use [Travis CI](https://travis-ci.org/) to perform _Continuous Integration_ on our projects. See [UsingTravis.md](UsingTravis.md) for more details.

### Making a Release

 1. Generate a JAR file [using Gradle](UsingGradle.md#creating-the-jar-file).
 2. Tag the repo with the version number. e.g. `v0.1`
 2. [Crete a new release using GitHub](https://help.github.com/articles/creating-releases/) 
    and upload the JAR file your created.
   
### Managing Dependencies

The project depends on the [Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which is better than these alternatives:

1. Include those libraries in the repo (this bloats the repo size)
2. Require developers to download those libraries manually (this creates extra work for developers)

## Appendix A: User Stories

Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | user | add to-do items | start viewing and managing my to-do items
`* * *` | user | delete to-do items | remove any outdated or invalid to-do items
`* * *` | user | edit title of to-do items | correct any typos or update the details of to-do items over time
`* * *` | user with many events | have multiple time slots for events | input for events which span multiple days 
`* * *` | user with many events | edit time slot(s) of events | correct any typos or update events’ timings after they are postponed or  confirmed 
`* * *` | user | view events and deadlines in chronological order, from the current day | figure out what events or deadlines are happening next  
`* * *` | user | view a list of floating tasks and deadlines | figure out what I can do when I am free
`* * *` | user | mark off floating tasks and deadlines | dismiss pending to-dos that I have done
`* * *` | user | block off certain dates and times with an event | have some free time
`* * *` | user with many to-do items | search for to-do items by their textual content | navigate through a long list of to-do items
`* * *` | user | undo the most recent action | revert a mistake
`* * *` | user with multiple devices | export or import to-do items to a specified folder | share the to-do list across multiple devices
`* * *` | new user | view all possible command types | learn about the functionality of the application while using it
`* * *` | new user | view the correct command syntax and sample commands after inputting a command wrongly | learn the correct command syntax while using it
`* *` | user | set events as recurring | avoid having to enter identical events periodically
`* *` | user | use command keywords (eg, "from", "to", "by") in titles of my to-do items | avoid having to change how I write my to-do because of the command keywords
`* *` | user with many to-do items | add tags to to-do items, and search to-do items by tags | quickly determine what kind of classification the to-do items are when I skim through the list
`* *` | very active user | summon the application and have it ready for input immediately via a keyboard shortcut | quickly be able to add to or remove from my to-do list when I have to
`* *` | user | receive a visual confirmation of the details of the to-do item I have added/edited | be able to verify modifications to my to-do list
`*` | user with upcoming events that have uncertain timings | mark events as “unconfirmed”, and manage and view them as “pending time confirmation” | better track and manage events with uncertain timings that are pending confirmation
`*` | new user | view syntax highlighting of the various fields of a command while inputting, in real time | be clear about how the application parses my to-do item’s fields and avoid unintended errors in input
`*` | user | customize command keywords with my own aliases | use commands that are personally more intuitive and possibly spend less time typing by having own keywords
`*` | user | be flexible in the order of keywords or fields in commands | be less rigid in typing commands and possibly spend less time typing

Priorities:

* `* * *` - High (Must have)
* `* *` - Medium (Nice to have)
* `*` - Low (Unlikely to have)

## Appendix B: Use Cases

In all the listed use cases, the **System** is our application and the **Actor** is the user.

### UC01 - Add to-do item

MSS:<br><br>
1. User types in an `add` command with details of the to-do item<br>
2. Application adds the to-do item to the current to-do list<br>
3. Application updates the GUI to reflect the changes<br>
Use case ends

Extensions:<br><br>
1a. Command was not properly formatted<br>
&nbsp;&nbsp;&nbsp; 1a1. Application uses GUI to report details of the error in parsing command<br>
&nbsp;&nbsp;&nbsp; 1a2. Application uses GUI to show correct `add` command syntax and a sample `add` command<br>
&nbsp;&nbsp;&nbsp; Use case ends
 
### UC02 - Delete to-do item

MSS:<br><br>
1. User types in an `delete` command with the screen index of the to-do item<br>
2. Application deletes the to-do item to the current to-do list<br>
3. Application updates the GUI to reflect the changes<br>
Use case ends

Extensions:<br><br>
1a. Command was not properly formatted<br>
&nbsp;&nbsp;&nbsp; 1a1. Application uses GUI to report details of the error in parsing command<br>
&nbsp;&nbsp;&nbsp; 1a2. Application uses GUI to show correct `delete` command syntax and a sample `delete` command<br>
&nbsp;&nbsp;&nbsp; Use case ends

1b. Screen index is invalid<br>
&nbsp;&nbsp;&nbsp; 1b1. Application uses GUI to report that screen index is invalid<br>
&nbsp;&nbsp;&nbsp; Use case ends

### UC03 - Find to-do items

MSS:<br><br>
1. User types in an `find` command with a list of words to search for<br>
2. Application searches through the to-do list and updates the GUI to show only the matched to-do items<br>
Use case ends

Extensions:<br><br>
1a. No words were given<br>
&nbsp;&nbsp;&nbsp; 1a1. Application updates GUI to show all to-do items<br>
&nbsp;&nbsp;&nbsp; Use case ends

2a. No to-do items matched<br>
&nbsp;&nbsp;&nbsp; 2a1. Application uses GUI to report that no to-do items were matched<br>
&nbsp;&nbsp;&nbsp; Use case ends

## Appendix C: Non Functional Requirements

1. Should start-up in at most 3s
2. Should process every command in at most 1s
3. Should work on any mainstream OS as long as it has Java 8 or higher installed
4. Should be able to hold up to 1000 to-dos
5. Should be able to hold up to 1000 tags per to-do
6. Should come with automated unit tests
7. Code should be open source
8. Should favor DOS style commands over Unix-style commands
9. Should not use more than 100MB of RAM
10. Number of existing files that has to be edited to add new commands is at most 2 existing files (easy to add)
11. New users should not take more than 1 hour to learn the commands and their syntax
12. New users should not require reading of a guide to navigate GUI (excluding learning of commands) 

## Appendix D: Glossary

- **Mainstream OS**: Windows, Linux, Unix and OS-X
- **To-do item**: Event, deadline or floating task added by the user
- **Screen index of to-do item**: The number visually tagged to a to-do item on screen (changes with how to-do items are listed)

## Appendix E: Product Survey

We tested a few existing to-do managers and analysed how much they would meet the needs of our target user Jim, a busy office worker who receives many to-dos by emails and prefers typing over using the mouse.

Product (Reviewer) | Positive points | Negative points
-------- | :--------- | :-----------
Google Keep (Zhiwen) | + Simplicity (clear and simple UI) <br> + Easy access from anywhere <br> + Native sync and integration with Google <br> + Mark things done easily <br> + Support photos, audios and lists in content | - Unable to do text formatting <br> - Search function only supports titles <br> - Unable to prioritise activities
Trello (Sheng Xuan) | + Multiple platform support (Web, mobile, desktop) <br> + Due date function, which is suitable for tasks with deadlines <br> + Search by keyword of a task <br> + Archive completed tasks <br> + Check list to track the progress of each task <br> + Attachment files to a task (ie. forms to reply in a email) | - At least 2 clicks (taps) are needed to add a new task <br> - Calendar view function is not free to use. In free version, no timeline can be shown, add events happen in specific dates are not supported <br> - Unable to undo
Anydo (Conan) | + Able to push tasks to a later date <br> + Easily reorder tasks <br> + Easy to use interface (4 major groups) <br> + Can be used on mobile and on browser <br> + Supports double confirmation of deleting a task when complete <br> + Can add details in later | - No clear distinguishing of the 3 types of tasks <br> - No sense of timeline, just tasks to complete by the end of the day <br> - Deadlines are not shown unless the task is clicked <br> - No search function (however, you can Ctrl+F, though no content search) <br> - The other 2 group “Upcoming” and “Someday” is kind of ambiguous
Google Calendar (Yun Chuan) | + Able to mark as done, and undo “done” <br> + Clean, simple interface which is not cluttered <br> + Autocomplete based on crowd-sourced or history (but doesn’t feel very “intelligent”) <br> + Push notifications for reminders/events <br> + Able to repeat events <br> + Both on mobile and on website, sync-ed <br> + “All-day” events <br> + “Schedule” view lists everything in chronological order, starting from today <br> + Able to “zoom in” (day view) and “zoom out” (month view) <br> + Split into: “events” and “reminders”, events have start to end time, reminders only have a reminder time | - Online, cannot export to data file <br> - Unable to mark deadlines accurately (not an “event”) <br> - Cannot search for todos <br> - Too many screens or user actions to add a task
