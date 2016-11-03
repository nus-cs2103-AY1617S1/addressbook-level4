package seedu.address.logic;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.TaskManager;
import seedu.address.model.task.Task;

/*
 * Responsible for testing the correct execution of FindCommand
 */
public class FindCommandTest extends CommandTest {

    /*
     * FindCommand format: find KEYWORD [AND] [MORE_KEYWORDS] [exact!]
     * 
     * Equivalence partitions for parameters: empty parameter, KEYWORD, KEYWORD
     * MORE_KEYWORDS, KEYWORD AND MORE_KEYWORDS, KEYWORD [AND] [MORE_KEYWORDS] exact!
     */

    // -------------------------test for invalid commands------------------------------------------------

    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    // ------------------------------test for valid cases------------------------------------------------

    /*
     * The search should not be case sensitive.
     */
    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateUndoneTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateUndoneTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateUndoneTaskWithName("key key");
        Task p4 = helper.generateUndoneTaskWithName("KEy sduauo");

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB,
                expectedList);
    }

    /*
     * By default, the matching should only compare word stems of keywords.
     * (Stemming is the process of reducing inflected (or sometimes derived)
     * words to their word stem. Example: "stems", "stemmer", "stemming",
     * "stemmed" as based on "stem")
     */
    @Test
    public void execute_find_onlyMatchesStemWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateUndoneTaskWithName("bla bla KEYs bla");
        Task pTarget2 = helper.generateUndoneTaskWithName("bla KEY bla bceofeia");
        Task p1 = helper.generateUndoneTaskWithName("KE Y");
        Task p2 = helper.generateUndoneTaskWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB,
                expectedList);
    }

    /*
     * Only events and tasks matching the exact keyword should be returned if
     * the command contains the exact! parameter.
     */
    @Test
    public void execute_find_onlyMatchesExactWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateUndoneTaskWithName("bla KEY bla bceofeia");
        Task p1 = helper.generateUndoneTaskWithName("KE Y");
        Task p2 = helper.generateUndoneTaskWithName("KEYKEYKEY sduauo");
        Task p3 = helper.generateUndoneTaskWithName("bla bla KEYs bla");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, p3);
        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY exact!", Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB, expectedList);
    }

    /*
     * By default, events and tasks matching at least one keyword should be
     * returned (i.e. OR search).
     */
    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateUndoneTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateUndoneTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateUndoneTaskWithName("key key");
        Task p1 = helper.generateUndoneTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find key rAnDoM", Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB, expectedList);
    }

    /*
     * Only events and tasks matching both groups of keywords should be returned
     * if the two groups of keywords are connected by AND (i.e. AND search). The
     * order of the keywords does not matter.
     */
    @Test
    public void execute_find_matchesAllKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateUndoneTaskWithName("bla rAnDoM bla KEY bla");
        Task pTarget2 = helper.generateUndoneTaskWithName("keys rAnDoM keys");
        Task p1 = helper.generateUndoneTaskWithName("bla rAnDoM bla bceofeia");
        Task p2 = helper.generateUndoneTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, p2, pTarget2);
        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find key AND rAnDoM", Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB, expectedList);
    }

    /*
     * Use both the AND and exact! parameter.
     */
    @Test
    public void execute_find_matchesAllExactKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateUndoneTaskWithName("bla rAnDoM bla KEY bla");
        Task p1 = helper.generateUndoneTaskWithName("bla rAnDoM bla bceofeia");
        Task p2 = helper.generateUndoneTaskWithName("keys rAnDoM keys");
        Task p3 = helper.generateUndoneTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, p2, p3);
        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find exact! key AND rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB, expectedList);
    }
}
