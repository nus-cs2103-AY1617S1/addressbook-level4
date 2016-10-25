package seedu.task.logic;

import org.junit.Ignore;
import org.junit.Test;

import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.EditEventCommand;
import seedu.task.logic.commands.EditTaskCommand;
import seedu.task.model.TaskBook;
import seedu.task.model.item.Event;
import seedu.task.model.item.Task;
import seedu.task.model.item.UniqueTaskList.DuplicateTaskException;
import seedu.taskcommons.core.Messages;

public class EditCommandTest extends CommandTest {

    /*
     * Tests for editing floating tasks and tasks
     */
    
    /*
     * 1) Invalid Edit Task and Event Command EPs
     *  - Editing a task to an existing task, DuplicateTaskException
     *  - Editing an event to an existing event, DuplicateEventException
     *  - Invalid edit command input
     *  - Invalid edit task index input
     *  - Invalid edit event index input
     */
    
    @Test
    public void execute_editFloatTask_duplicate() throws Exception {
        
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingDescTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        Task toBeAdded2 = helper.computingEditedNameFloatTask();
        expectedAB.addTask(toBeAdded2);
        Task toBeEdited = helper.computingDescTask();

        // execute command and verify result
        assertEditDuplicateCommandBehavior(helper.generateAddDescTaskCommand(toBeAdded), helper.generateAddDescTaskCommand(toBeAdded2),helper.generateListTaskCommand(),
                helper.generateEditFloatTaskCommand(toBeEdited,2),
                String.format(EditTaskCommand.MESSAGE_DUPLICATE_TASK, toBeEdited),
                expectedAB);

    }
    
    @Test
    public void execute_editEvent_duplicate() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Event toBeAdded = helper.computingUpComingEvent();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addEvent(toBeAdded);
        Event toBeAdded2 = helper.computingUpComingEvent2();
        expectedAB.addEvent(toBeAdded2);
        Event toBeEdited = helper.computingUpComingEvent();

        // execute command and verify result
        assertEditDuplicateCommandBehavior(helper.generateAddEventCommand(toBeAdded), helper.generateAddEventCommand(toBeAdded2),helper.generateListEventCommand(),
                helper.generateEditEventCommand(toBeEdited,2),
                String.format(EditEventCommand.MESSAGE_DUPLICATE_EVENT, toBeEdited),
                expectedAB);

    }
    
    @Test
    public void execute_invalidEditCommandInput() throws Exception {
        // setup expectations
        TaskBook expectedAB = new TaskBook();
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingTask();
        expectedAB.addTask(toBeAdded);
        String invalidEditCommand = "edit ajsdn 1";
        
        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                invalidEditCommand,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE),
                expectedAB,
                expectedAB.getTaskList()); 
        
    }
    
    @Test
    public void execute_editTask_invalidIndex_unsuccessful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        Task toBeEdited = helper.computingEditedFloatTask();

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditFloatTaskCommand(toBeEdited,2),
                String.format(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    @Test
    public void execute_editEvent_invalidIndex_unsuccessful() throws Exception {
     // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Event toBeAdded = helper.computingUpComingEvent();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addEvent(toBeAdded);
        Event toBeEdited = helper.computingUpComingEvent2();

        // execute command and verify result
        assertEditEventCommandBehavior(helper.generateAddEventCommand(toBeAdded),helper.generateListEventCommand(),
                helper.generateEditEventCommand(toBeEdited,2),
                String.format(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX),
                expectedAB,
                expectedAB.getEventList());

    }
    
    /*
     * 2) Valid edit float task command and successful execution EPs
     * 
     *  - Editing a floating task
     *      -> Editing name
     *      -> Editing description
     *      -> Editing name and description
     *      -> Adding deadline to change to deadline task
     *      -> Editing all 3 fields
     *      
     */

    //Editing float task name only
    @Test
    public void execute_editFloatTask_name_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingDescTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        Task toBeEdited = helper.computingEditedNameFloatTask();
        expectedAB.editTask(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddDescTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditFloatTaskCommand(toBeEdited,1),
                String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    //Editing float task desc only
    @Test
    public void execute_editFloatTask_desc_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingDescTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        Task toBeEdited = helper.computingEditedDescFloatTask();
        expectedAB.editTask(toBeEdited, toBeAdded);
        

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddDescTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditFloatTaskCommand(toBeEdited,1),
                String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    //Editing float task name and desc
    @Test
    public void execute_editFloatTask_name_desc_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingDescTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        Task toBeEdited = helper.computingEditedFloatTask();
        expectedAB.editTask(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddDescTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditFloatTaskCommand(toBeEdited,1),
                String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    //Adding deadline to float task
    @Test
    public void execute_editFloatTaskToDeadlineTask_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingDescTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        Task toBeEdited = helper.computingTask();
        expectedAB.editTask(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddDescTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditTaskCommand(toBeEdited,1),
                String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    /*
     * 3) Valid edit task command and successful execution EPs
     * 
     *   - Editing a task
     *      -> Editing name
     *      -> Editing description
     *      -> Editing deadline
     *      -> Editing all 3 fields
     *      -> Removing deadline to change to floating task
     *      
     */

    //Editing name
    @Test
    public void execute_editTask_name_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        Task toBeEdited = helper.computingEditedNameTask();
        expectedAB.editTask(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditTaskCommand(toBeEdited,1),
                String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    //Editing description
    @Test
    public void execute_editTask_desc_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        Task toBeEdited = helper.computingEditedDescTask();
        expectedAB.editTask(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditTaskCommand(toBeEdited,1),
                String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    //Editing deadline
    @Test
    public void execute_editTask_deadline_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        Task toBeEdited = helper.computingEditedDeadlineTask();
        expectedAB.editTask(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditTaskCommand(toBeEdited,1),
                String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    //Editing all 3 fields
    @Test
    public void execute_editTask_all_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        Task toBeEdited = helper.computingEditedTask();
        expectedAB.editTask(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditTaskCommand(toBeEdited,1),
                String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    //Removing deadline to change to floating task
    //TODO
    @Ignore
    @Test
    public void execute_editTask_remove_deadline_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        Task toBeEdited = helper.computingDescTask();
        expectedAB.editTask(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditFloatTaskCommand(toBeEdited,1),
                String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    /*
     * 4) Valid edit event command and successful execution EPs
     * 
     *   - Editing an event
     *      -> Editing name
     *      -> Editing description
     *      -> Editing entire duration
     *      -> Editing start duration
     *      -> Editing end duration
     *      -> Editing all 3 fields
     *      
     */
    
    //Editing name
    @Test
    public void execute_editEvent_name_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Event toBeAdded = helper.computingUpComingEvent();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addEvent(toBeAdded);
        Event toBeEdited = helper.computingEditedNameUpComingEvent();
        expectedAB.editEvent(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditEventCommandBehavior(helper.generateAddEventCommand(toBeAdded),helper.generateListEventCommand(),
                helper.generateEditEventCommand(toBeEdited,1),
                String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getEventList());

    }
    
    //Editing description
    @Test
    public void execute_editEvent_desc_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Event toBeAdded = helper.computingUpComingEvent();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addEvent(toBeAdded);
        Event toBeEdited = helper.computingEditedDescUpComingEvent();
        expectedAB.editEvent(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditEventCommandBehavior(helper.generateAddEventCommand(toBeAdded),helper.generateListEventCommand(),
                helper.generateEditEventDescCommand(toBeEdited,1),
                String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getEventList());

    }
    
    //Editing duration
    @Test
    public void execute_editEvent_duration_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Event toBeAdded = helper.computingUpComingEvent();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addEvent(toBeAdded);
        Event toBeEdited = helper.computingEditedDurationUpComingEvent();
        expectedAB.editEvent(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditEventCommandBehavior(helper.generateAddEventCommand(toBeAdded),helper.generateListEventCommand(),
                helper.generateEditEventDurationCommand(toBeEdited,1),
                String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getEventList());

    }
    
    //Editing start duration
    @Test
    public void execute_editEvent_StartDuration_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Event toBeAdded = helper.computingUpComingEvent();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addEvent(toBeAdded);
        Event toBeEdited = helper.computingEditedStartDurationUpComingEvent();
        expectedAB.editEvent(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditEventCommandBehavior(helper.generateAddEventCommand(toBeAdded),helper.generateListEventCommand(),
                helper.generateEditEventStartDurationCommand(toBeEdited,1),
                String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getEventList());

    }
    
    //Editing end duration
    @Test
    public void execute_editEvent_EndDuration_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Event toBeAdded = helper.computingUpComingEvent();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addEvent(toBeAdded);
        Event toBeEdited = helper.computingEditedEndDurationUpComingEvent();
        expectedAB.editEvent(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditEventCommandBehavior(helper.generateAddEventCommand(toBeAdded),helper.generateListEventCommand(),
                helper.generateEditEventEndDurationCommand(toBeEdited,1),
                String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getEventList());

    }
    
    //Editing all 3 fields
    @Test
    public void execute_editEvent_all_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Event toBeAdded = helper.computingUpComingEvent();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addEvent(toBeAdded);
        Event toBeEdited = helper.computingUpComingEvent2();
        expectedAB.editEvent(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditEventCommandBehavior(helper.generateAddEventCommand(toBeAdded),helper.generateListEventCommand(),
                helper.generateEditEventCommand(toBeEdited,1),
                String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getEventList());

    }
 
}
