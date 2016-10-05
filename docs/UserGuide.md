# User Guide 
* [Getting Started](#getting-started)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Getting Started
1. Ensure you have Java version 1.8.0_60 or later installed in your Computer.
2. Download the latest OneLine.jar from the releases tab.
3. Copy the file to the folder you want to use as the home folder for OneLine.
4. Double-click the file to start the app. The GUI should appear in a few seconds.
5. Type command in the command box and hit <kbd>enter</kbd> to run it 


## Features
1. Viewing Help: ` help ` 
> Displays list of available commmands and descriptions 

2. Adding a Task / Event: ` add `  
	- `add <name> [-due <date>] [#<cat>] [#<cat>] ...` 
> If no date is specified, task will be set as a floating task  

    - `add <name> [-on <date>] [-at <location>]...`  
	
	- `add <name> [-from <date><time> -to <date><time>] ...`
> If no date is specified, date will be set to the current day, or the next day if set time has passed  
> If no time is specified, start times will be set to 0000 and end times to 2359 

3. Editing: ` edit ` 
	- `edit <index> [-due <date>]` : edits task specified by index
	- `edit #<oldCat> #<newCat>` : edits category name 
    
4. Listing All Tasks: ` list `  
    - `list` :  lists all undone tasks sorted by by deadline  
    - `list <day / week>` : lists undone tasks with deadline in the current day / next 7 days  
    - `list float` : lists undone tasks with no deadline
    - `list #<cat>` : lists undone tasks in category
	- `list done` : lists tasks done within the past 7 days  
    
5. Mark Task as Done: ` done `  
	- `done <index>` : marks task specified by index as done 
    
6. Find: ` find `
	- `find <keyword>` : returns list of tasks with names similar to keyword
	- `find #<keyword>` : returns list of categories with names similar to keyword
	
7. Deleting a Task: ` del `   
    - `del <index>` : deletes task spcified by index 
	- `del #<cat>` : deletes category 

8. Undo: ` undo ` 
9. Redo: ` redo `

10. Change Storage: ` save ` 
	- `save <new path>` : changes the storage file to the file specified by new path 
    
11. Exiting Program: ` exit ` 

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
| add | `add <name> [-due <date>] [#<cat>] [#<cat>] ...` <br /><br /> `add <name> [-on <date>] [-at <location>]...` <br />`add <name> [-from <date><time> -to <date><time>] ...` |
| edit | `edit <index> [-due <date>]`<br /><br />`edit #<oldCat> #<newCat>` |
| list | `list` <br /><br /><br /> `list <day / week>` <br /><br /> `list float`<br />`list #<cat>`<br />`list done` |
| done | `done <index>` |
| find | `find <keyword>` <br /> `find #<keyword>` |
| delete | `del <index>` <br /> `del #<cat>`|
| storage | `save <path>` |
| exit | `exit` |
