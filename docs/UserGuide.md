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
 
> ##### Optional parameters
> The following parameters can be appended to the add command. <br>
> An event can only have one of each parameter.<br>
> An event can only have either start/end time or deadline parameter.<br>

> ###### Specifying start/end time : `from ... to ...`
> This parameter is used to indicate the starting and ending datetime of an event.<br>
> Format: **`from`**` [datetime] `**`to`**` [datetime]`<br>

> > The date for **`from`** can be omitted if it is on the same ending date.

> Examples:
> * **`add`**` dinner with mom `**`from`**` 1900 02/10/16 `**`to`**` 2030 02/10/16`
> * **`add`**` dinner with mom `**`from`**` 1900 `**`to`**` 2030 2/10/16`
> * **`add`**` dinner with mom `**`from`**` 1900 `**`to`**` 2030 2 oct 2016`

> ###### Specifying deadline : `by`
> This parameter is used to indicate the deadline of an event.<br>
> Format: **`by`**` [datetime]`<br>

> Examples:
> * **`add`**` submit proposal `**`by`**` 2359 2/10/16`

> > [datetime] can be in the following formats: 
> > * hhmm dd/mm/yy (e.g. 0730 12/10/16)
> > * hhmm dd mmm yyyy (e.g. 1730 12 oct 2016)
> > * hhmm dd mmm (e.g. 1730 12 oct)

> ###### Specifying location : `at`
> This parameter is used to indicate the venue of an event.<br>
> Format: **`at`**` [location]`<br>

> Examples:
> * **`add`**` dinner with mom `**`at`**` home`

> ###### Specifying remarks : `remarks`
> This parameter is used to add remarks for the event.<br>
> Format: **`remarks`**` [remarks]`<br>

> Examples:
> * **`add`**` dinner with mom `**`remarks`**` buy flowers`

### Listing all events : `list`
Shows a list of all events in the to-do list.<br>
Format: **`list`**` [filter]`<br>
> [filters] available: <br>
> * today - shows the list of events for today's date
> * week - shows the list of events for this week
> * month - shows the list of events for the current month
> * date (e.g. 12 Oct 2016) - shows the list of events for the specified date

Examples:
* **`list`**` today`
* **`list`**` 12 Oct 2016`

### Finding all events containing any keyword in the name: `find`
Finds events whose names contain any of the given keywords.<br>
Format: **`find`**` [keywords]`

> The search is case sensitive, the order of the keywords does not matter, only the name is searched, 
and events matching at least one keyword will be returned (i.e. `OR` search).

Examples: 
* `find mom`<br>
  Returns `dinner with mom` but not `dinner with Mom`
* `find mom dad sister`<br>
  Returns Any event having names `mom`, `dad`, or `sister`

### Deleting an event : `delete`
Delete the specified events from the to-do list.<br>
Format: **`delete`**` [index 1,index 2,...]`

> This command is capable to deleting single and multiple events. For multiple events, the indexes are separated by a comma. <br>
> Deletes the person at the specified index. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* **`list`**` today`<br>
  **`delete`**` 2`<br>
  Deletes the 2nd event in the results of the **`list`** command.
* **`find`**` mom`<br> 
  **`delete`**` 1,4`<br>
  Deletes the 1st and 4th event in the results of the **`find`** command.

### Marking event as done : `done`
Mark the events identified by the index numbers used in the last event listing.<br>
Format: **`done`**` [index 1,index 2,...]`

> This command is capable of marking single and multiple events. For multiple events, the indexes are separated by a comma. <br>
> Marks the events at the specified index. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...
  
 Examples: 
* **`list`**` today`<br>
  **`done`**` 2`<br>
  Marks the 2nd event in the results of the **`list`** command.
* **`find`**` mom`<br> 
  **`done`**` 1,2`<br>
  Marks the 1st and 2nd event in the results of the **`find`** command.

### Editing an event : `edit`
Edits the event identified by the index number used in the last event listing.<br>
Format: **`edit`**` [index] [details]`

> Edits the event specified based on the details given.<br>
> [details] follows the format in **`add`** command. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* **`list`**<br>
  **`edit`**` 2 dinner with dad`<br>
  Edits the name of the the 2nd event in the results of the **`list`** command.
* **`find`**` dinner` <br> 
  **`edit`**` 1 `**`from`**` 1830 `**`to`**` 2000 25 oct 2016 `**`at`**` popeyes`<br>
  Edits the time and location parameter of the 1st event in the results of the **`find`** command.

### Undoing last action performed : `undo`
Undo the last action performed in the to-do list. Irreversible.<br>
Format: **`undo`**

### Setting the storage location : `setstorage`
Sets the location of the storage file. <br>
Format: **`setstorage`**` [filepath]`

> The [filepath] provided can be both absolute or relative. Data file in current storage will be moved.

Examples: 
* **`setstorage`**` ../documents/todolist`.<br>
* **`setstorage`**` C://user/documents/todolist`.<br>

### Clearing all entries : `clear`
Clears all entries from the to-do list.<br>
Format: `clear`  

### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

### Saving the data 
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
