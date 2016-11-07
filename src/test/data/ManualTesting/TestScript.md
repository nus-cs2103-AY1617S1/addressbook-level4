# Manual Scripted Testing

## How to load the sample data

1. Download saavytasker.jar
1. Download the [SampleData.xml](/test/data/ManualTesting/SampleData.xml) file
2. Make a copy, and rename it is to `savvytasker.xml`
3. Copy `savvytasker.xml` and overwrite the same named file \data\savvytasker.xml
4. Launch savvytasker.jar

## Test script

1. List tasks

- List all tasks: `list` or <kbd>Ctrl</kbd> + <kbd>L</kbd>
> Lists all currently unmarked/ongoing tasks, according to due date.
> All floating tasks are listed after tasks with dates, according to the order they were entered.

- List all tasks by priority level: `list priority level` or <kbd>Ctrl</kbd> + <kbd>P</kbd>

- List all marked tasks: `list archived` or <kbd>Ctrl</kbd> + <kbd>A</kbd>
> Lists all marked/completed tasks.

2. Adding new tasks

- Add a task: `add Buy groceries`
> New task will be added to end of unmarked list, with a default `Medium` priority level (yellow in color).
> Task will be added as a `Floating Task`.

- Add a task with a deadline and High priority level: `add Submit assignment e/11-11-16 8pm p/High`
> New task will be added, with a `High` priority level (red in color), and end date of 11 Nov 2016, 8pm. 
> Start date will be defaulted to current date 12am.

- Add a task with a Low priority level: `add Watch a movie p/Low`
> New task will be added, with a `Low` priority level (green in color).
> Task wil be added as a `Floating Task`.

- Add a task with a start date, end date, location, and description: `add Attend dance class s/8-11-16 5pm e/8-11-16 7pm l/Bugis dance studio d/Salsa class`
> New task will be added, with respective start date, end date, location, and description.
> Task will be added to the respective dates on the calendar.

- Add an overdue task: `add Study for midterms s/6-10-16`
> New task will be added, end date will be defaulted to 23:59:59 of the start date.
> Task will be added as an overdue task.

3. Deleting tasks

- Delete a task: `delete 1`
> Removes the task listed at index 1 (Go to the gym).

- Delete multiple tasks: `delete 1 2 3`
> Removes the tasks listed at indexes 1, 2 and 3 (Swimming session, Project deadline, Homework submission deadline).

4. Undo commands

- Undo last command: `undo` or <kbd>Ctrl</kbd> + <kbd>Z</kbd>
> The last 'delete 1 2 3' command is undone, and all 3 deleted tasks are added back.

5. Redo commands

- Redo the last undone command: `redo` or <kbd>Ctrl</kbd> + <kbd>Y</kbd>
> The last undone `delete 1 2 3` command is redone, so all 3 tasks are deleted again.

6. Modify tasks

- Modify a task: `modify 1 t/Buy working supplies s/11-10-16 3pm e/11-10-16 4pm l/Popular Bookstore d/Pens p/Low`
> Changes the task listed at index 1 (Christmas shopping) to Buy working supplies, with new start date Nov 10 3pm and end date Nov 10 4pm, and priority level changed to 'Low'.
> Also adds a new location: Popular Bookstore, and new description: Pens.

7. Alias command

- Alias a keyword: `alias k/pjm r/Project Meeting` `add pjm`
> Future instances of `pjm` will be recognized as `Project Meeting`.
> A new task 'Project Meeting' will be added to the end of the list.

- Alias a command: `alias k/++ r/add` `++ pjm`
> Future instances of `++` will be recognized as an `add` command.
> A new task `Project Meeting` will be added to the end of the list.

- View aliased keys: `list alias` or <kbd>Ctrl</kbd> + <kbd>I</kbd>
> Lists all aliased keys (pjm, ++).

8. Unalias command

- Unalias an aliased keyword: `unalias pjm` `add pjm`
> Future instances of `pjm` will no longer be recognized as `Project Meeting`.
> A new task `pjm` will be added to the end of the list.

9. Mark a task

- Mark a completed task: `mark 1`
> Marks the task listed at index 1 (Buy working supplies).
> The newly marked task is relisted on the archived list.

10. Unmark a task

- Unmark a previously marked task: `list archived` `unmark 1`
> Shows the list of marked/archived tasks.
> Unmarks the task listed at index 1 (Buy working supplies).
> The newly unmarked task relists on the unmarked task list.

11. Find a task

- Find tasks containing keywords: `find gro`
> Shows all unmarked/unarchived tasks containing the keyword `gro` (Bring brother to playground, Go grocery shopping, Buy groceries).

- Find tasks containing exact match: `find t/exact buy groceries`
> Shows the task(s) containing the exact phrase: `buy groceries`, not case sensitive.

12. Clear all tasks

- Clear all tasks: `clear` or <kbd>Ctrl</kbd> + <kbd>D</kbd>
> Deletes all data from all lists.

13. Other commands

- View help: `help` or <kbd>Ctrl</kbd> + <kbd>H</kbd>
> Opens the help window.

- Exit the app: `exit` or <kbd>Ctrl</kbd> + <kbd>Q</kbd>
> Exits the SaavyTasker app.
