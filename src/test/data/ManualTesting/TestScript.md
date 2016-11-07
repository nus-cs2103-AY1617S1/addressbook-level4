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

*A note on dates in our sample commands: While our app supports timings without dates, e.g. "7pm" (which will default to today 7pm), for manual testing purposes we have specified all dates to not be on the same day. This is for consistency -- if we create a task by today 2pm and then complete it, the task will by hidden if the time now is after 2pm, but remain on the screen is the time now is before 2pm.*

### Add Task

Command | Expected behavior
------- | -----------------
`add task Buy donuts` | Floating task added
`add task Buy milk by tmr 7pm` | Task with deadline added
`add task` | Disambiguate: `add task "<name>" by "<deadline>"`

### Complete / Uncomplete Task

Command | Expected behavior
------- | -----------------
`complete 2` | Task (due in the future) is marked as complete and stays on the list 
`complete 1` | Floating task is marked as complete and hidden from the list
`uncomplete 1` | Completed task is marked as incomplete

### Add Event

Command | Expected behavior
------- | -----------------
`add event CS1010S meeting from tmr 7pm to tmr 9pm` | Event added tomorrow from 7-9pm
`add event Go to the zoo from tmr 4pm to tmr 6pm` | Disambiguate: Since "to" is a keyword, the command parser got confused with the start and end dates of the event.
`add event "Go to the zoo" from tmr 4pm to tmr 6pm` | Event added tomorrow from 4-6pm. Using quotes tells the command parser not to recognize anything within as tokens.

### Update Task / Event

The following should be displayed on the screen:
1. CS1010S meeting, 19:00 - 21:00
2. Go to the zoo, 16:00 - 18:00
3. Buy milk, 19:00

If the numbering differs from this order, please use the correct numbering in the commands.

Command | Expected behavior
------- | -----------------
`update 1 name CS1010FC meeting` | Event is renamed
`update 1 from tmr 6pm` | Event start time is changed to 6pm
`update 1 from tmr 4pm to tmr 5pm ` | Event time is changed to 4-5pm
`update 3 Buy baby milk` | Task is renamed
`update 3 by tmr 4pm` | Task deadline is changed to 4pm
`update 3 from tmr 4pm to tmr 5pm` | Disambiguation: A task only has a single deadline

### List query

The following should be displayed on the screen:
1. CS1010FC meeting, 16:00 - 17:00
2. Go to the zoo, 16:00 - 18:00
3. Buy baby milk, 16:00

If the numbering differs from this order, please use the correct numbering in the commands.

Command | Expected behavior
------- | -----------------
`list` | List shows all incomplete tasks, completed tasks in the future, and events in the future
`list completed` | Only the completed task "Buy donuts" from much earlier shows up
`list incomplete` | Only the incomplete task "Buy baby milk" shows up
`list events` | Only the two events show up
`list events before tmr 5.30pm`| Only "CS1010FC meeting" shows up
`list before tmr 7pm` | Only tasks and events before tomorrow 7pm show up, which are "CS1010FC meeting", "Go to the zoo", and "Buy baby milk"
