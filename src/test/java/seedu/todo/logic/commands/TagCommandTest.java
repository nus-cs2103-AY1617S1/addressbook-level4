package seedu.todo.logic.commands;

import org.junit.Before;
import org.junit.Test;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.testutil.TaskFactory;

import java.util.Set;
import java.util.StringJoiner;

import static org.junit.Assert.assertEquals;

//@@author A0135805H
/**
 * Performs integration testing for Tag Command.
 */
public class TagCommandTest extends CommandTest {

    /* Constants */
    private static final String[] VALID_TAG_NAMES = {
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
        model.add("Task 5 With 5 Tags", mutableTask -> {
            Set<Tag> newTags = TaskFactory.convertTagNamesToTags(VALID_TAG_NAMES[0],
                    VALID_TAG_NAMES[1], VALID_TAG_NAMES[2],
                    VALID_TAG_NAMES[3], VALID_TAG_NAMES[4]);
            mutableTask.setTags(newTags);
        });
        Thread.sleep(50);

        //Task indexed at 4
        model.add("Task 4 With 3 Tags", mutableTask -> {
            Set<Tag> newTags = TaskFactory.convertTagNamesToTags(VALID_TAG_NAMES[0],
                    VALID_TAG_NAMES[1], VALID_TAG_NAMES[2]);
            mutableTask.setTags(newTags);
        });
        Thread.sleep(50);

        //Task indexed at 3
        model.add("Task 3 With 1 Tag", mutableTask -> {
            Set<Tag> newTags = TaskFactory.convertTagNamesToTags(VALID_TAG_NAMES[1]);
            mutableTask.setTags(newTags);
        });
        Thread.sleep(50);

        //Task indexed at 2
        model.add("Task 2 With 1 Tag", mutableTask -> {
            Set<Tag> newTags = TaskFactory.convertTagNamesToTags(VALID_TAG_NAMES[0]);
            mutableTask.setTags(newTags);
        });
        Thread.sleep(50);

        //Task indexed at 1
        model.add("Task 1 No Tags");

    }

    /* Add Tag Test */
    @Test
    public void testAddTag_addSingleTag() throws Exception {
        //Adds a single tag to a task without tags.
        Set<Tag> expectedTags = TaskFactory.convertTagNamesToTags(VALID_TAG_NAMES[5]);
        setParameter("1 " + VALID_TAG_NAMES[5]);
        execute(true);

        ImmutableTask task = getTaskAt(1);
        assertEquals(expectedTags, task.getTags());
    }

    @Test
    public void testAddTag_addMaxTags() throws Exception {
        //Adds 5 tags to a task without tags.
        Set<Tag> expectedTags = TaskFactory.convertTagNamesToTags(VALID_TAG_NAMES[0],
                VALID_TAG_NAMES[1], VALID_TAG_NAMES[2],
                VALID_TAG_NAMES[3], VALID_TAG_NAMES[4]);

        StringJoiner joiner = new StringJoiner(" ");
        joiner.add("1")
                .add(VALID_TAG_NAMES[0])
                .add(VALID_TAG_NAMES[1])
                .add(VALID_TAG_NAMES[2])
                .add(VALID_TAG_NAMES[3])
                .add(VALID_TAG_NAMES[4]);

        setParameter(joiner.toString());
        execute(true);

        ImmutableTask task = getTaskAt(1);
        assertEquals(expectedTags, task.getTags());
    }

    @Test
    public void testAddTag_unrestrictedSeparators() throws Exception {
        //Allows separators such as space, commas.
        Set<Tag> expectedTags = TaskFactory.convertTagNamesToTags("Pikachu", "Pichu", "Raichu");
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
        //Adds tags that contains duplicated tags
        setParameter("1 hello say hello again");
        execute(false);
    }

    /* Helper Methods */
}
