# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `KeyboardWarrior.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your KeyboardWarrior.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Keyboard Warrior UI.jpg" width="600" height="350">
4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`add`**` Do CS2103 Tutorial` : 
     adds a todo task `Do CS2103 Tutorial` KeyboardWarrior.
   * **`remove`**` 3` : deletes the 3rd task shown
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
 
#### Adding a todo: `add`
Adds a todo task to the KeyboardWarrior<br>
Format: `TASK`

Examples: 
* `add Buy Chocolate`
* `add Download Github`
* `add Download Eclipse`

#### Adding a Deadline: `add`
Adds a deadline to the KeyboardWarrior<br>
Format: `TASK by [DATE] [TIME]`

Examples: 
* `add Do CS2103 Tutorial by Thursday`
* `add Submit Lab report by 020314 2030`

#### Adding a calendar task: `add`
Adds a specific task to the KeyboardWarrior that will be able to show any combinations of the following parameters:<br>

Format: `add DATE TIME [to TIME] TASK [@VENUE]`

Examples: 
* `add 010116 1810 Go to the mall`
* `add Sunday 0210 to 0300 Group Meeting @I3 MR9`
* `add Fri 1410 to 1600 Basketball Tryouts @13 Computing Dr 117417`

#### Find a task: `find`
Find all task in the KeyboardWarrior that contains a phrase, time, date or venue<br>
Format: `find [KEYWORD] [@VENUE]`

Examples: 
* `find baskebtall`
* `find @I3`

#### Show Calendar : `show`
Shows a calendar in the KeyboardWarrior.<br>
Format: `show [TIMEFRAME]`

Examples: 
* `show week`
* `show month`
* `show Saturday`
* `show 0405`
* `show Feb`

#### Complete a Todo or Deadline : `complete`
Mark a todo as complete and delete it from the KeyboardWarrior. Irreversible.<br>
Format: `complete INDEX`

> Complete the calendar task at the specified `INDEX`
  The index refers to the index number shown on the calendar<br>
  The index **must be a positive integer or a letter followed by a number** 1, D2, T3, ...

Example: 
* `complete T1`<br>
   Mark todo 1 as complete and delete it from the keep in viewfinder.
* `complete D2`<br>
   Mark deadline 2 as complete and delete it from the keep in viewfinder.
* `complete 3`<br>
   Mark task 3 as complete and delete it from the todo.

#### Remove a task from the calendar : `remove`
Remove a specified calendar task from the KeyboardWarrior. Irreversible.<br>
Format: `remove INDEX`

> Complete the calendar task at the specified `INDEX`
  The index refers to the index number shown on the calendar<br>
  The index **must be a positive integer or a letter followed by a number** 1, D2, T3, ...

Example: 
* `remove T1`<br>
   remove todo 1 from the keep in viewfinder.
* `remove D2`<br>
   remove deadline 2 from the keep in viewfinder.
* `remove 3`<br>
   remove task 3 it from the viewfinder.

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data 
Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous KeyboardWarrior folder.
       
## Command Summary

Command | Format  
-------- | :-------- 
Help | `help`
(Todo)| `TASK`
(Deadline)| `TASK by [DATE] [TIME]`
Add | `add DATE TIME to [TIME] TASK @ [VENUE]`
Complete | `complete INDEX`
Remove | `remove INDEX`
Find | `find [DATE] [TIME] [KEYWORD] @[VENUE]`
Show | `show`
Exit | `exit`
