# User Guide

Download the latest release below:

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)


## Quick Start

1. Ensure you have Java version `1.8.0_60` or later installed in your Computer.  
   > This app will not work with earlier versions of Java 8.
   
2. Download the latest `UJDTDL.jar` from the [releases](../../../releases) tab.
3. Copy the file to the folder you want to use as the home folder for the application.
4. Double-click the file to start the app. The GUI should appear in a few seconds. 
5. Type the command in the command box and press <kbd>Enter</kbd> to execute it.  
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
6. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**` Finish CS2103T homework -d next Friday` : 
     adds a new task with the deadline set at next Friday
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
7. Refer to the [Features](#features) section below for details of each command.

## Features



### Navigation

When launched the application will have several views as shown 


#### Command Format

* Words in `UPPERCASE` are the parameters.
* Items in `[SQUARE BRACKETS]` are optional.
* To specify parameters, such as the deadline for a task, use flags. Flags follow the Unix command format - single dash (eg. `-f`) for short flags and double dash for long flags (eg. `--all`)
* Items with `...` after them can have multiple instances.
* Most commands that updates a task require an `INDEX` parameter. This is number shown to the left of the task as shown in the screenshot below

:::danger
Insert image indicating where is the task index
:::


### Switch views : `view`
Switch between different views.  
Format: **`view`**` VIEW`

You can also use the underlined character in the view's name as the shortcut when switching views. 

Examples:

**`view`**` COMPLETED`
:::danger
Screenshot of the top bar 
:::

### Viewing help : `help`
Format: **`help`**

Shows help window which gives list of commands and their actions.
Help is also shown if you enter an incorrect command e.g. `abcd`
 
### Adding a task or event: `add`

Adds a new task or event.  
Format: 

**`add`**` TASK NAME [-d DEADLINE] [-m DESCRIPTION] [-r TIME] [-p] [-t TAG 1, TAG 2...]`
**`add`**` EVENT NAME -d START END [-m DESCRIPTION] [-l LOCATION] [-p] [-t TAG 1, TAG 2...]`
 

#### Parameters

For tasks, only the name is required. Tasks will be turned into events automatically if two dates are specified under the `-d` flag. 

* `TASK/EVENT NAME` - The name of the task or event
* `-d DEADLINE` - Specify a deadline for the task 
* `-d START END` - Specify the start and end time for the event 
* `-m DESCRIPTION` - Add a longer description for the task or event
* `-l LOCATION` - Where the event is held
* `-r TIME PERIOD` - Create a recurring task. 
* `-p` - Pins the task to the top of the list 
* `-t TAG 1, TAG 2, ...` - Tags to search for the task at a later time

Task or event titles should be kept short and concise. If you need to add more details, use `-m` to add a long description. Use `-l` to indicate the location where an event is happening. Time sensitive tasks can be marked with a deadline. Recurring tasks like 

You can attach tags to an item with `-t` to organize your tasks and events. Tags are comma separated and case insensitive. You can also pin important items by adding `-p` so that they always appear on the top the list they appear in.

All tasks are marked as pending (incomplete) when they are first entered. 

#### Examples: 

* **`add`**` Submit V.0.0 -d 5 Oct 2359 -t CS2103T, Week 8`
* **`add`**` Destroy the Earth -m Going to need a lot of TNT for this. Remember to get them at sale on Friday - 50% discount on bulk orders!`
* **`add`**` Music at the park -d 11 Dec 6pm to 8pm -l Botanic Gardens  -p`
* **`add`**` CS2103T Tutorial -r every Wednesday -t Y2S1`

### Deleting a task: `delete`
Deletes the specified task from todo list.  
Format: **`delete`**` INDEX`

Deletes the task at the specified `INDEX`. The index refers to the index number shown in the most recent listing.

#### Examples: 

* **`delete`**` 2`  
  Deletes the 2nd task on the list 
  
* **`find`**` Y2S1`  
  **`delete`**` 1`  
  Deletes the 1st task in the results of the **`find`**` Y2S1` command.

### Marking a task complete: `complete`

Format:

**`complete`**` INDEX`
**`complete`**` --all`

You can use this command to marks a task as completed. Completed task appear strukthrough to indicate they have been complete. Using the `all` flag will mark all tasks on the current view as completed. 

:::info
Need a picture here to show how that looks like
:::

### Pinning a task: `pin`

Format: 

**`pin`**` INDEX`

If a particular task or event is important, you can pin it to the top of every list the item appears in using this command. You can also use this command to unpin any pinned task. 

:::info
Need a picture here to show how that looks like
:::

### Editing a task: `edit`
Allows you to edit a specific task. 
Format: 

**`edit`**` INDEX [NAME] [-d DEADLINE] [-m DESCRIPTION] [-r TIME] [-p] [-t TAG 1, TAG 2...]`
**`edit`**` INDEX [NAME] [-d START END] [-m DESCRIPTION] [-l LOCATION] [-p] [-t TAG 1, TAG 2...]`

Use the `edit` command to make changes to the task specified by `INDEX`. The command accepts the same parameters as the `add` command. Any edit is immediately saved.

### Finding tasks based on tags or name: `find`
Finds tasks whose tags/name contain any of the given keywords.  
Format: **`find`**` KEYWORD [MORE KEYWORDS]`

The search is case insensitive and the order of the keywords does not matter. Only the title and tags are searched, and tasks matching at least one keyword will be returned.

Examples: 
* **`find`**` John`  
  Returns `Meet John for lunch`
* **`find`**` Jo`  
  Returns any task having title `Meet John for lunch`, `Jogging at the park`, or `Josting fights at the gym`
* **`find`**` Jo Ja`  
  Returns `Meet John for lunch`, `Meet Jane for lunch`, or `Jack and Jane's wedding`

### Exiting the program : `exit`
Exits the program.  
Format: **`exit`**  

### Undoing an action: `undo`
Undos most recent action.
Format: **`undo`**

Note: Only applies to commands which have made changes to the todo list.

### Redoing an action: `redo`
Redos most recent action which was undone.
Format: **`redo`**

### Loading an existing data file : `load`

Format: **`load`**` FILENAME`

Loads in another save file. You can use this to restore a backup or switch to different lists so you can for example have separate lists for school and home. Ensure that the file is converted into an `.xml` format for usage by the program.

Example:
**`load`**` "myDiscountTodo.xml"`

#### Autosave

Your todo list is saved automatically every time it is updated. There is no need to save manually.

### Changing the save location: `save`

Format: **`save`**
Todo list data are saved in a file called `discountTodo.xml` in the `data` folder. You can change the save file by specifying the file path as the first argument when running the program.


## FAQ
- How do I back up my data?
	As your data is saved to a `.xml` file that you specified, you can simply copy this file to a back up storage of your choice.
- How do I sync my data with multiple devices?
	Simply load the `.xml` file to a cloud sync service like Dropbox or Google Drive, and all updates will be reflected to all devices using the file.
- 
- How do I pay for this file
	Donations can be made via PayPal or Kashmi. Cash donations are fine too.

###

## Command Summary

Command  | Format  
-------- | :-------- 
Help     | **`help`**
Add      | **`add`**` NAME [-d DEADLINE or START END] [-m DESCRIPTION] [-r TIME] [-p] [-t TAG 1, TAG 2...]` 
Delete   | **`delete`**` INDEX`
Complete | **`complete`**` INDEX`
Pin      | **`pin`**` INDEX`
Edit     | **`edit`**` INDEX [NAME] [-d DEADLINE or START END] [-m DESCRIPTION] [-r TIME] [-p] [-t TAG 1, TAG 2...]` 
Find     | **`find`**` KEYWORD [MORE KEYWORDS...]`
Undo     | **`undo`**
Redo     | **`redo`**
Load     | **`load`**` FILENAME`
Save     | **`save`**
View     | **`view`**` VIEW`
