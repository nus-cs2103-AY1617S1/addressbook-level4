# Manual Testing Steps:
--------------
<b> How to load sample data </b> <br> 
<ol>
<li><ol><li>Make a copy of SampleData.xml from main\src\test\data\ManualTesting</li>
	 <li>Rename it to taskbook.xml</li>
	 <li>Navigate to main\data</li>
	 <li>Overwrite and replace the taskbook.xml file with the renamed taskbook.xml</li></ol>	
<li>Double-click the .jar file and the pre-saved data will automatically be loaded.</li>
<li>Commands to enter in command box:</li>
</ol>

<b>Add Command:</b><br>
<ol> 
<li><ol><li>Adding a Todo task:</li>
	<li>Type: add watch sausage party #movie #home #alone</li>
	<li>Expected result: task added in the todo list in the bottom right panel with the index of `T13`.</li> 
	<li>In the result display box, directly below the command box, there will be a message printed<br>
"New todo added: watch sausage party Date: no date Start: no start End: no end overdue: 0 category:3 complete: false Tags:[alone][movie][home]"</li></ol>
<li><ol><li>Adding a Deadline task:</li>
I	<li>Type: add pay electricity bill; 27.12.16; 2pm</li>
	<li>Expected result: task added in the deadline list in the top right panel with the index of `D9`.</li> 
	<li>In the result display box, directly below the command box, there will be a message printed<br>
"New deadline added: pay electricity bill Date: 27-12-16 Start: no start End: 1400 end overdue: 0 category:2 complete: false Tags:"</li></ol>
<li><ol><li>Adding a Deadline task with no end time:</li>
	<li>Type: add pay phone bill; 20.12.16</li>
	<li>Expected result: task added in the deadline list in the top right panel with the index of `D8`.</li> 
	<li>In the result display box, directly below the command box, there will be a message printed<br>
"New deadline added: pay electricity bill Date: 27-12-16 Start: no start End: 2359 end overdue: 0 category:2 complete: false Tags:"</li></ol>
<li><ol><li>Adding a Event task:</li>
	<li>Type: add [meeting with CS2103T; 10.12.16; 2pm; 3pm] #school</li>
	<li>Expected result: task added in the event list in the left panel with the index of `E7`.</li> 
	<li>In the result display box, directly below the command box, there will be a message printed<br>
"New event added: meeting with CS2103T Date: 10-12-16 Start: 1400 End: 1500 end overdue: 0 category:1 complete: false Tags:[school]"</li></ol>
<li><ol><li>Adding a Event task with no start time and end time:</li>
	<li>Type: add [group presentation; 10.01.17] #CS2101</li>
	<li>Expected result: task added in the event list in the left panel with the index of `E12`.</li> 
	<li>In the result display box, directly below the command box, there will be a message printed<br>
"New event added: group presentation Date: 10-01-17 Start: local time End: 2359 end overdue: 0 category:1 complete: false Tags:[CS2101]"</li></ol>

Note: the start time will reflect the current time on your computer.<br>

<li><ol><li>Adding a tag:</li>
	<li>Type: add T9 #friends</li>
	<li>Expected result: the todo with the index T9 will have a tag "friends" added to it.</li>
	<li>In the result display box, directly below the command box, there will be a message printed<br>
	"Edited task: T9       Changes: add #friends"</li></ol></ol>

Note: Tags can be updated in all categories.<br>

<b>Deleting Command</b><br>
<ol>
<li><ol><li>Deleting an event:</li>
	<li>Type: delete E12</li>
	<li>Expected result: the event with the index E12 will disappear.</li>
	<li>In the result display box, directly below the command box, there will be a message printed<br>
	"Deleted task: [E12]"</li></ol>
<li><ol><li>Deleting a multiple tasks:</li>
	<li>Type: delete E1, D1, T1</li>
	<li>Expected result: the event with the index E1, deadline with the index D1, todo with the index T1 will disappear.</li>
	<li>In the result display box, directly below the command box, there will be a message printed<br>
	"Delete task: [E1, D1, T1]"</li></ol>

Note: This is able to work for deleting multiple events, deadlines and todos individually.<br>		

<li><ol><li>Deleting a range of tasks:</li>
	<li>Type: delete T1-T3</li>
	<li>Expected result: the todos with the index T1 to T3 will disappear.</li>
	<li>In the result display box, directly below the command box, there will be a message printed<br>
	"Delete task: [T1, T2, T3]"</li></ol></ol>

Note: This is able to work for events and deadlines too.<br>	
	
<b>Edit Command</b><br>
<ol>
<li><ol><li>Editing a description:</li>
	<li>Type: edit E1 des movie @ JP</li>
	<li>Expected result: the event with the index E1 will have the new updated description.</li>
	<li>In the result display box, directly below the command box, there will be a message printed<br>
	"Edited task: E1    Changes: des movie @ JP"</li></ol>

Note: Other variables can be changed by changing the variable name from des to date, start and end.<br>

<li><ol><li>Editing a tag:</li>
	<li>Type: edit E1 tag leisure>family</li>
	<li>Expected result: the event with the index E1 will have the leisure tag updated to family.</li>
	<li>In the result display box, directly below the command box, there will be a message printed<br>
	"Edit task: E1     Changes: tag leisure>family"</li></ol></ol>

Note: Tags can be edited for events and deadlines.<br>

<b>Find Command</b><br>
<ol>
<li>Finding by partial keywords:</li>
<li>Type: find school</li>
<li>Expected result: Tasks containing the word `school` will be shown.</li>
<li>In the result display box, directly below the command box, there will be a message printed<br>
"Found 4 events! Found 6 deadines! Found 1 Todo!"<li></ol>

Note: The date, start time and end time can also be included for searching.<br>

<b>List Command</b><br>
<ol>
<li><ol><li>Listing done tasks:</li>
	<li>Type: list done</li>
	<li>Expected result: The tasks that are marked as done will be listed.</li>
	<li>In the result display box, directly below the command box, there will be a message printed<br>
	"Listed all completed tasks"</li></ol>
<li><ol><li>List undone tasks:</li>
	<li>Type: list</li>
	<li>Expected result: the task that are uncompleted will be listed.</li>
	<li>In the result display box, directly below the command box, there will be a message printed<br>
	"Listed all tasks"</li><ol></ol>
	
<b>Done Command</b><br>
<ol>
<li><ol><li>Completing a task:</li>
	<li>Type: done T1</li>
	<li>Expected result: The todo with the index T1 will be hidden away.</li>
	<li>In the result display box, directly below the command box, there will be a message printed<br>
	"Marked task as done:[T1]"</li></ol>
<li><ol><li>Completing multiple tasks:</li>
	<li>Type: done T1, E1, D1</li>
	<li>Expected result: The todo with the index T1, deadline with the index D1, event with the index E1 will be hidden away.</li>
	<li>In the result display box, directly below the command box, there will be a message printed<br>
	"Marked task as done:[T1, E1, D1]"</li></ol>

Note: This is able to work for completing multiple events, deadlines and todos individually.<br>

<li><ol><li>Completing range of task:</li>
	<li>Type: done D1-D3</li>
	<li>Expected result: The deadline with the index D1-D4 will be hidden away.</li>
	<li>In the result display box, directly below the command box, there will be a message printed<br>
	"Marked task as done:[D1, D2, D3, D4]"</li></ol></ol>

Note: This is able to work for events and deadlines too.<br>


	
