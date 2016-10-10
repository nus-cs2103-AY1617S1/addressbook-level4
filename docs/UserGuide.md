#Project name: taskBook

**Vision:** An intelligent and personalised task scheduler for Jim

##Userguide: 

**Step 1:**

Fill in your name and press “Enter”

This step will be required only for the first time you use the application. It is just to initialise the user’s name to make it more personalised.

**Step 2:**

 Type in command and press “Enter”

**Step 2a: Adding tasks**

To add a task, type " `add` taskname date time task_priority tagging venue recurrence pin”

* Eg, user can type `add` play soccer, date 120516, time 1212, high priority, #sport, @town green, repeat weekly, pin

Only command and taskname are compulsory; other fields are optional and will take default or null values if not entered. The rules for adding to the other fields are as follows:

`Date`: it can accept 
* formal dates (02/28/1979), 
* relaxed dates (oct 1st), 
* relative dates (the day before next thursday), 
* and even date alternatives (next wed or thurs).

`time`: The above date formats may be prefixed or suffixed with time information. 
* Eg: 0600h,06:00 hours,6pm, 5:30 a.m., 5, 12:59, 23:59, noon, midnight

Read more about [Natty Date Parser](http://natty.joestelmach.com/)

`priority`: Will be medium by default

`#`: tagging

`@`: venue

`repeat`: recurrence of event. Can be daily, weekly or monthly.

`Pin`: indicates task should be pinned

They do not need to be added in order

**Step 2b: Listing tasks**

To `list` tasks, type “`list` [type of list] 
taskBook contains the following lists: 

`Active`: 

The tasks that are still active, sorted from the earliest to the latest
*Eg: Typing `list active`
*Eg: Typing `list active 120316` returns a list of active tasks with deadlines on 12 March 2016.

`Expired`: 

The tasks that have passed the deadline, sorted from the latest deadline to the earliest
*Eg: Typing `list` `expired`
*Eg: Typing `list expired 120316` returns a list of expired tasks with deadlines on 12 March 2016.

`Done`: 

The tasks that are done, sorted from the most recent to the least recent

* Eg: Typing `list done`
* Eg: Typing `list done 120316` returns a list of tasks done on 12 March 2016.

`Ignore`: The tasks that have been ignored, sorted from the most recent to the least recent
* Eg: Typing `list ignored`
* Eg: Typing `list ignored 120316` returns a list of tasks with deadlines on 12 March 2016 that are ignored.

`Date`: The tasks all on the day stated
* Eg: Typing `list date 120316` returns a list of tasks on that 12 March 2016

`task priority`: all tasks that are of `high`/ `medium`/ `low priority`
* Eg: Typing `list high priority` return a list of tasks that are of high priority 

`Tags` : List of Tags you have used
* Eg: Typing `#` returns a list of a Tags created
* Eg: Typing `#sport` where sport is the name of a Tag, will return a list of tasks that are tagged with sports 

`Venue` : all tasks with the same venue
* Eg: Typing `list @Town Green` returns a list of events that are located at Town Green

`Repeat` : all tasks that are set to recurring
* Eg: Typing `list repeat` returns a list of tasks that are repeated
* Eg: Typing `list repeat weekly` returns a list of tasks that are repeated weekly

`Pin` : all pinned tasks
* Eg: Typing `list pin` return a list of pinned tasks

`Free slots`: all free slots 
* Eg: Typing `list slots 120316` returns a list of free slots on 12 March 2016

**Step 2c:Sorting Tasks:**
 
When the `list` that the user is looking at is not what he wants, he can use the `sort` function

`Lists` can be sorted by date from
* `Time`: Type sort earliest to latest or sort latest to earliest
* `Priority` : sort high to low or sort low to high

**Step 2d: Searching for tasks**

The `search` for tasks, type `search` `search_term` `search_field`

Only the `search term` is required.
* Eg: Typing `search meeting` returns a list of tasks that have the name ‘meeting’. Alternatively, 
* Eg: Typing `search meeting @Utown` returns a list tasks that have the name ‘meeting’ situated at Utown
* Eg: Typing `search lab date 120316` returns lab sessions on 12 March 2016

**Step 2e: Deleting tasks**

To `delete` a task on the list that is on the screen, type `delete` [ index of task in the list] 
* eg `delete 1 `

Alternatively, you can type the name of the task  
* eg `delete shopping`

**Step 2f: Postpone**

To `postpone` a task on the list that is on the screen, type `postpone` [index of task in the list or name of task] to [new deadline] 
* eg `type postpone 3 to 120316` or eg `postpone shopping to 120316`

Alternatively, to postpone the task to the next available free slot, by simply 
* typing `postpone 3` or `postpone shopping`.

**Step 2g: editing tasks**

To edit a task type `edit` (index of task in the list or name of task) (field) (changes)
* Eg: Typing `edit 1 date 120316` will change the deadline of the first task to 12 March 2016
* Eg: Typing `edit soccer @Casa` will change the venue of the soccer task to Casa.

**Step 2h : Finding next available time**

When you need to urgently make an appointment, you might need to find the next possible available timeslot. 
* Type `free slots` to return the next available slot. Alternatively, 
* Type `free slots 10` to return the next 10 available free slots.

**Undo:**
`Ctrl` + `Z` for windows or `Command` + `Z` for Macs