package seedu.todo.logic.commands;

import org.junit.Test;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.model.TodoModelTest;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.task.ImmutableTask;

import java.util.function.Consumer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

//@@author A0135817B
public class UndoCommandTest extends CommandTest {

    @Override
    protected BaseCommand commandUnderTest() {
        return new UndoCommand();
    }

    /**
     * This is an integration test for the redo command. For a more detailed test on the model itself 
     * {@link TodoModelTest#testUndo} and other related tests 
     */
    @Test
    public void testUndo() throws Exception {
        model.add("Test task");
        execute(true);
    }
    
    @Test(expected = ValidationException.class)
    public void testEmptyUndo() throws Exception {
        execute(false);
    }

    //@@author A0135805H
    @Test
    public void testUndo_undoAddTag() throws Exception {
        String tagName = "pikachu";
        model.add("Sample Task");
        Thread.sleep(10);
        model.addTagsToTask(1, tagName);

        //Sanity check: The tag is really added.
        assertTrue(model.getTask(1).getTags().contains(new Tag(tagName)));

        model.undo();

        //Check if the the tag is removed.
        assertFalse(model.getTask(1).getTags().contains(new Tag(tagName)));
    }

    @Test
    public void testUndo_undoDeleteTagFromATask() throws Exception {
        String tagName = "pikachu";
        model.add("Sample Task");
        Thread.sleep(10);
        model.addTagsToTask(1, tagName);
        model.deleteTagsFromTask(1, tagName);

        //Sanity check: The tag is really deleted.
        assertFalse(model.getTask(1).getTags().contains(new Tag(tagName)));

        model.undo();

        //Check if the the tag is restored.
        assertTrue(model.getTask(1).getTags().contains(new Tag(tagName)));
    }

    @Test
    public void testUndo_undoRenameTagFromTask() throws Exception {

        String oldName = "pikachu";
        String newName = "pichu";
        model.add("Sample Task");
        Thread.sleep(10);
        model.addTagsToTask(1, oldName);

        model.renameTag(1, oldName, newName);

        //Sanity check: The tag is really renamed.
        assertTrue(model.getTask(1).getTags().contains(new Tag(newName)));

        model.undo();

        //Check if the the tag is restored.
        assertTrue(model.getTask(1).getTags().contains(new Tag(oldName)));
    }

    @Test
    public void testUndo_undoTagRename_allTagNamesRestored() throws Exception {
        String oldTag = "pikachu";
        String newTag = "pichu";

        for (int i = 1; i <= 3; i ++) {
            model.add("Sample Task " + i);
            Thread.sleep(10);
            model.addTagsToTask(1, oldTag);
        }

        model.renameTag(oldTag, newTag);

        //Sanity check: The tags are really renamed.
        model.getObservableList().forEach(task -> {
            boolean hasOldTag = task.getTags().contains(new Tag(oldTag));
            boolean hasNewTag = task.getTags().contains(new Tag(newTag));
            assertTrue(hasNewTag && !hasOldTag);
        });

        model.undo();

        //Check if the old tag name is restored.
        model.getObservableList().forEach(task -> {
            boolean hasOldTag = task.getTags().contains(new Tag(oldTag));
            boolean hasNewTag = task.getTags().contains(new Tag(newTag));
            assertTrue(!hasNewTag && hasOldTag);
        });
    }

    @Test
    public void testUndo_undoTagDelete_allTagNamesRestored() throws Exception {
        String oldTag = "pikachu";

        for (int i = 1; i <= 3; i ++) {
            model.add("Sample Task " + i);
            Thread.sleep(10);
            model.addTagsToTask(1, oldTag);
        }

        model.deleteTags(oldTag);

        //Sanity check: The tags are really deleted.
        model.getObservableList().forEach(task -> {
            boolean hasOldTag = task.getTags().contains(new Tag(oldTag));
            assertTrue(!hasOldTag);
        });

        model.undo();

        //Checks if the tags are restored.
        model.getObservableList().forEach(task -> {
            boolean hasOldTag = task.getTags().contains(new Tag(oldTag));
            assertTrue(hasOldTag);
        });
    }
}
