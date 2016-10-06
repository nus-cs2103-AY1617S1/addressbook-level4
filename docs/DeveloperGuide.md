# Developer Guide 

* [Appendix A: User Stories](#appendix-a--user-stories)
* [Appendix B: Use Cases](#appendix-b--use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c--non-functional-requirements)
* [Appendix D: Glossary](#appendix-d--glossary)
* [Appendix E : Product Survey](#appendix-e-product-survey)



## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | new user | see usage instructions | refer to instructions when I forget how to use the App
`* * *` | user | add a new task |
`* * *` | user | delete a task | remove tasks that I no longer need
`* * *` | user | undo a command | undo mistakes easily
`* * *` | user | find a task by name | locate details of tasks without having to go through the entire list
`* * *` | user | have a one-shot approach to add tasks | minimize clicking and saves time
`* * *` | user | set recurring tasks| repeatedly input the same event
`* * *` | user | track completed/uncompleted tasks | better manage my schedule
`* * *` | user | modify storage path | store data in my desired location
`* * *` | user | upload my schedule online and snyc them across devices | view my schedules when I am using different devices
`* *` | user | set priorities to my tasks | prioratise tasks that are more important to me
`* *` | user with many tasks in the address book | sort tasks by different priority/dateline | view them differently

{More to be added}

## Appendix B : Use Cases

(For all use cases below, the **System** is the `AddressBook` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: Delete person

**MSS**

1. User requests to list persons
2. AddressBook shows a list of persons
3. User requests to delete a specific person in the list
4. AddressBook deletes the person <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. AddressBook shows an error message <br>
  Use case resumes at step 2

{More to be added}

## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 persons.
3. Should come with automated unit tests and open source code.
4. Should favor DOS style commands over Unix-style commands.

{More to be added}

## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

##### Private contact detail

> A contact detail that is not meant to be shared with others

## Appendix E : Product Survey

{TODO: Add a summary of competing products}

