# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Cheatsheet](#command-cheatsheet)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.
   > Having any Java 8 version is not enough.
   > This app will not work with earlier versions of Java 8
1. Download the latest `commando.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your To-do Manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/Ui.png" width="600">
4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`add`**` event participate in 'Eat and Become Fat' competition from 5 Sep 12:30 to 17:30`: adds an event with title "participate in 'Eat and Become Fat' competition" with start date & time of 5th September 12:30 and end date & time of 5th Septemeber 17:30 
   * **`delete`**` 3` : deletes the to-do item visually numbered with a "3"
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>

## Features
> **Command Format**
> * Words in `UPPERCASE` in a command format are the parameters for the command
> * Words in `[square brackets]` are optional parameters for the command
> * `...` after a parameter denote that the command accepts multiple values for the parameter
> * The order of parameters is fixed

### Viewing Help: `help`
Help is also shown if you enter an invalid command e.g. `abcd`.
 
### Adding an Event: `add event DESCRIPTION from START_DATETIME to END_DATETIME [#TAG...]`
Examples: 
* `add event participate in 'Eat and Become Fat' competition from 5 Sep 12:30 to 17:30 #important`
* `add event meeting with Dr Tan from 30 Sep 9:00 to 10:00 #work #boringstuff`
* `add event wild party in hostel room from 31 Dec 2016 to 1 Jan 2017 #yolo` 

### Adding a Deadline: `add deadline DESCRIPTION by DUE_DATETIME [#TAG...]`
Examples:
* `add deadline do Homework 10 by 23 Sep`
* `add deadline release V0.5 by 7 Nov 2359`
* `add deadline finish FYP by tmr 2359`

### Adding a Floating Task: `add task DESCRIPTION [#TAG...]` 
Examples:
* `add task shop for groceries: banana, pineapple, watermelon #housework`
* `add task watch Lord of the Rings`

### Editing an Event: `edit INDEX [DESCRIPTION] [from START_DATETIME] [to END_DATETIME] [#TAG...]`
Edits the details of an event, listed on screen with a number of `INDEX`.

Note that:

* At least one of the optional parameters should be included, if not the command does nothing interesting
* The `DESCRIPTION` or `#TAG...` parameters would replace the existing description and the whole list of tags for the event.

### Editing an Deadline: `edit INDEX [DESCRIPTION] [by DUE_DATETIME] [#TAG...]`
Edits the details of a deadline, listed on screen with a number of `INDEX`. Similar to editing an event.

### Editing a Floating Task: `edit INDEX [DESCRIPTION] [#TAG...]`
Edits the details of a floating task, listed on screen with a number of `INDEX`. Similar to editing an event.
  
### Deleting a To-Do Item: `delete INDEX`
Deletes an event, deadline or floating task, listed on screen with a number of `INDEX`.

### Clearing all To-Do Items: `clear`
Clears all to-do items. Use with caution.

### Finding To-Do Items: `find KEYWORD...`
Searches for and only lists all events, deadlines and floating tasks which have descriptions or tags containing *all* of the given keywords.

Search logic:

* Search is case insensitive
* Order of the keywords does not matter - e.g. `Fish Ball` will match `Ball Fish`
* Substrings will be matched - e.g. `fish` will match `Ballfish`
* Only items that have a match with all input keywords will be shown (i.e. `AND` search) - e.g. `fish ball` will match `fish for a ball` but not `fish for a fish`

### Setting Save Location: `set storage FILEPATH`
Changes the location where the to-do list is saved on the file system to `FILEPATH`.

### Exporting: `export FILEPATH`
Exports the current to-do list as a save data file at `FILEPATH`.

### Importing: `import FILEPATH`
Imports a valid save data file at `FILEPATH` and overrides the current to-do list with the imported to-do list. 

### Undoing: `undo`
Undos the last `add`, `edit`, `delete`, `clear` or `import` command

### Exiting the program : `exit`
Closes the application.

## FAQ   

### Saving data
Your to-do list is automatically saved to your file system after any command that changes the data. There is no need to save manually. 

### Sharing data across devices
Use the `export` command to generate a save data file, transfer the file to a 2nd device, and use the `import` command on the 2nd device to import your to-do list from that save file. Alternatively, you can use a cloud-syncing service (e.g. (Dropbox)[dropbox.com]) and the `set storage` command to sync the to-do list automatically by setting to the same storage file on both devices. 

### Defining file paths
A file path can be an absolute file path (e.g. C:/home/sally/statusReport) or file path relative to the `.jar` file (e.g. joe/foo).
	   
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

Command		   | Format  
-------------------| :-------- 
Help               | `help`
Add Event          | `add event DESCRIPTION from START_DATETIME to END_DATETIME [#TAG...]`
Add Deadline       | `add deadline DESCRIPTION by DUE_DATETIME [#TAG...]`
Add Floating Task  | `add task DESCRIPTION [#TAG...]`
Edit Event         | `edit INDEX [DESCRIPTION] [from START_DATETIME] [to END_DATETIME] [#TAG...]`
Edit Deadline      | `edit INDEX [DESCRIPTION] [by DUE_DATETIME] [#TAG...]`
Edit Floating Task | `edit INDEX [DESCRIPTION] [#TAG...]`
Delete To-Do Item  | `delete INDEX`
Clear              | `clear`
Find               | `find KEYWORD...`
Export             | `export FILEPATH`
Import             | `Import FILEPATH`
Set Save Location  | `set storage FILEPATH`
Undo               | `undo`
Exit               | `exit`
