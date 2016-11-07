# Manual Testing
This document will describe the steps to do manual testing on _Mastermind_.

## Setting Up
1. Download `Mastermind.jar` from our Github repository release page.
2. Download the `SampleData.xml` from here.
3. Create a folder call `data` in the same directory as the `Mastermind.jar`.
3. Put the `SampleData.xml` to the `data` folder.
4. Rename the `SampleData.xml` to `mastermind.xml`.
5. Double click `Mastermind.jar` or enter `java -jar mastermind.jar` in your terminal to run the application.

## Test Cases

### Request help window
1. Type `help` to display the help window.
2. The help window should display as a popup.
3. Enter any key, the help window should disappear

### Add a floating task
1. Enter `add watch doctor strange`.
2. The task will be added to home tab and tasks tab.
3. The task will be highlighted.

### Add a deadline
1. Enter `add prepare cs2103 exam by 22 november 2016 8pm`
2. The deadline should be added to home tab and deadline tab.
3. The time until duration should be displayed as tag under task name.

### Add an event
1. Enter `add attend cs2103 workshop from tomorrow 8pm to next monday 8pm.`.
2. The event should be added to home tab and event tab.
3. The event duration should be displayed as tag under task name.

### Using Previous Command
1. To use previous command press up-arrow key <kbd>↑</kbd>.
2. You should see prevous command you entered.
3. Press up-arrow key multiple time The sequence should be:
    - `add attend cs2103 workshop from tomorrow 8pm to next monday 8pm.`
    - `add prepare cs2103 exam by 22 november 2016 8pm`
    - `add watch doctor strange`
4. Cycle back to the most recent command by pressing down-arrow key <kbd>↓</kbd>.

### Add an event with tags
1. Enter `add exam period from 22 novemeber 2016 to 26 novemeber 2016 #exam,cs2103,revision`
2. The added task will display tags in bubble under "Tag" column.

### Add a floating task with escaped keywords
1. The escaped character is defined with `single quotes '`. Only the outermost single quotes are counted as wrapper.
2. Enter `add 'visit garden by the day' by this saturday 8pm`
3. The task should be added as a deadline, and the task name will appear as `visit garden by the bay`.
4. Enter `add 'watch the day after tomorrow' from this monday 7pm to next tuesdat 8pm`
5. The task should be added as event, and the task name will appear as `watch the day after tomorrow`.
6. Enter `add 'buy john's birthday present`
7. The task should be added as floating task, and the task name will appear as `buy john's birthday present`.

### Edit a task name without specifying all fields
1. Observe the current task name at index 1.
2. Enter `edit 1 name to buy sherry's present`.
3. The task name at index 1 should change to `buy sherry's present`.

### Edit a task name with multiple fields
1. Observe the current task values at index 1.
2. Enter `edit 1 name to celebrate birthday, start date to tomorrow 8pm, end date to tomorrow 9pm, tags to #birthday,cake`
3. The task values at index 1 should change to `celebrate birthday`, start date to tomorrow 8pm (relative), end date to 9pm (relative), with tags birthday and cake.

### Edit a task with multiple fields in any order
1. Observe the current task values at index 1.
2. Enter `edit 2 tags to #movie, name to watch fantastic beast`.
3. The task values at index 2 should change to `watch fantastic beast`, tags appear as movie.

### mark a task as done
1. Enter `mark 1`.
2. The task should disappear from home tab.
3. Enter `list archives`.
4. The task you just marked will be displayed under <kbd>Archives</kbd> tab.

### mark due
1. Enter `list` to switch back to home tab.
2. Enter `mark due`.
3. All the due task should disappear from home tab.
4. Enter `list archives`.
5. All due tasks should appear under <kbd>Archives</kbd> tab.
6. Enter `list` to switch back to home tab.

### Delete a task
1. Enter `delete 1`.
2. The first task should be removed.

### Undo an action
1. Enter `undo`.
2. The task you deleted in the previous test case will be recovered.

### Undo multiple times
1. Enter `undo`.
2. All the task you mark as due will be recovered.
3. Enter `undo` again.
4. The first task you marked as done in the test case "mark a task as done" will be recovered to home tab.

### Redo an action
1. Enter `redo`.
2. The first task will be marked as done again.

### Redo multiple times
1. Enter `redo`.
2. All the due tasks will be marked as done again.
3. Enter `redo`.
4. The first task will be deleted again.

### Unmark a task
1. Enter `list archives`.
2. Enter `unmark 1`
3. The task should disappear from <kbd>Archives</kbd> tab.
4. Enter `list` to switch back to home tab.
5. The task you just unmarked should appear under the list.

### Find all task by name
1. Enter `find tutorial`
2. All tasks name that contain keyword `tutorial` should appear in the list.
3. Enter `list` to reset the home tab display.

### Find all task by tag
1. Enter `findtag HACKATHON`
2. All tasks that contain tag `HACKATHON` should appear in the list.
3. Enter `list` to reset the home tab display.

### List upcoming
1. Enter `upcoming`.
2. All upcoming deadlines and events will be displayed.
3. Enter `upcoming deadlines`
4. Only upcoming deadlines are displayed.
5. Enter `upcoming events`
6. Only upcoming events are displayed.
7. Enter `list` to reset the home tab display.

### relocate save file
1. Enter `relocate /tmp` (Linux and Mac) or `C:\Desktop` (Windows).
2. Open the directory that you just entered, observe that `mastermind.xml` is relocated at that folder.

### Import .ics file
1. Download the file from [https://github.com/CS2103AUG2016-W11-C3/main/blob/master/src/test/data/ImportCommandTest/sample.ics](https://github.com/CS2103AUG2016-W11-C3/main/blob/master/src/test/data/ImportCommandTest/sample.ics).
2. Enter `import from <sample_ics_filepath>`, replace the `<sample_ics_filepath>` to the file location you downloaded. eg: `import from /home/jim/Downloads/sample.ics` or `import from C:\Users\Jim\Downloads\sample.ics`.
3. The task list should append the imported task at the bottom, with tags "ICSIMPORTED".

### Import .csv file
1. Download the file from [https://github.com/CS2103AUG2016-W11-C3/main/blob/master/src/test/data/ImportCommandTest/sample.csv](https://github.com/CS2103AUG2016-W11-C3/main/blob/master/src/test/data/ImportCommandTest/sample.csv).
2. Enter `import from <sample_ics_filepath>`, replace the `<sample_ics_filepath>` to the file location you downloaded. eg: `import from /home/jim/Downloads/sample.csv` or `import from C:\Users\Jim\Downloads\sample.csv`.
3. The task list should append the imported task at the bottom, with tags "CSVIMPORTED".

### Export .csv file
1. Enter `export to /tmp/exported-data.xml` (Linux & Mac) or `export to C:\Desktop\mastermind.xml`
2. Open up the folder and the `.xml` file you've exported, the exported data should be writtern in.
3. Enter `export deadlines events to /tmp/exported-data.xml` (Linux & Mac) or `export deadlines events to C:\Desktop\mastermind.xml` to export only events and deadlines.
4. repeat step 2 to inspect the content.

### Toggle action history panel
1. Enter `history` to toggle open the action history panel.
2. Clicking on the history entry will display the result at the right panel.
3. Enter `history` again to toggle close the action history panel.

### Add recurring task
1. Enter `add cs2103 lecture quiz from friday 4pm to friday 8pm weekly`.
2. The task should be displayed as an event, in addition a checkbox tick at the last column of the table.

### Clear all entries
1. Enter `clear`.
2. All tasks in application will be deleted.

### Exit program
1. Enter `exit`.
2. The program should be exited.
