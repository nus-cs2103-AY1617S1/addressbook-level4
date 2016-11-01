//package seedu.forgetmenot.model;
//
//import java.util.List;
//
//import org.junit.Test;
//
//import seedu.forgetmenot.logic.LogicManagerTest.TestDataHelper;
//import seedu.forgetmenot.logic.commands.EditCommand;
//import seedu.forgetmenot.model.task.Task;
//
//public class TaskManagerTest {
//    //@@author A0139671X
//    @Test
//    public void execute_edit_taskName() throws Exception {
//        TestDataHelper helper = new TestDataHelper();
//        Task pTarget1 = helper.generateTaskWithName("task 1");
//        Task pTarget2 = helper.generateTaskWithName("old name to change");
//        List<Task> twoTasks = helper.generateTaskList(pTarget1, pTarget2);
//        
//        TaskManager expectedAB = helper.generateTaskManager(twoTasks);
//        expectedAB.editTaskName(twoTasks.get(1), "new name");
//        helper.addToModel(model, twoTasks);
//        
//        assertCommandBehavior("edit 2 new name",
//                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, twoTasks.get(1)),
//                expectedAB,
//                expectedAB.getTaskList());
//    }
//    
//    //@@author A0139671X
//    @Test
//    public void execute_edit_taskStartTime() throws Exception {
//        TestDataHelper helper = new TestDataHelper();
//        List<Task> threeTasks = helper.generateTaskList(3);
//        
//        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
//        expectedAB.editTaskStartTime(threeTasks.get(1), "from 1/1/17 5pm");
//        helper.addToModel(model, threeTasks);
//        
//        assertCommandBehavior("edit 2 from 1/1/17 5pm",
//                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, threeTasks.get(1)),
//                expectedAB,
//                expectedAB.getTaskList());
//    }
//    
//    //@@author A0139671X
//    @Test
//    public void execute_edit_taskEndTime() throws Exception {
//        TestDataHelper helper = new TestDataHelper();
//        List<Task> threeTasks = helper.generateTaskList(3);
//        
//        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
//        expectedAB.editTaskEndTime(threeTasks.get(0), "by 3pm tomorrow");
//        helper.addToModel(model, threeTasks);
//        
//        assertCommandBehavior("edit 2 by 2/1/17 5am",
//                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, threeTasks.get(1)),
//                expectedAB,
//                expectedAB.getTaskList());
//    } 
//}
