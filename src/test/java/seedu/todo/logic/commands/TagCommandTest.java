package seedu.todo.logic.commands;

import org.junit.Before;
import org.junit.Test;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.task.ImmutableTask;

import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static seedu.todo.testutil.TaskFactory.convertToTags;

//@@author A0135805H
/**
 * Performs integration testing for Tag Command.
 */
public class TagCommandTest extends CommandTest {

    /* Constants */
    private static final String[] TAG_NAMES = {
        "MacBook_Pro1", "MacBook_Air", "Mac_PrO2", "Surface_-Pro3", "Surface-STUDIO", "SurFACE_BoOk"
    };

    /* Override Methods */
    @Override
    protected BaseCommand commandUnderTest() {
        return new TagCommand();
    }

    @Before
    public void setUp() throws Exception{
        //Task indexed at 5
        model.add("Task 5 With 5 Tags");
        Thread.sleep(10);

        //Task indexed at 4
        model.add("Task 4 With 3 Tags");
        Thread.sleep(10);

        //Task indexed at 3
        model.add("Task 3 With 1 Tag");
        Thread.sleep(10);

        //Task indexed at 2
        model.add("Task 2 With 1 Tag");
        Thread.sleep(10);

        //Task indexed at 1
        model.add("Task 1 With 0 Tags");
        Thread.sleep(10);

        //Add tags to dummy tasks
        model.addTagsToTask(5, TAG_NAMES[0], TAG_NAMES[1], TAG_NAMES[2], TAG_NAMES[3], TAG_NAMES[4]);
        model.addTagsToTask(4, TAG_NAMES[0], TAG_NAMES[1], TAG_NAMES[2]);
        model.addTagsToTask(3, TAG_NAMES[1]);
        model.addTagsToTask(2, TAG_NAMES[0]);
    }

    /* Add Tag Test */
    @Test
    public void testAddTag_addSingleTag() throws Exception {
        //Adds a single tag to a task without tags.
        Set<Tag> expectedTags = convertToTags(TAG_NAMES[5]);
        setParameter("1 " + TAG_NAMES[5]);
        execute(true);

        ImmutableTask task = getTaskAt(1);
        assertEquals(expectedTags, task.getTags());
    }

    @Test
    public void testAddTag_addMaxTags() throws Exception {
        //Adds 5 tags to a task without tags.
        Set<Tag> expectedTags = convertToTags(
                TAG_NAMES[0], TAG_NAMES[1], TAG_NAMES[2], TAG_NAMES[3], TAG_NAMES[4]);

        StringJoiner joiner = new StringJoiner(" ");
        joiner.add("1")
                .add(TAG_NAMES[0]).add(TAG_NAMES[1]).add(TAG_NAMES[2]).add(TAG_NAMES[3]).add(TAG_NAMES[4]);
        setParameter(joiner.toString());
        execute(true);

        ImmutableTask task = getTaskAt(1);
        assertEquals(expectedTags, task.getTags());
    }

    @Test
    public void testAddTag_unrestrictedSeparators() throws Exception {
        //Allows separators such as space, commas.
        Set<Tag> expectedTags = convertToTags("Pikachu", "Pichu", "Raichu");
        setParameter("1   Pichu, Pikachu Raichu   ");
        execute(true);

        ImmutableTask task = getTaskAt(1);
        assertEquals(expectedTags, task.getTags());
    }

    @Test (expected = ValidationException.class)
    public void testAddTag_addNoTags() throws Exception {
        //Provides no tag names.
        setParameter("1");
        execute(false);
    }

    @Test (expected = ValidationException.class)
    public void testAddTag_addTooManyTags1() throws Exception {
        //Adds 6 tags to a task without tags
        setParameter("1 tag1 tag2 tag3 tag4 tag5 tag6");
        execute(false);
    }

    @Test (expected = ValidationException.class)
    public void testAddTag_addTooManyTags2() throws Exception {
        //Adds 3 tags to a task with 3 tags
        setParameter("4 tag1 tag2 tag3");
        execute(false);
    }

    @Test (expected = ValidationException.class)
    public void testAddTag_addInvalidTag() throws Exception {
        //Adds a tag with invalid character (not alphanumeric, nor underscores, nor dashes)
        setParameter("1 invalid:)");
        execute(false);
    }

    @Test (expected = ValidationException.class)
    public void testAddTag_addTooLongTag() throws Exception {
        //Adds a tag with 21 characters
        setParameter("1 123456789012345678901");
        execute(false);
    }

    @Test (expected = ValidationException.class)
    public void testAddTag_duplicatedTagNames() throws Exception {
        //Adds tags that contains duplicated tag names
        setParameter("1 hello say hello again");
        execute(false);
    }

    /* Delete Tag From Task Test */
    @Test
    public void testDeleteTagFromTask_deleteOneTag() throws Exception {
        //Deletes one tag from a task with 3 tags. Expects 2 tags left.
        Set<Tag> expectedTags = new HashSet<>(getTaskAt(4).getTags());
        expectedTags.remove(new Tag(TAG_NAMES[0]));

        setParameter("4");
        setParameter("d", TAG_NAMES[0]);
        execute(true);

        ImmutableTask task = getTaskAt(4);
        assertEquals(expectedTags, task.getTags());
    }

    @Test
    public void testDeleteTagFromTask_deleteAllTags() throws Exception {
        //Deletes all the tags from a task with 5 tags. Expects none left.
        Set<Tag> expectedTags = new HashSet<>();

        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(TAG_NAMES[0]).add(TAG_NAMES[1]).add(TAG_NAMES[2]).add(TAG_NAMES[3]).add(TAG_NAMES[4]);

        setParameter("5");
        setParameter("d", joiner.toString());
        execute(true);

        ImmutableTask task = getTaskAt(5);
        assertEquals(expectedTags, task.getTags());
    }

    @Test
    public void testDeleteTagFromTask_deleteMissing() {
        //Deletes a tag that is not found. This should result in no-op.
        Set<Tag> expectedTags = new HashSet<>(getTaskAt(5).getTags());

        setParameter("5");
        setParameter("d", TAG_NAMES[1] + " " + TAG_NAMES[5]);

        try {
            execute(false);
            assert false; //After the above line, not supposed to happen!
        } catch (ValidationException e) {
            //Okay, exception is expected. Now to check the state of the object.
            Set<Tag> outcomeTags = new HashSet<>(getTaskAt(5).getTags());
            assertEquals(expectedTags, outcomeTags);
        }
    }

    @Test (expected = ValidationException.class)
    public void testDeleteTagFromTask_deleteNoParam() throws Exception {
        //Declares no parameters to delete command.
        setParameter("1");
        setParameter("d", "   ");
        execute(false);
    }

    /* Delete Tags Globally Test */
    @Test
    public void testDeleteTagGlobally_deleteOneTag() throws Exception {
        //Deletes one tag from the list of tags. All other tags should stay intact.
        Set<Tag> expects0Tags = convertToTags();
        Set<Tag> expects1Tags = convertToTags(TAG_NAMES[1]);
        Set<Tag> expects2Tags = convertToTags(TAG_NAMES[1], TAG_NAMES[2]);
        Set<Tag> expects4Tags = convertToTags(TAG_NAMES[1], TAG_NAMES[2], TAG_NAMES[3], TAG_NAMES[4]);

        setParameter("d", TAG_NAMES[0]);
        execute(true);

        assertEquals(expects0Tags, getTaskAt(1).getTags());
        assertEquals(expects0Tags, getTaskAt(2).getTags());
        assertEquals(expects1Tags, getTaskAt(3).getTags());
        assertEquals(expects2Tags, getTaskAt(4).getTags());
        assertEquals(expects4Tags, getTaskAt(5).getTags());
    }

    @Test
    public void testDeleteTagGlobally_deleteMoreTags() throws Exception {
        //Deletes two tags from the list of tags. All other tags should stay intact.
        Set<Tag> expects0Tags = convertToTags();
        Set<Tag> expects1Tags = convertToTags(TAG_NAMES[2]);
        Set<Tag> expects3Tags = convertToTags(TAG_NAMES[2], TAG_NAMES[3], TAG_NAMES[4]);

        setParameter("d", TAG_NAMES[0] + " " + TAG_NAMES[1]);
        execute(true);

        assertEquals(expects0Tags, getTaskAt(1).getTags());
        assertEquals(expects0Tags, getTaskAt(2).getTags());
        assertEquals(expects0Tags, getTaskAt(3).getTags());
        assertEquals(expects1Tags, getTaskAt(4).getTags());
        assertEquals(expects3Tags, getTaskAt(5).getTags());
    }

    @Test
    public void testDeleteTagGlobally_deleteMissingTags() {
        //Deletes a tag that does not exist. This should result in no op.
        List<Set<Tag>> listOfExpectedOutcome = model.getObservableList().stream()
                .map((Function<ImmutableTask, Set<Tag>>) task -> new HashSet<>(task.getTags()))
                .collect(Collectors.toList());

        setParameter("d", TAG_NAMES[1] + " " + TAG_NAMES[5]);

        try {
            execute(false);
            assert false; //After the above line, not supposed to happen!
        } catch (ValidationException e) {
            //Validation exception expected, now check that the tags are unmodified.
            for (int taskIndex = 1; taskIndex <= 5 ; taskIndex ++) {
                assertEquals(listOfExpectedOutcome.get(taskIndex - 1), getTaskAt(taskIndex).getTags());
            }
        }
    }

    @Test (expected = ValidationException.class)
    public void testDeleteTagGlobally_deleteNoParam() throws Exception {
        //Declares no parameters to delete command.
        setParameter("d", "   ");
        execute(false);
    }

    /* Rename Tag Test */
    @Test
    public void renameTag_renameTagSuccess() throws Exception {
        //Renames a tag successfully
        Set<Tag> expectsTask1Tag = convertToTags();
        Set<Tag> expectsTask2Tag = convertToTags(TAG_NAMES[5]);
        Set<Tag> expectsTask3Tag = convertToTags(TAG_NAMES[1]);
        Set<Tag> expectsTask4Tag = convertToTags(TAG_NAMES[5], TAG_NAMES[1], TAG_NAMES[2]);
        Set<Tag> expectsTask5Tag
                = convertToTags(TAG_NAMES[5], TAG_NAMES[1], TAG_NAMES[2], TAG_NAMES[3], TAG_NAMES[4]);

        setParameter("r", TAG_NAMES[0] + " " + TAG_NAMES[5]);
        execute(true);

        assertEquals(expectsTask1Tag, getTaskAt(1).getTags());
        assertEquals(expectsTask2Tag, getTaskAt(2).getTags());
        assertEquals(expectsTask3Tag, getTaskAt(3).getTags());
        assertEquals(expectsTask4Tag, getTaskAt(4).getTags());
        assertEquals(expectsTask5Tag, getTaskAt(5).getTags());
    }

    @Test (expected = ValidationException.class)
    public void renameTag_newNameExists() throws Exception {
        //Renames to a name that already exists.
        setParameter("r", TAG_NAMES[0] + " " + TAG_NAMES[1]);
        execute(false);
    }

    @Test (expected = ValidationException.class)
    public void renameTag_oldNameMissing() throws Exception {
        //Renames from a name that does not exist
        setParameter("r", TAG_NAMES[5] + " " + TAG_NAMES[1]);
        execute(false);
    }

    @Test (expected = ValidationException.class)
    public void renameTag_oneTagNameOnly() throws Exception {
        //Provides one tag name for rename only. This is incorrect.
        setParameter("r", TAG_NAMES[5]);
        execute(false);
    }

    @Test (expected = ValidationException.class)
    public void renameTag_renameNoParams() throws Exception {
        //Provides no tag names for renaming. This is incorrect.
        setParameter("r", "   ");
        execute(false);
    }
}
