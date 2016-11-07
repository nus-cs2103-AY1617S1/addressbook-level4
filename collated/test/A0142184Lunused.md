# A0142184Lunused
###### \java\guitests\AddCommandTest.java
``` java
//        //Adds a someday task
//        TestTask[] currentList = td.getTypicalTasks();
//        TestTask[] somedayList = td.getSomedayTasks();
//        TestTask taskToAdd = TypicalTestTasks.somedayAdd;
//        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//        assertAddSuccess(taskToAdd, "typical", currentList);
//        assertAddSuccess(taskToAdd, "someday", somedayList);
//        
//        //Adds a deadline task that is a today task
//        TestTask[] todayList = td.getTodayTasks();
//        taskToAdd = TypicalTestTasks.deadlineTodayAdd;
//        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//        assertAddSuccess(taskToAdd, "typical", currentList);
//        assertAddSuccess(taskToAdd, "today", todayList);
//       
//        //Adds a deadline task that is an tomorrow task
//        TestTask[] tomorrowList = td.getTomorrowTasks();
//        taskToAdd = TypicalTestTasks.deadlineTomorrowAdd;
//        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//        assertAddSuccess(taskToAdd, "typical", currentList);
//        assertAddSuccess(taskToAdd, "tomorrow", tomorrowList);
//
//        //Adds an event task that is a in-7-day task
//        TestTask[] in7DaysList = td.getIn7DaysTasks();
//        taskToAdd = TypicalTestTasks.eventIn7DaysAdd;
//        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//        assertAddSuccess(taskToAdd, "typical", currentList);
//        assertAddSuccess(taskToAdd, "in 7 days", in7DaysList);
//        
//        //Adds an event task that is a in-30-days task
//        TestTask[] in30DaysList = td.getIn30DaysTasks();
//        taskToAdd = TypicalTestTasks.eventIn30DaysAdd;
//        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
//        assertAddSuccess(taskToAdd, "typical", currentList);
//        assertAddSuccess(taskToAdd, "in 30 days", in30DaysList);
//        
//        //Adds a duplicate someday task
//        commandBox.runCommand(TypicalTestTasks.somedayAdd.getAddCommand());
//        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
//        assertTrue(taskListPanel.isListMatching(currentList));
//        
//        //Adds a duplicate deadline task
//        commandBox.runCommand(TypicalTestTasks.deadlineTodayAdd.getAddCommand());
//        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
//        assertTrue(taskListPanel.isListMatching(currentList));
//        
//        //Adds a duplicate event task
//        commandBox.runCommand(TypicalTestTasks.eventIn7DaysAdd.getAddCommand());
//        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
//        assertTrue(taskListPanel.isListMatching(currentList));
```
###### \java\guitests\AddCommandTest.java
``` java
//        //Confirms that the new task card contains the right data
//        if (taskToAdd.getTaskType().value.equals(TaskType.Type.SOMEDAY)) {
//        	SomedayTaskCardHandle addedCard = taskListPanel.navigateToSomedayTask(taskToAdd.getName().value);
//        	assertSomedayTaskMatching(taskToAdd, addedCard);
//        } else if (taskToAdd.getTaskType().value.equals(TaskType.Type.DEADLINE)) {
//        	DeadlineTaskCardHandle addedCard = taskListPanel.navigateToDeadlineTask(taskToAdd.getName().value);
//        	assertDeadlineTaskMatching(taskToAdd, addedCard);
//        } else if (taskToAdd.getTaskType().value.equals(TaskType.Type.EVENT)) {
//        	EventTaskCardHandle addedCard = taskListPanel.navigateToEventTask(taskToAdd.getName().value);
//        	assertEventTaskMatching(taskToAdd, addedCard);
//        } 
//        
//        //Confirms that the list now contains all previous tasks and the new task
//        TestTask[] expectedList = TestUtil.addTasksToList(list, taskToAdd);
//        switch (listType) {
//        case "typical":
//        	assertTrue(taskListPanel.isListMatching(expectedList));
//        case "today":
//            assertTrue(todayTaskListTabPanel.isListMatching(expectedList));
//        case "tomorrow":
//            assertTrue(tomorrowTaskListTabPanel.isListMatching(expectedList));
//        case "in 7 days":
//            assertTrue(in7DaysTaskListTabPanel.isListMatching(expectedList));
//        case "in 30 days":
//            assertTrue(in30DaysTaskListTabPanel.isListMatching(expectedList));
//        case "someday":
//            assertTrue(somedayTaskListTabPanel.isListMatching(expectedList));
//        default:
//        }
```
