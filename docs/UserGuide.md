# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

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


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`
 
#### Adding a task: `add`
Adds a task to the task management tool<br>
Format: `add TASK d/DESCRIPTIOM t/TITLE dd/DUE_DATE [t/TAG]...` 

> Tasks can have any number of tags (including 0)

Examples: 
* `add Homework d/ProgrammingEx1 t/Software Engineering dd/6 October`


#### Finding task : `find`
Shows a list of upcoming task in the task management tool.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`
Examples: 
* `find Homework`<br>
  Returns `Homework` but not `homework`


#### Deleting a task : `delete`
Deletes the specified task from the task management tool. Irreversible.<br>
Format: `delete KEYWORD`

> Deletes the task at the specified `KEYWORD`. 
  

Examples: 
* `delete Homework`<br> 

#### 


#### Adding task details: `editdetails`
Edit details to the existing task 
Format: `editdetails TASKID d/description t/title dd/duedate [t/TAG]...` 

Examples: 
* `editdetails 0 d/Programming t/SoftwareEngineering dd/10 October [t/TAG]...`

#### Setting task priority: `setpriority`
Set priority to a task
Format: `setpriority TASKID p/PRIORITY_VALUE` 

Examples: 
* `setpriority 0 p/10` 

#### Adding duplicate tasks for specific period of time: `addduplicate`
Set priority to a task
Format: `addduplicate INTERVAL TIMES d/description t/title dd/duedate [t/TAG]...` 

Examples: 
* `addduplicate 7 10 d/Programming t/SoftwareEngineering dd/10 October [t/TAG]...` 

#### Coloring task: `color`
Set color for a code of task
Format: `color TASKID c/COLOR_VALUE` 

Examples: 
* `color 0 c/RED` 

<img src="images/LeowYijin.jpg" width="150"><br>

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Address Book folder.
       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `add NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]...`
Clear | `clear`
Delete | `delete INDEX`
Find | `find KEYWORD [MORE_KEYWORDS]`
List | `list`
Help | `help`
Select | `select INDEX`
