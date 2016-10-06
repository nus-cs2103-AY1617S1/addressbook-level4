# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ] (#faq) - not done 
* [Command Summary] (#command-summary) - not done 

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `taskmanager.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Address Book.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : lists all contacts
   * **`add`**` John Doe p/98765432 e/johnd@gmail.com a/John street, block 123, #01-01` : 
     adds a contact named `John Doe` to the Address Book.
   * **`delete`**` 3` : deletes the 3rd contact shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


### Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.

## Getting Started

1.Open the Application
2.You will see a welcome message and a list of commands that you can use.
3.At any time, you can view the list of commands again by typing ‘help’.
4.If you type an incorrect command, help-screen auto pops out 
	
## When you need help (To see a list of all commands)

1.Type ‘help’. 
2.Press Enter.
4.The list of commands, their format and their function will be shown.

## When you have a new deadline, task or event

Add a deadline
1.Type ‘add deadline n/NAME d/[DATE] t/[TIME]’.
2.If t is not specified, t is assumed to be 23:59.
3.If d is not specified, d is assumed to be today.
4.If both t and d are not specified, created task is a floating task (see b). 
5.Press Enter.
6.The deadline will be added to your to do list.

Add a task 
1.Type ‘add task n/NAME’. 
2.Press Enter.
3.The task will be added to your to do list.

Add an event 
1.Add an event by typing ‘add event n/[Name of Event] sd/[Start Date] st/[Start Time] ed/[End Date] et/[End Time]’.
2.If st is empty, st is assumed to be 00:00:00.
3.If et is empty, et is assumed to be 23:59:59.
4.If sd or ed is empty, sd or ed is assumed to be the current system date.
5.Press Enter.

##When you need to edit a deadline, task or event (Important Note: Tasks are autom)

Edit a task’s name - If you know the name of the task

1.Type ‘edit [taskname] rn/[New Name of Task]’. 
2.A confirmation message will appear to ask whether the task specified is the one you want to rename. 
3.Type ‘y’ to confirm and ‘n’ to cancel.

Edit a task’s name - If you know the keyword of the task

1.Type ‘searchAndEdit [keyword] rn/[New Name of Task]’. 
2.A confirmation message will appear for each of the tasks that contain the keyword to ask whether the task specified is the one you want to rename.
3.Type ‘y’ to confirm and ‘n’ to cancel for each message.

Edit a task’s name - If you know the index of the task 

1.Type ‘editByIndex [index] rn/[New Name of Task]’. 
2.A confirmation message will appear to ask whether the task specified is the one you want to rename.
3.Type ‘y’ to confirm and ‘n’ to cancel.

##Edit a task’s start date, start time, end date and end time

(NOTE: 
Conversion between events, deadlines, and tasks only involve the difference in details specified.)
For event, can edit both start and end date and time.
For deadline, can only edit end date and time.
For task, cannot edit end datetime.

If you know the name of the task

1.Type ‘edit [taskname] sd/[New Start Date] st/[New Start Time] ed/[New End Date] et/[New End Time]’. 
2.A confirmation message will appear to ask whether the task specified is the one you want to edit. 
3.Type ‘y’ to confirm and ‘n’ to cancel.

If you know the keyword of the task

1.Type ‘searchAndEdit [keyword] sd/[New Start Date] st/[New Start Time]  ed/[New End Date] et/[New End Time]’. 
2.A confirmation message will appear for each of the tasks that contain the keyword to ask whether the task specified is the one you want to edit. 
3.Type ‘y’ to confirm and ‘n’ to cancel for each message.

If you know the index of the task 

1.Type ‘editByIndex [index] sd/[New Start Date] st/[New Start Time]  ed/[New End Date] et/[New End Time]’. 
2.A confirmation message will appear to ask whether the task specified is the one you want to edit. 
3.Type ‘y’ to confirm and ‘n’ to cancel.

##When you need to view your deadlines, tasks and events

View all deadlines, tasks and events

1.View the entire todo list by typing ‘viewAll’. 
2.Press Enter.

View undone deadlines, tasks and events

1.View only undone items by typing ‘viewUndone’. 
2.Press Enter.

##When you can’t find a specific deadline, task or event

1.Search for a task or event by typing ‘search [keyword]’. 
2.Press Enter.
3.A list of corresponding deadlines, tasks, and events will be shown.

##When you have finished a task

If you know the name of the task

1.Type ‘done [taskname]’. 
2.A confirmation message will appear to ask whether the task specified is the one you want to mark as done. 
3.Type ‘y’ to confirm and ‘n’ to cancel.

If you know the keyword of the task

1.Type ‘searchAndDone [keyword]’. 
2.A confirmation message will appear for each of the tasks that contain the keyword. 
3.Type ‘y’ to confirm and ‘n’ to cancel for each message.

If you know the index of the task 

1.Type ‘doneByIndex [index]’. 
2.A confirmation message will appear to ask whether the task specified is the one you want to mark as done. 
3.Type ‘y’ to confirm and ‘n’ to cancel.

##When you want to delete a deadline, task or event

If you know the name of the task

1.Type ‘delete [taskname]’. 
2.A confirmation message will appear to ask whether the task specified is the one you want to delete.
3.Type ‘y’ to confirm and ‘n’ to cancel.

If you know the keyword of the task

1.Type ‘searchAndDelete [keyword]’. 
2.A confirmation message will appear for each of the tasks that contain the keyword to ask whether the task specified is the one you want to delete.
3.Type ‘y’ to confirm deletion and ‘n’ to cancel for each message.

If you know the index of the task
1.Type ‘deleteByIndex [index]’. 
2.A confirmation message will appear to ask whether the task specified is the one you want to delete. 
3.Type ‘y’ to confirm and ‘n’ to cancel.

##When you need to undo your last action
1.Type ‘undo’.
2.Press Enter. 
3.A confirmation message will appear to ask whether you want to undo a certain action. 
4.Type ‘y’ to confirm and ‘n’ to cancel.

## When you need to specify a different data storage location

To store in an absolute path

1.Type ‘store /path/to/storage/folder’.
2.Press Enter.

To store in a relative path

1.Type ‘store path/to/storage/folder’
2.Press Enter.

