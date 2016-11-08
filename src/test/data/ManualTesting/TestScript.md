//@@author A0121657H

# Test Script
* [Getting Started](#getting-started)
* [Command Summary](#command-summary)

## Getting Started
1. Ensure you have Java version 1.8.0_60 or later installed in your Computer.
2. Download the latest OneLine.jar from the releases tab.
3. Copy the file to the folder you want to use as the home folder for OneLine.
4. Double-click the file to start the app. The Graphical User Interface (GUI) should appear in a few seconds.


## Step-by-step Guide

1\. In the same directory as the `[W09-C1][OneLine].jar` file, create a folder (if not already present) titled 'data'. Transfer the SampleData.xml file into the 'data' folder, and rename it to `taskbook.xml`.

2\. Launch `[W09-C1][OneLine].jar`.

3\. Enter `help`. Ensure that you can see a help menu that displays all possible commands. Close the help window.

4a. The first set of features to be covered are the add features. Enter `add Get Groceries`, an example of a floating task. Check that a task with the given title 'Get Groceries' appears on the task pane.

4b. For tasks with deadlines, enter `add User Guide .due Wednesday`. Check that it appears in the task pane, that the date is indeed Wednesday and that the time is set to 2359h.

4c. `add Meeting with boss .from 2pm .to 4pm`. Check that it appears in the task pane, the day is the current day, or the next day if the set time has passed.

4d. `add Developer Guide .due Wednesday #Project`. Check that a tag marked as documentation appears on the category pane on the left and the task appears in the task pane.

5a. The next few features will cover the edit features. Key in `edit 14 Get Groceries from Cold Storage`.

5b. To edit the task deadline, type `edit 14 .due Wednesday `. The task from 5a will be updated with the appropriate timing in the task pane.

5c. To remove the deadline, simply leave the field blank, eg. `edit 46 .due`. The get groceries task will return to its original position at task 14.

5d. To edit a particular task's category, key in `edit 47 #Project`. The task at index 47 will now be tagged under #Project.

5e. To edit a category name, enter `edit #Project #Software`. The Project category on the left as well as the tags by the tasks in the right task pane will be renamed.

5f. To edit a colour's category, enter `edit #Software yellow`. Other available colours: red, orange, yellow, green, blue, purple, pink, grey. The category in the pane should have its colour updated and all tasks associated with the tag in the task pane should be updated. 

6a. Next, the list feature will be covered. `list` allows you to list all current undone tasks. 

6b. `list float` to switch the task pane to the "float" view. This displays all floating tasks.
    
6c. `list today` switches the task pane to the "daily" view. This displays all tasks due today.

6d. `list week` switches the task pane to the "weekly" view. This displays all tasks due this week sorted by due date.

6e. `list done` displays all tasks that have been completed.

6f. `list #Software` displays all tasks tagged with the specified category "Software".

6g. Key in `list` to return to the original view of all uncompleted tasks.  

7\. Enter `done 7` to mark the check email task as done. Key in `list done` and verify that the check email task now appears in the task pane.

8\. Still in the list of tasks that are done, key in `undone 2` to mark the check email task as not done. Key in `list` again and verify that it is listed at index 7.

9\. `find Birthday` displays all tasks with the word 'Birthday' in the task name.  
    
10a. Key in `list` again to return to the original view. Now enter `del 1` to delete the task spcified by its index in the task pane. Check that the Birthday celebration at index 1 is now deleted.
    
10b. Key in `del #Software` to delete the category. Only the tag will be deleted, the tasks tagged under Software will now be uncategorised. Check that none of the tasks in the task pane are categorised under Software.

11\. Key in `undo` twice to undo the previous two delete acts. Check that the category Software is now back, and so is the Birthday celebration.

12\. Key in `redo` to delete the Birthday celebration again.

13a. Key in `loc` to show the current storage location. It should be `(directory of the jar file)\data\taskbook.xml` by default.

13b. Create a new folder called newData in the same directory as [W09-C1][OneLine].jar. Enter `loc (directory of the jar file)\newData` to change storage file to the newData folder. Navigate to the folder and open taskbook.xml to confirm that data has been correctly written to the new file path.

14\. Key in `exit` to close the application.

## Command Summary
| Command | Format |
| ------- | ------ |
| help | `help` |
| add | `add <name> [.due <date>] [#<cat>] [#<cat>] ...` <br /> `add <name> [.from <date><time> .to <date><time>] ...` <br /> `add <name> .every <period> ...` |
| edit | `edit <index> [.due <date>]`<br />`edit #<oldCat> [#<newCat>] [.c <colour>]` |
| list | `list` <br /> `list today` <br /> `list week` <br /> `list float`<br />`list #<cat>`<br />`list done` |
| done | `done <index>` |
| find | `find <keyword>` |
| delete | `del <index>` <br /> `del #<cat>`|
| storage | `loc <path>` |
| exit | `exit` |
