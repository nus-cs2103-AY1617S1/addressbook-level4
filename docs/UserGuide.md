# User Guide

* [About](#about)
* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

##**About**
Inbx_0 (pronounced as Inbox Zero) is a personal task managing assistant that helps to keep your tasks in order. It will help you manage your email inbox easily so as to keep your inbox uncluttered. 

Unlike all the other task managers out there, Inbx\_0 is a simple program that runs on single-line commands. Inbx_0 will execute your commands based on what you type immediately. No more looking for buttons and menus to get the program to do what you want. So let's get started!

## **Quick Start**

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `inbx_0.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Manager
3. Double-click the Task Manager
4. Double-click the  Task Manager.jar file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/Ui.png" width="600">

5. Type a command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
6. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**:`add EE2020 Midterms s/10-11-2016, 0900 e/10-11-2016, 1100 i/red` :  adds a task called `EE2020 Midterms` to the Task Manager
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
7. Refer to the [Features](#features) section below for details of each command.<br>



## **Features**

All the features in Inbx_0 are performed by typing in commands in the command line. There is a certain command format for each command and the general format can be described as such: 

**Command Format**
1.  Words in `UPPER_CASE` are the parameters that are compulsory and will be taken in by the app (e.g. NAME, DATE). 
<br>
2. Items in `SQUARE_BRACKETS` are optional and will not be required in order to perform the command (e.g. [tags], [date]).
<br>
3. Items with `...` after them can have multiple instances. This means that there can be more than one occurrence of the particular item. For example, “[t/TAG]...” means that you can have multiple tags. 
<br>
4.  The order of parameters is fixed and needs to be strictly followed.

The rest of the guide will be using the general command format to describe what is needed to execute each command.

#### <a id="help"></a>Help Command
Format: `help`

Looking for help? If you ever need a reminder on how to input certain commands or have some troubleshooting issues, you can easily access the ‘help’ command which will guide you to the right direction.

To access the help command, type the following into the command line:
> help

This will show a list of available commands with detailed instructions on how to execute each command and any issues that you might face.

> The Help command will be also activated if you enter an incorrect or invalid command repeatedly e.g. `abcd`

<br><br>
 
#### <a id="add"></a>Adding a task: `add`
Adds a floating task.<br>
Format: `add TASK` 

> Floating tasks are tasks without deadlines.

Examples: 
* `add Buy Groceries` <br>

Adds a task with deadlines.<br>
Format: `add TASK e/END_DATE, END_TIME i/IMPORTANCE, [t/TAGS]` 

Examples: 
* `add Do CS2103 Homework e/tomorrow, 10am, i/red`
* `add Finish Project Paper e/1 March, 12am i/green` <br>

Adds an event.<br>
Format: `add TASK s/START_DATE, START_TIME e/END_DATE, END_TIME i/IMPORTANCE`

Parameters: <br>
TASK: Name of the task <br>
s/START_DATE: The date that the task will start on <br>
START_TIME: The time of the start of the task <br>
e/END_DATE: The date of the task that the task will end on <br>
END_TIME: The time of the end of the task <br>
i/IMPORTANCE: The priority of the task. Can be `green`, `yellow` or `red` <br>
t/TAGS: Tags that are assigned to the task

> Tasks can have any number of tags (including 0)

Examples:
* `add Doctor's appointment, s/2 July 2016, 5pm, e/2 July 2016, 7:30pm i/green`
* `add SO’s Birthday, s/29 Feb, 12am e/1 March, 12am i/green, t/flowers chocolates`

<br><br>

#### <a id="list"></a>Listing tasks: `list`
Format: `list`

The List Command shows a list of all tasks in the task manager so that you will be able to keep track of different tasks on different days.

You will be able to view all of today’s tasks and  tasks that are due before a specific date. There will be index numbers allocated at the side of each task which will be needed for other operations such as deleting a task or selecting a task.


Listing today’s task. <br>
Format: `list today`

In order to shows a list of the tasks due today in the task manager, you have to type the following in the command line <br>

> list today

This will bring up all your tasks in a list which is sorted accordingly to the time that the task will be starting. <br>

Listing tasks due before a specific date. <br>
Format: `list d/[DATE]`

By keying in the following, it will display a list of the tasks due before the input date in the task manager.

> list d/[DATE]

> acceptable formats of DATE input: 
>     today/tomorrow
>     this/next + MON/TUE/WED/THU/FRI/SAT/SUN
>     DD/MM/[YYYY]: YYYY refers to the year but it is optional, MM refers to month and DD refers to the  coming date in the most recent year

Examples:
* `list d/tomorrow` shows every task from now to the end of tomorrow
* `list d/01/01/2017` shows every task from now till the end of 1st January 2017

Listing tasks with a certain importance. <br>
Format: `list i/[IMPORTANT]`

By keying in the following, it will display a list of the tasks associated with the input importance.

> list i/[IMPORTANCE]

> acceptable formats of IMPORTANT input: 
>     1,2,3 -- where 1 means the most important tasks
>     red, yellow, green

<br><br>

#### <a id="find"></a>Finding all tasks containing any keyword in their name: `find`
Format: `find KEYWORD [MORE_KEYWORDS]`

The Find command will search all tasks which contain any of the given keywords.

> find KEYWORD [MORE_KEYWORDS]

> * The search is case sensitive. e.g `homework` will not match `Homework`
> * The order of the keywords does not matter. e.g. `Movie Night` will match `Night Movie`
> * Only the name is searched.
> * Only full words will be matched e.g. `visit` will not match `visiting`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Concert` will match `Michael Jackson Concert`

Examples: 
* `find meeting`<br>
  Returns `Lunch meeting` but not `Lunch Meeting`
* `find Fish Friday`<br>
  Returns Any task having containing `Fish`, or `Friday` (eg. ‘Visit Fish Market’ and ‘Listen to Rebecca Black’s Friday’)

<br><br>

#### <a id="edit"></a>Editing a task: `edit`
Format: `edit INDEX [n/NAME], [s/START_DATE], [st/START_TIME], [e/END_DATE], [st/END_TIME], [i/IMPORTANCE]`

Made a spelling mistake or your event was postponed? You can use the Edit Command to swiftly rectify any tasks on the task list.

This can be done by typing the following:

> edit INDEX [n/NAME], [s/START_DATE], [st/START_TIME], [e/END_DATE], [et/END_TIME], [i/IMPORTANCE]

Parameters: <br>
INDEX: the number that was linked to the task you wish to edit <br>
[n/NAME]: the name you wish to change to <br>
[s/START_DATE]: the start date you wish to change to <br>
[st/START_TIME]: the start time you wish to change to <br>
[e/END_DATE]: the end date you wish to change to <br>
[et/END_TIME]: the end time you wish to change to <br>
[i/IMPORTANCE]: the level of priority you wish to change to

Other than the INDEX which is required, only one of the rest of the parameters in the square brackets is required in order for the Edit Command to work.

Examples
* `list today`<br>
  `edit 2 s/tomorrow`<br>
  Changes the 2nd task in today’s list to start tomorrow

* `find meeting`<br> 
  `edit 1 n/Business Lunch st/1pm`<br>
  Changes the name of 1st task in the results of the `find` command to ‘Business Lunch at 1 pm’ 

<br><br>

#### <a id="del"></a>Deleting a task: `del`
Format: `del INDEX`

If you have incorrectly keyed in the task and wish to remove it, you can delete the specified task from the task list by typing the following: <br>

> del INDEX

This will remove the task from task list. Do note that such a deletion is irreversible and you should double check whether you are deleting the correct task.

> Deletes the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list today`<br>
  `del 2`<br>
  Deletes the 2nd task in today’s list.

* `find meeting`<br> 
  `del 1`<br>
  Deletes the 1st task in the results of the `find` command for ‘meeting’.

<br><br>

#### <a id="sel"></a>Select a task : `sel`
Format: `sel INDEX`

In order to view more details on a task that you have created, you can select the task identified by the index number in the last listing. The Select Command can be performed by typing:

> sel INDEX

This will automatically display the selected task and you will be able to see whether you have placed any reminders on the task. You will also be able to view tags that are associated with the task.

> Selects the task and loads the task at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `sel 2`<br>
  Selects the 2nd task in the task list.
  
* `find project`<br>
  `sel 1`<br>
  Selects the 1st task in the results of the `find` command.

<br><br>

#### <a id="undo"></a>Undo previous action: `undo`
Format: `undo`

Undos the previous action done in the task manager if you've made a mistake. <br>

> Cannot undo CLEAR commands!

<br><br>

#### <a id="clr"></a>Clearing all tasks : `clr`
Format: `clr`

Tasks can easily become obsolete and checking off tasks individually can be quite a hassle. The Clear command will help you to remove all selected tasks and can be accessed by typing the following:

> clr [DATE]

Adding the date in the command line  is optional and by default it will clear the tasks that were scheduled today. By including the date, this will enable you to clear tasks from other days as well.

> Please ensure that the tasks are the ones that you want to clear before initiating the Clear Command.

<br><br>

#### <a id="done"></a>Mark a task as `done`
Format: `done + INDEX`

If you have finished a certain task and wish to mark it as finished you can give a `done` label to the specified task in the task list by typing the following: <br>

> done INDEX

This will label a task as `done`, when you display the task, you will be reminded that you have finished the specified task.

> The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Example: 
* `find today` <br>
  `done 2` <br>
 Gives a `done` label to the 2nd task in today’s list

<br><br>

#### <a id="exit"></a>Exiting the program : 
Format: `exit`

After using Inbx_0, you can easily exit the program by typing the following in the command line:

> exit

This will initiate a final save and after which, the program will close automatically.  

<br><br>

#### Saving the data 
Task Manager data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## **FAQ**

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Address Book folder.

**Q**: How do check if I have the correct Java Version?<br>
**A**: 
 1. Click Start on the task bar.
 2. Select Control Panel (or Settings > Control Panel) from the Start menu. The Control Panel is displayed.
 3. Select Java. The Java Control Panel dialog box is displayed .
 4. NOTE: if the Control Panel is in Category mode and you cannot see the Java option, switch the Control Panel to Classic View.
 5. Click the Java tab.
 6. In the Java Application Runtime Setting box, click View. The JNLP Runtime Settings dialog box is displayed.

       
## **Command Summary**

Command | Format  
-------- | :-------- 
[Help](#help) | `help`
[Add](#add) | `add NAME`
&nbsp; | `add NAME e/END_DATE, END_TIME i/IMPORTANCE, [t/TAGS]...`
&nbsp; | `add NAME s/START_DATE, START_TIME e/END_DATE, END_TIME i/IMPORTANCE, [t/TAGS]...`
[List](#list) | `list d/[DATE]`
&nbsp; | `list i/[IMPORTANCE]`
[Find](#find) | `find KEYWORD [MORE_KEYWORDS]`
[Edit](#edit) | `edit INDEX [n/NAME], [s/START_DATE], [st/START_TIME], [e/END_DATE], [et/END_TIME], [i/IMPORTANCE]`
[Delete](#del) | `del INDEX`
[Select](#sel) | `sel INDEX`
[Undo](#undo) | `undo`
[Clear](#clr) | `clr [DATE]`
[Done](#done) | `done INDEX`
[Exit](#exit) | `exit`


