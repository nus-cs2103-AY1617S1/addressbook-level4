*UNDONE* To load the sample data...

Add a floating task
Command: Read Harry Potter
Result: Task with name "Read Harry Potter", priority bar shaded yellow

Undo previous add command
Command: undo
Result: Previously added task "Read Harry Potter" is deleted.

Redo previous add command
Command: redo
Result: Previously deleted task "Read Harry Potter" is re-added.

Add a task with start date
Command: Read Harry Potter from 30th Nov
Result: Task with name "Read Harry Potter", with start date "Wed, 30 Nov 2016, 12:00 AM", priority bar shaded yellow

Add a task with end date
Command: Read Harry Potter by 30th Nov
Result: Task with name "Read Harry Potter", with end date "Wed, 30 Nov 2016, 11:59pm", priority bar shaded yellow

Add a task with start and end date
Command: Read Harry Potter from 30th Nov 8am to 9am
Result: Task with name "Read Harry Potter", with start date "Wed, 30 Nov 2016, 8:00am", end date "Wed, 30 Nov 2016, 9:00am", priority bar shaded yellow

Add a task with recurrence rate
Command: Jog repeat every Monday
Result: Task with name "Jog", with recurrence rate "every Monday", priority bar shaded yellow. Start date will take the nearest Monday. (i.e if today is Monday, start date will be today. Else, start date will be next Monday)
Command: Jog from 20th Nov 10am repeat every 3 days
Result: Task with name "Jog", with start date "Sun, 20 Nov 2016, 10:00am", recurrence rate "Every 3 Days", priority bar shaded yellow

Add a task with priority
Command: Buy kitkat -high
Result: Task with name "Buy kitkat", with priority bar shaded red

Add a task that contains all of these
Command: Overnight cycling from 10th Nov 10pm to 2am repeat every week -low
Result: Task with name "Overnight cycling", with start date "Thu, 10 Nov 2016, 10:00pm", end date "Fri, 11th Nov 2016, 2:00am", recurrence rate "Every Week", priority bar shaded green

Add a task with multiple keywords
Command: Go to the beach from my house from 10th Nov 10am to 11am
Result: Task with name "Go to the beach from my house", with start date "Thu, 10 Nov 2016, 10:00am", end date "Thu, 10 Nov 2016, 11:00am", priority bar shaded yellow

Add a task with end date earlier than start date
Command: eat from today 10pm to yesterday 10pm
Result: Error message, showing invalid command format, "End date should be later than start date." and add command format.

Add a task with invalid date
Command: eat from 40 Nov
Result: Error message, showing invalid command format, "Invalid date." and add command format.

Add a task with invalid rate
Command: eat from 10 Nov repeat every 0 days
Result: Error message, showing invalid command format, recurring constraints and add command format.

Add a task with invalid time period
Command: eat from 10 Nov repeat every 5 bobs
Result: Error message, showing invalid command format, recurring constraints and add command format.

Add a task with missing time period
Command: eat from 10 Nov repeat every 5
Result: Error message, showing invalid command format, recurring constraints and add command format.

Add a recurring task without dates
Command: eat repeat every 3 days
Result: Error message, showing invalid command format, recurrence and date constraints, and add command format.

Add a task with relative dates
Command: eat from this wed to next thurs
Result: Task with name "eat", start date will take the nearest Wednesday, end date will take the next Thursday.

Add a task with repeat start dates
Command: eat from today from tomorrow
Result: Error message, showing invalid command format, "Repeated start times are not allowed.", and add command format.

Add a task with repeat end dates
Command: eat by today by tomorrow
Result: Error message, showing invalid command format, "Repeated end times are not allowed.", and add command format.

Add a task with escaped input
Command: "increase word count from 1000 to 1500" from 10th Nov to 11th Nov
Result: Task with name "increase word count from 1000 to 1500", with start date "Thu, 10 Nov 2016, 12:00am", end date "Fri, 11th Nov 2016, 11:59pm", priority bar shaded yellow