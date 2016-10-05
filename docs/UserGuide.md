# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `lifekeeper.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Lifekeeper.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/UIprototype.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**` CS2103 T7A1 d/6 Oct 2016 p/2 r/5 Oct 2016 1800 t/teamC2` : 
     adds a task named `CS2103 T7A1` to the Lifekeeper.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is not fixed.

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`
 
#### Adding a person: `add`
Adds a task or category to Lifekeeper<br>
Format: `add TASK_NAME d/DUE_DATE p/PRIORITY_LEVEL r/REMINDER [t/TAG]...` 

> Tasks can have any number of tags (including 0)

Examples: 
* `add Grocery Shopping`
* `add Assignment 1 d/Tomorrow p/1 r/Today 2000`
* `add CS2103 T7A1 d/6 Oct 2016 p/2 r/5 Oct 2016 1800 t/teamC2`

#### Listing : `list`
Categories
Shows a list of all categories in LifeKeeper.<br>
Format: `list categories`

Tasks
Shows a list of tasks in Lifekeeper in the specified categories, if any.<br>
Format: `list tasks`

> All the tasks in Lifekeeper will be listed

Examples:
* `list tasks`
Lists all the tasks in Lifekeeper
* `list categories`
List all the categories that have been created by the user in Lifekeeper

#### Finding tasks by name, tag(s) or the range of due dates: `find`
Finding all tasks containing any keyword in their name
* Finds tasks whose names contain any of the given keywords.<br>
* Format: `find tasks KEYWORD [MORE_KEYWORDS]`

> * The search is not case sensitive. e.g `study` will match `Study`
> * The order of the keywords matters. e.g. `Assignment Due` will not match `Due Assignment`
> * Words containing the keywords will be matched e.g. `Exam` will match `Exams`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Shopping` will match `Clothes Shopping`

Examples: 
* `find tasks Homework Assignment`<br>
  Returns Any tasks with words containing `Homework`, `homework`, `Assignment`, or `assignment` in their names.

Finding all tasks containing a certain tag
* Finds tasks which has tags of given keywords attached to it.<br>
* Format: `find tags KEYWORD [MORE_KEYWORDS]`

> * The search is not case sensitive. 
> * Only full words will be matched.
> * Only tags matching the EXACT keyword will be returned.

Examples:
* `find tags CS2103`<br>
  Returns Any tasks containing the tag `CS2103` or `cs2103` but not `CS2103T` or `CS2103 Project`.

Finding all tasks which has deadlines falling within certain dates
* Finds tasks which has due dates falling between the specified range.<br>
* Format: `find dates STARTING_DATE ENDING_DATE`

> Date must be in the form `d MMM yyyy` or `d/MM/yyyy`

Examples:
`find dates 1 Oct 2016 31 Oct 2016`
  Returns Any tasks which has due dates falling in between 1st October 2016 and 31st October 2016.

#### Deleting a task: `delete`
Deletes the selected task from Lifekeeper. Irreversible.<br>
Format: `delete`

> Deletes the task with `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  

Examples: 
* `list`<br>
  `delete 1,3`<br>
  Deletes the 1st and 3rd task in the Lifekeeper task list.
* `find Dinner`<br>
  `delete 2`<br>
  Selects the 2nd person in the results of the `find` command and then deletes it.

#### Select: `select`
Category
Selects the category identified by the index number used in the last task listing.<br>
Format: `select INDEX`

> Selects the category at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...


Examples: 
* `list`<br>
  `select 2`<br>
  Selects the 2nd category in category list.

Task
Selects the task identified by the index number used in the last task listing.<br>
Format: `select INDEX`

> Selects the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...


Examples: 
* `list`<br>
  `select 2`<br>
  Selects the 2nd task in Lifekeeper.
* `find Dinner` <br> 
  `select 1`<br>
  Selects the 1st task in the results of the `find` command.
* `list category` <br> 
  `select 3`<br>
  `select 1`<br>
  Selects the 3rd category in the list and then select the 1st task shown in the category..


#### Editing a task: `edit`
Edits the selected task from Lifekeeper. Irreversible.<br>
Format: `edit [TASK_NAME] [c/CATEGORY] [d/DUE_DATE] p/PRIORITY_LEVEL r/REMINDER [t/TAG]...`

> Edits the task that was previously selected with `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  

Examples: 
* `list task work`<br>
  `select 2`<br>
  `edit assignment 5 c/school`<br>
  Edit the selected the 2nd task in the category work by changing its name to assignment 5 and category from work to school..
* `find Betsy`<br>
  `select 1`<br> 
  `edit d/15/06/2017`<br>
  Selects the 1st task in the results of the `find` command and then change the due date to 15/06/2017.


#### Clearing all entries : `clear`
Clears all entries from Lifekeeper.<br>
Format: `clear`  

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data 
Lifekeeper data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Address Book folder.
       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `add TASK_NAME d/DUE_DATE p/PRIORITY_LEVEL r/REMINDER [t/TAG]...` 
AddCat |`addcat CATEGORY_NAME`
Edit | `edit [TASK_NAME] [c/CATEGORY] [d/DUE_DATE] p/PRIORITY_LEVEL r/REMINDER [t/TAG]...`
Clear | `clear`
Delete | `delete [INDEX]...`
Find | `find KEYWORD [MORE_KEYWORDS]`
List | `list`
ListCat | `listcat`
Select | ‘select INDEX’
Done | `done`
Help | `help`
Exit | ‘exit’