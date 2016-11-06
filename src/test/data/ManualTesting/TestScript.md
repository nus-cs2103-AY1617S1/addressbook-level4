### **Loading data into JYM**

1. First download JYM and the sample xml file into a folder (ex. Downloads), then start the program by double clicking on it.

2. Second, in folder `Downloads`, create a new folder named `data`, then rename the xml file to `taskmanager.xml` and put it inside this `data` folder.

3. Start JYM and it will be loaded with the sample data.

Input :

Expected : Invalid command format!

help: Shows program usage instructions.

Example: `help`

Input : **undo**

Expected : There is no last operation.

**Add Command**

Input: Description

Example: `Do homework`

Expected : New task added:  Description: do homework Deadline: no deadline Location: no location

Parameters: [at LOCATION] [by/due DEADLINE]

Input: `Play soccer at MPSH by Friday 2pm`

Expected: New task added:  Description: Play soccer Deadline: 11-Nov-2016 02:00 PM Location: MPSH

**Update Command**

Input: update [INDEX] [DESCRIPTION] [at LOCATION] [by/due DEADLINE]

Example: `update 1 New Task`

Expected: Updated Task:  Description: New Task Deadline: [ORIGINAL DEADLINE] Location: [ORIGINAL LOCATION]

UI: The first task's descriptoin becomes `New Task`

**Delete Command**

Input: delete [INDEX]

Example: `delete 1`

Expected: Deleted Task:  Description: [DESCRIPTION] Deadline: [ORIGINAL DEADLINE] Location: [ORIGINAL LOCATION]

UI: The first task is deleted

**Undo Command**

Input: undo

Example: `undo`

Expected: undo the last delete command

UI: the task list will be recover to the last list

**Find Command**

Input: find [INDEX]

Example: `find New Task`

Expected: [SOME NUMBER] of tasks listed

UI: shows all the task with description New Task

**List Command**

Input: list

Example: `list`

Expected: list all tasks

UI: all the task will be listed

**Complete Command**

Input: complete [INDEX]

Example: `complete 1`

Expected: Completed Task!

UI: move the first task in the incomplete panel to the complete panel

**Storage Command**

Input: saveto [PATH]

Example: `saveto Folder1`

Expected: Successfully set the storage location!

UI: after this command, when new command performed, folder named `Folder1` will be created

**Clear Command**

Input: clear

Example: `clear`

Expected: Task manager has been cleared!

UI: all task list will be cleared
