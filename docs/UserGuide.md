# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

1. Ensure you have Java version `1.8.0_60` or later installed in your Computer.  

2. Download the latest `mastermind.jar` from our repository's [releases](https://github.com/CS2103AUG2016-W11-C3/main/releases) tab.
3. Copy the file to the folder you want to use as the home folder for your Mastermind.
4. Double-click the file to start the app. The app should appear in a few seconds.
   > <img src="https://github.com/CS2103AUG2016-W11-C3/main/blob/master/docs/images/Ui.png" width="600">

5. Type the command in the command box and press <kbd>Enter</kbd> to execute it.
   e.g. typing `help` and pressing <kbd>Enter</kbd> will open the help window.

6. Some example commands you can try:

   - `add 'CS2103T tutorial' ed/'tomorrow 11am'`
       - adds a task named `CS2103T tutorial` with deadline due tomorrow at 11am
   - `delete 1`
       - deletes the 1st task shown in the current list
   - `exit`
       - exits the app

7. Refer to the [Features](#features) section below for details of each command.  


## Features

### Command Formatting Style

>* Words in `angle brackets < >` are the parameters, items in `square brackets [ ]` are optional
>* Fields that appended with `ellipsis ...` such as `<option_field>...` indicates that the field accept multiple of values
>* Tasks will be added to the categories (event, deadlines, floating task) according to the keywords (`add`, `do`)
>* Parameters can be in any order
>* Separate different tags with `comma ,`
>* There are no limit to the number of tags a task can have

### Viewing help : `help`

First, let's get familiar with the command features that _Mastermind_ offers! Type `help` to display all the possible command usage.

_Format:_
```java
help
```

> Help is also shown if you enter an invalid command e.g. `abcd`

### Adding a task: `add`, `do`

For now, an empty list is not very interesting. Try to create a new task using `add` or `do` command. Both commands are equivalent. They exist to help you in constructing a more fluent command structure.

_Mastermind_ helps you to organize your task into three main categories:
- **Event**: Task with `startDate` and `endDate` specified.
- **Deadline**: Task with only `endDate` specified.
- **Floating Task**: Task without `startDate` nor `endDate`.

> Quick Tip: Tag your task so you can find them easier with `findtag` command!

#### Adds an event  
_Format:_
```java
(add|do) '<name>' [sd/'<start_date>'] [ed/'<end_date>'] [t/'<comma_separated_tags>...']
```

_Example:_
```java
> add 'attend workshop' sd/'today 7pm' ed/'next monday 1pm' t/'programming,java'
```
#### Adds a task with deadline
_Format:_

```java
(add|do) '<name>' [ed/'<end_date>'] [t/'<comma_separated_tags>...']
```  

_Example:_

```java
> add 'submit homework' ed/'next sunday 11pm' t/'math,physics'
```  
#### Adds a floating task
_Format:_
```java
(add|do) '<name>' [t/'<comma_separated_tags>...']
```  

_Example:_
```java
> do 'chores' t/'cleaning'
```  

#### Adds a recurring Deadline
_Format:_
```java
(add|do) [r/'recurrence'] <name>' [ed/'<end_date>'] [t/'<comma_separated_tags>...']
```  

_Example:_
```java
> do r/'daily' 'chores' ed/'today' t/'cleaning'
> do r/'weekly 2' 'workshop' sd/'tomorrow 3pm' ed/'tomorrow 5pm'
``` 

> Quick Tip: You can add recurring events too!  
> 'weekly 2' will repeat the Task only twice
> Use keywords like 'weekly', 'biweekly', 'monthly' and 'yearly' for recurring tasks  

> Mastermind uses [natural language processing](http://www.ocpsoft.org/prettytime/nlp/) for `startDate` & `endDate`, therefore it does not enforce any specific date format. Below are the possible list of date construct that Mastermind accepts:
>
> `three days from now`
> `tomorrow 6pm`
> `next saturday`
> `13 Oct 2016 6pm`
> `...`
>
> The list above is not exhaustive. Feel free to try any possible combination you have in mind!
>
> If the date you entered is incorrectly parsed, you are encouraged to enter a more explicit combination such as "tomorrow 6pm" instead of "tomorrow at 6".

### Listing all tasks of a category: `list`

After adding some task into _Mastermind_, you can `list` them according to their category. In addition to the [three main categories](#adding-a-task-add-do) mentioned in `add` command, _Mastermind_ also keeps a summarized view under <kbd>Home</kbd> tab; the <kbd>Archive</kbd> tab is reserved for task marked as completed.

_Format:_
```java
list <category_name>
```

> Possible values of `category_name` includes (case-insensitive):
> `Home`
> `Tasks`
> `Events`
> `Deadlines`
> `Archives`

### Finding all tasks containing any keyword in their description: `find`

Even with the list category feature, scrolling through possibly thousands of tasks to find a specific one can be quite troublesome. Fret not! _Mastermind_ provides you with `find` command to quickly search for a specific task your are looking for.

`find` command display result of all tasks whose description contain any of the given keywords.

_Format:_

```java
find <keyword>...
```

>* The search is case-insensitive.
>* The order of the keywords does not matter.
>* Tasks matching at least one keyword will be returned.

_Examples:_

```java
// returns Dinner on 10/10/16 at 1900hrs (Date)
> find Dinner
```  
```java
// returns CS2010 PS10 on 11 Oct by 1000hrs (Assignment)
> find "cs2010 ps10"
```  

### Editing a task : `edit`
Perhaps now you have a change of schedule, or you are unsatisfied with the task name you just created. _Mastermind_ allows you to quickly modify the task you created before using `edit` command.

_Format:_
```java
edit <index> '<name>' [sd/'<start_date>'] [ed/'<end_date>'] [t/'<comma_separated_tags>...']
```

>* At least one optional parameter is required.
>* can edit as many parameters as required

_Examples:_

```java
// Selects the 2nd task in Mastermind and edit the task name to Dinner.
> edit 2 'Dinner'
```
```java
// Selects the 1st task and edit the `startDate` to tomorrow 8pm.
edit 1 sd/'tomorrow 8pm'
```


### Deleting a task : `delete`

You just realize Taylor Swift concert is cancelled! Oh no!

Sadly you have to ask _Mastermind_ to remove the event from your bucket list. _Mastermind_ does the removal for you with a sympathetic pat on your shoulder.

_Format:_
```java
delete <index>
```

>* Deletes the task at the specified `index`.
>* The index refers to the index number shown in the most recent listing.

_Examples:_
```java
// list all the task
> list

// delete entry listed at index 2
> delete 2
```

```java
// find all the task with keyword, "taylor, swift, concert"
> find taylor swift concert

// delete first entry listed after "find" command
> delete 1
```

### Undo a command : `undo`

Suddenly, you just received a call that Taylor Swift concert is coming back up! Maybe you should buy the ticket after all.

Don't worry. _Mastermind_ has built in time machine that helps you travel to the past! It happily execute the `undo` command to recover the task you just deleted!

_Format:_
```java
undo
```

> `undo` only affect commands such as `add`, `edit`, `delete`, etc, that make changes to the _Mastermind_ storage. It has no effect on command such as `list`, `find`, `relocate` and `clear`.

Example:
```java
// deleted the task: "Buy ticket for Taylor Swift concert"
> delete 1

// Undo the last action
> undo

// returns
// Undo successfully.
// =====Undo Details=====
// [Undo Delete Command] Task added: Buy ticket for Taylor Swift concert. Tags:
// ==================
```

> You can `undo` as many time as you like to the earliest command you entered into _Mastermind_
>
> However, the `undo` takes effect on commands that you entered in the current session only. _Mastermind_ will forget the `undo` history once you close the application.

### Redo a command : `redo`

_Mastermind_ can travel back to the present too! If you ever regretted your `undo` command, _Mastermind_ can `redo` command that you just undone.

_Format:_
```java
redo
```
> `redo` only available immediately after an `undo` command is used.

_Example:_
```java
// Redo the last command being undone. See `undo`[#undo-a-command] command.
> redo

// returns
// Redo successfully.
// =====Undo Details=====
// [Redo Delete Command] Task delete: Buy ticket for Taylor Swift concert. Tags:
// ==================
```

> You can `redo` as many time as you like to the latest command you entered into _Mastermind_
>
> However, the `redo` takes effect on commands that you entered in the current session only. _Mastermind_ will forget the `redo` history once you close the application.
>
> Upon executing a new command (except `undo` and `redo`), _Mastermind_ will forget any existing command remain in the `redo` history.

### Completing tasks : `mark`

Mission accomplished! Now you can ask _Mastermind_ to `mark` a task as completed.

_Mastermind_ will archive the task for you. See <kbd>Archive</kbd> tab to review your completed task.

_Format:_
```java
mark <index>
```

> ```mark``` only affects task that are not complete yet. It has no effect on completed task.

_Examples:_
```java
// list all the task that are not completed
> list

// mark task at index 1 as completed
> mark 1
```

```java
// use "find" command to look for a specific task
> find CS2010

// select the "find" result and mark the task at index 1 as completed
> mark 1
```
### Repeating a previous command: <kbd>Up arrow</kdb>

Lazy to retype a similar command? Want to paste the previous command back to the field?  
_Mastermind_ can do just that!

_Format:_
``` <kbd>Up arrow</kdb>
```

_Example:_
```java
// Successfully loaded previous input
```

### Clearing all entries: `clear`

Want to clear everything? _Mastermind_ can do a spring cleaning for you in all categories.

_Format:_
```java
clear
```

_Example:_
```java
// All tasks in categories are deleted
> clear
```

### Changing save location : `relocate`

_Mastermind_ helps you relocates the save file to a new destination foler.

_Format:_
```
relocate <new_folder_destination>
```

_Example:_
```java
// save file has been relocated to new destination folder at ~/document/mastermind
> relocate ~/document/mastermind
```

### Exiting the program : `exit`

_Mastermind_ says, "Goodbye!"

Exit the application.

_Format:_
```
exit
```  

_Example:_
```java
// Mastermind says, "Goodbye!"
> exit
```

## FAQ

**Q**: How do I transfer my data to another Computer?  
**A**: Install the application in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Mastermind.

**Q**: Is my data secure?  
**A**: Your data is stored locally on your hard drive as a .xml file. Your data is as secure as your computer

**Q**: Where is the <kbd>save</kbd> button or command?  
**A**: Mastermind's data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.


## Command Summary

Command | Format  
-------- | :--------
Help | `help`
Add, Do | <code>(add &#124; do) [r/'&lt;reccurence&gt;'] '&lt;taskName&gt;' [sd/'&lt;start_date&gt;'] [ed/'&lt;end_date&gt;'] [t/'&lt;comma_separated_tags&gt;'];</code>
List | `list [<category_name>]`
Find | `find <keyword>...`
Find Tag | `findtag <keyword>...`
Edit | `edit <index>`
Delete | `delete <index>`
Undo | `undo`
Redo | `redo`
Mark | `mark <index>`
Previous | `p`
Clear | `clear`
Relocate | `relocate <new_destination_folder>`
Exit | `exit`
