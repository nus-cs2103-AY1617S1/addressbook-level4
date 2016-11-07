# A0132157M reused
###### /java/guitests/AddCommandTest.java
``` java
	public void add() throws IllegalValueException {
		// add one task
		TestTask[] currentList = td.getTypicaltasks();
		TestTask taskToAdd = new TaskBuilder().withName("TODO 123").withStartDate("28-11-2016")
				.withEndDate("29-11-2016").withPriority("1").withDone("false").build();
		assertAddSuccess(taskToAdd, currentList);
		currentList = TestUtil.addTasksToList(currentList, taskToAdd);

		// add one event
		TestEvent[] currentEventList = ed.getTypicalEvent();
		TestEvent eventToAdd = new EventBuilder().withName("EVENT 123").withStartDate("01-01-2017")
				.withEndDate("02-01-2017").withStartTime("01:30").withEndTime("02:00").withDone("false").build();
		assertAddEventSuccess(eventToAdd, currentEventList);
		currentEventList = TestUtil.addEventsToList(currentEventList, eventToAdd);

		// add one deadline
		TestDeadline[] currentDeadlineList = dd.getTypicalDeadline();
		TestDeadline ddToAdd = new DeadlineBuilder().withName("DEADLINE 1").withStartDate("30-11-2017")
				.withEndTime("10:00").withDone("false").build();
		assertAddDeadlineSuccess(ddToAdd, currentDeadlineList);
		currentDeadlineList = TestUtil.addDeadlinesToList(currentDeadlineList, ddToAdd);

		// add another task
		taskToAdd = new TaskBuilder().withName("AnotherTODO 123").withStartDate("29-11-2017").withEndDate("30-11-2017")
				.withPriority("3").withDone("false").build();
		assertAddSuccess(taskToAdd, currentList);
		currentList = TestUtil.addTasksToList(currentList, taskToAdd);

		// add another event
		eventToAdd = new EventBuilder().withName("AnotherEVENT 123").withStartDate("01-03-2017")
				.withEndDate("02-03-2017").withStartTime("01:30").withEndTime("02:00").withDone("false").build();
		assertAddEventSuccess(eventToAdd, currentEventList);
		currentEventList = TestUtil.addEventsToList(currentEventList, eventToAdd);

		// add another deadline
		ddToAdd = new DeadlineBuilder().withName("ANOTHEREADLINE 1").withStartDate("19-11-2017").withEndTime("10:00")
				.withDone("false").build();
		assertAddDeadlineSuccess(ddToAdd, currentDeadlineList);
		currentDeadlineList = TestUtil.addDeadlinesToList(currentDeadlineList, ddToAdd);

		// add duplicate task
		taskToAdd = new TaskBuilder().withName("TODO 123").withStartDate("28-11-2016").withEndDate("29-11-2016")
				.withPriority("1").withDone("false").build();
		commandBox.runCommand(taskToAdd.getAddCommand());
		assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
		assertTrue(taskListPanel.isListMatching(currentList));

		// add duplicate event
		eventToAdd = new EventBuilder().withName("EVENT 123").withStartDate("01-01-2017").withEndDate("02-01-2017")
				.withStartTime("01:30").withEndTime("02:00").withDone("false").build();
		commandBox.runCommand(eventToAdd.getAddCommand());
		assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
		assertTrue(eventListPanel.isListMatching(currentEventList));

		// add duplicate deadline
		ddToAdd = new DeadlineBuilder().withName("DEADLINE 1").withStartDate("30-11-2017").withEndTime("10:00")
				.withDone("false").build();
		commandBox.runCommand(ddToAdd.getAddCommand());
		assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
		assertTrue(deadlineListPanel.isListMatching(currentDeadlineList));

		// add to empty list
		// taskToAdd = new TaskBuilder().withName("TODO
		// 123").withStartDate("28-11-2016").withEndDate("29-11-2016").withPriority("2").withDone("false").build();
		// commandBox.runCommand(taskToAdd.getAddCommand());
		// assertAddEventSuccess(ed.e1);

		// invalid command
		commandBox.runCommand("adds assignment 66");
		assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
	}
	// =========================================================================================================================

	private void addAllDummyTodoTasks(TestTask... currentList) {
		for (TestTask t : currentList) {
			commandBox.runCommand(t.getAddCommand());
		}
	}

	private void addAllDummyEventTasks(TestEvent... currentList) {
		for (TestEvent t : currentList) {
			commandBox.runCommand(t.getAddCommand());
		}
	}

	private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
		addAllDummyTodoTasks(currentList);
		commandBox.runCommand(taskToAdd.getAddCommand());
		// confirm the new card contains the right data
		TaskCardHandle addedCard = taskListPanel.navigateTotask(taskToAdd.getName().name);
		assertMatching(taskToAdd, addedCard);
		// confirm the list now contains all previous tasks plus the new task
		TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
		assertTrue(taskListPanel.isListMatching(expectedList));
	}

```
###### /java/guitests/ClearCommandTest.java
``` java
	public void clear() throws IllegalValueException {

		// verify a non-empty list can be cleared
		TestTask[] currentList = new TestTask[] {};
		TestTask taskToAdd = new TaskBuilder().withName("TODO 123").withStartDate("28-11-2016")
				.withEndDate("29-11-2016").withPriority("1").withDone("false").build();
		assertAddSuccess(taskToAdd, currentList);
		currentList = TestUtil.addTasksToList(currentList, taskToAdd);
		assertClearTodoCommandSuccess();

		// verify other commands can work after a clear command
		TestTask[] currentList1 = new TestTask[] {};
		TestTask TaskToAdd = new TaskBuilder().withName("TODO 456").withStartDate("28-11-2016")
				.withEndDate("29-11-2016").withPriority("1").withDone("false").build();
		assertAddSuccess(TaskToAdd, currentList1);
		currentList1 = TestUtil.addTasksToList(currentList1, TaskToAdd);

		commandBox.runCommand("delete todo 1");
		assertListSize(0);

		// verify clear command works when the list is empty
		assertClearTodoCommandSuccess();

		// ------------------------------------------------------------------------
		// verify a non-empty list can be cleared
		TestEvent[] currentList2 = new TestEvent[] {};
		TestEvent taskToAdd1 = new EventBuilder().withName("EVENT 123").withStartDate("01-01-2017")
				.withEndDate("01-01-2017").withStartTime("01:30").withEndTime("02:00").withDone("false").build();
		assertAddEventSuccess(taskToAdd1, currentList2);
		currentList2 = TestUtil.addEventsToList(currentList2, taskToAdd1);
		assertClearEventCommandSuccess();

		// verify other commands can work after a clear command
		TestEvent[] currentList3 = new TestEvent[] {};
		TestEvent TaskToAdd2 = new EventBuilder().withName("EVENT 456").withStartDate("03-01-2017")
				.withEndDate("04-01-2017").withStartTime("01:30").withEndTime("02:00").withDone("false").build();
		assertAddEventSuccess(TaskToAdd2, currentList3);
		currentList3 = TestUtil.addEventsToList(currentList3, TaskToAdd2);

		commandBox.runCommand("delete event 1");
		assertListSize(0);

		// verify clear command works when the list is empty
		assertClearEventCommandSuccess();

		// ------------------------------------------------------------------------
		// verify a non-empty list can be cleared
		TestDeadline[] currentList4 = new TestDeadline[] {};
		TestDeadline taskToAdd4 = new DeadlineBuilder().withName("DEADLINE 123").withStartDate("01-01-2017")
				.withEndTime("10:00").withDone("false").build();
		assertAddDeadlineSuccess(taskToAdd4, currentList4);
		currentList4 = TestUtil.addDeadlinesToList(currentList4, taskToAdd4);
		assertClearDeadlineCommandSuccess();

		// verify other commands can work after a clear command
		TestDeadline[] currentList5 = new TestDeadline[] {};
		TestDeadline TaskToAdd5 = new DeadlineBuilder().withName("DEADLINE 456").withStartDate("01-01-2017")
				.withEndTime("10:00").withDone("false").build();
		assertAddDeadlineSuccess(TaskToAdd5, currentList5);
		currentList5 = TestUtil.addDeadlinesToList(currentList5, TaskToAdd5);

		commandBox.runCommand("delete deadline 1");
		assertListSize(0);

		// verify clear command works when the list is empty
		assertClearEventCommandSuccess();
	}

	private void assertClearTodoCommandSuccess() {
		commandBox.runCommand("clear todo");
		assertListSize(0);
		assertResultMessage("TodoList has been cleared!");
	}

```
###### /java/guitests/DeleteCommandTest.java
``` java
	public void delete() throws IllegalValueException {
		// Delete tasks in Todo list
		TestTask[] currentList = new TestTask[] {
				new TaskBuilder().withName("TODO 123").withStartDate("28-11-2016").withEndDate("29-11-2016")
						.withPriority("1").withDone("false").build(),
				new TaskBuilder().withName("TODO 456").withStartDate("01-12-2016").withEndDate("02-12-2016")
						.withPriority("1").withDone("false").build(),
				new TaskBuilder().withName("TODO 789").withStartDate("03-12-2016").withEndDate("04-12-2016")
						.withPriority("1").withDone("false").build(),
				new TaskBuilder().withName("TODO 101112").withStartDate("03-12-2016").withEndDate("04-12-2016")
						.withPriority("1").withDone("false").build() };
		addAllDummyTodoTasks(currentList);
		int targetIndex = 1;
		assertDeleteSuccess(targetIndex, currentList);

		// delete the last in the list
		currentList = TestUtil.removetaskFromList(currentList, targetIndex);
		targetIndex = currentList.length;
		assertDeleteSuccess(targetIndex, currentList);

		// delete from the middle of the list
		currentList = TestUtil.removetaskFromList(currentList, targetIndex);
		targetIndex = currentList.length / 2;
		assertDeleteSuccess(targetIndex, currentList);

		// invalid index
		commandBox.runCommand("delete todo" + currentList.length + 1);
		assertResultMessage("Invalid command format! \n"
				+ "delete: Deletes the task identified by the task type and the index number used in the last task listing.\n"
				+ "Parameters: TASK_TYPE INDEX_NUMBER(must be a positive integer)\n" + "Example: " + "delete"
				+ " todo 1\n" + "Example: " + "delete" + " event 1\n" + "Example: " + "delete" + " deadline 1");

		// Delete tasks in Event list
		TestEvent[] currentList1 = new TestEvent[] {
				new EventBuilder().withName("Event 123").withStartDate("01-01-2017").withEndDate("12-12-2017")
						.withStartTime("01:00").withEndTime("01:30").withDone("false").build(),
				new EventBuilder().withName("Event 456").withStartDate("01-01-2017").withEndDate("18-11-2017")
						.withStartTime("01:30").withEndTime("20:00").withDone("false").build(),
				new EventBuilder().withName("Eeambuilding 3").withStartDate("01-01-2017").withEndDate("20-11-2017")
						.withStartTime("01:30").withEndTime("02:00").withDone("false").build(),
				new EventBuilder().withName("Essignment 4").withStartDate("01-01-2017").withEndDate("12-12-2017")
						.withStartTime("01:00").withEndTime("01:30").withDone("false").build() };

		addAllDummyEventTasks(currentList1);
		int targetIndex1 = 1;
		assertDeleteEventSuccess(targetIndex1, currentList1);

		// delete the last in the list
		currentList1 = TestUtil.removeEventFromList(currentList1, targetIndex1);
		targetIndex1 = currentList1.length;
		assertDeleteEventSuccess(targetIndex1, currentList1);

		// delete from the middle of the list
		currentList1 = TestUtil.removeEventFromList(currentList1, targetIndex1);
		targetIndex1 = currentList1.length / 2;
		assertDeleteEventSuccess(targetIndex1, currentList1);

		// invalid index
		commandBox.runCommand("delete event" + currentList1.length + 1);
		assertResultMessage("Invalid command format! \n"
				+ "delete: Deletes the task identified by the task type and the index number used in the last task listing.\n"
				+ "Parameters: TASK_TYPE INDEX_NUMBER(must be a positive integer)\n" + "Example: " + "delete"
				+ " todo 1\n" + "Example: " + "delete" + " event 1\n" + "Example: " + "delete" + " deadline 1");

		// delete deadlines in deadline list
		TestDeadline[] currentList2 = new TestDeadline[] {
				new DeadlineBuilder().withName("d 1").withStartDate("30-11-2017").withEndTime("10:00").withDone("false")
						.build(),
				new DeadlineBuilder().withName("dd 1").withStartDate("30-11-2017").withEndTime("12:00")
						.withDone("false").build(),
				new DeadlineBuilder().withName("ddd 3").withStartDate("30-11-2017").withEndTime("13:00")
						.withDone("false").build(),
				new DeadlineBuilder().withName("dddd 3").withStartDate("30-11-2017").withEndTime("14:00")
						.withDone("false").build() };

		addAllDummyDeadlineTasks(currentList2);
		int targetIndex2 = 1;
		assertDeleteDeadlineSuccess(targetIndex2, currentList2);

		// delete the last in the list
		currentList2 = TestUtil.removeDeadlineFromList(currentList2, targetIndex2);
		targetIndex2 = currentList2.length;
		assertDeleteDeadlineSuccess(targetIndex2, currentList2);

		// delete from the middle of the list
		currentList2 = TestUtil.removeDeadlineFromList(currentList2, targetIndex2);
		targetIndex2 = currentList2.length / 2;
		assertDeleteDeadlineSuccess(targetIndex2, currentList2);

		// invalid index
		commandBox.runCommand("delete event" + currentList2.length + 1);
		assertResultMessage("Invalid command format! \n"
				+ "delete: Deletes the task identified by the task type and the index number used in the last task listing.\n"
				+ "Parameters: TASK_TYPE INDEX_NUMBER(must be a positive integer)\n" + "Example: " + "delete"
				+ " todo 1\n" + "Example: " + "delete" + " event 1\n" + "Example: " + "delete" + " deadline 1");
	}

	/**
	 * Runs the delete command to delete the task at specified index and
	 * confirms the result is correct.
	 * 
	 * @param targetIndexOneIndexed
	 *            e.g. to delete the first task in the list, 1 should be given
	 *            as the target index.
	 * @param currentList
	 *            A copy of the current list of tasks (before deletion).
	 * @throws IllegalValueException
	 */
	// author A0132157M reused
	private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList)
			throws IllegalValueException {
		TestTask taskToDelete = currentList[targetIndexOneIndexed - 1]; // -1
																		// because
																		// array
																		// uses
																		// zero
																		// indexing
		TestTask[] expectedRemainder = TestUtil.removetaskFromList(currentList, targetIndexOneIndexed);

		commandBox.runCommand("delete todo " + targetIndexOneIndexed);

		// confirm the list now contains all previous tasks except the deleted
		// task
		assertTrue(taskListPanel.isListMatching(expectedRemainder));

		// confirm the result message is correct
		assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete.getName().name.toString() + "\n" +
		// "Start Date: No Start Date" + "\n" +
		// "End Date: No End Date" + "\n" +
				"Priority: " + new Priority(taskToDelete.getPriority())));
	}

	// author A0132157M reused
	private void assertDeleteEventSuccess(int targetIndexOneIndexed, final TestEvent[] currentList)
			throws IllegalValueException {
		TestEvent taskToDelete = currentList[targetIndexOneIndexed - 1]; // -1
																			// because
																			// array
																			// uses
																			// zero
																			// indexing
		TestEvent[] expectedRemainder = TestUtil.removeEventFromList(currentList, targetIndexOneIndexed);

		commandBox.runCommand("delete event " + targetIndexOneIndexed);

		// confirm the list now contains all previous tasks except the deleted
		// task
		assertTrue(eventListPanel.isListMatching(expectedRemainder));

		// confirm the result message is correct
		assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS,
				taskToDelete.getName().name.toString() + "\n" + "Start Date: " + taskToDelete.getStartDate().date + "\n"
						+ "End Date: " + new EndDate(taskToDelete.getEndDate()) + "\n" + "StartTime: "
						+ new StartTime(taskToDelete.getStartTime()) + "\n" + "EndTime: "
						+ new EndTime(taskToDelete.getEndTime())));
	}

	// author A0132157M reused
	private void assertDeleteDeadlineSuccess(int targetIndexOneIndexed, final TestDeadline[] currentList)
			throws IllegalValueException {
		TestDeadline taskToDelete = currentList[targetIndexOneIndexed - 1]; // -1
																			// because
																			// array
																			// uses
																			// zero
																			// indexing
		TestDeadline[] expectedRemainder = TestUtil.removeDeadlineFromList(currentList, targetIndexOneIndexed);

		commandBox.runCommand("delete deadline " + targetIndexOneIndexed);

		// confirm the list now contains all previous tasks except the deleted
		// task
		assertTrue(deadlineListPanel.isListMatching(expectedRemainder));

		// confirm the result message is correct
		assertResultMessage(
				String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete.getName().name.toString() + "\n" + "Date: "
						+ taskToDelete.getStartDate() + "\n" + "EndTime: " + new EndTime(taskToDelete.getEndTime())));
	}

	private void addAllDummyTodoTasks(TestTask... currentList) {
		for (TestTask t : currentList) {
			commandBox.runCommand(t.getAddCommand());
		}
	}

	private void addAllDummyEventTasks(TestEvent... currentList) {
		for (TestEvent t : currentList) {
			commandBox.runCommand(t.getAddCommand());
		}
	}

	private void addAllDummyDeadlineTasks(TestDeadline... currentList) {
		for (TestDeadline t : currentList) {
			commandBox.runCommand(t.getAddCommand());
		}
	}

}
```
###### /java/guitests/FindCommandTest.java
``` java
	public void find_nonEmptyList() throws IllegalValueException {

		TestTask[] currentList = td.getTypicaltasks();
		TestTask taskToAdd = new TaskBuilder().withName("assignment 1").withStartDate("28-11-2016")
				.withEndDate("29-11-2016").withPriority("1").withDone("false").build();
		assertAddSuccess(taskToAdd, currentList);
		currentList = TestUtil.addTasksToList(currentList, taskToAdd);
		TestTask taskToAdd1 = new TaskBuilder().withName("assignment 2").withStartDate("29-11-2016")
				.withEndDate("30-11-2016").withPriority("1").withDone("false").build();
		assertAddSuccess(taskToAdd1, currentList);
		currentList = TestUtil.addTasksToList(currentList, taskToAdd1);
		// assertFindResult("find to priority 999"); //no results
		assertFindResult("find todo assignment 1", taskToAdd1);

		// find after deleting one result
		// commandBox.runCommand("delete 1");
		// assertFindResult("find project 1",td.a2);
	}

	@Test
```
###### /java/guitests/FindCommandTest.java
``` java
	public void find_nonEmptyEventList() throws IllegalValueException {

		TestEvent[] currentList = ed.getTypicalEvent();
		TestEvent taskToAdd = new EventBuilder().withName("assignment 1").withStartDate("28-11-2016")
				.withEndDate("29-11-2016").withStartTime("01:00").withEndTime("01:30").withDone("false").build();
		assertAddEventSuccess(taskToAdd, currentList);
		currentList = TestUtil.addEventsToList(currentList, taskToAdd);
		TestEvent taskToAdd1 = new EventBuilder().withName("assignment 2").withStartDate("29-11-2016")
				.withEndDate("30-11-2016").withStartTime("01:00").withEndTime("01:30").withDone("false").build();
		assertAddEventSuccess(taskToAdd1, currentList);
		currentList = TestUtil.addEventsToList(currentList, taskToAdd1);
		// assertFindResult("find to priority 999"); //no results
		assertFindEventResult("find event assignment 1", taskToAdd1);
	}

	@Test
```
###### /java/guitests/FindCommandTest.java
``` java
	public void find_nonEmptyDeadlineList() throws IllegalValueException {

		TestDeadline[] currentList = dd.getTypicalDeadline();
		TestDeadline taskToAdd = new DeadlineBuilder().withName("assignment 1").withStartDate("28-11-2016")
				.withEndTime("01:30").withDone("false").build();
		assertAddDeadlineSuccess(taskToAdd, currentList);
		currentList = TestUtil.addDeadlinesToList(currentList, taskToAdd);
		TestDeadline taskToAdd1 = new DeadlineBuilder().withName("assignment 2").withStartDate("29-11-2016")
				.withEndTime("01:30").withDone("false").build();
		assertAddDeadlineSuccess(taskToAdd1, currentList);
		currentList = TestUtil.addDeadlinesToList(currentList, taskToAdd1);
		// assertFindResult("find to priority 999"); //no results
		assertFindDeadlineResult("find deadline assignment 1", taskToAdd1);
	}

	@Test
	public void find_emptyList() {
		commandBox.runCommand("clear event");
		assertFindResult("find assignment 99"); // no results
	}

	@Test
	public void find_invalidCommand_fail() {
		commandBox.runCommand("findassignment");
		assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
	}

	private void assertFindResult(String command, TestTask... expectedHits) {
		commandBox.runCommand(command);
		assertListSize(expectedHits.length);
		if (expectedHits.length == 0) {
			assertResultMessage("Invalid command format! \n"
					+ "find: Finds all tasks whose names or start date contain any of the specified keywords and displays them as a list with index numbers.\n"
					+ "Parameters: TASK_TYPE KEYWORD [MORE_KEYWORDS]...\n" + "Example: find all homework urgent\n"
					+ "               " + "find date/25th December 2016");
		} else {
			assertResultMessage(expectedHits.length + " tasks listed!");
		}
		// assertTrue(taskListPanel.isListMatching(expectedHits));
	}

```
###### /java/guitests/FindCommandTest.java
``` java
	private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
		addAllDummyTodoTasks(currentList);
		commandBox.runCommand(taskToAdd.getAddCommand());
		// confirm the new card contains the right data
		TaskCardHandle addedCard = taskListPanel.navigateTotask(taskToAdd.getName().name);
		assertMatching(taskToAdd, addedCard);
		// confirm the list now contains all previous tasks plus the new task
		TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
		assertTrue(taskListPanel.isListMatching(expectedList));
	}

```
###### /java/guitests/FindCommandTest.java
``` java
	private void addAllDummyTodoTasks(TestTask... currentList) {
		for (TestTask t : currentList) {
			commandBox.runCommand(t.getAddCommand());
		}
	}

```
###### /java/guitests/guihandles/DeadlineListPanelHandle.java
``` java
public class DeadlineListPanelHandle extends GuiHandle {

	public static final int NOT_FOUND = -1;
	public static final String CARD_PANE_ID = "#name";

	private static final String task_LIST_VIEW_ID = "#deadlineListView";

	public DeadlineListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
		super(guiRobot, primaryStage, TestApp.APP_TITLE);
	}

	public List<ReadOnlyTask> getSelectedtasks() {
		ListView<ReadOnlyTask> taskList = getListView();
		return taskList.getSelectionModel().getSelectedItems();
	}

	public ListView<ReadOnlyTask> getListView() {
		return (ListView<ReadOnlyTask>) getNode(task_LIST_VIEW_ID);
	}

	/**
	 * Returns true if the list is showing the task details correctly and in
	 * correct order.
	 * 
	 * @param tasks
	 *            A list of task in the correct order.
	 */
	public boolean isListMatching(ReadOnlyTask... tasks) {
		return this.isListMatching(0, tasks); // something wrong, always return
												// false!!!
	}

	/**
	 * Clicks on the ListView.
	 */
	public void clickOnListView() {
		Point2D point = TestUtil.getScreenMidPoint(getListView());
		guiRobot.clickOn(point.getX(), point.getY());
	}

	/**
	 * Returns true if the {@code tasks} appear as the sub list (in that order)
	 * at position {@code startPosition}.
	 */
	public boolean containsInOrder(int startPosition, ReadOnlyTask... tasks) {
		List<ReadOnlyTask> tasksInList = getListView().getItems();

		// Return false if the list in panel is too short to contain the given
		// list
		if (startPosition + tasks.length > tasksInList.size()) {
			return false;
		}

		// Return false if any of the tasks doesn't match
		for (int i = 0; i < tasks.length; i++) {
			if (!tasksInList.get(startPosition + i).getName().name.equals(tasks[i].getName().name)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns true if the list is showing the task details correctly and in
	 * correct order.
	 * 
	 * @param startPosition
	 *            The starting position of the sub list.
	 * @param tasks
	 *            A list of task in the correct order.
	 */
	public boolean isListMatching(int startPosition, ReadOnlyTask... tasks) throws IllegalArgumentException {
		if (tasks.length + startPosition != getListView().getItems().size()) {
			throw new IllegalArgumentException(
					"List size mismatched\n" + "Expected " + (getListView().getItems().size() - 1) + " tasks");
		}
		assertTrue(this.containsInOrder(startPosition, tasks));
		for (int i = 0; i < tasks.length; i++) {
			final int scrollTo = i + startPosition;
			guiRobot.interact(() -> getListView().scrollTo(scrollTo));
			guiRobot.sleep(200);
			if (!TestUtil.compareCardAndDeadline(getDeadlineCardHandle(startPosition + i), tasks[i])) {
				return false;
			}
		}
		return true;
	}

	public DeadlineCardHandle navigateToDeadline(String readOnlyTask) {
		guiRobot.sleep(500); // Allow a bit of time for the list to be updated
		final Optional<ReadOnlyTask> task = getListView().getItems().stream()
				.filter(p -> p.getName().name.equals(readOnlyTask)).findAny();
		if (!task.isPresent()) {
			throw new IllegalStateException("Task not found: " + readOnlyTask);
		}

		return navigateToDeadline(task.get());
	}

	/**
	 * Navigates the listview to display and select the task.
	 */
	public DeadlineCardHandle navigateToDeadline(ReadOnlyTask Deadline) {
		int index = getDeadlineIndex(Deadline); // SOmething wrong. Always
												// return 0

		guiRobot.interact(() -> {
			getListView().scrollTo(index);
			guiRobot.sleep(150);
			getListView().getSelectionModel().select(index);
		});
		guiRobot.sleep(100);
		return getDeadlineCardHandle(Deadline);
	}

	/**
	 * Returns the position of the task given, {@code NOT_FOUND} if not found in
	 * the list.
	 */
	public int getDeadlineIndex(ReadOnlyTask targettask) {
		List<ReadOnlyTask> tasksInList = getListView().getItems();
		for (int i = 0; i < tasksInList.size(); i++) {
			if (tasksInList.get(i).getName().equals(targettask.getName().name)) {
				return i;
			}
		}
		return NOT_FOUND;
	}

	/**
	 * Gets a task from the list by index
	 */
	public ReadOnlyTask getDeadline(int index) {
		return getListView().getItems().get(index);
	}

	public DeadlineCardHandle getDeadlineCardHandle(int task) {
		return getDeadlineCardHandle(new Deadline(getListView().getItems().get(task)));
	}

	public DeadlineCardHandle getDeadlineCardHandle(ReadOnlyTask Deadline) {
		Set<Node> nodes = getAllCardNodes();
		Optional<Node> DeadlineCardNode = nodes.stream()
				.filter(n -> new DeadlineCardHandle(guiRobot, primaryStage, n).isSameDeadline(Deadline)).findFirst();
		if (DeadlineCardNode.isPresent()) {
			return new DeadlineCardHandle(guiRobot, primaryStage, DeadlineCardNode.get());
		} else {
			return null;
		}
	}

	protected Set<Node> getAllCardNodes() {
		return guiRobot.lookup(CARD_PANE_ID).queryAll();
	}

	public int getNumberOfDeadlines() {
		return getListView().getItems().size();
	}
}
```
###### /java/guitests/guihandles/EventListPanelHandle.java
``` java
public class EventListPanelHandle extends GuiHandle {

	public static final int NOT_FOUND = -1;
	public static final String CARD_PANE_ID = "#name";

	private static final String event_LIST_VIEW_ID = "#eventListView";

	public EventListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
		super(guiRobot, primaryStage, TestApp.APP_TITLE);
	}

	public List<ReadOnlyTask> getSelectedtasks() {
		ListView<ReadOnlyTask> taskList = getListView();
		return taskList.getSelectionModel().getSelectedItems();
	}

	public ListView<ReadOnlyTask> getListView() {
		return (ListView<ReadOnlyTask>) getNode(event_LIST_VIEW_ID);
	}

	/**
	 * Returns true if the list is showing the task details correctly and in
	 * correct order.
	 * 
	 * @param tasks
	 *            A list of task in the correct order.
	 */
	public boolean isListMatching(ReadOnlyTask... tasks) {
		return this.isListMatching(0, tasks); // something wrong, always return
												// false!!!
	}

	/**
	 * Clicks on the ListView.
	 */
	public void clickOnListView() {
		Point2D point = TestUtil.getScreenMidPoint(getListView());
		guiRobot.clickOn(point.getX(), point.getY());
	}

	/**
	 * Returns true if the {@code tasks} appear as the sub list (in that order)
	 * at position {@code startPosition}.
	 */
	public boolean containsInOrder(int startPosition, ReadOnlyTask... tasks) {
		List<ReadOnlyTask> tasksInList = getListView().getItems();

		// Return false if the list in panel is too short to contain the given
		// list
		if (startPosition + tasks.length > tasksInList.size()) {
			return false;
		}

		// Return false if any of the tasks doesn't match
		for (int i = 0; i < tasks.length; i++) {
			if (!tasksInList.get(startPosition + i).getName().name.equals(tasks[i].getName().name)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns true if the list is showing the task details correctly and in
	 * correct order.
	 * 
	 * @param startPosition
	 *            The starting position of the sub list.
	 * @param tasks
	 *            A list of task in the correct order.
	 */
	public boolean isListMatching(int startPosition, ReadOnlyTask... tasks) throws IllegalArgumentException {
		if (tasks.length + startPosition != getListView().getItems().size()) {
			throw new IllegalArgumentException(
					"List size mismatched\n" + "Expected " + (getListView().getItems().size() - 1) + " tasks");
		}
		assertTrue(this.containsInOrder(startPosition, tasks));
		for (int i = 0; i < tasks.length; i++) {
			final int scrollTo = i + startPosition;
			guiRobot.interact(() -> getListView().scrollTo(scrollTo));
			guiRobot.sleep(200);
			if (!TestUtil.compareCardAndEvent(getEventCardHandle(startPosition + i), tasks[i])) {
				return false;
			}
		}
		return true;
	}

```
###### /java/guitests/guihandles/TaskCardHandle.java
``` java
public class TaskCardHandle extends GuiHandle {
	private static final String NAME_FIELD_ID = "#name";
	private static final String STARTDATE_FIELD_ID = "#startDate";
	private static final String ENDDATE_FIELD_ID = "#endDate";
	private static final String PRIORITY_FIELD_ID = "#priority";
	private static final String DONE_FIELD_ID = "#isDone";

	private Node node;

	public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
		super(guiRobot, primaryStage, null);
		this.node = node;
	}

	protected String getTextFromLabel(String fieldId) {
		return getTextFromLabel(fieldId, node);
	}

	public String getName() {
		return getTextFromLabel(NAME_FIELD_ID);
	}

	public String getDate() {
		return getTextFromLabel(STARTDATE_FIELD_ID);
	}

	public String getEndDate() {
		return getTextFromLabel(ENDDATE_FIELD_ID);
	}

	public String getPriority() {
		return getTextFromLabel(PRIORITY_FIELD_ID);
	}

	public String getDone() {
		return getTextFromLabel(DONE_FIELD_ID);
	}

	public boolean isSametask(ReadOnlyTask task) {
		return getName().equals(task.getName().name); // &&
		// getDate().equals(task.getStartDate().date)
		// && getPriority().equals(task.getPriority().priority);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TaskCardHandle) {
			TaskCardHandle handle = (TaskCardHandle) obj;
			return getName().equals(handle.getName()) && getDate().equals(handle.getDate())
					&& getEndDate().equals(handle.getEndDate()) && getPriority().equals(handle.getPriority())
					&& getDone().equals(handle.getDone());
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return getName() + " " + getDate() + " " + getEndDate() + " " + getPriority() + " " + getDone();
	}
}
```
###### /java/guitests/guihandles/TaskListPanelHandle.java
``` java
public class TaskListPanelHandle extends GuiHandle {

	public static final int NOT_FOUND = -1;
	public static final String CARD_PANE_ID = "#name";

	private static final String task_LIST_VIEW_ID = "#todoListView";

	public TaskListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
		super(guiRobot, primaryStage, TestApp.APP_TITLE);
	}

	public List<ReadOnlyTask> getSelectedtasks() {
		ListView<ReadOnlyTask> taskList = getListView();
		return taskList.getSelectionModel().getSelectedItems();
	}

	public ListView<ReadOnlyTask> getListView() {
		return (ListView<ReadOnlyTask>) getNode(task_LIST_VIEW_ID);

	}

	/**
	 * Returns true if the list is showing the task details correctly and in
	 * correct order.
	 * 
	 * @param tasks
	 *            A list of task in the correct order.
	 */
	public boolean isListMatching(ReadOnlyTask... tasks) {
		return this.isListMatching(0, tasks); // something wrong, always return
												// false!!!
	}

	/**
	 * Clicks on the ListView.
	 */
	public void clickOnListView() {
		Point2D point = TestUtil.getScreenMidPoint(getListView());
		guiRobot.clickOn(point.getX(), point.getY());
	}

	/**
	 * Returns true if the {@code tasks} appear as the sub list (in that order)
	 * at position {@code startPosition}.
	 */
	public boolean containsInOrder(int startPosition, ReadOnlyTask... tasks) {
		List<ReadOnlyTask> tasksInList = getListView().getItems();

		// Return false if the list in panel is too short to contain the given
		// list
		if (startPosition + tasks.length > tasksInList.size()) {
			return false;
		}

		// Return false if any of the tasks doesn't match
		for (int i = 0; i < tasks.length; i++) {
			if (!tasksInList.get(startPosition + i).getName().name.equals(tasks[i].getName().name)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns true if the list is showing the task details correctly and in
	 * correct order.
	 * 
	 * @param startPosition
	 *            The starting position of the sub list.
	 * @param tasks
	 *            A list of task in the correct order.
	 */
	public boolean isListMatching(int startPosition, ReadOnlyTask... tasks) throws IllegalArgumentException {
		if (tasks.length + startPosition != getListView().getItems().size()) {
			throw new IllegalArgumentException("List size mismatched\n" + "Expected " + tasks.length + " "
					+ (getListView().getItems().size()) + " tasks");
		}
		assertTrue(this.containsInOrder(startPosition, tasks));
		for (int i = 0; i < tasks.length; i++) {
			final int scrollTo = i + startPosition;
			guiRobot.interact(() -> getListView().scrollTo(scrollTo));
			guiRobot.sleep(200);
			if (!TestUtil.compareCardAndTask(getTaskCardHandle(startPosition + i), tasks[i])) {
				return false;
			}
		}
		return true;
	}

```
###### /java/guitests/ListGuiTest.java
``` java
public abstract class ListGuiTest {

	/*
	 * The TestName Rule makes the current test name available inside test
	 * methods
	 */
	@Rule
	public TestName name = new TestName();

	TestApp testApp;

	protected TypicalTestTask td = new TypicalTestTask();
	protected TypicalTestEvent ed = new TypicalTestEvent();
	protected TypicalTestDeadline dd = new TypicalTestDeadline();

	/*
	 * Handles to GUI elements present at the start up are created in advance
	 * for easy access from child classes.
	 */
	protected MainGuiHandle mainGui;
	protected MainMenuHandle mainMenu;
	protected TaskListPanelHandle taskListPanel;
	protected EventListPanelHandle eventListPanel;
	protected DeadlineListPanelHandle deadlineListPanel;
	protected ResultDisplayHandle resultDisplay;
	protected CommandBoxHandle commandBox;
	private Stage stage;

	@BeforeClass
	public static void setupSpec() {
		try {
			FxToolkit.registerPrimaryStage();
			FxToolkit.hideStage();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	@Before
	public void setup() throws Exception {
		FxToolkit.setupStage((stage) -> {
			mainGui = new MainGuiHandle(new GuiRobot(), stage);
			mainMenu = mainGui.getMainMenu();
			taskListPanel = mainGui.getTaskListPanel();
			eventListPanel = mainGui.getEventListPanel();
			deadlineListPanel = mainGui.getDeadlineListPanel();
			resultDisplay = mainGui.getResultDisplay();
			commandBox = mainGui.getCommandBox();
			this.stage = stage;
		});
		EventsCenter.clearSubscribers();
		testApp = (TestApp) FxToolkit.setupApplication(() -> new TestApp(this::getInitialData, getDataFileLocation()));
		FxToolkit.showStage();
		while (!stage.isShowing())
			;
		mainGui.focusOnMainApp();
	}

	/**
	 * Override this in child classes to set the initial local data. Return null
	 * to use the data in the file specified in {@link #getDataFileLocation()}
	 */
```
###### /java/guitests/ListGuiTest.java
``` java
	protected TaskList getInitialData() {
		TaskList ab = TestUtil.generateEmptyTodoList();
		// TaskList cd = TestUtil.generateEmptyEventList();
		// TaskList ef = TestUtil.generateEmptyDeadlineList();
		TypicalTestTask.loadTodoListWithSampleData(ab);
		// TypicalTestEvent.loadEventListWithSampleData(cd);
		// TypicalTestDeadline.loadDeadlineListWithSampleData(ef);
		return ab;
	}

	/**
	 * Override this in child classes to set the data file location.
	 * 
	 * @return
	 */
	protected String getDataFileLocation() {
		return TestApp.SAVE_LOCATION_FOR_TESTING;
	}

	@After
	public void cleanup() throws TimeoutException {
		FxToolkit.cleanupStages();
	}

	/**
	 * Asserts the task shown in the card is same as the given task
	 */
	public void assertMatching(ReadOnlyTask task, TaskCardHandle card) {
		assertTrue(TestUtil.compareCardAndTask(card, task));
	}

```
###### /java/guitests/ListGuiTest.java
``` java
	public void assertEventMatching(ReadOnlyTask event, EventCardHandle card) {
		assertTrue(TestUtil.compareCardAndEvent(card, event));
	}

```
###### /java/guitests/ListGuiTest.java
``` java
	public void assertDeadlineMatching(ReadOnlyTask dd, DeadlineCardHandle card) {
		assertTrue(TestUtil.compareCardAndDeadline(card, dd));
	}

	/**
	 * Asserts the size of the task list is equal to the given number.
	 */
	protected void assertListSize(int size) {
		int numberOfTask = taskListPanel.getNumberOfTasks();
		assertEquals(size, numberOfTask);
	}

```
###### /java/guitests/ListGuiTest.java
``` java
	protected void assertEventListSize(int size) {
		int numberOfTask = eventListPanel.getNumberOfEvents();
		assertEquals(size, numberOfTask);
	}

```
###### /java/guitests/ListGuiTest.java
``` java
	protected void assertDeadlineListSize(int size) {
		int numberOfTask = deadlineListPanel.getNumberOfDeadlines();
		assertEquals(size, numberOfTask);
	}

	/**
	 * Asserts the message shown in the Result Display area is same as the given
	 * string.
	 * 
	 * @param expected
	 */
	protected void assertResultMessage(String expected) {
		assertEquals(expected, resultDisplay.getText());
	}
}
```
###### /java/seedu/Tdoo/commons/util/XmlUtilTest.java
``` java
	 public void saveDataToFile_validFile_dataSaved() throws Exception {
	 TEMP_FILE.createNewFile();
	
	 XmlSerializableTodoList dataToWrite = new XmlSerializableTodoList(new
	 TaskList());
	 XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
	 XmlSerializableTodoList dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE,
	 XmlSerializableTodoList.class);
	 assertEquals((new TaskList(dataToWrite)).toString(),(new
	 TaskList(dataFromFile)).toString());

	 }
}
```
###### /java/seedu/Tdoo/logic/LogicManagerTest.java
``` java
	public void setup() {
		model = new ModelManager();
		String tempTodoListFile = saveFolder.getRoot().getPath() + "TempTodoList.xml";
		String tempEventListFile = saveFolder.getRoot().getPath() + "TempEventList.xml";
		String tempDeadlineListFile = saveFolder.getRoot().getPath() + "TempDeadlineList.xml";
		String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
		logic = new LogicManager(model,
				new StorageManager(tempTodoListFile, tempEventListFile, tempDeadlineListFile, tempPreferencesFile));
		EventsCenter.getInstance().registerHandler(this);

		latestSavedTodoList = new TaskList(model.getTodoList()); // last saved
																	// assumed
																	// to be up
																	// to date
																	// before.
		latestSavedEventList = new TaskList(model.getEventList()); // last saved
																	// assumed
																	// to be up
																	// to date
																	// before.
		latestSavedDeadlineList = new TaskList(model.getDeadlineList()); // last
																			// saved
																			// assumed
																			// to
																			// be
																			// up
																			// to
																			// date
																			// before.
		helpShown = false;
		targetedJumpIndex = -1; // non yet
	}

	@After
	public void teardown() {
		EventsCenter.clearSubscribers();
	}

	@Test
	public void execute_invalid() throws Exception {
		String invalidCommand = "       ";
		assertCommandBehavior(invalidCommand, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
	}

	/**
	 * Executes the command and confirms that the result message is correct.
	 * Both the 'TodoList' and the 'last shown list' are expected to be empty.
	 * 
	 * @see #assertCommandBehavior(String, String, ReadOnlyTodoList, List)
	 */
	private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
		assertCommandBehavior(inputCommand, expectedMessage, new TaskList(), Collections.emptyList());
	}

	/**
	 * Executes the command and confirms that the result message is correct and
	 * also confirms that the following three parts of the LogicManager object's
	 * state are as expected:<br>
	 * - the internal TodoList data are same as those in the
	 * {@code expectedTodoList} <br>
	 * - the backing list shown by UI matches the {@code shownList} <br>
	 * - {@code expectedTodoList} was saved to the storage file. <br>
	 */
	private void assertCommandBehavior(String inputCommand, String expectedMessage, ReadOnlyTaskList expectedTodoList,
			List<? extends ReadOnlyTask> expectedShownList) throws Exception {

		// Execute the command
		CommandResult result = logic.execute(inputCommand);

		// Confirm the ui display elements should contain the right data
		assertEquals(expectedMessage, result.feedbackToUser);
		// assertEquals(expectedShownList, model.getFilteredTodoList());
		// Confirm the state of data (saved and in-memory) is as expected
		// assertEquals(expectedTodoList, model.getTodoList());
		// assertEquals(expectedTodoList, latestSavedTodoList);
	}

	@Test
	public void execute_unknownCommandWord() throws Exception {
		String unknownCommand = "uicfhmowqewca";
		assertCommandBehavior(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
	}

	@Test
	public void execute_help() throws Exception {
		assertCommandBehavior("help", HelpCommand.SHOWING_HELP_MESSAGE);
		assertTrue(helpShown);
	}

	@Test
	public void execute_exit() throws Exception {
		assertCommandBehavior("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT);
	}

	@Test
```
###### /java/seedu/Tdoo/logic/LogicManagerTest.java
``` java
	public void execute_add_invalidArgsFormat() throws Exception {
		String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
		assertCommandBehavior("add wrong args wrong args", expectedMessage);
		assertCommandBehavior("add Valid Name 12345 e/valid@email.butNoPhonePrefix a/valid, Todo", expectedMessage);
		assertCommandBehavior("add Valid Name p/12345 valid@email.butNoPrefix a/valid, Todo", expectedMessage);
		assertCommandBehavior("add Valid Name p/12345 e/valid@email.butNoTodoPrefix valid, Todo", expectedMessage);
	}

	@Test
```
###### /java/seedu/Tdoo/logic/LogicManagerTest.java
``` java
	public void execute_add_invalidtaskData() throws Exception {
		assertCommandBehavior("add todo[]\\[;] /11-12-2016 e/valid@e.mail a/valid, Todo",
				"Invalid command format! \n" + AddCommand.MESSAGE_USAGE);
		assertCommandBehavior("add event from/33-12-2016 Name p/not_numbers e/valid@e.mail a/valid, Todo",
				"Invalid command format! \n" + AddCommand.MESSAGE_USAGE);
		assertCommandBehavior("add deadline Name p/12345 e/notAnEmail a/valid, Todo",
				"Invalid command format! \n" + AddCommand.MESSAGE_USAGE);

	}

	@Test
```
###### /java/seedu/Tdoo/logic/LogicManagerTest.java
``` java
	public void execute_addTodo_successful() throws Exception {
		// setup expectations
		TestDataHelper helper = new TestDataHelper();

		Todo toBeAdded = helper.todoHelper();
		TaskList expectedAB = new TaskList();

		expectedAB.addTask(toBeAdded);

		// execute command and verify result
		assertCommandBehavior(
				helper.generateAddTodoCommand(toBeAdded), String
						.format(AddCommand.MESSAGE_SUCCESS,
								toBeAdded.getName().toString() + " from/" + toBeAdded.getStartDate().date.toString()
										+ " to/" + toBeAdded.getEndDate().endDate.toString()),
				expectedAB, expectedAB.getTaskList());
	}

	@Test
```
###### /java/seedu/Tdoo/logic/LogicManagerTest.java
``` java
	private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
		String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		TestDataHelper helper = new TestDataHelper();
		Task pTarget1 = helper.generatetask("todo 2");
		Task pTarget2 = helper.generatetask("todo 3");
		Task pTarget3 = helper.generatetask("todo 1");
		List<Task> taskList = helper.generatetaskList(pTarget1, pTarget2, pTarget3);

		// set AB state to 2 tasks
		model.resetTodoListData(new TaskList());
		for (Task p : taskList) {
			model.addTask(p);
		}

		assertCommandBehavior(commandWord, expectedMessage, model.getTodoList(), taskList);
	}

	/*
	 * @Test public void execute_selectInvalidArgsFormat_errorMessageShown()
	 * throws Exception { String expectedMessage =
	 * String.format(MESSAGE_INVALID_COMMAND_FORMAT,
	 * SelectCommand.MESSAGE_USAGE);
	 * assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
	 * }
	 */

	/*
	 * @Test public void execute_selectIndexNotFound_errorMessageShown() throws
	 * Exception { assertIndexNotFoundBehaviorForCommand("select"); }
	 * 
	 * @Test public void execute_select_jumpsToCorrecttask() throws Exception {
	 * TestDataHelper helper = new TestDataHelper(); List<Task> threetasks =
	 * helper.generatetaskList(3);
	 * 
	 * TaskList expectedAB = helper.generateTodoList(threetasks);
	 * helper.addToModel(model, threetasks);
	 * 
	 * assertCommandBehavior("select 2",
	 * String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2), expectedAB,
	 * expectedAB.getTaskList()); assertEquals(1, targetedJumpIndex);
	 * assertEquals(model.getFilteredTodoList().get(1), threetasks.get(1)); }
	 */

	@Test
	public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
		String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
		assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
	}

	@Test
```
###### /java/seedu/Tdoo/logic/LogicManagerTest.java
``` java
	public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
		assertIndexNotFoundBehaviorForCommand("delete todo 1");
	}

	@Test
```
###### /java/seedu/Tdoo/logic/LogicManagerTest.java
``` java
	public void execute_delete_removesCorrectTodo() throws Exception {
		String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		TestDataHelper helper = new TestDataHelper();
		Task pTarget1 = helper.generatetask("todo 2");
		Task pTarget2 = helper.generatetask("todo 3");
		Task pTarget3 = helper.generatetask("todo 1");
		Task pTarget4 = helper.generatetask("todo 4");

		List<Task> fourtasks = helper.generatetaskList(pTarget3, pTarget1, pTarget2, pTarget4);
		TaskList expectedAB = helper.generateTodoList(fourtasks);
		helper.addToModel(model, fourtasks);

		expectedAB.removeTask(fourtasks.get(1));

		assertCommandBehavior("delete todo 2",
				String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, fourtasks.get(1)), expectedAB,
				expectedAB.getTaskList());
	}

	@Test
```
###### /java/seedu/Tdoo/logic/LogicManagerTest.java
``` java
	class TestDataHelper {

		Todo todoHelper() throws Exception {
			Name name = new Name("TODO 111");
			StartDate date = new StartDate("01-01-2017");
			EndDate endDate = new EndDate("02-01-2017");
			Priority priority = new Priority("1");
			String isDone = "false";
			return new Todo(name, date, endDate, priority, isDone);
		}

```
###### /java/seedu/Tdoo/logic/LogicManagerTest.java
``` java
		Deadline deadlineHelper() throws Exception {
			Name name = new Name("DEADLINE 111");
			StartDate startDate = new StartDate("01-12-2016");
			EndTime endTime = new EndTime("02:00");
			String isDone = "false";
			return new Deadline(name, startDate, endTime, isDone);
		}

		/**
		 * Generates a valid task using the given seed. Running this function
		 * with the same parameter values guarantees the returned task will have
		 * the same state. Each unique seed will generate a unique task object.
		 *
		 * @param seed
		 *            used to generate the task data field values
		 */
```
###### /java/seedu/Tdoo/storage/StorageManagerTest.java
``` java
	public void setup() {
		storageManager = new StorageManager(getTempFilePath("TodoList.xml"), getTempFilePath("EventList.xml"),
				getTempFilePath("DeadlineList.xml"), getTempFilePath("prefs"));
	}

	private String getTempFilePath(String fileName) {
		return testFolder.getRoot().getPath() + fileName;
	}

	/*
	 * Note: This is an integration test that verifies the StorageManager is
	 * properly wired to the {@link JsonUserPrefsStorage} class. More extensive
	 * testing of UserPref saving/reading is done in {@link
	 * JsonUserPrefsStorageTest} class.
	 */

	@Test
	public void prefsReadSave() throws Exception {
		UserPrefs original = new UserPrefs();
		original.setGuiSettings(300, 600, 4, 6);
		storageManager.saveUserPrefs(original);
		UserPrefs retrieved = storageManager.readUserPrefs().get();
		assertEquals(original, retrieved);
	}

	@Test
	public void TodoListReadSave() throws Exception {
		TaskList original = new TypicalTestTask().getTypicalTodoList();
		LogsCenter.getLogger(StorageManagerTest.class).info("XXXXXX: " + original.getTasks());
		storageManager.saveTodoList(original);
		ReadOnlyTaskList retrieved = storageManager.readTodoList().get();
		LogsCenter.getLogger(StorageManagerTest.class).info("gretrieved et(): " + retrieved.toString());

		assertEquals(original, new TaskList(retrieved));
		// More extensive testing of TodoList saving/reading is done in
		// XmlTodoListStorageTest
	}

	@Test
```
###### /java/seedu/Tdoo/storage/StorageManagerTest.java
``` java
	public void EventListReadSave() throws Exception {
		TaskList original = new TypicalTestEvent().getTypicalEventList();
		storageManager.saveEventList(original);
		ReadOnlyTaskList retrieved = storageManager.readEventList().get();
		assertEquals(original, new TaskList(retrieved));
	}

	@Test
```
###### /java/seedu/Tdoo/storage/StorageManagerTest.java
``` java
	public void DeadlineListReadSave() throws Exception {
		TaskList original = new TypicalTestDeadline().getTypicalDeadlineList();
		storageManager.saveDeadlineList(original);
		ReadOnlyTaskList retrieved = storageManager.readDeadlineList().get();
		assertEquals(original, new TaskList(retrieved));
	}

	@Test
	public void getTodoListFilePath() {
		assertNotNull(storageManager.getTodoListFilePath());
	}

	@Test
```
###### /java/seedu/Tdoo/storage/StorageManagerTest.java
``` java
	public void getEventListFilePath() {
		assertNotNull(storageManager.getEventListFilePath());
	}

	@Test
```
###### /java/seedu/Tdoo/storage/StorageManagerTest.java
``` java
	public void getDeadlineListFilePath() {
		assertNotNull(storageManager.getDeadlineListFilePath());
	}

	@Test
	public void handleTodoListChangedEvent_exceptionThrown_eventRaised() throws IOException, IllegalValueException {
		// Create a StorageManager while injecting a stub with wrong format that
		// throws an exception when the save method is called
		Storage storage = new StorageManager(new XmlTodoListStorageExceptionThrowingStub("dummy"),
				new XmlEventListStorageExceptionThrowingStub("dummy"),
				new XmlDeadlineListStorageExceptionThrowingStub("dummy"), new JsonUserPrefsStorage("dummy"));
		EventsCollector eventCollector = new EventsCollector();
		storage.handleTodoListChangedEvent(new TodoListChangedEvent(new TodoListExceptionThrowingStub()));
		assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
	}

	@Test
```
###### /java/seedu/Tdoo/storage/StorageManagerTest.java
``` java
	public void handleEventListChangedEvent_exceptionThrown_eventRaised() throws IOException, IllegalValueException {
		// Create a StorageManager while injecting a stub that throws an
		// exception when the save method is called
		Storage storage = new StorageManager(null, new XmlEventListStorageExceptionThrowingStub("dummy"), null,
				new JsonUserPrefsStorage("dummy"));
		EventsCollector eventCollector = new EventsCollector();
		storage.handleEventListChangedEvent(new EventListChangedEvent(new EventListExceptionThrowingStub()));
		assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
	}

	@Test
```
###### /java/seedu/Tdoo/storage/StorageManagerTest.java
``` java
	public void handleDeadlineListChangedEvent_exceptionThrown_eventRaised() throws IOException, IllegalValueException {
		// Create a StorageManager while injecting a stub that throws an
		// exception when the save method is called
		Storage storage = new StorageManager(null, null, new XmlDeadlineListStorageExceptionThrowingStub("dummy"),
				new JsonUserPrefsStorage("dummy"));
		EventsCollector eventCollector = new EventsCollector();
		storage.handleDeadlineListChangedEvent(new DeadlineListChangedEvent(new DeadlineListExceptionThrowingStub()));
		assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
	}

```
###### /java/seedu/Tdoo/storage/StorageManagerTest.java
``` java
	class XmlEventListStorageExceptionThrowingStub extends XmlEventListStorage {

		public XmlEventListStorageExceptionThrowingStub(String filePath) {
			super(filePath);
		}

		// @Override
		public void saveEventList(ReadOnlyTaskList EventList, String filePath) throws IOException {
			throw new IOException("dummy exception");
		}
	}

```
###### /java/seedu/Tdoo/storage/StorageManagerTest.java
``` java
	class XmlDeadlineListStorageExceptionThrowingStub extends XmlDeadlineListStorage {

		public XmlDeadlineListStorageExceptionThrowingStub(String filePath) {
			super(filePath);
		}

		// @Override
		public void saveDeadlineList(ReadOnlyTaskList DeadlineList, String filePath) throws IOException {
			throw new IOException("dummy exception");
		}
	}

}
```
###### /java/seedu/Tdoo/storage/XmlTodoListStorageTest.java
``` java
	public void readAndSaveTodoList_allInOrder_success() throws Exception {
		String filePath = testFolder.getRoot().getPath() + "TempTodoList.xml";
		TypicalTestTask td = new TypicalTestTask();
		TaskList original = td.getTypicalTodoList();
		XmlTodoListStorage xmlTodoListStorage = new XmlTodoListStorage(filePath);

		// Save in new file and read back
		xmlTodoListStorage.saveTaskList(original, filePath);
		ReadOnlyTaskList readBack = xmlTodoListStorage.readTaskList(filePath).get();
		assertEquals(original, new TaskList(readBack));

		// Modify data, overwrite exiting file, and read back
		original.addTask(new Todo(new Name("todo 123"), new StartDate("11-11-2016"), new EndDate("12-11-2016"),
				new Priority("1"), ("false")));
		original.removeTask(new Todo(new Name("todo 123"), new StartDate("11-11-2016"), new EndDate("12-11-2016"),
				new Priority("1"), ("false")));
		xmlTodoListStorage.saveTaskList(original, filePath);
		readBack = xmlTodoListStorage.readTaskList(filePath).get();
		assertEquals(original, new TaskList(readBack));

		// Save and read without specifying file path
		original.addTask(new Todo(new Name("todo 123"), new StartDate("11-11-2016"), new EndDate("12-11-2016"),
				new Priority("1"), ("false")));
		xmlTodoListStorage.saveTaskList(original); // file path not specified
		readBack = xmlTodoListStorage.readTaskList().get(); // file path not
															// specified
		assertEquals(original, new TaskList(readBack));
	}

	@Test
```
###### /java/seedu/Tdoo/storage/XmlTodoListStorageTest.java
``` java
	public void readAndSaveEventList_allInOrder_success() throws Exception {
		String filePath = testFolder.getRoot().getPath() + "TempEventList.xml";
		TypicalTestEvent td = new TypicalTestEvent();
		TaskList original = td.getTypicalEventList();
		XmlEventListStorage xmlEventListStorage = new XmlEventListStorage(filePath);

		// Save in new file and read back
		xmlEventListStorage.saveTaskList(original, filePath);
		ReadOnlyTaskList readBack = xmlEventListStorage.readTaskList(filePath).get();
		assertEquals(original, new TaskList(readBack));

		// Modify data, overwrite exiting file, and read back
		original.addTask(new Event(new Name("todo 123"), new StartDate("11-11-2016"), new EndDate("12-11-2016"),
				new StartTime("01:00"), new EndTime("01:30"), ("false")));
		original.removeTask(new Event(new Name("todo 123"), new StartDate("11-11-2016"), new EndDate("12-11-2016"),
				new StartTime("01:00"), new EndTime("01:30"), ("false")));
		xmlEventListStorage.saveTaskList(original, filePath);
		readBack = xmlEventListStorage.readTaskList(filePath).get();
		assertEquals(original, new TaskList(readBack));

		// Save and read without specifying file path
		original.addTask(new Event(new Name("todo 123"), new StartDate("11-11-2016"), new EndDate("12-11-2016"),
				new StartTime("01:00"), new EndTime("01:30"), ("false")));
		xmlEventListStorage.saveTaskList(original); // file path not specified
		readBack = xmlEventListStorage.readTaskList().get(); // file path not
																// specified
		assertEquals(original, new TaskList(readBack));
	}

	@Test
```
###### /java/seedu/Tdoo/storage/XmlTodoListStorageTest.java
``` java
	public void readAndSaveDeadlineList_allInOrder_success() throws Exception {
		String filePath = testFolder.getRoot().getPath() + "TempDeadlineList.xml";
		TypicalTestDeadline td = new TypicalTestDeadline();
		TaskList original = td.getTypicalDeadlineList();
		XmlDeadlineListStorage xmlDeadlineListStorage = new XmlDeadlineListStorage(filePath);

		// Save in new file and read back
		xmlDeadlineListStorage.saveTaskList(original, filePath);
		ReadOnlyTaskList readBack = xmlDeadlineListStorage.readTaskList(filePath).get();
		assertEquals(original, new TaskList(readBack));

		// Modify data, overwrite exiting file, and read back
		original.addTask(
				new Deadline(new Name("todo 123"), new StartDate("11-11-2016"), new EndTime("01:30"), ("false")));
		original.removeTask(
				new Deadline(new Name("todo 123"), new StartDate("11-11-2016"), new EndTime("01:30"), ("false")));
		xmlDeadlineListStorage.saveTaskList(original, filePath);
		readBack = xmlDeadlineListStorage.readTaskList(filePath).get();
		assertEquals(original, new TaskList(readBack));

		// Save and read without specifying file path
		original.addTask(
				new Deadline(new Name("todo 123"), new StartDate("11-11-2016"), new EndTime("01:30"), ("false")));
		xmlDeadlineListStorage.saveTaskList(original); // file path not
														// specified
		readBack = xmlDeadlineListStorage.readTaskList().get(); // file path not
																// specified
		assertEquals(original, new TaskList(readBack));
	}

	@Test
	public void saveTodoList_nullTodoList_assertionFailure() throws IOException {
		thrown.expect(AssertionError.class);
		saveTaskList(null, ".xml");
	}

	public void saveTaskList(ReadOnlyTaskList TodoList, String filePath) throws IOException {
		new XmlTodoListStorage(filePath).saveTaskList(TodoList, addToTestDataPathIfNotNull(filePath));
	}

	@Test
	public void saveTodoList_nullFilePath_assertionFailure() throws IOException {
		thrown.expect(AssertionError.class);
		saveTaskList(new TaskList(), null);
	}

	@Override
	public String getTaskListFilePath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTaskListFilePath(String filePath) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<ReadOnlyTaskList> readTaskList() throws DataConversionException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ReadOnlyTaskList> readTaskList(String filePath) throws DataConversionException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveTaskList(ReadOnlyTaskList TaskList) throws IOException {
		// TODO Auto-generated method stub

	}

	// @Override
	// public void saveTaskList(ReadOnlyTaskList TaskList, String filePath)
	// throws IOException {
	// // TODO Auto-generated method stub
	//
	// }

}
```
###### /java/seedu/Tdoo/TestApp.java
``` java
public class TestApp extends MainApp {

	public static final String SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleData.xml");
	public static final String SAVE_LOCATION_FOR_EVENT_TESTING = TestUtil.getFilePathInSandboxFolder("sampleEventData.xml");
	public static final String SAVE_LOCATION_FOR_DEADLINE_TESTING = TestUtil.getFilePathInSandboxFolder("sampleDeadlineData.xml");
	protected static final String DEFAULT_PREF_FILE_LOCATION_FOR_TESTING = TestUtil
			.getFilePathInSandboxFolder("pref_testing.json");
	public static final String APP_TITLE = "Test App";
	protected static final String Todo_BOOK_NAME = "Test";
	protected static final String Event_BOOK_NAME = "TestEvent";
	protected static final String Deadline_BOOK_NAME = "TestDeadline";
	protected Supplier<ReadOnlyTaskList> initialDataSupplier = () -> null;
	protected String saveFileLocation = SAVE_LOCATION_FOR_TESTING;
	protected String saveEventFileLocation = SAVE_LOCATION_FOR_EVENT_TESTING;
	protected String saveDeadlineFileLocation = SAVE_LOCATION_FOR_DEADLINE_TESTING;

	public TestApp() {
	}

	public TestApp(Supplier<ReadOnlyTaskList> initialDataSupplier, String saveFileLocation) {
		super();
		this.initialDataSupplier = initialDataSupplier;
		this.saveFileLocation = saveFileLocation;

		// If some initial local data has been provided, write those to the file
		if (initialDataSupplier.get() != null) {
			TestUtil.createDataFileWithData(new XmlSerializableTodoList(this.initialDataSupplier.get()),
					this.saveFileLocation);
		}
	}

	@Override
	protected Config initConfig(String configFilePath) {
		Config config = super.initConfig(configFilePath);
		config.setAppTitle(APP_TITLE);
		config.setTodoListFilePath(saveFileLocation);
		config.setEventListFilePath(saveEventFileLocation);
	    config.setDeadlineListFilePath(saveDeadlineFileLocation);
		config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
		config.setTodoListName(Todo_BOOK_NAME);
		config.setEventListName(Event_BOOK_NAME);
		config.setDeadlineListName(Deadline_BOOK_NAME);
		return config;
	}

	@Override
	protected UserPrefs initPrefs(Config config) {
		UserPrefs userPrefs = super.initPrefs(config);
		double x = Screen.getPrimary().getVisualBounds().getMinX();
		double y = Screen.getPrimary().getVisualBounds().getMinY();
		userPrefs.updateLastUsedGuiSetting(new GuiSettings(600.0, 600.0, (int) x, (int) y));
		return userPrefs;
	}

	@Override
	public void start(Stage primaryStage) {
		ui.start(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
```
###### /java/seedu/Tdoo/testutil/DeadlineListBuilder.java
``` java
public class DeadlineListBuilder {

	private TaskList DeadlineList;

	public DeadlineListBuilder(TaskList DeadlineList) {
		this.DeadlineList = DeadlineList;
	}

	public DeadlineListBuilder withTask(Task task) throws UniqueTaskList.DuplicatetaskException {
		DeadlineList.addTask(task);
		return this;
	}

	public TaskList build() {
		return DeadlineList;
	}
}
```
###### /java/seedu/Tdoo/testutil/EventListBuilder.java
``` java
public class EventListBuilder {

	private TaskList EventList;

	public EventListBuilder(TaskList EventList) {
		this.EventList = EventList;
	}

	public EventListBuilder withTask(Task task) throws UniqueTaskList.DuplicatetaskException {
		EventList.addTask(task);
		return this;
	}

	public TaskList build() {
		return EventList;
	}
}
```
###### /java/seedu/Tdoo/testutil/TaskBuilder.java
``` java
public class TaskBuilder {

	private TestTask task;

	public TaskBuilder() {
		this.task = new TestTask();
	}

	public TaskBuilder withName(String name) throws IllegalValueException {
		this.task.setName(new Name(name));
		return this;
	}

	public TaskBuilder withStartDate(String date) throws IllegalValueException {
		this.task.setStartDate(new StartDate(date));
		return this;
	}

	public TaskBuilder withEndDate(String date) throws IllegalValueException {
		this.task.setEndDate(date);
		return this;
	}

	public TaskBuilder withDone(String dd) throws IllegalValueException {
		this.task.setDone(dd);
		return this;
	}

	public TaskBuilder withPriority(String priority) throws IllegalValueException {
		this.task.setPriority(priority);
		return this;
	}

	public TestTask build() {
		return this.task;
	}

}
```
###### /java/seedu/Tdoo/testutil/TestTask.java
``` java
public class TestTask implements ReadOnlyTask {

	private Name name;
	private String priority;
	private StartDate startDate;
	private String endDate;
	private String done;

	public TestTask() {
	}

	public void setName(Name name) {
		this.name = name;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public void setStartDate(StartDate sdate) {
		this.startDate = sdate;
	}

	public void setEndDate(String edate) {
		this.endDate = edate;
	}

	public void setDone(String done) {
		this.done = done;
	}

	// @Override
	public String getPriority() {
		return priority;
	}

	@Override
	public Name getName() {
		return name;
	}

	public StartDate getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getDone() {
		return done;
	}

	public String getAddCommand() {
		StringBuilder sb = new StringBuilder();
		sb.append("add " + this.getName().name + " ");
		sb.append("p/ " + this.getPriority());
		return sb.toString();
	}

}
```
###### /java/seedu/Tdoo/testutil/TestUtil.java
``` java
	private static Event[] getSampleeventData() {
		try {
			return new Event[] {
					new Event(new Name("EE 11"), new StartDate("01-11-2016"), new EndDate("02-11-2016"),
							new StartTime("1000"), new EndTime("1030"), "false"),
					new Event(new Name("Essignment 12"), new StartDate("02-11-2016"), new EndDate("02-11-2016"),
							new StartTime("1000"), new EndTime("1030"), "false"),
					new Event(new Name("Essignment 13"), new StartDate("03-11-2016"), new EndDate("02-11-2016"),
							new StartTime("1100"), new EndTime("1130"), "false"),
					new Event(new Name("Essignment 14"), new StartDate("04-11-2016"), new EndDate("02-11-2016"),
							new StartTime("1200"), new EndTime("1230"), "false"),
					new Event(new Name("Essignment 15"), new StartDate("05-11-2016"), new EndDate("02-11-2016"),
							new StartTime("1300"), new EndTime("1330"), "false"),
					new Event(new Name("Essignment 16"), new StartDate("06-11-2016"), new EndDate("02-11-2016"),
							new StartTime("1400"), new EndTime("1430"), "false"),
					new Event(new Name("Essignment 17"), new StartDate("07-11-2016"), new EndDate("02-11-2016"),
							new StartTime("1500"), new EndTime("1530"), "false"), };
		} catch (IllegalValueException e) {
			assert false;
			// not possible
			return null;
		}
	}

```
###### /java/seedu/Tdoo/testutil/TestUtil.java
``` java
	private static Deadline[] getSampledeadlineData() {
		try {
			return new Deadline[] {
					new Deadline(new Name("DD 11"), new StartDate("01-11-2016"), new EndTime("1100"), "false"),
					new Deadline(new Name("Dssignment 12"), new StartDate("02-11-2016"), new EndTime("1200"), "false"),
					new Deadline(new Name("Dssignment 13"), new StartDate("03-11-2016"), new EndTime("1300"), "false"),
					new Deadline(new Name("Dssignment 14"), new StartDate("04-11-2016"), new EndTime("1400"), "false"),
					new Deadline(new Name("Dssignment 15"), new StartDate("05-11-2016"), new EndTime("1500"), "false"),
					new Deadline(new Name("Dssignment 16"), new StartDate("06-11-2016"), new EndTime("1600"), "false"),
					new Deadline(new Name("Dssignment 17"), new StartDate("07-11-2016"), new EndTime("1700"),
							"false"), };
		} catch (IllegalValueException e) {
			assert false;
			// not possible
			return null;
		}
	}

	public static List<Task> generateSampletaskData() {
		return Arrays.asList(sampletaskData);
	}

```
###### /java/seedu/Tdoo/testutil/TestUtil.java
``` java
	public static List<Event> generateSampleeventData() {
		return Arrays.asList(sampleeventData);
	}

```
###### /java/seedu/Tdoo/testutil/TestUtil.java
``` java
	public static List<Deadline> generateSampledeadlineData() {
		return Arrays.asList(sampledeadlineData);
	}

	/**
	 * Appends the file name to the sandbox folder path. Creates the sandbox
	 * folder if it doesn't exist.
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFilePathInSandboxFolder(String fileName) {
		try {
			FileUtil.createDirs(new File(SANDBOX_FOLDER));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return SANDBOX_FOLDER + fileName;
	}

	public static void createDataFileWithSampleData(String filePath) {
		createDataFileWithData(generateSampleStorageTodoList(), filePath);
	}

	public static <T> void createDataFileWithData(T data, String filePath) {
		try {
			File saveFileForTesting = new File(filePath);
			FileUtil.createIfMissing(saveFileForTesting);
			XmlUtil.saveDataToFile(saveFileForTesting, data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String... s) {
		createDataFileWithSampleData(TestApp.SAVE_LOCATION_FOR_TESTING);
	}

	public static TaskList generateEmptyTodoList() {
		return new TaskList(new UniqueTaskList());
	}

```
###### /java/seedu/Tdoo/testutil/TestUtil.java
``` java
	public static TaskList generateEmptyEventList() {
		return new TaskList(new UniqueTaskList());
	}

```
###### /java/seedu/Tdoo/testutil/TestUtil.java
``` java
	public static TaskList generateEmptyDeadlineList() {
		return new TaskList(new UniqueTaskList());
	}

	public static XmlSerializableTodoList generateSampleStorageTodoList() {
		return new XmlSerializableTodoList(generateEmptyTodoList());
	}

```
###### /java/seedu/Tdoo/testutil/TestUtil.java
``` java
	public static XmlSerializableEventList generateSampleStorageEventList() {
		return new XmlSerializableEventList(generateEmptyEventList());
	}

```
###### /java/seedu/Tdoo/testutil/TestUtil.java
``` java
	public static XmlSerializableDeadlineList generateSampleStorageDeadlineList() {
		return new XmlSerializableDeadlineList(generateEmptyDeadlineList());
	}

	/**
	 * Tweaks the {@code keyCodeCombination} to resolve the
	 * {@code KeyCode.SHORTCUT} to their respective platform-specific keycodes
	 */
	public static KeyCode[] scrub(KeyCodeCombination keyCodeCombination) {
		List<KeyCode> keys = new ArrayList<>();
		if (keyCodeCombination.getAlt() == KeyCombination.ModifierValue.DOWN) {
			keys.add(KeyCode.ALT);
		}
		if (keyCodeCombination.getShift() == KeyCombination.ModifierValue.DOWN) {
			keys.add(KeyCode.SHIFT);
		}
		if (keyCodeCombination.getMeta() == KeyCombination.ModifierValue.DOWN) {
			keys.add(KeyCode.META);
		}
		if (keyCodeCombination.getControl() == KeyCombination.ModifierValue.DOWN) {
			keys.add(KeyCode.CONTROL);
		}
		keys.add(keyCodeCombination.getCode());
		return keys.toArray(new KeyCode[] {});
	}

	public static boolean isHeadlessEnvironment() {
		String headlessProperty = System.getProperty("testfx.headless");
		return headlessProperty != null && headlessProperty.equals("true");
	}

	public static void captureScreenShot(String fileName) {
		File file = GuiTest.captureScreenshot();
		try {
			Files.copy(file, new File(fileName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String descOnFail(Object... comparedObjects) {
		return "Comparison failed \n"
				+ Arrays.asList(comparedObjects).stream().map(Object::toString).collect(Collectors.joining("\n"));
	}

	public static void setFinalStatic(Field field, Object newValue)
			throws NoSuchFieldException, IllegalAccessException {
		field.setAccessible(true);
		// remove final modifier from field
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		// ~Modifier.FINAL is used to remove the final modifier from field so
		// that its value is no longer
		// final and can be changed
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		field.set(null, newValue);
	}

	public static void initRuntime() throws TimeoutException {
		FxToolkit.registerPrimaryStage();
		FxToolkit.hideStage();
	}

	public static void tearDownRuntime() throws Exception {
		FxToolkit.cleanupStages();
	}

	/**
	 * Gets private method of a class Invoke the method using
	 * method.invoke(objectInstance, params...)
	 *
	 * Caveat: only find method declared in the current Class, not inherited
	 * from supertypes
	 */
	public static Method getPrivateMethod(Class objectClass, String methodName) throws NoSuchMethodException {
		Method method = objectClass.getDeclaredMethod(methodName);
		method.setAccessible(true);
		return method;
	}

	public static void renameFile(File file, String newFileName) {
		try {
			Files.copy(file, new File(newFileName));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Gets mid point of a node relative to the screen.
	 * 
	 * @param node
	 * @return
	 */
	public static Point2D getScreenMidPoint(Node node) {
		double x = getScreenPos(node).getMinX() + node.getLayoutBounds().getWidth() / 2;
		double y = getScreenPos(node).getMinY() + node.getLayoutBounds().getHeight() / 2;
		return new Point2D(x, y);
	}

	/**
	 * Gets mid point of a node relative to its scene.
	 * 
	 * @param node
	 * @return
	 */
	public static Point2D getSceneMidPoint(Node node) {
		double x = getScenePos(node).getMinX() + node.getLayoutBounds().getWidth() / 2;
		double y = getScenePos(node).getMinY() + node.getLayoutBounds().getHeight() / 2;
		return new Point2D(x, y);
	}

	/**
	 * Gets the bound of the node relative to the parent scene.
	 * 
	 * @param node
	 * @return
	 */
	public static Bounds getScenePos(Node node) {
		return node.localToScene(node.getBoundsInLocal());
	}

	public static Bounds getScreenPos(Node node) {
		return node.localToScreen(node.getBoundsInLocal());
	}

	public static double getSceneMaxX(Scene scene) {
		return scene.getX() + scene.getWidth();
	}

	public static double getSceneMaxY(Scene scene) {
		return scene.getX() + scene.getHeight();
	}

	public static Object getLastElement(List<?> list) {
		return list.get(list.size() - 1);
	}

	/**
	 * Removes a subset from the list of tasks.
	 * 
	 * @param tasks
	 *            The list of tasks
	 * @param tasksToRemove
	 *            The subset of tasks.
	 * @return The modified tasks after removal of the subset from tasks.
	 */
	public static TestTask[] removetasksFromList(final TestTask[] tasks, TestTask... tasksToRemove) {
		List<TestTask> listOftasks = asList(tasks);
		listOftasks.removeAll(asList(tasksToRemove));
		return listOftasks.toArray(new TestTask[listOftasks.size()]);
	}

	public static TestEvent[] removeEventsFromList(final TestEvent[] events, TestEvent... eventsToRemove) {
		List<TestEvent> listOfEvents = asList(events);
		listOfEvents.removeAll(asList(eventsToRemove));
		return listOfEvents.toArray(new TestEvent[listOfEvents.size()]);
	}

	public static TestDeadline[] removeDeadlinesFromList(final TestDeadline[] events,
			TestDeadline... deadlineToRemove) {
		List<TestDeadline> listOfdd = asList(events);
		listOfdd.removeAll(asList(deadlineToRemove));
		return listOfdd.toArray(new TestDeadline[listOfdd.size()]);
	}

	/**
	 * Returns a copy of the list with the task at specified index removed.
	 * 
	 * @param list
	 *            original list to copy from
	 * @param targetIndexInOneIndexedFormat
	 *            e.g. if the first element to be removed, 1 should be given as
	 *            index.
	 */
	public static TestTask[] removetaskFromList(final TestTask[] list, int targetIndexInOneIndexedFormat) {
		return removetasksFromList(list, list[targetIndexInOneIndexedFormat - 1]);
	}

	public static TestEvent[] removeEventFromList(final TestEvent[] list, int targetIndexInOneIndexedFormat) {
		return removeEventsFromList(list, list[targetIndexInOneIndexedFormat - 1]);
	}

	public static TestDeadline[] removeDeadlineFromList(final TestDeadline[] list, int targetIndexInOneIndexedFormat) {
		return removeDeadlinesFromList(list, list[targetIndexInOneIndexedFormat - 1]);
	}

	/**
	 * Replaces tasks[i] with a task.
	 * 
	 * @param tasks
	 *            The array of tasks.
	 * @param task
	 *            The replacement task
	 * @param index
	 *            The index of the task to be replaced.
	 * @return
	 */
	public static TestTask[] replacetaskFromList(TestTask[] tasks, TestTask task, int index) {
		tasks[index] = task;
		return tasks;
	}

	public static TestEvent[] replaceEventFromList(TestEvent[] events, TestEvent event, int index) {
		events[index] = event;
		return events;
	}

	public static TestDeadline[] replaceDeadlineFromList(TestDeadline[] dds, TestDeadline dd, int index) {
		dds[index] = dd;
		return dds;
	}

	/**
	 * Appends tasks to the array of tasks.
	 * 
	 * @param tasks
	 *            A array of tasks.
	 * @param tasksToAdd
	 *            The tasks that are to be appended behind the original array.
	 * @return The modified array of tasks.
	 */
	public static TestTask[] addTasksToList(final TestTask[] tasks, TestTask... tasksToAdd) {
		List<TestTask> listOftasks = asList(tasks);
		listOftasks.addAll(asList(tasksToAdd));
		return listOftasks.toArray(new TestTask[listOftasks.size()]);
	}

	public static TestEvent[] addEventsToList(final TestEvent[] events, TestEvent... eventsToAdd) {
		List<TestEvent> listOfEvents = asList(events);
		listOfEvents.addAll(asList(eventsToAdd));
		return listOfEvents.toArray(new TestEvent[listOfEvents.size()]);
	}

	public static TestDeadline[] addDeadlinesToList(final TestDeadline[] events, TestDeadline... eventsToAdd) {
		List<TestDeadline> listOfEvents = asList(events);
		listOfEvents.addAll(asList(eventsToAdd));
		return listOfEvents.toArray(new TestDeadline[listOfEvents.size()]);
	}

	private static <T> List<T> asList(T[] objs) {
		List<T> list = new ArrayList<>();
		for (T obj : objs) {
			list.add(obj);
		}
		return list;
	}

	public static boolean compareCardAndTask(TaskCardHandle card, ReadOnlyTask task) {
		return card.isSametask(task);
	}

	public static boolean compareCardAndEvent(EventCardHandle card, ReadOnlyTask event) {
		return card.isSameEvent(event); // something wrong. Always return false
	}

	public static boolean compareCardAndDeadline(DeadlineCardHandle card, ReadOnlyTask tasks) {
		return card.isSameDeadline(tasks); // something wrong. Always return
											// false
	}

}
```
###### /java/seedu/Tdoo/testutil/TypicalTestDeadline.java
``` java
public class TypicalTestDeadline {

	public static TestDeadline d1, d2, d3, d4, d5, d6, d7, d8;

	public TypicalTestDeadline() {
		/*
		 * try { d1 = new
		 * DeadlineBuilder().withName("d 1").withDate("30-10-2017").withEndTime(
		 * "1000").withDone("ND").build(); d2 = new
		 * DeadlineBuilder().withName("dd 1").withDate("26-10-2017").withEndTime
		 * ("1200").build(); d3 = new
		 * DeadlineBuilder().withName("deambuilding 3").withDate("27-10-2017").
		 * withEndTime("1300").build(); d4 = new
		 * DeadlineBuilder().withName("deambuilding 3").withDate("27-10-2017").
		 * withEndTime("1300").build(); d5 = new
		 * DeadlineBuilder().withName("droject 5").withDate("28-10-2017").
		 * withEndTime("1500").build(); //Manually added d6 = new
		 * DeadlineBuilder().withName("dssignment 6").withDate("28-10-2017").
		 * withEndTime("1600").build(); d7 = new
		 * DeadlineBuilder().withName("domework 7").withDate("29-10-2017").
		 * withEndTime("1700").build();
		 * 
		 * } catch (IllegalValueException e) { e.printStackTrace(); assert false
		 * : "not possible"; }
		 */
	}

	public static void loadDeadlineListWithSampleData(TaskList ab) {

		/*
		 * try { ab.addTask(new Deadline(d1)); ab.addTask(new Deadline(d2));
		 * ab.addTask(new Deadline(d3)); ab.addTask(new Deadline(d4));
		 * ab.addTask(new Deadline(d5)); ab.addTask(new Deadline(d6));
		 * ab.addTask(new Deadline(d7)); } catch
		 * (UniqueTaskList.DuplicatetaskException e) { assert false :
		 * "not possible"; }
		 */
	}

	public TestDeadline[] getTypicalDeadline() throws IllegalValueException {
		return new TestDeadline[] {};
		// new DeadlineBuilder().withName("deadlinegtt
		// 1").withStartDate("30-11-2017").withEndTime("10:00").withDone("false").build(),
		// new DeadlineBuilder().withName("deadlinegtt
		// 2").withStartDate("26-11-2017").withEndTime("12:00").withDone("false").build(),
		// new DeadlineBuilder().withName("deadlinegtt
		// 3").withStartDate("27-11-2017").withEndTime("13:00").withDone("false").build(),
		// new DeadlineBuilder().withName("deadlinegtt
		// 4").withStartDate("27-11-2017").withEndTime("13:00").withDone("false").build(),
		// new DeadlineBuilder().withName("deadlinegtt
		// 5").withStartDate("28-11-2017").withEndTime("15:00").withDone("false").build()};
	}

	public TaskList getTypicalDeadlineList() {
		TaskList ab = new TaskList();
		loadDeadlineListWithSampleData(ab);
		return ab;
	}

}
```
###### /java/seedu/Tdoo/testutil/TypicalTestEvent.java
``` java
public class TypicalTestEvent {

	public static TestEvent e1, e2, e3, e4, e5, e6, e7, e8;

	public TypicalTestEvent() {
		/*
		 * try { e1 = new
		 * EventBuilder().withName("e 1").withStartDate("30-10-2016").
		 * withEndDate("31-10-2016").withStartTime("0130").withEndTime("0200").
		 * withDone("done").build(); e2 = new
		 * EventBuilder().withName("e 2").withStartDate("30-10-2016").
		 * withEndDate("31-10-2016").withStartTime("0130").withEndTime("0200").
		 * withDone("done").build(); e3 = new
		 * EventBuilder().withName("Eeambuilding 3").withStartDate("30-10-2016")
		 * .withEndDate("31-10-2016").withStartTime("0130").withEndTime("0200").
		 * withDone("done").build(); e4 = new
		 * EventBuilder().withName("Essignment 4").withStartDate("30-10-2016").
		 * withEndDate("31-10-2016").withStartTime("0130").withEndTime("0200").
		 * withDone("done").build(); e5 = new
		 * EventBuilder().withName("Eroject 5").withStartDate("30-10-2016").
		 * withEndDate("31-10-2016").withStartTime("0130").withEndTime("0200").
		 * withDone("done").build(); //Manually added e6 = new
		 * EventBuilder().withName("Essignment 6").withStartDate("30-10-2016").
		 * withEndDate("31-10-2016").withStartTime("0130").withEndTime("0200").
		 * withDone("done").build(); e7 = new
		 * EventBuilder().withName("Eomework 7").withStartDate("30-10-2016").
		 * withEndDate("31-10-2016").withStartTime("0130").withEndTime("0200").
		 * withDone("done").build();
		 * 
		 * } catch (IllegalValueException e) { e.printStackTrace(); assert false
		 * : "not possible"; }
		 */
	}

	public static void loadEventListWithSampleData(TaskList ab) {

		/*
		 * try { ab.addTask(new Event(e1)); ab.addTask(new Event(e2));
		 * ab.addTask(new Event(e3)); ab.addTask(new Event(e4)); ab.addTask(new
		 * Event(e5)); ab.addTask(new Event(e6)); ab.addTask(new Event(e7)); }
		 * catch (UniqueTaskList.DuplicatetaskException e) { assert false :
		 * "not possible"; }
		 */
	}

	public TestEvent[] getTypicalEvent() throws IllegalValueException {
		return new TestEvent[] {};
		// new EventBuilder().withName("eventgtt
		// 1").withStartDate("30-10-2016").withEndDate("31-10-2016").withStartTime("01:30").withEndTime("02:00").withDone("false").build(),
		// new EventBuilder().withName("eventgtt
		// 2").withStartDate("01-11-2016").withEndDate("02-11-2016").withStartTime("01:30").withEndTime("02:00").withDone("false").build(),
		// new EventBuilder().withName("eventgtt
		// 3").withStartDate("02-11-2016").withEndDate("03-11-2016").withStartTime("01:30").withEndTime("02:00").withDone("false").build(),
		// new EventBuilder().withName("eventgtt
		// 4").withStartDate("03-12-2016").withEndDate("04-12-2016").withStartTime("01:30").withEndTime("02:00").withDone("false").build(),
		// new EventBuilder().withName("eventgtt
		// 5").withStartDate("05-12-2016").withEndDate("06-12-2016").withStartTime("01:30").withEndTime("02:00").withDone("false").build()};
	}

	public TaskList getTypicalEventList() {
		TaskList ab = new TaskList();
		loadEventListWithSampleData(ab);
		return ab;
	}
}
```
