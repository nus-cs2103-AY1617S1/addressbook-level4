# Product survey on Todo.txt/Todo.sh

## Introduction

Todo.txt, as its core, is just a
[file format](https://github.com/ginatrapani/todo.txt-cli/wiki/The-Todo.txt-Format).
Due to the simplicity of its file format, an ecosystem of
[alternative applications](https://github.com/ginatrapani/todo.txt-cli/wiki/Other-Todo.txt-Projects)
have sprung up around it. In this document, we will only be focussing on the
[reference CLI implementation](https://github.com/ginatrapani/todo.txt-cli) of
Todo.txt, called todo.sh.

## Data model
Reference: [The todo.txt file format](https://github.com/ginatrapani/todo.txt-cli/wiki/The-Todo.txt-Format)

At its core, the todo.txt file format has support for the following pieces of
information for each task:

* Name `<String>` -- The name of the task. It cannot contain newlines. To
  prevent the task from being misinterpreted as a completed task, it should not
  start with the character ‘x’. Other than that, all characters are allowed.

* Priority `<Enum>` (optional) -- An uppercase character from A-Z (26
  priorities)

* Creation date `<Date>` (optional)

* Complete `<Boolean>` -- Whether the task is completed or not.

Additionally, words that start with `+` in a task name are treated as projects
which the task belongs to. Words that start with `@` in a task name are treated
as contexts which the task belongs to. As such, project and context names
cannot contain any whitespace.

There are 2 task lists: `todo.txt` and `done.txt`. `todo.txt` is used to store
the active tasks, while `done.txt` is used to store tasks that are completed
and have been archived.

## Keyboard-oriented

**(Positive)** Todo.sh is a fully keyboard-oriented terminal command-line
application. It accepts input as command-line arguments or from stdin, outputs
to stdout/stderr and then exits.

As such, commands can be entered in and executed in "one shot", fulfilling
Jim’s requirements.

## Works offline

**(Positive)** Todo.sh only operates on plain files on the local filesystem. It
does not connect to the Internet at all, and thus Jim can work offline with it.

## Cannot be summoned quickly

Todo.sh is a terminal command-line application that accepts input directly from
its command-line arguments. As such, it could be argued that it can be summoned
quickly, provided that Jim is in the terminal.

**(Negative)** Jim, unfortunately, is a Windows user. Terminal command line
applications cannot be summoned quickly on Windows, so Todo.sh fails to satisfy
this requirement.

## CRUD for Floating Tasks

Todo.txt/todo.sh has full support for the create-read-update-delete(CRUD) of
floating tasks.

### Creating Floating tasks

#### Adding a floating task

Floating tasks can be created with the `add/a` command.

Add a simple task:

	$ todo add Make peace between Cylons and humans
	1 Make peace between Cylons and humans
	TODO: 1 added.

Note that for multiple whitespace to be preserved, the task name must be quoted:

	$ todo add ‘Make peace between     Cylons and humans’
	1 Make peace between     Cylons and humans
	TODO: 1 added

Leading and trailing whitespace is preserved. All embedded newlines are
automatically and silently stripped. Other than that, all characters are
allowed. In terms of allowable characters in task names, the todo.txt format is
very flexible.

#### Adding a floating task that belongs to a `+project`

A project is just a word that starts with the `+` character. It can appear
anywhere, in any order, in the task name.

Add a task with a project name:

	$ todo a "Upgrade jump drives with Cylon technology +GalacticaRepairs"
	2 Upgrade jump drives with Cylon technology +GalacticaRepairs
	TODO: 2 added.

#### Adding a floating task that belongs to a `@context`

A context is just a word that starts with the `@` character. It can appear
anywhere, in any order, in the task name.

Add a task with a context:

	$ todo add "Check for DRADIS contact @CIC"
	3 Check for DRADIS contact @CIC
	TODO: 3 added.

#### Adding a floating task with a priority

Adding a task with priority `A`:

	$ todo add "(A) My Task"
	2 (A) My Task
	TODO: 2 added.

However, the priority specifier `(A)` must be FIRST. So the add command is not
that flexible.

#### Adding multiple tasks at once

Bringing things one step further, todo.sh allows Jim to add multiple tasks in
one shot:

	$ todo addm ‘Task 1
	Task 2’
	1 Task 1
	TODO: 1 added.
	2 Task 2
	TODO: 2 added.

Tasks are separated by newlines. Shell quoting is required, and thus this
command format is not really user friendly.

### Read floating tasks / simple search

#### Listing and filtering tasks (`list|ls`)

List all tasks:

	$ todo list
	3 Check for DRADIS contact @CIC
	1 Make peace between Cylons and humans
	2 Upgrade jump drives with Cylon technology +GalacticaRepairs
	--
	TODO: 3 of 3 tasks shown

By default, tasks are sorted by their name. This behaviour can be used by
enterprising users for e.g. sorting dates. We'll see a use of that later.

TERMs specified will list tasks that contain all of the terms. This is
case-insensitive:

	$ todo list cylons peace
	1 Make peace between Cylons and humans
	--
	TODO: 1 of 3 tasks shown
	Furthermore, TERMs will also match substrings of words:
	$ todo list lo ce
	1 Make peace between Cylons and humans
	--
	TODO: 1 of 3 tasks shown

The TERMs filtering takes into account the whole task name, including all
embedded `+projects` and `@contexts`.

All tasks with a priority will be shown first, in A-Z order. Tasks with the
same priority are sorted by name.

	$ todo ls
	3 (A) A
	2 (A) B
	1 (D) A
	4 A

### Updating Floating Tasks

#### Set the priority of a single task (`pri|p`)

	$ todo pri 2 A
	2 (A) Upgrade jump drives with Cylon technology +GalaticaRepairs
	TODO: 2 prioritized (A).

The priority will override the previous task priority:

	$ todo pri 2 B
	2 (B) Upgrade jump drives with Cylon technology +GalacticaRepairs
	TODO: 2 re-prioritized from (A) to (B).

#### Remove priorities from task(s) (`depri|dp`)

	$ todo depri 2
	2: Upgrade jump drives with Cylon technology +GalaticaRepairs
	TODO: 2 deprioritized

**(Positive)** Pretty straightforward and simple. Multiple tasks can be
deprioritised at the same time.

#### Updating task name/projects/contexts of a single task (`replace`)

The replace command allows Jim to completely replace a single task. This gives
the effect of allowing Jim to update a task’s name, projects, contexts,
priority or completion all at once.

For instance, Jim could replace the task name of a task:

	$ todo add a
	1 a
	TODO: 1 added
	$ todo replace 1 b
	1 a
	TODO: Replaced task with:
	1 b

**(Negative)** One major problem with this feature is that if Jim only wants to
update a certain field of a task, while keeping other fields intact, he would
have to repeat the data:

	$ todo add ‘(A) a +MyProject @MyContext’
	1 (A) a +MyProject @MyContext
	TODO: 1 added.
	$ todo replace 1 ‘b +MyProject @MyContext’
	1 (A) a +MyProject @MyContext
	TODO: Replaced task with:
	1 (A) b +MyProject @MyContext

In this case, although Jim only wanted to update the task name from `a` to `b`,
he still needed to repeat `+MyProject @MyContext` to preserve them. Note,
however, that todo.sh was nice enough to preserve the task priority.

#### Appending task `+project`(s)/`@context`(s) to a single task (`append|app`)

Using the append action, Jim can add an existing task to project(s) and
context(s):

	$ todo add My Task +MyProject
	1 My Task +MyProject
	TODO: 1 added.
	$ todo append 1 +MySecondProject +MyThirdProject @MyFirstContext @MySecondContext
	1 My Task +MyProject +MySecondProject +MyThirdProject @MyFirstContext @MySecondContext

**(Positive)** The append/app action can add a single task to multiple projects
and contexts at once.

**(Negative)** The append/app action only works on one task at a time.

### Deleting floating tasks

Tasks can be deleted with the del/rm command:

	$ todo add My Task
	1 My Task
	TODO: 1 added.
	$ todo rm 1
	Delete ‘My Task’? (y/n)
	1 My Task
	TODO: 1 deleted
	$ todo add A new task
	2 A new task
	TODO: 2 added.

**(Negative)** Only one task can be deleted at a time.

**(Positive)** Todo.sh asks for confirmation before deleting the task to reduce
user error. Also, note that the deleted task number is still reserved, new
tasks will not take that number. This is a good thing because it prevents
mistakes arising from users having to keep track of what task takes the deleted
task’s index.

## CRUD for Deadlines

The todo.txt file format does not have any explicit support for deadlines.
However, as the todo.txt format specification suggests, Jim could use the
behavioural properties of the todo.sh app to create tasks with deadlines.

### Creating deadlines

Jim must firstly agree on a personal rule that tasks with deadline must contain
the word `due:YYYY-MM-DD-HH:MM`, and that it must be the first word in the task
name. E.g.:

	$ todo add 'due:2016-09-24-13:00 Do homework'
	1 due:2016-09-24-13:00 Do homework
	TODO: 1 added.
	$ todo add 'due:2016-09-23-09:00 Wash house'
	2 due:2016-09-23-09:00 Wash house
	TODO: 2 added.

**(Negative)** However, as this is just a personal rule, todo.sh provides no
error-checking on the formatting at all. Jim could accidentally enter a totally
bogus due date and todo.sh would not bat an eye. Furthermore, the underlying
todo.txt could be corrupted with a bogus date and todo.sh will not detect it as
well. All Jim will get would be confusing incorrect results.

### Reading deadlines

By using the `ls` action and filtering by `due:`, all deadlines can be listed.

**(Positive)** Thanks to the personal rules on task names, deadlines will be
sorted by due date when they are printed out.

	$ todo ls due:
	2 due:2016-09-23-09:00 Wash house
	1 due:2016-09-24-13:00 Do homework
	--
	TODO: 2 of 2 tasks shown

### Updating/postponing deadlines

Deadlines can be updated by using the `replace` command:

	$ todo replace 2 ‘due:2016-09-25-09:00 Wash house’
	2 due:2016-09-23-09:00 Wash house
	TODO: Replaced task with:
	2 due:2016-09-25-09:00 Wash house

**(Negative)** However, the same criticisms of the `replace` command applies:
needless retyping. Also, no error checking for the `due:` format.

### Deleting deadlines

Since deadlines are just tasks, Jim could just use the usual `del/rm` action to
delete deadlines.

## CRUD for events

The todo.txt file format does not have any explicit support for events.
However, similarly to how we stored deadlines, Jim could set up a few personal
rules to store events.

**(Negative)** The same criticisms for deadlines, however, apply. Namely, no
error checking of the format.

## Looking for a suitable slot to schedule an event
No support at all.

## Ability to “block” multiple slots.
No support at all.

## Flexibility in command line format

The command line format when adding tasks has the following constraints:

* If the task is completed, it must begin with an `x `.

* If the task has a priority `(A)`, `(B)`, `(C)` etc., it must be before the
  task name. If the task is completed, the priority must be specified directly
  after the `x `.

**(Negative)** As we can see, the command line format is not very flexible when
adding tasks.

## Undo operations

Todo.sh has no support at all for undoing operations.

## Summary

Jim's Requirement | Todo.txt Support
:---------------- | ----------------
Summoned quickly from anywhere | No
Keyboard-oriented. Jim can type commands in "one shot" | Yes
Works offline | Yes
Simple search | Yes
CRUD support for floating tasks | Full
CRUD support for deadlines | Partial
CRUD support for events | Partial
Keep track of which items are done/not done | Yes
Look for a suitable slot to schedule an item | No
Ability to "block" multiple slots | No
Flexibility in command line format | Tiny
Undo operations | No

Todo.sh, as a command-line application, is fully keyboard oriented and thus
suitable for power users who prefer entering commands in one shot. As it only
operates on local text files, it works fully offline.  Furthermore, its data
model and user interface is specially geared towards the creation, reading,
updating and deleting (CRUD) of floating tasks, which it does quite well. One
exception is the interface to edit the name of a task, which is very clunky as
it requires the user to repeat existing information.

While the simplicity and flexibility of its data model means that CRUD
operations on deadlines and events are technically feasible, the lack of
explicit support from the application and data model means that the user
interface is poor and error checking is lacking. Users who store their
deadlines and events in todo.txt, without understanding how it works under the
hood, would thus be in for some nasty surprises.

Furthermore, the lack of explicit support for deadlines and events means that
the application has no support at all for looking for a slot to schedule an
item and the ability to "block" multiple time slots, as it has no concept of
time at all.

Finally, it has no support for undoing operations. While this is alleviated
somewhat as it asks for confirmation before deleting tasks, all other
operations are done without user confirmation.
