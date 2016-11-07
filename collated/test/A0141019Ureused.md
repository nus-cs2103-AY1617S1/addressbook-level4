# A0141019Ureused
###### \java\guitests\DeleteCommandTest.java
``` java
    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to delete the first task in the list,
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
    	assertDeleteSuccess(new int[]{targetIndexOneIndexed}, currentList);
    }
    
    /**
     * Runs the delete command to delete the task at specified indices and confirms the result is correct.
     * @param targetIndicesOneIndexed e.g. indices {1, 3} to delete the first and third tasks in the list,
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDeleteSuccess(int[] targetIndicesOneIndexed, final TestTask[] currentList) {
        TestTask[] tasksToDelete = new TestTask[targetIndicesOneIndexed.length];
        StringBuilder sbIndices = new StringBuilder();
        StringBuilder sbTasks = new StringBuilder();
        
        for (int i=0; i<targetIndicesOneIndexed.length; i++) {
        	tasksToDelete[i] = currentList[targetIndicesOneIndexed[i] - 1];
        	
        	sbIndices.append(targetIndicesOneIndexed[i] + " ");
        	if (i == 0) {
        		sbTasks.append(tasksToDelete[i]);
        	}
        	else {
        		sbTasks.append(", " + tasksToDelete[i]);
        	}
        }
        
        TestTask[] expectedRemainder = TestUtil.removeTasksFromListByIndex(currentList, targetIndicesOneIndexed);
         System.out.println("sb indices: " + sbIndices.toString());
        
        commandBox.runCommand("del " + sbIndices.toString());

        //confirm the list now contains all previous tasks except the deleted tasks
        assertTrue(listPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, "[" + sbTasks.toString() + "]"));
    }

}
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
        /** Generates an add deadline command based on the task given */
        String generateAddDeadlineCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add '");
            cmd.append(p.getName().toString() + "'");
            
            cmd.append("by " + p.getEndDate().get().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            
            UniqueTagList tags = p.getTags();
            for(Tag t: tags){
                cmd.append(" #").append(t.tagName);
            }

            return cmd.toString();
        }

        /**
         * Generates an TaskManager with auto-generated tasks.
         */
        TaskManager generateTaskManager(int numGenerated) throws Exception{
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, numGenerated);
            return taskManager;
        }

        /**
         * Generates an TaskManager based on the list of Tasks given.
         */
        TaskManager generateTaskManager(List<Task> tasks) throws Exception{
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, tasks);
            return taskManager;
        }

        /**
         * Adds auto-generated Task objects to the given TaskManager
         * @param taskManager The TaskManager to which the Tasks will be added
         */
        void addToTaskManager(TaskManager taskManager, int numGenerated) throws Exception{
            addToTaskManager(taskManager, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given TaskManager
         */
        void addToTaskManager(TaskManager taskManager, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                taskManager.addTask(p);
            }
        }
        
        void addToTaskManager(TaskManager taskManager, Task toBeAdded) throws Exception {
        	taskManager.addTask(toBeAdded);
		}

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Tasks will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception{
            addToModel(model, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        void addToModel(Model model, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                model.addTask(p);
            }
        }

        /**
         * Generates a list of Tasks based on the flags.
         */
        List<Task> generateTaskList(int numGenerated) throws Exception{
            List<Task> tasks = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                tasks.add(generateTask(i));
            }
            return tasks;
        }

        List<Task> generateTaskList(Task... tasks) {
            return Arrays.asList(tasks);
        }

        /**
         * Generates a Task object with given name. Other fields will have some dummy values.
         */
        Task generateTaskWithName(String name) throws Exception {
            return new Task(
                    new Name(name),
                    new TaskType("someday"),
                    new Status("pending"),
                    Optional.empty(),
                    Optional.empty(),
                    new UniqueTagList(new Tag("tag"))
            );
        }
    }
}
```
