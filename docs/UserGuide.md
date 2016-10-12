# User Guide
## 1. Table of Contents

1. [Table of Contents](#1-table-of-contents)
2. [About](#2-about)
3. [Getting Started](#3-getting-started)
    1. [Launch](#31-launch)
    2. [Commands](#32-commands)
4. [Summary of Commands](#4-summary-of-commands)
5. [Troubleshooting](#5-troubleshooting)

## 2. About

Remindaroo is a customized to-do list application that can help you manage your busy schedule and organize your to-dos. Whether its working on a project, buying groceries or planning a holiday, Remindaroo aims to help you get stuff done. So let's get started!

## 3. Getting Started
### 3.1 Launch

1. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.

2. Download the latest `remindaroo.jar` from the [releases](../../../releases) tab.
3. Copy the file to the folder you want to use as the home folder for Remindaroo.
4. Double-click the file to start the app. The GUI should appear in a few seconds.

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. type **`help`** and press <kbd>Enter</kbd> to open the help window.
5. Some examples of commands you can try:
   * **`add`**`buy bread` : adds a task named `buy bread` to Remindaroo
   * **`exit`** : exits the app
6. Refer to the [Commands](#32-commands) section below for details of each command.<br>

### <br> 3.2 Commands

> Format notations:
>
> * Words in `UPPER_CASE` are the parameters
> * Items in `[SQUARE_BRACKETS]` are optional
> * Keywords with `|` in between indicates that either one can be used
> * Order of parameters are fixed

<br>

> Special note on Date and Time parameters:
>
> `DATE_TIME`, `START_DATE_TIME` and `END_DATE_TIME` are parameters that can accept various inputs indicating a specific date or time. Some examples are as follows
> * Date, e.g. `29 Oct`
> * Date and time, e.g. `10 Nov 0900`
> * Relative day, e.g. `tomorrow`
> * Specific weekday, e.g. `wednesday`
>
> Note: If the timing is not specified, it will be set as 2359.

#### <br>3.2.1 Add New Activity: **`add`**
Adds an activity into Remindaroo. 3 types of activities are available:
* _floating task_ : one without specific time
* _deadline_ : one with just a specific time
* _event_ : one with a start time and end time

##### Formats:<br>
-  `add FLOATING_TASK`<br><br>
	> Example:
	> `add buy milk`
-  `add DEADLINE on|by DATE_TIME`<br><br>
	> Example:
	> `add Submit Assignment 1 on 23 Oct`
	> `add Submit Assignment 1 by 23 Oct 0900`

- `add EVENT from START_DATE_TIME to END_DATE_TIME`<br><br>
	> Example:
	> `add Football Tournament from 21 Oct 0800 to 23 Oct 2200`
	> `add Football Tournament from 21 Oct to 23 Oct`

#### <br>3.2.2 View Activities : **`view`**
Displays the details of the activity / activities<br><br>

##### _View an Activity_
Displays the details of the activity, i.e. date(s), starting time, ending time

##### Format:<br><br>
- `view ACTIVITY_ID`<br><br>
	> Example:
	> `view 1`
	> Activity with ID 1 (e.g. `Football Practice`) will be displayed

##### <br>_View Activities on given dates_
Displays details of activities on a given date or  over a given period

##### Formats:
- `view DATE_TIME [END_DATE_TIME]`<br><br>
	> Example:
	> `view 23 Oct`
	> `view 23 Oct 30 Oct`
- `view today`<br><br>
	> Displays all activities for current day
- `view tomorrow`<br><br>
	> Displays all activities for next day
- `view week`<br><br>
	> Displays all activities for current week
- `view month`<br><br>
	> Displays all activites for current month

#### <br>3.2.3 Delete Activity **`delete`**
Deletes a specific activity from Remindaroo

##### Format:<br><br>
- `delete ACTIVITY_ID`<br><br>
	> Example:
	> `delete 1`
	> Activity with ID 1 (e.g. `Football Practice`) is deleted from the Remindaroo

#### <br>3.2.4 Update Activity **`update`**
Updates name, date and/or time of specific activity to specified name, date and/or time.
**Note**: Activity type (_floating task_, _deadline_ or _event_) cannot be changed

##### Format:<br><br>
-  `update ACTIVITY_ID to [NEW_NAME] [DATE_TIME] [END_DATE_TIME]`<br><br>

	> Examples:
	> `update 1 to buy bread`
	> Activity with ID 1 is updated to `buy bread`
	>
	> `update 2 to 10 Oct 1000`
	> Activity with ID 2 is updated to be on / due by 10 October, 1000
	>
	> `update 3 to 11 Oct 1300 11 Oct 1400`
	> Activity with ID 3 is updated to be on 11 October, 1300 to 1400


#### <br>3.2.5 Mark Activity : **`mark`**
Marks an activity with status. If the activity already has a status, it will be overwritten by the status in this command.

Status has to be one of the following (case-insensitive):
* **pending**
* **completed**

##### Format:<br><br>
- `mark ACTIVITY_ID as STATUS`<br><br>
	> Example:
	> `mark 1 as completed`
	> Activity with ID 1 (e.g. `do assignment 1`) is marked as completed

#### <br>3.2.6 Find Next Activity : **`next`**
Displays the activity that is scheduled next (closest to current time)

##### Format:<br><br>
- `next`

#### <br>3.2.7 Search for Activities : **`search`**
Displays list of activities that match description (keyword / date / activity category / status)

##### Formats:<br><br>
- `search KEYWORDS`<br><br>
	> Example:
	> `search CS2101 tutorial`
- `search DATE`<br><br>
	> Example:
	> `search 23 Oct`
- `search ACTIVITY_CATEGORY`<br><br>
	> Example:
	> `search events`
- `search STATUS`<br><br>
    > Example:
    > `search completed`

#### <br>3.2.8 Undo last command : **`undo`**
Undo last command entered

##### Format:<br><br>
- `undo`<br><br>
	>  Example:
	>  `add CS2101 Tutorial`
	>  `undo`
	>  CS2101 tutorial is removed from Remindaroo

#### <br>3.2.9 Redo last command: **`redo`**

Redo last undo command
##### Format:<br><br>
- `redo`<br><br>
	>  Example:
	>  `add CS2101 Tutorial`
	>  `undo`
	>  `redo`
	>  CS2101 tutorial is re-added into Remindaroo

#### <br>3.2.10 Change Data Storage Location : **`store`**

Specify the path of a new folder for storing the data file of Remindaroo. The user should have permissions to access the folder

##### Format:<br><br>
- `store DATA_FILE_LOCATION`<br><br>
	> Example:
	> `store /User/Jim/Documents`
	> The Data file now resides in the folder `/User/Jim/Documents`

#### <br>3.2.11 Show Help Menu : **`help`**
Displays the instruction for using each command

##### Format:<br><br>
- `help [COMMAND]`<br><br>
	> Example:
	>
	> `help | help SOME_INVALID_COMMAND`
	> Displays instruction for all commands<br>
	> `help add`
	> Displays instruction for add command

#### <br>3.2.12 Exit Remindaroo : **`exit`**
Exits the program

##### Format:<br><br>
- `exit`

#### <br>3.2.13 Saving the data
ActivityManager data is saved in the hard disk automatically after any command changes the data.
There is no need to save manually.
<br>
## 4. Summary of Commands

| Commands        | Format        |
| ----------------|:-------------:|
| Add Floating Task | `add FLOATING_TASK` |
| Add Deadline | `add DEADLINE on|by DATE_TIME` |
| Add Event| `add EVENT from START_DATE_TIME to END_DATE_TIME`
| View an Activity| `view ACTIVITY_ID`|
| View Activities in a given period| `view DATE_TIME [END_DATE_TIME]` |
| Delete Activity | `delete ACTIVITY_ID`|
| Update Activity | `update ACTIVITY_ID to [NEW_NAME] [DATE_TIME] [END_DATE_TIME]` |
| Mark Activity   | `mark ACTIVITY_ID as STATUS` |
| Next Activity   | `next` |
| Search Activity | `search KEYWORD|DATE|CATEGORY`
| Undo            | `undo` |
| Redo            | `redo` |
| Change data storage location | `store DATA FILE LOCATION` |
| Help            | `help [COMMAND]` |
| Exit            | `exit` |



## 5. Troubleshooting

**Q:** How do I transfer my data to another Computer?
**A:** Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Remindaroo folder.
