# Manual Testing Steps:
--------------
<b>How to load sample data </b> <br> 
<ol>
<li><ol><li>Make a copy of SampleData.xml from main\src\test\data\ManualTesting</li>
	 <li>Rename it to taskbook.xml</li>
	 <li>Navigate to main\data</li>
	 <li>Overwrite and replace the taskbook.xml file with the renamed taskbook.xml</li></ol>	
<li>Double-click the .jar file and the pre-saved data will automatically be loaded.</li>
<li>Commands to enter in command box:</li>
</ol>

<b>1 Add Command:</b><br>

### 1.1 Adding a Todo task:
<ol><li>Type: add watch sausage party #movie #home #alone</li>
	<li>Expected result: task added in the todo list in the bottom right panel with the index of `T13`.</li> 
	<li>In the result display box, directly below the command box, there will be a message printed<br>
"New todo added: watch sausage party Date: no date Start: no start End: no end overdue: 0 category:3 complete: false Tags:[alone][movie][home]"</li></ol>

### 1.2 Adding a Deadline task:
<ol><li>Type: add pay electricity bill; 27.12.16; 2pm</li>
	<li>Expected result: task added in the deadline list in the top right panel with the index of `D9`.</li> 
	<li>In the result display box, directly below the command box, there will be a message printed<br>
"New deadline added: pay electricity bill Date: 27-12-16 Start: no start End: 1400 overdue: 0 category:2 complete: false Tags:"</li></ol>

### 1.3 Adding a Deadline task with no end time:
<ol><li>Type: add pay phone bill; 20.12.16</li>
	<li>Expected result: task added in the deadline list in the top right panel with the index of `D8`.</li> 
	<li>In the result display box, directly below the command box, there will be a message printed<br>
"New deadline added: pay electricity bill Date: 27-12-16 Start: no start End: 2359 overdue: 0 category:2 complete: false Tags:"</li></ol>

### 1.4 Adding a Event task:
<ol><li>Type: add [meeting with CS2103T; 10.12.16; 2pm; 3pm] #school</li>
	<li>Expected result: task added in the event list in the left panel with the index of `E7`.</li> 
	<li>In the result display box, directly below the command box, there will be a message printed<br>
"New event added: meeting with CS2103T Date: 10-12-16 Start: 1400 End: 1500 overdue: 0 category:1 complete: false Tags:[school]"</li></ol>
### 1.5 Adding a Event task with no end time
<ol><li>Type: add [party @ Siloso; 24.12.16; 10pm] #enjoy</li>
	<li>Expected result: task added in the event list in the left panel with the index of `E11`.</li> 
	<li>In the result display box, directly below the command box, there will be a message printed<br>
"New event added: party @ Siloso Date: 24-12-16 Start: 10pm End: 2359 overdue: 0 category:1 complete: false Tags:[enjoy]"</li></ol>

Note: The default end time is 2359

### 1.6 Adding a Event task with no start time and end time:
<ol><li>Type: add [group presentation; 10.01.17] #CS2101</li>
	<li>Expected result: task added in the event list in the left panel with the index of `E13`.</li> 
	<li>In the result display box, directly below the command box, there will be a message printed<br>
"New event added: group presentation Date: 10-01-17 Start: local time End: 2359 overdue: 0 category:1 complete: false Tags:[CS2101]"</li></ol>

Note: the start time will reflect the current time on your computer.<br>

### 1.7 Adding a Event task that is overdue:
<ol><li>Type: add [meet up with friends; 121212; 1pm; 5pm]</li>
	<li>Expected result: task added in the event list in the left panel with the index of `E1`.</li> 
	<li>In the result display box, directly below the command box, there will be a message printed<br>
"New event added: meet up with friends Date: 121212 Start: 1pm End: 5pm overdue: 1 category:1 complete: false Tags:[school]"</li></ol>

Note: The selected event should be in red.

### 1.8 Adding a tag:
<ol><li>Type: add T9 #friends</li>
	<li>Expected result: the todo with the index T9 will have a tag "friends" added to it.</li>
	<li>In the result display box, directly below the command box, there will be a message printed<br>
	"Edited task: T9       Changes: add #friends"</li></ol></ol>

Note: Tags can be updated in all categories.<br>

<b>2 Deleting Command</b><br>

### 2.1 Deleting an event:
<ol><li>Type: delete E1</li>
	<li>Expected result: the event with the index E1 will disappear.</li>
	<li>In the result display box, directly below the command box, there will be a message printed<br>
	"Deleted task: [E1]"</li></ol>

### 2.2 Deleting a multiple tasks:
<ol><li>Type: delete E1, D1, T1</li>
	<li>Expected result: the event with the index E1, deadline with the index D1, todo with the index T1 will disappear.</li>
	<li>In the result display box, directly below the command box, there will be a message printed<br>
	"Delete task: [E1, D1, T1]"</li></ol>

Note: This is able to work for deleting multiple events, deadlines and todos individually.<br>		

### 2.3 Deleting a range of tasks:
<ol><li>Type: delete T1-T3</li>
	<li>Expected result: the todos with the index T1 to T3 will disappear.</li>
	<li>In the result display box, directly below the command box, there will be a message printed<br>
	"Delete task: [T1, T2, T3]"</li></ol>

Note: This is able to work for events and deadlines too.<br>	
	
<b>3 Edit Command</b><br>

### 3.1 Editing a description:
<ol><li>Type: edit E1 des movie @ JP</li>
	<li>Expected result: the event with the index E1 will have the new updated description.</li>
	<li>In the result display box, directly below the command box, there will be a message printed<br>
	"Edited task: E1    Changes: des movie @ JP"</li></ol>

### 3.2 Editing a date:
<ol><li>Type: edit E1 date 101216</li>
	<li>Expected result: the event with the index E1 will have the new updated description.</li>
	<li>In the result display box, directly below the command box, there will be a message printed<br>
	"Edited task: E1    Changes: date 101216"</li></ol>

Note: The event will be shifted down to E6.

### 3.3 Editing an end:
<ol><li>Type: edit E6 end 2300</li>
	<li>Expected result: the event with the index E6 will have the new updated description.</li>
	<li>In the result display box, directly below the command box, there will be a message printed<br>
	"Edited task: E6     Changes: end 2300"</li></ol>

### 3.4 Editing a start:
<ol><li>Type: edit E6 start 10pm</li>
	<li>Expected result: the event with the index E6 will have the new updated description.</li>
	<li>In the result display box, directly below the command box, there will be a message printed<br>
	"Edited task: E6    Changes: start 10am"</li></ol>


### 3.5 Editing a specific tag:
<ol><li>Type: edit E6 tag leisure>family</li>
	<li>Expected result: the event with the index E6 will have the leisure tag updated to family.</li>
	<li>In the result display box, directly below the command box, there will be a message printed<br>
	"Edit task: E6     Changes: tag leisure>family"</li></ol>

Note: Tags can be edited for events and deadlines.<br>

### 3.6 Editing a tag:
<ol><li>Type: edit E9 tag computer</li>
	<li>Expected result: the event with the index E6 will have the leisure tag updated to family.</li>
	<li>In the result display box, directly below the command box, there will be a message printed<br>
	"Edit task: E9     Changes: tag computer"</li></ol>

<b>4 Find Command</b><br>

### 4 Finding by partial keywords:
<ol><li>Type: find school</li>
<li>Expected result: Tasks containing the word `school` will be shown.</li>
<li>In the result display box, directly below the command box, there will be a message printed<br>
"Found 4 events! Found 6 deadines! Found 1 Todo!"</li></ol>

Note: The date, start time and end time can also be included for searching.<br>

<b>5 List Command</b><br>

### 5.1 Listing done tasks:
<ol><li>Type: list done</li>
	<li>Expected result: The tasks that are marked as done will be listed.</li>
	<li>In the result display box, directly below the command box, there will be a message printed<br>
	"Listed all completed tasks"</li></ol>

### 5.2 List undone tasks:
<ol><li>Type: list</li>
	<li>Expected result: the task that are uncompleted will be listed.</li>
	<li>In the result display box, directly below the command box, there will be a message printed<br>
	"Listed all tasks"</li></ol>
	
<b>6 Done Command</b><br>

### 6.1 Completing a task:
<ol><li>Type: done T1</li>
	<li>Expected result: The todo with the index T1 will be hidden away.</li>
	<li>In the result display box, directly below the command box, there will be a message printed<br>
	"Marked task as done:[T1]"</li></ol>

### 6.2 Completing multiple tasks:
<ol><li>Type: done T1, E1, D1</li>
	<li>Expected result: The todo with the index T1, deadline with the index D1, event with the index E1 will be hidden away.</li>
	<li>In the result display box, directly below the command box, there will be a message printed<br>
	"Marked task as done:[T1, E1, D1]"</li></ol>

Note: This is able to work for completing multiple events, deadlines and todos individually.<br>

### 6.3 Completing range of task:
<ol><li>Type: done D1-D3</li>
	<li>Expected result: The deadline with the index D1-D4 will be hidden away.</li>
	<li>In the result display box, directly below the command box, there will be a message printed<br>
	"Marked task as done:[D1, D2, D3, D4]"</li></ol>

Note: This is able to work for events and deadlines too.<br>

<b>7 Undo Command</b><br>

### 7.1 Undo once:
<ol><li>Type: undo </li>
	<li>Expected result: The latest command will be undone. The three deleted deadline tasks will be back.
	<li>In the result display box, directly below the command box, there will be a message pritned<br>
	"Undo successful."</li></ol>

### 7.2 Undo multiple:
<ol><li>Type: undo 2</li>
	<li>Expected result: The latest command will be undone. All the completed tasks done previously are now uncompleted.
	<li>In the result display box, directly below the command box, there will be a message pritned<br>
	"Undo successful."</li></ol>

<b>8 Redo Command</b><br>

### 8.1 Redo Once:
<ol><li>Type: redo </li>
	<li>Expected result: Redo the previous command that was undone. Read Toto-chan will be marked as done.
	<li>In the result display box, directly below the command box, there will be a message pritned<br>
	"Redo succesful"</li></ol>

### 8.1 Redo multiple:
<ol><li>Type: redo 2</li>
	<li>Expected result: Redo the previous command that was undone. All the tasks that was marked undone is now done again.
	<li>In the result display box, directly below the command box, there will be a message pritned<br>
	"Redo succesful"</li></ol>
	
<b>9 Help Command</b><br>
<ol><li>Type: help</li>
	<li>Expected result: Brings out the command summary.
	<li>In the result display box, directly below the command box, there will be a message pritned<br>
	"Opened help window."</li></ol>
	
<b>10 Storage Command</b><br>
<ol><li>Type: storage `file path`</li>
	<li>Expected result: The storage file will be now stored in the new location.
	<li>In the result display box, directly below the command box, there will be a message pritned<br>
	"storage folder changed to `file path`\taskbook.xml"</li></ol>

<b>11 Incorrect Commands</b><br>
<ol><li>Type: adds</li>
	<li>Expected result: It is an incorrect command</li>
	<li>In the result display box, directly below the command box, there will be a message pritned<br>
	"Unkown command"</li></ol>
<ol><li>Type: add </li>
	<li>Expected result: It will print the invalid command format.</li>
	<li>In the result display box, directly below the command box, there will be a message pritned<br>
	"Invalid command format!"
Note: The examples of the add command will be printed in as well. <br>
<ol><li>Type: delete t1</li>
	<li>Expected result: It is an incorrect command as the index is case insensitive.</li>
	<li>In the result display box, directly below the command box, there will be a message pritned<br>
	"Invalid command format!"</li></ol>
Note: The examples of the delete command will be printed in as well. <br>
<ol><li>Type: add [event; 121212; 2359; 2200]</li>
	<li>Expected result: It is an incorrect command as the start time cannot be later than the end time.</li>
	<li>In the result display box, directly below the command box, there will be a message pritned<br>
	"The end time cannot be earlier or equal to the start time!"</li></ol>
<ol><li>Type: add deadline; 12\12\12</li>
	<li>Expected result: The format of the dates are wrong.</li>
	<li>In the result display box, directly below the command box, there will be a message pritned<br>
	"Dates should be entered in the format DDMMYY, DD.MM.YY, DD/MM/YY, DD-MM-YY"</li></ol>
<ol><li>Type: add E1 #YOLO!</li>
	<li>Expected result: Tags should be alphanumeric.</li>
	<li>In the result display box, directly below the command box, there will be a message pritned<br>
	"Tags names should be alphanumeric"</li></ol>
<ol><li>Type: edit E1 start 2359</li>
	<li>Expected result: It is an incorrect command as the start time cannot be later than the end time.</li>
	<li>In the result display box, directly below the command box, there will be a message pritned<br>
	"The start time cannot be later or equals to the end time!"</li></ol>
<ol><li>Type: edit E1 end 0001</li>
	<li>Expected result: It is an incorrect command as the end time cannot be earlier than the start time.</li>
	<li>In the result display box, directly below the command box, there will be a message pritned<br>
	"The end time cannot be earlier or equals to the start time!"</li></ol>
<ol><li>Type: undo 2000</li>
	<li>Expected result: It is not accepted as there are not so many things to be undo.</li>
	<li>In the result display box, directly below the command box, there will be a message pritned<br>
	"There are not so many tasks available to be undone"</li></ol>
<ol><li>Type: redo 2000</li>
	<li>Expected result: It is not accepted as there are not so many things to redo.</li>
	<li>In the result display box, directly below the command box, there will be a message pritned<br>
	"There are not so many tasks available to redo"</li></ol>
<ol><li>Type: List anything</li>
	<li>Expected result: It is not accepted as the command format is wrong.</li>
<ol><li>Type: undo 2000</li>
	<li>Expected result: It is not accepted as there are not so many things to be undo.</li>
	<li>In the result display box, directly below the command box, there will be a message pritned<br>
	"Invalid command format."</li></ol>
Note: The correct command examples will be listed in the results display window.
