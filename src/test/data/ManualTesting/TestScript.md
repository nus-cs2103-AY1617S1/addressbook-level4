# Test Script (for Manual Testing)

## Load sample data

To load the sample data, open the app once so that the config.json file is created. Then close the app. Copy the SampleData.xml file to the data folder. Change the file path in the config.json file to data/SampleData.xml. Start up the app again and the sample data will be loaded.

**result:** The task list is loaded.

## Help command

1. Type `help`

**result:** A new window appears with a cheatsheet for the commands, FAQ and shortcuts. Please exit after opening by clicking the X at the top.

## Add command

### Add floating task

1. Type `add buy milk`

**results:** Floating task with name "buy milk" is added and the main list goes to the new task added.

### Add a start time task

1. Type `add meeting with Jim from tmr 8pm`

**results:** Start time task with name "meeting with Jim" is added for the next day at 8pm and jumps to the task added.

2. Type `add buy apples at 7aaam`

**results:** Error message "Invalid Time!"

### Add a deadline task 

1. Type `add submit essay by 2359` 

**results:** Deadline with name "submit essay" is added by 11:59pm today.

2. Type `add dinner date with bob by 7pm 30/2/17`

**results:** Error message: "The date provided is invalid."

3. Type `add finish assignment by 2359`

**results:** WARNING! This task clashes with one of the tasks in ForgetMeNot. Type undo if you want to undo the previous add.
However, the task is added in ForgetMeNot with name "finish assignment" by 11:59pm today

### Add an event task

1. Type `add movie with Jane \\at Vivo from sunday 1pm to sunday 3pm`

**results:** Event with name "movie with Jane at Vivo" is added on the next Sunday 1pm to 3pm and jumps to the task added.

2. Type `add movie with John from 4:30pm to 3pm`

**results:** Error message: "Sorry! Your start time cannot be after your end time" is displayed

### Add a recurring task

1. Type `add play basketball with Jim at tuesday 2pm every week`

**results:** Task with name "play basketball with Jim" is added 10 times for 10 tuesdays, with the recurrence displayed on the task card.

2. Type `add Japanese lesson at saturday night 7pm every 2 weeks x4`

**results:** Task with name "Japanese lesson" is added 4 times for saturday at 7pm every 2 weeks.

3. Type `add Chinese lesson at sunday 3pm every two days`

**results:** Error message: "Sorry! Your recurrence format was invalid! Please try again.

## Edit Command

### Edit Name

1. Type `edit 3 collect new iPhone 7 plus`

**results:** Success message shown for edited task. Name of task index 3 changed to "collect new iPhone 7 plus"

2. Type `edit 100 by tmr`

**results:** Error message: "Sorry! The task index provided is invalid."

### Edit Start Time

1. Type `edit 2 at tmr 6pm`

**results:** Success message shown for edited task. Task indexed 2 start timing changed to 6pm tomorrow (depending on the current day, it may vary).

### Edit End Time

1. Type `edit 14 by 2359 three days later`
 
 **results:** Success message shown for edited task. Task indexed 14 end timing changed to 11:59pm three days from current day.
 
### Edit both start and end time

1. Type `edit 29 from 8pm 15/11/16 to 9pm 15/11/16`

**results:** Success message shown for edited task. Task indexed 29 has its start timing and end timing changed to the Nov 15, 2016, 08:00 PM and Nov 15, 2016, 09:00 PM respectively.

2. Type `edit 25 from 7pm to 6pm`

**results:** Error message: "Sorry! Your start time cannot be after the end time"

### Edit name, start time and end time

1. Type `edit 1 Buy milk from 5pm 15/01/17 to 6pm 15/01/17`

**results:** Success message shown for edited task. Task indexed 3o has its name, start timing and end timing changed to Jan 15, 2017, 05:00 PM and Jan 15, 2016, 06:00 PM respectively.

## Done task

1. Type `done 3`

**results:** Task that was indexed 3 is removed from the current list. Feedback shows the task that has been done.

2. Type `done 400`

**results:** Error message: "Sorry! The task index provided is invalid."

## Show

### Show done

1. Type `show done`

**results:** All done tasks are shown.

### Show undone

1. Type `show`

**results:**  All undone tasks are shown.

### Show floating

1. Type `show floating`

**results:** All floating tasks are shown.

### Show overdue

1. Type `show overdue`

**results:** All overdue tasks are shown.

### Show date

1. Type `show 7 dec`

**results:** Task "gym with Jim" will be shown.

### Show special days

1. Type `show 5 days later`

**results:** Tasks that are happening five days later will be shown.

1. Type `show xmas eve`

**results:** Tasks on Dec 24 2016 will be shown.

## Undone task

1. Type `show done`

**results:** Tasks that are done will be shown.

2. Type `undone 1`

**result:** Tasks that was indexed 1 will be removed from the currently list. Feedback shows that task has been undone.

## Find tasks

1. Type `find jim`

**results:** Tasks with the task name `jim` will appear on the list. Feedback shows the number of tasks listed.

2. Type `find jim pokemon`

**results:** Tasks with either the name `jim` or `pokemon` will show

## Delete tasks

1. Type `show`

**results:**  All undone tasks are shown.

1. Type `delete 4`

**results:** Task that was indexed 4 will be deleted and removed from the current list. 

## Undo tasks

1. Type `delete 2`
2. Type `undo`

**results:** Task that was previously deleted will be undone and reappear on the list.

## Redo tasks

1. Type `delete 1`
2. Type `undo`
3. Type `redo`

**results:** Task that was brought back by the undo will be deleted away again after redo command.

## Command history

1. Press up key

**results:** `redo` will appear in the command line.

## Auto-complete

1. Type `f`
2. Press spacebar

**results:** Find command will be auto-completed and appear in the command line.

## Clear done tasks

1. Type `show done`

**results:** Tasks that are done will be shown.

2. Type `clear done`

**results:** All tasks that were marked as done will be deleted and removed from the list.

## Clear tasks

1. Type `Clear`

**results:** All tasks will be deleted and removed from ForgetMeNot.

## Set storage

1. Type `setstorage data\newfile.xml\`

**results:** Feedback shows user that storage location is changed to the new location and prompt user to make a change to ForgetMeNot to save the data.

2. Type `add watch cartoon`

**results:** Floating task with name "watch cartoon" is added and the main list goes to the new task added.

##

1. Type `exit`

**results:** Exits ForgetMeNot

