# User Guide

* [Quick Start](#quick-start)
* [Features](#features)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `forgetmenot.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Address Book.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/ForgetMeNot.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Words in angle brackets `<>` are parameters.
> * The order of parameters is fixed.

#### Finding more information about various commands: `help`
Displays the list of commands(along with their formats) for the user to help him use those commands.<br>
Format: `help`

The user can also view more information and examples of using a particular command.
Format: `help <Command Name>`

Example:
* `help add`
Displays more ways(eg: shortcuts) to use the add command 

#### Adding a Task: `add`
Adds a task to the task manager<br>
Format : `add <task name>`<br>
   `add <task name> d/<date/day>` <br>
   `add <task name> d/<date/day> t/<time>`<br>

Date Format: dd/mm/yy
Time Format: 12-hour clock ( Eg: 10am, 5pm, etc)

> If the date and time are not mentioned, the task is passed as a Floating Task.
> For the date/day, the user can type either the date, which would add the task to that specific day or type the day, which would add the task to the nearest day of the week which matches day.

Examples:
* `add Read Harry Potter`
* `add CS2103T Tutorial d/01/10/16`
* `add CS2101 reflection d/Friday` 
  (if you type this on a Wednesday, it marks the slot for the coming Friday, that is, day after tomorrow)
* `add EE2021 assignment d/next Thursday`
  (if you type this on a Wednesday, it marks the slot not for the next day, but for the Thursday after that, that is, 8 days later)
* `add EE2020 project d/30/10/16 t/6pm`

#### Priority tasks: `priority`
Allows the user to set the priority of a task when creating it. If the task is of importance, the user can assign it with a high priority. <br>
Format : `add <task name> priority high`

Example:
* `add CS2101 by 01/01/2016 priority high`

#### Undo a Task: `undo`
Undo the most recent task entered in the command line<br>
Format: `undo`

#### Deleting a Task: `delete`
Deletes a particular task in the task manager<br>
Format: `delete <task index>`

Example:
* `delete s1`

#### Finding a Task: `Find`
Finds tasks in the task manager. Task manager will display all task with the input keywords<br>
Format: `find <task name>`

Example:
*`find project`

#### Showing full list: `show`
Displays all the task for the user to view.<br>
Format: `show`

#### Showing list for today: `show today`
Displays all the task for today for the user to view.<br>
Format: `show today`

#### Showing list for tomorrow: `show tomorrow`
Displays all the task for tomorrow for the user to view.<br>
Format: `show tomorrow`

#### Showing list for upcoming: `show upcoming`
Displays all the task for upcoming task for the user to view.<br>
Format: `show upcoming`

#### Showing list for specific date: `show <date>`
Displays all the task for today the user to view.<br>
Format: `show <date>`

Example:
* `show 10/10/16`

#### View: `done`
Displays all the completed task on that day for the user to view.<br>
Format: `show done`

#### Editing a Task: `edit`
Allows the user to edit a particular task<br>
Format: `edit <task index> <detail to edit> <new value>`

Example:
* `edit 2 from 11am to 2pm`
* `edit 1 CS2103T Tutorial`
* `edit 2 from today 3pm to tomorrow 10am`

#### View all task: `home`
Returns to the default view of the application when it is opened.<br>
Format: `home`

#### Mark as done: `done`
Marks a task as done. <br>
Format: `done <task index>`

Example:
* `done 1`


#### Saving the data 
Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

