# User Guide
Are you feeling stressed or overwhelmed with the number of things you have to do? Are you drowning in the pool of to-dos and confused about where to start? WhatNow is here to throw you a lifebuoy and to help you manage all your tasks.

Now that you know WhatNow is [about](../README.md), you can follow this guide to learn how to use WhatNow effectively.

Welcome to WhatNow! 

# Table of Contents
* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

1. Ensure that you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
2. Download the latest `WhatNow.jar` from the [releases](../../../releases) tab.
3. Copy the file to the folder you want to use as the home folder for your WhatNow.
4. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/UI_Prototype/WhatNowUI-Welcome-Colour-WithLabels.png" width="600"><br>
   > Figure 1: How the GUI will look like when using WhatNow for the first time. <br>

5. Type a command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** then press <kbd>Enter</kbd> will open the help window. 
6. Some example commands you can try:
   * **`list`** : lists all tasks in WhatNow.
   * **`add`**` Buy groceries` : adds a task called `Buy groceries` to WhatNow.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list.
   * **`exit`** : exits WhatNow.
7. Refer to the [Features](#features) section below for details of each command.<br>

## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.


#### Changing storage location : `change`
Changes the data file storage location. This action is reversible.<br>
Format: `change location to PATH`<br>
Result: `The data storage location has been successfully changed to PATH` message will be shown in the feedback box. <br>

Examples:
* `change location to C:\Users\Verbena\Dropbox\WhatNow`<br>
Changes the data file storage location to C:\Users\Verbena\Dropbox\WhatNow<br>
<img src="images/UI_Prototype/WhatNowUI-ChangeLocation-Colour.png" width="600"><br>
 Figure 2: How the GUI will look like when the storage location is changed.<br>



#### Adding a task: `add`
Adds a task to WhatNow. This action is reversible.<br>
Format: `add "DESCRIPTION" [on/by] [today/tomorrow/DAY/DATE] [from/at] [START_TIME] [to/till] [END_TIME] [every] [day/week/month/year/DAY] [low/medium/high]` <br> 
Result:`Task has been successfully added: <Task added> ` message will be shown and added task will be highlighted in the list for 3 seconds.<br>  

> * All task description should be written within double quotation marks.<br>
Eg Add “buy eggs”. <br>
> * The format for date must be *day month year* where year is optional or dd/mm/yyyy. If no year is specified, it is assumed to be the current year.<br>
Eg: 10 Oct, 4 November, 11 August 2017, 12/12/2016, 2/12/2016, 12/4/2016. Not 12/12/16. <br>
> *  If no date is specified, it is assumed to be today.<br>
> * The date cannot be a date in the past.
> * Time should be in the *12 hour format*.<br>
Eg: 10am, 6pm, 7.30pm, 7:30pm. Not 2359.

Examples:
* `add "Buy chocolate milk"`<br>
Adds a Todo task “Buy chocolate milk”.
* `add "Go NTUC" every Saturday medium`<br>
Adds a Scheduled task “Go NTUC” of medium priority on every Saturday.
* `add "CS2103 Project" by 20/11/2016 high`<br>
Adds a Scheduled task “CS2103 Project” of high priority on 20 November 2016. 
* `add "Buy pizza" at 7.30pm`<br>
Adds a Scheduled task “Buy pizza” today at 7.30pm. 
* `add "Study CS2105" from 2pm to 6pm`<br>
Adds a Scheduled task “Study CS2105” today from 2pm to 6pm. 
* `add "Study CS2106" tomorrow from 2pm to 6pm`<br>
Adds a Scheduled task “Study CS2105” tomorrow from 2pm to 6pm. 
* `add "Charlie Puth concert” on 15 Oct 6pm"`<br>
Adds a Scheduled task “Charlie Puth concert” on 15 October at 6pm.
* `add "Watch Storks movie" on 10/10 from 1pm to 3pm`<br>
Adds a Scheduled task “Watch Storks movie” on 10 October 2016 from 1pm to 3pm. 
* `add "Do CS2103T tutorial" on 4 Oct 2016 from 10am to 11am every week`<br>
Adds a recurring Scheduled task “CS2103T tutorial” on the 4 October 2016 from 10am to 11am every week. 
* `add “CS2105 Tutorial” on Thursday at 11am till 12pm every week`<br>
Adds a recurring Scheduled task “CS2105 Tutorial” on Thursday from 11am to 12pm.

>Tasks without date and time specified will be added to WhatNow as a Todo task and will<br> be displayed under the heading "Todo Tasks".
> <img src="images/UI_Prototype/WhatNowUI-AddTodo-Colour.png" width="600"><br>
> Figure 3: How the GUI will look like when a task without date and time specified is added under Todo tasks. <br>  

>Tasks with date and time specified will be added to WhatNow as a Scheduled task and will<br> be displayed under the heading "Schedule".<br> 
> <img src="images/UI_Prototype/WhatNowUI-AddSchedule-Colour.png" width="600"><br>
> Figure 4: How the GUI will look like when a task with date and time specified is added under Scheduled tasks. <br>  



#### Listing all tasks: `list`
Shows a list of all tasks that match task type, date, time and priority requested by you.<br>
Format: `list [todo/schedule/done/all] [on/at/with] [DATE/TIME/PRIORITY]`<br>
Result: `Listing all <todo/schedule/completed> tasks on/at/with <DATE/TIME/PRIORITY>` message will be shown in the feedback box.<br> 

> * If no task type(todo/schedule/done) is stated, then all tasks that are not done will be displayed.<br>
> * The format for date must be *day month year* where year is optional or dd/mm/yyyy. If no year is specified, it is assumed to be the current year.<br>
Eg: 10 Oct, 4 November, 11 August 2017, 12/12/2016, 2/12/2016, 12/4/2016. Not 12/12/16. <br>
> *  If no date is specified, it is assumed to be today.<br>
> * The date cannot be a date in the past.
> * Time should be in the *12 hour format*.<br>
Eg: 10am, 6pm, 7.30pm, 7:30pm. Not 2359.
> * The format for priority should have the word `priority` after low/medium/high<br>
> *A task type must be stated if you want to filter by date/time/priority. 

Examples: 
* `list`<br>
Returns a list of all tasks that are not done. <br>
<img src="images/UI_Prototype/WhatNowUI-List-Colour.png" width="600"><br>
Figure 5: How the GUI will look like when all tasks that are not done are listed.<br>
* `list all`<br>
 Returns a list of all incomplete and completed tasks. <br>
* `list done`<br>
 Returns a list of all Completed tasks. <br>
* `list todo`<br>
Returns a list of all Todo tasks. <br>
* `list todo with medium priority`<br>
Returns a list of all Todo tasks that have a medium priority. <br>
* `list all at 6pm`<br>
Returns a list of all tasks scheduled at 6pm. <br>
* `list schedule on 3 Nov`<br>
Returns a list of all Scheduled tasks scheduled for 3 November of the current year. <br>
* `list done`<br>
Returns a list of all tasks previously marked done. <br>
* `list all`<br>
Returns a list of all tasks. <br>



>If you want to view any of the Todo tasks, Scheduled tasks or Completed tasks only, then you only<br> need to type in “todo”, “schedule” or “done” as the keyword after the command list. <br>
> <img src="images/UI_Prototype/WhatNowUI-ListCompleted-Colour.png" width="600"><br>
> Figure 6: How the GUI will look like when all Completed tasks are listed.<br>


#### Marking a task as completed: `done`
Marks a task from WhatNow as completed.<br>
Format: `done todo/schedule INDEX`<br>
Results: `Todo/Scheduled Task INDEX has been successfully marked as done: <Task marked>` message will be shown in the feedback box.<br>

> * Marks the task at the specified `INDEX` from Todo task, Scheduled or Completed tasks as specified. <br>
> * The index refers to the index number shown in the current listing.<br>
> * The index **must be a positive integer** 1, 2, 3, ...

Examples:
* `done todo 1`<br>
  Marks the 1st Todo task from the displayed list in WhatNow as completed.<br>
* `done schedule 2`<br>
  Marks the 2nd Schedule task from the displayed list in WhatNow as completed.<br>
  
  

#### Deleting a task : `delete`
Deletes a task from WhatNow. This action is reversible.<br>
Format: `delete todo/schedule/done INDEX`<br>
Result: `Todo/Scheduled/Completed Task INDEX has been successfully deleted: <Task deleted>` message will be shown in the feedback box. <br>

> * Deletes the task at the specified `INDEX` from Todo tasks, Scheduled or Completed tasks as specified. <br>
> * The index refers to the index number shown in the current listing.<br>
> * The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `delete todo 4`<br>
  Deletes the 4th Todo task from the displayed list in WhatNow. <br>
  <img src="images/UI_Prototype/WhatNowUI-DeleteTodo-Colour.png" width="600"><br>
Figure 7: How the GUI will look like after a Todo Task is deleted.<br>
* `delete schedule 2`<br>
  Deletes the 2nd Schedule task from the displayed list in WhatNow. <br>
  <img src="images/UI_Prototype/WhatNowUI-DeleteSchedule-Colour.png" width="600"><br>
Figure 8: How the GUI will look like after a Scheduled Task is deleted.<br>



#### Updating a task : `update`
Updates a task from the list displayed. This action is reversible.<br>
Format: `update todo/schedule INDEX description/date/start/end/priority/tag  NEW_VALUE` <br>
Result: ` Todo/Schedule Task INDEX has been successfully updated.<br>
     From: <old task> <br>
     To: <new task>` message will be shown in the feedback box and the updated task will be highlighted in the list for 3 seconds.<br>  

> * Updates the description/date/start/end/status/priority/tag of the task at the specified `INDEX`<br> from todo tasks or schedule as specified.<br> 
> * The index refers to the index number shown in the current listing.<br>
> * The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `update todo 3 description Avengers`<br>
   Selects the 3rd todo task from the displayed list and changes the task description to ‘Avengers’
* `update todo 4 date 11 Nov`<br>
   Selects the 4th todo task from the displayed list and changes the date and the type of task from todo to scheduled. 
* `update todo 4 date none`<br>
   Selects the 4th todo task from the displayed list and changes the date and the type of task from scheduled to todo.
* `update schedule 5 time 8:30pm`<br>
   Selects the 5th schedule task from the displayed list and changes the time to 8:30pm. <br>
<img src="images/UI_Prototype/WhatNowUI-UpdateSchedule-Colour.png" width="600"> <br>
Figure 9: How the GUI will look like after a Scheduled Task is updated.<br>




#### Marking a task as completed : `done`
Marks a task from WhatNow as completed. This action is reversible.<br>
Format: `done todo/schedule INDEX`<br>
Result: `Todo/Scheduled Task INDEX has been successfully marked as completed: <Task marked>` message will be shown in the feedback box. <br>

> * Marks the task at the specified `INDEX` from Todo tasks, or Scheduled tasks as specified. <br>
> * The index refers to the index number shown in the current listing.<br>
> * The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `done todo 4`<br>
  Marks the 4th Todo task from the displayed list in WhatNow as completed. <br>
  <img src="images/UI_Prototype/WhatNowUI-DoneTodo-Colour.png" width="600"><br>
Figure 10: How the GUI will look like after a todo task has been marked as completed.<br>
* `done schedule 2`<br>
  Marks the 2nd Schedule task from the displayed list in WhatNow as completed. <br>
  <img src="images/UI_Prototype/WhatNowUI-DoneSchedule-Colour.png" width="600"><br>
Figure 11: How the GUI will look like after a scheduled task has been marked as completed.<br>



#### Undoing the previous action : `undo`
There can be multiple undos to revert to the previous state. This action is reversible.<br>
Format: `undo`  <br>
Result: `Previous action has been successfully undone: <previous action> ` message will be shown in the feedback box and if the previous action was delete or update, then the task will be highlighted in the list for 3 seconds.<br>

Examples: 
* `undo`<br>
   Undo the previous action <br>
<img src="images/UI_Prototype/WhatNowUI-Undo-Colour.png" width="600"><br>
Figure 12: How the GUI will look like after an update action is undone.<br>



#### Redoing the previous action : `redo`
There can be multiple redos to revert to the previous state. This action is reversible.<br>
Format: `redo`  
Result: `Previous action has been successfully redone: <previous action> ` message will be shown in the feedback box and if the previous action was add or update, then the task will be highlighted in the list for 3 seconds.<br>

Examples: 
* `redo`<br>
   Redo the previous action <br>
<img src="images/UI_Prototype/WhatNowUI-Redo-Colour.png" width="600"> <br>
Figure 13: How the GUI will look like after an update action is redone.<br>



#### Finding tasks containing any keyword in their description: `find`
Search for all tasks whose description contains any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`<br>
Result: `Results for `KEYWORD`: <number of matches> ` message will be shown in feedback box and the matches will be highlighted.<br>

> * Press <kbd>Enter</kbd> to move to the next match.
> * The search is case sensitive. e.g `Read` will not match `read`
> * The order of the keywords does not matter. e.g. `Read books` will match `books Read`
> * Only the task description is searched.
> * All task description containing the keyword will be matched .e.g. `Book` will match `Books`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Read Study` will match `Read books`

Examples: 
* `find Read`<br>
  Returns `Read books` but not `read`
* `find Read books lecture notes`<br>
  Returns any tasks having the description `Read`, `books`, `lecture` or `notes`.
* `find CS3235`<br>
  Returns any tasks having the description `CS3235`. <br>
<img src="images/UI_Prototype/WhatNowUI-Find-Colour.png" width="600"><br>
Figure 14: How the GUI will look like after a find for the keyword.<br>



#### Viewing help : `help`
Displays a list of all commands.
Format: `help`<br>
Result: `Displaying the help page` message will be shown in the feedback box.

> Help is also shown if you enter an incorrect command e.g. `abcd` <br>
> <img src="images/UI_Prototype/WhatNowUI-Help-Colour.png" width="600"><br>
> Figure 15: How the GUI will look like when the help page is displayed. <br>



#### Saving the data 
WhatNow data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually after you make any changes or additions.



#### Clearing all tasks : `clear`
Clears all tasks from WhatNow. This action is reversible.<br>
Format: `clear`  <br>
Result :`All tasks are cleared.` message will be shown in the feedback box.<br>



#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  <br>
Result: WhatNow will be exited. <br>


## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous WhatNow folder.
       

## Command Summary

Command | Format  
-------- | :-------- 
Add | `add "DESCRIPTION" [on/by] [today/tomorrow/DAY/DATE] [from/at] [START_TIME] [to/till] [END_TIME] [every] [day/week/month/year/DAY] [low/medium/high]`
Change | `change location to PATH`
Clear | `clear`
Delete | `delete todo/schedule/done INDEX`
Done | `done todo/schedule INDEX`
Exit | `exit`
Find | `find KEYWORD [MORE_KEYWORDS]`
Help | `help`
List | `list [todo/schedule/done/all] [on/at/with] [DATE/TIME/PRIORITY]`
Redo | `redo`
Update | `update todo/schedule INDEX description/date/start/end/priority/tag NEW_VALUE`
Undo | `undo`