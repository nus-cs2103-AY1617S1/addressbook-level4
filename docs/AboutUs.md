Schindler's
==============================
- [About Us](about-us)
- [User Stories](user-stories)
- [Use Cases](use-cases)

## About us

### Roles and responsibilities
**President: Vincent**

**Testing: Karen**

**Integration: Anthony**

**Scheduling and Tracking: Joe**


## User Stories
| User Stories | Likely/Not Likely |
| ------ | ----------- |
|As a user I can add an entry by specifying a entry title only, so that I can record entrys that need to be done ‘some day’.|Likely
|As a user I can add entries that have a specific start and end time, so that I can record events with a specific duration.|Likely|
|As a user I can edit an entry, so that I can make changes to entries as needed.|Likely|
|As a user I can list all entries that are due, so that I can get an overview of all my entries.|Likely|
|As a user, I can set a entry as completed, so that I can keep track of completed/uncompleted entries.|Likely|
|As a user I can find upcoming entries, so that I can decide what needs to be done soon.|Likely|
|As a user I can delete an entry, so that I can get rid of entries that I no longer care to track.|Likely|
|As a user I can search for an entry by different entry attributes, so that I can find specific entries.|Likely|
|As a user I can undo a certain number of actions so that I can correct any mistaken actions.|Likely|
|As a new user I can view more information about a particular command, so that I can learn how to use various commands.|Likely|
|As an advanced user I can use shorter versions of a command, so that type a command faster.|Likely|
|As a user, I can add tags to the entries so that I can categorize entries together|Likely|
|As a user, I can use autocompletion of entry description so that I can quickly add entries that are similar to what I have added before|Not Likely|
|As a user, I can schedule a entry to be automatically added with a given frequency so that I don’t have to manually add entries every time.|Likely|
|As a user, I can personalize my app by changing the color scheme so that it fits my aesthetic requirements.|Not Likely|
|As an advanced user, I can specify a specific folder as the data storage location, so that I can choose to store the data file in a local folder controlled by a cloud syncing service, allowing me to access entry data from multiple computers.|Likely|
|As an advanced user, I can rebind default commands into my own keyboard shortcuts, so that I can use shortcuts that I am comfortable with.|Not Likely|
|As a user, I can choose different views of all my tasks, e.g. “Due Today”, “Due This Week”, so that I can focus on what is important to me at that moment.|Likely|
|As a user, I can type in commands in a more ‘natural’ manner, so that I do not need to learn the command format.|Not Likely|

## Use Cases

**Use case: Create a floating task**

Actors: User

MSS

1. User request to create a new entry without specifying the start and/or end date and deadline
2. TodoList add that particular floating task into database and save it

  Use Case Ends

**Use case: Create an event**

Actors: User

MSS

1. User request to create a new entry while specifying the start and/or end date but not deadline
2. TodoList add that particular floating task into database and save it

  Use Case Ends

**Use Case: Create a deadline**

Actors: User

MSS

1. User request to create a new entry while specifying the deadline but no start and/or end date
2. TodoList add that particular deadline into database and save it

  Use Case Ends

**Use Case: Delete an entry**

Actors: User

MSS

1. User requests to delete an entry with a specified id
2. TodoList deletes the entry

  Use Case Ends

*Extensions*

1a. Entry with the specified id does not exist
> 1a1. TodoList warns the user the entry with the specified id does not exist
> 
> Use Case Ends

**Use Case: List all entries**

Actors: User

MSS

1. User requests to list all entries
2. TodoList shows a list of entries, sorted by date (oldest first)

  Use Case Ends

**Use Case: List entries with filters**

Actors: User

MSS

1. User requests to list entries with some filters
2. TodoList shows a list of entries that satisfies the filters, sorted by date (oldest first)

  Use Case Ends

**Use Case: Add tags to a list of entries**

Actors: User

MSS

1. User requests to list a filtered list of entries
2. User requests to add tags to the listed entries

  Use Case Ends

*Extensions*

2a. User attempts to add an invalid tag
>2a1. TodoList tells the user that the tag name is invalid
>
>Use Case Ends
