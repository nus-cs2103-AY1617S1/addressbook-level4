# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `lifekeeper.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Lifekeeper.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/UIprototype.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : lists all activities
   * **`add`**` CS2103 T7A1 d/6 Oct 2016 p/2 r/5 Oct 2016 1800 t/teamC2` : 
     adds an activity named `CS2103 T7A1` to the Lifekeeper.
   * **`delete`**` 3` : deletes the 3rd activity shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `[SQUARE_BRACKETS]` are optional.
> * Items in `{CURLY_BRACES}` cannot be input together with other items within a separate `{CURLY_BRACES}`
> * Items with `...` after them can have multiple instances.
> * The order of parameters is not fixed.

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`
 
#### Adding an activity: `add`

Adds an activity to Lifekeeper<br>
Format: `add ACTIVITY_NAME {[d/DUEDATE] [p/PRIORITY_LEVEL]}{[s/START_TIME] [e/END_TIME]} [r/REMINDER] [t/TAG]...` 

> Activities can have only either {[d/DUEDATE] [p/PRIORITY_LEVEL]} or {[s/START_TIME] [e/END_TIME]} 
> Activities can have any number of tags (including 0)

`DUEDATE` accepts `Date Time` format input and variable inputs namely:
* `tomorrow [TIME]`
* `today [TIME]`

`PRIORITY_LEVEL` has to be an integer equal or larger than 1, with `1` being the top priority.

`START_TIME` accepts `Date Time` format input and variable inputs namely:
* `tomorrow [TIME]`
* `today [TIME]`

`END_TIME` accepts `Date Time` format input and variable inputs namely:
* `tomorrow [TIME]`
* `today [TIME]`


`REMINDER` accepts `Date Time` format input and variable inputs namely:
* `tomorrow [TIME]`
* `today [TIME]`
* `[TIME] before` sets reminder at the specified time before the `DUEDATE`. e.g. `0015 before` for a reminder 15 minutes before the `DUEDATE`.

Examples: 
* `add Grocery Shopping`
* `add Assignment 1 d/Tomorrow p/1 r/Today 2000`
* `add Project Report d/Tomorrow 1700 t/school`
* `add CS2103 T7A1 d/6 Oct 2016 p/2 r/5 Oct 2016 1800 t/teamC2`
* `add Lunch s/1200 e/1300`
* `add Executive Meeting s/tomorrow 0900 e/tomorrow 1200`
* `add Concert s/tomorrow 1800 e/tomorrow 2000 t/Leisure`

#### Listing activities : `list`
Shows a list of activites in Lifekeeper, if any.<br>
Format: `list`

> All the activites in Lifekeeper will be listed

#### Listing tags: `listtag`
Shows a list of all tags in LifeKeeper.<br>
Format: `listtag`

> All the tags in Lifekeeper will be listed


#### Finding activities by name or tag(s): `find`
Finding all activities containing the queried keyword in their name
* Finds activities whose names contain any of the given keywords.<br>
* Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is not case sensitive. e.g `study` will match `Study`
> * The order of the keywords matters. e.g. `Assignment Due` will not match `Due Assignment`
> * Words containing the keywords will be matched e.g. `Exam` will match `Exams`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Shopping` will match `Clothes Shopping`

Examples: 
* `find activities Homework Assignment`<br>
  Returns Any activities with words containing `Homework`, `homework`, `Assignment`, or `assignment` in their names.

Finding all activities containing a certain tag    
* Finds activities which has tags of given keywords attached to it.<br>    
* Format: `find tags KEYWORD [MORE_KEYWORDS]`   
    
> * The search is not case sensitive.     
> * Only full words will be matched.    
> * Only tags matching the EXACT keyword will be returned.    
    
Examples:   
* `find CS2103`    
  Returns Any activities containing the tag `CS2103` or `cs2103` but not `CS2103T` or `CS2103 Project`.    

#### Deleting an activity: `delete`
Deletes the selected activity from Lifekeeper. Irreversible.<br>
Format: `delete INDEX`

> Deletes the activity with `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  

Examples: 
* `list`<br>
  `delete 1`<br>
  Deletes the 1st activity in the Lifekeeper activity list.
* `find Dinner`<br>
  `delete 2`<br>
  Deletes the 2nd activity listed in the results of the `find` command.

#### Marking an activity as done: `done`
Marks the activity as completed.<br>
Format: `done INDEX`

> Marks the activity with `INDEX` as completed.
  The index refers to the index number shown in the most recent listing.<br>
  

Examples: 
* `list`<br>
  `done 1`<br>
  Marks the 1st activity in the Lifekeeper activity list as completed.
* `find Admin`<br>
  `done 2`<br>
  Selects the 2nd activity in the results of the `find` command and then marks it as completed.

#### Editing an activity: `edit`
Edits the selected activity from Lifekeeper.<br>
Format: `edit INDEX [n/ACTIVITY_NAME] {[d/DUEDATE] [p/PRIORITY_LEVEL]}{[s/START_TIME] [e/END_TIME]} [r/REMINDER] [t/TAG]...` 

> Edits the activity that was previously selected with `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  

Examples: 
* `list`<br>
  `edit 2 n/assignment 5 r/today`<br>
  Edit the selected the 2nd activity in the list by changing its name to `assignment 5` and reminder to `today`.
* `find CS2103 Assignment`<br>
  `edit 1 d/15-06-2017`<br>
  Selects the 1st activity in the results of the `find` command and then change the DUEDATE to `15/06/2017`.

#### Undoing an action : `undo`
Reverts the action that was previously executed.<br>
Format: `undo`

#### Clearing all entries : `clear`
Clears all entries from Lifekeeper.<br>
Format: `clear`  

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data 
Lifekeeper data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Lifekeeper folder.
       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `add ACTIVITY_NAME {[d/DUEDATE] [p/PRIORITY_LEVEL]}  {[s/START_TIME] [e/END_TIME]} [r/REMINDER] [t/TAG]...` 
Edit | `edit INDEX [n/ACTIVITY_NAME] {[d/DUEDATE] [p/PRIORITY_LEVEL]} {[s/START_TIME] [e/END_TIME]} [r/REMINDER] [t/TAG]...` 
Clear | `clear`
Delete | `delete INDEX`
Find | `find KEYWORD [MORE_KEYWORDS]`
List | `list`
List Tags | `listtags`
Done | `done INDEX`
Undo | `undo`
Help | `help`
Exit | `exit`