<!-- @@author A0138420N --> 
# Manual Test Script
The sample data `SampleData.xml` can be used for manual testing.

## Loading of sample data
1. Download ggist.jar from our release.
2. Run ggist.jar and exit
3. Type `save SampleData.xml`
3. Delete `./data/SampleData.xml` in the subdirectory folder
6. Download and copy `SampleData.xml` to the `./data/` subdirectory folder
7. Run ggist.jar again
8. Sample data is loaded

## To add task : `add` command
Command | Expected Results |
------- | :--------------
`add read book`| A new task would appear on the current list.<br>The start and end column will be blank.<br>The border of the new task added will be highlighted
`add bake cookies by today -low` | A new task will appear on the list if the current list shown is today. <br> The start column will be blank. <br> The end column will show today's date with no time. <br> `bake cookies` will be highlighted orange to indicate low priority.
`add complete assignment by today 9pm -high` | A new task will appear on the list if the current list shown is today. <br> The start column will be blank. <br> The end column will show today's date with `9:00 PM` shown. <br> `complete assignment` will be highlighted red to indicate high priority.
`add go overseas from today 10pm to 30 nov -med`  | A new task will appear on the list if the current list shown is today. <br> The start column will show today's date with `10:00pm`. <br> The end column will show `Wed, 30 Nov 16` with `9:00 PM` shown. <br> `go overseas` will be highlighted orange to indicate medium priority.
`add play basketball from 1pm to 2pm`| A new task will appear on the list if the current list shown is today. <br> The start column will show today's date with `1:00pm`. <br> The end column will show today's date with `2:00 PM` shown.

## To see tasks of specified type: `list` command
Command | Expected Results |
------- | :--------------
`list`| The top panel of GGist will reflect `TASKS TO BE COMPLETED`.<br>The list shows all incomplete tasks
`list all` | The top panel of GGist will reflect `ALL TASKS`.<br>The list shows all tasks
`list done` | The top panel of GGist will reflect `COMPLETED TASKS`.<br>The list shows all tasks marked as done.<br>All completed tasks will be highlighted green
`list high` | The top panel of GGist will reflect `HIGH PRIORITY TASKS`.<br>The list shows tasks with high priority.<br>This can be used similarly for `med` and `low` priorities
`list 22 nov` | The top panel of GGist will reflect `TUESDAY, 22 NOVEMBER 2016`.<br>The list shows incomplete tasks to be done on 22 November.<br>This can be used similarly for all dates.

## To mark task as completed: `done` command 
Command | Expected Results |
------- | :--------------
done 1| The task at `index 1` will be marked as done be removed from the list
done 1,2,3 | Task at `index 1`, `index 2` and `index 3` will be marked as done and removed from the current list

## To unmark completed task as incomplete: `continue` command

This command can only be used on completed tasks. Either `list all` or `list done` to view completed task which are highlighed green.

Command | Expected Results |
------- | :--------------
continue 1| The task at `index 1` will be marked as undone be removed from the completed list.<br> If you used `list all`, the task highlihted will change color from green to dark brown
continue 1,2,3 | Task at `index 1`, `index 2` and `index 3` will be marked as undone and removed from the completed list. <br> If you used `list all`, the tasks highlihted will change color from green to dark brown

## To change details of your tasks: `edit` command
Command | Expected Results |
------- | :--------------
`edit 1 task charge iPad`| The task at `index 1` will have the task name changed to `charge iPad`
`edit 2 start date tomorrow` | The task at `index 2` will have the start date changed to the next day. <br> This is allowed if the start date and time does not come later than the end date and time.
`edit 3 end date 23 Dec, end time 9pm`| The task at `index 3` will have the end date changed to `Fri, 23 Dec 16` and end time changed to `9:00pm`. 
`edit 4 priority high`| the task at `index 4` will have the priority changed to high. The task will be highlighted red.

## To search for keywords: `search` command
Command | Expected Results |
------- | :--------------
`search do`| All task with the keyword `do` in their task name will be shown.

## To remove a task from _GGist_: `delete` command
Command | Expected Results |
------- | :--------------
`delete 1`| The task at `index 1` will be removed from the list
`delete 1,2,3` | Task at `index 1`, `index 2` and `index 3` will be removed from the list

## To revert the previous action: `undo` command

This command can be used multiple times in succession

Command | Expected Results |
------- | :--------------
`undo` | The last action will be reverted

## To revert the previous undo: `redo` command

This command can be used multiple times in succession, limited to the number of `undo` used

Command | Expected Results |
------- | :--------------
`redo` | The last undo will be reverted

## To save data to a different location: `save` command

`<FILE_LOCATION>` is a valid folder you wish to save the data to.

Command | Expected Results |
------- | :--------------
`save ggist.xml` | The save file name will be changed. <br> The bottom of the window will also reflect `./data/ggist.xml`
`save <FILE_LOCATION>` | All save data will be relocated to the specified file directory.<br> The default save file name is ggist.xml.<br> The bottom of the window will also reflect `/<FILE_LOCATION>/ggist.xml`
`save data` | All save data will be relocated back to the default data subdirectory.<br> The default save file name is ggist.xml.<br> The bottom of the window will also reflect `./data/ggist.xml`

## To remove all tasks: `clear` command
Command | Expected Results |
------- | :--------------
`clear` | All the tasks in the list will be cleared, including all the tasks in your current save file

## To exit the _GGist_: `exit` command
Command | Expected Results |
------- | :--------------
`exit` | The application closes


