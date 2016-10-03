## Contents

- [1. Quick Start](#1-quick-start)
- [2. Features](#2-features)
- [3. Command Summary](#3-command-summary)

## 1. Quick Start

1. **Install Java 8 Update 60**<br>
The latest version is available [here](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

2. **Download Amethyst task manager**<br>
Save the latest `.jar` file from the [releases](dummy link) tab to a folder of your choice.

3. **Launch the program**<br>
Double-click the file to start Amethyst. You will see the Graphical User Interface (GUI) below.

4. **Enter a command**<br>
To see a list of all available commands, type `help` and press <kbd>Enter</kbd>.

5. **Try some commands**
    - `add event=dinner with wife d=25/12/16 t=7 pm - 9 pm`
    Add an event with name 'dinner with wife' from 7 pm to 9 pm on 25th December 2016.
    - `list type=deadline`
    See all tasks that are deadlines arranged earliest first.
    - `find lab homework, boy`
    See all tasks with keywords "lab homework" or "boy" in their names.

<br>
## 2. Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Single quotes `' '` and doubel quotes `" "` are interchangeable. Each may be nested within the other.
> * A pipe `|` between items indicates an either-or relationship between them.
> * Only one item in curved brackets separated by a pipe `(item1 | item2)` may be used. All items within the brackets have equivalent functionality. They do _not_ indicate optionality.
> * Items with `...` after them can have multiple instances.
> * An example of the date format dd-mm-yy is 25-12-16. An example of dd-MMM-yy is 03-Mar-15.
> * An example of the time format hh:mm is 18:30. An example of hh:mmpm is 06:30pm.
> * The order of parameters is not fixed.


<br>
#### 2.1. Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`


<br>
#### 2.2. Adding a task: `add`
Adds a task to the address book. Three different types of tasks are supported.<br>
##### Events
Format: `add event="NAME" (t="hh:mm-hh:mm" | t="hh:mmam-hh:mmpm") (d="dd-mm-yy" | d="dd-MMM-yy")`
##### Deadlines
Format: `add deadline="NAME" (t="hh:mm" | t="hh:mmpm") (d="dd-mm-yy" | d="dd-MMM-yy")`
##### Floating tasks
Format: `add float="NAME"`

Examples:
* `add event="dinner with wife" d="25-12-16" t="7:00pm-9:00pm"`
* `add deadline='Lab Report' t='16:00' d='03-Mar-15'`
* `add float="Read EL James' book 50 Shades of Grey"`
* `add float="Learn "artistic" sarcasm"` is invalid since double quotes are used both to enclose the task name and within the name itself.


<br>
#### 2.3. Listing tasks: `list`
Shows a numbered list of tasks, filtered by optional parameters.<br>
Format: `list [d="dd-mm-yy"] [type="TASK_TYPE"] [done="true"|"false"] [t="dd-mm-yy"]`

> The 3 valid types are "event", "deadline" and "float".

Example:
* `list type="float" done="false"`


<br>
#### 2.4. Finding all tasks containing any keyword in their name: `find`
Finds tasks in which the name contains any of the given keywords.<br>
Format: `find "KEYPHRASE_WORD_1 KEY_PHRASE_WORD_2" ["MORE_KEYPHRASES"...]`

> * Keyphrases are bounded by quotes
> * The search is _case-insensitive_. e.g `"hANs bo"` will match `"Hans Bo"`
> * The order of the keywords within each phrase matters. e.g. `"Hans Bo"` will not match `"Bo Hans"`
> * The order of the keyphrases does not matter. e.g. `"Bo", "Hans"` will match `"Hans Swagtacular Bo"`
> * Only the name is searched.
> * Partial phrases will be matched e.g. `"ns B"` will match `"Hans Bo"`

Examples:
* `find 'meeting'`<br>
  Returns `'Meeting with John'`
* `find "Physics test" "chemistry" "biology"`<br>
  Returns any task containing `'Physics test'`, `'chemistry'`, or `'biology'`


<br>
#### 2.5. Deleting a task: `delete`
Deletes the specified tasks from the task manager. <br>
Format: `delete INDEX [MORE_INDICES...]`

> Deletes the person at the specified INDICES.
  The indices refers to the index numbers shown in the most recent listing.<br>
  The indices **must be positive integers** 1, 2, 3, ...

Examples:
* `list`<br>
  `delete 2 4`<br>
  Deletes the 2nd and 4th tasks in the task manager.
* `find birthday`<br>
  `delete 1`<br>
  Deletes the 1st birthday task in the results of the `find` command.


<br>
#### 2.6. Marking a task as done: `done`
Marks the specified tasks as done. <br>
Format: `done INDEX [MORE_INDICES...]`

Examples:
See 'Deleting a task'.


<br>
#### 2.7. Updating a task: `update`
Overwrites specified attributes of the specified task. <br>
Format: `update INDEX [name="NEW_NAME"] [t="hh:mm"] [d="dd-mm-yy"] [done=true|false]`

Example:
* `list type="event"` <br>
  `update 1 name='Hamlet at The Globe Theatre' t='08:00pm-11:00pm'` <br>
  Updates the name of 1st task listed to 'Hamlet at The Globe Theatre' and its time period to '08:00pm-11:00pm'.


<br>
#### 2.8. Undoing the last operation: `undo`
Takes the program to a state where the last operation performed did not occur. <br>
Format: `undo`

> The command can be called repeatedly and will undo all operations up to
> and including the first operation performed upon starting the program.
> Calling `undo` subsequently will print an error to the console and no change to the data will occur.


<br>
#### 2.9. Clearing all entries: `clear`
Clears all entries from the task manager. <br>
Format: `clear`


<br>
#### 2.10. Setting the data storage location: `set-storage`
Saves all task data to the specified folder. <br>
Format: `set-storage "FILEPATH"`

> Existing data will be moved to the new folder.

Example:
`set-storage "C:\Users\Liang\Desktop"`


<br>
#### 2.11. Setting an alias for existing commands: `add-alias`
Adds a new shortcut for existing commands. <br>
Format: `add-alias "COMMAND_ALIAS"="COMMAND_PHRASE"`

> On pressing enter, the entire string specified on the right-hand side of the equals sign will replace the alias.
> If an alias is typed within quotes, however, it will _not_ be replaced.

Examples:
* `add-alias 'del'='delete'` <br>
  The command `del` can be used in place of `delete`.
* `add-alias "addfl "="add float="` <br>
  The command `addfl "Clean the garage"` can be used in place of `add float="Clean the garage"`. <br>
  However, `add deadline="buy addfl a cake" t="4:00pm" d="12-Oct-16` does not become `add deadline="buy add float= a cake" t="4:00pm" d="12-Oct-16"`, since `addfl` was enclosed by quotation marks.


<br>
#### 2.12. Listing aliases: `list-alias`
Shows a numbered list of all configured aliases. <br>
Format: `list-alias`


<br>
#### 2.13. Deleting an alias: `delete-alias | remove-alias`
Removes previously set aliases. <br>
Format: `(delete-alias | remove-alias) INDEX [MORE_INDICES]`

Example:
* `list-alias`
* `remove-alias 2 3` <br>
  Deletes the 2nd and 3rd aliases output by the `list-alias` command.


<br>
#### 2.14. Exiting the program: `exit`
Exits the program. <br>
Format: `exit`


## 3. Command Summary

| Command         | Format         |
|-----------------|:-----------------|
|add event          |`add event="NAME" (t="hh:mm-hh:mm" | t="hh:mmam-hh:mmpm") (d="dd-mm-yy" | d="dd-MMM-yy")`|
|add deadline       |`add deadline="NAME" (t="hh:mm" | t="hh:mmpm") (d="dd-mm-yy" | d="dd-MMM-yy")`|
|add float          |`add float="NAME"`|
|list               |`list [d="dd-mm-yy"] [type="TASK_TYPE"] [done="true"|"false"] [t="dd-mm-yy"]`|
|find               |`find "KEYPHRASE_WORD_1 KEY_PHRASE_WORD_2", ["MORE_KEYPHRASES"...]`|
|delete             |`delete INDEX [MORE_INDICES...]`|
|update             |`update INDEX [name="NEWNAME"] [t="hh:mm"] [d="dd-mm-yy"] [done=true|false]` |
|mark done          |`done INDEX [MORE_INDICES...]`|
|undo               |`undo`     |
|clear              |`clear`|
|set storage location|`set-storage "FILEPATH"`|
|add command alias          |`add-alias "COMMAND_ALIAS"="COMMAND_PHRASE"`|
|delete command alias|`(delete-alias | remove-alias) "COMMAND_ALIAS"`|
|help               |`help`|
|exit        |`exit`|
