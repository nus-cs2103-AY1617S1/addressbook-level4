# User Guide

* [Getting Started](#getting-started)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Getting Started

1. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
2. Download the latest `simply.jar` from the [releases](../../../releases) tab.
3. Copy the file to the folder you want to use as the home folder for your Simply.
4. Double-click the file to start the app. The GUI should appear in a few seconds. 
  

5. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 

6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

###4.1 Add Command
There are three variations to the add command. You are able to choose a task to be categorized under events, deadlines or to-dos. To differentiate the formatting for these commands, refer to the section below.

####4.1.1 Add an event
Format : **Add** [&lt;event description&gt;, &lt;date(DDMMYY)&gt;, &lt;start time&gt;, &lt;end time&gt;]

Example: Add [Siloso beach event, 121216, 1600, 2200]

>Note: The start time and end time is optional when adding an event. 
Important: The square brackets are compulsory when adding an event.

####4.1.2 Add a deadline
Format: **Add** &lt;deadline description&gt;, &lt;date(DDMMYY)&gt;, &lt;end time&gt;

Example: Add complete report, 120916, 1900

>Important: The date and end time are compulsory when adding an deadline

####4.1.3 Add a todo
Format: **Add** &lt;todo descrption&gt;

Example: Add go swimming

>Note: To do tasks do not have a date or time.

###4.2 Editing a task

The edit task function enables you to update the task description which includes start time, end time, tags and priority and category. By typing the command and the task index followed by [enter], Simply will output the task details into the command bar for editing. After editing and pressing [enter], Simply will make the necessary changes to the task.


Step 1. Format: Edit &lt;index&gt; [enter]

Shown in the Command Bar: Index task details 

Step 2. Format: Changes to task details [enter]

Example: Edit D1 [enter]

Command bar: CS2103 Report, 130516, 1200 (Original task details)

Changes:Report, 140516, 1200 #CS2103 [enter]

This will edit the current deadline task from CS2103 report to Report with an additional tag called #CS2103.

Example: Edit T1 [enter]

Command Bar: read toto-chan (Original task details)

Changes: read toto-chan, 140516, 1600 [enter]

This will edit the current todo task into a deadline task with the deadline on 140516 at 1600H.

###4.3 Search Task by Partial Keyword

The search by partial keyword command enables you to search for any events, deadlines, to-dos that have been added to Simply. If the searched task have not been entered, an error message will be shown.

Format: 

Search &lt;keyword&gt;

Example: 

Search project

Search 050316

Search 2359

Search #CS2103

###4.4 Marking Task as Complete 

The marking task as complete command enables you to mark the task as complete and hide the task.

Format: 

Complete &lt;index&gt;

Complete &lt;index&gt;-&lt;index&gt;

Complete &lt;index&gt;, &lt;index&gt;
    
Example: 
Complete T1

Complete T1-T3

Complete T1, T3

>Note: if you want to select more than one task to complete, you can separated the task by a - to complete all tasks that are within the range. In addition, you also separate the task by a , to individually delete them.

###4.5 Display completed task

The display command will display the completed tasks in their respective categories.

Format: Display

Example: Display

###4.6 Undo the Most Recent Commands

The undo command enables you to undo the most recent command that have been executed.

Format: Undo &lt;number of operations&gt;
Example: 
Undo

Undo 2

>Note: The undo command can only undo a maximum of 5 commands that have been executed

###4.7 Deleting task

The delete command enables you to delete the tasks that you no longer need.

Format: 
Delete &lt;index&gt;

Delete &lt;index&gt;-&lt;index&gt;

Delete &lt;index&gt;, &lt;index&gt; 

Example: 
Delete T3

Delete T1-T3

Delete T1, T3

>Note: The delete command is flexible and allows you to delete more than 1 task at a time. If the indexes entered are separated by a -, Simply will delete all tasks between the numbers including the numbers enter. If the indexes are separated by a , Simply will delete the tasks entered individually. 

>Note: If no number is being adding, the default number of times the command will undo is 1.

###4.8 Exiting the program

This command enables you to close the program.

Format: Exit

Example: Exit

###4.9 Help

The help command will display the commands and their functionalities.

Format: Help

Example : Help

###Command Summary

 No. | Command | Format 
 -------- | :-------- | :--------- | :-----------
1| Add | <ul><li>add [event_description, date, start_time, end_time]</li>
<li>add deadline_description, date, end_time</li><li>add to-do_description</li>
<li>add [event_description, date, start_time, end_time] #tag !</li></ul>
2| Edit | edit &lt;index&gt; &lt;new_event_description&gt; 
3| Search | search &lt;keywords&gt;
4| Complete | complete &lt;index&gt;&lt;index&gt;... 
5| Undo | undo &lt;number of times to undo, up to 5&gt; 
6| Delete | delete &lt;index&gt;&lt;index&gt; 
7| Exit | exit 
8| help | help 


