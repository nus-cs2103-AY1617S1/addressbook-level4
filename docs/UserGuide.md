# User Guide

* [About](#about)
* [Quick Start](#quick-start)
* [Commands](#commands)
* [FAQ](#faq)
* [Command Cheatsheet](#command-cheatsheet)

## About
CommanDo is a task scheduler that helps you to manage the flood of things you need to do (to-do items).  This application is a primarily text-based – almost all input will be through commands, using the text box at the bottom. In this guide, we will be splitting the to-do items into 3 groups: events, deadlines and floating tasks, which are differentiated by the number of date time fields they require.

## Quick Start

1. Ensure you have Java version `1.8.0_60` or later installed in your Computer.

> Having any Java 8 version is not enough. This application will not work with earlier versions of Java 8

2. Download the latest `commando.jar` from the [releases](../../../releases) tab.

3. Copy the file to the folder you want to use as the home folder for your To-do Manager.

4. Double-click the file to start the app. The GUI should appear in a few seconds. 

   > <img src="images/Ui.png" width="600">
   
5. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
   
6. Some example commands you can try:

   * `add event participate in 'Eat and Become Fat' competition from 5 Sep 12:30 to 17:30`<br>
   adds an event with title "participate in 'Eat and Become Fat' competition" with start date & time of 5th September 12:30 and end date & time of 5th Septemeber 17:30 
   * `delete 3`<br>
   deletes the to-do item visually numbered with a "3"
   * `exit`<br>
   exits the app
   
7. Refer to the [Features](#features) section below for details of each command.<br>

## Commands

### Viewing Help: `help`

`help` or `help [COMMAND]`

Shows a complete list of commands and how to use them properly, or help on a single command.

When no commands are given, a complete list of commands and examples on how to use them are shown. 
If one command is given, examples on how to use that specific command is shown. 

If the fields are incorrect for any other commands, examples of how to use that command will be shown.

### Adding to-do items: `add`

To-do items contain, at minimum, a description of what it is. 
They may be added along with at most 2 date times and 0 or more tags.

#### Events:

An event is demarcated by a starting date-time and ending date-time.

`add event DESCRIPTION from START_DATETIME to END_DATETIME [#TAG...]`

Examples: 
* `add event participate in 'Eat and Become Fat' competition from 5 Sep 12:30 to 17:30 #important`
* `add event meeting with Dr Tan from 30 Sep 9:00 to 10:00 #work #boringstuff`
* `add event wild party in hostel room from 31 Dec 2016 to 1 Jan 2017 #yolo` 

Date & time formats are described in more detail [below](#supported-datetime-formats).

#### Deadlines: 

A deadline is bounded by a due date-time.

`add deadline DESCRIPTION by DUE_DATETIME [#TAG...]`

Examples:
* `add deadline do Homework 10 by 23 Sep`
* `add deadline release V0.5 by 7 Nov 2359`
* `add deadline finish FYP by tmr 2359`

#### Floating Tasks:

A floating task is not bounded by any dates or times.

`add task DESCRIPTION [#TAG...]` 

Examples:
* `add task shop for groceries: banana, pineapple, watermelon #housework`
* `add task watch Lord of the Rings`

### Editing to-do items: `edit`

You can edit existing to-do items by accessing their index, and stating the fields to replace.
The new tags in your input will replace the whole list of tags stored for the target to-do item.
At least one of the optional parameters should be included, if not the command does nothing.

#### Events: 

`edit INDEX [DESCRIPTION] [from START_DATETIME] [to END_DATETIME] [#TAG...]`
Edits the details of an event, listed on screen with a number of `INDEX`.

Examples:
* `edit 1 contemplate existence`
* `edit 1 from 7 feb 1200 to 7 feb 1300`
* `edit 1 #yodo`

#### Deadlines: 

`edit INDEX [DESCRIPTION] [by DUE_DATETIME] [#TAG...]`
Edits the details of a deadline, listed on screen with a number of `INDEX`.

Examples:
* `edit 2 plan shopping trip to IKEA`
* `edit 2 by 6 feb`
* `edit 2 #lunchthere`

#### Floating Tasks: 

`edit INDEX [DESCRIPTION] [#TAG...]`
Edits the details of a floating task, listed on screen with a number of `INDEX`.

Examples:
* `edit 3 watch Inception`
* `edit 3 #withFamily`
  
### Deleting a To-Do Item: `delete`

`delete INDEX`
Deletes an event, deadline or floating task, listed on screen with a number of `INDEX`.

### Clearing all To-Do Items: `clear`

`clear`
Clears all to-do items. Use with caution.

### Finding To-Do Items: `find`

`find KEYWORD...`
Searches for and only lists all to-do items which have descriptions or tags containing *all* of the given keywords.

Search logic:
* If 0 keywords are provided, all to-do items will be shown. 
* Case insensitive. – `Chicken Egg` will match `chicken egg`
* Ordering of the keywords does not matter. – `Chicken Egg` will match `Egg Chicken`
* Parts of words will be matched. – `chickens` will match `chicken`, `chicken` will not match `chickens`
* All keywords must be present. – `find chicken egg` will match `chicken lay egg` but not `bird lay egg`

### Marking a To-Do Item done: `mark`

`mark INDEX`
Marks an event, deadline or floating task, listed on screen with a number of `INDEX` done.

### Marking a To-Do Item not done: `unmark`

`unmark INDEX`
Marks an event, deadline or floating task, listed on screen with a number of `INDEX` not done.

### Setting Save Location: `set`

`set storage FILEPATH`
Changes the location where the to-do list is saved on the file system to `FILEPATH`.

A file path can be absolute C:/home/sally/statusReport) or relative to the `.jar` file joe/foo/bar).
* This applies to the next two commands which too, deals with the file location.

### Exporting: `export`

`export FILEPATH`
Exports the current to-do list as a save data file at `FILEPATH`.

### Importing: `import`

`import FILEPATH`
Imports a valid save data file at `FILEPATH` and overrides the current to-do list with the imported to-do list.

### Undoing: `undo`

`undo`
Undos the last `add`, `edit`, `delete`, `clear` or `import` command

### Exiting the program : `exit`

`exit`
Closes the application.

## FAQ   

### Saving data

Your to-do list is automatically saved to your file system after any command that changes the data. There is no need to save manually. 

### Sharing data across devices

Use the `export` command to generate a save data file, transfer the file to a 2nd device, and use the `import` command on the 2nd device to import your to-do list from that save file. Alternatively, you can use a cloud-syncing service (e.g. [Dropbox](dropbox.com)) and the `set storage` command to sync the to-do list automatically by setting to the same storage file on both devices. 

### Supported datetime formats

*CommanDo* supports a wide variety of date and time formats. 

A valid datetime is defined as:

1. A valid date
2. A valid date followed by a valid time

where examples of valid dates include:

- `10 Feburary 2016`
- `10 Feb 2016`
- `10 Feb`
- `10th Feb`
- `Feb 10`
- `10/02/2016` *(DD/MM/YYYY)*
- `10/02/16` *(DD/MM/YY)*
- `10/02` *(DD/MM)*
- `today` 
- `tomorrow` 
- `next Wednesday`
- `x* days after` (Any number bigger than 0 allowed)

and examples of valid time include:

- `19:00`
- `9am`
- `11:59pm`
- `2359`
- `0911h`
- `morning` *(0900h by default)*
- `afternoon` *(1200h by default)*
- `evening` *(1900h by default)*
       
## Command Cheatsheet

* `<>` denotes required details
* `[]` denotes optional
* `...` denotes 0 or more
* `+..` denotes 1 or more

Command		   | Format  
-------------------| :-------- 
Help               | `help`
Add Event          | `add <description> from <start> to <end> [<#TAG>...]`
Add Deadline       | `add <description> by <due> [<#TAG>...]`
Add Floating Task  | `add <description> [<#TAG>...]`
Edit Event         | `edit <index> [description] [from <start>] [to <end>] [<#TAG>...]`
Edit Deadline      | `edit <index> [description] [by <due>] [<#TAG>...]`
Edit Floating Task | `edit <index> [description] [<#TAG>...]`
Delete To-Do Item  | `delete <index>`
Clear              | `clear`
Find               | `find <keyword>+..`
Export             | `export <filepath>`
Import             | `Import <filepath>`
Mark Done          | `mark <index>`
Mark Not Done      | `unmark <index>`
Set Save Location  | `set storage <filepath>`
Undo               | `undo`
Exit               | `exit`
