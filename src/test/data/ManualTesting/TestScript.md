# Test Script


## Basic Command Summary

Command | Format | Usage Example
-------- | :------- | :--------
Add | `add TASKNAME d/<DEADLINE> p/<PRIORITY>` | add CS2103 Project d/231217 p/4
Edit | `edit INDEX <TASKNAME> d/<DEADLINE> p/<PRIORITY>` | edit CS2103 Project d/071116 p/5
Delete | `delete INDEX` | delete 2
List | `list`
Listall | `listall`
Listtag | `listtag KEYWORD` | listtag important
Find | `find KEYWORD <MORE_KEYWORDS>` | find CS2101 Report
Complete | `complete INDEX` | complete 1
Help | `help`
Undo | `undo`
Revert | `rev`
Update | `update`
Save | `save ./data/FILE_NAME.xml` | save ./data/jimsList.xml
Load | `Load ./data/FILE_NAME.xml` | load ./data/jimsList.xml
Scroll | `Scroll POINT` | scroll top
Clear | `clear`
Repeat | `repeat INDEX SCHEDULE` | repeat 3 weekly
Exit | `exit`

<div style="page-break-after: always;"></div>

## Test Sequence
- click on the icon and access the application
> * starts up the application  

- load ./data/ManualTesing/SampleData.xml
> * loads 'SampleData.xml' and closes the application

- restart the application

- listtag lesson
> * all tasks that contain 'lesson' as a tag will be shown

- find ger
> *  all tasks that has the name 'ger' regardless of spacing, wording or case will be shown

- delete 3
> * deletes the third task on the list 

- undo
> * the previously deleted task will now be back on the list.

- rev
> * undoes the 'undo' command previously given, the third task will be once again deleted

- edit 1 n/CS2111 s/010716 p/5
> * edits the first task on the current list with a new name, startline and priority

- complete 3
> * the third task will be marked as completed, and all startline and deadline will be set as blank

- list
> * all incompete tasks will be shown

- listall
> * shows all tasks regardless of it being completed or not

- repeat 1 weekly
> * a new tag [weekly] will be added to the first task and the task will also continuously repeat on the same day of the week till the user stops it.

- complete 49
> * the task for that week is completed, the task due for the next week will still be seen on list.

- undo
> * the completed state of the task is undone 

- unpdate 
> * the list will be updated and will reflect TPTM's color coded task warning system (TPTM is automatically updated every minute without any user prompt)

- repeat 49 off
> *  the tag [weekly] is deleted and the task will no longer repeat weekly

- help
> * user guide will appear on a pop up.

- save ./src/test/data/ManualTesting/SampleData2.xml 
> * saves all the changes made to SampleData.xml to SampleData2.xml  

- clear
> * a confirmation window will pop out, click confirm to clear TPTM and remove all data from SampleData.xml.

- exit
> * exits the application
