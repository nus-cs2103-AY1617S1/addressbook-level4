# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `addressbook.jar` from the 'releases' tab.
2. Copy the file to the folder you want to use as the home folder for your Address Book.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : lists all contacts
   * **`add`**` John Doe p/98765432 e/johnd@gmail.com a/John street, block 123, #01-01` : 
     adds a contact named `John Doe` to the Address Book.
   * **`delete`**` 3` : deletes the 3rd contact shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

### Viewing help : `help`
Format: **`help`**
> Help is also shown if you enter an incorrect command e.g. `abcd`
 
### Adding an event : `add`
Adds an event to the to do list.<br>
Format: **`add`**` [event_name]`<br>
> An event can have any number of optional parameters (including 0)
 
##### Optional parameters
The following parameters can be appended to the add command. <br>

###### Specifying time : `from ... to ...`
Format: **`from`**` [datetime] `**`to`**` [datetime]`<br>
This parameter is used to indicate the starting and ending datetime of an event.<br>
> The date for from can be omitted if it is the same as the ending date.

Examples:
* **`add`**` dinner with mom `**`from`**` 1900 02/10/16 `**`to`**` 2030 02/10/16`
* **`add`**` dinner with mom `**`from`**` 1900 `**`to`**` 2030 2/10/16`

###### Specifying time : `by`
Format: **`by`**` [datetime]`<br>
This parameter is used to indicate the deadline of an event.

Examples:
* **`add`**` submit proposal `**`by`**` 2359 2/10/16`

> Only one kind of time parameter (either `from...to...` or `by`) may be used in a command.<br>
> [datetime] can be in the following formats: 
> * hhmm dd/mm/yy (e.g. 0730 12/10/16)
> * hhmm dd mmm yyyy (e.g. 1730 12 oct 2016)
> * hhmm dd mmm (e.g. 1730 12 oct)

###### Specifying location : `at`
Format: **`at`**` [location]`<br>
This parameter is used to indicate the venue of an event.

Examples:
* **`add`**` dinner with mom `**`at`**` home`

###### Specifying remarks : `remarks`
Format: **`remarks`**` [remarks]`<br>
This parameter is used to add remarks for the event.

Examples:
* **`add`**` dinner with mom `**`remarks`**` buy flowers`






#### Listing all persons : `list`
Shows a list of all persons in the address book.<br>
Format: `list`

#### Finding all persons containing any keyword in their name: `find`
Finds persons whose names contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> The search is case sensitive, the order of the keywords does not matter, only the name is searched, 
and persons matching at least one keyword will be returned (i.e. `OR` search).

Examples: 
* `find John`<br>
  Returns `John Doe` but not `john`
* `find Betsy Tim John`<br>
  Returns Any person having names `Betsy`, `Tim`, or `John`

#### Deleting a person : `delete`
Deletes the specified person from the address book. Irreversible.<br>
Format: `delete INDEX`

> Deletes the person at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd person in the address book.
* `find Betsy`<br> 
  `delete 1`<br>
  Deletes the 1st person in the results of the `find` command.

#### Select a person : `select`
Selects the person identified by the index number used in the last person listing.<br>
Format: `select INDEX`

> Selects the person and loads the Google search page the person at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `select 2`<br>
  Selects the 2nd person in the address book.
* `find Betsy` <br> 
  `select 1`<br>
  Selects the 1st person in the results of the `find` command.

#### Clearing all entries : `clear`
Clears all entries from the address book.<br>
Format: `clear`  

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data 
Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your preious Address Book.
       
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
