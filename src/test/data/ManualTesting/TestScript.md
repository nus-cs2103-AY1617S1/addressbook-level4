# Test Script

## How to load sample data

1. Download [F10-C1][TARS].jar
2. Download SampleData.xml
3. Launch the app
4. Enter `cd SampleData.xml`

## Script

1. Add command  
   - Add an event: `add Sam's birthday /dt 20/11/2016 5pm to 20/11/2016 9pm /p m`  
   > Task will appear on the left panel with events that happen on the same day.
   
   - Add a deadline: `add Finish assignment /dt 20 Nov 5pm /p m`
   > Task will appear on the left panel with events that happen on the same day.
   
   - Add a floating task: `add Buy clothes`
   > Task will appear on the left panel with index 58.
   
   - recurring task: `add Swim lesson /dt 20 Nov 8am to 9am /r 10 every week`
   > 10 task will be added from index 59 - 68.

2. List command
   - List by earliest end date: `ls /dt`
   > Earliest task (ST3240 Slides) will appears at the top of the list.
   
   - list by latest end date: `ls /dt dsc`
   > Latest task (A Math Tuition) will appears at the top.
   
   - list by lowest priority (low to high): `ls /p`  
   > Task without any priority will be listed at the top.
   
   - list by priority (high to low): `ls /p dsc`
   > Task with high riority will be listed at the top.

3. Find command  
   - Find task by name: `find talk`  
   >  3 events with names that contain `talk` will be listed.
   >  Results display indicate quick search is performed as no prefixes are specified.
   
   - Find tasks by tags: `find /t Jeremy`  
   > Events which has the tag [jeremy] will be listed.
   
   - Find tasks by priority: `find /p h`  
   > Tasks with high priority will be listed.
   
   - Find tasks which are done `find /do`  
   > Three tasks (Collect Welfare pack, Cut hair and Push SMS Subscription) which are done are listed.
   
   - Find tasks which are undone `find /ud`  
   > Remaining undone tasks will be listed.
   
   - Find tasks by date range: `find /dt 20 nov to 21 nov`  
   > 4 events (Gym, Sam's birthday, Finish Assignment, Swim Lesson) will be listed.

4. Edit command  
   - Edit task name and priority: `edit 1 /n Gym with Sean /p l`
   
     > The name of the first task will be changed to `Gym with Sean` and the priority is changed to `l`.

5. Delete command
   - Delete the first task: `del 1`  
   
     > The first task, `Gym with Sean` is removed.

6. Undo command
   - Undo the previous command: `undo`  
   
     > The removed task, `Gym with Sean` is added back to the list.

7. Redo command
   - Redo the previous command: `redo`
   
     > The task, `Gym with Sean` is removed.

8. Do command
   - Marks a task as done:
     - Scroll to the top of the task list
     - `do 1`
   
       > The task `CS3244 Cheatsheet` is mark as done (the card is greyed with a tick).

9. Ud command
   - Marks a task as undone: `ud 1`
   
     > The task `CS3244 Cheatsheet` is mark as undone (the card is ungreyed and the tick is removed).

10. Help command 
    - Show help add: `help add`  
    > The help panel is shown on the right panel and scrolled to the add section.
    
    - Show help summary: `help summary`
    > The help panel is shown on the right panel and scrolled to the summary section.

11. Rsv command
    - Add a reserve task: `rsv Prepare project demo /dt 14/11 2pm to 3pm /dt 15/11 2pm to 3pm /dt 16/11 2pm to 3pm`
    
      > The reserve panel on the right is selected. The reserve task is added with 3 reserved dates.

12. Confirm command
    - Confirm reserve task: `confirm 1 3`
    
      > The task, `Prepare project demo` is added to the task list panel on the left with the datetime, `Wed, Nov 16 2016`.

13. Delete command
    - Deletes a range of task: `del 64..67`
    
      > 4 tasks, `A Math Tuition` is removed.

14. Tag command
    - List tags: `tag /ls`
    > 5 tags, `cs3244`, `finals`, `presentation`, `st3240`, `Jeremy` is shown at the result display box.
    
    - Rename tag: `tag /e 2 final`
    > All tasks that have the `Jeremy` tag is being replaced with the `JTuition` tag.
    
    - Delete tag: `tag /del 1`
    > All tasks that have the tag, `cs3244`, is removed.

15. Free command
    - Find free timeslots: `free 2 nov`
    
      > 2 tasks is shown on the left panel. 2 free timeslots is shown at the result display box.

16. Clear command
    - Clears all data: `clear`
    
      > All data will be cleared.
