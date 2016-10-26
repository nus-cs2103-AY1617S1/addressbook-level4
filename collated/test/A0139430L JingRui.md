# A0139430L JingRui
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_add_Event_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.EVENT_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getEventList(),
                expectedAB.getDeadlineList(),
                expectedAB.getTodoList());

    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_add_Deadline_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.beta();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        
        assertCommandBehavior(helper.generateAddDeadlineCommand(toBeAdded),
                String.format(AddCommand.DEADLINE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getEventList(),
                expectedAB.getDeadlineList(),
                expectedAB.getTodoList());
        
    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_add_Todo_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.charlie();
        TaskBook expectedAB = helper.generateAddressBook(1, 1, 1);
        helper.addToModel(model, 1, 1, 1);
        expectedAB.addTask(toBeAdded);
        
        assertCommandBehavior(helper.generateAddTodoCommand(toBeAdded),
                String.format(AddCommand.TODO_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getEventList(),
                expectedAB.getDeadlineList(),
                expectedAB.getTodoList());

    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_addDuplicateEvent_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // person already in internal address book

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_TASK,
                expectedAB,
                expectedAB.getEventList(),
                Collections.emptyList(),
                Collections.emptyList());
    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_addDuplicateDeadline_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.beta();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // person already in internal address book

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddDeadlineCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_TASK,
                expectedAB,
                Collections.emptyList(),
                expectedAB.getDeadlineList(),
                Collections.emptyList());
    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_addDuplicateTodo_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.charlie();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // person already in internal address book

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddTodoCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_TASK,
                expectedAB,
                Collections.emptyList(),
                Collections.emptyList(),
                expectedAB.getTodoList());
    }


```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_list_showsAllPersons() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskBook expectedAB = helper.generateAddressBook(2, 2, 2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getEventList();
        List<? extends ReadOnlyTask> expectedDeadlineList = expectedAB.getDeadlineList();
        List<? extends ReadOnlyTask> expectedTodoList = expectedAB.getTodoList();
        // prepare address book state
        helper.addToModel(model, 2, 2, 2);

        assertCommandBehavior("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList,
                expectedDeadlineList,
                expectedTodoList);
    }
    
    //@Test error
    public void execute_one_undo() throws Exception {
    	TestDataHelper helper = new TestDataHelper();
    	TaskBook expectedAB = helper.generateAddressBook(1, 0, 1);
    	Task toBeAdded = helper.generateDeadline(1);
    	helper.addToModel(model, 1, 0, 1);
    	expectedAB.addTask(toBeAdded);
    	
    	assertCommandBehavior("undo 1", UndoCommand.MESSAGE_UNDO_TASK_SUCCESS,
    			expectedAB,
    			expectedAB.getEventList(),
    			expectedAB.getDeadlineList(),
    			expectedAB.getTodoList());
    }

```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
    }

    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete E5");
        assertIndexNotFoundBehaviorForCommand("delete D5");
        assertIndexNotFoundBehaviorForCommand("delete T5");
    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_delete_removesCorrectEvent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threePersons = helper.generatePersonList(3);
        List<Task> threeDeadlines = helper.generateDeadlineList(3);
        List<Task> threeTodos = helper.generateTodoList(3);
        
        TaskBook expectedAB = helper.generateAddressBook(threePersons, threeDeadlines, threeTodos);
        expectedAB.removeTask(threePersons.get(1));
        
        helper.addToModel(model, threePersons, threeDeadlines, threeTodos);

        assertCommandBehavior("delete E2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, /*threePersons.get(1)*/ new String("[E2]")),
                expectedAB,
                expectedAB.getEventList(),
                expectedAB.getDeadlineList(),
                expectedAB.getTodoList());
    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_delete_removesCorrectDeadline() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threePersons = helper.generatePersonList(3);
        List<Task> threeDeadlines = helper.generateDeadlineList(3);
        List<Task> threeTodos = helper.generateTodoList(3);
        
        TaskBook expectedAB = helper.generateAddressBook(threePersons, threeDeadlines, threeTodos);
        expectedAB.removeTask(threeDeadlines.get(1));
        
        helper.addToModel(model, threePersons, threeDeadlines, threeTodos);

        assertCommandBehavior("delete D2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, /*threePersons.get(1)*/ new String("[D2]")),
                expectedAB,
                expectedAB.getEventList(),
                expectedAB.getDeadlineList(),
                expectedAB.getTodoList());
    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_delete_removesCorrectTodo() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threePersons = helper.generatePersonList(3);
        List<Task> threeDeadlines = helper.generateDeadlineList(3);
        List<Task> threeTodos = helper.generateTodoList(3);
        
        TaskBook expectedAB = helper.generateAddressBook(threePersons, threeDeadlines, threeTodos);
        expectedAB.removeTask(threeTodos.get(1));
        
        helper.addToModel(model, threePersons, threeDeadlines, threeTodos);

        assertCommandBehavior("delete T2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, /*threePersons.get(1)*/ new String("[T2]")),
                expectedAB,
                expectedAB.getEventList(),
                expectedAB.getDeadlineList(),
                expectedAB.getTodoList());
    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_delete_removesCorrectPersonsEventMultiple() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threePersons = helper.generatePersonList(3);
        List<Task> threeDeadlines = helper.generateDeadlineList(3);
        List<Task> threeTodos = helper.generateTodoList(3);
        
        TaskBook expectedAB = helper.generateAddressBook(threePersons, threeDeadlines, threeTodos);
        expectedAB.removeTask(threePersons.get(0));
        expectedAB.removeTask(threePersons.get(1));
        expectedAB.removeTask(threePersons.get(2));
        helper.addToModel(model, threePersons, threeDeadlines, threeTodos);

        assertCommandBehavior("delete E1, E2, E3",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, /*threePersons.get(1)*/ new String("[E1, E2, E3]")),
                expectedAB,
                expectedAB.getEventList(),
                expectedAB.getDeadlineList(),
                expectedAB.getTodoList());
    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_delete_removesCorrectEventRange() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threePersons = helper.generatePersonList(3);
        List<Task> threeDeadlines = helper.generateDeadlineList(3);
        List<Task> threeTodos = helper.generateTodoList(3);
        
        TaskBook expectedAB = helper.generateAddressBook(threePersons, threeDeadlines, threeTodos);
        expectedAB.removeTask(threePersons.get(0));
        expectedAB.removeTask(threePersons.get(1));
        expectedAB.removeTask(threePersons.get(2));
        helper.addToModel(model, threePersons, threeDeadlines, threeTodos);

        assertCommandBehavior("delete E1-E3",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, /*threePersons.get(1)*/ new String("[E1, E2, E3]")),
                expectedAB,
                expectedAB.getEventList(),
                expectedAB.getDeadlineList(),
                expectedAB.getTodoList());
    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_delete_removesCorrectDeadlineRange() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threePersons = helper.generatePersonList(3);
        List<Task> threeDeadlines = helper.generateDeadlineList(3);
        List<Task> threeTodos = helper.generateTodoList(3);
        
        TaskBook expectedAB = helper.generateAddressBook(threePersons, threeDeadlines, threeTodos);
        expectedAB.removeTask(threeDeadlines.get(0));
        expectedAB.removeTask(threeDeadlines.get(1));
        expectedAB.removeTask(threeDeadlines.get(2));
        helper.addToModel(model, threePersons, threeDeadlines, threeTodos);

        assertCommandBehavior("delete D1-D3",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, /*threePersons.get(1)*/ new String("[D1, D2, D3]")),
                expectedAB,
                expectedAB.getEventList(),
                expectedAB.getDeadlineList(),
                expectedAB.getTodoList());
    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_delete_removesCorrectDeadlineMultiple() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threePersons = helper.generatePersonList(3);
        List<Task> threeDeadlines = helper.generateDeadlineList(3);
        List<Task> threeTodos = helper.generateTodoList(3);
        
        TaskBook expectedAB = helper.generateAddressBook(threePersons, threeDeadlines, threeTodos);
        expectedAB.removeTask(threeDeadlines.get(0));
        expectedAB.removeTask(threeDeadlines.get(1));
        expectedAB.removeTask(threeDeadlines.get(2));
        helper.addToModel(model, threePersons, threeDeadlines, threeTodos);

        assertCommandBehavior("delete D1, D2, D3",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, /*threePersons.get(1)*/ new String("[D1, D2, D3]")),
                expectedAB,
                expectedAB.getEventList(),
                expectedAB.getDeadlineList(),
                expectedAB.getTodoList());
    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_delete_removesCorrectTodoRange() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threePersons = helper.generatePersonList(3);
        List<Task> threeDeadlines = helper.generateDeadlineList(3);
        List<Task> threeTodos = helper.generateTodoList(3);
        
        TaskBook expectedAB = helper.generateAddressBook(threePersons, threeDeadlines, threeTodos);
        expectedAB.removeTask(threeTodos.get(0));
        expectedAB.removeTask(threeTodos.get(1));
        expectedAB.removeTask(threeTodos.get(2));
        helper.addToModel(model, threePersons, threeDeadlines, threeTodos);

        assertCommandBehavior("delete T1-T3",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, /*threePersons.get(1)*/ new String("[T1, T2, T3]")),
                expectedAB,
                expectedAB.getEventList(),
                expectedAB.getDeadlineList(),
                expectedAB.getTodoList());
    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_delete_removesCorrectTodoMultiple() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threePersons = helper.generatePersonList(3);
        List<Task> threeDeadlines = helper.generateDeadlineList(3);
        List<Task> threeTodos = helper.generateTodoList(3);
        
        TaskBook expectedAB = helper.generateAddressBook(threePersons, threeDeadlines, threeTodos);
        expectedAB.removeTask(threeTodos.get(0));
        expectedAB.removeTask(threeTodos.get(1));
        expectedAB.removeTask(threeTodos.get(2));
        helper.addToModel(model, threePersons, threeDeadlines, threeTodos);

        assertCommandBehavior("delete T1, T2, T3",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, /*threePersons.get(1)*/ new String("[T1, T2, T3]")),
                expectedAB,
                expectedAB.getEventList(),
                expectedAB.getDeadlineList(),
                expectedAB.getTodoList());
    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_editInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("edit", expectedMessage);
    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_editIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("edit E5 des sadas");
        assertIndexNotFoundBehaviorForCommand("edit E5 date 121222");
        assertIndexNotFoundBehaviorForCommand("edit E5 start 1234");
        assertIndexNotFoundBehaviorForCommand("edit E5 end 2359");
        assertIndexNotFoundBehaviorForCommand("edit E5 tag love");
        assertIndexNotFoundBehaviorForCommand("edit E5 tag love>this");
        
        assertIndexNotFoundBehaviorForCommand("edit D5 des sadas");
        assertIndexNotFoundBehaviorForCommand("edit D5 date 121222");
        assertIndexNotFoundBehaviorForCommand("edit D5 start 1234");
        assertIndexNotFoundBehaviorForCommand("edit D5 end 2359");
        assertIndexNotFoundBehaviorForCommand("edit D5 tag love");
        assertIndexNotFoundBehaviorForCommand("edit D5 tag love>this");
        
        assertIndexNotFoundBehaviorForCommand("edit T5 des sadas");
        assertIndexNotFoundBehaviorForCommand("edit T5 date 121222");
        assertIndexNotFoundBehaviorForCommand("edit T5 start 1234");
        assertIndexNotFoundBehaviorForCommand("edit T5 end 2359");
        assertIndexNotFoundBehaviorForCommand("edit T5 tag love");
        assertIndexNotFoundBehaviorForCommand("edit T5 tag love>this");
    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_edit_editCorrectEventName() throws Exception {      
        TestDataHelper helper = new TestDataHelper();
        List<Task> threePersons = helper.generatePersonList(3);
        List<Task> threeDeadlines = helper.generateDeadlineList(3);
        List<Task> threeTodos = helper.generateTodoList(3);
        
        TaskBook expectedAB = helper.generateAddressBook(threePersons, threeDeadlines, threeTodos);
        helper.addToModel(model, threePersons, threeDeadlines, threeTodos);
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownEventList = model.getFilteredEventList();
        ReadOnlyTask eventToEdit = lastShownEventList.get(1-1);
        expectedAB.changeTask(eventToEdit, "des BEACH parTy" , 'E');

        assertCommandBehavior("edit E1 des BEACH parTy",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, "E", "1", "des BEACH parTy"),
                expectedAB,
                expectedAB.getEventList(),
                expectedAB.getDeadlineList(),
                expectedAB.getTodoList());
    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_edit_editCorrectEventDate() throws Exception {      
        TestDataHelper helper = new TestDataHelper();
        List<Task> threePersons = helper.generatePersonList(3);
        List<Task> threeDeadlines = helper.generateDeadlineList(3);
        List<Task> threeTodos = helper.generateTodoList(3);
        
        TaskBook expectedAB = helper.generateAddressBook(threePersons, threeDeadlines, threeTodos);
        helper.addToModel(model, threePersons, threeDeadlines, threeTodos);
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownEventList = model.getFilteredEventList();
        ReadOnlyTask eventToEdit = lastShownEventList.get(1-1);
        expectedAB.changeTask(eventToEdit, "date 12-12-17" , 'E');

        assertCommandBehavior("edit E1 date 12-12-17",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, "E", "1", "date 12-12-17"),
                expectedAB,
                expectedAB.getEventList(),
                expectedAB.getDeadlineList(),
                expectedAB.getTodoList());
    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_edit_editCorrectEventStart() throws Exception {      
        TestDataHelper helper = new TestDataHelper();
        List<Task> threePersons = helper.generatePersonList(3);
        List<Task> threeDeadlines = helper.generateDeadlineList(3);
        List<Task> threeTodos = helper.generateTodoList(3);
        
        TaskBook expectedAB = helper.generateAddressBook(threePersons, threeDeadlines, threeTodos);
        helper.addToModel(model, threePersons, threeDeadlines, threeTodos);
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownEventList = model.getFilteredEventList();
        ReadOnlyTask eventToEdit = lastShownEventList.get(1-1);
        expectedAB.changeTask(eventToEdit, "start 5.00pm" , 'E');

        assertCommandBehavior("edit E1 start 5.00pm",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, "E", "1", "start 5.00pm"),
                expectedAB,
                expectedAB.getEventList(),
                expectedAB.getDeadlineList(),
                expectedAB.getTodoList());
    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_edit_editCorrectEventEnd() throws Exception {      
        TestDataHelper helper = new TestDataHelper();
        List<Task> threePersons = helper.generatePersonList(3);
        List<Task> threeDeadlines = helper.generateDeadlineList(3);
        List<Task> threeTodos = helper.generateTodoList(3);
        
        TaskBook expectedAB = helper.generateAddressBook(threePersons, threeDeadlines, threeTodos);
        helper.addToModel(model, threePersons, threeDeadlines, threeTodos);
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownEventList = model.getFilteredEventList();
        ReadOnlyTask eventToEdit = lastShownEventList.get(1-1);
        expectedAB.changeTask(eventToEdit, "end 5.00pm" , 'E');

        assertCommandBehavior("edit E1 end 5.00pm",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, "E", "1", "end 5.00pm"),
                expectedAB,
                expectedAB.getEventList(),
                expectedAB.getDeadlineList(),
                expectedAB.getTodoList());
    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_edit_editCorrectEventSpecificTag() throws Exception {      
        TestDataHelper helper = new TestDataHelper();
        List<Task> threePersons = helper.generatePersonList(3);
        List<Task> threeDeadlines = helper.generateDeadlineList(3);
        List<Task> threeTodos = helper.generateTodoList(3);
        
        TaskBook expectedAB = helper.generateAddressBook(threePersons, threeDeadlines, threeTodos);
        helper.addToModel(model, threePersons, threeDeadlines, threeTodos);
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownEventList = model.getFilteredEventList();
        ReadOnlyTask eventToEdit = lastShownEventList.get(1-1);
        expectedAB.changeTask(eventToEdit, "tag tag1>anything" , 'E');

        assertCommandBehavior("edit E1 tag tag1>anything",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, "E", "1", "tag tag1>anything"),
                expectedAB,
                expectedAB.getEventList(),
                expectedAB.getDeadlineList(),
                expectedAB.getTodoList());
    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_edit_editCorrectEventTag() throws Exception {      
        TestDataHelper helper = new TestDataHelper();
        List<Task> threePersons = helper.generatePersonList(3);
        List<Task> threeDeadlines = helper.generateDeadlineList(3);
        List<Task> threeTodos = helper.generateTodoList(3);
        
        TaskBook expectedAB = helper.generateAddressBook(threePersons, threeDeadlines, threeTodos);
        helper.addToModel(model, threePersons, threeDeadlines, threeTodos);
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownEventList = model.getFilteredEventList();
        ReadOnlyTask eventToEdit = lastShownEventList.get(1-1);
        expectedAB.changeTask(eventToEdit, "tag anything" , 'E');

        assertCommandBehavior("edit E1 tag anything",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, "E", "1", "tag anything"),
                expectedAB,
                expectedAB.getEventList(),
                expectedAB.getDeadlineList(),
                expectedAB.getTodoList());
    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_edit_addTagSingle() throws Exception {      
        TestDataHelper helper = new TestDataHelper();
        List<Task> threePersons = helper.generatePersonList(3);
        List<Task> threeDeadlines = helper.generateDeadlineList(3);
        List<Task> threeTodos = helper.generateTodoList(3);
        
        TaskBook expectedAB = helper.generateAddressBook(threePersons, threeDeadlines, threeTodos);
        helper.addToModel(model, threePersons, threeDeadlines, threeTodos);
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownEventList = model.getFilteredEventList();
        ReadOnlyTask eventToEdit = lastShownEventList.get(1-1);
        expectedAB.changeTask(eventToEdit, "add anything" , 'E');

        assertCommandBehavior("add E1 #anything",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, "E", "1", "add #anything"),
                expectedAB,
                expectedAB.getEventList(),
                expectedAB.getDeadlineList(),
                expectedAB.getTodoList());
    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_edit_addTagMultiple() throws Exception {      
        TestDataHelper helper = new TestDataHelper();
        List<Task> threePersons = helper.generatePersonList(3);
        List<Task> threeDeadlines = helper.generateDeadlineList(3);
        List<Task> threeTodos = helper.generateTodoList(3);
        
        TaskBook expectedAB = helper.generateAddressBook(threePersons, threeDeadlines, threeTodos);
        helper.addToModel(model, threePersons, threeDeadlines, threeTodos);
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownEventList = model.getFilteredEventList();
        ReadOnlyTask eventToEdit = lastShownEventList.get(1-1);
        expectedAB.changeTask(eventToEdit, "add anything something" , 'E');

        assertCommandBehavior("add E1 #anything #something",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, "E", "1", "add #anything #something"),
                expectedAB,
                expectedAB.getEventList(),
                expectedAB.getDeadlineList(),
                expectedAB.getTodoList());
    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_edit_editCorrectDeadlineName() throws Exception {      
        TestDataHelper helper = new TestDataHelper();
        List<Task> threePersons = helper.generatePersonList(3);
        List<Task> threeDeadlines = helper.generateDeadlineList(3);
        List<Task> threeTodos = helper.generateTodoList(3);
        
        TaskBook expectedAB = helper.generateAddressBook(threePersons, threeDeadlines, threeTodos);
        helper.addToModel(model, threePersons, threeDeadlines, threeTodos);
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownEventList = model.getFilteredDeadlineList();
        ReadOnlyTask eventToEdit = lastShownEventList.get(1-1);
        expectedAB.changeTask(eventToEdit, "des BEACH parTy" , 'D');

        assertCommandBehavior("edit D1 des BEACH parTy",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, "D", "1", "des BEACH parTy"),
                expectedAB,
                expectedAB.getEventList(),
                expectedAB.getDeadlineList(),
                expectedAB.getTodoList());
    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_edit_editCorrectTodoName() throws Exception {      
        TestDataHelper helper = new TestDataHelper();
        List<Task> threePersons = helper.generatePersonList(3);
        List<Task> threeDeadlines = helper.generateDeadlineList(3);
        List<Task> threeTodos = helper.generateTodoList(3);
        
        TaskBook expectedAB = helper.generateAddressBook(threePersons, threeDeadlines, threeTodos);
        helper.addToModel(model, threePersons, threeDeadlines, threeTodos);
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownEventList = model.getFilteredTodoList();
        ReadOnlyTask eventToEdit = lastShownEventList.get(1-1);
        expectedAB.changeTask(eventToEdit, "des BEACH parTy" , 'T');

        assertCommandBehavior("edit T1 des BEACH parTy",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, "T", "1", "des BEACH parTy"),
                expectedAB,
                expectedAB.getEventList(),
                expectedAB.getDeadlineList(),
                expectedAB.getTodoList());
    }


    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }
```
