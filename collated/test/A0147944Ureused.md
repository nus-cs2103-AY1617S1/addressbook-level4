# A0147944Ureused
###### \java\guitests\guihandles\TaskCardHandle.java
``` java
package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.task.model.task.ReadOnlyTask;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String COLOR_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String DEADLINE_FIELD_ID = "#deadlineLabel";
    private static final String STARTTIME_FIELD_ID = "#startTimeLabel";
    private static final String ENDTIME_FIELD_ID = "#endTimeLabel";
   

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getTaskName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getStartTime() {
        return getTextFromLabel(STARTTIME_FIELD_ID).replace(" from ", "");
    }

    public String getEndTime() {
        return getTextFromLabel(ENDTIME_FIELD_ID).replace(" to ", "");
    }

    public String getDeadline() {
        return getTextFromLabel(DEADLINE_FIELD_ID).replace(" ends ", "");
    }
    
    public boolean getDoneStatus() {
        if(getColorFromLabel(COLOR_FIELD_ID, node).equals("-fx-background-color: #ADDBAC")) {
           
            return true;
        }
                
        else {
            return false;
        }
    }

    public boolean isSameTask(ReadOnlyTask task){
        return getTaskName().equals(task.getName().fullName) && getStartTime().equals(task.getStartTime().value)
                && getEndTime().equals(task.getEndTime().value) && getDeadline().equals(task.getDeadline().value)
                && getDoneStatus() == task.getStatus().getDoneStatus();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getTaskName().equals(handle.getTaskName())
                    && getDeadline().equals(handle.getDeadline()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getTaskName() + " " + getDeadline();
    }
}
```
###### \java\seedu\task\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_add_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add inval#id name", expectedMessage);
        assertCommandBehavior(
                "add Invalid start time, from 3@0.00am to 11.11pm by 10.00pm #tagged", expectedMessage);
        assertCommandBehavior(
                "add Invalid end time, from 10.00am to 11.7@1pm by 10.00pm #tagged", expectedMessage);
        assertCommandBehavior(
                "add Invalid deadline, from 10.00am to 11.11pm by 10#00pm #tagged", expectedMessage);
        assertCommandBehavior(
                "add Invalid tag, from 10.00am to 11.11pm by 10.00pm /#tagged#", expectedMessage);
    }

    @Test
    public void execute_add_invalidTaskData() throws Exception {
        assertCommandBehavior(
                "add Inval/id name, from 10.00 to 11.11 by 10.00", Name.MESSAGE_NAME_CONSTRAINTS);
        /*assertCommandBehavior(
                "add Invalid start time, from 20.00 to 11.11 by 10.00", StartTime.MESSAGE_STARTTIME_CONSTRAINTS);
        assertCommandBehavior(
                "add Invalid end time, from 10.00 to 19.11 by 10.00", EndTime.MESSAGE_ENDTIME_CONSTRAINTS);
        assertCommandBehavior(
                "add Invalid deadline, from 10.00 to 11.11 by 1000", Deadline.MESSAGE_DEADLINE_CONSTRAINTS);*/
        assertCommandBehavior(
                "add Invalid tag, from 10.00am to 11.11pm by 10.00 #invalid_-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS);

    }
```
###### \java\seedu\task\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_find_matchesPartialWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("KEYKEYKEY sduauo");
        Task p1 = helper.generateTaskWithName("KE Y");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, pTarget3, pTarget2);
        TaskManager expectedTM = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget3, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTM,
                expectedList);
    }
```
###### \java\seedu\task\logic\LogicManagerTest.java
``` java
        Task revise() throws Exception {
            Name name = new Name("Revise CS2103");
            StartTime startTime = new StartTime("2016-10-25");
            EndTime endTime = new EndTime("2016-11-25 15:00");
            Deadline location = new Deadline("2016-11-26 15:00");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, startTime, endTime, location, tags, new Status());
        }

        /**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values guarantees the returned person will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the task data field values
         */
        Task generateTask(int seed) throws Exception {
            return new Task(
                    new Name("Task " + seed),
                    new StartTime("11." + seed + "1am"),
                    new EndTime("11." + seed + "1pm"),
                    new Deadline("12." + seed + "2am"),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))),
                    new Status()
            );
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getName().toString());
            cmd.append(", from ").append(p.getStartTime());
            cmd.append(" to ").append(p.getEndTime());
            cmd.append(" by ").append(p.getDeadline());

            UniqueTagList tags = p.getTags();
            for(Tag t: tags){
                cmd.append(" #").append(t.tagName);
            }

            return cmd.toString();
        }
```
###### \java\seedu\task\logic\LogicManagerTest.java
``` java
        Task generateTaskWithName(String name) throws Exception {
            return new Task(
                    new Name(name),
                    new StartTime("11.11am"),
                    new EndTime("12.34pm"),
                    new Deadline("12.40pm"),
                    new UniqueTagList(new Tag("tag")),
                    new Status()
            );
        }
```
###### \java\seedu\task\testutil\TestUtil.java
``` java
    private static Task[] getSampleTaskData() {
        try {
            return new Task[]{
                    new Task(new Name("Accompany mom to the doctor"), new StartTime("10.00am"), new EndTime("02.59am"), new Deadline("10.00pm"), new UniqueTagList(), new Status()),
                    new Task(new Name("Borrow software engineering book"), new StartTime("10.00am"), new EndTime("02.59am"), new Deadline("10.00pm"), new UniqueTagList(), new Status()),
                    new Task(new Name("Call Jim"), new StartTime("10.00am"), new EndTime("02.59am"), new Deadline("10.00pm"), new UniqueTagList(), new Status()),
                    new Task(new Name("Do homework"), new StartTime("10.00am"), new EndTime("02.59am"), new Deadline("10.00pm"), new UniqueTagList(), new Status()),
                    new Task(new Name("Edit AddressBook file"), new StartTime("10.00am"), new EndTime("02.59am"), new Deadline("10.00pm"), new UniqueTagList(), new Status()),
                    new Task(new Name("Finish up the project"), new StartTime("10.00am"), new EndTime("02.59am"), new Deadline("10.00pm"), new UniqueTagList(), new Status()),
                    new Task(new Name("Go for a jog"), new StartTime("10.00am"), new EndTime("02.59am"), new Deadline("10.00pm"), new UniqueTagList(), new Status()),
                    new Task(new Name("Help Jim with his task"), new StartTime("10.00am"), new EndTime("02.59am"), new Deadline("10.00pm"), new UniqueTagList(), new Status()),
                    new Task(new Name("Iron new clothes"), new StartTime("10.00am"), new EndTime("02.59am"), new Deadline("10.00pm"), new UniqueTagList(), new Status())
            };
          
            
            
        } catch (IllegalValueException e) {
            assert false;
            //not possible
            return null;
        }
    }
```
###### \java\seedu\task\testutil\TypicalTestTasks.java
``` java
        try {
            taskA =  new TaskBuilder().withName("Accompany mom to the doctor").withDeadline("2016-10-27 15:00")
                    .withEndTime("2016-10-26 17:00").withStartTime("2016-10-25 02:00")
                    .withTags("gwsMum").withStatus(false, false, false).build();
            taskB = new TaskBuilder().withName("Borrow software engineering book").withDeadline("2016-10-27 16:00")
                    .withEndTime("2016-10-26 16:00").withStartTime("2016-10-25 03:00")
                    .withTags("study", "seRocks").withStatus(false, false, false).build();
            taskC = new TaskBuilder().withName("Call Jim").withStartTime("2016-10-25 04:00").withEndTime("2016-10-26 15:00").withDeadline("2016-10-27 17:00").withStatus(false, false, false).build();
            taskD = new TaskBuilder().withName("Do homework").withStartTime("2016-10-25 05:00").withEndTime("2016-10-26 14:00").withDeadline("2016-10-27 18:00").withStatus(false, false, false).build();
            taskE = new TaskBuilder().withName("Edit AddressBook file").withStartTime("2016-10-25 06:00").withEndTime("2016-10-26 13:49").withDeadline("2016-10-27 19:00").withStatus(false, false, false).build();
            taskF = new TaskBuilder().withName("Finish up the project").withStartTime("2016-10-25 07:00").withEndTime("2016-10-26 13:23").withDeadline("2016-10-27 20:00").withStatus(false, false, false).build();
            taskG = new TaskBuilder().withName("Go for a jog").withStartTime("2016-10-25 08:00").withEndTime("2016-10-26 12:00").withDeadline("2016-10-27 20:59").withStatus(false, false, false).build();
            taskH = new TaskBuilder().withName("Help Jim with his task").withStartTime("2016-10-25 09:00").withEndTime("2016-10-26 11:00").withDeadline("2016-10-27 21:00").withStatus(false, false, false).build();
            taskI = new TaskBuilder().withName("Iron new clothes").withStartTime("2016-10-25 02:59").withEndTime("2016-10-26 10:00").withDeadline("2016-10-27 22:00").withStatus(false, false, false).build();
            taskJ =  new TaskBuilder().withName("Accompany dad to the doctor").withDeadline("2016-10-27 15:00")
                    .withEndTime("2016-10-26 17:00").withStartTime("2016-10-25 02:00")
                    .withTags("gwsDad").withStatus(false, false, false).build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
```
