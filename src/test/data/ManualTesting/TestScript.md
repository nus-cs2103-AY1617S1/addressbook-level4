<!-- @@author A0093907W -->
# Manual Testing

## Seed data

We have included some randomly generated seed data in the file `./src/test/data/ManualTesting.json`.

To load this seed data:

1. Check if the file `./config.json` exists. On a freshly cloned copy of the app, this file should not exist.
	* If it exists, simply delete it to start afresh.
2. Copy the `ManualTesting.json` file to `./database.json`.
3. Run the app. It should start with the seed data in place.

## Test commands

We have implemented input disambiguation for most command types such that if the user enters a command that is ambiguous or missing parameters, we will prompt the user to disambiguate the command by auto-populating a command template with user's parameters on a best-effort basis.

For brevity, we will simply denote a disambiguation prompt with the prefix "disambiguate". 

### Add Task

Command | Expected behavior
------- | -----------------
`add task buy milk` | Floating task added
`add task buy milk by tmr 7pm` | Task with deadline added
`add task` | Disambiguate: `add task "<name>" by "<deadline>"`

### Complete / Uncomplete Task

Command | Expected behavior
------- | -----------------
`complete 2` | Task (due in the future) is marked as complete and stays on the list 
`complete 1` | Floating task is marked as complete and hidden from the list
`uncomplete 1` | Completed task is marked as incomplete
