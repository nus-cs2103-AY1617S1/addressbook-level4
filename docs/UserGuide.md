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
   > <img src="images/UIProject.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : lists all contacts
   * **`add`**` Homework  d/MathTut sd/09082016 dd/08102016` : 
     adds a task named `Homework` to the Task Management Tool.
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.

### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`
 
### Adding a task: `add`
Adds a task to the task management<br>
Format: `add TITLE d/DESCRIPTION [sd/START_DATE] [dd/DUE_DATE] [i/INTERVAL] [ti/TIME_INTERVAL] [-t TAG 1, TAG 2...] ...` 

> Tasks can have any number of tags (including 0)

Examples: 
* `add Homework d/ProgrammingEx1`

#### Adding an event
Adds a task which has a start time and end time. 
User has to specify TITLE, DESCRIPTION, START_DATE and DUE_DATE.
* `add Birthday Party d/Edwin's birthday party sd/08-01-2012 10:00 dd/08-01-2012 23:00`
* `add Soccer match d/Champion League sd/08-01-2012 18:00 dd/08-01-2012 20:00 i/8 ti/7`

#### Adding a deadline
Adds a task that have to be done before a specific deadline. 
User has to specify TITLE, DESCRIPTION and DUE_DATE.
* `add Homework d/ProgrammingEx1 dd/10-01-2012`
* `add Take home Lab d/ProgrammingEx1 dd/10-01-2012 23:59 i/4 ti/7`

#### Adding a floating task
Adds a task without specific times. 
User has to specify TITLE, DESCRIPTION.
* `add Homework d/ProgrammingEx1`
* `add Assignment d/Math i/4 ti/7`

#### Adding duplicated tasks
Adds a task with specific interval. START_DATE and DUE_DATE(if specified) will by adjusted accordingly to interval. 
User has to specify INTERVAL or TIME_INTERVAL.
* `add Homework d/ProgrammingEx1 i/2 dd/10-01-2012`
* `add Homework d/ProgrammingEx1 i/2 ti/10 dd/10-01-2012`


### List tasks : `list`
Shows a list of upcoming task in the task management tool.<br>
Format: `list`
Examples: 
* `list`<br>
  Returns `Homework` but not `homework`


### Deleting a task : `delete`
Deletes the specified task from the task management tool. Irreversible.<br>
Format: `delete TASKID`

> Deletes the task at the specified `KEYWORD`. 
 
Examples: 
* `delete 1`<br>

#### Change the file directory stored: `save`
Change the folder path name
Format: `save folderpath`

Example:
* `save C:\\Users\\<username>\\Desktop\\CS2103 Tutorial`.

#### Add Category: `Assign`
Assign Task to a category
Format: `Assign TASKID c/CATEGORY`

Example:
* `Assign 1 c/Work`

#### Complete a task: `done`
Set Task as completed
Format: `done TASKID`

Example: `done 0`
 
#### Edit a task: `edit`
Edit details to an existing task
Format: `edit TASKID t/TASK_NAME d/DESCRIPTION sd/START_DATE dd/DUE_DATE ts/TAGS`

Examples: 
* `edit 1 t/Homework d/Physics sd/11-11-2011 dd/12-12-2012 ts/school`

#### Setting task priority: `setpriority`
Set priority to a task
Format: `setpriority TASKID p/PRIORITY_VALUE` 

Examples: 
* `setpriority 0 p/10` 

#### Coloring task: `color`
Set color for a code of task
Format: `color TASKID c/COLOR_VALUE` 

Examples: 
* `color 0 c/RED` 



## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Address Book folder.
      
## Command Summary

Command | Format  
-------- | :-------- 
Add | `add TASK d/DESCRIPTION dd/DUE_DATE i/INTERVAL ti/TIME_INTERVAL...`
Edit | `edit TASKID t/TASK_NAME d/DESCRIPTION sd/START_DATE dd/DUE_DATE ts/TAGS`
Clear | `clear`
Delete | `delete TASKID`
Find | `find KEYWORD [MORE_KEYWORDS]`
List | `list`
Save | `save`
Help | `help`
Color | `color`

## Parameters 
Parameter     | Flag  |  Format           | Required                          | Meaning
TITLE         |       |  Text             |   Yes                             | Title of a task 
DESCRIPTION   |  /d   |  Text             |   Yes                             | Description of a task
START_DATE    |  /sd  |  Date(DD-MM-YYYY) |   NO                              | Start date of a task
DUE_DATE      |  /dd  |  Date(DD-MM-YYYY) |   YES(if START_DATE is specified) | Due Date of a task
INTERVAL      |  /i   |  Integer Number   |   NO                              | Interval of a task specifies how many times should be duplicated
TIME_INTERVAL |  /ti  |  Integer Number   |   NO                              | Time interval of a task specifies how many days are between duplicated tasks

