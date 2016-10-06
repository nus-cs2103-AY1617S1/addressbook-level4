# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.

1. Download the latest `taskmanager.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add contact`**` John Doe p/98765432 e/johnd@gmail.com a/John street, block 123, #01-01` :
     adds a contact named `John Doe` to the Address Book.
   * **`delete`**` 3` : deletes the 3rd task/contact shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

#### Adding a task: `add `
Adds a task to the task manager.<br>
Format: `add DESCRIPTION [pr/RANK] [time/TIME] [t/TAG]...`

> * Tasks can have different priorities, normal by default, high or low
> * Deadlines can be set for tasks
> * Tasks can have any number of tags (including 0)

Examples:
* `add Midterms pr/high time/wednesday t/important`
* `add get eggs pr/low t/family`
* `add organize room`

#### Adding a person: `add contact`
Adds a person to the address book.<br>
Format: `add contact NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]...`

> Persons can have any number of tags (including 0)

Examples:
* `add contact John Doe p/98765432 e/johnd@gmail.com a/John street, block 123, #01-01`
* `add contact Betsy Crowe p/1234567 e/betsycrowe@gmail.com a/Newgate Prison t/criminal t/friend`

#### Listing all tasks : `list`
Shows a list of all tasks in the task manager.<br>
Format: `list [-t] [-pr] [-t/TAGS]...`

> * Tasks are sorted chronologically by default
> * Tasks without deadlines are listed at the end when chronologically sorted

Modifiers | Action
---|---
-t | Tasks are listed chronologically
-pr | Tasks are listed by priority
-t/TAG | Tasks with the specified tag are listed

#### Listing all persons: `list contact`
Shows a list of all persons in the address book.<br>
Format: `list contact`

#### Finding all tasks containing any keyword in their description: `find`
Finds tasks whose description contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case sensitive. e.g `Gives` will not match `give`
> * The order of the keywords does not matter. e.g. `Give Eggs` will match `Eggs Give`
> * Only the description is searched.
> * Only full words will be matched e.g. `Return` will not match `Returns`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Return` will match `Return Car`

Examples:
* `find Tutorial`<br>
  Returns `Tutorial 8` but not `tutorial`
* `find Return Lunch Meeting`<br>
  Returns Any tasks having description containing `Return`, `Lunch`, or `Meeting`

#### Finding all persons containing any keyword in their name: `find contact`
Finds persons whose names contain any of the given keywords.<br>
Format: `find contact KEYWORD [MORE_KEYWORDS]`

> * The search is case sensitive. e.g `hans` will not match `Hans`
> * The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
> * Only the name is searched.
> * Only full words will be matched e.g. `Han` will not match `Hans`
> * Persons matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Hans` will match `Hans Bo`

Examples:
* `find contact John`<br>
  Returns `John Doe` but not `john`
* `find contact Betsy Tim John`<br>
  Returns Any person having names `Betsy`, `Tim`, or `John`

#### Deleting a task/person: `delete`
Deletes the specified person from the address book.<br>
Format: `delete INDEX`

> Deletes the person at the specified `INDEX`.
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples:
* `list`<br>
  `delete task 2`<br>
  Deletes the 2nd task in the task manager.
* `find contact Betsy`<br>
  `delete 1`<br>
  Deletes the 1st person in the results of the `find` command.

#### Select a task/person: `select`
Selects the person identified by the index number used in the last task/person listing.<br>
Format: `select INDEX`

> Selects the person and loads the Google search page the person at the specified `INDEX`.
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples:
* `list`<br>
  `select 2`<br>
  Selects the 2nd task in the task manager.
* `find contact Betsy` <br>
  `select 1`<br>
  Selects the 1st person in the results of the `find` command.

#### Editing a task/person: `edit`
Edits the task/person last selected.<br>
Format: `edit INPUT [MORE_INPUT]`

> * Edits the task/person by replacing the information stored with the input accordingly
> * Inputs are the same as specified in the `add` or `add contact` functions
> * Entries can be removed by calling the modifier but not specifying anything

Examples:
* `list`<br>
  `select 3`<br>
  `edit Complete tutorial 7 priority/ time/`<br>
  Edits the 3rd task in the task manager by replacing the description, resetting the priority and removing the deadline
* `find Chance`<br>
  `select 1`<br>
  `edit Serendipity p/92345678`<br>
  Renames the 2nd person found with the name `Chance`to `Serendipity` and and changes the recorded phone number to `92345678`

#### Listing all tags used: `list tags`
Lists all the tags used in the task manager and/or address book.<br>
Format: `list tags [-t] [-c]`

> Lists all tags used in both task manager and address book by default

Modifiers | Action
---|---
-t | List tags used in task manager
-c | List tags used in address book

#### Adding tags to a task/person: `add tag`
Add tags to last selected task/person.<br>
Format: `add tag [TAG]...`

Example:
* `list contact`<br>
  `select 2`<br>
  `add tag friend NUS`<br>
  Adds the tags `friend` and `NUS` to the 2nd contact selected

#### Removing tags from a task/person: `delete tag`
Remove tags from last selected task/person.<br>
Format: `delete tag [TAG]...`

Example:
* `list contact`<br>
  `select 3`<br>
  `delete tag foe NTU`<br>
  Removes the tags `foe` and `NTU` from the 3rd contact selected

#### Undo action: `undo`
Undoes the most recent change from the task manager or address book.<br>
Format: `undo`

#### Clearing entries: `clear`
Clears entries from the address book.<br>
Format: `clear [-a] [-t] [-c]`

Modifiers | Action
---|---
-a | Clears all from task manager and address book
-t | Clears all from task manager
-c | Clears all from address book  

#### Exiting the program: `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data
Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Address Book folder.

## Command Summary

Command | Format  
-------- | :--------
Add | `add NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]...`
Clear | `clear`
Delete | `delete INDEX`
Find | `find KEYWORD [MORE_KEYWORDS]`
List | `list`
Help | `help`
Select | `select INDEX`
