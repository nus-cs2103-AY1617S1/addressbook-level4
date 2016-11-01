package tars.logic;

import static tars.commons.core.Messages.MESSAGE_DUPLICATE_TAG;
import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tars.commons.core.Messages.MESSAGE_INVALID_TAG_DISPLAYED_INDEX;

import java.util.ArrayList;

import org.junit.Test;

import tars.logic.commands.RedoCommand;
import tars.logic.commands.TagCommand;
import tars.logic.commands.UndoCommand;
import tars.model.Tars;
import tars.model.tag.ReadOnlyTag;
import tars.model.tag.Tag;
import tars.model.task.ReadOnlyTask;
import tars.model.task.Task;
import tars.ui.Formatter;

/**
 * Logic command test for tag
 * 
 * @@author A0139924W
 */
public class TagLogicCommandTest extends LogicCommandTest {

    @Test
    public void execute_tag_invalidPrefix() throws Exception {
        assertCommandBehavior("tag /gg", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
    }

    @Test
    public void execute_tag_invalidFormat() throws Exception {
        assertCommandBehavior("tag ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        assertCommandBehavior("tag RANDOM_TEXT", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
    }

    @Test
    public void execute_tag_invalidIndex() throws Exception {
        // EP: negative number
        assertCommandBehavior("tag /e -1 VALIDTASKNAME", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        assertCommandBehavior("tag /del -1",
                MESSAGE_INVALID_TAG_DISPLAYED_INDEX);

        // EP: zero
        assertCommandBehavior("tag /e 0 VALIDTASKNAME",
                MESSAGE_INVALID_TAG_DISPLAYED_INDEX);
        assertCommandBehavior("tag /del 0",
                MESSAGE_INVALID_TAG_DISPLAYED_INDEX);

        // EP: signed number
        assertCommandBehavior("tag /e +1 VALIDTASKNAME", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        assertCommandBehavior("tag /e -2 VALIDTASKNAME", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        assertCommandBehavior("tag /del +1",
                MESSAGE_INVALID_TAG_DISPLAYED_INDEX);
        assertCommandBehavior("tag /del -1",
                MESSAGE_INVALID_TAG_DISPLAYED_INDEX);

        // EP: invalid number
        assertCommandBehavior("tag /del aaa", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        assertCommandBehavior("tag /del bbb", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
    }

    @Test
    public void execute_tag_emptyParameters() throws Exception {
        assertCommandBehavior("tag", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        assertCommandBehavior("tag  ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        assertCommandBehavior("tag -e", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        assertCommandBehavior("tag -e  ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        assertCommandBehavior("tag -del", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        assertCommandBehavior("tag -del  ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
    }

    @Test
    public void execute_tagList_emptyListSuccessful() throws Exception {
        // execute command and verify result
        assertCommandBehavior("tag /ls",
                new Formatter().formatTags(model.getUniqueTagList()));
    }

    @Test
    public void execute_tagList_filledListSuccessful() throws Exception {
        // setup expectations
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task toBeAdded = helper.meetAdam();
        Tars expectedTars = new Tars();
        expectedTars.addTask(toBeAdded);

        model.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior("tag /ls",
                new Formatter().formatTags(model.getUniqueTagList()),
                expectedTars, expectedTars.getTaskList());
    }

    @Test
    public void execute_tagRename_successful() throws Exception {
        // setup expectations
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task toBeAdded = helper.meetAdam();
        Tars expectedTars = new Tars();
        expectedTars.addTask(toBeAdded);

        model.addTask(toBeAdded);

        ReadOnlyTag toBeRenamed =
                expectedTars.getUniqueTagList().getInternalList().get(0);
        Tag newTag = new Tag("tag3");

        expectedTars.getUniqueTagList().update(toBeRenamed, newTag);
        expectedTars.renameTasksWithNewTag(toBeRenamed, newTag);

        // execute command and verify result
        assertCommandBehavior("tag /e 1 tag3",
                String.format(String.format(
                        TagCommand.MESSAGE_RENAME_TAG_SUCCESS, "tag1", "tag3")),
                expectedTars, expectedTars.getTaskList());
    }

    @Test
    public void execute_tagRename_duplicate() throws Exception {
        // setup expectations
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task toBeAdded = helper.meetAdam();
        Tars expectedTars = new Tars();
        expectedTars.addTask(toBeAdded);

        model.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior("tag /e 1 tag2", MESSAGE_DUPLICATE_TAG,
                expectedTars, expectedTars.getTaskList());
    }

    @Test
    public void execute_tagRename_invalidIndex() throws Exception {
        // setup expectations
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task toBeAdded = helper.meetAdam();
        Tars expectedTars = new Tars();
        expectedTars.addTask(toBeAdded);

        model.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior("tag /e 3 VALIDTAGNAME",
                MESSAGE_INVALID_TAG_DISPLAYED_INDEX, expectedTars,
                expectedTars.getTaskList());

        assertCommandBehavior("tag /e 4 VALIDTAGNAME",
                MESSAGE_INVALID_TAG_DISPLAYED_INDEX, expectedTars,
                expectedTars.getTaskList());
    }

    @Test
    public void execute_tagRename_invalidTagName() throws Exception {
        // setup expectations
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task toBeAdded = helper.meetAdam();
        Tars expectedTars = new Tars();
        expectedTars.addTask(toBeAdded);

        model.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior("tag /e 1 INVALID_TAG_NAME",
                Tag.MESSAGE_TAG_CONSTRAINTS, expectedTars,
                expectedTars.getTaskList());
    }

    @Test
    public void execute_tagDel_successful() throws Exception {
        // setup expectations
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task toBeAdded = helper.meetAdam();
        Tars expectedTars = new Tars();
        expectedTars.addTask(toBeAdded);

        model.addTask(toBeAdded);

        ReadOnlyTag toBeDeleted =
                expectedTars.getUniqueTagList().getInternalList().get(0);

        expectedTars.getUniqueTagList().remove(new Tag(toBeDeleted));
        expectedTars.removeTagFromAllTasks(toBeDeleted);

        // execute command and verify result
        assertCommandBehavior("tag /del 1", String
                .format(TagCommand.MESSAGE_DELETE_TAG_SUCCESS, toBeDeleted),
                expectedTars, expectedTars.getTaskList());
    }

    @Test
    public void execute_tagDel_invalidIndex() throws Exception {
        // setup expectations
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task toBeAdded = helper.meetAdam();
        Tars expectedTars = new Tars();
        expectedTars.addTask(toBeAdded);

        model.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior("tag /del 3", MESSAGE_INVALID_TAG_DISPLAYED_INDEX,
                expectedTars, expectedTars.getTaskList());

        assertCommandBehavior("tag /del 4", MESSAGE_INVALID_TAG_DISPLAYED_INDEX,
                expectedTars, expectedTars.getTaskList());
    }

    @Test
    public void execute_undoAndRedo_tagEditSuccessful() throws Exception {
        // setup expectations
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task toBeAdded = helper.meetAdam();
        Tars expectedTars = new Tars();
        expectedTars.addTask(toBeAdded);

        model.addTask(toBeAdded);

        ReadOnlyTag toBeRenamed =
                expectedTars.getUniqueTagList().getInternalList().get(0);
        Tag newTag = new Tag("tag3");

        expectedTars.getUniqueTagList().update(toBeRenamed, newTag);
        expectedTars.renameTasksWithNewTag(toBeRenamed, newTag);

        assertCommandBehavior("tag /e 1 tag3",
                String.format(String.format(
                        TagCommand.MESSAGE_RENAME_TAG_SUCCESS, "tag1", "tag3")),
                expectedTars, expectedTars.getTaskList());

        toBeRenamed = expectedTars.getUniqueTagList().getInternalList().get(0);
        newTag = new Tag("tag1");

        expectedTars.getUniqueTagList().update(toBeRenamed, newTag);
        expectedTars.renameTasksWithNewTag(toBeRenamed, newTag);

        assertCommandBehavior(UndoCommand.COMMAND_WORD,
                String.format(UndoCommand.MESSAGE_SUCCESS, ""), expectedTars,
                expectedTars.getTaskList());

        toBeRenamed = expectedTars.getUniqueTagList().getInternalList().get(0);
        newTag = new Tag("tag3");

        expectedTars.getUniqueTagList().update(toBeRenamed, newTag);
        expectedTars.renameTasksWithNewTag(toBeRenamed, newTag);

        assertCommandBehavior(RedoCommand.COMMAND_WORD,
                String.format(RedoCommand.MESSAGE_SUCCESS, ""), expectedTars,
                expectedTars.getTaskList());
    }

    @Test
    public void execute_undoAndRedo_tagDelSuccessful() throws Exception {
        // setup expectations
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task toBeAdded = helper.meetAdam();
        Tars expectedTars = new Tars();
        expectedTars.addTask(toBeAdded);

        model.addTask(toBeAdded);

        ReadOnlyTag toBeDeleted =
                expectedTars.getUniqueTagList().getInternalList().get(0);

        expectedTars.getUniqueTagList().remove(new Tag(toBeDeleted));
        ArrayList<ReadOnlyTask> editedTaskList =
                expectedTars.removeTagFromAllTasks(toBeDeleted);

        // execute command and verify result
        assertCommandBehavior("tag /del 1", String
                .format(TagCommand.MESSAGE_DELETE_TAG_SUCCESS, toBeDeleted),
                expectedTars, expectedTars.getTaskList());

        expectedTars.getUniqueTagList().add(new Tag(toBeDeleted));
        expectedTars.addTagToAllTasks(toBeDeleted, editedTaskList);

        assertCommandBehavior(UndoCommand.COMMAND_WORD,
                String.format(UndoCommand.MESSAGE_SUCCESS, ""), expectedTars,
                expectedTars.getTaskList());

        expectedTars.getUniqueTagList().remove(new Tag(toBeDeleted));
        expectedTars.removeTagFromAllTasks(toBeDeleted);

        assertCommandBehavior(RedoCommand.COMMAND_WORD,
                String.format(RedoCommand.MESSAGE_SUCCESS, ""), expectedTars,
                expectedTars.getTaskList());
    }
}
