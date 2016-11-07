# Test Script

## Load Sample Data
1. Download the zip file [F10-C3][Tary].zip
2. Extract all contents of the zip file into the same folder, if done right<br> 
  > Config.json<br>
  > Preferences.json<br>
  > Taryapp.jar<br>
  > and a folder named data should be in the same folder.
3. Launch [F10-C3][Tary].jar 


## Script
### 1. Add Command
* Add a floating task: `add buy coffee`<br>
  > adds floating task with name: buy coffee, priority set as default (1) and all other fields: nil.
* Add an event task: `add nus hackathon s/saturday d/sunday`<br>
  > adds task with name: nus hackathon, start date is next Saturday of the testing, and end date is next Sunday of testing.<br>
  > both are in the format: dd/mm/yyyy hh:mm
* Add a deadline task: `add CS2103 tutorial d/tomorrow`<br>
  > start date not specified but end date (which is deadline) is.
* Add a full task: `add write letter a/NUS s/today d/tomorrow p/5 t/formal`<br>
  > all fields in task card are fielded with given data
* Add a full task with a different order: `add write letter d/friday s/thursday t/tomorrow p/2`<br>
  > same as above
* Add a task with optional parameters: `add clean room a/home`<br>

### 2. Done Command
* Mark a task as done `done 1`<br>
  > task card will change task at index 1 from [X] to [O]
* Mark a task as done `done 4`<br>
  > same as above at index 4
* Mark a task as done `done 6`<br>
  > same as above at index 6
<br>
  
### 3. List Command
* List all the done tasks: `list done`<br>
  > returns task cards that were previously at index 1,4 and 6
* List all the tasks not done yet: `list`<br>
  > returns all task cards without index 1,4 and 6
<br>

### 4. Delete Command
* delete task: `delete 3`<br>
  > task with index 3 is removed from the list
* delete task: `delete 5`<br>
  > task with index 5 is removed from the list
* delete task: `delete 2`<br>
  > task with index 2 is removed from the list
<br>

### 5. Undo Command
* undo delete of task 2: `undo`
* undo delete of task 5: `undo`
* undo delete of task 3: `undo`
<br>

### 6. Edit Command
* add task: `add supermarket trip d/today p/3 a/cold storage`
* edit task (single parameter) at index 3: `edit 3 p/1`<br>
  > priority of index 3 bumped down
* edit task (multiple parameter) at the index of the added task: `edit INDEX p/5 a/NTUC`<br>
  > change of location and priority reflected on this task card
<br>

### 7. Select Command
* select the supermarket task: `select INDEX`<br>
  > displays the location of the task on google maps at the browser panel
<br>

### 8. Find Command
* find task called 'homework': `find homework`<br>
  > only returns list of tasks with homework in the name
* find tasks tagged with 'homework': `find t/homework`<br>
  > only returns list of tasks with a tag called homework
* find tasks with priority 5: `find p/5`<br>
  > returns most urgent tasks with max priority 5
* find tasks due today: `find d/today`<br>
  > returns all tasks due today
<br>

### 9. Help Command
* open help: `help`
<br>

### 10. Clear Command
* delete all tasks: `clear`
<br>

### 11. setPath Command
* set storage to specified path: `setPath data/newfile.xml`
<br>

### 12. Exit Command
* exit the app: `exit`

<br>




