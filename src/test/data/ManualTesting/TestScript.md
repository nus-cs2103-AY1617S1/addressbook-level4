# Manual Scripted Testing Guide for Tusk

## Loading sample data
The two data files that are included in this manual testing package are:

1. `SampleData-task.xml` (contains 50+ sample tasks)
2. `SampleData-alias.xml` (contains a few initial command aliases)

To load them into Tusk:

1. Rename `SampleData-task.xml` to `tasks.xml`
2. Rename `SampleData-alias.xml` to `alias.xml`
3. Run `Tusk.jar` so that it creates the `data/` subdirectory
4. Delete any files inside the `data/` subdirectory and copy the two renamed files into it.
5. Run `Tusk.jar` and the data should be loaded correctly. 

## What you should see when data is correctly loaded
1. The first task will be coloured yellow, and there should be many other tasks below that
2. ...

## Test Procedure Legend
The following tests are specified as such:

- `command to type into command box / actions to perform`

> Expected results

## Test Procedure

- `help`

> Should show the help window

- `adsf`

> Tasks remain unchanged since it is an invalid command

- `add meeting`

> Should add a task with description "meeting"

- `add homework by 29 dec`

> Task should show a deadline of `29th Dec 2016, 12.00 AM`

- `add homework on 30 dec`

> Task should show a date of `30th Dec 2016, 12.00 AM`

- `add some task by 29 dec 2.34am`

> Task should show a deadline of `29th Dec 2016, 2.34 AM`

- `add event from 29 dec 12.34 am to 30 dec 2.34pm`

> Task should show two dates, `29th Dec 2016, 12.34 AM` and `30th Dec 2016, 2.34 PM`

- `add overdue tasks by 10 jan 2005`

> Task should appear below any pinned tasks in the task list, and should be coloured in red (overdue style)

- `add event from 29 dec 12.34 am to 30 dec 2.66pm` (invalid end time)

> Task should have the description "event from 29 dec 12.34 am to 30 dec 2.66pm" as Tusk assumes you meant this as a description since the time is not valid

- `add "prepare for the conference on 23 dec"`

> Task should have the description "prepare for the conference on 23 dec" as the double quotation indicates that the date is part of the description

- `edit 1 desc clean desk table`

> The description of task at index 1 should change from "clean desk" to "clean desk table"

- `update 1 desc clean table`

> The description of task at index 1 should change from "clean desk table" to "clean table" since update command is an alternative to edit command.

- `edit 1 date 7 dec 10pm`

> The task at index 1 changes from a floating task to a deadline task with date `07 Dec 2016, 10.00PM`

- `edit 1 task buy groceries and bread by 6 dec 8pm`

> The task at index 1 changes from a floating task to a deadline task with description "buy groceries and bread" and date `06 Dec 2016, 8.00PM`

- `list`

> All incomplete tasks should be listed and the `Incomplete` tab should be highlighted

- `delete 1`

> The first task in the list should be deleted

- `complete 1`

> The first task in the list should disappear

- `list complete`

> The previously completed task should be in this list. The `Complete` tab should be highlighted

- `uncomplete 1`

> The first task in the list should disappear

- `list`

> All incomplete tasks should be listed, the `Incomplete` tab should be highlighted and the task that was just "uncompleted" should be in this list

- `pin 8` 

> The eighth task should now appear at the top of the list of tasks and be coloured yellow

- `unpin 1`

> The task that was just unpinned would no longer be at the top of the list

- `alias am add meeting`

> The results display should report that this alias is added successfully

- `list alias`

> The am -> add meeting alias should appear in this list

- `unalias am`

> The alias should be removed from the alias list

- `alias ae add event`

> The results display should report that this alias is added successfully

- `ae from 5pm to 6pm`

> A task should be added from today's date, 5.00 PM to 6.00 PM

- `clear`

> All tasks in the current list should be deleted and disappear

- `undo`

> All tasks that were just deleted should be restored

- `redo`

> All tasks that were restored should be deleted again

- `undo`

> All tasks that were just deleted should be restored

- `find mee`

> The tasks with "mee" as a substring in their descriptions should be shown. The `Find` tab at the top of the main window should now be highlighted

- `clear`

> The list of tasks with "mee" as a substring in their descriptions are all deleted and disappear. The incomplete task list should now be shown

- `undo`

> All tasks with "mee" as a substring in their descriptions that were just deleted should be restored

- <kbd>Up</kbd> arrow key

> The 'undo' command should be displayed in the command box

- <kbd>Up</kbd> arrow key
 
> The 'clear' command should be displayed in the command box

- <kbd>Down</kbd> arrow key

> The 'undo' command should be displayed in the command box

- <kbd>Control</kbd> + <kbd>Down</kbd> arrow key

> The current list of tasks should scroll about halfway down

- <kbd>Control</kbd> + <kbd>Up</kbd> arrow key

> The current list of tasks should scroll up slightly

- `unc`<kbd>TAB</kbd>

> The uncomplete command should be autocompleted for you

- `u`<kbd>TAB</kbd> (keep pressing <kbd>TAB</kbd>)

> The commands in the command box should cycle between `unalias`, `update`, `uncomplete`, `undo` and `unpin`

- `exit`

> Tusk should close

- Open Tusk again

> Tusk should reopen with the same state as before, except with a blank command history (so up/down should not work now)
