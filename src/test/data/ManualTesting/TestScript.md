Load the sample data<br>
Command: `store` and the filepath of the SampleData file. No need to specify `.xml` <br>
If running this from Jar file, specify the path to this SampleData.xml file depending on where the Jar file is. Or give an absolute path (this varies depending on the Operating System used and the username of the user, directory in which DearJim is cloned..) <br>
If running from Eclipse: `store src\test\data\ManualTesting\SampleData`. (for mac use `/`)<br>
Result: SampleData.xml file data will be loaded into DearJim. You should see >50 Tasks, some done, some undone.

|Action Details|Command Input|Result|
|---|---|---|---|
|Edit a task's name|`edit 1 buy Akshay two farewell gifts`|Task at index 1 name changed to "buy Akshay two farewell gifts"|
|Edit a task's priority|`edit 2 -high`|Task at index 2 priority bar shade turns from yellow to red.|

Undo previous edit command<br>
Command: `undo`<br>
Result: Previously changed task priority is changed back to yellow.<br>

Redo previous edit command<br>
Command: `redo`<br>
Result: Previously changed task priority is changed back to red.<br>

Edit a task's name with escaped input<br>
Command: `edit 1 "increase word count from 1000 to 1500"`<br>
Result: Task at index 1 name changed to "increase word count from 1000 to 1500".<br>

Edit a task's start time<br>
Command: `edit 21 from 10 nov 5pm`<br>
Result: Task at index 21 start date changed to "Thur, 10 Nov 2016, 5pm" and get sorted to index 8.<br>

Edit a task's end time<br>
Command: `edit 9 by 10 nov 6pm`<br>
Result: Task at index 9 end date changed to "Thur, 10 Nov 2016, 6pm".<br>

Edit a task's start and end time<br>
Command: `edit 6 from 11 nov 4pm to 6pm`<br>
Result: Task at index 6 start date changed to "Fri, 11 Nov 2016, 4pm", end date changed to "Fri, 11 Nov 2016, 6pm", and get sorted to index 12.<br>

Edit a task's recurrence rate<br>
Command: `edit 7 repeat every day`<br>
Result: Task at index 7 recurrence rate of "every day" added<br>

Edit a task with invalid end date that occurs before start date<br>
Command: `edit 7 by 4 nov 8pm`<br>
Result: Error message : "End date should be later than start date."<br>

Edit a floating task to include recurrence rate<br>
Command: `edit 22 repeat every fri`<br>
Result: Error message : "For recurring tasks to be valid, at least one DATE_TIME must be provided."<br>

Edit a task's name,start date, end date, recurrence and priority<br>
Command: `edit 2 Visit com room from 9 nov 11am to 12pm repeat every week -low`<br>
Result: Task at index 2 changed to name "Visit com room", start date "Wed 9 Nov 2016, 11am",
end date "Wed 9 Nov 2016, 12pm", recurrence rate of "every week", priority bar shade turns from yellow to green<br>

Edit a task's start date, recurrence and priority<br>
Command: `edit 21 from 24 dec 12pm repeat every year -high`<br>
Result: Task at index 21 changed start date "Sat 24 Dec 2016, 12pm", recurrence rate of "every year",
priority bar shade turns from yellow to red<br>

Edit reset a task start date, end date and recurrence<br>
Command: `edit 2 -reset start end repeat`<br>
Result: Task at index 2 get sorted to index 21 with no start date, end date and recurrence<br>

Add a floating task<br>
Command: `Read Harry Potter`<br>
Result: Task with name "Read Harry Potter", priority bar shaded yellow<br>

Undo previous add command<br>
Command: `undo`<br>
Result: Previously added task "Read Harry Potter" is deleted.<br>

Redo previous add command<br>
Command: `redo`<br>
Result: Previously deleted task "Read Harry Potter" is re-added.<br>

Add a task with start date<br>
Command: `Read Harry Potter from 30th Nov`<br>
Result: Task with name "Read Harry Potter", with start date "Wed, 30 Nov 2016, 12:00 AM", priority bar shaded yellow<br>

Add a task with end date<br>
Command: `Read Harry Potter by 30th Nov`<br>
Result: Task with name "Read Harry Potter", with end date "Wed, 30 Nov 2016, 11:59pm", priority bar shaded yellow<br>

Add a task with start and end date<br>
Command: `Read Harry Potter from 30th Nov 8am to 9am`<br>
Result: Task with name "Read Harry Potter", with start date "Wed, 30 Nov 2016, 8:00am", end date "Wed, 30 Nov 2016, 9:00am", priority bar shaded yellow<br>

Add a task with recurrence rate<br>
Command: `Jog repeat every Monday`<br>
Result: Task with name "Jog", with recurrence rate "every Monday", priority bar shaded yellow. Start date will take the nearest Monday. (i.e if today is Monday, start date will be today. Else, start date will be next Monday)<br>
Command: `Jog from 20th Nov 10am repeat every 3 days`<br>
Result: Task with name "Jog", with start date "Sun, 20 Nov 2016, 10:00am", recurrence rate "Every 3 Days", priority bar shaded yellow<br>

Add a task with priority<br>
Command: `Buy kitkat -high`<br>
Result: Task with name "Buy kitkat", with priority bar shaded red<br>

Add a task that contains all of these<br>
Command: `Overnight cycling from 10th Nov 10pm to 2am repeat every week -low`<br>
Result: Task with name "Overnight cycling", with start date "Thu, 10 Nov 2016, 10:00pm", end date "Fri, 11th Nov 2016, 2:00am", recurrence rate "Every Week", priority bar shaded green<br>

Add a task with multiple keywords<br>
Command: `Go to the beach from my house from 10th Nov 10am to 11am`<br>
Result: Task with name "Go to the beach from my house", with start date "Thu, 10 Nov 2016, 10:00am", end date "Thu, 10 Nov 2016, 11:00am", priority bar shaded yellow<br>

Add a task with end date earlier than start date<br>
Command: `eat from today 10pm to yesterday 10pm`<br>
Result: Error message, showing invalid command format, "End date should be later than start date." and add command format.<br>

Add a task with invalid date<br>
Command: `eat from 40 Nov`<br>
Result: Error message, showing invalid command format, "Invalid date." and add command format.<br>

Add a task with invalid rate<br>
Command: `eat from 10 Nov repeat every 0 days`<br>
Result: Error message, showing invalid command format, recurring constraints and add command format.<br>

Add a task with invalid time period<br>
Command: `eat from 10 Nov repeat every 5 bobs`<br>
Result: Error message, showing invalid command format, recurring constraints and add command format.<br>

Add a task with missing time period<br>
Command: `eat from 10 Nov repeat every 5`<br>
Result: Error message, showing invalid command format, recurring constraints and add command format.<br>

Add a recurring task without dates<br>
Command: `eat repeat every 3 days`<br>
Result: Error message, showing invalid command format, recurrence and date constraints, and add command format.<br>

Add a task with relative dates<br>
Command: `eat from this wed to next thurs`<br>
Result: Task with name "eat", start date will take the nearest Wednesday, end date will take the next Thursday.<br>

Add a task with repeat start dates<br>
Command: `eat from today from tomorrow`<br>
Result: Error message, showing invalid command format, "Repeated start times are not allowed.", and add command format.<br>

Add a task with repeat end dates<br>
Command: `eat by today by tomorrow`<br>
Result: Error message, showing invalid command format, "Repeated end times are not allowed.", and add command format.<br>

Add a task with escaped input<br>
Command: `"increase word count from 1000 to 1500" from 10th Nov to 11th Nov`<br>
Result: Task with name "increase word count from 1000 to 1500", with start date "Thu, 10 Nov 2016, 12:00am", end date "Fri, 11th Nov 2016, 11:59pm", priority bar shaded yellow<br>

Help <br>
Command: `help` <br>
Result: Help window pops up. Press any key while this window is active to close it. <br>

Delete first 3 tasks in list<br>
Command: `delete 1 2 3`
Result: "Jog", "increase word count from 1000 to 1500", "eat" tasks are deleted.

Undo previous delete command<br>
Command: `undo`<br>
Result: Restore previous 3 deleted tasks.<br>

Delete last task in list<br>
Command: `delete 33`<br>
Result: "bring my pet Han out" task is deleted.<br>

Delete an invalid index<br>
Command: `delete 33`<br>
Result: Error message: "The task index provided is invalid"<br>

Delete a mix of valid and invalid indexes<br>
Command: `delete 1 31 34 50 100`<br>
Result: "Jog" and "Read Harry Potter", the 1st and 31st tasks deleted. Invalid indexes ignored.<br>

Delete all invalid indexes<br>
Command: `delete 33 34 35 200`<br>
Result: Error message: "The task index provided is invalid"<br>

Archive first task in list<br>
Command: `done 1`<br>
Result: "increase word count from 1000 to 1500" archived<br>

Look at archived tasks<br>
Command: `list done`<br>
Result: Switches to list done view. Index 28 is the newly archived task "increase word count from 1000 to 1500"<br>

Add to list done view<br>
Command: `buy a pet kitty`<br>
Result: Error message: "Cannot do this command on done list. Please switch back to normal list view by typing list"<br>

Edit a task in list done view<br>
Command: `edit 1 new name`<br>
Result: Error message: "Cannot do this command on done list. Please switch back to normal list view by typing list"<br>

Delete from list done view<br>
Command: `delete 1`<br>
Result: "Attend CTF-101" task deleted.<br>

Clear all done tasks in list done view<br>
Command: `clear`<br>
Result: "Deletes all done tasks"<br>

Switch back to normal list view<br>
Command: `list`<br>
Result: Switches back to normal list view.<br>

Archive last 2 tasks in list<br>
Command: `done 28 29`<br>
Result: "Buy kitkat", "Visit com room" tasks archived.<br>

Undo the previous archive command<br>
Command: `undo`<br>
Result: "Buy kitkat", "Visit com room" tasks undoed.<br>

Archive recurring task
Command: `done 7`<br>
Result: "Teach CS1010 Tutorial" start and end date changed to 17 nov, 10 nov one archived in done list.

List all tasks today<br>
Command: `list today`<br>
Result: All tasks with date on the day of this manual test listed. To verify this, add a task `manual test dearjim by today`, and check that it appears along with all the other tasks on today. Type `undo` to revert adding this test task.<br>

Find all tasks containing a string, case insensitive<br>
Command: `find read`<br>
Result: All tasks containing read in their names shown. 3 "Read Harry Potter tasks shown"<br>

Get back to normal list view<br>
Command: `list`<br>
Result: Return to the normal list view.<br>

Change storage location again<br>
Command: `store nonexistingfile`<br>
Result: In the same folder where this DearJim is being run, a new file `nonexistingfile.xml` will be created, and all current data transfered over. The storage location in ui display also changes. (bottom left)<br>

Finish the manual testing and exit application<br>
Command: `exit`<br>
Result: DearJim closes. Yay!<br>
