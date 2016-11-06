# User Guide


* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)


<br>
## Quick Start


0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest version `.jar` file from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Emeraldo
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="../assets/UI_overview.png" width="1000"><br>


4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
   
5. Refer to the [Features](#features) section below for details of each command.<br>


<br>
## Features


> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.


<br>
#### Viewing help: `help`
Format: `help`


> * Help document will pop up and you can look for the necessary information regarding each command
> * Assistance also be provided if you enter an incorrect command e.g. `abcd`
 
<br>
[](@@author A0139749L)
#### Adding a task: `add`
Adds a task to the Emeraldo <br>
Format: `add "TASK_DESCRIPTION" [on DATE] [by DEADLINE_DATE_AND_TIME] [from START_DATE START_TIME] [to END_DATE AND_TIME] [#TAGS]...`


<br>


Type of task to be added | Examples
:------------------------|:----------
For a task with no date or time specified, only the task description is required to be specified.|`add "Do laundry"`
For a task with date but no time specified, it would be taken be as an all day event.|`add "Gyming with Jim" on 23 May 2016`
For a task with a deadline, the date and time must be specified.|`add "Do tutorial for EE module" by 5 May, 2pm`
For a scheduled task occurring over a period of time, the start and end date and time must be specified.|`add "Leadership workshop" from 3 Jun, 2pm to 3 Jun, 4pm`
For any tasks with tags, specify the tags as the last parameter.|`add "James wedding" on 30 Jun #Important`


>Tasks can have any number of tags or none at all

<br>

Accepted entry formats | Examples
:----------------------|:----------
Date formats | 4/03/2016 , 4/03/16 , 4-03-16 , 4 March 16 , 4/03 , 4 Mar
Time formats | 14:20 , 14.20 , 1420 , 2.20pm , 2:20pm 


<br>
[](@@author A0139342H)


#### Listing all tasks (both uncompleted and completed): `listall`
Shows a list of all tasks in the Emeraldo.<br>
Format: `listall`


<br>


#### Listing all tasks (both uncompleted and completed) by categories or tags: `listall`
Shows a list of all tasks in the Emeraldo according to a stated category or by tag. <br>
Format: `listall CATEGORY` or `listall TAG`


> * Categories include: today, tomorrow, completed, thisweek, nextweek, thismonth, nextmonth. <br>
> * Tags can be anything the user has input when adding a task.


Examples (category):
* `listall today`
* `listall tomorrow`
* `listall completed`


Examples (tag):
* `listall family`
* `listall work`
* `listall school`


<br>


#### Listing all uncompleted tasks: `list`
Shows a list of all uncompleted tasks in the Emeraldo.<br>
Format: `list`


<br>
#### Listing all uncompleted tasks by categories or tags: `list`
Shows a list of all uncompleted tasks in the Emeraldo according to a stated category or by tag. <br>
Format: `list CATEGORY` or `list TAG`


> * Categories include: today, tomorrow, completed, thisweek, nextweek, thismonth, nextmonth. <br>
> * Tags can be anything the user has input when adding a task.


Examples (category):
* `list today`
* `list tomorrow`
* `list completed`


Examples (tag):
* `list family`
* `list work`
* `list school`


<br>
#### Finding all tasks containing any keyword in their title: `findall`
Finds all tasks whose titles contain any of the given keywords.<br>
Format: `findall KEYWORD [MORE_KEYWORDS]`


> * Finds all tasks that has the keyword in the task title, and shows in the list sorted in index order.
> * The search is not case sensitive, e.g. `homework` will match `Homework`.
> * The order of the keywords does not matter. e.g. `to do homework` will match `homework to do`.




Examples: 
* `findall homework`<br>
  Returns `homework/Homework/HomeWoRk`
* `findall dinner meeting project`<br>
  Returns any task having names `dinner`, `meeting`, or `project`


<br>
[](@@author A0139196U)
#### Finding all uncompleted tasks containing any keyword in their title: `find`
Finds uncompleted tasks whose titles contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`


> * Finds all uncompleted tasks that has the keyword in the task title, and shows in the list sorted in index order.
> * The search is not case sensitive, e.g. `homework` will match `Homework`.
> * The order of the keywords does not matter. e.g. `to do homework` will match `homework to do`.


Examples: 
* `find homework`<br>
  Returns `homework/Homework/HomeWoRk`
* `find dinner meeting project`<br>
  Returns any task having names `dinner`, `meeting`, or `project`


<br>
[](@@author A0142290N)
#### Editing a task: `edit`
Edits the specified task from Emeraldo.<br>
Format: `edit INDEX ["TASK_DESCRIPTION"] [on DATE] [by DEADLINE_DATE_AND_TIME] [from START_DATE START_TIME] [to END_DATE AND_TIME]`


> * Task will be edited given the new parameter(s), and updated instantly.<br>
> * Requires at least 1 type of parameter to be passed in.


Examples:
* `list`<br>
  `edit 3 "Order pizza, netflix & chill"`<br>
  Edits task description with index 3 in the list
* `find KEYWORDS`<br>
  `edit 1 by 10/11/2016` <br>
  Edits task deadline with index 1 in the list


<br>
[](@@author A0139196U)
#### Editing a task’s tag: `tag add/delete/clear`
Edits the specified task’s tag in Emeraldo.<br>
Format: `tag add/delete/clear INDEX [#TAGS]`


> * Task’s tags will be edited given the new parameter, and updated instantly.<br>
> * Parameters cannot be one of the following reserved words: today, tomorrow, completed, thisweek, nextweek, thismonth, nextmonth.<br>
> * For add/delete, requires one parameter to be passed in as tag.<br>
> * For clear, parameter to be passed in is not required.


Examples:
* `list`<br>
  `tag add 5 #friends`<br>
  Adds the tag (#friends) to the task with index 5 in the list
* `list work`<br>
  `tag delete 3 #work`<br>
  Deletes the tag (#work) from the task with index 3 in the list
* `list`<br>
  `tag clear 2`<br>
  Clears all tags from the task with index 2 in the list


<br>
[](@@author A0139749L)
#### Deleting a task: `delete`
Deletes the specified task from Emeraldo.<br>
Format: `delete INDEX`


> * Task will be removed from the list.


Examples: 
* `list`<br>
  `delete 2`<br>
  Deletes task with index 2 in the list
* `find KEYWORDS`<br>
  `delete 459`<br>
  Delete task with index 459 in the list


<br>
[](@@author A0139342H)
#### Undo a command: `undo`
Undo the previous action. <br>
Format: `undo`


> * Emeraldo will revert the last action done.


Examples: 
* `delete 2`<br>
  `undo`<br>
  Restores task which had an index of 2 back to the list
* `add "do housework"`<br>
  `undo`<br>
  Deletes task of "do housework"


<br>
[](@@author A0139196U)
#### Mark task as complete: `completed`
Marks a task as completed.<br>
Format: `completed INDEX`


> * Emeraldo will mark task as completed and the date of completion will be shown instead of the due date set earlier


<br>
[](@@author A0142290N)
#### Clearing all entries: `clear`
Clears all entries from the Emeraldo in the save data.<br>
Format: `clear`  

<br>
#### Motivate Me: `motivateme`
Generates random quote to motivate a stressed user <br>
Format: `motivateme`

<br>
[](@@author A0139342H)
#### Change save location command: `saveto`
Changes the save location of the emeraldo.xml file. <br>
Format: `saveto FILEPATH`


> * Emeraldo change the save location according to FILEPATH.


Examples:
* `saveto ./`<br>
  Changes the save location to the folder where Emeraldo.jar is. 
* `saveto ./newFolder/`<br>
  Changes the save location to newFolder which is in the location where Emeraldo.jar is.
* `saveto c:/newFolder/`<br>
  Changes the save location to newFolder which is in C drive.
* `saveto default`<br>
  Changes the save location to the default location which is ./data/.

<br>
[](@@author A0139196U)
#### MotivateMe command: `motivateme`
Generates and display a motivational quote for the user. <br>
Format: `motivateme`


> * Emeraldo will display a random motivational quote to cheer on the user.


Examples:
* `motivateme`<br> 

<br>
[](@@author A0139342H)
#### Exiting the program: `exit`
Exits the program.<br>
Format: `exit`
[](@@author)


<br>
#### Saving the data 
Emeraldo data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.


> * The file name must end in `.xml` for it to be acceptable to the program.
>
> * When running the program inside Eclipse, you can set command line parameters before running the program.


<br>
## FAQ


**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Task Manager folder.


**Q**: How do I upload Emeraldo to the latest version? <br>
**A**: Download the latest ‘.jar’ file from the [releases](../../../releases) tab and replace your current ‘.jar’ file with it.


**Q**: I cannot access the Help document.<br>
**A**: Check your internet access. Internet connection is required to access the help document.


**Q**: How do I scroll through tasks without using the mouse?<br>
**A**: Use alt + Tab to navigate the various fields to the box containing the tasks, then use the up and down arrow keys to scroll through the tasks.


<br>
[](@@author A0139196U)      
## Command Summary (listed in alphabetical order)


Command | Format  
-------- | :-------- 
Add | `add "TASK_DESCRIPTION" [on DATE] [by DEADLINE_DATE, DEADLINE_TIME] [from START_DATE, START_TIME] [to END_DATE, END_TIME] [#TAGS]...`
Clear | `clear`
Completed | `completed INDEX`
Delete | `delete INDEX`
Edit | `edit INDEX ["TASK_DESCRIPTION"] [on DATE] [by DEADLINE_DATE, DEADLINE_TIME] [from START_DATE, START_TIME] [to END_DATE, END_TIME]`
Exit | `exit`
Find | `find KEYWORD [MORE_KEYWORDS]`
FindAll | `findall KEYWORD [MORE_KEYWORDS]`
Help | `help`
List | `list [CATEGORIES]` or `list [TAGS]`
ListAll | `listall [CATEGORIES]` or `listall [TAGS]`
SaveTo | `saveto FILEPATH`
Tag | `tag add/delete/clear INDEX [#TAGS]`
Undo | `undo`
