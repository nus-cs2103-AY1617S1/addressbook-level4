# User Guide
Are you feeling stressed or overwhelmed with the number of things you have to do? When you are drowning in the pool of to-dos, even mundane tasks like buying milk may bring tears to your eyes. Well you can hold back those precious tears because WhatNow will help you to manage all your tasks.

Now that you know WhatNow is [about](../README.md), you can follow this guide to learn how to use WhatNow effectively.

Welcome to WhatNow! 

# Table of Contents
* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `WhatNow.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your WhatNow.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/UI_Prototype/WhatNowUI-Welcome-Colour.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** then press <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : lists all tasks in WhatNow
   * **`add`**` Buy groceries` : adds a task called `Buy groceries` to WhatNow.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits WhatNow
6. Refer to the [Features](#features) section below for details of each command.<br>

## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.


#### Changing storage location : `change`
Changes the data file storage location. <br>
Format: `change location to PATH`

Examples:
* `change location to C:\Users\Verbena\Dropbox\WhatNow`<br>
Changes the data file storage location to C:\Users\Verbena\Dropbox\WhatNow<br>
<img src="images/UI_Prototype/WhatNowUI-ChangeLocation-Colour.png" width="600">


#### Adding a task: `add`
Adds a task to WhatNow<br>
Format: `add "DESCRIPTION" [on/by] [today/tomorrow/DAY/DATE] [from/at] [START_TIME] [to/till] [END_TIME] [every] [DAY/day/week/month/year] [low/medium/high]` 

>All task description should be written within double quotation marks. Eg Add “buy eggs”. <br>
The format for date must be *day month year* where year is optional. Eg: 10th Oct, 4 November, 11th August 2017. Not 12/12/12. <br>
Time should be in the *12 hour format*. Eg: 10am, 6pm, 7.30pm. Not 7:30pm, 2359.

Examples:
* `add "Do CS2103T tutorial" on 4 Oct 2016 from 10am to 11am every week`<br>
Adds a recurring scheduled task “CS2103T tutorial” on the 4th October 2016 from 10am to 11am every week. 
* `add "Watch Storks movie" on 10/10 from 1pm to 3pm`<br>
Adds a scheduled task “Watch Storks movie” on 10th October 2016 from 1pm to 3pm. 
* `add "CS2103 Project" by 20/11/2016 high`<br>
Adds a scheduled task “CS2103 Project” of high priority on 20th November 2016. 
* `add “CS2105 Tutorial” on Thursday at 11am till 12pm every week`<br>
Adds a recurring scheduled task “CS2105 Tutorial” on Thursday from 11am to 12pm.
* `add "Buy pizza" at 7pm`<br>
Adds a scheduled task “Buy pizza” today at 7pm. 
* `add "Charlie Puth concert” on 15th Oct 6pm"`<br>
Adds a scheduled task “Charlie Puth concert” on 15th October at 6pm.
* `add "Buy chocolate milk"`<br>
Adds a todo task “Buy chocolate milk”.

>Tasks with date and time specified will be added to WhatNow as a schedule task and will<br> be displayed under the heading "Schedule".<br> 
> <img src="images/UI_Prototype/WhatNowUI-AddSchedule-Colour.png" width="600">

>Tasks without date and time specified will be added to WhatNow as a todo task and will<br> be displayed under the heading "Todo Tasks".
> <img src="images/UI_Prototype/WhatNowUI-AddTodo-Colour.png" width="600">


#### Listing all tasks: `list`
Shows a list of all tasks that match task type, date, time and priority requested by user.<br>
Format: `list [date/time/priority] KEYWORD`

> Shows a list of all tasks that match the keyword for the task type, date, time and priority requested by user.

Examples: 
* `list`<br>
Returns a list of all tasks. <br>
<img src="images/UI_Prototype/WhatNowUI-List-Colour.png" width="600">
* `list completed`<br>
 Returns a list of all completed tasks. <br>
* `list todo`<br>
Returns a list of all todo tasks. <br>
* `list priority medium`<br>
Returns a list of all tasks that have a medium priority. <br>
* `list time 6pm`<br>
Returns a list of all tasks scheduled at 6pm. <br>
* `list date 3 Nov`<br>
Returns a list of all tasks scheduled for 3 November of the current year. <br>

>If you want to view any of the Todo tasks, Scheduled tasks or Completed tasks only, then you only<br> need to type in “todo”, “schedule” or “completed” as the keyword after the command list. <br>
> <img src="images/UI_Prototype/WhatNowUI-ListCompleted-Colour.png" width="600">


#### Deleting a task : `delete`
Deletes a task from WhatNow. Reversible.<br>
Format: `delete todo/schedule/completed INDEX`

> Deletes the task at the specified `INDEX` from todo tasks, schedule or completed tasks as specified. <br>
  The index refers to the index number shown in the current listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `delete todo 4`<br>
  Deletes the 4th todo task from the displayed list in WhatNow. <br>
  <img src="images/UI_Prototype/WhatNowUI-DeleteTodo-Colour.png" width="600">
* `delete schedule 2`<br>
  Deletes the 2nd schedule task from the displayed list in WhatNow. <br>
  <img src="images/UI_Prototype/WhatNowUI-DeleteSchedule-Colour.png" width="600">


#### Updating a task : `update`
Updates a task from the list displayed<br>
Format: `update todo/schedule INDEX description/date/start/end/status/priority  NEW_VALUE`

> Updates the description/date/start/end/status/priority of the task at the specified `INDEX` from todo tasks or schedule as specified.<br> 
  The index refers to the index number shown in the current listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `update todo 3 description Avengers`<br>
   Selects the 3rd todo task from the displayed list and changes the task description to ‘Avengers’
* `update todo 4 date 11 Nov`<br>
   Selects the 4th todo task from the displayed list and changes the type of task from todo to scheduled. 
* `update schedule 5 time 8:30pm`<br>
   Selects the 5th schedule task from the displayed list and changes the time to 8:30pm. <br>
<img src="images/UI_Prototype/WhatNowUI-UpdateSchedule-Colour.png" width="600"> 


#### Undoing the previous action : `undo`
There can be multiple undos to revert to the previous state.<br>
Format: `undo`  

Examples: 
* `undo`<br>
   Undo the previous action <br>
<img src="images/UI_Prototype/WhatNowUI-Undo-Colour.png" width="600">


#### Redoing the previous action : `redo`
There can be multiple redos to revert to the previous state.<br>
Format: `redo`  

Examples: 
* `redo`<br>
   Redo the previous action <br>
<img src="images/UI_Prototype/WhatNowUI-Redo-Colour.png" width="600">


#### Finding tasks containing any keyword in their description: `find`
Search for all tasks whose description contains any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

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
<img src="images/UI_Prototype/WhatNowUI-Find-Colour.png" width="600">


#### Viewing help : `help`
Displays a list of all commands.
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd` <br>
> <img src="images/UI_Prototype/WhatNowUI-Help-Colour.png" width="600">


#### Saving the data 
WhatNow data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually after you make any changes or additions.


#### Clearing all tasks : `clear`
Clears all tasks from WhatNow.<br>
Format: `clear`  


#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  


## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous WhatNow folder.
       

## Command Summary

Command | Format  
-------- | :-------- 
Add | `add "DESCRIPTION" [on/by] [today/tomorrow/DAY/DATE] [from/at] [START_TIME] [to/till] [END_TIME] [every] [DAY/day/week/month/year] [low/medium/high]`
Change | `change location to PATH`
Clear | `clear`
Delete | `delete todo/schedule/completed INDEX`
Exit | `exit`
Find | `find KEYWORD [MORE_KEYWORDS]`
Help | `help`
List | `list [date/time/priority] KEYWORD`
Redo | `redo`
Update | `update todo/schedule INDEX [description/date/start/end/status/priority]  NEW_VALUE`
Undo | `undo`