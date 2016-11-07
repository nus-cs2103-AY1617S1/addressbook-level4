# Test Script
<!-- @@author A0131813R -->
* [Description](#description)
* [Testing Commands](#testing-commands)

## Description

* Each card in the bottom left panel is a task added to the LifeKeeper.
* The LifeKeeper supports three types of entries:		
   * `Activity`
   * `Task`
   * `Event`
* Activity is a floating task which must have a valid `NAME` and optional `[REMINDER]`.<br>
* Task is a task which must have a valid `NAME` and either `[DUEDATE]` or `[PRIORITY]`. `[REMINDER]` can also be entered together with the rest.<br>
* Event is a task which must have a valid `NAME` and `[STARTTIME]` and optional `[ENDTIME]`. `[REMINDER]` can also be entered together with the rest.<br>
* All task types can have optional `[TAG]`, each task accepts multiple tags. `Tags` are shown with `[]`. <br>
* Colours of the background of the cards indicate the status of the task. 
  * `Green`: Task or Activity that has been marked as done, or an Event that has passed. A text `Completed` or `Event Over` is shown to notify user of the status of the task/event
  * `Yellow`: Task with a deadline within three days. A text `Task Deadline Approaching` is also shown to notify user of the impending deadline.
  * `Red`: Task with a duedate that has passed and yet not marked as done. A text `Task Overdue` is also shown to reminder user of the overdue task.
  * `Blue`: Events that are ongoing. This is defined as an event with a start time before the current time, and an end time after the current time. A text `Event Ongoing` is shown to notify the user of the event status.
  * `White`: The default colour indicating all other types of tasks.
* <img src="priority3.png" width="15"> shown on the right side of the card indicates the important or priority of the task. There are four levels of priority 0, 1, 2, 3 which are indicated by the number of exclamation marks shown. Priority is only applicable to task but not activity or event. 

## Loading saved data
Method 1
1. Go to File > Open.
2. Navigate to the folder src > test > data > ManualTesting.
3. Choose the file SampleData.xml and click 'Open'.

Method 2
1. Type open src/test/data/ManualTesting/SampleData.xml to load the sample data.

## Testing Commands

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `[SQUARE_BRACKETS]` are optional.
> * Items in `{CURLY_BRACES}` cannot be input together with other items within a separate `{CURLY_BRACES}`
> * Items with `...` after them can have multiple instances.
> * The order of parameters is not fixed.

### Help Command

#### Testing Command: `help`

> Help window pops up

### Add Command
 
#### Testing Command: `add activity`

> A new activity is added to the top of the list with name `Activity` and `Reminder: -`.

#### Testing Command: `add buy food r/12-12-2016 1800 t/Family`

> A new activity is added to the top of the list with name `buy food`, tag `[Family]` and reminder `Reminder: Mon, Dec 12, 2016 6:00PM`.

#### Testing Command: `add do IE 3101 Tutorial d/today p/3 t/IE3101`

> A new task is added to the top of the list with name `do IE 3101 Tutorial`, duedate ` Due on (Today's Date) 11:59PM`, tag `[IE3101]` and <img src="priority3.png" width="15">. 
> The colour of the background of the card will also turn yellow with the text `Task Deadline Approaching` shown on the right of the card.

#### Testing Command: `add IE3100 Assignment 4 d/wed 1800`. 

> A new task is added to the top of the list with name `IE3100 Assignment 4`, duedate ` Due on (upcoming Wednesday's date) 6:00PM` and `Reminder: -`. 

#### Testing Command: `add CS2103 Tutorial s/thu 1300`

> A new event is added to the top of the list with name `CS2103 Tutorial` and `From (upcoming Thursday's date) 1:00PM to 2:00PM`.

#### Testing Command: `add CS2103 Lecture s/every fri 1400 e/1500`

> A new recurring event is added to the top of the list with the name `CS2103 Lecture`, and Time `Every Friday, 2:00PM to 3:00PM

#### Testing Command: `add 345%^&`

> An invalid error will be shown, prompting the user that `Task name should be space or alphanumeric characters`.

#### Testing Command: `add MKT presentation s/tomorrow 1400 e/1100`

> An invalid error will be shown, prompting the user that `Event end time is before start time`.
> For all tasks with reminder, the reminder will automatically pop up at the scheduled time when the app is open.

#### Testing Command: `add IE3100 Tutorial d/wed 1800 t/IE3100`. 

> A new task is added to the top of the list with name `IE3100 Assignment 4`, duedate ` Due on (upcoming Wednesday's date) 6:00P` and `Reminder: -`
> There is also a tag `[IE3100]` shown.

#### Testing Command: `add IE3100 Group Assignment p/2 t/IE3100`

> A new task is added to the top of the list with name `IE3100 Assignment 4`, `Reminder: -` and  <img src="priority2.png" width="15">.
> There is also a tag `[IE3100]` shown.

#### Testing Command: `add homework 4 d/tomorrow t/IE3100`

> A new task is added to the top of the list with name `homework 4` and `Due on (tomorrow's date) 11:59PM`.
> The default deadline for a day if no time is given is 11:59PM.
> The colour of the background of the card will also turn yellow with the text `Task Deadline Approaching` shown on the right of the card.
> There is also a tag `[IE3100]` shown.

#### Testing Command: `add Project d/tomorrow p/1`

> A new task is added to the top of the list with name `homework 4`, `Due on (tomorrow's date) 11:59PM` and <img src="priority1 .png" width="15">.
> The default deadline for a day if no time is given is 11:59PM.
> The colour of the background of the card will also turn yellow with the text `Task Deadline Approaching` shown on the right of the card.

#### Testing Command: `add revise chapter 2 d/(today's date and one minute from now, e.g. 10-11-2016 2045`

> A new task is added to the top of the list with name `review chapter 2`, `Due on (one minute from the current time of usage)` 
> The colour of the background of the card will also turn yellow with the text `Task Deadline Approaching` shown on the right of the card.
> Wait for 2 minutes until the deadline is over, and the colour of the background of the card will turn red with the text `Task Overdue!`
> A new task is added to the top of the list with name `IE3100 Assignment 4`, duedate ` Due on (upcoming Wednesday's date) 6:00PM` and `Reminder: -`. 

#### Testing Command: `add CS2103 Tutorial s/thu 1300`

> The task cannot be added, as there is already a CS2103 Tutorial existing with the same start and end time

#### Testing Command: `add IE2140 Lab s/11-11-2016 1300 e/11-11-2016 1500 r/10-11-2016 2200`

> A new event is added to the top of the list with name `IE2140 Lab`, `From Fri, Nov 11, 2016 1:00PM to 3:00PM` and `Remind: Thu, Nov 10, 2016 10:00PM`.

#### Testing Command: `add go to Grandma House r/every sat 1300`

> A new activity is added to the top of the list with name `go to Grandma House` and `Reminder: Every Saturday, 1:00PM`.

#### Testing Command: `add CS2103 Lecture s/every fri 1400 e/1600`

> A new recurring Event is added to the top of the list with name `CS2103 Lecture` and `Every Friday 2:00 PM to 4:00 PM`.

#### Testing Command: `add MKT presentation s/11-11-2016 1245 e/1200`

> Invalid command since the end time for the event is earlier than the start time. Result Bar shows that the error.

#### Testing Command: `add CS Demo s/every (today's day of the week and one minute from now) e/(two minutes from now)`

> If today is Monday and current time is 1400Hrs, the command should be `add CS Demo s/every mon 1401 e/1402`
> Wait for one minute and the colour of the card will turn blue indicating the event is ongoing
> Wait for another minute and the colour of the card returns to white indicating that the event is over and the lifeKeeper updates the events to next week

#### Testing Command: `add tutorial 5 r/(today's date and one minute after the current time)`

> If today is Monday and current time is 1400Hrs, the command should be `add tutorial 5 r/mon 1401`
> A new activity is add with name `tutorial 5` and reminder one minute from the current time.
> Wait for one minute and a pop up will show to remind the user, click on `OK` to continue. 

#### Testing Command: `add tutorial 6 r/(today's date and one minute after the current time) t/IE3100`

> If current time is 1400Hrs, the command should be `add tutorial 6 r/today 1401`
> A new activity is add with name `tutorial 5` and reminder one minute from the current time.
> Wait for one minute and a pop up will show to remind the user, click on `OK` to continue. 

#### Testing Command: `add MNO presentation s/tomorrow 1400 e/1100`

> An invalid error will be shown, prompting the user that `Event end time is before start time`.

#### Testing Command: `add assignment 2 return r/10-10-2016 1230`

> Invalid command since the reminder time is before the current time. Result Bar shows the error that `reminder time has passed`.

#### Testing Command: `add revision for IE3120 s/10-10-2016 1200`

> Invalid command since the start time for the adding event is before the current time.

#### Testing Command: `add YellowCare Concert e/10-10-2017 1200`

> Invalid command since there is only end time without a start time for the event.

### Edit Command

#### Testing Command: `add assignment 2`, <kbd>Enter</kbd> , `edit 1 n/assignment 3`

>  The first entry `assignment 2` has its name changed to `assignment 3`

#### Testing Command: `edit 1 d/12-01-2017 23:55`

> The first entry `assignment 3` will be converted from activity type to task type with a valid duedate `Thu, Jan 12, 2017 11:55 PM`.
> The input time can accept time with a `:`

#### Testing Command: `add activity 2`, <kbd>Enter</kbd> , `edit 1 s/12-03-2017 1830`

> The first entry `activity 2` will be converted from activity type to event type with a valid start and end time `From Sun, Mar 12, 2017 6:30 PM`

#### Testing Command: `add submit homework d/11-01-2017 1200`, <kbd>Enter</kbd> ,`edit 1 s/12-01-2017 2200`

> The Result Bar shows `Task cannot be changed to event and vice versa.`
> An activity can be converted to either task with duedate or event with start and end time, but a task with duedate cannot be changed to event and vice versa.
> A new event is added to the top of the list with the name `CS2103 Tutorial`, `From (upcoming Thursday's date) 1:00PM yo 2:00PM`. 
> The default end time for an event is one hour after the start time.

#### Testing Command: `add prepare IE3100 cheatsheet`, <kbd>Enter</kbd>, `edit 1 e/today 2359`

> Invalid command for edit, the edit has to include both start and end time.

#### Testing Command: `add CS2103 Lab s/every fri 1400 e/1500`, <kbd>Enter</kbd>, `edit 1 s/every thu 1400 e/1500`

> The command edits the current start and end time of the entry `CS2103 Lab` and changes it to `Every Thursday, 2:00 PM tp 3:00 PM

> The Result Bar shows `Event must contain a start time`, and event cannot be edited without a valid start time input.
> A new recurring event is added to the top of the list with the name `CS2103 Lecture`, and Time `Every Friday, 2:00 PM to 3:00 PM

#### Testing Command: `add CS2010 quiz revision p/1`, <kbd>Enter</kbd>, `edit 1 p/3`

> The previously added entry `CS2010 quiz revision` has its priority level update to <img src="priority3.png" width="15">

#### Testing Command: `edit 2 t/CS2103`

> The command adds a tag into the second entry `CS2103 Lecture`.

#### Testing Command: `edit 2 r/tomorrow 1800`

> The command adds a reminder to the second entry `CS2103 Lecture`.

#### Testing Command: `add submit homework d/11-01-2017 1200`, <kbd>Enter</kbd> ,`edit 1 d/12-01-2017 2200`

> The command will change the duedate for the newly added entry to `Thu, Jan 12, 2017 10:00 PM`





### Find tag Command

#### Testing Command: `findtag IE3100`

> The command returns all the entries with the tag IE3100

### Done Command

#### Testing Command: `add speak to Prof r/today 2300`, <kbd>Enter</kbd> , 'done'

> The newly added activity `speak to Prof` will be marked as a done task. 
> It will be removed from the list and moved to the done list.

#### Testing Command: `add speak to ISE Department d/today 1700`, <kbd>Enter</kbd> , 'done'

> The newly added task `speak to ISE Department` will be marked as a done task. 
> It will be removed from the list and moved to the done list.

### List Command

#### Testing Command: `list all`

> Listing command that shows all the tasks inside the Lifekeeper

#### Testing Command: `list`

> The default listing with all the tasks excluding completed tasks and activities and also events that are over

#### Testing Command: `list done`

> Listing command that shows all completed tasks and activities and also events that are over

#### Testing Command: `list event`

> Listing command that shows all the events.

#### Testing Command: `list task`

> Listing command that shows all the tasks.

#### Testing Command: `list activity`

> Listing command that shows all the activities.

### Find Command

#### Testing Command: `find CS2103`

> All entries with the keyword `CS2103` in their names. 

#### Testing Command: `find cs2103`

> All entries with the keyword `cs2103` in their names. Find command is case insensitive.

#### Testing Command: `find cs`

> Zero entries listed, find command must have the keyword in full, is the keyword is `cs`, entries with `CS2103` will not be shown.

### Delete Command

#### Testing Command: `list`, <kbd>ENTER</kbd>, `delete 1`

> The first entry in the list will be deleted.

#### Testing Command: `delete 1000`

> This is an invalid command, the returned result is `The task index provided is invalid`.

### Undo Command: 

#### Testing Command: `add go to Utown r/today 2200`, <kbd>ENTER</kbd>, `undo`

> The newly added activity will be deleted, and the condition of the lifekeeper reverts back.

#### Testing Command: `add go to Utown r/today 2200`, <kbd>ENTER</kbd>, `edit 1 n/go to SOC`, <kbd>ENTER</kbd>, `undo`, `undo`

> After the first undo, the name of the entry reverts back to `go to Utown`, and after the second undo, it reverts back to the original list.

#### Testing Command: `delete 1`, <kbd>ENTER</kbd>, `undo`

> The first entry that was deleted is added back again.

#### Testing Command: `add go to E2 r/today 2200`,  <kbd>ENTER</kbd>, `list`, <kbd>ENTER</kbd>, `undo`

> `list`, `find` and `findtag` cannot be undone. It will undo the previous undo-able command, which in this case is the add command.

### Save Command

#### Testing Command: `save src/test/data/LifeKeeperData.xml

> A new Lifekeeperdata.xml will be created in the folder data

### Open Command

#### Testing Command: `open src/test/data/ManualTesting/SampleData.xml

> The SampleDate.xml will be loaded

### Clear Command

#### Testing Command: `clear`

> Erases everything in the LifeKeeper

### Exit Command

#### Testing Command: `exit`

> Leaves the Lifekeeper



