package tars.logic;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import org.junit.Test;

import tars.logic.commands.Command;
import tars.logic.commands.FindCommand;
import tars.model.Tars;
import tars.model.task.Task;
import tars.model.task.TaskQuery;

/**
 * Logic command test for find
 * 
 * @@author A0124333U
 */
public class FindLogicCommandTest extends LogicCommandTest {

    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
    public void execute_find_quickSearchOnlyMatchesFullWordsInNames()
            throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p1 = helper.generateTaskWithName("KE Y");
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks =
                helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        Tars expectedTars = helper.generateTars(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        String searchKeywords = "\nQuick Search Keywords: [KEY]";

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size())
                        + searchKeywords,
                expectedTars, expectedList);
    }

    @Test
    public void execute_find_quickSearchIsNotCaseSensitive() throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task p1 = helper.generateTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithName("key key");
        Task p4 = helper.generateTaskWithName("KEy sduauo");

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        Tars expectedTars = helper.generateTars(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        String searchKeywords = "\nQuick Search Keywords: [KEY]";

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size())
                        + searchKeywords,
                expectedTars, expectedList);
    }

    @Test
    public void execute_find_quickSearchMatchesIfAllKeywordPresent()
            throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task p1 = helper.generateTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task p3 = helper.generateTaskWithName("sduauo");
        Task pTarget1 = helper.generateTaskWithName("key key rAnDoM");

        List<Task> fourTasks = helper.generateTaskList(p1, p2, p3, pTarget1);
        Tars expectedTars = helper.generateTars(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1);
        helper.addToModel(model, fourTasks);

        String searchKeywords = "\nQuick Search Keywords: [key, rAnDoM]";

        assertCommandBehavior("find key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size())
                        + searchKeywords,
                expectedTars, expectedList);
    }

    @Test
    public void execute_find_filterSearchMatchesIfAllKeywordPresent()
            throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task pTarget1 = helper.meetAdam();
        Task p1 = helper.generateTask(2);
        Task p2 = helper.generateTask(3);

        List<Task> threeTasks = helper.generateTaskList(pTarget1, p1, p2);
        Tars expectedTars = helper.generateTars(threeTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1);
        helper.addToModel(model, threeTasks);

        String searchKeywords = "\nFilter Search Keywords: [Task Name: adam] "
                + "[DateTime: 01/09/2016 1400 to 01/09/2016 1500] [Priority: medium] "
                + "[Status: Undone] [Tags: tag1]";

        assertCommandBehavior(
                "find /n adam /dt 01/09/2016 1400 to 01/09/2016 1500 /p medium /ud /t tag1",
                Command.getMessageForTaskListShownSummary(expectedList.size())
                        + searchKeywords,
                expectedTars, expectedList);
    }

    @Test
    public void execute_find_filterSearchWithoutDateTimeQuery()
            throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task pTarget1 = helper.meetAdam();
        Task p1 = helper.generateTask(2);
        Task p2 = helper.generateTask(3);

        List<Task> threeTasks = helper.generateTaskList(pTarget1, p1, p2);
        Tars expectedTars = helper.generateTars(threeTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1);
        helper.addToModel(model, threeTasks);

        String searchKeywords = "\nFilter Search Keywords: [Task Name: adam] "
                + "[Priority: medium] " + "[Status: Undone] [Tags: tag1]";

        assertCommandBehavior("find /n adam /p medium /ud /t tag1",
                Command.getMessageForTaskListShownSummary(expectedList.size())
                        + searchKeywords,
                expectedTars, expectedList);
    }

    @Test
    public void execute_find_filterSearchSingleDateTimeQuery()
            throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task pTarget1 = helper.meetAdam();
        Task p1 = helper.generateTask(2);
        Task p2 = helper.generateTask(3);

        List<Task> threeTasks = helper.generateTaskList(pTarget1, p1, p2);
        Tars expectedTars = helper.generateTars(threeTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1);
        helper.addToModel(model, threeTasks);

        String searchKeywords =
                "\nFilter Search Keywords: [DateTime: 01/09/2016 1400] ";

        assertCommandBehavior("find /dt 01/09/2016 1400",
                Command.getMessageForTaskListShownSummary(expectedList.size())
                        + searchKeywords,
                expectedTars, expectedList);
    }

    @Test
    public void execute_find_filterSearchTaskNotFound() throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task pTarget1 = helper.meetAdam();
        Task p1 = helper.generateTask(2);
        Task p2 = helper.generateTask(3);

        List<Task> threeTasks = helper.generateTaskList(pTarget1, p1, p2);
        Tars expectedTars = helper.generateTars(threeTasks);
        List<Task> expectedList = helper.generateTaskList();
        helper.addToModel(model, threeTasks);

        String searchKeywords =
                "\nFilter Search Keywords: [DateTime: 01/09/2010 1400] ";

        assertCommandBehavior("find /dt 01/09/2010 1400",
                Command.getMessageForTaskListShownSummary(expectedList.size())
                        + searchKeywords,
                expectedTars, expectedList);
    }

    @Test
    public void execute_find_filterSearchBothDoneAndUndoneSearched()
            throws Exception {

        assertCommandBehavior("find /do /ud",
                TaskQuery.MESSAGE_BOTH_STATUS_SEARCHED_ERROR);
    }

    @Test
    public void execute_find_filterSearchMultipleFlagsUsed() throws Exception {
        TypicalTestDataHelper helper = new TypicalTestDataHelper();
        Task pTarget1 = helper.meetAdam();
        Task p1 = helper.generateTask(2);
        Task p2 = helper.generateTask(3);

        List<Task> threeTasks = helper.generateTaskList(pTarget1, p1, p2);
        Tars expectedTars = helper.generateTars(threeTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1);
        helper.addToModel(model, threeTasks);

        String searchKeywords =
                "\nFilter Search Keywords: [Task Name: meet adam] "
                        + "[Priority: medium] "
                        + "[Status: Undone] [Tags: tag2 tag1]";

        assertCommandBehavior("find /n meet adam /p medium /ud /t tag1 /t tag2",
                Command.getMessageForTaskListShownSummary(expectedList.size())
                        + searchKeywords,
                expectedTars, expectedList);
    }
}
