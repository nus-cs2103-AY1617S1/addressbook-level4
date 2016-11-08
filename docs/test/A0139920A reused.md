# A0139920A reused
###### /java/seedu/Tdoo/logic/LogicManagerTest.java
``` java
	public void execute_list_showsAllTodoTasks() throws Exception {
		// prepare expectations
		TestDataHelper helper = new TestDataHelper();
		Task pTarget1 = helper.generatetask("todo 1");
		Task pTarget2 = helper.generatetask("todo 2");
		Task pTarget3 = helper.generatetask("todo 3");
		List<Task> threetasks = helper.generatetaskList(pTarget1, pTarget2, pTarget3);
		TaskList expectedAB = helper.generateTodoList(threetasks);
		List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

		// prepare TodoList state
		helper.addToModel(model, threetasks);

		assertCommandBehavior("list todo", ListCommand.TODO_MESSAGE_SUCCESS, expectedAB, expectedList);
	}

	@Test
```
###### /java/seedu/Tdoo/logic/LogicManagerTest.java
``` java
	public void execute_list_showsAllEventTasks() throws Exception {
		// prepare expectations
		TestDataHelper helper = new TestDataHelper();
		Task pTarget1 = helper.generateEvents("event 1");
		Task pTarget2 = helper.generateEvents("event 2");
		Task pTarget3 = helper.generateEvents("event 3");
		List<Task> threetasks = helper.generatetaskList(pTarget1, pTarget2, pTarget3);
		TaskList expectedAB = helper.generateTodoList(threetasks);
		List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

		// prepare TodoList state
		helper.addToModel(model, threetasks);

		assertCommandBehavior("list event", ListCommand.EVENT_MESSAGE_SUCCESS, expectedAB, expectedList);
	}

	@Test
```
###### /java/seedu/Tdoo/logic/LogicManagerTest.java
``` java
	public void execute_list_showsAllDeadlineTasks() throws Exception {
		// prepare expectations
		TestDataHelper helper = new TestDataHelper();
		Task pTarget1 = helper.generateDeadline("dd 1");
		Task pTarget2 = helper.generateDeadline("ddd 2");
		Task pTarget3 = helper.generateDeadline("dddd 3");
		List<Task> threetasks = helper.generatetaskList(pTarget1, pTarget2, pTarget3);
		TaskList expectedAB = helper.generateTodoList(threetasks);
		List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

		// prepare TodoList state
		helper.addToModel(model, threetasks);

		assertCommandBehavior("list event", ListCommand.EVENT_MESSAGE_SUCCESS, expectedAB, expectedList);
	}

	@Test
```
