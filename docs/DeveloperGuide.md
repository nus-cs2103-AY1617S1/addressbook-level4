Developer Guide

Table of contents 

Introduction
Setting Up
Design 
Implementation
Testing
Development Opportunity
Appendix A : User Stories 
Appendix B : Use Cases
Appendix C : Non Functionality Requirements 
Appendix D : Glossary 
Appendix E : Product Survey









Introduction

Overview 
Unburden is an application which will help you to manage your tasks better. Unburden provides a simple command line interface which does not require any form of clicking. Unburden is written in Java. 

Purpose
This developer guide focuses primarily on the APIs used, the different components that work together and the main 4 components of Unburden. This developer guide will clearly explain the design of the software and also to showcase the core functionalities of the software. As such, the main purpose of this developer guide is to provide a general introduction and summary of the classes and components that are used within this application to help prospective developers who wish to develop Unburden further.

















Audience
The main audience of this developer’s guide is directed towards the students who may be interested to further develop this application or even prospective team members. This developer guide may also interest developers who are interested to join the team. 

Level of Difficulty

The entire application uses a huge range of APIs and requires a deep understanding of:

 Java programming language
Since the entire piece of code is written in Java, prospective developers have to be adept with the Java programming language. In addition, APIs are used extensively throughout the entire code. 

Eclipse IDE 
The main code is very long and hence it requires an integrated development environment to help in the process of editing and testing.

JavaFX
Being familiar with JavaFX will enable prospective developers to edit the user interface smoothly.

In essence, having all of the skills above will definitely help to enhance the understanding of the code and hence allow future developers to add on to the existing code easily and efficiently.

Technical Jargon

Certain terms used within this developer guide may seem alien to some. Hence this section is dedicated to define those terms used here.
 
(As of now, we’ve not encountered any yet.)
Setting up
Prerequisites
JDK 1.8.0_60 or later
Having any Java 8 version is not enough. 
This app will not work with earlier versions of Java 8.
Eclipse IDE
e(fx)clipse plugin for Eclipse (Do the steps 2 onwards given in this page)
Buildship Gradle Integration plugin from the Eclipse Marketplace
Importing the project into Eclipse
Fork this repo, and clone the fork to your computer
Open Eclipse (Note: Ensure you have installed the e(fx)clipse and buildship plugins as given in the prerequisites above)
Click File > Import
Click Gradle > Gradle Project > Next > Next
Click Browse, then locate the project's directory
Click Finish




Design


The architectural design shows how the various components work in tandem with each other. Main only has one class MainApp and it is responsible for:

Starting up Unburden: Initializes all components in order and ensures that the app starts to run
Exiting Unburden: Shuts down all components in order and clears the memory

The rest of the app consists of 4 main components other than main. They are :
Logic : Decides what to output with the inputs and executes commands
Model : Holds the data during runtime
UI : Controls the UI of the app
Storage : Reads and writes data to the hard disk

Each component has a interface which all its classes implements and is named after the component itself.
UI component

The UI component focuses on interacting with the user by displaying the necessary information to the user when requested. It is also responsible for the outlook of the application. The UI component consists of the abstract UiPart class which is the base class for the UI parts and each “UI part” is represented by a distinct part of the UI such as the panels or status bars. 

In essence, the UI makes use of JavaFx UI framework and majority of the classes import javafx methods. The various layouts of each “UI part” are stores as .fxml files in the src/main/resources/view folder. These files are named according to the respective class names. For example, the layout of the HelpWindow.java is stored in src/main/resources/view/HelpWindow.fxml


API
The UI component consists mainly of:
UI class
UIManager class
UIPart class
UILoader class
BrowserPanel class
CommandBox class
MainWindow class
ResultDisplay class
HelpWindow class
 TaskCard class
 TaskListPanel class

These classes work together to form the interface which the user interacts with when using the app. Each of the class are meant to function solely on one part of the UI. For instance, the ResultDisplay class is responsible for displaying the results of a command from the user.


The UI also consists of a MainWindow class which is made up of these “UI parts” such as CommandBox, ResultDisplay, PersonListPanel, StatusBarFooter, BrowserPanel.
All of these classes, including the MainWindow class inherit from the abstract UiPart class. 

The UI component,
Executes user commands using the Logic component.
Auto-updates when data in the Model change.
Responds to events raised from various parts of the App
Updates the UI accordingly.










Logic component

The Logic component consists of the Parser class which is responsible to taking in the inputs from the UI component, deciphering it, and then creating a Command class that can handle the user’s input correctly. LogicManager will then execute the command.


API

The API of the Logic component consists mainly of:
Logic class
LogicManager class
Parser class
All the command classes eg. AddCommand, EditCommand, DeleteCommand

These classes work together to categorize the different possible inputs from the user and sieves the important keywords out so that Model can continue executing the command entered by the user.

The Logic component,
Logic takes in the user’s input and passes it to the Parser class
Parser class will decide which Command class is able to handle the request
LogicManager class takes the command and executes it by calling Model
TaskResult class is created and returned to the UI to be displayed to the user



Model component
The Model component is mainly responsible for executing the outputs from the Logic component. It is also responsible for storing all the in-app data such as the user’s preferences and data which is needed when executing commands.  

API

The API of the Model component is in the Model class which consists of the main features of the task manager such as ‘add’, ‘delete’ and updates the task manager accordingly. The ModelManager class, which represents the in-memory model of the task manager data, inherit from the Model interface. 

ModelManager is able to:
Store the user preference
Store Unburden’s data





Storage component
The Storage component primarily focuses on storing data. Any data related to the application will be saved within Storage and can be accessed later when requested. Storage works closely with Model to read and write data from the app as and when the user requests to add or show existing data. 

API

The API of the Model component consists mainly of:
Model class
ModelManager class
ListOfTask class
UserPref class
ReadOnlyListOfTask class

These classes are responsible for storing the data from the user and also works with the Model component to execute the commands given by the user.

Storage is able to:
Save the data entered in by the user and also read it back to Model when requested 
Save user preferences and read it back when needed





Testing
Tests can be found in the ./src/test/java folder.
In Eclipse:
To run all tests, right-click on the src/test/java folder and choose Run as > JUnit Test
To run a subset of tests, you can right-click on a test package, test class, or a test and choose to run as a JUnit test.
Using Gradle:
See UsingGradle.md for how to run tests using Gradle.
We have two types of tests:
GUI Tests - These are System Tests that test the entire App by simulating user actions on the GUI. These are in the guitests package.
Non-GUI Tests - These are tests not involving the GUI. They include,
Unit tests targeting the lowest level methods/classes. 
e.g. seedu.address.commons.UrlUtilTest
Integration tests that are checking the integration of multiple code units (those code units are assumed to be working).
e.g. seedu.address.storage.StorageManagerTest
Hybrids of unit and integration tests. These test are checking multiple code units as well as how the are connected together.
e.g. seedu.address.logic.LogicManagerTest


Headless GUI Testing : Thanks to the TestFX library we use, our GUI tests can be run in the headless mode. In the headless mode, GUI tests do not show up on the screen. That means the developer can do other things on the Computer while the tests are running.
See UsingGradle.md to learn how to run tests in headless mode.
Troubleshooting tests
Problem: Tests fail because NullPointException when AssertionError is expected
Reason: Assertions are not enabled for JUnit tests. This can happen if you are not using a recent Eclipse version (i.e. Neonor later)
Solution: Enable assertions in JUnit tests as described. 
Delete run configurations created when you ran tests earlier.















Development Opportunities

While this app has been extensively developed, we feel that there is still room for improvement and can therefore be improved further. As such, the main reason for this section of the developer guide is to suggest possible improvements to Unburden. 

For future and prospective developers or students who are interested to join our team, do note that the following list is not exhaustive.

Some possible additions to be made:
Allow this app to be run on a mobile device such as a mobile phone or tablet
Create a login system which will protect each user’s data and allow them to access their data from any device
Add animations so as to appeal to a larger audience
Implement a synchronous system based on a clock that will remind the user (possibly even when the application is not running) when he/she has a deadline approaching










User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`

Must-Have Features
Priority | As a ... | I want to ... | So that I can...
---------- | :-------- | :--------- | :-----------
`* * *` | new user | list all the commands | know how to use the program
`* * *` | new user | view a command | know how to use that particular command 
`* * *` | user | add a task |
`* * *` | user | delete a task | remove a completed task from the list
`* * *` | user | find a task by name/description |
`* * *` | user | list all tasks | know what are the tasks i have entered
`* * *` | user | edit any information of the task | 


Nice-To-Have Features

`* *` | user | undo previous command | remove any task that is entered wrongly.

May-Have Features

`*` | user | find history of tasks of certain past period of time




User Cases 

1) Use case: Add task
User requests to add a task
Unburden will add the task
Unburden will show the task added
Use case ends


2) Use case: Delete task
User requests to delete a specific task
Unburden will request the user to confirm 
User confirms with Unburden
Unburden will delete the task
Use case ends


3) Use case: Find task
User requests to find a specific task
Unburden will search through the library of existing tasks
Unburden will sieve out tasks that contain the keywords typed in
Unburden will show a list of the tasks 
User is able to remember what he/she needs to do
Use case ends





4) Use case: Edit task
User requests to edit a task
Unburden will search for the task based on the user’s input
User will type in the new task description
Unburden will request the user to confirm the changes
User will confirm with Unburden
Unburden will update the new changes
Use case: ends

























Non Functionality Requirements 

The app should run smoothly
The app should be able to run all on all operating systems
The app should be able to store up to 1000 tasks per user
The app should not take up a lot of space
The app should be start up quickly
The app should be able to process requests from the user in under 1ms
The app should not crash
The app should not do anything else other than what the user inputs
The app should be able to recover from errors easily

Glossary 
UI - User Interface
API - Application Programming Interface
App - Application
IDE - Integrated Development environment













Product Survey
Based on research done, below is a table of some of the applicable features and which applications offer them. In effect, our group intends to sieve out the good features while omitting the less popular ones from these applications and implement them into Unburden.




Todoist
Wunderlist
Any.do
Remember the milk
Clear
Easy to setup and manage
Yes
Yes, easy to understand
Yes but not as well as the Wunderlist
Yes
Yes, but can be more intuitive
Able to collaborate with other people
Yes, able to share with other people
Yes, able to share with other people
Yes, able to share with other people
Yes, able to share with other people
Yes, able to share with other people


Focuses on tasks with specific deadline
No
No
Yes
No
No
Using color tags to differentiate different tasks or lists
No
No
No
No
Yes, able to colour code tasks and lists so that it is easier to differentiate
Able to set certain tasks as “recurring” 
No
No
Yes, “recurring” tasks will continue to remind the user 
No
No
Able to navigate around using only command line
No
No
No
No
No





