package seedu.todo.logic.commands;

import org.junit.Test;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.model.TodoModelTest;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.task.ImmutableTask;

import java.util.function.Consumer;

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
    public void testUndo_undoTagRename_allTagsUndone() throws Exception {
        String oldTag = "pikachu";
        String newTag = "pichu";

        for (int i = 1; i <= 3; i ++) {
            model.add("Sample Task " + i);
            Thread.sleep(10);
            model.addTagsToTask(1, oldTag);
        }

        model.renameTag(oldTag, newTag);

        model.getObservableList().forEach(task -> {
            boolean hasOldTag = task.getTags().contains(new Tag(oldTag));
            boolean hasNewTag = task.getTags().contains(new Tag(newTag));
            assertTrue(hasNewTag && !hasOldTag);
        });

        model.undo();

        model.getObservableList().forEach(task -> {
            boolean hasOldTag = task.getTags().contains(new Tag(oldTag));
            boolean hasNewTag = task.getTags().contains(new Tag(newTag));
            assertTrue(!hasNewTag && hasOldTag);
        });
    }
}
