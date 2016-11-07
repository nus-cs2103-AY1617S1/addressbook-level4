###Test Script (Manual Testing)


####Loading the sample data
1. Create a data folder in the same directory as Task!t.jar.
2. Place SampleData.xml inside the data folder.
3. Rename SampleData.xml to todolist.xml
4. Double click Task!t.jar. The data should be loaded.


####Open help window
1 Press F1 or type `help` in the command box.
```
Result: A new window will open up and you will be directed to the user guide.
```
####Adding a new task
#####Adding an event type task
1 Type `add watch movie from friday 7pm to friday 9pm at NEX remarks buy popcorn`
```
Result: A new task will be added to the incomplete tab.
> Date and time will be displayed at the right hand side with an image of an hourglass.
> Location and remarks will be displayed below the task name with a location marker icon and speech icon respectively.
```
#####Adding a deadline type task
1. Type `add buy phone cover by 9 Dec 2016 remarks book online`
```
Result: A new task will be added to the incomplete tab.
> The tasks will be sorted by date time.
> Time will be displayed as '9 Dec 2016' at the right hand side with with an image of an alarm clock.
> Remarks will be displayed below the task name with a speech icon.
```
#####Adding a float type task
1. Type `add buy milk`
```
Result: A new task will be added to the incomplete tab.
> Float type task will be displayed after event and deadline type task.
```


####Finding a task
#####Find task that contains either keywords
1. Type `find either football dinner`
```
Result: 7 tasks displayed! 4 tasks in incomplete tab, 3 tasks in completed tab, 0 tasks in overdue tab.
```
#####Find task that contains all keywords
1. Type `find all submit lab`
```
Result: 1 tasks displayed! 0 tasks in incomplete tab, 0 tasks in completed tab, 1 tasks in overdue tab.
```
#####Find task that fits exactly a phrase
1. Type `find phrase christmas present`
```
Result: 1 tasks displayed! 1 tasks in incomplete tab, 0 tasks in completed tab, 0 tasks in overdue tab.
```


####Listing the tasks
#####Listing today's task
1. Type `list today`
```
Result: Shows all tasks with today's date.
```
#####Listing this week's task
1. Type `list week`
```
Result: Shows all tasks for the current week, from Sunday to Saturday.
```
#####Listing this month's task
1. Type `list month`
```
Result: Shows all tasks for the current month.
```
#####Listing tasks on a specific date
1. Type `list 1 Dec 2016`
```
Result: 1 tasks displayed! 1 tasks in incomplete tab, 0 tasks in completed tab, 0 tasks in overdue tab..
```
#####Listing all task
1. Type `list`
```
Result: All tasks will be listed.
```


####Editing a task
#####Editing a task name
1. Type `edit 2 cook dinner with friends`
```
Result: The task name for index 2 will change to 'cook dinner with friends'. Other details remain unchanged.
```
#####Editing a task date
1. Type `edit 2 from 10 Nov 2016 5pm to 10 Nov 2016 6pm`
```
Result: The task date for index 2 will change to '9 Nov 2016 5pm to 9 Nov 2016 6pm'. Other details remain unchanged.
```
#####Editing a task location
1. Type `edit 3 at Stadium`
```
Result: A location will be changed for task in index 3 to Stadium. Other details remain unchanged.
```
#####Editing a task remark
1. Type `edit 2 remarks buy rice`
```
Result: The remarks for the task at index 2 will be added. Other details remain unchanged.
```


####Mark a task as completed
#####Marking a single task
1. Type `done 1`
```
Result: Incomplete tab will display 12 tasks. Click completed tab, it will display 43 tasks. The task should be at index 43.
```
2. Stay in complete tab and type `done 1`
```
Result: The command box will display an error message 'This task is already completed!'.
```
#####Marking multiple tasks
1. Click incomplete tab and type `done 1,2`
```
Result: Incomplete tab will display 10 tasks. Click completed tab, it will display 45 tasks. The tasks should be at index 44, 45.
```


####Undo an action
1. Type `undo`
```
Result: Your previous action will be undone. In this case, your tasks 'cook dinner with friends' and 'play football' will return to the incomplete tab.
```
2. Type `undo` again
```
Result: Your previous action will be undone again. In this case, your task 'football competition' will return to the incomplete tab.
```

####Redoing a task
1. Type `redo`
```
Result: Your previous undo will be redo-ed. In this case, your task 'football competition' will return to the complete tab

```
2. Type `redo` again
```
Result: Your previous undo will be redone. In this case, your tasks 'cook dinner with friends' and 'football competition' will return to the complete tab.
```

####Deleting a task
#####Deleting a single task
1. Type `delete 1`
```
Result: 'watch movie' will be deleted
```
#####Deleting multiple tasks
1. Type `delete 1,2`
```
Result: 'attending Jimmy 21st birthday celebration' and 'submit module feedback' will be deleted.
```

####Clear
#####Delete all data from every tab
1. Type `clear`
```
Result: Data in all tabs should be deleted
```

####Exit
#####Exit the application
1. Type `exit`
```
Result: Application should close.
```
