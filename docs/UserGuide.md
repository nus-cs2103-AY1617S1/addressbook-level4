<!--@@author A0139024M-->
# User Guide

1. [Introduction](#1-introduction)
2. [Getting Started](#2-getting-started)
3. [Features](#3-features)
4. [FAQ](#4-faq)
5. [Command Summary](#5-command-summary)
6. [Credits](#6-credits)
7. [Appendix](#7-appendix)

## 1. Introduction 

&nbsp;&nbsp;&nbsp;&nbsp; Have you ever felt like there are too many tasks to do, and you are unable to remember all of them? Or feel that your calendar is overflowing with sticky notes on the tasks to be done each day? Have no fear, as our all-in-one task management application, sTask, is here to save your day! sTask is a revolutionary, state-of-the-art task management application engineered by a group of passionate software designers from the National University of Singapore. 

sTask manages the different types of tasks that you will encounter in your daily life, be it a deadline for submission, or even a date with your significant other, this application can show you what are all the tasks that you have in one glance, or even a list of tasks for a specific day. sTask also manages your list of To Do tasks, which are non-dated tasks such as "Read the new Harry Potter book!", and displays them beautifully and neatly at the left side, so that you can refer to them any time you are free.

Love typing? You will love sTask, as you only need to use the keyboard to type simple commands to manage your tasks.

## 2. Getting Started

### 2.1 Setting Up sTask

&nbsp;&nbsp;&nbsp;&nbsp; <b>2.1.1</b> Please ensure that you have Java version `1.8.0_60` or later installed in your Computer.<br>
 >> Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
&nbsp;&nbsp;&nbsp;&nbsp; <b>2.1.2</b> You can download the latest `sTask.jar` from our 'releases' tab. <br>
&nbsp;&nbsp;&nbsp;&nbsp; <b>2.1.3</b> You can then copy the file to the folder you want to use as the home folder for sTask. <br>
&nbsp;&nbsp;&nbsp;&nbsp; <b>2.1.4</b> You can double-click the file to start the app. The User Interface should appear in a few seconds. <br>

### 2.2 The Beautiful User Interface
<br>
       <img src="images/Ui.png" width="1000"> <br>

&nbsp;&nbsp;&nbsp;&nbsp;Referring to Figure 1 above, you can see that,<br>
* The Smart Command Input Bar is where you can type your commands. <br>
* The Interactive Message Display is where sTask gives you feedback and interacts with you. <br>
* The To Do Task Panel is where all your tasks without date and time are displayed in alphabetical order. <br>
* The Dated Task Panel is where all your events and deadlines are displayed in chronological order. <br>   

>> Tasks are colour coded according to their status : blue for completed tasks, red for overdue deadlines, pink for expired events, and white for the rest.<br>   
>> You can refer to the [Features](#3-features) section below for details of each command or you can refer to the [Command Summary](#5-command-summary).<br>


## 3. Features

&nbsp;&nbsp;&nbsp;&nbsp; In the following section, we will guide you along on you can use sTask effectively so that you can manage your tasks efficiently, and become a Champion in your life. <br>

&nbsp;&nbsp;&nbsp;&nbsp;<b>3.1 Looking for Help </b><br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;After downloading sTask, you are not sure how to operate it. You can<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;glance through the list of commands and their formats by typing.<br>
>> `help` <br><br>

&nbsp;&nbsp;&nbsp;&nbsp;<b>3.2 Adding a Task </b><br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;After receiving an email from Starbucks that you have won a free drink,<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;but you are unsure of when you are free. You can remind yourself by adding<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;a To Do by typing <br>
>> `add TASKNAME d/TASK_DESCRIPTION [t/TAG...]` <br>
* add Claim coffee d/Starbucks


&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Your boss gives you until next friday 5pm to submit the project proposal.<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;You can add it as a Deadline by typing<br>
>> `add TASKNAME d/TASK_DESCRIPTION date/DATE_TIME [t/TAG...]` <br>
* add Finish project proposal date/next friday 5pm t/important


&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Your client has arranged to meet you for 2 hours on 28th October 2016<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;and you can add it as an Event by typing<br>
>> `add TASKNAME d/TASK_DESCRIPTION date/DATE_TIME to DATE_TIME[t/TAG...]` <br>
* add Meeting with client d/Prepare documents date/28-10-2016 10am to 28-10-2016 12pm <br><br>

>> Refer to [Appendix](#7-appendix) for more information on the possible Date and Time formats accepted by sTask. <br><br>

&nbsp;&nbsp;&nbsp;&nbsp; <b>3.3 Listing your Tasks </b><br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; You can list all your uncompleted tasks by typing<br>
>> `list all` <br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; You can list all your completed tasks by typing<br>
>> `list done` <br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; You can list all your overdue and expired tasks by typing<br>
>> `list od` <br><br>

&nbsp;&nbsp;&nbsp;&nbsp;<b>3.4 Editing your Task </b><br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; You want to update some of the details of the tasks that you have.<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; You can just edit them by typing<br>
>> `edit INDEX TASKNAME d/TASK_DESCRIPTION date/DATE_TIME [t/TAG...]` <br>
* edit A1 Buy iced tea during lunch d/add lemon date/today noon <br>
* edit B1 date/next friday 8pm <br>
* edit B2 t/urgent<br><br>

>> Refer to [Appendix](#7-appendix) for more information on the possible Date and Time formats accepted by sTask. <br><br>

&nbsp;&nbsp;&nbsp;&nbsp;<b>3.5 Finding your Tasks </b><br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Being overwhelmed by the large number of tasks you have,<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; you can search for them by typing<br>
>> `find [KEYWORD...]` <br>
* find meeting <br><br>
sTask searches through all the fields and returns all the tasks that contains your KEYWORD.<br><br>


&nbsp;&nbsp;&nbsp;&nbsp;<b>3.6 Viewing your tasks on a specific date </b><br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; You have forgotten what task you were supposed to do today. <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; You can see all your tasks for today by typing<br>
>> `view DATE` <br>
* view today <br>
* view 22-10-2017 <br>
* view valentine day<br><br>

>> Refer to [Appendix](#7-appendix) for more information on the possible Date formats accepted by sTask. <br><br>
  
&nbsp;&nbsp;&nbsp;&nbsp;<b>3.7 Deleting your Task </b><br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Your client has called you to cancel the meeting, so <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; you can delete that event by typing<br>
>> `delete INDEX` <br>
* delete B1 <br><br>

&nbsp;&nbsp;&nbsp;&nbsp;<b>3.8 Undoing an action </b><br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; You can undo your previous action by typing<br>
>> `undo` <br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;You can only undo the following commands: `add`, `edit`, `delete`, `done`.<br><br>

&nbsp;&nbsp;&nbsp;&nbsp;<b>3.9 Redoing an action </b><br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; You can redo your previous `undo` by typing<br>
>> `redo` <br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;You can only redo if you do not use the following commands: `add`, `edit`, `delete`, `done`.<br><br>


&nbsp;&nbsp;&nbsp;&nbsp;<b>3.10 Completing a task </b><br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; After you claimed your free Starbucks coffee,<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; you can mark this task as complete by typing<br>
>> `done INDEX`<br>
* done A10 <br><br>

&nbsp;&nbsp;&nbsp;&nbsp;<b>3.11 Selecting a task </b><br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; You can select a task by typing<br>
>> `select INDEX`<br>
* select A11 <br><br>
You can use this command to navigate through your list of tasks.<br><br>

&nbsp;&nbsp;&nbsp;&nbsp;<b>3.12 Changing saved data location </b><br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; You want to access all your tasks from multiple devices on Dropbox, so<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; you can change the location of your saved data by typing<br>
>> `save FOLDERPATH`<br>
* save C:\Users\Jim\Dropbox<br><br>

&nbsp;&nbsp;&nbsp;&nbsp;<b>3.13 Exiting sTask </b><br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;After a hectic long day, it is time to rest, so you <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;can exit the program by typing<br>
>> `exit`<br><br>
  
## 4. FAQ

&nbsp;&nbsp;&nbsp;&nbsp;<b>4.1 General:</b><br>

**Q**: Do I need to save manually?<br>
**A**: Data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.<br>

**Q**: How do I transfer my data to another computer?<br>
**A**: Install sTask in the other computer by following the steps in Getting Started, and replace the newly created data file with your existing one.<br>

**Q**: Can sTask integrate with my Google Calendar?<br>
**A**: Currently, sTask is not able to synchronise your data with Google Calendar, as our application is optimised to work without an internet connection. However, we will be looking at implementing this as an optional feature in the near future, so be sure to keep a lookout for our future updates!<br>

**Q**: Will the status of my tasks get updated in real time?<br>
**A**: Currently, sTask only updates the statuses of your tasks when an action is made.<br>

&nbsp;&nbsp;&nbsp;&nbsp;<b>4.2 Commands:</b><br>

**Q**: Can I convert a To Do task to a deadline without deleting and adding it again?<br>
**A**: You can use the edit command to add a date to your To Do task and it will be accepted as a deadline.<br>

**Q**: Can I remove any of the fields in a specific task?<br>
**A**: You can use the edit command and just specify the field without any data and it will remove the corresponding the field. For example, to remove the description of the task at index A1, just type "edit A1 d/".<br>

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous sTask.<br>

If you have any further enquiries, drop us an email at sTask@sTask.com.sg!<br><br>
       
## 5. Command Summary

Command | Format | Description 
-------- | :-------- | :--------
Help | `help` | Opens the help page in a new window
Add | `add TASK_NAME [d/DESCRIPTION] [date/DATE_TIME] [t/TAGS]` | Adds a task
List | `list all/done/od`<br> `ls all/done/od` | Lists the desired tasks
Edit | `edit INDEX [TASK_NAME] [d/DESCRIPTION] [date/DATE_TIME] [t/TAGS]` | Edits a task
Find | `find KEYWORD[...]` | Finds and displays tasks with KEYWORD[...]
View | `view DATE` | Views the tasks on a specific date
Delete | `delete INDEX` <br> `del INDEX` | Deletes a task
Undo | `undo` | Undoes the last action
Redo | `redo` | Redoes the last undo
Done | `done INDEX` | Marks a task as complete
Select | `select INDEX` <br> `sel INDEX` | Selects and scrolls to the task
Save | `save FOLDERPATH` | Changes the location of the save file
Exit | `exit` | Exits sTask
<br>

## 6. Credits

We are grateful to Professor Damith Chatura Rajapakse and his team of highly dedicated tutors and project mentors, for giving us this opportunity to develop this product and for guiding us along the way.

&nbsp;&nbsp;&nbsp;&nbsp;<b>6.1 Source Code</b>
We would like to acknowledge the original source of our code, i.e., the sample Address Book project created by the se-edu initiative at  https://github.com/se-edu/ .


&nbsp;&nbsp;&nbsp;&nbsp;<b>6.2 External Libaries</b>
We would like to acknowledge the developers of Natty for their natural language date parser at http://natty.joestelmach.com/

<br>
## 7. Appendix

&nbsp;&nbsp;&nbsp;&nbsp;<b>7.1 Possible Date Formats</b><br>

Format | Example
-------- | :-------- 
DD-MM-YYYY  | 27-2-2101
DD MMM YYYY | 15 MAY 2103
Relative Date	| Today<br>Tmr<br>2 weeks later<br>Christmas<br>Valentines Day

&nbsp;&nbsp;&nbsp;&nbsp;<b>7.2 Rejected Date Formats</b><br>
Format | Example
-------- | :-------- 
DD-MM  | 27-02
DD-MM-YY | 27-02-10
DD.MM.YY | 27.02.10
DD.MM.YYYY | 27.02.2010

&nbsp;&nbsp;&nbsp;&nbsp;<b>7.3 Possible Time Formats</b><br>
Format | Example
-------- | :-------- 
24HR | 2359
AM/PM | 2.30pm
Relative Time | 2 hours later <br> 30 mins later

&nbsp;&nbsp;&nbsp;&nbsp;<b>7.4 Rejected Time Formats</b><br>
Format | Example
-------- | :-------- 
Time | 230pm 

This is not an exhaustive list of formats, please visit the following website for more information
http://natty.joestelmach.com/doc.jsp
