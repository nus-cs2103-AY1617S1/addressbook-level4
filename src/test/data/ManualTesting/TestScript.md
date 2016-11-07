# Test Script

## Load Sample Data
1. Download the zip file [F10-C3][Tary].zip
2. Extract the file
3. Launch [F10-C3][Tary].jar 


## Script
### 1. Add Command
* Add a floating task: `add buy coffee`<br>
  > adds floating task with name: buy coffee, priority set as default (1) and all other fields: nil.
* Add an event task: `add nus hackathon s/saturday d/sunday`<br>
  > adds task with name: nus hackathon, start date is next Saturday of the testing, and end date is next Sunday of testing.<br>
  > both are in the format: dd/mm/yyyy hh:mmm 
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
  
### 3. List Command
* Mark






