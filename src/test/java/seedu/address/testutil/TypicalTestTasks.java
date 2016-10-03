package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskList;
import seedu.address.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask submitPrototype, submitProgressReport, developerMeeting, researchWhales, learnVim,
            buyBirthdayGift, signUpForYoga, attendWorkshop, updateGithubRepo;

    public TypicalTestTasks() {
        try {
            submitPrototype =  new TaskBuilder().withName("Submit prototype")
                    .withTags("urgent").build();
            submitProgressReport = new TaskBuilder().withName("Submit progress report")
                    .withTags("finance", "urgent").build();
            developerMeeting = new TaskBuilder().withName("Attend developer meeting").build();
            researchWhales = new TaskBuilder().withName("Research on whales").build();
            learnVim = new TaskBuilder().withName("Learn Vim").build();
            buyBirthdayGift = new TaskBuilder().withName("Buy birthday gift").build();
            signUpForYoga = new TaskBuilder().withName("Sign up for yoga").build();

            attendWorkshop = new TaskBuilder().withName("Attend workshop").build();
            updateGithubRepo = new TaskBuilder().withName("Update GitHub repository").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskListWithSampleData(TaskList tl) {

        try {
            tl.addPerson(new Task(submitPrototype));
            tl.addPerson(new Task(submitProgressReport));
            tl.addPerson(new Task(developerMeeting));
            tl.addPerson(new Task(researchWhales));
            tl.addPerson(new Task(learnVim));
            tl.addPerson(new Task(buyBirthdayGift));
            tl.addPerson(new Task(signUpForYoga));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{submitPrototype, submitProgressReport, developerMeeting, researchWhales, learnVim, buyBirthdayGift, signUpForYoga};
    }

    public TaskList getTypicalTaskList(){
        TaskList tl = new TaskList();
        loadTaskListWithSampleData(tl);
        return tl;
    }
}
