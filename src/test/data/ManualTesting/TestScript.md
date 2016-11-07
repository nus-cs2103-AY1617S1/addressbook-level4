#Test Script

#### To get the Sample data

1. Copy the 'SampleData.xml' from main/src/test/data/ManualTesting inside the 'data\' folder (if the folder does not exist create the folder)
2. If there is an existing file in the folder,delete the file.
3. Rename the 'SampleData.xml' to 'taskmanager.xml'.
4. Start the 'taskmanager.jar' to run the app.

##Help

1. Type `help`

**Result:** A help window will pop-out.

## Add a floating task

1. Type `add MA1505 d/Midterm`

**Result:** A new task "MA1505" with description "Midterm`" is created at the bottom of the list

## Add an event

1. Type 'add Party d/Edwin's Birthday Party sd/01-11-2015 dd/02-11-2015

**Result:** A new task "Party" with description "Edwin's Birthday Party" and start date of 01-11-2015 08:00 and due date of 02-11-2015 23:59 is created at the bottom of the list

## Add a deadline

1. Type `add PC1221 d/Homework dd/14-11-2015`

**Result:** A new task "PC1221" with description "Homework" and due date of 14-11-2015 23:59 is created at the bottom of the list 

##Add recurring task

1. Type `add CS2103 d/Online quiz i/4 ti/7 dd/07-09-2016"

**Result:** 4 new task "CS2103" with description "Online quiz" is created at the bottom of the list with due date for every 7 days

##Find a task

1. Type `find MA`

**Result:** 3 will be shown on the list.

##List the task

1. Type `list`

**Result:** All the task will be shown in the list.

##Edit a task

1. Type `edit 10 t/CFG1010 Seminar d/F2F Seminar`

**Result:** The task title will be change to "CFG1010 Seminar" with description F2F Seminar.

##Delete a task

1. Type `delete 11`

**Result:** The task will be deleted and existing task will be reindex.

##Undo delete task

1. Type `undo`

**Result:** The task that have been deleted previously will be restore.

##Re-do delete task

1. Type `redo`
 
**Result:** The previously undo will be re-do.

##Change the file folder path

1. `Type save C:\CS2103`

**Result:** It will be save at "C:\CS2103" with a "data\" folder and the "taskmanager.xml" file.

##History Commands

1. Type `history`

**Result:** It will show the history commands that have been use before.

##Set color code

1. Type `edit 4 c/red`

**Result:** Number 4 task will be set as red.

##Complete a task

1. Type `done 22`

**Result:** Task number 22 status will be set as "completed" and will be shifted to the bottom of the list.

##Customize command

1. Type `customize delete f/del`

**Result:** The command "delete" will be set as "del".

##Clear list

1. Type `clear`

**Result:** The list of task will be cleared.