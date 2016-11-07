# A0153467Yunused
###### \java\guitests\AddCommandTest.java
``` java
    @Ignore @Test
    public void add_recurring_task() {
        TestTask[] currentList = td.getTypicalTasks();
        
        //recur a task zero times (i.e. no recurrence at all)
        TestTask taskToAdd = TypicalTestTasks.recur;
        currentList = assertAddRecurringSuccess(0, taskToAdd, currentList);
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd));
        
        //recur a task twenty times (maximum amount)
        taskToAdd = TypicalTestTasks.recur2;
        currentList = assertAddRecurringSuccess(20, taskToAdd, currentList);
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd));
        
        //recurring task number exceeds the maximum
        commandBox.runCommand("add testRecurring recurs 21");
        assertResultMessage(AddCommand.MESSAGE_WRONG_NUMBER_OF_RECURRENCE);
        
        // recurring number of task is negative
        commandBox.runCommand("add testRecurring recurs -1");
        assertResultMessage(AddCommand.MESSAGE_NEGATIVE_NUMBER_OF_RECURRENCE);
        
        //invalid recurring argument with alphanumeric is not allowed
        commandBox.runCommand("add testRecurring recurs abc");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        
        //missing recurring argument 
        commandBox.runCommand("add testRecurring recurs ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
    
```
