# Test Script (Manual Testing)

## Loading the sample data
path /src/test/data/ManualTesting/SampleData.xml<br>

## Open help window

1. Type `help` or press F1<br>

> Note: Click away from command box before pressing F1.<br>

Result: A new window pops-up with a help window image, listing out all commands available.<br>

## Add new Todo task

1. Type `have coffee with Prof Damith`<br>

Result: A new todo task for `have coffee with Prof Damith` is added into the todo list panel. (index T5)<br>

## Add new Deadline task

1. Type `finish user guide 10 dec 2pm`<br>

Result: A new deadline task `finish user guide` with `Sat, 10 Dec 02:00pm` is added into deadlines list panel (index D12)<br>

## Add new Event task

1. Type `internship interview with Uncle Soo 2 dec 3 to 3.30pm`<br>

Result: A new event task `internship interview with Uncle Soo` with `Fri, 02 Dec 03:00PM to Fri, 02 Dec 03:30PM` is added into events list panel (index E9)<br>

## Add a task with tag

1. Type `add watch Kimi no Na Wa #Anime`<br>

Result: A new todo task for `watch Kimi no Na Wa` with `#Anime` into the todo list panel. (index T10)<br>

## View uncompleted tasks and upcoming events

1. Type `view` or press `Ctrl` + `Shift` + `T`<br>

Result: The task list will show all uncompleted tasks and upcoming events in the current task manager. (33 tasks listed)<br>

## View all tasks

1. Type `view all` or press `Ctrl` + `Shift` + `L` <br>

Result: The task list will show all tasks in the current task manager. (54 tasks listed)<br>

## View tasks for specific date

1. Type `view 15 Nov`<br>

Result: The task list will show all uncompleted deadlines tasks up to 15 Nov, all events tasks that fall in that date and all uncompleted todo tasks.(31 tasks listed) <br>

## View all completed tasks

1. Type `view done` or press `Ctrl` + `Shift` + `D`<br>

Result: The task list will show all completed tasks and past events in the current task manager. (21 tasks listed) <br>

## Find all tasks with a specific keyword in their name

1. Type `find dish`<br>

Result: The task list will show all tasks with the keyword `dish` in their name in the current task manager.(2 tasks listed)<br>

## Find all tasks with a specific tag

1. Type `find #work`<br>

Result: The task list will show all tasks with the tag `#work`in the current task manager. (11 tasks listed) <br>

## Edit the name of a Todo task

1. Type `view`
2. Type `edit t3 buy new blue tie`<br>

Result: The name of the todo task will be changed to `buy new blue tie`. (index T3) <br>

## Edit due date of a Deadline task

1. Type `edit d9 14 Nov`<br>

Result: The task will have it's due date changed to `Mon, 14 Nov 02:00PM`. (index D10) <br>

## Edit period of an Event task

1. Type `edit e6 14 Nov 5 to 6pm`<br>

Result: The task will have it's period changed to `Mon, 14 Nov 05:00PM to Mon, 14 Nov 06:00PM`.(index E7)<br>

## Edit a Deadline task to a Todo task

1. Type `edit d2 go to gym`<br>

Result: The task will be moved from the deadline list panel to the todo list panel with its due date removed. (index T5) <br>

## Edit a Todo task to a Deadline task

1. Type `edit t8 12 Nov 2pm`<br>

Result: The task will be moved frmo the todo list panel to the deadline list panel and with a due date `Sat, 12 Nov 02:00PM`.(index D8)<br>

## Edit an Event task to a Todo task

1. Type `edit e10 go shopping with Mary`<br>

Result: The task will be moved from the event list panel to the todo list panel and with its period removed. (index T5)<br>

## Edit a Todo task to an Event task

1. Type `edit t11 25 Nov 3 to 5pm`<br>

Result: The task will be moved from the todo list panel to the event list panel and with a period `Fri, 25 Nov 03:00PM to Fri, 25 Nov 05:00PM`. (index E9)<br>

## Edit a Deadline task to an Event task

1. Type `edit d9 14 Nov 9 to 10am`<br>

Result: The task will be moved from the deadline list panel to the event list panel with it's time parameter changed from a due date to a period of `Mon, 14 Nov 09:00AM to Mon, 14 Nov 10:00AM`. (index E6)<br>

## Edit an Event task to a Deadline task

1. Type `edit e4 11 Nov 4pm`<br>

Result: The task will be moved from the event list panel to the deadline list panel with it's time parameter changed from a period to a due date of `Fri, 11 Nov 04:00PM`.<br>

## Mark a single task as done

1. Type `done t7`<br>

Result: The command box will display the name of the task marked as done and the task will be removed from the default list view.<br>

## Mark multiple tasks as done

1. Type `done e1 t3 d5`<br>

Result: The command box will display the name of all the tasks marked as done and those tasks will be removed from the default list view.<br>

## Mark a range of tasks as done

1. Type `done t4-6`<br>

Result: The tasks from index T4 to T6 will be marked as done. <br>

## Delete a single task

1. Type `delete t3`<br>

Result: The command box will display the name of the task deleted and the task will be removed from the task manager.<br>

## Delete multiple tasks

1. Type `delete e1 t3 d5`<br>

Result: The command box will display the name of all the tasks deleted and those tasks will be removed from the task manager.<br>

## Delete a range of tasks

1. Type `delete d4-6`<br>

Result: The tasks from index T4 to T6 will be deleted. <br>

## Clear out all tasks in task manager

1. Type `clear` or press `Ctrl` + `Shift` + `C`<br>

Result: All tasks will be cleared out and the task manager will be empty. <br>

## Undo when no valid commands have been executed yet

1. Ensure the task manager has just been opened<br>
2. Type `undo` or press `Ctrl` + `Shift` + `U`<br>

Result: The command box will display that there is no more previous command in this session and task manager remains in the same state.<br>

## Undo a command

1. Type `clear`<br>
2. Type `undo` or press `Ctrl` + `Shift` + `U`<br>

Result: The command box will display the previous action undone and the task manager will revert back to the previous state.<br>

## Redo a command previously undone

1. Type `add don't delete me`<br>
2. Type `undo` or press `Ctrl` + `Shift` + `U`<br>
3. Type `redo` or press `Ctrl` + `Shift` + `Y`<br>

Result: The command box will display the previous undone action restored and the task manager will revert to the undone state.<br>

## Redo when no commands have been undone

1. Ensure no `undo` commands has been executed<br>
2. Type `redo` or press `Ctrl` + `Shift` + `Y`<br>

Result: The command box will display that there is no recent undone command in this session and the task manager remains in the same state.<br>

## Redo when a new valid command have been executed

1. Tyoe `add can't redo me`
2. Type `undo` or press `Ctrl` + `Shift` + `U`<br>
3. Type `add don't believe me?`
4. Type `redo` or press `Ctrl` + `Shift` + `Y`<br>

Result: The command box will display that there is no recent undone command in this session and the task manager remains in the same state.<br> 

## Close the task manager

1. Type `exit` or press `Esc`

Result: The task manager app will close.<br>
