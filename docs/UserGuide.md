# User Guide 
* [Getting Started](#getting-started)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Getting Started
1. Ensure you have Java version 1.8.0_60 or later installed in your Computer.
	> Having any Java 8 version is not enough.  
	> This app will not work with earlier versions of Java 8.
2. Download the latest OneLine.jar from the releases tab.
3. Copy the file to the folder you want to use as the home folder for OneLine.
4. Double-click the file to start the app. The GUI should appear in a few seconds.
5. Type command in the command box and hit <kbd>enter</kbd> to run it 


## Features
1. Viewing Help: ` help ` 
2. Adding a Task / Event: ` add `  
	- ` add <task name> due <date> in <category> priority <high / med / low> `
    - `add <event name> from <start date / time> to <end date / time>` 
    
3. Editing: ` edit ` 
	- ` edit <task name> <new task name> due <new date> in <new cat> priority <new priority> ` : edit task
	- ` edit <category name> <new cat name> ` : edit category name 
    
4. Listing All Tasks: ` list `  
    - ` list all ` :  lists all undone tasks sorted by priority, followed by deadline  
    - ` list <day / week / month> ` : list undone tasks / events with deadlines in the day / week / month  
    - ` list floating `: lists undone tasks with no deadline
    - ` list <category name>` : lists undone tasks in category
	- ` list done `: lists all done tasks 
    
5. Mark Task as Done: ` done `  
	- ` done <task name> ` 
    
5. Unmark Done Task: ` undone `   
    - ` undone <task name> `
	
6. Deleting a Task: ` del `   
    - ` del <task name> ` 

7. Undo: ` undo ` 
8. Redo: ` redo `

9. Change Storage: ` storage ` 
	- `storage <new path> ` : changes the storage file to the file specified by new path 
    
10. Search: ` find `
	- `find <search term>`: returns tasks with names similar to search term
    
11. Exiting Program : ` exit ` 

## User Stories

| As a... | I want... | So that I...  | Conditions |
|------|--------------|------------------|------------|
| user | to Create/View/Update/Delete tasks | - | - |
| user | to be able to view tasks easily | can focus on tasks | good user interface that doesn't clutter tasks |
| user | intuitive commands | don't have to remember commands | - |
| user | to mark tasks as done | can keep track of my progress | - |
| user | undo any mistakes I have made | don't have to worry about making irreversible moves | - |
| user | change storage location | can store tasks in a portable device | - |

## FAQ
Q: How do I transfer my data to another Computer?

A: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Address Book folder.

## Command Summary
| Command | Format |
| ------- | ------ |
| help | `help` |
| add | `add <task> due <date> priority <high/med/low>` <br /> `add <event> from <start date/time> to <end date/time>` |
| edit | `edit <task> <new task name> due <date date> in <new category> priority <new priority>`<br />`edit <category name> <new category name>` |
| list | `list all`<br /> `list <day/week/month>`<br />`list floating`<br /> `list <category>` <br /> `list done` |
| done | `done <task>` |
| undone | `undone <task>` |
| delete | `del <task> ` |
| storage | `storage <path>` |
| search | `find <search term>` |
| exit | `exit` |
