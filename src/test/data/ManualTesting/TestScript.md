# Manual Test Script
This document will provide the steps to perform manual testing.

## Loading of sample data
You can find the sample data at `SampleData.xml` in the same folder as this document.
To load sample data, follow the steps below:
1. Download dodobird.jar from our release.
2. Place dodobird.jar at a directory of your preference.
3. Run dodobird.jar at that directory
4. Exit dodobird
5. Delete the data file at the subdirectory `./data/dodobird.xml`
6. Copy SampleData.xml to the `./data/` subdirectory and rename it `dodobird.xml`
7. Rerun dodobird.jar
8. You now have the sample data loaded.

## Add command
Command | Expected Results |
------- | :--------------
add floating task| A floating task with name `floating task` is added to top of the list.
add task1 on today | A task with name `task1` and starting date `today` is added to the top of the list. <br> The default time is `00:00 hrs` <br> The task should appear at the Today panel on the right.
add task2 on today 1800 by tomorrow 1900 | A task with name `task2`, starting date `today` and ending date `tomorrow` is added to the top of the list. <br> The starting time is `18:00 hrs` and the ending time is `19:00 hrs`<br> `task2` should appear at the Today panel and the Next 7 Days panel on the right.
add task3 on 12/25/2017 1800 by 12/26/2017 1900 priority high every week| A task with name `task3`, starting date `12/25/2017` and ending date `12/26/2017` is added to the top of the list. <br> The starting time is `18:00 hrs` and the ending time is `19:00 hrs`<br> The priority circle should be red. <br> The recurrence label should display `Every: WEEK` <br>

## Mark command
Command | Expected Results |
------- | :--------------
mark 1| `task3` is mark as completed <br> The new starting date and ending date should be reflected as one week later.
mark 2 | `task2` will be mark as completed <br> This is also reflected in the Today panel with `task2` being strike out.

## Unmark command
Command | Expected Results |
------- | :--------------
search done <br> unmark 1| `task2` at the very top of the list of completed tasks is mark as not completed <br> The GUI should then display all the tasks that are not completed with `task2` back.

## Delete command
Command | Expected Results |
------- | :--------------
delete 1| `task3` at the very top of the list is deleted

## Tag command
Command | Expected Results |
------- | :--------------
tag 1 school| `task2` is tagged with `school` <br> A new tag card with name `school` will appear in the tag panel with the count of 1
tag 2 personal family| `task1` is tagged with `personal` `family` <br> Two new tag cards with names `personal` and `family` will appear in the tag panel with the count of 1.
tag 3 school| `floating task` is tagged with `school` <br> The tag card with name `school` will have its count increased to 2.

## Tag Panel Click
Command | Expected Results |
------- | :--------------
Click on `school` tag on tag panel| All the tasks that are tag with `school` should be displayed.

## Untag command
Command | Expected Results |
------- | :--------------
search all <br> untag 1 school | `task2` will have tag `school` removed <br> The tag card with name `school` in the tag panel will have its count decrease to 1.
untag 2 personal family| `task 1` will have its  `personal` and `family` tags removed. <br> The two tag cards with names `personal` and `family` will disappear in the tag panel.


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
update 3 read a story book| `floating task` will have its name changed to `read a story book`
update 2 on tmr by 3 days later | `task1` will have its starting date updated to `tomorrow` and its ending date updated to `3 days later` from the present date <br> The default time is `00:00 hrs` <br> `task1` will move from Today panel to the Next 7 Days panel.
update 1 priority mid | `task2` will have its priority updated to mid.  <br> The priority color of `task2` should turn to yellow.<br> The color change should also be reflected in the Today panel.
update 3 christmas party at school on 12/25/2016 1800 by 12/26/2016 1900 priority high every day| `task3` will update starting date to `12/25/2016` and ending date to `12/26/2016` <br> The starting time is `18:00 hrs` and the ending time is `19:00 hrs`<br> The priority circle should be red. <br> The recurrence label should display `Every: DAY` <br>
update 3 every - | `task3` should have its recurrence removed.
update 2 on - | `task1` should have its starting date removed.

## Search command
Command | Expected Results |
------- | :--------------
search | GUI should display all uncompleted tasks should appear.
search all | GUI should display all tasks.
search done | GUI should display all completed tasks.
search on 12/25/2016 | GUI should display all tasks with starting date 12/25/2016
search before 12/25/2016 | GUI should display all tasks that either start or end before and up till 12/25/2016.
search after today | GUI should display all tasks that either start or end from today.
search from today to 4 days later | GUI should display all tasks that falls in between today and 4 days later (inclusive)
search priority high | GUI should display all tasks with priority high
search tag school | GUI should display all tasks with `school` tag
search assignment | GUI should display all task with name or details that include the word assignment.
