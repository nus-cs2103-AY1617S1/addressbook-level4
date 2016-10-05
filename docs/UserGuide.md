# User Guide

* [About](#about)
* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## About
Do you have so many tasks to do everyday that you simply cannot keep track of them anymore? Don’t you wish there was an easy way to stay on top of your daily tasks without stressing out?

*Taskle is here to help you with all of that.*

It is a task management application with a single text box for all your commands. 
Coupled with short and easy to remember commands, managing your tasks has never been this easy. 

Stop waiting and make “Getting Started with Taskle” the last thing on your to-do list now!

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `addressbook.jar` from the [releases](../../../releases) tab.
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


## Features

Taskle makes it elegantly simple to manage your tasks. All of its features require only one single line of command making it very easy to use! Furthermore, it will be easy for you to keep track of your tasks with no hassle at all!

> **Command Format**
> * Words in **`BOLD`** are the parameters.
> * Words in *`ITALICS`* represent different categories under the same command.
> * Items in `SQUARE_BRACKETS` are optional.
> * The order of parameters is fixed.

<br>

#### Adding a Task: `add`
Adds a task into the to-do application. Tasks can be classified into 3 categories: *Floating Tasks* (Tasks with no deadlines), *Deadlines* and *Events*.<br><br>
Format:  

Type | Format  
:-------- | :-------- 
Floating Tasks | `add `**`task_name`**  
Deadlines | `add `**`deadline_name`**` by `**`date`**`[`**`time`**`] [remind `**`date time`**`]`  
Events | `add `**`event_name`**` on `**`date`**`[`**`time`**`] [remind `**`date time`**`]` <br> OR <br> `add `**`event_name`**` from `**`date`**`[`**`time`**`] to ` **`date`**`[`**`time`**`] [remind `**`date time`**`]`  

Examples:
* `add `**`Pay Bills`**
* `add `**`Do CS2101 Assignment`**` by `**`12 Oct`**
* `add `**`Business Trip`**` from `**`4 Oct`**` to `**`5 Oct`**` remind `**`3 Oct 2pm`**

Note:  
* When entering date and time, the following formats are allowed:
	* 14 Jul, 14 July
	* 9pm, 2100
	* today, tmr

* Words such as **by**, **on**, **from** and **to** are reserved for commands. When adding tasks, if the name consists of any of the reserved words, they should be enclosed within double quotation marks, (" "). For example:
	* add "**Collect equipment from Mary**" **tmr**

* When using reserved words **today** and **tmr**, it is alright to omit the word **on** (applicable for events). For example:
	* add **Club Briefing tmr** &emsp; instead of:&emsp; add **Club Briefing on tmr**
<br><br>	

#### Editing a Task : `edit; reschedule; remind`
Edits a task into the to-do application. There are 3 types of edits possible: *Edit Description*, *Reschedule* and *Remind*.<br><br>
Format:

Type | Format  
:-------- | :-------- 
Edit Description | `edit `**`task_number new_task_name`**  
Reschedule | `reschedule `**`task_number`**` to `**`date`**`[`**`time`**`] [remind `**`date time`**`]`  
Remind | `remind `**`task_number`**` on `**`date time`**

Examples:
* `edit `**`3 Pass money to Abel`**
* `reschedule `**`Submit proposal `**` to `**`13 Oct 5pm`**
* `remind `**`1 3 Oct 2pm`**

Note:
* Only one reminder is supported for each task. The date and time specified in the "Remind" command will replace any existing reminder. To remove a reminder completely, you will have to type "**null**" after typing the task number. For example:
	* remind **3 null**
	
* Reminders need to have a **time** specified in order for us to know the exact time to remind you of your appointments.
<br><br>

#### Removing a Task: `remove`
Removes a task from the to-do application.<br><br>
Format: `remove `**`task_number`**

Examples:
* `remove `**`4`**
<br><br>

#### Finding a Task : `find`
Finds a task in the to-do application, based on keywords.<br><br>
Format: `find [`**`search_query`**`][`**`-p/-d-o`**`]`

Examples:
* `find `**`meeting`**
* `find `**`submission -o`**

Note:
* After typing the words to search for, you can type any of the following keywords to search for specific types of events:
	* **-p**: Tasks that are still pending.
	* **-c**: Tasks that are marked as completed.
	* **-o**: Tasks that are overdue (applicable for deadlines only)
* To view all types of events, you can simply omit the above keywords. 
* To display all tasks, simply type `find`
* To display all the tasks of a specific type, simply omit the **search_query** parameter while including the keyword mentioned above. For example:
	* `find `**`-p`**
<br><br>

#### Marking a Task as Done: `done`
Undo previous that may have been entered incorrectly.<br><br>
Format: `undo`
<br><br>

#### Undoing a Recent Command: `undo`
Marks a task as done. This command is used when you are finished with the task.<br><br>
Format: `undo`
<br><br>

#### Clearing all Tasks: `clear`
Clears all tasks from the application.<br><br>
Format: `clear`
<br><br>

#### Viewing Help: `help`
Views help for commands
Format: `help`

Note:
* Help is also shown if you enter an incorrect command. For example:
	* `abcd`

<br><br>

#### Exiting the Application: `exit`
Exits the application.<br><br>
Format: `exit`  
<br><br>


## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Address Book folder.
       
## Command Summary

The table below shows the overall list of commands used in the application.<br>
Note the following conventions used:
* Words in **`BOLD`** are the parameters.
* Words in *`ITALICS`* represent different categories under the same command.
* Items in `SQUARE_BRACKETS` are optional.
* The order of parameters is fixed.

Command (Shortcut) | Format  
:-------- | :-------- 
Add Task `a` | `add `**`task_name`**  
Add Deadline `a` | `add `**`deadline_name`**` by `**`date`**` [`**`time`**`] [remind `**`date time`**`]` 
Add Event `a` | `add `**`event_name`**` on ` **`date`**` [`**`time`**`] [remind `**`date time`**`]`<br>OR<br>`add ` **`event_name`**` from `**`date`** ` [`**`time`**`] to `**`date`**` [`**`time`**`] [remind `**`date time`**`]`  
Edit Description `e` | `edit `**`task_number new_task_name`**
Reschedule `r` | `reschedule `**`task_number`**` to `**`date`**` [`**`time`**`] [remind `**`date time`**`]`
Set Reminder `s` | `remind `**`task_number date time`**
Remove `rm` | `remove `**`task_number`**
Find `f` | `find [`**`search_query`**`] [`**`-p/-c/-o`**`]`
Mark as Done `d` | `done `**`task_number`**
Undo `u` | `undo`
Clear | `clear`
Help `h` | `help`
Exit | `exit`
