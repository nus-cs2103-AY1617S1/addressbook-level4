# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#FAQ)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `MESS.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your to-do list.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   <img src="images/mockup pic.jpg" width="600"><br>

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**`meeting`: adds a task named CS2103 Tutorial
   * **`find`**`meeting `: searches the task named tutorial   
   * **`delete`**`1`: delete the first task in the list
   * **`complete`** `1`: mark the first task as completed
   * **`update`**`1 presentation c/10/10/2016:1200` : updates first task on the list to presentation having a deadline on 10/10/2016 on 12:00 while the number '1' is the index of task on the list
   * **`undo`** : undo previous one action
   * **`pin`**`1` : pin the first task in the list
   * **`exit`** :exit the program
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

**Command Format**
* The command is case insensitive.
* The order of parameters is fixed.
* Words in `UPPER_CASE` are the parameters.
* Words in `SQUARE_BRACKET` are optional.
 
#### Adding a task or event: `add`
Adds a task to the to-do list<br>
Format: `add TASK_NAME [s/START_DATE:START_TIME c/CLOSE_DATE:CLOSE_TIME t/TAG]`

> Date format of START_DATE and CLOSE_DATE includes words like today, tomorrow, 3 days from now, day after tomorrow, noon, 12pm, 6am

* `TASK_NAME` need not be unique.
* If there is no argument, the task will become floating.
* `START_DATE` refer to the starting date and time of an event. For a task, the timestamp will be automatically saved as start date and time when the task is created. User can input start date and time for events.
* `TAG` is for users to write tags for different tasks. Mulitple tags are available by typing `t/TAG t/TAG`.


Examples:
* `add proposal c/10-10-2016:2100` <br> Adds a proposal task with a deadline on 10-10-2016 at 21:00
* `add meeting s/01-10-2016:1300 c/01-10-2016:2200`<br> Adds a meeting event which start on 1-10-2016 at 1 p.m. and ends at 10 p.m.
* `add shopping` <br> Adds a floating task named revision test which has not specify the start and end date
* `add tutorial t/cs2103` <br> Adds a flaoting task named tutorial with a tag CS2013
* `add quiz t/cs2102 t/easy` <br> Adds a flaoting task named tutorial with a tag CS2012 and easy

Examples on date time flexibility:
* `add project c/3 days from now` <br> Adds a project task three days later from the time you input this command

#### Deleting a task : `delete`
Deletes a specific task by task name or index from the to-do list.<br>
Format: `delete TASK_NAME` or `delete INDEX`

> * INDEX refers to the number appears on the list in front the task name.

Examples:
* `delete meeting`<br>
  Deletes `meeting` task.
* `delete 1`<br>
  Deletes the first task in the to-do list.

#### Marking a task as completed: `complete`
Marks a specific task by index from the to-do list.<br>
FormatL `complete INDEX`

> * INDEX refers to the number appears on the list in front the task name.

Example:
* `complete 2`<br>
   Marks the second task on the list as completed.
   
#### Pin: `pin`
Pin a important task.<br>
Format: `pin INDEX`

> * INDEX refers to the number appears on the list in front the task name.

Example:
* `pin 1`<br>
pin the first task to show that it is an important task.

#### Listing all persons : `list`
Shows a list of tasks and events in the todo list.<br>
Format: `list`

#### Finding all tasks and events containing keyword in their name: `find`
Finds tasks which have names containing any of the given keywords.<br>
Format: `find KEYWORD` or `find t/TAG`

> * The search is case insensitive. e.g `meeting` will match `Meeting`
> * The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
> * Only task name is searched.
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
	e.g. `Hans` will match `Hans Bo`

Examples:
* `find meeting`<br>
  Returns tasks having name `meeting` 
* `find t/cs2103`<br>
  Returns tasks having tag `cs2103`

#### Activate real time search: `searchbox`
Activates the real time search, which is located in the same input box used to input commands.<br/>
Format: `searchbox`

> * To exit from real time search, just hit <kbd>Enter</kbd>.
> * Similar functionality to `find`

#### Update entries : `update`
Update a specific task.<br>
Format: `update INDEX [TASKNAME s/START_DATE:START_TIME c/CLOSE_DATE:CLOSE_TIME t/TAG rt/TO_REMOVE_TAG]`

> * INDEX refers to the number appears on the list in front the task name.
> * The TAG here will be added to the referred task and the orginial tag remains. If you want to delete a tag, use `rt/TO_REMOVE_TAG` to delete tag by name.
> * TO_REMOVE_TAG refers to the tag (or tags) that you want to be removed by typing the tags' name that you want to delete.
> * You can choose what to update. It depends on you whether you want to update only one information or update multiple information. 

Examples:
* `update 2 shopping c/03/10/2016:2100`<br>
   update the taks name of the second task on the list to shopping and the start time to 3/10/2016 9 p.m.

* `update 1 t/cs2103`<br>
  add the tag of the first task on to-do list to cs2103

* `update 3 c/three hours later` <br>
  update the taks name of the third task on the list to a deadline three hours after you type this command
  
* `update 2 t/family rt/friends` <br>  
   add a tag family to the second task and remove the tag named friends

#### Undo action : `undo`
Undo the previous action.<br>
Format: `undo`

> * Will only undo `add`, `delete` and `update` actions.

#### Viewing help : `help`
Show the help menu. Format: `help`
> Help is also shown if you enter an incorrect command e.g. `123abc`

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data
To-do list data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ
**Q**: Can I add event which have a start date and time to my to-do list ?<br>

**A**: Yes, you can create an event by typing command with a start and end date. For example, you have a trip from 10/10/2016 8:00 to 13/10/2016 21:00. You can type command like this: `add trip s/8am 10th October c/9pm 13th October`.
       
**Q**: If I don't know the deadline of my task yet, can I still add my task?<br>

**A**: Yes, you can still add your task. You can create a floating task by only type in command `add TASK_NAME` if you don't know the deadline of your task.

> <img src="images/mockup pic2.jpg" width="600"><br>
In this example, you can see shopping is a floating task without a start time and deadline. 
  	
## Command Summary

Command | Format  
-------- | :--------
Add | `add TASK_NAME [s/START_DATE:START_TIME c/CLOSE_DATE:CLOSE_TIME t/TAG]`
Delete | `delete TASK_NAME` or `delete INDEX`
Complete | `complete INDEX`
List | `list`
Find | `find KEYWORD` or `find t/TAG`
Update | `update INDEX [TASKNAME s/START_DATE:START_TIME c/CLOSE_DATE:CLOSE_TIME t/TAG rt/TO_REMOVE_TAG]`
Undo | `undo`
Pin | `pin`
Help | `help`
Exit | `exit`
