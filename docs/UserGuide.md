# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `Task!t.jar` from the 'releases' tab.
2. Copy the file to the folder you want to use as the home folder for your Task!t.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/UImockup.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`**` today` : lists all tasks for today
   * **`add`**` Buy milk `**`by`**` 30 Oct` : 
     adds a task named `Buy milk` to Task!t.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

### Viewing help : `help`
Format: **`help`**
> Help is also shown if you enter an incorrect command e.g. `abcd`
 
### Adding a task : `add`
Add a task to Task!t.<br>
Format: **`add`**` [task_name] [time parameter] [optional parameter 1] [optional parameter 2]...`<br>
 
> ##### Time parameter
> One of the following parameters must be appended to the add command. <br>

> ###### Specifying start/end time: `from ... to ...`
> This parameter is used to indicate the starting and ending datetime of a task.<br>
> Format: **`from`**` [datetime] `**`to`**` [datetime]`<br>

> > The date for **`from`** can be omitted if it is on the same ending date. <br>
> > A few variations in [datetime] format are accepted: <br>
> > - Both 24-Hour and AM/PM formats are accepted: <br>
> > E.g. 19:30 12 oct 2016 <br>
> > E.g. 7:30 PM 12 oct 2016 <br>
> > E.g. 7:30 pm 12 oct 2016 <br>

> > - Minutes can be omitted and assumed to be 00: <br> 
> > E.g. 7 pm 12 oct 2016 <br> 

> > - Month can be in abbreviations, or number separated by slash from day and year: <br>
> > E.g. 19:30 12 oct 2016 <br>
> > E.g. 19:30 12/10/2016 <br>

> > - Year can be omitted and assumed to be the current year: <br> 
> > E.g. 19:30 12/10 <br> 
> > E.g. 7:30 pm 12/10 <br> 


> Examples:
> * **`add`**` dinner with mom `**`from`**` 19:30 02 oct 2016 `**`to`**` 20:30 02 oct 2016`
> * **`add`**` dinner with mom `**`from`**` 19:30 `**`to`**` 20:30 02 oct 2016`

> ###### Specifying deadline : `by`
> This parameter is used to indicate the deadline of a task.<br>
> Format: **`by`**` [datetime]`<br>

> Examples:
> * **`add`**` submit proposal `**`by`**` 23:59 02 oct 2016`

> ##### Optional parameters
> The following parameters can be appended to the add command. <br>
> A task can only have one of each type of parameter.<br>
> A task can only have either start/end time or deadline parameter.<br>

> ###### Specifying location : `at`
> This parameter is used to indicate the venue of a task.<br>
> Format: **`at`**` [location]`<br>

> Examples:
> * **`add`**` dinner with mom `**`at`**` home`

> ###### Specifying remarks : `remarks`
> This parameter is used to add remarks for the task.<br>
> Format: **`remarks`**` [remarks]`<br>

> Examples:
> * **`add`**` dinner with mom `**`remarks`**` buy flowers`

Examples:
* **`add`**` Prepare meeting agenda `**`by`**` 11:00 7 oct 2016`
* **`add`**` Jimmy's wedding banquet `**`from`**` 19:00 `**`to`**` 22:00 11 nov 2016` `**`at`**` Trinity Church

### Listing all tasks : `list`
Shows the list of all tasks sorted temporally with the most recent ones displayed first.<br>
Format: **`list`**` [filter]`<br>
> [filters] available: <br>
> * today - shows the list of tasks for today's date
> * week - shows the list of tasks for this week
> * month - shows the list of tasks for the current month
> * date (e.g. 12 Oct 2016) - shows the list of tasks for the specified date

Examples:
* **`list`**` today`
* **`list`**` 12 Oct 2016`

### Finding all tasks containing keyword in the name: `find`
Finds tasks whose names contain any of the given keywords.<br>
Format: **`find`**` [findtype] [keywords]`

> The search is case insensitive. <br>
> the order of the keywords does not matter. <br>
> Only the task name is searched. <br>
> Tasks matching at least one keyword will be returned. <br>
> More relevant tasks (i.e. those matching more keywords) are displayed first.

Examples: 
* `find mom`<br>
  Returns both `dinner with mom` and `dinner with Mom`
* `find mom dad sister`<br>
  Returns Any task having `mom`, `dad`, or `sister`in its name

> ##### Optional specifiers:

> ###### Find tasks that contain all keywords: `all`
> This specifier demands that only tasks containing all, not just some, keywords will be returned. <br>
> Format: **`find all`**` [keywords]`<br>

> Examples:
> * `find all mom dad sister` <br>
> Returns `dinner with mom, dad and sister` but not `dinner with mom and dad`

> ###### Find by an exact phrase: `exactly`
> This specifier demands that only tasks containing all the keywords in the exact order will be returned. <br>
> Format: **`find exactly`**` [keywords]`<br>

> Examples:
> * `find exactly mom and dad` <br>
> Returns `dinner with mom and dad` but not `dinner with mom`

### Deleting a task : `delete`
Delete the specified tasks from the to-do list.<br>
Format: **`delete`**` [index 1,index 2,...]`

> This command is capable to deleting single and multiple tasks. For multiple tasks, the indexes are separated by a comma. <br>
> Deletes the task at the specified index. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* **`list`**` today`<br>
  **`delete`**` 2`<br>
  Deletes the 2nd task in the results of the **`list`** command.
* **`find`**` mom`<br> 
  **`delete`**` 1,4`<br>
  Deletes the 1st and 4th tasks in the results of the **`find`** command.

### Marking task as done : `done`
Mark the tasks identified by the index numbers used in the last task listing.<br>
Format: **`done`**` [index 1,index 2,...]`

> This command is capable of marking single and multiple tasks. For multiple tasks, the indexes are separated by a comma. <br>
> Marks the tasks at the specified index. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...
  
 Examples: 
* **`list`**` today`<br>
  **`done`**` 2`<br>
  Marks the 2nd task in the results of the **`list`** command.
* **`find`**` mom`<br> 
  **`done`**` 1,2`<br>
  Marks the 1st and 2nd tasks in the results of the **`find`** command.

### Editing a task : `edit`
Edits the task identified by the index number used in the last task listing.<br>
Format: **`edit`**` [index] [optional parameter 1] [optional parameter 2]...`

> Edits the tasks specified based on the details given.<br>
> [optional parameter] follows the format in **`add`** command. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ... <br>
> Leaving the detail blank after entering a parameter identifier deletes the parameter. <br>

Examples: 
* **`list`**<br>
  **`edit`**` 2 dinner with dad`<br>
  Edits the name of the the 2nd task in the results of the **`list`** command.
* **`find`**` dinner` <br> 
  **`edit`**` 1 `**`from`**` 1830 `**`to`**` 2000 25 oct 2016 `**`at`**` popeyes`<br>
  Edits the time and location parameter of the 1st task in the results of the **`find`** command.
* **`find`**` dinner` <br> 
  **`edit`**` 1 `**`at`**<br>
  Deletes the location parameter of the 1st task in the results of the **`find`** command.


### Undoing last action performed : `undo`
Undo the last action performed in Task!t. Irreversible.<br>
Memory supports up to five historical actions. <br>
Format: **`undo`**

### Setting the storage location : `setstorage`
Sets the location of the storage file. <br>
Format: **`setstorage`**` [filepath]`

> Data file in the previously used storage path will be moved over to the new path.

Examples: 
* **`setstorage`**` C://user/documents/todolist`.<br>

### Clearing all entries : `clear`
Clears all entries from Task!t.<br>
Format: `clear`  

### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

### Saving the data 
Task!t data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Task!t.
       
## Command Summary

Command | Format  
-------- | :-------- 
Add | **`add`**` [task_name] [time parameter] [optional parameter 1] [optional parameter 2]...`
Clear | **`clear`**
Delete | **`delete`**` [index 1,index 2,...]`
Done | **`done`**` [index 1,index 2,...]`
Edit | **`edit`**` [index] [optional parameter 1] [optional parameter 2]...`
Exit | **`exit`**
Find | **`find`**` [keywords]`
List | **`list`**` [filter]`
Help | **`help`**
Set storage | **`setstorage`**` [filepath]`
Undo | **`undo`**
