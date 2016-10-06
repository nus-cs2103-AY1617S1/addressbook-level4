# Sudowudo User Guide

## Quick Start
<!-- for getting the thing to actually work -->
<!-- need to wait on our refactoring for addressbook-level4 -->
1. Ensure you have Java version `1.8.0_60` or later installed.
2. Download the latest `sudowudo.jar` from the releases tab.
3. (work in progress)

## Features
<!-- each individual command -->
In the following sections, we outline the format of commands to use `Sudowudo`. Tags such as `date` are placeholders in the command syntax and denoted as *fields*.

### Getting Help
`Sudowudo` also stores documentation that can be accessed from its command-line interface using the following command.

```bash
# format
help           # for general how-to-use help
help command   # for command-specific help
```

```bash
# examples
help add       # command-specific help for add
help list       # command-specific help for list
```
### Field Formats

#### `descriptors`
Descriptors are words/phrases used for identification, such as for the name of an event or for an event's description. Quote marks are used to denote a descriptor.

```bash
"Dental Appointment" # valid
Dental Appointment   # invalid
```

#### `event_id`
Events can be assigned a numbers as an *identifier*. Each event/task has a unique `event_id`. The identifier does not need to be enclosed in quote marks.

#### `time`
Time is always expressed on a 24-hour clock, omitting the trailing "h".

```bash
1400     # 2pm (valid)
1400h    # invalid!
09:30    # invalid!
08:00pm  # invalid!
```

#### `date`
Dates can be expressed in multiple ways: naturally or using slash notation.

##### Natural Dates
`date` can be expressed in a natural manner like in normal speech. The format for this is in the form of `day month year` or `day month`. These fields are separated by whitespace.

`month` can be abbreviated to the first three letters, e.g. `October` and `Oct` are equivalent.

Omitting the year will mean that the date will be implicitly assumed to be the current year.


##### Slash Notation
`date` can also be expressed using numerics and slashes in the format `dd/mm/yyyy`. Leading zeroes in the date or month can be omitted.

##### Examples of `date`
```bash
14 October 2016  # valid natural form
14 October       # valid natural form (implicitly 2016)
14/10/2016       # valid slash notation
08/01/2016       # valid slash notation
8/1/2016         # valid slash notation
8/01/2016        # valid slash notation
```

### Notes on Syntax Examples
In the remainder of this section, note the following:

1. Fields that are in uppercase are *user parameters*.
2. The order of parameters in command formats is fixed.

### Adding an Event
#### Start and End Times
For an event with a definite start and end time, you can use the following syntax to add this event.

```bash
# format
add EVENT_NAME from START_TIME to END_TIME on DATE
```

Fields: [`EVENT_NAME`](#descriptors), [`START_TIME`](#time), [`END_TIME`](#time), [`DATE`](#date)

```bash
# examples
add "Dental Appointment" from 1200 to 1600 on 14 October 2016
add "Dental Appointment" from 1200 to 1600 on 14 October # implicitly current year
add "Dental Appointment" from 1200 to 1600 on 14/10/2016
add "Dental Appointment" from 1200 to 1600 on 1/8/2016
```

#### Deadlines
For a task with no definite start time but a definite end time (e.g. a homework assignment), you can use the following syntax.

```bash
# format
add TASK_NAME by TIME on DATE # with date and time
add TASK_NAME by DATE         # no definite time
add TASK_NAME by TIME         # no definite date
```

Fields: [`TASK_NAME`](#descriptors), [`TIME`](#time), [`DATE`](#date)

```bash
# examples
add "CS2103 Tutorial 6" by 7 October
add "CS2103 Peer Feedback" by 2359 on 27 Sep
```

#### Floating Tasks
Floating tasks do not have a definite start or end time.

```bash
# format
add TASK_NAME
```
Fields: [`TASK_NAME`](#descriptors)

```bash
# examples
add "Schedule CS2103 Consult" 
```

#### Blocking Slots
Some tasks/events do not have a definite name or description, e.g. simply marking busy periods. The `DATE` field is optional; omitting it implies that the period to block is today.

```bash
# format
block START_TIME to END_TIME on DATE
block START_TIME to END_TIME         # implicitly today
```
Fields: [`START_TIME`](#time), [`END_TIME`](#time), [`DATE`](#date)

```bash
# examples
block 1600 to 1800
block 0800 to 1300 on 5/10/2016
```

### Updating an Event
#### Editing Event Details
Sometimes it is necessary to change the details of your event because life.

```bash
# format
edit FIELD_NAME for EVENT_NAME to NEW_DETAIL
edit FIELD_NAME for EVENT_ID to NEW_DETAIL
```
Fields: [`FIELD_NAME`](), [`EVENT_NAME`](#descriptors), [`EVENT_ID`](#event-id), `NEW_DETAIL`

```bash
# examples
edit start_time for "Dental Appointment" to 1600
edit date for "CS2103 Consult" to 29 October
```

#### Marking as Complete
```bash
# format
done EVENT_NAME
done EVENT_ID
```
Fields: [`EVENT_NAME`](#descriptors), [`EVENT_ID`](#event-id)

```bash
# examples
done "Dental Appointment"
done 124235
```

### Deleting an Event
You can delete an event using its name. This is not the same as marking an event as complete (see [Marking as Complete](#marking-as-complete)).

```bash
# format
delete EVENT_NAME
delete EVENT_ID
```
Fields: [`EVENT_NAME`](#descriptors), [`EVENT_ID`](#event-id)

```bash
# examples
delete "CS2103 Tutorial 3"
delete 124294              # deletes event with ID 124294
```

### Searching for an Event
#### Searching by Keyword
You can search for specific events using keywords. The keywords are case-insensitive and can be simply part of the event name.
```bash
# format
find KEYWORD
find EVENT_ID
```
Fields: [`KEYWORD`](#descriptors), [`EVENT_ID`](#event-id)
```bash
# examples
find "cake"
find 12093
```

### Enumerating Tasks
You can enumerate a list of all the events, sorted alphabetically or chronologically.
```bash
list        # lists all events by name in chronological order
list -l     # lists all events' name and details in chronological order
```

### Next Thing to Do
What good is a productivity app if it doesn't boss you around and tell you what to do?

`next` can be used to return the next upcoming task, and can be chained successively to return the `n`-th upcoming task.

```bash
next           # returns next task
next next      # returns the 2nd upcoming task
next next next # returns the 3rd upcoming task
```

### Undoing
Use the `undo` command to undo the most recent action.
```bash
undo
```

## Expected Functionality

- CRUD (i.e., Create, Read, Update, Delete) support for tasks.
- Support for multiple task types:

| Type | Description |
|------|-------------|
|Events|has a definite start and end time.|
|Deadlines|tasks that have to be done before a specific deadline.|
|Floating tasks |tasks to be completed ‘someday’.|
|Blocks|timeslots that are simply marked as blocked/busy with no definite name, deadline or start/end times.|

- Simple search: A simple text search for finding an item if the user remembers some keywords from the item description. (start with a single parameter)
  - Searching tasks by keyword
- Enumerating tasks.
  - In chronological order
  - In alphabetical order
- Finding the next most urgent thing to do.
- Some way to keep track of which items are done and undone.
- Undo operations
