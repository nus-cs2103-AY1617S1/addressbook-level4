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
<br> 1. `add 'This is a someday task'`
<br> 2. `add 'Read Tintin: Book of Dragons' #bookshelf`
<br> 3. `add #bookshelf 'Read Asterix the Gaul'`

> Try using the command history feature now.

**Failed executions**
<br> 1. `add 'This is a someday task'` - Task already in task manager
<br> 2. `add No quotes around name`
<br> 3. `add 'Quote in name '<-- '`
<br> 4. `add #noname`
<br> 5. `add #tag with space`


#### Adding deadlines
**Successful executions**
<br> 1. `add 'This is a deadline task' by 17:00 25-12-16`
<br> 2. `add by 5:00pm 2016/12/5 'Finish grading papers' #anyorderworks`
<br> 3. `add 'Complete super hard lab' #anyorderworks by 5pm 25-dec-16`
<br> 4. `add 'Finish oxbow lake geography report' by 25/Dec-16 5am #flexibledateformat`
<br> 5. `add 'Make Halloween costume' by 1800 today #naturallanguage`

**Failed executions**
<br> 1. `add 'No by keyword' 25-12-16 2pm`
<br> 2. `add 5pm 'date and time seperated' by 25/12/2016`


#### Adding events
**Successful executions**
<br> 1. `add 'fill up ceg survey' from 8:00 12-5-13 to 10:00 13-5-13 #differentdays`
<br> 2. `add from 8:00 to 10:00 'test different orders' on 12-10-12 #useonkeyword`
<br> 3. `add 'party time!' from 8:00 to 9:00 #datetodayinferred #sameday`
<br> 4. `add on thursday from 8:00 to 9:00 'attend trivia night' #flexibleformat #useonkeyword`

**Failed executions**
<br> 1. `add 'end date before start date' from 8:00 to 6:00 on wednesday`
<br> 2. `add 'invalid date' from 8:00 to 9:00 on 29-2-17` - 29-2-16 is allowed since 2016 is a leap year
<br> 3. `add 'another invalid date' from 8:00 to 9:00 on 30-15-17`
<br> 4. `add 'yet another invalid date' from 8:00 to 27:00 on 30-12-17`

<br>
### Mark status of task
**Successful executions**
<br> 1. `done 1` - disappears from view
<br> 2. `pending 14 15`
<br> 3. `done 8 4 15`

**Failed executions**
<br> 1. `done 0`
<br> 2. `done 300`
<br> 3. `pending -2`
<br> 4. `pending invalid-argument`

<br>
### Listing tasks
**Successful executions**
<br> 1. `list` - lists all tasks except done tasks
<br> 2. `list all`
<br> 3. `list done`
<br> 4. `list pending`
<br> 5. `list today`
<br> 6. `list 25-12-16`
<br> 7. `list event`
<br> 8. `list ev` - same result as `list events`

**Failed executions**
<br> 1. `list invalid-argument`

<br>
### Finding tasks
**Successful executions**
<br> Find command lists all tasks with the keyphrases in the title or tags
<br> 1. `find report`
<br> 2. `find na`
<br> 3. `find bookshelf`
<br> 4. `find lab, report`

**Failed executions**
<br> 1. `find`

<br>
### Deleting tasks
Call `list` before the following commands.

**Successful executions**
<br> 1. `del 1`
<br> 2. `del 1 2`
<br> 3. `del 8 4 15`

**Failed executions**
<br> 1. `del 0`
<br> 2. `del 300`
<br> 3. `del -2`
<br> 4. `del invalid-argument`

<br>
### Edit a task
Choose the index of an event task to test the following commands. These examples assume the index to be 1.

**Successful executions**
<br> 1. `edit 1 'Changed name'`
<br> 2. `edit 1 from 1534 20/11/11`
<br> 3. `edit 1 from 1200 20/11/16 to 1200 21/11/16`
<br> 4. `edit 1 to 20/11/16 13:00 'Changed name again'`
<br> 5. `edit 1 done #gladitsover` - adds a tag
<br> 6. `edit 1 from 4pm` - changes start date to today
<br> 6. `edit 1 from -` - remove start time. Task becomes deadline with due by time equal to end time
<br> 7. `undo`, `edit 1 to -` - start time becomes due by time of deadline

**Failed executions**
<br> 1. `edit 1 to 11am 30-11-16` - start time after end time


<br>
### Undo and redo
**Successful executions**
<br> 1. `undo`
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
<br> 1. `clear` - enter `undo` after verifying to repopulate task manager for further testing

<br>
### Set storage location
**Successful executions**
Replace the file path below with a path pointing to an existing folder.

1. `set-storage C:\Users\Abeysinghe\Desktop save-as newfilename`

**Failed executions**
Replace the file path below with a path pointing to a _non-existing_ folder.

1. `set-storage C:\Users\Abeysinghe\Desktop save-as newfilename`

<br>
### Add alias
**Successful executions**
<br> 1. `add-alias ld=list done` - enter `ld` to see a list of all done tasks
<br> 2. `add 'ld'` will add a task called 'ld' and not 'list done'

<br>
### List all aliases
**Successful executions**
<br> 1. `list-alias`

<br>
### Delete command aliases
**Successful executions**
<br> 1. `list-alias`, `del-alias 1`

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
<br> 1. `help`

<br>
### Exit
**Successful executions**
<br> 1. `exit`