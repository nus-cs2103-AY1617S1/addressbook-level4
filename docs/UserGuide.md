# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## About Us

> **Bernard Koh**
> * Team Leader
> * Testing

> **Liu Zhen Dong**
> * Code Quality
> * Time Keeper

> **Leah Lim**
> * Documentation
> * GUI Design

> **Li Wang Huan**
> * Integration
> * Eclipse Specialist


## Table of Content
1. About GGist
2. User Guide

> * Getting Started
> * Feature Details
> * Troubleshoot
> * Command Cheatsheet

3. User Stories
4. Use Cases
5. Non-functional Requirements
6. Glossary
7. Product Survey

## About GGist

Are you constantly overwhelmed by the number of things you have to do everyday or do you have a really hard time managing them? If you answer is yes, GGist is the perfect solution to your daily struggle. GGist is a one-stop user friendly desktop organiser designed to aid working professionals like you to better organize and prioritise your everyday tasks.

Unlike most of the organisers in the market, GGist can be launched with a keyboard shortcut and accepts flexible natural language commands via keyboard. This makes it convenient for working professionals like you who can type fast, spend most of the time near a computer and prefer typing commands. 

Are you ready to embrace a new way of living and have your life better organized? Let’s begin!


## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `GGist.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your GGist application.
3. Double-click on the .jar file. Press both "control" and "G" at the same time to start the app. The GUI should appear in a few seconds. 
   > <img src="images/GGistUiProto.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`**` 31 oct` : 
     lists all the tasks on 31 oct in GGist.
   * **`add`**` water the plants, jul 10, 1400` : 
     adds a task `water the plants` with deadline 2pm on the 10th of July to GGist.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
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
 
#### Adding a task: `add`
Adds a task to GGist<br>
General format: `add TASK, [DATE], [TIME], [PRIORITY], [FREQUENCY]`

LEAH ADD IMAGE HERE!!!!!!!!!!!!

###### With deadline:
Format: `add TASK, DATE, TIME, [PRIORITY], [FREQUENCY]`
Examples: 
* `add write diary, jul 10, 1300`
* `add prepare presentation slides, mon, 1400, high`

###### Without any deadline
Format: `add TASK, [DATE], [TIME], [PRIORITY], [FREQUENCY]`
Examples: 
* `add buy milk, low`
* `add buy present for brother's birthday`

###### With start and end time
Format: `add TASK, DATE, TIME, [PRIORITY], [FREQUENCY]`
For this kind of task, the parameter TIME is in the format START-END.
Examples: 
* `add dad's birthday celebration, jul 10, 1900-2100, high`
* `add company's D&D, sun, 1900-2200`

###### Recurring
To make tasks repeating, simply  add the FREQUENCY parameter at the back.
Format: `add TASK, [DATE], [TIME], [PRIORITY], [FREQUENCY]`
Examples: 
* `add water the plants, 0800, high, daily`
* `add facial appointment, jul 10, med, monthly`

#### Listing all persons : `list`
Shows a list of all persons in the address book.<br>
Format: `list`

#### Finding all persons containing any keyword in their name: `find`
Finds persons whose names contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case sensitive. e.g `hans` will not match `Hans`
> * The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
> * Only the name is searched.
> * Only full words will be matched e.g. `Han` will not match `Hans`
> * Persons matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Hans` will match `Hans Bo`

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
