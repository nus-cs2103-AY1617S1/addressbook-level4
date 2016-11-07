<!--@@author A0143378Y-->
# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [Special Features](#special-features)
* [FAQ](#faq)
* [Command Summary](#command-summary)
* [Special Command Summary](#special-command-summary)


## Quick Start

1. Ensure you have Java version `1.8.0_60` or later installed in your Computer.  

2. Download the latest `mastermind.jar` from our repository's [releases](https://github.com/CS2103AUG2016-W11-C3/main/releases) tab.
3. Copy the file to the folder you want to use as the home folder for your Mastermind.
4. Double-click the file to start the app. The app should appear in a few seconds.
   > <img src="docs/images/latest_ui.PNG" width="600">

5. Type the command in the command box and press <kbd>Enter</kbd> to execute it.


    > Some example commands you can try:
    >
    >   - `add CS2103T tutorial by tomorrow 11am`
    >       - adds a task named `CS2103T tutorial` with deadline due tomorrow at 11am.
    >   - `delete 1`
    >       - deletes the 1st task shown in the current list.


6. Refer to the [Features](#features) section below for details of each command.  


## Features

### Command Formatting Style

>* Words in `angle brackets < >` are required parameters, fields in `square brackets [ ]` are optional.
>* Fields that are appended with `ellipsis ...` such as `<option_field>...` indicates that the field accept multiple of values.
>* Tasks will be added to the categories (event, deadlines, floating task) according to the keywords to the parameters inputed.
>* Parameters can be in any order.
>* Separate different tags with `comma ,`.
>* There are no limit to the number of tags a task can have.

### Viewing help : `help`

First, let's get familiar with the command features that _Mastermind_ offers! Type `help` and press <kbd>Enter</kbd> to display all the possible command usage.

_Format:_
```java
help
```

> _Quick Tip: Press any key to close the popup!_

### Adding a task: `add`, `do`

For now, an empty list is not very interesting. Try to create a new task using `add` or `do` command. Both commands are equivalent. They exist to help you in constructing a more fluent command structure.

_Mastermind_ automatically helps you to organize your task into three main categories:
- **Event**: Task with `startDate` and `endDate` specified.
- **Deadline**: Task with only `endDate` specified.
- **Floating Task**: Task without `startDate` and `endDate`.

> _Quick Tip: Tag your task so you can find them easier with `find` command!_
> - You can have multiple tags to your tasks! Simply just separate the different tags using a comma without a space.
> - There are no limit to the number of tags a task can have, feel free to tag as many as you like!

#### Adds an event  
_Format:_
```java
(add|do) <task_name> from <start_date> to <end_date> [#comma_separated_tags]
```

_Example:_
```java
> add attend workshop from today 7pm to next monday 1pm #programming,java
```
#### Adds a task with deadline
_Format:_

```java
(add|do) <task_name> by <end_date> [#comma_separated_tags]
```  

_Example:_

```java
> add submit homework by next sunday 11pm #math,physics
```  
#### Adds a floating task
_Format:_
```java
(add|do) <task_name> [#comma_separated_tags]
```  

_Example:_
```java
> do chores #cleaning
```  

#### Adds a recurring deadline
_Format:_
```java
(add|do) <task_name> by <end_date> [daily|weekly|monthly|yearly] [#comma_separated_tags]
```  

_Example:_
```java
> do chores today daily #cleaning
> do workshop from tomorrow 3pm to tomorrow 5pm weekly
```

> _Quick Tip: You can add recurring events too!_
> Use keywords like ```'daily', 'weekly', 'monthly' and 'yearly'``` for recurring tasks.

> _Mastermind_ uses [natural language processing](http://www.ocpsoft.org/prettytime/nlp/) for `<start_date>` & `<end_date>`, therefore it does not enforce any specific date format. Below are the possible list of date constructs that _Mastermind_ accepts:
>
> `tomorrow 6pm`
> `next saturday`
> `13 Oct 2016 6pm`
> `...`
>
> The list above is not exhaustive. Feel free to try any possible combination you have in mind!
>
> If the date you entered is incorrectly parsed, please enter a more explicit combination such as "tomorrow 6pm" instead of "tomorrow at 6".


### Editing a task : `edit`, `update`, `change`
Perhaps now you have a change of schedule, or you are unsatisfied with the task name you just created. _Mastermind_ allows you to quickly modify the task you created before using `edit` command.

_Format:_
```java
(edit|update|change) <index> [name to <name>,] [start date to <start_date>,] [end date to <end_date>,] [recur (daily|weekly|monthly|yearly),] [tags to #<comma_separated_tags>,]
```

>* At least one optional field is required.
>* You can edit as many fields as required.
>* Each field is separated by a comma.
>* You can omit the last `comma ,` if there's no more fields following:
>`edit 1 name to dinner with parents, start date to tomorrow 7pm`
>* Any malformed field will be dropped.

_Examples:_

```java
// Selects the 2nd task in Mastermind and edit the task name to Dinner.
> edit 2 name to parents with dinner, end date to tomorrow 7pm, recur daily, tags to #meal,family
```
```java
// Selects the 1st task and edit the `startDate` to tomorrow 8pm.
> change 1 start date to tomorrow 8pm
```
<!--@@author A0138862W -->

### Completing tasks : `mark`

Mission accomplished! Now you can ask _Mastermind_ to `mark` a task as completed.

_Mastermind_ will archive the task for you. See <kbd>Archives</kbd> tab to review your completed task.

_Format:_
```java
mark <index>
```
```java
mark due
```

> ```mark``` only affects tasks that are not complete yet. It has no effect on completed tasks.

> Using `mark due` it will mark all tasks that are due.

_Examples:_
```java
// use "find" command to look for a specific task
> find CS2010

// select the "find" result and mark the task at index 1 as completed
> mark 1
```

```java
// marks all due tasks in the tab
> mark due
```
### Unmarking a task : `unmark`

Oh no! You realise that one of your task is not complete yet but you have marked it. Not to worry, you can `unmark` that task.

_Format:_
```java
unmark <index>
```

> ```unmark``` only affects task that are completed. It has no effect on an uncompleted task.

_Examples:_
```java
// list all the tasks that are completed
> list archives

// mark task at index 1 as completed
> unmark 1
```

```java
// use "find" command to look for a specific task
> find CS2010

// select the "find" result and mark the task at index 1 as completed
> unmark 1
```


### Deleting a task : `delete`

You just realize Taylor Swift concert is cancelled! Oh no!

Sadly, you have to ask _Mastermind_ to remove the event from your bucket list. _Mastermind_ does the removal for you with a sympathetic pat on your shoulder.

_Format:_
```java
delete <index>
```

>* Deletes the task at the specified `index`.
>* The index refers to the index number shown in the most recent listing.

_Examples:_
```java
// delete entry listed at index 1
> delete 1
```

### Undo a command : `undo`

Suddenly, you received a call that Taylor Swift concert is coming back up! Maybe you should buy the ticket after all.

Don't worry. _Mastermind_ has a built in time machine that helps you travel to the past! You can execute the `undo` command to recover the task you just deleted!

_Format:_
```java
undo
```

> `undo` only affect commands such as `add`, `edit`, `delete`, `mark`, `unmark`. It has no effect on command such as `list`, `find`, `relocate`, `import`, `export`, `history` and `clear`.

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

> You can `undo` as many times as you like.
>
> However, the `undo` takes effect on commands that you entered in the current session only. _Mastermind_ will forget the `undo` history once you close the application.

### Redo a command : `redo`

_Mastermind_ can travel back to the present too! If you ever regret your `undo` command, _Mastermind_ can `redo` command that you just undid.

_Format:_
```java
redo
```
> `redo` is only available immediately after an `undo` command is used.

_Example:_
```java
// Redo the last command being undone. Refer to undo command.
> redo

// returns
// Redo successfully.
// =====Undo Details=====
// [Redo Delete Command] Task delete: Buy ticket for Taylor Swift concert. Tags:
// ==================
```

> You can `redo` as many times as you like.
>
> However, the `redo` takes effect on commands that you entered in the current session only. _Mastermind_ will forget the `redo` history once you close the application.
>
> Upon executing a new command (except `undo` and `redo`), _Mastermind_ will forget any existing command remaining in the `redo` history.



### Listing all tasks of a category: `list`

After adding some tasks into _Mastermind_, you can `list` them according to their category. In addition to the [three main categories](#adding-a-task-add-do) mentioned in `add` command, _Mastermind_ also keeps a summarized view under <kbd>Home</kbd> tab; the <kbd>Archives</kbd> tab is reserved for task marked as completed.

_Format:_
```java
list [tab_name]
```

> Possible values of `tab_name` includes (case-insensitive):
> `Home`
> `Tasks`
> `Events`
> `Deadlines`
> `Archives`
>
> _Quick Tip: You can also press <kbd>Ctrl</kbd> + <kbd>1</kbd>, <kbd>2</kbd>, <kbd>3</kbd>, <kbd>4</kbd> or <kbd>5</kbd> to switch to the respective tabs._

<!--@@author A0124797R-->

### Finding tasks that contain any keywords: `find`

Even with the list category feature, scrolling through possibly thousands of tasks to find a specific one can be quite troublesome. Fret not! _Mastermind_ provides you with the `find` command to quickly search for a specific task your are looking for.

`find` command displays a result of all tasks whose description contain any of the given keywords.

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
> using find will always switch back to the home tab.

### Finding all tasks that contain specific tags: `findtag`

You have tagged your tasks previously and now you want to know what tasks are in that category.
you can use `findtag` command to quickly search for tasks that are tagged with that specific tag.

`findtag` command display result of all tasks whose tags contain the given keywords.

_Format:_

```java
findtag <keyword>...
```

>* The search is case-sensitive.
>* The order of the keywords does not matter.
>* Tasks matching at least one keyword will be returned.

_Examples:_

```java
// returns Dinner on 10/10/16 at 1900hrs (Date)
> findtag date
```  


### Show upcoming tasks : `upcoming`

What if you have too many task to do and only want to know tasks that are going to be due. You can ask _Mastermind_ to list `upcoming` tasks!

_Format:_
```java
upcoming [tab_name]
```

> * Shows all floating tasks as well if there is no `[tab_name]` included.
> * Does not show tasks that are already due.

_Examples:_
```java
// list all tasks that are due within a weeks time.
> upcoming
```
```java
// list all deadlines that are due within a weeks time.
> upcoming deadlines
```



### Changing save location : `relocate`

_Mastermind_ allows you to relocate the save file to a new destination folder.

_Format:_
```
relocate <new_folder_destination>
```

_Example:_
```java
// save file has been relocated to new destination folder at ~/document/mastermind
> relocate ~/document/mastermind
```

> You can relocate into a cloud shared folder to access your save data from any other computer!
>
> Remember to input a folder and not a file!

### Importing file : `import`

_Mastermind_ allows you to import file from other to do list into _Mastermind_.

Currently _Mastermind_ supports _.csv_ and _.ics_ file.

> _.csv_ must be compliant to Google Calendar [specification](https://support.google.com/calendar/answer/37118?hl=en).

_Format:_
```
import from <file location>
```

_Example:_
```java
// import success
> import from ~/document/mastermind/data.ics
```

### Exporting data: `export`

_Mastermind_ can assist you in exporting your data to _.csv_ format too. The _.csv_ file is fully compatible with Google Calendar so you can use it to synchronize with your personal calendar.

_Format:_
```java
export [tasks] [deadlines] [events] [archives] to <destination_file_path>
```

_Example:_
```java
// export all data to personal folder
> export to C:\Users\Jim\mastermind.csv
```

```java
// export only deadlines and events data to personal folder
> export events deadlines to C:\Users\Jim\mastermind.csv
```
<!--@@author A0139194X-->

### Recall your action history: `history`

_Mastermind_ remembers the list of commands you executed. If you ever forget what you've executed hours ago or are unsure of what you can `undo` or `redo`, you can ask _Mastermind_ to show you the history.

_Format:_
```java
history
```

_Example:_
```java
> history
```

> * _Quick Tip: You can also click on the status bar above the command field to open up your action history._
> * Clicking on the history entry will display the result of that particular command you executed on the right panel.
> * The command will toggle the status bar open or close.

<!--@@A0143378Y-->

### Clearing all entries: `clear`

Want to clear everything? _Mastermind_ can do some spring cleaning for you in all categories.

_Format:_
```java
clear
```

_Example:_
```java
// All tasks in all categories are deleted
> clear
```
> `clear` command cannot be undone. Make sure you really mean to discard everything on _Mastermind_!


### Exiting the program : `exit`

_Mastermind_ says, "Goodbye!"

Exits the application.

> Worried that you have not saved your file? take a look [here](#faq)

_Format:_
```
exit
```  

_Example:_
```java
// Mastermind says, "Goodbye!"
> exit
```



## Special Features

### Autocomplete
_Mastermind_ is smart. While typing into _Mastermind_, it will prompt you on commands that contains letters you input. You can press <kbd>Enter</kbd> to complete the sentence.
_Mastermind_ will also learn the inputs you type for easier typing in the future. You can press <kbd>Esc</kbd> to stop the instance of Autocomplete.


### Repeating a previous command: <kbd>↑</kbd>

Lazy to retype a similar command? Want to paste the previous command back to the field?  
_Mastermind_ can do just that!

> You can go back as many previous command as you want.
> Similarly, you can press <kbd>↓</kbd> to get the next command.

_Format:_
<kbd>↑</kbd> (Up arrow key)


## FAQ

**Q**: How do I transfer my data to another Computer?  
**A**: Install the application in the other computer and overwrite the empty mastermind.xml file it creates with the mastermind.xml file that contains the data of your previous Mastermind.
Alternatively you can use the export and import function. However if you use this method, the tags will not be added to the new application.

**Q**: Is my data secure?  
**A**: Your data is stored locally on your hard drive as a .xml file. Your data is as secure as your computer

**Q**: Where is the <kbd>save</kbd> button or command?  
**A**: Mastermind's data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.


## Command Summary

Command | Format  
-------- | :--------
[Help](#viewing-help--help) | `help`
[Add, Do](#adding-a-task-add-do) | <code> (add&#124;do) <task_name> from [start_date] to [end_date] [daily&#124;weekly&#124;monthly&#124;yearly] [#comma_separated_tags]</code>
[Edit, Update, Change](#editing-a-task--edit-update-change) | <code>(edit&#124;update&#124;change) <index> [name to <name>,] [start date to <start_date>,] [end date to <end_date>,] [recur (daily&#124;weekly&#124;monthly&#124;yearly),] [tags to #<comma_separated_tags>]</code>
[Mark](#completing-tasks--mark) | <code>mark <index&#124;due></code>
[Unmark](#unmarking-a-task--unmark) | `unmark <index>`
[Delete](#deleting-a-task--delete) | `delete <index>`
[Undo](#undo-a-command--undo) | `undo`
[Redo](#redo-a-command--redo) | `redo`
[List](#listing-all-tasks-of-a-category-list) | `list [tab_name]`
[Find](#finding-tasks-that-contain-any-keywords-find) | `find <keyword>...`
[Find tag](#finding-all-tasks-that-contain-specific-tags-findtag) | `findtag <keyword>...`
[Upcoming](#show-upcoming-tasks--upcoming) | `upcoming [tab_name]`
[Relocate](#changing-save-location--relocate) | `relocate <new_destination_folder>`
[Import](#importing-file--import-from) | `import from <file_location>`
[Export](#exporting-data-export) | `export [tasks] [deadlines] [events] [archives] to <destination_file_path>`
[History](#recall-your-action-history-history) | `history`
[Clear](#clearing-all-entries-clear) | `clear`
[Exit](#exiting-the-program--exit) | `exit`


## Special Feature Summary

Command | Format  
-------- | :--------
[Autocomplete](#autocomplete) | <kbd>Enter</kbd>
[Previous](#repeating-a-previous-command-↑) | <kbd>↑</kbd>
