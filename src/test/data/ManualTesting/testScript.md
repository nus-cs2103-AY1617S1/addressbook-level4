[comment]: # (@@author A0148031R)
# Test Script for Manual Testing

## Loading the sample data

1. Ensure that you put `SampleData.xml` inside the `/src/test/data/ManualTesting` folder.
2. Start `Agendum.jar` by double clicking it.
3. Type `load src/test/data/ManualTesting/SampleData.xml`.

####Result:
* In the **Do It Soon** column, there should be 22 tasks appearing with indices from 1 to 22, where task 1-6 are highlighted in red, 7-12 in yellow, and the remaining not highlighted. 
* In the **Do It Anytime** column, there should be 16 tasks appearing, with indices from 23 to 38.
* In the **Done** column, there should be 12 tasks appearing, with indices 39-50.

## Help

1. Type `help`
2. Press <kbd>ESC</kbd>
3. Press <kbd>Ctrl H</kbd>
4. Press <kbd>Ctrl H</kbd>

####Result:
1. A help window appears in a table style, listing out all the command available, together with the command description and format.
2. Agendum exits the help window.
3. The help window will appear.
4. Agendum exits the help window.

## Date Time Formats

Before proceeding to add or schedule tasks with dates and time, here is a brief on the date time formats that Agendum supports, by combining any of the date format and time format below. The date/time formats are case insensitive too.

*Date Format*

| Date Format     | Example(s)           |
|-----------------|----------------------|
| Month/day       | 1/23                 |
| Day Month       | 1 Oct                |
| Month Day       | Oct 1                |
| Day of the week | Wed, Wednesday       |
| Relative date   | today, tmr, next wed |

 > If no year is specified, it is always assumed to be the current year.
 > It is possible to specify the year before or after the month-day pair in the first 3 formats (e.g. 1/23/2016 or 2016 1 Oct)
 > The day of the week refers to the following week. For example, today is Sunday (30 Oct). Agendum will interpret Wednesday and Sunday as 2 Nov and 6 Nov respectively (a week from now).

*Time Format*

| Time Format     | Example(s)                              |
|-----------------|-----------------------------------------|
| Hour            | 10, 22                                  |
| Hour:Minute     | 10:30                                   |
| Hour.Minute     | 10.30                                   |
| Relative time   | this morning, this afternoon, tonight   |

> By default, we use the 24-hour time format but we do support the meridian format as well e.g. 10am, 10pm


## Add
To add a task, you have to start your command with the keyword `add`.

>Here are the *acceptable format(s)*:

> * `add <name>` - adds a task which can be done anytime.
* `add <name> by <deadline>` - adds a task which have to be done by the specified deadline. Note the keyword `by`.
* `add <name> from <start time> to <end time>` - adds a event which will take place between start time and end time. Note the keyword `from` and `to`.


### 1. Add a floating task
Type `add watch movie`.
#### Result 
A new floating task named `watch movie` is created at the top of **Do It Anytime** column, and highlighted in purple borders.

### 2. Add a task with deadline
Type `add submit essay by 10pm`.
#### Result 
A new task named `submit essay` is created in the **Do It Soon** column, with its deadline under the name of this task. Also, it is highlighted in purple borders.

### 3. Add a task with event time
Type `add go for church camp from 20 nov 2pm to 25 nov 5pm`
#### Result 
A new task named `go for church` is created in the **Do It Soon** column, with its deadline under the name of this task	. Also, it is highlighted in purple borders.

### 4. Add a task with single quotation mark
Type `add 'drop by store' by tmr`
#### Result
A new task named `drop by store` is created in the **Do It Soon** column, with its deadline under the name of this task. 
>Sometimes Agendum may wrongly interpret part of task name as a deadline/event time. To avoid this wrong interpretation, you can add single quotation marks around the task name, and agendum will only interpret those inside the quotation marks as task name.


## Rename
To rename a task, you have to start your command with the keyword `rename`.

>Here is the *acceptable format*

> * `rename <id> <new name>` - give a new name to the task identified by <id>. The <id> must be a positive number and be in the most recent to-do list displayed.

Type `rename 1 complete peer review on TEAMMATES for 2101`.
#### Result
The task with index 1 is renamed to `complete peer review on TEAMMATES for 2101`. Also, it is highlighted in purple borders.

## (Re)schedule 
To reschedule a task, you have to start your command with the keyword `schedule`.

>Here are the *acceptable format(s)*:

>* `schedule <id>` - re-schedule the task identified by `<id>`. It can now be done anytime.  It is no longer bounded by a deadline or event time!
* `schedule <id> by <deadline>` - set or update the deadline for the task identified. Note the keyword `by`.
* `schedule <id> from <start time> to <end time>` - update the start/end time of the task identified by `<id>`. Note the keyword `from` and `to`.

###1. Schedule a task with no deadline or event time
Type `schedule 22`.
####Result
Task with index 22 now has no deadline, and it is shifted to **Do It Anytime** column with a new index. Also, it is highlighted in purple borders.

###2. Schedule a task with a deadline
Type `schedule 23 by 11 nov 10pm`.
####Result
Task with index 23 now has a deadline, and it is shifted to **Do It Soon** column with a new index. Also, it is highlighted in purple borders.

###3. Schedule a task with event time
Type `schedule 14 from 13 nov 2pm to 13 nov 4pm`.
####Result
Task with index 14 now has an event time, and it is assigned with a new index. Also, it is highlighted in purple borders.

## Delete
To delete a task, you have to start your command with the keyword `delete`.

>Here is the *format*:

>* `delete <id>...` - Deletes the task at the specified `INDEX`. Each `<id>` must be a positive number and in the most recent to-do list displayed. If there are multiple `<id>`s, they should be separated by commas, space, or input as a range.

###1. Delete a single task
Type `delete 10`.
####Result
Task with index 10 is deleted. 

###2. Delete multiple tasks
Type `delete 11 21-23`
####Result 
Tasks with indices 11, 21, 22 and 23 are deleted.

## Mark
To mark a task as completed, you have to start your command with the keyword `mark`.

>Here is the *format*:

>* `mark <id>...` - mark all the tasks identified by `<id>`(s) as completed. Each `<id>` must be a positive number and in the most recent to-do list displayed. If there are multiple `<id>`s, they should be separated by commas, space, or input as a range.

###1. Mark a single task
Type `mark 1`.
####Result
Task with index 1 is marked as completed, shifting from **Do It Soon** column to **Done** column, and the completion time is shown below the task name. Also, it is highlighted in purple borders.

###2. Mark multiple tasks
Type `mark 1 3-5`.
####Result
Tasks with indices 1, 3, 4 and 5 are marked as completed, shifting from **Do It Soon** column to **Done** column, and their completion times are shown below their task names. Also, they are all highlighted in purple borders.

## Unmark
To unmark a task as completed, you have to start your command with the keyword `unmark`.

>Here is the *format*:

>* `unmark <id>...` - unmark all the tasks identified by `<id>`(s) as uncompleted. Each `<id>` must be a positive number and in the most recent to-do list displayed.

###1. Unmark a single task
Type `mark 36`.
####Result
Task with index 36 is marked as completed, shifting from **Done** column to **Do It Soon** column, and there is no more completion time under the task name. Also, it is highlighted in purple borders. If there are multiple `<id>`s, they should be separated by commas, space, or input as a range.

###2. Unmark multiple tasks
Type `mark 37-40`.
####Result
Tasks with indices 37, 38, 39 and 40 are unmarked as uncompleted, shifting from **Done** column to **Do It Soon** column, and there are no more completion times under their task names. Also, they are all highlighted in purple borders.

## Undo
1. Type `undo`.
2. Press <kbd>Ctrl Z</kbd>

####Result
1. The previously unmarked tasks are marked again, and the affected tasks are highlighted in purple borders.
2. The previously marked task is marked again, and the affected task is highlighted in purple borders.

## Find
>Here is the *format*:

>* `find <keyword>...` - filter out all tasks containing any of the keyword(s) given. The keywords should be in full word(s), and they are case insensitive.

1. Type `find cs2103`
2. Type `find buy visit`
3. Press <kbd>ESC</kbd>

####Result
1. After the first step, tasks containing `cs2103` in their names will be listed out.
2. After the second step, tasks containing `buy` or `visit` in their names will be listed out.
3. After the third step, agendum will exit the find results and go back to list all the tasks.

## List
After you are done with searching for tasks, you can use `list` to exit your find results and see a list of tasks. Alternatively, you can press <kbd>ESC</kbd> to go exit.

1. Type `list`
2. Type `find cs2103`
3. Press <kbd>ESC</kbd>

####Result
1. After the first step, agendum will exit the find results and go back to list all the tasks.
2. After the second step, agendum will list out tasks containing `cs2103` in their names.
3. After the third step, agendum will exit the find results and go back to list all the tasks.

## Alias
To alias a command, you have to start your command with the keyword `alias`.
>Here is the *format*:

>* `alias <original command> <your command>` - `<your command>` must be a single alphanumeric word, and it cannot be an original-command or already aliased to another command. `<original command>` must be a command word that is specified in the Command Summary section

1. Type `alias schedule s`
2. Type `s 1 by 10pm`

####Result
1. After the first step, `s` is created as an alias command for `schedule`, and you can now use both `s` and `schedule` to reschedule a task.
2. After the second step, task with index 1 is rescheduled to be by today 10pm.

## Unalias
To unalias a command, you have to start your command with the keyword `unalias`.
>Here is the *format*:

>* `unalias <your command>` - `<your command>` must be a single alphanumeric word, and it cannot be a original-command or already aliased to another command.

1. Type `unalias s`
2. Type `s 1 by 11pm`
3. Type `schedule 1 by 11pm`

####Result
1. After the first step, `s` is no longer an alias command for `schedule`.
2. After the second step, the pop-up window will tell you that agendum no longer recognizes the command `s`.
3. After the third step, task with index 1 is rescheduled to be by today 11pm.

## Store
To change the default data save location, you have to start command with the keyword `store`.

>Here is the *format*:

>* `store <location>` - `<location>` must be a valid path to a file on the local computer. If the folders specified in the new file path does not exist, they will be created. Note that the save file in the old save location remains.

Type `> store src/test/data/ManualTesting/Test/newData.xml`

####Result
The storage file is stored in the specified path.

## Sync
To sync your tasks in agendum into Google calendar, you have to start command with the keyword `sync`.

>Here is the *format*:

>* `sync on` to turn syncing on
>* `sync off` to turn syncing off

1. Type `sync on`.
2. Type `sync off`.

####Result
1. After the first step, agendum will direct you to the Google Calendar authorization page in your browser. Once authorized with your Google account, there will be a pop-up window telling you that sync has been turned on, and you may close the authorization page and go back to agendum. After the authorization, all the tasks with an event time you added will sync to your Google Calendar, where a separate calendur called `Agendum Calendar` will appear for you to view all tasks from agendum.
2. After the second step, sync to Google Calendar will be turned off.






