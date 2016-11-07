# Manual Test Script
This document will provide the steps to perform manual testing.

## Loading of sample data
You can find the sample data at `SampleData.xml` in the same folder as this document.
To load sample data, follow the steps below: <br>
1. Download dodobird.jar from our release <br>
2. Place dodobird.jar at a directory of your preference <br>
3. Create a subdirectory with name `data` <br>
4. Copy SampleData.xml to the `./data/` subdirectory and rename it `dodobird.xml` <br>
5. Run dodobird.jar <br>
6. You now have the sample data loaded <br>

## Add command
Command | Expected Results |
------- | :--------------
add floating task| A floating task with name `floating task` is added to top of the list.
add task1 on today | A task with name `task1` and starting date `today` is added to the top of the list. <br> The default time is `00:00 hrs` <br> The task should appear at the Today panel on the right.
add task2 on today 1800 by tomorrow 1900 | A task with name `task2`, starting date `today` and ending date `tomorrow` is added to the top of the list. <br> The starting time is `18:00 hrs` and the ending time is `19:00 hrs`<br> `task2` should appear at the Today panel and the Next 7 Days panel on the right.
add task3 on 25 dec 2015 1800 by 26 dec 2015 1900 priority mid | A task with name `task3`, starting date `12/25/2015` and ending date `12/26/2015` is added to the top of the list. <br> The starting time is `18:00 hrs` and the ending time is `19:00 hrs`<br> The priority circle should be yellow. <br> All fields of the task, including the task name, starting date and ending date should be red to indicate overdue.
add task4 on 12/25/2016 1800 by 12/26/2016 1900 priority high every week ; christmas dinner| A task with name `task4`, starting date `12/25/2016` and ending date `12/26/2016` is added to the top of the list. <br> The starting time is `18:00 hrs` and the ending time is `19:00 hrs`<br> The priority circle should be red. <br> The recurrence label should display `Every: WEEK` <br> A line of details under the task name should display `christmas dinner`

## Mark command
Command | Expected Results |
------- | :--------------
mark 1| `task4` is mark as completed <br> The new starting date and ending date should be reflected as one week later.
mark 3 | `task2` will be mark as completed <br> `task2` will be hidden from the current list <br> This is also reflected in the Today panel with `task2` being strike out.

## Unmark command
Command | Expected Results |
------- | :--------------
search done <br> | The GUI should update showing all completed tasks
unmark 1| `task2` at the very top of the list of completed tasks is mark as not completed <br> The GUI should update with `task2` hidden from the list of completed tasks

## Delete command
Command | Expected Results |
------- | :--------------
search <br> | The GUI should update showing all uncompleted tasks
delete 1| `task4` at the very top of the list is deleted

## Tag command
Command | Expected Results |
------- | :--------------
tag 1 school| `task3` is tagged with `school` <br> A new tag card with name `school` will appear in the tag panel with the count of 14
tag 2 personal family| `task2` is tagged with `personal` `family` <br> Two new tag cards with names `personal` and `family` will appear in the tag panel with the count of 11 and 2 respectively.
tag 3 school| `task1` is tagged with `school` <br> The tag card with name `school` will have its count increased to 15.

## Tag Panel Click
Command | Expected Results |
------- | :--------------
Click on `school` tag on tag panel| 15 tasks that are tag with `school` should be displayed.

## Untag command
Command | Expected Results |
------- | :--------------
search |  The GUI should update showing all uncompleted tasks
untag 1 school | `task3` will have tag `school` removed <br> The tag card with name `school` in the tag panel will have its count decrease to 14.
untag 2 personal family| `task 2` will have its  `personal` and `family` tags removed. <br> The two tag cards with names `personal` and `family` will decrease their counts to 10 and 1 respectively.


## Clear command
Command | Expected Results |
------- | :--------------
clear | All the tasks in the list will be cleared


## Undo command
Command | Expected Results |
------- | :--------------
undo | The previous action will be undone. All the tasks will return.

## Update command
Command | Expected Results |
------- | :--------------
update 4 read a story book| `floating task` will have its name changed to `read a story book`
update 3 on tmr by 3 days later | `task1` will have its starting date updated to `tomorrow` and its ending date updated to `3 days later` from the present date <br> The default starting time is `00:00 hrs` and the default ending time is `23:59 hrs` <br> `task1` will move from Today panel to the Next 7 Days panel.
update 2 priority mid | `task2` will have its priority updated to mid.  <br> The priority color of `task2` should turn to yellow.<br> The color change should also be reflected in the Today and Next 7 Days panels.
update 3 christmas party at school on 12/25/2016 1800 by 12/26/2016 1900 priority high every day| `task1` will now have its name change to `christmas party at school` <br> The starting date will update to `12/25/2016` and ending date will update to `12/26/2016` <br> The starting time is `18:00 hrs` and the ending time is `19:00 hrs`<br> The priority circle should be red. <br> The recurrence label should display `Every: DAY` <br>
update 3 every - | `christmas party at school` should have its recurrence removed.
update 2 on - | `task2` should have its starting date removed.

## Search command
Command | Expected Results |
------- | :--------------
search | GUI should display all uncompleted tasks should appear.
search all | GUI should display all tasks.
search done | GUI should display all completed tasks.
search on 12/25/2016 | GUI should display all tasks with starting date 12/25/2016
search before 12/25/2016 | GUI should display all tasks that either start or end before and up till 12/25/2016 23:59.
search after today | GUI should display all tasks that either start or end from today 00:00.
search from today to 4 days later | GUI should display all tasks that falls in between today and 4 days later (inclusive)
search priority high | GUI should display all tasks with priority high
search tag school | GUI should display all tasks with `school` tag
search assignment | GUI should display all tasks with name or details that include the word assignment.

## Today Panel Click
Command | Expected Results |
------- | :--------------
Click on `Today` label on Today panel| GUI should display all tasks that should be done today.

## Next 7 Days Panel Click
Command | Expected Results |
------- | :--------------
Click on `Next 7 Days` label on Next 7 Days panel| GUI should display all tasks that should be done in the next 7 days.

## Store Command
Command | Expected Results |
------- | :--------------
store `..filepath`| GUI should update with new store location at the status bar. <br> A new `..filepath/dodobird.xml` will be created


## Reset Command
Command | Expected Results |
------- | :--------------
reset | GUI should update with new default location `.data/dodobird.xml` at the status bar.

## Help Command
Command | Expected Results |
------- | :--------------
help | Help window should appear


## Troubleshooting
* Unable to open jar file on Mac:
    * This is most probably due to permission issue on your OS. Move the jar file to a directory where you have root or admin permission.
