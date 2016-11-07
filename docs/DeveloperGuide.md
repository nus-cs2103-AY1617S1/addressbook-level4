<!--@@author A0139678J	-->

#
# Developer Guide

**Table of contents**


* [Introduction](#introduction)
* [Setting Up](#setting-up)
* [Design](#design)
* [Testing](#testing)
* [Future Works](#future-works)
* [Dev Ops](#dev-ops)
* [Appendix A: User Stories](#appendix-a--user-stories)
* [Appendix B: Use Cases](#appendix-b--use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c--non-functional-requirements)
* [Appendix D: Glossary](#appendix-d--glossary)
* [Appendix E : Product Survey](#appendix-e--product-survey)




## **Introduction**

###**Overview**

_Unburden_ is an application which will help users to manage their daily tasks better. _Unburden_ utilises a simple command line interface which allows users to keep track of their tasks without any form of clicking. _Unburden_ is written in Java.

###**Purpose**

This developer guide focuses primarily on the APIs used, the different components that work together and the main 4 components of _Unburden_. This developer guide will clearly explain the design of the software and also to showcase the core functionalities of the software. As such, the main purpose of this developer guide is to provide a general introduction and summary of the classes and components that are used within this application to help prospective developers who wish to develop _Unburden_ further.


###**Audience**

The main audience of this developer&#39;s guide is directed towards the students who may be interested to further develop this application or even prospective team members. This developer guide may also interest developers who are interested to join the team.

###**Good skills to have**

The entire application uses a huge range of APIs and requires a deep understanding of:

-  **Java programming language**

We have used Java and its APIs to write the main code hence it would be beneficial if you have a good grasp of Java. 


- **Eclipse IDE**

The main code is very long and using an IDE to help you in the process of writing, editing and testing would be very beneficial to you.


- **JavaFX**

Being familiar with JavaFX will enable you to edit the user interface smoothly since we use the JavaFX framework quite intensively throughout the project.

In essence, having all of the skills mentioned above will definitely help you to understand the code and hence allow you to add on to the existing code easily and efficiently. As such, we strongly recommend that you familiarize yourself with aforementioned skills. 

## **Setting up**

###**Prerequisites**

1. **JDK `1.8.0_60`**  or later<br>

    > Having any Java 8 version is not enough. <br>
    This app will not work with earlier versions of Java 8.
    
2. **Eclipse** IDE
3. **e(fx)clipse** plugin for Eclipse (Do the steps 2 onwards given in
   [this page](http://www.eclipse.org/efxclipse/install.html#for-the-ambitious))
4. **Buildship Gradle Integration** plugin from the Eclipse Marketplace


###**Importing the project into Eclipse**

1. Fork this repo, and clone the fork to your computer
2. Open Eclipse (Note: Ensure you have installed the **e(fx)clipse** and **buildship** plugins as given 
   in the prerequisites above)
3. Click `File` > `Import`
4. Click `Gradle` > `Gradle Project` > `Next` > `Next`
5. Click `Browse`, then locate the project's directory
6. Click `Finish`

  > * If you are asked whether to 'keep' or 'overwrite' config files, choose to 'keep'.
  > * Depending on your connection speed and server load, it can even take up to 30 minutes for the set up to finish
      (This is because Gradle downloads library files from servers during the project set up process)
  > * If Eclipse auto-changed any settings files during the import process, you can discard those changes.
  
#### Troubleshooting project setup

**Problem: Eclipse reports compile errors after new commits are pulled from Git**
* Reason: Eclipse fails to recognize new files that appeared due to the Git pull. 
* Solution: Refresh the project in Eclipse:<br> 
  Right click on the project (in Eclipse package explorer), choose `Gradle` -> `Refresh Gradle Project`.
  
**Problem: Eclipse reports some required libraries missing**
* Reason: Required libraries may not have been downloaded during the project import. 
* Solution: [Run tests using Gradle](UsingGradle.md) once (to refresh the libraries).
 

## **Design**

### **Architecture**

> <img src="DeveloperGuideImages/DesignArchitecture.png" width="600">

The architectural design in figure 1 shows how the various components work in tandem with each other. `Main` only has one class MainApp and it is responsible for:

 1. Starting up _Unburden_: Initializes all components in order and ensures that the app starts to run
 2. Exiting _Unburden_: Shuts down all components in order and clears the memory

The rest of the app consists of 4 main components other than `Main`. They are :

 1. `UI` : Controls the UI of the app. This is the component that the user interacts with and is responsible for how the application looks to the user. 
 2. `Logic` : Parses the input and executes the command. Logic updates the Model and Storage according to the commands given by the user.
 3. `Model` : Holds the data during runtime. Works with UI to display the results of the commands to the user.
 4. `Storage`: Reads and writes data to the hard disk. Ensures that the data is stored properly even after Unburden has been closed.
 
Each component has a interface which all its classes implements and is named after the component itself.

Each of the four components
* Defines its API in an `interface` with the same name as the Component.
* Exposes its functionality using a `{Component Name}Manager` class.

For example, the `Model` component defines it's API in the `Model.java`
interface and exposes its functionality using the `ModelManager.java` class.<br> 
 

### **Event-driven nature of the design**

><img src="DeveloperGuideImages/SequenceDiagram2.png" width="600">

In figure 2, the diagram below shows how the components interact when the user enters the command 'Done 1'.

Note how the `Model` simply raises a `ListOfTaskChangedEvent` when the data in _Unburden_ is changed,
instead of using `Storage` to save the updates to the hard disk. 


><img src="DeveloperGuideImages/SequenceDiagram1.png" width="600">

In Figure 3, the diagram shows how the `Eventcenter` reacts to the event. This results in the updates being saved to the hard disk and the status bar of the UI being updated to reflect the 'Last Updated' time.

Note how the event is being propagated through the `Eventscenter` to the `Storage` and `UI` without being coupled to the `Model`. This reduces coupling and reduces regression when editing files. Hence, it is highly recommended that you follow this nature of the design when editing the project.

**The sections that below will go into more detail of each component.**



###**UI component**

><img src="DeveloperGuideImages/UI.png" width="600">


The `UI` component focuses on interacting with the user by displaying the necessary information to the user when requested. It is also responsible for the outlook of the application. The `UI` component consists of the abstract `UiPart` class which is the base class for the `UI` parts and each &quot;UI part&quot; is represented by a distinct part of the `UI` such as the panels or status bars.

In essence, the `UI` component makes use of JavaFx framework and the majority of the classes in _Unburden_ import javafx methods. The various layouts of each &quot;UI part&quot; are stores as .fxml files in the src/main/resources/view folder. These files are named according to the respective class names. For example, the layout of the HelpWindow.java is stored in src/main/resources/view/HelpWindow.fxml



####**API**

The `UI` component consists mainly of the following classes:

 1. `UI` 
 2. `UIManager` 
 3. `UIPart` 
 4. `UILoader` 
 5. `SummaryPanel` 
 6. `CommandBox` 
 7. `MainWindow` 
 8. `ResultDisplay` 
 9. `HelpWindow` 
 10. `TaskCard` 
 11. `TaskListPanel` 

These classes work together to form the interface which the user interacts with when using the app. Each of the class are meant to function solely on one part of the UI. For instance, the ResultDisplay class is responsible for displaying the results of a command from the user and the SummaryPanel class is responsible for displaying an up-to-date count of the the tasks that are overdue, undone and even tasks that are due tomorrow.



The UI also consists of a `MainWindow` class which is made up of these &quot;UI parts&quot; such as `CommandBox`, `ResultDisplay`, `TaskListPanel`, `StatusBarFooter`, `BrowserPanel`.

All of these classes, including the MainWindow class inherit from the abstract UiPart class.

The `UI` component

- Takes in user commands and passes it to Logic.
- Auto-updates when data in the `Model` change.
- Responds to events raised from various parts of _Unburden_
- Updates the `UI` accordingly.



###**Logic component**

> <img src="DeveloperGuideImages/Logic.png" width="600">

According to Figure 5, the diagram shows the how each component of the `Logic` communicates with each other. Notice how each command class extends the extract class `Command`.

The `Logic` component consists of the Parser class which is responsible to taking in the inputs from the `UI` component, deciphering it, and then creating a Command class that can handle the user&#39;s input correctly. `LogicManager` will then execute the command.



####**Main API Classes**

The API of the Logic component consists mainly of the following classes:

 1. `Logic` 
 2. `LogicManager` 
 3. `Parser` 
 4. All the command classes eg. `AddCommand`, `EditCommand`, `ListCommand`

These classes work together to categorize the different possible inputs from the user and sieves the important keywords out so that `Model` can continue executing the command entered by the user.

The `Logic` component

- `Logic` takes in the user&#39;s input and passes it to the `Parser` class to check for validity
-  Returns an error message for invalid inputs back to the user 
-  Parses the input in `Parser` and decides which Command class is able to handle the input
- `LogicManager`  takes the command and executes it by calling `Model`
- `TaskResult` is created and returned to the `UI` to be displayed to the user

The sequence diagram above shows the interactions between the `Logic` component for the calling the execute() on "delete 1".

###**Model component**

> <img src="DeveloperGuideImages/Model.png" width="600">

You can see how the different components of the model interact with each other in figure 6.

The `Model` component is mainly responsible for executing the outputs from the `Logic` component. It is also responsible for storing all the in-app data such as the user&#39;s preferences and data which is needed when executing commands.

####**Main API Classes**

The API of `Model` consists of the following classes:

 1. `Model`
 2. `ModelManager` 
 3. `ListOfTask` 
 4. `UserPref` 
 5. `ReadOnlyListOfTask`

The API of the `Model` component is in the `Model` class which consists of the main features of the task manager such as &#39;add&#39;, &#39;delete&#39; and updates the task manager accordingly. The `ModelManager` class, which represents the in-memory model of the task manager data, inherit from the `Model` interface.

The `Model` component

 - Stores the user&#39;s preference <br>
 - Stores _Unburden&#39;s_ data <br>
 - Updates according to the command class called by `Logic`
 - Returns the result of each command back to the `UI` for feedback
 
###**Storage component**

> <img src="DeveloperGuideImages/Storage.png" width="600">

According to figure 8, the diagram shows how the the each component interacts with one another.

The `Storage` component primarily focuses on storing data. Any data related to the application will be saved within `Storage` and can be accessed later when requested. `Storage` works closely with `Model` to read and write data from the app as and when the user requests to add or show existing data.

####**Main API Classes**

The API of `Storage` consists of the following classes:

 1. `JSonUserPrefsStorage` 
 2. `Storage` 
 3. `StorageManager` 
 4. `TaskListStorage`
 5. `UserPrefsStorage`
 6. `XmlAdaptedTask`
 7. `XmlAdaptedTag`
 8. `XmlFileStorage`
 9. `XmlSerializableTaskList`
 10. `XmlTaskListStorage`

These classes are responsible for storing the data from the user and also works with the Model component to execute the commands given by the user. 

The `Storage` component

 - Saves the data entered in by the user and also read it back to `Model` when requested <br>
 - Saves user preferences and read it back when needed <br>
 - Saves the `Userpref` objects json format and the data in _Unburden_ in xml format <br> 




## **Implementation**

We are using java.util.logging package for logging. The LogsCenter class is used to manage the logging leveles and logging destinations.

Some things to note when using the java.util.logging package for logging:

- The logging level can be adjust by toggling the logLevel setting in the configuration file.
- Use the LogCenter.getLogger() to get the Logger for each class. This will also log messages according to the specified logging level.
- The console will display the current log messages. Alternatively, you may open the .log file to see the log messages.

####**Logging Levels**

The following are some of the logging levels which you might encounter when doing logging:

- SEVERE : This means that there is a critical problem which may cause the termination of _Unburden_.
- WARNING : Continue with caution.
- INFO : Information showing the noteworthy actions by _Unburden_.
- FINE : Details that are not usually noteworthy but may be useful in debugging. E.g Print the actual list instead of just its size.



## **Testing**

Tests can be found in the./src/test/javafolder.

In Eclipse:

- To run all tests, right-click on the src/test/java folder and choose Run as &gt; JUnit Test
- To run a subset of tests, you can right-click on a test package, test class, or a test and choose to run as a JUnit test.

Using Gradle:

- See [Gradle.md](https://github.com/nus-cs2103-AY1617S1/addressbook-level4/blob/master/docs/UsingGradle.md) for how to run tests using Gradle.

We have two types of tests:

1. **GUI Tests** - These are _System Tests_ that test _Unburden_ by simulating user actions on the GUI. These are in the guitests package.
2. **Non-GUI Tests** - These are tests not involving the GUI. They include,
	1. _Unit tests_ targeting the lowest level methods/classes. <br>
	2. g. address.commons.UrlUtilTest <br>
	3. _Integration tests_ that are checking the integration of multiple code units (those code units are assumed to be working). <br>
	4. g. address.storage.StorageManagerTest <br>
	5. Hybrids of unit and integration tests. These test are checking multiple code units as well as how the are connected together.<br>
	6. g. address.logic.LogicManagerTest <br>



**Headless GUI Testing** 
Thanks to the [TestFX](https://github.com/TestFX/TestFX) library we use, our GUI tests can be run in the _headless_ mode. In the headless mode, GUI tests do not show up on the screen. That means the developer can do other things on the Computer while the tests are running.

Look up the UsingGradle.md file to learn how to run tests in headless mode.

###**Troubleshooting tests**

Problem: Tests fail because of NullPointException when an AssertionError is expected

- Reason: Assertions are not enabled for JUnit tests. This can happen if you are not using a recent Eclipse version (i.e. _Neon_r later)
- Solution: Enable assertions in JUnit tests as described.
- Delete run configurations created when you ran tests earlier.



## **Future Works**

While this app has been extensively developed, we feel that there is still room for improvement and can therefore be improved further. As such, the main reason for this section of the developer guide is to suggest possible improvements to _Unburden._

For future and prospective developers or students who are interested to join our team, do note that the following list is not exhaustive.

Some possible additions to be made:

- Allow this app to be run on a mobile device such as a mobile phone or tablet
- Create a login system which will protect each user&#39;s data and allow them to access their data from any device
- Add animations so as to appeal to a larger audience
- Implement a synchronous system based on a clock that will remind the user (possibly even when the application is not running) when he/she has a deadline approaching

## **Dev Ops**

###**Build Automation**

We use Gradle to build automation. Do take a look at [UsingGradle.md](UsingGradle.md) to learn how to use Gradle for build automation.

###**Continuous Integration**

We use [Travis CI](https://travis-ci.org/) to perform _Continuous Integration_ on our projects.
Do take a look at [UsingTravis.md](UsingTravis.md) for more details.

###**Creating a new Release**

Here are the steps to create a new release.
 
 1. Generate a JAR file [using Gradle](UsingGradle.md#creating-the-jar-file).
 2. Tag the repo with the version number. e.g. `v0.6`.
 2. [Create a new release using GitHub](https://help.github.com/articles/creating-releases/) 
    and upload the .JAR file which you generated from Gradle in step 1.
   
###**Managing Dependencies**

A project often depends on third-party libraries. For example, _Unburden_ depends on the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these dependencies
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>

- Import those libraries in the repo but this is not recommended as it significantly increases the repo size<br>
- Require developers to download those libraries manually but this is not recommended because other developers have to do extra work<br>


## **Appendix A : User Stories**


Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`



| **Priority** | **As a...** | **I want to...** | **So that I can...** | 
| --- | --- | --- | --- | --- |
| `* * *` | new user | list all the commands | know how to use the program |
|`* * *` | new user | view a command | know how to use that particular command |
|`* * *` | user | add a event task | keep track of all important events which I have to attend |
|`* * *` | user | add a deadline task | keep track of deadlines and adhere to them |
|`* * *` | user | add a floating task | remind myself of less important tasks |
|`* * *` | user | add a deadline task with the time | prioritise all the different deadlines due on the same day |
|`* * *` | user | add a task with tags | properly categorize my tasks |
|`* * *` | user | delete a task | remove a completed task from the list |
|`* * *` | user | delete all tasks | declutter my task manager |
|`* * *` | user | delete all tasks with a certain name or tag | declutter my task manager |
|`* * *` | user | delete all tasks within a certain date | declutter my task manager |
|`* * *` | user | find a task by name/description/tags | find tasks that are related to one another |
|`* * *` | user | find a task by date | see what tasks I have to complete on the specified date |
|`* * *` | user | list all tasks | know what are the tasks I have entered |
|`* * *` | user | list all tasks due by today | know what are the tasks I need to complete by today |
|`* * *` | user | list all tasks due by next week | I can plan ahead of time |
|`* * *` | user | list all tasks that are undone | know which task to attend to first |
|`* * *` | user | list all tasks that are overdue | can attend to tasks that have |
|`* * *` | user | list all tasks that are done | so that I can revert back to undone if I ever need to |
|`* * *` | user | list all tasks that are within a stipulated time frame | plan my holidays |
|`* * *` | user | edit any information of the task | make changes to the tasks that I have created |
|`* *` | user | see what today's date is | I can properly allocate tasks with the correct date in mind |
|`* *` | user | undo previous command | remove any task that is entered wrongly |
|`* *` | user | keep my information safe | protect information that is private to me |
|`* *` | user | be reminded of my deadlines | meet my deadlines |
|`* *` | user | be reminded of my overdue tasks | I remember to finish it now |
|`* *` | user | prioritize my tasks | know which tasks are more important |
|`* *` | user | keep my deleted tasks temporarily | restore them should I need to |
|`* *` | user | auto correct my typos when typing a command | not retype/edit the command every time I make a typo |
|`* *` | user | asked every time I add a task | ensure that all fields have been entered correctly |
|`* *` | user | asked every time I delete a task | not accidentally lose information |
|`* *` | user | asked every time I edit a task | not accidentally change the wrong information |
|`* *` | user | see how many tasks I have to do for today at a glance | I can gauge how much time to spend on each task |
|`* *` | user | see how many tasks are undone at a glance | I get reminded of the tasks which I have yet to complete |
|`* *` | user | see how many tasks are overdue at a glance | I know that I missed something important |
|`* *` | user | see how many tasks are done at a glance| I know when to clear the application |
|`*` | user | automatically delete old tasks periodically | so I do not have to do it myself |
|`*` | user | find history of tasks of certain past periods of time | recall what I've done |
|`*` | user | predict and auto correct my typos | be lazy and not retype everything again |




## **Appendix B : Use Cases**


### Use case: Add task

##### MSS

1. User requests to add a task 
2. _Unburden_ saves the task to the list of tasks and displays the existing list of tasks

	Use case ends
  
##### Extensions

1.1  User requests to add a task with a wrong format <br>

1.2  _Unburden_ will give display a error message <br>  
  
     Use case ends



### Use case: Delete task

##### MSS

1. User requests to delete a specific task by index
2. _Unburden_ will delete the task and show the existing list of tasks

	Use case ends
    
##### Extensions
  
1.1  User requests to delete a specific task by giving an invalid index <br>

1.2  _Unburden_ will show an error message <br>
  
     Use case ends

2.1 User requests to list all tasks in _Unburden_ <br>

2.2 _Unburden_ will show the user the entire list of tasks <br>

3.2 User requests to delete all tasks in _Unburden_ <br>

3.4 _Unburden_ deletes all tasks <br>

	Use case ends





### Use case: Find task

##### MSS

1. User requests to find tasks based on a date
2. _Unburden_ will search through the existing tasks with the specified deadline and shows a list to the user
	
	Use case ends
  
##### Extension
  
1.1  User requests to find tasks based on a name <br>

1.2  _Unburden_ will search through the existing tasks with the specified task names and shows a list to the user <br>
  
     Use case ends
  
2.1  User requests to find tasks due today <br>

2.2  _Unburden_ will search through the existing tasks and shows a list of tasks due today <br>
  
     Use case ends
  
3.1  User requests to find tasks that are due tomorrow <br>

3.2  _Unburden_ will search through the existing tasks and shows a list of tasks due tomorrow to the user <br>
  
     Use case ends
     




### Use case: Edit task

##### MSS

1. User requests to edit a task based on the index of the tasks and the new updated fields
2. _Unburden_ updates the task to the given input fields and displays it to the user 
	
	Use case ends
  
##### Extension
  
1.1  User requests to edit a task based on an invalid index of tasks and the new updated fields <br>

1.2  _Unburden_ shows an error message <br>
  
     Use case ends 





### Use case: List task

##### MSS

1. User requests to list all tasks 
2. _Unburden_ shows a list of all tasks that are undone

      Use case ends
      
###### Extension

1.1 User requests to list of all tasks by a certain date <br>

2.2 _Unburden_ will show a list of all undone tasks that have deadlines before the specified date <br>

      Use case ends
      

2.1 User requests to list all tasks that are due within within a period of time specified by a start time and an end time <br>

2.2 _Unburden_ will show the user a list of undone tasks that have deadline that fall within the specified time period <br>

      Use case ends

3.1 User requests to list all tasks that are done <br>

3.2 _Unburden_ will show the user a list of tasks that have been marked "done" by the user <br>

      Use case ends
      
4.1 User requests to list all tasks that are overdue <br>

4.2 _Unburden_ will show the user a list of tasks that are overdue <br>

     Use case ends
     
5.1 User requests to list all tasks that are undone <br>

5.2 _Unburden_ will show the user a list of tasks that are undone <br>

     Use case ends





### Use case: Done task

##### MSS

1. User requests to mark a task as done by specifying the index of the task
2. _Unburden_ marks the task as done and removes it from the current list of pending tasks

     Use case ends

##### Extension 

1.1 User attempts to mark a task as done but enters the an index that does not exist <br>

1.2 _Unburden_ will show an error message saying that the input index is unspecified <br>

     Use case ends





## **Appendix C : Non Functional Requirements**

 1. _Unburden_ should run smoothly.
 2. _Unburden_ should be able to run all on all operating systems.
 3. _Unburden_ should be able to store up to 1000 tasks per user. 
 4. _Unburden_ should not take up a lot of space.
 5. _Unburden_ should be start up quickly.
 6. _Unburden_ should be able to process requests from the user in under 1ms.
 7. _Unburden_ should not crash.
 8. _Unburden_ should not do anything else other than what the user inputs.
 9. _Unburden_ should be able to recover from errors easily.
 10. _Unburden_ should be able to store the data safely when the application closes.


## **Appendix D : Glossary**

 1. UI - User Interface 
 2. API - Application Programming Interface 
 3. App - Application 
 4. IDE - Integrated Development environment 
 5. Repo - Repository
 6. CI - Continuous Integration



## **Appendix E : Product Survey**

Based on research done, below is a table of some of the applicable features and which applications offer them. In effect, our group intends to sieve out the good features while omitting the less popular ones from these applications and implement them into _Unburden_.

|   | **Todoist** | **Wunderlist** | **Any.do** | **Remember the milk** | **Clear** |
| --- | --- | --- | --- | --- | --- |
| **Easy to setup and manage** | _Yes_ | _Yes, easy to understand_ | _Yes but not as well as the Wunderlist_ | _Yes_ | _Yes, but can be more intuitive_ |
| **Able to collaborate with other people** | _Yes, able to share with other people_ | _Yes, able to share with other people_ | _Yes, able to share with other people_ | _Yes, able to share with other people_ | _Yes, able to share with other people_  |
| **Focuses on tasks with specific deadline** | _No_ | _No_ | _Yes_ | _No_ | _No_ |
| **Offline capabilities** | _Yes_ | _Yes, but some features are restricted_ | _Yes, but some features are restricted_ | _No_ | _No_ |
| **Able to navigate around using only command line** | _No_ | _No_ | _No_ | _No_ | _No_ |
