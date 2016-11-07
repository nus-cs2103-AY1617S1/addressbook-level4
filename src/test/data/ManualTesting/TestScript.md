###Test Script (Manual Testing)


####Loading the sample data
1. Create a data folder in the same directory as Task!t.jar.
2. Place SampleData.xml inside the data folder.
3. Double click Task!t.jar. The data should be loaded.


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
1. Type `find either phone milk`
```
Result: Only 2 tasks 'buy phone cover' and 'buy milk' will be listed.
```
#####Find task that contains all keywords
1. Type `find all buy phone`
```
Result: Only 1 task 'buy phone cover' will be listed.
```
#####Find task that fits exactly a phrase
1. Type `find phrase watch movie`
```
Result: Only 1 task 'watch movie' will be listed.
```


####Listing the tasks
#####Listing today's task
1. Type `list today`
```
Result: 0 task should be listed. Unless you are testing on 9 Dec 2016.
```
#####Listing this week's task
1. Type `list week`
```
Result: 1 task 'watch movie' will be listed. Unless you are testing on the week of 9 Dec 2016, which in that case will be 2 tasks.
```
#####Listing this month's task
1. Type `list month`
```
Result: 1 task 'watch movie' will be listed. Unless you are testing in Dec 2016, which in that case will be 2 tasks.
```
#####Listing tasks on a specific date
1. Type `list 9 Dec 2016`
```
Result: 1 task 'buy phone cover' will be listed.
```
#####Listing all task
1. Type `list`
```
Result: 3 tasks 'watch movie', 'buy phone cover' and 'buy milk' will be listed.
```


####Editing a task
#####Editing a task name
1. Type `edit 2 buy phone casing`
```
Result: The task name for index 2 will change to 'buy phone casing'. Other details remain unchanged.
```
#####Editing a task date
1. Type `edit 2 by 10 Dec 2016`
```
Result: The task date for index 2 will change to '10 Dec 2016'. Other details remain unchanged.
```
#####Editing a task location
1. Type `edit 3 at NTUC`
```
Result: A location will be added for task in index 3. Other details remain unchanged.
```
2. Type `edit 3 at Cold storage`
```
Result: The location for the task at index 3 will be changed to 'Cold storage'. Other details remain unchanged.
```
#####Editing a task remark
1. Type `edit 2 remarks buy from carousel`
```
Result: The remarks for the task at index 2 will be changed to 'buy from carousel'. Other details remain unchanged.
```


####Mark a task as completed
#####Marking a single task
1. Type `done 1`
```
Result: Incomplete tab will display 2 tasks 'buy phone casing' and 'buy milk'. Click completed tab, it will display 1 task 'watch movie'.
```
2. Stay in complete tab and type `done 1`
```
Result: The command box will display an error message 'This task is already completed!'.
```
#####Marking multiple tasks
1. Click incomplete tab and type `done 1,2`
```
Result: Incomplete tab will be empty. Click completed tab, it will display 3 tasks.
```


####Undo an action
1. Type `undo`
```
Result: Incomplete tab will display 2 tasks 'buy phone casing' and 'buy milk'. Click completed tab, it will display 1 task 'watch movie'.
```
2. Type `undo` again
```
Result: Incomplete tab will display 3 tasks. Click completed tab, it will be empty.
```


####Deleting a task
#####Deleting a single task
1. Type `delete 1`
```
Result: Incomplete tab will display 2 tasks 'buy phone casing' and 'buy milk'.
```
#####Deleting multiple tasks
1. Type `done 1,2`
```
Result: Incomplete tab will be empty. Click completed tab, it will display 3 tasks.
```
