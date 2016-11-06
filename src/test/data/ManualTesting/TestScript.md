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
2. Paste the file into the data storage directory (the `data` folder found in the same location as that of the app; if the directory does not exist, create it)
3. Delete the existing XML data file and rename SampleData.xml to the name of this existing XML data file.
4. Now to perform Manual Testing, perform the tests below in the given order.

## Tests for Add command

#### Adding a floating task
Command: ` ` <br>
Expected Output: ` `

#### Adding an event
Command: ` ` <br>
Expected Output: ` `

#### Adding a deadline
Command: ` ` <br>
Expected Output: ` `

#### Adding a task with start time only
Command: ` ` <br>
Expected Output: ` `

#### Adding an overlapping task
Command: ` ` <br>
Expected Output: ` `

#### Adding a duplicate task
Command: ` ` <br>
Expected Output: ` `

#### Adding in an invalid manner (Example 1)
Command: ` ` <br>
Expected Output: ` `

#### Adding in an invalid manner (Example 2)
Command: ` ` <br>
Expected Output: ` `

## Tests for Show command

#### Show all
Command: `show all` <br>
Expected Output: ` `

#### Show overdue
Command: `show overdue` or `overdue` <br>
Expected Output: ` `

#### Show done/complete
Command: `show done` or `show complete` <br>
Expected Output: ` `

#### Show today
Command: `show today` or `today` <br>
Expected Output: ` `

#### Show tomorrow
Command: `show tomorrow` or `tomorrow` <br>
Expected Output: ` `

#### Show floating
Command: `show floating` or `floating` <br>
Expected Output: ` `

#### Show for any random date
Command: `show 25/12/2016` or `show xmas` <br>
Expected Output: ` `

#### Show high priority tasks
Command: `show p/high` <br>
Expected Output: ` `

#### Show medium priority tasks
Command: `show p/med` <br>
Expected Output: ` `

#### Show low priority tasks
Command: `show p/low` <br>
Expected Output: ` `

#### Show upcoming/incomplete tasks
Command: `show upcoming` or `show incomplete` or `show` <br>
Expected Output: ` `

## Tests for Find command

#### Find non-existing task
Command: `find ` <br>
Expected Output: ` `

#### Find task with one matching result
Command: `find ` <br>
Expected Output: ` `

#### Find task with more than one matching result
Command: `find ` <br>
Expected Output: ` `

#### Find all tasks
Command: `find *` <br>
Expected Output: ` `

#### Find task with wildcard character *
Command: `find B*y*` <br>
Expected Output: ` `

## Tests for Delete command

#### Delete task by index 
Command: `delete ` <br>
Expected Output: ` `

#### Delete task by name with only one matching result
Command: `delete ` <br>
Expected Output: ` `

#### Delete task by name with multiple matching results
Command: `delete ` <br>
Expected Output: ` `

#### Delete task by name with zero matching results
Command: `delete ` <br>
Expected Output: ` `

## Tests for Update command

#### Update start time for a task
Command: `update ` <br>
Expected Output: ` `

#### Update end time for a task
Command: `update ` <br>
Expected Output: ` `

#### Update priority for a task
Command: `update ` <br>
Expected Output: ` `

#### Update recurring frequency for a task
Command: `update ` <br>
Expected Output: ` `

#### Update task details for a task
Command: `update ` <br>
Expected Output: ` `

#### Update floating task to a task with start time and end time
Command: `update ` <br>
Expected Output: ` `

#### Invalid attempt at update (Example 1)
Command: `update ` <br>
Expected Output: ` `

#### Invalid attempt at update (Example 2)
Command: `update ` <br>
Expected Output: ` `

## Tests for Done command

#### Complete task by index
Command: `done ` <br>
Expected Output: ` `

#### Complete task by name with only one matching result
Command: `done ` <br>
Expected Output: ` `

#### Complete task by name with multiple matching results
Command: `done ` <br>
Expected Output: ` `

#### Complete task by name with zero matching results
Command: `done ` <br>
Expected Output: ` `

## Tests for Setstorage command

#### Set storage file path to random file location
Command: `setstorage ` <br>
Expected Output: ` `

#### Set storage file path to default file location
Command: `setstorage default` <br>
Expected Output: ` `

## Tests for Undo command

#### Undo one change
Command 1: ` ` <br>
Command 2: `undo` <br>
Expected Output: `Your previous action has been undone.`

#### Undo two changes
Command 1: ` ` <br>
Command 2: ` ` <br>
Command 3: `undo` <br>
Expected Output: `Your previous action has been undone.` <br>
Command 4: `undo` <br>
Expected Output: `Your previous action has been undone.`

#### Undo three changes

Command 1: ` ` <br>
Command 2: ` ` <br>
Command 3: ` ` <br>
Command 4: `undo` <br>
Expected Output: `Your previous action has been undone.` <br>
Command 5: `undo` <br>
Expected Output: `Your previous action has been undone.` <br>
Command 6: `undo` <br>
Expected Output: `Your previous action has been undone.`

## Tests for Redo command

#### Redo one change
Command 1: ` ` <br>
Command 2: `undo` <br>
Command 3: `redo` <br>
Expected Output: `Your previous undo action has been redone.`

#### Redo two changes
Command 1: ` ` <br>
Command 2: ` ` <br>
Command 3: `undo` <br>
Command 4: `undo` <br>
Command 5: `redo` <br>
Expected Output: `Your previous undo action has been redone.` <br>
Command 6: `redo` <br>
Expected Output: `Your previous undo action has been redone.`

#### Redo three changes

Command 1: ` ` <br>
Command 2: ` ` <br>
Command 3: ` ` <br>
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



## Tests for Command History feature


