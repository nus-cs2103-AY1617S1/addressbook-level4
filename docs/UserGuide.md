# User Guide

* [Introduction](#introduction)
* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Introduction
This application is a task manager created to solve Jim's problems. Jim prefers typing over clicking, so you will primarily use Command Line Interface (CLI) for input. A Graphical User Interface (GUI) is present for your visual feedback.

This user guide covers the features of the application and has a short summary of commands at the end for your reference.


## Quick Start
0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.

1. Download the latest `taskmanager.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds. <br>
<div style="text-align:center" markdown="1">
<img src="images/UserGuide Mock up.png" title="Task manager GUI on start up" height = "800" width="500"><br>
<figcaption>Fig. 1: Task manager GUI on start up</figcaption>
</div>
4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`** `Midterms pr/high st/wednesday t/important` : adds a task "Midterms" to the Task Manger.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

Very often tasks in our daily lives have various parameters. For example, what to do , when to do it, what is it&#39;s priority and what is the venue.

In this application, you can record down tasks that contains the following parameters:

1. Task DESCRIPTION (what to do)
2. Task PRIORITY (what is it&#39;s priority)
3. Task STARTTIME(when does it start)
4. Task ENDTIME(when does it end)
5. Task TAGS(additional information)

**Adding a task**

General command format :

**add DESCRIPTION [pr/PRIORITY] [st/STARTTIME] [ed/ENDTIME] [t/TAG]...**   

- DESCRIPTION - can only contain alphanumeric characters

- PRIORITY - can only be either high, low or normal (If you don&#39;t specify PRIORITY, by default it will be normal.One task can only accept one PRIORITY value.)

- STARTTIME/ENDTIME

 Day can be in full or shorthand form (i.e. Wednesday, Wed or wed).
 Date in DD.MM.YYYY or D.M.YYYY format (i.e. 07.09.2016 or 7.9.2016).
 Time in 24 hr format HH:MM or H:MM (i.e. 08:00, 8:00)

- TAG – you can include any number of tags (including 0)

**Adding a simple task**

- Command format : **add DESCRIPTION**

- Adds a generic task with only the DESCRIPTION.

- Example :   **add organise room**

- Adds a task **organise room**  into the task manager.
PRIORITY is set to **normal** , all other parameters are empty.

**Adding a task with PRIORITY**

Command format : **add DESCRIPTION pr/PRIORITY**

Adds a task with its PRIORITY indicated.

Example :   **add get groceries pr/high****  **

Adds a task **get groceries**  with  **high**  PRIORITY to the task manager.

#### **Adding a task with deadline**

Command format : **add DESCRIPTION ed/ENDTIME**

Adds a task with its ENDTIME indicated (the task&#39;s ENDTIME is its deadline).

Example :

**add project ed/27.10.2016****  **

#### Adds a task **project****   **with deadline** 27.10.2016**.

        **add project ed/27.10.2016****  16:00**

#### Adds a task **project**  with deadline **27.10.2016 16:00**.

**add project ed/Wednesday****  16:00**

#### Adds a task **project**  with deadline **Wednesday 16:00**.

#### **Adding a task with STARTIME and ENDTIME**

Command format : **add DESCRIPTION st/STARTTIME ed/ENDTIME**

Adds a task with STARTTIME and ENDTIME.

Example :

**add Overseas vacation st/25.11.2016 ed/27.11.2016**

#### Adds a task **Overseas vacation**  with STARTTIME **25.11.2016  ** and ENDTIME **27.11.2016**.

**add Overseas vacation st/25.11.2016 08:00 ed/27.11.2016 18:00**

#### Adds a task **Overseas vacation**  with STARTTIME **25.11.2016  08:00** and ENDTIME **27.11.2016 18:00.**

#### **Adding a tagged task**

Command format :  **add DESCRIPTION t/TAG…**

Adds a task with a tag for additional information.

Example :   **add Dinner t/formal wear**

#### Adds a task **Dinner** with tag **formal wear**.

#### **Listing the tasks**

#### **Listing all tasks**

#### Command format : **list**

Tasks are listed in the order of your input.

**Listing all tasks by PRIORITY**

Command format : **list -pr**

Tasks with high PRIORITY are listed first, followed by normal PRIORITY, then low PRIORITY.

**Listing all tasks by start time**

Command format : **list -st**

Tasks will be listed by STARTTIME chronologically. Tasks without STARTIME will be listed at the end of the list.

**Listing all tasks by ENDTIME**

Command format : **list -ed**

Tasks will be listed by ENDTIME chronologically. Tasks without ENDTIME will be listed at the end of the list.

**Listing all the tags used**

Command format : **list tags**

Lists all the tags used in the task manager.

### **Deleting a task**

Command format : **delete INDEX**

**You have to list the tasks first to get the INDEX.**

Example : **delete 2**

        Deletes the 2nd task in the task manager.

**Finding tasks**

**Finding tasks with a keyword**

Command format : **find KEYWORD [MORE\_KEYWORDS]**

- The search is not case sensitive. e.g.  **Gives**  will match  **gives**.
- The order of the keywords does not matter. e.g.  **Give Eggs**  will match  **Eggs** Give.
- Only your task&#39;s DESCRIPTION is searched.
- Only full words will be matched e.g.  **Return**  will not match  **Returns**.

Examples :

**find Tutorial**

**Tutorial 8** is shown, but not **tutorial**.

**find lunch meeting**

Any tasks with DESCRIPTION that contains **lunch** and **meeting** will be returned.

**Finding tasks with specified PRIORITY**

Command format  : **find pr/PRIORITY**

Returns any tasks with the given PRIORITY.

Example : **find pr/high**

Returns any tasks with high PRIORITY.

**Finding tasks with a given start time**

Command format : find st/STARTTIME

Examples:

**        find st/tues**

Returns any task that starts after the current time of the specified day of the week, this Week.

**        find st/27.10.2016**

                Returns any task that starts after  **27.10.2016, 00:00**.

**        find st/16:00**

                Returns any tasks that starts after  **16:00 today**.

**Finding tasks with a given ENDTIME**

Command format : find ed/ENDTIME

**        find ed/tues**

Returns any task that ends before the current time of the specified day of the week, this Week.

**find ed/27.10.2016**

**               ** Returns any tasks that end **before 17.10.2016, 00:00**.

**find ed/16:00**

                Returns any tasks that end **before 16:00 today**.

**Finding tasks with a given tag**

Command format **: t/tag [MORE\_TAGS]**

Examples:

**find t/blue**

Returns any tasks with tag **blue**.

**find t/cheese salty**

Returns any tasks with tags **cheese** and **salty**.

**Updating a task**

General Format : **update INDEX PARAMETER NEW\_INPUT**

The INDEX refers to the task&#39;s listing index number.

Examples :

**        update 3 des/ go to school**

                Updates the listed third task&#39;s DESCRIPTION to &#39; **go to school&#39;**.

**        update 4 pr/high**

                Updates the listed third task&#39;s PRIORITY to **high**.

**        update 5 st/10:00**

                Updates the listed fifth task&#39;s STARTTIME to **10:00**.

**        update 6 ed/19:00**

                Updates the listed sixth task&#39;s ENDTTIME to **19:00**.

**Completing a task**

Format : **complete INDEX**

Examples: **complete 2**

        Marks the second task listed as &#39; **Completed**&#39;.

**Undo action**

Command format : **undo**

Undoes the most recent change (add/ delete/edit) from the task manager

**Clearing all tasks**

Command format : **clear**

Clears all tasks from the task managers

**Exiting the Program**

Command format : **exit**

Exits the program.

## FAQ

Q. How do I save data?                                                                           A. Task manager data are saved in the hard disk automatically after any command     that changes the data.There is no need to save manually.

Q. How do I transfer my data to another computer?                                                A. Install this app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Task Manager folder.

##

## Command Summary

| **Command** | **Format** |
| --- | --- |
| Add Task | add DESCRIPTION [pr/PRIORITY] [st/STARTTIME] [ed/ENDTIME] [t/TAG]... |
| Clear | clear |
| Complete | complete INDEX |
| Delete TAGS | deletetag INDEX TAG |
| Delete Task | delete INDEX |
| Edit | update INDEX PARAMETER NEW\_INFORMATION |
| Find Tasks | find KEYWORD [MORE\_KEYWORDS] |
| Find Tasks due by ENDTIME | find ed/ENDTIME |
| Find Tasks starting after STARTTIME | find st/STARTTIME |
| Find Tasks with PRIORITY | find pr/PRIORITY |
| Find tasks with TAGS | find t/TAG… |
| Help | help |
| List Tags | list tags |
| List all Tasks | list |
| List tasks by PRIORITY | List -pr |
| List tasks by STARTTIME | list -st |
| List tasks by ENDTIME | list -ed |
| Undo | undo |