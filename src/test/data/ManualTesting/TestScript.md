# Test Script

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
2. Paste the file into the data storage directory (the `data` folder found in the same location as the JAR file `Lazyman's Friend.jar`)
3. Delete the existing XML data file and rename SampleData.xml to the name of this existing XML data file.
4. Now to perform Manual Testing, perform the tests below in the given order.

## Tests for Add command

#### Adding a floating task
Command: ` `
Expected Output: ` `

#### Adding an event
Command: ` `
Expected Output: ` `

#### Adding a deadline
Command: ` `
Expected Output: ` `

#### Adding a task with start time only
Command: ` `
Expected Output: ` `

#### Adding an overlapping task
Command: ` `
Expected Output: ` `

#### Adding a duplicate task
Command: ` `
Expected Output: ` `

#### Adding in an invalid manner (Example 1)
Command: ` `
Expected Output: ` `

#### Adding in an invalid manner (Example 2)
Command: ` `
Expected Output: ` `

## Tests for Show command

#### Show all
Command: `show all`
Expected Output: ` `

#### Show overdue
Command: `show overdue` or `overdue`
Expected Output: ` `

#### Show done/complete
Command: `show done` or `show complete`
Expected Output: ` `

#### Show today
Command: `show today` or `today`
Expected Output: ` `

#### Show tomorrow
Command: `show tomorrow` or `tomorrow`
Expected Output: ` `

#### Show floating
Command: `show floating` or `floating`
Expected Output: ` `

#### Show for any random date
Command: `show 25/12/2016` or `show xmas`
Expected Output: ` `

#### Show high priority tasks
Command: `show p/high`
Expected Output: ` `

#### Show medium priority tasks
Command: `show p/med`
Expected Output: ` `

#### Show low priority tasks
Command: `show p/low`
Expected Output: ` `

#### Show upcoming/incomplete tasks
Command: `show upcoming` or `show incomplete` or `show`
Expected Output: ` `

## Tests for Find command

#### Find non-existing task
Command: `find `
Expected Output: ` `

#### Find task with one matching result
Command: `find `
Expected Output: ` `

#### Find task with more than one matching result
Command: `find `
Expected Output: ` `

#### Find all tasks
Command: `find *`
Expected Output: ` `

#### Find task with wildcard character *
Command: `find B*y*`
Expected Output: ` `

## Tests for Delete command

#### Delete task by index
Command: `delete `
Expected Output: ` `

#### Delete task by name with only one matching result
Command: `delete `
Expected Output: ` `

#### Delete task by name with multiple matching results
Command: `delete `
Expected Output: ` `

#### Delete task by name with zero matching results
Command: `delete `
Expected Output: ` `

## Tests for Update command

#### Update start time for a task
Command: `update `
Expected Output: ` `

#### Update end time for a task
Command: `update `
Expected Output: ` `

#### Update priority for a task
Command: `update `
Expected Output: ` `

#### Update recurring frequency for a task
Command: `update `
Expected Output: ` `

#### Update task details for a task
Command: `update `
Expected Output: ` `

#### Update floating task to a task with start time and end time
Command: `update `
Expected Output: ` `

#### Invalid attempt at update (Example 1)
Command: `update `
Expected Output: ` `

#### Invalid attempt at update (Example 2)
Command: `update `
Expected Output: ` `

## Tests for Done command

#### Complete task by index
Command: `done `
Expected Output: ` `

#### Complete task by name with only one matching result
Command: `done `
Expected Output: ` `

#### Complete task by name with multiple matching results
Command: `done `
Expected Output: ` `

#### Complete task by name with zero matching results
Command: `done `
Expected Output: ` `

## Tests for Setstorage command

#### Set storage file path to random file location
Command: `setstorage `
Expected Output: ` `

#### Set storage file path to default file location
Command: `setstorage default`
Expected Output: ` `

## Tests for Undo command



## Tests for Redo command



## Tests for Help command

#### Attempt opening help window
Command: `help`
Expected Output: `Opened help window.`

## Tests for Clear command

#### Clear all tasks
Command: `clear`
Expected Output: `Your task list has been cleared!`

#### Undo clear (this test is just to restore the cleared task list)
Command: `undo`
Expected Output: `Your previous action has been undone.`

## Tests for Autocomplete feature



## Tests for Command History feature


