package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import seedu.oneline.commons.core.Messages;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.logic.commands.AddCommand;
import seedu.oneline.logic.commands.CommandConstants;
import seedu.oneline.logic.commands.EditCommand;
import seedu.oneline.logic.commands.EditTaskCommand;
import seedu.oneline.logic.parser.Parser;
import seedu.oneline.model.Model;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.tag.UniqueTagList;
import seedu.oneline.model.task.TaskField;
import seedu.oneline.model.task.TaskName;
import seedu.oneline.model.task.TaskRecurrence;
import seedu.oneline.model.task.TaskTime;
import seedu.oneline.testutil.TestTask;
import seedu.oneline.testutil.TestUtil;
import seedu.oneline.testutil.TypicalTestTasks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.oneline.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class EditCommandTest extends TaskBookGuiTest {

    @Test
    public void edit() {
        //edit one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask newTask = td.eventExtra;
        Map<TaskField, String> fields = new HashMap<TaskField, String>();
        fields.put(TaskField.NAME, newTask.getName().name);
        fields.put(TaskField.START_TIME, newTask.getStartTime().toString());
        fields.put(TaskField.END_TIME, newTask.getEndTime().toString());
        fields.put(TaskField.DEADLINE, newTask.getDeadline().toString());
        fields.put(TaskField.RECURRENCE, newTask.getRecurrence().toString());
        fields.put(TaskField.TAG, newTask.getTag().getTagName());
        assertEditSuccess(2, fields, currentList);
        currentList[1] = newTask;

        //edit with invalid fields
        fields = new HashMap<TaskField, String>();
        fields.put(TaskField.START_TIME, "Not a real time");
        assertEditFailed(2, fields, TaskTime.MESSAGE_TASK_TIME_CONSTRAINTS, currentList);

//        //add another task
//        taskToAdd = TypicalTestTasks.todoExtra;
//        assertEditSuccess(taskToAdd, currentList);
//        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

//        //add duplicate task
//        commandBox.runCommand(TypicalTestTasks.eventExtra.getAddCommand());
//        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
//        assertTrue(taskListPanel.isListMatching(currentList));

//        //add to empty list
//        commandBox.runCommand("clear");
//        assertEditSuccess(TypicalTestTasks.event1);

        //invalid command
        commandBox.runCommand("edits Task");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertEditSuccess(int index, Map<TaskField, String> fields, TestTask... currentList) {
        Arrays.sort(currentList);
        TestTask[] expectedRemainder = currentList.clone();
        assert 0 <= index && index < expectedRemainder.length;
        StringBuilder cmd = new StringBuilder();
        cmd.append("edit ").append(index);
        TestTask newTask = new TestTask(expectedRemainder[index - 1]);
        try {
            if (fields.containsKey(TaskField.NAME)) {
                String newName = fields.get(TaskField.NAME);
                cmd.append(" ").append(newName);
                newTask.setName(new TaskName(newName));
            }
            if (fields.containsKey(TaskField.START_TIME)) {
                String newStartTime = fields.get(TaskField.START_TIME);
                cmd.append(" ")
                    .append(CommandConstants.KEYWORD_PREFIX)
                    .append(CommandConstants.KEYWORD_START_TIME)
                    .append(" ")
                    .append(newStartTime);
                newTask.setStartTime(new TaskTime(newStartTime));
            }
            if (fields.containsKey(TaskField.END_TIME)) {
                String newEndTime = fields.get(TaskField.END_TIME);
                cmd.append(" ")
                    .append(CommandConstants.KEYWORD_PREFIX)
                    .append(CommandConstants.KEYWORD_END_TIME)
                    .append(" ")
                    .append(newEndTime);
                newTask.setEndTime(new TaskTime(newEndTime));
            }
            if (fields.containsKey(TaskField.DEADLINE)) {
                String newDeadline = fields.get(TaskField.DEADLINE);
                cmd.append(" ")
                    .append(CommandConstants.KEYWORD_PREFIX)
                    .append(CommandConstants.KEYWORD_DEADLINE)
                    .append(" ")
                    .append(newDeadline);
                newTask.setDeadline(new TaskTime(newDeadline));
            }
            if (fields.containsKey(TaskField.RECURRENCE)) {
                String newRecurrence = fields.get(TaskField.RECURRENCE);
                cmd.append(" ")
                    .append(CommandConstants.KEYWORD_PREFIX)
                    .append(CommandConstants.KEYWORD_RECURRENCE)
                    .append(" ")
                    .append(newRecurrence);
                newTask.setRecurrence(new TaskRecurrence(newRecurrence));
            }
            if (fields.containsKey(TaskField.TAG)) {
                String newTag = fields.get(TaskField.TAG);
                cmd.append(" ")
                .append(CommandConstants.TAG_PREFIX)
                .append(newTag);
                newTask.setTag(Tag.getTag(newTag));
            }
        } catch (IllegalValueException e) {
            assert false : "Invalid input";
        }
        expectedRemainder[index - 1] = newTask;
        commandBox.runCommand(cmd.toString());
        Arrays.sort(expectedRemainder);

        assertTrue(taskPane.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(EditTaskCommand.MESSAGE_SUCCESS, newTask.toString()));
    }
    
    private void assertEditFailed(int index, Map<TaskField, String> fields, String message, TestTask... currentList) {
        TestTask[] expectedRemainder = currentList.clone();
        assert 0 <= index && index < expectedRemainder.length;
        StringBuilder cmd = new StringBuilder();
        cmd.append("edit ").append(index);
        if (fields.containsKey(TaskField.NAME)) {
            String newName = fields.get(TaskField.NAME);
            cmd.append(" ").append(newName);
        }
        if (fields.containsKey(TaskField.START_TIME)) {
            String newStartTime = fields.get(TaskField.START_TIME);
            cmd.append(" ")
                .append(CommandConstants.KEYWORD_PREFIX)
                .append(CommandConstants.KEYWORD_START_TIME)
                .append(" ")
                .append(newStartTime);
        }
        if (fields.containsKey(TaskField.END_TIME)) {
            String newEndTime = fields.get(TaskField.END_TIME);
            cmd.append(" ")
                .append(CommandConstants.KEYWORD_PREFIX)
                .append(CommandConstants.KEYWORD_END_TIME)
                .append(" ")
                .append(newEndTime);
        }
        if (fields.containsKey(TaskField.DEADLINE)) {
            String newDeadline = fields.get(TaskField.DEADLINE);
            cmd.append(" ")
                .append(CommandConstants.KEYWORD_PREFIX)
                .append(CommandConstants.KEYWORD_DEADLINE)
                .append(" ")
                .append(newDeadline);
        }
        if (fields.containsKey(TaskField.RECURRENCE)) {
            String newRecurrence = fields.get(TaskField.RECURRENCE);
            cmd.append(" ")
                .append(CommandConstants.KEYWORD_PREFIX)
                .append(CommandConstants.KEYWORD_RECURRENCE)
                .append(" ")
                .append(newRecurrence);
        }
        if (fields.containsKey(TaskField.TAG)) {
            String newTag = fields.get(TaskField.TAG);
            cmd.append(" ")
            .append(CommandConstants.TAG_PREFIX)
            .append(newTag);
        }
        commandBox.runCommand(cmd.toString());

        assertTrue(taskPane.isListMatching(false, expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(message);
    }

}
