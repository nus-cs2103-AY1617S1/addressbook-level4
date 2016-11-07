1. Loading Sample Data

1.1 Overwriting default data file with Sample Data:
1.1.1 Rename the SampleData.xml to jimi.xml (Copy and rename if original copy is desired)
1.1.2 Navigate to where Jimi.jar executable is.
1.1.3 Create a folder named `data` if not created already.
1.1.4 Copy the renamed jimi.xml into the data folder.
1.1.5 Run Jimi.jar; All the sample tasks and events should be loaded.

2. Add Command

2.1 Adding a Floating Task:
2.1.1 Type into the command box: add "tour Americas"
2.1.2 Task should be added to agenda task panel in main window; Added task details should be shown in result display window; Task should be highlighted.

2.2 Adding a Deadline Task:
2.2.1 Type into the command box: add "finish project" due next tuesday
2.2.2 Task should be added to agenda task panel in main window; Added task details should be shown in result display window; Task should be highlighted.

2.3 Adding Events:
2.3.1 Type into the command box: add "visit grandma" from next monday to next tuesday
2.3.2 Event should be added to the agenda event panel in main window; Event should be highlighted;  Added event details should be shown in result display window;
2.3.3 Type into the command box: add "attend seminar" on today 4pm
2.3.4 Event should be added to the agenda event panel in main window; Event shoudld be highlighted;  Added event details should be shown in result display window; End date of event should be today 23:59.

3. Delete Command

3.1 Deleting a single task:
3.1.1 Type into the command box: delete t1
3.1.2 Task should be deleted from task panel in main window; Result message in result display window should show details of deleted task;

3.2 Deleting a range of tasks:
3.2.1 Type into the command box: delete t1 to t5
3.2.2 Tasks should be deleted from task panel in main window; Result message in result display window should show details of all deleted tasks;

3.1 Deleting a single event:
3.1.1 Type into the command box: delete e1
3.1.2 Event should be deleted from event panel in main window; Result message in result display window should show details of deleted event;

3.2 Deleting a range of events:
3.2.1 Type into the command box: delete e1 to e5
3.2.2 Events should be deleted from task panel in main window; Result message in result display window should show details of all deleted events;

4. Edit Command

5. Complete Command

5.1 Completing a task:
5.1.1 Type into the command box: complete t1
5.1.2 Task should be completed; All completed tasks should be shown to user; Result display window should show completed task details;

6. Show Command

6.1 Showing all tasks and events:
6.1.1 Type into the command box: show all
6.1.2 All events and tasks should be displayed in the main window; 

6.2 Showing overdue tasks:
6.2.1 Type into the command box: show overdue
6.2.3 All overdue tasks should be displayed in the main window; Overdue tasks in summary panel should be expanded;

6.3 Showing incomplete tasks:
6.3.1 Type into the command box: show incomplete
6.3.2 All incomplete tasks should be displayed in the main window; Incomplete tasks in summary panel should be expanded;

6.4 Showing completed tasks:
6.4.1 Type into the command box: show completed
6.4.2 All completed tasks should be displayed in the main window; Completed tasks in summary panel should be expanded;

6.5 Showing day of week tasks/events:
6.5.1 Type into the command box: show today
6.5.2 All tasks and events for today should be displayed to user; Today list in summary panel should be expanded;
6.5.3 Type into the command box: show tomorrow
6.5.4 All tasks and events for tomorrow should be displayed to user; Tomorrow list in summary panel should be expanded;
6.5.5 Type into the command box: show saturday
6.5.6 All tasks and events for saturday should be displayed to user; Saturday list in summary panel should be expanded;
6.5.7 Type into the command box: show sunday
6.5.8 All tasks and events for sunday should be displayed to user; Sunday list in summary panel should be expanded;
6.5.9 Type into the command box: show monday
6.5.10 All tasks and events for monday should be displayed to user; Monday list in summary panel should be expanded;
6.5.11 Type into the command box: show tuesday
6.5.12 All tasks and events for tuesday should be displayed to user; Tuesday list in summary panel should be expanded;
6.5.13 Type into the command box: show wednesday
6.5.14 All tasks and events for wednesday should be displayed to user; Wednesday list in summary panel should be expanded;

7. Find Command

8. Clear Command

8.1 Clear all tasks and events:
8.1.1 Type into the command box: clear
8.1.2 All tasks and events should be removed from main window; Result display should show all tasks/events cleared message;

9. Undo Command

9.1 Undo-ing previous action (clear):
9.1.1 Type into the command box: undo
9.1.2 All cleared tasks and events should be returned to the main window; Result display should display undo successful message;

10. Redo Command

10.1 Redo-ing previous action (clear):
10.1.1 Type into the command box: redo
10.1.2 All tasks and events should be cleared from main window; Result display should display redo successful message;
10.1.3 Type into the command box: redo
10.1.4 Result window should display no-actions to be re-done message;

11. Saveas Command

11.1 Setting new save path location:
11.1.1 Type into the command box: saveas "dropbox/jimi.xml"
11.1.2 Result window should display successful message with new path location; Status bar footer should reflect new data save location;

11.2 Resetting save path location:
11.2.1 Type into the command box: saveas reset
11.2.2 Result window should display successful message with default path location; Status bar footer should reflect default data save location;

12. Help Command

12.1 Opening help window:
12.1.1 Type into the command box: help
12.1.2 Help window should open up local copy of user guide.

12.2 Showing help for specific command:
12.2.1 Type into the command box: help COMMAND eg. help add
12.2.2 Result window should display command format and usage instructions for that command.

13. Exit Command

13.1 Type into the command box: exit
13.2 Jimi should close.