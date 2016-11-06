# Test Script (for Manual Testing)

* [How to perform Manual Testing with Sample Data?](#how-to-perform-manual-testing-with-sample-data)
* [Tests for Add command](#tests-for-add-command)
* [Tests for Show command](#tests-for-show-command)
* [Tests for Find command](#tests-for-find-command)
* [Tests for Delete command](#tests-for-delete-command)
* [Tests for Update command](#tests-for-update-command)
* [Tests for Done command](#tests-for-done-command)
* [Tests for Setstorage command](#tests-for-setstorage-command)
* [Tests for Undo command](#tests-for-undo-command)
* [Tests for Redo command](#tests-for-redo-command)
* [Tests for Help command](#tests-for-help-command)
* [Tests for Clear command](#tests-for-clear-command)
* [Tests for Autocomplete feature](#tests-for-autocomplete-feature)
* [Tests for Command History feature](#tests-for-command-history-feature)

## How to perform Manual Testing with Sample Data?

1. Copy SampleData.xml file which can be found at `\src\test\data\ManualTesting\SampleData.xml`.
2. Paste the file into the data storage directory (the `data` folder found in the same location as that of the file `Lazyman's Friend.jar`; if the directory does not exist, create it)
3. Delete the existing XML data file and rename SampleData.xml to the name of this existing XML data file.
4. Start `Lazyman's Friend.jar` by double clicking it and to perform Manual Testing, perform the tests below in the given order.

## Tests for Add command

#### Adding a floating task
Command: `add Buy dinner for family` <br>
Expected Output: `New task added: Buy dinner for family`

#### Adding an event
Command: `add Practice for presentation from 25 dec 10am to 25 dec 12pm` <br>
Expected Output: `New task added: Practice for presentation`

#### Adding a deadline
Command: `add Ask for money by 26 dec 10pm` <br>
Expected Output: `New task added: Ask for money`

#### Adding a task with start time only
Command: `add Study for CS9999 at 19 Jan 2017` <br>
Expected Output: `New task added: Study for CS9999`

#### Adding an overlapping task
Command: `add Attend brunch from 25 dec 8am to 25 dec 11am` <br>
Expected Output: `New task added: Attend brunch. There is an overlap with other existing task(s).`

#### Adding a duplicate task
Command: `add Ask for money by 26 dec 10pm` <br>
Expected Output: `This task already exists in the to-do list.`

#### Adding in an invalid manner (Example 1)
Command: `add` <br>
Expected Output: `Task details should include only alphanumeric/whitespace characters and must not be empty.`

#### Adding in an invalid manner (Example 2)
Command: `add123` <br>
Expected Output: `Unknown command`

## Tests for Show command

#### Show all
Command: `show all` <br>
Expected Output: `57 task(s) listed!`

#### Show overdue
Command: `show overdue` or `overdue` <br>
Expected Output: `9 task(s) listed!`

#### Show done/complete
Command: `show done` or `show complete` <br>
Expected Output: `0 task(s) listed!`

#### Show today
Command: `show today` or `today` <br>
Expected Output: `4 task(s) listed!`

#### Show tomorrow
Command: `show tomorrow` or `tomorrow` <br>
Expected Output: `4 task(s) listed!`

#### Show floating
Command: `show floating` or `floating` <br>
Expected Output: `9 task(s) listed!`

#### Show for any random date
Command: `show 25/12/2016` or `show xmas` <br>
Expected Output: `5 task(s) listed!`

#### Show high priority tasks
Command: `show p/high` <br>
Expected Output: `6 task(s) listed!`

#### Show medium priority tasks
Command: `show p/med` <br>
Expected Output: `3 task(s) listed!`

#### Show low priority tasks
Command: `show p/low` <br>
Expected Output: `50 task(s) listed!`

#### Show upcoming/incomplete tasks
Command: `show upcoming` or `show incomplete` or `show` <br>
Expected Output: `57 task(s) listed!`

## Tests for Find command

#### Find non-existing task
Command: `find lunch` <br>
Expected Output: `No such task was found.`

#### Find task with one matching result
Command: `find judo` <br>
Expected Output: `1 task(s) listed!`

#### Find task with more than one matching result
Command: `find study` <br>
Expected Output: `5 task(s) listed!`

#### Find all tasks
Command: `find *` <br>
Expected Output: `59 task(s) listed!`

#### Find task with wildcard character *
Command: `find B*y*` <br>
Expected Output: `9 task(s) listed!`

## Tests for Delete command

#### Delete task by index 
Command: `delete 7` <br>
Expected Output: `Deleted Task: Buy cups`

#### Delete task by name with only one matching result
Command: `delete judo` <br>
Expected Output: `Deleted Task: Attend judo practice`

#### Delete task by name with multiple matching results
Command: `delete study` <br>
Expected Output: `Multiple tasks were found containing the entered keywords. Please check below and delete by index.`

#### Delete task by name with zero matching results
Command: `delete lunch`<br>
Expected Output: `No such task was found.`

## Tests for Update command

#### Update start time for a task
Command: `update 5 at 4 oct 1pm` <br>
Expected Output: `Task successfully updated: Go for checkup`

#### Update end time for a task
Command: `update 1 by 2 jan 2016` <br>
Expected Output: `Task successfully updated: Find my social life back`

#### Update priority for a task
Command: `update 2 p/med` <br>
Expected Output: `Task successfully updated: Clean toilet`

#### Update recurring frequency for a task
Command: `update 8 r/monthly` <br>
Expected Output: `Task successfully updated: Go for dance practice`

#### Update task details for a task
Command: `update 1 Must find my social life back` <br>
Expected Output: `Task successfully updated: Must find my social life back`

#### Update floating task to a task with start time and end time
Command: `update 12 from 8 nov 10pm to 8 nov 11pm` <br>
Expected Output: `Task successfully updated: Clean room`

#### Invalid attempt at update (Example 1)
Command: `update 99` <br>
Expected Output: `The task index provided is invalid`

#### Invalid attempt at update (Example 2)
Command: `update my task` <br>
Expected Output: `The task index provided is invalid`

## Tests for Done command

#### Complete task by index
Command: `done 2` <br>
Expected Output: `Completed Task: Clean toilet. Showing all completed tasks now.`

#### Complete task by name with only one matching result
Command 1: `show` <br>
Command 2: `done nap` <br>
Expected Output: `Completed Task: Nap. Showing all completed tasks now.`

#### Complete task by name with multiple matching results
Command: `done study` <br>
Expected Output: `Multiple tasks were found containing the entered keywords. Please check below and mark as complete by index.`

#### Complete task by name with zero matching results
Command: `done lunch` <br>
Expected Output: `No such task was found.`

## Tests for Setstorage command

#### Set storage file path to random file location
Command: `setstorage /Desktop` <br>
Expected Output: `Changed file path to: /Desktop`

#### Set storage file path to default file location
Command: `setstorage default` <br>
Expected Output: `Changed file path to: default`

#### Set storage file path to invalid file location
Command: `setstorage njdfhewjfhe` <br>
Expected Output: `File path not found. Please enter a valid file path.`

## Tests for Undo command

#### Undo one change
Command 1: `Add sleep` <br>
Command 2: `undo` <br>
Expected Output: `Your previous action has been undone.`

#### Undo two changes
Command 1: `Add sleep` <br>
Command 2: `update 15 p/med` <br>
Command 3: `undo` <br>
Expected Output: `Your previous action has been undone.` <br>
Command 4: `undo` <br>
Expected Output: `Your previous action has been undone.`

#### Undo three changes

Command 1: `Add sleep` <br>
Command 2: `update 15 p/med` <br>
Command 3: `done 8` <br>
Command 4: `undo` <br>
Expected Output: `Your previous action has been undone.` <br>
Command 5: `undo` <br>
Expected Output: `Your previous action has been undone.` <br>
Command 6: `undo` <br>
Expected Output: `Your previous action has been undone.`

## Tests for Redo command

#### Redo one change
Command 1: `add Sleep` <br>
Command 2: `undo` <br>
Command 3: `redo` <br>
Expected Output: `Your previous undo action has been redone.`

#### Redo two changes
Command 1: `Update 8 p/med` <br>
Command 2: `done 8` <br>
Command 3: `undo` <br>
Command 4: `undo` <br>
Command 5: `redo` <br>
Expected Output: `Your previous undo action has been redone.` <br>
Command 6: `redo` <br>
Expected Output: `Your previous undo action has been redone.`

#### Redo three changes

Command 1: `add Lunch` <br>
Command 2: `update 15 p/high` <br>
Command 3: `delete 8` <br>
Command 4: `undo` <br>
Command 5: `undo` <br>
Command 6: `undo` <br>
Command 7: `redo` <br>
Expected Output: `Your previous undo action has been redone.` <br>
Command 8: `redo` <br>
Expected Output: `Your previous undo action has been redone.` <br>
Command 9: `redo` <br>
Expected Output: `Your previous undo action has been redone.`

## Tests for Help command

#### Attempt opening help window
Command: `help` <br>
Expected Output: `Opened help window.`

## Tests for Clear command

#### Clear all tasks
Command: `clear` <br>
Expected Output: `Your task list has been cleared!`

#### Undo clear 
N.B.: This test is just to restore the cleared task list; should be performed immediately after clear command. <br>
Command: `undo` <br>
Expected Output: `Your previous action has been undone.`

## Tests for Autocomplete feature
#### Autocomplete with `TAB` button
Command: `f` followed by the `TAB` button <br>
Expected Output in Command Bar: `find`

#### Autocomplete with `SPACEBAR`
Command: `a` followed by the `SPACEBAR` <br>
Expected Output in Command Bar: `add`

#### Autocomplete for commands with same first character
Command 1: `s` followed by the `TAB` button <br>
Expected Output in Command Bar: `s`
Command 2: `h` followed by the `TAB` button <br>
Expected Output in Command Bar: `show`

## Tests for Command History feature

#### Find first previous entered command
Command: press `UP` arrow key
Expected Output in Command Bar: `undo`

#### Find next entered command
Command: press `DOWN` arrow key
Expected Output in Command Bar: `show`

#### Find second previous entered command
Command: press `UP` arrow key twice
Expected Output in Command Bar: `clear`