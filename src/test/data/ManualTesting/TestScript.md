# TestScript

## Loading the Sample Data

|Action|Command|Result|
|---|---|---|
|Load the sample data|`store` and the filepath of the SampleData file.<br>No need to specify `.xml`<br> If running this from Jar file, specify the path to this SampleData.xml file depending on where the Jar file is. Or give an absolute path (this varies depending on the Operating System used and the username of the user, directory in which DearJim is cloned..) <br>If running from Eclipse: `store src\test\data\ManualTesting\SampleData`. (for mac use `/`)<br>|SampleData.xml file data will be loaded into DearJim. You should see >50 Tasks, some done, some undone.

## Editing tasks

|Action|Command|Result|
|---|---|---|
|Edit a task's name |`edit 1 buy Akshay two farewell gifts`|Task at index 1 name changed to "buy Akshay two farewell gifts"|
|Edit a task's priority|`edit 2 -high`| Task at index 2 priority bar shade turns from yellow to red.|
|Undo previous edit command|`undo`|Previously changed task priority is changed back to yellow.|
|Redo previous edit command|`redo`|Previously changed task priority is changed back to red.|
|Edit a task's name with escaped input|`edit 1 "increase word count from 1000 to 1500"`|Task at index 1 name changed to "increase word count from 1000 to 1500".|
|Edit a task's start time|`edit 21 from 10 nov 5pm`|Task at index 21 start date changed to "Thur, 10 Nov 2016, 5pm" and get sorted to index 8.|
|Edit a task's end time|`edit 9 by 10 nov 6pm`|Task at index 9 end date changed to "Thur, 10 Nov 2016, 6pm".|
|Edit a task's start and end time|`edit 6 from 11 nov 4pm to 6pm`|Task at index 6 start date changed to "Fri, 11 Nov 2016, 4pm", end date changed to "Fri, 11 Nov 2016, 6pm", and get sorted to index 12.|
|Edit a task's recurrence rate|`edit 7 repeat every day`|Task at index 7 recurrence rate of "every day" added|
|Edit a task with invalid end date that occurs before start date|`edit 7 by 4 nov 8pm`|Error message : "End date should be later than start date."|
|Edit a floating task to include recurrence rate|`edit 22 repeat every fri`|Error message : "For recurring tasks to be valid, at least one DATE_TIME must be provided."|
|Edit a task's name,start date, end date, recurrence and priority|`edit 2 Visit com room from 9 nov 11am to 12pm repeat every week -low`|Task at index 2 changed to name "Visit com room", start date "Wed 9 Nov 2016, 11am", end date "Wed 9 Nov 2016, 12pm", recurrence rate of "every week", priority bar shade turns from yellow to green|
|Edit a task's start date, recurrence and priority|`edit 21 from 24 dec 12pm repeat every year -high`|Task at index 21 changed start date "Sat 24 Dec 2016, 12pm", recurrence rate of "every year", priority bar shade turns from yellow to red|
|Edit reset a task start date, end date and recurrence|`edit 2 -reset start end repeat`|Task at index 2 get sorted to index 21 with no start date, end date and recurrence|


## Adding tasks

|Action|Command|Result|
|---|---|---|
|Add a floating task|`Read Harry Potter`|Task with name "Read Harry Potter", priority bar shaded yellow|
|Undo previous add command|`undo`|Previously added task "Read Harry Potter" is deleted.|
|Redo previous add command|`redo`|Previously deleted task "Read Harry Potter" is re-added.|
|Add a task with start date|`Read Harry Potter from 30th Nov`|Task with name "Read Harry Potter", with start date "Wed, 30 Nov 2016, 12:00 AM", priority bar shaded yellow|
|Add a task with end date|`Read Harry Potter by 30th Nov`|Task with name "Read Harry Potter", with end date "Wed, 30 Nov 2016, 11:59pm", priority bar shaded yellow|
|Add a task with start and end date|`Read Harry Potter from 30th Nov 8am to 9am`|Task with name "Read Harry Potter", with start date "Wed, 30 Nov 2016, 8:00am", end date "Wed, 30 Nov 2016, 9:00am", priority bar shaded yellow|
|Add a task with recurrence rate|`Jog repeat every Monday`|Task with name "Jog", with recurrence rate "every Monday", priority bar shaded yellow. Start date will take the nearest Monday. (i.e if today is Monday, start date will be today. Else, start date will be next Monday)|
|Add a task with recurrence rate|`Jog from 20th Nov 10am repeat every 3 days`| Task with name "Jog", with start date "Sun, 20 Nov 2016, 10:00am", recurrence rate "Every 3 Days", priority bar shaded yellow|
|Add a task with priority|`Buy kitkat -high`|Task with name "Buy kitkat", with priority bar shaded red|
|Add a task that contains all of these|`Overnight cycling from 10th Nov 10pm to 2am repeat every week -low`|Task with name "Overnight cycling", with start date "Thu, 10 Nov 2016, 10:00pm", end date "Fri, 11th Nov 2016, 2:00am", recurrence rate "Every Week", priority bar shaded green|
|Add a task with multiple keywords|`Go to the beach from my house from 10th Nov 10am to 11am`|Task with name "Go to the beach from my house", with start date "Thu, 10 Nov 2016, 10:00am", end date "Thu, 10 Nov 2016, 11:00am", priority bar shaded yellow|
|Add a task with end date earlier than start date|`eat from today 10pm to yesterday 10pm`|Error message, showing invalid command format, "End date should be later than start date." and add command format.|
|Add a task with invalid date|`eat from 40 Nov`|Error message, showing invalid command format, "Invalid date." and add command format.|
|Add a task with invalid rate|`eat from 10 Nov repeat every 0 days`|Error message, showing invalid command format, recurring constraints and add command format.|
|Add a task with invalid time period|`eat from 10 Nov repeat every 5 bobs`|Error message, showing invalid command format, recurring constraints and add command format.|
|Add a task with missing time period|`eat from 10 Nov repeat every 5`|Error message, showing invalid command format, recurring constraints and add command format.|
|Add a recurring task without dates|`eat repeat every 3 days`|Error message, showing invalid command format, recurrence and date constraints, and add command format.|
|Add a task with relative dates|`eat from this wed to next thurs`|Task with name "eat", start date will take the nearest Wednesday, end date will take the next Thursday.|
|Add a task with repeat start dates|`eat from today from tomorrow`|Error message, showing invalid command format, "Repeated start times are not allowed.", and add command format.|
|Add a task with repeat end dates|`eat by today by tomorrow`|Error message, showing invalid command format, "Repeated end times are not allowed.", and add command format.|
|Add a task with escaped input|`"increase word count from 1000 to 1500" from 10th Nov to 11th Nov`|Task with name "increase word count from 1000 to 1500", with start date "Thu, 10 Nov 2016, 12:00am", end date "Fri, 11th Nov 2016, 11:59pm", priority bar shaded yellow|


## Help command
|Action|Command|Result|
|---|---|---|
|Help|`help`|Help window pops up. Press any key while this window is active to close it.|

## Delete tasks
|Action|Command|Result|
|---|---|---|
|Delete first 3 tasks in list|`delete 1 2 3`|"Jog", "increase word count from 1000 to 1500", "eat" tasks are deleted.|
|Undo previous delete command|`undo`|Restore previous 3 deleted tasks.|
|Delete last task in list|`delete 33`|"bring my pet Han out" task is deleted.|
|Delete an invalid index|`delete 33`|Error message: "The task index provided is invalid"|
|Delete a mix of valid and invalid indexes|`delete 1 31 34 50 100`|"Jog" and "Read Harry Potter", the 1st and 31st tasks deleted. Invalid indexes ignored.|
|Delete all invalid indexes|`delete 33 34 35 200`|Error message: "The task index provided is invalid"|

## Archive tasks
|Action|Command|Result|
|---|---|---|
|Archive first task in list|`done 1`|"increase word count from 1000 to 1500" archived|
|Look at archived tasks|`list done`|Switches to list done view. Index 28 is the newly archived task "increase word count from 1000 to 1500"|
|Add to list done view|`buy a pet kitty`|Error message: "Cannot do this command on done list. Please switch back to normal list view by typing list"|
|Edit a task in list done view|`edit 1 new name`|Error message: "Cannot do this command on done list. Please switch back to normal list view by typing list"|
|Delete from list done view|`delete 1`|"Attend CTF-101" task deleted.|
|Clear all done tasks in list done view|`clear`|"Deletes all done tasks"|
|Switch back to normal list view|`list`|Switches back to normal list view.|
|Archive last 2 tasks in list|`done 28 29`|"Buy kitkat", "Visit com room" tasks archived.|
|Undo the previous archive command|`undo`|"Buy kitkat", "Visit com room" tasks undoed.|
|Archive recurring task|`done 7`|"Teach CS1010 Tutorial" start and end date changed to 17 nov, 10 nov one archived in done list.|
|List all tasks today|`list today`| All tasks with date on the day of this manual test listed. To verify this, add a task `manual test dearjim by today`, and check that it appears along with all the other tasks on today. Type `undo` to revert adding this test task.|
|Find all tasks containing a string, case insensitive|`find read`|All tasks containing read in their names shown. 3 "Read Harry Potter tasks shown"|
|Get back to normal list view|`list`|Return to the normal list view.|

## Finishing up
|Action|Command|Result|
|---|---|---|
|Change storage location again|`store nonexistingfile`|In the same folder where this DearJim is being run, a new file `nonexistingfile.xml` will be created, and all current data transfered over. The storage location in ui display also changes. (bottom left)|
|Finish the manual testing and exit application|`exit`|DearJim closes. Yay!|
