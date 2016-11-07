# A0147986Hunused
###### \java\seedu\unburden\logic\LogicManagerTest.java
``` java
		/**
		 * test the select command
		 * 
		 */
		@Test
		public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
			String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
			assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
		}

		@Test
		public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
			assertIndexNotFoundBehaviorForCommand("select");
		}
		
		@Test
		public void execute_select_jumpsToCorrectPerson() throws Exception {
			TestDataHelper helper = new TestDataHelper();
			List<Task> threeTasks = helper.generateTaskList(3);

			ListOfTask expectedAB = helper.generateListOfTask(threeTasks);
			helper.addToModel(model, threeTasks);

			assertCommandBehavior("select 2", String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, threeTasks.get(1)), expectedAB,
					expectedAB.getTaskList());
			assertEquals(1, targetedJumpIndex);
			assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
		}
				
		@Test
		public void execute_deleteInvalidArgsFormat_errorMessageShown_Original() throws Exception {
			String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
			assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
		}

		@Test
		public void execute_deleteIndexNotFound_errorMessageShown_Original() throws Exception {
			assertIndexNotFoundBehaviorForCommand("delete");
		}

		@Test
		public void execute_delete_removesCorrectPerson() throws Exception {
			TestDataHelper helper = new TestDataHelper();
			List<Task> threeTasks = helper.generateTaskList(3);

			ListOfTask expectedAB = helper.generateListOfTask(threeTasks);
			expectedAB.removeTask(threeTasks.get(1));
			helper.addToModel(model, threeTasks);

			assertCommandBehavior("delete 2", String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
					expectedAB, expectedAB.getTaskList());
		}

		
```
###### \java\seedu\unburden\logic\LogicManagerTest.java
``` java
		/**test the multiple delete command. 
		 * test both reverse indexes and any kind 
		 * if format 
		 * @throws Exception
		 *  This is unused because I did not discuss with my teammates in advanced so they decided 
         * not to include this method
		 */
		@Test
		public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
			String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnwantedDeleteCommand.MESSAGE_USAGE);
			assertIncorrectIndexFormatBehaviorForCommand("multipledelete", expectedMessage);
		}
		
		@Test
		public void execute_deleteInvalidArgsFormat_errorMessageShownZero() throws Exception {
			String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnwantedDeleteCommand.MESSAGE_USAGE);
			assertIncorrectIndexFormatBehaviorForCommand("multipledelete 0", expectedMessage);
		}

		@Test
		public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
			assertIndexNotFoundBehaviorForCommand("multipledelete");
		}	

		@Test
		public void execute_delete_removesCorrectTask() throws Exception {
			TestDataHelper helper = new TestDataHelper();
			List<Task> threeTasks = helper.generateTaskList(3);

			ListOfTask expectedAB = helper.generateListOfTask(threeTasks);
			expectedAB.removeTask(threeTasks.get(1));
			ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
			deletedTasks.add(threeTasks.get(1));
			helper.addToModel(model, threeTasks);

			assertCommandBehavior("multipledelete 2", String.format(UnwantedDeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
					expectedAB, expectedAB.getTaskList());
		}
		
		@Test
		public void execute_delete_removesCorrectTaskWithDuplicate() throws Exception {
			TestDataHelper helper = new TestDataHelper();
			List<Task> threeTasks = helper.generateTaskList(3);

			ListOfTask expectedAB = helper.generateListOfTask(threeTasks);
			expectedAB.removeTask(threeTasks.get(1));
			ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
			deletedTasks.add(threeTasks.get(1));
			helper.addToModel(model, threeTasks);

			assertCommandBehavior("multipledelete 2 2 2 2 2 2", String.format(UnwantedDeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
					expectedAB, expectedAB.getTaskList());
		}
		
		@Test
		public void execute_delete_removesCorrectTaskWithDuplicate2() throws Exception {
			TestDataHelper helper = new TestDataHelper();
			List<Task> threeTasks = helper.generateTaskList(3);

			ListOfTask expectedAB = helper.generateListOfTask(threeTasks);
			expectedAB.removeTask(threeTasks.get(1));
			ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
			deletedTasks.add(threeTasks.get(1));
			helper.addToModel(model, threeTasks);

			assertCommandBehavior("multipledelete 2-2", String.format(UnwantedDeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
					expectedAB, expectedAB.getTaskList());
		}
		
		@Test
		public void execute_delete_removesMultipleTasks() throws Exception{
			TestDataHelper helper=new TestDataHelper();
			List<Task> threeTasks=helper.generateTaskList(3);
			ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
			
			ListOfTask expectedAB=helper.generateListOfTask(threeTasks);
			expectedAB.removeTask(threeTasks.get(0));
			expectedAB.removeTask(threeTasks.get(1));
			deletedTasks.add(threeTasks.get(0));
			deletedTasks.add(threeTasks.get(1));
			helper.addToModel(model, threeTasks);
			
			assertCommandBehavior("multipledelete 1 2 ",String.format(UnwantedDeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
					expectedAB,expectedAB.getTaskList());
		}
		
		@Test
		public void execute_delete_removesMultipleTasksWithZero() throws Exception{
			TestDataHelper helper=new TestDataHelper();
			List<Task> threeTasks=helper.generateTaskList(3);
			ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
			
			ListOfTask expectedAB=helper.generateListOfTask(threeTasks);
			expectedAB.removeTask(threeTasks.get(0));
			expectedAB.removeTask(threeTasks.get(1));
			deletedTasks.add(threeTasks.get(0));
			deletedTasks.add(threeTasks.get(1));
			helper.addToModel(model, threeTasks);
			
			assertCommandBehavior("multipledelete 01 02 ",String.format(UnwantedDeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
					expectedAB,expectedAB.getTaskList());
		}
		
		@Test
		public void execute_delete_removesMultipleTasksWithDuplicate() throws Exception{
			TestDataHelper helper=new TestDataHelper();
			List<Task> threeTasks=helper.generateTaskList(3);
			ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
			
			ListOfTask expectedAB=helper.generateListOfTask(threeTasks);
			expectedAB.removeTask(threeTasks.get(0));
			expectedAB.removeTask(threeTasks.get(1));
			deletedTasks.add(threeTasks.get(0));
			deletedTasks.add(threeTasks.get(1));
			helper.addToModel(model, threeTasks);
			
			assertCommandBehavior("multipledelete 1 2 1",String.format(UnwantedDeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
					expectedAB,expectedAB.getTaskList());
		}
		
		@Test
		public void execute_delete_removesMultipleTasksReverse() throws Exception{
			TestDataHelper helper=new TestDataHelper();
			List<Task> threeTasks=helper.generateTaskList(3);
			ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
			
			ListOfTask expectedAB=helper.generateListOfTask(threeTasks);
			expectedAB.removeTask(threeTasks.get(0));
			expectedAB.removeTask(threeTasks.get(1));
			deletedTasks.add(threeTasks.get(0));
			deletedTasks.add(threeTasks.get(1));
			helper.addToModel(model, threeTasks);
			
			assertCommandBehavior("multipledelete 2 1 ",String.format(UnwantedDeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
					expectedAB,expectedAB.getTaskList());
		}
		
		@Test
		public void execute_delete_removesMultipleTasksReverseWithZero() throws Exception{
			TestDataHelper helper=new TestDataHelper();
			List<Task> threeTasks=helper.generateTaskList(3);
			ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
			
			ListOfTask expectedAB=helper.generateListOfTask(threeTasks);
			expectedAB.removeTask(threeTasks.get(0));
			expectedAB.removeTask(threeTasks.get(1));
			deletedTasks.add(threeTasks.get(0));
			deletedTasks.add(threeTasks.get(1));
			helper.addToModel(model, threeTasks);
			
			assertCommandBehavior("multipledelete 02 01 ",String.format(UnwantedDeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
					expectedAB,expectedAB.getTaskList());
		}
		
		
		@Test
		public void execute_delete_removesMultipleTasksWithDash() throws Exception{
			TestDataHelper helper=new TestDataHelper();
			List<Task> threeTasks=helper.generateTaskList(3);
			ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
			
			ListOfTask expectedAB=helper.generateListOfTask(threeTasks);
			expectedAB.removeTask(threeTasks.get(0));
			expectedAB.removeTask(threeTasks.get(1));
			deletedTasks.add(threeTasks.get(0));
			deletedTasks.add(threeTasks.get(1));
			helper.addToModel(model, threeTasks);
			
			assertCommandBehavior("multipledelete 1-2 ",String.format(UnwantedDeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
					expectedAB,expectedAB.getTaskList());
		}

		@Test
		public void execute_delete_removesMultipleTasksWithDashReverse() throws Exception{
			TestDataHelper helper=new TestDataHelper();
			List<Task> threeTasks=helper.generateTaskList(3);
			ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
			
			ListOfTask expectedAB=helper.generateListOfTask(threeTasks);
			expectedAB.removeTask(threeTasks.get(0));
			expectedAB.removeTask(threeTasks.get(1));
			deletedTasks.add(threeTasks.get(0));
			deletedTasks.add(threeTasks.get(1));
			helper.addToModel(model, threeTasks);
			
			assertCommandBehavior("multipledelete 2-1 ",String.format(UnwantedDeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
					expectedAB,expectedAB.getTaskList());
		}

		@Test
		public void execute_delete_removesMultipleTasksWithDashReverseWithZero() throws Exception{
			TestDataHelper helper=new TestDataHelper();
			List<Task> threeTasks=helper.generateTaskList(3);
			ArrayList<ReadOnlyTask> deletedTasks=new ArrayList<ReadOnlyTask>();
			
			ListOfTask expectedAB=helper.generateListOfTask(threeTasks);
			expectedAB.removeTask(threeTasks.get(0));
			expectedAB.removeTask(threeTasks.get(1));
			deletedTasks.add(threeTasks.get(0));
			deletedTasks.add(threeTasks.get(1));
			helper.addToModel(model, threeTasks);
			
			assertCommandBehavior("multipledelete 02-01 ",String.format(UnwantedDeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, StringUtil.getTaskDetails(deletedTasks)),
					expectedAB,expectedAB.getTaskList());
		}
				
```
