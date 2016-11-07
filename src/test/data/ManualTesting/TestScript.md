# These are the instructions for testing the IvoryTasks application.

1. Download the latest `IvoryTasks.jar` from the [releases](../../../releases) tab.<br>

2. Copy the file to the folder you want to use as the home folder for your task manager.<br>

3. Load the sample data by editing the config.json file.

  > ### Change "taskManagerFilePath" : "src/test/data/ManualTesting/SampleData.xml"

4. Double-click the file to start the app. The GUI should appear in a few seconds. There should be about 60 items uploaded into the task manager. These items have various parameters optimized for testing various conditions.

# Run the following commands

Test Cases:

* Add Command:
    * Tasks
        * 1. add task buy groceries
        	* Response: Results panel displays details of the task added. Item list panel refreshes list of items with the task "buy groceries" added to the end of the list.
        * 2. a t clear table
        	* Response: Results panel displays details of the task added. Item list panel refreshes list of items with the task "clear table" added to the end of the list.
		* 3. task add Diwali ball
			* Response: Results panel displays "Invalid command format!"
    * Deadlines
         * 1. add deadline project report ed/tomorrow 3pm
         	* Response: Results panel displays details of the task added. Item list panel refreshes list of items with the deadline "project report" added to the end of the list. Item is in orange font because deadline is nearing.
         * 2. a d essay assignment ed/2016-11-09 et/23:59
         	* Response: Results panel displays details of the task added. Item list panel refreshes list of items with the deadline "essay assignment" added to the end of the list. Item is in red font because deadline is over.
    * Events
         * 1. add event concert sd/2016-11-10 st/10:00 ed/2016-11-10 et/22:00
         	* Response: Results panel displays details of the task added. Item list panel refreshes list of items with the event "concert" added to the end of the list. Item is in blue font because event is in progress.


* Delete Command
	* 1. delete 1
	    * Response: Results panel displays deleted item details and item list panel refreshes list of items.
	* 2. del 2 5 3
		* Response: Results panel displays details of deleted items in ascending index order and item list panel refreshes list of items.
	* 3. delete 0
		* Response: Results panel displays "Invalid command format!"

* Done Command
	* 1. done 1
		* Response: Item list panel refreshes list of items, with first item marked as done.
	* 2. d 2
		* Response: Item list panel refreshes list of items, with second item marked as done.
	* 3. d 0
		* Response: Results panel displays "Invalid command format!"

* Not Done Command
    * 1. notdone 1
        * Response: Item list panel refreshes list of items, with first item marked as not done.

* Edit Command
    * Tasks
    	* 1. edit 1 n/CS2103
    		* Response: App will display edited item's new details in the results panel.
    	* 2. e 1 n/CS3216
    		* Response: App will display edited item's new details in the results panel.
    	* 3. e 1 3216
    		* Response: Result panel shows "Unknown command"
    * Deadlines
    	* 1. edit 6 edt/tomorrow 6pm
    		* Response: App will display edited item's new details in the results panel.
    	* 2. e 6 edt/tomorrow 8pm
    		* Response: App will display edited item's new details in the results panel.
    	* 3. e 6 8pm
    		* Response: Result panel shows "Unknown command"
    * Events
    	* 1. edit 11 sdt/yesterday
    		* Response: App will display edited item's new details in the results panel.
    	* 2. e 11 sdt/2 weeks ago edt/tomorrow
    		* Response: App will display edited item's new details in the results panel.
    	* 3. e 10 thursday
    		* Response: Result panel shows "Unknown command"

* Find Command
	* 1. find tutorial
		* Response: Item list panel refreshes list of items that contain "tutorial"
	* 2. f birthday
		* Response: Item list panel refreshes list of items that contain "birthday"
	* 3. find assignmnt
		* Response: Item list panel refreshes list of items that contain "assignmnt" and also "assignment" (fuzzy search)  
	* 3. findassignment
		* Response: Result panel shows "Unknown command"

* Help Command
	* 1. help
		* Response: The list of commands, their input format and their functions will be shown.
	* 2. h
		* Response: The list of commands, their input format and their functions will be shown.

* List Command
	* List tasks
		* 1. listtask
			* Response: All tasks are listed in the item list panel
		* 2. lt
			* Response: All tasks are listed in the item list panel
		* 3. listtasks
			* Response: Result panel shows "Unknown command"
	* List deadlines
		* 1. listdeadline
			* Response: All deadlines are listed in the item list panel
		* 2. ld
			* Response: All deadlines are listed in the item list panel
		* 3. listdeadlines
			* Response: Result panel shows "Unknown command"
	* List events
		* 1. listevent
			* Response: All events are listed in the item list panel
		* 2. le
			* Response: All events are listed in the item list panel
		* 3. listevents
			* Response: Result panel shows "Unknown command"

	* List not done items
		* 1. listnotdone
			* Response: All not done items are listed in the item list panel
		* 2. lnd
			* Response: All not done items are listed in the item list panel
		* 3. listnd
			* Response: Result panel shows "Unknown command"

	* Undo Command
		* 1a. delete 5
			* Response: Results panel displays deleted item details and item list panel refreshes list of items.
		* 1b. undo
			* Response: Deleted item is restored. Item list panel refreshes list of items. Results panel shows the details of the undone command.

		* 2a. clear
			* Response: All items are deleted. Item list panel is cleared.
		* 2b. undo
			* Response: All deleted items are restored. Item list panel is refreshed with all previously saved items.

	* Redo Command
		* 1a. clear
			* Response: All items are deleted. Item list panel is cleared.
		* 1b. undo
			* Response: All deleted items are restored. Item list panel is refreshed with all previously saved items.
		* 1c. redo
			* Response: All items are deleted. Item list panel is cleared.

* Save Command
	* 1. save C:\User\data.xml
		* Response: App data saved to C:\User\ folder, under name data in .xml format.
	* 2. save data\todolist.xml
		* Response: App data saved to data folder in the home folder of the application, under name todolist in .xml format.
	* 3. save C:\testing.xml
		* Response: Message "Invalid command format! Specified directory is invalid!" is displayed in results panel.

* Select Command
	* 1. select 2
		* Response: Item of index 2 in the list shown on the item list panel is selected and the selected item details are shown in the item details panel.
	* 2. s 5
		* Response: Item of index 5 in the list shown on the item list panel is selected and the selected item details are shown in the item details panel.

* Clear Command
	* 1. clear
		* Response: All items are deleted. Item list panel is cleared.
