[TOC]

## Introduction

## Quick Start

1. Ensure you have [**Java version 8 update 60**][java] or later installed on your computer.

    !!! warning "This application will not work with earlier versions of Java 8"

2. Download the latest `UJDTDL.jar` from the [releases](../../../releases) tab.
3. Copy the file to the folder you want to use as the home folder for the application.
4. Double-click the file to start the app. 

    <img src="images/mockupV0_0.png" width="700" alt="Example of UI once launched"> 
    
5. Type the command in the command box and press <kbd>Enter</kbd> to execute it.  
   E.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
6. Some example commands you can try:

     * **`add`**` Finish CS2103T homework -d next Friday` : 
       adds a new task with the deadline set at next Friday
     * **`delete`**` 3` : deletes the 3<sup>rd</sup> task shown in the current list
     * **`exit`** : exits the app
     
7. Refer to the [Features](#features) section below for details of each command.

## Features

### Intelligent Views




### Events *and* Tasks

Most productivity apps only allow you to manage tasks *or* events. Our application can manage both. 

### Command Line Interface

 
## Commands Reference

### Command Format

* Words in `UPPERCASE` are the parameters.
* Items in `[SQUARE BRACKETS]` are optional.
* To specify parameters, such as the deadline for a task, use flags. Flags follow the Unix command format - single dash (eg. `-f`) for short flags and double dash for long flags (eg. `--all`)
* Items with `...` after them can have multiple instances.
* Most commands that updates a task require an `INDEX`. This is number shown to the left of the task as shown in the screenshot below

 <img src="images/sketch_task_index.jpg" width="350" alt="Index Number Location">


### Viewing help : **`help`**
Format: **`help`**

Shows help window which gives list of commands and their actions.
 
### Adding a task or event: **`add`**

Adds a new task or event.  
Format:  
**`add`**` TASK NAME [-d DEADLINE] [-m DESCRIPTION] [-r TIME] [-p] [-t TAG 1, TAG 2...]`  
**`add`**` EVENT NAME -d START END [-m DESCRIPTION] [-l LOCATION] [-p] [-t TAG 1, TAG 2...]`

Although there are a lot of parameters, only the name is required. Tasks will be turned into events 
automatically if two dates are specified under the `-d` flag. Here are some common scenarios

#### Adding a task

Adding a task is as simple as giving it a name.

!!! example
    **`add`**` Finish up developer guide for CS2101` 

#### Adding an event

Events normally will have include a start time, end time and a location and can be phrased as follows using the `-d` and `-l` flags.

!!! example
    **`add`**` Music at the park -d 11 Dec 6pm to 8pm -l Botanic Gardens  -p`

#### Adding a deadline

If you need something done by a specific time, add a deadline to your task.

!!! example
    **`add`**` Submit V.0.0 -d 5 Oct 2359`

#### Adding a recurring task

Recurring tasks only require the recurring time period. The task will be appear from the first occurrence 
of the specified day. If no date is specified, it will be added to today's list

!!! example
    **`add`**` CS2103T Tutorial -d 10am to 2pm -r every Wednesday`

#### Adding descriptions to a task

More details can be added to the task using the `-m` flag

!!! example
    **`add`**` Destroy the Earth -m Going to need a lot of TNT for this. Remember to get them at sale on Friday - 50% discount on bulk orders!`

#### Pinning a task

Important tasks can be pinned to the top of the list using the `-p` flag. See [the `pin` command](#pinning-a-task-pin) 
for more detail.

!!! example
    **`add`**` Meet Li Kai at Friday Hacks! -d 21 Oct 6pm to 8pm -p`

#### Organizing tasks using tags

If you have a lot of tasks you can use tags to organize them. See [the `tag` command](#managing-tags-tag) 
for more detail.

!!! example
    **`add`**` Finish tutorial 6 -d 10 Oct -t CS2106, School`


#### Parameter reference

Flag | Parameter        | Used to
-----| ---------------- | ----------------------
`-d` | `DEADLINE`       | Specify a deadline for the task
`-d` | `START END`      | Specify the start and end time for the event
`-m` | `DESCRIPTION`    | Add a long description to the task or event
`-l` | `LOCATION`       | Add a location to the event 
`-r` | `PERIOD`         | Create a recurring task
`-p` | -                | Pins the task to the top of the list
`-t` | `TAG 1, TAG 2, ...` | Tags to help organize your tasks 

### Deleting a task: **`delete`**

Deletes the specified task from todo list.  
Format: **`delete`**` INDEX`

Deletes the task at the specified `INDEX`. The index refers to the index number shown in the most recent listing.

!!! example
    
    **`delete`**` 2`  
    :    Deletes the 2nd task on the list 
      
    **`find`**` Y2S1`  
    **`delete`**` 1`  
    :    Deletes the 1st task in the results of the **`find`**` Y2S1` command.

### Marking a task complete: **`complete`**

Format:  
**`complete`**` INDEX`  
**`complete`**` --all`

You can use this command to mark a task as completed. Completed task appear struckthrough to indicate they have been complete. Using the `all` flag will mark all tasks on the current view as completed. 

<img src="images/sketch_task_completed.jpg" width="700" alt="Example of a Completed Task"><br />

### Pinning a task: **`pin`**

Format: **`pin`**` INDEX`

If a particular task or event is important, you can pin it to the top of every list the item appears in using this command. You can also use this command to unpin any pinned task. 

<img src="images/sketch_task_pinned.jpg" width="700" alt="Pinned Task"><br />


### Editing a task: **`edit`**

Allows you to edit a specific task.  
Format:  
**`edit`**` INDEX [NAME] [-d DEADLINE] [-m DESCRIPTION] [-r TIME] [-p] [-t TAG 1, TAG 2...]`  
**`edit`**` INDEX [NAME] [-d START END] [-m DESCRIPTION] [-l LOCATION] [-p] [-t TAG 1, TAG 2...]`

Use the `edit` command to make changes to the task specified by `INDEX`. The command accepts the same parameters as the `add` command. Any edit is immediately saved.


### Switch views : **`view`**

Switch between different views.  
Format: **`view`**` VIEW`

<img src="images/sketch_task_view.jpg" width="700" alt="View of completed tasks">

You can also use the underlined character in the view's name as the shortcut when switching views. 

!!! example
    **`view`**` completed`
    :    Show completed tasks only 


### Finding tasks: **`find`**

Finds tasks whose tags/name contain any of the given keywords.  
Format: **`find`**` KEYWORD [MORE KEYWORDS]`

The search is case insensitive and the order of the keywords does not matter. Only the title and tags are searched, and tasks matching at least one keyword will be returned.

!!! example  
    **`find`**` John`  
    :    Returns **Meet John for lunch**
    
    **`find`**` Jo`  
    :    Returns any task with **Jo** in the title, such as **Meet John for lunch**, 
         **Jogging at the park**, or **Josting fights at the gym**
    
    **`find`**` Jo Ja`  
    :    Returns any task with either **Jo** or **Ja** in the title, such as 
         **Meet John for lunch**, **Meet Jane for lunch**, or **Jack and Jane's wedding**

### Exiting the program : **`exit`**
Exits the program.  
Format: **`exit`**  

### Undoing an action: **`undo`**

Undo the most recent action.  
Format: **`undo`**

!!! note
    Only applies to commands which have made changes to the todo list.

### Redoing an action: **`redo`**

Redo the most recent action which was undone.  
Format: **`redo`**

### Loading an existing data file: **`load`**

Format: **`load`**` FILENAME`

Loads in another save file. You can use this to restore a backup or switch to different lists so you can 
(for example) have separate lists for school and home. 

!!! example

    **`load`**` "myDiscountTodo.xml"`

### Changing the save location: **`save`**

Format: **`save`**

By default, todo list data are saved in a file called `discountTodo.xml` in the `data` folder. You can change 
the save file by specifying the file path as the first argument when running the program.

!!! note "Autosave"
    Your todo list is saved automatically every time it is updated. There is no need to save manually.


## FAQ

**Q**: Is my data secure?  
**A**: Your data is stored locally on your hard drive as an `.xml` file. So, your data is as secure as your hard drive. We do not have access to your to-do list. 

**Q**: How do I back up my data?  
**A**: As your data is saved to a `.xml` file that you specified, you can simply copy this file to a back up storage of your choice.

**Q**: How do I sync my data with multiple devices?  
**A**: Simply load the `.xml` file to a cloud sync service like Dropbox or Google Drive, and all updates will be reflected to all devices using the file.

**Q**: How do I pay for this project?  
**A**: Donations can be made via PayPal or Kashmi. Cash donations are fine too. Basically if you wish to donate we will find a way for us to receive your money.

## Command Summary

Command  | Format  
-------- | :-------- 
Help     | **`help`**
Add      | **`add`**` NAME [-d DEADLINE or START END] [-m DESCRIPTION] [-r TIME] [-p] [-t TAG 1, TAG 2...]` 
Delete   | **`delete`**` INDEX`
Complete | **`complete`**` INDEX`
Pin      | **`pin`**` INDEX`
Edit     | **`edit`**` INDEX [NAME] [-d DEADLINE or START END] [-m DESCRIPTION] [-r TIME] [-p] [-t TAG 1, TAG 2...]` 
Find     | **`find`**` KEYWORD [MORE KEYWORDS...]`
Undo     | **`undo`**
Redo     | **`redo`**
Load     | **`load`**` FILENAME`
Save     | **`save`**
View     | **`view`**` VIEW`

[java]: https://www.java.com/en/download/