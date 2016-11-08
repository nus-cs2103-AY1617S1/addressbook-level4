------
# Testscript
------
<br>
## Load SampleData.xml
> **Instructions:**
 - 1. Create a folder called `data` at the same directory as `sTask.jar` executable.
 - 2. Copy and paste `SampleData.xml` into that folder and rename it as 'task.xml'.
 - 3. Run `sTask.jar`.

------
## 1. Help Command
------
### 1.1 Open help window
> **Command:** `help` <br>
> **Result:**
 - Result display panel posts message: <br>
    `Opened help window.`
 - Help window pops up and shows a summary of commands.

------
## 2. Add Command
------
### 2.1 Add a To Do task (floating task)
> **Command:** `add Claim coffee d/Starbucks` <br>
> **Result:** <br>
 - ResultDisplay panel posts message: <br>
    `New task added: Claim coffee Description: Starbucks'
 - To Do panel (left panel) selects newly added task card.
 - Default: To Do task has a white border 

### 2.2 Add a Deadline task
> **Command:** `add Finish project proposal date/next friday 5pm t/important`<br>
> **Result:**<br>
 - ResultDisplay panel posts message:<br>
    `New task added: Finish project proposal Date: 18-Nov-2016 Time: 17:00 Tags: [important]'
 - Events / Deadlines panel (right panel) selects newly added task card
 - Default: Deadline task has a white border (unless it is overdue at the point of adding, where it will have a red border)

### 2.3 Add a overdue Deadline task
> **Command:** `add Present EE2020 project date/7 Nov 2016 3.15pm`<br>
> **Result:**<br>
 - ResultDisplay panel posts message:<br>
    `New task added: Present EE2020 project Date: 7-Nov-2016 Time: 15:15'
 - Events / Deadlines panel (right panel) selects newly added task card
 - Default: Overdue deadline task has a red border.

### 2.4 Add an Event
> **Command:** `add Meeting with client d/Prepare documents date/28-11-2016 10am to 28-11-2016 12pm `<br>
> **Result:**<br>
 - Result display panel posts message:<br>
    `New task added: Meeting with client Description: Prepare documents Date: 28-Nov-2016 Time: 10:00 to 12:00`
 - Events / Deadlines panel (right panel) selects newly added task card
 - Default: Event has a white border (unless it is expired at the point of adding, where it will have a pink border)

### 2.5 Add an expired Event
> **Command:** `add Lunch with Jim date/28-Oct-2016 12pm to 1pm `<br>
> **Result:**<br>
 - Result display panel posts message:<br>
    `New task added: Lunch with Jim Description: Prepare documents Date: 28-Oct-2016 Time: 12:00 to 13:00`
 - Events / Deadlines panel (right panel) selects newly added task card
 - Expired: Event has a pink border 

### 2.6 Add an Event that spans across dates
> **Command:** `add Attend Halloween event d/At Sentosa date/28-Sep-2016 10am to 30-Sep-2016 10pm`<br>
> **Result:**<br>
 - Result display panel posts message:<br>
    `New task added: Attend Halloween event Description: At Sentosa Date: 28-Sep-2016 to 30-Sep-2016 Time: 10:00 to 22:00`
 - Events / Deadlines panel (right panel) selects newly added task card
 - Expired: Event has a pink border 

### 2.7 Add with invalid delimiter
> **Command:** `add Valid Task Name e/Wrong parameter for description date/tmr`<br>
> **Result:**<br>
 - Result display panel posts error:<br>
    `Invalid Command Format!`
    `add: Adds a task to the task book.`
    `Parameters: TASKNAME d/TASK_DESCRIPTION date/DD-MM-YYYY [24HR] [to 24HR] [t/TAG]...`
    `Example: add Wash Clothes d/Wash with detergent date/27-9-2016 2359 t/important`
 - Command input box turns red to prompt user that there is an error.

### 2.8 Add without task name
> **Command:** `add d/No name put in`<br>
> **Result:**<br>
 - Result display panel posts error:<br>
    `Invalid Command Format!`
    `add: Adds a task to the task book.`
    `Parameters: TASKNAME d/TASK_DESCRIPTION date/DD-MM-YYYY [24HR] [to 24HR] [t/TAG]...`
    `Example: add Wash Clothes d/Wash with detergent date/27-9-2016 2359 t/important`
 - Command input box turns red to prompt user that there is an error.

### 2.9 Add a task with name that contains '/'
> **Command:** `add hello/world d/Invalid name`<br>
> **Result:**<br>
 - Result display panel posts error:<br>
    `Invalid Command Format!`
    `add: Adds a task to the task book.`
    `Parameters: TASKNAME d/TASK_DESCRIPTION date/DD-MM-YYYY [24HR] [to 24HR] [t/TAG]...`
    `Example: add Wash Clothes d/Wash with detergent date/27-9-2016 2359 t/important`
 - Command input box turns red to prompt user that there is an error.

### 2.10 Add a deadline with date that delimits by '.'
> **Command:** `add hello world d/Wrong date format date/11.11.2011`<br>
> **Result:**<br>
 - Result display panel posts error:<br>
    `Date should be in DD-MM-YYYY format and cannot contain '.' character`
 - Command input box turns red to prompt user that there is an error.

### 2.11 Add a event with end date earlier than the start date
> **Command:** `add Hello World date/today to yesterday`<br>
> **Result:**<br>
 - Result display panel posts error:<br>
    `Date should have its start time before its end time.`
 - Command input box turns red to prompt user that there is an error.

### 2.12 Add a to do task with non-alphanumeric tags
> **Command:** `add Hello World t/!!!`<br>
> **Result:**<br>
 - Result display panel posts error:<br>
    `Tags names should be alphanumeric`
 - Command input box turns red to prompt user that there is an error.

------
## 3. List Command
------
### 3.1 List all uncompleted to do, events and deadlines
> **Command:** `list all`<br>
> **Result:**<br>
 - ResultDisplay panel posts message:<br>
    `Listed all tasks`
 - Number of tasks displayed on To Do: 16
 - Number of tasks displayed on Events / Deadlines: 35
 - Deselects any currently selected task, which are indicated by a green border.
 - Note: Expired events are labelled in pink, overdue deadlines are labelled in red.
 - All other uncompleted tasks are labelled in white.
 
### 3.2 List all completed to do, events and deadlines
> **Command:** `list done`<br>
> **Result:**<br>
 - ResultDisplay panel posts message:<br>
    `Listed completed tasks.`
 - Number of tasks displayed on To Do: 3
 - Number of tasks displayed on Events / Deadlines: 4
 - Deselects any currently selected task, which are indicated by a green border.
 - Note: Completed tasks are labelled in blue.
 
### 3.3 List all overdue and expired deadlines
> **Command:** `list od`<br>
> **Result:**<br>
 - ResultDisplay panel posts message:<br>
    `Listed overdue and expired tasks.`
 - Number of tasks displayed on To Do: 0
 - Number of tasks displayed on Events / Deadlines: 23
 - Deselects any currently selected task, which are indicated by a green border.
 - Note: Expired events are labelled in pink, overdue deadlines are labelled in red.
 - Note: Number of overdue and expired tasks might change according to time. (Data as of 7 Nov 2016, 1.55pm)
 
### 3.4 List with invalid arguments
> **Command:** `list crazy`<br>
> **Result:**<br>
 - ResultDisplay panel posts message:<br>
    `Invalid Command Format!`
    `list: Lists the tasks in the address book.`
    `Parameters: list all/od/done`
    `Example: list done`
 - Command input box turns red to prompt user that there is an error.

------
## 4. Edit Command
------
### 4.1 Edit name of task
> **Before:** `buy micro usb cable`<br>
> **Command:** `edit A1 buy mini usb cable`<br>
> **Result:**<br>
 - Result display panel posts message:<br>
    `Edited Task: buy mini usb cable Description: spare`
 - To Do panel (left panel) selects newly added task card.
 - Default: To Do task has a white border 

### 4.2 Edit description of task
> **Before:** `Starbucks`<br>
> **Command:** `edit A5 d/Coffee Bean`<br>
> **Result:**<br>
 - Result display panel posts message:<br>
    `Edited Task: Claim coffee Description: Coffee Bean`
 - To Do panel (left panel) selects newly added task card.
 - Default: To Do task has a white border 

 ### 4.3 Edit to remove description of task
> **Before:** `hong yu`<br>
> **Command:** `edit A2 d/`<br>
> **Result:**<br>
 - Result display panel posts message:<br>
    `Edited Task: buy new bag`
 - To Do panel (left panel) selects newly added task card.
 - Default: To Do task has a white border 

 ### 4.4 Edit date of event
> **Before:** `03-Nov-2016 09:00 to 10:00`<br>
> **Command:** `edit B6 date/20-Dec-2016 9am to 10am`<br>
> **Result:**<br>
 - Result display panel posts message:<br>
    `Edited Task: breakfast with 2103 team Date: 20-Dec-2016 Time: 09:00 to 10:00 Tags: [BestTeam]`
 - Events / Deadlines panel (right panel) selects newly added task card
 - Default: Event has a white border (unless it is expired at the point of adding, where it will have a pink border)

 ### 4.5 Edit date of deadline
> **Before:** `02-Nov-2016 17:00`<br>
> **Command:** `edit B3 date/30-Dec-2016 5pm`<br>
> **Result:**<br>
 - Result display panel posts message:<br>
    `Edited Task: Edited Task: cook rice Description: dinner Date: 30-Dec-2016 Time: 17:00`
 - Events / Deadlines panel (right panel) selects newly added task card
 - Default: Deadline task has a white border (unless it is overdue at the point of adding, where it will have a red border)

### 4.6 Edit from event to deadline
> **Before:** `03-Nov-2016 00:00 to 23:59`<br>
> **Command:** `edit B4 date/3-Nov-2016 2pm`<br>
> **Result:**<br>
 - Result display panel posts message:<br>
    `Edited Task: Edited Task: bring windows laptop Date: 03-Nov-2016 Time: 14:00`
 - Events / Deadlines panel (right panel) selects newly added task card
 - Default: Deadline task has a white border (unless it is overdue at the point of adding, where it will have a red border)

### 4.7 Edit from to do task to deadline
> **Before:** <br>
> **Command:** `edit A2 date/xmas`<br>
> **Result:**<br>
 - Result display panel posts message:<br>
    `Edited Task: Edited Task: buy new bag Date: 25-Dec-2016 Time: 23:59`
 - Events / Deadlines panel (right panel) selects newly added task card
 - Default: Deadline task has a white border (unless it is overdue at the point of adding, where it will have a red border)

### 4.8 Edit from deadline to to do task
> **Before:** `26-Nov-2016 13:00`<br>
> **Command:** `edit B31 date/`<br>
> **Result:**<br>
 - Result display panel posts message:<br>
    `Edited Task: Edited Task: cs2103T finals Tags: [exam]`
 - To Do panel (left panel) selects newly added task card.
 - Default: To Do task has a white border 

------
## 5. Find Command
------
### 5.1 Find by tags
> **Command:** `find cs210`<br>
> **Result:**
 - Result display panel posts message:<br>
    `5 tasks listed!`
 - To Do panel (left panel) lists all tasks that has any field that contains cs210.
 - Events / Deadlines panel (right panel) lists all tasks that has any field that contains cs210.
 - Number of tasks displayed on To Do: 1
 - Number of tasks displayed on Events / Deadlines: 4
 
### 5.2 Find by year
> **Command:** `find 2016`<br>
> **Result:**
 - Result display panel posts message:<br>
    `39 tasks listed!`
 - To Do panel (left panel) lists all tasks that has any field that contains 2016.
 - Events / Deadlines panel (right panel) lists all tasks that has any field that contains 2016.
 - Number of tasks displayed on To Do: 0
 - Number of tasks displayed on Events / Deadlines: 39

------
## 6. View Command
------
### 6.1 View all the tasks on specified date
> **Command:** `view 7 Nov 2016`<br>
> **Result:**<br>
 - Result display panel posts message:<br>
    `Viewing tasks for 07-Nov-2016`
 - Events / Deadlines panel (right panel) lists all tasks that falls on 7-Nov-2016.
 - Number of tasks displayed on Events / Deadlines: 3 
 - Note: To Do panel (left panel) does not change.

### 6.2 View all the tasks on specified date
> **Command:** `view valentines day`<br>
> **Result:**<br>
 - Result display panel posts message:<br>
    `Viewing tasks for 14-Feb-2017`
 - Events / Deadlines panel (right panel) lists all tasks that falls on 14-Feb-2017.
 - Number of tasks displayed on Events / Deadlines: 0
 - Note: To Do panel (left panel) does not change.

------
## 7. Delete Command
------
### 7.1 Delete event
> **Command:** `list all`
 - Number of tasks displayed on To Do: 16
 - Number of tasks displayed on Events / Deadlines: 35
> **Command:** `delete B6`<br>
 - Result display panel posts message:<br>
    `Deleted Task: demo event expired Date: 03-Nov-2016 Time: 11:05 to 11:11 Tags: [CS2103T]`
 - Number of tasks displayed on To Do: 16
 - Number of tasks displayed on Events / Deadlines: 34
 
### 7.2 Delete completed task
> **Command:** `list done`
 - Number of tasks displayed on To Do: 3
 - Number of tasks displayed on Events / Deadlines: 4
> **Command:** `delete A2`<br>
 - Result display panel posts message:<br>
    `Deleted Task: buy grips for tennis racket`
 - Number of tasks displayed on To Do: 2
 - Number of tasks displayed on Events / Deadlines: 4

------
## 8. Undo Command
------
### 8.1 Undo last command
> **Command:** `undo`<br>
> **Result:**<br>
 - Result display panel posts message:<br>
    `Action 'delete' has been reverted`
 - Number of tasks displayed on To Do: 16
 - Number of tasks displayed on Events / Deadlines: 34

------
## 9. Redo Command
------
### 9.2 Redo last undo command
> **Command:** `redo`<br>
> **Result:**<br>
 - Result display panel posts message:<br>
    `Undo action 'delete' has been reverted`
 - Number of tasks displayed on To Do: 16
 - Number of tasks displayed on Events / Deadlines: 34
 - Note: Number of tasks will not change as the task that was deleted was a completed task.

------
## 10. Done Command
------
### 10.1 Complete to do task
> **Command:** `list all`
 - Number of tasks displayed on To Do: 16
 - Number of tasks displayed on Events / Deadlines: 34
> **Command:** `done A1`<br>
 - Result display panel posts message:<br>
    `Completed Task: buy mini usb cable Description: spare`
 - Number of tasks displayed on To Do: 15
 - Number of tasks displayed on Events / Deadlines: 34 

### 10.2 Complete event
> **Command:** `done B2`<br>
 - Result display panel posts message:<br>
    `Completed Task: Lunch with Jin Date: 28-Oct-2016 Time: 12:00 to 13:00`
 - Number of tasks displayed on To Do: 15
 - Number of tasks displayed on Events / Deadlines: 33

------
## 11. Select Command
------
### 10.1 Select a to do task
> **Command:** `select A15`<br>
 - Result display panel posts message:<br>
    `Selected Task: A15`
 - Number of tasks displayed on To Do: 15
 - Number of tasks displayed on Events / Deadlines: 33
 - To Do panel (left panel) scrolls to selected task card.
 
### 10.2 Select a Events / Deadlines task
> **Command:** `select B15`<br>
 - Result display panel posts message:<br>
    `Selected Task: B15`
 - Number of tasks displayed on To Do: 15
 - Number of tasks displayed on Events / Deadlines: 33
 - Events / Deadlines panel (right panel) scrolls to selected task card.

------
## 12. Save Command
------
### 12.1 Save to a different folder
> **Command:** `save jim`<br>
 - Result display panel posts message:<br>
    `New file location saved.`
 - Bottom right hand corner indicates updated save folder location to "jim/task.xml".

------
## 13. Exit Command
------
### 13.1 Exits sTask
> **Command:** `exit`<br>
 - Application exits.

------
## End
------ 
