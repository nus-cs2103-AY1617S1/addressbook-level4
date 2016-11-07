<!-- A document explaining the steps to perform manual testing (i.e. manual scripted testing), starting with how to load the sample data.
Should cover all functionality of the product.
Should specify the command to type and the expected result (screenshots not required).
Limit the test cases to about 20 minutes worth of testing. -->

## Loading the sample data
1. Run [T10-C2][Amethyst].jar by double-clicking it in your preferred location, eg. C:\Users\Abeysinghe\Desktop\Amethyst
2. Close the program.
3. Copy SampleData.xml to the `\data` folder created when Amethyst was run, eg. C:\Users\Abeysinghe\Desktop\Amethyst\data
4. Delete taskmanager.xml.
5. Rename SampleData.xml to taskmanager.xml.
6. Run Amethyst.

## Manual testing
> Notes:
- All failed executions will result in an error message.
- See the user guide for a command summary.

### Command history
With the command box selected, you can press the up and down arrow keys to cycle through the history of the commands entered.

<br>
### Adding a task
You can add 3 types of tasks: 
- somedays, which have no relevant date,
- deadlines, which have a due by date, and
- events, which have a start and end time
<br>
#### Adding somedays
**Successful executions**
<br> 1. `add 'This is a someday task'` - adds a task
<br> 2. `add 'Read Tintin: Book of Dragons' #bookshelf` - adds a task with a tag
<br> 3. `add #bookshelf #favourite 'Read Asterix the Gaul'` - adds a task with 2 tags

> Try using the command history feature now.

**Failed executions**
<br> 1. `add 'This is a someday task'` - task already in task manager
<br> 2. `add No quotes around name`
<br> 3. `add 'Quote in name '<-- '`
<br> 4. `add #noname`
<br> 5. `add #tag with space`


#### Adding deadlines
**Successful executions**
<br> 1. `add 'This is a deadline task' by 17:00 25-12-16` - adds a task
<br> 2. `add by 5:00pm 2016/12/5 'Finish grading papers' #anyorderworks` - adds a task
<br> 3. `add 'Complete super hard lab' #anyorderworks by 5pm 25-dec-16` - adds a task
<br> 4. `add 'Finish oxbow lake geography report' by 25/Dec-16 5am #flexibledateformat` - adds a task
<br> 5. `add 'Make Halloween costume' by 1800 today #naturallanguage` - adds a task

**Failed executions**
<br> 1. `add 'No by keyword' 25-12-16 2pm`
<br> 2. `add 5pm 'date and time seperated' by 25/12/2016`


#### Adding events
**Successful executions**
<br> 1. `add 'fill up ceg survey' from 8:00 12-5-13 to 10:00 13-5-13 #differentdays` - adds a task
<br> 2. `add from 8:00 to 10:00 'test different orders' on 12-10-12 #useonkeyword` - adds a task
<br> 3. `add 'party time!' from 8:00 to 9:00 #datetodayinferred #sameday` - adds a task
<br> 4. `add on thursday from 8:00 to 9:00 'attend trivia night' #flexibleformat #useonkeyword` - adds a task

**Failed executions**
<br> 1. `add 'end date before start date' from 8:00 to 6:00 on wednesday`
<br> 2. `add 'invalid date' from 8:00 to 9:00 on 29-2-17` - invalid date (Note: 29-2-16 is allowed since 2016 is a leap year)
<br> 3. `add 'another invalid date' from 8:00 to 9:00 on 30-15-17` - invalid month
<br> 4. `add 'yet another invalid date' from 8:00 to 27:00 on 30-12-17` - invalid hour

<br>
### Mark status of task
**Successful executions**
<br> 1. `done 1` - 1st task marked done disappears from view
<br> 2. `list all`,  `pending [LAST_TASK_INDEX]` - removes done label from task. (Note: replace `[LAST_TASK_INDEX]` with index of the last task displayed after `list all` is called)
<br> 3. `done 8 4 15` - marks 8th, 4th and 15th tasks as done; they disappear from view

**Failed executions**
<br> 1. `done 0`
<br> 2. `done 300`
<br> 3. `pending -2`
<br> 4. `pending invalid-argument`

<br>
### Listing tasks
**Successful executions**
<br> 1. `list` - lists all tasks except done tasks
<br> 2. `list all` - lists all tasks including done tasks
<br> 3. `list done` - lists done tasks
<br> 4. `list pending` - lists pending tasks
<br> 5. `list today` - lists tasks that have start or end time today
<br> 6. `list 25-12-16` - lists tasks that have start or end time on 25/12/16
<br> 7. `list event` - lists event tasks
<br> 8. `list ev` - same result as `list events`

**Failed executions**
<br> 1. `list invalid-argument`

<br>
### Finding tasks
**Successful executions**
<br> Find command lists all tasks with the keyphrases in the title or tags
<br> 1. `find report`
<br> 2. `find na`
<br> 3. `find urgent`
<br> 4. `find lab, report`

**Failed executions**
<br> 1. `find`

<br>
### Deleting tasks
Call `list` once, before the following commands.

**Successful executions**
<br> 1. `del 1` - deletes 1st task
<br> 2. `del 1 2` - deletes 1st, 2nd tasks
<br> 3. `del 8 4 15` - deletes 8th, 4th, 15th tasks

**Failed executions**
<br> 1. `del 0`
<br> 2. `del 300`
<br> 3. `del -2`
<br> 4. `del invalid-argument`

<br>
### Edit a task
Choose the index of an event task to test the following commands. These examples assume the index to be 1.

**Successful executions**
<br> 1. `edit 1 'Changed name'` - changes name of first task
<br> 2. `edit 1 from 1534 20/11/11` - changes start time of first task
<br> 3. `edit 1 from 1200 20/11/16 to 1200 21/11/16` - changes start and end time of first task
<br> 4. `edit 1 to 20/11/16 13:00 'Changed name again'` - changes end time and name of first task
<br> 5. `edit 1 done #gladitsover` - adds a tag
<br> 6. `edit 1 from 4pm` - changes start date to today
<br> 6. `edit 1 from -` - remove start time. Task becomes deadline with due by time equal to end time
<br> 7. `undo`, `edit 1 to -` - start time becomes due by time of deadline

**Failed executions**
<br> 1. `edit 1 to 11am 30-11-16`- start time after end time error


<br>
### Undo and redo
**Successful executions**
<br> 1. `undo` - undoes last action
<br> 2. `undo` again
<br> 3. `del 1`, `undo`
<br> 4. `redo`
<br> 5. `undo`

**Failed executions**
<br> Follow each step in sequence.

<br> 1. Restart program, then enter `undo`
<br> 2. `redo`
<br> 3. `list done`, `undo` - no changes to underlying task list
<br> 4. `del 2`, `redo` - cannot redo if nothing was undone

<br>
### Clear
**Successful executions**
<br> 1. `clear` - clears all tasks. (Note: enter `undo` after verifying to repopulate task manager for further testing)

<br>
### Set storage location
**Successful executions**
Replace the file path below with a path pointing to an existing folder.

1. `set-storage C:\Users\Abeysinghe\Desktop save-as newfilename` - after restarting the program, data is copied to the new location

**Failed executions**
Replace the file path below with a path pointing to a _non-existing_ folder.

1. `set-storage C:\Users\Abeysinghe\Desktop save-as newfilename`

<br>
### Add alias
**Successful executions**
<br> 1. `add-alias ld=list done` - adds an alternative comand for `list done`. Enter `ld` to see a list of all done tasks
<br> 2. `add 'ld'` - adds a task called 'ld' and not 'list done'

<br>
### List all aliases
**Successful executions**
<br> 1. `list-alias` - list all aliases

<br>
### Delete command aliases
**Successful executions**
<br> 1. `list-alias`, `del-alias 1` - deletes first alias

<br>
### Tab
Switches the right pane tab.

**Successful executions**
<br> 1. `tab today`
<br> 2. `tab tomorrow`
<br> 3. `tab week`
<br> 4. `tab month`
<br> 5. `tab someday`
<br> 6. `tab tmr`
<br> 7. `tab sd`

**Failed executions**
<br> 1. `tab`
<br> 2. `tab invalid-argument`

<br>
### Help
This command requires an internet connection.

**Successful executions**
<br> 1. `help` - shows the help window in a popup

<br>
### Exit
**Successful executions**
<br> 1. `exit` - exits the application