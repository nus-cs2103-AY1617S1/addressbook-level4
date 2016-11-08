## Loading Sample Data
1. Place `dailyplanner.xml` in a folder named `data`.
2. Place the `data` folder in the same directory as `[F11-C4][DailyPlanner].jar` file
3. Run `[F11-C4][DailyPlaner].jar`

## Manual Testing
All commands should be entered in command box located  at the bottom, no clicking required. Using the app in fullscreen is recommended. Entering the following commands in the given order will cover and showcase functionality of the app:

### Help
- `help`
>Opens help window

### Add
- `add task`
>Floating task with name `task` added
- `add task2 s/today` 
>Task with only a start date added
- `add task3 s/today 2pm e/6pm`
>Fixed task with start and end added
- `add task4 s/today e/next week`
>Task spanning over multiple days added
- `add task5 c/homework c/math`
> Task with multiple categories added
```
For all add actions, observe that daily planner will sort the newly added task to its position in the list, based on urgency of the task. Daily planner will also automatically scroll to view the newly added position of the task.
```

### Show
- `show today`
> Shows all tasks for the current date
- `show this friday`
> Shows all tasks scheduled the first friday from current date
- `show completed` or `show complete`
> Shows all tasks that have been completed 
- `show not complete` or `show not completed`
> Shows all incomplete tasks
```
Daily planner will indicate which day's schedule you are currently looking at at the top.
```

### Find
- `find math`
>Finds all tasks with having keyword 'math'

### Edit
- `show`
> Show all tasks first
- `edit 1 s/3pm e/5pm`
> Task at index 1 is edited to start today at 3pm and end today at 5pm. The edited task is also sorted to its new position based on its new start and end time and the position is then scrolled to for the user to view
- `show tomorrow`
- `edit 1 newtaskname`
> This time, edit is called on index 1 of the list that user is currently viewing, which was tomorrow's schedule. Now, only the task's name is edited to 'newtaskname'
- `edit 2 c/fun`
> Task at index 2 of currently viewed list has its category changed to 'fun'

### Complete
- `complete 3`
> Task at index 3 of currently viewed list is marked completed
- `uncomplete 3` 
> The same task is now marked incomplete

### Delete
- `delete 3`
> Task at index 3 of currently viewed list is deleted
- `show completed`
- `delete completed`
> First, user views all completed tasks. Then, all completed tasks are deleted.

### Pin
- `pin 5`
> Task at index 5 of currently viewed list is pinned to the pin board
- `edit 5 s/day after tomorrow 6pm`
- `complete [NEW INDEX OF TASK 5 AFTER SORT]`
> Any changes made to the original task are also reflected on the pin board
- `unpin 1`
> Unpins task at index 1 of pin board

### Notifications
- `add task6 e/yesterday`
> Task should be added with an 'overdue' label, since the task was supposed to end yesterday
- `add task7 e/[2HOURS FROM NOW]`
> Task is added with a deadline coming up soon; task will hold a 'due soon' label to remind the user.

### Undo
- `add task8 s/two days ago`
- `undo`
- `delete 9`
- `delete 10`
- `undo`
- `undo`
- `edit 1 s/1am e/6am`
- `undo`
- `complete 1`
- `undo`
- `pin 1`
- `undo`
- `unpin 1`
- `undo`
> Add, delete, edit, complete, uncomplete, pin, unpin, complete can all be undone.