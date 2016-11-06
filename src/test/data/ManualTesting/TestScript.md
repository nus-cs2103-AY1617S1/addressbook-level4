# Manual Scripted Testing


## Loading the sample data


1. Download the emeraldo.jar from the latest [release] (https://github.com/CS2103AUG2016-T10-C3/main/releases) in github.
2. Place emeraldo.jar in your desired folder for testing.
3. In the same folder where emeraldo.jar is placed, create a folder called ‘data’.
4. Place the sample data (SampleData.xml) into the folder you have just created.
5. Rename SampleData.xml to emeraldo.xml
6. You can now launch emeraldo.jar with all the sample data loaded.
<br>
Overview of GUI
<img src="../../../../assets/UI_overview.png" width="1000">
<br>


<br>
## Add Command


Type `add “buy cheezels” ` into the command box
> Output console will display `New task added: buy cheezels  |  Date and Time: anytime  |  Tags: ` <br>
> New task added in the task view with index 51<br>
> Task view jumps to the task that has just been added
<br>


Type `add “Sam’s wedding” on 23 dec` into the command box
> Output console will display `New task added: Sam's wedding  |  Date and Time: on 23 Dec 2016  |  Tags: `<br>
> New task added in the task view with index 52<br>
> Task view jumps to the task that has just been added
<br>


Type `add “buy christmas present for christmas party” by 25/12/2016, 3pm #present` into the command box
> Output console will display `New task added: buy christmas present for christmas party  |  Date and Time: by 25 Dec 2016, 3.00pm  |  Tags: `<br>
> New task added in the task view with index 53 <br>
> Task view jumps to the task that has just been added
<br>


Type `add “Christmas wonderland @ gardens by the bay” from 13-12, 1600 to 13-12, 1900` into the command box
>Output console will display `New task added: Christmas wonderland @ gardens by the bay  |  Date and Time: from 13 Dec 2016, 4.00pm to 13 Dec 2016, 7.00pm  |  Tags: ` <br>
> New task added in the task view with index 54 <br>
> Task view jumps to the task that has just been added
<br>


Type `add “Christmas wonderland @ gardens by the bay” from 13 Dec 16, 4:00pm to 13 Dec 16, 7:00pm` into the command box
> Output console will display `This task already exists in the task manager`<br>
> Task will not be added
<br>


<br>
## Edit Command


Type `edit 2 “Surprise Anniversary date with darling”`
> Output console will display `Edited task: Surprise Anniversary date with darling  |  Date and Time: on 15 Nov 2016  |  Tags: ` <br>
> Task with index 2 edited with new description <br>
> Task view jumps to the task that has been edited
<br>


Type `edit 3 from 12 nov, 11am to 12 nov, 12pm` 
> Output console will display `Edited task: Apple internship interview  |  Date and Time: from 12 Nov 2016, 11.00am to 12 Nov 2016, 12.00pm  |  Tags: ` <br>
> Task with index 3 edited with new date and time <br>
> Task view jumps to the task that has been edited
<br>


Type `edit 5 by 08/11/2016, 5.20pm`
> Output console will display `Edited task: Buy MRT concession pass  |  Date and Time: by 08 Nov 2016, 5.20pm  |  Tags: ` <br>
> Task with index 5 edited with new date and time <br>
> Task view jumps to the task that has been edited
<br>


<br>
## Tag Edit Command


Type `tag add 4 #impt` into the command box
> Output console will display `Edited task: 4` <br>
> Task with index 4 will have the tag [impt] added <br>
> Task view jumps to the task that has been edited 
<br>


Type `tag add 4 #today` into the command box
> Output console will display `The tag you have entered is one of our reserved words and is not allowed. Please use another word. Our list of reserved words are: [today, tomorrow, thisweek, nextweek, thismonth, nextmonth, completed]`
<br>


Type `tag delete 4 #impt` into the command box
> Output console will display `Edited task: 4` <br>
> Task with index 4 will have the tag [impt] deleted <br>
> Task view jumps to the task that has been edited
<br>


Type `tag delete 1 #friends` into the command box
> Output console will display `Tag does not exist in the indicated task!`
<br>


Type `tag clear 1` into the command box
> Output console will display `Tags are empty!`
<br>


Type `tag add 2 #Anniversary` <br>
Type `tag add 2 #Date` <br>
Type `tag clear 2` into the command box
> Output console will display `Edited task: 2` <br>
> Task with index 2 will have all its tags cleared <br>
>Task view jumps to the task that has been edited
<br>


<br>
## Delete Command


Type `delete 5` into the command box
> Output console will display `Deleted task: Buy MRT concession pass  |  Date and Time: by 08 Nov 2016, 5.20pm  |  Tags: ` <br>
> Task with index 5 will be deleted
<br>


Type `delete 55` into the command box
> Output console will display “The task index provided is invalid”
<br>


<br>
## Completed Command


Type `completed 3` into the command box
> Output console will display `Completed task: Apple internship interview  |  Date and Time: Completed on 6 Nov 2016 at 6.54pm  |  Tags: ` <br> 
> Task with index 3 will be marked as completed
<br>


Type `completed 3` again into the command box
> Output console will display `This task is already completed`
<br>


Type `completed 55` again into the command box
> Output console will display `The task index provided is invalid`
<br>


<br>
## Clear Command


Type `clear` into the command box
> Output console will display `Emeraldo has been cleared!` <br>
> All task in Emeraldo will be deleted
<br>


<br>
## Undo Command


Type `undo` into the command box
> Output console will display `Undo successful!` <br>
> The clear command will be reversed
<br>


Type `add “undo this add”` into the command box <br>
Type `undo` into the command box
> Output console will display `Undo successful!` <br>
> The add command will be reversed
<br>


Type `edit 3 “undo this edit”` into the command box <br>
Type `undo` into the command box
> Output console will display `Undo successful!` <br>
> The edit command will be reversed
<br>


Type `delete 3 ` into the command box <br>
Type `undo` into the command box
> Output console will display `Undo successful!` <br>
> The delete command will be reversed
<br>


Type `completed 4` into the command box <br>
Type `undo` into the command box
> Output console will display `Undo successful!` <br>
> The completed command will be reversed
<br>


Type `tag add 3 #test` into the command box <br>
Type `undo` into the command box
> Output console will display `Undo successful!` <br>
> The tag command will be reversed
<br>


<br>
## List Command
<br>


Task to be added with a date to be defined by the tester. <br>
1. Type `add “today’s task” on (Today’s date)`
2. Type `add “tomorrow’s task” on (Tomorrow’s date) #friends`
3. Type `add “nextweek’s task” on (Any date next week)`
4. Type `add “nextmonth’s task” on (Any date next month)`
5. Type `add “uncompleted task” on 5 nov`
6. Type `Completed 54`
7. Type `Completed 55`
8. Type `Completed 56`
9. Type `Completed 57`
<br>


Type `list` into the command box
> Output console will display `Tasks that are uncompleted are successfully listed!`<br>
> All uncompleted tasks will be listed<br>
> There will be 52 tasks listed in total
<br>


Type `list friends` into the command box
> Output console will display `Uncompleted tasks with tag 'friends' successfully listed!
7 tasks listed!`<br>
> All uncompleted tasks with tag ‘friends’ will be listed<br>
> The tasks you created would not appear.
<br>


Type `list today` into the command box
> Output console will display `Uncompleted tasks happening today successfully listed!`<br>
> All uncompleted tasks occurring today will be listed.<br>
> The tasks you created would not appear.
<br>


Type `list tomorrow` into the command box
> Output console will display `Uncompleted tasks happening tomorrow successfully listed!`<br>
> All uncompleted tasks occurring tomorrow will be listed.<br>
> The tasks you created would not appear.
<br>


Type `list thisweek` into the command box
> Output console will display `Uncompleted tasks happening this week successfully listed!`<br>
> All uncompleted tasks occurring this week will be listed.<br>
> The tasks you created would not appear.
<br>


Type `list nextweek` into the command box
> Output console will display `Uncompleted tasks happening next week successfully listed!`<br>
> All uncompleted tasks occurring next week will be listed.<br>
> The tasks you created would not appear.
<br>


Type `list thismonth` into the command box
> Output console will display `Uncompleted tasks happening this month successfully listed!`<br>
> All uncompleted tasks occurring this month will be listed.<br>
> The tasks you created would not appear.
<br>


Type `list nextmonth` into the command box
> Output console will display `Uncompleted tasks happening next month successfully listed!`<br>
> All uncompleted tasks occurring next month will be listed.<br>
> The tasks you created would not appear.
<br>




## ListAll Command
<br>


Type `listall` into the command box
> Output console will display `All tasks are successfully listed!`<br>
> All tasks (both completed and uncompleted) will be listed.<br>
> There will be 57 tasks listed in total
<br>


Type `listall friends` into the command box
> Output console will display `All tasks with tag 'friends' successfully listed!
8 tasks listed!`<br>
> All tasks (both completed and uncompleted) that are tagged as “friends” will be listed.<br>
> The task with description “tomorrow’s task” will appear.
<br>


Type `listall today` into the command box
> Output console will display `All tasks happening today successfully listed!`<br>
> All tasks (both completed and uncompleted) occurring today will be listed.<br>
> The task with description “today’s task” will appear.
<br>


Type `listall tomorrow` into the command box
> Output console will display `All tasks happening tomorrow successfully listed!`<br>
> All tasks (both completed and uncompleted) occurring tomorrow will be listed.<br>
> The task with description “tomorrow’s task” will appear.
<br>


Type `listall thisweek` into the command box
> Output console will display `All tasks happening this week successfully listed!<br>
> All tasks (both completed and uncompleted) occurring this week will be listed.<br>
> The task with description “today’s task” will appear.<br>
> The task with description “nextweek’s task” will **not** appear.<br>
> Whether the other 2 tasks you created will appear, depends on what is the date you have input.
<br>


Type `listall nextweek` into the command box
> Output console will display `All tasks happening next week successfully listed!`<br>
> All tasks (both completed and uncompleted) occurring next week will be listed.<br>
> The task with description “nextweek’s task” will appear.<br>
> The task with description “today’s task” will **not** appear.<br>
> Whether the other 2 tasks you created will appear, depends on what is the date you have input.
<br>


Type `listall thismonth` into the command box
> Output console will display `All tasks happening this month successfully listed!` <br>
> All tasks (both completed and uncompleted) occurring this month will be listed.<br>
> The task with description “today’s task” will appear.<br>
> The task with description “nextmonth’s task” will **not** appear.<br>
> Whether the other 2 tasks you created will appear, depends on what is the date you have input.
<br>


Type `listall nextmonth` into the command box
> Output console will display `All tasks happening next month successfully listed!` <br>
> All tasks (both completed and uncompleted) occurring next month will be listed. <br>
> The task with description “nextmonth’s task” will appear. <br>
> The task with description “today’s task” will **not** appear. <br>
> Whether the other 2 tasks you created will appear, depends on what is the date you have input.
<br>


<br>
## Find Command


Type `find task` into the command box
> Output console will display `1 tasks listed!` <br>
> All uncompleted tasks with `task` in its description will be listed.
<br>


Type `find interview` into the command box
> Output console will display `4 tasks listed!` <br>
> All uncompleted tasks with `interview` in its description will be listed.
<br>


Type `find task interview` into the command box
> Output console will display `5 tasks listed!` <br>
> All uncompleted tasks with `interview` or `task` in its description will be listed.
<br>


<br>
## FindAll Command


Type `findall task` into the command box
> Output console will display `5 tasks listed!` <br>
> All tasks (both completed and uncompleted) with `task` in its description will be listed.
<br>


Type `findall task interview` into the command box
> Output console will display `10 tasks listed!` <br>
> All tasks (both completed and uncompleted) with `interview` or `task` in its description will be listed.
<br>


<br>
## Help Command


Type `help` into the command box
> Output console will not remain unchanged <br>
> A help window will appear which directs the user to our online User Guide in Github.
< br>


Press <kbd>alt<kbd/> + <kbd>F4<kbd\>


> Output console will display ‘Opened help window.’ <br>
> The help window will be closed
<br>


<br>
## SaveTo Command


Type `saveto ./newFolder/` into the command box
> Output console will display `Save location changed to ./newFolder/emeraldo.xml` <br>
> Status bar at the bottom showing the save location will show the new path ./newFolder/emeraldo.xml <br>


Type `add “test data”` into the command box
> New emeraldo.xml will be created at ./newFolder/ when a change is made <br>
> Confirm the creation of a folder named `newFolder` and the presence of a file named `emeraldo.xml` by going to the folder where emeraldo.jar was placed.
<br>


Type `saveto default` into the command box
> Output console will display `Save location changed to ./data/emeraldo.xml` <br>
> Status bar at the bottom showing the save location will show the new path ./data/emeraldo.xml <br>
<br>


Close the program and relaunch emeraldo.jar <br>
> Task data stored in ./data/emeraldo will be loaded <br>
> The task with description “test data” will not be in the task view as it is saved in ./newFolder/emeraldo.xml.
<br>


<br>
## MotivateMe Command


Type `motivateme` into the command box
> Output console will display a motivational quote generated at random


<br>
<br>


**END OF MANUAL TESTING**
