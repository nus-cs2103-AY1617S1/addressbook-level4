package seedu.task.logic;


public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */


    //Unmorphed parts
    //TODO: move to indiviual test classes
//    @Ignore
//    @Test
//    public void execute_exit() throws Exception {
//        assertCommandBehavior("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT);
//    }
//
//    @Ignore
//    @Test
//    public void execute_clear() throws Exception {
//        TestDataHelper helper = new TestDataHelper();
//        model.addTask(helper.generateTask(1));
//        model.addTask(helper.generateTask(2));
//        model.addTask(helper.generateTask(3));
//
//        assertTaskCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new TaskBook(), Collections.emptyList());
//    }

//    
//    @Ignore
//    @Test
//    public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
//        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
//        assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
//    }
//    @Ignore
//    @Test
//    public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
//        assertTaskIndexNotFoundBehaviorForCommand("select");
//    }
//
//    @Ignore
//    @Test
//    public void execute_select_jumpsToCorrectTask() throws Exception {
//        TestDataHelper helper = new TestDataHelper();
//        List<Task> threeTasks = helper.generateTaskList(3);
//
//        TaskBook expectedAB = helper.generateTaskBook_Tasks(threeTasks);
//        helper.addTaskToModel(model, threeTasks);
//
//        assertTaskCommandBehavior("select 2",
//                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2),
//                expectedAB,
//                expectedAB.getTaskList());
//        assertEquals(1, targetedJumpIndex);
//        assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
//    }



}
