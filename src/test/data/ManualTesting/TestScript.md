<!--@@author A0143095H	-->

# How to set-up

1. Open the sample file in notepad++
2. Go to the file directory where you keep the .JAR file
3. Create a folder called data
4. Create a xml file called tasklist.xml
5. Copy the sample file and paste in the tasklist.xml
6. Run the .JAR file

# Test Script 

## 1 Add Command <br>

### 1.1  Add a floating task <br>

    1.1.1    Type in the Command Box : add buy calculator 
    1.1.2    Task would be added to the task list panel in the main window.
    1.1.3    Task would be added to the result display panel.
    
    1.2.1    Type in the Command Box : add prepare for lab i/bring lab kit t/lab 
    1.2.2    Task would be added to the task list panel in the main window.
    1.2.3    Task would be added to the result display panel.
  
    1.3.1    Type in the Command Box : add book wedding dinner t t/wedding 
    1.3.2    Task would be added to the task list panel in the main window.
    1.3.3    Task would be added to the result display panel.


### 1.1  Add a deadline task <br>

    1.1.1    Type in the Command Box : add complete 2103 d/09-11-2016 t/CS2103
    1.1.2    Task would be added to the task list panel in the main window.
    1.1.3    Task would be added to the result display panel.
    
    1.2.1    Type in the Command Box : Add Complete 2020 project i/ Frequency and Sine wave d/12-11-2016
    1.2.2    Task would be added to the task list panel in the main window.
    1.2.3    Task would be added to the result display panel.
  
    1.3.1    Type in the Command Box : Add Go for lecture by tomorrow
    1.3.2    Task would be added to the task list panel in the main window.
    1.3.3    Task would be added to the result display panel.
    

### 1.1  Add a deadline task with end time <br>

    1.1.1    Type in the Command Box : Add Babysit Kimberly d/11-11-2016 e/1900
    1.1.2    Task would be added to the task list panel in the main window.
    1.1.3    Task would be added to the result display panel.
    
    1.2.1    Type in the Command Box : Add Submission for 2021 lab assignment d/19-11-2016 e/2359
    1.2.2    Task would be added to the task list panel in the main window.
    1.2.3    Task would be added to the result display panel.
  
    1.3.1    Type in the Command Box : Add Project submission i/1020 Lab work question 5 d/29-11-2016 e/2300
    1.3.2    Task would be added to the task list panel in the main window.
    1.3.3    Task would be added to the result display panel.


### 1.1  Add an event <br>

    1.1.1    Type in the Command Box : Add Consultation slot for meeting d/27-12-2016 s/1500 e/1800
    1.1.2    Task would be added to the task list panel in the main window.
    1.1.3    Task would be added to the result display panel.
    
    1.2.1    Type in the Command Box : Add 2103 Exam i/ 50 MCQ questions d/24-11-2016 s/1200 e/1400
    1.2.2    Task would be added to the task list panel in the main window.
    1.2.3    Task would be added to the result display panel.
  
    1.3.1    Type in the Command Box : Add 2102 meeting with teacher i/ regarding UI and DG d/29-11-2016 s/1200 e/1600
    1.3.2    Task would be added to the task list panel in the main window.
    1.3.3    Task would be added to the result display panel.


 
## 2 Delete Command <br>
 
    2.1.1   Type in the Command Box : delete 1
    2.1.2   Task should be deleted from the task list panel in the main window and the list would be updated.
    2.1.3   Task deleted would be displayed on the result display panel.
    
    2.2.1   Type in the Command Box : delete 3
    2.2.2   Task should be deleted from the task list panel in the main window and the list would be updated.
    2.2.3   Task deleted would be displayed on the result display panel.
    
    2.3.1   Type in the Command Box : delete 4
    2.3.2   Task should be deleted from the task list panel in the main window and the list would be updated.
    2.3.3   Task deleted would be displayed on the result display panel.
 
 
## 3 Edit Command <br>

    3.1.1   Type in the Command Box : edit 1 d/01-12-2016 
    3.1.2   Task would be updated on the task list panel in the main window. 
    3.1.3   Updated Task would be displayed on the result display panel.
    
    3.2.1   Type in the Command Box : edit 3 Bake a cake 
    3.2.2   Task would be updated on the task list panel in the main window. 
    3.2.3   Updated Task would be displayed on the result display panel.
    
    3.3.1   Type in the Command Box : edit 4 s/1300 e/1900 
    3.3.2   Task would be updated on the task list panel in the main window. 
    3.3.3   Updated Task would be displayed on the result display panel.
    
    3.4.1   Type in the Command Box : edit 2 d/12-11-2016 s/1200 e/2100
    3.4.2   Task would be updated on the task list panel in the main window. 
    3.4.3   Updated Task would be displayed on the result display panel.
    
    3.5.1   Type in the Command Box : edit 1 Build Coverage d/11-11-2016 s/1100 e/2100 
    3.5.2   Task would be updated on the task list panel in the main window. 
    3.5.3   Updated Task would be displayed on the result display panel.
   

## 4 Done Command <br>

    4.1.1   Type in the Command Box : done 1
    4.1.2   Task would be marked as done and removed from the task list panel in the main window. 
    4.1.3   Updated Task would be displayed on the result display panel.

    4.2.1   Type in the Command Box : done all 
    4.2.2   All tasks would be marked as done and removed from the task list panel in the main window. 
    4.2.3   Updated Task would be displayed on the result display panel.


## 5 Undone Command <br>

    5.1.1   Type in the Command Box : list done 
    5.1.2   Type in the Command Box : undone 1
    5.1.3   Task would be marked as undone and updated on the task list panel in the main window. 
    5.1.4   Updated Task would be displayed on the result display panel.

    5.2.1   Type in the Command Box : list done 
    5.2.2   Type in the Command Box : undone 2
    5.2.3   Task would be marked as undone and updated on the task list panel in the main window. 
    5.2.4   Updated Task would be displayed on the result display panel.


## 6 List Command <br>

    6.1.1   Type in the Command Box : list all
    6.1.2   Task List panel would be updated to show all tasks in the main window. 
    6.1.3   The result display would show the message that all tasks have been listed. 

    6.2.1   Type in the Command Box : list done 
    6.2.2   Task List panel would be updated to show all done tasks in the main window. 
    6.2.3   The result display would show the message that all done tasks have been listed. 
    
    6.3.1   Type in the Command Box : list overdue
    6.3.2   Task List panel would be updated to show all overdue tasks in the main window. 
    6.3.3   The result display would show the message that all overdue tasks have been listed. 
    
    6.4.1   Type in the Command Box : list 12-11-2016
    6.4.2   Task List panel would be updated to show all tasks which have are due from today till 12-11-2016. 
    6.4.3   The result display would show the message that all tasks in the range have been listed. 

    6.5.1   Type in the Command Box : list 12-11-2016 to 18-11-2016
    6.5.2   Task List panel would be updated to show all tasks which have are due from 11-12-2016 till 18-11-2016. 
    6.5.3   The result display would show the message that all tasks in the range have been listed. 
    
    6.2.1   Type in the Command Box : list
    6.2.2   Task List panel would be updated to show all undone tasks in the main window.  
    6.2.3   The result display would show the message that all undone tasks have been listed. 


## 7 Find Command <br>

    7.1.1   Type in the Command Box : Find 13-11-2016
    7.1.2   Task List panel would be updated to show all tasks that are due on 13-11-2016. 
    7.1.3   The result display would show the message that all tasks have been listed. 
    
    7.2.1   Type in the Command Box : Find homework
    7.2.2   Task List panel would be updated to show all tasks that have the name and tags in the main widow.
    7.2.3   The result display would show the message of the number of tasks that have been listed.

## 8 Clear Command <br>

    8.1.1   Type in the Command Box : Clear
    8.1.2   Task List panel would be cleared.
    8.1.2   The result display would show the message that all tasks in the current list have been cleared.
    
## 9 Undo Command <br>
    
    9.1.1   Type in the Command Box : Add hello
    9.1.2   Type in the Commmand Box : Undo
    9.1.3   Task would be deleted.
    
    9.2.1   Type in the Command Box : Delete 2
    9.2.2   Type in the Command Box : Undo
    9.2.3   Task would be restored and the task list willbe updated in the main window.
    
## 10 Redo Command <br>
    10.1.1  Type in the Command Box : Delete 3
    10.1.2  Type in the Command Box : Undo
    10.1.3  Type in the Command Box : Redo
    10.1.4  Task would be deleted and the task list would be updated in the main window.
    
## 11 Help Command <br>
    11.1.1  Type in the Command Box : Help
    11.1.2  The result display would show the help message for the formats for all the commands
    
## 12 Save to Directory <br>
    12.1.1  Type in the Command Box : setdir ListTask.xml
    12.1.2  The result display would show the message of the new file path
    
    12.2.1 Type in the Command Box : setdir reset
    12.2.2 The result display will show the message that the file directory has been reverted to its original file path
    
## 13 Exit Command <br>
    13.1.1 Type in the Commmand Box : Exit
    13.1.2 The application will exit
 
 
