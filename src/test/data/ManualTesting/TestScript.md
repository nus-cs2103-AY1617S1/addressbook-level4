## Setting up
1. Prepare a folder to put .jar file in
2. Ensure .jar is named `TaskManager.jar`
3. Create folder `data` inside prepared folder where `TaskManager.jar` resides
4. Place `protected.xml` and `SampleData.xml`in `data`
5. Ensure `protected.xml` is read-only (on windows, right click-> properties-> ensure `Read-only` under Atrributes: is checked, or use chmod)
6. Run `TaskManager.jar`, it should display an empty list
7. To load the SampleData.xml, you may use the <kbd>directory</kbd> command via the application<br> OR close the application, open up the generated `config.json` and edit `taskManagerFilePath` field to `data/SampleData.xml`. Restart the application manually. <br>

> Note: In the event `TaskManager.jar` is not named as such or Operating System is not supported, user is required to manually close and start the application again. The application will prompt the user to do so in the message box.

## Test script

|	Step	|	Action	|	Expected result	|
|	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	|	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	|	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	|
|	Help command	|		|		|
|	1	|	Enter `help` or `h`	|	Window will show, displaying userguide	|
|		|		|		|
|	Directory command	|		|		|
|	1	|	Enter `directory nonexistingfile/file`	|	Error message will be shown	|
|	2	|	Enter `directory ` or `dir ` followed by the filepath of `SampleData` without `.xml`	|	Succcess message if file exists	|
|		|	Alternatively, enter `directory` or `dir` to navigate using native file browser	|	A file browser will show, prompting user to select a file to load data from.	|
|		|		|		|
|	Backup command	|		|		|
|	1	|	Enter `backup c:/inaccessible` or a directory that is write protected	|	Error message will be shown, informing user directory is invalid	|
|	2	|	Enter `backup data/protected`	|	Error message will be shown, informing user data is not saved successfully	|
|	3	|	Enter `backup ` or `b ` followed by `backups/testdatabackup1`	|	Succcess message if directory is not protected and file exists, else an error message.	|
| 	|	Alternatively, enter `backup` or `b` to navigate using native file browser	|	A file browser will show, prompting user to select a file to save data to.	|
|		|		|		|
|	Clear Command	|		|		|
|	1	|	Enter `clear`	|	All tasks in the list will be cleared.	|
|		|		|		|
|	Directory command	|		|		|
|	1	|	Enter `directory ` or `dir ` followed by the filepath of `backups/testdatabackup1` without .xml	|	Succcess message if file exists, else an error message.	|
|		|		|		|
|	Find & List Command	|		|		|
|	1	|	Enter `find` or `f` followed by a keyword or a series of keywords to search by	|	Tasks with keyword(s) within its parameters will be filtered and displayed.	|
|	2	|	Enter `list`	|	All tasks will be displayed once again.	|
|		|		|		|
|	Sort Command	|		|		|
|	1	|	Enter `sort invalidparameter`	|	Error message will show, listing valid parameters.	|
|	2	|	Enter `sort name` or `sort n` or `sort title`	|	Tasks will be sorted according to their names lexicographically in ascending order	|
|	3	|	Enter `sort starttime` or `sort s` or `sort start`	|	List of tasks will be sorted according to the given parameter.	|
|	4	|	Enter `sort endtime` or `sort e` or `sort end`	|	List of tasks will be sorted according to the given parameter.	|
|	5	|	Enter `sort completed` or `sort c` or sort `done`	|	List of tasks will be sorted according to the given parameter.	|
|	6	|	Enter `sort favorite` or `sort favourite` or `sort fav` or `sort f`	|	List of tasks will be sorted according to the given parameter.	|
|	7	|	Enter `sort overdue` or `sort o` or `sort over`	|	List of tasks will be sorted according to the given parameter.	|
|	8	|	Enter `sort` or `sort default` or `sort standard`	|	List of tasks will be sorted according to a default set of rules.	|
|		|		|		|
|	Add command	|		|		|
|	1	|	Enter `add floating task #test`	|	This floating task with tag test will be shown on the list 	|
|	2	|	Enter `add go lecture, at this afternoon`	|	The new task called "go lecture" with start time today's date and 12.00am will add to task panle 	|
|	3	|	Enter `add finish cs2103 tutorial, by tomorrow morning`	|	The new task called `finish cs2103 tutorial"`with due time tomorrow's date at 08.00pm will be shown on the list 	|
|	4	|	Enter `add watch movie the day after tomorrow, at the day after tomorrow night`	|	The new task called `watch movie the day after tomorrow` with start time date of the day after tomorrow at 08.00pm will be shown on the list 	|
|	5	|	Enter `add cs2103 exam, from tomorrow 6pm to 5pm`	|	Error message will be shown, stating the start time can't be later than the end time 	|
|	6	|	Enter `add return money to Tom, at what time`	|	Error message will be shown, stating incorrect time format has been provided 	|
|	7	|	Enter `add cs2103 exam, from tomorrow 5pm to 7pm`	|	The new task called `cs2103 exam` with start time tomorrow's date at 5.00pm and with end time tomorrow's date at 7.00pm will be shown on the list 	|
|	8	|	Enter `add do a lot homework, from today 7pm to 9pm by 11pm`	|	The new task called `do a lot homework` with start time today's date at 7.00pm and with end time same date at 9.00pm and due same date at 11.00pm will be shown on the list 	|
|	9	|	Enter `add go to school, on monday`	|	The new task called `go to school` with start time next monday's date will be shown on the list 	|
|	10	|	Enter `add to do gym, to 3pm from 2pm`	|	The new task called `to do gym` with start time today's date at 3.00pm and with end time today's date at 2.00pm will be shown on the list 	|
|	11	|	Enter `add christmas party, from this christmas 7pm to 11.59pm #party`	|	The new task called `christmas party` with start time 2016-12-25 at 7.00pm and end time the same date at 11.59pm with tag party will be shown on the list 	|
|	12	|	Enter `add have a nap, at 1pm by 3pm`	|	The new task called `christmas party` with start time today's date at 1.00pm and with due the same date at 3.00pm will be shown on the list 	|
|		|		|		|
|	Done command	|		|		|
|	1	|	Enter `done ` followed by an index that corresponds to a task	|	Task will be highlighted green	|
|	2	|	Enter `done ` followed by an index that an index that does not exist in the task	|	Error message will be shown	|
|		|		|		|
|	Repeat command	|		|		|
|	1	|	Enter `sort name`	|	Tasks will be sorted according to their names lexicographically in ascending order	|
|	2	|	Enter `repeat ` followed by an index corresponding to any task without a time	|	Error message will be shown	|
|	3	|	Enter `repeat (index of a task with a time value) (weekly/w/daily/d/fortnightly/f/yearly/y/monthly/m)`	|	Message will be shown informing changes made to the task	|
|	4	|	Enter `done ` followed by the same index in step 3	|	Task will be highlighted green and a new task with given interval added to the time will be created	|
|	5	|	Enter `done ` followed by the index of the new task generated	|	Task will be highlighted green and a new task with given interval added to the time will be created	|
|		|		|		|
|	Edit command	|		|		|
|	2	|	Enter `add eat macdonlad`	|	The task called `eat macdonlad` will be shown on the list 	|
|	3	|	Enter `edit (index of newly added task) name, eat KFC`	|	Task manager will tell you the task index provided is invalid	|
|	4	|	Enter `edit (index of newly edited task) name, eat KFC`	|	The name of the task will change to "eat KFC"	|
|	5	|	Enter `edit (index of newly edited task) start, tomrrow 12am`	|	The start time of the task will change to tomorrow's date at 12.00pm	|
|	6	|	Enter `edit (index of newly edited task) end, tomorrow 10am`	|	Error message will be shown, stating the start time can't be later than the end time	|
|	7	|	Enter `edit (index of newly edited task) end, tomorrow 1pm`	|	The end time of the task will be changed to tomorrow's date at 1.00pm	|
|	7	|	Enter `edit (index of newly edited task) due, tomorrow 2pm`	|	The deadline time of the task will be changed to tomorrow's date at 2.00pm	|
|	8	|	Enter `edit (index of newly edited task) tag, #delicious #unhealthy`	|	The tag of the task will change to `#delicious, #unhealthy`	|
|		|		|		|
|	Delete Command	|		|		|
|	1	|	Enter `delete ` or `d ` followed by an index that corresponds to a task	|	Task will be removed from the list	|
|	2	|	Enter `delete ` or `d ` followed by an index that does not exist in the task	|	Error message will be shown	|
|		|		|		|
|	Favorite Command	|		|		|
|	1	|	Enter `favorite `, `favourite `, or `fav ` followed by an index that corresponds to a task	|	Task will have a yellow marker beside it	|
|	2	|	Enter `favorite `, `favourite `, or `fav ` followed by an index that does not exist in the task	|	Error message will be shown	|
|		|		|		|
|	Refresh Command	|		|		|
|	1	|	Enter `refresh`	|	Task manager will be refreshed	|
|		|		|		|
|	Undo command	|		|		|
|	1	|	Enter `add go for dental appointment, at 2pm #sgh`	|	New task added in taskmanager	|
|	2	|	Enter `undo` 	|	Task that was recently added is deleted	|
|	3	|	Enter `delete` followed by an index that exist in the task	|	Task with the given index is deleted	|
|	4	|	Enter `undo` 	|	Task that was previously delete is added back to the taskmanager	|
|	5	|	Enter `edit 1 name, test`	|	Task with the given index and field is edited	|
|	6	|	Enter `undo` 	|	Task edits back to where it was orignally was	|
|		|		|		|
|	7	|	Enter `done` followed by an index that exist and is undone in the taskmanager	|	Task will be highlighted in green	|
|	8	|	Enter `undone` followed by an index that exist and is done in the taskmanager	|	Task will be not be highlighted in green	|
|	9	|	Enter `undo` 	|	Previous task will be highlighted green	|
|	10	|	Enter `undo` 	|	Previous task will not be highlighted in green	|
|		|		|		|
|	11	|	Enter `favorite `, `favourite `, or `fav ` followed by an index that is not favorited	|	Task will be highlighted in yellow	|
|	12	|	Enter `unfavorite `, `unfavourite `, or `unfav ` followed by an index that is favorited 	|	Task will not be highlighted in yellow	|
|	13	|	Enter `undo` 	|	Previous task will be highlighted yellow	|
|	14	|	Enter `undo` 	|	Previous task will not be highlighted yellow	|
|		|		|		|
|	15	|	`sort name`	|	Sort to name	|
|	16	|	`undo`	|	Sort to original	|
|		|		|		|
|	17	|	`refresh`	|	Refresh the task manager	|
|	18	|	`undo`	|	Refresh the task manager	|
|		|		|		|
|	19	|	Enter `edit 1 name, test`	|		|
|	20	|	Enter `edit 2 name, test2`	|		|
|	21	|	Enter `undo 2`	|	List will revert back to 2 steps ago	|
|		|		|		|
|		|		|		|
|	Exit Command	|		|		|
|	1	|	Enter `exit`	|	Task manager exits	|

