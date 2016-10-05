# User Guide
## 1. Table of Contents
[TOC]
## 2. About

Remindaroo is a customized todo-list application that allows a user to records tasks and events, while reminding them of deadlines and important dates. Remindaroo aims to help users keep track to their stipulated schedules.

## 3. Getting Started
### 3.1 Launch

###### 1. Find the project in the `Project Explorer` or `Package Explorer` (usually located at the left side)
###### 2. Right click on the project
###### 3. Click `Run As > Java Application` and choose the `Main` class.
###### 4. The GUI should appear in a few seconds
###### 5. Type help to see the list of commands available in Remindaroo. Refer to Section 3.2 for further details.

### 3.2 Commands
#### 3.2.1 Add Activity

Adds an activity into the todo list

##### _Non-recurrent Activity_
Adds an activity that occurs only once

##### Formats:<br><br>
-  `add [ACTIVITY]`<br><br>
	> Example: `add Football Tournament`
-  `add [ACTIVITY] [DATE]`<br><br>
	> Example: `add Football Tournament 23 Oct`
-  `add [ACTIVITY] [DATE] [TIME]`<br><br>
	> Example: `add Football Tournament 23 Oct 0900`
- `add [ACTIVITY] [DATE] [START TIME] [END TIME]`<br><br>
	> Example: `add Football Tournament 23 Oct 0800 2200`
- `add [ACTIVITY] [START DATE] [END DATE] [START TIME] [END TIME]`<br><br>
	> Example: `add Football Tournament 21 Oct 23 Oct 0800 2200`

##### _Recurrent Activity_
##### **Adds an activity that repeats on a weekly basis**
##### Formats:
- ###### repeat add [ACTIVITY] [NUMBER OF WEEKS]
	- ###### Example: repeat add Football Practice 10
- ###### repeat add [ACTIVITY] [DATE] [START TIME] [END TIME] [NUMBER OF WEEKS]
	- ###### Example: repeat add Football Practice 23 Oct 0800 1500 10

#### 3.2.2 View Activity

##### **Displays the details of an activity / activities**
##### _View Single Activity_
##### **Displays the details the activity, i.e. date(s), starting time, ending time**
##### Formats:
- ###### view [ACTIVITY]
	- ###### Example: view Football Tournament
##### _View Activities on given dates_
##### **Displays details of activities on a given date or period**
##### Formats:
- ###### view [DATE]
	- ###### Example: view 23 Oct
- ###### view [START DATE] [END DATE]
	- ###### Example: view 23 Oct 30 Oct
- ###### view TODAY
	- Displays all activities for current day
- ###### view TOMORROW
	- Displays all activities for next day
- ###### view WEEK
	- Displays all activities for current week
- ###### view MONTH
	- Displays all activites for current month

#### 3.2.3 Delete Activity

##### **Deletes specific activity from todo list**
##### Formats:
- ###### delete [ACTIVITY]
	- ###### Example: delete Football Practice

#### 3.2.4 Update Activity

##### **Updates specific activity**
##### _Update date and time of activity_
##### **Updates date and time of specific activity to specified date and time**
##### Formats:
- ###### update [ACTIVITY] [START DATE] [END DATE] [START TIME] [END TIME]
	- ###### Example: update Football Tournament 20 Oct 23 Oct 0800 2200
##### _Update name of activity_
##### **Updates name of specific activity to specified name**
##### Formats:
- ###### update [EXISTING ACTIVITY NAME] < [NEW ACTIVITY NAME]
	- ###### Example: update Football Tournament < World Cup Qualifiers

#### 3.2.5 Mark Activity

##### **Marks activity with status**
##### Formats:
- ###### mark [ACTIVITY] < [STATUS]
	- 	###### Example: mark User Guide < in progress
:::info
##### There is no specified list of statuses that users must choose from.
:::

#### 3.2.6 Next Activity

##### **Displays activity that is scheduled next (closest to current time)**
##### Formats:
- ###### next

#### 3.2.7 Search Activity

##### **Displays list of activities that match description (Keyword / date / category)**
##### Formats:
- ###### search [KEYWORDS]
	- ###### Example: search CS2101
- ###### search [DATE]
	- ###### Example: search 23 Oct
- ###### search [CATEGORY]
	- ###### Example: search events

#### 3.2.8 Undo

##### **Undo last command entered**
##### Formats:
- ###### undo
	- ###### Example:
	>  `add CS2101 Tutorial`
	>  `undo`
	>  ###### CS2101 tutorial is removed from the todo list

#### 3.2.9 Redo

##### **Redo last undo command**
##### Formats:
- ###### redo
	- ###### Example: 	 
	>  `add CS2101 Tutorial`
	>  `undo`
	>  `redo`
	>  ###### CS2101 tutorial is re-added into the todo list

#### 3.2.10 Change Data Storage Location

##### **Specify the path of a new folder for storing the data file of the to-do list. The user should have permissions to access the folder**
##### Formats:
- ###### store [DATA FILE LOCATION]
	- ###### Example: store User/Jim/Documents

#### 3.2.11 Help

##### **Displays the instruction for using each command**
##### Formats:
- ###### help [COMMAND]
	- ###### Example: help add
:::info
##### Help is displayed if invalid command is entered
:::

#### 3.2.12 Saving the data

###### Address book data are saved in the hard disk automatically after any command that changes the data.
###### There is no need to save manually.

## 4. Summary of Commands

| Commands        | Format        |
| ----------------|:-------------:|
| Add Single Activity| `add [ACTIVITY] [START DATE] [END DATE] [START TIME] [END TIME]`|
| Add Recurring Activity| `repeat add [ACTIVITY] [DATE] [START TIME] [END TIME] [NUMBER OF WEEKS]`
| View Single Activity| `view [ACTIVITY]`|
| View Activities in a given period| `view [ACTIVITY] [START DATE] [END DATE]` |
| Delete Activity | `delete [ACTIVITY]`|
| Update Date and Time of Activity | `update [ACTIVITY] [START DATE] [END DATE] [START TIME] [END TIME]`
| Update Name of Activity| `update [EXISTING ACTIVITY NAME] < [NEW ACTIVITY NAME]`|
| Mark Activity   | `mark [ACTIVITY] < [STATUS]` |
| Next Activity   | `next` |
| Search Activity | `search [KEYWORD / DATE / CATEGORY]`
| Undo            | `undo` |
| Redo            | `redo` |
| Change data storage location | `store [DATA FILE LOCATION]` |
| Help            | `help [COMMAND]` |



## 5. Troubleshooting

**Q:** How do I transfer my data to another Computer?
**A:** Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Address Book folder.
