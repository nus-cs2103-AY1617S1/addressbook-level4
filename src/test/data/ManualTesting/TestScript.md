# Manual Testing : Test Script
##Steps to load the sample data
1. Navigate to WhatNow folder in file explorer
2. Ensure that both the WhatNow.jar and SampleData.xml are in the same foler
3. Replace the current whatnow.xml with SampleData.xml to be renamed into whatnow.xml
4. Launch WhatNow.jar

###Add Command
 Command: 'add "Eat chocolate"' <br>
 Expected: New task added: Eat chocolate <br> 
 
 Command: 'add "RollerSkating in east coast" on wednesday to sunday'<br>
 Expected: New task added: roller skating from 09/11/2016 to 13/11/2016 <br>
 
 Command: 'add "Fishing in village" on today to 24/12/2016 3pm' <br>
 Expected: New task added: Fishing in village from 07/11/2016 12:00am to 24/12/2016 3:00pm  <br>
 
 Command: 'add "Fly kite in marina bay" on tomorrow to sunday 5pm' <br>
 Expected: New task added: Fly kite in marina bay from 08/11/2016 12:00am to 13/11/2016 5:00pm  <br>
 
 Command: 'add "Fix computer" on today to tomorrow' <br>
 Expected: New task added: Fix computer from "08/11/2016" to "09/11/2016"  <br>
 
 Command: 'add "Sleep in orchard hotel" on 6/11/2016 to 7/11/2016' <br>
 Expected: The task date range is invalid!  <br>
 
 Command: 'add "Go India for tour" on 6/12/2016 4pm to 7/12/2016 6pm' <br>
 Expected: New task added: Go Malaysia tour from 06/12/2016 4:00pm to 07/12/2016 6:00pm  <br>
 
 Command: 'add "Go Thailand for sightseeing" on 10/12/2016 to 11/12/2016 5pm to 7pm' <br>
 Expected: New task added: Go Thailand for sightseeing from 10/12/2016 5:00pm to 11/12/2016 7:00pm  <br>

 Command: 'add "Go Korea to play" on 17/12/2016 to 16/12/2016 3pm to 5pm' <br>
 Expected: Entered an invalid date range format  <br>
 
 Command: 'add "Climb tree" on 14/12/2016 to sunday 4pm to 4:01pm' <br>
 Expected: Entered an invalid date range format  <br>
 
 Command: 'add "Go school for karate" on 11/12/2016 every week till 30/12/2016'
 Expected:  New task added: Go school for karate on 11/12/2016 every week till 30/12/2016

###Delete Command
 Command: 'delete schedule 1' <br>
 Expected: Deleted Task: Holiday in Denmark from 09/10/2016 04:45am to 14/10/2016 06:45pm  <br>
 
 Command: 'delete todo 1' <br>
 Expected: Deleted Task: Buy milk  <br>
 
###Done Command
 Command: 'done todo 2' <br>
 Expected: Task marked as completed: Sleep like a log  <br>
 
 Command: 'done schedule 2' <br>
 Expected: Task marked as completed: Overseas students coming over from 12/10/2016 to 14/10/2016 <br>

###List Command
 Command: 'list done' <br>
 Expected: Listed all completed tasks <br>
 
 Command: 'list' <br>
 Expected: Listed all incomplete tasks <br>
 
 Command: 'list all' <br>
 Expected: Listed all tasks <br>
 
###Undone Command
 Command: 'undone schedule 1' <br>
 Expected: Task marked as incompleted: Buy groceries on 12/10/2016 <br>
 	
###Clear Command
 Command: 'clear' <br>
 Expected: WhatNow has been cleared! <br>
 
###Undo Command
 Command: 'undo' <br>
 Expected: Undo Successfully <br>
 
 Command: 'add "Cookies for all"' <br>
 Expected: New task added: Cookies for all <br>
 
 Command: 'undo' <br>
 Expected: Undo Successfully <br>
 
###Redo Command
 Command: 'redo' <br>
 Expected: Redo Successfully <br>
 
###FreeTime command
 Command: 'freetime 17/10/2016' <br>
 Expected: No freetime was found for: 17/10/2016 <br>

###Pin Command
 Command: 'pin date 17/10/2016' <br>
 Expected: Pinned Item view updated! <br>
 
 Command: 'pin date none' <br>
 Expected: Pinned Item view updated! <br>
 
 Command: 'pin tag high' <br>
 Expected: Pinned Item view updated! <br>

###Update Command
 Command: 'update todo 6 date 30/12/2016' <br>
 Expected:Updated Task: 
From: Eat chocolate 
To: Eat chocolate on 30/12/2016 <br>

 Command: 'undo' <br>
 Expected: Undo Successfully <br>

 Command: 'redo' <br>
 Expected: Redo Successfully <br>
 
###Change Command

 Command: 'change location to C:\Users\lim\Desktop' <br>
 Expected: The data storage location has been successfully changed to: C:\Users\lim\Desktop\whatnow.xml <br>

###Undo Command
 Command: 'undo' <br>
 Expected: Successfully able to undo to ./data/whatnow.xml <br>

###Find Command
 Command: 'find tour' <br>
 Expected: 1 tasks listed! <br>
  
###Help Command
 Command: 'help' <br>
 Expected: Direct to user guide <br>
 
###Exit Command
 Command: 'exit' <br>
 Expected: Exit from WhatNow